# Skills 管理方案

> 为 Hop AI 助手提供可复用的技能（Skills）管理系统，允许用户创建、编辑、启用/禁用、激活技能，并在对话时将技能内容注入 LLM 系统提示词。

---

## 1. 设计目标

| 目标 | 说明 |
|------|------|
| **OpenCode 兼容** | SKILL.md 文件格式与 OpenCode 完全兼容，`name` + `description` 为标准字段，Hop 扩展字段使用 `hop_` 前缀 |
| **双触发模式** | `GLOBAL`（全局激活，每次对话自动注入）和 `MANUAL`（手动选择，用户按需激活） |
| **零外部依赖** | YAML frontmatter 解析由轻量手写解析器处理，无需 SnakeYAML / jackson-dataformat-yaml |
| **RAP + RCP 双平台** | 编辑器通过 `ContentEditorFacade` 抽象，RAP 下使用 Monaco，RCP 下使用 JFace SourceViewer |
| **内置技能可恢复** | 4 个内置技能从 classpath 资源安装，用户可修改但不可删除，可一键恢复 |

---

## 2. 整体架构

```
┌─────────────────────────────────────────────────────────┐
│                       HopGui.java                        │
│  ┌───────────────────────────────────────────────────┐  │
│  │  Sidebar Toolbar                                  │  │
│  │  ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐         │  │
│  │  │ ... │ │Term │ │Skill│ │Exec │ │ ... │         │  │
│  │  └─────┘ └─────┘ └──┬──┘ └─────┘ └─────┘         │  │
│  │                     │ onClick                      │  │
│  └─────────────────────┼─────────────────────────────┘  │
│                        ▼                                 │
│  ┌───────────────────────────────────────────────────┐  │
│  │            SkillManagerDialog                      │  │
│  │  ┌─────────────────────────────────────────────┐  │  │
│  │  │ Search Bar + Category Filter + Sort         │  │  │
│  │  ├─────────────────────────────────────────────┤  │  │
│  │  │ ┌─────────┐ ┌─────────┐ ┌─────────┐        │  │  │
│  │  │ │ Skill   │ │ Skill   │ │ Skill   │  Cards │  │  │
│  │  │ │ Card    │ │ Card    │ │ Card    │        │  │  │
│  │  │ └─────────┘ └─────────┘ └─────────┘        │  │  │
│  │  └─────────────────────────────────────────────┘  │  │
│  └───────────────────────┬───────────────────────────┘  │
│                          │ Edit/New                      │
│                          ▼                               │
│  ┌───────────────────────────────────────────────────┐  │
│  │            SkillEditorDialog                       │  │
│  │  ┌─────────────────────────────────────────────┐  │  │
│  │  │ Metadata Form (name/desc/category/trigger)  │  │  │
│  │  ├───────────────────┬─────────────────────────┤  │  │
│  │  │ ContentEditor     │  Preview                │  │  │
│  │  │ (Monaco/Source    │  (read-only Text)       │  │  │
│  │  │  Viewer)          │                         │  │  │
│  │  └───────────────────┴─────────────────────────┘  │  │
│  └───────────────────────────────────────────────────┘  │
│                                                         │
│  ┌───────────────────────────────────────────────────┐  │
│  │            LlmAssistantDialog                      │  │
│  │  ┌─────────────────────────────────────────────┐  │  │
│  │  │ Chat History Area                           │  │  │
│  │  ├─────────────────────────────────────────────┤  │  │
│  │  │ 🧩 Active Skills: xxx, yyy        [+]      │  │  │
│  │  ├─────────────────────────────────────────────┤  │  │
│  │  │ Input Area                                 │  │  │
│  │  └─────────────────────────────────────────────┘  │  │
│  └───────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────┐
│                   SkillManager (Singleton)               │
│                                                         │
│  loadAll()          → 从磁盘加载所有技能                  │
│  create/update/delete → CRUD 操作                       │
│  activateManual()   → 手动激活技能                       │
│  getActiveSkills()  → 获取当前活跃技能列表                │
│  buildSkillContext()→ 构建注入 LLM 的技能上下文           │
└──────────────────────┬──────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────┐
│                    SkillStorage                          │
│                                                         │
│  loadFrom(dir)  → 扫描目录，解析所有 SKILL.md            │
│  parse(file)    → YAML frontmatter + Markdown body      │
│  save(skill)    → 序列化并写入文件                       │
│  delete(dir)    → 递归删除技能目录                       │
└──────────────────────┬──────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────┐
│              文件系统                                     │
│                                                         │
│  .opencode/skills/          ← 项目级技能                  │
│    hop-quickstart/SKILL.md                                │
│    pipeline-debugger/SKILL.md                             │
│    my-custom-skill/SKILL.md                               │
│                                                         │
│  ~/.config/opencode/skills/ ← 全局技能（跨项目共享）       │
│    shared-skill/SKILL.md                                 │
└─────────────────────────────────────────────────────────┘
```

