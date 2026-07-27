import React from "react";

const Dashboard = () => {
  return (
    <div style={{ padding: "30px", fontFamily: "Arial, sans-serif" }}>
      <h1>HeapVortex Dashboard</h1>

      <div
        style={{
          display: "flex",
          gap: "20px",
          marginTop: "20px",
          flexWrap: "wrap",
        }}
      >
        <div style={cardStyle}>
          <h3>Used Memory</h3>
          <p>512 MB</p>
        </div>

        <div style={cardStyle}>
          <h3>Free Memory</h3>
          <p>1024 MB</p>
        </div>

        <div style={cardStyle}>
          <h3>Total Memory</h3>
          <p>1536 MB</p>
        </div>

        <div style={cardStyle}>
          <h3>Max Memory</h3>
          <p>2048 MB</p>
        </div>
      </div>

      <div style={{ marginTop: "30px" }}>
        <h2>Leak Status</h2>
        <p style={{ color: "green", fontWeight: "bold" }}>Normal</p>
      </div>
    </div>
  );
};

const cardStyle = {
  width: "200px",
  padding: "20px",
  borderRadius: "10px",
  boxShadow: "0 2px 8px rgba(0,0,0,0.2)",
  textAlign: "center",
  backgroundColor: "#ffffff",
};

export default Dashboard;
