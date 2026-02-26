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
        
        company.addWarehouses(w22);
        company.addWarehouses(w23);
        
        // 2. Add Product "MaterialX" to both W22 and W23.
        Product materialX = new Product();
        materialX.setName("MaterialX");
        
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(materialX);
        w22.addOccupations(po1);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(materialX);
        w23.addOccupations(po2);
        
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
        company.addWarehouses(w24);
        
        // 2. Add Product "DeviceY" to W24.
        Product deviceY = new Product();
        deviceY.setName("DeviceY");
        
        ProductOccupation po = new ProductOccupation();
        po.setProduct(deviceY);
        w24.addOccupations(po);
        
        // Action: Find locations of "DeviceY" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        assertTrue(locations.contains(new AbstractMap.SimpleEntry<>("CityO", "W24_addr")));
    }
    
    @Test
    public void testCase3_nonExistentProduct() {
        // SetUp:
        // 1. Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
        Warehouse w25 = new Warehouse();
        w25.setCity("SomeCity");
        w25.setAddress("W25_addr");
        company.addWarehouses(w25);
        
        Product partZ = new Product();
        partZ.setName("PartZ");
        
        ProductOccupation po = new ProductOccupation();
        po.setProduct(partZ);
        w25.addOccupations(po);
        
        // Action: Find locations of "ToolW" in C1.
        List<Map.Entry<String, String>> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertEquals(0, locations.size());
    }
    
    @Test
    public void testCase4_productInNoWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W26", "W27"] with other products. 
        Warehouse w26 = new Warehouse();
        w26.setCity("CityA");
        w26.setAddress("W26_addr");
        
        Warehouse w27 = new Warehouse();
        w27.setCity("CityB");
        w27.setAddress("W27_addr");
        
        company.addWarehouses(w26);
        company.addWarehouses(w27);
        
        // Add other products to warehouses
        Product otherProduct1 = new Product();
        otherProduct1.setName("OtherProduct1");
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(otherProduct1);
        w26.addOccupations(po1);
        
        Product otherProduct2 = new Product();
        otherProduct2.setName("OtherProduct2");
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(otherProduct2);
        w27.addOccupations(po2);
        
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
        Warehouse w28 = new Warehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        
        Warehouse w29 = new Warehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        
        company.addWarehouses(w28);
        company.addWarehouses(w29);
        
        // 2. Add Product "CommonPart" to both warehouses.
        Product commonPart = new Product();
        commonPart.setName("CommonPart");
        
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(commonPart);
        w28.addOccupations(po1);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(commonPart);
        w29.addOccupations(po2);
        
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