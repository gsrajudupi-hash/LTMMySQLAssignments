const $ = (id) => document.getElementById(id);
let cart = []; // {name, price, qty}

function render() {
  $("cart").innerHTML = "";
  cart.forEach((p, i) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `<td>${p.name}</td><td>${p.price}</td>
      <td><button data-a="dec" data-i="${i}" style="margin:0">−</button> ${p.qty}
          <button data-a="inc" data-i="${i}" style="margin:0">+</button></td>
      <td>${p.price * p.qty}</td>
      <td><button data-a="del" data-i="${i}" style="margin:0;background:#b3261e">Remove</button></td>`;
    $("cart").appendChild(tr);
  });
  $("items").textContent = cart.reduce((s, p) => s + p.qty, 0);
  $("grand").textContent = cart.reduce((s, p) => s + p.qty * p.price, 0);
}
$("add").addEventListener("click", () => {
  const name = $("pname").value.trim(),
    price = parseFloat($("pprice").value);
  if (!name || !(price >= 0)) {
    $("err").textContent = "Enter product name and price.";
    return;
  }
  $("err").textContent = "";
  const existing = cart.find(
    (p) => p.name.toLowerCase() === name.toLowerCase() && p.price === price,
  );
  existing ? existing.qty++ : cart.push({ name, price, qty: 1 });
  $("pname").value = $("pprice").value = "";
  render();
});
$("cart").addEventListener("click", (e) => {
  // event delegation
  const b = e.target.closest("button");
  if (!b) return;
  const i = +b.dataset.i;
  if (b.dataset.a === "inc") cart[i].qty++;
  if (b.dataset.a === "dec" && cart[i].qty > 1) cart[i].qty--;
  if (b.dataset.a === "del") cart.splice(i, 1);
  render();
});
