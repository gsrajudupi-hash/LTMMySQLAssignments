// Check login session

const loggedInUser =
    sessionStorage.getItem(
        "loggedInUser"
    );

if (!loggedInUser) {

    window.location.href =
        "login.html";
}

// Get customers from localStorage

const customers =
    JSON.parse(
        localStorage.getItem(
            "customers"
        )
    ) || [];

// Find logged-in customer

const customer =
    customers.find(
        customer =>
            customer.username === loggedInUser
    );

// Display account details

if (customer) {

    document.getElementById(
        "accountNumber"
    ).textContent =
        customer.accountNumber;

    document.getElementById(
        "accountType"
    ).textContent =
        customer.accountType;

    document.getElementById(
        "balance"
    ).textContent =
        customer.deposit;
}

// Show / Hide Balance

const balanceElement =
    document.getElementById(
        "balance"
    );

const toggleButton =
    document.getElementById(
        "toggleBalanceBtn"
    );

let isVisible = true;

toggleButton.addEventListener(
    "click",
    () => {

        if (isVisible) {

            balanceElement.textContent =
                "******";

            toggleButton.textContent =
                "Show Balance";

        } else {

            balanceElement.textContent =
                customer.deposit;

            toggleButton.textContent =
                "Hide Balance";
        }

        isVisible = !isVisible;
    }
);