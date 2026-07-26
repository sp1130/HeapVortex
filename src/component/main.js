// ===============================
// main.js
// ===============================

// Wait until the page is fully loaded
document.addEventListener("DOMContentLoaded", () => {
    console.log("Application Started Successfully!");

    // Navigation Buttons
    const buttons = document.querySelectorAll(".nav-btn");

    buttons.forEach(button => {
        button.addEventListener("click", () => {
            buttons.forEach(btn => btn.classList.remove("active"));
            button.classList.add("active");

            const page = button.getAttribute("data-page");
            showPage(page);
        });
    });

    // Load Home Page by default
    showPage("home");
});

// Function to Display Pages
function showPage(page) {
    const content = document.getElementById("content");

    switch (page) {
        case "home":
            content.innerHTML = `
                <h1>Welcome to HeapVortex</h1>
                <p>Visualize and understand Heap Data Structures with interactive animations.</p>
            `;
            break;

        case "about":
            content.innerHTML = `
                <h1>About</h1>
                <p>HeapVortex is a learning platform that helps students understand Min Heap, Max Heap, Insertion, Deletion, and Heapify operations.</p>
            `;
            break;

        case "services":
            content.innerHTML = `
                <h1>Features</h1>
                <ul>
                    <li>Min Heap Visualization</li>
                    <li>Max Heap Visualization</li>
                    <li>Heap Insert</li>
                    <li>Heap Delete</li>
                    <li>Heap Sort Animation</li>
                </ul>
            `;
            break;

        case "contact":
            content.innerHTML = `
                <h1>Contact</h1>
                <p>Email: support@heapvortex.com</p>
                <p>Phone: +91 9876543210</p>
            `;
            break;

        default:
            content.innerHTML = `
                <h1>404</h1>
                <p>Page Not Found</p>
            `;
    }
}

// Example Utility Functions
function showMessage(message) {
    alert(message);
}

function getCurrentDate() {
    const today = new Date();
    return today.toLocaleDateString();
}

console.log("Today's Date:", getCurrentDate());

// Button Example
const clickBtn = document.getElementById("clickBtn");

if (clickBtn) {
    clickBtn.addEventListener("click", () => {
        showMessage("Welcome to HeapVortex!");
    });
}
