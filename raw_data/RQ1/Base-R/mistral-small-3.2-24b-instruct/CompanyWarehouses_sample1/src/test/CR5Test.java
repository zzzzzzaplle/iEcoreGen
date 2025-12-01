import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1.
        Warehouse warehouse1 = new Warehouse("W22_addr", 100.0);
        warehouse1.setAddress("W22_addr");
        Warehouse warehouse2 = new Warehouse("W23_addr", 150.0);
        warehouse2.setAddress("W23_addr");
        
        // Add Product "MaterialX" to both W22 and W23.
        Supplier supplier = new Supplier("SupplierA", "SupplierAddress");
        Product product1 = new Product("MaterialX", supplier, false, 10.0);
        Product product2 = new Product("MaterialX", supplier, false, 15.0);
        
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1.
        List<String> result = company.getProductLocations("MaterialX");
        
        // Expected Output: ["W22_addr", "W23_addr"]
        assertEquals(2, result.size());
        assertTrue(result.contains("W22_addr"));
        assertTrue(result.contains("W23_addr"));
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1.
        Warehouse warehouse = new Warehouse("W24_addr", 200.0);
        warehouse.setAddress("W24_addr");
        
        // Add Product "DeviceY" to W24.
        Supplier supplier = new Supplier("SupplierB", "SupplierAddress2");
        Product product = new Product("DeviceY", supplier, true, 25.0);
        
        warehouse.addProduct(product);
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "DeviceY" in C1.
        List<String> result = company.getProductLocations("DeviceY");
        
        // Expected Output: ["W24_addr"]
        assertEquals(1, result.size());
        assertEquals("W24_addr", result.get(0));
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
        Warehouse warehouse = new Warehouse("W25_addr", 300.0);
        warehouse.setAddress("W25_addr");
        
        Supplier supplier = new Supplier("SupplierC", "SupplierAddress3");
        Product product = new Product("PartZ", supplier, false, 30.0);
        
        warehouse.addProduct(product);
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "ToolW" in C1.
        List<String> result = company.getProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products.
        Warehouse warehouse1 = new Warehouse("W26_addr", 400.0);
        warehouse1.setAddress("W26_addr");
        Warehouse warehouse2 = new Warehouse("W27_addr", 500.0);
        warehouse2.setAddress("W27_addr");
        
        // Add other products to warehouses
        Supplier supplier = new Supplier("SupplierD", "SupplierAddress4");
        Product product1 = new Product("OtherProduct1", supplier, true, 40.0);
        Product product2 = new Product("OtherProduct2", supplier, false, 50.0);
        
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "ItemV" in C1.
        List<String> result = company.getProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1.
        Warehouse warehouse1 = new Warehouse("W28_addr", 600.0);
        warehouse1.setAddress("W28_addr");
        Warehouse warehouse2 = new Warehouse("W29_addr", 700.0);
        warehouse2.setAddress("W29_addr");
        
        // Add Product "CommonPart" to both warehouses.
        Supplier supplier = new Supplier("SupplierE", "SupplierAddress5");
        Product product1 = new Product("CommonPart", supplier, false, 60.0);
        Product product2 = new Product("CommonPart", supplier, false, 70.0);
        
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1.
        List<String> result = company.getProductLocations("CommonPart");
        
        // Expected Output: ["W28_addr", "W29_addr"]
        assertEquals(2, result.size());
        assertTrue(result.contains("W28_addr"));
        assertTrue(result.contains("W29_addr"));
    }
}