---

## 3. SKILL.md 文件格式

### 3.1 文件结构

```
.opencode/skills/
└── my-skill/
    └── SKILL.md          ← 唯一必需的文件
```

每个技能是一个子目录，目录名即技能名（kebab-case），内含一个 `SKILL.md`。

### 3.2 SKILL.md 内容

```yaml
---
# ── OpenCode 标准字段（OpenCode 只读取这两个）──
name: pipeline-debugger
description: >-
  Use when the user asks to debug a pipeline, diagnose data issues,
  or troubleshoot transform errors.

# ── Hop 扩展字段（hop_ 前缀，OpenCode 自动忽略）──
hop_category: pipeline          # general | pipeline | database | workflow | custom
hop_enabled: true               # 是否启用
hop_trigger: manual             # global | manual
hop_priority: 0                 # 数字越大优先级越高（影响注入顺序）
hop_source: builtin             # builtin | custom
hop_version: "1.0.0"
hop_usage_count: 0
---

# 管道调试专家

你是一个 Hop 管道调试专家。当用户遇到管道问题时：

1. 首先确认错误信息和堆栈跟踪
2. 检查数据类型是否匹配
3. ...
```

### 3.3 字段说明

| 字段 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `name` | String | (必填) | kebab-case 技能名，必须与目录名一致 |
| `description` | String | (必填) | 触发描述，折叠块 `>-` 格式 |
| `hop_category` | String | `custom` | 分类标签，用于管理面板筛选 |
| `hop_enabled` | boolean | `true` | 是否启用（禁用的技能不会被激活） |
| `hop_trigger` | enum | `manual` | `global` = 每次对话自动注入；`manual` = 用户手动选择 |
| `hop_priority` | int | `0` | 优先级，影响注入顺序（高→低） |
| `hop_source` | String | `custom` | `builtin` = 内置（不可删除）；`custom` = 用户创建 |
| `hop_version` | String | `1.0.0` | 版本号 |
| `hop_usage_count` | int | `0` | 使用次数统计 |

> **OpenCode 兼容性**：OpenCode 只读取 `name` 和 `description`，所有 `hop_` 前缀字段会被忽略。Hop 和 OpenCode 可以共享同一份 SKILL.md 文件。

### 3.4 YAML 解析器

由于 UI 模块不依赖 SnakeYAML，`SkillStorage` 内置了一个轻量 YAML frontmatter 解析器，支持：

- 简单标量：`key: value`
- 布尔值：`true` / `false`
- 整数：`123`
- 折叠块标量：`>-`（多行折叠为单行，用空格连接）
- 字面块标量：`|`（保留换行）
- 引号字符串：`"value"` / `'value'`

---

## 4. 核心组件

### 4.1 Skill（数据模型）

**文件**: `ui/.../assistant/skills/Skill.java`

```java
@Getter @Setter
public class Skill {
    public enum TriggerType { MANUAL, GLOBAL }

    // OpenCode 标准字段
    private String name;
    private String description;

    // Hop 扩展字段
    private String category = "custom";
    private boolean enabled = true;
    private TriggerType trigger = TriggerType.MANUAL;
    private int priority = 0;
    private String source = "custom";
    private String version = "1.0.0";
    private int usageCount = 0;
    private Instant updatedAt;

    // 运行时字段（不持久化）
    private transient Path directory;
    private transient String content = "";

    public boolean isBuiltin() { return "builtin".equals(source); }
    public int estimateTokens() { return content.length() / 4; }
}
```

