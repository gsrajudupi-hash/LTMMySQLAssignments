export function calculateEMI(principal, annualRate, tenureMonths) {
  const monthlyRate = annualRate / 12 / 100;
  const powFactor = Math.pow(1 + monthlyRate, tenureMonths);
  const emi = (principal * monthlyRate * powFactor) / (powFactor - 1);
  
  const totalAmountPayable = emi * tenureMonths;
  const totalInterest = totalAmountPayable - principal;
 
  return {
    emi: emi.toFixed(2),
    totalInterest: totalInterest.toFixed(2),
    totalAmountPayable: totalAmountPayable.toFixed(2)
  };
}