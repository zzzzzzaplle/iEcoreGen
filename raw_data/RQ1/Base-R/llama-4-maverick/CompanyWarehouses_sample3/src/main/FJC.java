import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a supplier with a name and address.
 */
class Supplier {
    private String name;
    private String address;

    /**
     * Unparameterized constructor for Supplier.
     */
    public Supplier() {}

    /**
     * Constructor for Supplier.
     * 
     * @param name    the name of the supplier
     * @param address the address of the supplier
     */
    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
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
}

/**
 * Represents a product with its details.
 */
class Product {
    private String name;
    private Supplier supplier;
    private boolean isToxic;
    private double volume;

    /**
     * Unparameterized constructor for Product.
     */
    public Product() {}

    /**
     * Constructor for Product.
     * 
     * @param name     the name of the product
     * @param supplier the supplier of the product
     * @param isToxic  whether the product is toxic
     * @param volume   the volume of the product in m^3
     */
    public Product(String name, Supplier supplier, boolean isToxic, double volume) {
        this.name = name;
        this.supplier = supplier;
        this.isToxic = isToxic;
        this.volume = volume;
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
        return isToxic;
    }

    /**
     * Sets whether the product is toxic.
     * 
     * @param isToxic true if the product is toxic, false otherwise
     */
    public void setToxic(boolean isToxic) {
        this.isToxic = isToxic;
    }

    /**
     * Gets the volume of the product.
     * 
     * @return the volume of the product in m^3
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume of the product.
     * 
     * @param volume the volume to set in m^3
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }
}

/**
 * Represents a warehouse with its details and stored products.
 */
class Warehouse {
    private String city;
    private String address;
    private double surface;
    private List<Product> products;

    /**
     * Unparameterized constructor for Warehouse.
     */
    public Warehouse() {
        this.products = new ArrayList<>();
    }

    /**
     * Constructor for Warehouse.
     * 
     * @param city    the city where the warehouse is located
     * @param address the address of the warehouse
     * @param surface the surface area of the warehouse in m²
     */
    public Warehouse(String city, String address, double surface) {
        this.city = city;
        this.address = address;
        this.surface = surface;
        this.products = new ArrayList<>();
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
     * @return the surface area of the warehouse in m²
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
        this.products.add(product);
    }

    /**
     * Retrieves a list of all toxic product names present in the warehouse.
     * 
     * @return a list of toxic product names
     */
    public List<String> getToxicProductNames() {
        return products.stream()
                .filter(Product::isToxic)
                .map(Product::getName)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in the warehouse.
     * 
     * @return a list of distinct suppliers
     */
    public List<Supplier> getDistinctSuppliers() {
        return products.stream()
                .map(Product::getSupplier)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Verifies if the warehouse contains a specific product by product name.
     * 
     * @param productName the name of the product to verify
     * @return true if the product is in the warehouse, false otherwise
     */
    public boolean containsProduct(String productName) {
        return products.stream()
                .anyMatch(product -> product.getName().equals(productName));
    }
}

/**
 * Represents a company with its warehouses.
 */
class Company {
    private List<Warehouse> warehouses;

    /**
     * Unparameterized constructor for Company.
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Constructor for Company.
     * 
     * @param warehouses the list of warehouses
     */
    public Company(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Gets the list of warehouses in the company.
     * 
     * @return the list of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Sets the list of warehouses in the company.
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
     * @param warehouseAddress the address of the warehouse
     * @return a list of toxic product names, or an empty list if the warehouse does not exist
     */
    public List<String> getToxicProductNamesInWarehouse(String warehouseAddress) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getAddress().equals(warehouseAddress))
                .findFirst()
                .map(Warehouse::getToxicProductNames)
                .orElse(new ArrayList<>());
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     * 
     * @param warehouseAddress the address of the warehouse
     * @return a list of distinct suppliers, or an empty list if the warehouse does not exist or contains no products
     */
    public List<Supplier> getDistinctSuppliersInWarehouse(String warehouseAddress) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getAddress().equals(warehouseAddress))
                .findFirst()
                .map(Warehouse::getDistinctSuppliers)
                .orElse(new ArrayList<>());
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     * 
     * @param supplierName the name of the supplier
     * @return the total volume of products supplied by the supplier
     */
    public double countTotalVolumeBySupplier(String supplierName) {
        return warehouses.stream()
                .flatMap(warehouse -> warehouse.getProducts().stream())
                .filter(product -> product.getSupplier().getName().equals(supplierName))
                .mapToDouble(Product::getVolume)
                .sum();
    }

    /**
     * Verifies if a warehouse contains a specific product by product name.
     * 
     * @param warehouseAddress the address of the warehouse
     * @param productName      the name of the product
     * @return true if the product is in the warehouse, false if the warehouse or product does not exist
     */
    public boolean verifyProductInWarehouse(String warehouseAddress, String productName) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getAddress().equals(warehouseAddress))
                .findFirst()
                .map(warehouse -> warehouse.containsProduct(productName))
                .orElse(false);
    }

    /**
     * Verifies if the company contains a specific product by product name and returns its locations.
     * 
     * @param productName the name of the product
     * @return a list of warehouse cities and addresses where the product is found
     */
    public List<String> verifyProductLocations(String productName) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.containsProduct(productName))
                .map(warehouse -> warehouse.getCity() + ", " + warehouse.getAddress())
                .collect(Collectors.toList());
    }
}