const $ = (id) => document.getElementById(id);
$("submit").addEventListener("click", () => {
  const name = $("name").value.trim(),
    email = $("email").value.trim();
  if (!name || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
    $("err").textContent = "Enter a name and a valid email.";
    return;
  }
  $("err").textContent = "";
  const tr = document.createElement("tr");
  [
    name,
    email,
    $("rating").value,
    $("dept").value,
    $("sug").value,
    $("sub").checked ? "Yes" : "No",
  ].forEach((v) => {
    const td = document.createElement("td");
    td.textContent = v;
    tr.appendChild(td);
  });
  $("rows").appendChild(tr);
  $("name").value = $("email").value = $("sug").value = "";
  $("sub").checked = false;
});
