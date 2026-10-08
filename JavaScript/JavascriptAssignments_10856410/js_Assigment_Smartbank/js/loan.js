
import { formatINR } from "./storage.js";
// Loan calculation script
const amount = document.querySelector("#loanAmount");
const rate = document.querySelector("#interestRate");
const tenure = document.querySelector("#loanTenure");
const amountOutput = document.querySelector("#loanAmountOutput");
const calculate = () => {
  const P = Number(amount.value);
  const annualRate = Number(rate.value);
  const N = Number(tenure.value) * 12;
  const R = annualRate / 12 / 100;
  const emi = R === 0 ? P / N : P * R * Math.pow(1+R,N) / (Math.pow(1+R,N)-1);
  const total = emi * N;
  const interest = total - P;
  amountOutput.value = formatINR(P,true);
  amountOutput.textContent = formatINR(P,true);
  document.querySelector("#emiValue").textContent = formatINR(emi);
  document.querySelector("#principalValue").textContent = formatINR(P);
  document.querySelector("#interestValue").textContent = formatINR(interest);
  document.querySelector("#totalValue").textContent = formatINR(total);
  document.querySelector("#principalPercent").textContent = `${Math.round(P/total*100)}%`;
};
[amount,rate,tenure].forEach(el=>el?.addEventListener("input",calculate));
document.querySelectorAll(".loan-type").forEach(button => button.addEventListener("click",()=>{
  document.querySelectorAll(".loan-type").forEach(b=>b.classList.remove("active"));
  button.classList.add("active");
  document.querySelector("#loanType").value=button.dataset.type;
  const rates={"Personal Loan":10.5,"Home Loan":8.5,"Car Loan":9.2,"Education Loan":7.5};
  rate.value=rates[button.dataset.type];
  calculate();
}));
document.querySelector("#loanForm")?.addEventListener("submit",e=>{e.preventDefault();calculate()});
calculate();
