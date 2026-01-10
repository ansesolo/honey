import { formatters } from './utils/formatters.js';
import { authService } from './services/authService.js';
import { customerService } from './services/customerService.js';
import { productService } from './services/productService.js';
import { stockService } from './services/stockService.js';
import { formatErrorMessage } from './services/apiClient.js';

function app() {
return {
        currentView: 'login',
        isLoading: false,
        error: '',
        token: localStorage.getItem('authToken') || '',
        templates: { nav: '', dashboard: '',
                    customers: '', customersArray: '', customersModal: '', customersForm: '',
                    products: '', productsArray: '', productsModal: '', productsForm: '',
                    stocks: '', stocksArray: '', stocksForm: '',
                    footer: '', pagination: ''},
        credentials: { username: '', password: '' },
        items: [],
        selectedItem: null,
        hoveredItem: null,
        dictionaries: {units: [], categories: [], attributes: [], stockRules: [], flowers: [], catFlowers: []},

        showCustomerForm: false,
        showCustomerDetail: false,
        customerForm: { firstname: '', lastname: '', email: '', phone: '', street: '', postalCode: '', city: '' },

        showProductForm: false,
        showProductDetail: false,
        productForm: { name: '', unit: '', defaultPrice: '', category: '', flower: '', weight: ''},

        showStockForm: false,
        currentStockAction: null,
        stockActionForm: { productId: '', date: '', flower: '', quantity: 0, reason: '' },
        quickQuantities: {},
        products: [],

        currentPage: 1,
        itemsPerPage: 10,

        ...formatters,

        async init() {
            const token = localStorage.getItem('token');
            if (this.token) {
                await this.loadTemplates();
                await this.loadDictionaries();
                this.currentView = 'dashboard';
            } else {
                this.currentView = 'login';
            }
        },

        async loadDictionaries() {
            try {
                const [unitsRes, categoriesRes, attrsRes, flowersRes, catFlowers, stockRules] = await Promise.all([
                    productService.getEnumValues('units', this.token, () => this.handleLogout()),
                    productService.getEnumValues('categories', this.token, () => this.handleLogout()),
                    productService.getEnumValues('attributes', this.token, () => this.handleLogout()),
                    productService.getEnumValues('flowers', this.token, () => this.handleLogout()),
                    productService.getCategoriesWithFlower(this.token, () => this.handleLogout()),
                    stockService.getStockRules(this.token, () => this.handleLogout())
                ]);
                this.dictionaries.units = unitsRes;
                this.dictionaries.categories = categoriesRes;
                this.dictionaries.attributes = attrsRes;
                this.dictionaries.stockRules = stockRules;
                this.dictionaries.catFlowers = catFlowers;
                this.dictionaries.flowers = flowersRes;
            } catch (err) {
                console.error("Erreur lors du chargement des référentiels", err);
            }
        },

        getLabel(dictionaryName, id) {
            if (!id) return '-';
            const item = this.dictionaries[dictionaryName].find(i => i.id === id);
            return item ? item.label : id;
        },

        getDictionary(dictionaryName) {
            return this.dictionaries[dictionaryName];
        },

        async loadTemplates() {
            try {
                const paths = ['nav', 'dashboard', 'customers', 'customersForm', 'customersModal', 'customersArray', 'products', 'productsArray', 'productsForm', 'productsModal', 'stocks', 'stocksArray','stocksForm', 'footer', 'pagination'];
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
            if (view === 'stocks') await this.loadStocks();
        },

        async loadStocks() {
            this.isLoading = true;
            try {
                this.products = await productService.getAll(this.token, () => this.handleLogout());
                this.items = await stockService.getAll(this.token, () => this.handleLogout());
                this.currentPage = 1;
            } catch (err) {
                this.error = formatErrorMessage(err);
            } finally {
                this.isLoading = false;
            }
        },
        getEmptyStockForm() {
           return {productId: '', date: this.getTodayDate(), flower: '', quantity: 0, reason: '' };
        },
        closeStockForm() { this.showStockForm = false; this.selectedItem = null; this.error = ''},
        openStockAction(actionType) {
            this.selectedItem = null;
            this.currentStockAction = actionType;
            this.stockActionForm = this.getEmptyStockForm();
            this.showStockForm = true;
        },

        getTodayDate() {
            return new Date().toISOString().split('T')[0];
        },

        getActionTitle(actionType) {
            if (!actionType) return "";
            const rule = this.dictionaries.stockRules.find(r => r.id === actionType);
            return rule?.attributes?.label || actionType;
        },

        autoSelectProduct() {
            const filtered = this.getFilteredProducts(this.currentStockAction);

            if (filtered && filtered.length === 1) {
                this.$nextTick(() => {
                    this.stockActionForm.productId = filtered[0].publicId;
                });
            } else {
                this.stockActionForm.productId = '';
            }
        },

        getFilteredProducts(actionType) {
            if (!actionType) return [];
            const rule = this.dictionaries.stockRules.find(r => r.id === actionType);
            const allowedCategories = rule?.attributes?.categories;

            return this.products.filter(p => allowedCategories.includes(p.category));
        },

        async submitStockAction() {
            this.isLoading = true;
            try {
                const movementData = {
                    productId: this.stockActionForm.productId,
                    movementDate: this.stockActionForm.date,
                    quantity: parseFloat(this.stockActionForm.quantity),
                    flower: this.stockActionForm.flower,
                    type: this.currentStockAction,
                    reason: this.stockActionForm.reason || ''
                };

                await stockService.createMovement(movementData, this.token, () => this.handleLogout());
                await this.loadStocks();
                this.showStockForm = false;
                this.error = '';
            } catch (err) {
                this.error = formatErrorMessage(err);
            } finally {
                this.isLoading = false;
            }
        },

        getQuickQuantity(stockId) {
            return this.quickQuantities[stockId] || 1;
        },

        setQuickQuantity(stockId, value) {
            this.quickQuantities[stockId] = parseInt(value) || 1;
        },

        async quickAdjustStock(stock, direction) {
            const quantity = this.getQuickQuantity(stock.publicId);
            const actualQuantity = quantity * direction;
            const movementType = direction > 0 ? 'INCREASE_STOCK' : 'DECREASE_STOCK';

            this.isLoading = true;
            try {
                const movementData = {
                    productId: stock.product.publicId,
                    movementDate: this.getTodayDate(),
                    quantity: Math.abs(actualQuantity),
                    type: movementType,
                    reason: `Ajustement rapide`
                };

                await stockService.createMovement(movementData, this.token, () => this.handleLogout());
                await this.loadStocks();
                this.error = '';
            } catch (err) {
                this.error = formatErrorMessage(err);
            } finally {
                this.isLoading = false;
            }
        },

        viewStock(stock) {
            this.selectedStock = stock;
            this.showStockDetail = true;
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
        editProduct(product) {
            this.selectedItem = product;
            this.productForm = {
                name: product.name || '',
                unit: product.unit || '',
                defaultPrice: product.defaultPrice || 0,
                category: product.category || '',
                flower: product.flower || '',
                weight: product.weight || 0
            };
            this.showProductForm = true;
        },
        viewProduct(product) { this.selectedItem = product; this.showProductDetail = true; },
        closeProductForm() { this.showProductForm = false; this.selectedItem = null; this.error = ''},
        getEmptyProductForm() { return { name: '', unit: '', defaultPrice: '', category: '', flower: '', weight: '' }; },

        async deleteProduct(id) {
            if (!confirm('Supprimer ce produit ?')) return;
            try {
                await productService.delete(id, this.token, () => this.handleLogout());
                await this.loadProducts();
            } catch (err) {
                this.error = formatErrorMessage(err);
            }
        },

        shouldShowFlowerInProductForm() {
            return this.dictionaries.catFlowers.some(c => c.id === this.productForm.category);
        },

        shouldShowWeightInProductForm() {
            return 'FULL_JAR' === this.productForm.category;
        },

        async saveProduct() {
            this.isLoading = true;
            try {
                const productData = {
                    ...this.productForm,
                };
                if (this.selectedItem?.publicId) {
                    productData.publicId = this.selectedItem.publicId;
                }
                await productService.save(productData, this.token, () => this.handleLogout());
                await this.loadProducts();
                this.closeProductForm();
            } catch (err) {
                this.error = formatErrorMessage(err);
            } finally {
                this.isLoading = false;
            }
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