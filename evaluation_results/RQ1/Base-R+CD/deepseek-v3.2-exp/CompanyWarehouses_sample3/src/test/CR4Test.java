import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class CR4Test {
    
    private Company company;
    private Warehouse warehouse;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1. Add Product "WidgetA" to W17.
        warehouse = new Warehouse();
        warehouse.setCity("CityI");
        warehouse.setAddress("W17");
        company.addWarehouses(warehouse);
        
        Product product = new Product();
        product.setName("WidgetA");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(10.0);
        
        warehouse.addOccupations(occupation);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = warehouse.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product 'WidgetA' should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1. Add Product "GadgetB" to W18.
        warehouse = new Warehouse();
        warehouse.setCity("CityJ");
        warehouse.setAddress("W18");
        company.addWarehouses(warehouse);
        
        Product product = new Product();
        product.setName("GadgetB");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(15.0);
        
        warehouse.addOccupations(occupation);
        
        // Action: Verify "ToolC" in W18.
        boolean result = warehouse.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product 'ToolC' should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1. Add Product "ItemX" to "W18" in company C2.
        warehouse = new Warehouse();
        warehouse.setCity("CityI");
        warehouse.setAddress("W19");
        company.addWarehouses(warehouse);
        
        // Create a different warehouse W18 in a different company (not added to our test company)
        Warehouse warehouse18 = new Warehouse();
        warehouse18.setCity("CityJ");
        warehouse18.setAddress("W18");
        
        Product product = new Product();
        product.setName("ItemX");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(20.0);
        
        warehouse18.addOccupations(occupation);
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = warehouse.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product 'ItemX' should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1.
        warehouse = new Warehouse();
        warehouse.setCity("CityK");
        warehouse.setAddress("W20");
        company.addWarehouses(warehouse);
        
        // Action: Verify "PartY" in W20.
        boolean result = warehouse.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product 'PartY' should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1. Add Products ["CompA", "CompB", "CompC"] to W21.
        warehouse = new Warehouse();
        warehouse.setCity("CityL");
        warehouse.setAddress("W21");
        company.addWarehouses(warehouse);
        
        // Add CompA
        Product productA = new Product();
        productA.setName("CompA");
        ProductOccupation occupationA = new ProductOccupation();
        occupationA.setProduct(productA);
        occupationA.setVolume(5.0);
        warehouse.addOccupations(occupationA);
        
        // Add CompB
        Product productB = new Product();
        productB.setName("CompB");
        ProductOccupation occupationB = new ProductOccupation();
        occupationB.setProduct(productB);
        occupationB.setVolume(8.0);
        warehouse.addOccupations(occupationB);
        
        // Add CompC
        Product productC = new Product();
        productC.setName("CompC");
        ProductOccupation occupationC = new ProductOccupation();
        occupationC.setProduct(productC);
        occupationC.setVolume(12.0);
        warehouse.addOccupations(occupationC);
        
        // Action: Verify "CompB" in W21.
        boolean result = warehouse.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product 'CompB' should exist in warehouse W21 with multiple products", result);
    }
}