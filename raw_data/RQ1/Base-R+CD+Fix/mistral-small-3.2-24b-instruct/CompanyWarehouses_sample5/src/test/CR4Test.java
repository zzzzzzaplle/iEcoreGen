import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Warehouse w17 = new Warehouse();
        w17.setCity("CityI");
        w17.setAddress("W17");
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
    public void testCase2_ProductDoesNotExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        Warehouse w18 = new Warehouse();
        w18.setCity("CityJ");
        w18.setAddress("W18");
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
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        Warehouse w19 = new Warehouse();
        w19.setCity("CityM");
        w19.setAddress("W19");
        company.addWarehouse(w19);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2 (different company)
        Company company2 = new Company();
        Warehouse w18 = new Warehouse();
        w18.setCity("CityJ");
        w18.setAddress("W18");
        company2.addWarehouse(w18);
        
        Product itemX = new Product();
        itemX.setName("ItemX");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(itemX);
        w18.addOccupation(occupation);
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = w19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        Warehouse w20 = new Warehouse();
        w20.setCity("CityK");
        w20.setAddress("W20");
        company.addWarehouse(w20);
        
        // Action: Verify "PartY" in W20
        boolean result = w20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        Warehouse w21 = new Warehouse();
        w21.setCity("CityL");
        w21.setAddress("W21");
        company.addWarehouse(w21);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        Product compA = new Product();
        compA.setName("CompA");
        ProductOccupation occupationA = new ProductOccupation();
        occupationA.setProduct(compA);
        w21.addOccupation(occupationA);
        
        Product compB = new Product();
        compB.setName("CompB");
        ProductOccupation occupationB = new ProductOccupation();
        occupationB.setProduct(compB);
        w21.addOccupation(occupationB);
        
        Product compC = new Product();
        compC.setName("CompC");
        ProductOccupation occupationC = new ProductOccupation();
        occupationC.setProduct(compC);
        w21.addOccupation(occupationC);
        
        // Action: Verify "CompB" in W21
        boolean result = w21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}