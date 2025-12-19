    function app() {
        return {
            // État de l'application
            currentView: 'login',
            isLoading: false,
            error: '',
            token: localStorage.getItem('authToken') || '',

            // Credentials login
            credentials: {
                username: '',
                password: ''
            },

            // Données clients
            customers: [],
            selectedCustomer: null,
            hoveredCustomer: null,
            showCustomerForm: false,
            showCustomerDetail: false,
            customerForm: {
                firstname: '',
                lastname: '',
                email: '',
                phone: '',
                street: '',
                postalCode: '',
                city: ''
            },

            // Configuration API
            API_BASE_URL: 'http://localhost:8042',

            // Initialisation
            init() {
                if (this.token) {
                    this.currentView = 'dashboard';
                }
            },

            // === AUTHENTICATION ===
            async handleLogin() {
                this.isLoading = true;
                this.error = '';

                try {
                    const credentials = btoa(`${this.credentials.username}:${this.credentials.password}`);
                    const response = await fetch(`${this.API_BASE_URL}/api/login`, {
                        method: 'POST',
                        headers: {
                            'Authorization': `Basic ${credentials}`
                        }
                    });

                    if (!response.ok) {
                        throw new Error('Identifiants incorrects');
                    }

                    this.token = await response.text();
                    localStorage.setItem('authToken', this.token);
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
                this.customers = [];
            },

            // === NAVIGATION ===
            async navigateTo(view) {
                this.currentView = view;
                this.error = '';
                this.showCustomerForm = false;
                this.showCustomerDetail = false;

                if (view === 'customers') {
                    await this.loadCustomers();
                }
            },

            // === API CALLS ===
            async fetchAPI(endpoint, options = {}) {
                const defaultOptions = {
                    headers: {
                        'Authorization': `Bearer ${this.token}`,
                        'Content-Type': 'application/json',
                        ...options.headers
                    }
                };

                const response = await fetch(`${this.API_BASE_URL}${endpoint}`, {
                    ...options,
                    ...defaultOptions
                });

                if (response.status === 403 || response.status === 401) {
                    this.handleLogout();
                    throw new Error('Session expirée, veuillez vous reconnecter');
                }

                if (!response.ok) {
                    throw new Error(`Erreur ${response.status}: ${response.statusText}`);
                }

                return response;
            },

            // === CUSTOMERS CRUD ===
            async loadCustomers() {
                this.isLoading = true;
                this.error = '';

                try {
                    const response = await this.fetchAPI('/api/customers');
                    this.customers = await response.json();
                } catch (err) {
                    this.error = err.message;
                } finally {
                    this.isLoading = false;
                }
            },

            async saveCustomer() {
                this.isLoading = true;
                this.error = '';

                try {
                    const method = this.selectedCustomer?.id ? 'PUT' : 'POST';
                    const endpoint = this.selectedCustomer?.id
                        ? `/api/customers/${this.selectedCustomer.id}`
                        : '/api/customers';

                    await this.fetchAPI(endpoint, {
                        method: method,
                        body: JSON.stringify(this.customerForm)
                    });

                    await this.loadCustomers();
                    this.closeCustomerForm();
                } catch (err) {
                    this.error = err.message;
                } finally {
                    this.isLoading = false;
                }
            },

            async deleteCustomer(id) {
                if (!confirm('Êtes-vous sûr de vouloir supprimer ce client ?')) {
                    return;
                }

                this.isLoading = true;
                this.error = '';

                try {
                    await this.fetchAPI(`/api/customers/${id}`, {
                        method: 'DELETE'
                    });

                    await this.loadCustomers();
                } catch (err) {
                    this.error = err.message;
                } finally {
                    this.isLoading = false;
                }
            },

            // === FORM MANAGEMENT ===
            openCustomerForm() {
                this.selectedCustomer = null;
                this.customerForm = this.getEmptyCustomerForm();
                this.showCustomerForm = true;
            },

            editCustomer(customer) {
                this.selectedCustomer = customer;
                this.customerForm = { ...customer };
                this.showCustomerForm = true;
            },

            viewCustomer(customer) {
                this.selectedCustomer = customer;
                this.showCustomerDetail = true;
            },

            closeCustomerForm() {
                this.showCustomerForm = false;
                this.selectedCustomer = null;
                this.customerForm = this.getEmptyCustomerForm();
            },

            getEmptyCustomerForm() {
                return {
                    firstname: '',
                    lastname: '',
                    email: '',
                    phone: '',
                    street: '',
                    postalCode: '',
                    city: ''
                };
            },

            // === HELPERS ===
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
    }
