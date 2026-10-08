document.getElementById("calculate")
    .addEventListener("click", function() {
 
        let productName =
            document.getElementById("productName").value;
 
        let quantity =
            Number(document.getElementById("quantity").value);
 
        let price =
            Number(document.getElementById("price").value);
 
        let subtotal = quantity * price;
 
        let gst = subtotal * 0.18;
 
        let discount = 0;
 
        if (subtotal > 5000) {
            discount = subtotal * 0.10;
        }
 
        let finalAmount = subtotal + gst - discount;
 
        document.getElementById("result").innerHTML =
            "Product: " + productName + "<br>" +
            "Subtotal: ₹" + subtotal.toFixed(2) + "<br>" +
            "GST (18%): ₹" + gst.toFixed(2) + "<br>" +
            "Discount: ₹" + discount.toFixed(2) + "<br>" +
            "Final Amount: ₹" + finalAmount.toFixed(2);
    });
 