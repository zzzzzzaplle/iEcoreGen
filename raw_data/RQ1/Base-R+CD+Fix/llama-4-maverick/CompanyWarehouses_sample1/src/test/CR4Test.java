import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    private Company company2;
    
    @Before
    public void setUp() {
        // Initialize companies for tests
        company = new Company();
        company2 = new Company();
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1. Add Product "WidgetA" to W17.
        Warehouse warehouseW17 = new Warehouse();
        warehouseW17.setCity("CityI");
        warehouseW17.setAddress("W17");
        
        Product productWidgetA = new Product();
        productWidgetA.setName("WidgetA");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(productWidgetA);
        occupation.setVolume(100.0);
        
        warehouseW17.addOccupations(occupation);
        company.addWarehouses(warehouseW17);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = warehouseW17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_ProductDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1. Add Product "GadgetB" to W18.
        Warehouse warehouseW18 = new Warehouse();
        warehouseW18.setCity("CityJ");
        warehouseW18.setAddress("W18");
        
        Product productGadgetB = new Product();
        productGadgetB.setName("GadgetB");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(productGadgetB);
        occupation.setVolume(200.0);
        
        warehouseW18.addOccupations(occupation);
        company.addWarehouses(warehouseW18);
        
        // Action: Verify "ToolC" in W18.
        boolean result = warehouseW18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1. Add Product "ItemX" to "W18" in company C2.
        Warehouse warehouseW19 = new Warehouse();
        warehouseW19.setCity("CityM");
        warehouseW19.setAddress("W19");
        company.addWarehouses(warehouseW19);
        
        Warehouse warehouseW18 = new Warehouse();
        warehouseW18.setCity("CityJ");
        warehouseW18.setAddress("W18");
        
        Product productItemX = new Product();
        productItemX.setName("ItemX");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(productItemX);
        occupation.setVolume(150.0);
        
        warehouseW18.addOccupations(occupation);
        company2.addWarehouses(warehouseW18);
        
        // Action: Verify "ItemX" in Warehouse "W19".
        boolean result = warehouseW19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1.
        Warehouse warehouseW20 = new Warehouse();
        warehouseW20.setCity("CityK");
        warehouseW20.setAddress("W20");
        company.addWarehouses(warehouseW20);
        
        // Action: Verify "PartY" in W20.
        boolean result = warehouseW20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1. Add Products ["CompA", "CompB", "CompC"] to W21.
        Warehouse warehouseW21 = new Warehouse();
        warehouseW21.setCity("CityL");
        warehouseW21.setAddress("W21");
        
        // Create and add CompA
        Product productCompA = new Product();
        productCompA.setName("CompA");
        ProductOccupation occupationA = new ProductOccupation();
        occupationA.setProduct(productCompA);
        occupationA.setVolume(50.0);
        warehouseW21.addOccupations(occupationA);
        
        // Create and add CompB
        Product productCompB = new Product();
        productCompB.setName("CompB");
        ProductOccupation occupationB = new ProductOccupation();
        occupationB.setProduct(productCompB);
        occupationB.setVolume(75.0);
        warehouseW21.addOccupations(occupationB);
        
        // Create and add CompC
        Product productCompC = new Product();
        productCompC.setName("CompC");
        ProductOccupation occupationC = new ProductOccupation();
        occupationC.setProduct(productCompC);
        occupationC.setVolume(100.0);
        warehouseW21.addOccupations(occupationC);
        
        company.addWarehouses(warehouseW21);
        
        // Action: Verify "CompB" in W21.
        boolean result = warehouseW21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}