import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a fresh company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1.
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W17");
        warehouse.setCity("CityI");
        
        // SetUp: Add Product "WidgetA" to W17.
        Product product = new Product();
        product.setName("WidgetA");
        warehouse.addProduct(product);
        
        company.addWarehouse(warehouse);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = company.isProductInWarehouse("W17", "WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_ProductDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1.
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W18");
        warehouse.setCity("CityJ");
        
        // SetUp: Add Product "GadgetB" to W18.
        Product product = new Product();
        product.setName("GadgetB");
        warehouse.addProduct(product);
        
        company.addWarehouse(warehouse);
        
        // Action: Verify "ToolC" in W18.
        boolean result = company.isProductInWarehouse("W18", "ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1.
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setAddress("W19");
        company.addWarehouse(warehouse1);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2.
        // Note: Since we're only testing with company C1, W18 doesn't exist in C1
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setAddress("W18");
        Product product = new Product();
        product.setName("ItemX");
        warehouse2.addProduct(product);
        // warehouse2 is not added to company C1
        
        // Action: Verify "ItemX" in Warehouse "W19".
        boolean result = company.isProductInWarehouse("W19", "ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1.
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W20");
        warehouse.setCity("CityK");
        // No products added to warehouse
        company.addWarehouse(warehouse);
        
        // Action: Verify "PartY" in W20.
        boolean result = company.isProductInWarehouse("W20", "PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1.
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W21");
        warehouse.setCity("CityL");
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21.
        Product product1 = new Product();
        product1.setName("CompA");
        warehouse.addProduct(product1);
        
        Product product2 = new Product();
        product2.setName("CompB");
        warehouse.addProduct(product2);
        
        Product product3 = new Product();
        product3.setName("CompC");
        warehouse.addProduct(product3);
        
        company.addWarehouse(warehouse);
        
        // Action: Verify "CompB" in W21.
        boolean result = company.isProductInWarehouse("W21", "CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}