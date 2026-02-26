import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Supplier {
    private String name;
    private String address;

    public Supplier() {
    }

    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
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

class Product {
    private String name;
    private Supplier supplier;
    private boolean isToxic;
    private double volume;

    public Product() {
    }

    public Product(String name, Supplier supplier, boolean isToxic, double volume) {
        this.name = name;
        this.supplier = supplier;
        this.isToxic = isToxic;
        this.volume = volume;
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

class Warehouse {
    private String address;
    private double surface;
    private Map<String, Product> products;

    public Warehouse() {
        this.products = new HashMap<>();
    }

    public Warehouse(String address, double surface) {
        this.address = address;
        this.surface = surface;
        this.products = new HashMap<>();
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

    public Map<String, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.put(product.getName(), product);
    }

    public void removeProduct(String productName) {
        this.products.remove(productName);
    }
}

class Company {
    private List<Warehouse> warehouses;

    public Company() {
        this.warehouses = new ArrayList<>();
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    public void removeWarehouse(Warehouse warehouse) {
        this.warehouses.remove(warehouse);
    }

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse.
     *
     * @param warehouseAddress The address of the warehouse to search for toxic products.
     * @return A list of toxic product names. Returns an empty list if no toxic products are found or if the warehouse does not exist.
     */
    public List<String> getToxicProducts(String warehouseAddress) {
        List<String> toxicProducts = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                for (Product product : warehouse.getProducts().values()) {
                    if (product.isToxic()) {
                        toxicProducts.add(product.getName());
                    }
                }
                break;
            }
        }
        return toxicProducts;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     *
     * @param warehouseAddress The address of the warehouse to search for suppliers.
     * @return A list of distinct supplier names. Returns an empty list if the warehouse contains no products or does not exist.
     */
    public List<String> getSuppliers(String warehouseAddress) {
        List<String> suppliers = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                for (Product product : warehouse.getProducts().values()) {
                    Supplier supplier = product.getSupplier();
                    if (supplier != null && !suppliers.contains(supplier.getName())) {
                        suppliers.add(supplier.getName());
                    }
                }
                break;
            }
        }
        return suppliers;
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name within the company.
     *
     * @param supplierName The name of the supplier to count the volume for.
     * @return The total volume of products supplied by the specified supplier. Returns 0 if no products are found.
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0;
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
     * Verifies if a warehouse contains a specific product by product name.
     *
     * @param warehouseAddress The address of the warehouse to search for the product.
     * @param productName The name of the product to search for.
     * @return true if the product is found in the warehouse, false otherwise.
     */
    public boolean containsProduct(String warehouseAddress, String productName) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                return warehouse.getProducts().containsKey(productName);
            }
        }
        return false;
    }

    /**
     * Verifies if the company contains a specific product by product name and returns its locations.
     *
     * @param productName The name of the product to search for.
     * @return A list of warehouse addresses where the product is found. Returns an empty list if the product is not found.
     */
    public List<String> getProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getProducts().containsKey(productName)) {
                locations.add(warehouse.getAddress());
            }
        }
        return locations;
    }
}