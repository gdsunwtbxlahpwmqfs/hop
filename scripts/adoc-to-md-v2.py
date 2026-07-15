#!/usr/bin/env python3
"""
AsciiDoc to Markdown Converter v2
Handles ALL AsciiDoc syntax: tables, admonitions, links, includes, passthrough, etc.
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
ADOC_BASE = PROJECT_ROOT / "docs/hop-user-manual/modules/ROOT/pages"
IMAGES_BASE = PROJECT_ROOT / "docs/hop-user-manual/modules/ROOT/assets/images"


def load_config():
    with open(CONFIG_FILE, 'r', encoding='utf-8') as f:
        return json.load(f)


def parse_user_url_md(filepath):
    mappings = []
    current_category = None
    with open(filepath, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.strip()
            if line.startswith('## ') and '(' in line:
                category_match = re.match(r'^## (\S+)\s*\((\d+)\s*个\)', line)
                if category_match:
                    current_category = category_match.group(1)
                    continue
            if line.startswith('|') and current_category:
                parts = [p.strip() for p in line.split('|')[1:-1]]
                if len(parts) >= 2 and not parts[0].startswith('-') and parts[1] != 'URL':
                    component = parts[0]
                    url = parts[1]
                    description = parts[2] if len(parts) > 2 else ""
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


# ─── Include Resolution ───────────────────────────────────────────────

def resolve_includes(content, adoc_path):
    """Resolve include:: directives by inlining snippet content."""
    adoc_dir = Path(adoc_path).parent
    resolved_count = 0

    def do_resolve(match):
        nonlocal resolved_count
        inc_path = match.group(1).strip()
        if inc_path.startswith(('http://', 'https://')):
            return match.group(0)
        target = (adoc_dir / inc_path).resolve()
        if not target.exists():
            return f'<!-- include not found: {inc_path} -->'
        try:
            snippet = target.read_text(encoding='utf-8')
            snippet = _strip_license_header(snippet)
            resolved_count += 1
            return snippet
        except Exception:
            return f'<!-- include error: {inc_path} -->'

    prev_count = -1
    while resolved_count != prev_count:
        prev_count = resolved_count
        content = re.sub(r'^include::([^\[]+)\[\]\s*$', do_resolve, content, flags=re.MULTILINE)
    return content


def _strip_license_header(content):
    return re.sub(r'////.*?////\s*', '', content, flags=re.DOTALL)


# ─── Heading Conversion ──────────────────────────────────────────────

def convert_headings(content):
    def fix_heading_with_image(match):
        prefix = match.group(1)
        rest = match.group(2).strip()
        image_match = re.match(r'image:([^\[]+?)\[(.*?)\]\s*(.*)', rest, re.DOTALL)
        if image_match:
            alt_with_attrs = image_match.group(2)
            title_text = image_match.group(3).strip()
            alt = alt_with_attrs.split(',')[0].strip() if alt_with_attrs else ''
            if title_text:
                return f'{prefix} {title_text}'
            elif alt:
                return f'{prefix} {alt}'
            else:
                filename = image_match.group(1).split('/')[-1]
                for ext in ('.svg', '.png', '.jpg'):
                    filename = filename.replace(ext, '')
                return f'{prefix} {filename}'
        return match.group(0)

    content = re.sub(r'^(={1,4})\s+(image:.*?)$', fix_heading_with_image, content, flags=re.MULTILINE)
    content = re.sub(r'^= (.+)$', r'# \1', content, flags=re.MULTILINE)
    content = re.sub(r'^== (.+)$', r'## \1', content, flags=re.MULTILINE)
    content = re.sub(r'^=== (.+)$', r'### \1', content, flags=re.MULTILINE)
    content = re.sub(r'^==== (.+)$', r'#### \1', content, flags=re.MULTILINE)
    return content


# ─── Image Conversion ─────────────────────────────────────────────────

def convert_images(content, imagesdir):
    def replace_image(match):
        path = match.group(1).strip()
        alt = match.group(2) or ''
        if path.startswith(('http://', 'https://')):
            return f'<!-- online image removed: {path} -->'
        if len(path) > 200:
            return match.group(0)
        if imagesdir:
            full_path = f"{imagesdir}/{path}"
        else:
            full_path = f"assets/images/{path}"
        return f'![{alt}]({full_path})'

    def replace_image_in_heading(match):
        prefix = match.group(1)
        rest = match.group(3).strip()
        if rest:
            return f'{prefix} {rest}'
        return match.group(0)

    pattern_double = re.compile(r'image::([a-zA-Z0-9_/.\-]+?)\[(.*?)\]')
    content = pattern_double.sub(replace_image, content)
    pattern_single = re.compile(r'(?<!:)image:([a-zA-Z0-9_/.\-]+?)\[(.*?)\]')
    content = pattern_single.sub(replace_image, content)
    content = re.sub(r'^(#{1,4})\s+(!\[[^\]]*\]\([^)]+\))\s*(.*?)$', replace_image_in_heading, content, flags=re.MULTILINE)
    return content


# ─── Table Conversion ─────────────────────────────────────────────────

def convert_tables(content):
    """Convert ALL AsciiDoc tables including nested !=== tables."""
    def process_table_block(table_text):
        lines = [l.strip() for l in table_text.split('\n') if l.strip()]
        rows = []
        in_inner = False
        inner_rows = []
        header_done = False

        for line in lines:
            if line in ('|===', '|===='):
                continue
            if line == '!===':
                if not in_inner:
                    in_inner = True
                else:
                    in_inner = False
                    if inner_rows:
                        if rows:
                            rows.append([])
                        for r in inner_rows:
                            rows.append(r)
                        inner_rows = []
                continue

            if in_inner and line.startswith('!'):
                cells = [c.strip() for c in line[1:].split('!')]
                while cells and not cells[-1]:
                    cells.pop()
                if cells:
                    inner_rows.append(cells)
            elif not in_inner and line.startswith('|') and len(line) > 1:
                if line == '|' or line.strip() == '|':
                    rows.append([])
                    continue
                cells = [c.strip() for c in line[1:].split('|')]
                while cells and not cells[-1]:
                    cells.pop()
                if cells:
                    rows.append(cells)

        if not rows:
            return table_text

        max_cols = max(len(r) for r in rows)
        for r in rows:
            while len(r) < max_cols:
                r.append('')

        md_rows = []
        for i, row in enumerate(rows):
            if not any(row):
                continue
            if all(c.strip() in ('---', '====', '----', '') for c in row):
                continue
            cleaned = [c.replace('\n', ' ').strip() for c in row]
            md_rows.append('| ' + ' | '.join(cleaned) + ' |')
            if not header_done:
                md_rows.append('|' + '|'.join(['---'] * max_cols) + '|')
                header_done = True

        return '\n'.join(md_rows) if md_rows else table_text

    def replace_table(match):
        return process_table_block(match.group(0))

    pattern = re.compile(r'\|===.*?\|===', re.DOTALL)
    return pattern.sub(replace_table, content)


# ─── Link Conversion ──────────────────────────────────────────────────

def convert_links(content):
    """Convert ALL AsciiDoc link types to Markdown."""

    # 1. url[text^] and url[text] → [text](url)  (external links)
    def replace_url_link(match):
        url = match.group(1).strip()
        text = match.group(2).strip()
        if text.endswith('^'):
            text = text[:-1].strip()
        return f'[{text}]({url})'
    content = re.sub(r'(https?://[^\s\[]+)\[([^\]]*)\]', replace_url_link, content)

    # 2. link:url[text] → [text](url)
    def replace_link_directive(match):
        url = match.group(1).strip()
        text = match.group(2).strip() if match.group(2) else url
        if text.endswith('^'):
            text = text[:-1].strip()
        return f'[{text}]({url})'
    content = re.sub(r'link:([^\[]+)\[([^\]]*)\]', replace_link_directive, content)

    # 3. <<anchor,text>> → text,  <<anchor>> → (remove)
    def replace_xref_angle(match):
        inner = match.group(1).strip()
        parts = inner.split(',', 1)
        if len(parts) == 2:
            return parts[1].strip().strip('`"')
        return ''
    content = re.sub(r'<<([^>>]+)>>', replace_xref_angle, content)

    # 4. xref:path[text] → [text](path.md)
    def replace_xref(match):
        path = match.group(1).strip()
        text = match.group(2) or path
        # Cross-manual refs
        if '::' in path:
            manual, rel = path.split('::', 1)
            base_urls = {
                'tech-manual': 'https://hop.apache.org/tech-manual/latest/',
                'dev-manual': 'https://hop.apache.org/dev-manual/latest/',
            }
            if manual in base_urls:
                full_url = base_urls[manual] + rel.replace('.adoc', '.html')
                return f'[{text}]({full_url})'
            return f'[{text}]({rel})'
        if path.endswith('.adoc'):
            path = path[:-5] + '.md'
        elif not path.endswith(('.md', '.html', '.json', '.yaml', '.yml', '.txt', '.pdf')):
            if not path.startswith(('http://', 'https://', '#', 'mailto:')):
                path = path + '.md'
        return f'[{text}]({path})'
    content = re.sub(r'xref:([^\[]+?)\[([^\]]*)\]', replace_xref, content)

    # 5. .adoc extensions in already-converted links
    content = content.replace('.adoc#', '.md#')
    # Fix links like file.adoc#anchor.md → file.md#anchor
    content = re.sub(r'\.md\.adoc#', '.md#', content)
    # Fix pattern: something.adoc#_something.md → something.md#_something
    content = re.sub(r'([a-zA-Z0-9_/.\-]+)\.adoc#([^)\s]+)\.md', r'\1.md#\2', content)
    # Fix remaining .adoc references in markdown links
    content = re.sub(r'\]\(([^)]*?)\.adoc([^)]*?)\)', r'](\1.md\2)', content)
    # Fix .html references that should be .md (internal docs only)
    def fix_internal_html(match):
        pre = match.group(1)
        path = match.group(2)
        post = match.group(3)
        if path.startswith(('http://', 'https://')):
            return match.group(0)
        return f']({pre}{path}{post})'
    content = re.sub(r'\]\(([^)]*?)([a-zA-Z0-9_/.\-]+)\.html([^)]*?)\)', fix_internal_html, content)

    return content


# ─── Admonition Conversion ────────────────────────────────────────────

def convert_admonitions(content):
    """Convert AsciiDoc admonitions to Markdown blockquotes."""
    # Inline: TIP: text / NOTE: text / WARNING: text / CAUTION: text / IMPORTANT: text
    labels = {
        'TIP': '💡 提示',
        'NOTE': '📝 注意',
        'WARNING': '⚠️ 警告',
        'CAUTION': '⛔ 警告',
        'IMPORTANT': '❗ 重要',
    }
    for label, zh in labels.items():
        pattern = re.compile(r'^' + label + r':\s*(.+)$', re.MULTILINE)
        content = pattern.sub(lambda m, z=zh: f'> **{z}:** {m.group(1)}', content)

    # Delimited: [WARNING]\n====\ntext\n====
    for label, zh in labels.items():
        def replace_block(match, z=zh):
            text = match.group(1).strip()
            return f'> **{z}:** {text}'
        pattern = re.compile(r'\[' + label + r'\]\s*\n====\s*\n(.*?)\n====', re.DOTALL)
        content = pattern.sub(replace_block, content)

    return content


# ─── Passthrough Conversion ───────────────────────────────────────────

def convert_passthrough(content):
    """Convert +text+ and ++text++ passthrough to backtick code."""
    # ++text++ → `text` (used in link:++url++ patterns)
    content = re.sub(r'\+\+([^+]+)\+\+', r'`\1`', content)
    # `+text+` → `text` (backtick-enclosed passthrough)
    content = re.sub(r'`\+([^+]+)\+`', r'`\1`', content)
    # standalone +text+ → `text` (but not inside code blocks or already backticked)
    content = re.sub(r'(?<!`)`?\+([^+\n]+)\+`?(?![`])', lambda m: f'`{m.group(1)}`', content)
    # AsciiDoc line break: + at end of line → remove
    content = re.sub(r'\s*\+\s*$', '', content, flags=re.MULTILINE)
    return content


# ─── Tag & Marker Removal ────────────────────────────────────────────

def remove_tag_markers(content):
    """Remove // tag:: and // end:: markers."""
    content = re.sub(r'^//\s*tag::[^\[]*\[\]\s*$', '', content, flags=re.MULTILINE)
    content = re.sub(r'^//\s*end::[^\[]*\[\]\s*$', '', content, flags=re.MULTILINE)
    return content


