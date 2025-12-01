import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

class Company {
    private List<Warehouse> warehouses;

    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Retrieves the list of all warehouses.
     *
     * @return List of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Adds a warehouse to the company.
     *
     * @param warehouse The warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     *
     * @param supplierName The name of the supplier
     * @return The total volume of products supplied by the supplier
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        return this.warehouses.stream()
                .flatMap(warehouse -> warehouse.getOccupations().stream())
                .filter(occupation -> occupation.getProduct().getSupplier().getName().equals(supplierName))
                .mapToDouble(ProductOccupation::getVolume)
                .sum();
    }

    /**
     * Finds the locations of a specific product by product name.
     *
     * @param productName The name of the product
     * @return A list of warehouse cities and addresses where the product is found
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        for (Warehouse warehouse : this.warehouses) {
            for (ProductOccupation occupation : warehouse.getOccupations()) {
                if (occupation.getProduct().getName().equals(productName)) {
                    Map.Entry<String, String> entry = new java.util.AbstractMap.SimpleEntry<>(warehouse.getCity(), warehouse.getAddress());
                    locations.add(entry);
                }
            }
        }
        return locations;
    }
}

class Warehouse {
    private String city;
    private String address;
    private Double surface;
    private List<ProductOccupation> occupations;

    public Warehouse() {
        this.occupations = new ArrayList<>();
    }

    /**
     * Retrieves the city of the warehouse.
     *
     * @return The city of the warehouse
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the warehouse.
     *
     * @param city The city of the warehouse
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Retrieves the address of the warehouse.
     *
     * @return The address of the warehouse
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the warehouse.
     *
     * @param address The address of the warehouse
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the surface of the warehouse.
     *
     * @return The surface of the warehouse
     */
    public Double getSurface() {
        return surface;
    }

    /**
     * Sets the surface of the warehouse.
     *
     * @param surface The surface of the warehouse
     */
    public void setSurface(Double surface) {
        this.surface = surface;
    }

    /**
     * Retrieves the list of product occupations in the warehouse.
     *
     * @return List of product occupations
     */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /**
     * Adds a product occupation to the warehouse.
     *
     * @param occupation The product occupation to add
     */
    public void addOccupation(ProductOccupation occupation) {
        this.occupations.add(occupation);
    }

    /**
     * Retrieves a list of all toxic product names present in the warehouse.
     *
     * @return List of toxic product names
     */
    public List<String> retrieveToxicProductNames() {
        return this.occupations.stream()
                .filter(occupation -> occupation.getProduct().isToxic())
                .map(occupation -> occupation.getProduct().getName())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in the warehouse.
     *
     * @return List of distinct suppliers
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        return this.occupations.stream()
                .map(ProductOccupation::getProduct)
                .map(Product::getSupplier)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Verifies if the warehouse contains a specific product by product name.
     *
     * @param productName The name of the product
     * @return true if the product is found, false otherwise
     */
    public boolean containsProduct(String productName) {
        return this.occupations.stream()
                .anyMatch(occupation -> occupation.getProduct().getName().equals(productName));
    }
}

class ProductOccupation {
    private Double volume;
    private Product product;

    public ProductOccupation() {
    }

    /**
     * Retrieves the volume of the product occupation.
     *
     * @return The volume of the product occupation
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * Sets the volume of the product occupation.
     *
     * @param volume The volume of the product occupation
     */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /**
     * Retrieves the product of the product occupation.
     *
     * @return The product of the product occupation
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product of the product occupation.
     *
     * @param product The product of the product occupation
     */
    public void setProduct(Product product) {
        this.product = product;
    }
}

class Product {
    private String name;
    private boolean isToxic;
    private Supplier supplier;

    public Product() {
    }

    /**
     * Retrieves the name of the product.
     *
     * @return The name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name The name of the product
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
     * @param toxic true if the product is toxic, false otherwise
     */
    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    /**
     * Retrieves the supplier of the product.
     *
     * @return The supplier of the product
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the product.
     *
     * @param supplier The supplier of the product
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

class Supplier {
    private String name;
    private String address;

    public Supplier() {
    }

    /**
     * Retrieves the name of the supplier.
     *
     * @return The name of the supplier
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the supplier.
     *
     * @param name The name of the supplier
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the address of the supplier.
     *
     * @return The address of the supplier
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the supplier.
     *
     * @param address The address of the supplier
     */
    public void setAddress(String address) {
        this.address = address;
    }
}