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
        
        // Create product MaterialX
        Product product = new Product();
        product.setName("MaterialX");
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product);
        
        // Add occupations to warehouses
        warehouse1.addOccupation(occupation1);
        warehouse2.addOccupation(occupation2);
        
        // Add warehouses to company
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, result.size());
        
        // Verify first location
        assertEquals("CityM", result.get(0).getKey());
        assertEquals("W22_addr", result.get(0).getValue());
        
        // Verify second location
        assertEquals("CityN", result.get(1).getKey());
        assertEquals("W23_addr", result.get(1).getValue());
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityO");
        warehouse.setAddress("W24_addr");
        
        // Create product DeviceY
        Product product = new Product();
        product.setName("DeviceY");
        
        // Create product occupation
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        
        // Add occupation to warehouse
        warehouse.addOccupation(occupation);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "DeviceY" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, result.size());
        assertEquals("CityO", result.get(0).getKey());
        assertEquals("W24_addr", result.get(0).getValue());
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityR");
        warehouse.setAddress("W25_addr");
        
        // Create product PartZ
        Product product = new Product();
        product.setName("PartZ");
        
        // Create product occupation
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        
        // Add occupation to warehouse
        warehouse.addOccupation(occupation);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "ToolW" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
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
        
        // Create different products (not ItemV)
        Product product1 = new Product();
        product1.setName("ProductA");
        
        Product product2 = new Product();
        product2.setName("ProductB");
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        
        // Add occupations to warehouses
        warehouse1.addOccupation(occupation1);
        warehouse2.addOccupation(occupation2);
        
        // Add warehouses to company
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "ItemV" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
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
        
        // Create product CommonPart
        Product product = new Product();
        product.setName("CommonPart");
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product);
        
        // Add occupations to warehouses
        warehouse1.addOccupation(occupation1);
        warehouse2.addOccupation(occupation2);
        
        // Add warehouses to company
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1
        List<Map.Entry<String, String>> result = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, result.size());
        
        // Verify first location
        assertEquals("CityP", result.get(0).getKey());
        assertEquals("W28_addr", result.get(0).getValue());
        
        // Verify second location
        assertEquals("CityQ", result.get(1).getKey());
        assertEquals("W29_addr", result.get(1).getValue());
    }
}