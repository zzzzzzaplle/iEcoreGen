import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_WarehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityE");
        warehouse.setSurface(350.0);
        
        // Create suppliers
        Supplier supplierS6 = new Supplier();
        supplierS6.setName("S6");
        Supplier supplierS7 = new Supplier();
        supplierS7.setName("S7");
        
        // Add Product "Item1" from Supplier "S6"
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setSupplier(supplierS6);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(100.0);
        warehouse.addOccupations(occupation1);
        
        // Add Product "Item2" from Supplier "S7"
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setSupplier(supplierS7);
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(150.0);
        warehouse.addOccupations(occupation2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplierS6);
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(200.0);
        warehouse.addOccupations(occupation3);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        
        // Check that both suppliers are present and names match
        Set<String> supplierNames = new HashSet<>();
        for (Supplier supplier : result) {
            supplierNames.add(supplier.getName());
        }
        
        assertTrue(supplierNames.contains("S6"));
        assertTrue(supplierNames.contains("S7"));
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityF");
        warehouse.setSurface(250.0);
        
        // Create supplier
        Supplier supplierS8 = new Supplier();
        supplierS8.setName("S8");
        
        // Add Product "Item4" from Supplier "S8"
        Product product = new Product();
        product.setName("Item4");
        product.setSupplier(supplierS8);
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(120.0);
        warehouse.addOccupations(occupation);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityG");
        warehouse.setSurface(150.0);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // No warehouses added to company
        
        // Create a warehouse that is not added to the company
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("SomeCity");
        warehouse.setSurface(200.0);
        
        // Action: Retrieve unique suppliers for "W10" (warehouse not in company)
        // Since the warehouse is not part of the company, we test the method directly
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setSurface(300.0);
        
        // Create supplier
        Supplier supplierS9 = new Supplier();
        supplierS9.setName("S9");
        
        // Add Product "A1" from Supplier "S9"
        Product product1 = new Product();
        product1.setName("A1");
        product1.setSupplier(supplierS9);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(80.0);
        warehouse.addOccupations(occupation1);
        
        // Add Product "A2" from Supplier "S9"
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplierS9);
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(90.0);
        warehouse.addOccupations(occupation2);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}