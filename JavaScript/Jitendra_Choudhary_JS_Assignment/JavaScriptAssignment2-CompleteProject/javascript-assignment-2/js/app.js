const modules=[['home','Home'],['registration','Registration'],['attendance','Attendance'],['calculator','Price Calculator'],['voting','Voting'],['login','Login'],['feedback','Feedback'],['cart','Shopping Cart'],['gallery','Gallery'],['exam','Online Exam'],['salary','Salary']];
const nav=document.querySelector('#nav'); modules.forEach(([id,label])=>{const b=document.createElement('button');b.textContent=label;b.dataset.go=id;nav.appendChild(b)});
const descriptions=['Validate a student registration form','Manage employee check-in and check-out','Calculate GST, discount and final bill','Vote once for one of three candidates','Validate credentials and keyboard login','Add feedback to a live table','Manage cart quantities and totals','Explore hover and click events','Answer five MCQ questions','Generate a salary slip'];
document.querySelector('#cards').innerHTML=modules.slice(1).map((m,i)=>`<article class="card" data-go="${m[0]}"><span>${i+1}</span><h3>${m[1]}</h3><p>${descriptions[i]}</p></article>`).join('');
function showPage(id){document.querySelectorAll('.page').forEach(p=>p.classList.toggle('active',p.id===id));document.querySelectorAll('nav button').forEach(b=>b.classList.toggle('active',b.dataset.go===id));history.replaceState(null,'','#'+id);scrollTo({top:0,behavior:'smooth'})} document.addEventListener('click',e=>{const go=e.target.closest('[data-go]');if(go)showPage(go.dataset.go)});showPage(location.hash.slice(1)||'home');
const val=(id,msg,test)=>{const el=document.querySelector('#'+id),err=document.querySelector('#'+id+'Err');const ok=test(el.value.trim());err.textContent=ok?'':msg;return ok};
document.querySelector('#regForm').addEventListener('submit',e=>{e.preventDefault();const a=val('regName','Name is required.',v=>v.length>0),b=val('regEmail','Enter a valid email.',v=>/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v)),c=val('regMobile','Mobile must contain exactly 10 digits.',v=>/^\d{10}$/.test(v)),d=val('regPassword','Password must be at least 8 characters.',v=>v.length>=8),f=val('regConfirm','Passwords do not match.',v=>v===document.querySelector('#regPassword').value);const r=document.querySelector('#regResult');r.textContent=a&&b&&c&&d&&f?'Registration successful!':'Please correct the highlighted fields.';r.className='message full '+(a&&b&&c&&d&&f?'success':'danger')});
function attendance(type){const id=document.querySelector('#attId').value.trim(),name=document.querySelector('#attName').value.trim(),box=document.querySelector('#attStatus');if(!id||!name){box.className='status-card full';box.textContent='Employee ID and Employee Name are required.';return}const now=new Date().toLocaleString('en-IN');box.className='status-card full '+(type==='in'?'in':'out');box.innerHTML=`<strong>${name} (${id})</strong><br>${type==='in'?'Checked In':'Checked Out'} on ${now}`};document.querySelector('#checkIn').addEventListener('click',()=>attendance('in'));document.querySelector('#checkOut').addEventListener('click',()=>attendance('out'));
const money=n=>'₹'+n.toLocaleString('en-IN',{minimumFractionDigits:2,maximumFractionDigits:2});document.querySelector('#calculateBill').addEventListener('click',()=>{const q=Number(document.querySelector('#quantity').value),p=Number(document.querySelector('#price').value),box=document.querySelector('#billResult');if(q<1||p<0||!p){box.innerHTML='<p class="danger">Enter a valid quantity and price.</p>';return}const sub=q*p,gst=sub*.18,discount=sub>5000?sub*.10:0,final=sub+gst-discount;box.innerHTML=[['Subtotal',sub],['GST (18%)',gst],['Discount',discount],['Final Amount',final]].map(x=>`<div class="metric">${x[0]}<strong>${money(x[1])}</strong></div>`).join('')});
document.querySelector('#voteBtn').addEventListener('click',e=>{const selected=document.querySelector('input[name="candidate"]:checked'),r=document.querySelector('#voteResult');if(!selected){r.textContent='Please select a candidate.';r.className='message danger';return}r.textContent=`You voted for ${selected.value}. Thank you for voting.`;r.className='message success';e.target.disabled=true;document.querySelectorAll('input[name="candidate"]').forEach(x=>x.disabled=true)});
document.querySelector('#showPassword').addEventListener('change',e=>document.querySelector('#loginPassword').type=e.target.checked?'text':'password');document.querySelector('#loginForm').addEventListener('submit',e=>{e.preventDefault();const ok=document.querySelector('#username').value==='student'&&document.querySelector('#loginPassword').value==='Java@123',r=document.querySelector('#loginResult');r.textContent=ok?'Welcome, student!':'Invalid username or password.';r.className='message full '+(ok?'success':'danger')});
document.querySelector('#feedbackForm').addEventListener('submit',e=>{e.preventDefault();if(!e.target.reportValidity())return;const values=[customerName.value,customerEmail.value,rating.value,department.value,suggestions.value||'-',subscribe.checked?'Yes':'No'];const tr=document.createElement('tr');values.forEach(v=>{const td=document.createElement('td');td.textContent=v;tr.appendChild(td)});feedbackRows.appendChild(tr);e.target.reset()});
const cart={};function renderCart(){const box=document.querySelector('#cartItems'),entries=Object.values(cart);box.innerHTML=entries.length?entries.map(x=>`<div class="cart-row"><div><b>${x.name}</b><br>${money(x.price)}</div><div class="qty"><button data-cart="minus" data-id="${x.id}">−</button><b>${x.qty}</b><button data-cart="plus" data-id="${x.id}">+</button></div><button class="remove" data-cart="remove" data-id="${x.id}">Remove</button></div>`).join(''):'<p>Your cart is empty.</p>';const items=entries.reduce((s,x)=>s+x.qty,0),total=entries.reduce((s,x)=>s+x.qty*x.price,0);cartSummary.innerHTML=`<span>Total Items: ${items}</span><span>Grand Total: ${money(total)}</span>`}document.querySelectorAll('.add-product').forEach(b=>b.addEventListener('click',()=>{const {id,name,price}=b.dataset;cart[id]??={id,name,price:Number(price),qty:0};cart[id].qty++;renderCart()}));document.querySelector('#cartItems').addEventListener('click',e=>{const b=e.target.closest('[data-cart]');if(!b)return;const x=cart[b.dataset.id];if(b.dataset.cart==='plus')x.qty++;if(b.dataset.cart==='minus'){x.qty--;if(x.qty===0)delete cart[x.id]}if(b.dataset.cart==='remove')delete cart[x.id];renderCart()});renderCart();
document.querySelectorAll('.gallery-grid img').forEach(img=>{img.addEventListener('mouseenter',()=>img.nextElementSibling.textContent=img.dataset.title);img.addEventListener('mouseleave',()=>img.nextElementSibling.textContent=img.dataset.title);img.addEventListener('click',()=>{document.querySelector('#preview img').src=img.src;document.querySelector('#preview span').textContent=img.dataset.title})});
const questions=[['Which method selects an element by ID?',['querySelectorAll()','getElementById()','createElement()'],1],['Which event fires when a button is clicked?',['click','load','change'],0],['Which keyword declares a block-scoped variable?',['var','let','define'],1],['What does DOM stand for?',['Data Object Model','Document Object Model','Document Order Method'],1],['Which method attaches an event handler?',['addEventListener()','appendChild()','setAttribute()'],0]];examForm.innerHTML=questions.map((q,i)=>`<div class="question"><b>${i+1}. ${q[0]}</b>${q[1].map((a,j)=>`<label><input type="radio" name="q${i}" value="${j}"> ${a}</label>`).join('')}</div>`).join('')+'<button>Submit Exam</button>';examForm.addEventListener('submit',e=>{e.preventDefault();let correct=0,answered=0;questions.forEach((q,i)=>{const a=document.querySelector(`input[name="q${i}"]:checked`);if(a){answered++;if(Number(a.value)===q[2])correct++}});const wrong=questions.length-correct,pct=correct/questions.length*100,pass=pct>=60;examResult.innerHTML=[['Correct Answers',correct],['Wrong Answers',wrong],['Percentage',pct+'%'],['Status',pass?'Pass':'Fail']].map(x=>`<div class="metric">${x[0]}<strong class="${x[0]==='Status'?(pass?'success':'danger'):''}">${x[1]}</strong></div>`).join('')});
document.querySelector("#calculateSalary").addEventListener("click", () => {
  const name = salaryName.value.trim(),
    basic = Number(basicSalary.value);
  if (!name || basic <= 0) {
    salarySlip.innerHTML =
      '<p class="danger">Enter employee name and a valid basic salary.</p>';
    return;
  }
  const hra = basic * 0.2,
    da = basic * 0.15,
    pf = basic * 0.12,
    pt = 200,
    gross = basic + hra + da,
    ded = pf + pt,
    net = gross - ded;
  salarySlip.innerHTML = `<h3>Salary Slip: ${name}</h3>${[
    ["Basic Salary", basic],
    ["HRA (20%)", hra],
    ["DA (15%)", da],
    ["Gross Salary", gross],
    ["PF (12%)", pf],
    ["Professional Tax", pt],
    ["Total Deductions", ded],
    ["Net Salary", net],
  ]
    .map(
      (x, i) =>
        `<div class="salary-line ${i === 7 ? "total" : ""}"><span>${x[0]}</span><strong>${money(x[1])}</strong></div>`,
    )
    .join("")}`;
});