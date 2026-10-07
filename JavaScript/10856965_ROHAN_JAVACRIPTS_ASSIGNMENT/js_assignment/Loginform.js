// Toggle Password Visibility
document.getElementById("showPass").addEventListener("change", function() {
  let passField = document.getElementById("loginPassword");
  passField.type = this.checked ? "text" : "password";
});
 
// Listen for Enter key
document.getElementById("loginForm").addEventListener("keypress", function(e) {
  if (e.key === "Enter") {
    e.preventDefault();
    loginUser();
  }
});
 
function loginUser() {
  let user = document.getElementById("username").value;
  let pass = document.getElementById("loginPassword").value;
  let msg = document.getElementById("loginMsg");
 
  if (user === "admin" && pass === "123456") {
    msg.innerText = "Welcome, Admin!";
    msg.style.color = "green";
  } else {
    msg.innerText = "Error: Invalid credentials!";
    msg.style.color = "red";
  }
}