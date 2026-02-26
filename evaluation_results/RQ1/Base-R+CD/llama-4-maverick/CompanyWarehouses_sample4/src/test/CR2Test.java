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
        // Create Warehouse "W7" in "CityE" with surface 350m²
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityE");
        warehouse.setSurface(350.0);
        
        // Add Product "Item1" from Supplier "S6"
        Product product1 = new Product();
        product1.setName("Item1");
        Supplier supplier1 = new Supplier();
        supplier1.setName("S6");
        product1.setSupplier(supplier1);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(50.0);
        
        // Add Product "Item2" from Supplier "S7"
        Product product2 = new Product();
        product2.setName("Item2");
        Supplier supplier2 = new Supplier();
        supplier2.setName("S7");
        product2.setSupplier(supplier2);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(75.0);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplier1); // Same supplier as product1
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(100.0);
        
        // Add occupations to warehouse
        warehouse.addOccupation(occupation1);
        warehouse.addOccupation(occupation2);
        warehouse.addOccupation(occupation3);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, uniqueSuppliers.size());
        List<String> supplierNames = new ArrayList<>();
        for (Supplier supplier : uniqueSuppliers) {
            supplierNames.add(supplier.getName());
        }
        assertTrue(supplierNames.contains("S6"));
        assertTrue(supplierNames.contains("S7"));
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // Create Warehouse "W8" in "CityF" with surface 250m²
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityF");
        warehouse.setSurface(250.0);
        
        // Add Product "Item4" from Supplier "S8"
        Product product = new Product();
        product.setName("Item4");
        Supplier supplier = new Supplier();
        supplier.setName("S8");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(60.0);
        
        warehouse.addOccupation(occupation);
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, uniqueSuppliers.size());
        assertEquals("S8", uniqueSuppliers.get(0).getName());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // Create Warehouse "W9" in "CityG" with surface 150m² (no products)
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityG");
        warehouse.setSurface(150.0);
        // No products added
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, uniqueSuppliers.size());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // Initialize company C1 with no warehouses
        // Action: Retrieve unique suppliers for "W10" (non-existent warehouse)
        // Since we can't retrieve a non-existent warehouse from the company,
        // we test by creating a warehouse but not adding any products to it
        Warehouse warehouse = new Warehouse();
        
        // Action: Retrieve unique suppliers for W10
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, uniqueSuppliers.size());
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // Create Warehouse "W11" in "CityH" with surface 300m²
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setSurface(300.0);
        
        // Add Product "A1" from Supplier "S9"
        Product product1 = new Product();
        product1.setName("A1");
        Supplier supplier = new Supplier();
        supplier.setName("S9");
        product1.setSupplier(supplier);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(40.0);
        
        // Add Product "A2" from Supplier "S9"
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplier); // Same supplier
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(80.0);
        
        // Add occupations to warehouse
        warehouse.addOccupation(occupation1);
        warehouse.addOccupation(occupation2);
        
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, uniqueSuppliers.size());
        assertEquals("S9", uniqueSuppliers.get(0).getName());
    }
}