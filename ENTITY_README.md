# HeapVortex - Entity Layer

## 📌 Overview

The **Entity Layer** represents the core domain model of the HeapVortex application. Entity classes are mapped directly to database tables using **Spring Data JPA** and **Hibernate**. They store information related to JVM processes, heap dumps, memory statistics, garbage collection events, and heap analysis results.

The Entity layer acts as the bridge between the application and the database, ensuring persistent storage and retrieval of application data.

---

## 🎯 Responsibilities

- Represent database tables as Java classes.
- Store JVM monitoring and heap analysis data.
- Define relationships between entities.
- Support CRUD operations through JPA repositories.
- Maintain data integrity using annotations.

---

## 📂 Package Structure

```
com.heapvortex.entity
│
├── HeapDump.java
├── JvmProcess.java
├── MemorySnapshot.java
├── GCEvent.java
├── HeapAnalysis.java
├── ObjectNode.java
├── ObjectReference.java
└── User.java
```

---

## 📄 Entity Description

### JvmProcess

Stores information about running JVM applications.

**Attributes**
- id
- processId
- applicationName
- displayName
- host
- port
- connectionStatus

---

### HeapDump

Stores metadata of generated heap dump files.

**Attributes**
- id
- fileName
- filePath
- fileSize
- createdAt
- status

---

### MemorySnapshot

Stores JVM memory usage captured during monitoring.

**Attributes**
- id
- heapUsed
- heapCommitted
- heapMax
- nonHeapUsed
- nonHeapCommitted
- cpuUsage
- threadCount
- recordedAt

---

### GCEvent

Stores Garbage Collection event details.

**Attributes**
- id
- gcName
- action
- duration
- timestamp

---

### HeapAnalysis

Stores heap analysis results after processing a heap dump.

**Attributes**
- id
- gcRoot
- retainedMemory
- leakedObjectCount
- analysisTime

---

### ObjectNode

Represents an object in the JVM heap graph.

**Attributes**
- id
- objectId
- className
- shallowSize
- retainedSize
- gcRoot

---

### ObjectReference

Represents references between heap objects.

**Attributes**
- id
- sourceObject
- targetObject

---

### User (Optional)

Stores application user information for authentication.

**Attributes**
- id
- username
- email
- password
- role

---

## 🔗 Entity Relationships

```
JvmProcess
     │
     ├─────────────┐
     │             │
     ▼             ▼
HeapDump     MemorySnapshot
     │
     ▼
HeapAnalysis
     │
     ├─────────────┐
     ▼             ▼
ObjectNode   ObjectReference
```

---

## 🛠️ Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Lombok

---

## 📌 Benefits

- Clean database representation.
- Supports object-relational mapping (ORM).
- Simplifies CRUD operations.
- Maintains data consistency.
- Easily extendable for future features.

---

