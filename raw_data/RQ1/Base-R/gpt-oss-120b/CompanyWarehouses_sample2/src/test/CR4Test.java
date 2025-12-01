import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    private Warehouse warehouseW17;
    private Warehouse warehouseW18;
    private Warehouse warehouseW19;
    private Warehouse warehouseW20;
    private Warehouse warehouseW21;
    
    @Before
    public void setUp() {
        // Initialize company for testing
        company = new Company("C1");
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1. Add Product "WidgetA" to W17.
        warehouseW17 = new Warehouse("CityI", "AddressI", 100.0);
        Product widgetA = new Product("WidgetA", false, 5.0, new Supplier("SupplierA", "AddressA"));
        warehouseW17.addProduct(widgetA);
        company.addWarehouse(warehouseW17);
        
        // Action: Verify "WidgetA" in W17
        boolean result = company.warehouseContainsProduct("CityI", "WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1. Add Product "GadgetB" to W18.
        warehouseW18 = new Warehouse("CityJ", "AddressJ", 120.0);
        Product gadgetB = new Product("GadgetB", false, 3.0, new Supplier("SupplierB", "AddressB"));
        warehouseW18.addProduct(gadgetB);
        company.addWarehouse(warehouseW18);
        
        // Action: Verify "ToolC" in W18
        boolean result = company.warehouseContainsProduct("CityJ", "ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1 (no products added)
        warehouseW19 = new Warehouse("CityM", "AddressM", 80.0);
        company.addWarehouse(warehouseW19);
        
        // Note: Product "ItemX" is added to warehouse in company C2 (not C1), so it won't be found
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = company.warehouseContainsProduct("CityM", "ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1.
        warehouseW20 = new Warehouse("CityK", "AddressK", 90.0);
        company.addWarehouse(warehouseW20);
        
        // Action: Verify "PartY" in W20
        boolean result = company.warehouseContainsProduct("CityK", "PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1. Add Products ["CompA", "CompB", "CompC"] to W21.
        warehouseW21 = new Warehouse("CityL", "AddressL", 150.0);
        
        Product compA = new Product("CompA", false, 2.0, new Supplier("SupplierC", "AddressC"));
        Product compB = new Product("CompB", false, 4.0, new Supplier("SupplierD", "AddressD"));
        Product compC = new Product("CompC", false, 6.0, new Supplier("SupplierE", "AddressE"));
        
        warehouseW21.addProduct(compA);
        warehouseW21.addProduct(compB);
        warehouseW21.addProduct(compC);
        
        company.addWarehouse(warehouseW21);
        
        // Action: Verify "CompB" in W21
        boolean result = company.warehouseContainsProduct("CityL", "CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}