### 4.2 SkillStorage（持久化层）

**文件**: `ui/.../assistant/skills/SkillStorage.java`

| 方法 | 说明 |
|------|------|
| `loadFrom(Path skillsDir)` | 扫描目录下所有 `<name>/SKILL.md`，返回 Skill 列表 |
| `parse(Path skillFile)` | 解析单个 SKILL.md 文件 |
| `parseString(String raw, Path dir)` | 从字符串解析（用于内置技能） |
| `save(Skill, Path skillsDir)` | 序列化并写入 `<dir>/<name>/SKILL.md` |
| `serialize(Skill)` | 序列化为 SKILL.md 格式字符串 |
| `delete(Path skillDir)` | 递归删除技能目录 |

### 4.3 SkillManager（业务逻辑层）

**文件**: `ui/.../assistant/skills/SkillManager.java`

单例模式，线程安全（`CopyOnWriteArrayList` + `ConcurrentSkipListSet`）。

#### 加载

```java
public void loadAll()
```

1. 调用 `BuiltinSkills.ensureInstalled()` 确保内置技能已安装
2. 从 `PROJECT_SKILLS_DIR`（`.opencode/skills/`）加载项目级技能
3. 从 `GLOBAL_SKILLS_DIR`（`~/.config/opencode/skills/`）加载全局技能
4. 按名称去重：项目级技能优先于全局技能

#### CRUD

| 方法 | 说明 |
|------|------|
| `create(name, description, content)` | 创建新技能，保存到项目目录 |
| `update(skill)` | 更新已有技能 |
| `delete(name)` | 删除技能（内置技能不可删除） |
| `duplicate(sourceName, newName)` | 复制技能 |
| `toggleEnabled(name)` | 切换启用/禁用状态 |

#### 查询

| 方法 | 说明 |
|------|------|
| `getAll()` | 返回所有已加载技能 |
| `find(name)` | 按名称查找 |
| `search(query, category, enabledOnly)` | 多条件搜索 |
| `getGlobalSkills()` | 全局触发技能列表（按优先级降序） |
| `getManualSkills()` | 手动触发技能列表（按名称排序） |

#### 激活

| 方法 | 说明 |
|------|------|
| `activateManual(name)` | 手动激活一个技能 |
| `deactivateManual(name)` | 取消激活 |
| `isManualActive(name)` | 检查是否已激活 |
| `clearManualActivations()` | 清除所有手动激活 |
| `getActiveSkills()` | 返回当前活跃技能（启用的全局技能 + 已激活的手动技能） |

#### 提示词构建

```java
// 构建完整系统提示词（base prompt + skills）
public String buildAugmentedPrompt(String basePrompt)

// 仅构建技能上下文（用于作为 extraContext 注入）
public String buildSkillContext()
```

输出格式：
```
--- Skill: hop-quickstart ---
（技能 Markdown 内容）

--- Skill: pipeline-debugger ---
（技能 Markdown 内容）
```

### 4.4 BuiltinSkills（内置技能管理）

**文件**: `ui/.../assistant/skills/BuiltinSkills.java`

| 方法 | 说明 |
|------|------|
| `ensureInstalled(skillsDir)` | 从 classpath 资源安装缺失的内置技能 |
| `restore(skillsDir, name)` | 恢复单个内置技能到原始内容 |
| `restoreAll(skillsDir)` | 恢复所有内置技能 |

安装逻辑：
- 检查 `<skillsDir>/<name>/SKILL.md` 是否存在
- 已存在则跳过（尊重用户修改）
- 不存在则从 JAR classpath 提取并写入磁盘

---

## 5. 内置技能

| 技能名 | 触发 | 分类 | 说明 |
|--------|------|------|------|
| `hop-quickstart` | GLOBAL | general | Hop 新手引导，简化解释，提供学习路径 |
| `pipeline-design-patterns` | MANUAL | pipeline | 管道设计模式最佳实践 |
| `pipeline-debugger` | MANUAL | pipeline | 管道调试专家，系统化排障 |
| `performance-tuning` | MANUAL | pipeline | 性能调优建议 |

内置技能资源路径：`ui/src/main/resources/org/apache/hop/ui/hopgui/assistant/skills/builtin/<name>/SKILL.md`

