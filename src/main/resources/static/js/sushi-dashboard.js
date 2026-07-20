document.addEventListener('DOMContentLoaded', () => {
    loadRecentSushi();
});

async function fetchIngredientById(id) {
    const ingId = id || document.getElementById('ingIdInput').value.trim();
    const resultContainer = document.getElementById('ingResult');

    if (!ingId) {
        resultContainer.innerHTML = '<p class="error">Enter ingredient ID</p>';
        return;
    }

    document.getElementById('ingIdInput').value = ingId;

    try {
        const response = await fetch(`/api/sushi/ingredients/${ingId}`);
        if (!response.ok) {
            resultContainer.innerHTML = `<p class="error">Ingredient <b>${ingId}</b> not found</p>`;
            return;
        }

        const data = await response.json();
        resultContainer.innerHTML = `
            <div class="info-card">
                <p><strong>ID:</strong> ${data.id}</p>
                <p><strong>Name:</strong> ${data.name}</p>
                <p><strong>Type:</strong> <span class="badge">${data.type}</span></p>
            </div>
        `;
    } catch (err) {
        resultContainer.innerHTML = `<p class="error">Connection to server has been failed</p>`;
    }
}

async function loadRecentSushi() {
    const container = document.getElementById('recentSushiContainer');
    container.innerHTML = 'Loading...';

    try {
        const response = await fetch('/api/sushi/recent');
        if (!response.ok) throw new Error();

        const sushiList = await response.json();

        if (sushiList.length === 0) {
            container.innerHTML = '<p>Sushi has not been created yet</p>';
            return;
        }

        container.innerHTML = sushiList.map(sushi => `
            <div class="sushi-item">
                <div>
                    <strong>${sushi.name}</strong>
                    <div class="ingredients-list">
                        ${sushi.ingredients ? sushi.ingredients.map(i => i.name).join(', ') : 'Without ingredients :)'}
                    </div>
                </div>
                <span class="badge">#${sushi.id}</span>
            </div>
        `).join('');
    } catch (err) {
        container.innerHTML = `<p class="error">Sushi has not been loaded</p>`;
    }
}