import React from "react";

const MemoryCard = ({ title, value, unit }) => {
  return (
    <div
      style={{
        width: "220px",
        padding: "20px",
        borderRadius: "12px",
        backgroundColor: "#ffffff",
        boxShadow: "0 4px 10px rgba(0,0,0,0.15)",
        textAlign: "center",
        margin: "10px",
      }}
    >
      <h3 style={{ color: "#555", marginBottom: "10px" }}>{title}</h3>
      <h2 style={{ color: "#2c7be5", marginBottom: "5px" }}>{value}</h2>
      <p style={{ color: "gray" }}>{unit}</p>
    </div>
  );
};

export default MemoryCard;
