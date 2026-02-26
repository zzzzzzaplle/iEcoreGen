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
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        Warehouse warehouse1 = new Warehouse("CityM", "W22_addr", 100.0);
        Warehouse warehouse2 = new Warehouse("CityN", "W23_addr", 120.0);
        
        // Add Product "MaterialX" to both W22 and W23
        Supplier supplier1 = new Supplier("SupplierA", "AddressA");
        Product materialX1 = new Product("MaterialX", supplier1, false, 10.0);
        Product materialX2 = new Product("MaterialX", supplier1, false, 15.0);
        
        warehouse1.addProduct(materialX1);
        warehouse2.addProduct(materialX2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1
        List<String> locations = company.verifyProductLocations("MaterialX");
        
        // Expected Output: "city":"CityM","address":"W22_addr" and "city":"CityN","address":"W23_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains("CityM, W22_addr"));
        assertTrue(locations.contains("CityN, W23_addr"));
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse("CityO", "W24_addr", 150.0);
        
        // Add Product "DeviceY" to W24
        Supplier supplier = new Supplier("SupplierB", "AddressB");
        Product deviceY = new Product("DeviceY", supplier, true, 20.0);
        
        warehouse.addProduct(deviceY);
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "DeviceY" in C1
        List<String> locations = company.verifyProductLocations("DeviceY");
        
        // Expected Output: "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        assertEquals("CityO, W24_addr", locations.get(0));
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse("CityR", "W25_addr", 200.0);
        Supplier supplier = new Supplier("SupplierC", "AddressC");
        Product partZ = new Product("PartZ", supplier, false, 25.0);
        
        warehouse.addProduct(partZ);
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "ToolW" in C1
        List<String> locations = company.verifyProductLocations("ToolW");
        
        // Expected Output: []
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse1 = new Warehouse("CityS", "W26_addr", 180.0);
        Warehouse warehouse2 = new Warehouse("CityT", "W27_addr", 220.0);
        
        // Add other products (not "ItemV")
        Supplier supplier1 = new Supplier("SupplierD", "AddressD");
        Supplier supplier2 = new Supplier("SupplierE", "AddressE");
        Product product1 = new Product("OtherProduct1", supplier1, true, 30.0);
        Product product2 = new Product("OtherProduct2", supplier2, false, 35.0);
        
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "ItemV" in C1
        List<String> locations = company.verifyProductLocations("ItemV");
        
        // Expected Output: []
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse warehouse1 = new Warehouse("CityP", "W28_addr", 250.0);
        Warehouse warehouse2 = new Warehouse("CityQ", "W29_addr", 300.0);
        
        // Add Product "CommonPart" to both warehouses
        Supplier supplier = new Supplier("SupplierF", "AddressF");
        Product commonPart1 = new Product("CommonPart", supplier, false, 40.0);
        Product commonPart2 = new Product("CommonPart", supplier, true, 45.0);
        
        warehouse1.addProduct(commonPart1);
        warehouse2.addProduct(commonPart2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1
        List<String> locations = company.verifyProductLocations("CommonPart");
        
        // Expected Output: "city":"CityP","address":"W28_addr" and "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains("CityP, W28_addr"));
        assertTrue(locations.contains("CityQ, W29_addr"));
    }
}