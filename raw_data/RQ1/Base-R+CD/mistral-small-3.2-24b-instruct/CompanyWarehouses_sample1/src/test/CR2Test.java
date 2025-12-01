import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityE");
        warehouse.setSurface(350.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "Item1" from Supplier "S6"
        Supplier supplier1 = new Supplier();
        supplier1.setName("S6");
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setSupplier(supplier1);
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(product1);
        warehouse.addOccupation(po1);
        
        // SetUp: Add Product "Item2" from Supplier "S7"
        Supplier supplier2 = new Supplier();
        supplier2.setName("S7");
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setSupplier(supplier2);
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(product2);
        warehouse.addOccupation(po2);
        
        // SetUp: Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplier1);
        ProductOccupation po3 = new ProductOccupation();
        po3.setProduct(product3);
        warehouse.addOccupation(po3);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        assertEquals("S6", result.get(0).getName());
        assertEquals("S7", result.get(1).getName());
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityF");
        warehouse.setSurface(250.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "Item4" from Supplier "S8"
        Supplier supplier = new Supplier();
        supplier.setName("S8");
        Product product = new Product();
        product.setName("Item4");
        product.setSupplier(supplier);
        ProductOccupation po = new ProductOccupation();
        po.setProduct(product);
        warehouse.addOccupation(po);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityG");
        warehouse.setSurface(150.0);
        company.addWarehouse(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // No warehouses added to company
        
        // Action: Retrieve unique suppliers for "W10"
        // Since we don't have access to a warehouse by ID, we'll test with a warehouse that exists but has no products
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityX");
        warehouse.setSurface(200.0);
        // Note: This warehouse is not added to company, but we're testing retrieveUniqueSuppliers method directly
        
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setSurface(300.0);
        company.addWarehouse(warehouse);
        
        // SetUp: Add Product "A1" from Supplier "S9"
        Supplier supplier = new Supplier();
        supplier.setName("S9");
        Product product1 = new Product();
        product1.setName("A1");
        product1.setSupplier(supplier);
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(product1);
        warehouse.addOccupation(po1);
        
        // SetUp: Add Product "A2" from Supplier "S9"
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplier);
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(product2);
        warehouse.addOccupation(po2);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}