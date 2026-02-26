import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.AbstractMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Represents a supplier of products.
 */
 class Supplier {

    private String name;
    private String address;

    /** Unparameterized constructor. */
    public Supplier() {
        // default constructor
    }

    /** @return the supplier's name */
    public String getName() {
        return name;
    }

    /** @param name the supplier's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the supplier's address */
    public String getAddress() {
        return address;
    }

    /** @param address the supplier's address to set */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Equality based on name and address – useful for distinct collections. */
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
}

/**
 * Represents a product with a supplier.
 */
 class Product {

    private String name;
    private boolean isToxic;
    private Supplier supplier;

    /** Unparameterized constructor. */
    public Product() {
        // default constructor
    }

    /** @return the product's name */
    public String getName() {
        return name;
    }

    /** @param name the product's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return true if the product is toxic */
    public boolean isToxic() {
        return isToxic;
    }

    /** @param isToxic true if the product is toxic */
    public void setToxic(boolean isToxic) {
        this.isToxic = isToxic;
    }

    /** @return the supplier of the product */
    public Supplier getSupplier() {
        return supplier;
    }

    /** @param supplier the supplier to set */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents the occupation of a product inside a warehouse, i.e. how much volume it occupies.
 */
 class ProductOccupation {

    private Double volume;
    private Product product;

    /** Unparameterized constructor. */
    public ProductOccupation() {
        // default constructor
    }

    /** @return the volume occupied by the product (in m³) */
    public Double getVolume() {
        return volume;
    }

    /** @param volume the volume to set (in m³) */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /** @return the product stored in this occupation */
    public Product getProduct() {
        return product;
    }

    /** @param product the product to set */
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
    private Double surface; // in m²
    private List<ProductOccupation> occupations;

    /** Unparameterized constructor. */
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

    /** @return the surface area of the warehouse (in m²) */
    public Double getSurface() {
        return surface;
    }

    /** @param surface the surface area to set (in m²) */
    public void setSurface(Double surface) {
        this.surface = surface;
    }

    /** @return list of product occupations stored in this warehouse */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /**
     * Adds a product occupation to this warehouse.
     *
     * @param occupation the occupation to add
     */
    public void addOccupations(ProductOccupation occupation) {
        if (occupation != null) {
            this.occupations.add(occupation);
        }
    }

    /**
     * Retrieves a list of all toxic product names present in this warehouse.
     *
     * @return a list of toxic product names; empty list if none are found
     */
    public List<String> retrieveToxicProductNames() {
        List<String> toxicNames = new ArrayList<>();
        for (ProductOccupation occ : occupations) {
            Product p = occ.getProduct();
            if (p != null && p.isToxic()) {
                toxicNames.add(p.getName());
            }
        }
        return toxicNames;
    }

    /**
     * Retrieves a list of distinct suppliers whose products are stored in this warehouse.
     *
     * @return a list of unique suppliers; empty list if no products are stored
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
     * Checks whether a product with the given name exists in this warehouse.
     *
     * @param productName the name of the product to look for
     * @return true if the product is present; false otherwise or if the warehouse does not exist
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
}

/**
 * Represents a company that owns several warehouses.
 */
 class Company {

    private List<Warehouse> warehouses;

    /** Unparameterized constructor. */
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
        if (warehouse != null) {
            this.warehouses.add(warehouse);
        }
    }

    /**
     * Counts the total volume of products supplied by a specific supplier name across all warehouses.
     *
     * @param supplierName the name of the supplier whose products' volume should be summed
     * @return the total volume (in m³); 0 if no products from that supplier are found
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        if (supplierName == null) {
            return 0.0;
        }
        double total = 0.0;
        for (Warehouse wh : warehouses) {
            if (wh == null) continue;
            for (ProductOccupation occ : wh.getOccupations()) {
                Product p = occ.getProduct();
                if (p != null && p.getSupplier() != null
                        && supplierName.equals(p.getSupplier().getName())) {
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
     * Finds all locations (city and address) of warehouses that store a product with the given name.
     *
     * @param productName the name of the product to search for
     * @return a list of Map.Entry where the key is the city and the value is the address;
     *         empty list if the product is not found in any warehouse
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        if (productName == null) {
            return locations;
        }
        for (Warehouse wh : warehouses) {
            if (wh != null && wh.containsProduct(productName)) {
                locations.add(new AbstractMap.SimpleEntry<>(wh.getCity(), wh.getAddress()));
            }
        }
        return locations;
    }
}