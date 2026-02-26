import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class CR5Test {
    
    private Company company;
    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private Warehouse warehouse3;
    private Warehouse warehouse4;
    private Warehouse warehouse5;
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private Product product5;
    private Supplier supplier1;
    private Supplier supplier2;
    private ProductOccupation occupation1;
    private ProductOccupation occupation2;
    private ProductOccupation occupation3;
    private ProductOccupation occupation4;
    private ProductOccupation occupation5;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Initialize suppliers
        supplier1 = new Supplier();
        supplier1.setName("SupplierA");
        supplier1.setAddress("SupplierA_Addr");
        
        supplier2 = new Supplier();
        supplier2.setName("SupplierB");
        supplier2.setAddress("SupplierB_Addr");
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityM");
        warehouse1.setAddress("W22_addr");
        
        warehouse2 = new Warehouse();
        warehouse2.setCity("CityN");
        warehouse2.setAddress("W23_addr");
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // SetUp: Add Product "MaterialX" to both W22 and W23
        product1 = new Product();
        product1.setName("MaterialX");
        product1.setSupplier(supplier1);
        
        occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(100.0);
        
        occupation2 = new ProductOccupation();
        occupation2.setProduct(product1);
        occupation2.setVolume(150.0);
        
        warehouse1.addOccupation(occupation1);
        warehouse2.addOccupation(occupation2);
        
        // Action: Find locations of "MaterialX" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, result.size());
        
        // Verify first location
        Map.Entry<String, String> location1 = result.get(0);
        assertEquals("CityM", location1.getKey());
        assertEquals("W22_addr", location1.getValue());
        
        // Verify second location
        Map.Entry<String, String> location2 = result.get(1);
        assertEquals("CityN", location2.getKey());
        assertEquals("W23_addr", location2.getValue());
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityO");
        warehouse1.setAddress("W24_addr");
        
        company.addWarehouse(warehouse1);
        
        // SetUp: Add Product "DeviceY" to W24
        product1 = new Product();
        product1.setName("DeviceY");
        product1.setSupplier(supplier1);
        
        occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(200.0);
        
        warehouse1.addOccupation(occupation1);
        
        // Action: Find locations of "DeviceY" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, result.size());
        
        Map.Entry<String, String> location = result.get(0);
        assertEquals("CityO", location.getKey());
        assertEquals("W24_addr", location.getValue());
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityR");
        warehouse1.setAddress("W25_addr");
        
        company.addWarehouse(warehouse1);
        
        product1 = new Product();
        product1.setName("PartZ");
        product1.setSupplier(supplier1);
        
        occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(300.0);
        
        warehouse1.addOccupation(occupation1);
        
        // Action: Find locations of "ToolW" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityS");
        warehouse1.setAddress("W26_addr");
        
        warehouse2 = new Warehouse();
        warehouse2.setCity("CityT");
        warehouse2.setAddress("W27_addr");
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // SetUp: Warehouses are both in company C1
        product1 = new Product();
        product1.setName("ProductA");
        product1.setSupplier(supplier1);
        
        product2 = new Product();
        product2.setName("ProductB");
        product2.setSupplier(supplier2);
        
        occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(400.0);
        
        occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(500.0);
        
        warehouse1.addOccupation(occupation1);
        warehouse2.addOccupation(occupation2);
        
        // Action: Find locations of "ItemV" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityP");
        warehouse1.setAddress("W28_addr");
        
        warehouse2 = new Warehouse();
        warehouse2.setCity("CityQ");
        warehouse2.setAddress("W29_addr");
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // SetUp: Add Product "CommonPart" to both warehouses
        product1 = new Product();
        product1.setName("CommonPart");
        product1.setSupplier(supplier1);
        
        occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(600.0);
        
        occupation2 = new ProductOccupation();
        occupation2.setProduct(product1);
        occupation2.setVolume(700.0);
        
        warehouse1.addOccupation(occupation1);
        warehouse2.addOccupation(occupation2);
        
        // Action: Find locations of "CommonPart" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, result.size());
        
        // Verify first location
        Map.Entry<String, String> location1 = result.get(0);
        assertEquals("CityP", location1.getKey());
        assertEquals("W28_addr", location1.getValue());
        
        // Verify second location
        Map.Entry<String, String> location2 = result.get(1);
        assertEquals("CityQ", location2.getKey());
        assertEquals("W29_addr", location2.getValue());
    }
}