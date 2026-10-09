const $ = (id) => document.getElementById(id);
function setErr(id, text) {
  $("e-" + id).textContent = text;
  return text === "";
}

$("reg_form").addEventListener("submit", function (e) {
  let ok = true;
  ok = setErr("name", $("name").value.trim() ? "" : "Name is required.") && ok;
  ok = setErr(
      "email",
      /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test($("email").value) ? "" : "Enter a valid email address.",
    ) && ok;
  ok = setErr(
      "mobile",
      /^\d{10}$/.test($("mobile").value) ? "" : "Mobile must be exactly 10 digits.",
    ) && ok;
  ok = setErr(
      "pass",
      $("pass").value.length >= 8 ? "" : "Password must be at least 8 characters.",
    ) && ok;
  ok = setErr(
      "cpass",
      $("cpass").value && $("cpass").value === $("pass").value ? "" : "Passwords do not match.",
    ) && ok;
  if (!ok)
    e.preventDefault(); // block submission
  else {
    e.preventDefault();
    $("msg").textContent = "Registration successful!";
  }
});
