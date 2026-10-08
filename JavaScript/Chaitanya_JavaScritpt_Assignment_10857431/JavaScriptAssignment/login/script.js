let loginButton = document.getElementById("loginButton");
 
loginButton.addEventListener("click", login);
 
function login() {
 
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
 
    if (username === "admin" && password === "admin123") {
 
        document.getElementById("message").textContent =
            "Welcome, " + username;
 
        document.getElementById("message").style.color = "green";
 
    } else {
 
        document.getElementById("message").textContent =
            "Invalid username or password";
 
        document.getElementById("message").style.color = "red";
    }
}