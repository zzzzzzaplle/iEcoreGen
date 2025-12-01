import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a fresh company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // Test Case 1: "Warehouse with products from multiple suppliers"
        
        // SetUp:
        // 1. Create Warehouse "W7" in "CityE" with surface 350m², in company C1.
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityE");
        warehouse.setAddress("W7");
        warehouse.setSurface(350.0);
        
        // Create suppliers
        Supplier supplierS6 = new Supplier();
        supplierS6.setName("S6");
        supplierS6.setAddress("AddressS6");
        
        Supplier supplierS7 = new Supplier();
        supplierS7.setName("S7");
        supplierS7.setAddress("AddressS7");
        
        // 2. Add Product "Item1" from Supplier "S6".
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setSupplier(supplierS6);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(100.0);
        warehouse.addOccupation(occupation1);
        
        // 3. Add Product "Item2" from Supplier "S7".
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setSupplier(supplierS7);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(150.0);
        warehouse.addOccupation(occupation2);
        
        // 4. Add Product "Item3" from Supplier "S6" (duplicate supplier).
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplierS6);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(200.0);
        warehouse.addOccupation(occupation3);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7.
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals("Should return 2 unique suppliers", 2, result.size());
        
        // Verify supplier names
        List<String> supplierNames = new ArrayList<>();
        for (Supplier supplier : result) {
            supplierNames.add(supplier.getName());
        }
        
        assertTrue("Should contain supplier S6", supplierNames.contains("S6"));
        assertTrue("Should contain supplier S7", supplierNames.contains("S7"));
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // Test Case 2: "Warehouse with single product"
        
        // SetUp:
        // 1. Create Warehouse "W8" in "CityF" with surface 250m², in company C1.
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityF");
        warehouse.setAddress("W8");
        warehouse.setSurface(250.0);
        
        // 2. Add Product "Item4" from Supplier "S8".
        Supplier supplierS8 = new Supplier();
        supplierS8.setName("S8");
        supplierS8.setAddress("AddressS8");
        
        Product product = new Product();
        product.setName("Item4");
        product.setSupplier(supplierS8);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(100.0);
        warehouse.addOccupation(occupation);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W8.
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals("Should return 1 supplier", 1, result.size());
        assertEquals("Supplier name should be S8", "S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // Test Case 3: "Empty warehouse"
        
        // SetUp:
        // 1. Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1.
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityG");
        warehouse.setAddress("W9");
        warehouse.setSurface(150.0);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9.
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue("Should return empty list for warehouse with no products", result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // Test Case 4: "Non-existent warehouse"
        
        // SetUp:
        // 1. Initialize company C1 with no warehouses.
        // (Already done in @Before method)
        
        // Action: Retrieve unique suppliers for "W10".
        // Since the warehouse doesn't exist in the company, we need to handle this case
        // by creating a dummy warehouse and testing its behavior
        
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("Unknown");
        nonExistentWarehouse.setAddress("W10");
        nonExistentWarehouse.setSurface(0.0);
        
        List<Supplier> result = nonExistentWarehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue("Should return empty list for non-existent warehouse", result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // Test Case 5: "Warehouse with products from same supplier"
        
        // SetUp:
        // 1. Create Warehouse "W11" in "CityH" with surface 300m², in company C1.
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setAddress("W11");
        warehouse.setSurface(300.0);
        
        // Create supplier
        Supplier supplierS9 = new Supplier();
        supplierS9.setName("S9");
        supplierS9.setAddress("AddressS9");
        
        // 2. Add Product "A1" from Supplier "S9".
        Product product1 = new Product();
        product1.setName("A1");
        product1.setSupplier(supplierS9);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(50.0);
        warehouse.addOccupation(occupation1);
        
        // 3. Add Product "A2" from Supplier "S9".
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplierS9);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(75.0);
        warehouse.addOccupation(occupation2);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11.
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals("Should return 1 supplier even with multiple products", 1, result.size());
        assertEquals("Supplier name should be S9", "S9", result.get(0).getName());
    }
}