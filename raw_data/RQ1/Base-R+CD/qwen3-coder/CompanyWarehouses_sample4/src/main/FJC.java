import java.util.*;

/**
 * Represents a company that manages multiple warehouses.
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
     * @param warehouses List of warehouses
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Adds a warehouse to the company.
     *
     * @param warehouse Warehouse to add
     */
    public void addWarehouses(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier within the company.
     *
     * @param supplierName Name of the supplier
     * @return Total volume of products supplied by the supplier, or 0 if no products are found
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        
        for (Warehouse warehouse : warehouses) {
            for (ProductOccupation occupation : warehouse.getOccupations()) {
                Product product = occupation.getProduct();
                if (product.getSupplier() != null && 
                    supplierName.equals(product.getSupplier().getName())) {
                    totalVolume += occupation.getVolume();
                }
            }
        }
        
        return totalVolume;
    }

    /**
     * Finds all locations (city and address) where a specific product is stored.
     *
     * @param productName Name of the product to search for
     * @return List of Map.Entry where key is city and value is address, or empty list if not found
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        
        for (Warehouse warehouse : warehouses) {
            for (ProductOccupation occupation : warehouse.getOccupations()) {
                if (productName.equals(occupation.getProduct().getName())) {
                    locations.add(new AbstractMap.SimpleEntry<>(
                        warehouse.getCity(), 
                        warehouse.getAddress()
                    ));
                    break; // Avoid duplicate entries for the same warehouse
                }
            }
        }
        
        return locations;
    }
}

/**
 * Represents a warehouse with its location, surface area, and product occupations.
 */
class Warehouse {
    private String city;
    private String address;
    private Double surface;
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
     * @return City name
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the warehouse is located.
     *
     * @param city City name
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the address of the warehouse.
     *
     * @return Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the warehouse.
     *
     * @param address Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the surface area of the warehouse.
     *
     * @return Surface area in m²
     */
    public Double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse.
     *
     * @param surface Surface area in m²
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
     * @param occupations List of product occupations
     */
    public void setOccupations(List<ProductOccupation> occupations) {
        this.occupations = occupations;
    }

    /**
     * Adds a product occupation to the warehouse.
     *
     * @param occupation Product occupation to add
     */
    public void addOccupations(ProductOccupation occupation) {
        this.occupations.add(occupation);
    }

    /**
     * Retrieves a list of all toxic product names present in this warehouse.
     *
     * @return List of toxic product names, or empty list if none found
     */
    public List<String> retrieveToxicProductNames() {
        List<String> toxicProducts = new ArrayList<>();
        
        for (ProductOccupation occupation : occupations) {
            Product product = occupation.getProduct();
            if (product.isToxic()) {
                toxicProducts.add(product.getName());
            }
        }
        
        return toxicProducts;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in this warehouse.
     *
     * @return List of unique suppliers, or empty list if no products
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        Set<Supplier> uniqueSuppliers = new HashSet<>();
        
        for (ProductOccupation occupation : occupations) {
            Product product = occupation.getProduct();
            if (product.getSupplier() != null) {
                uniqueSuppliers.add(product.getSupplier());
            }
        }
        
        return new ArrayList<>(uniqueSuppliers);
    }

    /**
     * Checks if this warehouse contains a specific product by name.
     *
     * @param productName Name of the product to check
     * @return true if the product exists in this warehouse, false otherwise
     */
    public boolean containsProduct(String productName) {
        for (ProductOccupation occupation : occupations) {
            if (productName.equals(occupation.getProduct().getName())) {
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
     * Default constructor for ProductOccupation.
     */
    public ProductOccupation() {}

    /**
     * Gets the volume occupied by the product.
     *
     * @return Volume in m³
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * Sets the volume occupied by the product.
     *
     * @param volume Volume in m³
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
     * @param product Product
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
     * Default constructor for Product.
     */
    public Product() {}

    /**
     * Gets the name of the product.
     *
     * @return Product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name Product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the product is toxic.
     *
     * @return true if toxic, false otherwise
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
     * @return Supplier
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the product.
     *
     * @param supplier Supplier
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
     * Default constructor for Supplier.
     */
    public Supplier() {}

    /**
     * Gets the name of the supplier.
     *
     * @return Supplier name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the supplier.
     *
     * @param name Supplier name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the supplier.
     *
     * @return Supplier address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the supplier.
     *
     * @param address Supplier address
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