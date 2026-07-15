#!/usr/bin/env python3
"""
Phase 1: 收集所有插件注解的 documentationUrl 和本地 MD 文件，
生成 /tmp/temp帮助.md 汇总报告。
"""
import os
import re
import sys
import json
from datetime import datetime
from pathlib import Path
from collections import defaultdict

PROJECT_ROOT = Path(__file__).resolve().parent.parent
MANUAL_DIR = PROJECT_ROOT / "docs" / "hop-assistant-manual"
OUTPUT_FILE = Path("/tmp/temp帮助.md")

ANNOTATION_PATTERNS = {
    "Transform": re.compile(
        r'@Transform\s*\(\s*id\s*=\s*"([^"]+)"'
        r'(?:.*?name\s*=\s*"([^"]*)")?'
        r'(?:.*?documentationUrl\s*=\s*"([^"]*)")?',
        re.DOTALL,
    ),
    "Action": re.compile(
        r'@Action\s*\(\s*id\s*=\s*"([^"]+)"'
        r'(?:.*?name\s*=\s*"([^"]*)")?'
        r'(?:.*?documentationUrl\s*=\s*"([^"]*)")?',
        re.DOTALL,
    ),
    "DatabaseMetaPlugin": re.compile(
        r'@DatabaseMetaPlugin\s*\(\s*type\s*=\s*"([^"]+)"'
        r'(?:.*?typeDescription\s*=\s*"([^"]*)")?'
        r'(?:.*?documentationUrl\s*=\s*"([^"]*)")?',
        re.DOTALL,
    ),
    "HopMetadata": re.compile(
        r'@HopMetadata\s*\('
        r'(?:.*?key\s*=\s*"([^"]*)")?'
        r'(?:.*?name\s*=\s*"([^"]*)")?'
        r'(?:.*?documentationUrl\s*=\s*"([^"]*)")?',
        re.DOTALL,
    ),
    "HopPerspectivePlugin": re.compile(
        r'@HopPerspectivePlugin\s*\('
        r'(?:.*?id\s*=\s*"([^"]*)")?'
        r'(?:.*?name\s*=\s*"([^"]*)")?'
        r'(?:.*?documentationUrl\s*=\s*"([^"]*)")?',
        re.DOTALL,
    ),
    "VariableResolverPlugin": re.compile(
        r'@VariableResolverPlugin\s*\(\s*id\s*=\s*"([^"]+)"'
        r'(?:.*?name\s*=\s*"([^"]*)")?'
        r'(?:.*?documentationUrl\s*=\s*"([^"]*)")?',
        re.DOTALL,
    ),
}


def find_java_files(root):
    for dirpath, _, filenames in os.walk(root):
        for f in filenames:
            if f.endswith(".java"):
                yield os.path.join(dirpath, f)


def extract_annotations(filepath):
    with open(filepath, "r", encoding="utf-8", errors="replace") as f:
        content = f.read()

    results = []
    for ann_type, pattern in ANNOTATION_PATTERNS.items():
        for m in pattern.finditer(content):
            groups = m.groups()
            entry = {
                "annotationType": ann_type,
                "pluginId": groups[0] if groups[0] else "",
                "name": groups[1] if len(groups) > 1 and groups[1] else "",
                "documentationUrl": groups[2] if len(groups) > 2 and groups[2] else "",
                "file": filepath,
            }
            if entry["documentationUrl"]:
                results.append(entry)
    return results


def collect_md_files(root):
    md_files = []
    for dirpath, _, filenames in os.walk(root):
        for f in filenames:
            if f.endswith(".md") and f != "README.md":
                full = os.path.join(dirpath, f)
                rel = os.path.relpath(full, root)
                md_files.append({"relPath": rel, "fullPath": full})
    return sorted(md_files, key=lambda x: x["relPath"])


