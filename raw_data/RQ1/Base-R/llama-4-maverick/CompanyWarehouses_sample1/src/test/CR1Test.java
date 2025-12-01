import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // SetUp: Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        Warehouse w1 = new Warehouse("CityA", "W1", 500.0);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        Supplier s1 = new Supplier("S1", "Address1");
        Product chemX = new Product("ChemX", s1, true, 10.0);
        w1.getProducts().add(chemX);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Supplier s2 = new Supplier("S2", "Address2");
        Product paintY = new Product("PaintY", s2, true, 5.0);
        w1.getProducts().add(paintY);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Supplier s3 = new Supplier("S3", "Address3");
        Product toolZ = new Product("ToolZ", s3, false, 15.0);
        w1.getProducts().add(toolZ);
        
        // Add warehouse to company
        company.getWarehouses().add(w1);
        
        // Action: Retrieve toxic products in W1
        List<String> result = company.getToxicProductsInWarehouse("W1");
        
        // Expected Output: ["ChemX", "PaintY"]
        List<String> expected = new ArrayList<>();
        expected.add("ChemX");
        expected.add("PaintY");
        
        assertEquals("Should return all toxic product names from warehouse W1", expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse w2 = new Warehouse("CityB", "W2", 300.0);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Supplier s1 = new Supplier("S1", "Address1");
        Product boxA = new Product("BoxA", s1, false, 8.0);
        w2.getProducts().add(boxA);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Supplier s4 = new Supplier("S4", "Address4");
        Product cableB = new Product("CableB", s4, false, 3.0);
        w2.getProducts().add(cableB);
        
        // Add warehouse to company
        company.getWarehouses().add(w2);
        
        // Action: Retrieve toxic products in W2
        List<String> result = company.getToxicProductsInWarehouse("W2");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list when warehouse has no toxic products", expected, result);
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        Warehouse w3 = new Warehouse("CityC", "W3", 200.0);
        
        // Add warehouse to company
        company.getWarehouses().add(w3);
        
        // Action: Retrieve toxic products in W3
        List<String> result = company.getToxicProductsInWarehouse("W3");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list when warehouse has no products", expected, result);
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Warehouse w4 = new Warehouse("CityD", "W4", 350.0);
        company.getWarehouses().add(w4);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        List<String> result = company.getToxicProductsInWarehouse("W5");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list when warehouse does not exist", expected, result);
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse w6 = new Warehouse("CityD", "W6", 400.0);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        Supplier s5 = new Supplier("S5", "Address5");
        Product acidK = new Product("AcidK", s5, true, 12.0);
        w6.getProducts().add(acidK);
        
        // Add warehouse to company
        company.getWarehouses().add(w6);
        
        // Action: Retrieve toxic products in W6
        List<String> result = company.getToxicProductsInWarehouse("W6");
        
        // Expected Output: ["AcidK"]
        List<String> expected = new ArrayList<>();
        expected.add("AcidK");
        
        assertEquals("Should return single toxic product name from warehouse W6", expected, result);
    }
}