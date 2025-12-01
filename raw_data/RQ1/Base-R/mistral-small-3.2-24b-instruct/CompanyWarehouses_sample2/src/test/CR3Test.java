import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // Test Case 1: "Supplier with multiple products"
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier supplierS10 = new Supplier("S10", "Address S10");
        
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Product productP1 = new Product("P1", supplierS10, false, 5.0);
        Warehouse warehouseW12 = new Warehouse("W12", 100.0);
        warehouseW12.addProduct(productP1);
        company.addWarehouse(warehouseW12);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Product productP2 = new Product("P2", supplierS10, false, 10.0);
        Warehouse warehouseW13 = new Warehouse("W13", 150.0);
        warehouseW13.addProduct(productP2);
        company.addWarehouse(warehouseW13);
        
        // Action: List volumes for supplier "S10" in company C1.
        double result = company.getTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // Test Case 2: "Supplier with no products"
        // SetUp:
        // 1. Create company C2 without warehouse.
        // (Company is already created in setUp, no warehouses added)
        
        // 2. Create Supplier "S11" (no products linked).
        // (Supplier exists but no products are added to any warehouse)
        
        // Action: List volumes for supplier "S11" in company C2.
        double result = company.getTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // Test Case 3: "Non-existent supplier"
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier supplierS10 = new Supplier("S10", "Address S10");
        
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Product productP1 = new Product("P1", supplierS10, false, 5.0);
        Warehouse warehouseW12 = new Warehouse("W12", 100.0);
        warehouseW12.addProduct(productP1);
        company.addWarehouse(warehouseW12);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Product productP2 = new Product("P2", supplierS10, false, 10.0);
        Warehouse warehouseW13 = new Warehouse("W13", 150.0);
        warehouseW13.addProduct(productP2);
        company.addWarehouse(warehouseW13);
        
        // 4. Create Supplier "S12" (no products linked).
        // (Supplier exists but no products are added to any warehouse)
        
        // Action: List volumes for supplier name "S13" in company C1.
        double result = company.getTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // Test Case 4: "Supplier with single product"
        // SetUp:
        // 1. Add Supplier "S14" to system.
        Supplier supplierS14 = new Supplier("S14", "Address S14");
        
        // 2. Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3.
        Product productP3 = new Product("P3", supplierS14, false, 8.0);
        Warehouse warehouseW14 = new Warehouse("W14", 200.0);
        warehouseW14.addProduct(productP3);
        company.addWarehouse(warehouseW14);
        
        // Action: List volumes for supplier "S14" in company C3.
        double result = company.getTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // Test Case 5: "Supplier with products in multiple warehouses"
        // SetUp:
        // 1. Add Supplier "S15" to system.
        Supplier supplierS15 = new Supplier("S15", "Address S15");
        
        // 2. Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4.
        Product productX1 = new Product("X1", supplierS15, false, 3.0);
        Warehouse warehouseW15 = new Warehouse("W15", 250.0);
        warehouseW15.addProduct(productX1);
        company.addWarehouse(warehouseW15);
        
        // 3. Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4.
        Product productX2 = new Product("X2", supplierS15, false, 7.0);
        Warehouse warehouseW16 = new Warehouse("W16", 300.0);
        warehouseW16.addProduct(productX2);
        company.addWarehouse(warehouseW16);
        
        // 4. Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4.
        Product productX3 = new Product("X3", supplierS15, false, 2.0);
        warehouseW15.addProduct(productX3);
        
        // Action: List volumes for supplier "S15" in company C4.
        double result = company.getTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}