async function loadCustomers() {

    try {

        console.log("Loading...");

        const response =
            await fetch("/Banking_Website/data/customers.json");

        console.log(response);

        const customers =
            await response.json();

        localStorage.setItem(
            "customers",
            JSON.stringify(customers)
        );

        console.log(
            "Customers Loaded"
        );

    }
    catch(error) {

        console.error(error);
    }
}

loadCustomers();