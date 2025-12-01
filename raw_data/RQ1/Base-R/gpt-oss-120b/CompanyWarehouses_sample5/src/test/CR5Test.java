import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company("C1");
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        Warehouse warehouse22 = new Warehouse("CityM", "W22_addr", 100.0);
        Warehouse warehouse23 = new Warehouse("CityN", "W23_addr", 120.0);
        
        // Add Product "MaterialX" to both W22 and W23
        Product materialX1 = new Product("MaterialX", new Supplier("S1", "Addr1"), false, 5.0);
        Product materialX2 = new Product("MaterialX", new Supplier("S1", "Addr1"), false, 7.0);
        
        warehouse22.addProduct(materialX1);
        warehouse23.addProduct(materialX2);
        
        company.addWarehouse(warehouse22);
        company.addWarehouse(warehouse23);
        
        // Action: Find locations of "MaterialX" in C1
        List<String> locations = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains("City: CityM, Address: W22_addr"));
        assertTrue(locations.contains("City: CityN, Address: W23_addr"));
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse24 = new Warehouse("CityO", "W24_addr", 90.0);
        
        // Add Product "DeviceY" to W24
        Product deviceY = new Product("DeviceY", new Supplier("S2", "Addr2"), true, 3.0);
        warehouse24.addProduct(deviceY);
        
        company.addWarehouse(warehouse24);
        
        // Action: Find locations of "DeviceY" in C1
        List<String> locations = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        assertEquals("City: CityO, Address: W24_addr", locations.get(0));
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse25 = new Warehouse("CityR", "W25_addr", 110.0);
        Product partZ = new Product("PartZ", new Supplier("S3", "Addr3"), false, 4.0);
        warehouse25.addProduct(partZ);
        
        company.addWarehouse(warehouse25);
        
        // Action: Find locations of "ToolW" in C1
        List<String> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse26 = new Warehouse("CityS", "W26_addr", 95.0);
        Warehouse warehouse27 = new Warehouse("CityT", "W27_addr", 105.0);
        
        // Add other products to warehouses
        Product productA = new Product("ProductA", new Supplier("S4", "Addr4"), true, 2.0);
        Product productB = new Product("ProductB", new Supplier("S5", "Addr5"), false, 6.0);
        
        warehouse26.addProduct(productA);
        warehouse27.addProduct(productB);
        
        company.addWarehouse(warehouse26);
        company.addWarehouse(warehouse27);
        
        // Action: Find locations of "ItemV" in C1
        List<String> locations = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse warehouse28 = new Warehouse("CityP", "W28_addr", 115.0);
        Warehouse warehouse29 = new Warehouse("CityQ", "W29_addr", 125.0);
        
        // Add Product "CommonPart" to both warehouses
        Product commonPart1 = new Product("CommonPart", new Supplier("S6", "Addr6"), false, 8.0);
        Product commonPart2 = new Product("CommonPart", new Supplier("S6", "Addr6"), false, 9.0);
        
        warehouse28.addProduct(commonPart1);
        warehouse29.addProduct(commonPart2);
        
        company.addWarehouse(warehouse28);
        company.addWarehouse(warehouse29);
        
        // Action: Find locations of "CommonPart" in C1
        List<String> locations = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains("City: CityP, Address: W28_addr"));
        assertTrue(locations.contains("City: CityQ, Address: W29_addr"));
    }
}