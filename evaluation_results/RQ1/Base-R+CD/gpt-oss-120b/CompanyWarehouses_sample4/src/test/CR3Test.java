import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

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
    private Company company;
    private List<Product> products;
    
    public Warehouse(String id, Company company) {
        this.id = id;
        this.company = company;
        this.products = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public Company getCompany() {
        return company;
    }
    
    public void addProduct(Product product) {
        products.add(product);
    }
    
    public List<Product> getProducts() {
        return products;
    }
}

class Company {
    private String id;
    private List<Warehouse> warehouses;
    
    public Company(String id) {
        this.id = id;
        this.warehouses = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public void addWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }
    
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }
}

public class CR3Test {
    
    // Method to calculate total volume of products supplied by a specific supplier in a specific company
    public int getTotalVolumeBySupplierInCompany(String supplierName, Company company) {
        int totalVolume = 0;
        
        if (company == null || supplierName == null) {
            return 0;
        }
        
        for (Warehouse warehouse : company.getWarehouses()) {
            for (Product product : warehouse.getProducts()) {
                if (product.getSupplier() != null && 
                    product.getSupplier().getName().equals(supplierName)) {
                    totalVolume += product.getVolume();
                }
            }
        }
        
        return totalVolume;
    }
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier s10 = new Supplier("S10");
        
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Company c1 = new Company("C1");
        Warehouse w12 = new Warehouse("W12", c1);
        Product p1 = new Product("P1", 5, s10);
        w12.addProduct(p1);
        c1.addWarehouse(w12);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Warehouse w13 = new Warehouse("W13", c1);
        Product p2 = new Product("P2", 10, s10);
        w13.addProduct(p2);
        c1.addWarehouse(w13);
        
        // Action: List volumes for supplier "S10" in company C1.
        int result = getTotalVolumeBySupplierInCompany("S10", c1);
        
        // Expected Output: 15
        assertEquals(15, result);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // SetUp:
        // 1. Create company C2 without warehouse.
        Company c2 = new Company("C2");
        
        // 2. Create Supplier "S11" (no products linked).
        Supplier s11 = new Supplier("S11");
        
        // Action: List volumes for supplier "S11" in company C2.
        int result = getTotalVolumeBySupplierInCompany("S11", c2);
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier s10 = new Supplier("S10");
        
        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Company c1 = new Company("C1");
        Warehouse w12 = new Warehouse("W12", c1);
        Product p1 = new Product("P1", 5, s10);
        w12.addProduct(p1);
        c1.addWarehouse(w12);
        
        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Warehouse w13 = new Warehouse("W13", c1);
        Product p2 = new Product("P2", 10, s10);
        w13.addProduct(p2);
        c1.addWarehouse(w13);
        
        // 4. Create Supplier "S12" (no products linked).
        // Note: S12 is created but not used in this test
        
        // Action: List volumes for supplier name "S13" in company C1.
        int result = getTotalVolumeBySupplierInCompany("S13", c1);
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // SetUp:
        // 1. Add Supplier "S14" to system.
        Supplier s14 = new Supplier("S14");
        
        // 2. Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3.
        Company c3 = new Company("C3");
        Warehouse w14 = new Warehouse("W14", c3);
        Product p3 = new Product("P3", 8, s14);
        w14.addProduct(p3);
        c3.addWarehouse(w14);
        
        // Action: List volumes for supplier "S14" in company C3.
        int result = getTotalVolumeBySupplierInCompany("S14", c3);
        
        // Expected Output: 8
        assertEquals(8, result);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // SetUp:
        // 1. Add Supplier "S15" to system.
        Supplier s15 = new Supplier("S15");
        
        // 2. Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4.
        Company c4 = new Company("C4");
        Warehouse w15 = new Warehouse("W15", c4);
        Product x1 = new Product("X1", 3, s15);
        w15.addProduct(x1);
        c4.addWarehouse(w15);
        
        // 3. Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4.
        Warehouse w16 = new Warehouse("W16", c4);
        Product x2 = new Product("X2", 7, s15);
        w16.addProduct(x2);
        c4.addWarehouse(w16);
        
        // 4. Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4.
        Product x3 = new Product("X3", 2, s15);
        w15.addProduct(x3);
        
        // Action: List volumes for supplier "S15" in company C4.
        int result = getTotalVolumeBySupplierInCompany("S15", c4);
        
        // Expected Output: 12
        assertEquals(12, result);
    }
}