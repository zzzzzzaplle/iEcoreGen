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
        
        // Create Product "P1" from S10 with volume=5
        Product p1 = new Product();
        p1.setName("P1");
        p1.setSupplier(s10);
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(p1);
        occ1.setVolume(5.0);
        
        // Create Warehouse "W12" and add P1
        Warehouse w12 = new Warehouse();
        w12.setCity("City1");
        w12.setAddress("Address1");
        w12.addOccupation(occ1);
        
        // Create Product "P2" from S10 with volume=10
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(p2);
        occ2.setVolume(10.0);
        
        // Create Warehouse "W13" and add P2
        Warehouse w13 = new Warehouse();
        w13.setCity("City2");
        w13.setAddress("Address2");
        w13.addOccupation(occ2);
        
        // Add both warehouses to company C1
        company.addWarehouse(w12);
        company.addWarehouse(w13);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = company.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        Company c2 = new Company();
        
        // Create Supplier "S11" (no products linked)
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
        
        // Create Product "P1" from S10 with volume=5
        Product p1 = new Product();
        p1.setName("P1");
        p1.setSupplier(s10);
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(p1);
        occ1.setVolume(5.0);
        
        // Create Warehouse "W12" and add P1
        Warehouse w12 = new Warehouse();
        w12.setCity("City1");
        w12.setAddress("Address1");
        w12.addOccupation(occ1);
        
        // Create Product "P2" from S10 with volume=10
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(p2);
        occ2.setVolume(10.0);
        
        // Create Warehouse "W13" and add P2
        Warehouse w13 = new Warehouse();
        w13.setCity("City2");
        w13.setAddress("Address2");
        w13.addOccupation(occ2);
        
        // Add both warehouses to company C1
        company.addWarehouse(w12);
        company.addWarehouse(w13);
        
        // Create Supplier "S12" (no products linked)
        Supplier s12 = new Supplier();
        s12.setName("S12");
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = company.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // SetUp: Create Supplier "S14"
        Supplier s14 = new Supplier();
        s14.setName("S14");
        
        // Create Product "P3" from S14 with volume=8
        Product p3 = new Product();
        p3.setName("P3");
        p3.setSupplier(s14);
        
        ProductOccupation occ3 = new ProductOccupation();
        occ3.setProduct(p3);
        occ3.setVolume(8.0);
        
        // Create Warehouse "W14" and add P3
        Warehouse w14 = new Warehouse();
        w14.setCity("City3");
        w14.setAddress("Address3");
        w14.addOccupation(occ3);
        
        // Add warehouse to company C3
        Company c3 = new Company();
        c3.addWarehouse(w14);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = c3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // SetUp: Create Supplier "S15"
        Supplier s15 = new Supplier();
        s15.setName("S15");
        
        // Create Product "X1" from S15 with volume=3
        Product x1 = new Product();
        x1.setName("X1");
        x1.setSupplier(s15);
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(x1);
        occ1.setVolume(3.0);
        
        // Create Warehouse "W15" and add X1
        Warehouse w15 = new Warehouse();
        w15.setCity("City4");
        w15.setAddress("Address4");
        w15.addOccupation(occ1);
        
        // Create Product "X2" from S15 with volume=7
        Product x2 = new Product();
        x2.setName("X2");
        x2.setSupplier(s15);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(x2);
        occ2.setVolume(7.0);
        
        // Create Warehouse "W16" and add X2
        Warehouse w16 = new Warehouse();
        w16.setCity("City5");
        w16.setAddress("Address5");
        w16.addOccupation(occ2);
        
        // Create Product "X3" from S15 with volume=2
        Product x3 = new Product();
        x3.setName("X3");
        x3.setSupplier(s15);
        
        ProductOccupation occ3 = new ProductOccupation();
        occ3.setProduct(x3);
        occ3.setVolume(2.0);
        
        // Add X3 to Warehouse "W15"
        w15.addOccupation(occ3);
        
        // Add both warehouses to company C4
        Company c4 = new Company();
        c4.addWarehouse(w15);
        c4.addWarehouse(w16);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = c4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}