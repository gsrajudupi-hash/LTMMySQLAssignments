import { $, money, notify, formValues } from "./ui.js";

const RATES = { Personal: 10.5, Home: 8.5, Car: 9, Education: 9.5 };
// EMI = P × R × (1+R)^N / ((1+R)^N − 1)
export function calcEmi(principal, annualRate, years) {
  const R = annualRate / 12 / 100,
    N = years * 12;
  const emi =
    R === 0
      ? principal / N
      : (principal * R * (1 + R) ** N) / ((1 + R) ** N - 1);
  return { emi, total: emi * N, interest: emi * N - principal };
}
export function initLoan() {
  const form = $("#loan-form");
  const setRate = () =>
    (form.elements.rate.value = RATES[form.elements.loanType.value]);
  setRate();
  form.elements.loanType.addEventListener("change", setRate);
  form.addEventListener("submit", (e) => {
    e.preventDefault();
    const { amount, rate, years } = formValues(form);
    const [P, r, y] = [Number(amount), Number(rate), Number(years)];
    if (!(P > 0) || !(r >= 0 && r <= 40) || !(y >= 1 && y <= 30))
      return notify(
        "Enter amount > 0, rate 0–40% and tenure 1–30 years",
        "warning",
      );
    const { emi, total, interest } = calcEmi(P, r, y);
    $("#result").innerHTML =
      `<div class="grid"><div class="card stat"><span>Monthly EMI</span><strong>${money(emi)}</strong></div>
      <div class="card stat"><span>Total interest</span><strong>${money(interest)}</strong></div>
      <div class="card stat"><span>Total payable</span><strong>${money(total)}</strong></div></div>`;
  });
}
