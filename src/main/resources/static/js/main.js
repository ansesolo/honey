import { formatters } from './utils/formatters.js';
import { authService } from './services/authService.js';
import { customerService } from './services/customerService.js';
import { productService } from './services/productService.js';
import { formatErrorMessage } from './services/apiClient.js';

function app() {
return {
        currentView: 'login',
        isLoading: false,
        error: '',
        token: localStorage.getItem('authToken') || '',
        templates: { nav: '', dashboard: '',
                    customers: '', customersArray: '', customersModal: '', customersForm: '',
                    products: '', productsArray: '', productsModal: '',
                    stocks: '',
                    footer: '', pagination: ''},
        credentials: { username: '', password: '' },
        items: [],
        selectedItem: null,
        hoveredItem: null,
        dictionaries: {units: [], categories: [], attributes: []},
        showCustomerForm: false,
        showCustomerDetail: false,
        customerForm: { firstname: '', lastname: '', email: '', phone: '', street: '', postalCode: '', city: '' },
        showProductForm: false,
        showProductDetail: false,
        productForm: { name: '', unit: '', defaultPrice: '', category: '', attributes: ''},
        currentPage: 1,
        itemsPerPage: 10,

        ...formatters,

        async init() {
            if (this.token) {
                await this.loadTemplates();
                await this.loadDictionaries();
                this.currentView = 'dashboard';
            }
        },

        async loadDictionaries() {
            try {
                const [unitsRes, categoriesRes, attrsRes] = await Promise.all([
                    productService.getEnumValues('units', this.token, () => this.handleLogout()),
                    productService.getEnumValues('categories', this.token, () => this.handleLogout()),
                    productService.getEnumValues('attributes', this.token, () => this.handleLogout())
                ]);
                this.dictionaries.units = unitsRes;
                this.dictionaries.categories = categoriesRes;
                this.dictionaries.attributes = attrsRes;
            } catch (err) {
                console.error("Erreur lors du chargement des référentiels", err);
            }
        },

        getLabel(dictionaryName, id) {
            const item = this.dictionaries[dictionaryName].find(i => i.id === id);
            return item ? item.label : id;
        },

        async loadTemplates() {
            try {
                const paths = ['nav', 'dashboard', 'customers', 'customersForm', 'customersModal', 'customersArray', 'products', 'productsArray', 'productsModal', 'stocks', 'footer', 'pagination'];
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
            this.error = '';
            this.currentView = view;
            if (view === 'customers') await this.loadCustomers();
            if (view === 'products') await this.loadProducts();
        },

        async loadProducts() {
            this.isLoading = true;
            try {
                this.items = await productService.getAll(this.token, () => this.handleLogout());
                this.currentPage = 1;
            } catch (err) {
                this.error = formatErrorMessage(err);
            } finally {
                this.isLoading = false;
            }
        },

        openProductForm() { this.selectedItem = null; this.productForm = this.getEmptyProductForm(); this.showProductForm = true; },
        editProduct(product) { this.selectedItem = product; this.productForm = { ...product }; this.showProductForm = true; },
        viewProduct(product) { this.selectedItem = product; this.showProductDetail = true; },
        closeProductForm() { this.showProductForm = false; this.selectedItem = null; this.error = ''},
        getEmptyProductForm() { return { name: '', unit: '', defaultPrice: '', category: '', attributes: '' }; },

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