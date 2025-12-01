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

    /** Unparameterized constructor */
    public Supplier() {
        // default constructor
    }

    /** Parameterized constructor for convenience */
    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /** @return supplier name */
    public String getName() {
        return name;
    }

    /** @param name supplier name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return supplier address */
    public String getAddress() {
        return address;
    }

    /** @param address supplier address */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Equality based on name and address – useful for distinct collections. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;
        return Objects.equals(name, supplier.name) &&
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
    private Supplier supplier;
    private boolean toxic;
    private double volume; // cubic meters (m³)

    /** Unparameterized constructor */
    public Product() {
        // default constructor
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

    /** @param name product name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return product supplier */
    public Supplier getSupplier() {
        return supplier;
    }

    /** @param supplier product supplier */
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

    /** @return product volume in cubic meters */
    public double getVolume() {
        return volume;
    }

    /** @param volume product volume in cubic meters */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Product{name='" + name + '\'' +
                ", supplier=" + supplier +
                ", toxic=" + toxic +
                ", volume=" + volume + '}';
    }
}

/**
 * Represents a warehouse located in a city.
 */
 class Warehouse {

    private String city;
    private String address;
    private double surface; // square meters (m²)
    private List<Product> products;

    /** Unparameterized constructor */
    public Warehouse() {
        this.products = new ArrayList<>();
    }

    /** Parameterized constructor for convenience */
    public Warehouse(String city, String address, double surface) {
        this.city = city;
        this.address = address;
        this.surface = surface;
        this.products = new ArrayList<>();
    }

    /** @return city where the warehouse is located */
    public String getCity() {
        return city;
    }

    /** @param city city where the warehouse is located */
    public void setCity(String city) {
        this.city = city;
    }

    /** @return address of the warehouse */
    public String getAddress() {
        return address;
    }

    /** @param address address of the warehouse */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return surface area in square meters */
    public double getSurface() {
        return surface;
    }

    /** @param surface surface area in square meters */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /** @return mutable list of products stored in this warehouse */
    public List<Product> getProducts() {
        return products;
    }

    /** @param products list of products to store in this warehouse */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Adds a product to the warehouse.
     *
     * @param product product to add
     */
    public void addProduct(Product product) {
        if (product != null) {
            this.products.add(product);
        }
    }

    /**
     * Retrieves a list of all toxic product names present in this warehouse.
     *
     * @return list of toxic product names; empty list if none
     */
    public List<String> getToxicProductNames() {
        List<String> toxicNames = new ArrayList<>();
        for (Product p : products) {
            if (p.isToxic()) {
                toxicNames.add(p.getName());
            }
        }
        return toxicNames;
    }

    /**
     * Retrieves a set of distinct suppliers whose products are stored in this warehouse.
     *
     * @return distinct suppliers; empty set if none
     */
    public Set<Supplier> getDistinctSuppliers() {
        Set<Supplier> suppliers = new HashSet<>();
        for (Product p : products) {
            if (p.getSupplier() != null) {
                suppliers.add(p.getSupplier());
            }
        }
        return suppliers;
    }

    /**
     * Checks whether a product with the given name is stored in this warehouse.
     *
     * @param productName name of the product to look for
     * @return true if the product exists in the warehouse, false otherwise
     */
    public boolean containsProduct(String productName) {
        if (productName == null) {
            return false;
        }
        for (Product p : products) {
            if (productName.equals(p.getName())) {
                return true;
            }
        }
        return false;
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
 * Represents a company that owns multiple warehouses.
 */
 class Company {

    private String name;
    private List<Warehouse> warehouses;

    /** Unparameterized constructor */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /** Parameterized constructor for convenience */
    public Company(String name) {
        this.name = name;
        this.warehouses = new ArrayList<>();
    }

    /** @return company name */
    public String getName() {
        return name;
    }

    /** @param name company name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return mutable list of warehouses owned by the company */
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
     * @param warehouse warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        if (warehouse != null) {
            this.warehouses.add(warehouse);
        }
    }

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse.
     *
     * @param city the city of the warehouse to query
     * @return list of toxic product names; empty list if none or warehouse does not exist
     */
    public List<String> getToxicProductNamesInWarehouse(String city) {
        Warehouse w = findWarehouseByCity(city);
        if (w == null) {
            return Collections.emptyList();
        }
        return w.getToxicProductNames();
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     *
     * @param city the city of the warehouse to query
     * @return list of distinct suppliers; empty list if none or warehouse does not exist
     */
    public List<Supplier> getDistinctSuppliersInWarehouse(String city) {
        Warehouse w = findWarehouseByCity(city);
        if (w == null) {
            return Collections.emptyList();
        }
        Set<Supplier> supplierSet = w.getDistinctSuppliers();
        return new ArrayList<>(supplierSet);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     *
     * @param supplierName name of the supplier to aggregate volumes for
     * @return total volume in cubic meters; 0 if no matching products are found
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        if (supplierName == null) {
            return 0.0;
        }
        double total = 0.0;
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
     * @param city        city of the warehouse to examine
     * @param productName name of the product to look for
     * @return true if the product exists in the warehouse; false otherwise (including non‑existent warehouse)
     */
    public boolean warehouseContainsProduct(String city, String productName) {
        Warehouse w = findWarehouseByCity(city);
        if (w == null) {
            return false;
        }
        return w.containsProduct(productName);
    }

    /**
     * Verifies that the company contains a specific product by product name and returns its locations.
     *
     * @param productName name of the product to locate
     * @return list of strings formatted as "city - address" for each warehouse containing the product;
     * empty list if the product is not found in any warehouse
     */
    public List<String> getProductLocations(String productName) {
        if (productName == null) {
            return Collections.emptyList();
        }
        List<String> locations = new ArrayList<>();
        for (Warehouse w : warehouses) {
            if (w.containsProduct(productName)) {
                locations.add(w.getCity() + " - " + w.getAddress());
            }
        }
        return locations;
    }

    /**
     * Helper method to locate a warehouse by its city.
     *
     * @param city city of the warehouse
     * @return matching Warehouse object or null if not found
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

    @Override
    public String toString() {
        return "Company{name='" + name + '\'' + ", warehouses=" + warehouses + '}';
    }
}