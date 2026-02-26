import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        Warehouse warehouse22 = new Warehouse("CityM", "W22_addr", 100.0);
        Warehouse warehouse23 = new Warehouse("CityN", "W23_addr", 100.0);
        
        // Add Product "MaterialX" to both W22 and W23
        Product materialX = new Product("MaterialX", new Supplier("SupplierA", "AddrA"), false, 10.0);
        warehouse22.addProduct(materialX);
        warehouse23.addProduct(materialX);
        
        company.addWarehouse(warehouse22);
        company.addWarehouse(warehouse23);
        
        // Action: Find locations of "MaterialX" in C1
        List<String> result = company.verifyProductLocations("MaterialX");
        
        // Expected Output: ["CityM, W22_addr", "CityN, W23_addr"]
        List<String> expected = new ArrayList<>();
        expected.add("CityM, W22_addr");
        expected.add("CityN, W23_addr");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse24 = new Warehouse("CityO", "W24_addr", 100.0);
        
        // Add Product "DeviceY" to W24
        Product deviceY = new Product("DeviceY", new Supplier("SupplierB", "AddrB"), false, 15.0);
        warehouse24.addProduct(deviceY);
        
        company.addWarehouse(warehouse24);
        
        // Action: Find locations of "DeviceY" in C1
        List<String> result = company.verifyProductLocations("DeviceY");
        
        // Expected Output: ["CityO, W24_addr"]
        List<String> expected = new ArrayList<>();
        expected.add("CityO, W24_addr");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse25 = new Warehouse("CityR", "W25_addr", 100.0);
        Product partZ = new Product("PartZ", new Supplier("SupplierC", "AddrC"), false, 20.0);
        warehouse25.addProduct(partZ);
        
        company.addWarehouse(warehouse25);
        
        // Action: Find locations of "ToolW" in C1
        List<String> result = company.verifyProductLocations("ToolW");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse26 = new Warehouse("CityS", "W26_addr", 100.0);
        Warehouse warehouse27 = new Warehouse("CityT", "W27_addr", 100.0);
        
        // Add other products to warehouses
        Product partA = new Product("PartA", new Supplier("SupplierD", "AddrD"), false, 25.0);
        Product partB = new Product("PartB", new Supplier("SupplierE", "AddrE"), false, 30.0);
        
        warehouse26.addProduct(partA);
        warehouse27.addProduct(partB);
        
        company.addWarehouse(warehouse26);
        company.addWarehouse(warehouse27);
        
        // Action: Find locations of "ItemV" in C1
        List<String> result = company.verifyProductLocations("ItemV");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse warehouse28 = new Warehouse("CityP", "W28_addr", 100.0);
        Warehouse warehouse29 = new Warehouse("CityQ", "W29_addr", 100.0);
        
        // Add Product "CommonPart" to both warehouses
        Product commonPart = new Product("CommonPart", new Supplier("SupplierF", "AddrF"), false, 35.0);
        warehouse28.addProduct(commonPart);
        warehouse29.addProduct(commonPart);
        
        company.addWarehouse(warehouse28);
        company.addWarehouse(warehouse29);
        
        // Action: Find locations of "CommonPart" in C1
        List<String> result = company.verifyProductLocations("CommonPart");
        
        // Expected Output: ["CityP, W28_addr", "CityQ, W29_addr"]
        List<String> expected = new ArrayList<>();
        expected.add("CityP, W28_addr");
        expected.add("CityQ, W29_addr");
        
        assertEquals(expected, result);
    }
}