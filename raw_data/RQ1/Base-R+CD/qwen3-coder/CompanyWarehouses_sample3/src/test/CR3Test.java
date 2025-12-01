// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

// Mock classes to simulate the system entities
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
    private String id;
    private int volume;
    private Supplier supplier;
    
    public Product(String id, int volume, Supplier supplier) {
        this.id = id;
        this.volume = volume;
        this.supplier = supplier;
    }
    
    public String getId() {
        return id;
    }
    
    public int getVolume() {
        return volume;
    }
    
    public Supplier getSupplier() {
        return supplier;
    }
}

class Warehouse {
    private String id;
    private String company;
    private List<Product> products;
    
    public Warehouse(String id, String company) {
        this.id = id;
        this.company = company;
        this.products = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public String getCompany() {
        return company;
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public void addProduct(Product product) {
        products.add(product);
    }
}

class SystemManager {
    private List<Warehouse> warehouses;
    private List<Supplier> suppliers;
    
    public SystemManager() {
        this.warehouses = new ArrayList<>();
        this.suppliers = new ArrayList<>();
    }
    
    public void addWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }
    
    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
    }
    
    public int getTotalVolumeBySupplierAndCompany(String supplierName, String companyName) {
        int totalVolume = 0;
        
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getCompany().equals(companyName)) {
                for (Product product : warehouse.getProducts()) {
                    if (product.getSupplier().getName().equals(supplierName)) {
                        totalVolume += product.getVolume();
                    }
                }
            }
        }
        
        return totalVolume;
    }
}

public class CR3Test {
    private SystemManager systemManager;
    
    @Before
    public void setUp() {
        systemManager = new SystemManager();
    }
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier s10 = new Supplier("S10");
        systemManager.addSupplier(s10);
        
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Warehouse w12 = new Warehouse("W12", "C1");
        Product p1 = new Product("P1", 5, s10);
        w12.addProduct(p1);
        systemManager.addWarehouse(w12);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Warehouse w13 = new Warehouse("W13", "C1");
        Product p2 = new Product("P2", 10, s10);
        w13.addProduct(p2);
        systemManager.addWarehouse(w13);
        
        // Action: List volumes for supplier "S10" in company C1.
        int result = systemManager.getTotalVolumeBySupplierAndCompany("S10", "C1");
        
        // Expected Output: 15
        assertEquals(15, result);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // SetUp:
        // 1. Create company C2 without warehouse.
        // (No warehouse needed as there are no products)
        
        // 2. Create Supplier "S11" (no products linked).
        Supplier s11 = new Supplier("S11");
        systemManager.addSupplier(s11);
        
        // Action: List volumes for supplier "S11" in company C2.
        int result = systemManager.getTotalVolumeBySupplierAndCompany("S11", "C2");
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier s10 = new Supplier("S10");
        systemManager.addSupplier(s10);
        
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Warehouse w12 = new Warehouse("W12", "C1");
        Product p1 = new Product("P1", 5, s10);
        w12.addProduct(p1);
        systemManager.addWarehouse(w12);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Warehouse w13 = new Warehouse("W13", "C1");
        Product p2 = new Product("P2", 10, s10);
        w13.addProduct(p2);
        systemManager.addWarehouse(w13);
        
        // 4. Create Supplier "S12" (no products linked).
        Supplier s12 = new Supplier("S12");
        systemManager.addSupplier(s12);
        
        // Action: List volumes for supplier name "S13" in company C1.
        int result = systemManager.getTotalVolumeBySupplierAndCompany("S13", "C1");
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // SetUp:
        // 1. Add Supplier "S14" to system.
        Supplier s14 = new Supplier("S14");
        systemManager.addSupplier(s14);
        
        // 2. Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3.
        Warehouse w14 = new Warehouse("W14", "C3");
        Product p3 = new Product("P3", 8, s14);
        w14.addProduct(p3);
        systemManager.addWarehouse(w14);
        
        // Action: List volumes for supplier "S14" in company C3.
        int result = systemManager.getTotalVolumeBySupplierAndCompany("S14", "C3");
        
        // Expected Output: 8
        assertEquals(8, result);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // SetUp:
        // 1. Add Supplier "S15" to system.
        Supplier s15 = new Supplier("S15");
        systemManager.addSupplier(s15);
        
        // 2. Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4.
        Warehouse w15 = new Warehouse("W15", "C4");
        Product x1 = new Product("X1", 3, s15);
        w15.addProduct(x1);
        systemManager.addWarehouse(w15);
        
        // 3. Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4.
        Warehouse w16 = new Warehouse("W16", "C4");
        Product x2 = new Product("X2", 7, s15);
        w16.addProduct(x2);
        systemManager.addWarehouse(w16);
        
        // 4. Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4.
        Product x3 = new Product("X3", 2, s15);
        w15.addProduct(x3);
        
        // Action: List volumes for supplier "S15" in company C4.
        int result = systemManager.getTotalVolumeBySupplierAndCompany("S15", "C4");
        
        // Expected Output: 12
        assertEquals(12, result);
    }
}