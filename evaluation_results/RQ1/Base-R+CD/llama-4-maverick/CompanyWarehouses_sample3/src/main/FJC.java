import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

/**
 * Represents a company with multiple warehouses.
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
     * Gets the list of warehouses in the company.
     * 
     * @return List of warehouses.
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Adds a warehouse to the company's list of warehouses.
     * 
     * @param warehouse Warehouse to be added.
     */
    public void addWarehouses(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier within the company.
     * 
     * @param supplierName Name of the supplier.
     * @return Total volume of products supplied by the specified supplier.
     */
    public double countTotalVolumeBySupplier(String supplierName) {
        return warehouses.stream()
                .flatMap(warehouse -> warehouse.getOccupations().stream())
                .filter(occupation -> occupation.getProduct().getSupplier().getName().equals(supplierName))
                .mapToDouble(occupation -> occupation.getVolume())
                .sum();
    }

    /**
     * Finds the locations of a specific product within the company.
     * 
     * @param productName Name of the product.
     * @return List of warehouse cities and addresses where the product is found.
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.containsProduct(productName))
                .map(warehouse -> new SimpleEntry<>(warehouse.getCity(), warehouse.getAddress()))
                .collect(Collectors.toList());
    }
}

/**
 * Represents a warehouse with its attributes and product occupations.
 */
class Warehouse {
    private String city;
    private String address;
    private double surface;
    private List<ProductOccupation> occupations;

    /**
     * Default constructor for Warehouse.
     */
    public Warehouse() {
        this.occupations = new ArrayList<>();
    }

    /**
     * Gets the city where the warehouse is located.
     * 
     * @return City of the warehouse.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the warehouse is located.
     * 
     * @param city City to be set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the address of the warehouse.
     * 
     * @return Address of the warehouse.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the warehouse.
     * 
     * @param address Address to be set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the surface area of the warehouse.
     * 
     * @return Surface area of the warehouse.
     */
    public double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse.
     * 
     * @param surface Surface area to be set.
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /**
     * Gets the list of product occupations in the warehouse.
     * 
     * @return List of product occupations.
     */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /**
     * Adds a product occupation to the warehouse.
     * 
     * @param occupation Product occupation to be added.
     */
    public void addOccupations(ProductOccupation occupation) {
        this.occupations.add(occupation);
    }

    /**
     * Retrieves the names of all toxic products in the warehouse.
     * 
     * @return List of toxic product names.
     */
    public List<String> retrieveToxicProductNames() {
        return occupations.stream()
                .map(ProductOccupation::getProduct)
                .filter(Product::isToxic)
                .map(Product::getName)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the list of unique suppliers whose products are stored in the warehouse.
     * 
     * @return List of unique suppliers.
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        return occupations.stream()
                .map(ProductOccupation::getProduct)
                .map(Product::getSupplier)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Checks if the warehouse contains a specific product by name.
     * 
     * @param productName Name of the product to check.
     * @return True if the product is found, false otherwise.
     */
    public boolean containsProduct(String productName) {
        return occupations.stream()
                .anyMatch(occupation -> occupation.getProduct().getName().equals(productName));
    }
}

/**
 * Represents a product occupation in a warehouse.
 */
class ProductOccupation {
    private double volume;
    private Product product;

    /**
     * Default constructor for ProductOccupation.
     */
    public ProductOccupation() {}

    /**
     * Gets the volume of the product occupation.
     * 
     * @return Volume of the product occupation.
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume of the product occupation.
     * 
     * @param volume Volume to be set.
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Gets the product associated with the occupation.
     * 
     * @return Product associated with the occupation.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product associated with the occupation.
     * 
     * @param product Product to be set.
     */
    public void setProduct(Product product) {
        this.product = product;
    }
}

/**
 * Represents a product with its attributes.
 */
class Product {
    private String name;
    private boolean isToxic;
    private Supplier supplier;

    /**
     * Default constructor for Product.
     */
    public Product() {}

    /**
     * Gets the name of the product.
     * 
     * @return Name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * 
     * @param name Name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the product is toxic.
     * 
     * @return True if the product is toxic, false otherwise.
     */
    public boolean isToxic() {
        return isToxic;
    }

    /**
     * Sets whether the product is toxic.
     * 
     * @param isToxic Toxicity status to be set.
     */
    public void setToxic(boolean isToxic) {
        this.isToxic = isToxic;
    }

    /**
     * Gets the supplier of the product.
     * 
     * @return Supplier of the product.
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the product.
     * 
     * @param supplier Supplier to be set.
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents a supplier with its attributes.
 */
class Supplier {
    private String name;
    private String address;

    /**
     * Default constructor for Supplier.
     */
    public Supplier() {}

    /**
     * Gets the name of the supplier.
     * 
     * @return Name of the supplier.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the supplier.
     * 
     * @param name Name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the supplier.
     * 
     * @return Address of the supplier.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the supplier.
     * 
     * @param address Address to be set.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}