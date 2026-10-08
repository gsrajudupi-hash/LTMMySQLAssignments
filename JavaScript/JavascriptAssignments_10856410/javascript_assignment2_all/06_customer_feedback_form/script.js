feedbackForm.addEventListener('submit', e => {
    e.preventDefault();
    if (!feedbackForm.reportValidity()) return;
    const row = document.createElement('tr');[customerName.value, customerEmail.value, rating.value, suggestions.value || '-', department.value, subscribe.checked ? 'Yes' : 'No'].forEach(value => { const td = document.createElement('td'); td.textContent = value; row.appendChild(td) });
    feedbackBody.appendChild(row); feedbackForm.reset();
});