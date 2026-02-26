import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    /*
     * Test Case 1: "Supplier with multiple products"
     * SetUp:
     * 1. Create Supplier "S10"
     * 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
     * 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
     * Action: List volumes for supplier "S10" in company C1.
     * Expected Output: 15
     */
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // Create supplier S10
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // Create product P1 with volume 5 from supplier S10
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        productP1.setVolume(5.0);
        
        // Create warehouse W12 and add product P1
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setAddress("W12");
        warehouseW12.addProduct(productP1);
        
        // Create product P2 with volume 10 from supplier S10
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        productP2.setVolume(10.0);
        
        // Create warehouse W13 and add product P2
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setAddress("W13");
        warehouseW13.addProduct(productP2);
        
        // Add both warehouses to company
        company.addWarehouse(warehouseW12);
        company.addWarehouse(warehouseW13);
        
        // Test: Get total volume for supplier S10
        double result = company.getTotalVolumeBySupplier("S10");
        
        // Expected output: 15 (5 + 10)
        assertEquals(15.0, result, 0.001);
    }
    
    /*
     * Test Case 2: "Supplier with no products"
     * SetUp:
     * 1. Create company C2 without warehouse.
     * 2. Create Supplier "S11" (no products linked).
     * Action: List volumes for supplier "S11" in company C2.
     * Expected Output: 0
     */
    @Test
    public void testCase2_supplierWithNoProducts() {
        // Company C2 is already created in setUp() with no warehouses
        
        // Create supplier S11 (but no products linked to it)
        Supplier supplierS11 = new Supplier();
        supplierS11.setName("S11");
        
        // Test: Get total volume for supplier S11 in empty company
        double result = company.getTotalVolumeBySupplier("S11");
        
        // Expected output: 0 (no products in company)
        assertEquals(0.0, result, 0.001);
    }
    
    /*
     * Test Case 3: "Non-existent supplier"
     * SetUp:
     * 1. Create Supplier "S10"
     * 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
     * 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
     * 4. Create Supplier "S12" (no products linked).
     * Action: List volumes for supplier name "S13" in company C1.
     * Expected Output: 0
     */
    @Test
    public void testCase3_nonExistentSupplier() {
        // Create supplier S10
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // Create product P1 with volume 5 from supplier S10
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        productP1.setVolume(5.0);
        
        // Create warehouse W12 and add product P1
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setAddress("W12");
        warehouseW12.addProduct(productP1);
        
        // Create product P2 with volume 10 from supplier S10
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        productP2.setVolume(10.0);
        
        // Create warehouse W13 and add product P2
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setAddress("W13");
        warehouseW13.addProduct(productP2);
        
        // Create supplier S12 (no products linked)
        Supplier supplierS12 = new Supplier();
        supplierS12.setName("S12");
        
        // Add warehouses to company
        company.addWarehouse(warehouseW12);
        company.addWarehouse(warehouseW13);
        
        // Test: Get total volume for non-existent supplier S13
        double result = company.getTotalVolumeBySupplier("S13");
        
        // Expected output: 0 (supplier S13 doesn't exist in company)
        assertEquals(0.0, result, 0.001);
    }
    
    /*
     * Test Case 4: "Supplier with single product"
     * SetUp:
     * 1. Add Supplier "S14" to system.
     * 2. Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3.
     * Action: List volumes for supplier "S14" in company C3.
     * Expected Output: 8
     */
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // Create supplier S14
        Supplier supplierS14 = new Supplier();
        supplierS14.setName("S14");
        
        // Create product P3 with volume 8 from supplier S14
        Product productP3 = new Product();
        productP3.setName("P3");
        productP3.setSupplier(supplierS14);
        productP3.setVolume(8.0);
        
        // Create warehouse W14 and add product P3
        Warehouse warehouseW14 = new Warehouse();
        warehouseW14.setAddress("W14");
        warehouseW14.addProduct(productP3);
        
        // Add warehouse to company
        company.addWarehouse(warehouseW14);
        
        // Test: Get total volume for supplier S14
        double result = company.getTotalVolumeBySupplier("S14");
        
        // Expected output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    /*
     * Test Case 5: "Supplier with products in multiple warehouses"
     * SetUp:
     * 1. Add Supplier "S15" to system.
     * 2. Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4.
     * 3. Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4.
     * 4. Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4.
     * Action: List volumes for supplier "S15" in company C4.
     * Expected Output: 12
     */
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // Create supplier S15
        Supplier supplierS15 = new Supplier();
        supplierS15.setName("S15");
        
        // Create product X1 with volume 3 from supplier S15
        Product productX1 = new Product();
        productX1.setName("X1");
        productX1.setSupplier(supplierS15);
        productX1.setVolume(3.0);
        
        // Create product X2 with volume 7 from supplier S15
        Product productX2 = new Product();
        productX2.setName("X2");
        productX2.setSupplier(supplierS15);
        productX2.setVolume(7.0);
        
        // Create product X3 with volume 2 from supplier S15
        Product productX3 = new Product();
        productX3.setName("X3");
        productX3.setSupplier(supplierS15);
        productX3.setVolume(2.0);
        
        // Create warehouse W15 and add products X1 and X3
        Warehouse warehouseW15 = new Warehouse();
        warehouseW15.setAddress("W15");
        warehouseW15.addProduct(productX1);
        warehouseW15.addProduct(productX3);
        
        // Create warehouse W16 and add product X2
        Warehouse warehouseW16 = new Warehouse();
        warehouseW16.setAddress("W16");
        warehouseW16.addProduct(productX2);
        
        // Add both warehouses to company
        company.addWarehouse(warehouseW15);
        company.addWarehouse(warehouseW16);
        
        // Test: Get total volume for supplier S15
        double result = company.getTotalVolumeBySupplier("S15");
        
        // Expected output: 12 (3 + 7 + 2)
        assertEquals(12.0, result, 0.001);
    }
}