import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;
import java.util.Set;

/**
 * Represents a supplier of products.
 */
 class Supplier {

    private String name;
    private String address;

    /** Default constructor. */
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

    /** Equality based on name and address – useful for distinct‑supplier collections. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier)) return false;
        Supplier other = (Supplier) o;
        return (name == null ? other.name == null : name.equals(other.name))
                && (address == null ? other.address == null : address.equals(other.address));
    }

    @Override
    public int hashCode() {
        int result = (name != null) ? name.hashCode() : 0;
        result = 31 * result + ((address != null) ? address.hashCode() : 0);
        return result;
    }
}

/**
 * Represents a product that can be stored in a warehouse.
 */
 class Product {

    private String name;
    private boolean isToxic;
    private Supplier supplier;

    /** Default constructor. */
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

    /** Sets the toxic flag of the product. */
    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    /** Getter for the product's supplier. */
    public Supplier getSupplier() {
        return supplier;
    }

    /** Setter for the product's supplier. */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Couples a product with the volume it occupies in a particular warehouse.
 */
 class ProductOccupation {

    private Double volume;               // volume in cubic metres (m³)
    private Product product;

    /** Default constructor. */
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

    /** Getter for the product. */
    public Product getProduct() {
        return product;
    }

    /** Setter for the product. */
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
    private Double surface;                         // surface area in square metres (m²)
    private List<ProductOccupation> occupations;   // products stored in this warehouse

    /** Default constructor – creates an empty list of occupations. */
    public Warehouse() {
        this.occupations = new ArrayList<>();
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

    /** Adds a single product occupation to the warehouse. */
    public void addOccupations(ProductOccupation occupation) {
        if (occupation != null) {
            this.occupations.add(occupation);
        }
    }

    /**
     * Retrieves the names of all toxic products stored in this warehouse.
     *
     * @return a list containing the names of toxic products; empty if none are present.
     */
    public List<String> retrieveToxicProductNames() {
        List<String> toxicNames = new ArrayList<>();
        for (ProductOccupation po : occupations) {
            Product p = po.getProduct();
            if (p != null && p.isToxic() && p.getName() != null) {
                toxicNames.add(p.getName());
            }
        }
        return toxicNames;
    }

    /**
     * Retrieves a list of distinct suppliers whose products are stored in this warehouse.
     *
     * @return a list of unique {@link Supplier} objects; empty if the warehouse has no products.
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        Set<Supplier> uniqueSuppliers = new HashSet<>();
        for (ProductOccupation po : occupations) {
            Product p = po.getProduct();
            if (p != null && p.getSupplier() != null) {
                uniqueSuppliers.add(p.getSupplier());
            }
        }
        return new ArrayList<>(uniqueSuppliers);
    }

    /**
     * Checks whether a product with the given name exists in this warehouse.
     *
     * @param productName the name of the product to look for.
     * @return {@code true} if a product with the specified name is stored; {@code false} otherwise.
     */
    public boolean containsProduct(String productName) {
        if (productName == null) {
            return false;
        }
        for (ProductOccupation po : occupations) {
            Product p = po.getProduct();
            if (p != null && productName.equals(p.getName())) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Represents a company that owns a collection of warehouses.
 */
 class Company {

    private List<Warehouse> warehouses;

    /** Default constructor – creates an empty list of warehouses. */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /** Getter for the list of warehouses. */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Adds a warehouse to the company's collection.
     *
     * @param warehouse the {@link Warehouse} to add; ignored if {@code null}.
     */
    public void addWarehouses(Warehouse warehouse) {
        if (warehouse != null) {
            this.warehouses.add(warehouse);
        }
    }

    /**
     * Calculates the total volume of all products supplied by a specific supplier across
     * every warehouse owned by the company.
     *
     * @param supplierName the name of the supplier whose product volumes are to be summed.
     * @return the total volume (in cubic metres) supplied by the given supplier; {@code 0.0}
     *         if the supplier does not exist or supplies no products.
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        if (supplierName == null) {
            return 0.0;
        }
        double total = 0.0;
        for (Warehouse wh : warehouses) {
            for (ProductOccupation po : wh.getOccupations()) {
                Product p = po.getProduct();
                if (p != null && p.getSupplier() != null
                        && supplierName.equals(p.getSupplier().getName())) {
                    Double vol = po.getVolume();
                    if (vol != null) {
                        total += vol;
                    }
                }
            }
        }
        return total;
    }

    /**
     * Finds all locations (city and address) of warehouses that store a product with the
     * specified name.
     *
     * @param productName the name of the product to search for.
     * @return a list of {@link Map.Entry} where the key is the city and the value is the address
     *         of each warehouse containing the product; empty list if the product is not found.
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
}