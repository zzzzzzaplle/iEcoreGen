import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new company before each test
        company = new Company("C1");
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouse = new Warehouse("CityE", "W7", 350.0);
        
        // Add products with different suppliers
        Supplier supplierS6 = new Supplier("S6", "AddressS6");
        Supplier supplierS7 = new Supplier("S7", "AddressS7");
        
        warehouse.addProduct(new Product("Item1", supplierS6, false, 10.0));
        warehouse.addProduct(new Product("Item2", supplierS7, false, 15.0));
        warehouse.addProduct(new Product("Item3", supplierS6, false, 20.0)); // duplicate supplier
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityE");
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        assertTrue(containsSupplierWithName(result, "S6"));
        assertTrue(containsSupplierWithName(result, "S7"));
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse("CityF", "W8", 250.0);
        
        // Add single product
        Supplier supplierS8 = new Supplier("S8", "AddressS8");
        warehouse.addProduct(new Product("Item4", supplierS8, false, 12.0));
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityF");
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertTrue(containsSupplierWithName(result, "S8"));
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse("CityG", "W9", 150.0);
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityG");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // (company is already initialized with no warehouses in setUp method)
        
        // Action: Retrieve unique suppliers for "W10"
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W10");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse("CityH", "W11", 300.0);
        
        // Add products with same supplier
        Supplier supplierS9 = new Supplier("S9", "AddressS9");
        
        warehouse.addProduct(new Product("A1", supplierS9, false, 5.0));
        warehouse.addProduct(new Product("A2", supplierS9, false, 8.0));
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityH");
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertTrue(containsSupplierWithName(result, "S9"));
    }
    
    // Helper method to check if a list contains a supplier with the given name
    private boolean containsSupplierWithName(List<Supplier> suppliers, String name) {
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}