const loggedInUser =
    sessionStorage.getItem(
        "loggedInUser"
    );

if(!loggedInUser){

    window.location.href =
        "login.html";
}

const customers =
    JSON.parse(
        localStorage.getItem(
            "customers"
        )
    ) || [];

const customer =
    customers.find(
        customer =>
            customer.username ===
            loggedInUser
    );

if(customer){

    document.getElementById(
        "firstName"
    ).value =
        customer.firstName || "";

    document.getElementById(
        "lastName"
    ).value =
        customer.lastName || "";

    document.getElementById(
        "email"
    ).value =
        customer.email || "";

    document.getElementById(
        "mobile"
    ).value =
        customer.mobile || "";

    document.getElementById(
        "address"
    ).value =
        customer.address || "";

    document.getElementById(
        "city"
    ).value =
        customer.city || "";

    document.getElementById(
        "state"
    ).value =
        customer.state || "";

    document.getElementById(
        "pinCode"
    ).value =
        customer.pinCode || "";
}

document.getElementById(
    "profileForm"
).addEventListener(
    "submit",
    function(event){

        event.preventDefault();

        const mobile =
            document.getElementById(
                "mobile"
            ).value;

        const pinCode =
            document.getElementById(
                "pinCode"
            ).value;

        if(!/^[0-9]{10}$/.test(mobile)){

            alert(
                "Enter valid Mobile Number"
            );

            return;
        }

        if(!/^[0-9]{6}$/.test(pinCode)){

            alert(
                "Enter valid PIN Code"
            );

            return;
        }

        customer.firstName =
            document.getElementById(
                "firstName"
            ).value;

        customer.lastName =
            document.getElementById(
                "lastName"
            ).value;

        customer.email =
            document.getElementById(
                "email"
            ).value;

        customer.mobile =
            mobile;

        customer.address =
            document.getElementById(
                "address"
            ).value;

        customer.city =
            document.getElementById(
                "city"
            ).value;

        customer.state =
            document.getElementById(
                "state"
            ).value;

        customer.pinCode =
            pinCode;

        localStorage.setItem(
            "customers",
            JSON.stringify(customers)
        );

        alert(
            "Profile Updated Successfully"
        );
    }
);