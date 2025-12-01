import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(5);
        occupation1.setProduct(productP1);
        
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setCity("City12");
        warehouseW12.setAddress("Address12");
        warehouseW12.addOccupations(occupation1);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(10);
        occupation2.setProduct(productP2);
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setCity("City13");
        warehouseW13.setAddress("Address13");
        warehouseW13.addOccupations(occupation2);
        
        company.addWarehouses(warehouseW12);
        company.addWarehouses(warehouseW13);
        
        // Action: List volumes for supplier "S10" in company C1.
        double result = company.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // SetUp:
        // 1. Create company C2 without warehouse.
        // (Already created in setUp)
        
        // 2. Create Supplier "S11" (no products linked).
        // No action needed since no products are linked
        
        // Action: List volumes for supplier "S11" in company C2.
        double result = company.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NonExistentSupplier() {
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(5);
        occupation1.setProduct(productP1);
        
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setCity("City12");
        warehouseW12.setAddress("Address12");
        warehouseW12.addOccupations(occupation1);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(10);
        occupation2.setProduct(productP2);
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setCity("City13");
        warehouseW13.setAddress("Address13");
        warehouseW13.addOccupations(occupation2);
        
        // 4. Create Supplier "S12" (no products linked).
        // No action needed since no products are linked
        
        company.addWarehouses(warehouseW12);
        company.addWarehouses(warehouseW13);
        
        // Action: List volumes for supplier name "S13" in company C1.
        double result = company.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // SetUp:
        // 1. Add Supplier "S14" to system.
        Supplier supplierS14 = new Supplier();
        supplierS14.setName("S14");
        
        // 2. Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3.
        Product productP3 = new Product();
        productP3.setName("P3");
        productP3.setSupplier(supplierS14);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setVolume(8);
        occupation.setProduct(productP3);
        
        Warehouse warehouseW14 = new Warehouse();
        warehouseW14.setCity("City14");
        warehouseW14.setAddress("Address14");
        warehouseW14.addOccupations(occupation);
        
        company.addWarehouses(warehouseW14);
        
        // Action: List volumes for supplier "S14" in company C3.
        double result = company.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // SetUp:
        // 1. Add Supplier "S15" to system.
        Supplier supplierS15 = new Supplier();
        supplierS15.setName("S15");
        
        // 2. Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4.
        Product productX1 = new Product();
        productX1.setName("X1");
        productX1.setSupplier(supplierS15);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(3);
        occupation1.setProduct(productX1);
        
        Warehouse warehouseW15 = new Warehouse();
        warehouseW15.setCity("City15");
        warehouseW15.setAddress("Address15");
        warehouseW15.addOccupations(occupation1);
        
        // 3. Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4.
        Product productX2 = new Product();
        productX2.setName("X2");
        productX2.setSupplier(supplierS15);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(7);
        occupation2.setProduct(productX2);
        
        Warehouse warehouseW16 = new Warehouse();
        warehouseW16.setCity("City16");
        warehouseW16.setAddress("Address16");
        warehouseW16.addOccupations(occupation2);
        
        // 4. Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4.
        Product productX3 = new Product();
        productX3.setName("X3");
        productX3.setSupplier(supplierS15);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setVolume(2);
        occupation3.setProduct(productX3);
        
        warehouseW15.addOccupations(occupation3);
        
        company.addWarehouses(warehouseW15);
        company.addWarehouses(warehouseW16);
        
        // Action: List volumes for supplier "S15" in company C4.
        double result = company.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}