import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

 class Company {
    private List<Warehouse> warehouses;

    public Company() {
        this.warehouses = new ArrayList<>();
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier across all warehouses in the company.
     *
     * @param supplierName the name of the supplier to filter products by
     * @return the total volume of products from the specified supplier, returns 0 if no products are found
     */
    public double countTotalVolumeBySupplier(String supplierName) {
        double totalVolume = 0.0;
        for (Warehouse warehouse : warehouses) {
            for (ProductOccupation occupation : warehouse.getOccupations()) {
                Product product = occupation.getProduct();
                if (product.getSupplier().getName().equals(supplierName)) {
                    totalVolume += occupation.getVolume();
                }
            }
        }
        return totalVolume;
    }

    /**
     * Finds all warehouse locations (city and address) that contain a specific product.
     *
     * @param productName the name of the product to search for
     * @return a list of Map.Entry objects containing city and address pairs where the product is found,
     *         returns an empty list if the product is not found in any warehouse
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

 class Warehouse {
    private String city;
    private String address;
    private double surface;
    private List<ProductOccupation> occupations;

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

    public void setOccupations(List<ProductOccupation> occupations) {
        this.occupations = occupations;
    }

    public void addOccupation(ProductOccupation occupation) {
        this.occupations.add(occupation);
    }

    /**
     * Retrieves a list of all toxic product names present in this warehouse.
     *
     * @return a list of toxic product names, returns an empty list if no toxic products are found
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
     *
     * @return a list of unique suppliers, returns an empty list if the warehouse contains no products
     */
    public List<Supplier> retrieveUniqueSuppliers() {
        Set<Supplier> uniqueSuppliers = new HashSet<>();
        for (ProductOccupation occupation : occupations) {
            uniqueSuppliers.add(occupation.getProduct().getSupplier());
        }
        return new ArrayList<>(uniqueSuppliers);
    }

    /**
     * Verifies whether this warehouse contains a specific product by product name.
     *
     * @param productName the name of the product to search for
     * @return true if the product is found in the warehouse, false otherwise
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

 class ProductOccupation {
    private double volume;
    private Product product;

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

 class Product {
    private String name;
    private boolean isToxic;
    private Supplier supplier;

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

 class Supplier {
    private String name;
    private String address;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Supplier supplier = (Supplier) obj;
        return name.equals(supplier.name) && address.equals(supplier.address);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + address.hashCode();
    }
}