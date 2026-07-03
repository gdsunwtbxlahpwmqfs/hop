#!/usr/bin/env python3
"""
Generate markdown documentation from .hpl pipeline files for the knowledge base.

Walks the docs/hop-assistant-manual directory, finds all .hpl files,
parses them, and writes a companion .md file alongside each .hpl file
so the knowledge base indexer can ingest pipeline metadata.
"""
import os
import sys
import xml.etree.ElementTree as ET

MANUAL_DIR = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))),
                          "docs", "hop-assistant-manual")


def parse_hpl(filepath):
    """Parse a .hpl XML file and return a dict of extracted information."""
    tree = ET.parse(filepath)
    root = tree.getroot()

    info = {}
    info_el = root.find("info")
    if info_el is not None:
        for child in info_el:
            tag = child.tag
            text = (child.text or "").strip()
            info[tag] = text

    transforms = []
    for t in root.findall("transform"):
        name = t.findtext("name", "").strip()
        typ = t.findtext("type", "").strip()
        if name:
            transforms.append({"name": name, "type": typ})

    hops = []
    for hop in root.findall(".//hop"):
        fr = hop.findtext("from", "").strip()
        to = hop.findtext("to", "").strip()
        enabled = hop.findtext("enabled", "Y").strip()
        if fr and to:
            hops.append({"from": fr, "to": to, "enabled": enabled})

    notes = []
    for np_el in root.findall("notepads/notepad"):
        note_text = np_el.findtext("note", "")
        note = note_text.strip()
        if note:
            notes.append(note)

    params = []
    for p in root.findall(".//parameters/parameter"):
        name = p.findtext("name", "").strip()
        default = p.findtext("default_value", "").strip()
        desc = p.findtext("description", "").strip()
        if name:
            params.append({"name": name, "default": default, "description": desc})

    return {
        "name": info.get("name", ""),
        "description": info.get("description", ""),
        "extended_description": info.get("extended_description", ""),
        "transforms": transforms,
        "hops": hops,
        "notes": notes,
        "parameters": params,
    }


def generate_md(data, rel_path):
    """Generate markdown content from parsed .hpl data."""
    lines = []
    pipeline_name = data["name"] or os.path.splitext(os.path.basename(rel_path))[0]

    lines.append(f"# Pipeline: {pipeline_name}")
    lines.append("")

    lines.append("## Basic Information")
    lines.append("")
    lines.append(f"- **Pipeline Name:** {pipeline_name}")
    if data["description"]:
        lines.append(f"- **Description:** {data['description']}")
    if data["extended_description"]:
        lines.append(f"- **Extended Description:** {data['extended_description']}")
    lines.append(f"- **Source File:** `{rel_path}`")
    lines.append("")

    if data["parameters"]:
        lines.append("## Parameters")
        lines.append("")
        lines.append("| Parameter | Default | Description |")
        lines.append("|-----------|---------|-------------|")
        for p in data["parameters"]:
            lines.append(f"| {p['name']} | {p['default']} | {p['description']} |")
        lines.append("")

    if data["transforms"]:
        lines.append("## Transforms")
        lines.append("")
        lines.append("| Name | Type |")
        lines.append("|------|------|")
        for t in data["transforms"]:
            lines.append(f"| {t['name']} | {t['type']} |")
        lines.append("")

    if data["hops"]:
        lines.append("## Hops")
        lines.append("")
        lines.append("| From | To |")
        lines.append("|------|----|")
        for h in data["hops"]:
            lines.append(f"| {h['from']} | {h['to']} |")
        lines.append("")

    if data["notes"]:
        lines.append("## Notes")
        lines.append("")
        for i, note in enumerate(data["notes"], 1):
            if len(data["notes"]) > 1:
                lines.append(f"### Note {i}")
                lines.append("")
            for para in note.split("\n"):
                para = para.strip()
                if para:
                    lines.append(para)
                    lines.append("")
            lines.append("---")
            lines.append("")

    return "\n".join(lines)


def main():
    manual = MANUAL_DIR
    if not os.path.isdir(manual):
        print(f"ERROR: Manual directory not found: {manual}")
        sys.exit(1)

    hpl_files = []
    for root, dirs, files in os.walk(manual):
        for f in files:
            if f.endswith(".hpl"):
                hpl_files.append(os.path.join(root, f))

    if not hpl_files:
        print("No .hpl files found.")
        return

    generated = 0
    skipped = 0
    errors = 0

    for hpl_path in sorted(hpl_files):
        md_path = hpl_path + ".md"
        rel_path = os.path.relpath(hpl_path, manual)

        # Skip if .md is newer than .hpl
        if os.path.isfile(md_path):
            hpl_mtime = os.path.getmtime(hpl_path)
            md_mtime = os.path.getmtime(md_path)
            if md_mtime >= hpl_mtime:
                skipped += 1
                continue

        try:
            data = parse_hpl(hpl_path)
            md_content = generate_md(data, rel_path)
            with open(md_path, "w", encoding="utf-8") as f:
                f.write(md_content)
            generated += 1
        except ET.ParseError as e:
            print(f"  [SKIP] XML parse error: {rel_path}: {e}")
            errors += 1
        except Exception as e:
            print(f"  [ERR]  {rel_path}: {e}")
            errors += 1

    print(f"Generated: {generated}, Skipped (up-to-date): {skipped}, Errors: {errors}")
    if errors:
        sys.exit(1)


if __name__ == "__main__":
    main()
