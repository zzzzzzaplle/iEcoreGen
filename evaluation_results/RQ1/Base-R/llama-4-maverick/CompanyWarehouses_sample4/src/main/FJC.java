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
     * @param name Supplier's name
     * @param address Supplier's address
     */
    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /**
     * Gets the supplier's name.
     * @return Supplier's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the supplier's name.
     * @param name Supplier's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the supplier's address.
     * @return Supplier's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the supplier's address.
     * @param address Supplier's address
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
     * @param name Product's name
     * @param supplier Product's supplier
     * @param isToxic Whether the product is toxic
     * @param volume Product's volume in m^3
     */
    public Product(String name, Supplier supplier, boolean isToxic, double volume) {
        this.name = name;
        this.supplier = supplier;
        this.isToxic = isToxic;
        this.volume = volume;
    }

    /**
     * Gets the product's name.
     * @return Product's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product's name.
     * @param name Product's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product's supplier.
     * @return Product's supplier
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the product's supplier.
     * @param supplier Product's supplier
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * Checks if the product is toxic.
     * @return True if toxic, false otherwise
     */
    public boolean isToxic() {
        return isToxic;
    }

    /**
     * Sets whether the product is toxic.
     * @param toxic True if toxic, false otherwise
     */
    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    /**
     * Gets the product's volume.
     * @return Product's volume in m^3
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the product's volume.
     * @param volume Product's volume in m^3
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
     * @param city Warehouse's city
     * @param address Warehouse's address
     * @param surface Warehouse's surface in m²
     */
    public Warehouse(String city, String address, double surface) {
        this.city = city;
        this.address = address;
        this.surface = surface;
        this.products = new ArrayList<>();
    }

    /**
     * Gets the warehouse's city.
     * @return Warehouse's city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the warehouse's city.
     * @param city Warehouse's city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the warehouse's address.
     * @return Warehouse's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the warehouse's address.
     * @param address Warehouse's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the warehouse's surface.
     * @return Warehouse's surface in m²
     */
    public double getSurface() {
        return surface;
    }

    /**
     * Sets the warehouse's surface.
     * @param surface Warehouse's surface in m²
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /**
     * Gets the list of products stored in the warehouse.
     * @return List of products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the list of products stored in the warehouse.
     * @param products List of products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Adds a product to the warehouse.
     * @param product Product to add
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }

    /**
     * Retrieves a list of all toxic product names present in the warehouse.
     * @return List of toxic product names
     */
    public List<String> getToxicProductNames() {
        return products.stream()
                .filter(Product::isToxic)
                .map(Product::getName)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in the warehouse.
     * @return List of distinct suppliers
     */
    public List<Supplier> getDistinctSuppliers() {
        return products.stream()
                .map(Product::getSupplier)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Verifies that the warehouse contains a specific product by product name.
     * @param productName Name of the product to check
     * @return True if the product is in the warehouse, false otherwise
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
     * @param warehouses List of warehouses
     */
    public Company(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Gets the list of warehouses in the company.
     * @return List of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Sets the list of warehouses in the company.
     * @param warehouses List of warehouses
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Adds a warehouse to the company.
     * @param warehouse Warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse.
     * @param warehouseAddress Address of the warehouse to check
     * @return List of toxic product names
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
     * @param warehouseAddress Address of the warehouse to check
     * @return List of distinct suppliers
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
     * @param supplierName Name of the supplier
     * @return Total volume of products supplied by the supplier
     */
    public double countTotalVolumeSuppliedBy(String supplierName) {
        return warehouses.stream()
                .flatMap(warehouse -> warehouse.getProducts().stream())
                .filter(product -> product.getSupplier().getName().equals(supplierName))
                .mapToDouble(Product::getVolume)
                .sum();
    }

    /**
     * Verifies that a warehouse contains a specific product by product name.
     * @param warehouseAddress Address of the warehouse to check
     * @param productName Name of the product to check
     * @return True if the product is in the warehouse, false otherwise
     */
    public boolean verifyProductInWarehouse(String warehouseAddress, String productName) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getAddress().equals(warehouseAddress))
                .findFirst()
                .map(warehouse -> warehouse.containsProduct(productName))
                .orElse(false);
    }

    /**
     * Verifies that the company contains a specific product by product name and returns its locations.
     * @param productName Name of the product to check
     * @return List of warehouse cities and addresses where the product is found
     */
    public List<String> verifyProductLocations(String productName) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.containsProduct(productName))
                .map(warehouse -> warehouse.getCity() + ", " + warehouse.getAddress())
                .collect(Collectors.toList());
    }
}