# ─── Code Block Conversion ────────────────────────────────────────────

def convert_code_blocks(content):
    def replace_code(match):
        lang = match.group(1)
        code = match.group(2).strip()
        return f'```{lang}\n{code}\n```'
    content = re.sub(r'\[source,(\w+)\]\s*\n----\s*\n(.*?)\n----', replace_code, content, flags=re.DOTALL)
    # Standalone ---- blocks (no [source] prefix)
    content = re.sub(r'^----\s*$', '```', content, flags=re.MULTILINE)
    return content


# ─── List Conversion ──────────────────────────────────────────────────

def convert_lists(content):
    lines = content.split('\n')
    result = []
    in_code_block = False
    for line in lines:
        if line.strip().startswith('```'):
            in_code_block = not in_code_block
        if not in_code_block:
            if re.match(r'^\s*\*\s', line):
                line = re.sub(r'^(\s*)\*\s', r'\1- ', line)
        result.append(line)
    return '\n'.join(result)


# ─── Cleanup ──────────────────────────────────────────────────────────

def cleanup_content(content):
    """Remove AsciiDoc attributes, directives, and clean up."""
    # Remove [[anchor]] markers
    content = re.sub(r'^\[\[.*?\]\]\s*$', '', content, flags=re.MULTILINE)
    # Remove [%...] attribute blocks
    content = re.sub(r'^\[.*?\]\s*$', '', content, flags=re.MULTILINE)
    # Remove :key: value directives (but not inside code blocks)
    content = re.sub(r'^:[^:]+:.*$', '', content, flags=re.MULTILINE)
    # Remove multiple blank lines
    content = re.sub(r'\n{3,}', '\n\n', content)
    # Remove leading/trailing blank lines
    content = content.strip() + '\n'
    return content


