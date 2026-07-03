# Pipeline: neo4j-output-relationship-bug-2299-validation

## Basic Information

- **Pipeline Name:** neo4j-output-relationship-bug-2299-validation
- **Description:** Validation pipeline for relationship bug test (#2299)
- **Extended Description:** Validates that relationships are created with correct types when relationship type changes within a batch. The Cypher query verifies:
- All 6 expected relationships exist
- Each relationship has the correct type (HAS_ORDER, HAS_DELIVERABLE, HAS_WORK)
- The critical test: PROJECT->DELIVERABLE should have HAS_DELIVERABLE (not HAS_ORDER from first row)
- PROJECT->WORK should have HAS_WORK (not HAS_ORDER from first row)
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-relationship-bug-2299-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| OUTPUT | Dummy |
| Validate Relationships | Neo4jCypherOutput |

## Hops

| From | To |
|------|----|
| Validate Relationships | OUTPUT |
