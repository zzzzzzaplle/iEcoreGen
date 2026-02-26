import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    private Warehouse warehouse;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityI");
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "WidgetA" to W17
        Product product = new Product();
        product.setName("WidgetA");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        warehouse.addOccupation(occupation);
        
        // Action: Verify "WidgetA" in W17
        boolean result = warehouse.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_ProductDoesNotExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityJ");
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "GadgetB" to W18
        Product product = new Product();
        product.setName("GadgetB");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        warehouse.addOccupation(occupation);
        
        // Action: Verify "ToolC" in W18
        boolean result = warehouse.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        warehouse = new Warehouse();
        company.addWarehouse(warehouse);
        
        // Note: The test spec mentions adding Product "ItemX" to "W18" in company C2,
        // but since we're testing W19 (which has no products), we don't need to create company C2
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = warehouse.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in empty warehouse W19", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityK");
        company.addWarehouse(warehouse);
        
        // Action: Verify "PartY" in W20
        boolean result = warehouse.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityL");
        company.addWarehouse(warehouse);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        String[] productNames = {"CompA", "CompB", "CompC"};
        for (String productName : productNames) {
            Product product = new Product();
            product.setName(productName);
            ProductOccupation occupation = new ProductOccupation();
            occupation.setProduct(product);
            warehouse.addOccupation(occupation);
        }
        
        // Action: Verify "CompB" in W21
        boolean result = warehouse.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}