# ─── Image Copy ───────────────────────────────────────────────────────

def copy_local_images(source_dir, target_dir, images):
    copied = skipped = 0
    for image_path, _ in images:
        if image_path.startswith(('http://', 'https://')):
            continue
        src = Path(source_dir) / image_path
        dst = Path(target_dir) / image_path
        if src.exists():
            dst.parent.mkdir(parents=True, exist_ok=True)
            if not dst.exists() or src.stat().st_mtime > dst.stat().st_mtime:
                shutil.copy2(src, dst)
                copied += 1
            else:
                skipped += 1
    return copied, skipped


def process_images_for_file(adoc_content, imagesdir, source_dir, target_dir, adoc_path):
    image_pattern = re.compile(r'image::([^\[\]\n]+?)\[(.*?)\]')
    images = image_pattern.findall(adoc_content)
    image_pattern_single = re.compile(r'(?<!:)image:([^\[\]\n]+?)\[(.*?)\]')
    images.extend(image_pattern_single.findall(adoc_content))
    if not images:
        return 0, 0
    if imagesdir:
        full_source_dir = Path(source_dir) / imagesdir
    else:
        full_source_dir = IMAGES_BASE
    return copy_local_images(full_source_dir, target_dir, images)


# ─── Main Conversion Pipeline ─────────────────────────────────────────

