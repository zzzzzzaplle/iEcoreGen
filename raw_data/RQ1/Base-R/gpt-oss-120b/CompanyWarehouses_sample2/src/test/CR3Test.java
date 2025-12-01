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
        company = null;
    }
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        Supplier supplierS10 = new Supplier("S10", "Address S10");
        
        // SetUp: Create company C1
        company = new Company("C1");
        
        // SetUp: Create Warehouse "W12" and add Product "P1" (volume=5) from S10
        Warehouse warehouseW12 = new Warehouse("City1", "Address W12", 100.0);
        Product productP1 = new Product("P1", false, 5.0, supplierS10);
        warehouseW12.addProduct(productP1);
        company.addWarehouse(warehouseW12);
        
        // SetUp: Create Warehouse "W13" and add Product "P2" (volume=10) from S10
        Warehouse warehouseW13 = new Warehouse("City2", "Address W13", 150.0);
        Product productP2 = new Product("P2", false, 10.0, supplierS10);
        warehouseW13.addProduct(productP2);
        company.addWarehouse(warehouseW13);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = company.getTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        company = new Company("C2");
        
        // SetUp: Create Supplier "S11" (no products linked)
        // No products are added to any warehouse in company C2
        
        // Action: List volumes for supplier "S11" in company C2
        double result = company.getTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        Supplier supplierS10 = new Supplier("S10", "Address S10");
        
        // SetUp: Create company C1
        company = new Company("C1");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Warehouse warehouseW12 = new Warehouse("City1", "Address W12", 100.0);
        Product productP1 = new Product("P1", false, 5.0, supplierS10);
        warehouseW12.addProduct(productP1);
        company.addWarehouse(warehouseW12);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Warehouse warehouseW13 = new Warehouse("City2", "Address W13", 150.0);
        Product productP2 = new Product("P2", false, 10.0, supplierS10);
        warehouseW13.addProduct(productP2);
        company.addWarehouse(warehouseW13);
        
        // SetUp: Create Supplier "S12" (no products linked) - not actually added to any product
        // This step is not needed since we're testing a non-existent supplier
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = company.getTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // SetUp: Create Supplier "S14"
        Supplier supplierS14 = new Supplier("S14", "Address S14");
        
        // SetUp: Create company C3
        company = new Company("C3");
        
        // SetUp: Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3
        Warehouse warehouseW14 = new Warehouse("City3", "Address W14", 200.0);
        Product productP3 = new Product("P3", false, 8.0, supplierS14);
        warehouseW14.addProduct(productP3);
        company.addWarehouse(warehouseW14);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = company.getTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // SetUp: Create Supplier "S15"
        Supplier supplierS15 = new Supplier("S15", "Address S15");
        
        // SetUp: Create company C4
        company = new Company("C4");
        
        // SetUp: Add Product "X1" (volume=3) from S15 to Warehouse "W15" in company C4
        Warehouse warehouseW15 = new Warehouse("City4", "Address W15", 250.0);
        Product productX1 = new Product("X1", false, 3.0, supplierS15);
        warehouseW15.addProduct(productX1);
        company.addWarehouse(warehouseW15);
        
        // SetUp: Add Product "X2" (volume=7) from S15 to Warehouse "W16" in company C4
        Warehouse warehouseW16 = new Warehouse("City5", "Address W16", 300.0);
        Product productX2 = new Product("X2", false, 7.0, supplierS15);
        warehouseW16.addProduct(productX2);
        company.addWarehouse(warehouseW16);
        
        // SetUp: Add Product "X3" (volume=2) from S15 to Warehouse "W15" in company C4
        Product productX3 = new Product("X3", false, 2.0, supplierS15);
        warehouseW15.addProduct(productX3);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = company.getTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}