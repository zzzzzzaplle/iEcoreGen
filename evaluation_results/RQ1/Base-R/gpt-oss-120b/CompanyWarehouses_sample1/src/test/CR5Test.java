import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company("C1");
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        Warehouse warehouse1 = new Warehouse("CityM", "W22_addr", 100.0);
        Warehouse warehouse2 = new Warehouse("CityN", "W23_addr", 150.0);
        
        // Add Product "MaterialX" to both W22 and W23
        Product product1 = new Product("MaterialX", new Supplier("SupplierA", "AddressA"), false, 10.0);
        Product product2 = new Product("MaterialX", new Supplier("SupplierB", "AddressB"), true, 15.0);
        
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1
        List<String> locations = company.getProductLocations("MaterialX");
        
        // Expected Output: "CityM - W22_addr", "CityN - W23_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains("CityM - W22_addr"));
        assertTrue(locations.contains("CityN - W23_addr"));
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse("CityO", "W24_addr", 120.0);
        
        // Add Product "DeviceY" to W24
        Product product = new Product("DeviceY", new Supplier("SupplierC", "AddressC"), false, 8.0);
        warehouse.addProduct(product);
        
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "DeviceY" in C1
        List<String> locations = company.getProductLocations("DeviceY");
        
        // Expected Output: "CityO - W24_addr"
        assertEquals(1, locations.size());
        assertEquals("CityO - W24_addr", locations.get(0));
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse("CityR", "W25_addr", 200.0);
        Product product = new Product("PartZ", new Supplier("SupplierD", "AddressD"), true, 12.0);
        warehouse.addProduct(product);
        
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "ToolW" in C1
        List<String> locations = company.getProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse1 = new Warehouse("CityS", "W26_addr", 180.0);
        Warehouse warehouse2 = new Warehouse("CityT", "W27_addr", 220.0);
        
        // Add other products to warehouses
        Product product1 = new Product("ComponentA", new Supplier("SupplierE", "AddressE"), false, 5.0);
        Product product2 = new Product("ComponentB", new Supplier("SupplierF", "AddressF"), true, 7.0);
        
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "ItemV" in C1
        List<String> locations = company.getProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse warehouse1 = new Warehouse("CityP", "W28_addr", 160.0);
        Warehouse warehouse2 = new Warehouse("CityQ", "W29_addr", 190.0);
        
        // Add Product "CommonPart" to both warehouses
        Product product1 = new Product("CommonPart", new Supplier("SupplierG", "AddressG"), false, 9.0);
        Product product2 = new Product("CommonPart", new Supplier("SupplierH", "AddressH"), true, 11.0);
        
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1
        List<String> locations = company.getProductLocations("CommonPart");
        
        // Expected Output: "CityP - W28_addr", "CityQ - W29_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains("CityP - W28_addr"));
        assertTrue(locations.contains("CityQ - W29_addr"));
    }
}