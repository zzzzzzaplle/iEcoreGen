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
        warehouse = new Warehouse();
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1. Add Product "WidgetA" to W17.
        warehouse.setCity("CityI");
        warehouse.setAddress("W17");
        
        Product product = new Product();
        product.setName("WidgetA");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        
        warehouse.addOccupation(occupation);
        company.addWarehouse(warehouse);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = warehouse.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product 'WidgetA' should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_ProductDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1. Add Product "GadgetB" to W18.
        warehouse.setCity("CityJ");
        warehouse.setAddress("W18");
        
        Product product = new Product();
        product.setName("GadgetB");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        
        warehouse.addOccupation(occupation);
        company.addWarehouse(warehouse);
        
        // Action: Verify "ToolC" in W18.
        boolean result = warehouse.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product 'ToolC' should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1. Add Product "ItemX" to "W18" in company C2.
        warehouse.setCity("CityI");
        warehouse.setAddress("W19");
        company.addWarehouse(warehouse);
        
        // Create separate warehouse W18 in different company context (company C2)
        Warehouse warehouseW18 = new Warehouse();
        warehouseW18.setCity("CityJ");
        warehouseW18.setAddress("W18");
        
        Product product = new Product();
        product.setName("ItemX");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        
        warehouseW18.addOccupation(occupation);
        // Note: warehouseW18 is NOT added to company (simulating company C2 scenario)
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = warehouse.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product 'ItemX' should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1.
        warehouse.setCity("CityK");
        warehouse.setAddress("W20");
        company.addWarehouse(warehouse);
        
        // Action: Verify "PartY" in W20.
        boolean result = warehouse.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product 'PartY' should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1. Add Products ["CompA", "CompB", "CompC"] to W21.
        warehouse.setCity("CityL");
        warehouse.setAddress("W21");
        
        // Add Product CompA
        Product productA = new Product();
        productA.setName("CompA");
        ProductOccupation occupationA = new ProductOccupation();
        occupationA.setProduct(productA);
        warehouse.addOccupation(occupationA);
        
        // Add Product CompB
        Product productB = new Product();
        productB.setName("CompB");
        ProductOccupation occupationB = new ProductOccupation();
        occupationB.setProduct(productB);
        warehouse.addOccupation(occupationB);
        
        // Add Product CompC
        Product productC = new Product();
        productC.setName("CompC");
        ProductOccupation occupationC = new ProductOccupation();
        occupationC.setProduct(productC);
        warehouse.addOccupation(occupationC);
        
        company.addWarehouse(warehouse);
        
        // Action: Verify "CompB" in W21.
        boolean result = warehouse.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product 'CompB' should exist in warehouse W21", result);
    }
}