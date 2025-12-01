import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class CR1Test {
    
    // Assuming we have these classes based on the test specifications
    private Company companyC1, companyC2, companyC3, companyC4;
    private Warehouse w1, w2, w3, w4, w6;
    private Supplier s1, s2, s3, s4, s5;
    
    // Note: Since the actual implementation classes are not provided,
    // I'm assuming their structure based on the test requirements.
    
    @Before
    public void setUp() {
        // Initialize suppliers
        s1 = new Supplier("S1");
        s2 = new Supplier("S2");
        s3 = new Supplier("S3");
        s4 = new Supplier("S4");
        s5 = new Supplier("S5");
    }
    
    @Test
    public void testCase1_warehouseWithMultipleToxicProducts() {
        // Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        companyC1 = new Company("C1");
        w1 = new Warehouse("W1", "CityA", 500);
        companyC1.addWarehouse(w1);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        w1.addProduct(new Product("ChemX", true, 10, s1));
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        w1.addProduct(new Product("PaintY", true, 5, s2));
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        w1.addProduct(new Product("ToolZ", false, 15, s3));
        
        // Action: Retrieve toxic products in W1
        List<String> result = companyC1.getToxicProductsInWarehouse("W1");
        
        // Expected Output: ["ChemX", "PaintY"]
        List<String> expected = Arrays.asList("ChemX", "PaintY");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }
    
    @Test
    public void testCase2_warehouseWithNoToxicProducts() {
        // Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        companyC2 = new Company("C2");
        w2 = new Warehouse("W2", "CityB", 300);
        companyC2.addWarehouse(w2);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        w2.addProduct(new Product("BoxA", false, 8, s1));
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        w2.addProduct(new Product("CableB", false, 3, s4));
        
        // Action: Retrieve toxic products in W2
        List<String> result = companyC2.getToxicProductsInWarehouse("W2");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        companyC3 = new Company("C3");
        w3 = new Warehouse("W3", "CityC", 200);
        companyC3.addWarehouse(w3);
        
        // Action: Retrieve toxic products in W3
        List<String> result = companyC3.getToxicProductsInWarehouse("W3");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // Initialize company C3 with Warehouse "W4" only
        companyC3 = new Company("C3");
        w4 = new Warehouse("W4", "CityC", 200);
        companyC3.addWarehouse(w4);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        List<String> result = companyC3.getToxicProductsInWarehouse("W5");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_warehouseWithSingleToxicProduct() {
        // Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        companyC4 = new Company("C4");
        w6 = new Warehouse("W6", "CityD", 400);
        companyC4.addWarehouse(w6);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        w6.addProduct(new Product("AcidK", true, 12, s5));
        
        // Action: Retrieve toxic products in W6
        List<String> result = companyC4.getToxicProductsInWarehouse("W6");
        
        // Expected Output: ["AcidK"]
        List<String> expected = Arrays.asList("AcidK");
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }
}

// Assumed implementation classes based on test requirements
class Supplier {
    private String name;
    
    public Supplier(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}

class Product {
    private String name;
    private boolean toxic;
    private int volume;
    private Supplier supplier;
    
    public Product(String name, boolean toxic, int volume, Supplier supplier) {
        this.name = name;
        this.toxic = toxic;
        this.volume = volume;
        this.supplier = supplier;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isToxic() {
        return toxic;
    }
    
    public int getVolume() {
        return volume;
    }
    
    public Supplier getSupplier() {
        return supplier;
    }
}

class Warehouse {
    private String name;
    private String city;
    private int surface;
    private List<Product> products;
    
    public Warehouse(String name, String city, int surface) {
        this.name = name;
        this.city = city;
        this.surface = surface;
        this.products = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public String getCity() {
        return city;
    }
    
    public int getSurface() {
        return surface;
    }
    
    public void addProduct(Product product) {
        products.add(product);
    }
    
    public List<Product> getProducts() {
        return products;
    }
}

class Company {
    private String name;
    private List<Warehouse> warehouses;
    
    public Company(String name) {
        this.name = name;
        this.warehouses = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void addWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }
    
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }
    
    public List<String> getToxicProductsInWarehouse(String warehouseName) {
        List<String> toxicProducts = new ArrayList<>();
        
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getName().equals(warehouseName)) {
                for (Product product : warehouse.getProducts()) {
                    if (product.isToxic()) {
                        toxicProducts.add(product.getName());
                    }
                }
                return toxicProducts;
            }
        }
        
        // Return empty list if warehouse doesn't exist
        return toxicProducts;
    }
}