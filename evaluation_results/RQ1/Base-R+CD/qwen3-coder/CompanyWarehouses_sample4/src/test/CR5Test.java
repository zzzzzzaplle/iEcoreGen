import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR5Test {
    
    private Company company;
    private Warehouse w22, w23, w24, w25, w26, w27, w28, w29;
    private Product materialX, deviceY, partZ, otherProduct1, otherProduct2, commonPart;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create products
        materialX = new Product();
        materialX.setName("MaterialX");
        
        deviceY = new Product();
        deviceY.setName("DeviceY");
        
        partZ = new Product();
        partZ.setName("PartZ");
        
        commonPart = new Product();
        commonPart.setName("CommonPart");
        
        otherProduct1 = new Product();
        otherProduct1.setName("OtherProduct1");
        
        otherProduct2 = new Product();
        otherProduct2.setName("OtherProduct2");
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1.
        w22 = new Warehouse();
        w22.setCity("CityM");
        w22.setAddress("W22_addr");
        
        w23 = new Warehouse();
        w23.setCity("CityN");
        w23.setAddress("W23_addr");
        
        company.addWarehouses(w22);
        company.addWarehouses(w23);
        
        // 2. Add Product "MaterialX" to both W22 and W23.
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(materialX);
        occupation1.setVolume(100.0);
        w22.addOccupations(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(materialX);
        occupation2.setVolume(150.0);
        w23.addOccupations(occupation2);
        
        // Action: Find locations of "MaterialX" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, locations.size());
        
        Map<String, String> locationMap = new HashMap<>();
        for (Map.Entry<String, String> entry : locations) {
            locationMap.put(entry.getKey(), entry.getValue());
        }
        
        assertTrue(locationMap.containsKey("CityM"));
        assertEquals("W22_addr", locationMap.get("CityM"));
        assertTrue(locationMap.containsKey("CityN"));
        assertEquals("W23_addr", locationMap.get("CityN"));
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W24" in "CityO", in company C1.
        w24 = new Warehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        
        company.addWarehouses(w24);
        
        // 2. Add Product "DeviceY" to W24.
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(deviceY);
        occupation.setVolume(200.0);
        w24.addOccupations(occupation);
        
        // Action: Find locations of "DeviceY" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        assertEquals("CityO", locations.get(0).getKey());
        assertEquals("W24_addr", locations.get(0).getValue());
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp:
        // 1. Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
        w25 = new Warehouse();
        w25.setCity("CityP");
        w25.setAddress("W25_addr");
        
        company.addWarehouses(w25);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(partZ);
        occupation.setVolume(50.0);
        w25.addOccupations(occupation);
        
        // Action: Find locations of "ToolW" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W26", "W27"] with other products. 
        // 2. Warehouses are both in company C1.
        w26 = new Warehouse();
        w26.setCity("CityQ");
        w26.setAddress("W26_addr");
        
        w27 = new Warehouse();
        w27.setCity("CityR");
        w27.setAddress("W27_addr");
        
        company.addWarehouses(w26);
        company.addWarehouses(w27);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(otherProduct1);
        occupation1.setVolume(75.0);
        w26.addOccupations(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(otherProduct2);
        occupation2.setVolume(125.0);
        w27.addOccupations(occupation2);
        
        // Action: Find locations of "ItemV" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1.
        w28 = new Warehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        
        w29 = new Warehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        
        company.addWarehouses(w28);
        company.addWarehouses(w29);
        
        // 2. Add Product "CommonPart" to both warehouses.
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(commonPart);
        occupation1.setVolume(300.0);
        w28.addOccupations(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(commonPart);
        occupation2.setVolume(400.0);
        w29.addOccupations(occupation2);
        
        // Action: Find locations of "CommonPart" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        
        Map<String, String> locationMap = new HashMap<>();
        for (Map.Entry<String, String> entry : locations) {
            locationMap.put(entry.getKey(), entry.getValue());
        }
        
        assertTrue(locationMap.containsKey("CityP"));
        assertEquals("W28_addr", locationMap.get("CityP"));
        assertTrue(locationMap.containsKey("CityQ"));
        assertEquals("W29_addr", locationMap.get("CityQ"));
    }
}