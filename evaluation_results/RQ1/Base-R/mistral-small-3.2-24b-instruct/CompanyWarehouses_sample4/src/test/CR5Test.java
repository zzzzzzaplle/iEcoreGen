import org.junit.Before;
import org.junit.Test;
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
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setAddress("W22_addr");
        warehouse1.setCity("CityM");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setAddress("W23_addr");
        warehouse2.setCity("CityN");
        
        // Create product "MaterialX" and add to both warehouses
        Product product = new Product();
        product.setName("MaterialX");
        
        List<Product> products1 = new ArrayList<>();
        products1.add(product);
        warehouse1.setProducts(products1);
        
        List<Product> products2 = new ArrayList<>();
        products2.add(product);
        warehouse2.setProducts(products2);
        
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse1);
        warehouses.add(warehouse2);
        company.setWarehouses(warehouses);
        
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
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W24_addr");
        warehouse.setCity("CityO");
        
        // Add Product "DeviceY" to W24
        Product product = new Product();
        product.setName("DeviceY");
        
        List<Product> products = new ArrayList<>();
        products.add(product);
        warehouse.setProducts(products);
        
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "DeviceY" in C1
        List<String> locations = company.getProductLocations("DeviceY");
        
        // Expected Output: "city":"CityO","address":"W24_addr"
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
        
        List<Product> products = new ArrayList<>();
        products.add(product);
        warehouse.setProducts(products);
        
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "ToolW" in C1
        List<String> locations = company.getProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products. Warehouses are both in company C1
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setAddress("W26_addr");
        warehouse1.setCity("CityS");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setAddress("W27_addr");
        warehouse2.setCity("CityT");
        
        // Add different products to warehouses
        Product product1 = new Product();
        product1.setName("OtherProduct1");
        
        Product product2 = new Product();
        product2.setName("OtherProduct2");
        
        List<Product> products1 = new ArrayList<>();
        products1.add(product1);
        warehouse1.setProducts(products1);
        
        List<Product> products2 = new ArrayList<>();
        products2.add(product2);
        warehouse2.setProducts(products2);
        
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse1);
        warehouses.add(warehouse2);
        company.setWarehouses(warehouses);
        
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
        Product product = new Product();
        product.setName("CommonPart");
        
        List<Product> products1 = new ArrayList<>();
        products1.add(product);
        warehouse1.setProducts(products1);
        
        List<Product> products2 = new ArrayList<>();
        products2.add(product);
        warehouse2.setProducts(products2);
        
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse1);
        warehouses.add(warehouse2);
        company.setWarehouses(warehouses);
        
        // Action: Find locations of "CommonPart" in C1
        List<String> locations = company.getProductLocations("CommonPart");
        
        // Expected Output: "city":"CityP","address":"W28_addr" and "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains("CityP - W28_addr"));
        assertTrue(locations.contains("CityQ - W29_addr"));
    }
}