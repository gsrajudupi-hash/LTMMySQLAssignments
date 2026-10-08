const form = document.getElementById('studentForm');
form.addEventListener('submit', e => {
    e.preventDefault(); document.querySelectorAll('.error').forEach(x => x.textContent = ''); let ok = true;
    const name = document.getElementById('name').value.trim(), email = document.getElementById('email').value.trim(), mobile = document.getElementById('mobile').value.trim(), password = document.getElementById('password').value, confirmPassword = document.getElementById('confirmPassword').value;
    if (!name) { nameError.textContent = 'Name is required.'; ok = false } if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) { emailError.textContent = 'Enter a valid email.'; ok = false } if (!/^\d{10}$/.test(mobile)) { mobileError.textContent = 'Mobile number must contain exactly 10 digits.'; ok = false } if (password.length < 8) { passwordError.textContent = 'Password must be at least 8 characters.'; ok = false } if (confirmPassword !== password) { confirmError.textContent = 'Passwords do not match.'; ok = false }
    message.className = ok ? 'success' : 'error'; message.textContent = ok ? 'Registration successful.' : 'Please correct the errors above.'; if (ok) form.reset();
});