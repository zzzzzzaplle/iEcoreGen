import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        Warehouse warehouse = new Warehouse("CityE", "W7 Address", 350.0);
        
        // SetUp: Add Product "Item1" from Supplier "S6"
        Supplier supplier1 = new Supplier("S6", "Supplier6 Address");
        Product product1 = new Product("Item1", false, 10.0, supplier1);
        warehouse.addProduct(product1);
        
        // SetUp: Add Product "Item2" from Supplier "S7"
        Supplier supplier2 = new Supplier("S7", "Supplier7 Address");
        Product product2 = new Product("Item2", false, 15.0, supplier2);
        warehouse.addProduct(product2);
        
        // SetUp: Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product("Item3", true, 20.0, supplier1);
        warehouse.addProduct(product3);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityE");
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        
        // Verify supplier names are correct and unique
        boolean foundS6 = false;
        boolean foundS7 = false;
        for (Supplier s : result) {
            if ("S6".equals(s.getName())) {
                foundS6 = true;
            } else if ("S7".equals(s.getName())) {
                foundS7 = true;
            }
        }
        assertTrue("Should contain supplier S6", foundS6);
        assertTrue("Should contain supplier S7", foundS7);
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse("CityF", "W8 Address", 250.0);
        
        // SetUp: Add Product "Item4" from Supplier "S8"
        Supplier supplier = new Supplier("S8", "Supplier8 Address");
        Product product = new Product("Item4", false, 25.0, supplier);
        warehouse.addProduct(product);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityF");
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse("CityG", "W9 Address", 150.0);
        // No products added to warehouse
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityG");
        
        // Expected Output: []
        assertTrue("Result should be empty list", result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // (company is already initialized with no warehouses in setUp method)
        
        // Action: Retrieve unique suppliers for "W10"
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W10");
        
        // Expected Output: []
        assertTrue("Result should be empty list for non-existent warehouse", result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse("CityH", "W11 Address", 300.0);
        
        // SetUp: Add Product "A1" from Supplier "S9"
        Supplier supplier = new Supplier("S9", "Supplier9 Address");
        Product product1 = new Product("A1", false, 30.0, supplier);
        warehouse.addProduct(product1);
        
        // SetUp: Add Product "A2" from Supplier "S9"
        Product product2 = new Product("A2", true, 35.0, supplier);
        warehouse.addProduct(product2);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityH");
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}