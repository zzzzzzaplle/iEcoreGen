package edu.company.company4.test;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.company.CompanyFactory;
import edu.company.Company;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.ProductOccupation;

public class CR4Test {

    // Test Case 1: Product exists in warehouse
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // Setup
        CompanyFactory factory = CompanyFactory.eINSTANCE;
        Company c1 = factory.createCompany();
        Warehouse w17 = factory.createWarehouse();
        w17.setCity("CityI");
        c1.getWarehouses().add(w17);
        
        Product widgetA = factory.createProduct();
        widgetA.setName("WidgetA");
        
        ProductOccupation occ = factory.createProductOccupation();
        occ.setProduct(widgetA);
        w17.getOccupations().add(occ);
        
        // Action & Verification
        assertTrue(w17.containsProduct("WidgetA"));
    }

    // Test Case 2: Product doesn't exist in warehouse
    @Test
    public void testCase2_ProductNotInWarehouse() {
        // Setup
        CompanyFactory factory = CompanyFactory.eINSTANCE;
        Company c1 = factory.createCompany();
        Warehouse w18 = factory.createWarehouse();
        w18.setCity("CityJ");
        c1.getWarehouses().add(w18);
        
        Product gadgetB = factory.createProduct();
        gadgetB.setName("GadgetB");
        
        ProductOccupation occ = factory.createProductOccupation();
        occ.setProduct(gadgetB);
        w18.getOccupations().add(occ);
        
        // Action & Verification
        assertFalse(w18.containsProduct("ToolC"));
    }

    // Test Case 3: Non-existent warehouse
    @Test
    public void testCase3_NonExistentWarehouse() {
        // Setup
        CompanyFactory factory = CompanyFactory.eINSTANCE;
        Company c1 = factory.createCompany();
        Warehouse w19 = factory.createWarehouse();
        c1.getWarehouses().add(w19); // Warehouse exists but no products
        
        // Action & Verification
        assertFalse(w19.containsProduct("ItemX"));
    }

    // Test Case 4: Empty warehouse check
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // Setup
        CompanyFactory factory = CompanyFactory.eINSTANCE;
        Company c1 = factory.createCompany();
        Warehouse w20 = factory.createWarehouse();
        w20.setCity("CityK");
        c1.getWarehouses().add(w20);
        // No products added
        
        // Action & Verification
        assertFalse(w20.containsProduct("PartY"));
    }

    // Test Case 5: Multiple products in warehouse
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // Setup
        CompanyFactory factory = CompanyFactory.eINSTANCE;
        Company c1 = factory.createCompany();
        Warehouse w21 = factory.createWarehouse();
        w21.setCity("CityL");
        c1.getWarehouses().add(w21);
        
        // Create products
        String[] products = {"CompA", "CompB", "CompC"};
        for (String prodName : products) {
            Product p = factory.createProduct();
            p.setName(prodName);
            
            ProductOccupation occ = factory.createProductOccupation();
            occ.setProduct(p);
            w21.getOccupations().add(occ);
        }
        
        // Action & Verification
        assertTrue(w21.containsProduct("CompB"));
    }
}