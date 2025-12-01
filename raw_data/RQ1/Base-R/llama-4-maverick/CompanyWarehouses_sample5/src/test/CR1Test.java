import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // SetUp: Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        Warehouse warehouse = new Warehouse("CityA", "W1", 500.0);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        Supplier supplier1 = new Supplier("S1", "Address1");
        Product chemX = new Product("ChemX", supplier1, true, 10.0);
        warehouse.addProduct(chemX);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Supplier supplier2 = new Supplier("S2", "Address2");
        Product paintY = new Product("PaintY", supplier2, true, 5.0);
        warehouse.addProduct(paintY);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Supplier supplier3 = new Supplier("S3", "Address3");
        Product toolZ = new Product("ToolZ", supplier3, false, 15.0);
        warehouse.addProduct(toolZ);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W1
        List<String> result = company.getToxicProductNamesInWarehouse("W1");
        
        // Expected Output: ["ChemX", "PaintY"]
        List<String> expected = Arrays.asList("ChemX", "PaintY");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse warehouse = new Warehouse("CityB", "W2", 300.0);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Supplier supplier1 = new Supplier("S1", "Address1");
        Product boxA = new Product("BoxA", supplier1, false, 8.0);
        warehouse.addProduct(boxA);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Supplier supplier4 = new Supplier("S4", "Address4");
        Product cableB = new Product("CableB", supplier4, false, 3.0);
        warehouse.addProduct(cableB);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W2
        List<String> result = company.getToxicProductNamesInWarehouse("W2");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        Warehouse warehouse = new Warehouse("CityC", "W3", 200.0);
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W3
        List<String> result = company.getToxicProductNamesInWarehouse("W3");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Warehouse warehouse = new Warehouse("CityE", "W4", 350.0);
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        List<String> result = company.getToxicProductNamesInWarehouse("W5");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse warehouse = new Warehouse("CityD", "W6", 400.0);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        Supplier supplier5 = new Supplier("S5", "Address5");
        Product acidK = new Product("AcidK", supplier5, true, 12.0);
        warehouse.addProduct(acidK);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W6
        List<String> result = company.getToxicProductNamesInWarehouse("W6");
        
        // Expected Output: ["AcidK"]
        List<String> expected = Arrays.asList("AcidK");
        assertEquals(expected, result);
    }
}