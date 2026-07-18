import { useState, useEffect } from "react";

const useMemoryData = () => {
  const [memoryData, setMemoryData] = useState({
    usedMemory: 512,
    freeMemory: 1024,
    totalMemory: 1536,
    maxMemory: 2048,
    leakStatus: "Normal",
  });

  useEffect(() => {
    const interval = setInterval(() => {
      setMemoryData((prev) => ({
        ...prev,
        usedMemory: prev.usedMemory + Math.floor(Math.random() * 5),
        freeMemory: prev.freeMemory - Math.floor(Math.random() * 5),
      }));
    }, 3000);

    return () => clearInterval(interval);
  }, []);

  return memoryData;
};

export default useMemoryData;
