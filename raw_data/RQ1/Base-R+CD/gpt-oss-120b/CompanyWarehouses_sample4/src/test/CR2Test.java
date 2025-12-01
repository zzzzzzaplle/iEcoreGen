import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class CR2Test {
    
    private Company companyC1;
    
    @Before
    public void setUp() {
        // Initialize company C1 before each test
        companyC1 = new Company("C1");
    }
    
    @Test
    public void testCase1_WarehouseWithProductsFromMultipleSuppliers() {
        // SetUp:
        // 1. Create Warehouse "W7" in "CityE" with surface 350m², in company C1.
        Warehouse w7 = new Warehouse("W7", "CityE", 350);
        companyC1.addWarehouse(w7);
        
        // 2. Add Product "Item1" from Supplier "S6".
        Product item1 = new Product("Item1", "S6");
        w7.addProduct(item1);
        
        // 3. Add Product "Item2" from Supplier "S7".
        Product item2 = new Product("Item2", "S7");
        w7.addProduct(item2);
        
        // 4. Add Product "Item3" from Supplier "S6" (duplicate supplier).
        Product item3 = new Product("Item3", "S6");
        w7.addProduct(item3);
        
        // Action: Retrieve unique suppliers for W7.
        List<String> result = companyC1.getUniqueSuppliersForWarehouse("W7");
        
        // Expected Output: ["S6", "S7"]
        List<String> expected = Arrays.asList("S6", "S7");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
        assertTrue(expected.containsAll(result));
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // SetUp:
        // 1. Create Warehouse "W8" in "CityF" with surface 250m², in company C1.
        Warehouse w8 = new Warehouse("W8", "CityF", 250);
        companyC1.addWarehouse(w8);
        
        // 2. Add Product "Item4" from Supplier "S8".
        Product item4 = new Product("Item4", "S8");
        w8.addProduct(item4);
        
        // Action: Retrieve unique suppliers for W8.
        List<String> result = companyC1.getUniqueSuppliersForWarehouse("W8");
        
        // Expected Output: ["S8"]
        List<String> expected = Arrays.asList("S8");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
        assertTrue(expected.containsAll(result));
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1.
        Warehouse w9 = new Warehouse("W9", "CityG", 150);
        companyC1.addWarehouse(w9);
        
        // 2. Action: Retrieve unique suppliers for W9.
        List<String> result = companyC1.getUniqueSuppliersForWarehouse("W9");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp:
        // 1. Initialize company C1 with no warehouses.
        // (Already done in setUp)
        
        // Action: Retrieve unique suppliers for "W10".
        List<String> result = companyC1.getUniqueSuppliersForWarehouse("W10");
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // SetUp:
        // 1. Create Warehouse "W11" in "CityH" with surface 300m², in company C1.
        Warehouse w11 = new Warehouse("W11", "CityH", 300);
        companyC1.addWarehouse(w11);
        
        // 2. Add Product "A1" from Supplier "S9".
        Product a1 = new Product("A1", "S9");
        w11.addProduct(a1);
        
        // 3. Add Product "A2" from Supplier "S9".
        Product a2 = new Product("A2", "S9");
        w11.addProduct(a2);
        
        // Action: Retrieve unique suppliers for W11.
        List<String> result = companyC1.getUniqueSuppliersForWarehouse("W11");
        
        // Expected Output: ["S9"]
        List<String> expected = Arrays.asList("S9");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
        assertTrue(expected.containsAll(result));
    }
}

// Supporting classes to simulate the system
class Company {
    private String name;
    private List<Warehouse> warehouses;
    
    public Company(String name) {
        this.name = name;
        this.warehouses = new ArrayList<>();
    }
    
    public void addWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }
    
    public List<String> getUniqueSuppliersForWarehouse(String warehouseId) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getId().equals(warehouseId)) {
                return warehouse.getUniqueSuppliers();
            }
        }
        return new ArrayList<>(); // Return empty list if warehouse doesn't exist
    }
}

class Warehouse {
    private String id;
    private String city;
    private int surface;
    private List<Product> products;
    
    public Warehouse(String id, String city, int surface) {
        this.id = id;
        this.city = city;
        this.surface = surface;
        this.products = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public void addProduct(Product product) {
        products.add(product);
    }
    
    public List<String> getUniqueSuppliers() {
        List<String> uniqueSuppliers = new ArrayList<>();
        for (Product product : products) {
            if (!uniqueSuppliers.contains(product.getSupplier())) {
                uniqueSuppliers.add(product.getSupplier());
            }
        }
        return uniqueSuppliers;
    }
}

class Product {
    private String name;
    private String supplier;
    
    public Product(String name, String supplier) {
        this.name = name;
        this.supplier = supplier;
    }
    
    public String getSupplier() {
        return supplier;
    }
}