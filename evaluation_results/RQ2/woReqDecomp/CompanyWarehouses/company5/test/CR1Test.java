package edu.company.company5.test;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Product;
import edu.company.ProductOccupation;
import edu.company.Supplier;
import edu.company.Warehouse;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CR1Test {
    private CompanyFactory factory;

    @Before
    public void setUp() {
        // Initialize the factory using EMF pattern
        factory = CompanyFactory.eINSTANCE;
    }

    // Test Case 1: Warehouse with multiple toxic products
    @Test
    public void testCase1_toxicProductsInWarehouse() {
        // Create company
        Company company = factory.createCompany();
        
        // Create warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityA");
        company.getWarehouses().add(warehouse); // Add to company
        
        // Create toxic products
        Product chemX = createProduct("ChemX", true, "S1");
        Product paintY = createProduct("PaintY", true, "S2");
        Product toolZ = createProduct("ToolZ", false, "S3");
        
        // Add occupations
        addOccupation(warehouse, chemX, 10.0);
        addOccupation(warehouse, paintY, 5.0);
        addOccupation(warehouse, toolZ, 15.0);
        
        // Retrieve toxic product names
        EList<String> toxicNames = warehouse.retrieveToxicProductNames();
        
        // Verify results
        assertEquals(2, toxicNames.size());
        assertTrue(toxicNames.contains("ChemX"));
        assertTrue(toxicNames.contains("PaintY"));
    }

    // Test Case 2: Warehouse with no toxic products
    @Test
    public void testCase2_noToxicProductsInWarehouse() {
        // Create company
        Company company = factory.createCompany();
        
        // Create warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityB");
        company.getWarehouses().add(warehouse);
        
        // Create non-toxic products
        Product boxA = createProduct("BoxA", false, "S1");
        Product cableB = createProduct("CableB", false, "S4");
        
        // Add occupations
        addOccupation(warehouse, boxA, 8.0);
        addOccupation(warehouse, cableB, 3.0);
        
        // Retrieve toxic product names
        EList<String> toxicNames = warehouse.retrieveToxicProductNames();
        
        // Verify empty list
        assertTrue(toxicNames.isEmpty());
    }

    // Test Case 3: Empty warehouse
    @Test
    public void testCase3_emptyWarehouse() {
        // Create company
        Company company = factory.createCompany();
        
        // Create empty warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityC");
        company.getWarehouses().add(warehouse);
        
        // Retrieve toxic product names
        EList<String> toxicNames = warehouse.retrieveToxicProductNames();
        
        // Verify empty list
        assertTrue(toxicNames.isEmpty());
    }

    // Test Case 4: Non-existent warehouse
    @Test
    public void testCase4_nonExistentWarehouse() {
        // Create company
        Company company = factory.createCompany();
        
        // Create warehouse (W4) but query for non-existent warehouse (W5)
        Warehouse existingWarehouse = factory.createWarehouse();
        existingWarehouse.setCity("CityX");
        company.getWarehouses().add(existingWarehouse);
        
        // Create a new warehouse instance (W5) not added to company
        Warehouse nonExistentWarehouse = factory.createWarehouse();
        nonExistentWarehouse.setCity("CityY");
        
        // Retrieve toxic product names from non-existent warehouse
        EList<String> toxicNames = nonExistentWarehouse.retrieveToxicProductNames();
        
        // Verify empty list
        assertTrue(toxicNames.isEmpty());
    }

    // Test Case 5: Warehouse with single toxic product
    @Test
    public void testCase5_singleToxicProductInWarehouse() {
        // Create company
        Company company = factory.createCompany();
        
        // Create warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityD");
        company.getWarehouses().add(warehouse);
        
        // Create toxic product
        Product acidK = createProduct("AcidK", true, "S5");
        
        // Add occupation
        addOccupation(warehouse, acidK, 12.0);
        
        // Retrieve toxic product names
        EList<String> toxicNames = warehouse.retrieveToxicProductNames();
        
        // Verify results
        assertEquals(1, toxicNames.size());
        assertEquals("AcidK", toxicNames.get(0));
    }

    // Helper: Create product with supplier
    private Product createProduct(String name, boolean toxic, String supplierName) {
        Product product = factory.createProduct();
        product.setName(name);
        product.setToxic(toxic);
        
        Supplier supplier = factory.createSupplier();
        supplier.setName(supplierName);
        product.setSupplier(supplier);
        
        return product;
    }

    // Helper: Add product occupation to warehouse
    private void addOccupation(Warehouse warehouse, Product product, double volume) {
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(volume);
        warehouse.getOccupations().add(occupation);
    }
}