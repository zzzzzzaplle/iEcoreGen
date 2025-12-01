package edu.company.company5.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import edu.company.CompanyFactory;
import edu.company.Company;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.ProductOccupation;

public class CR4Test {
    private CompanyFactory factory;
    private Company company;
    private Warehouse warehouse;

    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
        company = factory.createCompany();
    }

    // Test Case 1: Product exists in warehouse
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // Create Warehouse "W17" in "CityI"
        warehouse = factory.createWarehouse();
        warehouse.setCity("CityI");
        warehouse.setAddress("W17");
        company.getWarehouses().add(warehouse);
        
        // Add Product "WidgetA" to W17
        Product product = factory.createProduct();
        product.setName("WidgetA");
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        warehouse.getOccupations().add(occupation);
        
        // Verify "WidgetA" in W17
        assertTrue(warehouse.containsProduct("WidgetA"));
    }

    // Test Case 2: Product doesn't exist in warehouse
    @Test
    public void testCase2_ProductNotInWarehouse() {
        // Create Warehouse "W18" in "CityJ"
        warehouse = factory.createWarehouse();
        warehouse.setCity("CityJ");
        warehouse.setAddress("W18");
        company.getWarehouses().add(warehouse);
        
        // Add Product "GadgetB" to W18
        Product product = factory.createProduct();
        product.setName("GadgetB");
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        warehouse.getOccupations().add(occupation);
        
        // Verify "ToolC" in W18 (doesn't exist)
        assertFalse(warehouse.containsProduct("ToolC"));
    }

    // Test Case 3: Non-existent warehouse
    @Test
    public void testCase3_NonExistentWarehouse() {
        // Create Warehouse "W19" (no products)
        warehouse = factory.createWarehouse();
        warehouse.setAddress("W19");
        company.getWarehouses().add(warehouse);
        
        // Create different company with product "ItemX"
        Company otherCompany = factory.createCompany();
        Warehouse otherWarehouse = factory.createWarehouse();
        otherWarehouse.setAddress("W18");
        otherCompany.getWarehouses().add(otherWarehouse);
        
        Product product = factory.createProduct();
        product.setName("ItemX");
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        otherWarehouse.getOccupations().add(occupation);
        
        // Verify "ItemX" in W19 (doesn't exist)
        assertFalse(warehouse.containsProduct("ItemX"));
    }

    // Test Case 4: Empty warehouse check
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // Create Warehouse "W20" with no products
        warehouse = factory.createWarehouse();
        warehouse.setCity("CityK");
        warehouse.setAddress("W20");
        company.getWarehouses().add(warehouse);
        
        // Verify "PartY" in empty warehouse
        assertFalse(warehouse.containsProduct("PartY"));
    }

    // Test Case 5: Multiple products in warehouse
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // Create Warehouse "W21" in "CityL"
        warehouse = factory.createWarehouse();
        warehouse.setCity("CityL");
        warehouse.setAddress("W21");
        company.getWarehouses().add(warehouse);
        
        // Add multiple products
        String[] productNames = {"CompA", "CompB", "CompC"};
        for (String name : productNames) {
            Product product = factory.createProduct();
            product.setName(name);
            ProductOccupation occupation = factory.createProductOccupation();
            occupation.setProduct(product);
            warehouse.getOccupations().add(occupation);
        }
        
        // Verify "CompB" exists
        assertTrue(warehouse.containsProduct("CompB"));
    }
}