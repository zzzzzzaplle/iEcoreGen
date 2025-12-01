import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // SetUp: Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityA");
        warehouse.setSurface(500.0);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        
        Product product1 = new Product();
        product1.setName("ChemX");
        product1.setToxic(true);
        product1.setSupplier(supplier1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(10.0);
        occupation1.setProduct(product1);
        warehouse.addOccupations(occupation1);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Supplier supplier2 = new Supplier();
        supplier2.setName("S2");
        
        Product product2 = new Product();
        product2.setName("PaintY");
        product2.setToxic(true);
        product2.setSupplier(supplier2);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(5.0);
        occupation2.setProduct(product2);
        warehouse.addOccupations(occupation2);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Supplier supplier3 = new Supplier();
        supplier3.setName("S3");
        
        Product product3 = new Product();
        product3.setName("ToolZ");
        product3.setToxic(false);
        product3.setSupplier(supplier3);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setVolume(15.0);
        occupation3.setProduct(product3);
        warehouse.addOccupations(occupation3);
        
        // Action: Retrieve toxic products in W1
        List<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, toxicProducts.size());
        assertTrue(toxicProducts.contains("ChemX"));
        assertTrue(toxicProducts.contains("PaintY"));
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityB");
        warehouse.setSurface(300.0);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        
        Product product1 = new Product();
        product1.setName("BoxA");
        product1.setToxic(false);
        product1.setSupplier(supplier1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(8.0);
        occupation1.setProduct(product1);
        warehouse.addOccupations(occupation1);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Supplier supplier2 = new Supplier();
        supplier2.setName("S4");
        
        Product product2 = new Product();
        product2.setName("CableB");
        product2.setToxic(false);
        product2.setSupplier(supplier2);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(3.0);
        occupation2.setProduct(product2);
        warehouse.addOccupations(occupation2);
        
        // Action: Retrieve toxic products in W2
        List<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(toxicProducts.isEmpty());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityC");
        warehouse.setSurface(200.0);
        
        // Action: Retrieve toxic products in W3
        List<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(toxicProducts.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // Note: Since we're testing a single warehouse's method, we'll create a warehouse but don't add it to company
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Company company = new Company();
        Warehouse warehouse4 = new Warehouse();
        warehouse4.setCity("CityD");
        warehouse4.setSurface(350.0);
        company.addWarehouses(warehouse4);
        
        // For this test case, we need to test retrieving toxic products from a warehouse that doesn't exist
        // Since we can't retrieve from non-existent warehouse directly, we'll create a new warehouse that's not in company
        Warehouse warehouse5 = new Warehouse(); // This represents "W5" which doesn't exist in C3
        
        // Action: Retrieve toxic products in W5
        List<String> toxicProducts = warehouse5.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(toxicProducts.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityD");
        warehouse.setSurface(400.0);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        Supplier supplier = new Supplier();
        supplier.setName("S5");
        
        Product product = new Product();
        product.setName("AcidK");
        product.setToxic(true);
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setVolume(12.0);
        occupation.setProduct(product);
        warehouse.addOccupations(occupation);
        
        // Action: Retrieve toxic products in W6
        List<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, toxicProducts.size());
        assertEquals("AcidK", toxicProducts.get(0));
    }
}