# Banking Data Integration Using Hadoop MapReduce

## Map-Side Join and Reduce-Side Join

This project demonstrates how Hadoop MapReduce can be used to integrate
related banking datasets using two different join techniques:

- Map-Side Join
- Reduce-Side Join

The implementation uses HDFS for data storage and Hadoop MapReduce for
distributed data processing.

---

## Project Objectives

1. Store banking datasets in HDFS.
2. Perform data integration using MapReduce.
3. Demonstrate a Map-Side Join using a small reference dataset.
4. Demonstrate a Reduce-Side Join using related datasets.
5. Compare where the join operation is performed in the MapReduce workflow.

---

## Dataset

The project uses the **Finance Fraud & Loans Dataset – TestDataBox**.

The main datasets used are:

- customers.csv
- accounts.csv
- transactions.csv
- branches.csv
- loans.csv

### Verified Record Counts

| Dataset | Data Records |
|---|---:|
| customers.csv | 1,111 |
| accounts.csv | 1,667 |
| transactions.csv | 50,000 |
| branches.csv | 50 |
| loans.csv | 333 |

Total data records across these five files: **53,161**

More information is available in [`Dataset/README.md`](Dataset/README.md).

---

# 1. Map-Side Join

## Join Performed

The `transactions.csv` data is joined with the smaller
`branches.csv` dataset using:

```text
BranchID
```
Map-Side Join Working

The smaller branches.csv dataset is loaded into the Mapper using
Distributed Cache.

The Mapper:

1.Reads a transaction record.
2.Extracts the BranchID.
3.Looks up the corresponding branch information.
4.Produces the joined output.

No Reducer is required for this implementation.

The Hadoop execution completed with:
```text
map 100%
reduce 0%
```
```text
##Source Code:
```
```text
MapSideJoin/
├── BankingMapSideJoin.java
└── BankingMapSideJoinMapper.java
```
# 2. Reduce-Side Join
Join Performed

``` text The customers.csv and accounts.csv datasets are joined using: ```
```CustomerID``` 
Reduce-Side Join Working

The Mapper identifies the source of each record and emits records using
the common CustomerID.

During Shuffle and Sort, records having the same CustomerID are grouped
together.

The Reducer then:

1.Separates customer and account records.
2.Finds matching CustomerID values.
3.Combines customer information with each corresponding account.
4.Produces the joined records.

For example, if one customer has four accounts, the Reducer can produce
four joined output records for that CustomerID.
```text
Source Code:
```
ReduceSideJoin/
├── BankingJoin.java
├── BankingJoinMapper.java
└── BankingJoinReducer.java
#3. Hadoop Components Used

The implementation uses:

1.Hadoop HDFS
2.Hadoop MapReduce
3.NameNode
4.DataNode
5.ResourceManager
6.NodeManager
7.Mapper
8.Reducer
9.Distributed Cache
#4. Project Structure
```text
Banking-Data-Integration-Hadoop/
│
├── MapSideJoin/
│   ├── BankingMapSideJoin.java
│   └── BankingMapSideJoinMapper.java
│
├── ReduceSideJoin/
│   ├── BankingJoin.java
│   ├── BankingJoinMapper.java
│   └── BankingJoinReducer.java
│
├── Dataset/
│   └── README.md
│
├── Results/
│   ├── map-side-join-output.txt
│   └── reduce-side-join-output.txt
│
├── Screenshots/
├── Presentation/
├── Report/
├── .gitignore
└── README.md
```
#5. Results
Sample outputs produced by the Hadoop jobs are available in:
Results/map-side-join-output.txt
Results/reduce-side-join-output.txt
The result files contain sample records from the actual Hadoop
executions.
6. Key Difference
| Feature                   | Map-Side Join                       | Reduce-Side Join                             |
| ------------------------- | ----------------------------------- | -------------------------------------------- |
| Join location             | Mapper                              | Reducer                                      |
| Main idea                 | Lookup small dataset during mapping | Group matching keys and join during reducing |
| Shuffle required for join | No                                  | Yes                                          |
| Reducer required          | No                                  | Yes                                          |
| Example                   | Transactions + Branches             | Customers + Accounts                         |

Conclusion
This project demonstrates two approaches for integrating related banking
datasets using Hadoop MapReduce.

The Map-Side Join performs the lookup during the Map phase by using a
small reference dataset. The Reduce-Side Join uses Shuffle and Sort to
bring records with the same join key together before performing the join
in the Reducer.

Together, these implementations demonstrate how different MapReduce
join strategies can be applied depending on the characteristics of the
datasets.
