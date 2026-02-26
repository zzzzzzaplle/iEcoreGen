import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a fresh company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityE");
        warehouse.setAddress("W7");
        warehouse.setSurface(350.0);
        
        // Create suppliers
        Supplier supplierS6 = new Supplier();
        supplierS6.setName("S6");
        Supplier supplierS7 = new Supplier();
        supplierS7.setName("S7");
        
        // Create products with their occupations
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setSupplier(supplierS6);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(100.0);
        
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setSupplier(supplierS7);
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(150.0);
        
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplierS6); // duplicate supplier
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(200.0);
        
        // Add occupations to warehouse
        warehouse.addOccupation(occupation1);
        warehouse.addOccupation(occupation2);
        warehouse.addOccupation(occupation3);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals("Should return 2 unique suppliers", 2, result.size());
        assertTrue("Should contain supplier S6", containsSupplierByName(result, "S6"));
        assertTrue("Should contain supplier S7", containsSupplierByName(result, "S7"));
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityF");
        warehouse.setAddress("W8");
        warehouse.setSurface(250.0);
        
        // Create supplier
        Supplier supplierS8 = new Supplier();
        supplierS8.setName("S8");
        
        // Create product with occupation
        Product product = new Product();
        product.setName("Item4");
        product.setSupplier(supplierS8);
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(100.0);
        
        // Add occupation to warehouse
        warehouse.addOccupation(occupation);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals("Should return 1 supplier", 1, result.size());
        assertEquals("Should be supplier S8", "S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityG");
        warehouse.setAddress("W9");
        warehouse.setSurface(150.0);
        
        // Add warehouse to company (no products added)
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue("Should return empty list", result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // (company is already empty from @Before setup)
        
        // Action: Retrieve unique suppliers for "W10" (non-existent warehouse)
        // Since we can't directly call retrieveUniqueSuppliers on a non-existent warehouse,
        // we need to simulate this by creating a warehouse but not adding it to the company
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("SomeCity");
        nonExistentWarehouse.setAddress("W10");
        nonExistentWarehouse.setSurface(200.0);
        
        List<Supplier> result = nonExistentWarehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue("Should return empty list for non-existent warehouse", result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setAddress("W11");
        warehouse.setSurface(300.0);
        
        // Create supplier
        Supplier supplierS9 = new Supplier();
        supplierS9.setName("S9");
        
        // Create products with their occupations (same supplier)
        Product product1 = new Product();
        product1.setName("A1");
        product1.setSupplier(supplierS9);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(50.0);
        
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplierS9);
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(75.0);
        
        // Add occupations to warehouse
        warehouse.addOccupation(occupation1);
        warehouse.addOccupation(occupation2);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals("Should return 1 supplier", 1, result.size());
        assertEquals("Should be supplier S9", "S9", result.get(0).getName());
    }
    
    // Helper method to check if a list of suppliers contains a supplier with specific name
    private boolean containsSupplierByName(List<Supplier> suppliers, String name) {
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}