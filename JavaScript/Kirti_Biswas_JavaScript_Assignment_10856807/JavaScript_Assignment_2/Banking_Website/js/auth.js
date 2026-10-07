const loginForm =
    document.getElementById("loginForm");

const showPassword =
    document.getElementById("showPassword");

showPassword.addEventListener("change", () => {

    const password =
        document.getElementById("password");

    password.type =
        showPassword.checked
            ? "text"
            : "password";
});

loginForm.addEventListener("submit", (event) => {

    event.preventDefault();

    const username =
        document.getElementById("username").value;

    const password =
        document.getElementById("password").value;

    if(username === "" || password === "")
    {
        alert("Enter Username and Password");
        return;
    }

    sessionStorage.setItem(
        "loggedInUser",
        username
    );

    window.location.href =
        "dashboard.html";

});

const registerForm =
    document.getElementById("registerForm");

if(registerForm){

    registerForm.addEventListener(
        "submit",
        function(event){

            event.preventDefault();

            const customerId =
                document.getElementById("customerId").value;

            const firstName =
                document.getElementById("firstName").value;

            const lastName =
                document.getElementById("lastName").value;

            const email =
                document.getElementById("email").value;

            const mobile =
                document.getElementById("mobile").value;

            const accountType =
                document.getElementById("accountType").value;

            const deposit =
                document.getElementById("deposit").value;

            const username =
                document.getElementById("username").value;

            const password =
                document.getElementById("password").value;

            const confirmPassword =
                document.getElementById("confirmPassword").value;

            if(password !== confirmPassword){
                alert("Passwords do not match");
                return;
            }

            let customers =
                JSON.parse(
                    localStorage.getItem("customers")
                ) || [];

            const accountNumber =
                "SB" + (100001 + customers.length);

            const customer = {

                customerId,
                firstName,
                lastName,
                email,
                mobile,
                accountType,
                deposit,
                accountNumber,
                username,
                password

            };

            customers.push(customer);

            localStorage.setItem(
                "customers",
                JSON.stringify(customers)
            );

            alert(
                "Registration Successful\nAccount Number : "
                + accountNumber
            );

            window.location.href =
                "login.html";

        }
    );

}