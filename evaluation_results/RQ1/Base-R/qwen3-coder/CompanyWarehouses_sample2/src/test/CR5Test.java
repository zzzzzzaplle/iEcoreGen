import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        warehouse1.setAddress("W22_addr");
        warehouse1.setCity("CityM");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setAddress("W23_addr");
        warehouse2.setCity("CityN");
        
        // Add Product "MaterialX" to both W22 and W23
        Product product1 = new Product();
        product1.setName("MaterialX");
        
        Product product2 = new Product();
        product2.setName("MaterialX");
        
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
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W24_addr");
        warehouse.setCity("CityO");
        
        // Add Product "DeviceY" to W24
        Product product = new Product();
        product.setName("DeviceY");
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
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W25_addr");
        warehouse.setCity("CityR");
        
        Product product = new Product();
        product.setName("PartZ");
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
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setAddress("W26_addr");
        warehouse1.setCity("CityS");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setAddress("W27_addr");
        warehouse2.setCity("CityT");
        
        // Add other products to warehouses
        Product product1 = new Product();
        product1.setName("OtherProductA");
        warehouse1.addProduct(product1);
        
        Product product2 = new Product();
        product2.setName("OtherProductB");
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
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setAddress("W28_addr");
        warehouse1.setCity("CityP");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setAddress("W29_addr");
        warehouse2.setCity("CityQ");
        
        // Add Product "CommonPart" to both warehouses
        Product product1 = new Product();
        product1.setName("CommonPart");
        
        Product product2 = new Product();
        product2.setName("CommonPart");
        
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