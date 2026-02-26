import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
        warehouse.setAddress("W7");
        warehouse.setSurface(350.0);
        company.addWarehouse(warehouse);
        
        // Add Product "Item1" from Supplier "S6"
        Supplier supplierS6 = new Supplier();
        supplierS6.setName("S6");
        
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setSupplier(supplierS6);
        
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(product1);
        po1.setVolume(100.0);
        warehouse.addOccupation(po1);
        
        // Add Product "Item2" from Supplier "S7"
        Supplier supplierS7 = new Supplier();
        supplierS7.setName("S7");
        
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setSupplier(supplierS7);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(product2);
        po2.setVolume(150.0);
        warehouse.addOccupation(po2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplierS6);
        
        ProductOccupation po3 = new ProductOccupation();
        po3.setProduct(product3);
        po3.setVolume(200.0);
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
        warehouse.setAddress("W8");
        warehouse.setSurface(250.0);
        company.addWarehouse(warehouse);
        
        // Add Product "Item4" from Supplier "S8"
        Supplier supplierS8 = new Supplier();
        supplierS8.setName("S8");
        
        Product product = new Product();
        product.setName("Item4");
        product.setSupplier(supplierS8);
        
        ProductOccupation po = new ProductOccupation();
        po.setProduct(product);
        po.setVolume(300.0);
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
        warehouse.setAddress("W9");
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
        
        // Action: Retrieve unique suppliers for "W10" - since W10 doesn't exist in company,
        // we need to test a warehouse that is not in the company
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("Unknown");
        nonExistentWarehouse.setAddress("W10");
        nonExistentWarehouse.setSurface(200.0);
        
        List<Supplier> result = nonExistentWarehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setAddress("W11");
        warehouse.setSurface(300.0);
        company.addWarehouse(warehouse);
        
        // Add Product "A1" from Supplier "S9"
        Supplier supplierS9 = new Supplier();
        supplierS9.setName("S9");
        
        Product product1 = new Product();
        product1.setName("A1");
        product1.setSupplier(supplierS9);
        
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(product1);
        po1.setVolume(120.0);
        warehouse.addOccupation(po1);
        
        // Add Product "A2" from Supplier "S9"
        Product product2 = new Product();
        product2.setName("A2");
        product2.setSupplier(supplierS9);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(product2);
        po2.setVolume(180.0);
        warehouse.addOccupation(po2);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}