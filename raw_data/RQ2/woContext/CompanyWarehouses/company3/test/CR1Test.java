package edu.company.company3.test;

import edu.company.CompanyFactory;
import edu.company.Company;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.Supplier;
import edu.company.ProductOccupation;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR1Test {
    
    private CompanyFactory factory;
    
    @Before
    public void setUp() {
        // Initialize factory using Ecore factory pattern
        factory = CompanyFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // 1. Create Company
        Company company = factory.createCompany();
        
        // 2. Create Warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityA");
        warehouse.setSurface(500);
        company.getWarehouses().add(warehouse);
        
        // 3. Create Products and Suppliers
        Supplier s1 = factory.createSupplier();
        s1.setName("S1");
        
        Supplier s2 = factory.createSupplier();
        s2.setName("S2");
        
        Supplier s3 = factory.createSupplier();
        s3.setName("S3");
        
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
        
        // 4. Create ProductOccupations
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setVolume(10);
        occ1.setProduct(chemX);
        warehouse.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setVolume(5);
        occ2.setProduct(paintY);
        warehouse.getOccupations().add(occ2);
        
        ProductOccupation occ3 = factory.createProductOccupation();
        occ3.setVolume(15);
        occ3.setProduct(toolZ);
        warehouse.getOccupations().add(occ3);
        
        // 5. Retrieve toxic products
        List<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // 6. Verify results
        assertEquals(2, toxicProducts.size());
        assertTrue(toxicProducts.contains("ChemX"));
        assertTrue(toxicProducts.contains("PaintY"));
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // 1. Create Company
        Company company = factory.createCompany();
        
        // 2. Create Warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityB");
        warehouse.setSurface(300);
        company.getWarehouses().add(warehouse);
        
        // 3. Create Products and Suppliers
        Supplier s1 = factory.createSupplier();
        s1.setName("S1");
        
        Supplier s4 = factory.createSupplier();
        s4.setName("S4");
        
        Product boxA = factory.createProduct();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        boxA.setSupplier(s1);
        
        Product cableB = factory.createProduct();
        cableB.setName("CableB");
        cableB.setToxic(false);
        cableB.setSupplier(s4);
        
        // 4. Create ProductOccupations
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setVolume(8);
        occ1.setProduct(boxA);
        warehouse.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setVolume(3);
        occ2.setProduct(cableB);
        warehouse.getOccupations().add(occ2);
        
        // 5. Retrieve toxic products
        List<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // 6. Verify results
        assertTrue(toxicProducts.isEmpty());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // 1. Create Company
        Company company = factory.createCompany();
        
        // 2. Create Warehouse (no products)
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityC");
        warehouse.setSurface(200);
        company.getWarehouses().add(warehouse);
        
        // 3. Retrieve toxic products
        List<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // 4. Verify results
        assertTrue(toxicProducts.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // 1. Create Company with one warehouse
        Company company = factory.createCompany();
        
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityD");
        warehouse.setSurface(400);
        company.getWarehouses().add(warehouse);
        
        // 2. Create a non-existent warehouse reference
        Warehouse nonExistent = factory.createWarehouse();
        nonExistent.setCity("CityE");
        nonExistent.setSurface(100);
        
        // 3. Retrieve toxic products from non-existent warehouse
        List<String> toxicProducts = nonExistent.retrieveToxicProductNames();
        
        // 4. Verify results
        assertTrue(toxicProducts.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // 1. Create Company
        Company company = factory.createCompany();
        
        // 2. Create Warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityD");
        warehouse.setSurface(400);
        company.getWarehouses().add(warehouse);
        
        // 3. Create Product and Supplier
        Supplier s5 = factory.createSupplier();
        s5.setName("S5");
        
        Product acidK = factory.createProduct();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        acidK.setSupplier(s5);
        
        // 4. Create ProductOccupation
        ProductOccupation occ = factory.createProductOccupation();
        occ.setVolume(12);
        occ.setProduct(acidK);
        warehouse.getOccupations().add(occ);
        
        // 5. Retrieve toxic products
        List<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // 6. Verify results
        assertEquals(1, toxicProducts.size());
        assertEquals("AcidK", toxicProducts.get(0));
    }
}