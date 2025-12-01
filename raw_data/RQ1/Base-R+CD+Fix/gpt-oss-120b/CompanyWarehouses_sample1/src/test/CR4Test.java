import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;

public class CR4Test {
    
    private Company company;
    private Warehouse warehouse;
    
    @Before
    public void setUp() {
        // Initialize company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityI");
        warehouse.setAddress("W17");
        company.addWarehouses(warehouse);
        
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
        warehouse = new Warehouse();
        warehouse.setCity("CityJ");
        warehouse.setAddress("W18");
        company.addWarehouses(warehouse);
        
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
        warehouse = new Warehouse();
        warehouse.setCity("CityM");
        warehouse.setAddress("W19");
        company.addWarehouses(warehouse);
        
        // Note: According to specification, product "ItemX" is added to W18 in company C2,
        // but we're checking in W19 which doesn't have any products
        // Since we're testing containsProduct on W19 which has no products, it should return false
        
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
        warehouse.setAddress("W20");
        company.addWarehouses(warehouse);
        
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
        warehouse.setAddress("W21");
        company.addWarehouses(warehouse);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        String[] productNames = {"CompA", "CompB", "CompC"};
        for (String name : productNames) {
            Product product = new Product();
            product.setName(name);
            ProductOccupation occupation = new ProductOccupation();
            occupation.setProduct(product);
            warehouse.addOccupations(occupation);
        }
        
        // Action: Verify "CompB" in W21
        boolean result = warehouse.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}