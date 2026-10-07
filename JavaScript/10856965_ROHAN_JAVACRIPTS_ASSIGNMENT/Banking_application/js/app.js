import { loadCustomerData } from "./api.js";
 
document.addEventListener("DOMContentLoaded", async () => {
  const services = [
    "Savings Account", "Current Account", "Fixed Deposit", 
    "Personal Loan", "Home Loan", "Education Loan", 
    "Credit Card", "Internet Banking"
  ];
 
  const container = document.getElementById("service-cards-container");
  if (container) {
    services.forEach(service => {
      const card = document.createElement("div");
      card.className = "card";
      card.innerHTML = `<h3>${service}</h3><p>Explore our fast and reliable ${service.toLowerCase()} solutions.</p>`;
      container.appendChild(card);
    });
  }
 
  // Load external sample JSON data using async/await Fetch API
  const customerData = await loadCustomerData();
  console.log("Loaded initial customers:", customerData);
});
 