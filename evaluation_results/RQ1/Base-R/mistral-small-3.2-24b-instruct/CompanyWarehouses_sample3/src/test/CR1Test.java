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
        warehouse.setAddress("W1");
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        Product chemX = new Product();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        chemX.setVolume(10);
        Supplier s1 = new Supplier();
        s1.setName("S1");
        chemX.setSupplier(s1);
        warehouse.addProduct(chemX);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Product paintY = new Product();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        paintY.setVolume(5);
        Supplier s2 = new Supplier();
        s2.setName("S2");
        paintY.setSupplier(s2);
        warehouse.addProduct(paintY);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Product toolZ = new Product();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        toolZ.setVolume(15);
        Supplier s3 = new Supplier();
        s3.setName("S3");
        toolZ.setSupplier(s3);
        warehouse.addProduct(toolZ);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W1
        List<String> result = company.getToxicProductsInWarehouse("W1");
        
        // Expected Output: ["ChemX", "PaintY"]
        List<String> expected = new ArrayList<>();
        expected.add("ChemX");
        expected.add("PaintY");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W2");
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Product boxA = new Product();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        boxA.setVolume(8);
        Supplier s1 = new Supplier();
        s1.setName("S1");
        boxA.setSupplier(s1);
        warehouse.addProduct(boxA);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Product cableB = new Product();
        cableB.setName("CableB");
        cableB.setToxic(false);
        cableB.setVolume(3);
        Supplier s4 = new Supplier();
        s4.setName("S4");
        cableB.setSupplier(s4);
        warehouse.addProduct(cableB);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W2
        List<String> result = company.getToxicProductsInWarehouse("W2");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W3");
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W3
        List<String> result = company.getToxicProductsInWarehouse("W3");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W4");
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        List<String> result = company.getToxicProductsInWarehouse("W5");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W6");
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        Product acidK = new Product();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        acidK.setVolume(12);
        Supplier s5 = new Supplier();
        s5.setName("S5");
        acidK.setSupplier(s5);
        warehouse.addProduct(acidK);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve toxic products in W6
        List<String> result = company.getToxicProductsInWarehouse("W6");
        
        // Expected Output: ["AcidK"]
        List<String> expected = new ArrayList<>();
        expected.add("AcidK");
        assertEquals(expected, result);
    }
}