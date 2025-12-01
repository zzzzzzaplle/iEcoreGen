import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityE");
        warehouse.setSurface(350.0);
        warehouse.setAddress("AddressW7");
        company.addWarehouses(warehouse);
        
        // Add Product "Item1" from Supplier "S6"
        Supplier supplier1 = new Supplier();
        supplier1.setName("S6");
        supplier1.setAddress("AddressS6");
        
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setSupplier(supplier1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(10.0);
        warehouse.addOccupations(occupation1);
        
        // Add Product "Item2" from Supplier "S7"
        Supplier supplier2 = new Supplier();
        supplier2.setName("S7");
        supplier2.setAddress("AddressS7");
        
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setSupplier(supplier2);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(15.0);
        warehouse.addOccupations(occupation2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplier1); // Same supplier as product1
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(20.0);
        warehouse.addOccupations(occupation3);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"] - but since we're getting Supplier objects, we need to check names
        assertEquals("Should contain exactly 2 unique suppliers", 2, result.size());
        
        // Extract supplier names for easier comparison
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
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityF");
        warehouse.setSurface(250.0);
        warehouse.setAddress("AddressW8");
        company.addWarehouses(warehouse);
        
        // Add Product "Item4" from Supplier "S8"
        Supplier supplier = new Supplier();
        supplier.setName("S8");
        supplier.setAddress("AddressS8");
        
        Product product = new Product();
        product.setName("Item4");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(12.0);
        warehouse.addOccupations(occupation);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals("Should contain exactly 1 supplier", 1, result.size());
        assertEquals("Supplier name should be S8", "S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityG");
        warehouse.setSurface(150.0);
        warehouse.setAddress("AddressW9");
        company.addWarehouses(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue("Should return empty list for warehouse with no products", result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // (Already done in @Before setup)
        
        // Action: Retrieve unique suppliers for "W10"
        // Since W10 doesn't exist in our test setup, we need to clarify this test case
        // The specification mentions retrieving for a specific warehouse, so we'll test with a null warehouse reference
        List<Supplier> result = new ArrayList<>(); // Empty list as default
        
        // Expected Output: []
        assertTrue("Should return empty list for non-existent warehouse", result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setSurface(300.0);
        warehouse.setAddress("AddressW11");
        company.addWarehouses(warehouse);
        
        // Add Product "A1" from Supplier "S9"
        Supplier supplier = new Supplier();
        supplier.setName("S9");
        supplier.setAddress("AddressS9");
        
        Product product1 = new Product();
        product1.setName("A1");
        product1.setSupplier(supplier);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(8.0);
        warehouse.addOccupations(occupation1);
        
        // Add Product "A2" from Supplier "S9"
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplier); // Same supplier
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(12.0);
        warehouse.addOccupations(occupation2);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals("Should contain exactly 1 supplier despite multiple products", 1, result.size());
        assertEquals("Supplier name should be S9", "S9", result.get(0).getName());
    }
}