def convert_single_file(adoc_path, md_path, config, source_dir, target_dir):
    try:
        with open(adoc_path, 'r', encoding='utf-8') as f:
            content = f.read()

        # Parse imagesdir before removing directives
        imagesdir_match = re.search(r'^:imagesdir:\s*(.+)$', content, re.MULTILINE)
        imagesdir = imagesdir_match.group(1).strip() if imagesdir_match else ''

        # 0. Resolve includes FIRST (inline snippet content)
        content = resolve_includes(content, adoc_path)

        # 1. Remove license header
        content = _strip_license_header(content)

        # 2. Remove tag markers
        content = remove_tag_markers(content)

        # 3. Convert code blocks (before other conversions to protect content)
        content = convert_code_blocks(content)

        # 4. Convert admonitions
        content = convert_admonitions(content)

        # 5. Convert passthrough
        content = convert_passthrough(content)

        # 6. Convert tables
        content = convert_tables(content)

        # 7. Convert images
        content = convert_images(content, imagesdir)

        # 8. Convert links (xref, link:, url[], <<>>)
        content = convert_links(content)

        # 9. Convert headings
        content = convert_headings(content)

        # 10. Convert lists
        content = convert_lists(content)

        # 11. Cleanup
        content = cleanup_content(content)

        # 12. Process images
        copied, skipped = process_images_for_file(
            content, imagesdir, source_dir, target_dir, adoc_path
        )

        # Write output
        md_path.parent.mkdir(parents=True, exist_ok=True)
        with open(md_path, 'w', encoding='utf-8') as f:
            f.write(content)

        return {'status': 'success', 'copied_images': copied, 'skipped_images': skipped}
    except Exception as e:
        return {'status': 'error', 'error': str(e)}


