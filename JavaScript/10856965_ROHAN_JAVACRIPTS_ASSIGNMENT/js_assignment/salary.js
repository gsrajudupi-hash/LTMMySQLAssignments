function calculateSalary(e) {
  e.preventDefault();
  let name = document.getElementById("empName").value;
  let basic = parseFloat(document.getElementById("basicSalary").value) || 0;
 
  let hra = basic * 0.20;
  let da = basic * 0.15;
  let pf = basic * 0.12;
  let profTax = 200;
 
  let grossSalary = basic + hra + da;
  let totalDeductions = pf + profTax;
  let netSalary = grossSalary - totalDeductions;
 
  let slipHTML = `
    <h3>Salary Slip for ${name}</h3>
    <p>Basic Salary: $${basic.toFixed(2)}</p>
    <p>HRA (20%): $${hra.toFixed(2)}</p>
    <p>DA (15%): $${da.toFixed(2)}</p>
    <p><strong>Gross Salary: $${grossSalary.toFixed(2)}</strong></p>
    <hr>
    <p>PF (12%): $${pf.toFixed(2)}</p>
    <p>Professional Tax: $${profTax.toFixed(2)}</p>
    <p><strong>Total Deductions: $${totalDeductions.toFixed(2)}</strong></p>
    <hr>
    <p><h2>Net Salary: $${netSalary.toFixed(2)}</h2></p>
  `;
 
  document.getElementById("salarySlip").innerHTML = slipHTML;
}