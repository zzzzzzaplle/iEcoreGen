import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize company before each test
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
        
        // Create product "MaterialX"
        Product product = new Product();
        product.setName("MaterialX");
        
        // Create product occupations for both warehouses
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product);
        
        warehouse1.addOccupations(occupation1);
        warehouse2.addOccupations(occupation2);
        
        company.addWarehouses(warehouse1);
        company.addWarehouses(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("MaterialX");
        
        // Expected Output: two entries with city and address pairs
        assertEquals(2, result.size());
        
        // Verify first entry: "city":"CityM","address":"W22_addr"
        assertEquals("CityM", result.get(0).getKey());
        assertEquals("W22_addr", result.get(0).getValue());
        
        // Verify second entry: "city":"CityN","address":"W23_addr"
        assertEquals("CityN", result.get(1).getKey());
        assertEquals("W23_addr", result.get(1).getValue());
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityO");
        warehouse.setAddress("W24_addr");
        
        // Add Product "DeviceY" to W24
        Product product = new Product();
        product.setName("DeviceY");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        
        warehouse.addOccupations(occupation);
        company.addWarehouses(warehouse);
        
        // Action: Find locations of "DeviceY" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("DeviceY");
        
        // Expected Output: one entry with city and address
        assertEquals(1, result.size());
        
        // Verify entry: "city":"CityO","address":"W24_addr"
        assertEquals("CityO", result.get(0).getKey());
        assertEquals("W24_addr", result.get(0).getValue());
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityR");
        warehouse.setAddress("W25_addr");
        
        Product product = new Product();
        product.setName("PartZ");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        
        warehouse.addOccupations(occupation);
        company.addWarehouses(warehouse);
        
        // Action: Find locations of "ToolW" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("ToolW");
        
        // Expected Output: empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setCity("CityS");
        warehouse1.setAddress("W26_addr");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setCity("CityT");
        warehouse2.setAddress("W27_addr");
        
        // Add different products to warehouses
        Product product1 = new Product();
        product1.setName("ProductA");
        
        Product product2 = new Product();
        product2.setName("ProductB");
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        
        warehouse1.addOccupations(occupation1);
        warehouse2.addOccupations(occupation2);
        
        company.addWarehouses(warehouse1);
        company.addWarehouses(warehouse2);
        
        // Action: Find locations of "ItemV" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("ItemV");
        
        // Expected Output: empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
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
        
        // Add Product "CommonPart" to both warehouses
        Product product = new Product();
        product.setName("CommonPart");
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product);
        
        warehouse1.addOccupations(occupation1);
        warehouse2.addOccupations(occupation2);
        
        company.addWarehouses(warehouse1);
        company.addWarehouses(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("CommonPart");
        
        // Expected Output: two entries with city and address pairs
        assertEquals(2, result.size());
        
        // Verify first entry: "city":"CityP","address":"W28_addr"
        assertEquals("CityP", result.get(0).getKey());
        assertEquals("W28_addr", result.get(0).getValue());
        
        // Verify second entry: "city":"CityQ","address":"W29_addr"
        assertEquals("CityQ", result.get(1).getKey());
        assertEquals("W29_addr", result.get(1).getValue());
    }
}