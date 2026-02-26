import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public class CR4Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1
        Warehouse w17 = new Warehouse();
        w17.setCity("CityI");
        w17.setAddress("W17");
        company.addWarehouses(w17);
        
        // SetUp: Add Product "WidgetA" to W17
        Product product = new Product();
        product.setName("WidgetA");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        w17.addOccupations(occupation);
        
        // Action: Verify "WidgetA" in W17
        boolean result = w17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_ProductDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        Warehouse w18 = new Warehouse();
        w18.setCity("CityJ");
        w18.setAddress("W18");
        company.addWarehouses(w18);
        
        // SetUp: Add Product "GadgetB" to W18
        Product product = new Product();
        product.setName("GadgetB");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        w18.addOccupations(occupation);
        
        // Action: Verify "ToolC" in W18
        boolean result = w18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        Warehouse w19 = new Warehouse();
        w19.setCity("CityM");
        w19.setAddress("W19");
        company.addWarehouses(w19);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2
        // Note: W18 is in different company (C2), not accessible from current company C1
        Company companyC2 = new Company();
        Warehouse w18 = new Warehouse();
        w18.setCity("CityN");
        w18.setAddress("W18");
        companyC2.addWarehouses(w18);
        
        Product product = new Product();
        product.setName("ItemX");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        w18.addOccupations(occupation);
        
        // Action: Verify "ItemX" in Warehouse "W19" (which doesn't contain ItemX)
        boolean result = w19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        Warehouse w20 = new Warehouse();
        w20.setCity("CityK");
        w20.setAddress("W20");
        company.addWarehouses(w20);
        
        // Action: Verify "PartY" in W20
        boolean result = w20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        Warehouse w21 = new Warehouse();
        w21.setCity("CityL");
        w21.setAddress("W21");
        company.addWarehouses(w21);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        String[] productNames = {"CompA", "CompB", "CompC"};
        for (String name : productNames) {
            Product product = new Product();
            product.setName(name);
            ProductOccupation occupation = new ProductOccupation();
            occupation.setProduct(product);
            w21.addOccupations(occupation);
        }
        
        // Action: Verify "CompB" in W21
        boolean result = w21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue(result);
    }
}