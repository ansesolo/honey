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
        if (contentType && (contentType.includes("application/json") || contentType.includes("application/problem+json"))) {
            data = await response.json();
        }
    }

    if (!response.ok) {
        const error = new Error(`Erreur ${response.status}`);
        console.log("ALF data ", data);
        error.payload = data;
        console.log("ALF error ", error);
        throw error;
    }

    return data;
}

export function formatErrorMessage(err) {
    const problem = err.payload;

    if (!problem) return err.message || "Unexpected error occurred";

    const parts = [];

    if (problem.title) parts.push(problem.title);
    if (problem.detail) parts.push(problem.detail);
    if (problem.errors) {
        if (typeof problem.errors === 'object' && !Array.isArray(problem.errors)) {
            const formattedFields = Object.entries(problem.errors)
                .map(([field, message]) => `${field}: ${message}`)
                .join(' | ');
            parts.push(formattedFields);
        } else if (typeof problem.errors === 'string') {
            parts.push(problem.errors);
        }
    }

    return parts.join(' - ');
}