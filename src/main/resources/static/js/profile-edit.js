document.addEventListener('DOMContentLoaded', () => {
    const profileForm = document.getElementById('profileForm');

    if (!profileForm) return;

    profileForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const userId = document.getElementById('userId').value;
        const saveBtn = document.getElementById('saveBtn');

        const token = document.querySelector("meta[name='_csrf']")?.getAttribute("content");
        const header = document.querySelector("meta[name='_csrf_header']")?.getAttribute("content");

        const payload = {
            username: document.getElementById('username').value,
            fullName: document.getElementById('fullName').value,
            street: document.getElementById('street').value,
            city: document.getElementById('city').value,
            region: document.getElementById('region').value
        };

        saveBtn.disabled = true;
        saveBtn.innerText = 'Saving...';

        try {
            const headers = { 'Content-Type': 'application/json' };
            if (token && header) {
                headers[header] = token;
            }

            const response = await fetch(`/api/users/${userId}`, {
                method: 'PUT',
                headers: headers,
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                showAlert('Profile saved successfully!', 'success');
            } else {
                const errorData = await response.json().catch(() => ({}));
                showAlert(errorData.message || 'Error updating profile', 'error');
            }
        } catch (err) {
            showAlert('Network error or server unreachable', 'error');
        } finally {
            saveBtn.disabled = false;
            saveBtn.innerText = 'Save Changes';
        }
    });

    function showAlert(msg, type) {
        const alertBox = document.getElementById('statusAlert');
        if (!alertBox) return;

        alertBox.innerText = msg;
        alertBox.className = `alert alert-${type}`;
        alertBox.style.display = 'block';

        setTimeout(() => {
            alertBox.style.display = 'none';
        }, 4000);
    }
});