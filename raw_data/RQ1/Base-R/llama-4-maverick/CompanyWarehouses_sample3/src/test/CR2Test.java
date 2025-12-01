import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        Warehouse warehouse = new Warehouse("CityE", "W7", 350.0);
        
        // Add products from multiple suppliers (including duplicate supplier)
        Supplier supplierS6 = new Supplier("S6", "Address S6");
        Supplier supplierS7 = new Supplier("S7", "Address S7");
        
        warehouse.addProduct(new Product("Item1", supplierS6, false, 10.0));
        warehouse.addProduct(new Product("Item2", supplierS7, false, 15.0));
        warehouse.addProduct(new Product("Item3", supplierS6, false, 20.0)); // Duplicate supplier
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W7");
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        
        // Check supplier names (order doesn't matter in this context)
        List<String> supplierNames = new ArrayList<>();
        for (Supplier supplier : result) {
            supplierNames.add(supplier.getName());
        }
        
        assertTrue(supplierNames.contains("S6"));
        assertTrue(supplierNames.contains("S7"));
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse("CityF", "W8", 250.0);
        
        // Add single product from supplier S8
        Supplier supplierS8 = new Supplier("S8", "Address S8");
        warehouse.addProduct(new Product("Item4", supplierS8, false, 25.0));
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W8");
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse("CityG", "W9", 150.0);
        // No products added to warehouse
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W9");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // (company is already empty from @Before setup)
        
        // Action: Retrieve unique suppliers for "W10"
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W10");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse("CityH", "W11", 300.0);
        
        // Add multiple products from same supplier S9
        Supplier supplierS9 = new Supplier("S9", "Address S9");
        
        warehouse.addProduct(new Product("A1", supplierS9, false, 30.0));
        warehouse.addProduct(new Product("A2", supplierS9, false, 35.0));
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W11");
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}