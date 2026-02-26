import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    
    private Warehouse warehouse;
    private Product product;
    private ProductOccupation occupation;
    
    @Before
    public void setUp() {
        warehouse = new Warehouse();
        product = new Product();
        occupation = new ProductOccupation();
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W17" in "CityI", in company C1.
        warehouse.setCity("CityI");
        warehouse.setAddress("W17");
        
        // 2. Add Product "WidgetA" to W17.
        product.setName("WidgetA");
        occupation.setProduct(product);
        warehouse.addOccupation(occupation);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = warehouse.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W18" in "CityJ", in company C1.
        warehouse.setCity("CityJ");
        warehouse.setAddress("W18");
        
        // 2. Add Product "GadgetB" to W18.
        product.setName("GadgetB");
        occupation.setProduct(product);
        warehouse.addOccupation(occupation);
        
        // Action: Verify "ToolC" in W18.
        boolean result = warehouse.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp:
        // 1. Initialize Warehouse "W19" in company C1.
        warehouse.setCity("CityM");
        warehouse.setAddress("W19");
        // (Warehouse exists but has no products)
        
        // 2. Add Product "ItemX" to "W18" in company C2.
        // (This is just context; ItemX is not in W19)
        
        // Action: Verify "ItemX" in Warehouse "W19".
        boolean result = warehouse.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp:
        // 1. Create Warehouse "W20" in "CityK" (no products), in company C1.
        warehouse.setCity("CityK");
        warehouse.setAddress("W20");
        // Warehouse is initialized with empty occupations list
        
        // Action: Verify "PartY" in W20.
        boolean result = warehouse.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W21" in "CityL", in company C1.
        warehouse.setCity("CityL");
        warehouse.setAddress("W21");
        
        // 2. Add Products ["CompA", "CompB", "CompC"] to W21.
        List<ProductOccupation> occupations = new ArrayList<>();
        
        Product productA = new Product();
        productA.setName("CompA");
        ProductOccupation occupationA = new ProductOccupation();
        occupationA.setProduct(productA);
        occupations.add(occupationA);
        
        Product productB = new Product();
        productB.setName("CompB");
        ProductOccupation occupationB = new ProductOccupation();
        occupationB.setProduct(productB);
        occupations.add(occupationB);
        
        Product productC = new Product();
        productC.setName("CompC");
        ProductOccupation occupationC = new ProductOccupation();
        occupationC.setProduct(productC);
        occupations.add(occupationC);
        
        warehouse.setOccupations(occupations);
        
        // Action: Verify "CompB" in W21.
        boolean result = warehouse.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue(result);
    }
}