import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company1;
    private Company company2;
    
    @Before
    public void setUp() {
        company1 = new Company();
        company2 = new Company();
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // Create Warehouse "W17" in "CityI", in company C1
        Warehouse w17 = new Warehouse();
        w17.setCity("CityI");
        w17.setAddress("Address17");
        company1.addWarehouses(w17);
        
        // Add Product "WidgetA" to W17
        Product product = new Product();
        product.setName("WidgetA");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        w17.addOccupations(occupation);
        
        // Verify "WidgetA" in W17
        boolean result = w17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_productDoesNotExistInWarehouse() {
        // Create Warehouse "W18" in "CityJ", in company C1
        Warehouse w18 = new Warehouse();
        w18.setCity("CityJ");
        w18.setAddress("Address18");
        company1.addWarehouses(w18);
        
        // Add Product "GadgetB" to W18
        Product product = new Product();
        product.setName("GadgetB");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        w18.addOccupations(occupation);
        
        // Verify "ToolC" in W18
        boolean result = w18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // Initialize Warehouse "W19" in company C1
        Warehouse w19 = new Warehouse();
        w19.setCity("City19");
        w19.setAddress("Address19");
        company1.addWarehouses(w19);
        
        // Add Product "ItemX" to "W18" in company C2
        Warehouse w18 = new Warehouse();
        w18.setCity("City18");
        w18.setAddress("Address18");
        company2.addWarehouses(w18);
        
        Product product = new Product();
        product.setName("ItemX");
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        w18.addOccupations(occupation);
        
        // Verify "ItemX" in Warehouse "W19"
        boolean result = w19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // Create Warehouse "W20" in "CityK" (no products), in company C1
        Warehouse w20 = new Warehouse();
        w20.setCity("CityK");
        w20.setAddress("Address20");
        company1.addWarehouses(w20);
        
        // Verify "PartY" in W20
        boolean result = w20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // Create Warehouse "W21" in "CityL", in company C1
        Warehouse w21 = new Warehouse();
        w21.setCity("CityL");
        w21.setAddress("Address21");
        company1.addWarehouses(w21);
        
        // Add Products ["CompA", "CompB", "CompC"] to W21
        String[] productNames = {"CompA", "CompB", "CompC"};
        for (String name : productNames) {
            Product product = new Product();
            product.setName(name);
            
            ProductOccupation occupation = new ProductOccupation();
            occupation.setProduct(product);
            w21.addOccupations(occupation);
        }
        
        // Verify "CompB" in W21
        boolean result = w21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue(result);
    }
}