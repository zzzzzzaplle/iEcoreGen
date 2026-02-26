import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    private Company company;
    private Warehouse warehouse;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1
        warehouse = new Warehouse("CityI", 1000.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "WidgetA" to W17
        Product product = new Product("WidgetA", null, false, 10.0);
        warehouse.addProduct(product);
        
        // Action: Verify "WidgetA" in W17
        boolean result = warehouse.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product 'WidgetA' should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        warehouse = new Warehouse("CityJ", 1000.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "GadgetB" to W18
        Product product = new Product("GadgetB", null, false, 10.0);
        warehouse.addProduct(product);
        
        // Action: Verify "ToolC" in W18
        boolean result = warehouse.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product 'ToolC' should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        warehouse = new Warehouse("W19", 1000.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2
        // Note: Since we're testing W19, we don't add ItemX to W19
        Warehouse warehouse2 = new Warehouse("W18", 1000.0);
        Product product = new Product("ItemX", null, false, 10.0);
        warehouse2.addProduct(product);
        // Note: warehouse2 is not added to company since it's in company C2
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = warehouse.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product 'ItemX' should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        warehouse = new Warehouse("CityK", 1000.0);
        company.addWarehouse(warehouse);
        
        // Action: Verify "PartY" in W20
        boolean result = warehouse.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product 'PartY' should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        warehouse = new Warehouse("CityL", 1000.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        Product product1 = new Product("CompA", null, false, 10.0);
        Product product2 = new Product("CompB", null, false, 15.0);
        Product product3 = new Product("CompC", null, false, 20.0);
        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        
        // Action: Verify "CompB" in W21
        boolean result = warehouse.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product 'CompB' should exist in warehouse W21", result);
    }
}