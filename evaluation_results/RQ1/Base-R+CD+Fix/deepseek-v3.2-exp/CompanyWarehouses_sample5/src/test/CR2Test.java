import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityE");
        warehouse.setSurface(350.0);
        
        // Create suppliers
        Supplier supplierS6 = new Supplier();
        supplierS6.setName("S6");
        Supplier supplierS7 = new Supplier();
        supplierS7.setName("S7");
        
        // Create products with suppliers
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setSupplier(supplierS6);
        
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setSupplier(supplierS7);
        
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplierS6); // duplicate supplier
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(10.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(15.0);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(20.0);
        
        // Add occupations to warehouse
        warehouse.addOccupation(occupation1);
        warehouse.addOccupation(occupation2);
        warehouse.addOccupation(occupation3);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        
        // Verify both suppliers are present
        boolean foundS6 = false;
        boolean foundS7 = false;
        for (Supplier supplier : result) {
            if ("S6".equals(supplier.getName())) {
                foundS6 = true;
            } else if ("S7".equals(supplier.getName())) {
                foundS7 = true;
            }
        }
        assertTrue("Supplier S6 should be present", foundS6);
        assertTrue("Supplier S7 should be present", foundS7);
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityF");
        warehouse.setSurface(250.0);
        
        // Create supplier
        Supplier supplierS8 = new Supplier();
        supplierS8.setName("S8");
        
        // Create product with supplier
        Product product = new Product();
        product.setName("Item4");
        product.setSupplier(supplierS8);
        
        // Create product occupation
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(25.0);
        
        // Add occupation to warehouse
        warehouse.addOccupation(occupation);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityG");
        warehouse.setSurface(150.0);
        // No products added
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // No warehouses added to company
        
        // Since we can't retrieve a non-existent warehouse from the company,
        // we'll test that calling retrieveUniqueSuppliers() on a warehouse not in the company
        // would return empty list (but this scenario doesn't make sense in the context)
        
        // Instead, let's test that a warehouse not added to any company still returns empty list
        // when it has no products
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("SomeCity");
        nonExistentWarehouse.setSurface(200.0);
        
        // Action: Retrieve unique suppliers for non-existent warehouse
        List<Supplier> result = nonExistentWarehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setSurface(300.0);
        
        // Create supplier
        Supplier supplierS9 = new Supplier();
        supplierS9.setName("S9");
        
        // Create products with same supplier
        Product product1 = new Product();
        product1.setName("A1");
        product1.setSupplier(supplierS9);
        
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplierS9);
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(30.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(35.0);
        
        // Add occupations to warehouse
        warehouse.addOccupation(occupation1);
        warehouse.addOccupation(occupation2);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}