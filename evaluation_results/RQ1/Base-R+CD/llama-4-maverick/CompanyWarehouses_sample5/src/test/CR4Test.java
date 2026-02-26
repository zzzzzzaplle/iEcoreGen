import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company companyC1;
    private Company companyC2;
    
    @Before
    public void setUp() {
        companyC1 = new Company();
        companyC2 = new Company();
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W17" in "CityI", in company C1.
        Warehouse w17 = new Warehouse();
        w17.setCity("CityI");
        w17.setAddress("W17");
        companyC1.addWarehouses(w17);
        
        // 2. Add Product "WidgetA" to W17.
        Product widgetA = new Product();
        widgetA.setName("WidgetA");
        Supplier supplier = new Supplier();
        supplier.setName("Supplier1");
        widgetA.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(widgetA);
        occupation.setVolume(10.0);
        w17.addOccupations(occupation);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = w17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W18" in "CityJ", in company C1.
        Warehouse w18 = new Warehouse();
        w18.setCity("CityJ");
        w18.setAddress("W18");
        companyC1.addWarehouses(w18);
        
        // 2. Add Product "GadgetB" to W18.
        Product gadgetB = new Product();
        gadgetB.setName("GadgetB");
        Supplier supplier = new Supplier();
        supplier.setName("Supplier1");
        gadgetB.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(gadgetB);
        occupation.setVolume(10.0);
        w18.addOccupations(occupation);
        
        // Action: Verify "ToolC" in W18.
        boolean result = w18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp:
        // 1. Initialize Warehouse "W19" in company C1.
        Warehouse w19 = new Warehouse();
        w19.setCity("CityL");
        w19.setAddress("W19");
        companyC1.addWarehouses(w19);
        
        // 2. Add Product "ItemX" to "W18" in company C2.
        Warehouse w18 = new Warehouse();
        w18.setCity("CityM");
        w18.setAddress("W18");
        companyC2.addWarehouses(w18);
        
        Product itemX = new Product();
        itemX.setName("ItemX");
        Supplier supplier = new Supplier();
        supplier.setName("Supplier1");
        itemX.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(itemX);
        occupation.setVolume(10.0);
        w18.addOccupations(occupation);
        
        // Action: Verify "ItemX" in Warehouse "W19".
        boolean result = w19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp:
        // 1. Create Warehouse "W20" in "CityK" (no products), in company C1.
        Warehouse w20 = new Warehouse();
        w20.setCity("CityK");
        w20.setAddress("W20");
        companyC1.addWarehouses(w20);
        
        // Action: Verify "PartY" in W20.
        boolean result = w20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W21" in "CityL", in company C1.
        Warehouse w21 = new Warehouse();
        w21.setCity("CityL");
        w21.setAddress("W21");
        companyC1.addWarehouses(w21);
        
        // 2. Add Products ["CompA", "CompB", "CompC"] to W21.
        String[] productNames = {"CompA", "CompB", "CompC"};
        List<ProductOccupation> occupations = new ArrayList<>();
        
        for (String productName : productNames) {
            Product product = new Product();
            product.setName(productName);
            Supplier supplier = new Supplier();
            supplier.setName("Supplier1");
            product.setSupplier(supplier);
            
            ProductOccupation occupation = new ProductOccupation();
            occupation.setProduct(product);
            occupation.setVolume(10.0);
            w21.addOccupations(occupation);
        }
        
        // Action: Verify "CompB" in W21.
        boolean result = w21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue(result);
    }
}