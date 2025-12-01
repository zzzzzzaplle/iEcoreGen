import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_WarehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouseW7 = new Warehouse();
        warehouseW7.setCity("CityE");
        warehouseW7.setSurface(350.0);
        
        // Create suppliers
        Supplier supplierS6 = new Supplier();
        supplierS6.setName("S6");
        Supplier supplierS7 = new Supplier();
        supplierS7.setName("S7");
        
        // Create products
        Product productItem1 = new Product();
        productItem1.setName("Item1");
        productItem1.setSupplier(supplierS6);
        
        Product productItem2 = new Product();
        productItem2.setName("Item2");
        productItem2.setSupplier(supplierS7);
        
        Product productItem3 = new Product();
        productItem3.setName("Item3");
        productItem3.setSupplier(supplierS6); // duplicate supplier
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productItem1);
        occupation1.setVolume(100.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productItem2);
        occupation2.setVolume(150.0);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(productItem3);
        occupation3.setVolume(200.0);
        
        // Add occupations to warehouse
        warehouseW7.addOccupations(occupation1);
        warehouseW7.addOccupations(occupation2);
        warehouseW7.addOccupations(occupation3);
        
        // Add warehouse to company
        company.addWarehouses(warehouseW7);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = warehouseW7.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        // Check supplier names
        List<String> supplierNames = new ArrayList<>();
        for (Supplier supplier : result) {
            supplierNames.add(supplier.getName());
        }
        assertTrue(supplierNames.contains("S6"));
        assertTrue(supplierNames.contains("S7"));
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouseW8 = new Warehouse();
        warehouseW8.setCity("CityF");
        warehouseW8.setSurface(250.0);
        
        // Create supplier
        Supplier supplierS8 = new Supplier();
        supplierS8.setName("S8");
        
        // Create product
        Product productItem4 = new Product();
        productItem4.setName("Item4");
        productItem4.setSupplier(supplierS8);
        
        // Create product occupation
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(productItem4);
        occupation.setVolume(100.0);
        
        // Add occupation to warehouse
        warehouseW8.addOccupations(occupation);
        
        // Add warehouse to company
        company.addWarehouses(warehouseW8);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = warehouseW8.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouseW9 = new Warehouse();
        warehouseW9.setCity("CityG");
        warehouseW9.setSurface(150.0);
        
        // Add warehouse to company
        company.addWarehouses(warehouseW9);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = warehouseW9.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // (Already done in setUp method)
        
        // Action: Retrieve unique suppliers for "W10"
        // Since we're testing the method on a specific warehouse instance, 
        // and W10 doesn't exist in our company, we need to create a scenario
        // where we try to call the method on a warehouse that exists but has no products
        
        // Create a warehouse that exists but has no products
        Warehouse warehouseW10 = new Warehouse();
        warehouseW10.setCity("SomeCity");
        warehouseW10.setSurface(200.0);
        
        // Action: Retrieve unique suppliers for W10 (empty warehouse)
        List<Supplier> result = warehouseW10.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouseW11 = new Warehouse();
        warehouseW11.setCity("CityH");
        warehouseW11.setSurface(300.0);
        
        // Create supplier
        Supplier supplierS9 = new Supplier();
        supplierS9.setName("S9");
        
        // Create products
        Product productA1 = new Product();
        productA1.setName("A1");
        productA1.setSupplier(supplierS9);
        
        Product productA2 = new Product();
        productA2.setName("A2");
        productA2.setSupplier(supplierS9); // same supplier
        
        // Create product occupations
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(productA1);
        occupation1.setVolume(120.0);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(productA2);
        occupation2.setVolume(180.0);
        
        // Add occupations to warehouse
        warehouseW11.addOccupations(occupation1);
        warehouseW11.addOccupations(occupation2);
        
        // Add warehouse to company
        company.addWarehouses(warehouseW11);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouseW11.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}