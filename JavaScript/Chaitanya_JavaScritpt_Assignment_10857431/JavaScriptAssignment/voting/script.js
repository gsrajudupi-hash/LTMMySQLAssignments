document.getElementById("voteButton")
    .addEventListener("click", function() {
 
        let selectedCandidate =
            document.querySelector(
                'input[name="candidate"]:checked'
            );
 
        if (selectedCandidate === null) {
            document.getElementById("result").textContent =
                "Please select a candidate.";
            return;
        }
 
        document.getElementById("result").innerHTML =
            "Selected Candidate: " +
            selectedCandidate.value +
            "<br>Thank you for voting.";
 
        document.getElementById("voteButton").disabled = true;
    });