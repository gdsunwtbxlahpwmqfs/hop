# Pipeline: All-Transform

## Basic Information

- **Pipeline Name:** All-Transform
- **Source File:** `03-转换插件/输入类/samples/all-transforms.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| DYNAMIC_FIELD_NAME | ROWNUM |  |
| PERIOD | 202005 |  |
| START_DATE | 2020-01-01 |  |

## Transforms

| Name | Type |
|------|------|
| AWS SNS Notify | SnsNotify |
| Add a checksum | CheckSum |
| Add constants | Constant |
| Add sequence | Sequence |
| Add value fields changing sequence | FieldsChangeSequence |
| Analytic query | AnalyticQuery |
| Apache Tika | Tika |
| Append streams | Append |
| Avro Decode | AvroDecode |
| Avro Encode | AvroEncode |
| Avro File Input | AvroFileInput |
| Avro File Output | AvroOutput |
| Azure Event Hubs Listener | AzureListener |
| Azure Event Hubs Writer | AzureWriter |
| Block until transforms finish | BlockUntilTransformsFinish |
| Blocking transform | BlockingTransform |
| Calculator | Calculator |
| Call DB procedure | DBProc |
| Cassandra input | CassandraInput |
| Cassandra output | CassandraOutput |
| Change file encoding | ChangeFileEncoding |
| Check if file is locked | FileLocked |
| Check if webservice is available | WebServiceAvailable |
| Clone row | CloneRow |
| Closure generator | ClosureGenerator |
| Coalesce Fields | Coalesce |
| Column exists | ColumnExists |
| Combination lookup/update | CombinationLookup |
| Concat Fields | ConcatFields |
| CrateDB bulk loader | CrateDBBulkLoader |
| Credit card validator | CreditCardValidator |
| Data grid | DataGrid |
| Data validator | Validator |
| Database join | DBJoin |
| Database lookup | DBLookup |
| De-serialize from file | CubeInput |
| Delay row | Delay |
| Detect Language | DetectLanguage |
| Dimension lookup/update | DimensionLookup |
| Doris bulk loader | DorisBulkLoader |
| Dummy | Dummy |
| Dynamic SQL row | DynamicSqlRow |
| ETL metadata injection | MetaInject |
| Execute SQL script | ExecSql |
| Execute row SQL script | ExecSqlRow |
| Fake data | Fake |
| Filter rows | FilterRows |
| Fuzzy match | FuzzyMatch |
| Generate random value | RandomValue |
| Generate rows | RowGenerator |
| Get JDBC Metadata | JdbcMetadata |
| Get Server Status | GetServerStatus |
| Get data from XML | getXMLData |
| Get system info | SystemInfo |
| Get table names | GetTableNames |
| Google Analytics 4 | GoogleAnalytics |
| Google Sheets Input | GoogleSheetsInput |
| Google Sheets Output | GoogleSheetsOutput |
| Group by | GroupBy |
| HTML to Text | Html2Text |
| Identify last row in a stream | DetectLastRow |
| If Null | IfNull |
| Injector | Injector |
| Insert / update | InsertUpdate |
| Insert False | InsertUpdate |
| JSON input | JsonInput |
| JSON output | JsonOutput |
| JavaScript | ScriptValueMod |
| Join rows (cartesian product) | JoinRows |
| Kafka Consumer | KafkaConsumer |
| Kafka Producer | KafkaProducerOutput |
| LDAP input | LDAPInput |
| LDAP output | LDAPOutput |
| Language Model Chat | LanguageModelChat |
| Load file content in memory | LoadFileInput |
| Mail | Mail |
| Mapping Input | MappingInput |
| Mapping Output | MappingOutput |
| Memory group by | MemoryGroupBy |
| Merge join | MergeJoin |
| Merge rows (diff) | MergeRows |
| Metadata Input | MetadataInput |
| Metadata structure of stream | TransformMetaStructure |
| Microsoft Access output | AccessOutput |
| Microsoft Excel input | ExcelInput |
| Microsoft Excel writer | TypeExitExcelWriterTransform |
| MonetDB bulk loader | MonetDBBulkLoader |
| MongoDB Delete | MongoDbDelete |
| MongoDB input | MongoDbInput |
| Multiway merge join | MultiwayMergeJoin |
| Neo4j Cypher Builder | Neo4jCypherBuilder |
| Number range | NumberRange |
| Oracle bulk loader | OraBulkLoader |
| PGP decrypt stream | PGPDecryptStream |
| PGP encrypt stream | PGPEncryptStream |
| Parquet File Input | ParquetFileInput |
| Parquet File Output | ParquetFileOutput |
| Pipeline Logging | PipelineLogging |
| Pipeline executor | PipelineExecutor |
| PostgreSQL Bulk Loader | PGBulkLoader |
| Properties output | PropertyOutput |
| REST client | Rest |
| Redshift bulk loader | RedshiftBulkLoader |
| Regex evaluation | RegexEval |
| Replace in string | ReplaceString |
| Reservoir sampling | ReservoirSampling |
| Row denormaliser | Denormaliser |
| Rules accumulator | RuleAccumulator |
| Rules executor | RuleExecutor |
| Run SSH commands | SSH |
| SAS Input | SASInput |
| SQL file output | SQLFileOutput |
| SSTable output | SSTableOutput |
| Salesforce delete | SalesforceDelete |
| Salesforce input | SalesforceInput |
| Salesforce insert | SalesforceInsert |
| Salesforce update | SalesforceUpdate |
| Salesforce upsert | SalesforceUpsert |
| Schema Mapping | SchemaMapping |
| Script | SuperScript |
| Set variables | SetVariable |
| Snowflake Bulk Loader | SnowflakeBulkLoader |
| Sort rows | SortRows |
| Split field to rows | SplitFieldToRows3 |
| Split fields | FieldSplitter |
| Splunk Input | SplunkInput |
| Standardize phone number | StandardizePhoneNumber |
| Stanford Simple NLP | StanfordSimpleNlp |
| Stream Schema Merge | StreamSchema |
| Stream lookup | StreamLookup |
| String operations | StringOperations |
| Strings cut | StringCut |
| Switch / case | SwitchCase |
| Synchronize after merge | SynchronizeAfterMerge |
| Table compare | TableCompare |
| Table exists | TableExists |
| Table input | TableInput |
| Teradata Fastload bulk loader | TeraFast |
| Text file input (deprecated) | TextFileInput |
| Text file output | TextFileOutput |
| Token Replacement | TokenReplacementPlugin |
| Update | Update |
| User defined Java class | UserDefinedJavaClass |
| User defined Java expression | Janino |
| Value mapper | ValueMapper |
| Vertica bulk loader | VerticaBulkLoader |
| Web services lookup | WebServiceLookup |
| Workflow Logging | WorkflowLogging |
| Workflow executor | WorkflowExecutor |
| Write to log | WriteToLog |
| XML input stream (StAX) | XMLInputStream |
| XSD validator | XSDValidator |
| XSD validator 2 | XSDValidator |
| XSL Transformation | XSLT |
| YAML input | YamlInput |
| Zip file | ZipFile |
