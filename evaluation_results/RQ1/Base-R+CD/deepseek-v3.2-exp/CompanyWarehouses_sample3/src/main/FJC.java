import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.Set;

/**
 * Represents a company that manages multiple warehouses.
 */
 class Company {
    private List<Warehouse> warehouses;

    /**
     * Default constructor initializes an empty list of warehouses.
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Retrieves the list of warehouses managed by the company.
     * @return List of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Adds a warehouse to the company's list of warehouses.
     * @param warehouse The warehouse to add
     */
    public void addWarehouses(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier across all warehouses.
     * @param supplierName The name of the supplier to search for
     * @return Total volume of products from the specified supplier, or 0 if no products found
     */
    public double countTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        for (Warehouse warehouse : warehouses) {
            List<ProductOccupation> occupations = warehouse.getOccupations();
            for (ProductOccupation occupation : occupations) {
                Product product = occupation.getProduct();
                if (product.getSupplier().getName().equals(supplierName)) {
                    totalVolume += occupation.getVolume();
                }
            }
        }
        return totalVolume;
    }

    /**
     * Finds all locations (city and address) where a specific product is stored.
     * @param productName The name of the product to search for
     * @return List of Map entries containing city and address pairs where the product is found
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.containsProduct(productName)) {
                locations.add(new SimpleEntry<>(warehouse.getCity(), warehouse.getAddress()));
            }
        }
        return locations;
    }
}

/**
 * Represents a warehouse that stores products in a specific location.
 */
class Warehouse {
    private String city;
    private String address;
    private double surface;
    private List<ProductOccupation> occupations;

    /**
     * Default constructor initializes empty lists and default values.
     */
    public Warehouse() {
        this.occupations = new ArrayList<>();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /**
     * Adds a product occupation to the warehouse.
     * @param occupation The product occupation to add
     */
    public void addOccupations(ProductOccupation occupation) {
        this.occupations.add(occupation);
    }

    /**
     * Retrieves a list of all toxic product names present in this warehouse.
     * @return List of toxic product names, or empty list if none found
     */
    public List<String> retrieveToxicProductNames() {
        List<String> toxicProducts = new ArrayList<>();
        for (ProductOccupation occupation : occupations) {
            Product product = occupation.getProduct();
            if (product.isToxic()) {
                toxicProducts.add(product.getName());
            }
        }
        return toxicProducts;
    }

    /**
     * Retrieves a list of all distinct suppliers whose products are stored in this warehouse.
     * @return List of unique suppliers, or empty list if none found
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        Set<Supplier> uniqueSuppliers = new HashSet<>();
        for (ProductOccupation occupation : occupations) {
            uniqueSuppliers.add(occupation.getProduct().getSupplier());
        }
        return new ArrayList<>(uniqueSuppliers);
    }

    /**
     * Verifies if this warehouse contains a specific product.
     * @param productName The name of the product to search for
     * @return true if the product is found, false otherwise
     */
    public boolean containsProduct(String productName) {
        for (ProductOccupation occupation : occupations) {
            if (occupation.getProduct().getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Represents the occupation of a product in a warehouse, including volume information.
 */
class ProductOccupation {
    private double volume;
    private Product product;

    /**
     * Default constructor.
     */
    public ProductOccupation() {
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

/**
 * Represents a product with its properties and supplier information.
 */
class Product {
    private String name;
    private boolean isToxic;
    private Supplier supplier;

    /**
     * Default constructor.
     */
    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isToxic() {
        return isToxic;
    }

    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents a supplier of products.
 */
class Supplier {
    private String name;
    private String address;

    /**
     * Default constructor.
     */
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

    /**
     * Equals method for proper Set operations in retrieveUniqueSuppliers.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Supplier supplier = (Supplier) obj;
        return name.equals(supplier.name) && address.equals(supplier.address);
    }

    /**
     * HashCode method for proper Set operations in retrieveUniqueSuppliers.
     */
    @Override
    public int hashCode() {
        return (name + address).hashCode();
    }
}