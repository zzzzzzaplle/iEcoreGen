import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;

public class CR5Test {
    
    private Company company;
    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private Warehouse warehouse3;
    private Warehouse warehouse4;
    private Warehouse warehouse5;
    private Product productMaterialX;
    private Product productDeviceY;
    private Product productPartZ;
    private Product productCommonPart;
    private ProductOccupation occupation1;
    private ProductOccupation occupation2;
    private ProductOccupation occupation3;
    private ProductOccupation occupation4;
    private ProductOccupation occupation5;
    private Supplier supplier1;
    private Supplier supplier2;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create suppliers
        supplier1 = new Supplier();
        supplier1.setName("SupplierA");
        supplier1.setAddress("SupplierA_Addr");
        
        supplier2 = new Supplier();
        supplier2.setName("SupplierB");
        supplier2.setAddress("SupplierB_Addr");
        
        // Create products
        productMaterialX = new Product();
        productMaterialX.setName("MaterialX");
        productMaterialX.setToxic(false);
        productMaterialX.setSupplier(supplier1);
        
        productDeviceY = new Product();
        productDeviceY.setName("DeviceY");
        productDeviceY.setToxic(false);
        productDeviceY.setSupplier(supplier1);
        
        productPartZ = new Product();
        productPartZ.setName("PartZ");
        productPartZ.setToxic(false);
        productPartZ.setSupplier(supplier2);
        
        productCommonPart = new Product();
        productCommonPart.setName("CommonPart");
        productCommonPart.setToxic(false);
        productCommonPart.setSupplier(supplier1);
        
        // Create product occupations
        occupation1 = new ProductOccupation();
        occupation1.setVolume(100.0);
        occupation1.setProduct(productMaterialX);
        
        occupation2 = new ProductOccupation();
        occupation2.setVolume(150.0);
        occupation2.setProduct(productMaterialX);
        
        occupation3 = new ProductOccupation();
        occupation3.setVolume(200.0);
        occupation3.setProduct(productDeviceY);
        
        occupation4 = new ProductOccupation();
        occupation4.setVolume(250.0);
        occupation4.setProduct(productPartZ);
        
        occupation5 = new ProductOccupation();
        occupation5.setVolume(300.0);
        occupation5.setProduct(productCommonPart);
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1.
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityM");
        warehouse1.setAddress("W22_addr");
        warehouse1.addOccupation(occupation1);
        
        warehouse2 = new Warehouse();
        warehouse2.setCity("CityN");
        warehouse2.setAddress("W23_addr");
        warehouse2.addOccupation(occupation2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, result.size());
        
        // Check first location
        Map.Entry<String, String> location1 = result.get(0);
        assertEquals("CityM", location1.getKey());
        assertEquals("W22_addr", location1.getValue());
        
        // Check second location
        Map.Entry<String, String> location2 = result.get(1);
        assertEquals("CityN", location2.getKey());
        assertEquals("W23_addr", location2.getValue());
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1.
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityO");
        warehouse1.setAddress("W24_addr");
        warehouse1.addOccupation(occupation3);
        
        company.addWarehouse(warehouse1);
        
        // Action: Find locations of "DeviceY" in C1.
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
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityR");
        warehouse1.setAddress("W25_addr");
        warehouse1.addOccupation(occupation4);
        
        company.addWarehouse(warehouse1);
        
        // Action: Find locations of "ToolW" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products.
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityS");
        warehouse1.setAddress("W26_addr");
        warehouse1.addOccupation(occupation1); // Contains MaterialX
        
        warehouse2 = new Warehouse();
        warehouse2.setCity("CityT");
        warehouse2.setAddress("W27_addr");
        warehouse2.addOccupation(occupation3); // Contains DeviceY
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "ItemV" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1.
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityP");
        warehouse1.setAddress("W28_addr");
        warehouse1.addOccupation(occupation5); // Contains CommonPart
        
        warehouse2 = new Warehouse();
        warehouse2.setCity("CityQ");
        warehouse2.setAddress("W29_addr");
        warehouse2.addOccupation(occupation5); // Contains CommonPart
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, result.size());
        
        // Check first location
        Map.Entry<String, String> location1 = result.get(0);
        assertEquals("CityP", location1.getKey());
        assertEquals("W28_addr", location1.getValue());
        
        // Check second location
        Map.Entry<String, String> location2 = result.get(1);
        assertEquals("CityQ", location2.getKey());
        assertEquals("W29_addr", location2.getValue());
    }
}