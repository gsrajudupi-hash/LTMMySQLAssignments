const $ = (id) => document.getElementById(id);
const candidates = ["Candidate A", "Candidate B", "Candidate C"];
$("cands").innerHTML = candidates
  .map(
    (c, i) =>
      `<label style="font-weight:normal"><input type="radio" name="cand" value="${c}"> ${c}</label>`,
  )
  .join("");

function lock(choice) {
  $("vote").disabled = true;
  document
    .querySelectorAll("input[name=cand]")
    .forEach((r) => (r.disabled = true));
  $("sel").textContent = "You voted for: " + choice;
  $("thanks").textContent = "Thank you for voting.";
}
const saved = localStorage.getItem("votedFor"); // remembers vote after page reload
if (saved) lock(saved);

$("vote").addEventListener("click", () => {
  const r = document.querySelector("input[name=cand]:checked");
  if (!r) {
    $("err").textContent = "Please select a candidate.";
    return;
  }
  $("err").textContent = "";
  localStorage.setItem("votedFor", r.value);
  lock(r.value);
});
$("reset").addEventListener("click", () => {
  localStorage.removeItem("votedFor");
  location.reload();
});
