const $ = (id) => document.getElementById(id);
const questions = [
  {
    qtn: "Which tag is used for the largest heading?",
    opt: ["<h6>", "<h1>", "<head>", "<title>"],
    ans: 1,
  },
  {
    qtn: "Which method selects an element by id?",
    opt: ["getElementById", "queryAll", "getId", "selectId"],
    ans: 0,
  },
  {
    qtn: "Which keyword declares a block-scoped variable?",
    opt: ["var", "let", "int", "dim"],
    ans: 1,
  },
  {
    qtn: "Which event fires when a button is clicked?",
    opt: ["onhover", "onsubmit", "click", "press"],
    ans: 2,
  },
  {
    qtn: "What does DOM stand for?",
    opt: [
      "Data Object Model",
      "Document Object Model",
      "Digital Order Map",
      "Display Object Method",
    ],
    ans: 1,
  },
];
$("exam").innerHTML = questions
  .map(
    (x, i) =>
      `<p><b>${i + 1}. ${x.qtn}</b></p>` + 
      x.opt .map(
          (o, j) =>
            `<label style="font-weight:normal"><input type="radio" name="qtn${i}" value="${j}"> ${o.replace(/</g, "&lt;")}</label>`,
        )
        .join(""),
  )
  .join("");

$("submit").addEventListener("click", () => {
  let correct = 0,
    answered = 0;
  questions.forEach((x, i) => {
    const r = document.querySelector(`input[name=qtn${i}]:checked`);
    if (r) {
      answered++;
      if (+r.value === x.ans) correct++;
    }
  });
  if (
    answered < questions.length &&
    !confirm("Some questions are unanswered. Submit anyway?")
  )
  return;
  const wrong = questions.length - correct;
  const pct = (correct / questions.length) * 100;
  const pass = pct >= 40;
  $("result").innerHTML = `<table>
    <tr><td>Correct Answers</td><td>${correct}</td></tr>
    <tr><td>Wrong Answers</td><td>${wrong}</td></tr>
    <tr><td>Percentage</td><td>${pct.toFixed(1)}%</td></tr>
    <tr><th>Status</th><th style="color:${pass ? "green" : "crimson"}">${pass ? "PASS" : "FAIL"}</th></tr></table>`;
  $("submit").disabled = true;
});
