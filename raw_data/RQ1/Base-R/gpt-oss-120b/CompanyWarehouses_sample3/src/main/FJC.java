import java.util.ArrayList;
import java.util.Collections;
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

    /** No‑argument constructor required by the specification. */
    public Supplier() {
        // fields stay null until set via setters
    }

    /** Getter for supplier name. */
    public String getName() {
        return name;
    }

    /** Setter for supplier name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for supplier address. */
    public String getAddress() {
        return address;
    }

    /** Setter for supplier address. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Equality based on both name and address (both are required to identify a supplier). */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier)) return false;
        Supplier supplier = (Supplier) o;
        Objects.equals(name, supplier.name) &&
               Objects.equals(address, supplier.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public String toString() {
        return "Supplier{name='" + name + '\'' + ", address='" + address + '\'' + '}';
    }
}

/**
 * Represents a product stored in a warehouse.
 */
 class Product {

    private String name;
    private boolean toxic;
    private double volume;          // in cubic meters (m³)
    private Supplier supplier;      // the supplier of this product

    /** No‑argument constructor required by the specification. */
    public Product() {
        // fields stay default values until set via setters
    }

    /** Getter for product name. */
    public String getName() {
        return name;
    }

    /** Setter for product name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for toxic flag. */
    public boolean isToxic() {
        return toxic;
    }

    /** Setter for toxic flag. */
    public void setToxic(boolean toxic) {
        this.toxic = toxic;
    }

    /** Getter for product volume (m³). */
    public double getVolume() {
        return volume;
    }

    /** Setter for product volume (m³). */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /** Getter for product supplier. */
    public Supplier getSupplier() {
        return supplier;
    }

    /** Setter for product supplier. */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Product{name='" + name + '\'' +
                ", toxic=" + toxic +
                ", volume=" + volume +
                ", supplier=" + supplier + '}';
    }
}

/**
 * Represents a warehouse located in a city.
 */
 class Warehouse {

    private String city;                // city where the warehouse is located
    private String address;             // street address of the warehouse
    private double surface;             // in square meters (m²)
    private List<Product> products;     // products stored in this warehouse

    /** No‑argument constructor required by the specification. */
    public Warehouse() {
        this.products = new ArrayList<>();
    }

    /** Getter for city. */
    public String getCity() {
        return city;
    }

    /** Setter for city. */
    public void setCity(String city) {
        this.city = city;
    }

    /** Getter for address. */
    public String getAddress() {
        return address;
    }

    /** Setter for address. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Getter for surface (m²). */
    public double getSurface() {
        return surface;
    }

    /** Setter for surface (m²). */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /** Getter for the mutable list of products. */
    public List<Product> getProducts() {
        return products;
    }

    /** Setter for the list of products (replaces current list). */
    public void setProducts(List<Product> products) {
        this.products = products == null ? new ArrayList<>() : products;
    }

    /** Adds a single product to this warehouse. */
    public void addProduct(Product product) {
        if (product != null) {
            this.products.add(product);
        }
    }

    /** Removes a product from this warehouse. */
    public boolean removeProduct(Product product) {
        return this.products.remove(product);
    }

    @Override
    public String toString() {
        return "Warehouse{city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", surface=" + surface +
                ", products=" + products + '}';
    }
}

/**
 * Represents the whole company, containing multiple warehouses.
 */
 class Company {

    private List<Warehouse> warehouses;   // all warehouses belonging to the company

    /** No‑argument constructor required by the specification. */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /** Getter for the list of warehouses. */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /** Setter for the list of warehouses (replaces current list). */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses == null ? new ArrayList<>() : warehouses;
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
     * @param city the city identifying the warehouse
     * @return an immutable list of toxic product names; empty if none or warehouse not found
     */
    public List<String> getToxicProductNamesInWarehouse(String city) {
        Warehouse wh = findWarehouseByCity(city);
        if (wh == null) {
            return Collections.emptyList();
        }
        List<String> toxicNames = new ArrayList<>();
        for (Product p : wh.getProducts()) {
            if (p.isToxic()) {
                toxicNames.add(p.getName());
            }
        }
        return Collections.unmodifiableList(toxicNames);
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     *
     * @param city the city identifying the warehouse
     * @return an immutable list of distinct suppliers; empty if none or warehouse not found
     */
    public List<Supplier> getDistinctSuppliersInWarehouse(String city) {
        Warehouse wh = findWarehouseByCity(city);
        if (wh == null) {
            return Collections.emptyList();
        }
        Set<Supplier> distinct = new HashSet<>();
        for (Product p : wh.getProducts()) {
            if (p.getSupplier() != null) {
                distinct.add(p.getSupplier());
            }
        }
        return Collections.unmodifiableList(new ArrayList<>(distinct));
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     *
     * @param supplierName the name of the supplier to search for
     * @return total volume (m³) of matching products; 0 if none found
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        if (supplierName == null) {
            return 0.0;
        }
        double total = 0.0;
        for (Warehouse wh : warehouses) {
            for (Product p : wh.getProducts()) {
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
     * @param city        the city identifying the warehouse
     * @param productName the name of the product to look for
     * @return {@code true} if the product exists in the warehouse; {@code false} otherwise
     */
    public boolean warehouseContainsProduct(String city, String productName) {
        Warehouse wh = findWarehouseByCity(city);
        if (wh == null || productName == null) {
            return false;
        }
        for (Product p : wh.getProducts()) {
            if (productName.equals(p.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves all locations (city and address) of warehouses that store a product with the given name.
     *
     * @param productName the name of the product to search for
     * @return an immutable list of strings formatted as "city - address"; empty if not found
     */
    public List<String> getProductLocations(String productName) {
        if (productName == null) {
            return Collections.emptyList();
        }
        List<String> locations = new ArrayList<>();
        for (Warehouse wh : warehouses) {
            for (Product p : wh.getProducts()) {
                if (productName.equals(p.getName())) {
                    locations.add(wh.getCity() + " - " + wh.getAddress());
                    break; // no need to continue scanning this warehouse once found
                }
            }
        }
        return Collections.unmodifiableList(locations);
    }

    /**
     * Helper method to locate a warehouse by its city.
     *
     * @param city the city name of the warehouse
     * @return the {@link Warehouse} instance if found; {@code null} otherwise
     */
    private Warehouse findWarehouseByCity(String city) {
        if (city == null) {
            return null;
        }
        for (Warehouse wh : warehouses) {
            if (city.equals(wh.getCity())) {
                return wh;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Company{warehouses=" + warehouses + '}';
    }
}