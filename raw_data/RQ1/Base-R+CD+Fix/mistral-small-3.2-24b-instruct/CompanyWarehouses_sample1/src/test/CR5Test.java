import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        
        // SetUp: Add Product "MaterialX" to both W22 and W23
        Supplier supplier = new Supplier();
        supplier.setName("TestSupplier");
        
        Product product = new Product();
        product.setName("MaterialX");
        product.setSupplier(supplier);
        
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(product);
        po1.setVolume(100.0);
        warehouse1.addOccupation(po1);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(product);
        po2.setVolume(200.0);
        warehouse2.addOccupation(po2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "MaterialX" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("MaterialX");
        
        // Expected Output: Two entries with city and address pairs
        assertEquals(2, locations.size());
        
        // Verify first location: "city":"CityM","address":"W22_addr"
        assertEquals("CityM", locations.get(0).getKey());
        assertEquals("W22_addr", locations.get(0).getValue());
        
        // Verify second location: "city":"CityN","address":"W23_addr"
        assertEquals("CityN", locations.get(1).getKey());
        assertEquals("W23_addr", locations.get(1).getValue());
    }
    
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // SetUp: Create Warehouse "W24" in "CityO", in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityO");
        warehouse.setAddress("W24_addr");
        
        // SetUp: Add Product "DeviceY" to W24
        Supplier supplier = new Supplier();
        supplier.setName("TestSupplier");
        
        Product product = new Product();
        product.setName("DeviceY");
        product.setSupplier(supplier);
        
        ProductOccupation po = new ProductOccupation();
        po.setProduct(product);
        po.setVolume(150.0);
        warehouse.addOccupation(po);
        
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "DeviceY" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("DeviceY");
        
        // Expected Output: One entry with city and address
        assertEquals(1, locations.size());
        
        // Verify location: "city":"CityO","address":"W24_addr"
        assertEquals("CityO", locations.get(0).getKey());
        assertEquals("W24_addr", locations.get(0).getValue());
    }
    
    @Test
    public void testCase3_NonExistentProduct() {
        // SetUp: Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ"
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("TestCity");
        warehouse.setAddress("W25_addr");
        
        Supplier supplier = new Supplier();
        supplier.setName("TestSupplier");
        
        Product product = new Product();
        product.setName("PartZ");
        product.setSupplier(supplier);
        
        ProductOccupation po = new ProductOccupation();
        po.setProduct(product);
        po.setVolume(50.0);
        warehouse.addOccupation(po);
        
        company.addWarehouse(warehouse);
        
        // Action: Find locations of "ToolW" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("ToolW");
        
        // Expected Output: Empty list []
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // SetUp: Create Warehouses ["W26", "W27"] with other products
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setCity("CityA");
        warehouse1.setAddress("W26_addr");
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setCity("CityB");
        warehouse2.setAddress("W27_addr");
        
        // Add different products to warehouses
        Supplier supplier = new Supplier();
        supplier.setName("TestSupplier");
        
        Product product1 = new Product();
        product1.setName("ProductA");
        product1.setSupplier(supplier);
        
        Product product2 = new Product();
        product2.setName("ProductB");
        product2.setSupplier(supplier);
        
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(product1);
        po1.setVolume(100.0);
        warehouse1.addOccupation(po1);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(product2);
        po2.setVolume(200.0);
        warehouse2.addOccupation(po2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "ItemV" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("ItemV");
        
        // Expected Output: Empty list []
        assertNotNull(locations);
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
        
        // SetUp: Add Product "CommonPart" to both warehouses
        Supplier supplier = new Supplier();
        supplier.setName("TestSupplier");
        
        Product product = new Product();
        product.setName("CommonPart");
        product.setSupplier(supplier);
        
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(product);
        po1.setVolume(300.0);
        warehouse1.addOccupation(po1);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(product);
        po2.setVolume(400.0);
        warehouse2.addOccupation(po2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: Find locations of "CommonPart" in C1
        List<Map.Entry<String, String>> locations = company.findProductLocations("CommonPart");
        
        // Expected Output: Two entries with city and address pairs
        assertEquals(2, locations.size());
        
        // Verify first location: "city":"CityP","address":"W28_addr"
        assertEquals("CityP", locations.get(0).getKey());
        assertEquals("W28_addr", locations.get(0).getValue());
        
        // Verify second location: "city":"CityQ","address":"W29_addr"
        assertEquals("CityQ", locations.get(1).getKey());
        assertEquals("W29_addr", locations.get(1).getValue());
    }
}