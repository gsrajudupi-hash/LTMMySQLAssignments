// Check whether the customer is logged in

const loggedInUser =
    sessionStorage.getItem("loggedInUser");

if (!loggedInUser) {

    window.location.href =
        "login.html";
}


// Get customers from localStorage

const customers =
    JSON.parse(
        localStorage.getItem("customers")
    ) || [];


// Find the logged-in customer

const currentCustomer =
    customers.find(
        customer =>
            customer.username === loggedInUser
    );


// Get all transactions

const allTransactions =
    JSON.parse(
        localStorage.getItem("transactions")
    ) || [];


// Get transactions of current customer

let customerTransactions =
    allTransactions.filter(
        transaction =>
            transaction.accountNumber ===
            currentCustomer.accountNumber
    );


// Access HTML elements

const tableBody =
    document.getElementById(
        "transactionTableBody"
    );

const searchInput =
    document.getElementById(
        "searchTransaction"
    );

const typeFilter =
    document.getElementById(
        "typeFilter"
    );

const dateFilter =
    document.getElementById(
        "dateFilter"
    );

const sortTransaction =
    document.getElementById(
        "sortTransaction"
    );

const clearFilterBtn =
    document.getElementById(
        "clearFilterBtn"
    );

const transactionMessage =
    document.getElementById(
        "transactionMessage"
    );


// Format amount as Indian currency

const formatCurrency = amount => {

    return new Intl.NumberFormat(
        "en-IN",
        {
            style: "currency",
            currency: "INR"
        }
    ).format(amount);
};


// Display transactions

const displayTransactions =
    (transactions = customerTransactions) => {

        tableBody.innerHTML = "";

        if (transactions.length === 0) {

            transactionMessage.textContent =
                "No transactions found.";

            return;
        }

        transactionMessage.textContent = "";

        // map() creates the HTML rows

        tableBody.innerHTML =
            transactions.map(
                transaction => {

                    const typeClass =
                        transaction.type.toLowerCase();

                    return `
                        <tr>
                            <td>
                                ${transaction.date}
                            </td>

                            <td>
                                ${transaction.transactionId}
                            </td>

                            <td>
                                <span class="transaction-type ${typeClass}">
                                    ${transaction.type}
                                </span>
                            </td>

                            <td>
                                ${transaction.description}
                            </td>

                            <td>
                                ${formatCurrency(
                                    Number(transaction.amount)
                                )}
                            </td>

                            <td>
                                ${formatCurrency(
                                    Number(transaction.balance)
                                )}
                            </td>
                        </tr>
                    `;
                }
            ).join("");
    };


// Calculate transaction summary using reduce()

const calculateSummary = () => {

    const summary =
        customerTransactions.reduce(
            (result, transaction) => {

                const amount =
                    Number(transaction.amount);

                if (transaction.type === "Credit") {

                    result.totalCredits += amount;
                }

                if (transaction.type === "Debit") {

                    result.totalDebits += amount;
                }

                if (transaction.type === "Transfer") {

                    result.totalTransfers += amount;
                }

                result.totalAmount += amount;

                return result;
            },
            {
                totalCredits: 0,
                totalDebits: 0,
                totalTransfers: 0,
                totalAmount: 0
            }
        );

    const average =
        customerTransactions.length > 0
            ? summary.totalAmount /
              customerTransactions.length
            : 0;

    document.getElementById(
        "totalCredits"
    ).textContent =
        formatCurrency(summary.totalCredits);

    document.getElementById(
        "totalDebits"
    ).textContent =
        formatCurrency(summary.totalDebits);

    document.getElementById(
        "totalTransfers"
    ).textContent =
        formatCurrency(summary.totalTransfers);

    document.getElementById(
        "averageTransaction"
    ).textContent =
        formatCurrency(average);
};


// Search, filter and sort transactions

const applyFilters = () => {

    const searchValue =
        searchInput.value
            .trim()
            .toLowerCase();

    const selectedType =
        typeFilter.value;

    const selectedDate =
        dateFilter.value;

    const selectedSort =
        sortTransaction.value;


    // filter() filters matching transactions

    let filteredTransactions =
        customerTransactions.filter(
            transaction => {

                const matchesId =
                    transaction.transactionId
                        .toLowerCase()
                        .includes(searchValue);

                const matchesType =
                    selectedType === "All" ||
                    transaction.type === selectedType;

                const matchesDate =
                    selectedDate === "" ||
                    transaction.date === selectedDate;

                return (
                    matchesId &&
                    matchesType &&
                    matchesDate
                );
            }
        );


    // Spread operator prevents changing original array

    filteredTransactions = [
        ...filteredTransactions
    ];


    // sort() sorts the transaction result

    filteredTransactions.sort(
        (firstTransaction, secondTransaction) => {

            if (selectedSort === "oldest") {

                return new Date(
                    firstTransaction.date
                ) - new Date(
                    secondTransaction.date
                );
            }

            if (selectedSort === "highest") {

                return Number(
                    secondTransaction.amount
                ) - Number(
                    firstTransaction.amount
                );
            }

            if (selectedSort === "lowest") {

                return Number(
                    firstTransaction.amount
                ) - Number(
                    secondTransaction.amount
                );
            }

            return new Date(
                secondTransaction.date
            ) - new Date(
                firstTransaction.date
            );
        }
    );

    displayTransactions(
        filteredTransactions
    );
};


// Search event

searchInput.addEventListener(
    "input",
    applyFilters
);


// Transaction type filter event

typeFilter.addEventListener(
    "change",
    applyFilters
);


// Date filter event

dateFilter.addEventListener(
    "change",
    applyFilters
);


// Sorting event

sortTransaction.addEventListener(
    "change",
    applyFilters
);


// Clear all filters

clearFilterBtn.addEventListener(
    "click",
    () => {

        searchInput.value = "";

        typeFilter.value = "All";

        dateFilter.value = "";

        sortTransaction.value =
            "newest";

        applyFilters();
    }
);


// Logout functionality

const logoutBtn =
    document.getElementById(
        "logoutBtn"
    );

logoutBtn.addEventListener(
    "click",
    () => {

        sessionStorage.removeItem(
            "loggedInUser"
        );

        window.location.href =
            "login.html";
    }
);


// Initial display

calculateSummary();

applyFilters();