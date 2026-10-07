function validateForm(event) {
  event.preventDefault(); // Prevent submission by default
  let isValid = true;
 
  let name = document.getElementById("name").value.trim();
  let email = document.getElementById("email").value.trim();
  let mobile = document.getElementById("mobile").value.trim();
  let password = document.getElementById("password").value;
  let confirmPass = document.getElementById("confirmPassword").value;
 
  // Name check
  if (name === "") {
    document.getElementById("nameError").innerText = "Name cannot be empty";
    isValid = false;
  }
 
  // Email RegEx check
  let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    document.getElementById("emailError").innerText = "Invalid email format";
    isValid = false;
  }
 
  // Mobile check (10 digits)
  let mobileRegex = /^\d{10}$/;
  if (!mobileRegex.test(mobile)) {
    document.getElementById("mobileError").innerText = "Mobile must be 10 digits";
    isValid = false;
  }
 
  // Password length check
  if (password.length < 8) {
    document.getElementById("passError").innerText = "Password must be at least 8 characters";
    isValid = false;
  }
 
  // Confirm password match
  if (password !== confirmPass) {
    document.getElementById("confirmError").innerText = "Passwords do not match";
    isValid = false;
  }
 
  if (isValid) {
    alert("Form submitted successfully!");
    // document.getElementById("regForm").submit();
  }
}