package edu.company.company3.test;

import edu.company.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR4Test {
    private CompanyFactory factory;
    private Company company;

    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
        company = factory.createCompany();
    }

    // Test Case 1: Product exists in warehouse
    @Test
    public void testCase1_productExists() {
        // Create warehouse W17
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityI");
        company.getWarehouses().add(warehouse);
        
        // Create product WidgetA
        Product product = factory.createProduct();
        product.setName("WidgetA");
        
        // Create product occupation
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        warehouse.getOccupations().add(occupation);
        
        // Verify WidgetA exists in W17
        assertTrue(warehouse.containsProduct("WidgetA"));
    }

    // Test Case 2: Product doesn't exist in warehouse
    @Test
    public void testCase2_productDoesNotExist() {
        // Create warehouse W18
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityJ");
        company.getWarehouses().add(warehouse);
        
        // Create product GadgetB
        Product product = factory.createProduct();
        product.setName("GadgetB");
        
        // Create product occupation
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        warehouse.getOccupations().add(occupation);
        
        // Verify ToolC doesn't exist in W18
        assertFalse(warehouse.containsProduct("ToolC"));
    }

    // Test Case 3: Non-existent warehouse
    @Test
    public void testCase3_nonExistentWarehouse() {
        // Create warehouse W19 (empty)
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityX");
        company.getWarehouses().add(warehouse);
        
        // Create different company C2
        Company company2 = factory.createCompany();
        Warehouse warehouse2 = factory.createWarehouse();
        warehouse2.setCity("CityY");
        company2.getWarehouses().add(warehouse2);
        
        // Add ItemX to warehouse in C2
        Product product = factory.createProduct();
        product.setName("ItemX");
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        warehouse2.getOccupations().add(occupation);
        
        // Verify ItemX doesn't exist in W19 (in C1)
        assertFalse(warehouse.containsProduct("ItemX"));
    }

    // Test Case 4: Empty warehouse check
    @Test
    public void testCase4_emptyWarehouse() {
        // Create empty warehouse W20
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityK");
        company.getWarehouses().add(warehouse);
        
        // Verify PartY doesn't exist in W20
        assertFalse(warehouse.containsProduct("PartY"));
    }

    // Test Case 5: Multiple products in warehouse
    @Test
    public void testCase5_multipleProducts() {
        // Create warehouse W21
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityL");
        company.getWarehouses().add(warehouse);
        
        // Add multiple products
        String[] products = {"CompA", "CompB", "CompC"};
        for (String productName : products) {
            Product product = factory.createProduct();
            product.setName(productName);
            ProductOccupation occupation = factory.createProductOccupation();
            occupation.setProduct(product);
            warehouse.getOccupations().add(occupation);
        }
        
        // Verify CompB exists in W21
        assertTrue(warehouse.containsProduct("CompB"));
    }
}