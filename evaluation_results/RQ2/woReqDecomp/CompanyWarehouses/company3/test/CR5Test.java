package edu.company.company3.test;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Product;
import edu.company.ProductOccupation;
import edu.company.Supplier;
import edu.company.Warehouse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR5Test {
    
    private CompanyFactory factory;
    private Company company;
    
    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
        company = factory.createCompany();
    }

    // Test Case 1: Product in multiple warehouses
    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // Create warehouses
        Warehouse w22 = createWarehouse("CityM", "W22_addr");
        Warehouse w23 = createWarehouse("CityN", "W23_addr");
        
        // Create product and add to both warehouses
        Product materialX = createProduct("MaterialX");
        addProductToWarehouse(w22, materialX);
        addProductToWarehouse(w23, materialX);
        
        // Add warehouses to company
        company.getWarehouses().add(w22);
        company.getWarehouses().add(w23);
        
        // Find locations for MaterialX
        EList<Map<String, String>> locations = company.findProductLocations("MaterialX");
        
        // Verify results
        assertEquals(2, locations.size());
        assertContainsLocation(locations, "CityM", "W22_addr");
        assertContainsLocation(locations, "CityN", "W23_addr");
    }

    // Test Case 2: Product in single warehouse
    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // Create warehouse
        Warehouse w24 = createWarehouse("CityO", "W24_addr");
        
        // Create product and add to warehouse
        Product deviceY = createProduct("DeviceY");
        addProductToWarehouse(w24, deviceY);
        
        // Add warehouse to company
        company.getWarehouses().add(w24);
        
        // Find locations for DeviceY
        EList<Map<String, String>> locations = company.findProductLocations("DeviceY");
        
        // Verify results
        assertEquals(1, locations.size());
        assertContainsLocation(locations, "CityO", "W24_addr");
    }

    // Test Case 3: Non-existent product
    @Test
    public void testCase3_NonExistentProduct() {
        // Create warehouse with product PartZ
        Warehouse w25 = createWarehouse("CityP", "W25_addr");
        Product partZ = createProduct("PartZ");
        addProductToWarehouse(w25, partZ);
        
        // Add warehouse to company
        company.getWarehouses().add(w25);
        
        // Find locations for ToolW
        EList<Map<String, String>> locations = company.findProductLocations("ToolW");
        
        // Verify results
        assertTrue(locations.isEmpty());
    }

    // Test Case 4: Product in no warehouses
    @Test
    public void testCase4_ProductInNoWarehouses() {
        // Create warehouses with other products
        Warehouse w26 = createWarehouse("CityQ", "W26_addr");
        Warehouse w27 = createWarehouse("CityR", "W27_addr");
        Product otherProduct = createProduct("OtherProduct");
        addProductToWarehouse(w26, otherProduct);
        addProductToWarehouse(w27, otherProduct);
        
        // Add warehouses to company
        company.getWarehouses().add(w26);
        company.getWarehouses().add(w27);
        
        // Find locations for ItemV
        EList<Map<String, String>> locations = company.findProductLocations("ItemV");
        
        // Verify results
        assertTrue(locations.isEmpty());
    }

    // Test Case 5: Product in all warehouses
    @Test
    public void testCase5_ProductInAllWarehouses() {
        // Create warehouses
        Warehouse w28 = createWarehouse("CityP", "W28_addr");
        Warehouse w29 = createWarehouse("CityQ", "W29_addr");
        
        // Create common product and add to both warehouses
        Product commonPart = createProduct("CommonPart");
        addProductToWarehouse(w28, commonPart);
        addProductToWarehouse(w29, commonPart);
        
        // Add warehouses to company
        company.getWarehouses().add(w28);
        company.getWarehouses().add(w29);
        
        // Find locations for CommonPart
        EList<Map<String, String>> locations = company.findProductLocations("CommonPart");
        
        // Verify results
        assertEquals(2, locations.size());
        assertContainsLocation(locations, "CityP", "W28_addr");
        assertContainsLocation(locations, "CityQ", "W29_addr");
    }

    // Helper method to create warehouse with city and address
    private Warehouse createWarehouse(String city, String address) {
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity(city);
        warehouse.setAddress(address);
        return warehouse;
    }

    // Helper method to create product with name
    private Product createProduct(String name) {
        Product product = factory.createProduct();
        product.setName(name);
        return product;
    }

    // Helper method to add product to warehouse
    private void addProductToWarehouse(Warehouse warehouse, Product product) {
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        warehouse.getOccupations().add(occupation);
    }

    // Helper method to verify location presence
    private void assertContainsLocation(EList<Map<String, String>> locations, 
                                        String city, String address) {
        for (Map<String, String> location : locations) {
            if (city.equals(location.get("city")) && 
                address.equals(location.get("address"))) {
                return;
            }
        }
        fail("Location not found: " + city + ", " + address);
    }
}