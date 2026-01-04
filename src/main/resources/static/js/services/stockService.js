import { fetchAPI } from './apiClient.js';

export const stockService = {
  async getAll(token, onUnauthorized) {
    return fetchAPI('/api/stocks',  { method: 'GET' }, token, onUnauthorized);
  },

  async getByProduct(productId, token, onUnauthorized) {
    return fetchAPI(`/stocks/product/${productId}`,  { method: 'GET' }, token, onUnauthorized);
  },

  async createMovement(movementData, token, onUnauthorized) {
    return fetchAPI('/api/stocks/movement', {
        method: 'POST',
        body: JSON.stringify(movementData)
    },  token, onUnauthorized);
  },

  async getStockRules(token, logoutCb) {
      return fetchAPI('/api/stocks/rules', { method: 'GET' }, token, logoutCb);
  }
};