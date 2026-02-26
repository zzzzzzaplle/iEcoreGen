import java.util.ArrayList;
import java.util.List;

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
}

/**
 * Represents a product stored in warehouses.
 */
class Product {
    private String name;
    private boolean toxic;
    private double volume;
    private Supplier supplier;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents a warehouse where products are stored.
 */
class Warehouse {
    private String address;
    private double surface;
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
        this.products.add(product);
    }
}

/**
 * Represents a company that owns warehouses and manages products.
 */
class Company {
    private List<Warehouse> warehouses;
    private String name;

    public Company() {
        this.warehouses = new ArrayList<>();
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a warehouse to this company.
     *
     * @param warehouse the warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse.
     *
     * @param warehouseAddress the address of the warehouse to check
     * @return a list of toxic product names, or an empty list if no toxic products are found or if the warehouse does not exist
     */
    public List<String> getToxicProductsInWarehouse(String warehouseAddress) {
        List<String> toxicProducts = new ArrayList<>();
        
        Warehouse targetWarehouse = null;
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                targetWarehouse = warehouse;
                break;
            }
        }
        
        if (targetWarehouse == null) {
            return toxicProducts;
        }
        
        for (Product product : targetWarehouse.getProducts()) {
            if (product.isToxic()) {
                toxicProducts.add(product.getName());
            }
        }
        
        return toxicProducts;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     *
     * @param warehouseAddress the address of the warehouse to check
     * @return a list of distinct suppliers, or an empty list if the warehouse contains no products or does not exist
     */
    public List<Supplier> getSuppliersInWarehouse(String warehouseAddress) {
        List<Supplier> suppliers = new ArrayList<>();
        
        Warehouse targetWarehouse = null;
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                targetWarehouse = warehouse;
                break;
            }
        }
        
        if (targetWarehouse == null) {
            return suppliers;
        }
        
        for (Product product : targetWarehouse.getProducts()) {
            Supplier supplier = product.getSupplier();
            if (supplier != null && !suppliers.contains(supplier)) {
                suppliers.add(supplier);
            }
        }
        
        return suppliers;
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within this company.
     *
     * @param supplierName the name of the supplier to check
     * @return the total volume of products supplied by the specified supplier, or 0 if no products are found
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getProducts()) {
                if (product.getSupplier() != null && 
                    product.getSupplier().getName().equals(supplierName)) {
                    totalVolume += product.getVolume();
                }
            }
        }
        
        return totalVolume;
    }

    /**
     * Verifies that a warehouse contains a specific product by product name.
     *
     * @param warehouseAddress the address of the warehouse to check
     * @param productName the name of the product to look for
     * @return true if the product is in the warehouse, false if the warehouse or product does not exist
     */
    public boolean isProductInWarehouse(String warehouseAddress, String productName) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                for (Product product : warehouse.getProducts()) {
                    if (product.getName().equals(productName)) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Verifies that the company contains a specific product by product name and returns its locations.
     *
     * @param productName the name of the product to look for
     * @return a list of warehouse addresses where the product is found, or an empty list if the product is not found
     */
    public List<String> getProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getProducts()) {
                if (product.getName().equals(productName)) {
                    locations.add(warehouse.getAddress());
                    break; // No need to check other products in the same warehouse
                }
            }
        }
        
        return locations;
    }
}