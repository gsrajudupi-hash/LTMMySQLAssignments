document.getElementById("feedbackForm")
    .addEventListener("submit", function(event) {
 
        event.preventDefault();
 
        let name =
            document.getElementById("customerName").value;
 
        let email =
            document.getElementById("email").value;
 
        let rating =
            document.getElementById("rating").value;
 
        let suggestions =
            document.getElementById("suggestions").value;
 
        let department =
            document.getElementById("department").value;
 
        let subscribe =
            document.getElementById("subscribe").checked
                ? "Yes"
                : "No";
 
        let table =
            document.getElementById("feedbackTable")
                .getElementsByTagName("tbody")[0];
 
        let row = table.insertRow();
 
        row.insertCell(0).textContent = name;
        row.insertCell(1).textContent = email;
        row.insertCell(2).textContent = rating;
        row.insertCell(3).textContent = suggestions;
        row.insertCell(4).textContent = department;
        row.insertCell(5).textContent = subscribe;
 
        document.getElementById("feedbackForm").reset();
    });