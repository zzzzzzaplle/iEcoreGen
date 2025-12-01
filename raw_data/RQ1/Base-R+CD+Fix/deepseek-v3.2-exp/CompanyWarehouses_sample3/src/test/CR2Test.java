import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

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
        supplierS6.setAddress("AddressS6");
        
        Supplier supplierS7 = new Supplier();
        supplierS7.setName("S7");
        supplierS7.setAddress("AddressS7");
        
        // Create products
        Product item1 = new Product();
        item1.setName("Item1");
        item1.setSupplier(supplierS6);
        
        Product item2 = new Product();
        item2.setName("Item2");
        item2.setSupplier(supplierS7);
        
        Product item3 = new Product();
        item3.setName("Item3");
        item3.setSupplier(supplierS6); // duplicate supplier
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(item1);
        occupation1.setVolume(100.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(item2);
        occupation2.setVolume(150.0);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(item3);
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
        assertEquals(2, result.size());
        
        // Verify both suppliers are present
        boolean foundS6 = false;
        boolean foundS7 = false;
        for (Supplier supplier : result) {
            if (supplier.getName().equals("S6")) {
                foundS6 = true;
            } else if (supplier.getName().equals("S7")) {
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
        supplierS8.setAddress("AddressS8");
        
        // Create product
        Product item4 = new Product();
        item4.setName("Item4");
        item4.setSupplier(supplierS8);
        
        // Create product occupation
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(item4);
        occupation.setVolume(100.0);
        
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
        // Company is already initialized with no warehouses in setUp()
        
        // Action: Since we don't have warehouse "W10", we need to test a warehouse that exists in the company
        // but we'll create a warehouse and test it directly
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("CityX");
        nonExistentWarehouse.setSurface(200.0);
        
        // Action: Retrieve unique suppliers for non-existent warehouse in company context
        // (Note: The company doesn't have this warehouse, but we can test the warehouse method directly)
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
        supplierS9.setAddress("AddressS9");
        
        // Create products
        Product a1 = new Product();
        a1.setName("A1");
        a1.setSupplier(supplierS9);
        
        Product a2 = new Product();
        a2.setName("A2");
        a2.setSupplier(supplierS9);
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(a1);
        occupation1.setVolume(120.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(a2);
        occupation2.setVolume(180.0);
        
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