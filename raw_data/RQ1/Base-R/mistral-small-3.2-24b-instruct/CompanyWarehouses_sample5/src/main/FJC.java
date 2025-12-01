import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Supplier {
    private String name;
    private String address;

    /**
     * Default constructor for Supplier.
     */
    public Supplier() {
    }

    /**
     * Constructor for Supplier with name and address.
     * @param name The name of the supplier.
     * @param address The address of the supplier.
     */
    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /**
     * @return The name of the supplier.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name of the supplier.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The address of the supplier.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address of the supplier.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}

class Product {
    private String name;
    private Supplier supplier;
    private boolean isToxic;
    private double volume;

    /**
     * Default constructor for Product.
     */
    public Product() {
    }

    /**
     * Constructor for Product with all fields.
     * @param name The name of the product.
     * @param supplier The supplier of the product.
     * @param isToxic Whether the product is toxic.
     * @param volume The volume of the product in cubic meters.
     */
    public Product(String name, Supplier supplier, boolean isToxic, double volume) {
        this.name = name;
        this.supplier = supplier;
        this.isToxic = isToxic;
        this.volume = volume;
    }

    /**
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The supplier of the product.
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * @param supplier The supplier of the product.
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * @return Whether the product is toxic.
     */
    public boolean isToxic() {
        return isToxic;
    }

    /**
     * @param isToxic Whether the product is toxic.
     */
    public void setToxic(boolean isToxic) {
        this.isToxic = isToxic;
    }

    /**
     * @return The volume of the product in cubic meters.
     */
    public double getVolume() {
        return volume;
    }

    /**
     * @param volume The volume of the product in cubic meters.
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }
}

class Warehouse {
    private String address;
    private double surface;
    private Map<String, Product> products;

    /**
     * Default constructor for Warehouse.
     */
    public Warehouse() {
        this.products = new HashMap<>();
    }

    /**
     * Constructor for Warehouse with address, surface, and products.
     * @param address The address of the warehouse.
     * @param surface The surface area of the warehouse in square meters.
     */
    public Warehouse(String address, double surface) {
        this.address = address;
        this.surface = surface;
        this.products = new HashMap<>();
    }

    /**
     * @return The address of the warehouse.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address of the warehouse.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return The surface area of the warehouse in square meters.
     */
    public double getSurface() {
        return surface;
    }

    /**
     * @param surface The surface area of the warehouse in square meters.
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /**
     * @return The map of products in the warehouse.
     */
    public Map<String, Product> getProducts() {
        return products;
    }

    /**
     * @param products The map of products in the warehouse.
     */
    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    /**
     * Adds a product to the warehouse.
     * @param product The product to add.
     */
    public void addProduct(Product product) {
        if (product != null) {
            products.put(product.getName(), product);
        }
    }

    /**
     * Retrieves a list of all toxic product names present in the warehouse.
     * @return A list of toxic product names.
     */
    public List<String> getToxicProductNames() {
        List<String> toxicProducts = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.isToxic()) {
                toxicProducts.add(product.getName());
            }
        }
        return toxicProducts;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in the warehouse.
     * @return A set of distinct supplier names.
     */
    public Set<String> getDistinctSuppliers() {
        Set<String> suppliers = new HashSet<>();
        for (Product product : products.values()) {
            if (product.getSupplier() != null) {
                suppliers.add(product.getSupplier().getName());
            }
        }
        return suppliers;
    }

    /**
     * Verifies if the warehouse contains a specific product by product name.
     * @param productName The name of the product to check.
     * @return True if the product is found, false otherwise.
     */
    public boolean containsProduct(String productName) {
        return products.containsKey(productName);
    }
}

class Company {
    private List<Warehouse> warehouses;

    /**
     * Default constructor for Company.
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * @return The list of warehouses in the company.
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * @param warehouses The list of warehouses in the company.
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Adds a warehouse to the company.
     * @param warehouse The warehouse to add.
     */
    public void addWarehouse(Warehouse warehouse) {
        if (warehouse != null) {
            warehouses.add(warehouse);
        }
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     * @param warehouseAddress The address of the warehouse.
     * @return A set of distinct supplier names.
     */
    public Set<String> getDistinctSuppliersInWarehouse(String warehouseAddress) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                return warehouse.getDistinctSuppliers();
            }
        }
        return new HashSet<>();
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     * @param supplierName The name of the supplier.
     * @return The total volume of products supplied by the supplier.
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getProducts().values()) {
                if (product.getSupplier() != null && product.getSupplier().getName().equals(supplierName)) {
                    totalVolume += product.getVolume();
                }
            }
        }
        return totalVolume;
    }

    /**
     * Verifies if the company contains a specific product by product name and returns its locations.
     * @param productName The name of the product to check.
     * @return A list of warehouse cities and addresses if found, an empty list otherwise.
     */
    public List<String> getProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.containsProduct(productName)) {
                locations.add(warehouse.getAddress());
            }
        }
        return locations;
    }
}