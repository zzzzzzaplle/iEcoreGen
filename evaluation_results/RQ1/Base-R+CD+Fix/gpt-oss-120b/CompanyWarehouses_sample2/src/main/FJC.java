import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a supplier of products.
 */
 class Supplier {
    private String name;
    private String address;

    /** No‑argument constructor. */
    public Supplier() {
    }

    /** Getter for supplier name. */
    public String getName() {
        return name;
    }

    /** Setter for supplier name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for supplier address. */
    public String getAddress() {
        return address;
    }

    /** Setter for supplier address. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Equality based on name and address – useful for deduplication. */
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
        int result = (name == null) ? 0 : name.hashCode();
        result = 31 * result + ((address == null) ? 0 : address.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Supplier{name='" + name + "', address='" + address + "'}";
    }
}

/**
 * Represents a product.
 */
 class Product {
    private String name;
    private boolean isToxic;
    private Supplier supplier;

    /** No‑argument constructor. */
    public Product() {
    }

    /** Getter for product name. */
    public String getName() {
        return name;
    }

    /** Setter for product name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns true if the product is toxic. */
    public boolean isToxic() {
        return isToxic;
    }

    /** Setter for toxic flag. */
    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    /** Getter for the supplier of this product. */
    public Supplier getSupplier() {
        return supplier;
    }

    /** Setter for the supplier of this product. */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Product{name='" + name + "', isToxic=" + isToxic + ", supplier=" + supplier + "}";
    }
}

/**
 * Represents the occupation of a warehouse by a product, i.e. the amount of
 * volume that a product occupies inside a warehouse.
 */
 class ProductOccupation {
    private Double volume;          // volume in cubic metres (m³)
    private Product product;

    /** No‑argument constructor. */
    public ProductOccupation() {
    }

    /** Getter for the occupied volume. */
    public Double getVolume() {
        return volume;
    }

    /** Setter for the occupied volume. */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /** Getter for the product stored. */
    public Product getProduct() {
        return product;
    }

    /** Setter for the product stored. */
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductOccupation{volume=" + volume + ", product=" + product + "}";
    }
}

/**
 * Represents a warehouse located in a city.
 */
 class Warehouse {
    private String city;
    private String address;
    private Double surface;                     // surface in square metres (m²)
    private List<ProductOccupation> occupations = new ArrayList<>();

    /** No‑argument constructor. */
    public Warehouse() {
    }

    /** Getter for city name. */
    public String getCity() {
        return city;
    }

    /** Setter for city name. */
    public void setCity(String city) {
        this.city = city;
    }

    /** Getter for warehouse address. */
    public String getAddress() {
        return address;
    }

    /** Setter for warehouse address. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Getter for surface area. */
    public Double getSurface() {
        return surface;
    }

    /** Setter for surface area. */
    public void setSurface(Double surface) {
        this.surface = surface;
    }

    /** Getter for the list of product occupations. */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /**
     * Adds a product occupation to this warehouse.
     *
     * @param occupation the occupation to add
     */
    public void addOccupation(ProductOccupation occupation) {
        if (occupation != null) {
            occupations.add(occupation);
        }
    }

    /**
     * Retrieves the names of all toxic products stored in this warehouse.
     *
     * @return a list of toxic product names; empty if none exist
     */
    public List<String> retrieveToxicProductNames() {
        Set<String> toxicNames = new HashSet<>();
        for (ProductOccupation occ : occupations) {
            Product p = occ.getProduct();
            if (p != null && p.isToxic()) {
                toxicNames.add(p.getName());
            }
        }
        return new ArrayList<>(toxicNames);
    }

    /**
     * Retrieves a list of distinct suppliers whose products are stored in this warehouse.
     *
     * @return a list of unique suppliers; empty if the warehouse holds no products
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        Set<Supplier> uniqueSuppliers = new HashSet<>();
        for (ProductOccupation occ : occupations) {
            Product p = occ.getProduct();
            if (p != null && p.getSupplier() != null) {
                uniqueSuppliers.add(p.getSupplier());
            }
        }
        return new ArrayList<>(uniqueSuppliers);
    }

    /**
     * Checks whether a product with the given name is stored in this warehouse.
     *
     * @param productName name of the product to look for
     * @return true if the product exists in the warehouse; false otherwise
     */
    public boolean containsProduct(String productName) {
        if (productName == null) {
            return false;
        }
        for (ProductOccupation occ : occupations) {
            Product p = occ.getProduct();
            if (p != null && productName.equals(p.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Warehouse{city='" + city + "', address='" + address + "', surface=" + surface + "}";
    }
}

/**
 * Represents a company that owns several warehouses.
 */
 class Company {
    private List<Warehouse> warehouses = new ArrayList<>();

    /** No‑argument constructor. */
    public Company() {
    }

    /** Getter for the list of warehouses. */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Adds a warehouse to the company.
     *
     * @param warehouse the warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        if (warehouse != null) {
            warehouses.add(warehouse);
        }
    }

    /**
     * Counts the total volume of products supplied by a specific supplier across
     * all warehouses owned by the company.
     *
     * @param supplierName name of the supplier whose product volumes are summed
     * @return total volume (in cubic metres); 0 if no matching products are found
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        if (supplierName == null) {
            return 0.0;
        }
        double total = 0.0;
        for (Warehouse wh : warehouses) {
            for (ProductOccupation occ : wh.getOccupations()) {
                Product p = occ.getProduct();
                if (p != null && p.getSupplier() != null
                        && supplierName.equals(p.getSupplier().getName())) {
                    total += occ.getVolume() != null ? occ.getVolume() : 0.0;
                }
            }
        }
        return total;
    }

    /**
     * Finds all warehouses that store a product with the given name.
     *
     * @param productName name of the product to locate
     * @return a list of {@link Map.Entry} where the key is the city and the value
     *         is the address of each warehouse containing the product; empty list if none found
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        if (productName == null) {
            return Collections.emptyList();
        }
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        for (Warehouse wh : warehouses) {
            if (wh.containsProduct(productName)) {
                locations.add(new AbstractMap.SimpleEntry<>(wh.getCity(), wh.getAddress()));
            }
        }
        return locations;
    }

    @Override
    public String toString() {
        return "Company{warehouses=" + warehouses + "}";
    }
}