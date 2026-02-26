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
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m²
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W7");
        warehouse.setSurface(350.0);
        
        // Create suppliers and products
        Supplier supplierS6 = new Supplier();
        supplierS6.setName("S6");
        
        Supplier supplierS7 = new Supplier();
        supplierS7.setName("S7");
        
        Product item1 = new Product();
        item1.setName("Item1");
        item1.setSupplier(supplierS6);
        
        Product item2 = new Product();
        item2.setName("Item2");
        item2.setSupplier(supplierS7);
        
        Product item3 = new Product();
        item3.setName("Item3");
        item3.setSupplier(supplierS6); // duplicate supplier
        
        // Add products to warehouse
        warehouse.addProduct(item1);
        warehouse.addProduct(item2);
        warehouse.addProduct(item3);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<String> result = company.getDistinctSuppliersInWarehouse("W7");
        
        // Expected Output: ["S6", "S7"]
        List<String> expected = new ArrayList<>();
        expected.add("S6");
        expected.add("S7");
        
        assertEquals("Warehouse with products from multiple suppliers should return both unique suppliers", 
                     expected, result);
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m²
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W8");
        warehouse.setSurface(250.0);
        
        // Create supplier and product
        Supplier supplierS8 = new Supplier();
        supplierS8.setName("S8");
        
        Product item4 = new Product();
        item4.setName("Item4");
        item4.setSupplier(supplierS8);
        
        // Add product to warehouse
        warehouse.addProduct(item4);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<String> result = company.getDistinctSuppliersInWarehouse("W8");
        
        // Expected Output: ["S8"]
        List<String> expected = new ArrayList<>();
        expected.add("S8");
        
        assertEquals("Warehouse with single product should return its supplier", 
                     expected, result);
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products)
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W9");
        warehouse.setSurface(150.0);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<String> result = company.getDistinctSuppliersInWarehouse("W9");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Empty warehouse should return empty supplier list", 
                     expected, result);
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company with no warehouses
        // (company is already initialized with no warehouses in setUp())
        
        // Action: Retrieve unique suppliers for "W10"
        List<String> result = company.getDistinctSuppliersInWarehouse("W10");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Non-existent warehouse should return empty supplier list", 
                     expected, result);
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m²
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W11");
        warehouse.setSurface(300.0);
        
        // Create supplier
        Supplier supplierS9 = new Supplier();
        supplierS9.setName("S9");
        
        // Create products from same supplier
        Product a1 = new Product();
        a1.setName("A1");
        a1.setSupplier(supplierS9);
        
        Product a2 = new Product();
        a2.setName("A2");
        a2.setSupplier(supplierS9);
        
        // Add products to warehouse
        warehouse.addProduct(a1);
        warehouse.addProduct(a2);
        
        // Add warehouse to company
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<String> result = company.getDistinctSuppliersInWarehouse("W11");
        
        // Expected Output: ["S9"]
        List<String> expected = new ArrayList<>();
        expected.add("S9");
        
        assertEquals("Warehouse with products from same supplier should return unique supplier once", 
                     expected, result);
    }
}