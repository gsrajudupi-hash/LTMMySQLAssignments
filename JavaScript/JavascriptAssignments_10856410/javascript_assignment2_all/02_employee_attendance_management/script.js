const attendance = document.getElementById('attendance');
function updateAttendance(type) {
    const id = employeeId.value.trim(), name = employeeName.value.trim();
    if (!id || !name) { attendance.className = 'result error'; attendance.textContent = 'Enter Employee ID and Employee Name.'; return }
    const isIn = type === 'Checked In'; attendance.className = 'result ' + (isIn ? 'status-in' : 'status-out');
    attendance.innerHTML = `<strong>${type}</strong><br>Employee ID: ${id}<br>Employee Name: ${name}<br>Date & Time: ${new Date().toLocaleString()}`;
}
checkIn.addEventListener('click', () => updateAttendance('Checked In'));
checkOut.addEventListener('click', () => updateAttendance('Checked Out'));