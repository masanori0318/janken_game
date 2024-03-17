document.getElementById('gameForm').addEventListener('submit', function(event) {
  event.preventDefault();
  var choice = document.getElementById('choice').value;
  play(choice);
});

function play(playerChoice) {
  var xhr = new XMLHttpRequest();
  xhr.open('POST', '/', true);
  xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
  xhr.onreadystatechange = function() {
      if (xhr.readyState === XMLHttpRequest.DONE) {
          if (xhr.status === 200) {
              document.getElementById('result').innerHTML = xhr.responseText;
          } else {
              console.error('Request failed:', xhr.statusText);
          }
      }
  };
  xhr.send('choice=' + encodeURIComponent(playerChoice));
}