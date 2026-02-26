import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize company before each test
        company = new Company();
        company.setName("C1");
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
        product1.setSupplier(supplier1);
        warehouse.addProduct(product1);
        
        // Add Product "Item2" from Supplier "S7"
        Product product2 = new Product();
        product2.setName("Item2");
        Supplier supplier2 = new Supplier();
        supplier2.setName("S7");
        product2.setSupplier(supplier2);
        warehouse.addProduct(product2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplier1); // Same supplier as product1
        warehouse.addProduct(product3);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<String> result = company.getDistinctSuppliersInWarehouse("W7");
        
        // Expected Output: ["S6", "S7"]
        List<String> expected = Arrays.asList("S6", "S7");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
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
        product.setSupplier(supplier);
        warehouse.addProduct(product);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<String> result = company.getDistinctSuppliersInWarehouse("W8");
        
        // Expected Output: ["S8"]
        List<String> expected = Arrays.asList("S8");
        assertEquals(expected, result);
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
        List<String> result = company.getDistinctSuppliersInWarehouse("W9");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // Company already initialized with no warehouses in setUp()
        
        // Action: Retrieve unique suppliers for "W10"
        List<String> result = company.getDistinctSuppliersInWarehouse("W10");
        
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
        product1.setSupplier(supplier);
        warehouse.addProduct(product1);
        
        // Add Product "A2" from Supplier "S9"
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplier); // Same supplier as product1
        warehouse.addProduct(product2);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<String> result = company.getDistinctSuppliersInWarehouse("W11");
        
        // Expected Output: ["S9"]
        List<String> expected = Arrays.asList("S9");
        assertEquals(expected, result);
    }
}