def main():
    print("==> Phase 1: 收集插件注解信息...")
    annotations = []
    scanned = 0
    for jf in find_java_files(PROJECT_ROOT / "engine" / "src"):
        annotations.extend(extract_annotations(jf))
        scanned += 1
    for jf in find_java_files(PROJECT_ROOT / "core" / "src"):
        annotations.extend(extract_annotations(jf))
        scanned += 1
    for jf in find_java_files(PROJECT_ROOT / "ui" / "src"):
        annotations.extend(extract_annotations(jf))
        scanned += 1
    plugins_dir = PROJECT_ROOT / "plugins"
    if plugins_dir.is_dir():
        for cat in plugins_dir.iterdir():
            if cat.is_dir() and (cat / "pom.xml").exists():
                for sub in cat.iterdir():
                    if sub.is_dir() and (sub / "pom.xml").exists():
                        src_dir = sub / "src"
                        if src_dir.is_dir():
                            for jf in find_java_files(src_dir):
                                annotations.extend(extract_annotations(jf))
                                scanned += 1

    print(f"   扫描了 {scanned} 个 Java 文件，发现 {len(annotations)} 个带 documentationUrl 的注解")

    by_type = defaultdict(list)
    for a in annotations:
        by_type[a["annotationType"]].append(a)

    print("==> 收集本地 MD 文件...")
    md_files = collect_md_files(MANUAL_DIR)
    print(f"   发现 {len(md_files)} 个 MD 文件")

    print("==> 生成报告...")
    lines = []
    lines.append("# 帮助文档信息汇总")
    lines.append(f"\n> 生成时间: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    lines.append(f"> 扫描 Java 文件数: {scanned}")
    lines.append(f"> 带 documentationUrl 的插件注解数: {len(annotations)}")
    lines.append(f"> 本地 MD 文件数: {len(md_files)}")
    lines.append("")

    lines.append("---")
    lines.append("")
    lines.append("## 一、插件注解 documentationUrl 汇总")
    lines.append("")
    type_order = ["Transform", "Action", "DatabaseMetaPlugin", "HopMetadata", "HopPerspectivePlugin", "VariableResolverPlugin"]
    for ann_type in type_order:
        items = by_type.get(ann_type, [])
        lines.append(f"### {ann_type}（{len(items)} 个）")
        lines.append("")
        lines.append("| 序号 | 插件ID | 名称 | documentationUrl |")
        lines.append("|------|--------|------|------------------|")
        for i, item in enumerate(sorted(items, key=lambda x: x["pluginId"]), 1):
            pid = item["pluginId"]
            name = item["name"]
            url = item["documentationUrl"]
            lines.append(f"| {i} | `{pid}` | {name} | `{url}` |")
        lines.append("")

    lines.append("---")
    lines.append("")
    lines.append("## 二、本地 MD 文件清单")
    lines.append("")
    current_group = ""
    for md in md_files:
        rel = md["relPath"]
        parts = rel.split(os.sep)
        group = parts[0] if len(parts) > 1 else "(根目录)"
        if group != current_group:
            lines.append(f"### {group}")
            lines.append("")
            current_group = group
        fname = parts[-1] if parts else rel
        lines.append(f"- `{rel}`")
    lines.append("")

    lines.append("---")
    lines.append("")
    lines.append("## 三、统计摘要")
    lines.append("")
    lines.append("| 注解类型 | 数量 |")
    lines.append("|----------|------|")
    for ann_type in type_order:
        lines.append(f"| {ann_type} | {len(by_type.get(ann_type, []))} |")
    lines.append(f"| **合计** | **{len(annotations)}** |")
    lines.append("")

    with open(OUTPUT_FILE, "w", encoding="utf-8") as f:
        f.write("\n".join(lines))
    print(f"==> 报告已生成: {OUTPUT_FILE}")
    print(f"    总计 {len(annotations)} 个插件注解，{len(md_files)} 个本地 MD 文件")


if __name__ == "__main__":
    main()
