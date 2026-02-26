import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a supplier of products.
 */
 class Supplier {

    private String name;
    private String address;

    /** Default constructor. */
    public Supplier() {
        // No‑arg constructor
    }

    /** @return the supplier name */
    public String getName() {
        return name;
    }

    /** @param name the supplier name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the supplier address */
    public String getAddress() {
        return address;
    }

    /** @param address the supplier address to set */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Equality based on name and address (used for distinct supplier collections). */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Supplier)) return false;
        Supplier other = (Supplier) obj;
        return (name == null ? other.name == null : name.equals(other.name))
                && (address == null ? other.address == null : address.equals(other.address));
    }

    @Override
    public int hashCode() {
        int result = (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Supplier{name='" + name + '\'' + ", address='" + address + '\'' + '}';
    }
}

/**
 * Represents a product.
 */
 class Product {

    private String name;
    private boolean isToxic;
    private Supplier supplier;

    /** Default constructor. */
    public Product() {
        // No‑arg constructor
    }

    /** @return the product name */
    public String getName() {
        return name;
    }

    /** @param name the product name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return true if the product is toxic */
    public boolean isToxic() {
        return isToxic;
    }

    /** @param toxic true if the product is toxic */
    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    /** @return the supplier of the product */
    public Supplier getSupplier() {
        return supplier;
    }

    /** @param supplier the supplier to set */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Product{name='" + name + '\'' + ", isToxic=" + isToxic + ", supplier=" + supplier + '}';
    }
}

/**
 * Represents the occupation of a product in a warehouse (product + volume).
 */
 class ProductOccupation {

    private Double volume;          // volume in cubic meters (m³)
    private Product product;

    /** Default constructor. */
    public ProductOccupation() {
        // No‑arg constructor
    }

    /** @return the volume occupied by the product */
    public Double getVolume() {
        return volume;
    }

    /** @param volume the volume to set */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /** @return the product stored */
    public Product getProduct() {
        return product;
    }

    /** @param product the product to set */
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductOccupation{volume=" + volume + ", product=" + product + '}';
    }
}

/**
 * Represents a warehouse located in a city.
 */
 class Warehouse {

    private String city;
    private String address;
    private Double surface;                     // surface in square meters (m²)
    private List<ProductOccupation> occupations;

    /** Default constructor. Initializes the occupations list. */
    public Warehouse() {
        this.occupations = new ArrayList<>();
    }

    /** @return the city where the warehouse is located */
    public String getCity() {
        return city;
    }

    /** @param city the city to set */
    public void setCity(String city) {
        this.city = city;
    }

    /** @return the address of the warehouse */
    public String getAddress() {
        return address;
    }

    /** @param address the address to set */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return the surface area of the warehouse */
    public Double getSurface() {
        return surface;
    }

    /** @param surface the surface area to set */
    public void setSurface(Double surface) {
        this.surface = surface;
    }

    /** @return the list of product occupations stored in the warehouse */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /**
     * Adds a product occupation to this warehouse.
     *
     * @param occupation the occupation to add
     */
    public void addOccupations(ProductOccupation occupation) {
        this.occupations.add(occupation);
    }

    /**
     * Retrieves the names of all toxic products stored in this warehouse.
     *
     * @return a list of distinct toxic product names; empty list if none are found
     */
    public List<String> retrieveToxicProductNames() {
        Set<String> toxicNames = new HashSet<>();
        for (ProductOccupation occupation : occupations) {
            Product product = occupation.getProduct();
            if (product != null && product.isToxic()) {
                toxicNames.add(product.getName());
            }
        }
        return new ArrayList<>(toxicNames);
    }

    /**
     * Retrieves a list of distinct suppliers whose products are stored in this warehouse.
     *
     * @return a list of unique {@link Supplier} objects; empty list if no products are stored
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
     * Checks whether a product with the given name is stored in this warehouse.
     *
     * @param productName the name of the product to look for
     * @return {@code true} if the product exists in the warehouse; {@code false} otherwise
     */
    public boolean containsProduct(String productName) {
        if (productName == null) {
            return false;
        }
        for (ProductOccupation occupation : occupations) {
            Product product = occupation.getProduct();
            if (product != null && productName.equals(product.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Warehouse{city='" + city + '\'' + ", address='" + address + '\'' +
                ", surface=" + surface + ", occupations=" + occupations + '}';
    }
}

/**
 * Represents a company that owns several warehouses.
 */
 class Company {

    private List<Warehouse> warehouses;

    /** Default constructor. Initializes the warehouses list. */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /** @return the list of warehouses belonging to the company */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Adds a warehouse to the company.
     *
     * @param warehouse the warehouse to add
     */
    public void addWarehouses(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Calculates the total volume of all products supplied by a specific supplier
     * across all warehouses of the company.
     *
     * @param supplierName the name of the supplier
     * @return the summed volume (in cubic meters); {@code 0.0} if no matching products are found
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        if (supplierName == null) {
            return 0.0;
        }
        double total = 0.0;
        for (Warehouse warehouse : warehouses) {
            for (ProductOccupation occupation : warehouse.getOccupations()) {
                Product product = occupation.getProduct();
                if (product != null && product.getSupplier() != null &&
                        supplierName.equals(product.getSupplier().getName())) {
                    Double vol = occupation.getVolume();
                    if (vol != null) {
                        total += vol;
                    }
                }
            }
        }
        return total;
    }

    /**
     * Finds all locations (city and address) of warehouses that store a product with the given name.
     *
     * @param productName the name of the product to search for
     * @return a list of {@link Map.Entry} where the key is the city and the value is the address;
     *         empty list if the product is not stored in any warehouse
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        if (productName == null) {
            return locations;
        }
        for (Warehouse warehouse : warehouses) {
            if (warehouse.containsProduct(productName)) {
                locations.add(new AbstractMap.SimpleEntry<>(warehouse.getCity(), warehouse.getAddress()));
            }
        }
        return locations;
    }

    @Override
    public String toString() {
        return "Company{warehouses=" + warehouses + '}';
    }
}