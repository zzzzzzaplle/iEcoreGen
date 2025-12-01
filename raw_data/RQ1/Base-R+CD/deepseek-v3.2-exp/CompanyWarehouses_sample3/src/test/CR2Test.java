import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityE");
        warehouse.setAddress("W7");
        warehouse.setSurface(350.0);
        
        // Add Product "Item1" from Supplier "S6"
        Supplier supplier1 = new Supplier();
        supplier1.setName("S6");
        supplier1.setAddress("AddressS6");
        
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setToxic(false);
        product1.setSupplier(supplier1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(100.0);
        warehouse.addOccupations(occupation1);
        
        // Add Product "Item2" from Supplier "S7"
        Supplier supplier2 = new Supplier();
        supplier2.setName("S7");
        supplier2.setAddress("AddressS7");
        
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setToxic(false);
        product2.setSupplier(supplier2);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(150.0);
        warehouse.addOccupations(occupation2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setToxic(false);
        product3.setSupplier(supplier1); // Same supplier as product1
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(200.0);
        warehouse.addOccupations(occupation3);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals("Should contain exactly 2 unique suppliers", 2, uniqueSuppliers.size());
        
        // Verify both suppliers are present
        boolean hasS6 = false;
        boolean hasS7 = false;
        for (Supplier supplier : uniqueSuppliers) {
            if (supplier.getName().equals("S6")) {
                hasS6 = true;
            }
            if (supplier.getName().equals("S7")) {
                hasS7 = true;
            }
        }
        assertTrue("Should contain supplier S6", hasS6);
        assertTrue("Should contain supplier S7", hasS7);
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityF");
        warehouse.setAddress("W8");
        warehouse.setSurface(250.0);
        
        // Add Product "Item4" from Supplier "S8"
        Supplier supplier = new Supplier();
        supplier.setName("S8");
        supplier.setAddress("AddressS8");
        
        Product product = new Product();
        product.setName("Item4");
        product.setToxic(false);
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(300.0);
        warehouse.addOccupations(occupation);
        
        company.addWarehouses(warehouse);
        
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
        warehouse.setAddress("W9");
        warehouse.setSurface(150.0);
        // No products added - warehouse is empty
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue("Should return empty list for warehouse with no products", 
                   uniqueSuppliers.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // Company is already initialized with no warehouses in @Before method
        
        // Action: Retrieve unique suppliers for "W10" (non-existent warehouse)
        // Since we don't have a method to get warehouse by name, we need to create a scenario
        // where we test a warehouse that exists but has no products (similar to empty warehouse)
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("AnyCity");
        warehouse.setAddress("W10");
        warehouse.setSurface(200.0);
        // No products added - simulating non-existent warehouse behavior
        
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue("Should return empty list for warehouse with no products", 
                   uniqueSuppliers.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setAddress("W11");
        warehouse.setSurface(300.0);
        
        // Add Product "A1" from Supplier "S9"
        Supplier supplier = new Supplier();
        supplier.setName("S9");
        supplier.setAddress("AddressS9");
        
        Product product1 = new Product();
        product1.setName("A1");
        product1.setToxic(false);
        product1.setSupplier(supplier);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(100.0);
        warehouse.addOccupations(occupation1);
        
        // Add Product "A2" from Supplier "S9"
        Product product2 = new Product();
        product2.setName("A2");
        product2.setToxic(false);
        product2.setSupplier(supplier); // Same supplier as product1
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(200.0);
        warehouse.addOccupations(occupation2);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals("Should contain exactly 1 supplier despite multiple products", 
                     1, uniqueSuppliers.size());
        assertEquals("Supplier should be S9", "S9", uniqueSuppliers.get(0).getName());
    }
}