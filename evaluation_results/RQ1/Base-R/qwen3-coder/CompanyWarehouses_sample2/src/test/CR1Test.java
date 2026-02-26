import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test to ensure isolation
        company = new Company();
    }
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // SetUp: Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        Warehouse w1 = new Warehouse();
        w1.setAddress("W1");
        w1.setCity("CityA");
        w1.setSurface(500.0);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        Product chemX = new Product();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        chemX.setVolume(10.0);
        Supplier s1 = new Supplier();
        s1.setName("S1");
        chemX.setSupplier(s1);
        w1.addProduct(chemX);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Product paintY = new Product();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        paintY.setVolume(5.0);
        Supplier s2 = new Supplier();
        s2.setName("S2");
        paintY.setSupplier(s2);
        w1.addProduct(paintY);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Product toolZ = new Product();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        toolZ.setVolume(15.0);
        Supplier s3 = new Supplier();
        s3.setName("S3");
        toolZ.setSupplier(s3);
        w1.addProduct(toolZ);
        
        company.addWarehouse(w1);
        
        // Action: Retrieve toxic products in W1
        List<String> result = company.getToxicProductsInWarehouse("W1");
        
        // Expected Output: ["ChemX", "PaintY"]
        List<String> expected = new ArrayList<>();
        expected.add("ChemX");
        expected.add("PaintY");
        
        assertEquals("Should return all toxic product names", expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse w2 = new Warehouse();
        w2.setAddress("W2");
        w2.setCity("CityB");
        w2.setSurface(300.0);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Product boxA = new Product();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        boxA.setVolume(8.0);
        Supplier s1 = new Supplier();
        s1.setName("S1");
        boxA.setSupplier(s1);
        w2.addProduct(boxA);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Product cableB = new Product();
        cableB.setName("CableB");
        cableB.setToxic(false);
        cableB.setVolume(3.0);
        Supplier s4 = new Supplier();
        s4.setName("S4");
        cableB.setSupplier(s4);
        w2.addProduct(cableB);
        
        company.addWarehouse(w2);
        
        // Action: Retrieve toxic products in W2
        List<String> result = company.getToxicProductsInWarehouse("W2");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list when no toxic products exist", expected, result);
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        Warehouse w3 = new Warehouse();
        w3.setAddress("W3");
        w3.setCity("CityC");
        w3.setSurface(200.0);
        
        company.addWarehouse(w3);
        
        // Action: Retrieve toxic products in W3
        List<String> result = company.getToxicProductsInWarehouse("W3");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list for warehouse with no products", expected, result);
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Warehouse w4 = new Warehouse();
        w4.setAddress("W4");
        w4.setCity("CityD");
        w4.setSurface(350.0);
        
        company.addWarehouse(w4);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        List<String> result = company.getToxicProductsInWarehouse("W5");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list for non-existent warehouse", expected, result);
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse w6 = new Warehouse();
        w6.setAddress("W6");
        w6.setCity("CityD");
        w6.setSurface(400.0);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        Product acidK = new Product();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        acidK.setVolume(12.0);
        Supplier s5 = new Supplier();
        s5.setName("S5");
        acidK.setSupplier(s5);
        w6.addProduct(acidK);
        
        company.addWarehouse(w6);
        
        // Action: Retrieve toxic products in W6
        List<String> result = company.getToxicProductsInWarehouse("W6");
        
        // Expected Output: ["AcidK"]
        List<String> expected = new ArrayList<>();
        expected.add("AcidK");
        
        assertEquals("Should return single toxic product name", expected, result);
    }
}