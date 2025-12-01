import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private Warehouse warehouse;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // SetUp: Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        warehouse = new Warehouse();
        warehouse.setCity("CityA");
        warehouse.setSurface(500.0);
        company.addWarehouse(warehouse);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(10.0);
        Product product1 = new Product();
        product1.setName("ChemX");
        product1.setToxic(true);
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        product1.setSupplier(supplier1);
        occupation1.setProduct(product1);
        warehouse.addOccupation(occupation1);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(5.0);
        Product product2 = new Product();
        product2.setName("PaintY");
        product2.setToxic(true);
        Supplier supplier2 = new Supplier();
        supplier2.setName("S2");
        product2.setSupplier(supplier2);
        occupation2.setProduct(product2);
        warehouse.addOccupation(occupation2);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setVolume(15.0);
        Product product3 = new Product();
        product3.setName("ToolZ");
        product3.setToxic(false);
        Supplier supplier3 = new Supplier();
        supplier3.setName("S3");
        product3.setSupplier(supplier3);
        occupation3.setProduct(product3);
        warehouse.addOccupation(occupation3);
        
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
        warehouse = new Warehouse();
        warehouse.setCity("CityB");
        warehouse.setSurface(300.0);
        company.addWarehouse(warehouse);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(8.0);
        Product product1 = new Product();
        product1.setName("BoxA");
        product1.setToxic(false);
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        product1.setSupplier(supplier1);
        occupation1.setProduct(product1);
        warehouse.addOccupation(occupation1);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(3.0);
        Product product2 = new Product();
        product2.setName("CableB");
        product2.setToxic(false);
        Supplier supplier2 = new Supplier();
        supplier2.setName("S4");
        product2.setSupplier(supplier2);
        occupation2.setProduct(product2);
        warehouse.addOccupation(occupation2);
        
        // Action: Retrieve toxic products in W2
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        warehouse = new Warehouse();
        warehouse.setCity("CityC");
        warehouse.setSurface(200.0);
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W3
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Warehouse w4 = new Warehouse();
        w4.setCity("CityE");
        w4.setSurface(350.0);
        company.addWarehouse(w4);
        
        // Create a different warehouse that's not in the company
        Warehouse w5 = new Warehouse();
        w5.setCity("CityF");
        w5.setSurface(250.0);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        List<String> result = w5.retrieveToxicProductNames();
        
        // Expected Output: []
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        warehouse = new Warehouse();
        warehouse.setCity("CityD");
        warehouse.setSurface(400.0);
        company.addWarehouse(warehouse);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        ProductOccupation occupation = new ProductOccupation();
        occupation.setVolume(12.0);
        Product product = new Product();
        product.setName("AcidK");
        product.setToxic(true);
        Supplier supplier = new Supplier();
        supplier.setName("S5");
        product.setSupplier(supplier);
        occupation.setProduct(product);
        warehouse.addOccupation(occupation);
        
        // Action: Retrieve toxic products in W6
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, result.size());
        assertEquals("AcidK", result.get(0));
    }
}