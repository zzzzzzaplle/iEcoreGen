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
    public void testCase1_productInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setCity("CityM");
        warehouse1.setAddress("W22_addr");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setCity("CityN");
        warehouse2.setAddress("W23_addr");
        
        // Add Product "MaterialX" to both W22 and W23
        Product product1 = new Product();
        product1.setName("MaterialX");
        
        Product product2 = new Product();
        product2.setName("MaterialX");
        
        warehouse1.getProducts().add(product1);
        warehouse2.getProducts().add(product2);
        
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1
        List<String> result = company.getProductLocations("MaterialX");
        
        // Expected Output: "CityM - W22_addr", "CityN - W23_addr"
        assertEquals(2, result.size());
        assertTrue(result.contains("CityM - W22_addr"));
        assertTrue(result.contains("CityN - W23_addr"));
    }
    
    @Test
    public void testCase2_productInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityO");
        warehouse.setAddress("W24_addr");
        
        // Add Product "DeviceY" to W24
        Product product = new Product();
        product.setName("DeviceY");
        
        warehouse.getProducts().add(product);
        company.getWarehouses().add(warehouse);
        
        // Action: Find locations of "DeviceY" in C1
        List<String> result = company.getProductLocations("DeviceY");
        
        // Expected Output: "CityO - W24_addr"
        assertEquals(1, result.size());
        assertEquals("CityO - W24_addr", result.get(0));
    }
    
    @Test
    public void testCase3_nonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityR");
        warehouse.setAddress("W25_addr");
        
        Product product = new Product();
        product.setName("PartZ");
        
        warehouse.getProducts().add(product);
        company.getWarehouses().add(warehouse);
        
        // Action: Find locations of "ToolW" in C1
        List<String> result = company.getProductLocations("ToolW");
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_productInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setCity("CityS");
        warehouse1.setAddress("W26_addr");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setCity("CityT");
        warehouse2.setAddress("W27_addr");
        
        // Add other products to warehouses
        Product product1 = new Product();
        product1.setName("OtherProduct1");
        
        Product product2 = new Product();
        product2.setName("OtherProduct2");
        
        warehouse1.getProducts().add(product1);
        warehouse2.getProducts().add(product2);
        
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Action: Find locations of "ItemV" in C1
        List<String> result = company.getProductLocations("ItemV");
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_productInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setCity("CityP");
        warehouse1.setAddress("W28_addr");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setCity("CityQ");
        warehouse2.setAddress("W29_addr");
        
        // Add Product "CommonPart" to both warehouses
        Product product1 = new Product();
        product1.setName("CommonPart");
        
        Product product2 = new Product();
        product2.setName("CommonPart");
        
        warehouse1.getProducts().add(product1);
        warehouse2.getProducts().add(product2);
        
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1
        List<String> result = company.getProductLocations("CommonPart");
        
        // Expected Output: "CityP - W28_addr", "CityQ - W29_addr"
        assertEquals(2, result.size());
        assertTrue(result.contains("CityP - W28_addr"));
        assertTrue(result.contains("CityQ - W29_addr"));
    }
}