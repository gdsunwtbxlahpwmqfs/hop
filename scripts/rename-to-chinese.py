#!/usr/bin/env python3
"""
Phase 3: Rename directories to Chinese and reorganize files.
Uses MANUAL_OVERRIDE from build-url-mapping.py for plugin subcategories.
"""
import os
import re
import json
import shutil
from pathlib import Path
from collections import defaultdict

PROJECT_ROOT = Path(__file__).resolve().parent.parent
MANUAL_DIR = PROJECT_ROOT / "docs" / "hop-assistant-manual"
BUILD_MAPPING = PROJECT_ROOT / "scripts" / "build-url-mapping.py"


def extract_manual_override():
    """Extract MANUAL_OVERRIDE dict from build-url-mapping.py source."""
    content = BUILD_MAPPING.read_text(encoding="utf-8")
    # Find the MANUAL_OVERRIDE dictionary
    match = re.search(r'MANUAL_OVERRIDE\s*=\s*\{(.+?)\n\}', content, re.DOTALL)
    if not match:
        raise ValueError("Cannot find MANUAL_OVERRIDE in build-url-mapping.py")
    dict_text = match.group(0)
    # Execute it safely
    namespace = {}
    exec(dict_text, namespace)
    return namespace["MANUAL_OVERRIDE"]


def build_plugin_subcategory_map(override):
    """Build a map: english_stem → (chinese_category, chinese_subcategory)."""
    mapping = {}
    for url, chinese_path in override.items():
        # url: /pipeline/transforms/csvinput.html
        # chinese_path: 03-转换插件/输入类/CSV文件输入.md
        url_id = url.rstrip("/").split("/")[-1].replace(".html", "").replace("-", "").lower()
        parts = chinese_path.split("/")
        category = parts[0]  # e.g. 03-转换插件
        subcategory = parts[1] if len(parts) > 2 else ""  # e.g. 输入类

        # Determine the english source path stem
        if "/pipeline/transforms/" in url:
            src_stem = url.split("/pipeline/transforms/")[1].replace(".html", "")
        elif "/workflow/actions/" in url:
            src_stem = url.split("/workflow/actions/")[1].replace(".html", "")
        elif "/database/databases/" in url:
            src_stem = url.split("/database/databases/")[1].replace(".html", "")
        elif "/metadata-types/" in url:
            src_stem = url.split("/metadata-types/")[1].replace(".html", "")
        else:
            continue

        mapping[src_stem.lower().replace("-", "").replace("_", "")] = (category, subcategory)
    return mapping


# Directory mapping for non-plugin files
NON_PLUGIN_DIR_MAP = {
    "getting-started": "01-快速入门",
    "hop-gui": "10-HopGUI",
    "projects": "12-变量与项目管理",
    "how-to-guides": "16-HowTo指南",
    "best-practices": "13-最佳实践与技巧",
    "protips": "13-最佳实践与技巧",
    "faq": "17-常见问题",
    "community-blogs": "20-社区资源",
    "samples": "20-社区资源",
    "logging": "15-日志",
    "vfs": "14-虚拟文件系统",
    "password": "18-安全与密码",
    "hop-server": "09-Hop工具",
    "hop-rest": "09-Hop工具",
    "hop-run": "09-Hop工具",
    "hop-search": "09-Hop工具",
    "hop-doc": "09-Hop工具",
}

# Root-level files mapping
ROOT_FILE_MAP = {
    "index.md": "index.md",  # keep at root
    "installation-configuration.md": "01-快速入门/installation-configuration.md",
    "supported-jvms.md": "01-快速入门/supported-jvms.md",
    "concepts.md": "02-核心概念/concepts.md",
    "metadata-driven-architecture.md": "02-核心概念/metadata-driven-architecture.md",
    "hop-usps.md": "02-核心概念/hop-usps.md",
    "variables.md": "12-变量与项目管理/variables.md",
    "vfs.md": "14-虚拟文件系统/vfs.md",
    "image-testpage.md": None,  # delete
}

