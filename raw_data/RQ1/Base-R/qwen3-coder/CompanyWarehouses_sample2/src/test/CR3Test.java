import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Supplier supplierS10;
    private Supplier supplierS11;
    private Supplier supplierS12;
    private Supplier supplierS14;
    private Supplier supplierS15;
    
    @Before
    public void setUp() {
        // Initialize common objects that may be used across multiple tests
        company = new Company();
        supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        supplierS11 = new Supplier();
        supplierS11.setName("S11");
        
        supplierS12 = new Supplier();
        supplierS12.setName("S12");
        
        supplierS14 = new Supplier();
        supplierS14.setName("S14");
        
        supplierS15 = new Supplier();
        supplierS15.setName("S15");
    }
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        // Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        // Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        
        // Create warehouse W12
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setAddress("W12");
        
        // Create product P1 with supplier S10 and volume 5
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setVolume(5.0);
        productP1.setSupplier(supplierS10);
        warehouseW12.addProduct(productP1);
        
        // Create warehouse W13
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setAddress("W13");
        
        // Create product P2 with supplier S10 and volume 10
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setVolume(10.0);
        productP2.setSupplier(supplierS10);
        warehouseW13.addProduct(productP2);
        
        // Add warehouses to company
        company.addWarehouse(warehouseW12);
        company.addWarehouse(warehouseW13);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = company.getTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        // Create Supplier "S11" (no products linked)
        Company companyC2 = new Company();
        
        // Action: List volumes for supplier "S11" in company C2
        double result = companyC2.getTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        // Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        // Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        // Create Supplier "S12" (no products linked)
        
        // Create warehouse W12
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setAddress("W12");
        
        // Create product P1 with supplier S10 and volume 5
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setVolume(5.0);
        productP1.setSupplier(supplierS10);
        warehouseW12.addProduct(productP1);
        
        // Create warehouse W13
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setAddress("W13");
        
        // Create product P2 with supplier S10 and volume 10
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setVolume(10.0);
        productP2.setSupplier(supplierS10);
        warehouseW13.addProduct(productP2);
        
        // Add warehouses to company
        company.addWarehouse(warehouseW12);
        company.addWarehouse(warehouseW13);
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = company.getTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        // Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3
        Company companyC3 = new Company();
        
        // Create warehouse W14
        Warehouse warehouseW14 = new Warehouse();
        warehouseW14.setAddress("W14");
        
        // Create product P3 with supplier S14 and volume 8
        Product productP3 = new Product();
        productP3.setName("P3");
        productP3.setVolume(8.0);
        productP3.setSupplier(supplierS14);
        warehouseW14.addProduct(productP3);
        
        // Add warehouse to company
        companyC3.addWarehouse(warehouseW14);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = companyC3.getTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        // Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4
        // Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4
        // Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4
        Company companyC4 = new Company();
        
        // Create warehouse W15
        Warehouse warehouseW15 = new Warehouse();
        warehouseW15.setAddress("W15");
        
        // Create product X1 with supplier S15 and volume 3
        Product productX1 = new Product();
        productX1.setName("X1");
        productX1.setVolume(3.0);
        productX1.setSupplier(supplierS15);
        warehouseW15.addProduct(productX1);
        
        // Create product X3 with supplier S15 and volume 2
        Product productX3 = new Product();
        productX3.setName("X3");
        productX3.setVolume(2.0);
        productX3.setSupplier(supplierS15);
        warehouseW15.addProduct(productX3);
        
        // Create warehouse W16
        Warehouse warehouseW16 = new Warehouse();
        warehouseW16.setAddress("W16");
        
        // Create product X2 with supplier S15 and volume 7
        Product productX2 = new Product();
        productX2.setName("X2");
        productX2.setVolume(7.0);
        productX2.setSupplier(supplierS15);
        warehouseW16.addProduct(productX2);
        
        // Add warehouses to company
        companyC4.addWarehouse(warehouseW15);
        companyC4.addWarehouse(warehouseW16);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = companyC4.getTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}