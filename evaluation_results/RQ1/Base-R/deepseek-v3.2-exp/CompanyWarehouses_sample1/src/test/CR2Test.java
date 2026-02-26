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
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W7");
        warehouse.setCity("CityE");
        warehouse.setSurface(350.0);
        
        // Add Product "Item1" from Supplier "S6"
        Product product1 = new Product();
        product1.setName("Item1");
        Supplier supplier1 = new Supplier();
        supplier1.setName("S6");
        product1.setSupplier(supplier1);
        
        // Add Product "Item2" from Supplier "S7"
        Product product2 = new Product();
        product2.setName("Item2");
        Supplier supplier2 = new Supplier();
        supplier2.setName("S7");
        product2.setSupplier(supplier2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplier1); // Same supplier as product1
        
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        warehouse.setProducts(products);
        
        company.getWarehouses().add(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W7");
        
        // Expected Output: ["S6", "S7"]
        assertEquals("Should return 2 unique suppliers", 2, result.size());
        
        // Check that both suppliers are present (order doesn't matter)
        boolean foundS6 = false;
        boolean foundS7 = false;
        for (Supplier supplier : result) {
            if ("S6".equals(supplier.getName())) {
                foundS6 = true;
            } else if ("S7".equals(supplier.getName())) {
                foundS7 = true;
            }
        }
        assertTrue("Should contain supplier S6", foundS6);
        assertTrue("Should contain supplier S7", foundS7);
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W8");
        warehouse.setCity("CityF");
        warehouse.setSurface(250.0);
        
        // Add Product "Item4" from Supplier "S8"
        Product product = new Product();
        product.setName("Item4");
        Supplier supplier = new Supplier();
        supplier.setName("S8");
        product.setSupplier(supplier);
        
        List<Product> products = new ArrayList<>();
        products.add(product);
        warehouse.setProducts(products);
        
        company.getWarehouses().add(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W8");
        
        // Expected Output: ["S8"]
        assertEquals("Should return 1 supplier", 1, result.size());
        assertEquals("Should be supplier S8", "S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W9");
        warehouse.setCity("CityG");
        warehouse.setSurface(150.0);
        // No products added (empty warehouse)
        
        company.getWarehouses().add(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W9");
        
        // Expected Output: []
        assertTrue("Should return empty list", result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // (company is already initialized with no warehouses in setUp method)
        
        // Action: Retrieve unique suppliers for "W10"
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W10");
        
        // Expected Output: []
        assertTrue("Should return empty list for non-existent warehouse", result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W11");
        warehouse.setCity("CityH");
        warehouse.setSurface(300.0);
        
        // Add Product "A1" from Supplier "S9"
        Product product1 = new Product();
        product1.setName("A1");
        Supplier supplier = new Supplier();
        supplier.setName("S9");
        product1.setSupplier(supplier);
        
        // Add Product "A2" from Supplier "S9"
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplier); // Same supplier as product1
        
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        warehouse.setProducts(products);
        
        company.getWarehouses().add(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = company.getDistinctSuppliersInWarehouse("W11");
        
        // Expected Output: ["S9"]
        assertEquals("Should return 1 supplier", 1, result.size());
        assertEquals("Should be supplier S9", "S9", result.get(0).getName());
    }
}