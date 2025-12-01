import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a fresh company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouseW7 = new Warehouse();
        warehouseW7.setCity("CityE");
        warehouseW7.setAddress("W7");
        warehouseW7.setSurface(350.0);
        
        // Add Product "Item1" from Supplier "S6"
        Supplier supplierS6 = new Supplier();
        supplierS6.setName("S6");
        Product item1 = new Product();
        item1.setName("Item1");
        item1.setSupplier(supplierS6);
        warehouseW7.addProduct(item1);
        
        // Add Product "Item2" from Supplier "S7"
        Supplier supplierS7 = new Supplier();
        supplierS7.setName("S7");
        Product item2 = new Product();
        item2.setName("Item2");
        item2.setSupplier(supplierS7);
        warehouseW7.addProduct(item2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product item3 = new Product();
        item3.setName("Item3");
        item3.setSupplier(supplierS6);
        warehouseW7.addProduct(item3);
        
        company.addWarehouse(warehouseW7);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W7");
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        assertTrue(containsSupplierWithName(result, "S6"));
        assertTrue(containsSupplierWithName(result, "S7"));
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouseW8 = new Warehouse();
        warehouseW8.setCity("CityF");
        warehouseW8.setAddress("W8");
        warehouseW8.setSurface(250.0);
        
        // Add Product "Item4" from Supplier "S8"
        Supplier supplierS8 = new Supplier();
        supplierS8.setName("S8");
        Product item4 = new Product();
        item4.setName("Item4");
        item4.setSupplier(supplierS8);
        warehouseW8.addProduct(item4);
        
        company.addWarehouse(warehouseW8);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W8");
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertTrue(containsSupplierWithName(result, "S8"));
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouseW9 = new Warehouse();
        warehouseW9.setCity("CityG");
        warehouseW9.setAddress("W9");
        warehouseW9.setSurface(150.0);
        // No products added
        
        company.addWarehouse(warehouseW9);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W9");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // Company is already empty from @Before setup
        
        // Action: Retrieve unique suppliers for "W10"
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W10");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouseW11 = new Warehouse();
        warehouseW11.setCity("CityH");
        warehouseW11.setAddress("W11");
        warehouseW11.setSurface(300.0);
        
        // Add Product "A1" from Supplier "S9"
        Supplier supplierS9 = new Supplier();
        supplierS9.setName("S9");
        Product a1 = new Product();
        a1.setName("A1");
        a1.setSupplier(supplierS9);
        warehouseW11.addProduct(a1);
        
        // Add Product "A2" from Supplier "S9"
        Product a2 = new Product();
        a2.setName("A2");
        a2.setSupplier(supplierS9);
        warehouseW11.addProduct(a2);
        
        company.addWarehouse(warehouseW11);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W11");
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertTrue(containsSupplierWithName(result, "S9"));
    }
    
    // Helper method to check if a list of suppliers contains a supplier with the given name
    private boolean containsSupplierWithName(List<Supplier> suppliers, String name) {
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}