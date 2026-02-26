import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class Company {
    private List<Warehouse> warehouses;

    public Company() {
        this.warehouses = new ArrayList<>();
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void addWarehouses(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     *
     * @param supplierName The name of the supplier to count the volume for.
     * @return The total volume of products supplied by the specified supplier.
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        Double totalVolume = 0.0;
        for (Warehouse warehouse : warehouses) {
            for (ProductOccupation occupation : warehouse.getOccupations()) {
                if (occupation.getProduct().getSupplier().getName().equals(supplierName)) {
                    totalVolume += occupation.getVolume();
                }
            }
        }
        return totalVolume;
    }

    /**
     * Finds the locations of a specific product by product name.
     *
     * @param productName The name of the product to find locations for.
     * @return A list of warehouse cities and addresses where the product is found.
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
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

    public void addOccupations(ProductOccupation occupation) {
        this.occupations.add(occupation);
    }

    /**
     * Retrieves a list of all toxic product names present in the warehouse.
     *
     * @return A list of toxic product names.
     */
    public List<String> retrieveToxicProductNames() {
        List<String> toxicProducts = new ArrayList<>();
        for (ProductOccupation occupation : occupations) {
            if (occupation.getProduct().isToxic()) {
                toxicProducts.add(occupation.getProduct().getName());
            }
        }
        return toxicProducts;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in the warehouse.
     *
     * @return A list of distinct suppliers.
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        for (ProductOccupation occupation : occupations) {
            Supplier supplier = occupation.getProduct().getSupplier();
            if (!suppliers.contains(supplier)) {
                suppliers.add(supplier);
            }
        }
        return suppliers;
    }

    /**
     * Verifies if the warehouse contains a specific product by product name.
     *
     * @param productName The name of the product to check.
     * @return true if the product is found, false otherwise.
     */
    public boolean containsProduct(String productName) {
        for (ProductOccupation occupation : occupations) {
            if (occupation.getProduct().getName().equals(productName)) {
                return true;
            }
        }
        return false;
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

    public void setToxic(boolean isToxic) {
        this.isToxic = isToxic;
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