import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a company that manages multiple warehouses.
 */
 class Company {
    private List<Warehouse> warehouses;

    /**
     * Constructs a new Company with an empty list of warehouses.
     */
    public Company() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * Gets the list of warehouses.
     *
     * @return the list of warehouses
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Sets the list of warehouses.
     *
     * @param warehouses the list of warehouses to set
     */
    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    /**
     * Adds a warehouse to the company.
     *
     * @param warehouse the warehouse to add
     */
    public void addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
    }

    /**
     * Counts the total volume of products supplied by a specific supplier within the company.
     *
     * @param supplierName the name of the supplier
     * @return the total volume of products supplied by the specified supplier, or 0 if none found
     */
    public Double countTotalVolumeBySupplier(String supplierName) {
        if (supplierName == null || warehouses == null) {
            return 0.0;
        }

        return warehouses.stream()
                .flatMap(warehouse -> warehouse.getOccupations().stream())
                .filter(occupation -> occupation.getProduct() != null &&
                        occupation.getProduct().getSupplier() != null &&
                        supplierName.equals(occupation.getProduct().getSupplier().getName()))
                .mapToDouble(ProductOccupation::getVolume)
                .sum();
    }

    /**
     * Finds all locations (city and address) where a specific product is stored across all warehouses.
     *
     * @param productName the name of the product to search for
     * @return a list of Map.Entry where key is city and value is address of warehouses containing the product,
     *         or an empty list if the product is not found
     */
    public List<Map.Entry<String, String>> findProductLocations(String productName) {
        List<Map.Entry<String, String>> locations = new ArrayList<>();

        if (productName == null || warehouses == null) {
            return locations;
        }

        for (Warehouse warehouse : warehouses) {
            if (warehouse.containsProduct(productName)) {
                locations.add(new AbstractMap.SimpleEntry<>(warehouse.getCity(), warehouse.getAddress()));
            }
        }

        return locations;
    }
}