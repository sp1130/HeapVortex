// ===============================
// utils.js
// Common Utility Functions
// ===============================

// API Base URL
export const API_BASE_URL = "http://localhost:5000/api";

// API Request
export const apiRequest = async (endpoint, method = "GET", data = null) => {
  const options = {
    method,
    headers: {
      "Content-Type": "application/json",
    },
  };

  if (data) {
    options.body = JSON.stringify(data);
  }

  try {
    const response = await fetch(`${API_BASE_URL}${endpoint}`, options);

    if (!response.ok) {
      throw new Error("Request Failed");
    }

    return await response.json();
  } catch (error) {
    console.error("API Error:", error);
    throw error;
  }
};

// Capitalize Text
export const capitalize = (text) => {
  if (!text) return "";
  return text.charAt(0).toUpperCase() + text.slice(1);
};

// Format Date
export const formatDate = (date) => {
  return new Date(date).toLocaleDateString();
};

// Truncate Long Text
export const truncateText = (text, length = 50) => {
  if (!text) return "";
  return text.length > length
    ? text.substring(0, length) + "..."
    : text;
};

// Email Validation
export const isEmail = (email) => {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
};

// Phone Validation
export const isPhone = (phone) => {
  return /^[6-9]\d{9}$/.test(phone);
};

// Password Validation
export const isPasswordStrong = (password) => {
  return password.length >= 8;
};

// Local Storage Functions
export const saveToStorage = (key, value) => {
  localStorage.setItem(key, JSON.stringify(value));
};

export const getFromStorage = (key) => {
  const value = localStorage.getItem(key);
  return value ? JSON.parse(value) : null;
};

export const removeFromStorage = (key) => {
  localStorage.removeItem(key);
};

export const clearStorage = () => {
  localStorage.clear();
};

// Random ID Generator
export const generateId = () => {
  return Math.random().toString(36).substring(2, 10);
};

// Delay Function
export const delay = (ms) => {
  return new Promise((resolve) => setTimeout(resolve, ms));
};

// Constants
export const APP_NAME = "Frontend Project";

export const ROUTES = {
  HOME: "/",
  LOGIN: "/login",
  DASHBOARD: "/dashboard",
};

export const STATUS = {
  SUCCESS: "success",
  ERROR: "error",
  LOADING: "loading",
};
