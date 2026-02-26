import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a supplier of products.
 */
class Supplier {
    private String name;
    private String address;

    /**
     * Default constructor for Supplier.
     */
    public Supplier() {
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
 * Represents a product stored in warehouses.
 */
class Product {
    private String name;
    private Supplier supplier;
    private boolean toxic;
    private double volume; // in m^3

    /**
     * Default constructor for Product.
     */
    public Product() {
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
     * @return the volume in m^3
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume occupied by the product.
     *
     * @param volume the volume to set in m^3
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }
}

/**
 * Represents a warehouse where products are stored.
 */
class Warehouse {
    private String address;
    private double surface; // in m²
    private List<Product> products;

    /**
     * Default constructor for Warehouse.
     */
    public Warehouse() {
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
     * @return the surface area in m²
     */
    public double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse.
     *
     * @param surface the surface area to set in m²
     */
    public void setSurface(double surface) {
        this.surface = surface;
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
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
    }
}

/**
 * Represents a company with multiple warehouses.
 */
class Company {
    private List<Warehouse> warehouses;
    private String name;

    /**
     * Default constructor for Company.
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Gets the list of warehouses owned by the company.
     *
     * @return the list of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Sets the list of warehouses owned by the company.
     *
     * @param warehouses the list of warehouses to set
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Gets the name of the company.
     *
     * @return the name of the company
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a warehouse to the company.
     *
     * @param warehouse the warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        if (warehouses == null) {
            warehouses = new ArrayList<>();
        }
        warehouses.add(warehouse);
    }

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse.
     *
     * @param warehouseAddress the address of the warehouse to check
     * @return a list of toxic product names, or an empty list if no toxic products are found or if the warehouse does not exist
     */
    public List<String> getToxicProductsInWarehouse(String warehouseAddress) {
        List<String> toxicProducts = new ArrayList<>();
        
        if (warehouseAddress == null || warehouses == null) {
            return toxicProducts;
        }
        
        Warehouse targetWarehouse = null;
        for (Warehouse warehouse : warehouses) {
            if (warehouseAddress.equals(warehouse.getAddress())) {
                targetWarehouse = warehouse;
                break;
            }
        }
        
        if (targetWarehouse == null || targetWarehouse.getProducts() == null) {
            return toxicProducts;
        }
        
        for (Product product : targetWarehouse.getProducts()) {
            if (product.isToxic()) {
                toxicProducts.add(product.getName());
            }
        }
        
        return toxicProducts;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     *
     * @param warehouseAddress the address of the warehouse to check
     * @return a list of distinct suppliers, or an empty list if the warehouse contains no products or does not exist
     */
    public List<Supplier> getSuppliersInWarehouse(String warehouseAddress) {
        List<Supplier> suppliers = new ArrayList<>();
        
        if (warehouseAddress == null || warehouses == null) {
            return suppliers;
        }
        
        Warehouse targetWarehouse = null;
        for (Warehouse warehouse : warehouses) {
            if (warehouseAddress.equals(warehouse.getAddress())) {
                targetWarehouse = warehouse;
                break;
            }
        }
        
        if (targetWarehouse == null || targetWarehouse.getProducts() == null) {
            return suppliers;
        }
        
        for (Product product : targetWarehouse.getProducts()) {
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
     * @param supplierName the name of the supplier to check
     * @return the total volume of products supplied by the specified supplier, or 0 if no products are found
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        
        if (supplierName == null || warehouses == null) {
            return totalVolume;
        }
        
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getProducts() != null) {
                for (Product product : warehouse.getProducts()) {
                    if (product.getSupplier() != null && supplierName.equals(product.getSupplier().getName())) {
                        totalVolume += product.getVolume();
                    }
                }
            }
        }
        
        return totalVolume;
    }

    /**
     * Verifies that a warehouse contains a specific product by product name.
     *
     * @param warehouseAddress the address of the warehouse to check
     * @param productName the name of the product to look for
     * @return true if the product is in the warehouse, false if the warehouse or product does not exist
     */
    public boolean isProductInWarehouse(String warehouseAddress, String productName) {
        if (warehouseAddress == null || productName == null || warehouses == null) {
            return false;
        }
        
        for (Warehouse warehouse : warehouses) {
            if (warehouseAddress.equals(warehouse.getAddress()) && warehouse.getProducts() != null) {
                for (Product product : warehouse.getProducts()) {
                    if (productName.equals(product.getName())) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * Verifies that the company contains a specific product by product name and returns its locations.
     *
     * @param productName the name of the product to look for
     * @return a list of warehouse addresses where the product is found, or an empty list if the product is not found
     */
    public List<String> getProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        
        if (productName == null || warehouses == null) {
            return locations;
        }
        
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getProducts() != null) {
                for (Product product : warehouse.getProducts()) {
                    if (productName.equals(product.getName())) {
                        locations.add(warehouse.getAddress());
                        break; // Avoid adding the same warehouse multiple times for the same product
                    }
                }
            }
        }
        
        return locations;
    }
}