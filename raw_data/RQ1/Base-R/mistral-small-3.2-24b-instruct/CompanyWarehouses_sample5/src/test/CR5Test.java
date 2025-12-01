import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        Warehouse warehouse1 = new Warehouse("W22_addr", 100.0);
        warehouse1.setAddress("W22_addr");
        
        Warehouse warehouse2 = new Warehouse("W23_addr", 150.0);
        warehouse2.setAddress("W23_addr");
        
        // Add warehouses to company
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Create supplier and product
        Supplier supplier = new Supplier("SupplierA", "SupplierAddress");
        Product product = new Product("MaterialX", supplier, false, 10.0);
        
        // Add Product "MaterialX" to both W22 and W23
        warehouse1.addProduct(product);
        warehouse2.addProduct(product);
        
        // Action: Find locations of "MaterialX" in C1
        List<String> locations = company.getProductLocations("MaterialX");
        
        // Expected Output: ["W22_addr", "W23_addr"]
        assertEquals(2, locations.size());
        assertTrue(locations.contains("W22_addr"));
        assertTrue(locations.contains("W23_addr"));
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse("W24_addr", 200.0);
        warehouse.setAddress("W24_addr");
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Create supplier and product
        Supplier supplier = new Supplier("SupplierB", "SupplierAddress");
        Product product = new Product("DeviceY", supplier, true, 15.0);
        
        // Add Product "DeviceY" to W24
        warehouse.addProduct(product);
        
        // Action: Find locations of "DeviceY" in C1
        List<String> locations = company.getProductLocations("DeviceY");
        
        // Expected Output: ["W24_addr"]
        assertEquals(1, locations.size());
        assertEquals("W24_addr", locations.get(0));
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse("W25_addr", 300.0);
        warehouse.setAddress("W25_addr");
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Create supplier and product
        Supplier supplier = new Supplier("SupplierC", "SupplierAddress");
        Product product = new Product("PartZ", supplier, false, 20.0);
        
        // Add Product "PartZ" to W25
        warehouse.addProduct(product);
        
        // Action: Find locations of "ToolW" in C1
        List<String> locations = company.getProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse1 = new Warehouse("W26_addr", 250.0);
        warehouse1.setAddress("W26_addr");
        
        Warehouse warehouse2 = new Warehouse("W27_addr", 350.0);
        warehouse2.setAddress("W27_addr");
        
        // Add warehouses to company
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Create suppliers and products
        Supplier supplier1 = new Supplier("SupplierD", "SupplierAddress");
        Product product1 = new Product("OtherProduct1", supplier1, true, 25.0);
        
        Supplier supplier2 = new Supplier("SupplierE", "SupplierAddress");
        Product product2 = new Product("OtherProduct2", supplier2, false, 30.0);
        
        // Add other products to warehouses (but not "ItemV")
        warehouse1.addProduct(product1);
        warehouse2.addProduct(product2);
        
        // Action: Find locations of "ItemV" in C1
        List<String> locations = company.getProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse warehouse1 = new Warehouse("W28_addr", 400.0);
        warehouse1.setAddress("W28_addr");
        
        Warehouse warehouse2 = new Warehouse("W29_addr", 450.0);
        warehouse2.setAddress("W29_addr");
        
        // Add warehouses to company
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Create supplier and product
        Supplier supplier = new Supplier("SupplierF", "SupplierAddress");
        Product product = new Product("CommonPart", supplier, false, 35.0);
        
        // Add Product "CommonPart" to both warehouses
        warehouse1.addProduct(product);
        warehouse2.addProduct(product);
        
        // Action: Find locations of "CommonPart" in C1
        List<String> locations = company.getProductLocations("CommonPart");
        
        // Expected Output: ["W28_addr", "W29_addr"]
        assertEquals(2, locations.size());
        assertTrue(locations.contains("W28_addr"));
        assertTrue(locations.contains("W29_addr"));
    }
}