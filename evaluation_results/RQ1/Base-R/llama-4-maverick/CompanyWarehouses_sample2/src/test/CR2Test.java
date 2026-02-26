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
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouseW7 = new Warehouse("W7", "CityE", 350.0);
        
        // Add Product "Item1" from Supplier "S6"
        Supplier supplierS6 = new Supplier("S6", "AddressS6");
        Product item1 = new Product("Item1", supplierS6, false, 10.0);
        warehouseW7.getProducts().add(item1);
        
        // Add Product "Item2" from Supplier "S7"
        Supplier supplierS7 = new Supplier("S7", "AddressS7");
        Product item2 = new Product("Item2", supplierS7, true, 15.0);
        warehouseW7.getProducts().add(item2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product item3 = new Product("Item3", supplierS6, false, 20.0);
        warehouseW7.getProducts().add(item3);
        
        company.getWarehouses().add(warehouseW7);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = company.getSuppliersInWarehouse("W7");
        
        // Expected Output: ["S6", "S7"]
        assertEquals("Should return 2 unique suppliers", 2, result.size());
        
        // Verify both suppliers are present
        boolean hasS6 = false;
        boolean hasS7 = false;
        for (Supplier supplier : result) {
            if (supplier.getName().equals("S6")) {
                hasS6 = true;
            } else if (supplier.getName().equals("S7")) {
                hasS7 = true;
            }
        }
        assertTrue("Should contain supplier S6", hasS6);
        assertTrue("Should contain supplier S7", hasS7);
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouseW8 = new Warehouse("W8", "CityF", 250.0);
        
        // Add Product "Item4" from Supplier "S8"
        Supplier supplierS8 = new Supplier("S8", "AddressS8");
        Product item4 = new Product("Item4", supplierS8, false, 25.0);
        warehouseW8.getProducts().add(item4);
        
        company.getWarehouses().add(warehouseW8);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = company.getSuppliersInWarehouse("W8");
        
        // Expected Output: ["S8"]
        assertEquals("Should return 1 supplier", 1, result.size());
        assertEquals("Should be supplier S8", "S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouseW9 = new Warehouse("W9", "CityG", 150.0);
        // No products added
        
        company.getWarehouses().add(warehouseW9);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = company.getSuppliersInWarehouse("W9");
        
        // Expected Output: []
        assertTrue("Should return empty list", result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // No warehouses added to company
        
        // Action: Retrieve unique suppliers for "W10"
        List<Supplier> result = company.getSuppliersInWarehouse("W10");
        
        // Expected Output: []
        assertTrue("Should return empty list for non-existent warehouse", result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouseW11 = new Warehouse("W11", "CityH", 300.0);
        
        // Add Product "A1" from Supplier "S9"
        Supplier supplierS9 = new Supplier("S9", "AddressS9");
        Product a1 = new Product("A1", supplierS9, false, 30.0);
        warehouseW11.getProducts().add(a1);
        
        // Add Product "A2" from Supplier "S9"
        Product a2 = new Product("A2", supplierS9, true, 35.0);
        warehouseW11.getProducts().add(a2);
        
        company.getWarehouses().add(warehouseW11);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = company.getSuppliersInWarehouse("W11");
        
        // Expected Output: ["S9"]
        assertEquals("Should return 1 supplier", 1, result.size());
        assertEquals("Should be supplier S9", "S9", result.get(0).getName());
    }
}