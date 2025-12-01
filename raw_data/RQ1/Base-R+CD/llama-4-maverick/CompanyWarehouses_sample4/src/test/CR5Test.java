import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CR5Test {
    
    private Company company;
    private Warehouse w22, w23, w24, w25, w26, w27, w28, w29;
    private Product materialX, deviceY, partZ, toolW, itemV, commonPart;
    
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
        
        toolW = new Product();
        toolW.setName("ToolW");
        
        itemV = new Product();
        itemV.setName("ItemV");
        
        commonPart = new Product();
        commonPart.setName("CommonPart");
        
        // Create suppliers
        Supplier supplier = new Supplier();
        supplier.setName("SupplierA");
        
        // Set suppliers to products
        materialX.setSupplier(supplier);
        deviceY.setSupplier(supplier);
        partZ.setSupplier(supplier);
        toolW.setSupplier(supplier);
        itemV.setSupplier(supplier);
        commonPart.setSupplier(supplier);
        
        // Create warehouses for test cases
        w22 = new Warehouse();
        w22.setCity("CityM");
        w22.setAddress("W22_addr");
        
        w23 = new Warehouse();
        w23.setCity("CityN");
        w23.setAddress("W23_addr");
        
        w24 = new Warehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        
        w25 = new Warehouse();
        w25.setCity("CityP");
        w25.setAddress("W25_addr");
        
        w26 = new Warehouse();
        w26.setCity("CityQ");
        w26.setAddress("W26_addr");
        
        w27 = new Warehouse();
        w27.setCity("CityR");
        w27.setAddress("W27_addr");
        
        w28 = new Warehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        
        w29 = new Warehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
    }
    
    @Test
    public void testCase1_productInMultipleWarehouses() {
        // Setup: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(materialX);
        occupation1.setVolume(100.0);
        w22.addOccupation(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(materialX);
        occupation2.setVolume(150.0);
        w23.addOccupation(occupation2);
        
        company.addWarehouse(w22);
        company.addWarehouse(w23);
        
        // Action: Find locations of "MaterialX" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, result.size());
        assertTrue(result.contains(Map.entry("CityM", "W22_addr")));
        assertTrue(result.contains(Map.entry("CityN", "W23_addr")));
    }
    
    @Test
    public void testCase2_productInSingleWarehouse() {
        // Setup: Create Warehouse "W24" in "CityO", in company C1
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(deviceY);
        occupation.setVolume(200.0);
        w24.addOccupation(occupation);
        
        company.addWarehouse(w24);
        
        // Action: Find locations of "DeviceY" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, result.size());
        assertTrue(result.contains(Map.entry("CityO", "W24_addr")));
    }
    
    @Test
    public void testCase3_nonExistentProduct() {
        // Setup: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(partZ);
        occupation.setVolume(50.0);
        w25.addOccupation(occupation);
        
        company.addWarehouse(w25);
        
        // Action: Find locations of "ToolW" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_productInNoWarehouses() {
        // Setup: Create Warehouses ["W26", "W27"] with other products
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(partZ);
        occupation1.setVolume(75.0);
        w26.addOccupation(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(deviceY);
        occupation2.setVolume(125.0);
        w27.addOccupation(occupation2);
        
        company.addWarehouse(w26);
        company.addWarehouse(w27);
        
        // Action: Find locations of "ItemV" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_productInAllWarehouses() {
        // Setup: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(commonPart);
        occupation1.setVolume(300.0);
        w28.addOccupation(occupation1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(commonPart);
        occupation2.setVolume(400.0);
        w29.addOccupation(occupation2);
        
        company.addWarehouse(w28);
        company.addWarehouse(w29);
        
        // Action: Find locations of "CommonPart" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, result.size());
        assertTrue(result.contains(Map.entry("CityP", "W28_addr")));
        assertTrue(result.contains(Map.entry("CityQ", "W29_addr")));
    }
}