const checkInBtn = document.getElementById("checkInBtn");
const checkOutBtn = document.getElementById("checkOutBtn");
const statusDisplay = document.getElementById("statusDisplay");
 
checkInBtn.addEventListener("click", () => {
  let time = new Date().toLocaleString();
  statusDisplay.innerText = `Checked In at: ${time}`;
  statusDisplay.style.color = "green";
});
 
checkOutBtn.addEventListener("click", () => {
  let time = new Date().toLocaleString();
  statusDisplay.innerText = `Checked Out at: ${time}`;
  statusDisplay.style.color = "orange";
});
 