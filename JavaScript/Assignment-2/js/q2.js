const $ = (id) => document.getElementById(id);
const tick = () => ($("clock").textContent = new Date().toLocaleString());
tick();
setInterval(tick, 1000);

function mark(action, color) {
  const id = $("eid").value.trim(),
    name = $("ename").value.trim();
  if (!id || !name) {
    $("err").textContent = "Enter Employee ID and Name first.";
    return;
  }
  $("err").textContent = "";
  const s = $("status");
  s.textContent = name + " — " + action;
  s.style.color = color;
  s.style.fontWeight = "bold";
  const tr = document.createElement("tr");
  tr.innerHTML = `<td>${id}</td>`+
                `<td>${name}</td>`+
                `<td style="color:${color}">${action}</td>`+
                `<td>${new Date().toLocaleString()}</td>`;
                $("log").prepend(tr);
}
$("in").addEventListener("click", () => mark("Checked In", "green"));
$("out").addEventListener("click", () => mark("Checked Out", "crimson"));
