import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // Test Case 1: "Supplier with multiple products"
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        productP1.setVolume(5.0);
        
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        productP2.setVolume(10.0);
        
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setAddress("W12");
        warehouseW12.addProduct(productP1);
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setAddress("W13");
        warehouseW13.addProduct(productP2);
        
        Company companyC1 = new Company();
        companyC1.setName("C1");
        companyC1.addWarehouse(warehouseW12);
        companyC1.addWarehouse(warehouseW13);
        
        // Action: List volumes for supplier "S10" in company C1.
        double result = companyC1.getTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // Test Case 2: "Supplier with no products"
        // SetUp:
        // 1. Create company C2 without warehouse.
        Company companyC2 = new Company();
        companyC2.setName("C2");
        
        // 2. Create Supplier "S11" (no products linked).
        Supplier supplierS11 = new Supplier();
        supplierS11.setName("S11");
        
        // Action: List volumes for supplier "S11" in company C2.
        double result = companyC2.getTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NonExistentSupplier() {
        // Test Case 3: "Non-existent supplier"
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        productP1.setVolume(5.0);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        productP2.setVolume(10.0);
        
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setAddress("W12");
        warehouseW12.addProduct(productP1);
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setAddress("W13");
        warehouseW13.addProduct(productP2);
        
        Company companyC1 = new Company();
        companyC1.setName("C1");
        companyC1.addWarehouse(warehouseW12);
        companyC1.addWarehouse(warehouseW13);
        
        // 4. Create Supplier "S12" (no products linked).
        Supplier supplierS12 = new Supplier();
        supplierS12.setName("S12");
        
        // Action: List volumes for supplier name "S13" in company C1.
        double result = companyC1.getTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // Test Case 4: "Supplier with single product"
        // SetUp:
        // 1. Add Supplier "S14" to system.
        Supplier supplierS14 = new Supplier();
        supplierS14.setName("S14");
        
        // 2. Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3.
        Product productP3 = new Product();
        productP3.setName("P3");
        productP3.setSupplier(supplierS14);
        productP3.setVolume(8.0);
        
        Warehouse warehouseW14 = new Warehouse();
        warehouseW14.setAddress("W14");
        warehouseW14.addProduct(productP3);
        
        Company companyC3 = new Company();
        companyC3.setName("C3");
        companyC3.addWarehouse(warehouseW14);
        
        // Action: List volumes for supplier "S14" in company C3.
        double result = companyC3.getTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // Test Case 5: "Supplier with products in multiple warehouses"
        // SetUp:
        // 1. Add Supplier "S15" to system.
        Supplier supplierS15 = new Supplier();
        supplierS15.setName("S15");
        
        // 2. Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4.
        Product productX1 = new Product();
        productX1.setName("X1");
        productX1.setSupplier(supplierS15);
        productX1.setVolume(3.0);
        
        // 3. Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4.
        Product productX2 = new Product();
        productX2.setName("X2");
        productX2.setSupplier(supplierS15);
        productX2.setVolume(7.0);
        
        // 4. Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4.
        Product productX3 = new Product();
        productX3.setName("X3");
        productX3.setSupplier(supplierS15);
        productX3.setVolume(2.0);
        
        Warehouse warehouseW15 = new Warehouse();
        warehouseW15.setAddress("W15");
        warehouseW15.addProduct(productX1);
        warehouseW15.addProduct(productX3);
        
        Warehouse warehouseW16 = new Warehouse();
        warehouseW16.setAddress("W16");
        warehouseW16.addProduct(productX2);
        
        Company companyC4 = new Company();
        companyC4.setName("C4");
        companyC4.addWarehouse(warehouseW15);
        companyC4.addWarehouse(warehouseW16);
        
        // Action: List volumes for supplier "S15" in company C4.
        double result = companyC4.getTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}