# Snippet directory mapping
SNIPPET_DIR_MAP = {
    "snippets/hop-concepts": "02-核心概念",
    "snippets/best-practices": "13-最佳实践与技巧",
    "snippets/variables": "12-变量与项目管理",
    "snippets/hop-tools": "09-Hop工具",
}


def determine_target(rel_path, subcat_map):
    """Determine the target Chinese path for a file."""
    parts = rel_path.split("/")

    # Skip assets/ and url-mapping.json
    if parts[0] == "assets" or rel_path == "url-mapping.json":
        return None

    # Root-level files
    if len(parts) == 1 and parts[0] in ROOT_FILE_MAP:
        target = ROOT_FILE_MAP[parts[0]]
        return target  # None means delete

    # Snippets
    if parts[0] == "snippets":
        for src_prefix, target_dir in SNIPPET_DIR_MAP.items():
            src_dir = src_prefix.split("/")[-1]
            if len(parts) >= 2 and parts[1] == src_dir:
                filename = parts[-1]
                return f"{target_dir}/{filename}"
        # Other snippets → 02-核心概念
        if len(parts) == 2:
            return f"02-核心概念/{parts[1]}"
        return f"02-核心概念/{parts[-1]}"

    # Plugin transforms: pipeline/transforms/
    if len(parts) >= 2 and parts[0] == "pipeline" and parts[1] == "transforms":
        filename = parts[-1]
        stem = filename.replace(".md", "").lower().replace("-", "").replace("_", "")
        if stem in subcat_map:
            cat, subcat = subcat_map[stem]
            if subcat:
                return f"{cat}/{subcat}/{filename}"
            return f"{cat}/{filename}"
        # Uncategorised transforms
        return f"03-转换插件/其他转换/{filename}"

    # Plugin actions: workflow/actions/
    if len(parts) >= 2 and parts[0] == "workflow" and parts[1] == "actions":
        filename = parts[-1]
        stem = filename.replace(".md", "").lower().replace("-", "").replace("_", "")
        if stem in subcat_map:
            cat, subcat = subcat_map[stem]
            if subcat:
                return f"{cat}/{subcat}/{filename}"
            return f"{cat}/{filename}"
        return f"04-动作插件/其他动作/{filename}"

    # Database plugins: database/databases/
    if len(parts) >= 2 and parts[0] == "database" and parts[1] == "databases":
        filename = parts[-1]
        return f"05-数据库插件/{filename}"

    # database/ (non-databases)
    if parts[0] == "database":
        filename = parts[-1]
        return f"05-数据库插件/{filename}"

    # Metadata types: metadata-types/
    if parts[0] == "metadata-types":
        filename = parts[-1]
        stem = filename.replace(".md", "").lower().replace("-", "").replace("_", "")
        if stem in subcat_map:
            cat, subcat = subcat_map[stem]
            if subcat:
                return f"{cat}/{subcat}/{filename}"
            return f"{cat}/{filename}"
        return f"06-元数据类型/{filename}"

    # Pipeline non-transform files: pipeline/XXX.md
    if parts[0] == "pipeline":
        # Beam goes to 11-Beam引擎
        if len(parts) >= 2 and parts[1] == "beam":
            filename = parts[-1]
            if len(parts) > 3:
                return f"11-Beam引擎/{parts[2]}/{filename}"
            return f"11-Beam引擎/{filename}"
        # Pipeline run configurations → 07-管道
        if len(parts) >= 2 and parts[1] == "pipeline-run-configurations":
            filename = parts[-1]
            return f"07-管道/{filename}"
        # Other pipeline files → 07-管道
        filename = parts[-1]
        return f"07-管道/{filename}"

    # Workflow non-action files: workflow/XXX.md
    if parts[0] == "workflow":
        if len(parts) >= 2 and parts[1] == "workflow-run-configurations":
            filename = parts[-1]
            return f"08-工作流/{filename}"
        filename = parts[-1]
        return f"08-工作流/{filename}"

    # Hop tools: hop-tools/XXX.md
    if parts[0] == "hop-tools":
        filename = parts[-1]
        if len(parts) > 2:
            return f"09-Hop工具/{parts[1]}-{filename}" if not filename.startswith("hop-") else f"09-Hop工具/{filename}"
        return f"09-Hop工具/{filename}"

    # Technology: technology/XXX.md
    if parts[0] == "technology":
        filename = parts[-1]
        return f"19-技术集成/{filename}"

    # Non-plugin directories with direct mapping
    if parts[0] in NON_PLUGIN_DIR_MAP:
        target_dir = NON_PLUGIN_DIR_MAP[parts[0]]
        filename = parts[-1]
        # Flatten subdirectories
        return f"{target_dir}/{filename}"

    # Unknown → keep in 02-核心概念
    filename = parts[-1]
    return f"02-核心概念/{filename}"


