document.addEventListener('DOMContentLoaded', () => {

    // --- Sidebar Navigation Switching ---
    const navItems = document.querySelectorAll('.nav-item');
    const taskPanels = document.querySelectorAll('.task-panel');
    const currentTaskTitle = document.getElementById('currentTaskTitle');
    const currentTaskDesc = document.getElementById('currentTaskDesc');

    const taskTitles = {
        'task1': { title: '1. Student Registration Form Validation', desc: 'Validate form fields with real-time error reporting and prevent invalid submissions.' },
        'task2': { title: '2. Employee Attendance Management', desc: 'Demonstrate event handling using Check-In and Check-Out buttons with live timestamps.' },
        'task3': { title: '3. Product Price Calculator', desc: 'Calculate total bills dynamically including 18% GST and 10% discount for orders above ₹5000.' },
        'task4': { title: '4. Online Voting System', desc: 'Use radio buttons and events to allow single-vote casting and disable voting afterwards.' },
        'task5': { title: '5. Login Validation & Password Toggle', desc: 'Validate login credentials with Show/Hide password toggle and Enter key submission.' },
        'task6': { title: '6. Customer Feedback Form', desc: 'Handle form elements and render dynamic records into a table without page refresh.' },
        'task7': { title: '7. Shopping Cart (DOM Manipulation)', desc: 'Add, remove, and adjust quantities of products with live subtotal and grand total updates.' },
        'task8': { title: '8. Dynamic Image Gallery', desc: 'Mouse hover and click events for image enlargement, titles, and modal preview.' },
        'task9': { title: '9. Online Exam & Quiz System', desc: 'Process 5 multiple-choice questions, calculate score, percentage, and pass/fail status.' },
        'task10': { title: '10. Employee Salary & Payslip Calculator', desc: 'Perform complex payroll calculations (HRA, DA, PF, Professional Tax) using DOM manipulation.' }
    };

    navItems.forEach(item => {
        item.addEventListener('click', () => {
            navItems.forEach(i => i.classList.remove('active'));
            taskPanels.forEach(p => p.classList.remove('active'));

            item.classList.add('active');
            const targetId = item.getAttribute('data-target');
            document.getElementById(targetId).classList.add('active');

            if (taskTitles[targetId]) {
                currentTaskTitle.textContent = taskTitles[targetId].title;
                currentTaskDesc.textContent = taskTitles[targetId].desc;
            }
        });
    });


    // --- TASK 1: Student Registration Form Validation ---
    const regForm = document.getElementById('regForm');
    const regName = document.getElementById('regName');
    const regEmail = document.getElementById('regEmail');
    const regMobile = document.getElementById('regMobile');
    const regPass = document.getElementById('regPass');
    const regConfirmPass = document.getElementById('regConfirmPass');
    const regSuccess = document.getElementById('regSuccess');

    regForm.addEventListener('submit', (e) => {
        e.preventDefault();
        let isValid = true;

        // Name Validation
        if (regName.value.trim() === '') {
            document.getElementById('errName').textContent = 'Name should not be empty.';
            isValid = false;
        } else {
            document.getElementById('errName').textContent = '';
        }

        // Email Validation
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(regEmail.value)) {
            document.getElementById('errEmail').textContent = 'Enter a valid email format.';
            isValid = false;
        } else {
            document.getElementById('errEmail').textContent = '';
        }

        // Mobile Validation (exact 10 digits)
        const mobileRegex = /^\d{10}$/;
        if (!mobileRegex.test(regMobile.value)) {
            document.getElementById('errMobile').textContent = 'Mobile number must contain exactly 10 digits.';
            isValid = false;
        } else {
            document.getElementById('errMobile').textContent = '';
        }

        // Password Validation (min 8 chars)
        if (regPass.value.length < 8) {
            document.getElementById('errPass').textContent = 'Password should be at least 8 characters.';
            isValid = false;
        } else {
            document.getElementById('errPass').textContent = '';
        }

        // Confirm Password Match
        if (regConfirmPass.value !== regPass.value || regConfirmPass.value === '') {
            document.getElementById('errConfirmPass').textContent = 'Passwords do not match.';
            isValid = false;
        } else {
            document.getElementById('errConfirmPass').textContent = '';
        }

        if (isValid) {
            regSuccess.classList.remove('hidden');
            regForm.reset();
            setTimeout(() => regSuccess.classList.add('hidden'), 5000);
        } else {
            regSuccess.classList.add('hidden');
        }
    });


    // --- TASK 2: Employee Attendance Management ---
    const btnCheckIn = document.getElementById('btnCheckIn');
    const btnCheckOut = document.getElementById('btnCheckOut');
    const empIdInput = document.getElementById('empId');
    const empNameInput = document.getElementById('empName');
    const attStatusBadge = document.getElementById('attStatusBadge');
    const dispEmpInfo = document.getElementById('dispEmpInfo');
    const dispTimestamp = document.getElementById('dispTimestamp');

    btnCheckIn.addEventListener('click', () => {
        const id = empIdInput.value.trim() || 'EMP-UNKNOWN';
        const name = empNameInput.value.trim() || 'Employee';
        const now = new Date().toLocaleString();

        dispEmpInfo.textContent = `${name} (${id})`;
        dispTimestamp.textContent = now;

        attStatusBadge.textContent = 'Status: Checked In';
        attStatusBadge.className = 'status-badge status-checkedin';
    });

    btnCheckOut.addEventListener('click', () => {
        const id = empIdInput.value.trim() || 'EMP-UNKNOWN';
        const name = empNameInput.value.trim() || 'Employee';
        const now = new Date().toLocaleString();

        dispEmpInfo.textContent = `${name} (${id})`;
        dispTimestamp.textContent = now;

        attStatusBadge.textContent = 'Status: Checked Out';
        attStatusBadge.className = 'status-badge status-checkedout';
    });


    // --- TASK 3: Product Price Calculator ---
    const calcBillBtn = document.getElementById('calcBillBtn');
    const prodQty = document.getElementById('prodQty');
    const prodPrice = document.getElementById('prodPrice');
    const invoiceSummary = document.getElementById('invoiceSummary');

    calcBillBtn.addEventListener('click', () => {
        const qty = parseFloat(prodQty.value) || 0;
        const price = parseFloat(prodPrice.value) || 0;

        if (qty <= 0 || price <= 0) {
            alert('Please enter valid quantity and price.');
            return;
        }

        const subtotal = qty * price;
        const gst = subtotal * 0.18;
        let discount = 0;

        if (subtotal > 5000) {
            discount = subtotal * 0.10;
        }

        const finalAmount = subtotal + gst - discount;

        document.getElementById('invSubtotal').textContent = `₹${subtotal.toFixed(2)}`;
        document.getElementById('invGst').textContent = `₹${gst.toFixed(2)}`;
        document.getElementById('invDiscount').textContent = `₹${discount.toFixed(2)}`;
        document.getElementById('invFinal').textContent = `₹${finalAmount.toFixed(2)}`;

        invoiceSummary.classList.remove('hidden');
    });


    // --- TASK 4: Online Voting System ---
    const votingForm = document.getElementById('votingForm');
    const voteBtn = document.getElementById('voteBtn');
    const voteResult = document.getElementById('voteResult');
    const votedCandidateText = document.getElementById('votedCandidateText');
    let hasVoted = false;

    votingForm.addEventListener('submit', (e) => {
        e.preventDefault();
        if (hasVoted) return;

        const selectedCandidate = document.querySelector('input[name="candidate"]:checked');
        if (!selectedCandidate) {
            alert('Please select a candidate before voting.');
            return;
        }

        hasVoted = true;
        votedCandidateText.textContent = `You voted for: ${selectedCandidate.value}`;
        voteResult.classList.remove('hidden');

        // Disable all radio buttons and vote button
        const radios = document.querySelectorAll('input[name="candidate"]');
        radios.forEach(r => r.disabled = true);
        voteBtn.disabled = true;
        voteBtn.style.opacity = '0.6';
    });


    // --- TASK 5: Login Form & Password Toggle ---
    const loginForm = document.getElementById('loginForm');
    const loginPass = document.getElementById('loginPass');
    const showPassCheck = document.getElementById('showPassCheck');
    const togglePassBtn = document.getElementById('togglePassBtn');
    const eyeIcon = document.getElementById('eyeIcon');
    const loginMessage = document.getElementById('loginMessage');

    showPassCheck.addEventListener('change', () => {
        if (showPassCheck.checked) {
            loginPass.type = 'text';
            eyeIcon.className = 'fa-solid fa-eye-slash';
        } else {
            loginPass.type = 'password';
            eyeIcon.className = 'fa-solid fa-eye';
        }
    });

    togglePassBtn.addEventListener('click', () => {
        showPassCheck.checked = !showPassCheck.checked;
        showPassCheck.dispatchEvent(new Event('change'));
    });

    loginForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const user = document.getElementById('loginUser').value.trim();
        const pass = loginPass.value;

        if (user === 'admin' && pass === 'password123') {
            loginMessage.textContent = 'Welcome, Admin! Login successful.';
            loginMessage.className = 'login-msg success';
        } else if (user !== '' && pass !== '') {
            loginMessage.textContent = 'Welcome back, ' + user + '! Login successful.';
            loginMessage.className = 'login-msg success';
        } else {
            loginMessage.textContent = 'Error: Invalid username or password.';
            loginMessage.className = 'login-msg error';
        }
        loginMessage.classList.remove('hidden');
    });


    // --- TASK 6: Customer Feedback Form ---
    const feedbackForm = document.getElementById('feedbackForm');
    const feedbackTbody = document.getElementById('feedbackTbody');
    const feedbackCount = document.getElementById('feedbackCount');
    let feedbackList = [];

    feedbackForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const name = document.getElementById('fbName').value;
        const email = document.getElementById('fbEmail').value;
        const rating = document.getElementById('fbRating').value;
        const dept = document.getElementById('fbDept').value;
        const suggestions = document.getElementById('fbSuggestions').value || 'None';
        const subscribe = document.getElementById('fbSubscribe').checked ? 'Yes' : 'No';

        const record = { name, email, rating, dept, suggestions, subscribe };
        feedbackList.push(record);
        renderFeedbackTable();
        feedbackForm.reset();
    });

    function renderFeedbackTable() {
        feedbackCount.textContent = feedbackList.length;
        if (feedbackList.length === 0) {
            feedbackTbody.innerHTML = `<tr class="empty-row"><td colspan="6">No feedback entries yet. Submit the form above.</td></tr>`;
            return;
        }

        let html = '';
        feedbackList.forEach(item => {
            html += `
                <tr>
                    <td>${item.name}</td>
                    <td>${item.email}</td>
                    <td>⭐ ${item.rating}/5</td>
                    <td>${item.dept}</td>
                    <td>${item.suggestions}</td>
                    <td>${item.subscribe}</td>
                </tr>
            `;
        });
        feedbackTbody.innerHTML = html;
    }


    // --- TASK 7: Shopping Cart ---
    let cartItems = [
        { id: 1, name: 'Wireless Bluetooth Earbuds', price: 1299, qty: 1 },
        { id: 2, name: 'Smart Fitness Band', price: 2499, qty: 2 }
    ];

    const cartTbody = document.getElementById('cartTbody');
    const totalCartItems = document.getElementById('totalCartItems');
    const grandCartTotal = document.getElementById('grandCartTotal');
    const addCartProdBtn = document.getElementById('addCartProdBtn');

    function renderCart() {
        let totalItemsCount = 0;
        let grandTotalAmount = 0;
        let html = '';

        if (cartItems.length === 0) {
            cartTbody.innerHTML = `<tr><td colspan="5" style="text-align: center; color: var(--text-muted);">Your cart is empty.</td></tr>`;
            totalCartItems.textContent = '0';
            grandCartTotal.textContent = '₹0.00';
            return;
        }

        cartItems.forEach((item, index) => {
            const itemTotal = item.price * item.qty;
            totalItemsCount += item.qty;
            grandTotalAmount += itemTotal;

            html += `
                <tr>
                    <td>${item.name}</td>
                    <td>₹${item.price}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="updateQty(${index}, -1)">-</button>
                        <span style="margin: 0 10px; font-weight: 600;">${item.qty}</span>
                        <button class="btn btn-sm btn-primary" onclick="updateQty(${index}, 1)">+</button>
                    </td>
                    <td>₹${itemTotal}</td>
                    <td><button class="btn btn-sm btn-danger" onclick="removeCartItem(${index})"><i class="fa-solid fa-trash"></i></button></td>
                </tr>
            `;
        });

        cartTbody.innerHTML = html;
        totalCartItems.textContent = totalItemsCount;
        grandCartTotal.textContent = `₹${grandTotalAmount.toFixed(2)}`;
    }

    window.updateQty = function(index, change) {
        cartItems[index].qty += change;
        if (cartItems[index].qty <= 0) {
            cartItems.splice(index, 1);
        }
        renderCart();
    };

    window.removeCartItem = function(index) {
        cartItems.splice(index, 1);
        renderCart();
    };

    addCartProdBtn.addEventListener('click', () => {
        const nameInput = document.getElementById('cartProdName');
        const priceInput = document.getElementById('cartProdPrice');
        const name = nameInput.value.trim();
        const price = parseFloat(priceInput.value);

        if (!name || isNaN(price) || price <= 0) {
            alert('Please enter valid product name and price.');
            return;
        }

        cartItems.push({ id: Date.now(), name, price, qty: 1 });
        nameInput.value = '';
        priceInput.value = '';
        renderCart();
    });

    renderCart();


    // --- TASK 8: Image Gallery ---
    const galleryItems = document.querySelectorAll('.gallery-item');
    const imageModal = document.getElementById('imageModal');
    const modalImg = document.getElementById('modalImg');
    const modalTitle = document.getElementById('modalTitle');
    const modalDesc = document.getElementById('modalDesc');
    const closeModal = document.getElementById('closeModal');

    galleryItems.forEach(item => {
        const img = item.querySelector('img');
        const title = item.getAttribute('data-title');
        const desc = item.getAttribute('data-desc');

        item.addEventListener('click', () => {
            modalImg.src = img.src;
            modalTitle.textContent = title;
            modalDesc.textContent = desc;
            imageModal.classList.remove('hidden');
        });
    });

    closeModal.addEventListener('click', () => {
        imageModal.classList.add('hidden');
    });

    window.addEventListener('click', (e) => {
        if (e.target === imageModal) {
            imageModal.classList.add('hidden');
        }
    });


    // --- TASK 9: Online Exam System ---
    const quizForm = document.getElementById('quizForm');
    const quizResult = document.getElementById('quizResult');
    
    const correctAnswers = {
        q1: 'let',
        q2: 'Document Object Model',
        q3: 'JSON.parse()',
        q4: 'onclick',
        q5: 'color'
    };

    quizForm.addEventListener('submit', (e) => {
        e.preventDefault();
        let correctCount = 0;
        let wrongCount = 0;
        const totalQ = Object.keys(correctAnswers).length;

        for (let q in correctAnswers) {
            const selected = document.querySelector(`input[name="${q}"]:checked`);
            if (selected && selected.value === correctAnswers[q]) {
                correctCount++;
            } else {
                wrongCount++;
            }
        }

        const percentage = (correctCount / totalQ) * 100;
        const isPass = percentage >= 60;

        document.getElementById('resCorrect').textContent = correctCount;
        document.getElementById('resWrong').textContent = wrongCount;
        document.getElementById('resPercent').textContent = `${percentage}%`;
        
        const resStatus = document.getElementById('resStatus');
        const resStatusBox = document.getElementById('resStatusBox');
        
        if (isPass) {
            resStatus.textContent = 'PASS';
            resStatusBox.style.background = '#dcfce7';
            resStatusBox.style.color = '#15803d';
        } else {
            resStatus.textContent = 'FAIL';
            resStatusBox.style.background = '#fee2e2';
            resStatusBox.style.color = '#b91c1c';
        }

        quizResult.classList.remove('hidden');
    });


    // --- TASK 10: Employee Salary Calculator ---
    const salaryForm = document.getElementById('salaryForm');
    const payslipContainer = document.getElementById('payslipContainer');

    salaryForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const name = document.getElementById('salEmpName').value.trim() || 'Employee';
        const basic = parseFloat(document.getElementById('basicSalary').value) || 0;

        if (basic <= 0) {
            alert('Please enter a valid basic salary.');
            return;
        }

        const hra = basic * 0.20;
        const da = basic * 0.15;
        const gross = basic + hra + da;

        const pf = basic * 0.12;
        const professionalTax = 200;
        const totalDeductions = pf + professionalTax;

        const netSalary = gross - totalDeductions;

        document.getElementById('slipName').textContent = name;
        document.getElementById('slipBasic').textContent = `₹${basic.toFixed(2)}`;
        document.getElementById('slipHra').textContent = `₹${hra.toFixed(2)}`;
        document.getElementById('slipDa').textContent = `₹${da.toFixed(2)}`;
        document.getElementById('slipGross').textContent = `₹${gross.toFixed(2)}`;

        document.getElementById('slipPf').textContent = `₹${pf.toFixed(2)}`;
        document.getElementById('slipPt').textContent = `₹${professionalTax.toFixed(2)}`;
        document.getElementById('slipDeductions').textContent = `₹${totalDeductions.toFixed(2)}`;

        document.getElementById('slipNet').textContent = `₹${netSalary.toFixed(2)}`;

        payslipContainer.classList.remove('hidden');
    });

});
