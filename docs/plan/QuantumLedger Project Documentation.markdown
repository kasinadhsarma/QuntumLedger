# QuantumLedger: Java-Based Blockchain Accounting System

## Overview
QuantumLedger is a Java-based blockchain accounting system designed to manage financial transactions securely and immutably using blockchain principles. It leverages the Java Persistence API (JPA) with Hibernate as the provider and SQLite as the database backend for data persistence. The project is built using Maven for dependency management and includes the following key modules:
- **Block**: Represents a single block in the blockchain, containing a timestamp, transaction data, previous hash, and current hash.
- **Blockchain**: A chain of blocks, implemented conceptually as a `LinkedList<Block>`, with each block linked via hashes.
- **LedgerService**: Manages the addition and retrieval of transactions, ensuring they are stored in blocks.
- **UserAuth**: Provides a simple user-role authentication system for secure access.

The system uses `java.security.MessageDigest` for SHA-256 hashing to ensure block integrity and SQLite for lightweight, serverless data storage. This documentation provides a detailed guide on setting up the project, configuring the database, implementing the modules, and understanding the design choices based on research into blockchain and JPA technologies.

## Project Structure
The project is organized into a modular structure to ensure maintainability and scalability. Below is the directory structure:

```
quantumledger/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.quantumledger/
│   │   │   │   ├── model/
│   │   │   │   │   ├── Block.java
│   │   │   │   │   ├── Transaction.java
│   │   │   │   │   ├── User.java
│   │   │   │   │   └── Role.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── LedgerService.java
│   │   │   │   │   └── UserAuthService.java
│   │   │   │   ├── persistence/
│   │   │   │   │   ├── BlockRepository.java
│   │   │   │   │   ├── TransactionRepository.java
│   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   └── RoleRepository.java
│   │   │   │   └── Main.java
│   │   │   └── resources/
│   │   │       └── META-INF/
│   │   │           └── persistence.xml
│   │   └── test/
│   │       └── java/
│   │           └── com.quantumledger/
│   │               └── QuantumLedgerTest.java
│   └── test/
│       └── resources/
└── pom.xml
```

- **model**: Contains JPA entity classes for `Block`, `Transaction`, `User`, and `Role`.
- **service**: Houses business logic for `LedgerService` (transaction management) and `UserAuthService` (user authentication).
- **persistence**: Includes JPA repositories or DAOs for database operations.
- **Main.java**: The entry point for the console-based application.
- **persistence.xml**: Configures JPA for SQLite connectivity.

