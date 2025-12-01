import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company companyC1;
    private Company companyC2;
    private Company companyC3;
    private Company companyC4;
    
    @Before
    public void setUp() {
        // Initialize companies for testing
        companyC1 = new Company();
        companyC2 = new Company();
        companyC3 = new Company();
        companyC4 = new Company();
    }
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        Supplier s10 = new Supplier("S10", "Address S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Warehouse w12 = new Warehouse("City1", "W12", 100.0);
        Product p1 = new Product("P1", s10, false, 5.0);
        w12.addProduct(p1);
        companyC1.addWarehouse(w12);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Warehouse w13 = new Warehouse("City2", "W13", 150.0);
        Product p2 = new Product("P2", s10, false, 10.0);
        w13.addProduct(p2);
        companyC1.addWarehouse(w13);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = companyC1.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        // Company C2 is already initialized in setUp()
        
        // SetUp: Create Supplier "S11" (no products linked)
        Supplier s11 = new Supplier("S11", "Address S11");
        // No products added to any warehouse
        
        // Action: List volumes for supplier "S11" in company C2
        double result = companyC2.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        Supplier s10 = new Supplier("S10", "Address S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Warehouse w12 = new Warehouse("City1", "W12", 100.0);
        Product p1 = new Product("P1", s10, false, 5.0);
        w12.addProduct(p1);
        companyC1.addWarehouse(w12);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Warehouse w13 = new Warehouse("City2", "W13", 150.0);
        Product p2 = new Product("P2", s10, false, 10.0);
        w13.addProduct(p2);
        companyC1.addWarehouse(w13);
        
        // SetUp: Create Supplier "S12" (no products linked)
        Supplier s12 = new Supplier("S12", "Address S12");
        // No products added for S12
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = companyC1.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        Supplier s14 = new Supplier("S14", "Address S14");
        
        // SetUp: Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3
        Warehouse w14 = new Warehouse("City3", "W14", 200.0);
        Product p3 = new Product("P3", s14, false, 8.0);
        w14.addProduct(p3);
        companyC3.addWarehouse(w14);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = companyC3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        Supplier s15 = new Supplier("S15", "Address S15");
        
        // SetUp: Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4
        Warehouse w15 = new Warehouse("City4", "W15", 120.0);
        Product x1 = new Product("X1", s15, false, 3.0);
        w15.addProduct(x1);
        companyC4.addWarehouse(w15);
        
        // SetUp: Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4
        Warehouse w16 = new Warehouse("City5", "W16", 180.0);
        Product x2 = new Product("X2", s15, false, 7.0);
        w16.addProduct(x2);
        companyC4.addWarehouse(w16);
        
        // SetUp: Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4
        Product x3 = new Product("X3", s15, false, 2.0);
        w15.addProduct(x3);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = companyC4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}