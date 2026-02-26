import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class Company {
    private List<Warehouse> warehouses;

    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Retrieves the list of all warehouses in the company.
     *
     * @return List of Warehouse objects
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
    public double countTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        for (Warehouse warehouse : warehouses) {
            for (ProductOccupation po : warehouse.getOccupations()) {
                Product product = po.getProduct();
                if (product.getSupplier().getName().equals(supplierName)) {
                    totalVolume += po.getVolume();
                }
            }
        }
        return totalVolume;
    }

    /**
     * Finds the locations of a specific product by product name.
     *
     * @param productName The name of the product
     * @return A list of warehouse cities and addresses where the product is found
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            for (ProductOccupation po : warehouse.getOccupations()) {
                if (po.getProduct().getName().equals(productName)) {
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
    private double surface;
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

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
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
     *
     * @return List of toxic product names
     */
    public List<String> retrieveToxicProductNames() {
        List<String> toxicProducts = new ArrayList<>();
        for (ProductOccupation po : occupations) {
            if (po.getProduct().isToxic()) {
                toxicProducts.add(po.getProduct().getName());
            }
        }
        return toxicProducts;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in the warehouse.
     *
     * @return List of distinct Supplier objects
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        for (ProductOccupation po : occupations) {
            Supplier supplier = po.getProduct().getSupplier();
            if (!suppliers.contains(supplier)) {
                suppliers.add(supplier);
            }
        }
        return suppliers;
    }

    /**
     * Verifies if the warehouse contains a specific product by product name.
     *
     * @param productName The name of the product to check
     * @return true if the product is found, false otherwise
     */
    public boolean containsProduct(String productName) {
        for (ProductOccupation po : occupations) {
            if (po.getProduct().getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }
}

class ProductOccupation {
    private double volume;
    private Product product;

    public ProductOccupation() {
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
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