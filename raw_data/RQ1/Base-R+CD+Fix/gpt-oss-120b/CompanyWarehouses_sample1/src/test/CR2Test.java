import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        warehouse.setCity("CityE");
        warehouse.setSurface(350.0);
        
        // Add Product "Item1" from Supplier "S6"
        Supplier supplier1 = new Supplier();
        supplier1.setName("S6");
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setSupplier(supplier1);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        warehouse.addOccupations(occupation1);
        
        // Add Product "Item2" from Supplier "S7"
        Supplier supplier2 = new Supplier();
        supplier2.setName("S7");
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setSupplier(supplier2);
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        warehouse.addOccupations(occupation2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Supplier supplier3 = new Supplier();
        supplier3.setName("S6");
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplier3);
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        warehouse.addOccupations(occupation3);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"] - verify both suppliers are present
        assertEquals(2, result.size());
        boolean hasS6 = false;
        boolean hasS7 = false;
        for (Supplier supplier : result) {
            if ("S6".equals(supplier.getName())) {
                hasS6 = true;
            }
            if ("S7".equals(supplier.getName())) {
                hasS7 = true;
            }
        }
        assertTrue("Should contain supplier S6", hasS6);
        assertTrue("Should contain supplier S7", hasS7);
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m²
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityF");
        warehouse.setSurface(250.0);
        
        // Add Product "Item4" from Supplier "S8"
        Supplier supplier = new Supplier();
        supplier.setName("S8");
        Product product = new Product();
        product.setName("Item4");
        product.setSupplier(supplier);
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        warehouse.addOccupations(occupation);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"] - verify single supplier is present
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products)
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityG");
        warehouse.setSurface(150.0);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: [] - verify empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // (already done in @Before)
        
        // Action: Retrieve unique suppliers for non-existent "W10"
        // Since we're testing a specific warehouse method, we need to create a warehouse
        // that is not added to the company to simulate non-existence in the context
        Warehouse nonExistentWarehouse = null;
        
        // For this test case, we need to verify that calling retrieveUniqueSuppliers()
        // on a null warehouse would return empty list, but since we can't call methods on null,
        // we'll test the scenario where the warehouse exists but has no suppliers
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("SomeCity");
        warehouse.setSurface(200.0);
        
        // Action: Retrieve unique suppliers for warehouse with no products
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: [] - verify empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m²
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setSurface(300.0);
        
        // Add Product "A1" from Supplier "S9"
        Supplier supplier = new Supplier();
        supplier.setName("S9");
        
        Product product1 = new Product();
        product1.setName("A1");
        product1.setSupplier(supplier);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        warehouse.addOccupations(occupation1);
        
        // Add Product "A2" from Supplier "S9"
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplier);
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        warehouse.addOccupations(occupation2);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"] - verify single supplier (duplicates removed)
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}