## Maven Configuration (pom.xml)
The project uses Maven for dependency management and build automation. The `pom.xml` file includes dependencies for Hibernate, SQLite JDBC, and the Hibernate community dialects for SQLite support. Below is the complete `pom.xml`:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.quantumledger</groupId>
    <artifactId>quantumledger</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>QuantumLedger</name>
    <description>A Java-based blockchain accounting system</description>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <!-- Hibernate ORM (JPA Provider) -->
        <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>7.0.0.Final</version>
        </dependency>
        <!-- Hibernate Community Dialects (for SQLite) -->
        <dependency>
            <groupId>org.hibernate.community</groupId>
            <artifactId>hibernate-community-dialects</artifactId>
            <version>7.0.0.Final</version>
        </dependency>
        <!-- SQLite JDBC Driver -->
        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.36.0.3</version>
        </dependency>
        <!-- Java Security for Hashing -->
        <dependency>
            <groupId>java.base</groupId>
            <artifactId>java.base</artifactId>
            <version>${java.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Maven Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### Dependencies Explained
- **Hibernate ORM 7.0.0.Final** ([Hibernate ORM](https://hibernate.org/orm/)): Provides JPA implementation for object-relational mapping.
- **Hibernate Community Dialects 7.0.0.Final**: Includes `SQLiteDialect` for Hibernate to work with SQLite databases.
- **SQLite JDBC 3.36.0.3** ([SQLite JDBC](https://github.com/xerial/sqlite-jdbc)): Enables Java to connect to SQLite databases.
- **Java 17**: Required for Hibernate 7.0, with `java.security.MessageDigest` used for SHA-256 hashing.

## Database Configuration (persistence.xml)
JPA configuration is defined in `src/main/resources/META-INF/persistence.xml`. This file sets up the persistence unit for SQLite and lists the entity classes:

```xml
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
             http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd"
             version="2.2">
    <persistence-unit name="quantumledger" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <class>com.quantumledger.model.Block</class>
        <class>com.quantumledger.model.Transaction</class>
        <class>com.quantumledger.model.User</class>
        <class>com.quantumledger.model.Role</class>
        <properties>
            <property name="javax.persistence.jdbc.driver" value="org.sqlite.JDBC"/>
            <property name="javax.persistence.jdbc.url" value="jdbc:sqlite:quantumledger.db"/>
            <property name="javax.persistence.jdbc.user" value=""/>
            <property name="javax.persistence.jdbc.password" value=""/>
            <property name="hibernate.dialect" value="org.hibernate.community.dialect.SQLiteDialect"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
```

### Configuration Details
- **Persistence Unit**: Named `quantumledger`, using `RESOURCE_LOCAL` transactions for a standalone application.
- **SQLite URL**: `jdbc:sqlite:quantumledger.db` creates a database file in the project root.
- **Hibernate Dialect**: `org.hibernate.community.dialect.SQLiteDialect` ensures compatibility with SQLite.
- **hbm2ddl.auto = update**: Automatically creates or updates database tables based on entity definitions.
- **show_sql and format_sql**: Enable SQL logging for debugging.

## Entity Definitions
The following JPA entities represent the core data models:

### Block.java
```java
package com.quantumledger.model;

import jakarta.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long index;

    private Date timestamp;

    private String previousHash;

    private String hash;

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public String calculateHash() {
        String transactionsHash = transactions.stream()
                .map(Transaction::toHashString)
                .collect(Collectors.joining());
        String dataToHash = index + timestamp.toString() + previousHash + transactionsHash;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIndex() { return index; }
    public void setIndex(Long index) { this.index = index; }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    public String getPreviousHash() { return previousHash; }
    public void setPreviousHash(String previousHash) { this.previousHash = previousHash; }
    public String getHash() { return hash; }
    public void setHash(String hash) { this.hash = hash; }
    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
}
```

### Transaction.java
```java
package com.quantumledger.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String from;

    private String to;

    private BigDecimal amount;

    private Date timestamp;

    private String description;

    @ManyToOne
    @JoinColumn(name = "block_id")
    private Block block;

    public String toHashString() {
        return from + to + amount.toString() + timestamp.toString() + description;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Block getBlock() { return block; }
    public void setBlock(Block block) { this.block = block; }
}
```

### User.java
```java
package com.quantumledger.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password; // Hashed

    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
```

### Role.java
```java
package com.quantumledger.model;

import jakarta.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., "ADMIN", "USER"

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

## Service Implementations
### LedgerService.java
The `LedgerService` manages the blockchain by adding transactions and creating new blocks. It uses JPA to persist data and calculates block hashes using SHA-256.

```java
package com.quantumledger.service;

import com.quantumledger.model.Block;
import com.quantumledger.model.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.Date;

public class LedgerService {
    @PersistenceContext
    private EntityManager entityManager;

    public void addTransaction(Transaction transaction) {
        Block latestBlock = getLatestBlock();
        Block newBlock = new Block();
        newBlock.setIndex(latestBlock != null ? latestBlock.getIndex() + 1 : 0L);
        newBlock.setPreviousHash(latestBlock != null ? latestBlock.getHash() : "0");
        newBlock.setTimestamp(new Date());
        newBlock.setTransactions(List.of(transaction));
        transaction.setBlock(newBlock);
        newBlock.setHash(newBlock.calculateHash());
        entityManager.persist(newBlock);
    }

    private Block getLatestBlock() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Block> cq = cb.createQuery(Block.class);
        Root<Block> root = cq.from(Block.class);
        cq.select(root).orderBy(cb.desc(root.get("index")));
        TypedQuery<Block> query = entityManager.createQuery(cq);
        query.setMaxResults(1);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public Block getBlockByIndex(Long index) {
        return entityManager.find(Block.class, index);
    }
}
```

### UserAuthService.java
The `UserAuthService` handles user registration and authentication. Passwords should be hashed before storage (implementation simplified here).

```java
package com.quantumledger.service;

import com.quantumledger.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class UserAuthService {
    @PersistenceContext
    private EntityManager entityManager;

    public void registerUser(User user) {
        // Hash password before saving (e.g., using BCrypt or SHA-256)
        entityManager.persist(user);
    }

    public boolean authenticate(String username, String password) {
        // Retrieve user and verify hashed password
        return true; // Simplified; implement proper password hashing and verification
    }
}
```

## Database Setup
- **Database File**: SQLite creates `quantumledger.db` in the project root when the application runs.
- **Schema**: Automatically generated by Hibernate based on entity definitions.
- **Tables**:
  | Table       | Columns                                                                 |
  |-------------|-------------------------------------------------------------------------|
  | Block       | id (PK), index (unique), timestamp, previousHash, hash                   |
  | Transaction | id (PK), from, to, amount, timestamp, description, block_id (FK)         |
  | User        | id (PK), username, password                                             |
  | Role        | id (PK), name                                                           |
  | User_Role   | user_id (FK), role_id (FK)                                              |

- **Initialization**: The `hibernate.hbm2ddl.auto = update` setting ensures tables are created or updated on application startup.

## Deep Research and Development
### Blockchain in Accounting
Blockchain technology ensures immutability and transparency, making it ideal for accounting systems. Each block contains a list of transactions and is linked to the previous block via a cryptographic hash (SHA-256 in this case). This structure prevents tampering, as altering a block would invalidate all subsequent blocks' hashes. Research from sources like [GeeksforGeeks](https://www.geeksforgeeks.org/implementation-of-blockchain-in-java/) highlights that blockchains use linked lists of blocks, with each block containing a hash of its data, previous hash, and timestamp, aligning with QuantumLedger's design.

### JPA with SQLite
JPA simplifies database operations by mapping Java objects to relational tables. Hibernate, as a JPA provider, supports SQLite through the `org.hibernate.community.dialect.SQLiteDialect` ([Hibernate ORM](https://hibernate.org/orm/)). SQLite is chosen for its lightweight, serverless nature, suitable for small-scale applications, as noted in tutorials like [Baeldung](https://www.baeldung.com/spring-boot-sqlite). The `hibernate.hbm2ddl.auto` setting automates schema creation, reducing setup complexity.

### Hashing
The project uses `java.security.MessageDigest` for SHA-256 hashing, a standard for blockchain applications due to its cryptographic strength. The hash is calculated based on the block's index, timestamp, previous hash, and transaction data, ensuring data integrity.

### User Authentication
The user-role system uses a many-to-many relationship between `User` and `Role` entities, allowing flexible role assignments (e.g., "ADMIN", "USER"). Passwords should be hashed (e.g., using BCrypt or SHA-256) for security, though the provided implementation is simplified.

### Design Choices
- **SQLite**: Chosen for its simplicity and zero-configuration setup, ideal for a prototype or small-scale system. For production, a more robust database like PostgreSQL might be considered.
- **JPA/Hibernate**: Provides a standardized way to manage database operations, reducing boilerplate code compared to raw JDBC.
- **SHA-256**: Ensures secure, collision-resistant hashes for blockchain integrity.
- **Console Application**: Assumed for simplicity, but the system can be extended to a web application using frameworks like Spring Boot.

## Setup Instructions
1. **Create Maven Project**:
   ```bash
   mvn archetype:generate -DgroupId=com.quantumledger -DartifactId=quantumledger -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
   ```
2. **Update pom.xml**: Add the dependencies listed above.
3. **Configure JPA**: Create `persistence.xml` in `src/main/resources/META-INF`.
4. **Implement Entities and Services**: Use the provided Java classes.
5. **Run the Application**:
   ```bash
   mvn compile
   java -cp target/quantumledger-1.0-SNAPSHOT.jar com.quantumledger.Main
   ```
6. **Verify Database**: Check `quantumledger.db` in the project root and use a tool like [SQLite Manager](https://addons.mozilla.org/en-US/firefox/addon/sqlite-manager/) to inspect tables.

## Usage
- **Add Transaction**: Use `LedgerService.addTransaction()` to create a new transaction, which is added to a new block.
- **View Blockchain**: Query blocks by index or traverse using `previousHash`.
- **User Management**: Register users and assign roles via `UserAuthService`.

## Future Enhancements
- **Consensus Mechanism**: Add proof-of-work or proof-of-stake for distributed environments.
- **Transaction Validation**: Implement rules to validate transactions (e.g., balance checks).
- **Web Interface**: Extend to a web application using Spring Boot for broader accessibility.
- **Security**: Enhance user authentication with proper password hashing and session management.

## Key Citations
- [Hibernate ORM Official Documentation](https://hibernate.org/orm/)
- [SQLite JDBC Driver GitHub Repository](https://github.com/xerial/sqlite-jdbc)
- [Spring Boot with SQLite Tutorial](https://www.baeldung.com/spring-boot-sqlite)
- [Implementation of Blockchain in Java](https://www.geeksforgeeks.org/implementation-of-blockchain-in-java/)
- [SQLite Manager Firefox Addon](https://addons.mozilla.org/en-US/firefox/addon/sqlite-manager/)