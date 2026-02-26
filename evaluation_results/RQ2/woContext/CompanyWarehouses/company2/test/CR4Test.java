package edu.company.company2.test;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.company.CompanyFactory;
import edu.company.Company;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.ProductOccupation;

public class CR4Test {
    private CompanyFactory factory = CompanyFactory.eINSTANCE;
    private Company company;
    private Warehouse warehouse;
    
    // Test Case 1: Product exists in warehouse
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // Setup
        company = factory.createCompany();
        warehouse = factory.createWarehouse();
        warehouse.setCity("CityI");
        company.getWarehouses().add(warehouse);
        
        Product product = factory.createProduct();
        product.setName("WidgetA");
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        warehouse.getOccupations().add(occupation);
        
        // Action & Assertion
        assertTrue("WidgetA should exist in warehouse", warehouse.containsProduct("WidgetA"));
    }
    
    // Test Case 2: Product doesn't exist in warehouse
    @Test
    public void testCase2_ProductDoesNotExistInWarehouse() {
        // Setup
        company = factory.createCompany();
        warehouse = factory.createWarehouse();
        warehouse.setCity("CityJ");
        company.getWarehouses().add(warehouse);
        
        Product product = factory.createProduct();
        product.setName("GadgetB");
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        warehouse.getOccupations().add(occupation);
        
        // Action & Assertion
        assertFalse("ToolC should not exist in warehouse", warehouse.containsProduct("ToolC"));
    }
    
    // Test Case 3: Non-existent warehouse
    @Test
    public void testCase3_NonExistentWarehouse() {
        // Setup
        company = factory.createCompany();
        Warehouse w19 = factory.createWarehouse();
        company.getWarehouses().add(w19);
        
        // Create separate company/warehouse with product
        Company company2 = factory.createCompany();
        Warehouse w18 = factory.createWarehouse();
        company2.getWarehouses().add(w18);
        
        Product product = factory.createProduct();
        product.setName("ItemX");
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        w18.getOccupations().add(occupation);
        
        // Action & Assertion
        assertFalse("ItemX should not exist in W19", w19.containsProduct("ItemX"));
    }
    
    // Test Case 4: Empty warehouse check
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // Setup
        company = factory.createCompany();
        warehouse = factory.createWarehouse();
        warehouse.setCity("CityK");
        company.getWarehouses().add(warehouse);
        
        // Action & Assertion
        assertFalse("PartY should not exist in empty warehouse", warehouse.containsProduct("PartY"));
    }
    
    // Test Case 5: Multiple products in warehouse
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // Setup
        company = factory.createCompany();
        warehouse = factory.createWarehouse();
        warehouse.setCity("CityL");
        company.getWarehouses().add(warehouse);
        
        // Add CompA
        Product productA = factory.createProduct();
        productA.setName("CompA");
        ProductOccupation occupationA = factory.createProductOccupation();
        occupationA.setProduct(productA);
        warehouse.getOccupations().add(occupationA);
        
        // Add CompB
        Product productB = factory.createProduct();
        productB.setName("CompB");
        ProductOccupation occupationB = factory.createProductOccupation();
        occupationB.setProduct(productB);
        warehouse.getOccupations().add(occupationB);
        
        // Add CompC
        Product productC = factory.createProduct();
        productC.setName("CompC");
        ProductOccupation occupationC = factory.createProductOccupation();
        occupationC.setProduct(productC);
        warehouse.getOccupations().add(occupationC);
        
        // Action & Assertion
        assertTrue("CompB should exist in warehouse", warehouse.containsProduct("CompB"));
    }
}