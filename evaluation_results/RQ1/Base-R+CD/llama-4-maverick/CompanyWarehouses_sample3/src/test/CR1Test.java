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
    
    @Before
    public void setUp() {
        // Initialize companies for each test case
        company1 = new Company();
        company2 = new Company();
        company3 = new Company();
        company4 = new Company();
    }
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setCity("CityA");
        warehouse1.setSurface(500);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        Product chemX = new Product();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        Supplier s1 = new Supplier();
        s1.setName("S1");
        chemX.setSupplier(s1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(chemX);
        occupation1.setVolume(10);
        warehouse1.addOccupations(occupation1);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Product paintY = new Product();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        Supplier s2 = new Supplier();
        s2.setName("S2");
        paintY.setSupplier(s2);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(paintY);
        occupation2.setVolume(5);
        warehouse1.addOccupations(occupation2);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Product toolZ = new Product();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        Supplier s3 = new Supplier();
        s3.setName("S3");
        toolZ.setSupplier(s3);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(toolZ);
        occupation3.setVolume(15);
        warehouse1.addOccupations(occupation3);
        
        company1.addWarehouses(warehouse1);
        
        // Action: Retrieve toxic products in W1
        List<String> result = warehouse1.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        List<String> expected = new ArrayList<>();
        expected.add("ChemX");
        expected.add("PaintY");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setCity("CityB");
        warehouse2.setSurface(300);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Product boxA = new Product();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        Supplier s1 = new Supplier();
        s1.setName("S1");
        boxA.setSupplier(s1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(boxA);
        occupation1.setVolume(8);
        warehouse2.addOccupations(occupation1);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Product cableB = new Product();
        cableB.setName("CableB");
        cableB.setToxic(false);
        Supplier s4 = new Supplier();
        s4.setName("S4");
        cableB.setSupplier(s4);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(cableB);
        occupation2.setVolume(3);
        warehouse2.addOccupations(occupation2);
        
        company2.addWarehouses(warehouse2);
        
        // Action: Retrieve toxic products in W2
        List<String> result = warehouse2.retrieveToxicProductNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        Warehouse warehouse3 = new Warehouse();
        warehouse3.setCity("CityC");
        warehouse3.setSurface(200);
        
        company3.addWarehouses(warehouse3);
        
        // Action: Retrieve toxic products in W3
        List<String> result = warehouse3.retrieveToxicProductNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // Initialize company C3 with Warehouse "W4" only
        Warehouse warehouse4 = new Warehouse();
        warehouse4.setCity("CityD");
        warehouse4.setSurface(150);
        company3.addWarehouses(warehouse4);
        
        // Since we're simulating accessing a non-existent warehouse,
        // we'll create a new warehouse W5 that's not part of any company
        Warehouse warehouse5 = new Warehouse();
        warehouse5.setCity("CityE");
        warehouse5.setSurface(100);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        List<String> result = warehouse5.retrieveToxicProductNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse warehouse6 = new Warehouse();
        warehouse6.setCity("CityD");
        warehouse6.setSurface(400);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        Product acidK = new Product();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        Supplier s5 = new Supplier();
        s5.setName("S5");
        acidK.setSupplier(s5);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(acidK);
        occupation.setVolume(12);
        warehouse6.addOccupations(occupation);
        
        company4.addWarehouses(warehouse6);
        
        // Action: Retrieve toxic products in W6
        List<String> result = warehouse6.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        List<String> expected = new ArrayList<>();
        expected.add("AcidK");
        
        assertEquals(expected, result);
    }
}