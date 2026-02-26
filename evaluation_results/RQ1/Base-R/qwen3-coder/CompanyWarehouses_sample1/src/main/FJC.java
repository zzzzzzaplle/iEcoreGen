import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a supplier of products.
 */
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

/**
 * Represents a product stored in warehouses.
 */
class Product {
    private String name;
    private Supplier supplier;
    private boolean toxic;
    private double volume; // in m^3

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public boolean isToxic() {
        return toxic;
    }

    public void setToxic(boolean toxic) {
        this.toxic = toxic;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}

/**
 * Represents a warehouse where products are stored.
 */
class Warehouse {
    private String address;
    private double surface; // in m²
    private List<Product> products;

    public Warehouse() {
        this.products = new ArrayList<>();
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Adds a product to this warehouse.
     *
     * @param product the product to add
     */
    public void addProduct(Product product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
    }

    /**
     * Retrieves a list of all toxic product names present in this warehouse.
     *
     * @return a list of toxic product names, or an empty list if none are found
     */
    public List<String> getToxicProductNames() {
        List<String> toxicNames = new ArrayList<>();
        if (products != null) {
            for (Product product : products) {
                if (product.isToxic()) {
                    toxicNames.add(product.getName());
                }
            }
        }
        return toxicNames;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in this warehouse.
     *
     * @return a list of distinct suppliers, or an empty list if no products exist
     */
    public List<Supplier> getDistinctSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        if (products != null) {
            for (Product product : products) {
                Supplier supplier = product.getSupplier();
                if (supplier != null && !suppliers.contains(supplier)) {
                    suppliers.add(supplier);
                }
            }
        }
        return suppliers;
    }

    /**
     * Verifies that this warehouse contains a specific product by product name.
     *
     * @param productName the name of the product to check
     * @return true if the product is in the warehouse, false otherwise
     */
    public boolean containsProduct(String productName) {
        if (productName == null || products == null) {
            return false;
        }
        for (Product product : products) {
            if (productName.equals(product.getName())) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Represents a company with multiple warehouses.
 */
class Company {
    private String name;
    private List<Warehouse> warehouses;

    public Company() {
        this.warehouses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Adds a warehouse to this company.
     *
     * @param warehouse the warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        if (warehouses == null) {
            warehouses = new ArrayList<>();
        }
        warehouses.add(warehouse);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within this company.
     *
     * @param supplierName the name of the supplier
     * @return the total volume of products supplied by the specified supplier, or 0 if none found
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        if (supplierName == null || warehouses == null) {
            return totalVolume;
        }
        
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getProducts() != null) {
                for (Product product : warehouse.getProducts()) {
                    if (product.getSupplier() != null && 
                        supplierName.equals(product.getSupplier().getName())) {
                        totalVolume += product.getVolume();
                    }
                }
            }
        }
        return totalVolume;
    }

    /**
     * Verifies that the company contains a specific product by product name and returns its locations.
     *
     * @param productName the name of the product to find
     * @return a list of warehouse addresses where the product is found, or an empty list if not found
     */
    public List<String> getProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        if (productName == null || warehouses == null) {
            return locations;
        }
        
        for (Warehouse warehouse : warehouses) {
            if (warehouse.containsProduct(productName)) {
                locations.add(warehouse.getAddress());
            }
        }
        return locations;
    }
}