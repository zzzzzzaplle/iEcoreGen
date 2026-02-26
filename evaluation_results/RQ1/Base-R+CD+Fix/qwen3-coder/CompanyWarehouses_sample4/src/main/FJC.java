import java.util.*;

/**
 * Company class represents a company that manages multiple warehouses.
 */
class Company {
    private List<Warehouse> warehouses;

    /**
     * Default constructor initializes the warehouses list.
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Gets the list of warehouses.
     *
     * @return List of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Sets the list of warehouses.
     *
     * @param warehouses List of warehouses to set
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Adds a warehouse to the company.
     *
     * @param warehouse Warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier within the company.
     *
     * @param supplierName Name of the supplier
     * @return Total volume of products supplied by the supplier, or 0 if none found
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        
        if (supplierName == null || warehouses == null) {
            return 0.0;
        }
        
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
     * Finds all warehouse locations where a specific product is stored.
     *
     * @param productName Name of the product to search for
     * @return List of Map entries with city and address of warehouses containing the product, or empty list if not found
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        
        if (productName == null || warehouses == null) {
            return locations;
        }
        
        for (Warehouse warehouse : warehouses) {
            if (warehouse.containsProduct(productName)) {
                locations.add(new AbstractMap.SimpleEntry<>(warehouse.getCity(), warehouse.getAddress()));
            }
        }
        
        return locations;
    }
}

/**
 * Warehouse class represents a warehouse with its location, surface area, and product occupations.
 */
class Warehouse {
    private String city;
    private String address;
    private Double surface;
    private List<ProductOccupation> occupations;

    /**
     * Default constructor initializes the occupations list.
     */
    public Warehouse() {
        this.occupations = new ArrayList<>();
    }

    /**
     * Gets the city of the warehouse.
     *
     * @return City of the warehouse
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the warehouse.
     *
     * @param city City to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the address of the warehouse.
     *
     * @return Address of the warehouse
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the warehouse.
     *
     * @param address Address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the surface area of the warehouse.
     *
     * @return Surface area of the warehouse
     */
    public Double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse.
     *
     * @param surface Surface area to set
     */
    public void setSurface(Double surface) {
        this.surface = surface;
    }

    /**
     * Gets the list of product occupations in the warehouse.
     *
     * @return List of product occupations
     */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /**
     * Sets the list of product occupations in the warehouse.
     *
     * @param occupations List of product occupations to set
     */
    public void setOccupations(List<ProductOccupation> occupations) {
        this.occupations = occupations;
    }

    /**
     * Adds a product occupation to the warehouse.
     *
     * @param occupation Product occupation to add
     */
    public void addOccupation(ProductOccupation occupation) {
        this.occupations.add(occupation);
    }

    /**
     * Retrieves a list of all toxic product names present in this warehouse.
     *
     * @return List of toxic product names, or empty list if none found
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
     * Retrieves the list of all distinct suppliers whose products are stored in this warehouse.
     *
     * @return List of distinct suppliers, or empty list if none found
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
     * @param productName Name of the product to check
     * @return true if the product is in the warehouse, false otherwise
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
 * ProductOccupation class represents the occupation of a product in a warehouse with its volume.
 */
class ProductOccupation {
    private Double volume;
    private Product product;

    /**
     * Default constructor.
     */
    public ProductOccupation() {
    }

    /**
     * Gets the volume occupied by the product.
     *
     * @return Volume of the product
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * Sets the volume occupied by the product.
     *
     * @param volume Volume to set
     */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /**
     * Gets the product.
     *
     * @return Product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product.
     *
     * @param product Product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }
}

/**
 * Product class represents a product with its properties and supplier.
 */
class Product {
    private String name;
    private boolean isToxic;
    private Supplier supplier;

    /**
     * Default constructor.
     */
    public Product() {
    }

    /**
     * Gets the name of the product.
     *
     * @return Name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name Name to set
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
     * @param isToxic true if toxic, false otherwise
     */
    public void setToxic(boolean isToxic) {
        this.isToxic = isToxic;
    }

    /**
     * Gets the supplier of the product.
     *
     * @return Supplier of the product
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the product.
     *
     * @param supplier Supplier to set
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Supplier class represents a supplier with name and address.
 */
class Supplier {
    private String name;
    private String address;

    /**
     * Default constructor.
     */
    public Supplier() {
    }

    /**
     * Gets the name of the supplier.
     *
     * @return Name of the supplier
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the supplier.
     *
     * @param name Name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the supplier.
     *
     * @return Address of the supplier
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the supplier.
     *
     * @param address Address to set
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