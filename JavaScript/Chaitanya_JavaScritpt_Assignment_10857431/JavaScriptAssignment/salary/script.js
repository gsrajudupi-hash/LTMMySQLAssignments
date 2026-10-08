document.getElementById("calculate")
    .addEventListener("click", function() {
 
        let employeeName =
            document.getElementById("employeeName").value;
 
        let basicSalary =
            Number(document.getElementById("basicSalary").value);
 
        let hra = basicSalary * 0.20;
 
        let da = basicSalary * 0.15;
 
        let grossSalary =
            basicSalary + hra + da;
 
        let pf = basicSalary * 0.12;
 
        let professionalTax = 200;
 
        let totalDeductions =
            pf + professionalTax;
 
        let netSalary =
            grossSalary - totalDeductions;
 
        document.getElementById("salarySlip").innerHTML =
            "<h3>Salary Slip</h3>" +
            "Employee Name: " + employeeName + "<br>" +
            "Basic Salary: ₹" + basicSalary.toFixed(2) + "<br>" +
            "HRA (20%): ₹" + hra.toFixed(2) + "<br>" +
            "DA (15%): ₹" + da.toFixed(2) + "<br>" +
            "Gross Salary: ₹" + grossSalary.toFixed(2) + "<br>" +
            "PF (12%): ₹" + pf.toFixed(2) + "<br>" +
            "Professional Tax: ₹" + professionalTax.toFixed(2) + "<br>" +
            "Total Deductions: ₹" + totalDeductions.toFixed(2) + "<br>" +
            "<strong>Net Salary: ₹" +
            netSalary.toFixed(2) +
            "</strong>";
    });
 