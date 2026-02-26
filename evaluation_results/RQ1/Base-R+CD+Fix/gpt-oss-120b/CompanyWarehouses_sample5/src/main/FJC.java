import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap;

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
 * Represents the occupation of a product within a warehouse (product + stored volume).
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

    /** Getter for the list of product occupations */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /** Adds a product occupation to the warehouse */
    public void addOccupations(ProductOccupation occupation) {
        if (occupation != null) {
            occupations.add(occupation);
        }
    }

    /**
     * Retrieves the names of all toxic products stored in this warehouse.
     *
     * @return a list of distinct toxic product names; empty list if none are found
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
     * Retrieves the distinct suppliers whose products are stored in this warehouse.
     *
     * @return a list of unique suppliers; empty list if no products are stored
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        Set<String> seen = new HashSet<>();
        List<Supplier> uniqueSuppliers = new ArrayList<>();
        for (ProductOccupation occ : occupations) {
            Product prod = occ.getProduct();
            if (prod != null) {
                Supplier sup = prod.getSupplier();
                if (sup != null && !seen.contains(sup.getName())) {
                    seen.add(sup.getName());
                    uniqueSuppliers.add(sup);
                }
            }
        }
        return uniqueSuppliers;
    }

    /**
     * Checks whether a product with the given name exists in this warehouse.
     *
     * @param productName the name of the product to look for
     * @return {@code true} if the product is present; {@code false} otherwise
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
 * Represents a company that owns multiple warehouses.
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
     * @param warehouse the warehouse to add
     */
    public void addWarehouses(Warehouse warehouse) {
        if (warehouse != null) {
            warehouses.add(warehouse);
        }
    }

    /**
     * Counts the total stored volume of all products supplied by a given supplier name
     * across all warehouses owned by the company.
     *
     * @param supplierName the name of the supplier to filter by
     * @return the summed volume; {@code 0.0} if no matching products are found
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        if (supplierName == null) {
            return 0.0;
        }
        double total = 0.0;
        for (Warehouse wh : warehouses) {
            for (ProductOccupation occ : wh.getOccupations()) {
                Product prod = occ.getProduct();
                if (prod != null) {
                    Supplier sup = prod.getSupplier();
                    if (sup != null && supplierName.equals(sup.getName())) {
                        Double vol = occ.getVolume();
                        if (vol != null) {
                            total += vol;
                        }
                    }
                }
            }
        }
        return total;
    }

    /**
     * Finds all locations (city and address) of warehouses that store a product with the
     * given name.
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
        for (Warehouse wh : warehouses) {
            if (wh.containsProduct(productName)) {
                locations.add(new AbstractMap.SimpleEntry<>(wh.getCity(), wh.getAddress()));
            }
        }
        return locations;
    }
}