package edu.company.company5.test;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Product;
import edu.company.ProductOccupation;
import edu.company.Supplier;
import edu.company.Warehouse;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR3Test {

    private CompanyFactory factory;

    @Before
    public void setUp() {
        // Initialize EMF factory
        factory = CompanyFactory.eINSTANCE;
    }

    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // Setup
        Company company = factory.createCompany();
        Supplier supplier = factory.createSupplier();
        supplier.setName("S10");
        
        Warehouse wh1 = factory.createWarehouse();
        wh1.setAddress("W12");
        company.getWarehouses().add(wh1);
        
        Warehouse wh2 = factory.createWarehouse();
        wh2.setAddress("W13");
        company.getWarehouses().add(wh2);
        
        // Create products for supplier
        Product p1 = factory.createProduct();
        p1.setSupplier(supplier);
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(p1);
        occ1.setVolume(5.0);
        wh1.getOccupations().add(occ1);
        
        Product p2 = factory.createProduct();
        p2.setSupplier(supplier);
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(p2);
        occ2.setVolume(10.0);
        wh2.getOccupations().add(occ2);
        
        // Action
        double result = company.countTotalVolumeBySupplier("S10");
        
        // Assert
        assertEquals(15.0, result, 0.001);
    }

    @Test
    public void testCase2_SupplierWithNoProducts() {
        // Setup
        Company company = factory.createCompany();
        Supplier supplier = factory.createSupplier();
        supplier.setName("S11");
        
        // Action
        double result = company.countTotalVolumeBySupplier("S11");
        
        // Assert
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase3_NonExistentSupplier() {
        // Setup
        Company company = factory.createCompany();
        Supplier supplier = factory.createSupplier();
        supplier.setName("S10");
        
        Warehouse wh = factory.createWarehouse();
        wh.setAddress("W12");
        company.getWarehouses().add(wh);
        
        Product p = factory.createProduct();
        p.setSupplier(supplier);
        ProductOccupation occ = factory.createProductOccupation();
        occ.setProduct(p);
        occ.setVolume(5.0);
        wh.getOccupations().add(occ);
        
        // Action
        double result = company.countTotalVolumeBySupplier("S13");
        
        // Assert
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // Setup
        Company company = factory.createCompany();
        Supplier supplier = factory.createSupplier();
        supplier.setName("S14");
        
        Warehouse wh = factory.createWarehouse();
        wh.setAddress("W14");
        company.getWarehouses().add(wh);
        
        Product p = factory.createProduct();
        p.setSupplier(supplier);
        ProductOccupation occ = factory.createProductOccupation();
        occ.setProduct(p);
        occ.setVolume(8.0);
        wh.getOccupations().add(occ);
        
        // Action
        double result = company.countTotalVolumeBySupplier("S14");
        
        // Assert
        assertEquals(8.0, result, 0.001);
    }

    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // Setup
        Company company = factory.createCompany();
        Supplier supplier = factory.createSupplier();
        supplier.setName("S15");
        
        Warehouse wh1 = factory.createWarehouse();
        wh1.setAddress("W15");
        company.getWarehouses().add(wh1);
        
        Warehouse wh2 = factory.createWarehouse();
        wh2.setAddress("W16");
        company.getWarehouses().add(wh2);
        
        // Products in warehouse1
        Product x1 = factory.createProduct();
        x1.setSupplier(supplier);
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(x1);
        occ1.setVolume(3.0);
        wh1.getOccupations().add(occ1);
        
        Product x3 = factory.createProduct();
        x3.setSupplier(supplier);
        ProductOccupation occ3 = factory.createProductOccupation();
        occ3.setProduct(x3);
        occ3.setVolume(2.0);
        wh1.getOccupations().add(occ3);
        
        // Products in warehouse2
        Product x2 = factory.createProduct();
        x2.setSupplier(supplier);
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(x2);
        occ2.setVolume(7.0);
        wh2.getOccupations().add(occ2);
        
        // Action
        double result = company.countTotalVolumeBySupplier("S15");
        
        // Assert
        assertEquals(12.0, result, 0.001);
    }
}