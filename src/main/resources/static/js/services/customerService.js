import { fetchAPI } from './apiClient.js';

export const customerService = {
    async getAll(token, logoutCb) {
        return fetchAPI('/api/customers', {}, token, logoutCb);
    },
    async save(customer, token, logoutCb) {
        const method = customer.publicId ? 'PUT' : 'POST';
        const endpoint = customer.publicId ? `/api/customers/${customer.publicId}` : '/api/customers';
        return fetchAPI(endpoint, {
            method: method,
            body: JSON.stringify(customer)
        }, token, logoutCb);
    },
    async delete(id, token, logoutCb) {
        return fetchAPI(`/api/customers/${id}`, { method: 'DELETE' }, token, logoutCb);
    }
};