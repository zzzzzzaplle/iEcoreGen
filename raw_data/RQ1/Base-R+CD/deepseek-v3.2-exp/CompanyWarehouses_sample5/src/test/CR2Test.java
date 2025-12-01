import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        warehouse.setAddress("W7");
        warehouse.setSurface(350.0);
        
        // Add Product "Item1" from Supplier "S6"
        Product product1 = new Product();
        product1.setName("Item1");
        Supplier supplier1 = new Supplier();
        supplier1.setName("S6");
        supplier1.setAddress("AddressS6");
        product1.setSupplier(supplier1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(100.0);
        warehouse.addOccupation(occupation1);
        
        // Add Product "Item2" from Supplier "S7"
        Product product2 = new Product();
        product2.setName("Item2");
        Supplier supplier2 = new Supplier();
        supplier2.setName("S7");
        supplier2.setAddress("AddressS7");
        product2.setSupplier(supplier2);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(150.0);
        warehouse.addOccupation(occupation2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplier1); // Same supplier as product1
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(200.0);
        warehouse.addOccupation(occupation3);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        assertTrue(result.contains(supplier1));
        assertTrue(result.contains(supplier2));
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityF");
        warehouse.setAddress("W8");
        warehouse.setSurface(250.0);
        
        // Add Product "Item4" from Supplier "S8"
        Product product = new Product();
        product.setName("Item4");
        Supplier supplier = new Supplier();
        supplier.setName("S8");
        supplier.setAddress("AddressS8");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(120.0);
        warehouse.addOccupation(occupation);
        
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
        warehouse.setAddress("W9");
        warehouse.setSurface(150.0);
        // No products added
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // No warehouses added to company
        
        // Action: Retrieve unique suppliers for "W10"
        // Since we don't have access to warehouse by name, we'll test with a new warehouse
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("Unknown");
        nonExistentWarehouse.setAddress("W10");
        
        List<Supplier> result = nonExistentWarehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setAddress("W11");
        warehouse.setSurface(300.0);
        
        // Add Product "A1" from Supplier "S9"
        Product product1 = new Product();
        product1.setName("A1");
        Supplier supplier = new Supplier();
        supplier.setName("S9");
        supplier.setAddress("AddressS9");
        product1.setSupplier(supplier);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(80.0);
        warehouse.addOccupation(occupation1);
        
        // Add Product "A2" from Supplier "S9"
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplier); // Same supplier
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(90.0);
        warehouse.addOccupation(occupation2);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}