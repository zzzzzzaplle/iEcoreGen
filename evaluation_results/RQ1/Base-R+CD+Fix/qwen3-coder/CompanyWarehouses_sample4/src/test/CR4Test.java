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
        Warehouse w17 = new Warehouse();
        w17.setCity("CityI");
        company.addWarehouse(w17);
        
        // SetUp: Add Product "WidgetA" to W17
        Product widgetA = new Product();
        widgetA.setName("WidgetA");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(widgetA);
        w17.addOccupation(occupation);
        
        // Action: Verify "WidgetA" in W17
        boolean result = w17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        Warehouse w18 = new Warehouse();
        w18.setCity("CityJ");
        company.addWarehouse(w18);
        
        // SetUp: Add Product "GadgetB" to W18
        Product gadgetB = new Product();
        gadgetB.setName("GadgetB");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(gadgetB);
        w18.addOccupation(occupation);
        
        // Action: Verify "ToolC" in W18
        boolean result = w18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        Warehouse w19 = new Warehouse();
        company.addWarehouse(w19);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2 (different company)
        Company company2 = new Company();
        Warehouse w18 = new Warehouse();
        Product itemX = new Product();
        itemX.setName("ItemX");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(itemX);
        w18.addOccupation(occupation);
        company2.addWarehouse(w18);
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = w19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        Warehouse w20 = new Warehouse();
        w20.setCity("CityK");
        company.addWarehouse(w20);
        
        // Action: Verify "PartY" in W20
        boolean result = w20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        Warehouse w21 = new Warehouse();
        w21.setCity("CityL");
        company.addWarehouse(w21);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        String[] productNames = {"CompA", "CompB", "CompC"};
        for (String productName : productNames) {
            Product product = new Product();
            product.setName(productName);
            ProductOccupation occupation = new ProductOccupation();
            occupation.setProduct(product);
            w21.addOccupation(occupation);
        }
        
        // Action: Verify "CompB" in W21
        boolean result = w21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}