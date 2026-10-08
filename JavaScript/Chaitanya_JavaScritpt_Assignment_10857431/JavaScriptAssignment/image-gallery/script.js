let images = document.querySelectorAll(".gallery img");
 
images.forEach(function(image) {
 
    image.addEventListener("mouseenter", function() {
 
        document.getElementById("title").textContent =
            this.title;
    });
 
 
    image.addEventListener("mouseleave", function() {
 
        document.getElementById("title").textContent = "";
    });
 
 
    image.addEventListener("click", function() {
 
        document.getElementById("preview").src =
            this.src;
    });
 
});