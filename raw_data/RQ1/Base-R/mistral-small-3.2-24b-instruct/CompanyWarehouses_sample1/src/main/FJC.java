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
    private List<Product> products;

    public Warehouse() {
        this.products = new ArrayList<>();
    }

    public Warehouse(String address, double surface) {
        this.address = address;
        this.surface = surface;
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

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public boolean containsProduct(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getToxicProductNames() {
        List<String> toxicProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.isToxic()) {
                toxicProducts.add(product.getName());
            }
        }
        return toxicProducts;
    }

    public List<Supplier> getDistinctSuppliers() {
        List<Supplier> distinctSuppliers = new ArrayList<>();
        for (Product product : products) {
            Supplier supplier = product.getSupplier();
            if (!distinctSuppliers.contains(supplier)) {
                distinctSuppliers.add(supplier);
            }
        }
        return distinctSuppliers;
    }

    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0;
        for (Product product : products) {
            if (product.getSupplier().getName().equals(supplierName)) {
                totalVolume += product.getVolume();
            }
        }
        return totalVolume;
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

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse.
     *
     * @param warehouseAddress The address of the warehouse to search for toxic products.
     * @return A list of toxic product names found in the specified warehouse. Returns an empty list if no toxic products are found or if the warehouse does not exist.
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
     *
     * @param warehouseAddress The address of the warehouse to search for distinct suppliers.
     * @return A list of distinct suppliers found in the specified warehouse. Returns an empty list if the warehouse contains no products or does not exist.
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
     *
     * @param supplierName The name of the supplier to count the total volume for.
     * @return The total volume of products supplied by the specified supplier. Returns 0 if no products are found.
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0;
        for (Warehouse warehouse : warehouses) {
            totalVolume += warehouse.getTotalVolumeBySupplier(supplierName);
        }
        return totalVolume;
    }

    /**
     * Verifies if a specific warehouse contains a specific product by product name.
     *
     * @param warehouseAddress The address of the warehouse to search for the product.
     * @param productName The name of the product to search for.
     * @return true if the product is found in the specified warehouse, false otherwise.
     */
    public boolean containsProductInWarehouse(String warehouseAddress, String productName) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                return warehouse.containsProduct(productName);
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
            if (warehouse.containsProduct(productName)) {
                locations.add(warehouse.getAddress());
            }
        }
        return locations;
    }
}