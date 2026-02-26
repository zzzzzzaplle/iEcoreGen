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
    private Supplier supplierS10;
    private Supplier supplierS11;
    private Supplier supplierS12;
    private Supplier supplierS14;
    private Supplier supplierS15;
    private Product productP1;
    private Product productP2;
    private Product productP3;
    private Product productX1;
    private Product productX2;
    private Product productX3;
    private ProductOccupation occupation1;
    private ProductOccupation occupation2;
    private ProductOccupation occupation3;
    private ProductOccupation occupation4;
    private ProductOccupation occupation5;
    private ProductOccupation occupation6;

    @Before
    public void setUp() {
        // Initialize common objects that might be reused across tests
        company = new Company();
        warehouse1 = new Warehouse();
        warehouse2 = new Warehouse();
        warehouse3 = new Warehouse();
        
        supplierS10 = new Supplier();
        supplierS10.setName("S10");
        supplierS10.setAddress("Address S10");
        
        supplierS11 = new Supplier();
        supplierS11.setName("S11");
        supplierS11.setAddress("Address S11");
        
        supplierS12 = new Supplier();
        supplierS12.setName("S12");
        supplierS12.setAddress("Address S12");
        
        supplierS14 = new Supplier();
        supplierS14.setName("S14");
        supplierS14.setAddress("Address S14");
        
        supplierS15 = new Supplier();
        supplierS15.setName("S15");
        supplierS15.setAddress("Address S15");
        
        productP1 = new Product();
        productP1.setName("P1");
        productP1.setVolume(5.0);
        productP1.setSupplier(supplierS10);
        
        productP2 = new Product();
        productP2.setName("P2");
        productP2.setVolume(10.0);
        productP2.setSupplier(supplierS10);
        
        productP3 = new Product();
        productP3.setName("P3");
        productP3.setVolume(8.0);
        productP3.setSupplier(supplierS14);
        
        productX1 = new Product();
        productX1.setName("X1");
        productX1.setVolume(3.0);
        productX1.setSupplier(supplierS15);
        
        productX2 = new Product();
        productX2.setName("X2");
        productX2.setVolume(7.0);
        productX2.setSupplier(supplierS15);
        
        productX3 = new Product();
        productX3.setName("X3");
        productX3.setVolume(2.0);
        productX3.setSupplier(supplierS15);
        
        occupation1 = new ProductOccupation();
        occupation1.setVolume(5.0);
        occupation1.setProduct(productP1);
        
        occupation2 = new ProductOccupation();
        occupation2.setVolume(10.0);
        occupation2.setProduct(productP2);
        
        occupation3 = new ProductOccupation();
        occupation3.setVolume(8.0);
        occupation3.setProduct(productP3);
        
        occupation4 = new ProductOccupation();
        occupation4.setVolume(3.0);
        occupation4.setProduct(productX1);
        
        occupation5 = new ProductOccupation();
        occupation5.setVolume(7.0);
        occupation5.setProduct(productX2);
        
        occupation6 = new ProductOccupation();
        occupation6.setVolume(2.0);
        occupation6.setProduct(productX3);
    }

    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // Test Case 1: "Supplier with multiple products"
        // SetUp:
        // 1. Create Supplier "S10"
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        
        Company c1 = new Company();
        
        Warehouse w12 = new Warehouse();
        w12.setCity("City W12");
        w12.setAddress("Address W12");
        w12.addOccupation(occupation1);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("City W13");
        w13.setAddress("Address W13");
        w13.addOccupation(occupation2);
        
        c1.addWarehouse(w12);
        c1.addWarehouse(w13);
        
        // Action: List volumes for supplier "S10" in company C1.
        double result = c1.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }

    @Test
    public void testCase2_SupplierWithNoProducts() {
        // Test Case 2: "Supplier with no products"
        // SetUp:
        // 1. Create company C2 without warehouse.
        // 2. Create Supplier "S11" (no products linked).
        
        Company c2 = new Company();
        // No warehouses added to company C2
        
        // Action: List volumes for supplier "S11" in company C2.
        double result = c2.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase3_NonExistentSupplier() {
        // Test Case 3: "Non-existent supplier"
        // SetUp:
        // 1. Create Supplier "S10"
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        // 4. Create Supplier "S12" (no products linked).
        
        Company c1 = new Company();
        
        Warehouse w12 = new Warehouse();
        w12.setCity("City W12");
        w12.setAddress("Address W12");
        w12.addOccupation(occupation1);
        
        Warehouse w13 = new Warehouse();
        w13.setCity("City W13");
        w13.setAddress("Address W13");
        w13.addOccupation(occupation2);
        
        c1.addWarehouse(w12);
        c1.addWarehouse(w13);
        
        // Action: List volumes for supplier name "S13" in company C1.
        double result = c1.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // Test Case 4: "Supplier with single product"
        // SetUp:
        // 1. Add Supplier "S14" to system.
        // 2. Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3.
        
        Company c3 = new Company();
        
        Warehouse w14 = new Warehouse();
        w14.setCity("City W14");
        w14.setAddress("Address W14");
        w14.addOccupation(occupation3);
        
        c3.addWarehouse(w14);
        
        // Action: List volumes for supplier "S14" in company C3.
        double result = c3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }

    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // Test Case 5: "Supplier with products in multiple warehouses"
        // SetUp:
        // 1. Add Supplier "S15" to system.
        // 2. Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4.
        // 3. Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4.
        // 4. Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4.
        
        Company c4 = new Company();
        
        Warehouse w15 = new Warehouse();
        w15.setCity("City W15");
        w15.setAddress("Address W15");
        w15.addOccupation(occupation4); // X1 volume=3
        w15.addOccupation(occupation6); // X3 volume=2
        
        Warehouse w16 = new Warehouse();
        w16.setCity("City W16");
        w16.setAddress("Address W16");
        w16.addOccupation(occupation5); // X2 volume=7
        
        c4.addWarehouse(w15);
        c4.addWarehouse(w16);
        
        // Action: List volumes for supplier "S15" in company C4.
        double result = c4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}