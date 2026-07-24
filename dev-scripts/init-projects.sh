#!/bin/bash
# 初始化项目&环境目录脚本（基于 config/projects 实际结构：default、samples）
# 配置模型对齐 hop-config.json 的 projectsConfig 与 Hop 源码：
#   - LifecycleEnvironment : name / purpose / projectName / configurationFiles
#   - ProjectLifecycle     : name / lifecycleEnvironments(List<String>) / configurationFiles
#   - 环境配置文件采用 Hop 官方惯例：${PROJECT_HOME}/{env}-env-config.json（顶层 variables 数组）
set -e

# 全局环境变量（和配置文件变量对齐）
export HOP_CONFIG_FOLDER="${HOP_CONFIG_FOLDER:-/opt/hop/config}"
export HOP_AUDIT_FOLDER="${HOP_AUDIT_FOLDER:-${HOP_CONFIG_FOLDER}/audit}"
STANDARD_PROJECT_FOLDER="${HOP_CONFIG_FOLDER}/projects"

# 1. 创建标准项目根目录
mkdir -p "${STANDARD_PROJECT_FOLDER}"

# ----------------------------------------------------------------------------
# 工具函数：生成各类型元数据配置文件
# ----------------------------------------------------------------------------

# 生成 execution-data-profile（执行数据采样配置）
gen_execution_data_profile() {
  local out="$1"
  cat > "${out}" <<'EOF'
{
  "name": "first-last",
  "description": "capture the first and last 100 rows of every Hop transform",
  "sampler": [
    {
      "FirstRowsExecutionDataSampler": {
        "sampleSize": "100"
      }
    },
    {
      "LastRowsExecutionDataSampler": {
        "sampleSize": "100"
      }
    }
  ]
}
EOF
}

# 生成 execution-info-location（执行信息存储位置）
gen_execution_info_location() {
  local out="$1"
  cat > "${out}" <<'EOF'
{
  "executionInfoLocation": {
    "local-folder": {
      "pluginName": "File location",
      "pluginId": "local-folder",
      "rootFolder": "${HOP_AUDIT_FOLDER}/executions/"
    }
  },
  "dataLoggingDelay": "2000",
  "name": "local-audit",
  "description": "",
  "dataLoggingInterval": "5000"
}
EOF
}

# 生成 pipeline-run-configuration（管道运行配置）
gen_pipeline_run_config() {
  local out="$1"
  cat > "${out}" <<'EOF'
{
  "engineRunConfiguration": {
    "Local": {
      "feedback_size": "50000",
      "sample_size": "100",
      "sample_type_in_gui": "Last",
      "rowset_size": "10000",
      "safe_mode": false,
      "show_feedback": false,
      "topo_sort": false,
      "gather_metrics": false
    }
  },
  "name": "local",
  "configurationVariables": [],
  "description": "",
  "executionInfoLocationName": "local-audit",
  "dataProfile": "first-last",
  "defaultSelection": true
}
EOF
}

# 生成 workflow-run-configuration（工作流运行配置）
gen_workflow_run_config() {
  local out="$1"
  cat > "${out}" <<'EOF'
{
  "engineRunConfiguration": {
    "Local": {
      "safe_mode": false
    }
  },
  "name": "local",
  "description": "",
  "executionInfoLocationName": "local-audit",
  "defaultSelection": true
}
EOF
}

# 生成项目级 project-config.json
#   $1 输出路径  $2 父项目名（可为空）
gen_project_config() {
  local out="$1"
  local parent="$2"
  local parent_field
  if [ -n "${parent}" ]; then
    parent_field="\"parentProjectName\" : \"${parent}\","
  else
    parent_field="\"parentProjectName\" : \"\","
  fi
  cat > "${out}" <<EOF
{
  "metadataBaseFolder": "\${PROJECT_HOME}/metadata",
  "unitTestsBasePath": "\${PROJECT_HOME}",
  "dataSetsCsvFolder": "\${PROJECT_HOME}/datasets",
  "enforcingExecutionInHome": true,
  ${parent_field}
  "config": {
    "variables": []
  }
}
EOF
}

