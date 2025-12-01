import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
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
        Warehouse warehouse = new Warehouse("W7", 350.0);
        
        // SetUp: Add Product "Item1" from Supplier "S6"
        Supplier supplier1 = new Supplier("S6", "Address1");
        Product product1 = new Product("Item1", supplier1, false, 10.0);
        warehouse.addProduct(product1);
        
        // SetUp: Add Product "Item2" from Supplier "S7"
        Supplier supplier2 = new Supplier("S7", "Address2");
        Product product2 = new Product("Item2", supplier2, false, 15.0);
        warehouse.addProduct(product2);
        
        // SetUp: Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Supplier supplier3 = new Supplier("S6", "Address3");
        Product product3 = new Product("Item3", supplier3, true, 20.0);
        warehouse.addProduct(product3);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<String> result = company.getSuppliers("W7");
        
        // Expected Output: ["S6", "S7"]
        List<String> expected = Arrays.asList("S6", "S7");
        assertEquals("Should return both unique suppliers S6 and S7", expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse("W8", 250.0);
        
        // SetUp: Add Product "Item4" from Supplier "S8"
        Supplier supplier = new Supplier("S8", "Address4");
        Product product = new Product("Item4", supplier, false, 25.0);
        warehouse.addProduct(product);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<String> result = company.getSuppliers("W8");
        
        // Expected Output: ["S8"]
        List<String> expected = Arrays.asList("S8");
        assertEquals("Should return single supplier S8", expected, result);
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse("W9", 150.0);
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<String> result = company.getSuppliers("W9");
        
        // Expected Output: []
        assertTrue("Should return empty list for warehouse with no products", result.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // (company is already initialized with no warehouses in setUp)
        
        // Action: Retrieve unique suppliers for "W10"
        List<String> result = company.getSuppliers("W10");
        
        // Expected Output: []
        assertTrue("Should return empty list for non-existent warehouse", result.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse("W11", 300.0);
        
        // SetUp: Add Product "A1" from Supplier "S9"
        Supplier supplier = new Supplier("S9", "Address5");
        Product product1 = new Product("A1", supplier, false, 30.0);
        warehouse.addProduct(product1);
        
        // SetUp: Add Product "A2" from Supplier "S9"
        Product product2 = new Product("A2", supplier, true, 35.0);
        warehouse.addProduct(product2);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<String> result = company.getSuppliers("W11");
        
        // Expected Output: ["S9"]
        List<String> expected = Arrays.asList("S9");
        assertEquals("Should return single supplier S9 even with multiple products", expected, result);
    }
}