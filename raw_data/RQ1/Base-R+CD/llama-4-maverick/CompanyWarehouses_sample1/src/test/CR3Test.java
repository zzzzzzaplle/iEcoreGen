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
        
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(p1);
        po1.setVolume(5);
        
        Warehouse w12 = new Warehouse();
        w12.setCity("W12");
        w12.addOccupation(po1);
        companyC1.addWarehouse(w12);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(p2);
        po2.setVolume(10);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("W13");
        w13.addOccupation(po2);
        companyC1.addWarehouse(w13);
        
        // Action: List volumes for supplier "S10" in company C1.
        double result = companyC1.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // SetUp:
        // 1. Create company C2 without warehouse.
        // (Already done in setUp())
        
        // 2. Create Supplier "S11" (no products linked).
        Supplier s11 = new Supplier();
        s11.setName("S11");
        
        // Action: List volumes for supplier "S11" in company C2.
        double result = companyC2.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
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
        
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(p1);
        po1.setVolume(5);
        
        Warehouse w12 = new Warehouse();
        w12.setCity("W12");
        w12.addOccupation(po1);
        companyC1.addWarehouse(w12);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(p2);
        po2.setVolume(10);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("W13");
        w13.addOccupation(po2);
        companyC1.addWarehouse(w13);
        
        // 4. Create Supplier "S12" (no products linked).
        // (Not needed for this test)
        
        // Action: List volumes for supplier name "S13" in company C1.
        double result = companyC1.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
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
        
        ProductOccupation po3 = new ProductOccupation();
        po3.setProduct(p3);
        po3.setVolume(8);
        
        Warehouse w14 = new Warehouse();
        w14.setCity("W14");
        w14.addOccupation(po3);
        companyC3.addWarehouse(w14);
        
        // Action: List volumes for supplier "S14" in company C3.
        double result = companyC3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
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
        
        ProductOccupation pox1 = new ProductOccupation();
        pox1.setProduct(x1);
        pox1.setVolume(3);
        
        Warehouse w15 = new Warehouse();
        w15.setCity("W15");
        w15.addOccupation(pox1);
        
        // 3. Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4.
        Product x2 = new Product();
        x2.setName("X2");
        x2.setSupplier(s15);
        
        ProductOccupation pox2 = new ProductOccupation();
        pox2.setProduct(x2);
        pox2.setVolume(7);
        
        Warehouse w16 = new Warehouse();
        w16.setCity("W16");
        w16.addOccupation(pox2);
        
        // 4. Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4.
        Product x3 = new Product();
        x3.setName("X3");
        x3.setSupplier(s15);
        
        ProductOccupation pox3 = new ProductOccupation();
        pox3.setProduct(x3);
        pox3.setVolume(2);
        
        w15.addOccupation(pox3);
        
        // Add warehouses to company
        companyC4.addWarehouse(w15);
        companyC4.addWarehouse(w16);
        
        // Action: List volumes for supplier "S15" in company C4.
        double result = companyC4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}