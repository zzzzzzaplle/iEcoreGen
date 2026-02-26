import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Company companyC1;
    private Company companyC2;
    
    @Before
    public void setUp() {
        // Initialize companies for testing
        companyC1 = new Company("C1");
        companyC2 = new Company("C2");
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W17" in "CityI", in company C1.
        Warehouse w17 = new Warehouse("W17", "CityI");
        companyC1.addWarehouse(w17);
        
        // 2. Add Product "WidgetA" to W17.
        Product widgetA = new Product("WidgetA");
        w17.addProduct(widgetA);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = w17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W18" in "CityJ", in company C1.
        Warehouse w18 = new Warehouse("W18", "CityJ");
        companyC1.addWarehouse(w18);
        
        // 2. Add Product "GadgetB" to W18.
        Product gadgetB = new Product("GadgetB");
        w18.addProduct(gadgetB);
        
        // Action: Verify "ToolC" in W18.
        boolean result = w18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp:
        // 1. Initialize Warehouse "W19" in company C1.
        Warehouse w19 = new Warehouse("W19", "SomeCity");
        companyC1.addWarehouse(w19);
        
        // 2. Add Product "ItemX" to "W18" in company C2.
        Warehouse w18 = new Warehouse("W18", "SomeOtherCity");
        companyC2.addWarehouse(w18);
        Product itemX = new Product("ItemX");
        w18.addProduct(itemX);
        
        // Action: Verify "ItemX" in Warehouse "W19".
        boolean result = w19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp:
        // 1. Create Warehouse "W20" in "CityK" (no products), in company C1.
        Warehouse w20 = new Warehouse("W20", "CityK");
        companyC1.addWarehouse(w20);
        // No products added to warehouse
        
        // Action: Verify "PartY" in W20.
        boolean result = w20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W21" in "CityL", in company C1.
        Warehouse w21 = new Warehouse("W21", "CityL");
        companyC1.addWarehouse(w21);
        
        // 2. Add Products ["CompA", "CompB", "CompC"] to W21.
        Product compA = new Product("CompA");
        Product compB = new Product("CompB");
        Product compC = new Product("CompC");
        w21.addProduct(compA);
        w21.addProduct(compB);
        w21.addProduct(compC);
        
        // Action: Verify "CompB" in W21.
        boolean result = w21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue(result);
    }
}

// Supporting classes that would need to exist for the tests to work
class Company {
    private String name;
    private java.util.List<Warehouse> warehouses;
    
    public Company(String name) {
        this.name = name;
        this.warehouses = new java.util.ArrayList<>();
    }
    
    public void addWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }
}

class Warehouse {
    private String name;
    private String city;
    private java.util.List<Product> products;
    
    public Warehouse(String name, String city) {
        this.name = name;
        this.city = city;
        this.products = new java.util.ArrayList<>();
    }
    
    public void addProduct(Product product) {
        products.add(product);
    }
    
    public boolean containsProduct(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }
    
    public String getName() {
        return name;
    }
}

class Product {
    private String name;
    
    public Product(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}