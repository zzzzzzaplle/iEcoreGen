import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    // Model classes based on test requirements
    static class Company {
        private String name;
        private List<Warehouse> warehouses;
        
        public Company(String name) {
            this.name = name;
            this.warehouses = new ArrayList<>();
        }
        
        public void addWarehouse(Warehouse warehouse) {
            this.warehouses.add(warehouse);
        }
        
        public List<Warehouse> getWarehouses() {
            return warehouses;
        }
        
        public Warehouse getWarehouseByName(String name) {
            for (Warehouse w : warehouses) {
                if (w.getName().equals(name)) {
                    return w;
                }
            }
            return null;
        }
    }
    
    static class Warehouse {
        private String name;
        private String city;
        private double surface;
        private List<Product> products;
        
        public Warehouse(String name, String city, double surface) {
            this.name = name;
            this.city = city;
            this.surface = surface;
            this.products = new ArrayList<>();
        }
        
        public void addProduct(Product product) {
            this.products.add(product);
        }
        
        public String getName() {
            return name;
        }
        
        public List<Product> getProducts() {
            return products;
        }
    }
    
    static class Product {
        private String name;
        private boolean toxic;
        private double volume;
        private Supplier supplier;
        
        public Product(String name, boolean toxic, double volume, Supplier supplier) {
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
    }
    
    static class Supplier {
        private String name;
        
        public Supplier(String name) {
            this.name = name;
        }
    }
    
    // Method under test - to be implemented based on CR1 specification
    private List<String> getToxicProductNames(Company company, String warehouseName) {
        List<String> toxicProductNames = new ArrayList<>();
        
        // Find the warehouse
        Warehouse warehouse = company.getWarehouseByName(warehouseName);
        if (warehouse == null) {
            return toxicProductNames; // Return empty list if warehouse doesn't exist
        }
        
        // Check each product in the warehouse
        for (Product product : warehouse.getProducts()) {
            if (product.isToxic()) {
                toxicProductNames.add(product.getName());
            }
        }
        
        return toxicProductNames;
    }
    
    @Test
    public void testCase1_warehouseWithMultipleToxicProducts() {
        // SetUp:
        // 1. Create Warehouse "W1" in "CityA" with surface 500m², in company C1.
        Company c1 = new Company("C1");
        Warehouse w1 = new Warehouse("W1", "CityA", 500);
        c1.addWarehouse(w1);
        
        // 2. Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1".
        Supplier s1 = new Supplier("S1");
        Product chemX = new Product("ChemX", true, 10, s1);
        w1.addProduct(chemX);
        
        // 3. Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2".
        Supplier s2 = new Supplier("S2");
        Product paintY = new Product("PaintY", true, 5, s2);
        w1.addProduct(paintY);
        
        // 4. Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3".
        Supplier s3 = new Supplier("S3");
        Product toolZ = new Product("ToolZ", false, 15, s3);
        w1.addProduct(toolZ);
        
        // Action: Retrieve toxic products in W1.
        List<String> result = getToxicProductNames(c1, "W1");
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ChemX"));
        assertTrue(result.contains("PaintY"));
    }
    
    @Test
    public void testCase2_warehouseWithNoToxicProducts() {
        // SetUp:
        // 1. Create Warehouse "W2" in "CityB" with surface 300m², in company C2.
        Company c2 = new Company("C2");
        Warehouse w2 = new Warehouse("W2", "CityB", 300);
        c2.addWarehouse(w2);
        
        // 2. Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1".
        Supplier s1 = new Supplier("S1");
        Product boxA = new Product("BoxA", false, 8, s1);
        w2.addProduct(boxA);
        
        // 3. Add Product "CableB" (toxic=false, volume=3) from Supplier "S4".
        Supplier s4 = new Supplier("S4");
        Product cableB = new Product("CableB", false, 3, s4);
        w2.addProduct(cableB);
        
        // Action: Retrieve toxic products in W2.
        List<String> result = getToxicProductNames(c2, "W2");
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3.
        Company c3 = new Company("C3");
        Warehouse w3 = new Warehouse("W3", "CityC", 200);
        c3.addWarehouse(w3);
        
        // Action: Retrieve toxic products in W3.
        List<String> result = getToxicProductNames(c3, "W3");
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp:
        // 1. Initialize company C3 with Warehouse "W4" only.
        Company c3 = new Company("C3");
        Warehouse w4 = new Warehouse("W4", "SomeCity", 100);
        c3.addWarehouse(w4);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3).
        List<String> result = getToxicProductNames(c3, "W5");
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_warehouseWithSingleToxicProduct() {
        // SetUp:
        // 1. Create Warehouse "W6" in "CityD" with surface 400m², in company C4.
        Company c4 = new Company("C4");
        Warehouse w6 = new Warehouse("W6", "CityD", 400);
        c4.addWarehouse(w6);
        
        // 2. Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5".
        Supplier s5 = new Supplier("S5");
        Product acidK = new Product("AcidK", true, 12, s5);
        w6.addProduct(acidK);
        
        // Action: Retrieve toxic products in W6.
        List<String> result = getToxicProductNames(c4, "W6");
        
        // Expected Output: ["AcidK"]
        assertEquals(1, result.size());
        assertEquals("AcidK", result.get(0));
    }
}