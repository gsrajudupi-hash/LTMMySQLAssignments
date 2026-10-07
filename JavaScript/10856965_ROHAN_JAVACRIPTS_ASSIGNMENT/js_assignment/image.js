const images = document.querySelectorAll(".gallery-img");
const previewBox = document.getElementById("largePreview");
 
images.forEach(img => {
  img.addEventListener("mouseover", () => {
    img.style.transform = "scale(1.1)";
    document.getElementById("imgTitle").innerText = img.alt;
  });
 
  img.addEventListener("mouseleave", () => {
    img.style.transform = "scale(1.0)";
    document.getElementById("imgTitle").innerText = "";
  });
 
  img.addEventListener("click", () => {
    previewBox.src = img.src;
  });
});