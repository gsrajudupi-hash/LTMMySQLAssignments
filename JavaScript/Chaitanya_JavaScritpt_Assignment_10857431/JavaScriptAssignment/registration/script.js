document.getElementById("registrationForm")
    .addEventListener("submit", function(event) {
 
        event.preventDefault();
 
        let name = document.getElementById("name").value.trim();
        let email = document.getElementById("email").value.trim();
        let mobile = document.getElementById("mobile").value.trim();
        let password = document.getElementById("password").value;
        let confirmPassword = document.getElementById("confirmPassword").value;
 
        let valid = true;
 
        document.getElementById("nameError").textContent = "";
        document.getElementById("emailError").textContent = "";
        document.getElementById("mobileError").textContent = "";
        document.getElementById("passwordError").textContent = "";
        document.getElementById("confirmPasswordError").textContent = "";
        document.getElementById("success").textContent = "";
 
        if (name === "") {
            document.getElementById("nameError").textContent =
                "Name is required";
            valid = false;
        }
 
        let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
 
        if (!emailPattern.test(email)) {
            document.getElementById("emailError").textContent =
                "Enter a valid email";
            valid = false;
        }
 
        let mobilePattern = /^\d{10}$/;
 
        if (!mobilePattern.test(mobile)) {
            document.getElementById("mobileError").textContent =
                "Mobile must contain exactly 10 digits";
            valid = false;
        }
 
        if (password.length < 8) {
            document.getElementById("passwordError").textContent =
                "Password must contain at least 8 characters";
            valid = false;
        }
 
        if (password !== confirmPassword) {
            document.getElementById("confirmPasswordError").textContent =
                "Passwords do not match";
            valid = false;
        }
 
        if (valid) {
            document.getElementById("success").textContent =
                "Registration successful!";
        }
    });