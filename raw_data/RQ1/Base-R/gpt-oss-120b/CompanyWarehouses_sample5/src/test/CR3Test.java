import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        company = new Company("C1");
        supplierS10 = new Supplier("S10", "Address S10");
        supplierS11 = new Supplier("S11", "Address S11");
        supplierS12 = new Supplier("S12", "Address S12");
        supplierS14 = new Supplier("S14", "Address S14");
        supplierS15 = new Supplier("S15", "Address S15");
    }
    
    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        // Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Warehouse warehouseW12 = new Warehouse("CityW12", "Address W12", 100.0);
        Product productP1 = new Product("P1", supplierS10, false, 5.0);
        warehouseW12.addProduct(productP1);
        company.addWarehouse(warehouseW12);
        
        // Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Warehouse warehouseW13 = new Warehouse("CityW13", "Address W13", 150.0);
        Product productP2 = new Product("P2", supplierS10, false, 10.0);
        warehouseW13.addProduct(productP2);
        company.addWarehouse(warehouseW13);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = company.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        Company companyC2 = new Company("C2");
        
        // Create Supplier "S11" (no products linked)
        Supplier supplierS11 = new Supplier("S11", "Address S11");
        
        // Action: List volumes for supplier "S11" in company C2
        double result = companyC2.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        // Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Warehouse warehouseW12 = new Warehouse("CityW12", "Address W12", 100.0);
        Product productP1 = new Product("P1", supplierS10, false, 5.0);
        warehouseW12.addProduct(productP1);
        company.addWarehouse(warehouseW12);
        
        // Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Warehouse warehouseW13 = new Warehouse("CityW13", "Address W13", 150.0);
        Product productP2 = new Product("P2", supplierS10, false, 10.0);
        warehouseW13.addProduct(productP2);
        company.addWarehouse(warehouseW13);
        
        // Create Supplier "S12" (no products linked)
        Supplier supplierS12 = new Supplier("S12", "Address S12");
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = company.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        // Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3
        Company companyC3 = new Company("C3");
        Warehouse warehouseW14 = new Warehouse("CityW14", "Address W14", 120.0);
        Product productP3 = new Product("P3", supplierS14, false, 8.0);
        warehouseW14.addProduct(productP3);
        companyC3.addWarehouse(warehouseW14);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = companyC3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        Company companyC4 = new Company("C4");
        
        // Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4
        Warehouse warehouseW15 = new Warehouse("CityW15", "Address W15", 200.0);
        Product productX1 = new Product("X1", supplierS15, false, 3.0);
        warehouseW15.addProduct(productX1);
        companyC4.addWarehouse(warehouseW15);
        
        // Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4
        Warehouse warehouseW16 = new Warehouse("CityW16", "Address W16", 180.0);
        Product productX2 = new Product("X2", supplierS15, false, 7.0);
        warehouseW16.addProduct(productX2);
        companyC4.addWarehouse(warehouseW16);
        
        // Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4
        Product productX3 = new Product("X3", supplierS15, false, 2.0);
        warehouseW15.addProduct(productX3);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = companyC4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}