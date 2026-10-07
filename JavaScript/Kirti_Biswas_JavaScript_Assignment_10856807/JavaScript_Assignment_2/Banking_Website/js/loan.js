const loanForm =
    document.getElementById(
        "loanForm"
    );

loanForm.addEventListener(
    "submit",
    function(event){

        event.preventDefault();

        const loanType =
            document.getElementById(
                "loanType"
            ).value;

        const amount =
            Number(
                document.getElementById(
                    "loanAmount"
                ).value
            );

        const rate =
            Number(
                document.getElementById(
                    "interestRate"
                ).value
            );

        const tenure =
            Number(
                document.getElementById(
                    "loanTenure"
                ).value
            );

        const monthlyRate =
            (rate / 12) / 100;

        const months =
            tenure * 12;

        const emi =
            amount *
            monthlyRate *
            Math.pow(
                1 + monthlyRate,
                months
            ) /
            (
                Math.pow(
                    1 + monthlyRate,
                    months
                ) - 1
            );

        const totalAmount =
            emi * months;

        const totalInterest =
            totalAmount - amount;

        document.getElementById(
            "loanResult"
        ).innerHTML = `

            <h3>
                Loan Summary
            </h3>

            <p>
                Loan Type :
                ${loanType}
            </p>

            <p>
                Monthly EMI :
                ₹${emi.toFixed(2)}
            </p>

            <p>
                Total Interest :
                ₹${totalInterest.toFixed(2)}
            </p>

            <p>
                Total Payable :
                ₹${totalAmount.toFixed(2)}
            </p>
        `;
    }
);