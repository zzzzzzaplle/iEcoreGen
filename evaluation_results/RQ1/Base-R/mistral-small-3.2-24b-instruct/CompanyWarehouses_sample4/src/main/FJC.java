import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Company {
    private List<Warehouse> warehouses;

    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse.
     * @param warehouseAddress The address of the warehouse to check for toxic products.
     * @return A list of toxic product names. Returns an empty list if no toxic products are found or the warehouse does not exist.
     */
    public List<String> getToxicProductsInWarehouse(String warehouseAddress) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                return warehouse.getToxicProductNames();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     * @param warehouseAddress The address of the warehouse to check for suppliers.
     * @return A list of distinct supplier names. Returns an empty list if the warehouse contains no products or does not exist.
     */
    public List<String> getDistinctSuppliersInWarehouse(String warehouseAddress) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                return warehouse.getDistinctSupplierNames();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     * @param supplierName The name of the supplier to count the volume for.
     * @return The total volume of products supplied by the specified supplier. Returns 0 if no products are found.
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        for (Warehouse warehouse : warehouses) {
            totalVolume += warehouse.getTotalVolumeBySupplier(supplierName);
        }
        return totalVolume;
    }

    /**
     * Verifies if the company contains a specific product by product name and returns its locations.
     * @param productName The name of the product to search for.
     * @return A list of warehouse cities and addresses where the product is found. Returns an empty list if the product is not found.
     */
    public List<String> getProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.containsProduct(productName)) {
                locations.add(warehouse.getCity() + " - " + warehouse.getAddress());
            }
        }
        return locations;
    }

    // Getter and Setter for warehouses
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
}

class Warehouse {
    private String address;
    private String city;
    private double surface;
    private List<Product> products;

    public Warehouse() {
        this.products = new ArrayList<>();
    }

    /**
     * Retrieves a list of all toxic product names present in the warehouse.
     * @return A list of toxic product names. Returns an empty list if no toxic products are found.
     */
    public List<String> getToxicProductNames() {
        List<String> toxicProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.isToxic()) {
                toxicProducts.add(product.getName());
            }
        }
        return toxicProducts;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in the warehouse.
     * @return A list of distinct supplier names. Returns an empty list if the warehouse contains no products.
     */
    public List<String> getDistinctSupplierNames() {
        Map<String, Supplier> supplierMap = new HashMap<>();
        for (Product product : products) {
            supplierMap.put(product.getSupplier().getName(), product.getSupplier());
        }
        List<String> distinctSuppliers = new ArrayList<>(supplierMap.keySet());
        return distinctSuppliers;
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name in the warehouse.
     * @param supplierName The name of the supplier to count the volume for.
     * @return The total volume of products supplied by the specified supplier. Returns 0 if no products are found.
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        for (Product product : products) {
            if (product.getSupplier().getName().equals(supplierName)) {
                totalVolume += product.getVolume();
            }
        }
        return totalVolume;
    }

    /**
     * Verifies if the warehouse contains a specific product by product name.
     * @param productName The name of the product to check for.
     * @return True if the product is found in the warehouse, false otherwise.
     */
    public boolean containsProduct(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    // Getter and Setter for address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter and Setter for city
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // Getter and Setter for surface
    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    // Getter and Setter for products
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

class Product {
    private String name;
    private Supplier supplier;
    private boolean isToxic;
    private double volume;

    public Product() {
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for supplier
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    // Getter and Setter for isToxic
    public boolean isToxic() {
        return isToxic;
    }

    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    // Getter and Setter for volume
    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}

class Supplier {
    private String name;
    private String address;

    public Supplier() {
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}