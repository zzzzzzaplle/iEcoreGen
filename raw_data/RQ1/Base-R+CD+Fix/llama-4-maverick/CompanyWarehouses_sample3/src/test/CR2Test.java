import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class CR2Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new company before each test
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
        
        // Create products and occupations
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setSupplier(supplierS6);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setSupplier(supplierS7);
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplierS6); // Duplicate supplier
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        
        // Add occupations to warehouse
        warehouse.addOccupations(occupation1);
        warehouse.addOccupations(occupation2);
        warehouse.addOccupations(occupation3);
        
        // Add warehouse to company
        company.addWarehouses(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> "S6".equals(s.getName())));
        assertTrue(result.stream().anyMatch(s -> "S7".equals(s.getName())));
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
        
        // Create product and occupation
        Product product = new Product();
        product.setName("Item4");
        product.setSupplier(supplierS8);
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        
        // Add occupation to warehouse
        warehouse.addOccupations(occupation);
        
        // Add warehouse to company
        company.addWarehouses(warehouse);
        
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
        
        // Add warehouse to company (no products added)
        company.addWarehouses(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        
        // Action: Retrieve unique suppliers for "W10" (which doesn't exist)
        // Since we can't directly test non-existent warehouse in the current API,
        // we'll test by calling retrieveUniqueSuppliers on a warehouse that's not added to company
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("SomeCity");
        nonExistentWarehouse.setSurface(200.0);
        
        // This warehouse is not added to company, simulating "non-existent" scenario
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
        
        // Create products and occupations
        Product product1 = new Product();
        product1.setName("A1");
        product1.setSupplier(supplierS9);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplierS9); // Same supplier
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        
        // Add occupations to warehouse
        warehouse.addOccupations(occupation1);
        warehouse.addOccupations(occupation2);
        
        // Add warehouse to company
        company.addWarehouses(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}