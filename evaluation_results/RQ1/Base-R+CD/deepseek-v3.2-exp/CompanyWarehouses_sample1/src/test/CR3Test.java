import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10" and add products to warehouses in company C1
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
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(productP1);
        occ1.setVolume(5.0);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(productP2);
        occ2.setVolume(10.0);
        
        // Create warehouses and add occupations
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setCity("City12");
        warehouseW12.setAddress("Address12");
        warehouseW12.addOccupation(occ1);
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setCity("City13");
        warehouseW13.setAddress("Address13");
        warehouseW13.addOccupation(occ2);
        
        // Add warehouses to company
        company.addWarehouse(warehouseW12);
        company.addWarehouse(warehouseW13);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = company.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse and Supplier "S11" (no products linked)
        Supplier supplierS11 = new Supplier();
        supplierS11.setName("S11");
        
        // Company C2 has no warehouses (empty company from setUp)
        
        // Action: List volumes for supplier "S11" in company C2
        double result = company.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NonExistentSupplier() {
        // SetUp: Create Supplier "S10" and add products to warehouses in company C1
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        Supplier supplierS12 = new Supplier();
        supplierS12.setName("S12");
        
        // Create products from S10
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        
        // Create product occupations with volumes
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(productP1);
        occ1.setVolume(5.0);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(productP2);
        occ2.setVolume(10.0);
        
        // Create warehouses and add occupations
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setCity("City12");
        warehouseW12.setAddress("Address12");
        warehouseW12.addOccupation(occ1);
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setCity("City13");
        warehouseW13.setAddress("Address13");
        warehouseW13.addOccupation(occ2);
        
        // Add warehouses to company
        company.addWarehouse(warehouseW12);
        company.addWarehouse(warehouseW13);
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = company.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" and product to warehouse in company C3
        Supplier supplierS14 = new Supplier();
        supplierS14.setName("S14");
        
        // Create product from S14
        Product productP3 = new Product();
        productP3.setName("P3");
        productP3.setSupplier(supplierS14);
        
        // Create product occupation with volume
        ProductOccupation occ = new ProductOccupation();
        occ.setProduct(productP3);
        occ.setVolume(8.0);
        
        // Create warehouse and add occupation
        Warehouse warehouseW14 = new Warehouse();
        warehouseW14.setCity("City14");
        warehouseW14.setAddress("Address14");
        warehouseW14.addOccupation(occ);
        
        // Add warehouse to company
        company.addWarehouse(warehouseW14);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = company.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" and products to multiple warehouses in company C4
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
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(productX1);
        occ1.setVolume(3.0);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(productX2);
        occ2.setVolume(7.0);
        
        ProductOccupation occ3 = new ProductOccupation();
        occ3.setProduct(productX3);
        occ3.setVolume(2.0);
        
        // Create warehouses and add occupations
        Warehouse warehouseW15 = new Warehouse();
        warehouseW15.setCity("City15");
        warehouseW15.setAddress("Address15");
        warehouseW15.addOccupation(occ1);
        warehouseW15.addOccupation(occ3); // X1 and X3 in W15
        
        Warehouse warehouseW16 = new Warehouse();
        warehouseW16.setCity("City16");
        warehouseW16.setAddress("Address16");
        warehouseW16.addOccupation(occ2); // X2 in W16
        
        // Add warehouses to company
        company.addWarehouse(warehouseW15);
        company.addWarehouse(warehouseW16);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = company.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}