def main():
    print("==> Phase 3: Renaming directories to Chinese")

    # Extract subcategory mapping from MANUAL_OVERRIDE
    override = extract_manual_override()
    subcat_map = build_plugin_subcategory_map(override)
    print(f"   Loaded {len(subcat_map)} plugin subcategory mappings")

    # Collect all .md files
    md_files = []
    for root, dirs, files in os.walk(MANUAL_DIR):
        for f in files:
            if f.endswith(".md"):
                full = os.path.join(root, f)
                rel = os.path.relpath(full, MANUAL_DIR)
                md_files.append(rel)

    print(f"   Found {len(md_files)} .md files")

    # Build move plan
    move_plan = {}
    deletes = []
    for rel in sorted(md_files):
        target = determine_target(rel, subcat_map)
        if target is None:
            deletes.append(rel)
        else:
            move_plan[rel] = target

    print(f"   Plan: {len(move_plan)} moves, {len(deletes)} deletes")

    # Execute moves
    moved = 0
    errors = 0
    for src_rel, dst_rel in sorted(move_plan.items()):
        src = MANUAL_DIR / src_rel
        dst = MANUAL_DIR / dst_rel

        if src == dst:
            continue

        # Check for filename collisions
        if dst.exists():
            print(f"   [WARN] Collision: {dst_rel} already exists, skipping {src_rel}")
            errors += 1
            continue

        dst.parent.mkdir(parents=True, exist_ok=True)
        try:
            shutil.move(str(src), str(dst))
            moved += 1
        except Exception as e:
            print(f"   [ERROR] {src_rel} → {dst_rel}: {e}")
            errors += 1

    # Execute deletes
    for rel in deletes:
        path = MANUAL_DIR / rel
        if path.exists():
            path.unlink()

    # Clean up empty directories
    cleaned = 0
    for root, dirs, files in os.walk(MANUAL_DIR, topdown=False):
        for d in dirs:
            dirpath = os.path.join(root, d)
            if d == "assets":
                continue
            try:
                if not os.listdir(dirpath):
                    os.rmdir(dirpath)
                    cleaned += 1
            except OSError:
                pass

    print(f"\n==> Done: {moved} moved, {len(deletes)} deleted, {cleaned} dirs cleaned, {errors} errors")

    # Show final directory structure
    print("\n==> Final directory structure:")
    for root, dirs, files in os.walk(MANUAL_DIR):
        rel = os.path.relpath(root, MANUAL_DIR)
        md_count = sum(1 for f in files if f.endswith(".md"))
        if md_count > 0 or rel == ".":
            depth = 0 if rel == "." else rel.count(os.sep) + 1
            indent = "  " * depth
            name = "." if rel == "." else os.path.basename(root)
            print(f"{indent}{name}/ ({md_count} md files)")


if __name__ == "__main__":
    main()
