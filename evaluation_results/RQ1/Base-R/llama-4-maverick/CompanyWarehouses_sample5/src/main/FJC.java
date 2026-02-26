import java.util.*;

/**
 * Represents a supplier with a name and address.
 */
class Supplier {
    private String name;
    private String address;

    /**
     * Default constructor for Supplier.
     */
    public Supplier() {}

    /**
     * Constructs a Supplier with the given name and address.
     * @param name The name of the supplier.
     * @param address The address of the supplier.
     */
    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /**
     * Gets the name of the supplier.
     * @return The name of the supplier.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the supplier.
     * @param name The new name of the supplier.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the supplier.
     * @return The address of the supplier.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the supplier.
     * @param address The new address of the supplier.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}

/**
 * Represents a product with its properties.
 */
class Product {
    private String name;
    private Supplier supplier;
    private boolean isToxic;
    private double volume;

    /**
     * Default constructor for Product.
     */
    public Product() {}

    /**
     * Constructs a Product with the given properties.
     * @param name The name of the product.
     * @param supplier The supplier of the product.
     * @param isToxic Whether the product is toxic.
     * @param volume The volume of the product in m^3.
     */
    public Product(String name, Supplier supplier, boolean isToxic, double volume) {
        this.name = name;
        this.supplier = supplier;
        this.isToxic = isToxic;
        this.volume = volume;
    }

    /**
     * Gets the name of the product.
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * @param name The new name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the supplier of the product.
     * @return The supplier of the product.
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the product.
     * @param supplier The new supplier of the product.
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * Checks if the product is toxic.
     * @return True if the product is toxic, false otherwise.
     */
    public boolean isToxic() {
        return isToxic;
    }

    /**
     * Sets whether the product is toxic.
     * @param toxic True if the product is toxic, false otherwise.
     */
    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    /**
     * Gets the volume of the product.
     * @return The volume of the product in m^3.
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume of the product.
     * @param volume The new volume of the product in m^3.
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }
}

/**
 * Represents a warehouse with its properties and stored products.
 */
class Warehouse {
    private String city;
    private String address;
    private double surface;
    private List<Product> products;

    /**
     * Default constructor for Warehouse.
     */
    public Warehouse() {
        this.products = new ArrayList<>();
    }

    /**
     * Constructs a Warehouse with the given properties.
     * @param city The city where the warehouse is located.
     * @param address The address of the warehouse.
     * @param surface The surface area of the warehouse in m².
     */
    public Warehouse(String city, String address, double surface) {
        this.city = city;
        this.address = address;
        this.surface = surface;
        this.products = new ArrayList<>();
    }

    /**
     * Gets the city where the warehouse is located.
     * @return The city of the warehouse.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the warehouse is located.
     * @param city The new city of the warehouse.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the address of the warehouse.
     * @return The address of the warehouse.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the warehouse.
     * @param address The new address of the warehouse.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the surface area of the warehouse.
     * @return The surface area of the warehouse in m².
     */
    public double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse.
     * @param surface The new surface area of the warehouse in m².
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /**
     * Gets the list of products stored in the warehouse.
     * @return The list of products in the warehouse.
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the list of products stored in the warehouse.
     * @param products The new list of products in the warehouse.
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Adds a product to the warehouse.
     * @param product The product to be added.
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }

    /**
     * Retrieves a list of all toxic product names present in the warehouse.
     * @return A list of toxic product names. Returns an empty list if no toxic products are found.
     */
    public List<String> getToxicProductNames() {
        List<String> toxicProductNames = new ArrayList<>();
        for (Product product : products) {
            if (product.isToxic()) {
                toxicProductNames.add(product.getName());
            }
        }
        return toxicProductNames;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in the warehouse.
     * @return A list of distinct suppliers. Returns an empty list if the warehouse contains no products.
     */
    public List<Supplier> getDistinctSuppliers() {
        Set<Supplier> distinctSuppliers = new HashSet<>();
        for (Product product : products) {
            distinctSuppliers.add(product.getSupplier());
        }
        return new ArrayList<>(distinctSuppliers);
    }

    /**
     * Verifies if the warehouse contains a specific product by product name.
     * @param productName The name of the product to be verified.
     * @return True if the product is in the warehouse, false otherwise or if the product does not exist.
     */
    public boolean containsProduct(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Represents a company with its warehouses.
 */
class Company {
    private List<Warehouse> warehouses;

    /**
     * Default constructor for Company.
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Constructs a Company with the given list of warehouses.
     * @param warehouses The list of warehouses belonging to the company.
     */
    public Company(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Gets the list of warehouses belonging to the company.
     * @return The list of warehouses.
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Sets the list of warehouses belonging to the company.
     * @param warehouses The new list of warehouses.
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Adds a warehouse to the company.
     * @param warehouse The warehouse to be added.
     */
    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse.
     * @param warehouseAddress The address of the warehouse to be searched.
     * @return A list of toxic product names. Returns an empty list if no toxic products are found or if the warehouse does not exist.
     */
    public List<String> getToxicProductNamesInWarehouse(String warehouseAddress) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                return warehouse.getToxicProductNames();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     * @param warehouseAddress The address of the warehouse to be searched.
     * @return A list of distinct suppliers. Returns an empty list if the warehouse contains no products or does not exist.
     */
    public List<Supplier> getDistinctSuppliersInWarehouse(String warehouseAddress) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                return warehouse.getDistinctSuppliers();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     * @param supplierName The name of the supplier.
     * @return The total volume of products supplied by the given supplier. Returns 0 if no products are found.
     */
    public double countTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0;
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getProducts()) {
                if (product.getSupplier().getName().equals(supplierName)) {
                    totalVolume += product.getVolume();
                }
            }
        }
        return totalVolume;
    }

    /**
     * Verifies that a warehouse contains a specific product by product name.
     * @param warehouseAddress The address of the warehouse to be verified.
     * @param productName The name of the product to be verified.
     * @return True if the product is in the warehouse, false otherwise or if the warehouse or product does not exist.
     */
    public boolean verifyProductInWarehouse(String warehouseAddress, String productName) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                return warehouse.containsProduct(productName);
            }
        }
        return false;
    }

    /**
     * Verifies that the company contains a specific product by product name and returns its locations.
     * @param productName The name of the product to be verified.
     * @return A list of warehouse cities and addresses where the product is found. Returns an empty list if the product is not found.
     */
    public List<String> verifyProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.containsProduct(productName)) {
                locations.add(warehouse.getCity() + ", " + warehouse.getAddress());
            }
        }
        return locations;
    }
}