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
    public void testCase1_supplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Product product1 = new Product();
        product1.setName("P1");
        product1.setVolume(5.0);
        product1.setSupplier(supplierS10);
        
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setAddress("W12");
        warehouse1.addProduct(product1);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product product2 = new Product();
        product2.setName("P2");
        product2.setVolume(10.0);
        product2.setSupplier(supplierS10);
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setAddress("W13");
        warehouse2.addProduct(product2);
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = company.getTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        Company companyC2 = new Company();
        companyC2.setName("C2");
        
        // SetUp: Create Supplier "S11" (no products linked)
        Supplier supplierS11 = new Supplier();
        supplierS11.setName("S11");
        
        // Action: List volumes for supplier "S11" in company C2
        double result = companyC2.getTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Product product1 = new Product();
        product1.setName("P1");
        product1.setVolume(5.0);
        product1.setSupplier(supplierS10);
        
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setAddress("W12");
        warehouse1.addProduct(product1);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product product2 = new Product();
        product2.setName("P2");
        product2.setVolume(10.0);
        product2.setSupplier(supplierS10);
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setAddress("W13");
        warehouse2.addProduct(product2);
        
        // SetUp: Create Supplier "S12" (no products linked)
        Supplier supplierS12 = new Supplier();
        supplierS12.setName("S12");
        
        company.addWarehouse(warehouse1);
        company.addWarehouse(warehouse2);
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = company.getTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        Supplier supplierS14 = new Supplier();
        supplierS14.setName("S14");
        
        // SetUp: Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3
        Product product = new Product();
        product.setName("P3");
        product.setVolume(8.0);
        product.setSupplier(supplierS14);
        
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W14");
        warehouse.addProduct(product);
        
        Company companyC3 = new Company();
        companyC3.setName("C3");
        companyC3.addWarehouse(warehouse);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = companyC3.getTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        Supplier supplierS15 = new Supplier();
        supplierS15.setName("S15");
        
        // SetUp: Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4
        Product product1 = new Product();
        product1.setName("X1");
        product1.setVolume(3.0);
        product1.setSupplier(supplierS15);
        
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setAddress("W15");
        warehouse1.addProduct(product1);
        
        // SetUp: Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4
        Product product2 = new Product();
        product2.setName("X2");
        product2.setVolume(7.0);
        product2.setSupplier(supplierS15);
        
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setAddress("W16");
        warehouse2.addProduct(product2);
        
        // SetUp: Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4
        Product product3 = new Product();
        product3.setName("X3");
        product3.setVolume(2.0);
        product3.setSupplier(supplierS15);
        
        warehouse1.addProduct(product3);
        
        Company companyC4 = new Company();
        companyC4.setName("C4");
        companyC4.addWarehouse(warehouse1);
        companyC4.addWarehouse(warehouse2);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = companyC4.getTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}