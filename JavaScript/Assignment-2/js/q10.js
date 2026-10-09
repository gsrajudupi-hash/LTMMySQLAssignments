const $ = (id) => document.getElementById(id);
$("calc").addEventListener("click", () => {
  const name = $("name").value.trim(),
    basic = parseFloat($("basic").value);
  if (!name || !(basic > 0)) {
    $("err").textContent = "Enter employee name and basic salary.";
    return;
  }
  $("err").textContent = "";
  const hra = basic * 0.2,
    da = basic * 0.15,
    pf = basic * 0.12,
    pt = 200;
  const gross = basic + hra + da,
    deductions = pf + pt,
    net = gross - deductions;
  const f = (n) => "₹" + n.toFixed(2);

  const slip = $("slip");
  slip.innerHTML = "";
  const h = document.createElement("h3");
  h.textContent = "Salary Slip — " + name;
  const t = document.createElement("table");
  [
    ["Basic", basic],
    ["HRA (20%)", hra],
    ["DA (15%)", da],
    ["Gross Salary", gross],
    ["PF (12%)", pf],
    ["Professional Tax", pt],
    ["Total Deductions", deductions],
    ["Net Salary", net],
  ].forEach(([k, v]) => {
    const tr = t.insertRow();
    tr.insertCell().textContent = k;
    tr.insertCell().textContent = f(v);
    if (["Gross Salary", "Total Deductions", "Net Salary"].includes(k))
      tr.style.fontWeight = "bold";
  });
  slip.append(h, t);
});
