package edu.company.company1.test;

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
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // Create company
        Company company = factory.createCompany();
        
        // Create supplier S10
        Supplier s10 = factory.createSupplier();
        s10.setName("S10");
        
        // Create warehouses
        Warehouse w12 = factory.createWarehouse();
        w12.setCity("City12");
        w12.setAddress("Address12");
        company.getWarehouses().add(w12);
        
        Warehouse w13 = factory.createWarehouse();
        w13.setCity("City13");
        w13.setAddress("Address13");
        company.getWarehouses().add(w13);
        
        // Create products
        Product p1 = factory.createProduct();
        p1.setName("P1");
        p1.setSupplier(s10);
        s10.getProducts().add(p1);
        
        Product p2 = factory.createProduct();
        p2.setName("P2");
        p2.setSupplier(s10);
        s10.getProducts().add(p2);
        
        // Create product occupations
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(p1);
        po1.setVolume(5);
        w12.getOccupations().add(po1);
        
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(p2);
        po2.setVolume(10);
        w13.getOccupations().add(po2);
        
        // Test
        double result = company.countTotalVolumeBySupplier("S10");
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // Create company C2 without warehouse
        Company company = factory.createCompany();
        
        // Create Supplier S11 (no products linked)
        Supplier s11 = factory.createSupplier();
        s11.setName("S11");
        
        // Test
        double result = company.countTotalVolumeBySupplier("S11");
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // Create company
        Company company = factory.createCompany();
        
        // Create supplier S10
        Supplier s10 = factory.createSupplier();
        s10.setName("S10");
        
        // Create warehouses
        Warehouse w12 = factory.createWarehouse();
        w12.setCity("City12");
        w12.setAddress("Address12");
        company.getWarehouses().add(w12);
        
        Warehouse w13 = factory.createWarehouse();
        w13.setCity("City13");
        w13.setAddress("Address13");
        company.getWarehouses().add(w13);
        
        // Create products
        Product p1 = factory.createProduct();
        p1.setName("P1");
        p1.setSupplier(s10);
        s10.getProducts().add(p1);
        
        Product p2 = factory.createProduct();
        p2.setName("P2");
        p2.setSupplier(s10);
        s10.getProducts().add(p2);
        
        // Create product occupations
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(p1);
        po1.setVolume(5);
        w12.getOccupations().add(po1);
        
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(p2);
        po2.setVolume(10);
        w13.getOccupations().add(po2);
        
        // Create Supplier S12 (no products linked)
        Supplier s12 = factory.createSupplier();
        s12.setName("S12");
        
        // Test for non-existent supplier S13
        double result = company.countTotalVolumeBySupplier("S13");
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // Create company
        Company company = factory.createCompany();
        
        // Add Supplier S14 to system
        Supplier s14 = factory.createSupplier();
        s14.setName("S14");
        
        // Create warehouse
        Warehouse w14 = factory.createWarehouse();
        w14.setCity("City14");
        w14.setAddress("Address14");
        company.getWarehouses().add(w14);
        
        // Add Product P3 from S14
        Product p3 = factory.createProduct();
        p3.setName("P3");
        p3.setSupplier(s14);
        s14.getProducts().add(p3);
        
        // Create product occupation
        ProductOccupation po3 = factory.createProductOccupation();
        po3.setProduct(p3);
        po3.setVolume(8);
        w14.getOccupations().add(po3);
        
        // Test
        double result = company.countTotalVolumeBySupplier("S14");
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // Create company
        Company company = factory.createCompany();
        
        // Add Supplier S15 to system
        Supplier s15 = factory.createSupplier();
        s15.setName("S15");
        
        // Create warehouses
        Warehouse w15 = factory.createWarehouse();
        w15.setCity("City15");
        w15.setAddress("Address15");
        company.getWarehouses().add(w15);
        
        Warehouse w16 = factory.createWarehouse();
        w16.setCity("City16");
        w16.setAddress("Address16");
        company.getWarehouses().add(w16);
        
        // Create products
        Product x1 = factory.createProduct();
        x1.setName("X1");
        x1.setSupplier(s15);
        s15.getProducts().add(x1);
        
        Product x2 = factory.createProduct();
        x2.setName("X2");
        x2.setSupplier(s15);
        s15.getProducts().add(x2);
        
        Product x3 = factory.createProduct();
        x3.setName("X3");
        x3.setSupplier(s15);
        s15.getProducts().add(x3);
        
        // Create product occupations
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(x1);
        po1.setVolume(3);
        w15.getOccupations().add(po1);
        
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(x2);
        po2.setVolume(7);
        w16.getOccupations().add(po2);
        
        ProductOccupation po3 = factory.createProductOccupation();
        po3.setProduct(x3);
        po3.setVolume(2);
        w15.getOccupations().add(po3);
        
        // Test
        double result = company.countTotalVolumeBySupplier("S15");
        assertEquals(12.0, result, 0.001);
    }
}