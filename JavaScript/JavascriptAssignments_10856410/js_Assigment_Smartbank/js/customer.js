
import { currentCustomer, read, write, KEYS } from "./storage.js";
import { notify } from "./app.js";

const customer = currentCustomer();
const form = document.querySelector("#profileForm");
const fill = () => {
  if (!customer) return;
  ["firstName","lastName","email","mobile","address","city","state","pin"].forEach(key => form.elements[key].value = customer[key] || "");
  const name = `${customer.firstName} ${customer.lastName}`;
  const initials = `${customer.firstName?.[0]||""}${customer.lastName?.[0]||""}`.toUpperCase();
  document.querySelector("#profileName").textContent=name;
  document.querySelector("#profileCustomerId").textContent=`${customer.customerId} · SmartBank customer`;
  document.querySelector("#profileAvatar").textContent=initials;
  document.querySelector("#detailCustomerId").textContent=customer.customerId;
  document.querySelector("#detailUsername").textContent=customer.username;
  document.querySelector("#detailAccountType").textContent=customer.accountType || "Savings";
};
form?.addEventListener("submit",e=>{
  e.preventDefault();
  const data=Object.fromEntries(new FormData(form).entries());
  if(!/^\S+@\S+\.\S+$/.test(data.email)){notify("Enter a valid email.","error");return}
  if(!/^\d{10}$/.test(data.mobile)){notify("Enter a valid 10-digit mobile number.","error");return}
  const customers=read(KEYS.customers,[]);
  const index=customers.findIndex(c=>c.customerId===customer.customerId);
  customers[index]={...customers[index],...data};
  write(KEYS.customers,customers);
  Object.assign(customer,data);
  fill();
  notify("Profile updated successfully.");
});
document.querySelector("#resetProfile")?.addEventListener("click",fill);
fill();
