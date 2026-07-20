document.addEventListener('DOMContentLoaded', () => {
    const token = document.getElementById('security-context').getAttribute('data-token');

    const formTitle = document.getElementById('form-title');
    const formSection = document.querySelector('.form-section');

    const inputId = document.getElementById('ingId');
    const inputName = document.getElementById('ingName');
    const selectType = document.getElementById('ingType');
    const inputPicture = document.getElementById('ingPicture');

    const btnSave = document.getElementById('btnSave');
    const btnCancel = document.getElementById('btnCancel');

    let isEditMode = false;

    const getHeaders = () => ({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    });

    btnSave.addEventListener('click', () => {
        const payload = {
            id: inputId.value.trim(),
            name: inputName.value.trim(),
            type: selectType.value,
            picture: inputPicture.value.trim()
        };

        if (!payload.id || !payload.name) {
            alert('Please fill out all fields.');
            return;
        }

        const url = isEditMode ? `/api/ingredients/${payload.id}` : '/api/ingredients';
        const method = isEditMode ? 'PUT' : 'POST';

        fetch(url, {
            method: method,
            headers: getHeaders(),
            body: JSON.stringify(payload)
        })
            .then(response => {
                if (response.ok) {
                    window.location.reload();
                } else {
                    alert(`Operation failed. Status: ${response.status}`);
                }
            })
            .catch(err => console.error('Error saving ingredient:', err));
    });

    document.querySelector('table').addEventListener('click', (e) => {
        if (e.target.classList.contains('btn-edit')) {
            isEditMode = true;
            formTitle.textContent = 'Edit Ingredient';

            inputId.value = e.target.getAttribute('data-id');
            inputId.disabled = true;
            inputName.value = e.target.getAttribute('data-name');
            selectType.value = e.target.getAttribute('data-type');
            inputPicture.value = e.target.getAttribute('data-picture') || '';

            btnCancel.style.display = 'inline-block';

            formTitle.scrollIntoView({ behavior: 'smooth', block: 'start' });
            formSection.classList.remove('form-section--highlight');
            void formSection.offsetWidth;
            formSection.classList.add('form-section--highlight');
        }

        if (e.target.classList.contains('btn-delete')) {
            const id = e.target.getAttribute('data-id');
            if (confirm(`Are you sure you want to delete ingredient "${id}"?`)) {
                fetch(`/api/ingredients/${id}`, {
                    method: 'DELETE',
                    headers: getHeaders()
                })
                    .then(response => {
                        if (response.ok) {
                            window.location.reload();
                        } else {
                            alert(`Delete failed. Status: ${response.status}`);
                        }
                    })
                    .catch(err => console.error('Error deleting ingredient:', err));
            }
        }
    });

    btnCancel.addEventListener('click', () => {
        isEditMode = false;
        formTitle.textContent = 'Create New Ingredient';
        inputId.value = '';
        inputId.disabled = false;
        inputName.value = '';
        inputPicture.value = '';
        selectType.selectedIndex = 0;
        btnCancel.style.display = 'none';
    });
});