import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private Warehouse warehouse3;
    private Supplier supplier1;
    private Supplier supplier2;
    private Supplier supplier3;
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private Product product5;
    
    @Before
    public void setUp() {
        company = new Company();
        warehouse1 = new Warehouse(100.0, "Address 1", "City1");
        warehouse2 = new Warehouse(150.0, "Address 2", "City2");
        warehouse3 = new Warehouse(200.0, "Address 3", "City3");
        supplier1 = new Supplier("S10", "Supplier Address 1");
        supplier2 = new Supplier("S11", "Supplier Address 2");
        supplier3 = new Supplier("S12", "Supplier Address 3");
    }
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        Supplier s10 = new Supplier("S10", "Supplier Address");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Product p1 = new Product("P1", false, 5.0, s10);
        Warehouse w12 = new Warehouse(100.0, "Warehouse Address 12", "City12");
        w12.addProduct(p1);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product p2 = new Product("P2", false, 10.0, s10);
        Warehouse w13 = new Warehouse(150.0, "Warehouse Address 13", "City13");
        w13.addProduct(p2);
        
        Company c1 = new Company();
        c1.addWarehouse(w12);
        c1.addWarehouse(w13);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = c1.getTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        Company c2 = new Company();
        
        // SetUp: Create Supplier "S11" (no products linked)
        Supplier s11 = new Supplier("S11", "Supplier Address");
        
        // Action: List volumes for supplier "S11" in company C2
        double result = c2.getTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        Supplier s10 = new Supplier("S10", "Supplier Address");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Product p1 = new Product("P1", false, 5.0, s10);
        Warehouse w12 = new Warehouse(100.0, "Warehouse Address 12", "City12");
        w12.addProduct(p1);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product p2 = new Product("P2", false, 10.0, s10);
        Warehouse w13 = new Warehouse(150.0, "Warehouse Address 13", "City13");
        w13.addProduct(p2);
        
        // SetUp: Create Supplier "S12" (no products linked)
        Supplier s12 = new Supplier("S12", "Supplier Address");
        
        Company c1 = new Company();
        c1.addWarehouse(w12);
        c1.addWarehouse(w13);
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = c1.getTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        Supplier s14 = new Supplier("S14", "Supplier Address");
        
        // SetUp: Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3
        Product p3 = new Product("P3", false, 8.0, s14);
        Warehouse w14 = new Warehouse(120.0, "Warehouse Address 14", "City14");
        w14.addProduct(p3);
        
        Company c3 = new Company();
        c3.addWarehouse(w14);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = c3.getTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        Supplier s15 = new Supplier("S15", "Supplier Address");
        
        // SetUp: Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4
        Product x1 = new Product("X1", false, 3.0, s15);
        Warehouse w15 = new Warehouse(100.0, "Warehouse Address 15", "City15");
        w15.addProduct(x1);
        
        // SetUp: Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4
        Product x2 = new Product("X2", false, 7.0, s15);
        Warehouse w16 = new Warehouse(150.0, "Warehouse Address 16", "City16");
        w16.addProduct(x2);
        
        // SetUp: Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4
        Product x3 = new Product("X3", false, 2.0, s15);
        w15.addProduct(x3);
        
        Company c4 = new Company();
        c4.addWarehouse(w15);
        c4.addWarehouse(w16);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = c4.getTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}