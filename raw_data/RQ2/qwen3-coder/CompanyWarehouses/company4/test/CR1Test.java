package edu.company.company4.test;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Supplier;
import edu.company.Product;
import edu.company.ProductOccupation;
import edu.company.Warehouse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR1Test {
    
    private CompanyFactory factory;
    
    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // SetUp
        Company c1 = factory.createCompany();
        Warehouse w1 = factory.createWarehouse();
        w1.setCity("CityA");
        w1.setSurface(500);
        c1.getWarehouses().add(w1);
        
        // Create products and occupations
        Product chemX = createProduct("ChemX", true, "S1");
        Product paintY = createProduct("PaintY", true, "S2");
        Product toolZ = createProduct("ToolZ", false, "S3");
        
        createProductOccupation(w1, chemX, 10);
        createProductOccupation(w1, paintY, 5);
        createProductOccupation(w1, toolZ, 15);
        
        // Action: Retrieve toxic products in W1
        Set<String> result = new HashSet<>(w1.retrieveToxicProductNames());
        
        // Expected Output: ["ChemX", "PaintY"]
        Set<String> expected = new HashSet<>(Arrays.asList("ChemX", "PaintY"));
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp
        Company c2 = factory.createCompany();
        Warehouse w2 = factory.createWarehouse();
        w2.setCity("CityB");
        w2.setSurface(300);
        c2.getWarehouses().add(w2);
        
        // Create products and occupations
        Product boxA = createProduct("BoxA", false, "S1");
        Product cableB = createProduct("CableB", false, "S4");
        
        createProductOccupation(w2, boxA, 8);
        createProductOccupation(w2, cableB, 3);
        
        // Action: Retrieve toxic products in W2
        assertTrue(w2.retrieveToxicProductNames().isEmpty());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp
        Company c3 = factory.createCompany();
        Warehouse w3 = factory.createWarehouse();
        w3.setCity("CityC");
        w3.setSurface(200);
        c3.getWarehouses().add(w3);
        
        // Action: Retrieve toxic products in W3
        assertTrue(w3.retrieveToxicProductNames().isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp
        Company c3 = factory.createCompany();
        Warehouse w4 = factory.createWarehouse();
        w4.setCity("CityC");
        c3.getWarehouses().add(w4);
        
        // Create a warehouse not added to the company
        Warehouse w5 = factory.createWarehouse();
        w5.setCity("CityD");
        
        // Action: Retrieve toxic products in W5
        assertTrue(w5.retrieveToxicProductNames().isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp
        Company c4 = factory.createCompany();
        Warehouse w6 = factory.createWarehouse();
        w6.setCity("CityD");
        w6.setSurface(400);
        c4.getWarehouses().add(w6);
        
        // Create product and occupation
        Product acidK = createProduct("AcidK", true, "S5");
        createProductOccupation(w6, acidK, 12);
        
        // Action: Retrieve toxic products in W6
        Set<String> result = new HashSet<>(w6.retrieveToxicProductNames());
        Set<String> expected = new HashSet<>(Arrays.asList("AcidK"));
        assertEquals(expected, result);
    }
    
    // Helper methods
    private Product createProduct(String name, boolean toxic, String supplierName) {
        Product product = factory.createProduct();
        product.setName(name);
        product.setToxic(toxic);
        
        Supplier supplier = factory.createSupplier();
        supplier.setName(supplierName);
        product.setSupplier(supplier);
        
        return product;
    }
    
    private void createProductOccupation(Warehouse warehouse, Product product, double volume) {
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(volume);
        warehouse.getOccupations().add(occupation);
    }
}