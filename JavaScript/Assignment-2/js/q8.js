const $ = (id) => document.getElementById(id);
const images = [
  { title: "Bird-1", src: "../images/bird1.jpeg" },
  { title: "Bird-2", src: "../images/bird2.jpeg" },
  { title: "Bird-3", src: "../images/bird3.jpeg" },
  { title: "Bird-4", src: "../images/bird4.jpeg" },
];
images.forEach((im) => {
  const img = document.createElement("img");
  img.src = im.src;
  img.alt = im.title;
  img.width = 110;
  img.style.cssText = "transition:transform .2s;cursor:pointer;border-radius:4px";
  img.addEventListener("mouseover", () => {
    img.style.transform = "scale(1.3)";
    $("title").textContent = im.title;
  });
  img.addEventListener("mouseleave", () => {
    img.style.transform = "scale(1)";
    $("title").textContent = "";
  });
  img.addEventListener("click", () => {
    $("preview").innerHTML = `<img src="${im.src}" style="max-width:100%;border-radius:4px"><p>${im.title}</p>`;
  });
  $("gallery").appendChild(img);
});
