# Dataset

## Dataset Used

Finance Fraud & Loans Dataset – TestDataBox

The dataset contains relational banking data used to demonstrate
Map-Side Join and Reduce-Side Join using Hadoop MapReduce.

## Main Files Used

- customers.csv
- accounts.csv
- transactions.csv
- branches.csv
- loans.csv

## Verified Record Counts

- customers.csv: 1,111 data records
- accounts.csv: 1,667 data records
- transactions.csv: 50,000 data records
- branches.csv: 50 data records
- loans.csv: 333 data records

Total data records across these five files: 53,161

## Join Operations

### Map-Side Join

The transactions data is joined with the smaller branches table
using BranchID.

The branches table is loaded into the Mapper, allowing the Mapper
to perform the lookup without a Reduce phase.

### Reduce-Side Join

The customers and accounts tables are joined using CustomerID.

The Mapper tags records according to their source table.
During Shuffle and Sort, records with the same CustomerID are
grouped together. The Reducer then combines customer information
with the corresponding account records.

## Note

The record counts above were verified from the local dataset files
used in the Hadoop implementation.
