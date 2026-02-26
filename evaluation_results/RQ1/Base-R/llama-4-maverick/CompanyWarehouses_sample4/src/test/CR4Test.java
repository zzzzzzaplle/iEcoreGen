import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1
        Warehouse warehouse = new Warehouse("CityI", "W17", 100.0);
        
        // SetUp: Add Product "WidgetA" to W17
        Product product = new Product("WidgetA", new Supplier("SupplierA", "AddressA"), false, 10.0);
        warehouse.addProduct(product);
        company.addWarehouse(warehouse);
        
        // Action: Verify "WidgetA" in W17
        boolean result = company.verifyProductInWarehouse("W17", "WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_ProductDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        Warehouse warehouse = new Warehouse("CityJ", "W18", 150.0);
        
        // SetUp: Add Product "GadgetB" to W18
        Product product = new Product("GadgetB", new Supplier("SupplierB", "AddressB"), true, 5.0);
        warehouse.addProduct(product);
        company.addWarehouse(warehouse);
        
        // Action: Verify "ToolC" in W18
        boolean result = company.verifyProductInWarehouse("W18", "ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        Warehouse warehouse1 = new Warehouse("CityM", "W19", 200.0);
        company.addWarehouse(warehouse1);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2
        // Note: We need to create a separate company for C2 since our test company is C1
        Company company2 = new Company();
        Warehouse warehouse2 = new Warehouse("CityN", "W18", 120.0);
        Product product = new Product("ItemX", new Supplier("SupplierX", "AddressX"), false, 8.0);
        warehouse2.addProduct(product);
        company2.addWarehouse(warehouse2);
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = company.verifyProductInWarehouse("W19", "ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        Warehouse warehouse = new Warehouse("CityK", "W20", 180.0);
        company.addWarehouse(warehouse);
        
        // Action: Verify "PartY" in W20
        boolean result = company.verifyProductInWarehouse("W20", "PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        Warehouse warehouse = new Warehouse("CityL", "W21", 250.0);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        Supplier supplier = new Supplier("SupplierC", "AddressC");
        Product product1 = new Product("CompA", supplier, false, 15.0);
        Product product2 = new Product("CompB", supplier, true, 12.0);
        Product product3 = new Product("CompC", supplier, false, 18.0);
        
        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        company.addWarehouse(warehouse);
        
        // Action: Verify "CompB" in W21
        boolean result = company.verifyProductInWarehouse("W21", "CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}