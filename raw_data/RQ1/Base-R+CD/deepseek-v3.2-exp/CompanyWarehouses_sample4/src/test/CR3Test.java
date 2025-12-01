import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;

public class CR3Test {
    
    private Company company;
    private Warehouse warehouse;
    private Supplier supplier;
    private Product product;
    private ProductOccupation occupation;
    
    @Before
    public void setUp() {
        // Common setup that can be reused across tests
        company = new Company();
        warehouse = new Warehouse();
        supplier = new Supplier();
        product = new Product();
        occupation = new ProductOccupation();
    }
    
    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        Supplier s10 = new Supplier();
        s10.setName("S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Product p1 = new Product();
        p1.setName("P1");
        p1.setVolume(5);
        p1.setSupplier(s10);
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(p1);
        occ1.setVolume(5);
        
        Warehouse w12 = new Warehouse();
        w12.setCity("City1");
        w12.setAddress("W12");
        w12.addOccupation(occ1);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product p2 = new Product();
        p2.setName("P2");
        p2.setVolume(10);
        p2.setSupplier(s10);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(p2);
        occ2.setVolume(10);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("City2");
        w13.setAddress("W13");
        w13.addOccupation(occ2);
        
        Company c1 = new Company();
        c1.addWarehouse(w12);
        c1.addWarehouse(w13);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = c1.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        Company c2 = new Company();
        
        // SetUp: Create Supplier "S11" (no products linked)
        Supplier s11 = new Supplier();
        s11.setName("S11");
        
        // Action: List volumes for supplier "S11" in company C2
        double result = c2.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        Supplier s10 = new Supplier();
        s10.setName("S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Product p1 = new Product();
        p1.setName("P1");
        p1.setVolume(5);
        p1.setSupplier(s10);
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(p1);
        occ1.setVolume(5);
        
        Warehouse w12 = new Warehouse();
        w12.setCity("City1");
        w12.setAddress("W12");
        w12.addOccupation(occ1);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product p2 = new Product();
        p2.setName("P2");
        p2.setVolume(10);
        p2.setSupplier(s10);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(p2);
        occ2.setVolume(10);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("City2");
        w13.setAddress("W13");
        w13.addOccupation(occ2);
        
        // SetUp: Create Supplier "S12" (no products linked)
        Supplier s12 = new Supplier();
        s12.setName("S12");
        
        Company c1 = new Company();
        c1.addWarehouse(w12);
        c1.addWarehouse(w13);
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = c1.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        Supplier s14 = new Supplier();
        s14.setName("S14");
        
        // SetUp: Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3
        Product p3 = new Product();
        p3.setName("P3");
        p3.setVolume(8);
        p3.setSupplier(s14);
        
        ProductOccupation occ = new ProductOccupation();
        occ.setProduct(p3);
        occ.setVolume(8);
        
        Warehouse w14 = new Warehouse();
        w14.setCity("City3");
        w14.setAddress("W14");
        w14.addOccupation(occ);
        
        Company c3 = new Company();
        c3.addWarehouse(w14);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = c3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        Supplier s15 = new Supplier();
        s15.setName("S15");
        
        // SetUp: Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4
        Product x1 = new Product();
        x1.setName("X1");
        x1.setVolume(3);
        x1.setSupplier(s15);
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(x1);
        occ1.setVolume(3);
        
        Warehouse w15 = new Warehouse();
        w15.setCity("City4");
        w15.setAddress("W15");
        w15.addOccupation(occ1);
        
        // SetUp: Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4
        Product x2 = new Product();
        x2.setName("X2");
        x2.setVolume(7);
        x2.setSupplier(s15);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(x2);
        occ2.setVolume(7);
        
        Warehouse w16 = new Warehouse();
        w16.setCity("City5");
        w16.setAddress("W16");
        w16.addOccupation(occ2);
        
        // SetUp: Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4
        Product x3 = new Product();
        x3.setName("X3");
        x3.setVolume(2);
        x3.setSupplier(s15);
        
        ProductOccupation occ3 = new ProductOccupation();
        occ3.setProduct(x3);
        occ3.setVolume(2);
        
        w15.addOccupation(occ3);
        
        Company c4 = new Company();
        c4.addWarehouse(w15);
        c4.addWarehouse(w16);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = c4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}