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
        warehouse = new Warehouse();
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1
        warehouse.setCity("CityI");
        
        // SetUp: Add Product "WidgetA" to W17
        Product product = new Product();
        product.setName("WidgetA");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        warehouse.addOccupations(occupation);
        
        // Action: Verify "WidgetA" in W17
        boolean result = warehouse.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_ProductDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        warehouse.setCity("CityJ");
        
        // SetUp: Add Product "GadgetB" to W18
        Product product = new Product();
        product.setName("GadgetB");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        warehouse.addOccupations(occupation);
        
        // Action: Verify "ToolC" in W18
        boolean result = warehouse.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        warehouse.setCity("W19");
        
        // SetUp: Add Product "ItemX" to "W18" in company C2
        // Note: W18 is different from W19, so W19 remains empty
        Product product = new Product();
        product.setName("ItemX");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        // This product is added to a different warehouse (not W19)
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = warehouse.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        warehouse.setCity("CityK");
        // No products added to warehouse - it remains empty
        
        // Action: Verify "PartY" in W20
        boolean result = warehouse.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        warehouse.setCity("CityL");
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        Product product1 = new Product();
        product1.setName("CompA");
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        warehouse.addOccupations(occupation1);
        
        Product product2 = new Product();
        product2.setName("CompB");
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        warehouse.addOccupations(occupation2);
        
        Product product3 = new Product();
        product3.setName("CompC");
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        warehouse.addOccupations(occupation3);
        
        // Action: Verify "CompB" in W21
        boolean result = warehouse.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}