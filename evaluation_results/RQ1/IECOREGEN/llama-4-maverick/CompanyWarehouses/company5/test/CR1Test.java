package edu.company.company5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.company.Company;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.Supplier;
import edu.company.ProductOccupation;
import edu.company.CompanyFactory;
import org.eclipse.emf.common.util.EList;

public class CR1Test {
    
    private CompanyFactory factory;
    
    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_warehouseWithMultipleToxicProducts() {
        // Create company
        Company c1 = factory.createCompany();
        
        // Create warehouse W1
        Warehouse w1 = factory.createWarehouse();
        w1.setCity("CityA");
        w1.setSurface(500.0);
        c1.getWarehouses().add(w1);
        
        // Create suppliers
        Supplier s1 = factory.createSupplier();
        s1.setName("S1");
        Supplier s2 = factory.createSupplier();
        s2.setName("S2");
        Supplier s3 = factory.createSupplier();
        s3.setName("S3");
        
        // Create products
        Product chemX = factory.createProduct();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        chemX.setSupplier(s1);
        
        Product paintY = factory.createProduct();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        paintY.setSupplier(s2);
        
        Product toolZ = factory.createProduct();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        toolZ.setSupplier(s3);
        
        // Create product occupations
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(chemX);
        po1.setVolume(10.0);
        w1.getOccupations().add(po1);
        
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(paintY);
        po2.setVolume(5.0);
        w1.getOccupations().add(po2);
        
        ProductOccupation po3 = factory.createProductOccupation();
        po3.setProduct(toolZ);
        po3.setVolume(15.0);
        w1.getOccupations().add(po3);
        
        // Action: Retrieve toxic products in W1
        EList<String> result = w1.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ChemX"));
        assertTrue(result.contains("PaintY"));
    }
    
    @Test
    public void testCase2_warehouseWithNoToxicProducts() {
        // Create company
        Company c2 = factory.createCompany();
        
        // Create warehouse W2
        Warehouse w2 = factory.createWarehouse();
        w2.setCity("CityB");
        w2.setSurface(300.0);
        c2.getWarehouses().add(w2);
        
        // Create suppliers
        Supplier s1 = factory.createSupplier();
        s1.setName("S1");
        Supplier s4 = factory.createSupplier();
        s4.setName("S4");
        
        // Create products
        Product boxA = factory.createProduct();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        boxA.setSupplier(s1);
        
        Product cableB = factory.createProduct();
        cableB.setName("CableB");
        cableB.setToxic(false);
        cableB.setSupplier(s4);
        
        // Create product occupations
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(boxA);
        po1.setVolume(8.0);
        w2.getOccupations().add(po1);
        
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(cableB);
        po2.setVolume(3.0);
        w2.getOccupations().add(po2);
        
        // Action: Retrieve toxic products in W2
        EList<String> result = w2.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // Create company
        Company c3 = factory.createCompany();
        
        // Create warehouse W3
        Warehouse w3 = factory.createWarehouse();
        w3.setCity("CityC");
        w3.setSurface(200.0);
        c3.getWarehouses().add(w3);
        
        // Action: Retrieve toxic products in W3 (no products added)
        EList<String> result = w3.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // Create company C3
        Company c3 = factory.createCompany();
        
        // Create warehouse W4
        Warehouse w4 = factory.createWarehouse();
        w4.setCity("CityE");
        w4.setSurface(600.0);
        c3.getWarehouses().add(w4);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        // Since we're simulating a non-existent warehouse, we'll just create a new warehouse
        // that's not associated with the company
        Warehouse w5 = factory.createWarehouse();
        w5.setCity("CityF");
        w5.setSurface(100.0);
        // Note: w5 is not added to c3.getWarehouses()
        
        EList<String> result = w5.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_warehouseWithSingleToxicProduct() {
        // Create company
        Company c4 = factory.createCompany();
        
        // Create warehouse W6
        Warehouse w6 = factory.createWarehouse();
        w6.setCity("CityD");
        w6.setSurface(400.0);
        c4.getWarehouses().add(w6);
        
        // Create supplier
        Supplier s5 = factory.createSupplier();
        s5.setName("S5");
        
        // Create product
        Product acidK = factory.createProduct();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        acidK.setSupplier(s5);
        
        // Create product occupation
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(acidK);
        po1.setVolume(12.0);
        w6.getOccupations().add(po1);
        
        // Action: Retrieve toxic products in W6
        EList<String> result = w6.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, result.size());
        assertEquals("AcidK", result.get(0));
    }
}