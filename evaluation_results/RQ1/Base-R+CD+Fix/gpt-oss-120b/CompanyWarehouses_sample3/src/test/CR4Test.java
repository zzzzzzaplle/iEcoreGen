import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    private Warehouse warehouseW17;
    private Warehouse warehouseW18;
    private Warehouse warehouseW19;
    private Warehouse warehouseW20;
    private Warehouse warehouseW21;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Set up Warehouse W17 for Test Case 1
        warehouseW17 = new Warehouse();
        warehouseW17.setCity("CityI");
        warehouseW17.setAddress("W17");
        company.addWarehouses(warehouseW17);
        
        // Set up Warehouse W18 for Test Case 2
        warehouseW18 = new Warehouse();
        warehouseW18.setCity("CityJ");
        warehouseW18.setAddress("W18");
        company.addWarehouses(warehouseW18);
        
        // Set up Warehouse W19 for Test Case 3
        warehouseW19 = new Warehouse();
        warehouseW19.setCity("CityM");
        warehouseW19.setAddress("W19");
        company.addWarehouses(warehouseW19);
        
        // Set up Warehouse W20 for Test Case 4
        warehouseW20 = new Warehouse();
        warehouseW20.setCity("CityK");
        warehouseW20.setAddress("W20");
        company.addWarehouses(warehouseW20);
        
        // Set up Warehouse W21 for Test Case 5
        warehouseW21 = new Warehouse();
        warehouseW21.setCity("CityL");
        warehouseW21.setAddress("W21");
        company.addWarehouses(warehouseW21);
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1. Add Product "WidgetA" to W17.
        Product productWidgetA = new Product();
        productWidgetA.setName("WidgetA");
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productWidgetA);
        occupation1.setVolume(10.0);
        
        warehouseW17.addOccupations(occupation1);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = warehouseW17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_ProductDoesNotExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1. Add Product "GadgetB" to W18.
        Product productGadgetB = new Product();
        productGadgetB.setName("GadgetB");
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productGadgetB);
        occupation2.setVolume(15.0);
        
        warehouseW18.addOccupations(occupation2);
        
        // Action: Verify "ToolC" in W18.
        boolean result = warehouseW18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1. Add Product "ItemX" to "W18" in company C2.
        // Note: Since we only have one company in our test setup, we'll interpret this as:
        // W19 has no products, while ItemX is in W18 (different warehouse)
        Product productItemX = new Product();
        productItemX.setName("ItemX");
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(productItemX);
        occupation3.setVolume(20.0);
        
        warehouseW18.addOccupations(occupation3);
        
        // Action: Verify "ItemX" in Warehouse "W19".
        boolean result = warehouseW19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1.
        // No products added to W20 (already empty from setup)
        
        // Action: Verify "PartY" in W20.
        boolean result = warehouseW20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1. Add Products ["CompA", "CompB", "CompC"] to W21.
        Product productCompA = new Product();
        productCompA.setName("CompA");
        
        Product productCompB = new Product();
        productCompB.setName("CompB");
        
        Product productCompC = new Product();
        productCompC.setName("CompC");
        
        ProductOccupation occupationA = new ProductOccupation();
        occupationA.setProduct(productCompA);
        occupationA.setVolume(5.0);
        
        ProductOccupation occupationB = new ProductOccupation();
        occupationB.setProduct(productCompB);
        occupationB.setVolume(8.0);
        
        ProductOccupation occupationC = new ProductOccupation();
        occupationC.setProduct(productCompC);
        occupationC.setVolume(12.0);
        
        warehouseW21.addOccupations(occupationA);
        warehouseW21.addOccupations(occupationB);
        warehouseW21.addOccupations(occupationC);
        
        // Action: Verify "CompB" in W21.
        boolean result = warehouseW21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("CompB should exist in warehouse W21", result);
    }
}