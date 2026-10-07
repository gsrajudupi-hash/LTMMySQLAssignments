const transferForm =
    document.getElementById(
        "transferForm"
    );

transferForm.addEventListener(
    "submit",
    function (event) {

        event.preventDefault();

        const loggedInUser =
            sessionStorage.getItem(
                "loggedInUser"
            );

        const customers =
            JSON.parse(
                localStorage.getItem(
                    "customers"
                )
            ) || [];

        const customer =
            customers.find(
                c =>
                c.username === loggedInUser
            );

        if (!customer) {

            alert(
                "Customer not found. Please login again."
            );

            return;
        }

        const beneficiaryAccount =
            document.getElementById(
                "beneficiaryAccount"
            ).value;

        const beneficiaryName =
            document.getElementById(
                "beneficiaryName"
            ).value;

        const amount =
            Number(
                document.getElementById(
                    "amount"
                ).value
            );

        const transferType =
            document.getElementById(
                "transferType"
            ).value;

        const remarks =
            document.getElementById(
                "remarks"
            ).value;

        if (amount <= 0) {

            alert(
                "Enter valid amount"
            );

            return;
        }

        if (amount > customer.deposit) {

            alert(
                "Insufficient Balance"
            );

            return;
        }

        customer.deposit =
            Number(customer.deposit) - amount;

        localStorage.setItem(
            "customers",
            JSON.stringify(
                customers
            )
        );

        const transactions =
            JSON.parse(
                localStorage.getItem(
                    "transactions"
                )
            ) || [];

        const transaction = {

            transactionId:
                "TXN" + Date.now(),

            accountNumber:
                customer.accountNumber,

            type:
                "Transfer",

            description:
                "Transfer To " +
                beneficiaryName,

            amount:
                amount,

            date:
                new Date()
                    .toISOString()
                    .split("T")[0],

            balance:
                customer.deposit,

            beneficiaryAccount:
                beneficiaryAccount,

            beneficiaryName:
                beneficiaryName,

            transferType:
                transferType,

            remarks:
                remarks
        };

        transactions.push(
            transaction
        );

        localStorage.setItem(
            "transactions",
            JSON.stringify(
                transactions
            )
        );

        console.log(
            JSON.parse(
                localStorage.getItem(
                    "transactions"
                )
            )
        );

        alert(
            "Transfer Successful"
        );

        transferForm.reset();
    }
);