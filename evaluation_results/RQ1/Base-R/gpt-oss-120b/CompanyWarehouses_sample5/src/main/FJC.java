import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * Represents a supplier of products.
 */
 class Supplier {

    private String name;
    private String address;

    /** Unparameterized constructor */
    public Supplier() {
    }

    /** Parameterized constructor for convenience */
    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /** @return the supplier's name */
    public String getName() {
        return name;
    }

    /** @param name the supplier's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the supplier's address */
    public String getAddress() {
        return address;
    }

    /** @param address the supplier's address to set */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Equality based on supplier name (assumed unique) */
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
    private Supplier supplier;
    private boolean toxic;
    private double volume; // cubic meters (m³)

    /** Unparameterized constructor */
    public Product() {
    }

    /** Parameterized constructor for convenience */
    public Product(String name, Supplier supplier, boolean toxic, double volume) {
        this.name = name;
        this.supplier = supplier;
        this.toxic = toxic;
        this.volume = volume;
    }

    /** @return product name */
    public String getName() {
        return name;
    }

    /** @param name product name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return supplier of the product */
    public Supplier getSupplier() {
        return supplier;
    }

    /** @param supplier supplier to set */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /** @return true if the product is toxic */
    public boolean isToxic() {
        return toxic;
    }

    /** @param toxic true if the product is toxic */
    public void setToxic(boolean toxic) {
        this.toxic = toxic;
    }

    /** @return volume occupied by the product in cubic meters */
    public double getVolume() {
        return volume;
    }

    /** @param volume volume to set (cubic meters) */
    public void setVolume(double volume) {
        this.volume = volume;
    }
}

/**
 * Represents a warehouse located in a city.
 */
 class Warehouse {

    private String city;
    private String address;
    private double surface; // square meters (m²)
    private List<Product> products = new ArrayList<>();

    /** Unparameterized constructor */
    public Warehouse() {
    }

    /** Parameterized constructor for convenience */
    public Warehouse(String city, String address, double surface) {
        this.city = city;
        this.address = address;
        this.surface = surface;
    }

    /** @return city where the warehouse is located */
    public String getCity() {
        return city;
    }

    /** @param city city to set */
    public void setCity(String city) {
        this.city = city;
    }

    /** @return address of the warehouse */
    public String getAddress() {
        return address;
    }

    /** @param address address to set */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return surface area of the warehouse in square meters */
    public double getSurface() {
        return surface;
    }

    /** @param surface surface area to set (square meters) */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /** @return list of products stored in the warehouse */
    public List<Product> getProducts() {
        return products;
    }

    /** @param products list of products to set */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Adds a product to the warehouse.
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
 * Represents a company that owns several warehouses.
 */
 class Company {

    private String name;
    private List<Warehouse> warehouses = new ArrayList<>();

    /** Unparameterized constructor */
    public Company() {
    }

    /** Parameterized constructor for convenience */
    public Company(String name) {
        this.name = name;
    }

    /** @return company name */
    public String getName() {
        return name;
    }

    /** @param name company name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return list of warehouses owned by the company */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /** @param warehouses list of warehouses to set */
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
     * @return list of toxic product names; empty list if none or warehouse does not exist
     */
    public List<String> getToxicProductNamesInWarehouse(String city) {
        List<String> toxicNames = new ArrayList<>();
        Warehouse wh = findWarehouseByCity(city);
        if (wh == null) {
            return toxicNames; // empty
        }

        Set<String> seen = new HashSet<>();
        for (Product p : wh.getProducts()) {
            if (p.isToxic() && !seen.contains(p.getName())) {
                toxicNames.add(p.getName());
                seen.add(p.getName());
            }
        }
        return toxicNames;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     *
     * @param city the city identifying the warehouse
     * @return list of distinct suppliers; empty list if warehouse does not exist or contains no products
     */
    public List<Supplier> getDistinctSuppliersInWarehouse(String city) {
        List<Supplier> distinctSuppliers = new ArrayList<>();
        Warehouse wh = findWarehouseByCity(city);
        if (wh == null) {
            return distinctSuppliers; // empty
        }

        Set<Supplier> supplierSet = new HashSet<>();
        for (Product p : wh.getProducts()) {
            Supplier s = p.getSupplier();
            if (s != null && supplierSet.add(s)) {
                distinctSuppliers.add(s);
            }
        }
        return distinctSuppliers;
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     *
     * @param supplierName the name of the supplier
     * @return total volume (cubic meters) of all products from the supplier; 0 if none are found
     */
    public double countTotalVolumeBySupplier(String supplierName) {
        double total = 0.0;
        if (supplierName == null) {
            return total;
        }
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
     * @return true if the product exists in the warehouse; false otherwise (including non‑existent warehouse)
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
     * Finds all locations (city and address) of a product with the given name across the company's warehouses.
     *
     * @param productName the name of the product to search for
     * @return list of strings formatted as "City: <city>, Address: <address>"; empty list if not found
     */
    public List<String> findProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        if (productName == null) {
            return locations;
        }
        for (Warehouse wh : warehouses) {
            for (Product p : wh.getProducts()) {
                if (productName.equals(p.getName())) {
                    String loc = "City: " + wh.getCity() + ", Address: " + wh.getAddress();
                    locations.add(loc);
                    break; // avoid duplicate entries for the same warehouse
                }
            }
        }
        return locations;
    }

    /**
     * Helper method to locate a warehouse by its city.
     *
     * @param city the city name to search for
     * @return the Warehouse instance if found; null otherwise
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
}