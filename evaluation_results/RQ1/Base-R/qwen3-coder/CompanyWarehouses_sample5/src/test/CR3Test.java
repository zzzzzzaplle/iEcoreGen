import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setVolume(5.0);
        productP1.setSupplier(supplierS10);
        
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setAddress("W12");
        warehouseW12.addProduct(productP1);
        company.addWarehouse(warehouseW12);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setVolume(10.0);
        productP2.setSupplier(supplierS10);
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setAddress("W13");
        warehouseW13.addProduct(productP2);
        company.addWarehouse(warehouseW13);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = company.getTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse (already done in setUp)
        // SetUp: Create Supplier "S11" (no products linked)
        // No products are added to any warehouse
        
        // Action: List volumes for supplier "S11" in company C2
        double result = company.getTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setVolume(5.0);
        productP1.setSupplier(supplierS10);
        
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setAddress("W12");
        warehouseW12.addProduct(productP1);
        company.addWarehouse(warehouseW12);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setVolume(10.0);
        productP2.setSupplier(supplierS10);
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setAddress("W13");
        warehouseW13.addProduct(productP2);
        company.addWarehouse(warehouseW13);
        
        // SetUp: Create Supplier "S12" (no products linked) - not needed for this test case
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = company.getTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        Supplier supplierS14 = new Supplier();
        supplierS14.setName("S14");
        
        // SetUp: Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3
        Product productP3 = new Product();
        productP3.setName("P3");
        productP3.setVolume(8.0);
        productP3.setSupplier(supplierS14);
        
        Warehouse warehouseW14 = new Warehouse();
        warehouseW14.setAddress("W14");
        warehouseW14.addProduct(productP3);
        company.addWarehouse(warehouseW14);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = company.getTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        Supplier supplierS15 = new Supplier();
        supplierS15.setName("S15");
        
        // SetUp: Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4
        Product productX1 = new Product();
        productX1.setName("X1");
        productX1.setVolume(3.0);
        productX1.setSupplier(supplierS15);
        
        Warehouse warehouseW15 = new Warehouse();
        warehouseW15.setAddress("W15");
        warehouseW15.addProduct(productX1);
        company.addWarehouse(warehouseW15);
        
        // SetUp: Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4
        Product productX2 = new Product();
        productX2.setName("X2");
        productX2.setVolume(7.0);
        productX2.setSupplier(supplierS15);
        
        Warehouse warehouseW16 = new Warehouse();
        warehouseW16.setAddress("W16");
        warehouseW16.addProduct(productX2);
        company.addWarehouse(warehouseW16);
        
        // SetUp: Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4
        Product productX3 = new Product();
        productX3.setName("X3");
        productX3.setVolume(2.0);
        productX3.setSupplier(supplierS15);
        
        warehouseW15.addProduct(productX3);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = company.getTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}