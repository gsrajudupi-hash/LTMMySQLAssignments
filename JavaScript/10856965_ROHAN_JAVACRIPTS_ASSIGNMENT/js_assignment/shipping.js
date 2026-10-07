let cart = [];
 
function addItem(name, price) {
  let existing = cart.find(item => item.name === name);
  if (existing) {
    existing.qty += 1;
  } else {
    cart.push({ name, price, qty: 1 });
  }
  updateCartUI();
}
 
function updateQuantity(name, change) {
  let item = cart.find(i => i.name === name);
  if (item) {
    item.qty += change;
    if (item.qty <= 0) {
      cart = cart.filter(i => i.name !== name);
    }
  }
  updateCartUI();
}
 
function updateCartUI() {
  let totalItems = cart.reduce((sum, i) => sum + i.qty, 0);
  let grandTotal = cart.reduce((sum, i) => sum + (i.price * i.qty), 0);
 
  document.getElementById("totalItems").innerText = totalItems;
  document.getElementById("grandTotal").innerText = grandTotal.toFixed(2);
}