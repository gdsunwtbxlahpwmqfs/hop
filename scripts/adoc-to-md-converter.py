#!/usr/bin/env python3
"""
AsciiDoc to Markdown Converter
Converts .adoc files from hop-user-manual to .md with Chinese translation
"""
import os
import re
import json
import sys
import shutil
from pathlib import Path
from datetime import datetime

PROJECT_ROOT = Path(__file__).resolve().parent.parent
CONFIG_FILE = PROJECT_ROOT / "scripts" / "adoc-to-md-config.json"


def load_config():
    """Load configuration file"""
    with open(CONFIG_FILE, 'r', encoding='utf-8') as f:
        return json.load(f)


def parse_user_url_md(filepath):
    """Parse user-url.md file to extract mappings"""
    mappings = []
    current_category = None

    with open(filepath, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.strip()

            # Detect category header
            if line.startswith('## ') and '(' in line:
                category_match = re.match(r'^## (\S+)\s*\((\d+)\s*个\)', line)
                if category_match:
                    current_category = category_match.group(1)
                    continue

            # Detect table rows
            if line.startswith('|') and current_category:
                parts = [p.strip() for p in line.split('|')[1:-1]]
                # Skip header rows and separator rows
                if len(parts) >= 2 and not parts[0].startswith('-') and parts[1] != 'URL':
                    component = parts[0]
                    url = parts[1]
                    description = parts[2] if len(parts) > 2 else ""

                    # Convert URL to file path
                    # /best-practices/index.html -> best-practices/index.adoc
                    adoc_path = url.replace('.html', '.adoc').lstrip('/')

                    mappings.append({
                        'category': current_category,
                        'component': component,
                        'url': url,
                        'adoc_path': adoc_path,
                        'description': description,
                        'md_path': adoc_path.replace('.adoc', '.md')
                    })

    return mappings


def parse_asciidoc(content):
    """Parse AsciiDoc content and extract structured data"""
    result = {
        'title': '',
        'description': '',
        'imagesdir': '',
        'images': [],
        'cross_refs': [],
        'code_blocks': [],
        'raw_content': content
    }

    # Extract title (= Title)
    title_match = re.search(r'^= (.+)$', content, re.MULTILINE)
    if title_match:
        result['title'] = title_match.group(1).strip()

    # Extract description
    desc_match = re.search(r':description:\s*(.+)$', content, re.MULTILINE)
    if desc_match:
        result['description'] = desc_match.group(1).strip()

    # Extract imagesdir
    imagesdir_match = re.search(r':imagesdir:\s*(.+)$', content, re.MULTILINE)
    if imagesdir_match:
        result['imagesdir'] = imagesdir_match.group(1).strip()

    # Extract image references (image:path[alt])
    image_pattern = re.compile(r'image:([^\[]+?)(?:\[([^\]]*)\])?(?=\s|$)')
    result['images'] = image_pattern.findall(content)

    # Extract cross references (xref:path[text])
    xref_pattern = re.compile(r'xref:([^\[]+?)(?:\[([^\]]*)\])?')
    result['cross_refs'] = xref_pattern.findall(content)

    # Extract code blocks ([source,lang]\n----\n...\n----)
    code_pattern = re.compile(r'\[source,(\w+)\]\s*\n----\s*\n(.*?)\n----', re.DOTALL)
    result['code_blocks'] = code_pattern.findall(content)

    return result


def remove_license_header(content):
    """Remove ASF license header (//// ... //// blocks)"""
    pattern = r'////.*?////\s*'
    return re.sub(pattern, '', content, flags=re.DOTALL)


def remove_asciidoc_directives(content):
    """Remove AsciiDoc directives"""
    # Remove :key: value directives
    content = re.sub(r'^:[^:]+:.*$', '', content, flags=re.MULTILINE)
    # Remove [[anchor]] markers
    content = re.sub(r'^\[\[.*?\]\]\s*$', '', content, flags=re.MULTILINE)
    # Remove [%noheader] and other attributes
    content = re.sub(r'^\[.*?\]\s*$', '', content, flags=re.MULTILINE)
    # Remove empty lines left behind
    content = re.sub(r'\n{3,}', '\n\n', content)
    return content


def convert_headings(content):
    """Convert heading format (= -> #)"""
    # First, handle titles with embedded images (= image:path[alt, attributes] Title)
    # Extract just the text part and create a proper heading
    def fix_heading_with_image(match):
        prefix = match.group(1)  # = or == etc.
        rest = match.group(2).strip()

        # Check if it contains an image reference
        # Match image:path[anything] possibly followed by more text
        image_match = re.match(r'image:([^\[]+?)\[(.*?)\]\s*(.*)', rest, re.DOTALL)
        if image_match:
            path = image_match.group(1)
            alt_with_attrs = image_match.group(2)
            title_text = image_match.group(3).strip()

            # Extract just the alt text (first part before comma)
            alt = alt_with_attrs.split(',')[0].strip() if alt_with_attrs else ''

            if title_text:
                return f'{prefix} {title_text}'
            elif alt:
                return f'{prefix} {alt}'
            else:
                # Use filename as fallback
                filename = path.split('/')[-1].replace('.svg', '').replace('.png', '').replace('.jpg', '')
                return f'{prefix} {filename}'

        return match.group(0)

    # Fix headings with images first - match = image:... up to end of line
    content = re.sub(r'^(={1,4})\s+(image:.*?)$', fix_heading_with_image, content, flags=re.MULTILINE)

    # = Title -> # Title (only at line start)
    content = re.sub(r'^= (.+)$', r'# \1', content, flags=re.MULTILINE)
    # == Section -> ## Section
    content = re.sub(r'^== (.+)$', r'## \1', content, flags=re.MULTILINE)
    # === Subsection -> ### Subsection
    content = re.sub(r'^=== (.+)$', r'### \1', content, flags=re.MULTILINE)
    # ==== Subsubsection -> #### Subsubsection
    content = re.sub(r'^==== (.+)$', r'#### \1', content, flags=re.MULTILINE)
    return content


def convert_images(content, imagesdir):
    """Convert image references"""
    def replace_image(match):
        path = match.group(1).strip()
        alt = match.group(2) or ''

        # If online image (http/https), remove it
        if path.startswith(('http://', 'https://')):
            return f'<!-- 在线图片已移除: {path} -->'

        # Skip if path is too long (likely a parsing error)
        if len(path) > 200:
            return match.group(0)

        # Build local image path
        if imagesdir:
            full_path = f"{imagesdir}/{path}"
        else:
            # If imagesdir is not set, images are still relative to assets/images
            # This is the standard AsciiDoc convention for this project
            full_path = f"assets/images/{path}"

        # Convert to Markdown image syntax
        return f'![{alt}]({full_path})'

    # First, handle images in headings (= image:path[alt] Title)
    def replace_image_in_heading(match):
        prefix = match.group(1)  # # or ## etc.
        image_part = match.group(2)
        rest = match.group(3).strip()

        # Extract image info
        image_match = re.match(r'!\[([^\]]*)\]\(([^)]+)\)', image_part)
        if image_match:
            alt = image_match.group(1)
            path = image_match.group(2)
            # Just use the text part for heading
            if rest:
                return f'{prefix} {rest}'
            elif alt:
                return f'{prefix} {alt}'
            else:
                return f'{prefix} Documentation'

        return match.group(0)

    # Convert images with double colon syntax (image::path[alt]) - relative to imagesdir
    # Path must be valid: alphanumeric, slashes, dots, hyphens, underscores
    pattern_double = re.compile(r'image::([a-zA-Z0-9_/.\-]+?)\[(.*?)\]')
    content = pattern_double.sub(replace_image, content)

    # Convert images with single colon syntax (image:path[alt])
    pattern_single = re.compile(r'image:([a-zA-Z0-9_/.\-]+?)\[(.*?)\]')
    content = pattern_single.sub(replace_image, content)

    # Fix headings that now have images
    content = re.sub(r'^(#{1,4})\s+(!\[[^\]]*\]\([^)]+\))\s*(.*?)$', replace_image_in_heading, content, flags=re.MULTILINE)

    return content


def convert_cross_refs(content):
    """Convert cross references"""
    def replace_xref(match):
        path = match.group(1).strip()
        text = match.group(2) or path

        # Convert .adoc links to .md
        if path.endswith('.adoc'):
            path = path[:-5] + '.md'
        elif not path.endswith(('.md', '.html', '.json', '.yaml', '.yml', '.txt', '.pdf')):
            if not path.startswith(('http://', 'https://', '#', 'mailto:')):
                # No extension, assume .md
                path = path + '.md'

        return f'[{text}]({path})'

    # Match xref:path[text] - path can contain dots, slashes, hyphens, underscores
    # The key is that path ends at [ character
    pattern = re.compile(r'xref:([^\[]+?)\[([^\]]*)\]')
    return pattern.sub(replace_xref, content)


def convert_code_blocks(content):
    """Convert code blocks"""
    def replace_code(match):
        lang = match.group(1)
        code = match.group(2).strip()
        return f'```{lang}\n{code}\n```'

    pattern = re.compile(r'\[source,(\w+)\]\s*\n----\s*\n(.*?)\n----', re.DOTALL)
    return pattern.sub(replace_code, content)


def convert_lists(content):
    """Convert AsciiDoc lists to Markdown lists"""
    # Convert * items to - items (but not inside code blocks)
    lines = content.split('\n')
    result = []
    in_code_block = False

    for line in lines:
        if line.strip().startswith('```'):
            in_code_block = not in_code_block

        if not in_code_block:
            # Convert * item to - item (only at line start)
            if re.match(r'^\s*\*\s', line):
                line = re.sub(r'^(\s*)\*\s', r'\1- ', line)

        result.append(line)

    return '\n'.join(result)


def convert_tables(content):
    """Convert AsciiDoc tables to Markdown tables"""
    def replace_table(match):
        table_content = match.group(0)

        # Extract table rows
        rows = []
        for line in table_content.split('\n'):
            line = line.strip()
            # Skip empty lines and delimiter lines
            if not line or line in ('|===', '!===', '!==='):
                continue
            if line.startswith('|') and len(line) > 1:
                # Remove leading | and split by |
                cells = [c.strip() for c in line[1:].split('|')]
                # Filter out empty cells at the end
                while cells and not cells[-1]:
                    cells.pop()
                if cells:
                    rows.append(cells)

        if not rows:
            return table_content

        # Find max columns
        max_cols = max(len(row) for row in rows) if rows else 1

        # Pad rows to have same number of columns
        for row in rows:
            while len(row) < max_cols:
                row.append('')

        # Build Markdown table
        md_rows = []
        for i, row in enumerate(rows):
            # Skip rows that look like separators
            if all(cell.strip() in ('', '---', '===', '----') for cell in row):
                continue
            md_rows.append('| ' + ' | '.join(row) + ' |')
            if i == 0:
                # Add separator row after first row
                md_rows.append('|' + '|'.join(['---'] * max_cols) + '|')

        if not md_rows:
            return table_content

        return '\n'.join(md_rows)

    # Match |=== ... ===| table blocks (including nested tables with !===)
    pattern = re.compile(r'\|===.*?\|===', re.DOTALL)
    return pattern.sub(replace_table, content)


def translate_content(content, config):
    """Translate content to Chinese, preserving technical terms"""
    # Common English phrases to Chinese mapping (standalone lines only)
    common_phrases = {
        'Description': '描述',
        'Options': '选项',
        'Supported Engines': '支持的引擎',
        'Logging': '日志',
        'Example': '示例',
        'Examples': '示例',
        'Note': '注意',
        'Warning': '警告',
        'Tip': '提示',
        'See also': '另请参阅',
        'Default': '默认值',
        'Required': '必需',
        'Optional': '可选',
        'Introduction': '简介',
        'Overview': '概述',
        'Summary': '摘要',
        'Background': '背景',
        'Prerequisites': '前提条件',
        'Syntax': '语法',
        'Parameters': '参数',
        'Settings': '设置',
        'Configuration': '配置',
        'Usage': '用法',
        'Features': '功能特性',
        'Limitations': '限制',
        'Known Issues': '已知问题',
        'Troubleshooting': '故障排除',
        'FAQ': '常见问题',
        'Appendix': '附录',
        'References': '参考文献',
        'Transform name': '转换名称',
        'Action name': '动作名称',
        'Type': '类型',
        'Name': '名称',
        'Value': '值',
        'Description': '描述',
        'Plugin': '插件',
        'Transform': '转换',
        'Pipeline': 'Pipeline',
        'Workflow': 'Workflow',
    }

    translated = content

    # Replace common phrases (only at line start or standalone lines)
    for en, zh in common_phrases.items():
        # Match standalone lines
        pattern = re.compile(r'^\s*' + re.escape(en) + r'\s*$', re.MULTILINE)
        translated = pattern.sub(zh, translated)

    return translated


def copy_local_images(source_dir, target_dir, images):
    """Copy local images to target directory"""
    copied = 0
    skipped = 0

    for image_path, _ in images:
        # Skip online images
        if image_path.startswith(('http://', 'https://')):
            continue

        # Build source file path
        src = Path(source_dir) / image_path
        dst = Path(target_dir) / image_path

        if src.exists():
            # Create target directory
            dst.parent.mkdir(parents=True, exist_ok=True)

            # Copy file
            if not dst.exists() or src.stat().st_mtime > dst.stat().st_mtime:
                shutil.copy2(src, dst)
                copied += 1
            else:
                skipped += 1

    return copied, skipped


def process_images_for_file(adoc_content, imagesdir, source_dir, target_dir, adoc_path):
    """Process images for a single file"""
    # Extract all image references
    # Use [^\[\]\n] to avoid matching newlines and brackets
    image_pattern = re.compile(r'image::([^\[\]\n]+?)\[(.*?)\]')
    images = image_pattern.findall(adoc_content)

    # Also check single colon syntax (but not double colon)
    # Use negative lookbehind to avoid matching image::
    image_pattern_single = re.compile(r'(?<!:)image:([^\[\]\n]+?)\[(.*?)\]')
    images.extend(image_pattern_single.findall(adoc_content))

    if not images:
        return 0, 0

    # Build full source directory path
    if imagesdir:
        full_source_dir = Path(source_dir) / imagesdir
    else:
        # If imagesdir is not set, images are still relative to assets/images
        # This is the standard AsciiDoc convention for this project
        full_source_dir = Path(source_dir).parent / 'assets' / 'images'

    return copy_local_images(full_source_dir, target_dir, images)


def convert_single_file(adoc_path, md_path, config, source_dir, target_dir):
    """Convert a single file"""
    try:
        # Read source file
        with open(adoc_path, 'r', encoding='utf-8') as f:
            content = f.read()

        # Parse content FIRST to extract imagesdir before removing directives
        parsed = parse_asciidoc(content)

        # Remove license header
        if config['conversion_rules']['remove_license_header']:
            content = remove_license_header(content)

        # Remove AsciiDoc directives
        if config['conversion_rules']['remove_asciidoc_directives']:
            content = remove_asciidoc_directives(content)

        # Convert format
        converted = content

        if config['conversion_rules']['convert_headings']:
            converted = convert_headings(converted)

        if config['conversion_rules']['convert_images']:
            converted = convert_images(converted, parsed['imagesdir'])

        if config['conversion_rules']['convert_links']:
            converted = convert_cross_refs(converted)

        if config['conversion_rules']['convert_code_blocks']:
            converted = convert_code_blocks(converted)

        # Convert lists
        converted = convert_lists(converted)

        # Convert tables
        converted = convert_tables(converted)

        # Translate content
        if config['conversion_rules']['translate_content']:
            converted = translate_content(converted, config)

        # Process images
        copied, skipped = process_images_for_file(
            content,
            parsed['imagesdir'],
            source_dir,
            target_dir,
            adoc_path
        )

        # Clean up multiple blank lines
        converted = re.sub(r'\n{3,}', '\n\n', converted)

        # Write target file
        md_path.parent.mkdir(parents=True, exist_ok=True)
        with open(md_path, 'w', encoding='utf-8') as f:
            f.write(converted)

        return {
            'status': 'success',
            'copied_images': copied,
            'skipped_images': skipped
        }

    except Exception as e:
        return {
            'status': 'error',
            'error': str(e)
        }


def generate_missing_file_doc(component, category, url, config):
    """Generate help document for missing files"""
    template = config.get('missing_file_template', {})
    issue_url = template.get('issue_url', 'https://github.com/apache/hop/issues')
    email = template.get('email', 'dev@hop.apache.org')

    content = f"""# {component}

> 此文档暂未提供，请联系管理员获取帮助。

## 基本信息

- **组件名称:** {component}
- **所属类目:** {category}
- **文档路径:** {url}

## 联系方式

如果您需要此文档的详细信息，请通过以下方式联系管理员：

- 提交 Issue: {issue_url}
- 邮件列表: {email}

---

*此文档由转换脚本自动生成于 {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}*
"""
    return content


def batch_convert(config):
    """Batch convert all files"""
    source_dir = PROJECT_ROOT / config['source_dir']
    target_dir = PROJECT_ROOT / config['target_dir']
    user_url_file = PROJECT_ROOT / config['user_url_file']

    # Parse mappings
    mappings = parse_user_url_md(user_url_file)

    stats = {
        'total': len(mappings),
        'converted': 0,
        'missing': 0,
        'errors': 0,
        'images_copied': 0,
        'images_skipped': 0
    }

    print(f"   Processing {stats['total']} files...")

    for i, mapping in enumerate(mappings, 1):
        adoc_path = source_dir / mapping['adoc_path']
        md_path = target_dir / mapping['md_path']

        # Check if source file exists
        if not adoc_path.exists():
            # Generate missing file help document
            content = generate_missing_file_doc(
                mapping['component'],
                mapping['category'],
                mapping['url'],
                config
            )

            md_path.parent.mkdir(parents=True, exist_ok=True)
            with open(md_path, 'w', encoding='utf-8') as f:
                f.write(content)

            stats['missing'] += 1
            continue

        # Convert file
        result = convert_single_file(
            adoc_path,
            md_path,
            config,
            source_dir,
            target_dir
        )

        if result['status'] == 'success':
            stats['converted'] += 1
            stats['images_copied'] += result['copied_images']
            stats['images_skipped'] += result['skipped_images']

            if (i % 50 == 0) or (i == stats['total']):
                print(f"  [{i}/{stats['total']}] Processed")
        else:
            print(f"  [{i}/{stats['total']}] [ERROR] {mapping['adoc_path']}: {result['error']}")
            stats['errors'] += 1

    return stats


def validate_conversion(config):
    """Validate conversion results"""
    target_dir = PROJECT_ROOT / config['target_dir']
    user_url_file = PROJECT_ROOT / config['user_url_file']

    mappings = parse_user_url_md(user_url_file)

    missing_files = []
    empty_files = []

    for mapping in mappings:
        md_path = target_dir / mapping['md_path']

        if not md_path.exists():
            missing_files.append(mapping['md_path'])
        elif md_path.stat().st_size == 0:
            empty_files.append(mapping['md_path'])

    return {
        'total_mappings': len(mappings),
        'missing_files': missing_files,
        'empty_files': empty_files,
        'valid_files': len(mappings) - len(missing_files) - len(empty_files)
    }


def main():
    """Main function"""
    print(f"==> Starting conversion {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")

    config = load_config()
    print(f"   Config loaded")

    # Execute batch conversion
    stats = batch_convert(config)

    print(f"\n==> Conversion statistics:")
    print(f"   Total: {stats['total']}")
    print(f"   Converted: {stats['converted']}")
    print(f"   Missing: {stats['missing']}")
    print(f"   Errors: {stats['errors']}")
    print(f"   Images copied: {stats['images_copied']}")
    print(f"   Images skipped: {stats['images_skipped']}")

    # Validate conversion results
    print(f"\n==> Validating conversion results...")
    validation = validate_conversion(config)
    print(f"   Valid files: {validation['valid_files']}")
    print(f"   Missing files: {len(validation['missing_files'])}")
    print(f"   Empty files: {len(validation['empty_files'])}")

    if validation['missing_files']:
        print(f"\n   Missing files list:")
        for f in validation['missing_files'][:10]:
            print(f"     - {f}")
        if len(validation['missing_files']) > 10:
            print(f"     ... and {len(validation['missing_files']) - 10} more files")

    print(f"\n==> Conversion complete")


if __name__ == "__main__":
    main()
