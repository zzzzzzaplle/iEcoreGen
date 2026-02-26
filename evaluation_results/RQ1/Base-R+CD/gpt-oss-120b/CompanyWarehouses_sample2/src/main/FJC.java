/* -------------------------------------------------
 * File: Supplier.java
 * ------------------------------------------------- */
package com.example.warehouse;

import java.util.Objects;

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

    /** @return the supplier name */
    public String getName() {
        return name;
    }

    /**
     * Sets the supplier name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the supplier address */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the supplier address.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Equality based on name and address */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;
        return Objects.equals(name, supplier.name) &&
               Objects.equals(address, supplier.address);
    }

    /** Hash code based on name and address */
    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    /** String representation */
    @Override
    public String toString() {
        return "Supplier{name='" + name + '\'' + ", address='" + address + '\'' + '}';
    }
}

/* -------------------------------------------------
 * File: Product.java
 * ------------------------------------------------- */
package com.example.warehouse;

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

    /** @return the product name */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return true if the product is toxic, false otherwise */
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

    /** @return the supplier of the product */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier for the product.
     *
     * @param supplier the supplier to set
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /** String representation */
    @Override
    public String toString() {
        return "Product{name='" + name + '\'' + ", isToxic=" + isToxic + ", supplier=" + supplier + '}';
    }
}

/* -------------------------------------------------
 * File: ProductOccupation.java
 * ------------------------------------------------- */
package com.example.warehouse;

/**
 * Represents the occupation of a product within a warehouse,
 * i.e., the volume it occupies.
 */
 class ProductOccupation {

    /** Volume (in cubic meters) occupied by the product */
    private Double volume;

    /** The product that occupies the volume */
    private Product product;

    /** Unparameterized constructor */
    public ProductOccupation() {
        // default constructor
    }

    /** @return the volume occupied */
    public Double getVolume() {
        return volume;
    }

    /**
     * Sets the volume occupied.
     *
     * @param volume the volume to set (in m³)
     */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /** @return the product associated with this occupation */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product for this occupation.
     *
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /** String representation */
    @Override
    public String toString() {
        return "ProductOccupation{volume=" + volume + ", product=" + product + '}';
    }
}

/* -------------------------------------------------
 * File: Warehouse.java
 * ------------------------------------------------- */
package com.example.warehouse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a warehouse located in a specific city.
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

    /** @return the city of the warehouse */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the warehouse.
     *
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /** @return the address of the warehouse */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the warehouse.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return the surface area of the warehouse */
    public Double getSurface() {
        return surface;
    }

    /**
     * Sets the surface area of the warehouse.
     *
     * @param surface the surface area to set (in m²)
     */
    public void setSurface(Double surface) {
        this.surface = surface;
    }

    /** @return the list of product occupations stored in the warehouse */
    public List<ProductOccupation> getOccupations() {
        return occupations;
    }

    /**
     * Adds a product occupation to the warehouse.
     *
     * @param occupation the product occupation to add
     */
    public void addOccupations(ProductOccupation occupation) {
        if (occupation != null) {
            this.occupations.add(occupation);
        }
    }

    /**
     * Retrieves the names of all toxic products stored in this warehouse.
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
     * @param productName the name of the product to search for
     * @return {@code true} if the product is present; {@code false} otherwise or if the warehouse does not exist
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

    /** String representation */
    @Override
    public String toString() {
        return "Warehouse{city='" + city + '\'' + ", address='" + address + '\'' +
               ", surface=" + surface + ", occupations=" + occupations + '}';
    }
}

/* -------------------------------------------------
 * File: Company.java
 * ------------------------------------------------- */
package com.example.warehouse;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /** @return the list of warehouses owned by the company */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Adds a warehouse to the company's collection.
     *
     * @param warehouse the warehouse to add
     */
    public void addWarehouses(Warehouse warehouse) {
        if (warehouse != null) {
            this.warehouses.add(warehouse);
        }
    }

    /**
     * Counts the total volume of all products supplied by a specific supplier across all warehouses.
     *
     * @param supplierName the name of the supplier whose products' volume should be summed
     * @return the total volume (in cubic meters) of the supplier's products; {@code 0.0} if none are found
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        if (supplierName == null) {
            return 0.0;
        }
        double totalVolume = 0.0;
        for (Warehouse warehouse : warehouses) {
            for (ProductOccupation occupation : warehouse.getOccupations()) {
                Product product = occupation.getProduct();
                if (product != null && product.getSupplier() != null &&
                    supplierName.equals(product.getSupplier().getName())) {
                    Double volume = occupation.getVolume();
                    if (volume != null) {
                        totalVolume += volume;
                    }
                }
            }
        }
        return totalVolume;
    }

    /**
     * Finds all locations (city and address) of warehouses that store a product with the given name.
     *
     * @param productName the name of the product to locate
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

    /** String representation */
    @Override
    public String toString() {
        return "Company{warehouses=" + warehouses + '}';
    }
}