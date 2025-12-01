import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1.
        Warehouse w22 = new Warehouse();
        w22.setCity("CityM");
        w22.setAddress("W22_addr");
        
        Warehouse w23 = new Warehouse();
        w23.setCity("CityN");
        w23.setAddress("W23_addr");
        
        // Add Product "MaterialX" to both W22 and W23.
        Product materialX = new Product();
        materialX.setName("MaterialX");
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(materialX);
        w22.addOccupation(occ1);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(materialX);
        w23.addOccupation(occ2);
        
        company.addWarehouse(w22);
        company.addWarehouse(w23);
        
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
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1.
        Warehouse w24 = new Warehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        
        // Add Product "DeviceY" to W24.
        Product deviceY = new Product();
        deviceY.setName("DeviceY");
        
        ProductOccupation occ = new ProductOccupation();
        occ.setProduct(deviceY);
        w24.addOccupation(occ);
        
        company.addWarehouse(w24);
        
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
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
        Warehouse w25 = new Warehouse();
        w25.setCity("CityR");
        w25.setAddress("W25_addr");
        
        Product partZ = new Product();
        partZ.setName("PartZ");
        
        ProductOccupation occ = new ProductOccupation();
        occ.setProduct(partZ);
        w25.addOccupation(occ);
        
        company.addWarehouse(w25);
        
        // Action: Find locations of "ToolW" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products.
        Warehouse w26 = new Warehouse();
        w26.setCity("CityS");
        w26.setAddress("W26_addr");
        
        Warehouse w27 = new Warehouse();
        w27.setCity("CityT");
        w27.setAddress("W27_addr");
        
        // Add other products to warehouses
        Product otherProduct1 = new Product();
        otherProduct1.setName("OtherProduct1");
        
        Product otherProduct2 = new Product();
        otherProduct2.setName("OtherProduct2");
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(otherProduct1);
        w26.addOccupation(occ1);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(otherProduct2);
        w27.addOccupation(occ2);
        
        // Warehouses are both in company C1.
        company.addWarehouse(w26);
        company.addWarehouse(w27);
        
        // Action: Find locations of "ItemV" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1.
        Warehouse w28 = new Warehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        
        Warehouse w29 = new Warehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        
        // Add Product "CommonPart" to both warehouses.
        Product commonPart = new Product();
        commonPart.setName("CommonPart");
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(commonPart);
        w28.addOccupation(occ1);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(commonPart);
        w29.addOccupation(occ2);
        
        company.addWarehouse(w28);
        company.addWarehouse(w29);
        
        // Action: Find locations of "CommonPart" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        
        // Verify both locations (order may vary, so check both possibilities)
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