# ─── Batch & Validate ─────────────────────────────────────────────────

def batch_convert(config):
    source_dir = PROJECT_ROOT / config['source_dir']
    target_dir = PROJECT_ROOT / config['target_dir']
    user_url_file = PROJECT_ROOT / config['user_url_file']
    mappings = parse_user_url_md(user_url_file)

    stats = {'total': len(mappings), 'converted': 0, 'missing': 0,
             'errors': 0, 'images_copied': 0, 'images_skipped': 0}  # type: dict[str, int]
    print(f"   Processing {stats['total']} files...")

    for i, mapping in enumerate(mappings, 1):
        adoc_path = source_dir / mapping['adoc_path']
        md_path = target_dir / mapping['md_path']

        if not adoc_path.exists():
            md_path.parent.mkdir(parents=True, exist_ok=True)
            with open(md_path, 'w', encoding='utf-8') as f:
                f.write(f'# {mapping["component"]}\n\n> 此文档暂未提供，请联系管理员获取帮助。\n')
            stats['missing'] += 1
            continue

        result = convert_single_file(adoc_path, md_path, config, source_dir, target_dir)
        if result['status'] == 'success':
            stats['converted'] += 1
            stats['images_copied'] += result['copied_images']
            stats['images_skipped'] += result['skipped_images']
            if i % 50 == 0 or i == stats['total']:
                print(f"  [{i}/{stats['total']}] Processed")
        else:
            print(f"  [{i}/{stats['total']}] [ERROR] {mapping['adoc_path']}: {result['error']}")
            stats['errors'] += 1
    return stats


def validate_conversion(config):
    target_dir = PROJECT_ROOT / config['target_dir']
    user_url_file = PROJECT_ROOT / config['user_url_file']
    mappings = parse_user_url_md(user_url_file)

    residual_patterns = [
        (r'include::[^\[]+\[\]', 'unresolved include'),
        (r'link:[^\[]+\[[^\]]*\]', 'unconverted link:'),
        (r'(?:^|\s)TIP:', 'TIP: admonition'),
        (r'(?:^|\s)NOTE:', 'NOTE: admonition'),
        (r'(?:^|\s)WARNING:', 'WARNING: admonition'),
        (r'(?:^|\s)CAUTION:', 'CAUTION: admonition'),
        (r'\|===', 'AsciiDoc table delimiter'),
        (r'!===', 'AsciiDoc engine table'),
        (r'<<[^>]+>>', '<<anchor>> reference'),
        (r'//\s*(?:tag|end)::', 'tag marker'),
        (r'`?\+[^+]+\+`?', 'passthrough'),
    ]

    issues = {}
    missing = []
    for mapping in mappings:
        md_path = target_dir / mapping['md_path']
        if not md_path.exists():
            missing.append(mapping['md_path'])
            continue
        content = md_path.read_text(encoding='utf-8')
        for pattern, label in residual_patterns:
            matches = re.findall(pattern, content)
            if matches:
                key = f"{label} ({len(matches)} occurrences)"
                issues[key] = issues.get(key, 0) + 1

    return {'total_mappings': len(mappings), 'missing': missing, 'issues': issues}


def main():
    print(f"==> Starting conversion v2 {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    config = load_config()
    print(f"   Config loaded")

    stats = batch_convert(config)

    print(f"\n==> Conversion statistics:")
    for k, v in stats.items():
        print(f"   {k}: {v}")

    print(f"\n==> Validating conversion results...")
    validation = validate_conversion(config)
    print(f"   Total mappings: {validation['total_mappings']}")
    print(f"   Missing files: {len(validation['missing'])}")
    if validation['issues']:
        print(f"\n   Residual AsciiDoc issues:")
        for issue, count in sorted(validation['issues'].items()):
            print(f"     {issue}: {count} files")
    else:
        print(f"   ✅ No residual AsciiDoc syntax found!")

    print(f"\n==> Conversion complete")


if __name__ == "__main__":
    main()
