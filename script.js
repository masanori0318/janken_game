document.getElementById('gameForm').addEventListener('submit', playGame);

function playGame(event) {
    event.preventDefault(); // フォームのデフォルトの送信を防止

    // プレイヤーの選択を取得
    const playerSelection = document.getElementById('choice').value.trim().toLowerCase();

    // フォームデータを作成
    const formData = new FormData();
    formData.append('choice', playerSelection);

    // POSTリクエストを送信
    fetch('http://localhost:8000/', {
        method: 'POST',
        body: formData  // フォームデータをリクエストボディにセット
    })
    .then(response => response.text())
    .then(result => {
        document.getElementById('result').innerHTML = result;  // 結果を表示
    })
    .catch(error => console.error('Error:', error));
}