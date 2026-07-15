# Qi Hop 支持和不支持的 JVM

## 概述

运行 Qi Hop 需要 Java JVM，由于市场上的 JVM 种类繁多且不断增多，Qi Hop 在某些 JVM 上可能会出现运行问题。

本页面介绍了我们提供支持的 JVM 以及已列入黑名单的 JVM。

> **💡 提示:** 请查看[安装](installation-configuration.md)页面，获取有关如何安装和配置 Qi Hop 的完整说明。

## 支持的 JVM

Qi Hop 可以很好地运行在以下 64 位 Java 21 运行时上。

- [Oracle Java Runtime](https://www.java.com/)
- [Microsoft OpenJDK](https://www.microsoft.com/openjdk)（适用于 Windows、MacOS 和 Linux 的 OpenJDK 版本）。
- [OpenJDK Java Runtime](https://openjdk.java.net/install/)

## 不支持的 JVM

以下列表中的所有 JVM 已确认在 Qi Hop 上存在问题。

- [Adoptium](https://adoptium.net/)

其他 Java 运行时可能可以正常工作，你可能是在尝鲜。如果遇到问题，欢迎提交 [GitHub Issue](https://hop.apache.org/community/tools/#GitHub-Issues)，但请注明你的 JRE 和版本。
