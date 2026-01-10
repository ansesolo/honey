import { fetchAPI } from './apiClient.js';

export const productService = {
    async getAll(token, logoutCb, filters = {}) {
        const queryParams = new URLSearchParams(filters).toString();
        const url = `/api/products${queryParams ? `?${queryParams}` : ''}`;

        return fetchAPI(url, {}, token, logoutCb);
    },
    async save(product, token, logoutCb) {
        const method = product.publicId ? 'PUT' : 'POST';
        const endpoint = product.publicId ? `/api/products/${product.publicId}` : '/api/products';
        return fetchAPI(endpoint, {
            method: method,
            body: JSON.stringify(product)
        }, token, logoutCb);
    },
    async delete(id, token, logoutCb) {
        return fetchAPI(`/api/products/${id}`, { method: 'DELETE' }, token, logoutCb);
    },
    async getEnumValues(id, token, logoutCb) {
        return fetchAPI(`/api/products/enum/${id}`, { method: 'GET' }, token, logoutCb);
    },
    async getCategoriesWithFlower(token, logoutCb) {
        return fetchAPI(`/api/products/categories/hasflowers`, { method: 'GET' }, token, logoutCb);
    }
};