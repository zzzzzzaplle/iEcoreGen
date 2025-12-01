import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company("TestCompany");
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1.
        Warehouse warehouse = new Warehouse("CityI", "AddressI", 100.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "WidgetA" to W17.
        Product product = new Product("WidgetA", new Supplier("SupplierA", "AddrA"), false, 10.0);
        warehouse.addProduct(product);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = company.warehouseContainsProduct("CityI", "WidgetA");
        
        // Expected Output: true
        assertTrue("Product 'WidgetA' should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_productDoesNotExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1.
        Warehouse warehouse = new Warehouse("CityJ", "AddressJ", 100.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "GadgetB" to W18.
        Product product = new Product("GadgetB", new Supplier("SupplierB", "AddrB"), false, 15.0);
        warehouse.addProduct(product);
        
        // Action: Verify "ToolC" in W18.
        boolean result = company.warehouseContainsProduct("CityJ", "ToolC");
        
        // Expected Output: false
        assertFalse("Product 'ToolC' should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1.
        Warehouse warehouse = new Warehouse("CityJ", "AddressJ", 100.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2.
        // Note: This test case appears to have a typo - we're adding to W19 but checking W18
        // Following the specification literally: We add ItemX to a different warehouse (W18 in C2)
        // but our company only has W19, so when checking W19 for ItemX, it should return false
        Product product = new Product("ItemX", new Supplier("SupplierX", "AddrX"), false, 20.0);
        warehouse.addProduct(product);
        
        // Action: Verify "ItemX" in Warehouse "W19" (which doesn't exist in our company)
        boolean result = company.warehouseContainsProduct("CityK", "ItemX");
        
        // Expected Output: false
        assertFalse("Warehouse 'W19' should not exist", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1.
        Warehouse warehouse = new Warehouse("CityK", "AddressK", 100.0);
        company.addWarehouse(warehouse);
        // No products added to warehouse
        
        // Action: Verify "PartY" in W20.
        boolean result = company.warehouseContainsProduct("CityK", "PartY");
        
        // Expected Output: false
        assertFalse("Empty warehouse should not contain any products", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1.
        Warehouse warehouse = new Warehouse("CityL", "AddressL", 100.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21.
        Product product1 = new Product("CompA", new Supplier("Supplier1", "Addr1"), false, 5.0);
        Product product2 = new Product("CompB", new Supplier("Supplier2", "Addr2"), false, 8.0);
        Product product3 = new Product("CompC", new Supplier("Supplier3", "Addr3"), false, 12.0);
        
        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        
        // Action: Verify "CompB" in W21.
        boolean result = company.warehouseContainsProduct("CityL", "CompB");
        
        // Expected Output: true
        assertTrue("Product 'CompB' should exist in warehouse with multiple products", result);
    }
}