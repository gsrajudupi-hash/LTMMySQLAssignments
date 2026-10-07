function submitExam(e) {
  e.preventDefault();
  const answers = { q1: "A", q2: "B", q3: "C", q4: "D", q5: "A" };
  let score = 0;
  let totalQuestions = 5;
 
  for (let q in answers) {
    let selected = document.querySelector(`input[name="${q}"]:checked`);
    if (selected && selected.value === answers[q]) {
      score++;
    }
  }
 
  let wrong = totalQuestions - score;
  let percentage = (score / totalQuestions) * 100;
  let status = percentage >= 40 ? "Pass" : "Fail";
 
  document.getElementById("correctCount").innerText = score;
  document.getElementById("wrongCount").innerText = wrong;
  document.getElementById("percentage").innerText = percentage + "%";
  document.getElementById("passStatus").innerText = status;
}