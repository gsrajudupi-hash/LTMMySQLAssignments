export const setupLoanCalculator = () => {
  const form = document.getElementById("loan-form");
  if (!form) return;

  form.addEventListener("submit", event => {
    event.preventDefault();
    const loanType = document.getElementById("loanType").value;
    const principal = Number(document.getElementById("loanAmount").value);
    const annualRate = Number(document.getElementById("interestRate").value);
    const years = Number(document.getElementById("loanTenure").value);
    const months = years * 12;
    const monthlyRate = annualRate / 12 / 100;

    if (principal <= 0 || annualRate < 0 || years <= 0) return;

    const emi = monthlyRate === 0
      ? principal / months
      : principal * monthlyRate * Math.pow(1 + monthlyRate, months) /
        (Math.pow(1 + monthlyRate, months) - 1);

    const totalAmount = emi * months;
    const totalInterest = totalAmount - principal;
    const format = value => `₹${value.toLocaleString("en-IN", { maximumFractionDigits: 2 })}`;

    document.getElementById("loan-result").innerHTML = `
      <div><b>Loan:</b> ${loanType}</div>
      <div><b>Monthly EMI:</b> ${format(emi)}</div>
      <div><b>Total Interest:</b> ${format(totalInterest)}</div>
      <div><b>Total Amount Payable:</b> ${format(totalAmount)}</div>
    `;
  });
};
