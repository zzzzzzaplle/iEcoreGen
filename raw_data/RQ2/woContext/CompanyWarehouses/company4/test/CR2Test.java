package edu.company.company4.test;

import edu.company.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import org.eclipse.emf.common.util.EList;

public class CR2Test {
    private CompanyFactory factory;
    private Company company;
    
    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
        company = factory.createCompany();
    }
    
    // Test Case 1: Warehouse with products from multiple suppliers
    @Test
    public void testCase1_warehouseWithMultipleSuppliers() {
        // Create warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityE");
        warehouse.setSurface(350.0);
        company.getWarehouses().add(warehouse);
        
        // Create suppliers
        Supplier s6 = factory.createSupplier();
        s6.setName("S6");
        Supplier s7 = factory.createSupplier();
        s7.setName("S7");
        
        // Create products
        Product p1 = factory.createProduct();
        p1.setSupplier(s6);
        Product p2 = factory.createProduct();
        p2.setSupplier(s7);
        Product p3 = factory.createProduct();
        p3.setSupplier(s6);  // Duplicate supplier
        
        // Create occupations
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(p1);
        warehouse.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(p2);
        warehouse.getOccupations().add(occ2);
        
        ProductOccupation occ3 = factory.createProductOccupation();
        occ3.setProduct(p3);
        warehouse.getOccupations().add(occ3);
        
        // Retrieve unique suppliers
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Collect supplier names
        Set<String> supplierNames = new HashSet<>();
        for (Supplier s : result) {
            supplierNames.add(s.getName());
        }
        
        // Verify results
        assertEquals(2, supplierNames.size());
        assertTrue(supplierNames.contains("S6"));
        assertTrue(supplierNames.contains("S7"));
    }
    
    // Test Case 2: Warehouse with single product
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // Create warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityF");
        warehouse.setSurface(250.0);
        company.getWarehouses().add(warehouse);
        
        // Create supplier
        Supplier s8 = factory.createSupplier();
        s8.setName("S8");
        
        // Create product
        Product p4 = factory.createProduct();
        p4.setSupplier(s8);
        
        // Create occupation
        ProductOccupation occ = factory.createProductOccupation();
        occ.setProduct(p4);
        warehouse.getOccupations().add(occ);
        
        // Retrieve unique suppliers
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Verify results
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    // Test Case 3: Empty warehouse
    @Test
    public void testCase3_emptyWarehouse() {
        // Create empty warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityG");
        warehouse.setSurface(150.0);
        company.getWarehouses().add(warehouse);
        
        // Retrieve unique suppliers
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Verify results
        assertTrue(result.isEmpty());
    }
    
    // Test Case 4: Non-existent warehouse
    @Test
    public void testCase4_nonexistentWarehouse() {
        // Create warehouse but don't add to company
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityH");
        warehouse.setSurface(300.0);
        
        // Retrieve unique suppliers
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Verify results
        assertTrue(result.isEmpty());
    }
    
    // Test Case 5: Warehouse with products from same supplier
    @Test
    public void testCase5_warehouseWithSameSupplier() {
        // Create warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityI");
        warehouse.setSurface(300.0);
        company.getWarehouses().add(warehouse);
        
        // Create supplier
        Supplier s9 = factory.createSupplier();
        s9.setName("S9");
        
        // Create products
        Product a1 = factory.createProduct();
        a1.setSupplier(s9);
        Product a2 = factory.createProduct();
        a2.setSupplier(s9);
        
        // Create occupations
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(a1);
        warehouse.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(a2);
        warehouse.getOccupations().add(occ2);
        
        // Retrieve unique suppliers
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Verify results
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}