---

## 6. UI 组件

### 6.1 侧边栏入口（HopGui.java）

在 `HopGui.java` 中注册 `SIDEBAR_TOOLBAR_ITEM_SKILLS` 按钮：

```java
sidebarToolbarDescriptors.add(
    SidebarToolbarItemDescriptor.builder()
        .id(SIDEBAR_TOOLBAR_ITEM_SKILLS)
        .imagePath("ui/images/skills.svg")
        .imageSize(sidebarIconSize)
        .tooltip(BaseMessages.getString(SkillManager.class,
            "SkillsManager.Sidebar.Tooltip"))
        .onSelect(() -> {
            SkillManagerDialog dialog = new SkillManagerDialog(shell);
            dialog.open();
        })
        .selectedSupplier(() -> SkillManagerDialog.isOpen())
        .available(LlmAssistantConfig.getInstance().isEnabled())
        .build());
```

- 图标位置：Terminal 按钮之后
- 可见条件：`LlmAssistantConfig.isEnabled()` 为 true
- 点击行为：打开技能管理面板

### 6.2 技能管理面板（SkillManagerDialog）

**文件**: `ui/.../assistant/skills/ui/SkillManagerDialog.java`（~718 行）

卡片式布局，包含：

- **搜索栏**：按名称/描述模糊搜索
- **分类筛选**：All / General / Pipeline / Database / Workflow / Custom
- **排序**：Name / Usage Count
- **仅启用复选框**：过滤已禁用技能
- **技能卡片**：每个卡片显示名称、描述、触发类型徽章、启用开关、操作按钮
- **操作按钮**：编辑（Edit）、复制（Duplicate）、删除（Delete，内置不可删）
- **底部按钮**：新建技能（New Skill）、恢复内置（Restore Built-in Skills）、关闭（Close）

### 6.3 技能编辑器（SkillEditorDialog）

**文件**: `ui/.../assistant/skills/ui/SkillEditorDialog.java`（~510 行）

分为上下两部分：

**上半部分 — 元数据表单**：
- Name（kebab-case，编辑已有技能时只读）
- Description（触发描述）
- Category（下拉选择）
- Trigger（Global Active / Manual Trigger）
- Priority（Spinner）
- Version
- Enabled（复选框）

**下半部分 — SashForm 左右分栏**：

| 左栏 | 右栏 |
|------|------|
| `ContentEditorFacade.createContentEditor(parent, "markdown")` | 只读 SWT Text 预览 |
| RAP: Monaco Editor（Markdown 语法高亮） | 实时同步编辑器内容 |
| RCP: JFace SourceViewer | |
| 顶部显示 token 估算（~N tokens） | |

编辑器通过 `ContentEditorFacade` 抽象层自动适配运行平台：
- **RAP（Hop Web）**: Monaco Editor，支持语法高亮、行号、代码折叠
- **RCP（桌面）**: JFace SourceViewer + RuleBasedSourceViewerConfiguration

### 6.4 助手对话框技能栏（LlmAssistantDialog）

**文件**: `ui/.../assistant/LlmAssistantDialog.java`

在聊天输入框上方添加技能选择栏：

```
┌──────────────────────────────────────────────────┐
│              Chat History Area                    │
├──────────────────────────────────────────────────┤
│ 🧩 Active Skills: hop-quickstart (Global)   [+] │
├──────────────────────────────────────────────────┤
│              Input Area                           │
└──────────────────────────────────────────────────┘
```

- **🧩 标签**：显示当前活跃技能列表
- **[+] 按钮**：弹出菜单，列出所有手动技能（带勾选状态和描述摘要）
- **全局技能**：自动标记 `(Global)` / `(全局)`（i18n）
- **点击菜单项**：切换手动技能的激活/取消激活

---

## 7. 激活与提示词注入流程

### 7.1 激活流程

```
用户操作                           系统响应
─────────                          ─────────
GLOBAL 技能
  技能 hop_enabled=true              → 自动出现在 getActiveSkills()
  技能 hop_enabled=false             → 从 getActiveSkills() 中排除

MANUAL 技能
  用户点击 [+] → 勾选技能           → SkillManager.activateManual(name)
                                     → activeManualSkills.add(name)
  用户点击 [+] → 取消勾选           → SkillManager.deactivateManual(name)
                                     → activeManualSkills.remove(name)
```

