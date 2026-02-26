import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        Product chemX = new Product();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        Supplier s1 = new Supplier();
        s1.setName("S1");
        chemX.setSupplier(s1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(chemX);
        occupation1.setVolume(10.0);
        warehouse.addOccupation(occupation1);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Product paintY = new Product();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        Supplier s2 = new Supplier();
        s2.setName("S2");
        paintY.setSupplier(s2);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(paintY);
        occupation2.setVolume(5.0);
        warehouse.addOccupation(occupation2);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Product toolZ = new Product();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        Supplier s3 = new Supplier();
        s3.setName("S3");
        toolZ.setSupplier(s3);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(toolZ);
        occupation3.setVolume(15.0);
        warehouse.addOccupation(occupation3);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W1
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ChemX"));
        assertTrue(result.contains("PaintY"));
        assertFalse(result.contains("ToolZ"));
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityB");
        warehouse.setSurface(300.0);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Product boxA = new Product();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        Supplier s1 = new Supplier();
        s1.setName("S1");
        boxA.setSupplier(s1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(boxA);
        occupation1.setVolume(8.0);
        warehouse.addOccupation(occupation1);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Product cableB = new Product();
        cableB.setName("CableB");
        cableB.setToxic(false);
        Supplier s4 = new Supplier();
        s4.setName("S4");
        cableB.setSupplier(s4);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(cableB);
        occupation2.setVolume(3.0);
        warehouse.addOccupation(occupation2);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W2
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityC");
        warehouse.setSurface(200.0);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W3
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Warehouse warehouseW4 = new Warehouse();
        warehouseW4.setCity("CityD");
        warehouseW4.setSurface(350.0);
        company.addWarehouse(warehouseW4);
        
        // Create a separate warehouse that is not added to the company (W5 doesn't exist in C3)
        Warehouse warehouseW5 = new Warehouse();
        warehouseW5.setCity("CityE");
        warehouseW5.setSurface(250.0);
        
        // Action: Retrieve toxic products in W5 (doesn't exist in C3)
        // Since W5 is not part of the company, we test retrieving from a warehouse not in company
        List<String> result = warehouseW5.retrieveToxicProductNames();
        
        // Expected Output: [] - empty list for non-existent warehouse in the context
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
        Product acidK = new Product();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        Supplier s5 = new Supplier();
        s5.setName("S5");
        acidK.setSupplier(s5);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(acidK);
        occupation.setVolume(12.0);
        warehouse.addOccupation(occupation);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W6
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, result.size());
        assertEquals("AcidK", result.get(0));
    }
}