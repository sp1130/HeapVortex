import { useState, useEffect } from "react";

const useMemoryData = () => {
  const [memoryData, setMemoryData] = useState({
    usedMemory: 512,
    totalMemory: 1536,
    maxMemory: 2048,
    freeMemory: 1024,
    leakStatus: "Normal",
  });

  useEffect(() => {
    const interval = setInterval(() => {
      setMemoryData((prev) => {
        const increase = Math.floor(Math.random() * 10);
        const newUsed = Math.min(prev.usedMemory + increase, prev.maxMemory);
        const newFree = Math.max(prev.totalMemory - newUsed, 0);

        let status = "Normal";
        if (newUsed > prev.totalMemory * 0.8) {
          status = "Warning";
        }
        if (newUsed > prev.totalMemory * 0.95) {
          status = "Memory Leak Suspected";
        }

        return {
          ...prev,
          usedMemory: newUsed,
          freeMemory: newFree,
          leakStatus: status,
        };
      });
    }, 3000);

    return () => clearInterval(interval);
  }, []);

  return memoryData;
};

export default useMemoryData;