### 7.2 提示词注入流程

```
用户发送消息
    │
    ▼
LlmAssistantDialog.sendMessage()
    │
    ├── 1. SkillManager.getInstance().buildSkillContext()
    │      → 遍历 getActiveSkills()
    │      → 拼接: "\n\n--- Skill: {name} ---\n{content}"
    │      → 返回 skillContext 字符串
    │
    ├── 2. 构建 extraContext
    │      extraContext = skillContext + ragContext
    │
    ├── 3. client.streamChat(messages, extraContext)
    │      → extraContext 作为系统提示词补充注入 LLM
    │
    ▼
LLM 收到: system_prompt + skill_content + rag_context + user_message
```

### 7.3 活跃技能排序

`getActiveSkills()` 返回的列表按 `priority` 降序排列，高优先级技能的内容先注入。

---

## 8. 文件布局

### 8.1 源码文件

```
ui/src/main/java/org/apache/hop/ui/hopgui/assistant/skills/
├── Skill.java                          # 数据模型
├── SkillStorage.java                   # SKILL.md 解析/序列化
├── SkillManager.java                   # 单例管理器（CRUD + 激活 + 提示词）
├── BuiltinSkills.java                  # 内置技能安装/恢复
└── ui/
    ├── SkillManagerDialog.java         # 技能管理面板
    └── SkillEditorDialog.java          # 技能编辑器

ui/src/main/resources/org/apache/hop/ui/hopgui/assistant/skills/
├── builtin/
│   ├── hop-quickstart/SKILL.md
│   ├── pipeline-design-patterns/SKILL.md
│   ├── pipeline-debugger/SKILL.md
│   └── performance-tuning/SKILL.md
└── messages/
    ├── messages_en_US.properties       # 英文 (77 keys)
    └── messages_zh_CN.properties       # 中文 (77 keys)

ui/src/main/resources/ui/images/
└── skills.svg                          # 拼图图标
```

### 8.2 运行时文件

```
# 项目级技能目录（当前工作目录下）
.opencode/skills/
├── hop-quickstart/SKILL.md             # 内置（首次运行自动安装）
├── pipeline-design-patterns/SKILL.md
├── pipeline-debugger/SKILL.md
├── performance-tuning/SKILL.md
└── my-custom-skill/SKILL.md            # 用户创建

# 全局技能目录（跨项目共享）
~/.config/opencode/skills/
└── shared-skill/SKILL.md
```

### 8.3 集成点文件

| 文件 | 修改内容 |
|------|---------|
| `ui/.../HopGui.java` | 新增 `SIDEBAR_TOOLBAR_ITEM_SKILLS` 常量、侧边栏按钮注册、`SkillManager.loadAll()` 初始化 |
| `ui/.../assistant/LlmAssistantDialog.java` | 新增技能选择栏 UI、`showSkillPicker()` 弹出菜单、`sendMessage()` 中注入 skillContext |
| `ui/pom.xml` | 新增 `apache-rat-plugin` SKILL.md 排除规则 |

---

## 9. 国际化

所有用户可见文本通过 `BaseMessages.getString()` 获取，消息键定义在两个 properties 文件中：

| 文件 | 语言 | 键数量 |
|------|------|--------|
| `messages_en_US.properties` | 英文 | 77 |
| `messages_zh_CN.properties` | 中文 | 77 |

**注意**：`PKG` 引用 `SkillManager.class`（位于 `...assistant.skills` 包），消息文件路径为 `...assistant.skills.messages.messages_*.properties`。

在 `HopGui.java` 和 `LlmAssistantDialog.java` 中引用技能消息时，必须使用 `SkillManager.class` 作为 PKG：
```java
BaseMessages.getString(SkillManager.class, "SkillsManager.Sidebar.Tooltip")
```

---

## 10. 技能优先级与 Token 预算

| 配置项 | 说明 |
|--------|------|
| `hop_priority` | 整数，值越大优先级越高。相同触发类型的技能按优先级降序排列 |
| Token 估算 | `content.length() / 4`，在编辑器顶部实时显示 |
| 注入顺序 | 高优先级先注入，全局技能和手动技能混合排序 |

