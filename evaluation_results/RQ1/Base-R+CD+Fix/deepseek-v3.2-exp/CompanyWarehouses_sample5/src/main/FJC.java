import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

 class Company {
    private List<Warehouse> warehouses;

    /**
     * Default constructor initializing an empty list of warehouses.
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Retrieves the list of warehouses in the company.
     * @return List of Warehouse objects
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Sets the list of warehouses for the company.
     * @param warehouses List of Warehouse objects to set
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Adds a warehouse to the company's list of warehouses.
     * @param warehouse Warehouse object to add
     */
    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier across all warehouses in the company.
     * @param supplierName Name of the supplier to filter products by
     * @return Total volume of products from the specified supplier, or 0 if no products found
     */
    public double countTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        for (Warehouse warehouse : warehouses) {
            for (ProductOccupation occupation : warehouse.getOccupations()) {
                Product product = occupation.getProduct();
                if (product != null && product.getSupplier() != null && 
                    product.getSupplier().getName().equals(supplierName)) {
                    totalVolume += occupation.getVolume();
                }
            }
        }
        return totalVolume;
    }

    /**
     * Finds all warehouse locations (city and address) that contain a specific product.
     * @param productName Name of the product to search for
     * @return List of Map entries containing city and address pairs where the product is found
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.containsProduct(productName)) {
                locations.add(Map.entry(warehouse.getCity(), warehouse.getAddress()));
            }
        }
        return locations;
    }
}

 class Warehouse {
    private String city;
    private String address;
    private double surface;
    private List<ProductOccupation> occupations;

    /**
     * Default constructor initializing an empty list of product occupations.
     */
    public Warehouse() {
        this.occupations = new ArrayList<>();
    }

    /**
     * Retrieves the city where the warehouse is located.
     * @return City name as String
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the warehouse is located.
     * @param city City name to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Retrieves the address of the warehouse.
     * @return Warehouse address as String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the warehouse.
     * @param address Warehouse address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the surface area of the warehouse in square meters.
     * @return Surface area as double
     */
    public double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse in square meters.
     * @param surface Surface area to set
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /**
     * Retrieves the list of product occupations in the warehouse.
     * @return List of ProductOccupation objects
     */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /**
     * Sets the list of product occupations for the warehouse.
     * @param occupations List of ProductOccupation objects to set
     */
    public void setOccupations(List<ProductOccupation> occupations) {
        this.occupations = occupations;
    }

    /**
     * Adds a product occupation to the warehouse.
     * @param occupation ProductOccupation object to add
     */
    public void addOccupation(ProductOccupation occupation) {
        this.occupations.add(occupation);
    }

    /**
     * Retrieves a list of all toxic product names present in this warehouse.
     * @return List of toxic product names, empty list if none found
     */
    public List<String> retrieveToxicProductNames() {
        List<String> toxicProducts = new ArrayList<>();
        for (ProductOccupation occupation : occupations) {
            Product product = occupation.getProduct();
            if (product != null && product.isToxic()) {
                toxicProducts.add(product.getName());
            }
        }
        return toxicProducts;
    }

    /**
     * Retrieves a list of all distinct suppliers whose products are stored in this warehouse.
     * @return List of Supplier objects, empty list if none found
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        Set<Supplier> uniqueSuppliers = new HashSet<>();
        for (ProductOccupation occupation : occupations) {
            Product product = occupation.getProduct();
            if (product != null && product.getSupplier() != null) {
                uniqueSuppliers.add(product.getSupplier());
            }
        }
        return new ArrayList<>(uniqueSuppliers);
    }

    /**
     * Verifies whether this warehouse contains a specific product by product name.
     * @param productName Name of the product to search for
     * @return true if the product is found, false otherwise
     */
    public boolean containsProduct(String productName) {
        for (ProductOccupation occupation : occupations) {
            Product product = occupation.getProduct();
            if (product != null && product.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }
}

 class ProductOccupation {
    private double volume;
    private Product product;

    /**
     * Default constructor.
     */
    public ProductOccupation() {
    }

    /**
     * Retrieves the volume occupied by the product in cubic meters.
     * @return Volume as double
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume occupied by the product in cubic meters.
     * @param volume Volume to set
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Retrieves the product associated with this occupation.
     * @return Product object
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product associated with this occupation.
     * @param product Product object to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }
}

 class Product {
    private String name;
    private boolean isToxic;
    private Supplier supplier;

    /**
     * Default constructor.
     */
    public Product() {
    }

    /**
     * Retrieves the name of the product.
     * @return Product name as String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * @param name Product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the product is toxic.
     * @return true if toxic, false otherwise
     */
    public boolean isToxic() {
        return isToxic;
    }

    /**
     * Sets the toxicity status of the product.
     * @param isToxic true if toxic, false otherwise
     */
    public void setToxic(boolean isToxic) {
        this.isToxic = isToxic;
    }

    /**
     * Retrieves the supplier of the product.
     * @return Supplier object
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier of the product.
     * @param supplier Supplier object to set
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

 class Supplier {
    private String name;
    private String address;

    /**
     * Default constructor.
     */
    public Supplier() {
    }

    /**
     * Retrieves the name of the supplier.
     * @return Supplier name as String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the supplier.
     * @param name Supplier name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the address of the supplier.
     * @return Supplier address as String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the supplier.
     * @param address Supplier address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}