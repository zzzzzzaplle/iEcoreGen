import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
        Warehouse w17 = new Warehouse();
        w17.setCity("CityI");
        w17.setAddress("W17");
        
        // Add Product "WidgetA" to W17
        Product widgetA = new Product();
        widgetA.setName("WidgetA");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(widgetA);
        w17.addOccupation(occupation);
        
        // Action: Verify "WidgetA" in W17
        boolean result = w17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("WidgetA should be found in warehouse W17", result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        Warehouse w18 = new Warehouse();
        w18.setCity("CityJ");
        w18.setAddress("W18");
        
        // Add Product "GadgetB" to W18
        Product gadgetB = new Product();
        gadgetB.setName("GadgetB");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(gadgetB);
        w18.addOccupation(occupation);
        
        // Action: Verify "ToolC" in W18
        boolean result = w18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("ToolC should not be found in warehouse W18", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        Warehouse w19 = new Warehouse();
        w19.setCity("CityM");
        w19.setAddress("W19");
        
        // Note: Product "ItemX" is added to "W18" in company C2 (different warehouse)
        // Since we're testing W19, and ItemX is not in W19, this simulates the non-existent scenario
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = w19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("ItemX should not be found in warehouse W19", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        Warehouse w20 = new Warehouse();
        w20.setCity("CityK");
        w20.setAddress("W20");
        // No products added to simulate empty warehouse
        
        // Action: Verify "PartY" in W20
        boolean result = w20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("PartY should not be found in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        Warehouse w21 = new Warehouse();
        w21.setCity("CityL");
        w21.setAddress("W21");
        
        // Add Products ["CompA", "CompB", "CompC"] to W21
        String[] productNames = {"CompA", "CompB", "CompC"};
        for (String name : productNames) {
            Product product = new Product();
            product.setName(name);
            ProductOccupation occupation = new ProductOccupation();
            occupation.setProduct(product);
            w21.addOccupation(occupation);
        }
        
        // Action: Verify "CompB" in W21
        boolean result = w21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("CompB should be found in warehouse W21", result);
    }
}