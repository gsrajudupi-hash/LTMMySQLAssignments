import { StorageManager } from "./storage.js";
 
export const AuthManager = {
  // Handle customer login
  login(username, password) {
    // Basic validation check (can be enhanced to check against registered customers in localStorage)
    if (username && password.length >= 6) {
      StorageManager.setLoggedInUser(username);
      return { success: true, message: "Login successful!" };
    }
    return { success: false, message: "Invalid username or password (minimum 6 characters)." };
  },
 
  // Check if a user is logged in (protects dashboard and banking pages)
  protectRoute() {
    const loggedInUser = StorageManager.getLoggedInUser();
    if (!loggedInUser) {
      // Redirect to login page if user is not authenticated
      window.location.href = "login.html";
    }
  },
 
  // Handle user logout
  logout() {
    StorageManager.clearSession(); // Removes 'loggedInUser' from sessionStorage[span_4](start_span)[span_4](end_span)
    window.location.href = "login.html"; // Redirect to login page[span_5](start_span)[span_5](end_span)
  }
};
 
// Event listener configuration for login form if present on the page
document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("login-form");
  if (loginForm) {
    loginForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const usernameInput = document.getElementById("login-username").value;
      const passwordInput = document.getElementById("login-password").value;
 
      const result = AuthManager.login(usernameInput, passwordInput);
      if (result.success) {
        window.location.href = "dashboard.html"; // Redirect after successful login[span_6](start_span)[span_6](end_span)
      } else {
        alert(result.message);
      }
    });
  }
 
  // Event listener configuration for logout button if present on the page
  const logoutBtn = document.getElementById("logout-btn");
  if (logoutBtn) {
    logoutBtn.addEventListener("click", (e) => {
      e.preventDefault();
      AuthManager.logout();
    });
  }
});