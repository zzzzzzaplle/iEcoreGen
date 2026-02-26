import java.util.*;

/**
 * Represents a company that manages multiple warehouses.
 */
class Company {
    private List<Warehouse> warehouses;

    /**
     * Constructs a new Company with an empty list of warehouses.
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Gets the list of warehouses.
     *
     * @return the list of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Adds a warehouse to the company.
     *
     * @param warehouse the warehouse to add
     */
    public void addWarehouses(Warehouse warehouse) {
        if (warehouse != null) {
            this.warehouses.add(warehouse);
        }
    }

    /**
     * Counts the total volume of products supplied by a specific supplier within the company.
     *
     * @param supplierName the name of the supplier
     * @return the total volume of products supplied by the supplier, or 0 if no products are found
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        if (supplierName == null || warehouses == null) {
            return 0.0;
        }

        double totalVolume = 0.0;
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getOccupations() != null) {
                for (ProductOccupation occupation : warehouse.getOccupations()) {
                    Product product = occupation.getProduct();
                    if (product != null && product.getSupplier() != null &&
                            supplierName.equals(product.getSupplier().getName())) {
                        totalVolume += occupation.getVolume();
                    }
                }
            }
        }
        return totalVolume;
    }

    /**
     * Finds all locations (city and address) where a specific product is stored.
     *
     * @param productName the name of the product to find
     * @return a list of entries where each entry contains a city (key) and address (value), 
     *         or an empty list if the product is not found
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        
        if (productName == null || warehouses == null) {
            return locations;
        }

        for (Warehouse warehouse : warehouses) {
            if (warehouse.containsProduct(productName)) {
                locations.add(new AbstractMap.SimpleEntry<>(
                    warehouse.getCity(), 
                    warehouse.getAddress()
                ));
            }
        }
        
        return locations;
    }
}

/**
 * Represents a warehouse that stores products.
 */
class Warehouse {
    private String city;
    private String address;
    private Double surface;
    private List<ProductOccupation> occupations;

    /**
     * Constructs a new Warehouse with empty attributes.
     */
    public Warehouse() {
        this.occupations = new ArrayList<>();
    }

    /**
     * Gets the city where the warehouse is located.
     *
     * @return the city
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
     * @return the address
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
     * @return the surface area
     */
    public Double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse.
     *
     * @param surface the surface area to set
     */
    public void setSurface(Double surface) {
        this.surface = surface;
    }

    /**
     * Gets the list of product occupations in the warehouse.
     *
     * @return the list of product occupations
     */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /**
     * Adds a product occupation to the warehouse.
     *
     * @param occupation the product occupation to add
     */
    public void addOccupations(ProductOccupation occupation) {
        if (occupation != null) {
            this.occupations.add(occupation);
        }
    }

    /**
     * Retrieves a list of all toxic product names present in this warehouse.
     *
     * @return a list of toxic product names, or an empty list if none are found
     */
    public List<String> retrieveToxicProductNames() {
        List<String> toxicProducts = new ArrayList<>();
        
        if (occupations == null) {
            return toxicProducts;
        }
        
        for (ProductOccupation occupation : occupations) {
            Product product = occupation.getProduct();
            if (product != null && product.isToxic()) {
                toxicProducts.add(product.getName());
            }
        }
        
        return toxicProducts;
    }

    /**
     * Retrieves a list of all distinct suppliers whose products are stored in this warehouse.
     *
     * @return a list of distinct suppliers, or an empty list if none are found
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        Set<Supplier> uniqueSuppliers = new HashSet<>();
        
        if (occupations == null) {
            return new ArrayList<>(uniqueSuppliers);
        }
        
        for (ProductOccupation occupation : occupations) {
            Product product = occupation.getProduct();
            if (product != null && product.getSupplier() != null) {
                uniqueSuppliers.add(product.getSupplier());
            }
        }
        
        return new ArrayList<>(uniqueSuppliers);
    }

    /**
     * Checks if this warehouse contains a specific product by name.
     *
     * @param productName the name of the product to check
     * @return true if the product exists in this warehouse, false otherwise
     */
    public boolean containsProduct(String productName) {
        if (productName == null || occupations == null) {
            return false;
        }
        
        for (ProductOccupation occupation : occupations) {
            Product product = occupation.getProduct();
            if (product != null && productName.equals(product.getName())) {
                return true;
            }
        }
        
        return false;
    }
}

/**
 * Represents the occupation of a product in a warehouse with its volume.
 */
class ProductOccupation {
    private Double volume;
    private Product product;

    /**
     * Constructs a new ProductOccupation with default values.
     */
    public ProductOccupation() {
    }

    /**
     * Gets the volume occupied by the product.
     *
     * @return the volume
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * Sets the volume occupied by the product.
     *
     * @param volume the volume to set
     */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /**
     * Gets the product.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product.
     *
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }
}

/**
 * Represents a product with its properties.
 */
class Product {
    private String name;
    private boolean isToxic;
    private Supplier supplier;

    /**
     * Constructs a new Product with default values.
     */
    public Product() {
    }

    /**
     * Gets the name of the product.
     *
     * @return the name
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
     * Gets the supplier of the product.
     *
     * @return the supplier
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
 * Represents a supplier with name and address.
 */
class Supplier {
    private String name;
    private String address;

    /**
     * Constructs a new Supplier with default values.
     */
    public Supplier() {
    }

    /**
     * Gets the name of the supplier.
     *
     * @return the name
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
     * @return the address
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