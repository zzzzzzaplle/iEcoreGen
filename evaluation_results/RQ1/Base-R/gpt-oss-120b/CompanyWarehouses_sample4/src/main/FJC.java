import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a supplier of products.
 */
 class Supplier {

    /** Supplier name */
    private String name;

    /** Supplier address */
    private String address;

    /** No‑argument constructor required for tests */
    public Supplier() {
    }

    /** Full constructor for convenience */
    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /** @return the supplier name */
    public String getName() {
        return name;
    }

    /** @param name the supplier name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the supplier address */
    public String getAddress() {
        return address;
    }

    /** @param address the supplier address to set */
    public void setAddress(String address) {
        this.address = address;
    }
}

/**
 * Represents a product stored in a warehouse.
 */
 class Product {

    /** Product name */
    private String name;

    /** True if the product is toxic */
    private boolean toxic;

    /** Volume in cubic metres (m³) */
    private double volume;

    /** Supplier of this product */
    private Supplier supplier;

    /** No‑argument constructor required for tests */
    public Product() {
    }

    /** Full constructor for convenience */
    public Product(String name, boolean toxic, double volume, Supplier supplier) {
        this.name = name;
        this.toxic = toxic;
        this.volume = volume;
        this.supplier = supplier;
    }

    /** @return the product name */
    public String getName() {
        return name;
    }

    /** @param name the product name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return true if the product is toxic */
    public boolean isToxic() {
        return toxic;
    }

    /** @param toxic true if the product is toxic */
    public void setToxic(boolean toxic) {
        this.toxic = toxic;
    }

    /** @return the volume of the product (m³) */
    public double getVolume() {
        return volume;
    }

    /** @param volume the volume to set (m³) */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /** @return the supplier of the product */
    public Supplier getSupplier() {
        return supplier;
    }

    /** @param supplier the supplier to set */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents a warehouse that stores products.
 */
 class Warehouse {

    /** Surface area of the warehouse (m²) */
    private double surface;

    /** Physical address of the warehouse */
    private String address;

    /** City where the warehouse is located (used as identifier) */
    private String city;

    /** List of products stored in this warehouse */
    private List<Product> products = new ArrayList<>();

    /** No‑argument constructor required for tests */
    public Warehouse() {
    }

    /** Full constructor for convenience */
    public Warehouse(double surface, String address, String city) {
        this.surface = surface;
        this.address = address;
        this.city = city;
    }

    /** @return the surface area (m²) */
    public double getSurface() {
        return surface;
    }

    /** @param surface the surface area to set (m²) */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /** @return the warehouse address */
    public String getAddress() {
        return address;
    }

    /** @param address the address to set */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return the city where the warehouse is located */
    public String getCity() {
        return city;
    }

    /** @param city the city to set */
    public void setCity(String city) {
        this.city = city;
    }

    /** @return the list of products stored in this warehouse */
    public List<Product> getProducts() {
        return products;
    }

    /** @param products the product list to set */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Adds a product to this warehouse.
     *
     * @param product the product to add
     */
    public void addProduct(Product product) {
        if (product != null) {
            this.products.add(product);
        }
    }
}

/**
 * Represents the whole company that owns several warehouses.
 */
 class Company {

    /** List of warehouses owned by the company */
    private List<Warehouse> warehouses = new ArrayList<>();

    /** No‑argument constructor required for tests */
    public Company() {
    }

    /** @return the list of warehouses */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /** @param warehouses the list of warehouses to set */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Adds a warehouse to the company.
     *
     * @param warehouse the warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        if (warehouse != null) {
            this.warehouses.add(warehouse);
        }
    }

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse.
     *
     * @param city the city identifying the warehouse
     * @return a list of toxic product names; empty list if none found or warehouse does not exist
     */
    public List<String> getToxicProductNamesInWarehouse(String city) {
        List<String> toxicNames = new ArrayList<>();
        Warehouse w = findWarehouseByCity(city);
        if (w != null) {
            for (Product p : w.getProducts()) {
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
     * @param city the city identifying the warehouse
     * @return a list of distinct suppliers; empty list if none found or warehouse does not exist
     */
    public List<Supplier> getDistinctSuppliersInWarehouse(String city) {
        Set<String> seen = new HashSet<>();
        List<Supplier> distinctSuppliers = new ArrayList<>();
        Warehouse w = findWarehouseByCity(city);
        if (w != null) {
            for (Product p : w.getProducts()) {
                Supplier s = p.getSupplier();
                if (s != null && !seen.contains(s.getName())) {
                    seen.add(s.getName());
                    distinctSuppliers.add(s);
                }
            }
        }
        return distinctSuppliers;
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the whole company.
     *
     * @param supplierName the name of the supplier
     * @return the total volume (m³) of all products from that supplier; 0 if none are found
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
     * @param city        the city identifying the warehouse
     * @param productName the name of the product to look for
     * @return true if the product exists in the warehouse; false otherwise (including non‑existent warehouse)
     */
    public boolean warehouseContainsProduct(String city, String productName) {
        Warehouse w = findWarehouseByCity(city);
        if (w == null || productName == null) {
            return false;
        }
        for (Product p : w.getProducts()) {
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
     * @return a list of strings in the format "city - address"; empty list if product not found anywhere
     */
    public List<String> getProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        if (productName == null) {
            return locations;
        }
        for (Warehouse w : warehouses) {
            for (Product p : w.getProducts()) {
                if (productName.equals(p.getName())) {
                    locations.add(w.getCity() + " - " + w.getAddress());
                    break; // stop scanning this warehouse once product is found
                }
            }
        }
        return locations;
    }

    /**
     * Helper method to locate a warehouse by its city.
     *
     * @param city the city identifier
     * @return the Warehouse object if found; null otherwise
     */
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