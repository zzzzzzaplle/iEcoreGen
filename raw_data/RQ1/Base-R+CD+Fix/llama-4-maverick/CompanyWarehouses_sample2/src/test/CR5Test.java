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
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        Warehouse w22 = new Warehouse();
        w22.setCity("CityM");
        w22.setAddress("W22_addr");
        
        Warehouse w23 = new Warehouse();
        w23.setCity("CityN");
        w23.setAddress("W23_addr");
        
        // Add Product "MaterialX" to both W22 and W23
        Supplier supplier1 = new Supplier();
        supplier1.setName("SupplierA");
        
        Product materialX = new Product();
        materialX.setName("MaterialX");
        materialX.setSupplier(supplier1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(materialX);
        occupation1.setVolume(100.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(materialX);
        occupation2.setVolume(150.0);
        
        w22.addOccupations(occupation1);
        w23.addOccupations(occupation2);
        
        company.addWarehouses(w22);
        company.addWarehouses(w23);
        
        // Action: Find locations of "MaterialX" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, locations.size());
        
        // Verify first location
        assertEquals("CityM", locations.get(0).getKey());
        assertEquals("W22_addr", locations.get(0).getValue());
        
        // Verify second location
        assertEquals("CityN", locations.get(1).getKey());
        assertEquals("W23_addr", locations.get(1).getValue());
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse w24 = new Warehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        
        // Add Product "DeviceY" to W24
        Supplier supplier1 = new Supplier();
        supplier1.setName("SupplierB");
        
        Product deviceY = new Product();
        deviceY.setName("DeviceY");
        deviceY.setSupplier(supplier1);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(deviceY);
        occupation.setVolume(200.0);
        
        w24.addOccupations(occupation);
        company.addWarehouses(w24);
        
        // Action: Find locations of "DeviceY" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        assertEquals("CityO", locations.get(0).getKey());
        assertEquals("W24_addr", locations.get(0).getValue());
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse w25 = new Warehouse();
        w25.setCity("CityR");
        w25.setAddress("W25_addr");
        
        Supplier supplier1 = new Supplier();
        supplier1.setName("SupplierC");
        
        Product partZ = new Product();
        partZ.setName("PartZ");
        partZ.setSupplier(supplier1);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(partZ);
        occupation.setVolume(300.0);
        
        w25.addOccupations(occupation);
        company.addWarehouses(w25);
        
        // Action: Find locations of "ToolW" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse w26 = new Warehouse();
        w26.setCity("CityS");
        w26.setAddress("W26_addr");
        
        Warehouse w27 = new Warehouse();
        w27.setCity("CityT");
        w27.setAddress("W27_addr");
        
        // Add different products to warehouses
        Supplier supplier1 = new Supplier();
        supplier1.setName("SupplierD");
        
        Product productA = new Product();
        productA.setName("ProductA");
        productA.setSupplier(supplier1);
        
        Product productB = new Product();
        productB.setName("ProductB");
        productB.setSupplier(supplier1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productA);
        occupation1.setVolume(400.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productB);
        occupation2.setVolume(500.0);
        
        w26.addOccupations(occupation1);
        w27.addOccupations(occupation2);
        
        company.addWarehouses(w26);
        company.addWarehouses(w27);
        
        // Action: Find locations of "ItemV" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse w28 = new Warehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        
        Warehouse w29 = new Warehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        
        // Add Product "CommonPart" to both warehouses
        Supplier supplier1 = new Supplier();
        supplier1.setName("SupplierE");
        
        Product commonPart = new Product();
        commonPart.setName("CommonPart");
        commonPart.setSupplier(supplier1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(commonPart);
        occupation1.setVolume(600.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(commonPart);
        occupation2.setVolume(700.0);
        
        w28.addOccupations(occupation1);
        w29.addOccupations(occupation2);
        
        company.addWarehouses(w28);
        company.addWarehouses(w29);
        
        // Action: Find locations of "CommonPart" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        
        // Verify first location
        assertEquals("CityP", locations.get(0).getKey());
        assertEquals("W28_addr", locations.get(0).getValue());
        
        // Verify second location
        assertEquals("CityQ", locations.get(1).getKey());
        assertEquals("W29_addr", locations.get(1).getValue());
    }
}