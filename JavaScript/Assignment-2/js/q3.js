const $ = (id) => document.getElementById(id);
const money = (n) => "₹" + n.toFixed(2);
$("calc").addEventListener("click", () => {
  const qty = parseFloat($("qty").value),
    price = parseFloat($("price").value);
  if (!$("pname").value.trim() || !(qty > 0) || !(price >= 0)) {
    $("err").textContent = "Enter product name, quantity and price.";
    $("res").style.display = "none";
    return;
  }
  $("err").textContent = "";
  const subtotal = qty * price;
  const gst = subtotal * 0.18;
  const discount = subtotal > 5000 ? subtotal * 0.1 : 0; // 10% if subtotal exceeds ₹5000
  $("sub").textContent = money(subtotal);
  $("gst").textContent = money(gst);
  $("disc").textContent = money(discount);
  $("fin").textContent = money(subtotal + gst - discount);
  $("res").style.display = "table";
});
