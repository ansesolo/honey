const API_BASE_URL = 'http://localhost:8042';

export async function fetchAPI(endpoint, options = {}, token, onLogout) {
    const defaultOptions = {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
            ...options.headers
        }
    };

    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        ...defaultOptions
    });

    if (response.status === 403 || response.status === 401) {
        onLogout();
        throw new Error('Session expirée, veuillez vous reconnecter.');
    }

    let data = null;
    if (response.status !== 204) {
        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
            data = await response.json();
        }
    }

    if (!response.ok) {
        const error = new Error(`Erreur ${response.status}`);
        error.payload = data;
        throw error;
    }

    return data;
}

export function formatErrorMessage(err) {
    const problem = err.payload;
    if (problem && problem.errors) {
        return Object.entries(problem.errors)
            .map(([field, message]) => `${field}: ${message}`)
            .join(' | ');
    }
    return problem?.detail || err.message || "Une erreur inconnue est survenue";
}