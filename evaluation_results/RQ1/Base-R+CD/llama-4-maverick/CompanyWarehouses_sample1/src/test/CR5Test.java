import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_productInMultipleWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1.
        Warehouse w22 = new Warehouse();
        w22.setCity("CityM");
        w22.setAddress("W22_addr");
        
        Warehouse w23 = new Warehouse();
        w23.setCity("CityN");
        w23.setAddress("W23_addr");
        
        company.addWarehouse(w22);
        company.addWarehouse(w23);
        
        // 2. Add Product "MaterialX" to both W22 and W23.
        Product materialX = new Product();
        materialX.setName("MaterialX");
        materialX.setSupplier(new Supplier());
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(materialX);
        occupation1.setVolume(10.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(materialX);
        occupation2.setVolume(15.0);
        
        w22.addOccupation(occupation1);
        w23.addOccupation(occupation2);
        
        // Action: Find locations of "MaterialX" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains(new AbstractMap.SimpleEntry<>("CityM", "W22_addr")));
        assertTrue(locations.contains(new AbstractMap.SimpleEntry<>("CityN", "W23_addr")));
    }
    
    @Test
    public void testCase2_productInSingleWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W24" in "CityO", in company C1.
        Warehouse w24 = new Warehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        
        company.addWarehouse(w24);
        
        // 2. Add Product "DeviceY" to W24.
        Product deviceY = new Product();
        deviceY.setName("DeviceY");
        deviceY.setSupplier(new Supplier());
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(deviceY);
        occupation.setVolume(20.0);
        
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
        Warehouse w25 = new Warehouse();
        w25.setCity("CityP");
        w25.setAddress("W25_addr");
        
        company.addWarehouse(w25);
        
        Product partZ = new Product();
        partZ.setName("PartZ");
        partZ.setSupplier(new Supplier());
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(partZ);
        occupation.setVolume(5.0);
        
        w25.addOccupation(occupation);
        
        // Action: Find locations of "ToolW" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_productInNoWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W26", "W27"] with other products. 
        Warehouse w26 = new Warehouse();
        w26.setCity("CityQ");
        w26.setAddress("W26_addr");
        
        Warehouse w27 = new Warehouse();
        w27.setCity("CityR");
        w27.setAddress("W27_addr");
        
        company.addWarehouse(w26);
        company.addWarehouse(w27);
        
        // Add other products to warehouses
        Product otherProduct1 = new Product();
        otherProduct1.setName("OtherProduct1");
        otherProduct1.setSupplier(new Supplier());
        
        Product otherProduct2 = new Product();
        otherProduct2.setName("OtherProduct2");
        otherProduct2.setSupplier(new Supplier());
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(otherProduct1);
        occupation1.setVolume(10.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(otherProduct2);
        occupation2.setVolume(15.0);
        
        w26.addOccupation(occupation1);
        w27.addOccupation(occupation2);
        
        // 2. Warehouses are both in company C1.
        // Action: Find locations of "ItemV" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_productInAllWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1.
        Warehouse w28 = new Warehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        
        Warehouse w29 = new Warehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        
        company.addWarehouse(w28);
        company.addWarehouse(w29);
        
        // 2. Add Product "CommonPart" to both warehouses.
        Product commonPart = new Product();
        commonPart.setName("CommonPart");
        commonPart.setSupplier(new Supplier());
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(commonPart);
        occupation1.setVolume(25.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(commonPart);
        occupation2.setVolume(30.0);
        
        w28.addOccupation(occupation1);
        w29.addOccupation(occupation2);
        
        // Action: Find locations of "CommonPart" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains(new AbstractMap.SimpleEntry<>("CityP", "W28_addr")));
        assertTrue(locations.contains(new AbstractMap.SimpleEntry<>("CityQ", "W29_addr")));
    }
}