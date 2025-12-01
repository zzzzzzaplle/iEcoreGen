import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

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
        
        // Create Supplier S1
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        supplier1.setAddress("Address1");
        
        // Create Product ChemX (toxic=true, volume=10) from Supplier S1
        Product chemX = new Product();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        chemX.setSupplier(supplier1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(chemX);
        occupation1.setVolume(10.0);
        warehouse.addOccupation(occupation1);
        
        // Create Supplier S2
        Supplier supplier2 = new Supplier();
        supplier2.setName("S2");
        supplier2.setAddress("Address2");
        
        // Create Product PaintY (toxic=true, volume=5) from Supplier S2
        Product paintY = new Product();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        paintY.setSupplier(supplier2);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(paintY);
        occupation2.setVolume(5.0);
        warehouse.addOccupation(occupation2);
        
        // Create Supplier S3
        Supplier supplier3 = new Supplier();
        supplier3.setName("S3");
        supplier3.setAddress("Address3");
        
        // Create Product ToolZ (toxic=false, volume=15) from Supplier S3
        Product toolZ = new Product();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        toolZ.setSupplier(supplier3);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(toolZ);
        occupation3.setVolume(15.0);
        warehouse.addOccupation(occupation3);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W1
        List<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, toxicProducts.size());
        assertTrue(toxicProducts.contains("ChemX"));
        assertTrue(toxicProducts.contains("PaintY"));
        assertFalse(toxicProducts.contains("ToolZ"));
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityB");
        warehouse.setSurface(300.0);
        
        // Create Supplier S1
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        supplier1.setAddress("Address1");
        
        // Create Product BoxA (toxic=false, volume=8) from Supplier S1
        Product boxA = new Product();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        boxA.setSupplier(supplier1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(boxA);
        occupation1.setVolume(8.0);
        warehouse.addOccupation(occupation1);
        
        // Create Supplier S4
        Supplier supplier4 = new Supplier();
        supplier4.setName("S4");
        supplier4.setAddress("Address4");
        
        // Create Product CableB (toxic=false, volume=3) from Supplier S4
        Product cableB = new Product();
        cableB.setName("CableB");
        cableB.setToxic(false);
        cableB.setSupplier(supplier4);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(cableB);
        occupation2.setVolume(3.0);
        warehouse.addOccupation(occupation2);
        
        company.addWarehouse(warehouse);
        
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
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W3
        List<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(toxicProducts.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Warehouse warehouseW4 = new Warehouse();
        warehouseW4.setCity("CityE");
        warehouseW4.setSurface(350.0);
        company.addWarehouse(warehouseW4);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        // Since we're testing warehouse-level functionality, we test with a warehouse that doesn't exist in our company
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("CityF");
        nonExistentWarehouse.setSurface(250.0);
        
        List<String> toxicProducts = nonExistentWarehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(toxicProducts.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityD");
        warehouse.setSurface(400.0);
        
        // Create Supplier S5
        Supplier supplier5 = new Supplier();
        supplier5.setName("S5");
        supplier5.setAddress("Address5");
        
        // Create Product AcidK (toxic=true, volume=12) from Supplier S5
        Product acidK = new Product();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        acidK.setSupplier(supplier5);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(acidK);
        occupation.setVolume(12.0);
        warehouse.addOccupation(occupation);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W6
        List<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, toxicProducts.size());
        assertEquals("AcidK", toxicProducts.get(0));
    }
}