import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR5Test {
    
    private Company company;
    private Warehouse w22, w23, w24, w25, w26, w27, w28, w29;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_productInMultipleWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1.
        w22 = new Warehouse();
        w22.setCity("CityM");
        w22.setAddress("W22_addr");
        
        w23 = new Warehouse();
        w23.setCity("CityN");
        w23.setAddress("W23_addr");
        
        company.addWarehouse(w22);
        company.addWarehouse(w23);
        
        // 2. Add Product "MaterialX" to both W22 and W23.
        Product materialX = new Product();
        materialX.setName("MaterialX");
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(materialX);
        w22.addOccupation(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(materialX);
        w23.addOccupation(occupation2);
        
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
    public void testCase2_productInSingleWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W24" in "CityO", in company C1.
        w24 = new Warehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        
        company.addWarehouse(w24);
        
        // 2. Add Product "DeviceY" to W24.
        Product deviceY = new Product();
        deviceY.setName("DeviceY");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(deviceY);
        w24.addOccupation(occupation);
        
        // Action: Find locations of "DeviceY" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        assertEquals("CityO", locations.get(0).getKey());
        assertEquals("W24_addr", locations.get(0).getValue());
    }
    
    @Test
    public void testCase3_nonExistentProduct() {
        // SetUp:
        // 1. Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
        w25 = new Warehouse();
        w25.setCity("CityR");
        w25.setAddress("W25_addr");
        
        company.addWarehouse(w25);
        
        Product partZ = new Product();
        partZ.setName("PartZ");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(partZ);
        w25.addOccupation(occupation);
        
        // Action: Find locations of "ToolW" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertEquals(0, locations.size());
    }
    
    @Test
    public void testCase4_productInNoWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W26", "W27"] with other products. 
        w26 = new Warehouse();
        w26.setCity("CityS");
        w26.setAddress("W26_addr");
        
        w27 = new Warehouse();
        w27.setCity("CityT");
        w27.setAddress("W27_addr");
        
        company.addWarehouse(w26);
        company.addWarehouse(w27);
        
        // Add other products to warehouses
        Product otherProduct = new Product();
        otherProduct.setName("OtherProduct");
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(otherProduct);
        w26.addOccupation(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(otherProduct);
        w27.addOccupation(occupation2);
        
        // 2. Warehouses are both in company C1.
        // Already added above
        
        // Action: Find locations of "ItemV" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertEquals(0, locations.size());
    }
    
    @Test
    public void testCase5_productInAllWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1.
        w28 = new Warehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        
        w29 = new Warehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        
        company.addWarehouse(w28);
        company.addWarehouse(w29);
        
        // 2. Add Product "CommonPart" to both warehouses.
        Product commonPart = new Product();
        commonPart.setName("CommonPart");
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(commonPart);
        w28.addOccupation(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(commonPart);
        w29.addOccupation(occupation2);
        
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