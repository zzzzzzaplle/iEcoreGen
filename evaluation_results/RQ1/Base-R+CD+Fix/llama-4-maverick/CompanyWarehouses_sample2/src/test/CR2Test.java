import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class CR2Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_WarehouseWithProductsFromMultipleSuppliers() {
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouseW7 = new Warehouse();
        warehouseW7.setCity("CityE");
        warehouseW7.setSurface(350.0);
        company.addWarehouses(warehouseW7);
        
        // SetUp: Add Product "Item1" from Supplier "S6"
        Supplier supplierS6 = new Supplier();
        supplierS6.setName("S6");
        
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setSupplier(supplierS6);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(100.0);
        warehouseW7.addOccupations(occupation1);
        
        // SetUp: Add Product "Item2" from Supplier "S7"
        Supplier supplierS7 = new Supplier();
        supplierS7.setName("S7");
        
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setSupplier(supplierS7);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(150.0);
        warehouseW7.addOccupations(occupation2);
        
        // SetUp: Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplierS6);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(200.0);
        warehouseW7.addOccupations(occupation3);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = warehouseW7.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        assertEquals("S6", result.get(0).getName());
        assertEquals("S7", result.get(1).getName());
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouseW8 = new Warehouse();
        warehouseW8.setCity("CityF");
        warehouseW8.setSurface(250.0);
        company.addWarehouses(warehouseW8);
        
        // SetUp: Add Product "Item4" from Supplier "S8"
        Supplier supplierS8 = new Supplier();
        supplierS8.setName("S8");
        
        Product product4 = new Product();
        product4.setName("Item4");
        product4.setSupplier(supplierS8);
        
        ProductOccupation occupation4 = new ProductOccupation();
        occupation4.setProduct(product4);
        occupation4.setVolume(120.0);
        warehouseW8.addOccupations(occupation4);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = warehouseW8.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouseW9 = new Warehouse();
        warehouseW9.setCity("CityG");
        warehouseW9.setSurface(150.0);
        company.addWarehouses(warehouseW9);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = warehouseW9.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // Company is already initialized with no warehouses in setUp() method
        
        // Action: Retrieve unique suppliers for "W10"
        // Since W10 doesn't exist in the company, we need to simulate this scenario
        // by creating a warehouse that is not added to the company
        Warehouse warehouseW10 = new Warehouse();
        warehouseW10.setCity("CityJ");
        warehouseW10.setSurface(200.0);
        
        // Action: Retrieve unique suppliers for W10 (warehouse not in company)
        List<Supplier> result = warehouseW10.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouseW11 = new Warehouse();
        warehouseW11.setCity("CityH");
        warehouseW11.setSurface(300.0);
        company.addWarehouses(warehouseW11);
        
        // SetUp: Add Product "A1" from Supplier "S9"
        Supplier supplierS9 = new Supplier();
        supplierS9.setName("S9");
        
        Product productA1 = new Product();
        productA1.setName("A1");
        productA1.setSupplier(supplierS9);
        
        ProductOccupation occupationA1 = new ProductOccupation();
        occupationA1.setProduct(productA1);
        occupationA1.setVolume(80.0);
        warehouseW11.addOccupations(occupationA1);
        
        // SetUp: Add Product "A2" from Supplier "S9"
        Product productA2 = new Product();
        productA2.setName("A2");
        productA2.setSupplier(supplierS9);
        
        ProductOccupation occupationA2 = new ProductOccupation();
        occupationA2.setProduct(productA2);
        occupationA2.setVolume(90.0);
        warehouseW11.addOccupations(occupationA2);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouseW11.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}