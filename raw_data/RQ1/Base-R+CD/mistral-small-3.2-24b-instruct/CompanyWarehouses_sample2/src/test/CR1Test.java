import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // SetUp: Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityA");
        warehouse.setSurface(500.0);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        ProductOccupation occupation1 = new ProductOccupation();
        Product product1 = new Product();
        product1.setName("ChemX");
        product1.setToxic(true);
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        product1.setSupplier(supplier1);
        occupation1.setProduct(product1);
        occupation1.setVolume(10.0);
        warehouse.addOccupations(occupation1);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        ProductOccupation occupation2 = new ProductOccupation();
        Product product2 = new Product();
        product2.setName("PaintY");
        product2.setToxic(true);
        Supplier supplier2 = new Supplier();
        supplier2.setName("S2");
        product2.setSupplier(supplier2);
        occupation2.setProduct(product2);
        occupation2.setVolume(5.0);
        warehouse.addOccupations(occupation2);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        ProductOccupation occupation3 = new ProductOccupation();
        Product product3 = new Product();
        product3.setName("ToolZ");
        product3.setToxic(false);
        Supplier supplier3 = new Supplier();
        supplier3.setName("S3");
        product3.setSupplier(supplier3);
        occupation3.setProduct(product3);
        occupation3.setVolume(15.0);
        warehouse.addOccupations(occupation3);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve toxic products in W1
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ChemX"));
        assertTrue(result.contains("PaintY"));
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityB");
        warehouse.setSurface(300.0);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        ProductOccupation occupation1 = new ProductOccupation();
        Product product1 = new Product();
        product1.setName("BoxA");
        product1.setToxic(false);
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        product1.setSupplier(supplier1);
        occupation1.setProduct(product1);
        occupation1.setVolume(8.0);
        warehouse.addOccupations(occupation1);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        ProductOccupation occupation2 = new ProductOccupation();
        Product product2 = new Product();
        product2.setName("CableB");
        product2.setToxic(false);
        Supplier supplier2 = new Supplier();
        supplier2.setName("S4");
        product2.setSupplier(supplier2);
        occupation2.setProduct(product2);
        occupation2.setVolume(3.0);
        warehouse.addOccupations(occupation2);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve toxic products in W2
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added)
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityC");
        warehouse.setSurface(200.0);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve toxic products in W3
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Warehouse existingWarehouse = new Warehouse();
        existingWarehouse.setCity("CityD");
        existingWarehouse.setSurface(250.0);
        company.addWarehouses(existingWarehouse);
        
        // Create a non-existent warehouse object (W5) not added to company
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("CityE");
        nonExistentWarehouse.setSurface(350.0);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        // Since we're testing warehouse method directly, we test the non-existent warehouse object
        List<String> result = nonExistentWarehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityD");
        warehouse.setSurface(400.0);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        ProductOccupation occupation = new ProductOccupation();
        Product product = new Product();
        product.setName("AcidK");
        product.setToxic(true);
        Supplier supplier = new Supplier();
        supplier.setName("S5");
        product.setSupplier(supplier);
        occupation.setProduct(product);
        occupation.setVolume(12.0);
        warehouse.addOccupations(occupation);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve toxic products in W6
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, result.size());
        assertEquals("AcidK", result.get(0));
    }
}