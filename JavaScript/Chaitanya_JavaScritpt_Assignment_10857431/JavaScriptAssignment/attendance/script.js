let checkInButton = document.getElementById("checkIn");
let checkOutButton = document.getElementById("checkOut");
 
checkInButton.addEventListener("click", function() {
    showAttendance("Checked In", "checked-in");
});
 
checkOutButton.addEventListener("click", function() {
    showAttendance("Checked Out", "checked-out");
});
 
function showAttendance(status, className) {
 
    let employeeId = document.getElementById("employeeId").value;
    let employeeName = document.getElementById("employeeName").value;
 
    let currentDate = new Date();
 
    document.getElementById("attendance").innerHTML =
        "Employee ID: " + employeeId + "<br>" +
        "Employee Name: " + employeeName + "<br>" +
        "Date & Time: " + currentDate.toLocaleString() + "<br>" +
        "<span class='" + className + "'>" +
        "Status: " + status +
        "</span>";
}