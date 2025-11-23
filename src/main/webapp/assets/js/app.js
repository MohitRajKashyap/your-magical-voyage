class MagicalVoyageApp {
    constructor() {
        this.init();
    }

    init() {
        this.initializeEventListeners();
        this.initializeComponents();
        this.checkAuthStatus();
    }

    initializeEventListeners() {
        document.addEventListener('DOMContentLoaded', () => {
            this.handleFormSubmissions();
            this.handleSearchInteractions();
            this.handleModalOperations();
            this.handleTabSwitching();
        });
    }

    initializeComponents() {
        this.initializeDatePickers();
        this.initializeAutoComplete();
        this.initializeNotifications();
    }

    initializeDatePickers() {
        const dateInputs = document.querySelectorAll('input[type="date"]');
        dateInputs.forEach(input => {
            const today = new Date().toISOString().split('T')[0];
            input.min = today;

            if (input.id === 'checkInDate') {
                input.addEventListener('change', (e) => {
                    const checkOutInput = document.getElementById('checkOutDate');
                    if (checkOutInput) {
                        checkOutInput.min = e.target.value;
                    }
                });
            }
        });
    }

    initializeAutoComplete() {
        const searchInputs = document.querySelectorAll('.search-autocomplete');
        searchInputs.forEach(input => {
            input.addEventListener('input', this.debounce((e) => {
                this.handleAutoComplete(e.target);
            }, 300));
        });
    }

    initializeNotifications() {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(alert => {
            setTimeout(() => {
                alert.style.opacity = '0';
                setTimeout(() => alert.remove(), 300);
            }, 5000);
        });
    }

    handleFormSubmissions() {
        const forms = document.querySelectorAll('form');
        forms.forEach(form => {
            form.addEventListener('submit', (e) => {
                const submitBtn = form.querySelector('button[type="submit"]');
                if (submitBtn) {
                    submitBtn.disabled = true;
                    submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
                }
            });
        });
    }

    handleSearchInteractions() {
        const searchTabs = document.querySelectorAll('.search-tab');
        const searchForms = document.querySelectorAll('.search-form-content');

        searchTabs.forEach(tab => {
            tab.addEventListener('click', () => {
                const target = tab.getAttribute('data-target');

                searchTabs.forEach(t => t.classList.remove('active'));
                tab.classList.add('active');

                searchForms.forEach(form => form.classList.add('d-none'));
                document.getElementById(target).classList.remove('d-none');
            });
        });
    }

    handleModalOperations() {
        const modalTriggers = document.querySelectorAll('[data-modal]');
        modalTriggers.forEach(trigger => {
            trigger.addEventListener('click', () => {
                const modalId = trigger.getAttribute('data-modal');
                const modal = document.getElementById(modalId);
                if (modal) {
                    this.openModal(modal);
                }
            });
        });

        const closeButtons = document.querySelectorAll('.modal-close, .modal-overlay');
        closeButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                if (e.target === button || button.classList.contains('modal-close')) {
                    this.closeModal(button.closest('.modal'));
                }
            });
        });
    }

    openModal(modal) {
        modal.classList.add('active');
        document.body.style.overflow = 'hidden';
    }

    closeModal(modal) {
        modal.classList.remove('active');
        document.body.style.overflow = 'auto';
    }

    handleTabSwitching() {
        const tabButtons = document.querySelectorAll('.tab-button');
        const tabContents = document.querySelectorAll('.tab-content');

        tabButtons.forEach(button => {
            button.addEventListener('click', () => {
                const tabId = button.getAttribute('data-tab');

                tabButtons.forEach(btn => btn.classList.remove('active'));
                button.classList.add('active');

                tabContents.forEach(content => content.classList.remove('active'));
                document.getElementById(tabId).classList.add('active');
            });
        });
    }

    checkAuthStatus() {
        const authRequiredPages = ['/dashboard', '/admin', '/agent', '/traveler'];
        const currentPath = window.location.pathname;

        if (authRequiredPages.some(page => currentPath.includes(page))) {
            this.verifyAuthentication();
        }
    }

    verifyAuthentication() {
        const token = localStorage.getItem('authToken');
        if (!token) {
            window.location.href = '/login';
        }
    }

    debounce(func, wait) {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    }

    showLoading() {
        const loadingEl = document.createElement('div');
        loadingEl.className = 'loading-overlay';
        loadingEl.innerHTML = '<div class="spinner"></div>';
        document.body.appendChild(loadingEl);
    }

    hideLoading() {
        const loadingEl = document.querySelector('.loading-overlay');
        if (loadingEl) {
            loadingEl.remove();
        }
    }

    showToast(message, type = 'info') {
        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;
        toast.innerHTML = `
            <div class="toast-content">
                <i class="fas fa-${this.getToastIcon(type)}"></i>
                <span>${message}</span>
            </div>
            <button class="toast-close">&times;</button>
        `;

        document.body.appendChild(toast);

        setTimeout(() => toast.classList.add('show'), 100);

        const closeBtn = toast.querySelector('.toast-close');
        closeBtn.addEventListener('click', () => this.hideToast(toast));

        setTimeout(() => this.hideToast(toast), 5000);
    }

    hideToast(toast) {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 300);
    }

    getToastIcon(type) {
        const icons = {
            success: 'check-circle',
            error: 'exclamation-circle',
            warning: 'exclamation-triangle',
            info: 'info-circle'
        };
        return icons[type] || 'info-circle';
    }

    formatCurrency(amount) {
        return new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        }).format(amount);
    }

    formatDate(dateString) {
        return new Date(dateString).toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric'
        });
    }

    formatDateTime(dateString) {
        return new Date(dateString).toLocaleString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    }
}

const app = new MagicalVoyageApp();

class FlightBookingManager {
    constructor() {
        this.selectedFlight = null;
        this.passengers = 1;
    }

    selectFlight(flightId) {
        this.selectedFlight = flightId;
        this.updateBookingSummary();
    }

    updatePassengers(count) {
        this.passengers = count;
        this.updateBookingSummary();
    }

    updateBookingSummary() {
        if (!this.selectedFlight) return;

        const flightElement = document.querySelector(`[data-flight-id="${this.selectedFlight}"]`);
        const price = parseFloat(flightElement.getAttribute('data-price'));
        const total = price * this.passengers;

        document.getElementById('bookingSummary').innerHTML = `
            <div class="booking-summary">
                <h4>Booking Summary</h4>
                <div class="summary-details">
                    <div class="summary-row">
                        <span>Flight Price:</span>
                        <span>${app.formatCurrency(price)}</span>
                    </div>
                    <div class="summary-row">
                        <span>Passengers:</span>
                        <span>${this.passengers}</span>
                    </div>
                    <div class="summary-row total">
                        <span>Total:</span>
                        <span>${app.formatCurrency(total)}</span>
                    </div>
                </div>
            </div>
        `;
    }

    proceedToPayment() {
        if (!this.selectedFlight || this.passengers < 1) {
            app.showToast('Please select a flight and number of passengers', 'error');
            return;
        }

        app.showLoading();

        setTimeout(() => {
            app.hideLoading();
            document.getElementById('paymentModal').classList.add('active');
        }, 1000);
    }
}

const flightManager = new FlightBookingManager();