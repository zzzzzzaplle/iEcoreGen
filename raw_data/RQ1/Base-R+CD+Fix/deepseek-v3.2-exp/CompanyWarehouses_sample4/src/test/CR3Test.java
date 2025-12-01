import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

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
        
        // Create products from S10
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        
        // Create product occupations with volumes
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productP1);
        occupation1.setVolume(5.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productP2);
        occupation2.setVolume(10.0);
        
        // Create warehouses and add occupations
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setCity("City1");
        warehouseW12.setAddress("Address1");
        warehouseW12.addOccupation(occupation1);
        
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
    public void testCase2_supplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        // Company is already created in setUp()
        
        // Create Supplier "S11" (no products linked)
        Supplier supplierS11 = new Supplier();
        supplierS11.setName("S11");
        
        // Action: List volumes for supplier "S11" in company C2
        double result = company.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // Create products from S10
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        
        // Create product occupations with volumes
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productP1);
        occupation1.setVolume(5.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productP2);
        occupation2.setVolume(10.0);
        
        // Create warehouses and add occupations
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setCity("City1");
        warehouseW12.setAddress("Address1");
        warehouseW12.addOccupation(occupation1);
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setCity("City2");
        warehouseW13.setAddress("Address2");
        warehouseW13.addOccupation(occupation2);
        
        // Add warehouses to company C1
        company.addWarehouse(warehouseW12);
        company.addWarehouse(warehouseW13);
        
        // Create Supplier "S12" (no products linked)
        Supplier supplierS12 = new Supplier();
        supplierS12.setName("S12");
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = company.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        Supplier supplierS14 = new Supplier();
        supplierS14.setName("S14");
        
        // Add Product "P3" (volume=8) from S14
        Product productP3 = new Product();
        productP3.setName("P3");
        productP3.setSupplier(supplierS14);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(productP3);
        occupation.setVolume(8.0);
        
        // Add to Warehouse "W14" in company C3
        Warehouse warehouseW14 = new Warehouse();
        warehouseW14.setCity("City3");
        warehouseW14.setAddress("Address3");
        warehouseW14.addOccupation(occupation);
        
        company.addWarehouse(warehouseW14);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = company.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        Supplier supplierS15 = new Supplier();
        supplierS15.setName("S15");
        
        // Create products from S15
        Product productX1 = new Product();
        productX1.setName("X1");
        productX1.setSupplier(supplierS15);
        
        Product productX2 = new Product();
        productX2.setName("X2");
        productX2.setSupplier(supplierS15);
        
        Product productX3 = new Product();
        productX3.setName("X3");
        productX3.setSupplier(supplierS15);
        
        // Create product occupations with volumes
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productX1);
        occupation1.setVolume(3.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productX2);
        occupation2.setVolume(7.0);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(productX3);
        occupation3.setVolume(2.0);
        
        // Create warehouses and add occupations
        Warehouse warehouseW15 = new Warehouse();
        warehouseW15.setCity("City4");
        warehouseW15.setAddress("Address4");
        warehouseW15.addOccupation(occupation1);
        warehouseW15.addOccupation(occupation3); // X3 also in W15
        
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