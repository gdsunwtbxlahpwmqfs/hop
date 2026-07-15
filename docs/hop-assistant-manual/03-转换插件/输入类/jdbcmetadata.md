# ![JDBC Metadata transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/jdbcmetadata.svg) JDBC Metadata

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 方法和参数选项卡
在此选项卡中，您可以指定要调用的 DatabaseMetaData 对象的 Java 方法以获取 metadata。它还控制步骤行为的其他方面。

*Always pass the input row:* 控制在没有 metadata 时此步骤的行为。如果未勾选此选项，步骤将不会产生任何输出行。如果勾选此选项，输入行仍将被传递。在这种情况下，源自 metadata 结果集的任何输出字段都将为 NULL。

*Metadata Method:* 此下拉列表可用于指定对 DatabaseMetaData 对象调用哪个 metadata 方法以获取 metadata：

- Catalogs：参见 DatabaseMetaData.getCatalogs()

- Best row identifier：参见 [DatabaseMetaData.getBestRowIdentifier()](`http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData#getBestRowIdentifier(java.lang.String, java.lang.String, java.lang.String, int, boolean)`)

- Column privileges：参见 [DatabaseMetaData.getColumnPrivileges()](`http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData#getColumnPrivileges(java.lang.String, java.lang.String, java.lang.String, java.lang.String)`)

- Columns：参见 [DatabaseMetaData.getColumns()](`http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData#getColumns(java.lang.String, java.lang.String, java.lang.String, java.lang.String)`)

- Cross references：参见 [DatabaseMetaData.getCrossReferences()](`http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData#getCrossReference(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)`)

- Exported key columns：参见 [DatabaseMetaData.getExportedKeys()](`http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData#getExportedKeys(java.lang.String, java.lang.String, java.lang.String)`)

- Foreign key columns：参见 [DatabaseMetaData.getImportedKeys()](`http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData#getImportedKeys(java.lang.String, java.lang.String, java.lang.String)`)

- Primary key columns：参见 [DatabaseMetaData.getPrimaryKeys()](`http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData#getPrimaryKeys(java.lang.String, java.lang.String, java.lang.String)`)

- Schemas：参见 DatabaseMetaData.getSchemas()

- Table privileges：参见 [DatabaseMetaData.getTablePrivileges()](`http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData#getTablePrivileges(java.lang.String, java.lang.String, java.lang.String)`)

- Table types：参见 DatabaseMetaData.getTableTypes()

- Tables：参见 [`http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData.html#getTables(java.lang.String, java.lang.String, java.lang.String, java.lang.String](`http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData#getTables(java.lang.String, java.lang.String, java.lang.String, java.lang.String))`[DatabaseMetaData.getTables()]

- Data types：参见 DatabaseMetaData.getTypeInfo()

- Version columns：参见 [DatabaseMetaData.getVersionColumns()](`http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData#getVersionColumns(java.lang.String, java.lang.String, java.lang.String)`)

- 选择方法后，字段将被添加到选项卡中，以便您可以指定该方法所需的任何参数。有关特定方法的参数及其含义的描述，请参阅每个方法的 javadoc（上方链接）。

*Get arguments from fields:* 如果勾选此选项，可以通过从输入流中选择字段来指定方法的参数，该字段的值将用作实际参数值。如果未勾选此选项，可以直接在参数字段中输入字面量作为参数值。

*Remove argument fields:* 此选项在勾选 Get arguments from fields 时适用。勾选后，被选为参数字段的字段将从输出流中移除。当您链式串联多个 JdbcMetaData 步骤，使用上游步骤输出的字段作为下游步骤的参数字段时，此选项通常很方便。在这种情况下，使用此选项可以移除大量重复字段。

## 输出字段选项卡
此选项卡允许您控制输出 metadata 如何添加到输出流中。

*Output Fields:* 当您在方法和参数选项卡上选择特定 metadata 方法时，此网格会自动填充相应的字段。使用此网格视图可移除或重命名输出字段。

*Get fields:* 使用此按钮可重新填充网格。这将重新添加已移除的字段，但保留已重命名的字段。
