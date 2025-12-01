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
    public void testCase1_SupplierWithMultipleProducts() {
        // Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // Create Product "P1" from S10 with volume=5
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productP1);
        occupation1.setVolume(5);
        
        // Create Product "P2" from S10 with volume=10
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productP2);
        occupation2.setVolume(10);
        
        // Create Warehouse "W12" and add product P1
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setCity("City1");
        warehouseW12.setAddress("Address1");
        warehouseW12.addOccupation(occupation1);
        
        // Create Warehouse "W13" and add product P2
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setCity("City2");
        warehouseW13.setAddress("Address2");
        warehouseW13.addOccupation(occupation2);
        
        // Add warehouses to company C1
        company.addWarehouse(warehouseW12);
        company.addWarehouse(warehouseW13);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = company.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // Create company C2 without warehouse (already empty from setUp)
        // Create Supplier "S11" (no products linked)
        Supplier supplierS11 = new Supplier();
        supplierS11.setName("S11");
        
        // No products or warehouses added to company
        
        // Action: List volumes for supplier "S11" in company C2
        double result = company.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NonExistentSupplier() {
        // Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // Create Product "P1" from S10 with volume=5
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productP1);
        occupation1.setVolume(5);
        
        // Create Product "P2" from S10 with volume=10
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productP2);
        occupation2.setVolume(10);
        
        // Create Warehouse "W12" and add product P1
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setCity("City1");
        warehouseW12.setAddress("Address1");
        warehouseW12.addOccupation(occupation1);
        
        // Create Warehouse "W13" and add product P2
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setCity("City2");
        warehouseW13.setAddress("Address2");
        warehouseW13.addOccupation(occupation2);
        
        // Add warehouses to company C1
        company.addWarehouse(warehouseW12);
        company.addWarehouse(warehouseW13);
        
        // Create Supplier "S12" (no products linked) - not added to any products
        Supplier supplierS12 = new Supplier();
        supplierS12.setName("S12");
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = company.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // Create Supplier "S14"
        Supplier supplierS14 = new Supplier();
        supplierS14.setName("S14");
        
        // Create Product "P3" from S14 with volume=8
        Product productP3 = new Product();
        productP3.setName("P3");
        productP3.setSupplier(supplierS14);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(productP3);
        occupation.setVolume(8);
        
        // Create Warehouse "W14" and add product P3
        Warehouse warehouseW14 = new Warehouse();
        warehouseW14.setCity("City3");
        warehouseW14.setAddress("Address3");
        warehouseW14.addOccupation(occupation);
        
        // Add warehouse to company C3
        company.addWarehouse(warehouseW14);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = company.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // Create Supplier "S15"
        Supplier supplierS15 = new Supplier();
        supplierS15.setName("S15");
        
        // Create Product "X1" from S15 with volume=3
        Product productX1 = new Product();
        productX1.setName("X1");
        productX1.setSupplier(supplierS15);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productX1);
        occupation1.setVolume(3);
        
        // Create Product "X2" from S15 with volume=7
        Product productX2 = new Product();
        productX2.setName("X2");
        productX2.setSupplier(supplierS15);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productX2);
        occupation2.setVolume(7);
        
        // Create Product "X3" from S15 with volume=2
        Product productX3 = new Product();
        productX3.setName("X3");
        productX3.setSupplier(supplierS15);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(productX3);
        occupation3.setVolume(2);
        
        // Create Warehouse "W15" and add products X1 and X3
        Warehouse warehouseW15 = new Warehouse();
        warehouseW15.setCity("City4");
        warehouseW15.setAddress("Address4");
        warehouseW15.addOccupation(occupation1);
        warehouseW15.addOccupation(occupation3);
        
        // Create Warehouse "W16" and add product X2
        Warehouse warehouseW16 = new Warehouse();
        warehouseW16.setCity("City5");
        warehouseW16.setAddress("Address5");
        warehouseW16.addOccupation(occupation2);
        
        // Add warehouses to company C4
        company.addWarehouse(warehouseW15);
        company.addWarehouse(warehouseW16);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = company.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}