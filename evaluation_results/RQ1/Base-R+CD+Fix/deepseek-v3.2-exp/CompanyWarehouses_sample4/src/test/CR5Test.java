import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setCity("CityM");
        warehouse1.setAddress("W22_addr");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setCity("CityN");
        warehouse2.setAddress("W23_addr");
        
        // Create supplier and product
        Supplier supplier = new Supplier();
        supplier.setName("Supplier1");
        
        Product product = new Product();
        product.setName("MaterialX");
        product.setSupplier(supplier);
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product);
        occupation1.setVolume(100.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product);
        occupation2.setVolume(150.0);
        
        // Add occupations to warehouses
        warehouse1.addOccupation(occupation1);
        warehouse2.addOccupation(occupation2);
        
        // Add warehouses to company
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, locations.size());
        
        // Verify first location
        assertEquals("CityM", locations.get(0).getKey());
        assertEquals("W22_addr", locations.get(0).getValue());
        
        // Verify second location
        assertEquals("CityN", locations.get(1).getKey());
        assertEquals("W23_addr", locations.get(1).getValue());
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityO");
        warehouse.setAddress("W24_addr");
        
        // Create supplier and product
        Supplier supplier = new Supplier();
        supplier.setName("Supplier2");
        
        Product product = new Product();
        product.setName("DeviceY");
        product.setSupplier(supplier);
        
        // Create product occupation
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(200.0);
        
        // Add occupation to warehouse
        warehouse.addOccupation(occupation);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "DeviceY" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        assertEquals("CityO", locations.get(0).getKey());
        assertEquals("W24_addr", locations.get(0).getValue());
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityR");
        warehouse.setAddress("W25_addr");
        
        // Create supplier and product
        Supplier supplier = new Supplier();
        supplier.setName("Supplier3");
        
        Product product = new Product();
        product.setName("PartZ");
        product.setSupplier(supplier);
        
        // Create product occupation
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(300.0);
        
        // Add occupation to warehouse
        warehouse.addOccupation(occupation);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "ToolW" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setCity("CityS");
        warehouse1.setAddress("W26_addr");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setCity("CityT");
        warehouse2.setAddress("W27_addr");
        
        // Create suppliers and products (different from "ItemV")
        Supplier supplier1 = new Supplier();
        supplier1.setName("Supplier4");
        
        Supplier supplier2 = new Supplier();
        supplier2.setName("Supplier5");
        
        Product product1 = new Product();
        product1.setName("ProductA");
        product1.setSupplier(supplier1);
        
        Product product2 = new Product();
        product2.setName("ProductB");
        product2.setSupplier(supplier2);
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(400.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(500.0);
        
        // Add occupations to warehouses
        warehouse1.addOccupation(occupation1);
        warehouse2.addOccupation(occupation2);
        
        // Add warehouses to company
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "ItemV" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setCity("CityP");
        warehouse1.setAddress("W28_addr");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setCity("CityQ");
        warehouse2.setAddress("W29_addr");
        
        // Create supplier and product
        Supplier supplier = new Supplier();
        supplier.setName("Supplier6");
        
        Product product = new Product();
        product.setName("CommonPart");
        product.setSupplier(supplier);
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product);
        occupation1.setVolume(600.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product);
        occupation2.setVolume(700.0);
        
        // Add occupations to warehouses
        warehouse1.addOccupation(occupation1);
        warehouse2.addOccupation(occupation2);
        
        // Add warehouses to company
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        
        // Verify first location
        assertEquals("CityP", locations.get(0).getKey());
        assertEquals("W28_addr", locations.get(0).getValue());
        
        // Verify second location
        assertEquals("CityQ", locations.get(1).getKey());
        assertEquals("W29_addr", locations.get(1).getValue());
    }
}