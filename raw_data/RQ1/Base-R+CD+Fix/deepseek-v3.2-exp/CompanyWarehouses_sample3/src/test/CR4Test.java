import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_ProductExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1.
        Warehouse w17 = new Warehouse();
        w17.setCity("CityI");
        w17.setAddress("W17");
        company.addWarehouse(w17);
        
        // SetUp: Add Product "WidgetA" to W17.
        Product widgetA = new Product();
        widgetA.setName("WidgetA");
        Supplier supplier = new Supplier();
        supplier.setName("SupplierA");
        supplier.setAddress("AddressA");
        widgetA.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(widgetA);
        occupation.setVolume(100.0);
        w17.addOccupation(occupation);
        
        // Action: Verify "WidgetA" in W17.
        boolean result = w17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_ProductDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1.
        Warehouse w18 = new Warehouse();
        w18.setCity("CityJ");
        w18.setAddress("W18");
        company.addWarehouse(w18);
        
        // SetUp: Add Product "GadgetB" to W18.
        Product gadgetB = new Product();
        gadgetB.setName("GadgetB");
        Supplier supplier = new Supplier();
        supplier.setName("SupplierB");
        supplier.setAddress("AddressB");
        gadgetB.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(gadgetB);
        occupation.setVolume(150.0);
        w18.addOccupation(occupation);
        
        // Action: Verify "ToolC" in W18.
        boolean result = w18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_NonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1.
        Warehouse w19 = new Warehouse();
        w19.setCity("CityM");
        w19.setAddress("W19");
        company.addWarehouse(w19);
        
        // SetUp: Add Product "ItemX" to "W18" in company C2.
        // Note: W18 is in different company (C2), so W19 (in C1) should not contain ItemX
        Company company2 = new Company();
        Warehouse w18 = new Warehouse();
        w18.setCity("CityN");
        w18.setAddress("W18");
        company2.addWarehouse(w18);
        
        Product itemX = new Product();
        itemX.setName("ItemX");
        Supplier supplier = new Supplier();
        supplier.setName("SupplierX");
        supplier.setAddress("AddressX");
        itemX.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(itemX);
        occupation.setVolume(200.0);
        w18.addOccupation(occupation);
        
        // Action: Verify "ItemX" in Warehouse "W19".
        boolean result = w19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_EmptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1.
        Warehouse w20 = new Warehouse();
        w20.setCity("CityK");
        w20.setAddress("W20");
        company.addWarehouse(w20);
        
        // Action: Verify "PartY" in W20.
        boolean result = w20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_MultipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1.
        Warehouse w21 = new Warehouse();
        w21.setCity("CityL");
        w21.setAddress("W21");
        company.addWarehouse(w21);
        
        // SetUp: Add Products ["CompA", "CompB", "CompC"] to W21.
        Supplier supplier = new Supplier();
        supplier.setName("SupplierComp");
        supplier.setAddress("AddressComp");
        
        // Add CompA
        Product compA = new Product();
        compA.setName("CompA");
        compA.setSupplier(supplier);
        ProductOccupation occupationA = new ProductOccupation();
        occupationA.setProduct(compA);
        occupationA.setVolume(50.0);
        w21.addOccupation(occupationA);
        
        // Add CompB
        Product compB = new Product();
        compB.setName("CompB");
        compB.setSupplier(supplier);
        ProductOccupation occupationB = new ProductOccupation();
        occupationB.setProduct(compB);
        occupationB.setVolume(75.0);
        w21.addOccupation(occupationB);
        
        // Add CompC
        Product compC = new Product();
        compC.setName("CompC");
        compC.setSupplier(supplier);
        ProductOccupation occupationC = new ProductOccupation();
        occupationC.setProduct(compC);
        occupationC.setVolume(100.0);
        w21.addOccupation(occupationC);
        
        // Action: Verify "CompB" in W21.
        boolean result = w21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}