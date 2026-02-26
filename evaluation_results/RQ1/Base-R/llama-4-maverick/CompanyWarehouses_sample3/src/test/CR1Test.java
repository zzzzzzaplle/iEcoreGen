import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // SetUp: Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        Warehouse warehouse1 = new Warehouse("CityA", "W1", 500.0);
        
        // Add products to warehouse
        Supplier supplier1 = new Supplier("S1", "Address S1");
        Supplier supplier2 = new Supplier("S2", "Address S2");
        Supplier supplier3 = new Supplier("S3", "Address S3");
        
        warehouse1.addProduct(new Product("ChemX", supplier1, true, 10.0));
        warehouse1.addProduct(new Product("PaintY", supplier2, true, 5.0));
        warehouse1.addProduct(new Product("ToolZ", supplier3, false, 15.0));
        
        company.addWarehouse(warehouse1);
        
        // Action: Retrieve toxic products in W1
        List<String> result = company.getToxicProductNamesInWarehouse("W1");
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ChemX"));
        assertTrue(result.contains("PaintY"));
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse warehouse2 = new Warehouse("CityB", "W2", 300.0);
        
        // Add non-toxic products to warehouse
        Supplier supplier1 = new Supplier("S1", "Address S1");
        Supplier supplier4 = new Supplier("S4", "Address S4");
        
        warehouse2.addProduct(new Product("BoxA", supplier1, false, 8.0));
        warehouse2.addProduct(new Product("CableB", supplier4, false, 3.0));
        
        company.addWarehouse(warehouse2);
        
        // Action: Retrieve toxic products in W2
        List<String> result = company.getToxicProductNamesInWarehouse("W2");
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        Warehouse warehouse3 = new Warehouse("CityC", "W3", 200.0);
        company.addWarehouse(warehouse3);
        
        // Action: Retrieve toxic products in W3
        List<String> result = company.getToxicProductNamesInWarehouse("W3");
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Warehouse warehouse4 = new Warehouse("CityE", "W4", 350.0);
        Supplier supplier6 = new Supplier("S6", "Address S6");
        warehouse4.addProduct(new Product("MaterialA", supplier6, true, 7.0));
        company.addWarehouse(warehouse4);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        List<String> result = company.getToxicProductNamesInWarehouse("W5");
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse warehouse6 = new Warehouse("CityD", "W6", 400.0);
        
        // Add single toxic product to warehouse
        Supplier supplier5 = new Supplier("S5", "Address S5");
        warehouse6.addProduct(new Product("AcidK", supplier5, true, 12.0));
        
        company.addWarehouse(warehouse6);
        
        // Action: Retrieve toxic products in W6
        List<String> result = company.getToxicProductNamesInWarehouse("W6");
        
        // Expected Output: ["AcidK"]
        assertEquals(1, result.size());
        assertEquals("AcidK", result.get(0));
    }
}