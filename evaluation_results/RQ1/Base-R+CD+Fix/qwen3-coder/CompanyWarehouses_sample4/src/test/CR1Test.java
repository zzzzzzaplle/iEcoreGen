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
        Warehouse w1 = new Warehouse();
        w1.setCity("CityA");
        w1.setSurface(500.0);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        Supplier s1 = new Supplier();
        s1.setName("S1");
        Product chemX = new Product();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        chemX.setSupplier(s1);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(chemX);
        occupation1.setVolume(10.0);
        w1.addOccupation(occupation1);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Supplier s2 = new Supplier();
        s2.setName("S2");
        Product paintY = new Product();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        paintY.setSupplier(s2);
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(paintY);
        occupation2.setVolume(5.0);
        w1.addOccupation(occupation2);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Supplier s3 = new Supplier();
        s3.setName("S3");
        Product toolZ = new Product();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        toolZ.setSupplier(s3);
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(toolZ);
        occupation3.setVolume(15.0);
        w1.addOccupation(occupation3);
        
        company.addWarehouse(w1);
        
        // Action: Retrieve toxic products in W1
        List<String> result = w1.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        List<String> expected = Arrays.asList("ChemX", "PaintY");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse w2 = new Warehouse();
        w2.setCity("CityB");
        w2.setSurface(300.0);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Supplier s1 = new Supplier();
        s1.setName("S1");
        Product boxA = new Product();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        boxA.setSupplier(s1);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(boxA);
        occupation1.setVolume(8.0);
        w2.addOccupation(occupation1);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Supplier s4 = new Supplier();
        s4.setName("S4");
        Product cableB = new Product();
        cableB.setName("CableB");
        cableB.setToxic(false);
        cableB.setSupplier(s4);
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(cableB);
        occupation2.setVolume(3.0);
        w2.addOccupation(occupation2);
        
        company.addWarehouse(w2);
        
        // Action: Retrieve toxic products in W2
        List<String> result = w2.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        Warehouse w3 = new Warehouse();
        w3.setCity("CityC");
        w3.setSurface(200.0);
        
        company.addWarehouse(w3);
        
        // Action: Retrieve toxic products in W3
        List<String> result = w3.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Warehouse w4 = new Warehouse();
        w4.setCity("CityE");
        w4.setSurface(350.0);
        
        company.addWarehouse(w4);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        // Since we're testing retrieveToxicProductNames on a specific warehouse object,
        // we need to create a non-existent warehouse scenario differently
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("CityF");
        nonExistentWarehouse.setSurface(250.0);
        // No products added to this warehouse
        
        List<String> result = nonExistentWarehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse w6 = new Warehouse();
        w6.setCity("CityD");
        w6.setSurface(400.0);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        Supplier s5 = new Supplier();
        s5.setName("S5");
        Product acidK = new Product();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        acidK.setSupplier(s5);
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(acidK);
        occupation.setVolume(12.0);
        w6.addOccupation(occupation);
        
        company.addWarehouse(w6);
        
        // Action: Retrieve toxic products in W6
        List<String> result = w6.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        List<String> expected = Arrays.asList("AcidK");
        assertEquals(expected, result);
    }
}