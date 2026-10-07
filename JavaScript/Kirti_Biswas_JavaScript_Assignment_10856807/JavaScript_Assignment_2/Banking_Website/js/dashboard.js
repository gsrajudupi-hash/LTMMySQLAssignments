const loggedInUser = sessionStorage.getItem("loggedInUser");

if (!loggedInUser) {
  window.location.href = "login.html";
}

const customers = JSON.parse(localStorage.getItem("customers")) || [];

const customer = customers.find((c) => c.username === loggedInUser);

if (customer) {
  document.getElementById("accountNumber").textContent = customer.accountNumber;

  document.getElementById("balance").textContent = "₹" + customer.deposit;
}
const logoutBtn =
    document.getElementById("logoutBtn");

if (logoutBtn) {

    logoutBtn.addEventListener(
        "click",
        function (event) {

            event.preventDefault();

            sessionStorage.removeItem(
                "loggedInUser"
            );

            window.location.href =
                "login.html";
        }
    );
}