 document.getElementById("examForm")
    .addEventListener("submit", function(event) {
 
        event.preventDefault();
 
        let answers = {
            q1: "JavaScript",
            q2: "p",
            q3: "getElementById",
            q4: "//",
            q5: "let"
        };
 
        let correct = 0;
 
        for (let question in answers) {
 
            let selected =
                document.querySelector(
                    'input[name="' + question + '"]:checked'
                );
 
            if (selected && selected.value === answers[question]) {
                correct++;
            }
        }
 
        let wrong = 5 - correct;
 
        let percentage = (correct / 5) * 100;
 
        let status =
            percentage >= 50 ? "PASS" : "FAIL";
 
        document.getElementById("result").innerHTML =
            "Correct Answers: " + correct + "<br>" +
            "Wrong Answers: " + wrong + "<br>" +
            "Percentage: " + percentage + "%<br>" +
            "Status: " + status;
    });
