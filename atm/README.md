# ATM System
Low Level Design Implementation in Java

## Functional Requirements

**Core flow**
1. A customer authenticates via card + PIN before any transaction is permitted
2. Supported transactions: **Withdraw, Deposit, Balance Inquiry, Mini Statement**
3. Multi-bank support — a single ATM can authenticate and serve cards issued by different banks, routed by `bankId`
4. Cash is dispensed in an optimal mix of denominations (₹2000 / ₹500 / ₹100) rather than a single note type
5. Withdrawals: physical cash is dispensed **before** the account is debited; if the debit is subsequently declined, the dispensed cash is returned to inventory (compensating rollback) — the transaction fails cleanly rather than leaving the machine short of cash with no matching debit

## Non-Functional Requirements

1. **Correctness under partial failure**: no transaction may leave the system in a state where cash left the machine without a corresponding successful debit, or vice versa
2. **Extensibility**: adding a new transaction type, a new account type, or a new cash denomination should mean adding a class, not editing existing ones (Open/Closed Principle)
3. **Encapsulation of the ledger**: account statement entries can only be appended by the `Account` hierarchy itself (`protected` access), never by arbitrary external code holding an `Account` reference
4. Thread-safety on the shared cash inventory (single physical `CashDispenser` resource per ATM)

## Explicit Assumptions

1. Single ATM instance per running system (not modeling a network of ATMs sharing one cash pool)
2. `Bank` is modeled as an interface with an in-memory implementation (`InMemoryBank`) — no real persistence layer or network call
3. Card PIN is stored and compared as plaintext for demo purposes — a production system would hash it and never store it on the card itself
4. IDs (`accountId`, `cardNumber`, `bankId`) are plain `String`s for interview-scope simplicity; a production system would likely use typed wrapper IDs internally and separate internal (`Long`) vs. external (`UUID`) identifier strategies — noted as a deliberate simplification, not an oversight

## Out of Scope (for this iteration)

1. Fund transfers between accounts
2. PIN change / card blocking self-service
3. Receipt printing 
4. Concurrent multi-ATM access to a shared bank ledger (single-ATM, single-JVM scope only)