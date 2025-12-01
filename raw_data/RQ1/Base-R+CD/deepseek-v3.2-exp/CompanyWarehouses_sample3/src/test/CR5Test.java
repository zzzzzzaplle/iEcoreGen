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
        company = new Company();
    }
    
    @Test
    public void testCase1_productInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1.
        Warehouse warehouse22 = new Warehouse();
        warehouse22.setCity("CityM");
        warehouse22.setAddress("W22_addr");
        
        Warehouse warehouse23 = new Warehouse();
        warehouse23.setCity("CityN");
        warehouse23.setAddress("W23_addr");
        
        // SetUp: Add Product "MaterialX" to both W22 and W23.
        Product materialX = new Product();
        materialX.setName("MaterialX");
        
        ProductOccupation occupation22 = new ProductOccupation();
        occupation22.setProduct(materialX);
        warehouse22.addOccupations(occupation22);
        
        ProductOccupation occupation23 = new ProductOccupation();
        occupation23.setProduct(materialX);
        warehouse23.addOccupations(occupation23);
        
        company.addWarehouses(warehouse22);
        company.addWarehouses(warehouse23);
        
        // Action: Find locations of "MaterialX" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
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
    public void testCase2_productInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1.
        Warehouse warehouse24 = new Warehouse();
        warehouse24.setCity("CityO");
        warehouse24.setAddress("W24_addr");
        
        // SetUp: Add Product "DeviceY" to W24.
        Product deviceY = new Product();
        deviceY.setName("DeviceY");
        
        ProductOccupation occupation24 = new ProductOccupation();
        occupation24.setProduct(deviceY);
        warehouse24.addOccupations(occupation24);
        
        company.addWarehouses(warehouse24);
        
        // Action: Find locations of "DeviceY" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        
        Map.Entry<String, String> location = locations.get(0);
        assertEquals("CityO", location.getKey());
        assertEquals("W24_addr", location.getValue());
    }
    
    @Test
    public void testCase3_nonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
        Warehouse warehouse25 = new Warehouse();
        warehouse25.setCity("CityR");
        warehouse25.setAddress("W25_addr");
        
        Product partZ = new Product();
        partZ.setName("PartZ");
        
        ProductOccupation occupation25 = new ProductOccupation();
        occupation25.setProduct(partZ);
        warehouse25.addOccupations(occupation25);
        
        company.addWarehouses(warehouse25);
        
        // Action: Find locations of "ToolW" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_productInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products.
        Warehouse warehouse26 = new Warehouse();
        warehouse26.setCity("CityS");
        warehouse26.setAddress("W26_addr");
        
        Warehouse warehouse27 = new Warehouse();
        warehouse27.setCity("CityT");
        warehouse27.setAddress("W27_addr");
        
        // Add different products to warehouses
        Product productA = new Product();
        productA.setName("ProductA");
        
        Product productB = new Product();
        productB.setName("ProductB");
        
        ProductOccupation occupation26 = new ProductOccupation();
        occupation26.setProduct(productA);
        warehouse26.addOccupations(occupation26);
        
        ProductOccupation occupation27 = new ProductOccupation();
        occupation27.setProduct(productB);
        warehouse27.addOccupations(occupation27);
        
        // SetUp: Warehouses are both in company C1.
        company.addWarehouses(warehouse26);
        company.addWarehouses(warehouse27);
        
        // Action: Find locations of "ItemV" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_productInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1.
        Warehouse warehouse28 = new Warehouse();
        warehouse28.setCity("CityP");
        warehouse28.setAddress("W28_addr");
        
        Warehouse warehouse29 = new Warehouse();
        warehouse29.setCity("CityQ");
        warehouse29.setAddress("W29_addr");
        
        // SetUp: Add Product "CommonPart" to both warehouses.
        Product commonPart = new Product();
        commonPart.setName("CommonPart");
        
        ProductOccupation occupation28 = new ProductOccupation();
        occupation28.setProduct(commonPart);
        warehouse28.addOccupations(occupation28);
        
        ProductOccupation occupation29 = new ProductOccupation();
        occupation29.setProduct(commonPart);
        warehouse29.addOccupations(occupation29);
        
        company.addWarehouses(warehouse28);
        company.addWarehouses(warehouse29);
        
        // Action: Find locations of "CommonPart" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        
        // Verify both locations exist (order may vary, so check both possibilities)
        boolean foundCityP = false;
        boolean foundCityQ = false;
        
        for (Map.Entry<String, String> location : locations) {
            if ("CityP".equals(location.getKey()) && "W28_addr".equals(location.getValue())) {
                foundCityP = true;
            }
            if ("CityQ".equals(location.getKey()) && "W29_addr".equals(location.getValue())) {
                foundCityQ = true;
            }
        }
        
        assertTrue("Should contain CityP with address W28_addr", foundCityP);
        assertTrue("Should contain CityQ with address W29_addr", foundCityQ);
    }
}