> **注意**：当前版本未实现 token 预算截断。如果技能内容过多导致 token 超限，LLM 会返回错误。建议单个技能内容控制在 2000 token（~8000 字符）以内。

---

## 11. 调试

### 11.1 快速热更新（RAP 模式）

修改 UI 代码后，无需完整重建 WAR：

```bash
# 1. 编译 + 打包 UI JAR
./mvnw compile -pl ui -Dfast-build -DskipTests -q
./mvnw jar:jar -pl ui -Dfast-build -DskipTests -q

# 2. 替换部署目录中的 JAR
cp ui/target/hop-ui-2.19.0-SNAPSHOT.jar \
   tomcat-run/webapps/ROOT/WEB-INF/lib/

# 3. 清除 Tomcat 缓存
rm -rf tomcat-run/work/Catalina

# 4. 重启
bash start-hop-web.sh --restart --no-llm
```

### 11.2 完整重建

```bash
./mvnw install -pl ui -Dfast-build -DskipTests -q
bash start-hop-web.sh --force-build
```

### 11.3 验证技能加载

启动后在 `tomcat-run/logs/catalina.out` 中搜索：

```
SkillManager - Loaded N skills (M project, K global)
BuiltinSkills - Installed built-in skill: xxx
```

### 11.4 访问地址

| 服务 | URL |
|------|-----|
| Web UI | http://localhost:8080/ui |
| REST API | http://localhost:8080/api/v1/ |
| REST Docs Stats | http://localhost:8080/api/v1/docs/stats |

---

## 12. 扩展指南

### 12.1 添加新的内置技能

1. 在 `ui/src/main/resources/.../skills/builtin/` 下创建 `<new-skill>/SKILL.md`
2. 在 `BuiltinSkills.BUILTIN_NAMES` 中添加技能名
3. 重新编译 UI 模块

### 12.2 添加新的分类

1. 在 `SkillEditorDialog.CATEGORIES` 数组中添加分类名
2. 在 `SkillManagerDialog.CATEGORY_VALUES` 中添加对应值
3. 在 `SkillManagerDialog` 的 `categoryFilter.add()` 中添加显示标签
4. 在两个 properties 文件中添加 `SkillsManager.Label.Xxx` 键

### 12.3 自定义技能目录

修改 `SkillManager` 中的两个常量：

```java
public static final Path PROJECT_SKILLS_DIR = Path.of(".opencode", "skills");
public static final Path GLOBAL_SKILLS_DIR =
    Path.of(System.getProperty("user.home"), ".config", "opencode", "skills");
```

或通过系统属性外部化（需要自行扩展）。

### 12.4 与 OpenCode 共享技能

由于 SKILL.md 格式兼容，只需将 `.opencode/skills/` 目录指向同一位置：

```bash
# 软链接 Hop 项目技能目录到 OpenCode 目录
ln -s ~/.config/opencode/skills .opencode/skills
```

OpenCode 会读取 `name` 和 `description` 字段用于技能匹配，忽略所有 `hop_` 前缀字段。

---

## 13. 构建检查清单

| 检查项 | 命令 |
|--------|------|
| License Header | `./mvnw clean apache-rat:check -pl ui` |
| Checkstyle（无 star import） | `./mvnw checkstyle:check -pl ui` |
| Spotless 格式化 | `./mvnw spotless:apply -pl ui` |
| 编译 | `./mvnw compile -pl ui -Dfast-build` |
| RAP 编译 | `./mvnw compile -pl rap -Dfast-build`（需先 `install` UI 模块） |

---

## 14. 后续规划

| 功能 | 状态 | 说明 |
|------|------|------|
| Token 预算管理 | 待实现 | 当活跃技能总 token 超限时，按优先级截断低优先级技能 |
| 技能导入/导出 | 待实现 | 支持批量导入/导出 SKILL.md 文件 |
| 自动触发匹配 | 待实现 | 基于 `description` 语义匹配，自动激活相关技能 |
| 技能版本升级检测 | 待实现 | 检测内置技能是否有新版本，提示用户升级 |
| Monaco 完整集成 | 已完成 | SkillEditorDialog 通过 `ContentEditorFacade` 使用 Monaco |
