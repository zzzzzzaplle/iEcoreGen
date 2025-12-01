import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a product with its properties and supplier information
 */
class Product {
    private String name;
    private boolean isToxic;
    private double volume; // in m^3
    private Supplier supplier;
    
    public Product() {}
    
    /**
     * @return the name of the product
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return true if the product is toxic, false otherwise
     */
    public boolean isToxic() {
        return isToxic;
    }
    
    /**
     * @param isToxic the isToxic to set
     */
    public void setToxic(boolean isToxic) {
        this.isToxic = isToxic;
    }
    
    /**
     * @return the volume of the product in m^3
     */
    public double getVolume() {
        return volume;
    }
    
    /**
     * @param volume the volume to set
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }
    
    /**
     * @return the supplier of the product
     */
    public Supplier getSupplier() {
        return supplier;
    }
    
    /**
     * @param supplier the supplier to set
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents a supplier with name and address
 */
class Supplier {
    private String name;
    private String address;
    
    public Supplier() {}
    
    /**
     * @return the name of the supplier
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the address of the supplier
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}

/**
 * Represents a warehouse with location, capacity, and stored products
 */
class Warehouse {
    private String city;
    private String address;
    private double surface; // in m²
    private List<Product> products;
    
    public Warehouse() {
        this.products = new ArrayList<>();
    }
    
    /**
     * @return the city where the warehouse is located
     */
    public String getCity() {
        return city;
    }
    
    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }
    
    /**
     * @return the address of the warehouse
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * @return the surface area of the warehouse in m²
     */
    public double getSurface() {
        return surface;
    }
    
    /**
     * @param surface the surface to set
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }
    
    /**
     * @return the list of products stored in the warehouse
     */
    public List<Product> getProducts() {
        return products;
    }
    
    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    /**
     * Adds a product to the warehouse
     * @param product the product to add
     */
    public void addProduct(Product product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
    }
}

/**
 * Represents a company that manages multiple warehouses
 */
class Company {
    private String name;
    private List<Warehouse> warehouses;
    
    public Company() {
        this.warehouses = new ArrayList<>();
    }
    
    /**
     * @return the name of the company
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the list of warehouses owned by the company
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }
    
    /**
     * @param warehouses the warehouses to set
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
    
    /**
     * Adds a warehouse to the company
     * @param warehouse the warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        if (warehouses == null) {
            warehouses = new ArrayList<>();
        }
        warehouses.add(warehouse);
    }
    
    /**
     * Retrieves a list of all toxic product names present in a specific warehouse
     * @param warehouseAddress the address of the warehouse to search in
     * @return a list of toxic product names, empty list if no toxic products are found or if the warehouse does not exist
     */
    public List<String> getToxicProductsInWarehouse(String warehouseAddress) {
        List<String> toxicProducts = new ArrayList<>();
        
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                for (Product product : warehouse.getProducts()) {
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
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse
     * @param warehouseAddress the address of the warehouse to search in
     * @return a list of distinct supplier names, empty list if the warehouse contains no products or does not exist
     */
    public List<String> getDistinctSuppliersInWarehouse(String warehouseAddress) {
        Set<String> supplierNames = new HashSet<>();
        
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                for (Product product : warehouse.getProducts()) {
                    if (product.getSupplier() != null) {
                        supplierNames.add(product.getSupplier().getName());
                    }
                }
                break;
            }
        }
        
        return new ArrayList<>(supplierNames);
    }
    
    /**
     * Counts the total volume of products supplied by a specific supplier name within the company
     * @param supplierName the name of the supplier to filter by
     * @return the total volume of products from the supplier, 0 if no products are found
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
     * Verifies that a warehouse contains a specific product by product name
     * @param warehouseAddress the address of the warehouse to search in
     * @param productName the name of the product to search for
     * @return true if the product is found in the warehouse, false if the warehouse or product does not exist
     */
    public boolean warehouseContainsProduct(String warehouseAddress, String productName) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                for (Product product : warehouse.getProducts()) {
                    if (product.getName().equals(productName)) {
                        return true;
                    }
                }
                break;
            }
        }
        
        return false;
    }
    
    /**
     * Verifies that the company contains a specific product by product name and returns its locations
     * @param productName the name of the product to search for
     * @return a list of warehouse cities and addresses where the product is found, empty list if the product is not found
     */
    public List<String> getProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getProducts()) {
                if (product.getName().equals(productName)) {
                    String location = warehouse.getCity() + " - " + warehouse.getAddress();
                    locations.add(location);
                    break; // No need to check other products in this warehouse for the same product
                }
            }
        }
        
        return locations;
    }
}