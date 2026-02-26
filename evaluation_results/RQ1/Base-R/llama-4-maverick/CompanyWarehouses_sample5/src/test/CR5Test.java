import org.junit.Test;
import org.junit.Before;
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
    public void testCase1_productInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        Warehouse warehouse1 = new Warehouse("CityM", "W22_addr", 100.0);
        Warehouse warehouse2 = new Warehouse("CityN", "W23_addr", 150.0);
        
        // SetUp: Add Product "MaterialX" to both W22 and W23
        Supplier supplier1 = new Supplier("SupplierA", "AddressA");
        Product product1 = new Product("MaterialX", supplier1, false, 10.0);
        Product product2 = new Product("MaterialX", supplier1, false, 15.0);
        
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1
        List<String> actualLocations = company.verifyProductLocations("MaterialX");
        
        // Expected Output: "city":"CityM","address":"W22_addr" and "city":"CityN","address":"W23_addr"
        List<String> expectedLocations = new ArrayList<>();
        expectedLocations.add("CityM, W22_addr");
        expectedLocations.add("CityN, W23_addr");
        
        assertEquals("Product should be found in both warehouses", expectedLocations, actualLocations);
    }
    
    @Test
    public void testCase2_productInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse("CityO", "W24_addr", 200.0);
        
        // SetUp: Add Product "DeviceY" to W24
        Supplier supplier = new Supplier("SupplierB", "AddressB");
        Product product = new Product("DeviceY", supplier, true, 5.0);
        
        warehouse.addProduct(product);
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "DeviceY" in C1
        List<String> actualLocations = company.verifyProductLocations("DeviceY");
        
        // Expected Output: "city":"CityO","address":"W24_addr"
        List<String> expectedLocations = new ArrayList<>();
        expectedLocations.add("CityO, W24_addr");
        
        assertEquals("Product should be found in single warehouse", expectedLocations, actualLocations);
    }
    
    @Test
    public void testCase3_nonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse("CityR", "W25_addr", 300.0);
        Supplier supplier = new Supplier("SupplierC", "AddressC");
        Product product = new Product("PartZ", supplier, false, 20.0);
        
        warehouse.addProduct(product);
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "ToolW" in C1
        List<String> actualLocations = company.verifyProductLocations("ToolW");
        
        // Expected Output: []
        List<String> expectedLocations = new ArrayList<>();
        
        assertEquals("Non-existent product should return empty list", expectedLocations, actualLocations);
    }
    
    @Test
    public void testCase4_productInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse1 = new Warehouse("CityS", "W26_addr", 400.0);
        Warehouse warehouse2 = new Warehouse("CityT", "W27_addr", 500.0);
        
        Supplier supplier1 = new Supplier("SupplierD", "AddressD");
        Supplier supplier2 = new Supplier("SupplierE", "AddressE");
        
        Product product1 = new Product("ComponentA", supplier1, true, 8.0);
        Product product2 = new Product("ComponentB", supplier2, false, 12.0);
        
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "ItemV" in C1
        List<String> actualLocations = company.verifyProductLocations("ItemV");
        
        // Expected Output: []
        List<String> expectedLocations = new ArrayList<>();
        
        assertEquals("Product not in any warehouse should return empty list", expectedLocations, actualLocations);
    }
    
    @Test
    public void testCase5_productInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse warehouse1 = new Warehouse("CityP", "W28_addr", 600.0);
        Warehouse warehouse2 = new Warehouse("CityQ", "W29_addr", 700.0);
        
        // SetUp: Add Product "CommonPart" to both warehouses
        Supplier supplier = new Supplier("SupplierF", "AddressF");
        Product product1 = new Product("CommonPart", supplier, false, 25.0);
        Product product2 = new Product("CommonPart", supplier, false, 30.0);
        
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1
        List<String> actualLocations = company.verifyProductLocations("CommonPart");
        
        // Expected Output: "city":"CityP","address":"W28_addr" and "city":"CityQ","address":"W29_addr"
        List<String> expectedLocations = new ArrayList<>();
        expectedLocations.add("CityP, W28_addr");
        expectedLocations.add("CityQ, W29_addr");
        
        assertEquals("Product should be found in all warehouses", expectedLocations, actualLocations);
    }
}