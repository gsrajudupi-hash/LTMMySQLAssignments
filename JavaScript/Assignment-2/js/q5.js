const $ = (id) => document.getElementById(id);
function login() {
  const ok = $("user").value === "admin" && $("pass").value === "admin123";
  $("msg").textContent = ok
    ? "Welcome, " + $("user").value + "!"
    : "Invalid username or password.";
  $("msg").style.color = ok ? "green" : "#b3261e";
}
$("login").addEventListener("click", login);
document.addEventListener("keydown", (e) => {
  if (e.key === "Enter") login();
});
$("show").addEventListener(
  "change",
  () => ($("pass").type = $("show").checked ? "text" : "password"),
);
