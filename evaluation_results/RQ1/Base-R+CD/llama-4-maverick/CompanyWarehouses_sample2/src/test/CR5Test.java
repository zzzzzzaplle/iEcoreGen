import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

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
        
        // 2. Add Product "MaterialX" to both W22 and W23.
        Product materialX = new Product();
        materialX.setName("MaterialX");
        materialX.setSupplier(new Supplier()); // Required to avoid NullPointerException
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(materialX);
        occupation1.setVolume(10.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(materialX);
        occupation2.setVolume(15.0);
        
        w22.addOccupations(occupation1);
        w23.addOccupations(occupation2);
        
        company.addWarehouses(w22);
        company.addWarehouses(w23);
        
        // Action: Find locations of "MaterialX" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, result.size());
        assertTrue(result.contains(new SimpleEntry<>("CityM", "W22_addr")));
        assertTrue(result.contains(new SimpleEntry<>("CityN", "W23_addr")));
    }
    
    @Test
    public void testCase2_productInSingleWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W24" in "CityO", in company C1.
        Warehouse w24 = new Warehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        
        // 2. Add Product "DeviceY" to W24.
        Product deviceY = new Product();
        deviceY.setName("DeviceY");
        deviceY.setSupplier(new Supplier()); // Required to avoid NullPointerException
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(deviceY);
        occupation.setVolume(20.0);
        
        w24.addOccupations(occupation);
        company.addWarehouses(w24);
        
        // Action: Find locations of "DeviceY" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, result.size());
        assertEquals(new SimpleEntry<>("CityO", "W24_addr"), result.get(0));
    }
    
    @Test
    public void testCase3_nonExistentProduct() {
        // SetUp:
        // 1. Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
        Warehouse w25 = new Warehouse();
        w25.setCity("CityP");
        w25.setAddress("W25_addr");
        
        Product partZ = new Product();
        partZ.setName("PartZ");
        partZ.setSupplier(new Supplier()); // Required to avoid NullPointerException
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(partZ);
        occupation.setVolume(5.0);
        
        w25.addOccupations(occupation);
        company.addWarehouses(w25);
        
        // Action: Find locations of "ToolW" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
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
        
        // Adding other products to warehouses
        Product otherProduct = new Product();
        otherProduct.setName("OtherProduct");
        otherProduct.setSupplier(new Supplier()); // Required to avoid NullPointerException
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(otherProduct);
        occupation1.setVolume(10.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(otherProduct);
        occupation2.setVolume(15.0);
        
        w26.addOccupations(occupation1);
        w27.addOccupations(occupation2);
        
        company.addWarehouses(w26);
        company.addWarehouses(w27);
        
        // 2. Warehouses are both in company C1.
        // Action: Find locations of "ItemV" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
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
        
        // 2. Add Product "CommonPart" to both warehouses.
        Product commonPart = new Product();
        commonPart.setName("CommonPart");
        commonPart.setSupplier(new Supplier()); // Required to avoid NullPointerException
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(commonPart);
        occupation1.setVolume(25.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(commonPart);
        occupation2.setVolume(30.0);
        
        w28.addOccupations(occupation1);
        w29.addOccupations(occupation2);
        
        company.addWarehouses(w28);
        company.addWarehouses(w29);
        
        // Action: Find locations of "CommonPart" in C1.
        List<Map.Entry<String, String>> result = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, result.size());
        assertTrue(result.contains(new SimpleEntry<>("CityP", "W28_addr")));
        assertTrue(result.contains(new SimpleEntry<>("CityQ", "W29_addr")));
    }
}