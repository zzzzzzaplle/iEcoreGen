import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;

public class CR5Test {
    private Company company;
    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private Warehouse warehouse3;
    private Product product1;
    private Product product2;
    private Product product3;
    private ProductOccupation occupation1;
    private ProductOccupation occupation2;
    private ProductOccupation occupation3;
    private Supplier supplier1;
    private Supplier supplier2;

    @Before
    public void setUp() {
        company = new Company();
        
        // Create suppliers
        supplier1 = new Supplier();
        supplier1.setName("SupplierA");
        supplier1.setAddress("SupplierA_Address");
        
        supplier2 = new Supplier();
        supplier2.setName("SupplierB");
        supplier2.setAddress("SupplierB_Address");
        
        // Create products
        product1 = new Product();
        product1.setName("MaterialX");
        product1.setSupplier(supplier1);
        
        product2 = new Product();
        product2.setName("DeviceY");
        product2.setSupplier(supplier1);
        
        product3 = new Product();
        product3.setName("PartZ");
        product3.setSupplier(supplier2);
        
        // Create product occupations
        occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(100.0);
        
        occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(50.0);
        
        occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(75.0);
    }

    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // SetUp: Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityM");
        warehouse1.setAddress("W22_addr");
        warehouse1.addOccupations(occupation1);
        
        warehouse2 = new Warehouse();
        warehouse2.setCity("CityN");
        warehouse2.setAddress("W23_addr");
        warehouse2.addOccupations(occupation1);
        
        company.addWarehouses(warehouse1);
        company.addWarehouses(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, locations.size());
        
        boolean foundCityM = false;
        boolean foundCityN = false;
        
        for (Map.Entry<String, String> entry : locations) {
            if (entry.getKey().equals("CityM") && entry.getValue().equals("W22_addr")) {
                foundCityM = true;
            }
            if (entry.getKey().equals("CityN") && entry.getValue().equals("W23_addr")) {
                foundCityN = true;
            }
        }
        
        assertTrue("Should find product in CityM", foundCityM);
        assertTrue("Should find product in CityN", foundCityN);
    }

    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityO");
        warehouse1.setAddress("W24_addr");
        warehouse1.addOccupations(occupation2);
        
        company.addWarehouses(warehouse1);
        
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
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityR");
        warehouse1.setAddress("W25_addr");
        warehouse1.addOccupations(occupation3);
        
        company.addWarehouses(warehouse1);
        
        // Action: Find locations of "ToolW" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue("Should return empty list for non-existent product", locations.isEmpty());
    }

    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityS");
        warehouse1.setAddress("W26_addr");
        warehouse1.addOccupations(occupation1); // Contains MaterialX
        
        warehouse2 = new Warehouse();
        warehouse2.setCity("CityT");
        warehouse2.setAddress("W27_addr");
        warehouse2.addOccupations(occupation2); // Contains DeviceY
        
        company.addWarehouses(warehouse1);
        company.addWarehouses(warehouse2);
        
        // Action: Find locations of "ItemV" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue("Should return empty list when product is not in any warehouse", locations.isEmpty());
    }

    @Test
    public void testCase5_ProductInAllWarehouses() {
        // SetUp: Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityP");
        warehouse1.setAddress("W28_addr");
        warehouse1.addOccupations(occupation1);
        
        warehouse2 = new Warehouse();
        warehouse2.setCity("CityQ");
        warehouse2.setAddress("W29_addr");
        warehouse2.addOccupations(occupation1);
        
        company.addWarehouses(warehouse1);
        company.addWarehouses(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        
        boolean foundCityP = false;
        boolean foundCityQ = false;
        
        for (Map.Entry<String, String> entry : locations) {
            if (entry.getKey().equals("CityP") && entry.getValue().equals("W28_addr")) {
                foundCityP = true;
            }
            if (entry.getKey().equals("CityQ") && entry.getValue().equals("W29_addr")) {
                foundCityQ = true;
            }
        }
        
        assertTrue("Should find product in CityP", foundCityP);
        assertTrue("Should find product in CityQ", foundCityQ);
    }
}