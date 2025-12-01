import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // SetUp: Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        productP1.setVolume(5.0);
        
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setAddress("W12");
        List<Product> productsW12 = new ArrayList<>();
        productsW12.add(productP1);
        warehouseW12.setProducts(productsW12);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        productP2.setVolume(10.0);
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setAddress("W13");
        List<Product> productsW13 = new ArrayList<>();
        productsW13.add(productP2);
        warehouseW13.setProducts(productsW13);
        
        // Add warehouses to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouseW12);
        warehouses.add(warehouseW13);
        company.setWarehouses(warehouses);
        
        // Action: List volumes for supplier "S10" in company C1
        double result = company.getTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // SetUp: Create company C2 without warehouse
        // (company is already created in setUp with empty warehouses list)
        
        // SetUp: Create Supplier "S11" (no products linked)
        // No action needed since supplier has no products and company has no warehouses
        
        // Action: List volumes for supplier "S11" in company C2
        double result = company.getTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NonExistentSupplier() {
        // SetUp: Create Supplier "S10"
        Supplier supplierS10 = new Supplier();
        supplierS10.setName("S10");
        
        // SetUp: Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1
        Product productP1 = new Product();
        productP1.setName("P1");
        productP1.setSupplier(supplierS10);
        productP1.setVolume(5.0);
        
        Warehouse warehouseW12 = new Warehouse();
        warehouseW12.setAddress("W12");
        List<Product> productsW12 = new ArrayList<>();
        productsW12.add(productP1);
        warehouseW12.setProducts(productsW12);
        
        // SetUp: Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1
        Product productP2 = new Product();
        productP2.setName("P2");
        productP2.setSupplier(supplierS10);
        productP2.setVolume(10.0);
        
        Warehouse warehouseW13 = new Warehouse();
        warehouseW13.setAddress("W13");
        List<Product> productsW13 = new ArrayList<>();
        productsW13.add(productP2);
        warehouseW13.setProducts(productsW13);
        
        // SetUp: Create Supplier "S12" (no products linked)
        // No action needed since this supplier has no products
        
        // Add warehouses to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouseW12);
        warehouses.add(warehouseW13);
        company.setWarehouses(warehouses);
        
        // Action: List volumes for supplier name "S13" in company C1
        double result = company.getTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // SetUp: Add Supplier "S14" to system
        Supplier supplierS14 = new Supplier();
        supplierS14.setName("S14");
        
        // SetUp: Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3
        Product productP3 = new Product();
        productP3.setName("P3");
        productP3.setSupplier(supplierS14);
        productP3.setVolume(8.0);
        
        Warehouse warehouseW14 = new Warehouse();
        warehouseW14.setAddress("W14");
        List<Product> productsW14 = new ArrayList<>();
        productsW14.add(productP3);
        warehouseW14.setProducts(productsW14);
        
        // Add warehouse to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouseW14);
        company.setWarehouses(warehouses);
        
        // Action: List volumes for supplier "S14" in company C3
        double result = company.getTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // SetUp: Add Supplier "S15" to system
        Supplier supplierS15 = new Supplier();
        supplierS15.setName("S15");
        
        // SetUp: Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4
        Product productX1 = new Product();
        productX1.setName("X1");
        productX1.setSupplier(supplierS15);
        productX1.setVolume(3.0);
        
        Warehouse warehouseW15 = new Warehouse();
        warehouseW15.setAddress("W15");
        List<Product> productsW15 = new ArrayList<>();
        productsW15.add(productX1);
        
        // SetUp: Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4
        Product productX2 = new Product();
        productX2.setName("X2");
        productX2.setSupplier(supplierS15);
        productX2.setVolume(7.0);
        
        Warehouse warehouseW16 = new Warehouse();
        warehouseW16.setAddress("W16");
        List<Product> productsW16 = new ArrayList<>();
        productsW16.add(productX2);
        
        // SetUp: Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4
        Product productX3 = new Product();
        productX3.setName("X3");
        productX3.setSupplier(supplierS15);
        productX3.setVolume(2.0);
        productsW15.add(productX3);
        
        warehouseW15.setProducts(productsW15);
        warehouseW16.setProducts(productsW16);
        
        // Add warehouses to company
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouseW15);
        warehouses.add(warehouseW16);
        company.setWarehouses(warehouses);
        
        // Action: List volumes for supplier "S15" in company C4
        double result = company.getTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}