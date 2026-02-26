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
     *
     * @param warehouseAddress the address of the warehouse to check
     * @return a list of toxic product names, or an empty list if none are found
     */
    public List<String> getToxicProductsInWarehouse(String warehouseAddress) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                return warehouse.getToxicProducts();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     *
     * @param warehouseAddress the address of the warehouse to check
     * @return a list of distinct supplier names, or an empty list if none are found
     */
    public List<String> getDistinctSuppliersInWarehouse(String warehouseAddress) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                return warehouse.getDistinctSuppliers();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     *
     * @param supplierName the name of the supplier to check
     * @return the total volume of products supplied by the supplier, or 0 if none are found
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        for (Warehouse warehouse : warehouses) {
            totalVolume += warehouse.getTotalVolumeBySupplier(supplierName);
        }
        return totalVolume;
    }

    /**
     * Verifies that the company contains a specific product by product name and returns its locations.
     *
     * @param productName the name of the product to check
     * @return a list of warehouse cities and addresses where the product is found, or an empty list if not found
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

    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
}

class Warehouse {
    private String address;
    private double surface;
    private List<Product> products;

    public Warehouse() {
        this.products = new ArrayList<>();
    }

    /**
     * Retrieves a list of all toxic product names present in the warehouse.
     *
     * @return a list of toxic product names, or an empty list if none are found
     */
    public List<String> getToxicProducts() {
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
     *
     * @return a list of distinct supplier names, or an empty list if none are found
     */
    public List<String> getDistinctSuppliers() {
        List<String> distinctSuppliers = new ArrayList<>();
        Map<String, Boolean> supplierMap = new HashMap<>();
        for (Product product : products) {
            String supplierName = product.getSupplier().getName();
            if (!supplierMap.containsKey(supplierName)) {
                distinctSuppliers.add(supplierName);
                supplierMap.put(supplierName, true);
            }
        }
        return distinctSuppliers;
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the warehouse.
     *
     * @param supplierName the name of the supplier to check
     * @return the total volume of products supplied by the supplier, or 0 if none are found
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
     * Verifies that the warehouse contains a specific product by product name.
     *
     * @param productName the name of the product to check
     * @return true if the product is found, false otherwise
     */
    public boolean containsProduct(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public void addProduct(Product product) {
        this.products.add(product);
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
}

class Product {
    private String name;
    private Supplier supplier;
    private boolean isToxic;
    private double volume;

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
        return isToxic;
    }

    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

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