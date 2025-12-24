export const formatters = {
    formatDate(dateString) {
        if (!dateString) return '-';
        return new Date(dateString).toLocaleDateString('fr-FR');
    },
    formatDateTime(dateString) {
        if (!dateString) return '-';
        return new Date(dateString).toLocaleString('fr-FR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        });
    },
    formatAddress(customer) {
        const parts = [
            customer.street,
            customer.postalCode,
            customer.city
        ].filter(Boolean);
        return parts.length > 0 ? parts.join(', ') : '-';
    }
};