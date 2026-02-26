import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;

public class CR3Test {
    private Company company;
    private Supplier supplierS10;
    private Supplier supplierS11;
    private Supplier supplierS12;
    private Supplier supplierS14;
    private Supplier supplierS15;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        company = new Company();
        supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        supplierS11 = new Supplier();
        supplierS11.setName("S11");
        
        supplierS12 = new Supplier();
        supplierS12.setName("S12");
        
        supplierS14 = new Supplier();
        supplierS14.setName("S14");
        
        supplierS15 = new Supplier();
        supplierS15.setName("S15");
    }
    
    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        // Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        // Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        
        // Create warehouses
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setCity("City1");
        warehouseW12.setAddress("Address1");
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setCity("City2");
        warehouseW13.setAddress("Address2");
        
        // Create products for S10
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(5.0);
        occupation1.setProduct(productP1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(10.0);
        occupation2.setProduct(productP2);
        
        // Add occupations to warehouses
        warehouseW12.addOccupation(occupation1);
        warehouseW13.addOccupation(occupation2);
        
        // Add warehouses to company
        company.addWarehouse(warehouseW12);
        company.addWarehouse(warehouseW13);
        
        // Action: List volumes for supplier "S10" in company C1
        Double result = company.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        // Create Supplier "S11" (no products linked)
        Company companyC2 = new Company();
        
        // Action: List volumes for supplier "S11" in company C2
        Double result = companyC2.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        // Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        // Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        // Create Supplier "S12" (no products linked)
        
        // Create warehouses
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setCity("City1");
        warehouseW12.setAddress("Address1");
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setCity("City2");
        warehouseW13.setAddress("Address2");
        
        // Create products for S10
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(5.0);
        occupation1.setProduct(productP1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(10.0);
        occupation2.setProduct(productP2);
        
        // Add occupations to warehouses
        warehouseW12.addOccupation(occupation1);
        warehouseW13.addOccupation(occupation2);
        
        // Add warehouses to company
        company.addWarehouse(warehouseW12);
        company.addWarehouse(warehouseW13);
        
        // Action: List volumes for supplier name "S13" in company C1
        Double result = company.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        // Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3
        Company companyC3 = new Company();
        
        Warehouse warehouseW14 = new Warehouse();
        warehouseW14.setCity("City3");
        warehouseW14.setAddress("Address3");
        
        Product productP3 = new Product();
        productP3.setName("P3");
        productP3.setSupplier(supplierS14);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setVolume(8.0);
        occupation.setProduct(productP3);
        
        warehouseW14.addOccupation(occupation);
        companyC3.addWarehouse(warehouseW14);
        
        // Action: List volumes for supplier "S14" in company C3
        Double result = companyC3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        // Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4
        // Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4
        // Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4
        Company companyC4 = new Company();
        
        // Create warehouses
        Warehouse warehouseW15 = new Warehouse();
        warehouseW15.setCity("City4");
        warehouseW15.setAddress("Address4");
        
        Warehouse warehouseW16 = new Warehouse();
        warehouseW16.setCity("City5");
        warehouseW16.setAddress("Address5");
        
        // Create products for S15
        Product productX1 = new Product();
        productX1.setName("X1");
        productX1.setSupplier(supplierS15);
        
        Product productX2 = new Product();
        productX2.setName("X2");
        productX2.setSupplier(supplierS15);
        
        Product productX3 = new Product();
        productX3.setName("X3");
        productX3.setSupplier(supplierS15);
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(3.0);
        occupation1.setProduct(productX1);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(7.0);
        occupation2.setProduct(productX2);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setVolume(2.0);
        occupation3.setProduct(productX3);
        
        // Add occupations to warehouses
        warehouseW15.addOccupation(occupation1);
        warehouseW16.addOccupation(occupation2);
        warehouseW15.addOccupation(occupation3);
        
        // Add warehouses to company
        companyC4.addWarehouse(warehouseW15);
        companyC4.addWarehouse(warehouseW16);
        
        // Action: List volumes for supplier "S15" in company C4
        Double result = companyC4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}