# 生成生命周期环境配置文件（注入变量，Hop 惯例：顶层 variables 数组）
#   $1 输出路径  $2 环境名(dev/test/prod)  $3 用途描述  $4 数据库主机  $5 日志级别
gen_env_config() {
  local out="$1"
  local env="$2"
  local purpose="$3"
  local db_host="$4"
  local log_level="$5"
  cat > "${out}" <<EOF
{
  "variables": [
    {
      "name": "HOP_ENVIRONMENT",
      "value": "${env}",
      "description": "当前生命周期环境：${purpose}"
    },
    {
      "name": "DATA_DB_HOST",
      "value": "${db_host}",
      "description": "数据源数据库主机"
    },
    {
      "name": "DATA_DB_PORT",
      "value": "5432",
      "description": "数据源数据库端口"
    },
    {
      "name": "LOG_LEVEL",
      "value": "${log_level}",
      "description": "日志级别"
    }
  ]
}
EOF
}

# ----------------------------------------------------------------------------
# 2. 初始化 default 项目（含完整元数据：4 类 + 三套生命周期环境配置）
# ----------------------------------------------------------------------------
init_default_project() {
  local project="default"
  local PROJECT_HOME="${STANDARD_PROJECT_FOLDER}/${project}"
  local META="${PROJECT_HOME}/metadata"

  mkdir -p "${META}/execution-data-profile" \
           "${META}/execution-info-location" \
           "${META}/pipeline-run-configuration" \
           "${META}/workflow-run-configuration"

  gen_project_config "${PROJECT_HOME}/project-config.json" ""
  gen_execution_data_profile  "${META}/execution-data-profile/first-last.json"
  gen_execution_info_location "${META}/execution-info-location/local-audit.json"
  gen_pipeline_run_config     "${META}/pipeline-run-configuration/local.json"
  gen_workflow_run_config     "${META}/workflow-run-configuration/local.json"

  # 三套生命周期环境配置（Hop 惯例：平铺在项目根，命名为 {env}-env-config.json）
  gen_env_config "${PROJECT_HOME}/dev-env-config.json"  "dev"  "开发" "localhost"         "DEBUG"
  gen_env_config "${PROJECT_HOME}/test-env-config.json" "test" "测试" "test-db.internal"  "INFO"
  gen_env_config "${PROJECT_HOME}/prod-env-config.json" "prod" "生产" "prod-db.internal"  "WARN"

  echo "✅ 项目 ${project} 初始化完成（4 类元数据 + dev/test/prod 三套环境配置）"
}

# ----------------------------------------------------------------------------
# 3. 初始化 samples 项目（继承 default，含 2 类元数据 + 示例目录）
# ----------------------------------------------------------------------------
init_samples_project() {
  local project="samples"
  local PROJECT_HOME="${STANDARD_PROJECT_FOLDER}/${project}"
  local META="${PROJECT_HOME}/metadata"

  mkdir -p "${META}/pipeline-run-configuration" \
           "${META}/workflow-run-configuration" \
           "${PROJECT_HOME}/pipelines/input" \
           "${PROJECT_HOME}/workflows/parallel" \
           "${PROJECT_HOME}/loops"

  # samples 继承 default 项目（复用其 execution-data-profile / execution-info-location）
  gen_project_config "${PROJECT_HOME}/project-config.json" "default"
  gen_pipeline_run_config "${META}/pipeline-run-configuration/local.json"
  gen_workflow_run_config "${META}/workflow-run-configuration/local.json"

  echo "✅ 项目 ${project} 初始化完成（2 类元数据，继承 default）"
}

# ----------------------------------------------------------------------------
# 主流程
# ----------------------------------------------------------------------------
init_default_project
init_samples_project

echo -e "\n🎉 所有项目环境初始化完毕，配置文件路径：${STANDARD_PROJECT_FOLDER}"
echo -e "   - default : 标准父项目（4 类元数据 + dev/test/prod 生命周期环境）"
echo -e "   - samples : 示例项目（继承 default）"
echo -e "\n对应 hop-config.json 中："
echo -e "   - lifecycleEnvironments : dev / test / prod（引用 \${PROJECT_HOME}/*-env-config.json）"
echo -e "   - projectLifecycles    : data_pipeline(dev→test→prod) / report_task(dev→prod)"
