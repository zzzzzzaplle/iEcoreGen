package edu.company.company3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.ProductOccupation;
import edu.company.Supplier;

public class CR4Test {
    
    private CompanyFactory factory;
    private Company company1;
    private Company company2;
    
    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
        company1 = factory.createCompany();
        company2 = factory.createCompany();
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W17" in "CityI", in company C1.
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityI");
        warehouse.setAddress("Address17");
        company1.getWarehouses().add(warehouse);
        
        // 2. Add Product "WidgetA" to W17.
        Product product = factory.createProduct();
        product.setName("WidgetA");
        product.setToxic(false);
        
        Supplier supplier = factory.createSupplier();
        supplier.setName("SupplierA");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(10.0);
        
        warehouse.getOccupations().add(occupation);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = warehouse.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_productDoesNotExistInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W18" in "CityJ", in company C1.
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityJ");
        warehouse.setAddress("Address18");
        company1.getWarehouses().add(warehouse);
        
        // 2. Add Product "GadgetB" to W18.
        Product product = factory.createProduct();
        product.setName("GadgetB");
        product.setToxic(false);
        
        Supplier supplier = factory.createSupplier();
        supplier.setName("SupplierB");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(15.0);
        
        warehouse.getOccupations().add(occupation);
        
        // Action: Verify "ToolC" in W18.
        boolean result = warehouse.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp:
        // 1. Initialize Warehouse "W19" in company C1.
        Warehouse warehouseW19 = factory.createWarehouse();
        warehouseW19.setCity("CityL");
        warehouseW19.setAddress("Address19");
        company1.getWarehouses().add(warehouseW19);
        
        // 2. Add Product "ItemX" to "W18" in company C2.
        Warehouse warehouseW18 = factory.createWarehouse();
        warehouseW18.setCity("CityM");
        warehouseW18.setAddress("Address18");
        company2.getWarehouses().add(warehouseW18);
        
        Product product = factory.createProduct();
        product.setName("ItemX");
        product.setToxic(false);
        
        Supplier supplier = factory.createSupplier();
        supplier.setName("SupplierC");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(20.0);
        
        warehouseW18.getOccupations().add(occupation);
        
        // Action: Verify "ItemX" in Warehouse "W19".
        boolean result = warehouseW19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp:
        // 1. Create Warehouse "W20" in "CityK" (no products), in company C1.
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityK");
        warehouse.setAddress("Address20");
        company1.getWarehouses().add(warehouse);
        // No products added to warehouse
        
        // Action: Verify "PartY" in W20.
        boolean result = warehouse.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W21" in "CityL", in company C1.
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityL");
        warehouse.setAddress("Address21");
        company1.getWarehouses().add(warehouse);
        
        // 2. Add Products ["CompA", "CompB", "CompC"] to W21.
        String[] productNames = {"CompA", "CompB", "CompC"};
        
        for (int i = 0; i < productNames.length; i++) {
            Product product = factory.createProduct();
            product.setName(productNames[i]);
            product.setToxic(false);
            
            Supplier supplier = factory.createSupplier();
            supplier.setName("SupplierD" + i);
            product.setSupplier(supplier);
            
            ProductOccupation occupation = factory.createProductOccupation();
            occupation.setProduct(product);
            occupation.setVolume(5.0 * (i + 1));
            
            warehouse.getOccupations().add(occupation);
        }
        
        // Action: Verify "CompB" in W21.
        boolean result = warehouse.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue(result);
    }
}