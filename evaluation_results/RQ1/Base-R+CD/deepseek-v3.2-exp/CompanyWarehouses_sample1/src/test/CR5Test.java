import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
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
        
        // Add Product "MaterialX" to both W22 and W23
        Product product = new Product();
        product.setName("MaterialX");
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product);
        warehouse1.addOccupation(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product);
        warehouse2.addOccupation(occupation2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("MaterialX");
        
        // Expected Output: "city":"CityM","address":"W22_addr" and "city":"CityN","address":"W23_addr"
        assertEquals(2, result.size());
        
        // Verify first entry
        Map.Entry<String, String> entry1 = result.get(0);
        assertEquals("CityM", entry1.getKey());
        assertEquals("W22_addr", entry1.getValue());
        
        // Verify second entry
        Map.Entry<String, String> entry2 = result.get(1);
        assertEquals("CityN", entry2.getKey());
        assertEquals("W23_addr", entry2.getValue());
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
        warehouse.addOccupation(occupation);
        
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "DeviceY" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("DeviceY");
        
        // Expected Output: "city":"CityO","address":"W24_addr"
        assertEquals(1, result.size());
        
        Map.Entry<String, String> entry = result.get(0);
        assertEquals("CityO", entry.getKey());
        assertEquals("W24_addr", entry.getValue());
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
        warehouse.addOccupation(occupation);
        
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "ToolW" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("ToolW");
        
        // Expected Output: []
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
        warehouse1.addOccupation(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        warehouse2.addOccupation(occupation2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "ItemV" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("ItemV");
        
        // Expected Output: []
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
        warehouse1.addOccupation(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product);
        warehouse2.addOccupation(occupation2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("CommonPart");
        
        // Expected Output: "city":"CityP","address":"W28_addr" and "city":"CityQ","address":"W29_addr"
        assertEquals(2, result.size());
        
        // Verify both entries exist (order may vary, so check both)
        boolean foundCityP = false;
        boolean foundCityQ = false;
        
        for (Map.Entry<String, String> entry : result) {
            if ("CityP".equals(entry.getKey()) && "W28_addr".equals(entry.getValue())) {
                foundCityP = true;
            }
            if ("CityQ".equals(entry.getKey()) && "W29_addr".equals(entry.getValue())) {
                foundCityQ = true;
            }
        }
        
        assertTrue("Should contain CityP with address W28_addr", foundCityP);
        assertTrue("Should contain CityQ with address W29_addr", foundCityQ);
    }
}