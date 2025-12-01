import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a supplier of products.
 */
 class Supplier {

    private String name;
    private String address;

    /** Unparameterized constructor. */
    public Supplier() {
        // default constructor
    }

    /** Parameterized constructor for convenience. */
    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /** @return the supplier's name. */
    public String getName() {
        return name;
    }

    /** @param name the supplier's name to set. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the supplier's address. */
    public String getAddress() {
        return address;
    }

    /** @param address the supplier's address to set. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Equality based on the supplier's name (assumed unique). */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;
        return Objects.equals(name, supplier.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Represents a product stored in a warehouse.
 */
 class Product {

    private String name;
    private boolean toxic;
    private double volume; // in cubic meters (m³)
    private Supplier supplier;

    /** Unparameterized constructor. */
    public Product() {
        // default constructor
    }

    /** Parameterized constructor for convenience. */
    public Product(String name, boolean toxic, double volume, Supplier supplier) {
        this.name = name;
        this.toxic = toxic;
        this.volume = volume;
        this.supplier = supplier;
    }

    /** @return the product name. */
    public String getName() {
        return name;
    }

    /** @param name the product name to set. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return true if the product is toxic. */
    public boolean isToxic() {
        return toxic;
    }

    /** @param toxic set the toxic flag. */
    public void setToxic(boolean toxic) {
        this.toxic = toxic;
    }

    /** @return the volume occupied by the product (m³). */
    public double getVolume() {
        return volume;
    }

    /** @param volume the volume to set (m³). */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /** @return the supplier of the product. */
    public Supplier getSupplier() {
        return supplier;
    }

    /** @param supplier the supplier to set. */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents a warehouse located in a city.
 */
 class Warehouse {

    private String city;
    private String address;
    private double surface; // in square meters (m²)
    private List<Product> products;

    /** Unparameterized constructor. */
    public Warehouse() {
        this.products = new ArrayList<>();
    }

    /** Parameterized constructor for convenience. */
    public Warehouse(String city, String address, double surface) {
        this.city = city;
        this.address = address;
        this.surface = surface;
        this.products = new ArrayList<>();
    }

    /** @return the city where the warehouse is located. */
    public String getCity() {
        return city;
    }

    /** @param city the city to set. */
    public void setCity(String city) {
        this.city = city;
    }

    /** @return the address of the warehouse. */
    public String getAddress() {
        return address;
    }

    /** @param address the address to set. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return the surface area of the warehouse (m²). */
    public double getSurface() {
        return surface;
    }

    /** @param surface the surface area to set (m²). */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /** @return the list of products stored in the warehouse. */
    public List<Product> getProducts() {
        return products;
    }

    /** @param products the list of products to set. */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /** Adds a product to the warehouse. */
    public void addProduct(Product product) {
        if (product != null) {
            this.products.add(product);
        }
    }
}

/**
 * Represents a company that owns several warehouses.
 */
 class Company {

    private String name;
    private List<Warehouse> warehouses;

    /** Unparameterized constructor. */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /** Parameterized constructor for convenience. */
    public Company(String name) {
        this.name = name;
        this.warehouses = new ArrayList<>();
    }

    /** @return the company's name. */
    public String getName() {
        return name;
    }

    /** @param name the company's name to set. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the list of warehouses owned by the company. */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /** @param warehouses the list of warehouses to set. */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /** Adds a warehouse to the company. */
    public void addWarehouse(Warehouse warehouse) {
        if (warehouse != null) {
            this.warehouses.add(warehouse);
        }
    }

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse.
     *
     * @param city the city where the warehouse is located
     * @return a list of toxic product names; empty list if none or warehouse does not exist
     */
    public List<String> getToxicProductNamesInWarehouse(String city) {
        List<String> toxicNames = new ArrayList<>();
        Warehouse warehouse = findWarehouseByCity(city);
        if (warehouse != null) {
            for (Product p : warehouse.getProducts()) {
                if (p.isToxic()) {
                    toxicNames.add(p.getName());
                }
            }
        }
        return toxicNames;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     *
     * @param city the city where the warehouse is located
     * @return a list of distinct {@link Supplier}s; empty list if none or warehouse does not exist
     */
    public List<Supplier> getDistinctSuppliersInWarehouse(String city) {
        Set<Supplier> supplierSet = new HashSet<>();
        Warehouse warehouse = findWarehouseByCity(city);
        if (warehouse != null) {
            for (Product p : warehouse.getProducts()) {
                if (p.getSupplier() != null) {
                    supplierSet.add(p.getSupplier());
                }
            }
        }
        return new ArrayList<>(supplierSet);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     *
     * @param supplierName the name of the supplier
     * @return the summed volume (m³) of all matching products; 0 if none are found
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double total = 0.0;
        if (supplierName == null) {
            return total;
        }
        for (Warehouse w : warehouses) {
            for (Product p : w.getProducts()) {
                Supplier s = p.getSupplier();
                if (s != null && supplierName.equals(s.getName())) {
                    total += p.getVolume();
                }
            }
        }
        return total;
    }

    /**
     * Verifies that a warehouse contains a specific product by product name.
     *
     * @param city        the city where the warehouse is located
     * @param productName the name of the product to look for
     * @return {@code true} if the product exists in the warehouse; {@code false} otherwise
     */
    public boolean warehouseContainsProduct(String city, String productName) {
        Warehouse warehouse = findWarehouseByCity(city);
        if (warehouse == null || productName == null) {
            return false;
        }
        for (Product p : warehouse.getProducts()) {
            if (productName.equals(p.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the locations (city and address) of all warehouses that store a product with the given name.
     *
     * @param productName the name of the product to search for
     * @return a list of strings formatted as "City: &lt;city&gt;, Address: &lt;address&gt;";
     *         empty list if the product is not stored anywhere
     */
    public List<String> getProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        if (productName == null) {
            return locations;
        }
        for (Warehouse w : warehouses) {
            for (Product p : w.getProducts()) {
                if (productName.equals(p.getName())) {
                    locations.add("City: " + w.getCity() + ", Address: " + w.getAddress());
                    break; // avoid duplicate entries for the same warehouse
                }
            }
        }
        return locations;
    }

    /** Helper method to locate a warehouse by its city. */
    private Warehouse findWarehouseByCity(String city) {
        if (city == null) {
            return null;
        }
        for (Warehouse w : warehouses) {
            if (city.equals(w.getCity())) {
                return w;
            }
        }
        return null;
    }
}