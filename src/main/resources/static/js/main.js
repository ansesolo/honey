import { formatters } from './utils/formatters.js';
import { authService } from './services/authService.js';
import { customerService } from './services/customerService.js';
import { formatErrorMessage } from './services/apiClient.js';

function app() {
    return {
        currentView: 'login',
        isLoading: false,
        error: '',
        token: localStorage.getItem('authToken') || '',
        templates: { nav: '', dashboard: '', customers: '', products: '', stocks: '', footer: '' },
        credentials: { username: '', password: '' },
        items: [],
        selectedItem: null,
        hoveredItem: null,
        showCustomerForm: false,
        showCustomerDetail: false,
        customerForm: { firstname: '', lastname: '', email: '', phone: '', street: '', postalCode: '', city: '' },
        currentPage: 1,
        itemsPerPage: 10,

        ...formatters,

        async init() {
            if (this.token) {
                await this.loadTemplates();
                this.currentView = 'dashboard';
            }
        },

        async loadTemplates() {
            try {
                const paths = ['nav', 'dashboard', 'customers', 'products', 'stocks', 'footer'];
                const contents = await Promise.all(paths.map(p => fetch(`includes/${p}.html`).then(r => r.text())));
                paths.forEach((p, i) => this.templates[p] = contents[i]);
            } catch (error) {
                console.error("Erreur templates:", error);
            }
        },

        async handleLogin() {
            this.isLoading = true;
            this.error = '';
            try {
                this.token = await authService.login(this.credentials.username, this.credentials.password);
                localStorage.setItem('authToken', this.token);
                await this.loadTemplates();
                this.currentView = 'dashboard';
                this.credentials = { username: '', password: '' };
            } catch (err) {
                this.error = err.message;
            } finally {
                this.isLoading = false;
            }
        },

        handleLogout() {
            this.token = '';
            localStorage.removeItem('authToken');
            this.currentView = 'login';
            this.items = [];
        },

        async navigateTo(view) {
            this.currentView = view;
            if (view === 'customers') await this.loadCustomers();
        },

        async loadCustomers() {
            this.isLoading = true;
            try {
                this.items = await customerService.getAll(this.token, () => this.handleLogout());
                this.currentPage = 1;
            } catch (err) {
                this.error = formatErrorMessage(err);
            } finally {
                this.isLoading = false;
            }
        },

        async saveCustomer() {
            this.isLoading = true;
            try {
                await customerService.save(this.customerForm, this.token, () => this.handleLogout());
                await this.loadCustomers();
                this.closeCustomerForm();
            } catch (err) {
                this.error = formatErrorMessage(err);
            } finally {
                this.isLoading = false;
            }
        },

        async deleteCustomer(id) {
            if (!confirm('Supprimer ce client ?')) return;
            try {
                await customerService.delete(id, this.token, () => this.handleLogout());
                await this.loadCustomers();
            } catch (err) {
                this.error = formatErrorMessage(err);
            }
        },

        openCustomerForm() { this.selectedItem = null; this.customerForm = this.getEmptyCustomerForm(); this.showCustomerForm = true; },
        editCustomer(customer) { this.selectedItem = customer; this.customerForm = { ...customer }; this.showCustomerForm = true; },
        viewCustomer(customer) { this.selectedItem = customer; this.showCustomerDetail = true; },
        closeCustomerForm() { this.showCustomerForm = false; this.selectedItem = null; this.error = ''},
        getEmptyCustomerForm() { return { firstname: '', lastname: '', email: '', phone: '', street: '', postalCode: '', city: '' }; },

        get paginatedItems() {
            const start = (this.currentPage - 1) * this.itemsPerPage;
            return this.items.slice(start, start + this.itemsPerPage);
        },
        get totalPages() { return Math.ceil(this.items.length / this.itemsPerPage); },
        get hasNextPage() { return this.currentPage < this.totalPages; },
        get hasPreviousPage() { return this.currentPage > 1; },
        nextPage() { if (this.hasNextPage) this.currentPage++; },
        previousPage() { if (this.hasPreviousPage) this.currentPage--; },
        goToPage(page) { if (page >= 1 && page <= this.totalPages) this.currentPage = page; },
        get pageNumbers() {
            const pages = [];
            for (let i = 1; i <= this.totalPages; i++) pages.push(i);
            return pages;
        }
    };
}

window.app = app;