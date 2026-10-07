function castVote() {
  let selectedCandidate = document.querySelector('input[name="candidate"]:checked');
  if (!selectedCandidate) {
    alert("Please select a candidate first.");
    return;
  }
 
  document.getElementById("resultMsg").innerText = `You voted for: ${selectedCandidate.value}. Thank you for voting.`;
  document.getElementById("voteBtn").disabled = true;
  
  // Disable all radio buttons
  let radios = document.querySelectorAll('input[name="candidate"]');
  radios.forEach(r => r.disabled = true);
}