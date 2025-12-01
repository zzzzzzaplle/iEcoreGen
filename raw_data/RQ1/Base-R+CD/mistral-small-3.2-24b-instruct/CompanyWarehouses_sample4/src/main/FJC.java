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
     * Retrieves the list of all warehouses in the company.
     * @return List of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Adds a warehouse to the company.
     * @param warehouse Warehouse to be added
     */
    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     * @param supplierName Name of the supplier
     * @return Total volume of products supplied by the supplier
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        return warehouses.stream()
                .flatMap(warehouse -> warehouse.getOccupations().stream())
                .filter(occupation -> occupation.getProduct().getSupplier().getName().equals(supplierName))
                .mapToDouble(ProductOccupation::getVolume)
                .sum();
    }

    /**
     * Finds the locations of a specific product by product name.
     * @param productName Name of the product
     * @return List of warehouse cities and addresses where the product is found
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.containsProduct(productName)) {
                locations.add(new java.util.AbstractMap.SimpleEntry<>(warehouse.getCity(), warehouse.getAddress()));
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getSurface() {
        return surface;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    public void addOccupation(ProductOccupation occupation) {
        this.occupations.add(occupation);
    }

    /**
     * Retrieves a list of all toxic product names present in the warehouse.
     * @return List of toxic product names
     */
    public List<String> retrieveToxicProductNames() {
        return occupations.stream()
                .filter(occupation -> occupation.getProduct().isToxic())
                .map(occupation -> occupation.getProduct().getName())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in the warehouse.
     * @return List of distinct suppliers
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        return occupations.stream()
                .map(ProductOccupation::getProduct)
                .map(Product::getSupplier)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Verifies if the warehouse contains a specific product by product name.
     * @param productName Name of the product
     * @return true if the product is found, false otherwise
     */
    public boolean containsProduct(String productName) {
        return occupations.stream()
                .anyMatch(occupation -> occupation.getProduct().getName().equals(productName));
    }
}

class ProductOccupation {
    private Double volume;
    private Product product;

    public ProductOccupation() {
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Product getProduct() {
        return product;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isToxic() {
        return isToxic;
    }

    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

class Supplier {
    private String name;
    private String address;

    public Supplier() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}