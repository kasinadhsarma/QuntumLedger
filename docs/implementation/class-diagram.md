# Class Diagram - Core Components

```mermaid
classDiagram
    class Block {
        -Long id
        -String hash
        -String previousHash
        -Instant timestamp
        -int nonce
        -List~Transaction~ transactions
        +calculateHash() String
        +mineBlock(difficulty) void
    }
    
    class Transaction {
        -String transactionId
        -String sender
        -String recipient
        -double amount
        -long timestamp
        -String signature
        +signTransaction(privateKey) void
        +verifySignature() boolean
        +isValid() boolean
    }
    
    class Blockchain {
        -List~Block~ chain
        -int difficulty
        +addBlock(block) boolean
        +isChainValid() boolean
        +getLatestBlock() Block
    }
    
    class LedgerService {
        -Blockchain blockchain
        -DatabaseManager dbManager
        +createTransaction(sender, recipient, amount) Transaction
        +processTransaction(transaction) boolean
        +mineBlock() Block
    }
    
    class DatabaseManager {
        -EntityManager em
        +saveBlock(block) void
        +saveTransaction(transaction) void
        +getBlockchain() List~Block~
    }
    
    class User {
        -Long id
        -String username
        -String passwordHash
        -Set~Role~ roles
        +authenticate(password) boolean
    }
    
    class Role {
        -Long id
        -String name
    }
    
    %% Relationships
    Block "*" --* "1" Blockchain : part of
    Transaction "*" --* "1" Block : contained in
    Blockchain "1" --o "1" LedgerService : manages
    LedgerService "1" --> "1" DatabaseManager : uses
    User "*" --> "*" Role : has
    DatabaseManager --> Block : persists
    DatabaseManager --> Transaction : persists
```

This class diagram illustrates the core components of the QuantumLedger system and their relationships:

1. **Block**
   - Core data structure containing transactions
   - Implements mining with proof of work
   - Maintains chain integrity through previous hash

2. **Transaction**
   - Represents financial transfers between parties
   - Includes digital signatures and validation
   - Self-contained validation logic

3. **Blockchain**
   - Manages the chain of blocks
   - Enforces chain validity rules
   - Controls mining difficulty

4. **LedgerService**
   - Orchestrates blockchain operations
   - Processes transactions
   - Manages block creation and mining

5. **DatabaseManager**
   - Handles persistence using JPA/Hibernate
   - Manages entity lifecycles
   - Provides data access methods

6. **User & Role**
   - Implements authentication and authorization
   - Supports role-based access control
   - Uses secure password hashing

The diagram shows both the structure and relationships between components, helping developers understand the system architecture and data flow.
