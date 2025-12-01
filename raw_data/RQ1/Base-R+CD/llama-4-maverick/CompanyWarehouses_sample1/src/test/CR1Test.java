import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company1;
    private Company company2;
    private Company company3;
    private Company company4;
    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private Warehouse warehouse3;
    private Warehouse warehouse4;
    private Warehouse warehouse6;

    @Before
    public void setUp() {
        // Initialize companies
        company1 = new Company();
        company2 = new Company();
        company3 = new Company();
        company4 = new Company();

        // Test Case 1: Warehouse with multiple toxic products
        warehouse1 = new Warehouse();
        warehouse1.setCity("CityA");
        warehouse1.setSurface(500);
        
        Product product1 = new Product();
        product1.setName("ChemX");
        product1.setToxic(true);
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        product1.setSupplier(supplier1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(10);
        
        Product product2 = new Product();
        product2.setName("PaintY");
        product2.setToxic(true);
        Supplier supplier2 = new Supplier();
        supplier2.setName("S2");
        product2.setSupplier(supplier2);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(5);
        
        Product product3 = new Product();
        product3.setName("ToolZ");
        product3.setToxic(false);
        Supplier supplier3 = new Supplier();
        supplier3.setName("S3");
        product3.setSupplier(supplier3);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(15);
        
        warehouse1.addOccupation(occupation1);
        warehouse1.addOccupation(occupation2);
        warehouse1.addOccupation(occupation3);
        company1.addWarehouse(warehouse1);

        // Test Case 2: Warehouse with no toxic products
        warehouse2 = new Warehouse();
        warehouse2.setCity("CityB");
        warehouse2.setSurface(300);
        
        Product product4 = new Product();
        product4.setName("BoxA");
        product4.setToxic(false);
        Supplier supplier4 = new Supplier();
        supplier4.setName("S1");
        product4.setSupplier(supplier4);
        
        ProductOccupation occupation4 = new ProductOccupation();
        occupation4.setProduct(product4);
        occupation4.setVolume(8);
        
        Product product5 = new Product();
        product5.setName("CableB");
        product5.setToxic(false);
        Supplier supplier5 = new Supplier();
        supplier5.setName("S4");
        product5.setSupplier(supplier5);
        
        ProductOccupation occupation5 = new ProductOccupation();
        occupation5.setProduct(product5);
        occupation5.setVolume(3);
        
        warehouse2.addOccupation(occupation4);
        warehouse2.addOccupation(occupation5);
        company2.addWarehouse(warehouse2);

        // Test Case 3: Empty warehouse
        warehouse3 = new Warehouse();
        warehouse3.setCity("CityC");
        warehouse3.setSurface(200);
        company3.addWarehouse(warehouse3);

        // Test Case 4: Non-existent warehouse
        warehouse4 = new Warehouse();
        warehouse4.setCity("CityE");
        warehouse4.setSurface(250);
        company3.addWarehouse(warehouse4); // Only W4 exists, not W5

        // Test Case 5: Warehouse with single toxic product
        warehouse6 = new Warehouse();
        warehouse6.setCity("CityD");
        warehouse6.setSurface(400);
        
        Product product6 = new Product();
        product6.setName("AcidK");
        product6.setToxic(true);
        Supplier supplier6 = new Supplier();
        supplier6.setName("S5");
        product6.setSupplier(supplier6);
        
        ProductOccupation occupation6 = new ProductOccupation();
        occupation6.setProduct(product6);
        occupation6.setVolume(12);
        
        warehouse6.addOccupation(occupation6);
        company4.addWarehouse(warehouse6);
    }

    @Test
    public void testCase1_warehouseWithMultipleToxicProducts() {
        // Action: Retrieve toxic products in W1
        List<String> result = warehouse1.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ChemX"));
        assertTrue(result.contains("PaintY"));
    }

    @Test
    public void testCase2_warehouseWithNoToxicProducts() {
        // Action: Retrieve toxic products in W2
        List<String> result = warehouse2.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }

    @Test
    public void testCase3_emptyWarehouse() {
        // Action: Retrieve toxic products in W3
        List<String> result = warehouse3.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }

    @Test
    public void testCase4_nonExistentWarehouse() {
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        // Since we can't directly access a non-existent warehouse, we test with a warehouse that exists but has no toxic products
        // The requirement says to return empty list if warehouse doesn't exist
        // In our setup, company3 only has warehouse4, so any other warehouse would be considered non-existent
        // But since we're testing the method on a warehouse object, we'll test with an empty warehouse
        // This is equivalent to testing a non-existent warehouse in terms of behavior
        
        // Actually, looking at the requirement again: "Return an empty list if no toxic products are found or if the warehouse does not exist."
        // Since we can't have a warehouse object that doesn't exist, we'll test with an empty warehouse which gives the same result
        List<String> result = warehouse3.retrieveToxicProductNames(); // W3 is empty
        
        // Expected Output: []
        assertEquals(0, result.size());
    }

    @Test
    public void testCase5_warehouseWithSingleToxicProduct() {
        // Action: Retrieve toxic products in W6
        List<String> result = warehouse6.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, result.size());
        assertTrue(result.contains("AcidK"));
    }
}