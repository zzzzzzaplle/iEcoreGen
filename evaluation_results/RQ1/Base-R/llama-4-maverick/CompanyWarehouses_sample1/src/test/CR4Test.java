import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1
        Warehouse warehouse = new Warehouse("CityI", "W17", 100.0);
        
        // SetUp: Add Product "WidgetA" to W17
        Supplier supplier = new Supplier("Supplier1", "Address1");
        Product product = new Product("WidgetA", supplier, false, 10.0);
        warehouse.getProducts().add(product);
        
        company.getWarehouses().add(warehouse);
        
        // Action: Verify "WidgetA" in W17
        boolean result = company.verifyProductInWarehouse("W17", "WidgetA");
        
        // Expected Output: true
        assertTrue("Product 'WidgetA' should exist in warehouse 'W17'", result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        Warehouse warehouse = new Warehouse("CityJ", "W18", 120.0);
        
        // SetUp: Add Product "GadgetB" to W18
        Supplier supplier = new Supplier("Supplier2", "Address2");
        Product product = new Product("GadgetB", supplier, false, 15.0);
        warehouse.getProducts().add(product);
        
        company.getWarehouses().add(warehouse);
        
        // Action: Verify "ToolC" in W18
        boolean result = company.verifyProductInWarehouse("W18", "ToolC");
        
        // Expected Output: false
        assertFalse("Product 'ToolC' should not exist in warehouse 'W18'", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        Warehouse warehouseW19 = new Warehouse("CityM", "W19", 150.0);
        company.getWarehouses().add(warehouseW19);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2 (different company)
        Company companyC2 = new Company();
        Warehouse warehouseW18 = new Warehouse("CityN", "W18", 130.0);
        Supplier supplier = new Supplier("Supplier3", "Address3");
        Product product = new Product("ItemX", supplier, false, 20.0);
        warehouseW18.getProducts().add(product);
        companyC2.getWarehouses().add(warehouseW18);
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = company.verifyProductInWarehouse("W19", "ItemX");
        
        // Expected Output: false
        assertFalse("Product 'ItemX' should not exist in warehouse 'W19'", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        Warehouse warehouse = new Warehouse("CityK", "W20", 110.0);
        // No products added (empty warehouse)
        
        company.getWarehouses().add(warehouse);
        
        // Action: Verify "PartY" in W20
        boolean result = company.verifyProductInWarehouse("W20", "PartY");
        
        // Expected Output: false
        assertFalse("Product 'PartY' should not exist in empty warehouse 'W20'", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        Warehouse warehouse = new Warehouse("CityL", "W21", 140.0);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        Supplier supplier = new Supplier("Supplier4", "Address4");
        Product product1 = new Product("CompA", supplier, false, 12.0);
        Product product2 = new Product("CompB", supplier, false, 18.0);
        Product product3 = new Product("CompC", supplier, false, 22.0);
        
        warehouse.getProducts().add(product1);
        warehouse.getProducts().add(product2);
        warehouse.getProducts().add(product3);
        
        company.getWarehouses().add(warehouse);
        
        // Action: Verify "CompB" in W21
        boolean result = company.verifyProductInWarehouse("W21", "CompB");
        
        // Expected Output: true
        assertTrue("Product 'CompB' should exist in warehouse 'W21'", result);
    }
}