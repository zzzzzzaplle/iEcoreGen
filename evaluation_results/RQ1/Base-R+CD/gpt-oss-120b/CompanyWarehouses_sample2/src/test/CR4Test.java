import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import com.example.warehouse.Warehouse;
import com.example.warehouse.Product;
import com.example.warehouse.ProductOccupation;
import com.example.warehouse.Company;

public class CR4Test {
    
    private Company companyC1;
    private Company companyC2;
    
    @Before
    public void setUp() {
        // Initialize companies for test cases
        companyC1 = new Company();
        companyC2 = new Company();
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1
        Warehouse warehouseW17 = new Warehouse();
        warehouseW17.setCity("CityI");
        companyC1.addWarehouses(warehouseW17);
        
        // SetUp: Add Product "WidgetA" to W17
        Product productWidgetA = new Product();
        productWidgetA.setName("WidgetA");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(productWidgetA);
        warehouseW17.addOccupations(occupation);
        
        // Action: Verify "WidgetA" in W17
        boolean result = warehouseW17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product 'WidgetA' should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_ProductDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1
        Warehouse warehouseW18 = new Warehouse();
        warehouseW18.setCity("CityJ");
        companyC1.addWarehouses(warehouseW18);
        
        // SetUp: Add Product "GadgetB" to W18
        Product productGadgetB = new Product();
        productGadgetB.setName("GadgetB");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(productGadgetB);
        warehouseW18.addOccupations(occupation);
        
        // Action: Verify "ToolC" in W18
        boolean result = warehouseW18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product 'ToolC' should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1
        Warehouse warehouseW19 = new Warehouse();
        companyC1.addWarehouses(warehouseW19);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2
        Warehouse warehouseW18 = new Warehouse();
        companyC2.addWarehouses(warehouseW18);
        Product productItemX = new Product();
        productItemX.setName("ItemX");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(productItemX);
        warehouseW18.addOccupations(occupation);
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = warehouseW19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product 'ItemX' should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1
        Warehouse warehouseW20 = new Warehouse();
        warehouseW20.setCity("CityK");
        companyC1.addWarehouses(warehouseW20);
        
        // Action: Verify "PartY" in W20
        boolean result = warehouseW20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product 'PartY' should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1
        Warehouse warehouseW21 = new Warehouse();
        warehouseW21.setCity("CityL");
        companyC1.addWarehouses(warehouseW21);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21
        String[] productNames = {"CompA", "CompB", "CompC"};
        for (String productName : productNames) {
            Product product = new Product();
            product.setName(productName);
            ProductOccupation occupation = new ProductOccupation();
            occupation.setProduct(product);
            warehouseW21.addOccupations(occupation);
        }
        
        // Action: Verify "CompB" in W21
        boolean result = warehouseW21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product 'CompB' should exist in warehouse W21", result);
    }
}