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
