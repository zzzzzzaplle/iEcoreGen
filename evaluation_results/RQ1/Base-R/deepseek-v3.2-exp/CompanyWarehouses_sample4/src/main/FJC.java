import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a supplier of products
 */
class Supplier {
    private String name;
    private String address;

    /**
     * Default constructor
     */
    public Supplier() {
    }

    /**
     * Gets the supplier name
     * @return the supplier name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the supplier name
     * @param name the supplier name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the supplier address
     * @return the supplier address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the supplier address
     * @param address the supplier address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}

/**
 * Represents a product stored in warehouses
 */
class Product {
    private String name;
    private boolean toxic;
    private double volume;
    private Supplier supplier;

    /**
     * Default constructor
     */
    public Product() {
    }

    /**
     * Gets the product name
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the product is toxic
     * @return true if the product is toxic, false otherwise
     */
    public boolean isToxic() {
        return toxic;
    }

    /**
     * Sets the toxic status of the product
     * @param toxic the toxic status to set
     */
    public void setToxic(boolean toxic) {
        this.toxic = toxic;
    }

    /**
     * Gets the volume occupied by the product
     * @return the volume in cubic meters
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume occupied by the product
     * @param volume the volume in cubic meters to set
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Gets the supplier of the product
     * @return the supplier
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the product
     * @param supplier the supplier to set
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents a warehouse that stores products
 */
class Warehouse {
    private String city;
    private String address;
    private double surface;
    private List<Product> products;

    /**
     * Default constructor
     */
    public Warehouse() {
        this.products = new ArrayList<>();
    }

    /**
     * Gets the warehouse city
     * @return the warehouse city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the warehouse city
     * @param city the warehouse city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the warehouse address
     * @return the warehouse address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the warehouse address
     * @param address the warehouse address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the warehouse surface area
     * @return the surface area in square meters
     */
    public double getSurface() {
        return surface;
    }

    /**
     * Sets the warehouse surface area
     * @param surface the surface area in square meters to set
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /**
     * Gets the list of products in the warehouse
     * @return the list of products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the list of products in the warehouse
     * @param products the list of products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Adds a product to the warehouse
     * @param product the product to add
     */
    public void addProduct(Product product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
    }

    /**
     * Removes a product from the warehouse
     * @param product the product to remove
     */
    public void removeProduct(Product product) {
        if (products != null) {
            products.remove(product);
        }
    }
}

/**
 * Represents a company that manages multiple warehouses
 */
class Company {
    private List<Warehouse> warehouses;

    /**
     * Default constructor
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Gets the list of warehouses
     * @return the list of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Sets the list of warehouses
     * @param warehouses the list of warehouses to set
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Adds a warehouse to the company
     * @param warehouse the warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        if (warehouses == null) {
            warehouses = new ArrayList<>();
        }
        warehouses.add(warehouse);
    }

    /**
     * Removes a warehouse from the company
     * @param warehouse the warehouse to remove
     */
    public void removeWarehouse(Warehouse warehouse) {
        if (warehouses != null) {
            warehouses.remove(warehouse);
        }
    }

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse
     * @param warehouseAddress the address of the warehouse to search
     * @return a list of toxic product names, or empty list if no toxic products are found or if the warehouse does not exist
     */
    public List<String> getToxicProductsInWarehouse(String warehouseAddress) {
        List<String> toxicProducts = new ArrayList<>();
        
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                for (Product product : warehouse.getProducts()) {
                    if (product.isToxic()) {
                        toxicProducts.add(product.getName());
                    }
                }
                break;
            }
        }
        
        return toxicProducts;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse
     * @param warehouseAddress the address of the warehouse to search
     * @return a list of distinct suppliers, or empty list if the warehouse contains no products or does not exist
     */
    public List<Supplier> getDistinctSuppliersInWarehouse(String warehouseAddress) {
        Set<Supplier> distinctSuppliers = new HashSet<>();
        
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                for (Product product : warehouse.getProducts()) {
                    distinctSuppliers.add(product.getSupplier());
                }
                break;
            }
        }
        
        return new ArrayList<>(distinctSuppliers);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company
     * @param supplierName the name of the supplier
     * @return the total volume of products from the supplier, or 0 if no products are found
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getProducts()) {
                if (product.getSupplier() != null && 
                    product.getSupplier().getName().equals(supplierName)) {
                    totalVolume += product.getVolume();
                }
            }
        }
        
        return totalVolume;
    }

    /**
     * Verifies that a warehouse contains a specific product by product name
     * @param warehouseAddress the address of the warehouse to check
     * @param productName the name of the product to search for
     * @return false if the warehouse or product does not exist; true if the product is in the warehouse
     */
    public boolean warehouseContainsProduct(String warehouseAddress, String productName) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                for (Product product : warehouse.getProducts()) {
                    if (product.getName().equals(productName)) {
                        return true;
                    }
                }
                break;
            }
        }
        
        return false;
    }

    /**
     * Verifies that the company contains a specific product by product name and return its locations
     * @param productName the name of the product to search for
     * @return a list of warehouse cities and addresses if found; empty list if the product is not found
     */
    public List<String> getProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getProducts()) {
                if (product.getName().equals(productName)) {
                    String location = warehouse.getCity() + " - " + warehouse.getAddress();
                    if (!locations.contains(location)) {
                        locations.add(location);
                    }
                    break;
                }
            }
        }
        
        return locations;
    }
}