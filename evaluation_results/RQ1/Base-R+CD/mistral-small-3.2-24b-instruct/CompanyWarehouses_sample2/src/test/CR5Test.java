import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;

public class CR5Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setCity("CityM");
        warehouse1.setAddress("W22_addr");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setCity("CityN");
        warehouse2.setAddress("W23_addr");
        
        // SetUp: Add Product "MaterialX" to both W22 and W23
        Supplier supplier = new Supplier();
        supplier.setName("TestSupplier");
        
        Product product = new Product();
        product.setName("MaterialX");
        product.setSupplier(supplier);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product);
        occupation1.setVolume(10.0);
        warehouse1.addOccupations(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product);
        occupation2.setVolume(15.0);
        warehouse2.addOccupations(occupation2);
        
        company.addWarehouses(warehouse1);
        company.addWarehouses(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("MaterialX");
        
        // Expected Output: "city":"CityM","address":"W22_addr" and "city":"CityN","address":"W23_addr"
        assertEquals(2, locations.size());
        
        // Verify first location
        Map.Entry<String, String> location1 = locations.get(0);
        assertEquals("CityM", location1.getKey());
        assertEquals("W22_addr", location1.getValue());
        
        // Verify second location
        Map.Entry<String, String> location2 = locations.get(1);
        assertEquals("CityN", location2.getKey());
        assertEquals("W23_addr", location2.getValue());
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityO");
        warehouse.setAddress("W24_addr");
        
        // SetUp: Add Product "DeviceY" to W24
        Supplier supplier = new Supplier();
        supplier.setName("TestSupplier");
        
        Product product = new Product();
        product.setName("DeviceY");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(20.0);
        warehouse.addOccupations(occupation);
        
        company.addWarehouses(warehouse);
        
        // Action: Find locations of "DeviceY" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("DeviceY");
        
        // Expected Output: "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        
        Map.Entry<String, String> location = locations.get(0);
        assertEquals("CityO", location.getKey());
        assertEquals("W24_addr", location.getValue());
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("TestCity");
        warehouse.setAddress("W25_addr");
        
        Supplier supplier = new Supplier();
        supplier.setName("TestSupplier");
        
        Product product = new Product();
        product.setName("PartZ");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(30.0);
        warehouse.addOccupations(occupation);
        
        company.addWarehouses(warehouse);
        
        // Action: Find locations of "ToolW" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setCity("CityA");
        warehouse1.setAddress("W26_addr");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setCity("CityB");
        warehouse2.setAddress("W27_addr");
        
        // Add different products to warehouses
        Supplier supplier = new Supplier();
        supplier.setName("TestSupplier");
        
        Product product1 = new Product();
        product1.setName("ProductA");
        product1.setSupplier(supplier);
        
        Product product2 = new Product();
        product2.setName("ProductB");
        product2.setSupplier(supplier);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(10.0);
        warehouse1.addOccupations(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(15.0);
        warehouse2.addOccupations(occupation2);
        
        company.addWarehouses(warehouse1);
        company.addWarehouses(warehouse2);
        
        // Action: Find locations of "ItemV" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setCity("CityP");
        warehouse1.setAddress("W28_addr");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setCity("CityQ");
        warehouse2.setAddress("W29_addr");
        
        // SetUp: Add Product "CommonPart" to both warehouses
        Supplier supplier = new Supplier();
        supplier.setName("TestSupplier");
        
        Product product = new Product();
        product.setName("CommonPart");
        product.setSupplier(supplier);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product);
        occupation1.setVolume(25.0);
        warehouse1.addOccupations(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product);
        occupation2.setVolume(35.0);
        warehouse2.addOccupations(occupation2);
        
        company.addWarehouses(warehouse1);
        company.addWarehouses(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("CommonPart");
        
        // Expected Output: "city":"CityP","address":"W28_addr" and "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        
        // Verify first location
        Map.Entry<String, String> location1 = locations.get(0);
        assertEquals("CityP", location1.getKey());
        assertEquals("W28_addr", location1.getValue());
        
        // Verify second location
        Map.Entry<String, String> location2 = locations.get(1);
        assertEquals("CityQ", location2.getKey());
        assertEquals("W29_addr", location2.getValue());
    }
}