import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        warehouse = new Warehouse();
        warehouse.setCity("CityI");
        warehouse.setAddress("AddressW17");
        company.addWarehouses(warehouse);
        
        // SetUp: Add Product "WidgetA" to W17
        Product product = new Product();
        product.setName("WidgetA");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(100.0);
        warehouse.addOccupations(occupation);
        
        // Action: Verify "WidgetA" in W17
        boolean result = warehouse.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_productDoesNotExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityJ");
        warehouse.setAddress("AddressW18");
        company.addWarehouses(warehouse);
        
        // SetUp: Add Product "GadgetB" to W18
        Product product = new Product();
        product.setName("GadgetB");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(150.0);
        warehouse.addOccupations(occupation);
        
        // Action: Verify "ToolC" in W18
        boolean result = warehouse.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityM");
        warehouse.setAddress("AddressW19");
        company.addWarehouses(warehouse);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2 (different company)
        Warehouse warehouseC2 = new Warehouse();
        warehouseC2.setCity("CityN");
        warehouseC2.setAddress("AddressW18");
        Product product = new Product();
        product.setName("ItemX");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(200.0);
        warehouseC2.addOccupations(occupation);
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = warehouse.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityK");
        warehouse.setAddress("AddressW20");
        company.addWarehouses(warehouse);
        
        // Action: Verify "PartY" in W20
        boolean result = warehouse.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityL");
        warehouse.setAddress("AddressW21");
        company.addWarehouses(warehouse);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        String[] productNames = {"CompA", "CompB", "CompC"};
        for (String name : productNames) {
            Product product = new Product();
            product.setName(name);
            ProductOccupation occupation = new ProductOccupation();
            occupation.setProduct(product);
            occupation.setVolume(50.0);
            warehouse.addOccupations(occupation);
        }
        
        // Action: Verify "CompB" in W21
        boolean result = warehouse.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}