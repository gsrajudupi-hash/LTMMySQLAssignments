voteForm.addEventListener('submit', e => {
    e.preventDefault(); const selected = document.querySelector('input[name="candidate"]:checked');
    if (!selected) { voteResult.innerHTML = '<span class="error">Please select a candidate.</span>'; return } voteResult.innerHTML = `Selected candidate: <strong>${selected.value}</strong><p class="success">Thank you for voting.</p>`; voteButton.disabled = true; document.querySelectorAll('input[name="candidate"]').forEach(r => r.disabled = true);
});