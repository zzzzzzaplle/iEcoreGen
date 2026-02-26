import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;

public class CR3Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        Supplier s10 = new Supplier();
        s10.setName("S10");
        
        // Create products from S10
        Product p1 = new Product();
        p1.setName("P1");
        p1.setSupplier(s10);
        
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        
        // Create product occupations
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(p1);
        occ1.setVolume(5.0);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(p2);
        occ2.setVolume(10.0);
        
        // Create warehouses and add occupations
        Warehouse w12 = new Warehouse();
        w12.setCity("City12");
        w12.setAddress("Address12");
        w12.addOccupation(occ1);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("City13");
        w13.setAddress("Address13");
        w13.addOccupation(occ2);
        
        // Add warehouses to company
        company.addWarehouse(w12);
        company.addWarehouse(w13);
        
        // Action: List volumes for supplier "S10" in company C1
        Double result = company.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        // (already created in setUp method)
        
        // Create Supplier "S11" (no products linked)
        Supplier s11 = new Supplier();
        s11.setName("S11");
        // No products added to any warehouse
        
        // Action: List volumes for supplier "S11" in company C2
        Double result = company.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        Supplier s10 = new Supplier();
        s10.setName("S10");
        
        // Create products from S10
        Product p1 = new Product();
        p1.setName("P1");
        p1.setSupplier(s10);
        
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        
        // Create product occupations
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(p1);
        occ1.setVolume(5.0);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(p2);
        occ2.setVolume(10.0);
        
        // Create warehouses and add occupations
        Warehouse w12 = new Warehouse();
        w12.setCity("City12");
        w12.setAddress("Address12");
        w12.addOccupation(occ1);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("City13");
        w13.setAddress("Address13");
        w13.addOccupation(occ2);
        
        // Add warehouses to company
        company.addWarehouse(w12);
        company.addWarehouse(w13);
        
        // Create Supplier "S12" (no products linked)
        Supplier s12 = new Supplier();
        s12.setName("S12");
        // No products from S12 added to warehouses
        
        // Action: List volumes for supplier name "S13" in company C1
        Double result = company.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        Supplier s14 = new Supplier();
        s14.setName("S14");
        
        // Add Product "P3" (volume=8) from S14
        Product p3 = new Product();
        p3.setName("P3");
        p3.setSupplier(s14);
        
        ProductOccupation occ = new ProductOccupation();
        occ.setProduct(p3);
        occ.setVolume(8.0);
        
        // Add to Warehouse "W14" in company C3
        Warehouse w14 = new Warehouse();
        w14.setCity("City14");
        w14.setAddress("Address14");
        w14.addOccupation(occ);
        
        company.addWarehouse(w14);
        
        // Action: List volumes for supplier "S14" in company C3
        Double result = company.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        Supplier s15 = new Supplier();
        s15.setName("S15");
        
        // Create products from S15
        Product x1 = new Product();
        x1.setName("X1");
        x1.setSupplier(s15);
        
        Product x2 = new Product();
        x2.setName("X2");
        x2.setSupplier(s15);
        
        Product x3 = new Product();
        x3.setName("X3");
        x3.setSupplier(s15);
        
        // Create product occupations
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(x1);
        occ1.setVolume(3.0);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(x2);
        occ2.setVolume(7.0);
        
        ProductOccupation occ3 = new ProductOccupation();
        occ3.setProduct(x3);
        occ3.setVolume(2.0);
        
        // Add products to warehouses
        Warehouse w15 = new Warehouse();
        w15.setCity("City15");
        w15.setAddress("Address15");
        w15.addOccupation(occ1);
        w15.addOccupation(occ3); // X1 and X3 in W15
        
        Warehouse w16 = new Warehouse();
        w16.setCity("City16");
        w16.setAddress("Address16");
        w16.addOccupation(occ2); // X2 in W16
        
        // Add warehouses to company
        company.addWarehouse(w15);
        company.addWarehouse(w16);
        
        // Action: List volumes for supplier "S15" in company C4
        Double result = company.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}