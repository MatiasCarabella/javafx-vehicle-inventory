// Global state
let vehicles = [];
let editingVehicleId = null;

// Initialize application when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    setupSearchFilter();
    setupModalListeners();
});

/**
 * Called by Java backend to update the vehicles list.
 * @param {string} vehiclesJson - JSON string containing array of vehicles
 */
function updateVehicles(vehiclesJson) {
    vehicles = JSON.parse(vehiclesJson);
    renderVehicles();
    updateStats();
}

/**
 * Renders the vehicles grid with cards for each vehicle.
 * @param {Array} filteredVehicles - Optional filtered list of vehicles to render
 */
function renderVehicles(filteredVehicles = null) {
    const grid = document.getElementById('vehiclesGrid');
    const emptyState = document.getElementById('emptyState');
    const vehiclesToRender = filteredVehicles || vehicles;
    
    if (vehiclesToRender.length === 0) {
        grid.innerHTML = '';
        emptyState.classList.add('show');
        return;
    }
    
    emptyState.classList.remove('show');
    
    grid.innerHTML = vehiclesToRender.map(vehicle => `
        <div class="vehicle-card">
            <div class="vehicle-header">
                <div class="vehicle-title">
                    <div class="vehicle-make">${escapeHtml(vehicle.make)}</div>
                    <div class="vehicle-model">${escapeHtml(vehicle.model)}</div>
                </div>
                <div class="vehicle-actions">
                    <button class="icon-btn edit" onclick="editVehicle(${vehicle.id})" title="Edit">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </button>
                    <button class="icon-btn delete" onclick="deleteVehicle(${vehicle.id})" title="Delete">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <polyline points="3 6 5 6 21 6" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </button>
                </div>
            </div>
            <div class="vehicle-details">
                <div class="detail-item">
                    <span class="detail-label">Year</span>
                    <span class="detail-value">${vehicle.year}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Color</span>
                    <span class="detail-value">${escapeHtml(vehicle.color)}</span>
                </div>
                <div class="detail-item vehicle-price">
                    <span class="detail-label">Price</span>
                    <span class="detail-value">$${formatPrice(vehicle.price)}</span>
                </div>
            </div>
        </div>
    `).join('');
}

/**
 * Updates the dashboard statistics (total vehicles and total value).
 */
function updateStats() {
    const totalVehicles = vehicles.length;
    const totalValue = vehicles.reduce((sum, v) => sum + v.price, 0);
    
    document.getElementById('totalVehicles').textContent = totalVehicles;
    document.getElementById('totalValue').textContent = '$' + formatPrice(totalValue);
}

/**
 * Sets up the search filter functionality.
 */
function setupSearchFilter() {
    const searchInput = document.getElementById('searchInput');
    searchInput.addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        if (!query) {
            renderVehicles();
            return;
        }
        
        const filtered = vehicles.filter(v => 
            v.make.toLowerCase().includes(query) ||
            v.model.toLowerCase().includes(query) ||
            v.color.toLowerCase().includes(query) ||
            v.year.toString().includes(query)
        );
        renderVehicles(filtered);
    });
}

/**
 * Shows the modal dialog for adding a new vehicle.
 */
function showAddVehicleModal() {
    editingVehicleId = null;
    document.getElementById('modalTitle').textContent = 'Add Vehicle';
    document.getElementById('vehicleForm').reset();
    document.getElementById('vehicleModal').classList.add('show');
}

/**
 * Opens the edit modal for a specific vehicle.
 * @param {number} id - The vehicle ID to edit
 */
function editVehicle(id) {
    const vehicle = vehicles.find(v => v.id === id);
    if (!vehicle) return;
    
    editingVehicleId = id;
    document.getElementById('modalTitle').textContent = 'Edit Vehicle';
    document.getElementById('make').value = vehicle.make;
    document.getElementById('model').value = vehicle.model;
    document.getElementById('year').value = vehicle.year;
    document.getElementById('color').value = vehicle.color;
    document.getElementById('price').value = vehicle.price;
    document.getElementById('vehicleModal').classList.add('show');
}

let deleteVehicleId = null;

/**
 * Shows the confirmation dialog for deleting a vehicle.
 * @param {number} id - The vehicle ID to delete
 */
function deleteVehicle(id) {
    deleteVehicleId = id;
    document.getElementById('confirmModal').classList.add('show');
}

/**
 * Confirms and executes the vehicle deletion.
 */
function confirmDelete() {
    if (deleteVehicleId) {
        window.status = 'deleteVehicle:' + deleteVehicleId;
        closeConfirm();
    }
}

/**
 * Closes the delete confirmation dialog.
 */
function closeConfirm() {
    document.getElementById('confirmModal').classList.remove('show');
    deleteVehicleId = null;
}

/**
 * Closes the vehicle add/edit modal.
 */
function closeModal() {
    document.getElementById('vehicleModal').classList.remove('show');
    editingVehicleId = null;
}

/**
 * Handles the vehicle form submission (add or update).
 * @param {Event} event - The form submit event
 */
function handleSubmit(event) {
    event.preventDefault();
    
    const vehicleData = {
        id: editingVehicleId,
        make: document.getElementById('make').value,
        model: document.getElementById('model').value,
        year: parseInt(document.getElementById('year').value),
        color: document.getElementById('color').value,
        price: parseFloat(document.getElementById('price').value)
    };
    
    const action = editingVehicleId ? 'updateVehicle' : 'addVehicle';
    const encodedData = encodeURIComponent(JSON.stringify(vehicleData));
    
    window.status = action + ':' + encodedData;
    
    closeModal();
}

/**
 * Displays a toast notification.
 * @param {string} message - The message to display
 * @param {string} type - The type of toast (success, error, etc.)
 */
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.className = `toast ${type} show`;
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

/**
 * Formats a price value with proper decimal places.
 * @param {number} price - The price to format
 * @returns {string} Formatted price string
 */
function formatPrice(price) {
    return price.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
}

/**
 * Escapes HTML special characters to prevent XSS.
 * @param {string} text - The text to escape
 * @returns {string} HTML-safe text
 */
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

/**
 * Sets up event listeners for modal interactions.
 */
function setupModalListeners() {
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') {
            closeModal();
        }
    });

    const modal = document.getElementById('vehicleModal');
    if (modal) {
        modal.addEventListener('click', (e) => {
            if (e.target.id === 'vehicleModal') {
                closeModal();
            }
        });
    }
}
