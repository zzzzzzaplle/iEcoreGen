import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR4Test {

    private Company companyC1;
    private Company companyC2;
    private Warehouse w17, w18, w19, w20, w21;

    @Before
    public void setUp() {
        // Initialize companies
        companyC1 = new Company();
        companyC2 = new Company();

        // Initialize warehouses
        w17 = new Warehouse();
        w17.setCity("CityI");
        w17.setAddress("Address17");

        w18 = new Warehouse();
        w18.setCity("CityJ");
        w18.setAddress("Address18");

        w19 = new Warehouse();
        w19.setCity("CityUnknown");
        w19.setAddress("Address19");

        w20 = new Warehouse();
        w20.setCity("CityK");
        w20.setAddress("Address20");

        w21 = new Warehouse();
        w21.setCity("CityL");
        w21.setAddress("Address21");

        // Add warehouses to company C1
        companyC1.addWarehouses(w17);
        companyC1.addWarehouses(w18);
        companyC1.addWarehouses(w19);
        companyC1.addWarehouses(w20);
        companyC1.addWarehouses(w21);
    }

    @Test
    public void testCase1_productExistsInWarehouse() {
        // Setup: Create Warehouse "W17" in "CityI", in company C1. Add Product "WidgetA" to W17.
        Product product = new Product();
        product.setName("WidgetA");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(10.0);
        w17.addOccupations(occupation);

        // Action: Verify "WidgetA" in W17.
        boolean result = w17.containsProduct("WidgetA");

        // Expected Output: true
        assertTrue(result);
    }

    @Test
    public void testCase2_productDoesNotExistInWarehouse() {
        // Setup: Create Warehouse "W18" in "CityJ", in company C1. Add Product "GadgetB" to W18.
        Product product = new Product();
        product.setName("GadgetB");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(15.0);
        w18.addOccupations(occupation);

        // Action: Verify "ToolC" in W18.
        boolean result = w18.containsProduct("ToolC");

        // Expected Output: false
        assertFalse(result);
    }

    @Test
    public void testCase3_nonExistentWarehouse() {
        // Setup: Initialize Warehouse "W19" in company C1. Add Product "ItemX" to "W18" in company C2.
        Warehouse w18InC2 = new Warehouse();
        w18InC2.setCity("CityJ");
        w18InC2.setAddress("Address18");
        companyC2.addWarehouses(w18InC2);

        Product product = new Product();
        product.setName("ItemX");
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(20.0);
        w18InC2.addOccupations(occupation);

        // Action: Verify "ItemX" in Warehouse "W19".
        boolean result = w19.containsProduct("ItemX");

        // Expected Output: false
        assertFalse(result);
    }

    @Test
    public void testCase4_emptyWarehouseCheck() {
        // Setup: Create Warehouse "W20" in "CityK" (no products), in company C1.

        // Action: Verify "PartY" in W20.
        boolean result = w20.containsProduct("PartY");

        // Expected Output: false
        assertFalse(result);
    }

    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // Setup: Create Warehouse "W21" in "CityL", in company C1. Add Products ["CompA", "CompB", "CompC"] to W21.
        List<String> productNames = new ArrayList<>();
        productNames.add("CompA");
        productNames.add("CompB");
        productNames.add("CompC");

        for (String name : productNames) {
            Product product = new Product();
            product.setName(name);
            ProductOccupation occupation = new ProductOccupation();
            occupation.setProduct(product);
            occupation.setVolume(5.0);
            w21.addOccupations(occupation);
        }

        // Action: Verify "CompB" in W21.
        boolean result = w21.containsProduct("CompB");

        // Expected Output: true
        assertTrue(result);
    }
}