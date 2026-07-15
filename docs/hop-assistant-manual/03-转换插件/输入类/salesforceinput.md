# ![Salesforce Input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/SFI.svg) Salesforce Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## General

Enter the following information in the transform name field:

- Transform name: Specifies the unique name of the Salesforce Input transform on the canvas.
You can customize the name or leave it as the default.

> **📝 注意:** If your organization has enabled My Domain, you may see 'Restricted Domain' in your Salesforce login history when attempting to use the default URL, as described [here](https://help.salesforce.com/s/articleView?id=000387792&type=1). See below for options.

## Options

### Connection

The Salesforce transform can use a pre-defined [Salesforce Connection](../其他转换/salesforce-connection.md) or use inline connection settings directly.
When using a Salesforce Connection, the connection settings (URL, username, password, OAuth tokens) defined in the metadata will be used.
When used without a Salesforce Connection, the connection details need to be specified directly in the transform.

| Option | Description |
|---|---|
| Salesforce Connection | The (optional) [Salesforce Connection](../其他转换/salesforce-connection.md) to use. When selected, the connection settings from the metadata will be used and the inline connection fields will be disabled. |
| Salesforce Webservice URL a | Specify the URL to the Salesforce Webservice. |
| Username | Specify the username for authenticating to Salesforce |
| Password | Specify the password for authenticating to Salesforce. |
| Test Connection | Click to verify the connection can be made to the Salesforce Webservice URL you specified. |
## Settings

In this panel, you can set the module to query from as well as the query conditions.

| Option | Description |
|---|---|
| Specify query | Select this check box to manually execute a query based on your own SOQL statements. |
| Module a | Select the module (table) from which you want to retrieve data. |
| Query Condition a | Enter any query filters you want to apply. |

## Content Tab

The content tab allows you to optionally include additional descriptive fields in the result set.

### Advanced

Use these options to further refine the data returned from the queries specified in the Settings tab.
For example, you may want to only query deleted records within a specified date range.
The Advanced panel includes the following fields:

| Option | Description |
|---|---|
| Retrieve | Select which records you want to retrieve to further define your pool of data. |
| Query all records | Select the check box to query all the records you are retrieving. |
| Start date | Specify the starting date for retrieving the records in the date range. |
| End date | Specify the end date for retrieving the records in the date range. |

### Additional Fields

This panel includes the following fields:

| Option | Description |
|---|---|
| Include URL in output? |  |
| Include Module in output? |  |
| Include SQL in output? |  |
| Include timestamp in output? |  |
| Include Rownum in output? |  |
| Include deletion date in output? |  |

### Other fields

Enter information for the remaining fields on the Content tab.

| Option | Description |
|---|---|
| Time out | Specify the timeout interval in milliseconds before the transform times out. |
| Use compression | Select to compress (.gzip) the data when connecting between Hop and Salesforce. |
| Limit a | Specify the maximum number of records to retrieve. |

## Fields tab

The fields tab displays the fields that are read from the Salesforce module selected on the Settings tab.
You will need to go to the Fields tab and press the Get Fields button to populate the fields returned before you can preview the rows returned.

### Use Field API Names

The "Use Field API Names" checkbox allows you to choose between using Salesforce Field API Names (e.g., `CustomField__c`) or Field Labels (e.g., "Customer ID") for the output field names in your pipeline.

- **API Names (recommended)**: Salesforce API names are permanent identifiers that won't change even if an administrator modifies the field label in Salesforce. This prevents your pipelines from breaking when field labels are updated.
- **Labels**: Field labels are user-friendly names that can be changed by Salesforce administrators. Using labels may make pipelines easier to read but creates a maintenance risk if labels are modified.

> **📝 注意:** For new transforms, "Use Field API Names" is enabled by default. For existing transforms created before this feature was added, the checkbox defaults to off to maintain backward compatibility.

The following are the properties display in the Fields tab:

| Option | Description |
|---|---|
| Name | The name of the input field. |
| Field | The name of the field that contains the record. |
| IsIdLookup? | Specify if the field will be used as a fieldname ID (links to an External ID) when accessing the database to perform other calls, such as delete, insert, update, and upsert. |
| Type | The data type of the field. |
| Format | An optional mask for converting the format of the original field. |
| Length a | The length of the field depends on the following field types: |
| Precision | Number of floating point digits for number-type fields. |
| Currency | Symbol used to represent currencies ($5,000.00 or €5.000,00 for example). |
| Decimal | A decimal point can be a "." or "," (5,000.00 or 5.000,00 for example). |
| Group | A grouping can be a "," or "." (5,000.00 or 5.000,00 for example). |
| Trim type | The trimming method to apply to a string. |
| Repeat | If the corresponding value in this row is empty, repeat the one from the last time it was not empty. |

Salesforce Input

### Example Case: Retrieving Deleted Records from Salesforce

#### Scenario
You want to retrieve all deleted records from Salesforce, even when the dataset exceeds the default pagination size of 2000 records.

#### Steps
1. **Set "Retrieve" to "Deleted"**:
   In the *Advanced* section of the transform, select the "Retrieve" option and set it to **Deleted**.

2. **Enable "Query all records"**:
   - Check the box labeled **Query all records** in the *Advanced* section.
   - This allows the transform to retrieve all available records across multiple pages.

3. **Add a Query Condition**:
   - In the *Settings* tab, include the following query condition:
     `IsDeleted = true`
   - This ensures the transform retrieves only deleted records.

#### Note on Pagination
If "Query all records" is not enabled, the transform will retrieve only the first 2000 records due to Salesforce's default pagination settings. Enabling "Query all records" ensures that the system processes all pages of data until all matching records are retrieved.

#### Example Use Case
You want to retrieve all customer records deleted within the past 30 days. Configure your transform as follows:

- Retrieve: Set to `Deleted`.
- Query Condition: 
  `IsDeleted = true AND LastModifiedDate >= LAST_N_DAYS:30`

#### Troubleshooting
- If the transform retrieves fewer records than expected, verify that **Query all records** is enabled.
- Ensure your Salesforce user has appropriate permissions to access deleted records.
