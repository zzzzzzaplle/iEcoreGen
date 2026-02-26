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
        
        // Create warehouses for test cases
        warehouseW17 = new Warehouse();
        warehouseW17.setCity("CityI");
        warehouseW17.setAddress("W17");
        
        warehouseW18 = new Warehouse();
        warehouseW18.setCity("CityJ");
        warehouseW18.setAddress("W18");
        
        warehouseW19 = new Warehouse();
        warehouseW19.setCity("CityM");
        warehouseW19.setAddress("W19");
        
        warehouseW20 = new Warehouse();
        warehouseW20.setCity("CityK");
        warehouseW20.setAddress("W20");
        
        warehouseW21 = new Warehouse();
        warehouseW21.setCity("CityL");
        warehouseW21.setAddress("W21");
        
        // Add warehouses to company C1
        company.addWarehouses(warehouseW17);
        company.addWarehouses(warehouseW18);
        company.addWarehouses(warehouseW19);
        company.addWarehouses(warehouseW20);
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
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_ProductDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1. Add Product "GadgetB" to W18.
        Product productGadgetB = new Product();
        productGadgetB.setName("GadgetB");
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productGadgetB);
        occupation1.setVolume(15.0);
        
        warehouseW18.addOccupations(occupation1);
        
        // Action: Verify "ToolC" in W18.
        boolean result = warehouseW18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1. Add Product "ItemX" to "W18" in company C2.
        // Note: The test specification mentions company C2, but our test setup only has company C1.
        // We'll interpret this as adding Product "ItemX" to warehouse W18 (which exists in C1)
        // and then checking warehouse W19 (which is empty) in company C1.
        
        Product productItemX = new Product();
        productItemX.setName("ItemX");
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productItemX);
        occupation1.setVolume(20.0);
        
        warehouseW18.addOccupations(occupation1);
        
        // Action: Verify "ItemX" in Warehouse "W19".
        boolean result = warehouseW19.containsProduct("ItemX");
        
        // Expected Output: false (since W19 doesn't contain ItemX, even though W18 does)
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1.
        // Warehouse W20 is already created in setUp() with no products
        
        // Action: Verify "PartY" in W20.
        boolean result = warehouseW20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
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
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productCompA);
        occupation1.setVolume(5.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productCompB);
        occupation2.setVolume(8.0);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(productCompC);
        occupation3.setVolume(12.0);
        
        warehouseW21.addOccupations(occupation1);
        warehouseW21.addOccupations(occupation2);
        warehouseW21.addOccupations(occupation3);
        
        // Action: Verify "CompB" in W21.
        boolean result = warehouseW21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}