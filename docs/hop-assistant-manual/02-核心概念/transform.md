Transform 是 Pipeline 中执行的工作单元。
典型的 Transform 操作包括从文件、数据库读取数据，执行查找或连接，丰富、清洗数据等。
Pipeline 中的所有 Transform 并行执行。
Transform 处理数据并将处理后的数据批次通过 Hop 传递给后续 Action 处理。
