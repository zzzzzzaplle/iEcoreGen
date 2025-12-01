// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR1Test {
    
    private Company company;
    private Warehouse w1, w2, w3, w4, w6;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_warehouseWithMultipleToxicProducts() {
        // Create Warehouse "W1" in "CityA" with surface 500m²
        w1 = new Warehouse();
        w1.setCity("CityA");
        w1.setSurface(500.0);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        Product chemX = new Product();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        Supplier s1 = new Supplier();
        s1.setName("S1");
        chemX.setSupplier(s1);
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(chemX);
        po1.setVolume(10.0);
        w1.addOccupation(po1);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Product paintY = new Product();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        Supplier s2 = new Supplier();
        s2.setName("S2");
        paintY.setSupplier(s2);
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(paintY);
        po2.setVolume(5.0);
        w1.addOccupation(po2);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Product toolZ = new Product();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        Supplier s3 = new Supplier();
        s3.setName("S3");
        toolZ.setSupplier(s3);
        ProductOccupation po3 = new ProductOccupation();
        po3.setProduct(toolZ);
        po3.setVolume(15.0);
        w1.addOccupation(po3);
        
        company.addWarehouse(w1);
        
        // Action: Retrieve toxic products in W1
        List<String> result = w1.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ChemX"));
        assertTrue(result.contains("PaintY"));
    }
    
    @Test
    public void testCase2_warehouseWithNoToxicProducts() {
        // Create Warehouse "W2" in "CityB" with surface 300m²
        w2 = new Warehouse();
        w2.setCity("CityB");
        w2.setSurface(300.0);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Product boxA = new Product();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        Supplier s1 = new Supplier();
        s1.setName("S1");
        boxA.setSupplier(s1);
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(boxA);
        po1.setVolume(8.0);
        w2.addOccupation(po1);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Product cableB = new Product();
        cableB.setName("CableB");
        cableB.setToxic(false);
        Supplier s4 = new Supplier();
        s4.setName("S4");
        cableB.setSupplier(s4);
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(cableB);
        po2.setVolume(3.0);
        w2.addOccupation(po2);
        
        company.addWarehouse(w2);
        
        // Action: Retrieve toxic products in W2
        List<String> result = w2.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // Create Warehouse "W3" in "CityC" with surface 200m² (no products added)
        w3 = new Warehouse();
        w3.setCity("CityC");
        w3.setSurface(200.0);
        
        company.addWarehouse(w3);
        
        // Action: Retrieve toxic products in W3
        List<String> result = w3.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // Initialize company C3 with Warehouse "W4" only
        w4 = new Warehouse();
        w4.setCity("CityD");
        w4.setSurface(150.0);
        company.addWarehouse(w4);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        // Since we can't directly access a non-existent warehouse,
        // we create a new warehouse W5 that is not added to the company
        Warehouse w5 = new Warehouse();
        w5.setCity("CityE");
        w5.setSurface(100.0);
        
        // Expected Output: []
        List<String> result = w5.retrieveToxicProductNames();
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithSingleToxicProduct() {
        // Create Warehouse "W6" in "CityD" with surface 400m²
        w6 = new Warehouse();
        w6.setCity("CityD");
        w6.setSurface(400.0);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        Product acidK = new Product();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        Supplier s5 = new Supplier();
        s5.setName("S5");
        acidK.setSupplier(s5);
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(acidK);
        po1.setVolume(12.0);
        w6.addOccupation(po1);
        
        company.addWarehouse(w6);
        
        // Action: Retrieve toxic products in W6
        List<String> result = w6.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, result.size());
        assertTrue(result.contains("AcidK"));
    }
}