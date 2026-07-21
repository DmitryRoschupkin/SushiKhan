document.addEventListener('DOMContentLoaded', () => {
    const securityContext = document.getElementById('security-context');
    const token = securityContext ? securityContext.getAttribute('data-token') : '';

    const getHeaders = () => {
        const headers = { 'Content-Type': 'application/json' };
        if (token) headers['Authorization'] = `Bearer ${token}`;
        return headers;
    };

    document.querySelector('table').addEventListener('click', (e) => {
        if (e.target.classList.contains('btn-toggle-status')) {
            const userId = e.target.getAttribute('data-id');
            const currentStatus = e.target.getAttribute('data-enabled') === 'true';
            const newStatus = !currentStatus;
            const actionName = newStatus ? 'unban' : 'ban';

            if (confirm(`Are you sure you want to ${actionName} user #${userId}?`)) {
                fetch(`/api/users/${userId}/status`, {
                    method: 'PATCH',
                    headers: getHeaders(),
                    body: JSON.stringify({ enabled: newStatus })
                })
                    .then(async response => {
                        if (response.ok) {
                            window.location.reload();
                        } else {
                            const errorData = await response.json();
                            alert(errorData.message || `Operation failed. Status: ${response.status}`);
                        }
                    })
                    .catch(err => console.error('Error toggling status:', err));
            }
        }

        if (e.target.classList.contains('btn-delete')) {
            const userId = e.target.getAttribute('data-id');
            const username = e.target.getAttribute('data-username');

            if (confirm(`Are you sure you want to delete user "${username}" (#${userId})?`)) {
                fetch(`/api/users/${userId}`, {
                    method: 'DELETE',
                    headers: getHeaders()
                })
                    .then(async response => {
                        if (response.ok) {
                            window.location.reload();
                        } else {
                            const errorData = await response.json();
                            alert(errorData.message || `Delete failed. Status: ${response.status}`);
                        }
                    })
                    .catch(err => console.error('Error deleting user:', err));
            }
        }
    });
});