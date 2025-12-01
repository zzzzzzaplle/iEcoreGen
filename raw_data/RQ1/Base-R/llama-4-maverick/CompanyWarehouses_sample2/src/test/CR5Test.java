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
        Warehouse warehouse1 = new Warehouse("W22_addr", "CityM", 100.0);
        Warehouse warehouse2 = new Warehouse("W23_addr", "CityN", 150.0);
        
        // Add Product "MaterialX" to both W22 and W23
        Supplier supplier = new Supplier("TestSupplier", "SupplierAddress");
        Product product1 = new Product("MaterialX", supplier, false, 10.0);
        Product product2 = new Product("MaterialX", supplier, false, 15.0);
        
        warehouse1.getProducts().add(product1);
        warehouse2.getProducts().add(product2);
        
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse1);
        warehouses.add(warehouse2);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "MaterialX" in C1
        List<String> result = company.getProductLocations("MaterialX");
        
        // Expected Output: "CityM, W22_addr" and "CityN, W23_addr"
        assertEquals(2, result.size());
        assertTrue(result.contains("CityM, W22_addr"));
        assertTrue(result.contains("CityN, W23_addr"));
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse("W24_addr", "CityO", 200.0);
        
        // Add Product "DeviceY" to W24
        Supplier supplier = new Supplier("TestSupplier", "SupplierAddress");
        Product product = new Product("DeviceY", supplier, false, 20.0);
        
        warehouse.getProducts().add(product);
        
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "DeviceY" in C1
        List<String> result = company.getProductLocations("DeviceY");
        
        // Expected Output: "CityO, W24_addr"
        assertEquals(1, result.size());
        assertEquals("CityO, W24_addr", result.get(0));
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse("W25_addr", "CityR", 250.0);
        
        Supplier supplier = new Supplier("TestSupplier", "SupplierAddress");
        Product product = new Product("PartZ", supplier, false, 25.0);
        
        warehouse.getProducts().add(product);
        
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "ToolW" in C1
        List<String> result = company.getProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse1 = new Warehouse("W26_addr", "CityS", 300.0);
        Warehouse warehouse2 = new Warehouse("W27_addr", "CityT", 350.0);
        
        Supplier supplier = new Supplier("TestSupplier", "SupplierAddress");
        Product product1 = new Product("OtherProduct1", supplier, false, 30.0);
        Product product2 = new Product("OtherProduct2", supplier, false, 35.0);
        
        warehouse1.getProducts().add(product1);
        warehouse2.getProducts().add(product2);
        
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse1);
        warehouses.add(warehouse2);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "ItemV" in C1
        List<String> result = company.getProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse warehouse1 = new Warehouse("W28_addr", "CityP", 400.0);
        Warehouse warehouse2 = new Warehouse("W29_addr", "CityQ", 450.0);
        
        // Add Product "CommonPart" to both warehouses
        Supplier supplier = new Supplier("TestSupplier", "SupplierAddress");
        Product product1 = new Product("CommonPart", supplier, false, 40.0);
        Product product2 = new Product("CommonPart", supplier, false, 45.0);
        
        warehouse1.getProducts().add(product1);
        warehouse2.getProducts().add(product2);
        
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse1);
        warehouses.add(warehouse2);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "CommonPart" in C1
        List<String> result = company.getProductLocations("CommonPart");
        
        // Expected Output: "CityP, W28_addr" and "CityQ, W29_addr"
        assertEquals(2, result.size());
        assertTrue(result.contains("CityP, W28_addr"));
        assertTrue(result.contains("CityQ, W29_addr"));
    }
}