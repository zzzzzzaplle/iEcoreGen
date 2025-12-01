import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        warehouse.setAddress("W17");
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
        assertTrue(result);
    }
    
    @Test
    public void testCase2_ProductDoesNotExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityJ");
        warehouse.setAddress("W18");
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
        assertFalse(result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityI");
        warehouse.setAddress("W19");
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2
        // Note: Since we're testing against W19, adding to W18 doesn't affect the test
        Warehouse warehouse18 = new Warehouse();
        warehouse18.setCity("CityJ");
        warehouse18.setAddress("W18");
        
        Product product = new Product();
        product.setName("ItemX");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        warehouse18.addOccupation(occupation);
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = warehouse.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityK");
        warehouse.setAddress("W20");
        company.addWarehouse(warehouse);
        
        // Action: Verify "PartY" in W20
        boolean result = warehouse.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityL");
        warehouse.setAddress("W21");
        company.addWarehouse(warehouse);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        Product product1 = new Product();
        product1.setName("CompA");
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        warehouse.addOccupation(occupation1);
        
        Product product2 = new Product();
        product2.setName("CompB");
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        warehouse.addOccupation(occupation2);
        
        Product product3 = new Product();
        product3.setName("CompC");
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        warehouse.addOccupation(occupation3);
        
        // Action: Verify "CompB" in W21
        boolean result = warehouse.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue(result);
    }
}