# QuantumLedger Implementation Documentation

## Table of Contents
1. [System Architecture](#system-architecture)
2. [Core Components](#core-components)
3. [Database Integration](#database-integration)
4. [Security Features](#security-features)
5. [Running the Application](#running-the-application)

## System Architecture

QuantumLedger is implemented as a Java-based blockchain system with the following key architectural components:

```
com.quantumledger/
├── core/           # Core blockchain components
├── auth/           # Authentication and user management
├── service/        # Business logic and services
├── persistence/    # Database integration
└── util/          # Utility classes
```

## Core Components

### 1. Block Implementation (`Block.java`)
```java
@Entity
@Table(name = "blocks")
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hash;
    private String previousHash;
    private Instant timestamp;
    private int nonce;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
```
- Implements the fundamental block structure
- Uses JPA annotations for persistence
- Includes mining capability with adjustable difficulty
- Maintains a list of transactions within each block

### 2. Transaction Implementation (`Transaction.java`)
```java
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private String transactionId;
    @Column(nullable = false)
    private String sender;
    @Column(nullable = false)
    private String recipient;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private long timestamp;
    @Column
    private String signature;
}
```
- Represents individual blockchain transactions
- Implements digital signatures (placeholder for now)
- Includes transaction validation logic

### 3. Ledger Service (`LedgerService.java`)
- Manages blockchain operations
- Handles transaction processing
- Coordinates block creation and mining
- Manages blockchain integrity

## Database Integration

### PostgreSQL Configuration
The system uses PostgreSQL as its primary database, configured through JPA/Hibernate:

```xml
<persistence-unit name="QuantumLedgerPU" transaction-type="RESOURCE_LOCAL">
    <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
    <properties>
        <property name="jakarta.persistence.jdbc.url" 
                  value="jdbc:postgresql://localhost:5432/quantumledger"/>
        <property name="hibernate.dialect" 
                  value="org.hibernate.dialect.PostgreSQLDialect"/>
        <property name="hibernate.hbm2ddl.auto" value="update"/>
    </properties>
</persistence-unit>
```

### Entity Relationships
- One-to-Many relationship between Block and Transaction
- Automatic schema generation and updates
- Connection pooling for improved performance

## Security Features

### 1. Transaction Security
- Hash-based transaction IDs
- Digital signature support (to be implemented)
- Transaction validation rules

### 2. Block Security
- SHA-256 hashing for block integrity
- Proof of Work implementation
- Previous hash linking for chain integrity

### 3. User Authentication
- BCrypt password hashing
- Role-based access control
- User entity management

## Running the Application

### Prerequisites
1. Java 17 or higher
2. PostgreSQL 12 or higher
3. Maven 3.8+

### Database Setup
```sql
CREATE DATABASE quantumledger;
ALTER USER postgres WITH PASSWORD 'postgres';
```

### Building the Project
```bash
mvn clean package
```

### Running the Application
```bash
java -jar target/quantumledger-1.0-SNAPSHOT.jar
```

### Verification
The application will:
1. Connect to PostgreSQL
2. Create necessary tables
3. Initialize the blockchain
4. Start accepting transactions

## Technical Specifications

### Dependencies
- **Jakarta Persistence**: Database ORM
- **Hibernate**: JPA implementation
- **PostgreSQL**: Database driver
- **BouncyCastle**: Cryptographic operations
- **BCrypt**: Password hashing
- **JUnit & Mockito**: Testing framework

### Performance Considerations
- Connection pooling enabled (pool size: 10)
- Eager fetching for block transactions
- Automatic schema updates
- Transaction batching support

### Future Improvements
1. Implement full digital signature system
2. Add distributed consensus mechanism
3. Enhance mining difficulty adjustment
4. Implement smart contracts
5. Add REST API endpoints
6. Improve transaction verification
7. Add more comprehensive testing
