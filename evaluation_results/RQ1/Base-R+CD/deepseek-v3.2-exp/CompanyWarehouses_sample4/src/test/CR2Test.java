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
        warehouse.setSurface(350.0);
        
        // Create Supplier S6 and products
        Supplier supplierS6 = new Supplier();
        supplierS6.setName("S6");
        
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setSupplier(supplierS6);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(100.0);
        
        // Create Supplier S7 and product
        Supplier supplierS7 = new Supplier();
        supplierS7.setName("S7");
        
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setSupplier(supplierS7);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(150.0);
        
        // Add another product from Supplier S6 (duplicate supplier)
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplierS6);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(200.0);
        
        // Add all occupations to warehouse
        warehouse.addOccupation(occupation1);
        warehouse.addOccupation(occupation2);
        warehouse.addOccupation(occupation3);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals("Should contain exactly 2 unique suppliers", 2, uniqueSuppliers.size());
        
        // Verify both suppliers are present
        boolean foundS6 = false;
        boolean foundS7 = false;
        for (Supplier supplier : uniqueSuppliers) {
            if (supplier.getName().equals("S6")) {
                foundS6 = true;
            } else if (supplier.getName().equals("S7")) {
                foundS7 = true;
            }
        }
        
        assertTrue("Should contain supplier S6", foundS6);
        assertTrue("Should contain supplier S7", foundS7);
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityF");
        warehouse.setSurface(250.0);
        
        // Create Supplier S8 and product
        Supplier supplierS8 = new Supplier();
        supplierS8.setName("S8");
        
        Product product = new Product();
        product.setName("Item4");
        product.setSupplier(supplierS8);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(120.0);
        
        // Add occupation to warehouse
        warehouse.addOccupation(occupation);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals("Should contain exactly 1 supplier", 1, uniqueSuppliers.size());
        assertEquals("Supplier should be S8", "S8", uniqueSuppliers.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityG");
        warehouse.setSurface(150.0);
        
        // Add warehouse to company (no products added)
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue("Should return empty list for warehouse with no products", uniqueSuppliers.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // (company is already initialized with no warehouses in setUp method)
        
        // Action: Retrieve unique suppliers for "W10" (non-existent warehouse)
        // Since we don't have access to retrieve warehouse by name, we'll test with a warehouse not added to company
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("SomeCity");
        nonExistentWarehouse.setSurface(200.0);
        
        // This warehouse exists as an object but is not in the company and has no products
        List<Supplier> uniqueSuppliers = nonExistentWarehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue("Should return empty list for warehouse with no products", uniqueSuppliers.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setSurface(300.0);
        
        // Create Supplier S9
        Supplier supplierS9 = new Supplier();
        supplierS9.setName("S9");
        
        // Create Product A1 from Supplier S9
        Product product1 = new Product();
        product1.setName("A1");
        product1.setSupplier(supplierS9);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(80.0);
        
        // Create Product A2 from Supplier S9 (same supplier)
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplierS9);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(90.0);
        
        // Add both occupations to warehouse
        warehouse.addOccupation(occupation1);
        warehouse.addOccupation(occupation2);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals("Should contain exactly 1 supplier", 1, uniqueSuppliers.size());
        assertEquals("Supplier should be S9", "S9", uniqueSuppliers.get(0).getName());
    }
}