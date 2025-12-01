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
        company = new Company();
        
        // Create warehouses for test cases
        warehouseW17 = new Warehouse("CityI", "W17", 100.0);
        warehouseW18 = new Warehouse("CityJ", "W18", 120.0);
        warehouseW19 = new Warehouse("CityM", "W19", 150.0);
        warehouseW20 = new Warehouse("CityK", "W20", 80.0);
        warehouseW21 = new Warehouse("CityL", "W21", 200.0);
        
        // Add warehouses to company
        company.addWarehouse(warehouseW17);
        company.addWarehouse(warehouseW18);
        company.addWarehouse(warehouseW20);
        company.addWarehouse(warehouseW21);
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1. Add Product "WidgetA" to W17.
        Supplier supplier1 = new Supplier("SupplierA", "AddressA");
        Product widgetA = new Product("WidgetA", supplier1, false, 10.0);
        warehouseW17.addProduct(widgetA);
        
        // Action: Verify "WidgetA" in W17
        boolean result = company.verifyProductInWarehouse("W17", "WidgetA");
        
        // Expected Output: true
        assertTrue("Product 'WidgetA' should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1. Add Product "GadgetB" to W18.
        Supplier supplier2 = new Supplier("SupplierB", "AddressB");
        Product gadgetB = new Product("GadgetB", supplier2, true, 15.0);
        warehouseW18.addProduct(gadgetB);
        
        // Action: Verify "ToolC" in W18
        boolean result = company.verifyProductInWarehouse("W18", "ToolC");
        
        // Expected Output: false
        assertFalse("Product 'ToolC' should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1. Add Product "ItemX" to "W18" in company C2.
        // Note: W19 is created but NOT added to company, so it doesn't exist in company C1
        Supplier supplier3 = new Supplier("SupplierC", "AddressC");
        Product itemX = new Product("ItemX", supplier3, false, 20.0);
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = company.verifyProductInWarehouse("W19", "ItemX");
        
        // Expected Output: false
        assertFalse("Warehouse W19 should not exist in company", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1.
        // No products added to warehouse W20 (it remains empty)
        
        // Action: Verify "PartY" in W20
        boolean result = company.verifyProductInWarehouse("W20", "PartY");
        
        // Expected Output: false
        assertFalse("Product 'PartY' should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1. Add Products ["CompA", "CompB", "CompC"] to W21.
        Supplier supplier4 = new Supplier("SupplierD", "AddressD");
        Product compA = new Product("CompA", supplier4, false, 5.0);
        Product compB = new Product("CompB", supplier4, true, 8.0);
        Product compC = new Product("CompC", supplier4, false, 12.0);
        
        warehouseW21.addProduct(compA);
        warehouseW21.addProduct(compB);
        warehouseW21.addProduct(compC);
        
        // Action: Verify "CompB" in W21
        boolean result = company.verifyProductInWarehouse("W21", "CompB");
        
        // Expected Output: true
        assertTrue("Product 'CompB' should exist in warehouse W21", result);
    }
}