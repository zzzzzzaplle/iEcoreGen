import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class CR5Test {
    private Company company;
    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private Warehouse warehouse3;
    private Warehouse warehouse4;
    private Warehouse warehouse5;
    private Product productMaterialX;
    private Product productDeviceY;
    private Product productPartZ;
    private Product productCommonPart;
    private Supplier supplier1;
    private Supplier supplier2;
    private ProductOccupation occupation1;
    private ProductOccupation occupation2;
    private ProductOccupation occupation3;
    private ProductOccupation occupation4;
    private ProductOccupation occupation5;
    private ProductOccupation occupation6;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Create suppliers
        supplier1 = new Supplier();
        supplier1.setName("SupplierA");
        supplier1.setAddress("SupplierA_Address");
        
        supplier2 = new Supplier();
        supplier2.setName("SupplierB");
        supplier2.setAddress("SupplierB_Address");
        
        // Create products
        productMaterialX = new Product();
        productMaterialX.setName("MaterialX");
        productMaterialX.setToxic(false);
        productMaterialX.setSupplier(supplier1);
        
        productDeviceY = new Product();
        productDeviceY.setName("DeviceY");
        productDeviceY.setToxic(false);
        productDeviceY.setSupplier(supplier1);
        
        productPartZ = new Product();
        productPartZ.setName("PartZ");
        productPartZ.setToxic(false);
        productPartZ.setSupplier(supplier2);
        
        productCommonPart = new Product();
        productCommonPart.setName("CommonPart");
        productCommonPart.setToxic(false);
        productCommonPart.setSupplier(supplier1);
        
        // Create product occupations
        occupation1 = new ProductOccupation();
        occupation1.setProduct(productMaterialX);
        occupation1.setVolume(100.0);
        
        occupation2 = new ProductOccupation();
        occupation2.setProduct(productMaterialX);
        occupation2.setVolume(150.0);
        
        occupation3 = new ProductOccupation();
        occupation3.setProduct(productDeviceY);
        occupation3.setVolume(200.0);
        
        occupation4 = new ProductOccupation();
        occupation4.setProduct(productPartZ);
        occupation4.setVolume(250.0);
        
        occupation5 = new ProductOccupation();
        occupation5.setProduct(productCommonPart);
        occupation5.setVolume(300.0);
        
        occupation6 = new ProductOccupation();
        occupation6.setProduct(productCommonPart);
        occupation6.setVolume(350.0);
        
        // Create warehouses
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityM");
        warehouse1.setAddress("W22_addr");
        warehouse1.setSurface(1000.0);
        warehouse1.addOccupation(occupation1);
        
        warehouse2 = new Warehouse();
        warehouse2.setCity("CityN");
        warehouse2.setAddress("W23_addr");
        warehouse2.setSurface(1200.0);
        warehouse2.addOccupation(occupation2);
        
        warehouse3 = new Warehouse();
        warehouse3.setCity("CityO");
        warehouse3.setAddress("W24_addr");
        warehouse3.setSurface(1400.0);
        warehouse3.addOccupation(occupation3);
        
        warehouse4 = new Warehouse();
        warehouse4.setCity("CityP");
        warehouse4.setAddress("W25_addr");
        warehouse4.setSurface(1600.0);
        warehouse4.addOccupation(occupation4);
        
        warehouse5 = new Warehouse();
        warehouse5.setCity("CityQ");
        warehouse5.setAddress("W26_addr");
        warehouse5.setSurface(1800.0);
        
        // Add another product to warehouse5 for test case 4
        Product otherProduct1 = new Product();
        otherProduct1.setName("OtherProduct1");
        otherProduct1.setToxic(false);
        otherProduct1.setSupplier(supplier2);
        
        ProductOccupation otherOccupation1 = new ProductOccupation();
        otherOccupation1.setProduct(otherProduct1);
        otherOccupation1.setVolume(400.0);
        warehouse5.addOccupation(otherOccupation1);
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // Setup: Create warehouses and add product to both
        Company c1 = new Company();
        Warehouse w22 = new Warehouse();
        w22.setCity("CityM");
        w22.setAddress("W22_addr");
        
        Warehouse w23 = new Warehouse();
        w23.setCity("CityN");
        w23.setAddress("W23_addr");
        
        // Create product MaterialX
        Product materialX = new Product();
        materialX.setName("MaterialX");
        materialX.setToxic(false);
        
        // Create product occupations
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(materialX);
        po1.setVolume(100.0);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(materialX);
        po2.setVolume(150.0);
        
        // Add occupations to warehouses
        w22.addOccupation(po1);
        w23.addOccupation(po2);
        
        // Add warehouses to company
        c1.addWarehouse(w22);
        c1.addWarehouse(w23);
        
        // Action: Find locations of MaterialX
        List<Map.Entry<String, String>> locations = c1.findProductLocations("MaterialX");
        
        // Expected Output: Two locations with specified city and address
        assertEquals("Should find 2 locations", 2, locations.size());
        
        // Verify first location
        assertEquals("City should be CityM", "CityM", locations.get(0).getKey());
        assertEquals("Address should be W22_addr", "W22_addr", locations.get(0).getValue());
        
        // Verify second location
        assertEquals("City should be CityN", "CityN", locations.get(1).getKey());
        assertEquals("Address should be W23_addr", "W23_addr", locations.get(1).getValue());
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // Setup: Create warehouse and add product
        Company c1 = new Company();
        Warehouse w24 = new Warehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        
        // Create product DeviceY
        Product deviceY = new Product();
        deviceY.setName("DeviceY");
        deviceY.setToxic(false);
        
        // Create product occupation
        ProductOccupation po = new ProductOccupation();
        po.setProduct(deviceY);
        po.setVolume(200.0);
        
        // Add occupation to warehouse
        w24.addOccupation(po);
        
        // Add warehouse to company
        c1.addWarehouse(w24);
        
        // Action: Find locations of DeviceY
        List<Map.Entry<String, String>> locations = c1.findProductLocations("DeviceY");
        
        // Expected Output: One location with specified city and address
        assertEquals("Should find 1 location", 1, locations.size());
        
        // Verify the location
        assertEquals("City should be CityO", "CityO", locations.get(0).getKey());
        assertEquals("Address should be W24_addr", "W24_addr", locations.get(0).getValue());
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // Setup: Create warehouse with different product
        Company c1 = new Company();
        Warehouse w25 = new Warehouse();
        w25.setCity("CityP");
        w25.setAddress("W25_addr");
        
        // Create product PartZ
        Product partZ = new Product();
        partZ.setName("PartZ");
        partZ.setToxic(false);
        
        // Create product occupation
        ProductOccupation po = new ProductOccupation();
        po.setProduct(partZ);
        po.setVolume(250.0);
        
        // Add occupation to warehouse
        w25.addOccupation(po);
        
        // Add warehouse to company
        c1.addWarehouse(w25);
        
        // Action: Find locations of non-existent product ToolW
        List<Map.Entry<String, String>> locations = c1.findProductLocations("ToolW");
        
        // Expected Output: Empty list
        assertTrue("Should return empty list", locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // Setup: Create warehouses with other products
        Company c1 = new Company();
        Warehouse w26 = new Warehouse();
        w26.setCity("CityR");
        w26.setAddress("W26_addr");
        
        Warehouse w27 = new Warehouse();
        w27.setCity("CityS");
        w27.setAddress("W27_addr");
        
        // Create other products
        Product otherProduct1 = new Product();
        otherProduct1.setName("OtherProduct1");
        otherProduct1.setToxic(false);
        
        Product otherProduct2 = new Product();
        otherProduct2.setName("OtherProduct2");
        otherProduct2.setToxic(false);
        
        // Create product occupations
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(otherProduct1);
        po1.setVolume(300.0);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(otherProduct2);
        po2.setVolume(350.0);
        
        // Add occupations to warehouses
        w26.addOccupation(po1);
        w27.addOccupation(po2);
        
        // Add warehouses to company
        c1.addWarehouse(w26);
        c1.addWarehouse(w27);
        
        // Action: Find locations of ItemV (which doesn't exist)
        List<Map.Entry<String, String>> locations = c1.findProductLocations("ItemV");
        
        // Expected Output: Empty list
        assertTrue("Should return empty list", locations.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // Setup: Create warehouses and add same product to both
        Company c1 = new Company();
        Warehouse w28 = new Warehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        
        Warehouse w29 = new Warehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        
        // Create product CommonPart
        Product commonPart = new Product();
        commonPart.setName("CommonPart");
        commonPart.setToxic(false);
        
        // Create product occupations
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(commonPart);
        po1.setVolume(400.0);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(commonPart);
        po2.setVolume(450.0);
        
        // Add occupations to warehouses
        w28.addOccupation(po1);
        w29.addOccupation(po2);
        
        // Add warehouses to company
        c1.addWarehouse(w28);
        c1.addWarehouse(w29);
        
        // Action: Find locations of CommonPart
        List<Map.Entry<String, String>> locations = c1.findProductLocations("CommonPart");
        
        // Expected Output: Two locations with specified cities and addresses
        assertEquals("Should find 2 locations", 2, locations.size());
        
        // Verify first location
        assertEquals("City should be CityP", "CityP", locations.get(0).getKey());
        assertEquals("Address should be W28_addr", "W28_addr", locations.get(0).getValue());
        
        // Verify second location
        assertEquals("City should be CityQ", "CityQ", locations.get(1).getKey());
        assertEquals("Address should be W29_addr", "W29_addr", locations.get(1).getValue());
    }
}