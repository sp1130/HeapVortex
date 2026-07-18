// =============================================
// main.js
// Entry point of the React Application
// =============================================
import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";

import App from "./App";
import "./index.css";

// Create the React root and render the application
const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
);

// =============================================
// Application Information
// =============================================

console.log("======================================");
console.log("🚀 Full Stack Web Application Started");
console.log("Version : 1.0.0");
console.log("Environment : Development");
console.log("Framework : React + Vite");
console.log("======================================");
