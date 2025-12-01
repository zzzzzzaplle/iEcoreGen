import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.AbstractMap;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        Supplier s10 = new Supplier();
        s10.setName("S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Product p1 = new Product();
        p1.setName("P1");
        p1.setSupplier(s10);
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(p1);
        occ1.setVolume(5.0);
        
        Warehouse w12 = new Warehouse();
        w12.setCity("City12");
        w12.setAddress("Address12");
        w12.addOccupations(occ1);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(p2);
        occ2.setVolume(10.0);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("City13");
        w13.setAddress("Address13");
        w13.addOccupations(occ2);
        
        company.addWarehouses(w12);
        company.addWarehouses(w13);
        
        // Action: List volumes for supplier "S10" in company C1
        Double result = company.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(Double.valueOf(15.0), result);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        Company c2 = new Company();
        
        // SetUp: Create Supplier "S11" (no products linked)
        Supplier s11 = new Supplier();
        s11.setName("S11");
        
        // Action: List volumes for supplier "S11" in company C2
        Double result = c2.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(Double.valueOf(0.0), result);
    }
    
    @Test
    public void testCase3_NonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        Supplier s10 = new Supplier();
        s10.setName("S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Product p1 = new Product();
        p1.setName("P1");
        p1.setSupplier(s10);
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(p1);
        occ1.setVolume(5.0);
        
        Warehouse w12 = new Warehouse();
        w12.setCity("City12");
        w12.setAddress("Address12");
        w12.addOccupations(occ1);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(p2);
        occ2.setVolume(10.0);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("City13");
        w13.setAddress("Address13");
        w13.addOccupations(occ2);
        
        // SetUp: Create Supplier "S12" (no products linked)
        Supplier s12 = new Supplier();
        s12.setName("S12");
        
        company.addWarehouses(w12);
        company.addWarehouses(w13);
        
        // Action: List volumes for supplier name "S13" in company C1
        Double result = company.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(Double.valueOf(0.0), result);
    }
    
    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        Supplier s14 = new Supplier();
        s14.setName("S14");
        
        // SetUp: Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3
        Product p3 = new Product();
        p3.setName("P3");
        p3.setSupplier(s14);
        ProductOccupation occ3 = new ProductOccupation();
        occ3.setProduct(p3);
        occ3.setVolume(8.0);
        
        Warehouse w14 = new Warehouse();
        w14.setCity("City14");
        w14.setAddress("Address14");
        w14.addOccupations(occ3);
        
        Company c3 = new Company();
        c3.addWarehouses(w14);
        
        // Action: List volumes for supplier "S14" in company C3
        Double result = c3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(Double.valueOf(8.0), result);
    }
    
    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        Supplier s15 = new Supplier();
        s15.setName("S15");
        
        // SetUp: Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4
        Product x1 = new Product();
        x1.setName("X1");
        x1.setSupplier(s15);
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(x1);
        occ1.setVolume(3.0);
        
        Warehouse w15 = new Warehouse();
        w15.setCity("City15");
        w15.setAddress("Address15");
        w15.addOccupations(occ1);
        
        // SetUp: Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4
        Product x2 = new Product();
        x2.setName("X2");
        x2.setSupplier(s15);
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(x2);
        occ2.setVolume(7.0);
        
        Warehouse w16 = new Warehouse();
        w16.setCity("City16");
        w16.setAddress("Address16");
        w16.addOccupations(occ2);
        
        // SetUp: Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4
        Product x3 = new Product();
        x3.setName("X3");
        x3.setSupplier(s15);
        ProductOccupation occ3 = new ProductOccupation();
        occ3.setProduct(x3);
        occ3.setVolume(2.0);
        
        w15.addOccupations(occ3);
        
        Company c4 = new Company();
        c4.addWarehouses(w15);
        c4.addWarehouses(w16);
        
        // Action: List volumes for supplier "S15" in company C4
        Double result = c4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(Double.valueOf(12.0), result);
    }
}