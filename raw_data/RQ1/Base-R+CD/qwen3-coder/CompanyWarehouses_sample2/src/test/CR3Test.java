import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private Company companyC1;
    private Company companyC2;
    private Company companyC3;
    private Company companyC4;
    
    @Before
    public void setUp() {
        // Initialize companies
        companyC1 = new Company();
        companyC2 = new Company();
        companyC3 = new Company();
        companyC4 = new Company();
    }
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier s10 = new Supplier();
        s10.setName("S10");
        
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Product p1 = new Product();
        p1.setName("P1");
        p1.setSupplier(s10);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(p1);
        occupation1.setVolume(5.0);
        
        Warehouse w12 = new Warehouse();
        w12.setCity("W12");
        w12.addOccupation(occupation1);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(p2);
        occupation2.setVolume(10.0);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("W13");
        w13.addOccupation(occupation2);
        
        // Add warehouses to company C1
        companyC1.addWarehouse(w12);
        companyC1.addWarehouse(w13);
        
        // Action: List volumes for supplier "S10" in company C1.
        Double result = companyC1.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(Double.valueOf(15.0), result);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // SetUp:
        // 1. Create company C2 without warehouse.
        // (companyC2 is already created in setUp)
        
        // 2. Create Supplier "S11" (no products linked).
        // Action: List volumes for supplier "S11" in company C2.
        Double result = companyC2.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(Double.valueOf(0.0), result);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier s10 = new Supplier();
        s10.setName("S10");
        
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Product p1 = new Product();
        p1.setName("P1");
        p1.setSupplier(s10);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(p1);
        occupation1.setVolume(5.0);
        
        Warehouse w12 = new Warehouse();
        w12.setCity("W12");
        w12.addOccupation(occupation1);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(p2);
        occupation2.setVolume(10.0);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("W13");
        w13.addOccupation(occupation2);
        
        // Add warehouses to company C1
        companyC1.addWarehouse(w12);
        companyC1.addWarehouse(w13);
        
        // 4. Create Supplier "S12" (no products linked).
        // Action: List volumes for supplier name "S13" in company C1.
        Double result = companyC1.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(Double.valueOf(0.0), result);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // SetUp:
        // 1. Add Supplier "S14" to system.
        Supplier s14 = new Supplier();
        s14.setName("S14");
        
        // 2. Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3.
        Product p3 = new Product();
        p3.setName("P3");
        p3.setSupplier(s14);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(p3);
        occupation3.setVolume(8.0);
        
        Warehouse w14 = new Warehouse();
        w14.setCity("W14");
        w14.addOccupation(occupation3);
        
        // Add warehouse to company C3
        companyC3.addWarehouse(w14);
        
        // Action: List volumes for supplier "S14" in company C3.
        Double result = companyC3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(Double.valueOf(8.0), result);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // SetUp:
        // 1. Add Supplier "S15" to system.
        Supplier s15 = new Supplier();
        s15.setName("S15");
        
        // 2. Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4.
        Product x1 = new Product();
        x1.setName("X1");
        x1.setSupplier(s15);
        
        ProductOccupation occupationX1 = new ProductOccupation();
        occupationX1.setProduct(x1);
        occupationX1.setVolume(3.0);
        
        Warehouse w15 = new Warehouse();
        w15.setCity("W15");
        w15.addOccupation(occupationX1);
        
        // 3. Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4.
        Product x2 = new Product();
        x2.setName("X2");
        x2.setSupplier(s15);
        
        ProductOccupation occupationX2 = new ProductOccupation();
        occupationX2.setProduct(x2);
        occupationX2.setVolume(7.0);
        
        Warehouse w16 = new Warehouse();
        w16.setCity("W16");
        w16.addOccupation(occupationX2);
        
        // 4. Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4.
        Product x3 = new Product();
        x3.setName("X3");
        x3.setSupplier(s15);
        
        ProductOccupation occupationX3 = new ProductOccupation();
        occupationX3.setProduct(x3);
        occupationX3.setVolume(2.0);
        
        w15.addOccupation(occupationX3);
        
        // Add warehouses to company C4
        companyC4.addWarehouse(w15);
        companyC4.addWarehouse(w16);
        
        // Action: List volumes for supplier "S15" in company C4.
        Double result = companyC4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(Double.valueOf(12.0), result);
    }
}