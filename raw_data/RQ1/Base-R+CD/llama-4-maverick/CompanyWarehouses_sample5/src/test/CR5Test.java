import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;

public class CR5Test {
    
    private Company company;
    private Warehouse w22, w23, w24, w25, w26, w27, w28, w29;
    private Product materialX, deviceY, partZ, toolW, itemV, commonPart;
    private Supplier supplier1, supplier2, supplier3;
    
    @Before
    public void setUp() {
        // Initialize company
        company = new Company();
        
        // Initialize suppliers
        supplier1 = new Supplier();
        supplier1.setName("Supplier1");
        supplier1.setAddress("Supplier1 Address");
        
        supplier2 = new Supplier();
        supplier2.setName("Supplier2");
        supplier2.setAddress("Supplier2 Address");
        
        supplier3 = new Supplier();
        supplier3.setName("Supplier3");
        supplier3.setAddress("Supplier3 Address");
        
        // Initialize products
        materialX = new Product();
        materialX.setName("MaterialX");
        materialX.setToxic(false);
        materialX.setSupplier(supplier1);
        
        deviceY = new Product();
        deviceY.setName("DeviceY");
        deviceY.setToxic(true);
        deviceY.setSupplier(supplier2);
        
        partZ = new Product();
        partZ.setName("PartZ");
        partZ.setToxic(false);
        partZ.setSupplier(supplier1);
        
        toolW = new Product();
        toolW.setName("ToolW");
        toolW.setToxic(false);
        toolW.setSupplier(supplier2);
        
        itemV = new Product();
        itemV.setName("ItemV");
        itemV.setToxic(true);
        itemV.setSupplier(supplier3);
        
        commonPart = new Product();
        commonPart.setName("CommonPart");
        commonPart.setToxic(false);
        commonPart.setSupplier(supplier1);
        
        // Initialize warehouses
        w22 = new Warehouse();
        w22.setCity("CityM");
        w22.setAddress("W22_addr");
        w22.setSurface(1000.0);
        
        w23 = new Warehouse();
        w23.setCity("CityN");
        w23.setAddress("W23_addr");
        w23.setSurface(1200.0);
        
        w24 = new Warehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        w24.setSurface(800.0);
        
        w25 = new Warehouse();
        w25.setCity("CityP");
        w25.setAddress("W25_addr");
        w25.setSurface(900.0);
        
        w26 = new Warehouse();
        w26.setCity("CityQ");
        w26.setAddress("W26_addr");
        w26.setSurface(1100.0);
        
        w27 = new Warehouse();
        w27.setCity("CityR");
        w27.setAddress("W27_addr");
        w27.setSurface(1300.0);
        
        w28 = new Warehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        w28.setSurface(1500.0);
        
        w29 = new Warehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        w29.setSurface(1600.0);
    }
    
    @Test
    public void testCase1_productInMultipleWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1.
        company.addWarehouses(w22);
        company.addWarehouses(w23);
        
        // 2. Add Product "MaterialX" to both W22 and W23.
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(materialX);
        po1.setVolume(100.0);
        w22.addOccupations(po1);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(materialX);
        po2.setVolume(150.0);
        w23.addOccupations(po2);
        
        // Action: Find locations of "MaterialX" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, result.size());
        assertTrue(result.contains(new AbstractMap.SimpleEntry<>("CityM", "W22_addr")));
        assertTrue(result.contains(new AbstractMap.SimpleEntry<>("CityN", "W23_addr")));
    }
    
    @Test
    public void testCase2_productInSingleWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W24" in "CityO", in company C1.
        company.addWarehouses(w24);
        
        // 2. Add Product "DeviceY" to W24.
        ProductOccupation po = new ProductOccupation();
        po.setProduct(deviceY);
        po.setVolume(200.0);
        w24.addOccupations(po);
        
        // Action: Find locations of "DeviceY" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, result.size());
        assertTrue(result.contains(new AbstractMap.SimpleEntry<>("CityO", "W24_addr")));
    }
    
    @Test
    public void testCase3_nonExistentProduct() {
        // SetUp:
        // 1. Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
        company.addWarehouses(w25);
        
        ProductOccupation po = new ProductOccupation();
        po.setProduct(partZ);
        po.setVolume(50.0);
        w25.addOccupations(po);
        
        // Action: Find locations of "ToolW" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_productInNoWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W26", "W27"] with other products. 
        company.addWarehouses(w26);
        company.addWarehouses(w27);
        
        // Add other products to warehouses
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(partZ);
        po1.setVolume(75.0);
        w26.addOccupations(po1);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(deviceY);
        po2.setVolume(125.0);
        w27.addOccupations(po2);
        
        // 2. Warehouses are both in company C1.
        // Already added above
        
        // Action: Find locations of "ItemV" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_productInAllWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1.
        company.addWarehouses(w28);
        company.addWarehouses(w29);
        
        // 2. Add Product "CommonPart" to both warehouses.
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(commonPart);
        po1.setVolume(300.0);
        w28.addOccupations(po1);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(commonPart);
        po2.setVolume(350.0);
        w29.addOccupations(po2);
        
        // Action: Find locations of "CommonPart" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, result.size());
        assertTrue(result.contains(new AbstractMap.SimpleEntry<>("CityP", "W28_addr")));
        assertTrue(result.contains(new AbstractMap.SimpleEntry<>("CityQ", "W29_addr")));
    }
}