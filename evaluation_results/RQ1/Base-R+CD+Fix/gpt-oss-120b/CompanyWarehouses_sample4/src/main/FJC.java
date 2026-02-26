import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

/**
 * Represents a supplier of products.
 */
 class Supplier {

    private String name;
    private String address;

    /** Default constructor */
    public Supplier() {
    }

    /** Getter for name */
    public String getName() {
        return name;
    }

    /** Setter for name */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for address */
    public String getAddress() {
        return address;
    }

    /** Setter for address */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Equality based on name and address (used for distinct supplier collections) */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;
        return Objects.equals(name, supplier.name) &&
               Objects.equals(address, supplier.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}

/**
 * Represents a product.
 */
 class Product {

    private String name;
    private boolean isToxic;
    private Supplier supplier;

    /** Default constructor */
    public Product() {
    }

    /** Getter for name */
    public String getName() {
        return name;
    }

    /** Setter for name */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns true if the product is toxic */
    public boolean isToxic() {
        return isToxic;
    }

    /** Setter for toxic flag */
    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    /** Getter for supplier */
    public Supplier getSupplier() {
        return supplier;
    }

    /** Setter for supplier */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents the occupation of a product inside a warehouse (product + its stored volume).
 */
 class ProductOccupation {

    private Double volume;
    private Product product;

    /** Default constructor */
    public ProductOccupation() {
    }

    /** Getter for volume */
    public Double getVolume() {
        return volume;
    }

    /** Setter for volume */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /** Getter for product */
    public Product getProduct() {
        return product;
    }

    /** Setter for product */
    public void setProduct(Product product) {
        this.product = product;
    }
}

/**
 * Represents a warehouse located in a city.
 */
 class Warehouse {

    private String city;
    private String address;
    private Double surface;
    private List<ProductOccupation> occupations;

    /** Default constructor */
    public Warehouse() {
        this.occupations = new ArrayList<>();
    }

    /** Getter for city */
    public String getCity() {
        return city;
    }

    /** Setter for city */
    public void setCity(String city) {
        this.city = city;
    }

    /** Getter for address */
    public String getAddress() {
        return address;
    }

    /** Setter for address */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Getter for surface */
    public Double getSurface() {
        return surface;
    }

    /** Setter for surface */
    public void setSurface(Double surface) {
        this.surface = surface;
    }

    /** Getter for occupations */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /** Adds a product occupation to the warehouse */
    public void addOccupations(ProductOccupation occupation) {
        if (occupation != null) {
            this.occupations.add(occupation);
        }
    }

    /**
     * Retrieves a list of all toxic product names stored in this warehouse.
     *
     * @return a list containing the names of toxic products; empty list if none are found.
     */
    public List<String> retrieveToxicProductNames() {
        Set<String> toxicNames = new HashSet<>();
        for (ProductOccupation occ : occupations) {
            Product prod = occ.getProduct();
            if (prod != null && prod.isToxic()) {
                toxicNames.add(prod.getName());
            }
        }
        return new ArrayList<>(toxicNames);
    }

    /**
     * Retrieves a list of distinct suppliers whose products are stored in this warehouse.
     *
     * @return a list of unique {@link Supplier} objects; empty list if no products are stored.
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        Set<Supplier> uniqueSuppliers = new HashSet<>();
        for (ProductOccupation occ : occupations) {
            Product prod = occ.getProduct();
            if (prod != null && prod.getSupplier() != null) {
                uniqueSuppliers.add(prod.getSupplier());
            }
        }
        return new ArrayList<>(uniqueSuppliers);
    }

    /**
     * Checks whether a product with the given name is stored in this warehouse.
     *
     * @param productName the name of the product to look for
     * @return {@code true} if the product exists in the warehouse; {@code false} otherwise.
     */
    public boolean containsProduct(String productName) {
        if (productName == null) {
            return false;
        }
        for (ProductOccupation occ : occupations) {
            Product prod = occ.getProduct();
            if (prod != null && productName.equals(prod.getName())) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Represents a company that owns several warehouses.
 */
 class Company {

    private List<Warehouse> warehouses;

    /** Default constructor */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /** Getter for the list of warehouses */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Adds a warehouse to the company.
     *
     * @param warehouse the {@link Warehouse} to add
     */
    public void addWarehouses(Warehouse warehouse) {
        if (warehouse != null) {
            this.warehouses.add(warehouse);
        }
    }

    /**
     * Counts the total volume of products supplied by a specific supplier across all warehouses.
     *
     * @param supplierName the name of the supplier
     * @return total volume (in cubic meters) of all products from the supplier; {@code 0.0} if none are found.
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        if (supplierName == null) {
            return 0.0;
        }
        double total = 0.0;
        for (Warehouse wh : warehouses) {
            for (ProductOccupation occ : wh.getOccupations()) {
                Product prod = occ.getProduct();
                if (prod != null && prod.getSupplier() != null &&
                    supplierName.equals(prod.getSupplier().getName())) {
                    Double vol = occ.getVolume();
                    if (vol != null) {
                        total += vol;
                    }
                }
            }
        }
        return total;
    }

    /**
     * Finds all locations (city and address) where a product with the given name is stored.
     *
     * @param productName the name of the product to search for
     * @return a list of {@link Map.Entry} where key is the city and value is the address; empty list if none found.
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        if (productName == null) {
            return locations;
        }
        Set<String> seen = new HashSet<>(); // to avoid duplicate entries for the same warehouse
        for (Warehouse wh : warehouses) {
            for (ProductOccupation occ : wh.getOccupations()) {
                Product prod = occ.getProduct();
                if (prod != null && productName.equals(prod.getName())) {
                    String key = wh.getCity() + "|" + wh.getAddress();
                    if (!seen.contains(key)) {
                        locations.add(new AbstractMap.SimpleEntry<>(wh.getCity(), wh.getAddress()));
                        seen.add(key);
                    }
                    break; // no need to check other occupations of the same warehouse for the same product
                }
            }
        }
        return locations;
    }
}