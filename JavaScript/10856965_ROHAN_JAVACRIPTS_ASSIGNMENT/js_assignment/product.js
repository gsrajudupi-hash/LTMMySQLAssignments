function calculateBill() {
  let price = parseFloat(document.getElementById("price").value) || 0;
  let qty = parseInt(document.getElementById("quantity").value) || 0;
 
  let subtotal = price * qty;
  let gst = subtotal * 0.18;
  let totalBeforeDiscount = subtotal + gst;
  
  let discount = 0;
  if (totalBeforeDiscount > 5000) {
    discount = totalBeforeDiscount * 0.10;
  }
 
  let finalAmount = totalBeforeDiscount - discount;
 
  document.getElementById("subtotal").innerText = subtotal.toFixed(2);
  document.getElementById("gst").innerText = gst.toFixed(2);
  document.getElementById("discount").innerText = discount.toFixed(2);
  document.getElementById("finalAmount").innerText = finalAmount.toFixed(2);
}
 
function castVote() {
  let selectedCandidate = document.querySelector('input[name="candidate"]:checked');
  if (!selectedCandidate) {
    alert("Please select a candidate first.");
    return;
  }
 
  document.getElementById("resultMsg").innerText = `You voted for: ${selectedCandidate.value}. Thank you for voting.`;
  document.getElementById("voteBtn").disabled = true;
  
  // Disable all radio buttons
  let radios = document.querySelectorAll('input[name="candidate"]');
  radios.forEach(r => r.disabled = true);
}
 