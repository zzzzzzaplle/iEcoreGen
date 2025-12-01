import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

public class CR1Test {
    
    private Company company;
    private Warehouse warehouse;
    private Product product1;
    private Product product2;
    private Product product3;
    private Supplier supplier1;
    private Supplier supplier2;
    private Supplier supplier3;
    private ProductOccupation occupation1;
    private ProductOccupation occupation2;
    private ProductOccupation occupation3;
    
    @Before
    public void setUp() {
        // Initialize common objects that might be used across tests
        company = new Company();
        warehouse = new Warehouse();
        supplier1 = new Supplier();
        supplier2 = new Supplier();
        supplier3 = new Supplier();
        product1 = new Product();
        product2 = new Product();
        product3 = new Product();
        occupation1 = new ProductOccupation();
        occupation2 = new ProductOccupation();
        occupation3 = new ProductOccupation();
    }
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // SetUp: Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        Warehouse w1 = new Warehouse();
        w1.setCity("CityA");
        w1.setSurface(500.0);
        
        // SetUp: Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        Supplier s1 = new Supplier();
        s1.setName("S1");
        Product chemX = new Product();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        chemX.setVolume(10.0);
        chemX.setSupplier(s1);
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(chemX);
        occ1.setVolume(10.0);
        w1.addOccupation(occ1);
        
        // SetUp: Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Supplier s2 = new Supplier();
        s2.setName("S2");
        Product paintY = new Product();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        paintY.setVolume(5.0);
        paintY.setSupplier(s2);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(paintY);
        occ2.setVolume(5.0);
        w1.addOccupation(occ2);
        
        // SetUp: Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Supplier s3 = new Supplier();
        s3.setName("S3");
        Product toolZ = new Product();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        toolZ.setVolume(15.0);
        toolZ.setSupplier(s3);
        
        ProductOccupation occ3 = new ProductOccupation();
        occ3.setProduct(toolZ);
        occ3.setVolume(15.0);
        w1.addOccupation(occ3);
        
        // Action: Retrieve toxic products in W1
        List<String> toxicProducts = w1.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        List<String> expected = new ArrayList<>();
        expected.add("ChemX");
        expected.add("PaintY");
        
        assertEquals("Warehouse with multiple toxic products should return correct toxic product names", 
                     expected, toxicProducts);
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse w2 = new Warehouse();
        w2.setCity("CityB");
        w2.setSurface(300.0);
        
        // SetUp: Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Supplier s1 = new Supplier();
        s1.setName("S1");
        Product boxA = new Product();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        boxA.setVolume(8.0);
        boxA.setSupplier(s1);
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(boxA);
        occ1.setVolume(8.0);
        w2.addOccupation(occ1);
        
        // SetUp: Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Supplier s4 = new Supplier();
        s4.setName("S4");
        Product cableB = new Product();
        cableB.setName("CableB");
        cableB.setToxic(false);
        cableB.setVolume(3.0);
        cableB.setSupplier(s4);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(cableB);
        occ2.setVolume(3.0);
        w2.addOccupation(occ2);
        
        // Action: Retrieve toxic products in W2
        List<String> toxicProducts = w2.retrieveToxicProductNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Warehouse with no toxic products should return empty list", 
                     expected, toxicProducts);
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        Warehouse w3 = new Warehouse();
        w3.setCity("CityC");
        w3.setSurface(200.0);
        // No products added intentionally
        
        // Action: Retrieve toxic products in W3
        List<String> toxicProducts = w3.retrieveToxicProductNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Empty warehouse should return empty list", 
                     expected, toxicProducts);
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Company c3 = new Company();
        Warehouse w4 = new Warehouse();
        w4.setCity("CityE");
        w4.setSurface(350.0);
        c3.addWarehouse(w4);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        // Since the method retrieveToxicProductNames() is called on a Warehouse object,
        // and we cannot call it on a non-existent warehouse, this test case needs clarification.
        // Assuming the intent is to test that calling the method on any warehouse (even one not in company)
        // should handle it properly. Since the method doesn't check company membership, 
        // we'll create a separate warehouse not added to any company.
        
        Warehouse w5 = new Warehouse(); // Not added to any company
        w5.setCity("CityF");
        w5.setSurface(250.0);
        
        // Action: Retrieve toxic products in W5 (exists as object but not in company)
        List<String> toxicProducts = w5.retrieveToxicProductNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Warehouse with no products (even if not in company) should return empty list", 
                     expected, toxicProducts);
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse w6 = new Warehouse();
        w6.setCity("CityD");
        w6.setSurface(400.0);
        
        // SetUp: Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        Supplier s5 = new Supplier();
        s5.setName("S5");
        Product acidK = new Product();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        acidK.setVolume(12.0);
        acidK.setSupplier(s5);
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(acidK);
        occ1.setVolume(12.0);
        w6.addOccupation(occ1);
        
        // Action: Retrieve toxic products in W6
        List<String> toxicProducts = w6.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        List<String> expected = new ArrayList<>();
        expected.add("AcidK");
        
        assertEquals("Warehouse with single toxic product should return correct toxic product name", 
                     expected, toxicProducts);
    }
}