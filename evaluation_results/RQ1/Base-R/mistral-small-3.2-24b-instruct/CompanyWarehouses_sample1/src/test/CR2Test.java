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
        Warehouse warehouse = new Warehouse("CityE", 350.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "Item1" from Supplier "S6"
        Supplier supplier1 = new Supplier("S6", "Address1");
        Product product1 = new Product("Item1", supplier1, false, 10.0);
        warehouse.addProduct(product1);
        
        // SetUp: Add Product "Item2" from Supplier "S7"
        Supplier supplier2 = new Supplier("S7", "Address2");
        Product product2 = new Product("Item2", supplier2, false, 20.0);
        warehouse.addProduct(product2);
        
        // SetUp: Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product("Item3", supplier1, false, 15.0);
        warehouse.addProduct(product3);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityE");
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        assertEquals("S6", result.get(0).getName());
        assertEquals("S7", result.get(1).getName());
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse("CityF", 250.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "Item4" from Supplier "S8"
        Supplier supplier = new Supplier("S8", "Address3");
        Product product = new Product("Item4", supplier, false, 25.0);
        warehouse.addProduct(product);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityF");
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse("CityG", 150.0);
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityG");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // (Already handled by @Before setUp method)
        
        // Action: Retrieve unique suppliers for "W10"
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W10");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse("CityH", 300.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "A1" from Supplier "S9"
        Supplier supplier = new Supplier("S9", "Address4");
        Product product1 = new Product("A1", supplier, false, 30.0);
        warehouse.addProduct(product1);
        
        // SetUp: Add Product "A2" from Supplier "S9"
        Product product2 = new Product("A2", supplier, false, 35.0);
        warehouse.addProduct(product2);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityH");
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}