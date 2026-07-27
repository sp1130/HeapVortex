# MemoryCard Component

## Overview
The MemoryCard component is a reusable React UI component used in the HeapVortex dashboard. It displays JVM memory statistics in a clean card layout.

## Purpose
This component helps present important memory information such as:
- Used Memory
- Free Memory
- Total Memory
- Max Memory

## Props

| Prop | Description |
|------|-------------|
| title | Memory type (Used Memory, Free Memory, etc.) |
| value | Memory value |
| unit | Unit of measurement (MB, GB) |

## Example

```jsx
<MemoryCard
    title="Used Memory"
    value="512"
    unit="MB"
/>



# Dashboard Page

## Overview
The Dashboard page is the main interface of the HeapVortex application. It provides a quick overview of JVM heap memory statistics and memory leak status.

## Features
- Displays Used Memory
- Displays Free Memory
- Displays Total Memory
- Displays Maximum Memory
- Shows current Leak Status
- Simple and responsive layout

## Purpose
The Dashboard helps users monitor JVM memory usage in real time through a clean and user-friendly interface.

## Future Enhancements
- Live memory updates from backend APIs
- Interactive charts
- 3D Heap Visualization
- Garbage Collection statistics
- Memory leak alerts
