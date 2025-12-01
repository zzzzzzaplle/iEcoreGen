import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company("C1");
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouse = new Warehouse("CityE", "AddressE", 350.0);
        
        // Add Product "Item1" from Supplier "S6"
        Supplier supplier1 = new Supplier("S6", "AddressS6");
        Product product1 = new Product("Item1", supplier1, false, 10.0);
        warehouse.addProduct(product1);
        
        // Add Product "Item2" from Supplier "S7"
        Supplier supplier2 = new Supplier("S7", "AddressS7");
        Product product2 = new Product("Item2", supplier2, false, 15.0);
        warehouse.addProduct(product2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product("Item3", supplier1, false, 20.0);
        warehouse.addProduct(product3);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityE");
        
        // Expected Output: ["S6", "S7"]
        assertEquals("Should return 2 unique suppliers", 2, result.size());
        
        // Check that both suppliers are present (order doesn't matter)
        List<String> supplierNames = new ArrayList<>();
        for (Supplier s : result) {
            supplierNames.add(s.getName());
        }
        assertTrue("Should contain supplier S6", supplierNames.contains("S6"));
        assertTrue("Should contain supplier S7", supplierNames.contains("S7"));
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse("CityF", "AddressF", 250.0);
        
        // Add Product "Item4" from Supplier "S8"
        Supplier supplier = new Supplier("S8", "AddressS8");
        Product product = new Product("Item4", supplier, false, 12.0);
        warehouse.addProduct(product);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityF");
        
        // Expected Output: ["S8"]
        assertEquals("Should return exactly 1 supplier", 1, result.size());
        assertEquals("Should be supplier S8", "S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse("CityG", "AddressG", 150.0);
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityG");
        
        // Expected Output: []
        assertTrue("Should return empty list", result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // Action: Retrieve unique suppliers for "W10"
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W10");
        
        // Expected Output: []
        assertTrue("Should return empty list for non-existent warehouse", result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse("CityH", "AddressH", 300.0);
        
        // Add Product "A1" from Supplier "S9"
        Supplier supplier = new Supplier("S9", "AddressS9");
        Product product1 = new Product("A1", supplier, false, 8.0);
        warehouse.addProduct(product1);
        
        // Add Product "A2" from Supplier "S9"
        Product product2 = new Product("A2", supplier, false, 12.0);
        warehouse.addProduct(product2);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("CityH");
        
        // Expected Output: ["S9"]
        assertEquals("Should return exactly 1 supplier", 1, result.size());
        assertEquals("Should be supplier S9", "S9", result.get(0).getName());
    }
}