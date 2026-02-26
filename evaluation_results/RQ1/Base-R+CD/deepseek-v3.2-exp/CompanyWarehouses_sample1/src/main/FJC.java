import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a company that manages multiple warehouses.
 */
class Company {
    private List<Warehouse> warehouses;

    /**
     * Default constructor.
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Gets the list of warehouses.
     * @return List of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Sets the list of warehouses.
     * @param warehouses List of warehouses to set
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Adds a warehouse to the company.
     * @param warehouse Warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        if (warehouses == null) {
            warehouses = new ArrayList<>();
        }
        warehouses.add(warehouse);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier across all warehouses.
     * @param supplierName Name of the supplier
     * @return Total volume of products from the supplier; 0 if no products found
     */
    public double countTotalVolumeBySupplier(String supplierName) {
        if (warehouses == null || supplierName == null) return 0.0;
        
        return warehouses.stream()
                .filter(Objects::nonNull)
                .flatMap(warehouse -> warehouse.getOccupations().stream())
                .filter(Objects::nonNull)
                .filter(occ -> occ.getProduct() != null && occ.getProduct().getSupplier() != null)
                .filter(occ -> supplierName.equals(occ.getProduct().getSupplier().getName()))
                .mapToDouble(ProductOccupation::getVolume)
                .sum();
    }

    /**
     * Finds all locations (city and address) where a specific product is stored.
     * @param productName Name of the product to search for
     * @return List of map entries containing city and address pairs; empty list if product not found
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        if (warehouses == null || productName == null) return new ArrayList<>();
        
        return warehouses.stream()
                .filter(Objects::nonNull)
                .filter(warehouse -> warehouse.containsProduct(productName))
                .map(warehouse -> new AbstractMap.SimpleEntry<>(warehouse.getCity(), warehouse.getAddress()))
                .collect(Collectors.toList());
    }
}

/**
 * Represents a warehouse located in a specific city.
 */
class Warehouse {
    private String city;
    private String address;
    private double surface;
    private List<ProductOccupation> occupations;

    /**
     * Default constructor.
     */
    public Warehouse() {
        this.occupations = new ArrayList<>();
    }

    /**
     * Gets the city where the warehouse is located.
     * @return City name
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the warehouse is located.
     * @param city City name
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the address of the warehouse.
     * @return Warehouse address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the warehouse.
     * @param address Warehouse address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the surface area of the warehouse in square meters.
     * @return Surface area
     */
    public double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse in square meters.
     * @param surface Surface area
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /**
     * Gets the list of product occupations in the warehouse.
     * @return List of product occupations
     */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /**
     * Sets the list of product occupations in the warehouse.
     * @param occupations List of product occupations
     */
    public void setOccupations(List<ProductOccupation> occupations) {
        this.occupations = occupations;
    }

    /**
     * Adds a product occupation to the warehouse.
     * @param occupation Product occupation to add
     */
    public void addOccupation(ProductOccupation occupation) {
        if (occupations == null) {
            occupations = new ArrayList<>();
        }
        occupations.add(occupation);
    }

    /**
     * Retrieves a list of all toxic product names present in this warehouse.
     * @return List of toxic product names; empty list if no toxic products found
     */
    public List<String> retrieveToxicProductNames() {
        if (occupations == null) return new ArrayList<>();
        
        return occupations.stream()
                .filter(Objects::nonNull)
                .map(ProductOccupation::getProduct)
                .filter(Objects::nonNull)
                .filter(Product::isToxic)
                .map(Product::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all distinct suppliers whose products are stored in this warehouse.
     * @return List of unique suppliers; empty list if no products found
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        if (occupations == null) return new ArrayList<>();
        
        return occupations.stream()
                .filter(Objects::nonNull)
                .map(ProductOccupation::getProduct)
                .filter(Objects::nonNull)
                .map(Product::getSupplier)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Verifies if the warehouse contains a specific product.
     * @param productName Name of the product to check
     * @return true if the product is found, false otherwise
     */
    public boolean containsProduct(String productName) {
        if (occupations == null || productName == null) return false;
        
        return occupations.stream()
                .filter(Objects::nonNull)
                .map(ProductOccupation::getProduct)
                .filter(Objects::nonNull)
                .anyMatch(product -> productName.equals(product.getName()));
    }
}

/**
 * Represents the occupation of a product in a warehouse, including the volume it occupies.
 */
class ProductOccupation {
    private double volume;
    private Product product;

    /**
     * Default constructor.
     */
    public ProductOccupation() {}

    /**
     * Gets the volume occupied by the product in cubic meters.
     * @return Volume in cubic meters
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume occupied by the product in cubic meters.
     * @param volume Volume in cubic meters
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Gets the product associated with this occupation.
     * @return Product object
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product associated with this occupation.
     * @param product Product object
     */
    public void setProduct(Product product) {
        this.product = product;
    }
}

/**
 * Represents a product with its properties and supplier information.
 */
class Product {
    private String name;
    private boolean isToxic;
    private Supplier supplier;

    /**
     * Default constructor.
     */
    public Product() {}

    /**
     * Gets the name of the product.
     * @return Product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * @param name Product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the product is toxic.
     * @return true if toxic, false otherwise
     */
    public boolean isToxic() {
        return isToxic;
    }

    /**
     * Sets whether the product is toxic.
     * @param toxic true if toxic, false otherwise
     */
    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    /**
     * Gets the supplier of the product.
     * @return Supplier object
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the product.
     * @param supplier Supplier object
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents a supplier of products.
 */
class Supplier {
    private String name;
    private String address;

    /**
     * Default constructor.
     */
    public Supplier() {}

    /**
     * Gets the name of the supplier.
     * @return Supplier name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the supplier.
     * @param name Supplier name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the supplier.
     * @return Supplier address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the supplier.
     * @param address Supplier address
     */
    public void setAddress(String address) {
        this.address = address;
    }
}