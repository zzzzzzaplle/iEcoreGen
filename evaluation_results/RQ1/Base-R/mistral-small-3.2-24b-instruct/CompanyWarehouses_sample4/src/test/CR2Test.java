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
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W7");
        warehouse.setCity("CityE");
        warehouse.setSurface(350.0);
        
        // Create products with different suppliers
        Product product1 = new Product();
        product1.setName("Item1");
        Supplier supplier1 = new Supplier();
        supplier1.setName("S6");
        product1.setSupplier(supplier1);
        
        Product product2 = new Product();
        product2.setName("Item2");
        Supplier supplier2 = new Supplier();
        supplier2.setName("S7");
        product2.setSupplier(supplier2);
        
        Product product3 = new Product();
        product3.setName("Item3");
        Supplier supplier3 = new Supplier();
        supplier3.setName("S6"); // Duplicate supplier
        product3.setSupplier(supplier3);
        
        // Add products to warehouse
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        warehouse.setProducts(products);
        
        // Add warehouse to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);
        company.setWarehouses(warehouses);
        
        // Action: Retrieve unique suppliers for W7
        List<String> result = company.getDistinctSuppliersInWarehouse("W7");
        
        // Expected Output: ["S6", "S7"]
        List<String> expected = new ArrayList<>();
        expected.add("S6");
        expected.add("S7");
        
        assertEquals("Should return unique suppliers S6 and S7", expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W8");
        warehouse.setCity("CityF");
        warehouse.setSurface(250.0);
        
        // Create single product
        Product product = new Product();
        product.setName("Item4");
        Supplier supplier = new Supplier();
        supplier.setName("S8");
        product.setSupplier(supplier);
        
        // Add product to warehouse
        List<Product> products = new ArrayList<>();
        products.add(product);
        warehouse.setProducts(products);
        
        // Add warehouse to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);
        company.setWarehouses(warehouses);
        
        // Action: Retrieve unique suppliers for W8
        List<String> result = company.getDistinctSuppliersInWarehouse("W8");
        
        // Expected Output: ["S8"]
        List<String> expected = new ArrayList<>();
        expected.add("S8");
        
        assertEquals("Should return single supplier S8", expected, result);
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W9");
        warehouse.setCity("CityG");
        warehouse.setSurface(150.0);
        // No products added (empty products list)
        
        // Add warehouse to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);
        company.setWarehouses(warehouses);
        
        // Action: Retrieve unique suppliers for W9
        List<String> result = company.getDistinctSuppliersInWarehouse("W9");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list for warehouse with no products", expected, result);
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // company already initialized with empty warehouses list in setUp()
        
        // Action: Retrieve unique suppliers for "W10"
        List<String> result = company.getDistinctSuppliersInWarehouse("W10");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list for non-existent warehouse", expected, result);
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("W11");
        warehouse.setCity("CityH");
        warehouse.setSurface(300.0);
        
        // Create products with same supplier
        Product product1 = new Product();
        product1.setName("A1");
        Supplier supplier1 = new Supplier();
        supplier1.setName("S9");
        product1.setSupplier(supplier1);
        
        Product product2 = new Product();
        product2.setName("A2");
        Supplier supplier2 = new Supplier();
        supplier2.setName("S9"); // Same supplier
        product2.setSupplier(supplier2);
        
        // Add products to warehouse
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        warehouse.setProducts(products);
        
        // Add warehouse to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);
        company.setWarehouses(warehouses);
        
        // Action: Retrieve unique suppliers for W11
        List<String> result = company.getDistinctSuppliersInWarehouse("W11");
        
        // Expected Output: ["S9"]
        List<String> expected = new ArrayList<>();
        expected.add("S9");
        
        assertEquals("Should return single supplier S9 even with multiple products", expected, result);
    }
}