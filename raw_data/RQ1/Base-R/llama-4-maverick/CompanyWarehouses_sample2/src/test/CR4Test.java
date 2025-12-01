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
        Warehouse warehouse = new Warehouse("W17", "CityI", 100.0);
        
        // SetUp: Add Product "WidgetA" to W17
        Supplier supplier = new Supplier("SupplierA", "AddressA");
        Product product = new Product("WidgetA", supplier, false, 10.0);
        warehouse.getProducts().add(product);
        company.getWarehouses().add(warehouse);
        
        // Action: Verify "WidgetA" in W17
        boolean result = company.verifyProductInWarehouse("W17", "WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        Warehouse warehouse = new Warehouse("W18", "CityJ", 150.0);
        
        // SetUp: Add Product "GadgetB" to W18
        Supplier supplier = new Supplier("SupplierB", "AddressB");
        Product product = new Product("GadgetB", supplier, false, 15.0);
        warehouse.getProducts().add(product);
        company.getWarehouses().add(warehouse);
        
        // Action: Verify "ToolC" in W18
        boolean result = company.verifyProductInWarehouse("W18", "ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Create Warehouse "W18" in company C1 and add Product "ItemX"
        Warehouse warehouse1 = new Warehouse("W18", "CityM", 200.0);
        Supplier supplier = new Supplier("SupplierC", "AddressC");
        Product product = new Product("ItemX", supplier, false, 20.0);
        warehouse1.getProducts().add(product);
        
        // Create Warehouse "W19" in company C1 (empty)
        Warehouse warehouse2 = new Warehouse("W19", "CityN", 250.0);
        
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Action: Verify "ItemX" in Warehouse "W19" (which doesn't contain ItemX)
        boolean result = company.verifyProductInWarehouse("W19", "ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        Warehouse warehouse = new Warehouse("W20", "CityK", 300.0);
        company.getWarehouses().add(warehouse);
        
        // Action: Verify "PartY" in W20
        boolean result = company.verifyProductInWarehouse("W20", "PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        Warehouse warehouse = new Warehouse("W21", "CityL", 350.0);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        Supplier supplier = new Supplier("SupplierD", "AddressD");
        Product product1 = new Product("CompA", supplier, false, 25.0);
        Product product2 = new Product("CompB", supplier, false, 30.0);
        Product product3 = new Product("CompC", supplier, false, 35.0);
        
        warehouse.getProducts().add(product1);
        warehouse.getProducts().add(product2);
        warehouse.getProducts().add(product3);
        company.getWarehouses().add(warehouse);
        
        // Action: Verify "CompB" in W21
        boolean result = company.verifyProductInWarehouse("W21", "CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}