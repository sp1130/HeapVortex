package com.heapvortex.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

/*====================================================
  1. JVM PROCESS ENTITY
====================================================*/

@Entity
@Table(name = "jvm_process")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JvmProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long processId;
    private String applicationName;
    private String displayName;
    private String host;
    private Integer port;
    private Boolean connected;

    @OneToMany(mappedBy = "jvmProcess", cascade = CascadeType.ALL)
    private List<HeapDump> heapDumps;
}

/*====================================================
  2. HEAP DUMP ENTITY
====================================================*/

@Entity
@Table(name = "heap_dump")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class HeapDump {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String filePath;
    private Long fileSize;
    private String status;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "jvm_process_id")
    private JvmProcess jvmProcess;
}

/*====================================================
  3. MEMORY SNAPSHOT ENTITY
====================================================*/

@Entity
@Table(name = "memory_snapshot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class MemorySnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long heapUsed;
    private long heapCommitted;
    private long heapMax;
    private long nonHeapUsed;
    private long nonHeapCommitted;

    private double cpuUsage;
    private int threadCount;

    private LocalDateTime recordedAt;
}

/*====================================================
  4. GC EVENT ENTITY
====================================================*/

@Entity
@Table(name = "gc_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class GCEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gcName;
    private String action;
    private long duration;
    private LocalDateTime eventTime;
}

/*====================================================
  5. HEAP ANALYSIS ENTITY
====================================================*/

@Entity
@Table(name = "heap_analysis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class HeapAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gcRoot;
    private long retainedMemory;
    private int leakedObjectCount;

    @OneToMany(mappedBy = "heapAnalysis", cascade = CascadeType.ALL)
    private List<ObjectNode> leakedObjects;
}

/*====================================================
  6. OBJECT NODE ENTITY
====================================================*/

@Entity
@Table(name = "object_node")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ObjectNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objectId;
    private String className;
    private long shallowSize;
    private long retainedSize;
    private boolean gcRoot;

    @ManyToOne
    @JoinColumn(name = "analysis_id")
    private HeapAnalysis heapAnalysis;
}

/*====================================================
  7. OBJECT REFERENCE ENTITY
====================================================*/

@Entity
@Table(name = "object_reference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ObjectReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceObject;
    private String targetObject;
}

/*====================================================
  8. USER ENTITY (OPTIONAL)
====================================================*/

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;
}
