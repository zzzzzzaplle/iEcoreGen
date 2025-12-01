import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;

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
        // Initialize common objects that might be used across multiple tests
        company = new Company();
        warehouse1 = new Warehouse();
        warehouse2 = new Warehouse();
        warehouse3 = new Warehouse();
        supplierS10 = new Supplier();
        supplierS11 = new Supplier();
        supplierS12 = new Supplier();
        supplierS14 = new Supplier();
        supplierS15 = new Supplier();
        productP1 = new Product();
        productP2 = new Product();
        productP3 = new Product();
        productX1 = new Product();
        productX2 = new Product();
        productX3 = new Product();
        occupation1 = new ProductOccupation();
        occupation2 = new ProductOccupation();
        occupation3 = new ProductOccupation();
        occupation4 = new ProductOccupation();
        occupation5 = new ProductOccupation();
        occupation6 = new ProductOccupation();
    }

    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        supplierS10.setName("S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        occupation1.setVolume(5.0);
        occupation1.setProduct(productP1);
        
        warehouse1.setCity("City1");
        warehouse1.setAddress("W12");
        warehouse1.addOccupations(occupation1);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        occupation2.setVolume(10.0);
        occupation2.setProduct(productP2);
        
        warehouse2.setCity("City2");
        warehouse2.setAddress("W13");
        warehouse2.addOccupations(occupation2);
        
        company.addWarehouses(warehouse1);
        company.addWarehouses(warehouse2);
        
        // Action: List volumes for supplier "S10" in company C1
        Double result = company.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }

    @Test
    public void testCase2_SupplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        Company companyC2 = new Company();
        
        // SetUp: Create Supplier "S11" (no products linked)
        supplierS11.setName("S11");
        
        // Action: List volumes for supplier "S11" in company C2
        Double result = companyC2.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase3_NonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        supplierS10.setName("S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        occupation1.setVolume(5.0);
        occupation1.setProduct(productP1);
        
        warehouse1.setCity("City1");
        warehouse1.setAddress("W12");
        warehouse1.addOccupations(occupation1);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        occupation2.setVolume(10.0);
        occupation2.setProduct(productP2);
        
        warehouse2.setCity("City2");
        warehouse2.setAddress("W13");
        warehouse2.addOccupations(occupation2);
        
        // SetUp: Create Supplier "S12" (no products linked)
        supplierS12.setName("S12");
        
        company.addWarehouses(warehouse1);
        company.addWarehouses(warehouse2);
        
        // Action: List volumes for supplier name "S13" in company C1
        Double result = company.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        supplierS14.setName("S14");
        
        // SetUp: Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3
        productP3.setName("P3");
        productP3.setSupplier(supplierS14);
        occupation3.setVolume(8.0);
        occupation3.setProduct(productP3);
        
        warehouse3.setCity("City3");
        warehouse3.setAddress("W14");
        warehouse3.addOccupations(occupation3);
        
        Company companyC3 = new Company();
        companyC3.addWarehouses(warehouse3);
        
        // Action: List volumes for supplier "S14" in company C3
        Double result = companyC3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }

    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        supplierS15.setName("S15");
        
        // SetUp: Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4
        productX1.setName("X1");
        productX1.setSupplier(supplierS15);
        occupation4.setVolume(3.0);
        occupation4.setProduct(productX1);
        
        Warehouse warehouseW15 = new Warehouse();
        warehouseW15.setCity("City4");
        warehouseW15.setAddress("W15");
        warehouseW15.addOccupations(occupation4);
        
        // SetUp: Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4
        productX2.setName("X2");
        productX2.setSupplier(supplierS15);
        occupation5.setVolume(7.0);
        occupation5.setProduct(productX2);
        
        Warehouse warehouseW16 = new Warehouse();
        warehouseW16.setCity("City5");
        warehouseW16.setAddress("W16");
        warehouseW16.addOccupations(occupation5);
        
        // SetUp: Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4
        productX3.setName("X3");
        productX3.setSupplier(supplierS15);
        occupation6.setVolume(2.0);
        occupation6.setProduct(productX3);
        
        warehouseW15.addOccupations(occupation6);
        
        Company companyC4 = new Company();
        companyC4.addWarehouses(warehouseW15);
        companyC4.addWarehouses(warehouseW16);
        
        // Action: List volumes for supplier "S15" in company C4
        Double result = companyC4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}