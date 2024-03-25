document.addEventListener("DOMContentLoaded", function() {
  const choices = ["Rock", "Paper", "Scissors"];

  function computerPlay() {
      return choices[Math.floor(Math.random() * choices.length)];
  }

  function playRound(playerSelection, computerSelection) {
      if (playerSelection === computerSelection) {
          return "It's a tie!";
      } else if (
          (playerSelection === "Rock" && computerSelection === "Scissors") ||
          (playerSelection === "Paper" && computerSelection === "Rock") ||
          (playerSelection === "Scissors" && computerSelection === "Paper")
      ) {
          return "You win! " + playerSelection + " beats " + computerSelection + ".";
      } else {
          return "You lose! " + computerSelection + " beats " + playerSelection + ".";
      }
  }

  const buttons = document.querySelectorAll("#choices button");
  buttons.forEach(button => {
      button.addEventListener("click", function() {
          const playerSelection = this.id;
          const computerSelection = computerPlay();
          const result = playRound(playerSelection, computerSelection);
          document.getElementById("result").textContent = result;
      });
  });
});