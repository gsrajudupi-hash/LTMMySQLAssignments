function addFeedback(e) {
  e.preventDefault();
  let name = document.getElementById("custName").value;
  let email = document.getElementById("custEmail").value;
  let rating = document.getElementById("custRating").value;
  let dept = document.getElementById("custDept").value;
  let subscribe = document.getElementById("custSub").checked ? "Yes" : "No";
 
  let tableRow = `<tr>
    <td>${name}</td><td>${email}</td><td>${rating}</td><td>${dept}</td><td>${subscribe}</td>
  </tr>`;
 
  document.getElementById("feedbackTableBody").innerHTML += tableRow;
  document.getElementById("feedbackForm").reset();
}