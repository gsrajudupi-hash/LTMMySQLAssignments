const services = [

    "Savings Account",
    "Current Account",
    "Fixed Deposit",
    "Personal Loan",
    "Home Loan",
    "Education Loan",
    "Credit Card",
    "Internet Banking"

];

const serviceContainer =
    document.getElementById("serviceContainer");

services.forEach(service => {

    const card =
        document.createElement("div");

    card.classList.add("service-card");

    card.innerHTML = `
        <h3>${service}</h3>
        <p>
          Best banking solution for customers.
        </p>
    `;

    serviceContainer.appendChild(card);

});