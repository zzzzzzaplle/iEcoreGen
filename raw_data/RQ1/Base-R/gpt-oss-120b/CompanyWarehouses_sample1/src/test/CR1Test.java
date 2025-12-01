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
        Warehouse w1 = new Warehouse("CityA", "AddressA", 500.0);
        company.addWarehouse(w1);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        Supplier s1 = new Supplier("S1", "SupplierAddress1");
        Product chemX = new Product("ChemX", s1, true, 10.0);
        w1.addProduct(chemX);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Supplier s2 = new Supplier("S2", "SupplierAddress2");
        Product paintY = new Product("PaintY", s2, true, 5.0);
        w1.addProduct(paintY);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Supplier s3 = new Supplier("S3", "SupplierAddress3");
        Product toolZ = new Product("ToolZ", s3, false, 15.0);
        w1.addProduct(toolZ);
        
        // Action: Retrieve toxic products in W1
        List<String> result = company.getToxicProductNamesInWarehouse("CityA");
        
        // Expected Output: ["ChemX", "PaintY"]
        List<String> expected = new ArrayList<>();
        expected.add("ChemX");
        expected.add("PaintY");
        
        assertEquals("Should return both toxic product names", expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse w2 = new Warehouse("CityB", "AddressB", 300.0);
        company.addWarehouse(w2);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Supplier s1 = new Supplier("S1", "SupplierAddress1");
        Product boxA = new Product("BoxA", s1, false, 8.0);
        w2.addProduct(boxA);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Supplier s4 = new Supplier("S4", "SupplierAddress4");
        Product cableB = new Product("CableB", s4, false, 3.0);
        w2.addProduct(cableB);
        
        // Action: Retrieve toxic products in W2
        List<String> result = company.getToxicProductNamesInWarehouse("CityB");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list when no toxic products exist", expected, result);
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        Warehouse w3 = new Warehouse("CityC", "AddressC", 200.0);
        company.addWarehouse(w3);
        
        // Action: Retrieve toxic products in W3
        List<String> result = company.getToxicProductNamesInWarehouse("CityC");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list for warehouse with no products", expected, result);
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Warehouse w4 = new Warehouse("CityE", "AddressE", 600.0);
        company.addWarehouse(w4);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        List<String> result = company.getToxicProductNamesInWarehouse("W5");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list for non-existent warehouse", expected, result);
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse w6 = new Warehouse("CityD", "AddressD", 400.0);
        company.addWarehouse(w6);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        Supplier s5 = new Supplier("S5", "SupplierAddress5");
        Product acidK = new Product("AcidK", s5, true, 12.0);
        w6.addProduct(acidK);
        
        // Action: Retrieve toxic products in W6
        List<String> result = company.getToxicProductNamesInWarehouse("CityD");
        
        // Expected Output: ["AcidK"]
        List<String> expected = new ArrayList<>();
        expected.add("AcidK");
        
        assertEquals("Should return single toxic product name", expected, result);
    }
}