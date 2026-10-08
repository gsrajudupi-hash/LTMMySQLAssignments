const VALID_USERNAME = 'admin',
    VALID_PASSWORD = 'Admin@123';
showPassword.addEventListener('change', () => loginPassword.type = showPassword.checked ? 'text' : 'password');
loginForm.addEventListener('submit', e => {
    e.preventDefault();
    const valid = username.value === VALID_USERNAME && loginPassword.value === VALID_PASSWORD;
    loginMessage.className = valid ? 'success' : 'error'; loginMessage.textContent = valid ? `Welcome, ${username.value}!` : 'Invalid username or password.';
});
loginForm.addEventListener('keydown', e => { if (e.key === 'Enter') 
    { e.preventDefault();
     loginForm.requestSubmit(); } });