import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize company C1 for all tests
        company = new Company("C1");
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // SetUp:
        // 1. Create Warehouse "W7" in "CityE" with surface 350m², in company C1.
        Warehouse w7 = new Warehouse("W7", "CityE", 350);
        company.addWarehouse(w7);
        
        // 2. Add Product "Item1" from Supplier "S6".
        Product item1 = new Product("Item1", "S6");
        w7.addProduct(item1);
        
        // 3. Add Product "Item2" from Supplier "S7".
        Product item2 = new Product("Item2", "S7");
        w7.addProduct(item2);
        
        // 4. Add Product "Item3" from Supplier "S6" (duplicate supplier).
        Product item3 = new Product("Item3", "S6");
        w7.addProduct(item3);
        
        // Action: Retrieve unique suppliers for W7.
        List<String> result = company.getUniqueSuppliersForWarehouse("W7");
        
        // Expected Output: ["S6", "S7"]
        List<String> expected = Arrays.asList("S6", "S7");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
        assertTrue(expected.containsAll(result));
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp:
        // 1. Create Warehouse "W8" in "CityF" with surface 250m², in company C1.
        Warehouse w8 = new Warehouse("W8", "CityF", 250);
        company.addWarehouse(w8);
        
        // 2. Add Product "Item4" from Supplier "S8".
        Product item4 = new Product("Item4", "S8");
        w8.addProduct(item4);
        
        // Action: Retrieve unique suppliers for W8.
        List<String> result = company.getUniqueSuppliersForWarehouse("W8");
        
        // Expected Output: ["S8"]
        List<String> expected = Arrays.asList("S8");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
        assertTrue(expected.containsAll(result));
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1.
        Warehouse w9 = new Warehouse("W9", "CityG", 150);
        company.addWarehouse(w9);
        
        // Action: Retrieve unique suppliers for W9.
        List<String> result = company.getUniqueSuppliersForWarehouse("W9");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp:
        // 1. Initialize company C1 with no warehouses.
        // (Already done in setUp)
        
        // Action: Retrieve unique suppliers for "W10".
        List<String> result = company.getUniqueSuppliersForWarehouse("W10");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp:
        // 1. Create Warehouse "W11" in "CityH" with surface 300m², in company C1.
        Warehouse w11 = new Warehouse("W11", "CityH", 300);
        company.addWarehouse(w11);
        
        // 2. Add Product "A1" from Supplier "S9".
        Product a1 = new Product("A1", "S9");
        w11.addProduct(a1);
        
        // 3. Add Product "A2" from Supplier "S9".
        Product a2 = new Product("A2", "S9");
        w11.addProduct(a2);
        
        // Action: Retrieve unique suppliers for W11.
        List<String> result = company.getUniqueSuppliersForWarehouse("W11");
        
        // Expected Output: ["S9"]
        List<String> expected = Arrays.asList("S9");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
        assertTrue(expected.containsAll(result));
    }
}