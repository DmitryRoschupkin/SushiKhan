async function openIngredientModal(id) {
    const modal = document.getElementById('ingredientModal');
    const modalBody = document.getElementById('modalBody');

    modal.style.display = 'flex';
    modalBody.innerHTML = '<p>Loading details...</p>';

    try {
        const response = await fetch(`/api/sushi/ingredients/${id}`);
        if (!response.ok) {
            modalBody.innerHTML = `<p class="error">Ingredient <b>${id}</b> not found.</p>`;
            return;
        }

        const data = await response.json();
        const imageSrc = data.picture ? data.picture : '/images/ingredients/default.jpeg';
        modalBody.innerHTML = `
            <div class="ingredient-modal-card">
                <div class="img-container">
                    <img src="${imageSrc}" alt="${data.name}" class="ingredient-img" onerror="this.src='/images/ingredients/default.jpeg';">
                </div>
                <div class="info-card">
                    <p><strong>ID:</strong> ${data.id}</p>
                    <p><strong>Name:</strong> ${data.name}</p>
                    <p><strong>Type:</strong> <span class="badge">${data.type}</span></p>
                </div>
            </div>
        `;
    } catch (err) {
        modalBody.innerHTML = `<p class="error">Failed to connect to the server.</p>`;
    }
}

function closeIngredientModal() {
    document.getElementById('ingredientModal').style.display = 'none';
}

window.addEventListener('click', (event) => {
    const modal = document.getElementById('ingredientModal');
    if (event.target === modal) {
        closeIngredientModal();
    }
});