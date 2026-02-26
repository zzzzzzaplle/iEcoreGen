import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import com.example.warehouse.Warehouse;
import com.example.warehouse.Company;
import com.example.warehouse.Product;
import com.example.warehouse.Supplier;
import com.example.warehouse.ProductOccupation;

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
        company.addWarehouses(warehouse);
        
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
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, uniqueSuppliers.size());
        assertTrue(containsSupplierWithName(uniqueSuppliers, "S6"));
        assertTrue(containsSupplierWithName(uniqueSuppliers, "S7"));
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp: Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityF");
        warehouse.setSurface(250.0);
        company.addWarehouses(warehouse);
        
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
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, uniqueSuppliers.size());
        assertEquals("S8", uniqueSuppliers.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp: Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityG");
        warehouse.setSurface(150.0);
        company.addWarehouses(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(uniqueSuppliers.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp: Initialize company C1 with no warehouses
        // No warehouses added to company
        
        // Action: Retrieve unique suppliers for "W10" (non-existent warehouse)
        // Since we're testing the warehouse method directly, we'll create a warehouse but not add it to company
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityJ");
        warehouse.setSurface(200.0);
        
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(uniqueSuppliers.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp: Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityH");
        warehouse.setSurface(300.0);
        company.addWarehouses(warehouse);
        
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
        List<Supplier> uniqueSuppliers = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, uniqueSuppliers.size());
        assertEquals("S9", uniqueSuppliers.get(0).getName());
    }
    
    // Helper method to check if a list contains a supplier with the given name
    private boolean containsSupplierWithName(List<Supplier> suppliers, String name) {
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}