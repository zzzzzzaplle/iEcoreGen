import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a supplier of products with a name and address.
 */
class Supplier {
    private String name;
    private String address;

    /**
     * Constructs a new Supplier with default values.
     */
    public Supplier() {
        this.name = "";
        this.address = "";
    }

    /**
     * Gets the name of the supplier.
     *
     * @return the name of the supplier
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the supplier.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the supplier.
     *
     * @return the address of the supplier
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the supplier.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(name, supplier.name) && Objects.equals(address, supplier.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}

/**
 * Represents a product with name, toxicity, volume, and supplier information.
 */
class Product {
    private String name;
    private boolean toxic;
    private double volume;
    private Supplier supplier;

    /**
     * Constructs a new Product with default values.
     */
    public Product() {
        this.name = "";
        this.toxic = false;
        this.volume = 0.0;
        this.supplier = new Supplier();
    }

    /**
     * Gets the name of the product.
     *
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the product is toxic.
     *
     * @return true if the product is toxic, false otherwise
     */
    public boolean isToxic() {
        return toxic;
    }

    /**
     * Sets whether the product is toxic.
     *
     * @param toxic true if the product is toxic, false otherwise
     */
    public void setToxic(boolean toxic) {
        this.toxic = toxic;
    }

    /**
     * Gets the volume occupied by the product.
     *
     * @return the volume in cubic meters
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume occupied by the product.
     *
     * @param volume the volume to set in cubic meters
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Gets the supplier of the product.
     *
     * @return the supplier of the product
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the product.
     *
     * @param supplier the supplier to set
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents a warehouse with an address, surface area, and stored products.
 */
class Warehouse {
    private String address;
    private double surface;
    private String city;
    private List<Product> products;

    /**
     * Constructs a new Warehouse with default values.
     */
    public Warehouse() {
        this.address = "";
        this.surface = 0.0;
        this.city = "";
        this.products = new ArrayList<>();
    }

    /**
     * Gets the address of the warehouse.
     *
     * @return the address of the warehouse
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the warehouse.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the surface area of the warehouse.
     *
     * @return the surface area in square meters
     */
    public double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse.
     *
     * @param surface the surface area to set in square meters
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /**
     * Gets the city where the warehouse is located.
     *
     * @return the city of the warehouse
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the warehouse is located.
     *
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the list of products stored in the warehouse.
     *
     * @return the list of products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the list of products stored in the warehouse.
     *
     * @param products the list of products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Adds a product to the warehouse.
     *
     * @param product the product to add
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }
}

/**
 * Represents a company with multiple warehouses.
 */
class Company {
    private List<Warehouse> warehouses;

    /**
     * Constructs a new Company with default values.
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Gets the list of warehouses belonging to the company.
     *
     * @return the list of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Sets the list of warehouses belonging to the company.
     *
     * @param warehouses the list of warehouses to set
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Adds a warehouse to the company.
     *
     * @param warehouse the warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse.
     *
     * @param warehouseAddress the address of the warehouse to search
     * @return a list of toxic product names, or an empty list if no toxic products are found or if the warehouse does not exist
     */
    public List<String> getToxicProductsInWarehouse(String warehouseAddress) {
        List<String> toxicProducts = new ArrayList<>();
        
        Warehouse warehouse = findWarehouseByAddress(warehouseAddress);
        if (warehouse == null) {
            return toxicProducts;
        }
        
        for (Product product : warehouse.getProducts()) {
            if (product.isToxic()) {
                toxicProducts.add(product.getName());
            }
        }
        
        return toxicProducts;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     *
     * @param warehouseAddress the address of the warehouse to search
     * @return a list of distinct suppliers, or an empty list if the warehouse contains no products or does not exist
     */
    public List<Supplier> getSuppliersInWarehouse(String warehouseAddress) {
        List<Supplier> suppliers = new ArrayList<>();
        
        Warehouse warehouse = findWarehouseByAddress(warehouseAddress);
        if (warehouse == null) {
            return suppliers;
        }
        
        for (Product product : warehouse.getProducts()) {
            Supplier supplier = product.getSupplier();
            if (supplier != null && !suppliers.contains(supplier)) {
                suppliers.add(supplier);
            }
        }
        
        return suppliers;
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     *
     * @param supplierName the name of the supplier to search for
     * @return the total volume of products supplied by the specified supplier, or 0 if no products are found
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getProducts()) {
                if (product.getSupplier() != null && 
                    supplierName.equals(product.getSupplier().getName())) {
                    totalVolume += product.getVolume();
                }
            }
        }
        
        return totalVolume;
    }

    /**
     * Verifies that a warehouse contains a specific product by product name.
     *
     * @param warehouseAddress the address of the warehouse to search
     * @param productName the name of the product to search for
     * @return true if the product is in the warehouse, false if the warehouse or product does not exist
     */
    public boolean isProductInWarehouse(String warehouseAddress, String productName) {
        Warehouse warehouse = findWarehouseByAddress(warehouseAddress);
        if (warehouse == null) {
            return false;
        }
        
        for (Product product : warehouse.getProducts()) {
            if (productName.equals(product.getName())) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Verifies that the company contains a specific product by product name and returns its locations.
     *
     * @param productName the name of the product to search for
     * @return a list of warehouse cities and addresses where the product is found, or an empty list if the product is not found
     */
    public List<String> getProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getProducts()) {
                if (productName.equals(product.getName())) {
                    locations.add(warehouse.getCity() + " - " + warehouse.getAddress());
                    break; // Avoid duplicate entries for the same warehouse
                }
            }
        }
        
        return locations;
    }

    /**
     * Helper method to find a warehouse by its address.
     *
     * @param address the address of the warehouse to find
     * @return the warehouse with the specified address, or null if not found
     */
    private Warehouse findWarehouseByAddress(String address) {
        for (Warehouse warehouse : warehouses) {
            if (address.equals(warehouse.getAddress())) {
                return warehouse;
            }
        }
        return null;
    }
}