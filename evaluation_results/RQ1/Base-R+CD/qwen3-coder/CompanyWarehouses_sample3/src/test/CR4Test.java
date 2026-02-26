import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

// Mock classes to simulate the system under test
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
    
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }
    
    public void addWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }
}

class Warehouse {
    private String name;
    private String city;
    private Company company;
    private List<Product> products;
    
    public Warehouse(String name, String city, Company company) {
        this.name = name;
        this.city = city;
        this.company = company;
        this.products = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public String getCity() {
        return city;
    }
    
    public Company getCompany() {
        return company;
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public void addProduct(Product product) {
        products.add(product);
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

// Assuming there's a service class that contains the method to verify product in warehouse
class WarehouseService {
    public boolean isProductInWarehouse(String productName, String warehouseName, Company company) {
        // Return false if company is null
        if (company == null) {
            return false;
        }
        
        // Find the warehouse by name
        Warehouse warehouse = null;
        for (Warehouse w : company.getWarehouses()) {
            if (w.getName().equals(warehouseName)) {
                warehouse = w;
                break;
            }
        }
        
        // Return false if warehouse doesn't exist
        if (warehouse == null) {
            return false;
        }
        
        // Check if product exists in warehouse
        for (Product product : warehouse.getProducts()) {
            if (product.getName().equals(productName)) {
                return true;
            }
        }
        
        // Product not found in warehouse
        return false;
    }
}

public class CR4Test {
    private Company companyC1;
    private Company companyC2;
    private WarehouseService warehouseService;
    
    @Before
    public void setUp() {
        companyC1 = new Company("C1");
        companyC2 = new Company("C2");
        warehouseService = new WarehouseService();
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W17" in "CityI", in company C1.
        Warehouse w17 = new Warehouse("W17", "CityI", companyC1);
        companyC1.addWarehouse(w17);
        
        // 2. Add Product "WidgetA" to W17.
        Product widgetA = new Product("WidgetA");
        w17.addProduct(widgetA);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = warehouseService.isProductInWarehouse("WidgetA", "W17", companyC1);
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W18" in "CityJ", in company C1.
        Warehouse w18 = new Warehouse("W18", "CityJ", companyC1);
        companyC1.addWarehouse(w18);
        
        // 2. Add Product "GadgetB" to W18.
        Product gadgetB = new Product("GadgetB");
        w18.addProduct(gadgetB);
        
        // Action: Verify "ToolC" in W18.
        boolean result = warehouseService.isProductInWarehouse("ToolC", "W18", companyC1);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp:
        // 1. Initialize Warehouse "W19" in company C1.
        Warehouse w19 = new Warehouse("W19", "CityL", companyC1);
        companyC1.addWarehouse(w19);
        
        // 2. Add Product "ItemX" to "W18" in company C2.
        Warehouse w18 = new Warehouse("W18", "CityM", companyC2);
        companyC2.addWarehouse(w18);
        Product itemX = new Product("ItemX");
        w18.addProduct(itemX);
        
        // Action: Verify "ItemX" in Warehouse "W19".
        boolean result = warehouseService.isProductInWarehouse("ItemX", "W19", companyC1);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp:
        // 1. Create Warehouse "W20" in "CityK" (no products), in company C1.
        Warehouse w20 = new Warehouse("W20", "CityK", companyC1);
        companyC1.addWarehouse(w20);
        // No products added to W20
        
        // Action: Verify "PartY" in W20.
        boolean result = warehouseService.isProductInWarehouse("PartY", "W20", companyC1);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W21" in "CityL", in company C1.
        Warehouse w21 = new Warehouse("W21", "CityL", companyC1);
        companyC1.addWarehouse(w21);
        
        // 2. Add Products ["CompA", "CompB", "CompC"] to W21.
        Product compA = new Product("CompA");
        Product compB = new Product("CompB");
        Product compC = new Product("CompC");
        w21.addProduct(compA);
        w21.addProduct(compB);
        w21.addProduct(compC);
        
        // Action: Verify "CompB" in W21.
        boolean result = warehouseService.isProductInWarehouse("CompB", "W21", companyC1);
        
        // Expected Output: true
        assertTrue(result);
    }
}