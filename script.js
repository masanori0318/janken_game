document.getElementById('gameForm').addEventListener('submit', playGame);

function playGame(event) {
    event.preventDefault(); // Prevent form submission
    const playerSelection = document.getElementById('choice').value.trim().toLowerCase();
    const requestOptions = {
        method: 'POST',
        body: JSON.stringify({ choice: playerSelection }),
        headers: {
            'Content-Type': 'application/json'
        }
    };
    fetch('/', requestOptions)
        .then(response => response.text())
        .then(result => {
            document.getElementById('result').innerHTML = result;
        })
        .catch(error => console.error('Error:', error));
}