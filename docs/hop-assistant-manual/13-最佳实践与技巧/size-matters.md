请合理控制 workflow 中的 action 数量和 pipeline 中的 transform 数量。

- 更大的 pipeline 或 workflow 会变得更难调试和开发。
- 每添加一个 transform 到 pipeline 中，运行时至少会启动一个新线程。仅仅因为数百个 transform 对应数百个线程，就可能显著降低速度。

如果您发现需要拆分 pipeline，可以使用 [Serialize to file](pipeline/transforms/serialize-to-file.md) transform 将中间数据写入临时文件。然后 workflow 中的下一个 pipeline 可以使用 [De-serialize from file](pipeline/transforms/serialize-de-from-file.md) transform 再次读取数据。虽然显然您也可以使用数据库或其他文件类型来完成同样的操作，但这些 transform 的执行速度最快。
