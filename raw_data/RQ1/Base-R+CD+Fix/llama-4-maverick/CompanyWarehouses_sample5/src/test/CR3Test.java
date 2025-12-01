import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(5.0);
        occupation1.setProduct(p1);
        
        Warehouse w12 = new Warehouse();
        w12.setCity("CityW12");
        w12.setAddress("AddressW12");
        List<ProductOccupation> occupationsW12 = new ArrayList<>();
        occupationsW12.add(occupation1);
        w12.setOccupations(occupationsW12);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(10.0);
        occupation2.setProduct(p2);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("CityW13");
        w13.setAddress("AddressW13");
        List<ProductOccupation> occupationsW13 = new ArrayList<>();
        occupationsW13.add(occupation2);
        w13.setOccupations(occupationsW13);
        
        // Add warehouses to company
        company.getWarehouses().add(w12);
        company.getWarehouses().add(w13);
        
        // Action: List volumes for supplier "S10" in company C1
        Double result = company.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse (already done in setUp)
        // SetUp: Create Supplier "S11" (no products linked)
        Supplier s11 = new Supplier();
        s11.setName("S11");
        
        // Action: List volumes for supplier "S11" in company C2
        Double result = company.countTotalVolumeBySupplier("S11");
        
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
        p1.setSupplier(s10);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(5.0);
        occupation1.setProduct(p1);
        
        Warehouse w12 = new Warehouse();
        w12.setCity("CityW12");
        w12.setAddress("AddressW12");
        List<ProductOccupation> occupationsW12 = new ArrayList<>();
        occupationsW12.add(occupation1);
        w12.setOccupations(occupationsW12);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product p2 = new Product();
        p2.setName("P2");
        p2.setSupplier(s10);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(10.0);
        occupation2.setProduct(p2);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("CityW13");
        w13.setAddress("AddressW13");
        List<ProductOccupation> occupationsW13 = new ArrayList<>();
        occupationsW13.add(occupation2);
        w13.setOccupations(occupationsW13);
        
        // SetUp: Create Supplier "S12" (no products linked)
        Supplier s12 = new Supplier();
        s12.setName("S12");
        
        // Add warehouses to company
        company.getWarehouses().add(w12);
        company.getWarehouses().add(w13);
        
        // Action: List volumes for supplier name "S13" in company C1
        Double result = company.countTotalVolumeBySupplier("S13");
        
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
        p3.setSupplier(s14);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setVolume(8.0);
        occupation.setProduct(p3);
        
        Warehouse w14 = new Warehouse();
        w14.setCity("CityW14");
        w14.setAddress("AddressW14");
        List<ProductOccupation> occupations = new ArrayList<>();
        occupations.add(occupation);
        w14.setOccupations(occupations);
        
        // Add warehouse to company
        company.getWarehouses().add(w14);
        
        // Action: List volumes for supplier "S14" in company C3
        Double result = company.countTotalVolumeBySupplier("S14");
        
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
        x1.setSupplier(s15);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(3.0);
        occupation1.setProduct(x1);
        
        Warehouse w15 = new Warehouse();
        w15.setCity("CityW15");
        w15.setAddress("AddressW15");
        List<ProductOccupation> occupationsW15 = new ArrayList<>();
        occupationsW15.add(occupation1);
        
        // SetUp: Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4
        Product x3 = new Product();
        x3.setName("X3");
        x3.setSupplier(s15);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setVolume(2.0);
        occupation3.setProduct(x3);
        
        occupationsW15.add(occupation3);
        w15.setOccupations(occupationsW15);
        
        // SetUp: Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4
        Product x2 = new Product();
        x2.setName("X2");
        x2.setSupplier(s15);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(7.0);
        occupation2.setProduct(x2);
        
        Warehouse w16 = new Warehouse();
        w16.setCity("CityW16");
        w16.setAddress("AddressW16");
        List<ProductOccupation> occupationsW16 = new ArrayList<>();
        occupationsW16.add(occupation2);
        w16.setOccupations(occupationsW16);
        
        // Add warehouses to company
        company.getWarehouses().add(w15);
        company.getWarehouses().add(w16);
        
        // Action: List volumes for supplier "S15" in company C4
        Double result = company.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}