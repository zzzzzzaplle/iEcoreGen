import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a supplier of products.
 */
 class Supplier {

    /** Supplier name */
    private String name;

    /** Supplier address */
    private String address;

    /** Unparameterized constructor */
    public Supplier() {
        // default constructor
    }

    /**
     * Returns the supplier's name.
     *
     * @return the name of the supplier
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the supplier's name.
     *
     * @param name the name to set for the supplier
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the supplier's address.
     *
     * @return the address of the supplier
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the supplier's address.
     *
     * @param address the address to set for the supplier
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Equality based on name and address.
     *
     * @param o the other object
     * @return true if both suppliers have the same name and address
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;

        if (name != null ? !name.equals(supplier.name) : supplier.name != null) return false;
        return address != null ? address.equals(supplier.address) : supplier.address == null;
    }

    /**
     * Hash code based on name and address.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}

/**
 * Represents a product stored in a warehouse.
 */
 class Product {

    /** Product name */
    private String name;

    /** Flag indicating whether the product is toxic */
    private boolean isToxic;

    /** Supplier of the product */
    private Supplier supplier;

    /** Unparameterized constructor */
    public Product() {
        // default constructor
    }

    /**
     * Returns the product's name.
     *
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product's name.
     *
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns whether the product is toxic.
     *
     * @return true if toxic, false otherwise
     */
    public boolean isToxic() {
        return isToxic;
    }

    /**
     * Sets the toxic flag for the product.
     *
     * @param toxic true if the product is toxic, false otherwise
     */
    public void setToxic(boolean toxic) {
        isToxic = toxic;
    }

    /**
     * Returns the supplier of the product.
     *
     * @return supplier object
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier for the product.
     *
     * @param supplier supplier to associate with the product
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

/**
 * Represents the occupation of a product within a warehouse, i.e., the amount of volume it occupies.
 */
 class ProductOccupation {

    /** Volume occupied by the product (in cubic meters) */
    private Double volume;

    /** The product itself */
    private Product product;

    /** Unparameterized constructor */
    public ProductOccupation() {
        // default constructor
    }

    /**
     * Returns the volume occupied by the product.
     *
     * @return volume in cubic meters
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * Sets the volume occupied by the product.
     *
     * @param volume volume in cubic meters
     */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /**
     * Returns the product associated with this occupation.
     *
     * @return product instance
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product for this occupation.
     *
     * @param product product to associate
     */
    public void setProduct(Product product) {
        this.product = product;
    }
}

/**
 * Represents a warehouse located in a city, storing multiple product occupations.
 */
 class Warehouse {

    /** City where the warehouse is located */
    private String city;

    /** Physical address of the warehouse */
    private String address;

    /** Surface area of the warehouse (in square meters) */
    private Double surface;

    /** List of product occupations stored in the warehouse */
    private List<ProductOccupation> occupations;

    /** Unparameterized constructor */
    public Warehouse() {
        this.occupations = new ArrayList<>();
    }

    /**
     * Returns the city of the warehouse.
     *
     * @return city name
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the warehouse.
     *
     * @param city city name to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the address of the warehouse.
     *
     * @return address string
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the warehouse.
     *
     * @param address address string to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the surface area of the warehouse.
     *
     * @return surface area in square meters
     */
    public Double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse.
     *
     * @param surface surface area in square meters
     */
    public void setSurface(Double surface) {
        this.surface = surface;
    }

    /**
     * Returns the list of product occupations stored in the warehouse.
     *
     * @return list of ProductOccupation objects
     */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /**
     * Adds a product occupation to the warehouse.
     *
     * @param occupation product occupation to add
     */
    public void addOccupations(ProductOccupation occupation) {
        if (occupation != null) {
            this.occupations.add(occupation);
        }
    }

    /**
     * Retrieves the names of all toxic products present in this warehouse.
     *
     * @return a list of toxic product names; empty list if none are found
     */
    public List<String> retrieveToxicProductNames() {
        List<String> toxicNames = new ArrayList<>();
        for (ProductOccupation occupation : occupations) {
            Product product = occupation.getProduct();
            if (product != null && product.isToxic()) {
                toxicNames.add(product.getName());
            }
        }
        return toxicNames;
    }

    /**
     * Retrieves a list of distinct suppliers whose products are stored in this warehouse.
     *
     * @return a list of unique Supplier objects; empty list if the warehouse has no products
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
     * Checks whether the warehouse contains a product with the given name.
     *
     * @param productName name of the product to search for
     * @return true if a product with the specified name exists in the warehouse; false otherwise
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
}

/**
 * Represents a company that owns multiple warehouses.
 */
 class Company {

    /** List of warehouses owned by the company */
    private List<Warehouse> warehouses;

    /** Unparameterized constructor */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Returns the list of warehouses owned by the company.
     *
     * @return list of Warehouse objects
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Adds a warehouse to the company's collection.
     *
     * @param warehouse warehouse to add
     */
    public void addWarehouses(Warehouse warehouse) {
        if (warehouse != null) {
            this.warehouses.add(warehouse);
        }
    }

    /**
     * Counts the total volume of products supplied by a specific supplier across all warehouses.
     *
     * @param supplierName name of the supplier to aggregate volume for
     * @return total volume (in cubic meters) of products from the given supplier; 0.0 if none are found
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        if (supplierName == null) {
            return 0.0;
        }
        double totalVolume = 0.0;
        for (Warehouse warehouse : warehouses) {
            if (warehouse == null) {
                continue;
            }
            for (ProductOccupation occupation : warehouse.getOccupations()) {
                Product product = occupation.getProduct();
                if (product != null && product.getSupplier() != null
                        && supplierName.equals(product.getSupplier().getName())) {
                    Double vol = occupation.getVolume();
                    if (vol != null) {
                        totalVolume += vol;
                    }
                }
            }
        }
        return totalVolume;
    }

    /**
     * Finds all locations (city and address) of warehouses that store a product with the given name.
     *
     * @param productName name of the product to locate
     * @return a list of Map.Entry where the key is the city and the value is the address;
     *         empty list if the product is not stored in any warehouse
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        if (productName == null) {
            return Collections.emptyList();
        }
        List<Map.Entry<String, String>> locations = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse == null) {
                continue;
            }
            if (warehouse.containsProduct(productName)) {
                locations.add(new AbstractMap.SimpleEntry<>(warehouse.getCity(), warehouse.getAddress()));
            }
        }
        return locations;
    }
}