import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a supplier of products with name and address information.
 */
class Supplier {
    private String name;
    private String address;

    /**
     * Default constructor for Supplier.
     */
    public Supplier() {
    }

    /**
     * Gets the name of the supplier.
     * @return the supplier name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the supplier.
     * @param name the supplier name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the supplier.
     * @return the supplier address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the supplier.
     * @param address the supplier address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}

/**
 * Represents a product with its properties including name, toxicity, volume, and supplier.
 */
class Product {
    private String name;
    private boolean isToxic;
    private double volume;
    private Supplier supplier;

    /**
     * Default constructor for Product.
     */
    public Product() {
    }

    /**
     * Gets the name of the product.
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the product is toxic.
     * @return true if the product is toxic, false otherwise
     */
    public boolean isToxic() {
        return isToxic;
    }

    /**
     * Sets the toxicity of the product.
     * @param isToxic the toxicity status to set
     */
    public void setToxic(boolean isToxic) {
        this.isToxic = isToxic;
    }

    /**
     * Gets the volume of the product in cubic meters.
     * @return the product volume
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume of the product in cubic meters.
     * @param volume the product volume to set
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Gets the supplier of the product.
     * @return the product supplier
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the product.
     * @param supplier the product supplier to set
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents a warehouse with its location, surface area, and stored products.
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
        products = new ArrayList<>();
    }

    /**
     * Gets the city where the warehouse is located.
     * @return the warehouse city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the warehouse is located.
     * @param city the warehouse city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the address of the warehouse.
     * @return the warehouse address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the warehouse.
     * @param address the warehouse address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the surface area of the warehouse in square meters.
     * @return the warehouse surface area
     */
    public double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse in square meters.
     * @param surface the warehouse surface area to set
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /**
     * Gets the list of products stored in the warehouse.
     * @return the list of products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the list of products stored in the warehouse.
     * @param products the list of products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

/**
 * Represents a company that manages multiple warehouses.
 */
class Company {
    private List<Warehouse> warehouses;

    /**
     * Default constructor for Company.
     */
    public Company() {
        warehouses = new ArrayList<>();
    }

    /**
     * Gets the list of warehouses owned by the company.
     * @return the list of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Sets the list of warehouses owned by the company.
     * @param warehouses the list of warehouses to set
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Retrieves a list of all toxic product names present in a specific warehouse.
     * @param warehouseAddress the address of the warehouse to search
     * @return a list of toxic product names, empty list if no toxic products are found or if the warehouse does not exist
     */
    public List<String> getToxicProductNamesInWarehouse(String warehouseAddress) {
        List<String> toxicProductNames = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                for (Product product : warehouse.getProducts()) {
                    if (product.isToxic()) {
                        toxicProductNames.add(product.getName());
                    }
                }
                return toxicProductNames;
            }
        }
        return toxicProductNames;
    }

    /**
     * Retrieves the list of all distinct suppliers whose products are stored in a specific warehouse.
     * @param warehouseAddress the address of the warehouse to search
     * @return a set of distinct suppliers, empty set if the warehouse contains no products or does not exist
     */
    public Set<Supplier> getDistinctSuppliersInWarehouse(String warehouseAddress) {
        Set<Supplier> distinctSuppliers = new HashSet<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getAddress().equals(warehouseAddress)) {
                for (Product product : warehouse.getProducts()) {
                    distinctSuppliers.add(product.getSupplier());
                }
                return distinctSuppliers;
            }
        }
        return distinctSuppliers;
    }

    /**
     * Counts the total volume of products supplied by a specific supplier within the company.
     * @param supplierName the name of the supplier
     * @return the total volume of products from the supplier, 0 if no products are found
     */
    public double getTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
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
     * @param warehouseAddress the address of the warehouse to search
     * @param productName the name of the product to find
     * @return true if the product is in the warehouse, false if the warehouse or product does not exist
     */
    public boolean warehouseContainsProduct(String warehouseAddress, String productName) {
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
     * @param productName the name of the product to find
     * @return a list of warehouse cities and addresses where the product is found, empty list if the product is not found
     */
    public List<String> getProductLocations(String productName) {
        List<String> locations = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getProducts()) {
                if (product.getName().equals(productName)) {
                    locations.add(warehouse.getCity() + " - " + warehouse.getAddress());
                    break;
                }
            }
        }
        return locations;
    }
}