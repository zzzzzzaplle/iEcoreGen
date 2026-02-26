import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        Warehouse w22 = new Warehouse(100.0, "W22_addr", "CityM");
        Warehouse w23 = new Warehouse(150.0, "W23_addr", "CityN");
        
        // Add Product "MaterialX" to both W22 and W23
        Supplier supplier1 = new Supplier("SupplierA", "AddressA");
        Product materialX1 = new Product("MaterialX", false, 10.0, supplier1);
        Product materialX2 = new Product("MaterialX", false, 15.0, supplier1);
        
        w22.addProduct(materialX1);
        w23.addProduct(materialX2);
        
        company.addWarehouse(w22);
        company.addWarehouse(w23);
        
        // Action: Find locations of "MaterialX" in C1
        List<String> locations = company.getProductLocations("MaterialX");
        
        // Expected Output: "city":"CityM","address":"W22_addr" and "city":"CityN","address":"W23_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains("CityM - W22_addr"));
        assertTrue(locations.contains("CityN - W23_addr"));
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse w24 = new Warehouse(200.0, "W24_addr", "CityO");
        
        // Add Product "DeviceY" to W24
        Supplier supplier2 = new Supplier("SupplierB", "AddressB");
        Product deviceY = new Product("DeviceY", true, 5.0, supplier2);
        
        w24.addProduct(deviceY);
        company.addWarehouse(w24);
        
        // Action: Find locations of "DeviceY" in C1
        List<String> locations = company.getProductLocations("DeviceY");
        
        // Expected Output: "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        assertEquals("CityO - W24_addr", locations.get(0));
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse w25 = new Warehouse(300.0, "W25_addr", "CityR");
        Supplier supplier3 = new Supplier("SupplierC", "AddressC");
        Product partZ = new Product("PartZ", false, 20.0, supplier3);
        
        w25.addProduct(partZ);
        company.addWarehouse(w25);
        
        // Action: Find locations of "ToolW" in C1
        List<String> locations = company.getProductLocations("ToolW");
        
        // Expected Output: []
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse w26 = new Warehouse(400.0, "W26_addr", "CityS");
        Warehouse w27 = new Warehouse(500.0, "W27_addr", "CityT");
        
        Supplier supplier4 = new Supplier("SupplierD", "AddressD");
        Product productA = new Product("ProductA", true, 8.0, supplier4);
        Product productB = new Product("ProductB", false, 12.0, supplier4);
        
        w26.addProduct(productA);
        w27.addProduct(productB);
        
        company.addWarehouse(w26);
        company.addWarehouse(w27);
        
        // Action: Find locations of "ItemV" in C1
        List<String> locations = company.getProductLocations("ItemV");
        
        // Expected Output: []
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse w28 = new Warehouse(600.0, "W28_addr", "CityP");
        Warehouse w29 = new Warehouse(700.0, "W29_addr", "CityQ");
        
        // Add Product "CommonPart" to both warehouses
        Supplier supplier5 = new Supplier("SupplierE", "AddressE");
        Product commonPart1 = new Product("CommonPart", false, 25.0, supplier5);
        Product commonPart2 = new Product("CommonPart", false, 30.0, supplier5);
        
        w28.addProduct(commonPart1);
        w29.addProduct(commonPart2);
        
        company.addWarehouse(w28);
        company.addWarehouse(w29);
        
        // Action: Find locations of "CommonPart" in C1
        List<String> locations = company.getProductLocations("CommonPart");
        
        // Expected Output: "city":"CityP","address":"W28_addr" and "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains("CityP - W28_addr"));
        assertTrue(locations.contains("CityQ - W29_addr"));
    }
}