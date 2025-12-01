package edu.company.company2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Product;
import edu.company.ProductOccupation;
import edu.company.Supplier;
import edu.company.Warehouse;

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
        Warehouse w17 = factory.createWarehouse();
        w17.setCity("CityI");
        w17.setAddress("Address17");
        company1.getWarehouses().add(w17);
        
        // 2. Add Product "WidgetA" to W17.
        Product widgetA = factory.createProduct();
        widgetA.setName("WidgetA");
        widgetA.setToxic(false);
        
        Supplier supplier = factory.createSupplier();
        supplier.setName("SupplierA");
        widgetA.setSupplier(supplier);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(widgetA);
        occupation.setVolume(10.0);
        w17.getOccupations().add(occupation);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = w17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_productDoesNotExistInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W18" in "CityJ", in company C1.
        Warehouse w18 = factory.createWarehouse();
        w18.setCity("CityJ");
        w18.setAddress("Address18");
        company1.getWarehouses().add(w18);
        
        // 2. Add Product "GadgetB" to W18.
        Product gadgetB = factory.createProduct();
        gadgetB.setName("GadgetB");
        gadgetB.setToxic(false);
        
        Supplier supplier = factory.createSupplier();
        supplier.setName("SupplierB");
        gadgetB.setSupplier(supplier);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(gadgetB);
        occupation.setVolume(15.0);
        w18.getOccupations().add(occupation);
        
        // Action: Verify "ToolC" in W18.
        boolean result = w18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp:
        // 1. Initialize Warehouse "W19" in company C1.
        Warehouse w19 = factory.createWarehouse();
        w19.setCity("CityK");
        w19.setAddress("Address19");
        company1.getWarehouses().add(w19);
        
        // 2. Add Product "ItemX" to "W18" in company C2.
        Warehouse w18 = factory.createWarehouse();
        w18.setCity("CityL");
        w18.setAddress("Address18");
        company2.getWarehouses().add(w18);
        
        Product itemX = factory.createProduct();
        itemX.setName("ItemX");
        itemX.setToxic(false);
        
        Supplier supplier = factory.createSupplier();
        supplier.setName("SupplierC");
        itemX.setSupplier(supplier);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(itemX);
        occupation.setVolume(20.0);
        w18.getOccupations().add(occupation);
        
        // Action: Verify "ItemX" in Warehouse "W19".
        boolean result = w19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp:
        // 1. Create Warehouse "W20" in "CityK" (no products), in company C1.
        Warehouse w20 = factory.createWarehouse();
        w20.setCity("CityK");
        w20.setAddress("Address20");
        company1.getWarehouses().add(w20);
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
        Warehouse w21 = factory.createWarehouse();
        w21.setCity("CityL");
        w21.setAddress("Address21");
        company1.getWarehouses().add(w21);
        
        // 2. Add Products ["CompA", "CompB", "CompC"] to W21.
        String[] productNames = {"CompA", "CompB", "CompC"};
        
        for (int i = 0; i < productNames.length; i++) {
            Product product = factory.createProduct();
            product.setName(productNames[i]);
            product.setToxic(false);
            
            Supplier supplier = factory.createSupplier();
            supplier.setName("Supplier" + i);
            product.setSupplier(supplier);
            
            ProductOccupation occupation = factory.createProductOccupation();
            occupation.setProduct(product);
            occupation.setVolume(5.0 * (i + 1));
            w21.getOccupations().add(occupation);
        }
        
        // Action: Verify "CompB" in W21.
        boolean result = w21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue(result);
    }
}