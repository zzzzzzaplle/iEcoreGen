import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        
        // Create suppliers
        Supplier s1 = new Supplier();
        s1.setName("S1");
        Supplier s2 = new Supplier();
        s2.setName("S2");
        Supplier s3 = new Supplier();
        s3.setName("S3");
        
        // Create products
        Product chemX = new Product();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        chemX.setVolume(10.0);
        chemX.setSupplier(s1);
        
        Product paintY = new Product();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        paintY.setVolume(5.0);
        paintY.setSupplier(s2);
        
        Product toolZ = new Product();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        toolZ.setVolume(15.0);
        toolZ.setSupplier(s3);
        
        // Create product occupations and add to warehouse
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(chemX);
        occ1.setVolume(10.0);
        w1.addOccupation(occ1);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(paintY);
        occ2.setVolume(5.0);
        w1.addOccupation(occ2);
        
        ProductOccupation occ3 = new ProductOccupation();
        occ3.setProduct(toolZ);
        occ3.setVolume(15.0);
        w1.addOccupation(occ3);
        
        company.addWarehouse(w1);
        
        // Action: Retrieve toxic products in W1
        List<String> result = w1.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ChemX"));
        assertTrue(result.contains("PaintY"));
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse w2 = new Warehouse();
        w2.setCity("CityB");
        w2.setSurface(300.0);
        
        // Create suppliers
        Supplier s1 = new Supplier();
        s1.setName("S1");
        Supplier s4 = new Supplier();
        s4.setName("S4");
        
        // Create products
        Product boxA = new Product();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        boxA.setVolume(8.0);
        boxA.setSupplier(s1);
        
        Product cableB = new Product();
        cableB.setName("CableB");
        cableB.setToxic(false);
        cableB.setVolume(3.0);
        cableB.setSupplier(s4);
        
        // Create product occupations and add to warehouse
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(boxA);
        occ1.setVolume(8.0);
        w2.addOccupation(occ1);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(cableB);
        occ2.setVolume(3.0);
        w2.addOccupation(occ2);
        
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
        
        // Create a non-existent warehouse for testing
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("NonExistentCity");
        nonExistentWarehouse.setSurface(100.0);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        // Since we're calling retrieveToxicProductNames on a warehouse object,
        // and nonExistentWarehouse has no products, it should return empty list
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
        
        // Create supplier
        Supplier s5 = new Supplier();
        s5.setName("S5");
        
        // Create product
        Product acidK = new Product();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        acidK.setVolume(12.0);
        acidK.setSupplier(s5);
        
        // Create product occupation and add to warehouse
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(acidK);
        occ1.setVolume(12.0);
        w6.addOccupation(occ1);
        
        company.addWarehouse(w6);
        
        // Action: Retrieve toxic products in W6
        List<String> result = w6.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, result.size());
        assertEquals("AcidK", result.get(0));
    }
}