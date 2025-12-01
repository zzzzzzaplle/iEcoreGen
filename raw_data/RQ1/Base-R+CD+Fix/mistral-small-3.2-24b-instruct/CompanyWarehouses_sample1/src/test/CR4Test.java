import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Company companyC1;
    private Company companyC2;
    private Warehouse warehouseW17;
    private Warehouse warehouseW18;
    private Warehouse warehouseW19;
    private Warehouse warehouseW20;
    private Warehouse warehouseW21;
    
    @Before
    public void setUp() {
        // Initialize companies
        companyC1 = new Company();
        companyC2 = new Company();
        
        // Set up warehouses
        warehouseW17 = new Warehouse();
        warehouseW17.setCity("CityI");
        
        warehouseW18 = new Warehouse();
        warehouseW18.setCity("CityJ");
        
        warehouseW19 = new Warehouse();
        
        warehouseW20 = new Warehouse();
        warehouseW20.setCity("CityK");
        
        warehouseW21 = new Warehouse();
        warehouseW21.setCity("CityL");
        
        // Add warehouses to companies
        companyC1.addWarehouse(warehouseW17);
        companyC1.addWarehouse(warehouseW18);
        companyC1.addWarehouse(warehouseW19);
        companyC1.addWarehouse(warehouseW20);
        companyC1.addWarehouse(warehouseW21);
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1
        // SetUp: Add Product "WidgetA" to W17
        Product productWidgetA = new Product();
        productWidgetA.setName("WidgetA");
        
        ProductOccupation occupationWidgetA = new ProductOccupation();
        occupationWidgetA.setProduct(productWidgetA);
        
        warehouseW17.addOccupation(occupationWidgetA);
        
        // Action: Verify "WidgetA" in W17
        boolean result = warehouseW17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_ProductDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        // SetUp: Add Product "GadgetB" to W18
        Product productGadgetB = new Product();
        productGadgetB.setName("GadgetB");
        
        ProductOccupation occupationGadgetB = new ProductOccupation();
        occupationGadgetB.setProduct(productGadgetB);
        
        warehouseW18.addOccupation(occupationGadgetB);
        
        // Action: Verify "ToolC" in W18
        boolean result = warehouseW18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        // SetUp: Add Product "ItemX" to "W18" in company C2
        Product productItemX = new Product();
        productItemX.setName("ItemX");
        
        ProductOccupation occupationItemX = new ProductOccupation();
        occupationItemX.setProduct(productItemX);
        
        warehouseW18.addOccupation(occupationItemX);
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = warehouseW19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        // Warehouse W20 is already empty from setup
        
        // Action: Verify "PartY" in W20
        boolean result = warehouseW20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        Product productCompA = new Product();
        productCompA.setName("CompA");
        
        Product productCompB = new Product();
        productCompB.setName("CompB");
        
        Product productCompC = new Product();
        productCompC.setName("CompC");
        
        ProductOccupation occupationCompA = new ProductOccupation();
        occupationCompA.setProduct(productCompA);
        
        ProductOccupation occupationCompB = new ProductOccupation();
        occupationCompB.setProduct(productCompB);
        
        ProductOccupation occupationCompC = new ProductOccupation();
        occupationCompC.setProduct(productCompC);
        
        warehouseW21.addOccupation(occupationCompA);
        warehouseW21.addOccupation(occupationCompB);
        warehouseW21.addOccupation(occupationCompC);
        
        // Action: Verify "CompB" in W21
        boolean result = warehouseW21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}