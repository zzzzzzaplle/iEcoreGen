import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1
        Warehouse warehouse = new Warehouse(100.0, "AddressI", "CityI");
        warehouse.setCity("CityI");
        
        // SetUp: Add Product "WidgetA" to W17
        Product product = new Product();
        product.setName("WidgetA");
        product.setToxic(false);
        product.setVolume(10.0);
        warehouse.addProduct(product);
        
        company.addWarehouse(warehouse);
        
        // Action: Verify "WidgetA" in W17
        boolean result = company.warehouseContainsProduct("CityI", "WidgetA");
        
        // Expected Output: true
        assertTrue("Product 'WidgetA' should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_ProductDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        Warehouse warehouse = new Warehouse(150.0, "AddressJ", "CityJ");
        warehouse.setCity("CityJ");
        
        // SetUp: Add Product "GadgetB" to W18
        Product product = new Product();
        product.setName("GadgetB");
        product.setToxic(false);
        product.setVolume(15.0);
        warehouse.addProduct(product);
        
        company.addWarehouse(warehouse);
        
        // Action: Verify "ToolC" in W18
        boolean result = company.warehouseContainsProduct("CityJ", "ToolC");
        
        // Expected Output: false
        assertFalse("Product 'ToolC' should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1 (empty warehouse)
        Warehouse warehouse1 = new Warehouse(200.0, "AddressM", "CityM");
        warehouse1.setCity("CityM");
        company.addWarehouse(warehouse1);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2 (different company)
        Company company2 = new Company();
        Warehouse warehouse2 = new Warehouse(120.0, "AddressN", "CityN");
        warehouse2.setCity("CityN");
        Product product = new Product();
        product.setName("ItemX");
        product.setToxic(false);
        product.setVolume(8.0);
        warehouse2.addProduct(product);
        company2.addWarehouse(warehouse2);
        
        // Action: Verify "ItemX" in Warehouse "W19" (which is actually CityM in our setup)
        boolean result = company.warehouseContainsProduct("CityM", "ItemX");
        
        // Expected Output: false
        assertFalse("Product 'ItemX' should not exist in warehouse W19 (CityM)", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        Warehouse warehouse = new Warehouse(180.0, "AddressK", "CityK");
        warehouse.setCity("CityK");
        company.addWarehouse(warehouse);
        
        // Action: Verify "PartY" in W20
        boolean result = company.warehouseContainsProduct("CityK", "PartY");
        
        // Expected Output: false
        assertFalse("Product 'PartY' should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        Warehouse warehouse = new Warehouse(250.0, "AddressL", "CityL");
        warehouse.setCity("CityL");
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        Product product1 = new Product();
        product1.setName("CompA");
        product1.setToxic(false);
        product1.setVolume(12.0);
        warehouse.addProduct(product1);
        
        Product product2 = new Product();
        product2.setName("CompB");
        product2.setToxic(false);
        product2.setVolume(18.0);
        warehouse.addProduct(product2);
        
        Product product3 = new Product();
        product3.setName("CompC");
        product3.setToxic(true);
        product3.setVolume(25.0);
        warehouse.addProduct(product3);
        
        company.addWarehouse(warehouse);
        
        // Action: Verify "CompB" in W21
        boolean result = company.warehouseContainsProduct("CityL", "CompB");
        
        // Expected Output: true
        assertTrue("Product 'CompB' should exist in warehouse W21", result);
    }
}