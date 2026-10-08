function updateCart() {
 
    let products = document.querySelectorAll(".product");
 
    let totalItems = 0;
    let grandTotal = 0;
 
    products.forEach(function(product) {
 
        let price = Number(product.dataset.price);
 
        let quantity =
            Number(product.querySelector(".quantity").textContent);
 
        totalItems += quantity;
        grandTotal += price * quantity;
    });
 
    document.getElementById("totalItems").textContent =
        totalItems;
 
    document.getElementById("grandTotal").textContent =
        grandTotal;
}
 
 
document.querySelectorAll(".increase")
    .forEach(function(button) {
 
        button.addEventListener("click", function() {
 
            let quantity =
                this.parentElement.querySelector(".quantity");
 
            quantity.textContent =
                Number(quantity.textContent) + 1;
 
            updateCart();
        });
    });
 
 
document.querySelectorAll(".decrease")
    .forEach(function(button) {
 
        button.addEventListener("click", function() {
 
            let quantity =
                this.parentElement.querySelector(".quantity");
 
            let currentQuantity =
                Number(quantity.textContent);
 
            if (currentQuantity > 1) {
                quantity.textContent =
                    currentQuantity - 1;
            }
 
            updateCart();
        });
    });
 
 
document.querySelectorAll(".remove")
    .forEach(function(button) {
 
        button.addEventListener("click", function() {
 
            this.parentElement.remove();
 
            updateCart();
        });
    });