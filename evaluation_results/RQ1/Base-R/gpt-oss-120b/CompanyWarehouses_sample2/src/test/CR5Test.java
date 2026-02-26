import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        
        // SetUp: Add Product "MaterialX" to both W22 and W23
        Supplier supplier = new Supplier("Supplier1", "SupplierAddress");
        Product product1 = new Product("MaterialX", false, 10.0, supplier);
        Product product2 = new Product("MaterialX", false, 15.0, supplier);
        
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1
        List<String> locations = company.getProductLocations("MaterialX");
        
        // Expected Output: ["City: CityM, Address: W22_addr", "City: CityN, Address: W23_addr"]
        assertEquals(2, locations.size());
        assertTrue(locations.contains("City: CityM, Address: W22_addr"));
        assertTrue(locations.contains("City: CityN, Address: W23_addr"));
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse("CityO", "W24_addr", 200.0);
        
        // SetUp: Add Product "DeviceY" to W24
        Supplier supplier = new Supplier("Supplier2", "SupplierAddress2");
        Product product = new Product("DeviceY", true, 5.0, supplier);
        
        warehouse.addProduct(product);
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "DeviceY" in C1
        List<String> locations = company.getProductLocations("DeviceY");
        
        // Expected Output: ["City: CityO, Address: W24_addr"]
        assertEquals(1, locations.size());
        assertEquals("City: CityO, Address: W24_addr", locations.get(0));
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse("CityR", "W25_addr", 300.0);
        Supplier supplier = new Supplier("Supplier3", "SupplierAddress3");
        Product product = new Product("PartZ", false, 8.0, supplier);
        
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
        Warehouse warehouse1 = new Warehouse("CityS", "W26_addr", 400.0);
        Warehouse warehouse2 = new Warehouse("CityT", "W27_addr", 500.0);
        
        Supplier supplier = new Supplier("Supplier4", "SupplierAddress4");
        Product product1 = new Product("ItemA", false, 12.0, supplier);
        Product product2 = new Product("ItemB", true, 7.0, supplier);
        
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
        Warehouse warehouse1 = new Warehouse("CityP", "W28_addr", 600.0);
        Warehouse warehouse2 = new Warehouse("CityQ", "W29_addr", 700.0);
        
        // SetUp: Add Product "CommonPart" to both warehouses
        Supplier supplier = new Supplier("Supplier5", "SupplierAddress5");
        Product product1 = new Product("CommonPart", false, 20.0, supplier);
        Product product2 = new Product("CommonPart", false, 25.0, supplier);
        
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1
        List<String> locations = company.getProductLocations("CommonPart");
        
        // Expected Output: ["City: CityP, Address: W28_addr", "City: CityQ, Address: W29_addr"]
        assertEquals(2, locations.size());
        assertTrue(locations.contains("City: CityP, Address: W28_addr"));
        assertTrue(locations.contains("City: CityQ, Address: W29_addr"));
    }
}