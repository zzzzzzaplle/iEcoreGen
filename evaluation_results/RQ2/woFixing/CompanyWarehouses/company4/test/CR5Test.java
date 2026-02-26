package edu.company.company4.test;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Warehouse;
import edu.company.ProductOccupation;
import edu.company.Product;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.eclipse.emf.common.util.BasicEList;

public class CR5Test {

    // Test Case 1: Product in multiple warehouses
    @Test
    public void testCase1_productInMultipleWarehouses() {
        CompanyFactory factory = CompanyFactory.eINSTANCE;
        Company company = factory.createCompany();
        
        // Create warehouses
        Warehouse warehouse1 = factory.createWarehouse();
        warehouse1.setCity("CityM");
        warehouse1.setAddress("W22_addr");
        company.getWarehouses().add(warehouse1);
        
        Warehouse warehouse2 = factory.createWarehouse();
        warehouse2.setCity("CityN");
        warehouse2.setAddress("W23_addr");
        company.getWarehouses().add(warehouse2);
        
        // Create product and occupations
        Product product = factory.createProduct();
        product.setName("MaterialX");
        
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product);
        warehouse1.getOccupations().add(occupation1);
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product);
        warehouse2.getOccupations().add(occupation2);
        
        // Execute method under test
        List<Map<String, String>> locations = company.findProductLocations("MaterialX");
        
        // Verify results
        assertEquals(2, locations.size());
        
        Map<String, String> location1 = locations.get(0);
        assertEquals("CityM", location1.get("city"));
        assertEquals("W22_addr", location1.get("address"));
        
        Map<String, String> location2 = locations.get(1);
        assertEquals("CityN", location2.get("city"));
        assertEquals("W23_addr", location2.get("address"));
    }

    // Test Case 2: Product in single warehouse
    @Test
    public void testCase2_productInSingleWarehouse() {
        CompanyFactory factory = CompanyFactory.eINSTANCE;
        Company company = factory.createCompany();
        
        // Create warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityO");
        warehouse.setAddress("W24_addr");
        company.getWarehouses().add(warehouse);
        
        // Create product and occupation
        Product product = factory.createProduct();
        product.setName("DeviceY");
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        warehouse.getOccupations().add(occupation);
        
        // Execute method under test
        List<Map<String, String>> locations = company.findProductLocations("DeviceY");
        
        // Verify results
        assertEquals(1, locations.size());
        
        Map<String, String> location = locations.get(0);
        assertEquals("CityO", location.get("city"));
        assertEquals("W24_addr", location.get("address"));
    }

    // Test Case 3: Non-existent product
    @Test
    public void testCase3_nonExistentProduct() {
        CompanyFactory factory = CompanyFactory.eINSTANCE;
        Company company = factory.createCompany();
        
        // Create warehouse with different product
        Warehouse warehouse = factory.createWarehouse();
        Product existingProduct = factory.createProduct();
        existingProduct.setName("PartZ");
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(existingProduct);
        warehouse.getOccupations().add(occupation);
        company.getWarehouses().add(warehouse);
        
        // Execute method under test
        List<Map<String, String>> locations = company.findProductLocations("ToolW");
        
        // Verify no results
        assertTrue(locations.isEmpty());
    }

    // Test Case 4: Product in no warehouses
    @Test
    public void testCase4_productInNoWarehouses() {
        CompanyFactory factory = CompanyFactory.eINSTANCE;
        Company company = factory.createCompany();
        
        // Create warehouses with different products
        Warehouse warehouse1 = factory.createWarehouse();
        Product product1 = factory.createProduct();
        product1.setName("WidgetA");
        
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product1);
        warehouse1.getOccupations().add(occupation1);
        company.getWarehouses().add(warehouse1);
        
        Warehouse warehouse2 = factory.createWarehouse();
        Product product2 = factory.createProduct();
        product2.setName("GadgetB");
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product2);
        warehouse2.getOccupations().add(occupation2);
        company.getWarehouses().add(warehouse2);
        
        // Execute method under test
        List<Map<String, String>> locations = company.findProductLocations("ItemV");
        
        // Verify no results
        assertTrue(locations.isEmpty());
    }

    // Test Case 5: Product in all warehouses
    @Test
    public void testCase5_productInAllWarehouses() {
        CompanyFactory factory = CompanyFactory.eINSTANCE;
        Company company = factory.createCompany();
        
        // Create warehouses
        Warehouse warehouse1 = factory.createWarehouse();
        warehouse1.setCity("CityP");
        warehouse1.setAddress("W28_addr");
        company.getWarehouses().add(warehouse1);
        
        Warehouse warehouse2 = factory.createWarehouse();
        warehouse2.setCity("CityQ");
        warehouse2.setAddress("W29_addr");
        company.getWarehouses().add(warehouse2);
        
        // Create product and occupations
        Product product = factory.createProduct();
        product.setName("CommonPart");
        
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product);
        warehouse1.getOccupations().add(occupation1);
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product);
        warehouse2.getOccupations().add(occupation2);
        
        // Execute method under test
        List<Map<String, String>> locations = company.findProductLocations("CommonPart");
        
        // Verify results
        assertEquals(2, locations.size());
        
        // Check both locations exist regardless of order
        Map<String, String> location1 = locations.get(0);
        Map<String, String> location2 = locations.get(1);
        
        if ("CityP".equals(location1.get("city"))) {
            assertEquals("W28_addr", location1.get("address"));
            assertEquals("CityQ", location2.get("city"));
            assertEquals("W29_addr", location2.get("address"));
        } else {
            assertEquals("CityQ", location1.get("city"));
            assertEquals("W29_addr", location1.get("address"));
            assertEquals("CityP", location2.get("city"));
            assertEquals("W28_addr", location2.get("address"));
        }
    }
}