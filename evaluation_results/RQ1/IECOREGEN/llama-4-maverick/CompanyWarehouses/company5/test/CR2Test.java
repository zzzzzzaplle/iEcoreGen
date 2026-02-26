package edu.company.company5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.company.*;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize company C1
        company = CompanyFactory.eINSTANCE.createCompany();
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityE");
        warehouse.setAddress("AddressW7");
        warehouse.setSurface(350.0);
        company.getWarehouses().add(warehouse);
        
        // Add Product "Item1" from Supplier "S6"
        Product product1 = CompanyFactory.eINSTANCE.createProduct();
        product1.setName("Item1");
        Supplier supplier1 = CompanyFactory.eINSTANCE.createSupplier();
        supplier1.setName("S6");
        supplier1.setAddress("AddressS6");
        product1.setSupplier(supplier1);
        
        ProductOccupation occupation1 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(10.0);
        warehouse.getOccupations().add(occupation1);
        
        // Add Product "Item2" from Supplier "S7"
        Product product2 = CompanyFactory.eINSTANCE.createProduct();
        product2.setName("Item2");
        Supplier supplier2 = CompanyFactory.eINSTANCE.createSupplier();
        supplier2.setName("S7");
        supplier2.setAddress("AddressS7");
        product2.setSupplier(supplier2);
        
        ProductOccupation occupation2 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(15.0);
        warehouse.getOccupations().add(occupation2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = CompanyFactory.eINSTANCE.createProduct();
        product3.setName("Item3");
        product3.setSupplier(supplier1); // Same supplier as product1
        
        ProductOccupation occupation3 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(20.0);
        warehouse.getOccupations().add(occupation3);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getName().equals("S6")));
        assertTrue(result.stream().anyMatch(s -> s.getName().equals("S7")));
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityF");
        warehouse.setAddress("AddressW8");
        warehouse.setSurface(250.0);
        company.getWarehouses().add(warehouse);
        
        // Add Product "Item4" from Supplier "S8"
        Product product = CompanyFactory.eINSTANCE.createProduct();
        product.setName("Item4");
        Supplier supplier = CompanyFactory.eINSTANCE.createSupplier();
        supplier.setName("S8");
        supplier.setAddress("AddressS8");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(25.0);
        warehouse.getOccupations().add(occupation);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityG");
        warehouse.setAddress("AddressW9");
        warehouse.setSurface(150.0);
        company.getWarehouses().add(warehouse);
        // No products added
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // Initialize company C1 with no warehouses
        // Already done in setUp()
        
        // Since we're testing a method on a warehouse object, we need to create a warehouse
        // but don't add it to the company (simulating non-existent warehouse in company context)
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        // Not adding to company.getWarehouses()
        
        // Action: Retrieve unique suppliers for warehouse (which has no products)
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityH");
        warehouse.setAddress("AddressW11");
        warehouse.setSurface(300.0);
        company.getWarehouses().add(warehouse);
        
        // Add Product "A1" from Supplier "S9"
        Product product1 = CompanyFactory.eINSTANCE.createProduct();
        product1.setName("A1");
        Supplier supplier = CompanyFactory.eINSTANCE.createSupplier();
        supplier.setName("S9");
        supplier.setAddress("AddressS9");
        product1.setSupplier(supplier);
        
        ProductOccupation occupation1 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(12.0);
        warehouse.getOccupations().add(occupation1);
        
        // Add Product "A2" from Supplier "S9"
        Product product2 = CompanyFactory.eINSTANCE.createProduct();
        product2.setName("A2");
        product2.setSupplier(supplier); // Same supplier
        
        ProductOccupation occupation2 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(18.0);
        warehouse.getOccupations().add(occupation2);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}