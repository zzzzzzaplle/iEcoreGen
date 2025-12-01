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
        // SetUp: Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouseW7 = new Warehouse();
        warehouseW7.setCity("CityE");
        warehouseW7.setSurface(350.0);
        
        // Add Product "Item1" from Supplier "S6"
        Supplier supplierS6 = new Supplier();
        supplierS6.setName("S6");
        
        Product product1 = new Product();
        product1.setName("Item1");
        product1.setSupplier(supplierS6);
        
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        
        // Add Product "Item2" from Supplier "S7"
        Supplier supplierS7 = new Supplier();
        supplierS7.setName("S7");
        
        Product product2 = new Product();
        product2.setName("Item2");
        product2.setSupplier(supplierS7);
        
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = new Product();
        product3.setName("Item3");
        product3.setSupplier(supplierS6);
        
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        
        // Add all occupations to warehouse
        warehouseW7.addOccupations(occupation1);
        warehouseW7.addOccupations(occupation2);
        warehouseW7.addOccupations(occupation3);
        
        company.addWarehouses(warehouseW7);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = warehouseW7.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getName().equals("S6")));
        assertTrue(result.stream().anyMatch(s -> s.getName().equals("S7")));
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouseW8 = new Warehouse();
        warehouseW8.setCity("CityF");
        warehouseW8.setSurface(250.0);
        
        // Add Product "Item4" from Supplier "S8"
        Supplier supplierS8 = new Supplier();
        supplierS8.setName("S8");
        
        Product product4 = new Product();
        product4.setName("Item4");
        product4.setSupplier(supplierS8);
        
        ProductOccupation occupation4 = new ProductOccupation();
        occupation4.setProduct(product4);
        
        warehouseW8.addOccupations(occupation4);
        company.addWarehouses(warehouseW8);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = warehouseW8.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
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
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // Action: Retrieve unique suppliers for "W10"
        // Since W10 doesn't exist in the company, we need to create a separate warehouse that's not added to company
        Warehouse warehouseW10 = new Warehouse();
        warehouseW10.setCity("SomeCity");
        warehouseW10.setSurface(200.0);
        
        List<Supplier> result = warehouseW10.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouseW11 = new Warehouse();
        warehouseW11.setCity("CityH");
        warehouseW11.setSurface(300.0);
        
        // Add Product "A1" from Supplier "S9"
        Supplier supplierS9 = new Supplier();
        supplierS9.setName("S9");
        
        Product productA1 = new Product();
        productA1.setName("A1");
        productA1.setSupplier(supplierS9);
        
        ProductOccupation occupationA1 = new ProductOccupation();
        occupationA1.setProduct(productA1);
        
        // Add Product "A2" from Supplier "S9"
        Product productA2 = new Product();
        productA2.setName("A2");
        productA2.setSupplier(supplierS9);
        
        ProductOccupation occupationA2 = new ProductOccupation();
        occupationA2.setProduct(productA2);
        
        warehouseW11.addOccupations(occupationA1);
        warehouseW11.addOccupations(occupationA2);
        company.addWarehouses(warehouseW11);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = warehouseW11.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}