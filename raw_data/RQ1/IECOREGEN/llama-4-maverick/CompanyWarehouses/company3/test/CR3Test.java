package edu.company.company3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.Supplier;
import edu.company.ProductOccupation;

public class CR3Test {
    
    private CompanyFactory factory;
    
    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
    }
    
    /**
     * Test Case 1: Supplier with multiple products
     * SetUp:
     * 1. Create Supplier "S10"
     * 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
     * 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
     * Action: List volumes for supplier "S10" in company C1.
     * Expected Output: 15
     */
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // Create company C1
        Company c1 = factory.createCompany();
        
        // Create Supplier "S10"
        Supplier s10 = factory.createSupplier();
        s10.setName("S10");
        
        // Create Warehouse "W12"
        Warehouse w12 = factory.createWarehouse();
        w12.setCity("W12");
        w12.setAddress("Address W12");
        w12.setSurface(100.0);
        
        // Create Warehouse "W13"
        Warehouse w13 = factory.createWarehouse();
        w13.setCity("W13");
        w13.setAddress("Address W13");
        w13.setSurface(100.0);
        
        // Add warehouses to company
        c1.getWarehouses().add(w12);
        c1.getWarehouses().add(w13);
        
        // Create Product "P1"
        Product p1 = factory.createProduct();
        p1.setName("P1");
        p1.setToxic(false);
        p1.setSupplier(s10);
        
        // Create Product "P2"
        Product p2 = factory.createProduct();
        p2.setName("P2");
        p2.setToxic(false);
        p2.setSupplier(s10);
        
        // Add products to supplier
        s10.getProducts().add(p1);
        s10.getProducts().add(p2);
        
        // Create ProductOccupation for P1 with volume=5
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(p1);
        po1.setVolume(5.0);
        w12.getOccupations().add(po1);
        
        // Create ProductOccupation for P2 with volume=10
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(p2);
        po2.setVolume(10.0);
        w13.getOccupations().add(po2);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = c1.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    /**
     * Test Case 2: Supplier with no products
     * SetUp:
     * 1. Create company C2 without warehouse.
     * 2. Create Supplier "S11" (no products linked).
     * Action: List volumes for supplier "S11" in company C2.
     * Expected Output: 0
     */
    @Test
    public void testCase2_supplierWithNoProducts() {
        // Create company C2 without warehouse
        Company c2 = factory.createCompany();
        
        // Create Supplier "S11" (no products linked)
        Supplier s11 = factory.createSupplier();
        s11.setName("S11");
        
        // Action: List volumes for supplier "S11" in company C2
        double result = c2.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    /**
     * Test Case 3: Non-existent supplier
     * SetUp:
     * 1. Create Supplier "S10"
     * 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
     * 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
     * 4. Create Supplier "S12" (no products linked).
     * Action: List volumes for supplier name "S13" in company C1.
     * Expected Output: 0
     */
    @Test
    public void testCase3_nonExistentSupplier() {
        // Create company C1
        Company c1 = factory.createCompany();
        
        // Create Supplier "S10"
        Supplier s10 = factory.createSupplier();
        s10.setName("S10");
        
        // Create Supplier "S12" (no products linked)
        Supplier s12 = factory.createSupplier();
        s12.setName("S12");
        
        // Create Warehouse "W12"
        Warehouse w12 = factory.createWarehouse();
        w12.setCity("W12");
        w12.setAddress("Address W12");
        w12.setSurface(100.0);
        
        // Create Warehouse "W13"
        Warehouse w13 = factory.createWarehouse();
        w13.setCity("W13");
        w13.setAddress("Address W13");
        w13.setSurface(100.0);
        
        // Add warehouses to company
        c1.getWarehouses().add(w12);
        c1.getWarehouses().add(w13);
        
        // Create Product "P1"
        Product p1 = factory.createProduct();
        p1.setName("P1");
        p1.setToxic(false);
        p1.setSupplier(s10);
        
        // Create Product "P2"
        Product p2 = factory.createProduct();
        p2.setName("P2");
        p2.setToxic(false);
        p2.setSupplier(s10);
        
        // Add products to supplier S10
        s10.getProducts().add(p1);
        s10.getProducts().add(p2);
        
        // Create ProductOccupation for P1 with volume=5
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(p1);
        po1.setVolume(5.0);
        w12.getOccupations().add(po1);
        
        // Create ProductOccupation for P2 with volume=10
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(p2);
        po2.setVolume(10.0);
        w13.getOccupations().add(po2);
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = c1.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    /**
     * Test Case 4: Supplier with single product
     * SetUp:
     * 1. Add Supplier "S14" to system.
     * 2. Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3.
     * Action: List volumes for supplier "S14" in company C3.
     * Expected Output: 8
     */
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // Create company C3
        Company c3 = factory.createCompany();
        
        // Add Supplier "S14" to system
        Supplier s14 = factory.createSupplier();
        s14.setName("S14");
        
        // Create Warehouse "W14"
        Warehouse w14 = factory.createWarehouse();
        w14.setCity("W14");
        w14.setAddress("Address W14");
        w14.setSurface(100.0);
        
        // Add warehouse to company
        c3.getWarehouses().add(w14);
        
        // Add Product "P3" (volume=8) from S14
        Product p3 = factory.createProduct();
        p3.setName("P3");
        p3.setToxic(false);
        p3.setSupplier(s14);
        
        // Add product to supplier
        s14.getProducts().add(p3);
        
        // Create ProductOccupation for P3 with volume=8
        ProductOccupation po3 = factory.createProductOccupation();
        po3.setProduct(p3);
        po3.setVolume(8.0);
        w14.getOccupations().add(po3);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = c3.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    /**
     * Test Case 5: Supplier with products in multiple warehouses
     * SetUp:
     * 1. Add Supplier "S15" to system.
     * 2. Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4.
     * 3. Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4.
     * 4. Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4.
     * Action: List volumes for supplier "S15" in company C4.
     * Expected Output: 12
     */
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // Create company C4
        Company c4 = factory.createCompany();
        
        // Add Supplier "S15" to system
        Supplier s15 = factory.createSupplier();
        s15.setName("S15");
        
        // Create Warehouse "W15"
        Warehouse w15 = factory.createWarehouse();
        w15.setCity("W15");
        w15.setAddress("Address W15");
        w15.setSurface(100.0);
        
        // Create Warehouse "W16"
        Warehouse w16 = factory.createWarehouse();
        w16.setCity("W16");
        w16.setAddress("Address W16");
        w16.setSurface(100.0);
        
        // Add warehouses to company
        c4.getWarehouses().add(w15);
        c4.getWarehouses().add(w16);
        
        // Add Product "X1" (volume=3) from S15
        Product x1 = factory.createProduct();
        x1.setName("X1");
        x1.setToxic(false);
        x1.setSupplier(s15);
        
        // Add Product "X2" (volume=7) from S15
        Product x2 = factory.createProduct();
        x2.setName("X2");
        x2.setToxic(false);
        x2.setSupplier(s15);
        
        // Add Product "X3" (volume=2) from S15
        Product x3 = factory.createProduct();
        x3.setName("X3");
        x3.setToxic(false);
        x3.setSupplier(s15);
        
        // Add products to supplier
        s15.getProducts().add(x1);
        s15.getProducts().add(x2);
        s15.getProducts().add(x3);
        
        // Create ProductOccupation for X1 with volume=3 in W15
        ProductOccupation pox1 = factory.createProductOccupation();
        pox1.setProduct(x1);
        pox1.setVolume(3.0);
        w15.getOccupations().add(pox1);
        
        // Create ProductOccupation for X2 with volume=7 in W16
        ProductOccupation pox2 = factory.createProductOccupation();
        pox2.setProduct(x2);
        pox2.setVolume(7.0);
        w16.getOccupations().add(pox2);
        
        // Create ProductOccupation for X3 with volume=2 in W15
        ProductOccupation pox3 = factory.createProductOccupation();
        pox3.setProduct(x3);
        pox3.setVolume(2.0);
        w15.getOccupations().add(pox3);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = c4.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}