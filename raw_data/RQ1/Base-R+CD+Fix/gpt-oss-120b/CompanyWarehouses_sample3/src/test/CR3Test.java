import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Warehouse warehouse;
    private Supplier supplier;
    private Product product;
    private ProductOccupation occupation;
    
    @Before
    public void setUp() {
        // Reset objects before each test
        company = new Company();
        warehouse = new Warehouse();
        supplier = new Supplier();
        product = new Product();
        occupation = new ProductOccupation();
    }
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // Test Case 1: "Supplier with multiple products"
        // SetUp: Create Supplier "S10", add products to warehouses in company C1
        
        // Create supplier S10
        Supplier s10 = new Supplier();
        s10.setName("S10");
        
        // Create company C1
        Company c1 = new Company();
        
        // Create warehouse W12 and add product P1 from S10 (volume=5)
        Warehouse w12 = new Warehouse();
        Product p1 = new Product();
        p1.setName("P1");
        p1.setSupplier(s10);
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(p1);
        occ1.setVolume(5.0);
        w12.addOccupations(occ1);
        c1.addWarehouses(w12);
        
        // Create warehouse W13 and add product P2 from S10 (volume=10)
        Warehouse w13 = new Warehouse();
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(p2);
        occ2.setVolume(10.0);
        w13.addOccupations(occ2);
        c1.addWarehouses(w13);
        
        // Action: List volumes for supplier "S10" in company C1
        Double result = c1.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // Test Case 2: "Supplier with no products"
        // SetUp: Create company C2 without warehouse, create Supplier "S11"
        
        // Create company C2 (no warehouses added)
        Company c2 = new Company();
        
        // Create supplier S11 (no products linked)
        Supplier s11 = new Supplier();
        s11.setName("S11");
        
        // Action: List volumes for supplier "S11" in company C2
        Double result = c2.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // Test Case 3: "Non-existent supplier"
        // SetUp: Create Supplier S10 with products, create Supplier S12 (no products),
        // but query for non-existent S13
        
        // Create supplier S10
        Supplier s10 = new Supplier();
        s10.setName("S10");
        
        // Create company C1
        Company c1 = new Company();
        
        // Create warehouse W12 and add product P1 from S10 (volume=5)
        Warehouse w12 = new Warehouse();
        Product p1 = new Product();
        p1.setName("P1");
        p1.setSupplier(s10);
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(p1);
        occ1.setVolume(5.0);
        w12.addOccupations(occ1);
        c1.addWarehouses(w12);
        
        // Create warehouse W13 and add product P2 from S10 (volume=10)
        Warehouse w13 = new Warehouse();
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(p2);
        occ2.setVolume(10.0);
        w13.addOccupations(occ2);
        c1.addWarehouses(w13);
        
        // Create supplier S12 (no products linked)
        Supplier s12 = new Supplier();
        s12.setName("S12");
        
        // Action: List volumes for supplier name "S13" in company C1
        Double result = c1.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // Test Case 4: "Supplier with single product"
        // SetUp: Add Supplier "S14" with product P3 in warehouse W14, company C3
        
        // Create supplier S14
        Supplier s14 = new Supplier();
        s14.setName("S14");
        
        // Create company C3
        Company c3 = new Company();
        
        // Create warehouse W14 and add product P3 from S14 (volume=8)
        Warehouse w14 = new Warehouse();
        Product p3 = new Product();
        p3.setName("P3");
        p3.setSupplier(s14);
        ProductOccupation occ = new ProductOccupation();
        occ.setProduct(p3);
        occ.setVolume(8.0);
        w14.addOccupations(occ);
        c3.addWarehouses(w14);
        
        // Action: List volumes for supplier "S14" in company C3
        Double result = c3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // Test Case 5: "Supplier with products in multiple warehouses"
        // SetUp: Add Supplier "S15" with products in warehouses W15 and W16, company C4
        
        // Create supplier S15
        Supplier s15 = new Supplier();
        s15.setName("S15");
        
        // Create company C4
        Company c4 = new Company();
        
        // Create warehouse W15 and add products X1 and X3 from S15
        Warehouse w15 = new Warehouse();
        
        // Product X1 (volume=3) from S15
        Product x1 = new Product();
        x1.setName("X1");
        x1.setSupplier(s15);
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(x1);
        occ1.setVolume(3.0);
        w15.addOccupations(occ1);
        
        // Product X3 (volume=2) from S15
        Product x3 = new Product();
        x3.setName("X3");
        x3.setSupplier(s15);
        ProductOccupation occ3 = new ProductOccupation();
        occ3.setProduct(x3);
        occ3.setVolume(2.0);
        w15.addOccupations(occ3);
        
        c4.addWarehouses(w15);
        
        // Create warehouse W16 and add product X2 from S15 (volume=7)
        Warehouse w16 = new Warehouse();
        Product x2 = new Product();
        x2.setName("X2");
        x2.setSupplier(s15);
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(x2);
        occ2.setVolume(7.0);
        w16.addOccupations(occ2);
        c4.addWarehouses(w16);
        
        // Action: List volumes for supplier "S15" in company C4
        Double result = c4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}