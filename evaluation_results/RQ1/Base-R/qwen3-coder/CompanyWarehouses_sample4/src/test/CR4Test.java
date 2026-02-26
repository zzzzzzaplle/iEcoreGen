import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
        company.setName("C1");
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W17");
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "WidgetA" to W17
        Product product = new Product();
        product.setName("WidgetA");
        warehouse.addProduct(product);
        
        // Action: Verify "WidgetA" in W17
        boolean result = company.isProductInWarehouse("W17", "WidgetA");
        
        // Expected Output: true
        assertTrue("Product 'WidgetA' should exist in warehouse 'W17'", result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W18");
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "GadgetB" to W18
        Product product = new Product();
        product.setName("GadgetB");
        warehouse.addProduct(product);
        
        // Action: Verify "ToolC" in W18
        boolean result = company.isProductInWarehouse("W18", "ToolC");
        
        // Expected Output: false
        assertFalse("Product 'ToolC' should not exist in warehouse 'W18'", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W19");
        company.addWarehouse(warehouse);
        
        // Note: Product "ItemX" is added to "W18" in company C2, but our company is C1
        // So W19 in C1 has no products
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = company.isProductInWarehouse("W19", "ItemX");
        
        // Expected Output: false
        assertFalse("Product 'ItemX' should not exist in warehouse 'W19'", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W20");
        company.addWarehouse(warehouse);
        
        // Action: Verify "PartY" in W20
        boolean result = company.isProductInWarehouse("W20", "PartY");
        
        // Expected Output: false
        assertFalse("Product 'PartY' should not exist in empty warehouse 'W20'", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W21");
        company.addWarehouse(warehouse);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        Product product1 = new Product();
        product1.setName("CompA");
        warehouse.addProduct(product1);
        
        Product product2 = new Product();
        product2.setName("CompB");
        warehouse.addProduct(product2);
        
        Product product3 = new Product();
        product3.setName("CompC");
        warehouse.addProduct(product3);
        
        // Action: Verify "CompB" in W21
        boolean result = company.isProductInWarehouse("W21", "CompB");
        
        // Expected Output: true
        assertTrue("Product 'CompB' should exist in warehouse 'W21'", result);
    }
}