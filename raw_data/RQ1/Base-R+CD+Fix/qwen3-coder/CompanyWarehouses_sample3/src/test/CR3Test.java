import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // Create Product "P1" (volume=5) from S10
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productP1);
        occupation1.setVolume(5.0);
        
        // Create Warehouse "W12"
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setCity("City1");
        warehouseW12.setAddress("Address1");
        warehouseW12.addOccupations(occupation1);
        
        // Create Product "P2" (volume=10) from S10
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productP2);
        occupation2.setVolume(10.0);
        
        // Create Warehouse "W13"
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setCity("City2");
        warehouseW13.setAddress("Address2");
        warehouseW13.addOccupations(occupation2);
        
        // Create company C1 and add warehouses
        Company companyC1 = new Company();
        companyC1.addWarehouses(warehouseW12);
        companyC1.addWarehouses(warehouseW13);
        
        // Action: List volumes for supplier "S10" in company C1
        Double result = companyC1.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        Company companyC2 = new Company();
        
        // Create Supplier "S11" (no products linked)
        Supplier supplierS11 = new Supplier();
        supplierS11.setName("S11");
        
        // Action: List volumes for supplier "S11" in company C2
        Double result = companyC2.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // Add Product "P1" (volume=5) from S10
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productP1);
        occupation1.setVolume(5.0);
        
        // Create Warehouse "W12"
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setCity("City1");
        warehouseW12.setAddress("Address1");
        warehouseW12.addOccupations(occupation1);
        
        // Add Product "P2" (volume=10) from S10
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productP2);
        occupation2.setVolume(10.0);
        
        // Create Warehouse "W13"
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setCity("City2");
        warehouseW13.setAddress("Address2");
        warehouseW13.addOccupations(occupation2);
        
        // Create Supplier "S12" (no products linked)
        Supplier supplierS12 = new Supplier();
        supplierS12.setName("S12");
        
        // Create company C1 and add warehouses
        Company companyC1 = new Company();
        companyC1.addWarehouses(warehouseW12);
        companyC1.addWarehouses(warehouseW13);
        
        // Action: List volumes for supplier name "S13" in company C1
        Double result = companyC1.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_SupplierWithSingleProduct() {
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
        
        // Create Warehouse "W14"
        Warehouse warehouseW14 = new Warehouse();
        warehouseW14.setCity("City3");
        warehouseW14.setAddress("Address3");
        warehouseW14.addOccupations(occupation);
        
        // Create company C3 and add warehouse
        Company companyC3 = new Company();
        companyC3.addWarehouses(warehouseW14);
        
        // Action: List volumes for supplier "S14" in company C3
        Double result = companyC3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        Supplier supplierS15 = new Supplier();
        supplierS15.setName("S15");
        
        // Add Product "X1" (volume=3) from S15 to Warehouse "W15"
        Product productX1 = new Product();
        productX1.setName("X1");
        productX1.setSupplier(supplierS15);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productX1);
        occupation1.setVolume(3.0);
        
        // Create Warehouse "W15"
        Warehouse warehouseW15 = new Warehouse();
        warehouseW15.setCity("City4");
        warehouseW15.setAddress("Address4");
        warehouseW15.addOccupations(occupation1);
        
        // Add Product "X2" (volume=7) from S15 to Warehouse "W16"
        Product productX2 = new Product();
        productX2.setName("X2");
        productX2.setSupplier(supplierS15);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productX2);
        occupation2.setVolume(7.0);
        
        // Create Warehouse "W16"
        Warehouse warehouseW16 = new Warehouse();
        warehouseW16.setCity("City5");
        warehouseW16.setAddress("Address5");
        warehouseW16.addOccupations(occupation2);
        
        // Add Product "X3" (volume=2) from S15 to Warehouse "W15"
        Product productX3 = new Product();
        productX3.setName("X3");
        productX3.setSupplier(supplierS15);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(productX3);
        occupation3.setVolume(2.0);
        warehouseW15.addOccupations(occupation3);
        
        // Create company C4 and add warehouses
        Company companyC4 = new Company();
        companyC4.addWarehouses(warehouseW15);
        companyC4.addWarehouses(warehouseW16);
        
        // Action: List volumes for supplier "S15" in company C4
        Double result = companyC4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}