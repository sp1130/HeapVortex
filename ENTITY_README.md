# HeapVortex - Entity Layer

## 📌 Overview

The Entity represents the core domain model of the HeapVortex application. Entity classes are mapped directly to database tables using **Spring Data JPA** and **Hibernate**. They store information related to JVM processes, heap dumps, memory statistics, garbage collection events, and heap analysis results.

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

# HeapVortex - Parser Module

## 📌 Overview

The **Parser Module** is responsible for reading and processing Java Heap Dump (`.hprof`) files generated from running JVM applications. It extracts object information, memory usage, reference chains, and Garbage Collection (GC) Roots to support memory leak detection and visualization.

This module acts as the core processing engine of HeapVortex by converting raw heap dump data into structured objects that can be analyzed and displayed through the application.

---

## 🎯 Objectives

- Read and parse `.hprof` heap dump files.
- Extract JVM object information.
- Identify object reference relationships.
- Calculate shallow and retained memory sizes.
- Detect Garbage Collection (GC) Roots.
- Generate structured data for visualization.
- Support memory leak analysis.

---

## 📂 Package Structure

```
com.heapvortex.parser
│
├── HeapDumpParser.java
├── ObjectParser.java
├── ReferenceParser.java
├── GCParser.java
├── MemoryParser.java
└── ParserConfiguration.java
```

---

## ⚙️ Responsibilities

- Read heap dump files.
- Parse JVM object metadata.
- Extract object references.
- Analyze memory allocation.
- Detect unreachable objects.
- Build object graph data.
- Send parsed results to the Service Layer.

---

## 🔄 Parser Workflow

```
Heap Dump (.hprof)
        │
        ▼
HeapDumpParser
        │
        ▼
Object Extraction
        │
        ▼
Reference Analysis
        │
        ▼
GC Root Detection
        │
        ▼
Memory Calculation
        │
        ▼
DTO Conversion
        │
        ▼
Frontend Visualization
```

---

## 📋 Key Components

### HeapDumpParser
- Reads heap dump files.
- Initializes the parsing process.

### ObjectParser
- Extracts JVM object information.
- Identifies object types and class names.

### ReferenceParser
- Builds object reference relationships.
- Creates parent-child object mappings.

### GCParser
- Detects Garbage Collection Roots.
- Finds retained objects.

### MemoryParser
- Calculates memory usage.
- Computes shallow and retained sizes.

---

## 🛠️ Technologies Used

- Java 21
- Spring Boot
- Eclipse Memory Analyzer (MAT)
- Java I/O
- Collections Framework
- Lombok

---

## ✅ Features

- Heap dump parsing
- Object extraction
- Reference chain analysis
- GC Root detection
- Memory usage calculation
- Leak candidate identification
- High-performance parsing
- Clean and modular architecture

---

## 📈 Future Enhancements

- Parallel heap parsing
- Incremental parsing
- Large heap optimization
- Export parsed results as JSON
- Multi-threaded parsing
- Support for additional heap dump formats

---


