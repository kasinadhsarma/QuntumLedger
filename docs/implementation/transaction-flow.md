# Transaction Flow Diagram

```mermaid
sequenceDiagram
    participant Client
    participant LedgerService
    participant Transaction
    participant Block
    participant Blockchain
    participant DatabaseManager

    Client->>LedgerService: Create Transaction
    activate LedgerService
    
    LedgerService->>Transaction: new Transaction(sender, recipient, amount)
    activate Transaction
    Transaction-->>Transaction: calculateHash()
    Transaction-->>Transaction: signTransaction()
    Transaction-->>LedgerService: return transaction
    deactivate Transaction
    
    LedgerService->>Transaction: isValid()
    activate Transaction
    Transaction-->>LedgerService: validation result
    deactivate Transaction
    
    alt is valid
        LedgerService->>Block: Add to pending transactions
        activate Block
        Block-->>LedgerService: Transaction added
        deactivate Block
        
        LedgerService->>Block: Mine block (if enough transactions)
        activate Block
        Block-->>Block: calculateHash()
        Block-->>Block: mineBlock(difficulty)
        Block-->>LedgerService: Block mined
        deactivate Block
        
        LedgerService->>Blockchain: Add Block
        activate Blockchain
        Blockchain-->>Blockchain: Verify block
        Blockchain->>DatabaseManager: Save block & transactions
        DatabaseManager-->>Blockchain: Saved
        Blockchain-->>LedgerService: Block added
        deactivate Blockchain
        
        LedgerService-->>Client: Transaction processed
    else is invalid
        LedgerService-->>Client: Transaction rejected
    end
    
    deactivate LedgerService
```

This sequence diagram illustrates the flow of a transaction in the QuantumLedger blockchain system:

1. **Client Initiation**: The process begins when a client requests to create a transaction.

2. **Transaction Creation**:
   - LedgerService creates a new Transaction object
   - Transaction generates its hash and digital signature
   - Basic validation is performed

3. **Transaction Processing**:
   - Valid transactions are added to pending transactions in a Block
   - When enough transactions are collected, block mining begins
   - Block calculates its hash and performs Proof of Work

4. **Block Addition**:
   - New block is verified and added to the blockchain
   - Block and its transactions are persisted to the database
   - Confirmation is sent back to the client

5. **Error Handling**:
   - Invalid transactions are rejected immediately
   - Validation occurs at multiple levels
   - Each step includes proper error handling
