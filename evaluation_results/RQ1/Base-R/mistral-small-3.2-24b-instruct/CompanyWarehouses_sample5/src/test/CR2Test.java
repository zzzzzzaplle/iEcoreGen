import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_WarehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouseW7 = new Warehouse("CityE", 350.0);
        
        // SetUp: Add Product "Item1" from Supplier "S6"
        Supplier supplierS6 = new Supplier("S6", "AddressS6");
        Product item1 = new Product("Item1", supplierS6, false, 10.0);
        warehouseW7.addProduct(item1);
        
        // SetUp: Add Product "Item2" from Supplier "S7"
        Supplier supplierS7 = new Supplier("S7", "AddressS7");
        Product item2 = new Product("Item2", supplierS7, true, 15.0);
        warehouseW7.addProduct(item2);
        
        // SetUp: Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product item3 = new Product("Item3", supplierS6, false, 20.0);
        warehouseW7.addProduct(item3);
        
        company.addWarehouse(warehouseW7);
        
        // Action: Retrieve unique suppliers for W7
        Set<String> result = company.getDistinctSuppliersInWarehouse("CityE");
        
        // Expected Output: ["S6", "S7"]
        Set<String> expected = new HashSet<>();
        expected.add("S6");
        expected.add("S7");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouseW8 = new Warehouse("CityF", 250.0);
        
        // SetUp: Add Product "Item4" from Supplier "S8"
        Supplier supplierS8 = new Supplier("S8", "AddressS8");
        Product item4 = new Product("Item4", supplierS8, false, 12.0);
        warehouseW8.addProduct(item4);
        
        company.addWarehouse(warehouseW8);
        
        // Action: Retrieve unique suppliers for W8
        Set<String> result = company.getDistinctSuppliersInWarehouse("CityF");
        
        // Expected Output: ["S8"]
        Set<String> expected = new HashSet<>();
        expected.add("S8");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouseW9 = new Warehouse("CityG", 150.0);
        company.addWarehouse(warehouseW9);
        
        // Action: Retrieve unique suppliers for W9
        Set<String> result = company.getDistinctSuppliersInWarehouse("CityG");
        
        // Expected Output: []
        Set<String> expected = new HashSet<>();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // (Already done in setUp method - company is empty)
        
        // Action: Retrieve unique suppliers for "W10"
        Set<String> result = company.getDistinctSuppliersInWarehouse("W10");
        
        // Expected Output: []
        Set<String> expected = new HashSet<>();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouseW11 = new Warehouse("CityH", 300.0);
        
        // SetUp: Add Product "A1" from Supplier "S9"
        Supplier supplierS9 = new Supplier("S9", "AddressS9");
        Product a1 = new Product("A1", supplierS9, true, 8.0);
        warehouseW11.addProduct(a1);
        
        // SetUp: Add Product "A2" from Supplier "S9"
        Product a2 = new Product("A2", supplierS9, false, 12.0);
        warehouseW11.addProduct(a2);
        
        company.addWarehouse(warehouseW11);
        
        // Action: Retrieve unique suppliers for W11
        Set<String> result = company.getDistinctSuppliersInWarehouse("CityH");
        
        // Expected Output: ["S9"]
        Set<String> expected = new HashSet<>();
        expected.add("S9");
        
        assertEquals(expected, result);
    }
}