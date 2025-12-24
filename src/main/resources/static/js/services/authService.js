const API_BASE_URL = 'http://localhost:8042';

export const authService = {
    async login(username, password) {
        const credentials = btoa(`${username}:${password}`);
        const response = await fetch(`${API_BASE_URL}/api/login`, {
            method: 'POST',
            headers: {
                'Authorization': `Basic ${credentials}`
            }
        });

        if (!response.ok) {
            throw new Error('Identifiants incorrects');
        }

        return await response.text();
    }
};