# HeapVortex
HeapVortex – 3D JVM Memory Leak Profiler :- Build a tool that can inspect a running Java application and help developers find memory leaks visually.  Instead of looking at thousands of lines of memory reports, developers can see a 3D map of Java objects and immediately identify which objects are consuming memory.

# HeapVortex - DTO Layer

## Overview

The **DTO (Data Transfer Object)** layer in HeapVortex is responsible for transferring data between the backend and frontend while maintaining a clean separation between the API and database entities.

DTOs help improve security, reduce unnecessary data exposure, and provide structured request and response objects for REST APIs and WebSocket communication.

---

## Project

**HeapVortex – 3D JVM Memory Leak Profiler**

HeapVortex is the an advanced Spring Boot application that monitors JVM memory usage, captures heap dumps, analyzes object references, detects memory leaks, and streams live JVM telemetry to a React + Three.js frontend.

---

## DTO Package Structure

```
com.heapvortex.dto
│
├── request
│   ├── ConnectJvmRequestDTO
│   ├── HeapDumpRequestDTO
│   └── AnalyzeHeapRequestDTO
│
└── response
    ├── ApiResponseDTO
    ├── GCEventDTO
    ├── GraphDTO
    ├── HeapAnalysisDTO
    ├── HeapDumpResponseDTO
    ├── JvmProcessDTO
    ├── LiveTelemetryDTO
    ├── MemoryStatsDTO
    ├── ObjectEdgeDTO
    └── ObjectNodeDTO
```

---

## Request DTOs

### ConnectJvmRequestDTO

Used to establish a connection with a target JVM through JMX.

**Fields**

- processId
- host
- port
- username
- password

---

### HeapDumpRequestDTO

Used to request a heap dump generation.

**Fields**

- processId
- outputLocation

---

### AnalyzeHeapRequestDTO

Used to analyze an existing heap dump file.

**Fields**

- heapDumpFile

---

## Response DTOs

### JvmProcessDTO

Represents information about available JVM processes.

**Fields**

- processId
- applicationName
- displayName
- jmxUrl
- connected

---

### MemoryStatsDTO

Contains JVM memory statistics.

**Fields**

- heapUsed
- heapCommitted
- heapMax
- nonHeapUsed
- nonHeapCommitted
- heapUsagePercentage

---

### HeapDumpResponseDTO

Returns information after generating a heap dump.

**Fields**

- id
- fileName
- fileSize
- status
- createdAt

---

### GCEventDTO

Represents a Garbage Collection event.

**Fields**

- gcName
- action
- duration
- timestamp

---

### ObjectNodeDTO

Represents an object node in the JVM heap graph.

**Fields**

- id
- className
- shallowSize
- retainedSize
- gcRoot

---

### ObjectEdgeDTO

Represents a reference between two objects.

**Fields**

- source
- target

---

### GraphDTO

Transfers the complete object graph to the frontend.

**Fields**

- nodes
- edges

---

### HeapAnalysisDTO

Contains heap analysis results.

**Fields**

- gcRoot
- retainedMemory
- leakedObjectCount
- leakedObjects

---

### LiveTelemetryDTO

Used for real-time WebSocket communication.

**Fields**

- memory
- gcEvent
- cpuUsage
- threadCount
- uptime

---

### ApiResponseDTO

Generic response wrapper for all REST APIs.

**Fields**

- success
- message
- data

---

## DTO Flow

```
React Frontend
       │
       ▼
Controller
       │
       ▼
Request DTO
       │
       ▼
Service Layer
       │
       ▼
Entity
       │
       ▼
Mapper
       │
       ▼
Response DTO
       │
       ▼
React Frontend
```

---

## Benefits of DTOs

- Provides a clear separation between entities and API models.
- Prevents direct exposure of database entities.
- Improves API readability and maintainability.
- Simplifies request validation.
- Supports secure and efficient data transfer.
- Enables structured communication for REST APIs and WebSocket events.

---

## Technologies Used

- Java 21
- Spring Boot
- Lombok
- Spring Web
- Spring Validation
- JMX (Java Management Extensions)
- Eclipse MAT API
- WebSocket
- React
- Three.js

---
# useMemoryData Hook

## Overview
The `useMemoryData` hook is a custom React hook used to manage JVM memory data in the HeapVortex application.

## Purpose
It provides memory statistics that can be displayed on the dashboard.

## Returned Data
- Used Memory
- Free Memory
- Total Memory
- Max Memory
- Leak Status

## Features
- Uses React Hooks (`useState` and `useEffect`)
- Simulates real-time memory updates
- Easy to connect with backend APIs in the future
- Reusable across multiple components

## Future Enhancements
- Fetch live JVM data from backend
- Support WebSocket updates
- Error handling
- Loading state

# Memory Service

## Overview
The Memory Service is responsible for communicating with the backend API to retrieve JVM memory statistics.

## Purpose
This service acts as a bridge between the frontend and backend by fetching memory-related information.

## Features
- Fetches JVM memory data from REST API
- Handles API errors gracefully
- Returns fallback dummy data during development
- Easy to reuse across multiple React components

## API Endpoint

GET /api/memory

## Returned Data

```json
{
  "usedMemory": 512,
  "freeMemory": 1024,
  "totalMemory": 1536,
  "maxMemory": 2048,
  "leakStatus": "Normal"
}
```

## Future Enhancements

- Axios integration
- WebSocket support
- Authentication
- Retry mechanism
- Loading and error handling
