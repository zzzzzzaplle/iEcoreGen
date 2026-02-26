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
     * Default constructor for Supplier.
     */
    public Supplier() {}

    /**
     * Constructor for Supplier with name and address.
     * @param name The name of the supplier.
     * @param address The address of the supplier.
     */
    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /**
     * Gets the name of the supplier.
     * @return The name of the supplier.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the supplier.
     * @param name The new name for the supplier.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the supplier.
     * @return The address of the supplier.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the supplier.
     * @param address The new address for the supplier.
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
     * Default constructor for Product.
     */
    public Product() {}

    /**
     * Constructor for Product with details.
     * @param name The name of the product.
     * @param supplier The supplier of the product.
     * @param isToxic Whether the product is toxic.
     * @param volume The volume of the product in m^3.
     */
    public Product(String name, Supplier supplier, boolean isToxic, double volume) {
        this.name = name;
        this.supplier = supplier;
        this.isToxic = isToxic;
        this.volume = volume;
    }

    /**
     * Gets the name of the product.
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * @param name The new name for the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the supplier of the product.
     * @return The supplier of the product.
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the product.
     * @param supplier The new supplier for the product.
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * Checks if the product is toxic.
     * @return True if the product is toxic, false otherwise.
     */
    public boolean isToxic() {
        return isToxic;
    }

    /**
     * Sets whether the product is toxic.
     * @param toxic True if the product is toxic, false otherwise.
     */
    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    /**
     * Gets the volume of the product.
     * @return The volume of the product in m^3.
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume of the product.
     * @param volume The new volume for the product in m^3.
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
     * Default constructor for Warehouse.
     */
    public Warehouse() {
        this.products = new ArrayList<>();
    }

    /**
     * Constructor for Warehouse with details.
     * @param city The city where the warehouse is located.
     * @param address The address of the warehouse.
     * @param surface The surface area of the warehouse in m².
     */
    public Warehouse(String city, String address, double surface) {
        this.city = city;
        this.address = address;
        this.surface = surface;
        this.products = new ArrayList<>();
    }

    /**
     * Gets the city where the warehouse is located.
     * @return The city of the warehouse.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the warehouse is located.
     * @param city The new city for the warehouse.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the address of the warehouse.
     * @return The address of the warehouse.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the warehouse.
     * @param address The new address for the warehouse.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the surface area of the warehouse.
     * @return The surface area of the warehouse in m².
     */
    public double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse.
     * @param surface The new surface area for the warehouse in m².
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /**
     * Gets the list of products stored in the warehouse.
     * @return The list of products in the warehouse.
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the list of products stored in the warehouse.
     * @param products The new list of products for the warehouse.
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Retrieves a list of names of toxic products present in the warehouse.
     * @return A list of names of toxic products. Returns an empty list if no toxic products are found.
     */
    public List<String> getToxicProductNames() {
        return products.stream()
                .filter(Product::isToxic)
                .map(Product::getName)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of distinct suppliers whose products are stored in the warehouse.
     * @return A list of distinct suppliers. Returns an empty list if the warehouse contains no products.
     */
    public List<Supplier> getDistinctSuppliers() {
        return products.stream()
                .map(Product::getSupplier)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Verifies if the warehouse contains a specific product by name.
     * @param productName The name of the product to check.
     * @return True if the product is found in the warehouse, false otherwise.
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
     * Default constructor for Company.
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Constructor for Company with warehouses.
     * @param warehouses The list of warehouses for the company.
     */
    public Company(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Gets the list of warehouses for the company.
     * @return The list of warehouses.
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Sets the list of warehouses for the company.
     * @param warehouses The new list of warehouses for the company.
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Retrieves a list of toxic product names present in a specific warehouse.
     * @param warehouseAddress The address of the warehouse to check.
     * @return A list of toxic product names. Returns an empty list if the warehouse does not exist or contains no toxic products.
     */
    public List<String> getToxicProductsInWarehouse(String warehouseAddress) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getAddress().equals(warehouseAddress))
                .findFirst()
                .map(Warehouse::getToxicProductNames)
                .orElse(new ArrayList<>());
    }

    /**
     * Retrieves a list of distinct suppliers whose products are stored in a specific warehouse.
     * @param warehouseAddress The address of the warehouse to check.
     * @return A list of distinct suppliers. Returns an empty list if the warehouse does not exist or contains no products.
     */
    public List<Supplier> getSuppliersInWarehouse(String warehouseAddress) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getAddress().equals(warehouseAddress))
                .findFirst()
                .map(Warehouse::getDistinctSuppliers)
                .orElse(new ArrayList<>());
    }

    /**
     * Counts the total volume of products supplied by a specific supplier within the company.
     * @param supplierName The name of the supplier.
     * @return The total volume of products supplied by the supplier. Returns 0 if no products are found.
     */
    public double countTotalVolumeBySupplier(String supplierName) {
        return warehouses.stream()
                .flatMap(warehouse -> warehouse.getProducts().stream())
                .filter(product -> product.getSupplier().getName().equals(supplierName))
                .mapToDouble(Product::getVolume)
                .sum();
    }

    /**
     * Verifies if a warehouse contains a specific product by name.
     * @param warehouseAddress The address of the warehouse to check.
     * @param productName The name of the product to check.
     * @return True if the product is found in the warehouse, false if the warehouse or product does not exist.
     */
    public boolean verifyProductInWarehouse(String warehouseAddress, String productName) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getAddress().equals(warehouseAddress))
                .findFirst()
                .map(warehouse -> warehouse.containsProduct(productName))
                .orElse(false);
    }

    /**
     * Verifies if the company contains a specific product by name and returns its locations.
     * @param productName The name of the product to check.
     * @return A list of warehouse cities and addresses where the product is found. Returns an empty list if the product is not found.
     */
    public List<String> verifyProductLocations(String productName) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.containsProduct(productName))
                .map(warehouse -> warehouse.getCity() + ", " + warehouse.getAddress())
                .collect(Collectors.toList());
    }
}