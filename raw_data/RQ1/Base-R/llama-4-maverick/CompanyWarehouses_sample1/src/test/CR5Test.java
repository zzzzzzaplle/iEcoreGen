import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        Warehouse warehouse1 = new Warehouse("CityM", "W22_addr", 100.0);
        Warehouse warehouse2 = new Warehouse("CityN", "W23_addr", 150.0);
        
        // Create supplier and product "MaterialX"
        Supplier supplier = new Supplier("SupplierA", "SupplierAddress");
        Product product = new Product("MaterialX", supplier, false, 10.0);
        
        // Add product to both warehouses
        warehouse1.getProducts().add(product);
        warehouse2.getProducts().add(product);
        
        // Add warehouses to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse1);
        warehouses.add(warehouse2);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "MaterialX" in C1
        List<String> result = company.verifyProductLocations("MaterialX");
        
        // Expected Output: ["CityM, W22_addr", "CityN, W23_addr"]
        assertEquals(2, result.size());
        assertTrue(result.contains("CityM, W22_addr"));
        assertTrue(result.contains("CityN, W23_addr"));
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse("CityO", "W24_addr", 200.0);
        
        // Create supplier and product "DeviceY"
        Supplier supplier = new Supplier("SupplierB", "SupplierAddressB");
        Product product = new Product("DeviceY", supplier, true, 15.0);
        
        // Add product to warehouse
        warehouse.getProducts().add(product);
        
        // Add warehouse to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "DeviceY" in C1
        List<String> result = company.verifyProductLocations("DeviceY");
        
        // Expected Output: ["CityO, W24_addr"]
        assertEquals(1, result.size());
        assertEquals("CityO, W24_addr", result.get(0));
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse("CityR", "W25_addr", 300.0);
        
        // Create supplier and product "PartZ"
        Supplier supplier = new Supplier("SupplierC", "SupplierAddressC");
        Product product = new Product("PartZ", supplier, false, 20.0);
        
        // Add product to warehouse
        warehouse.getProducts().add(product);
        
        // Add warehouse to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "ToolW" in C1
        List<String> result = company.verifyProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse1 = new Warehouse("CityS", "W26_addr", 400.0);
        Warehouse warehouse2 = new Warehouse("CityT", "W27_addr", 500.0);
        
        // Create suppliers and other products (not "ItemV")
        Supplier supplier1 = new Supplier("SupplierD", "SupplierAddressD");
        Supplier supplier2 = new Supplier("SupplierE", "SupplierAddressE");
        
        Product product1 = new Product("PartA", supplier1, true, 25.0);
        Product product2 = new Product("PartB", supplier2, false, 30.0);
        
        // Add products to warehouses
        warehouse1.getProducts().add(product1);
        warehouse2.getProducts().add(product2);
        
        // Add warehouses to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse1);
        warehouses.add(warehouse2);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "ItemV" in C1
        List<String> result = company.verifyProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse warehouse1 = new Warehouse("CityP", "W28_addr", 600.0);
        Warehouse warehouse2 = new Warehouse("CityQ", "W29_addr", 700.0);
        
        // Create supplier and product "CommonPart"
        Supplier supplier = new Supplier("SupplierF", "SupplierAddressF");
        Product product = new Product("CommonPart", supplier, false, 35.0);
        
        // Add product to both warehouses
        warehouse1.getProducts().add(product);
        warehouse2.getProducts().add(product);
        
        // Add warehouses to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse1);
        warehouses.add(warehouse2);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "CommonPart" in C1
        List<String> result = company.verifyProductLocations("CommonPart");
        
        // Expected Output: ["CityP, W28_addr", "CityQ, W29_addr"]
        assertEquals(2, result.size());
        assertTrue(result.contains("CityP, W28_addr"));
        assertTrue(result.contains("CityQ, W29_addr"));
    }
}