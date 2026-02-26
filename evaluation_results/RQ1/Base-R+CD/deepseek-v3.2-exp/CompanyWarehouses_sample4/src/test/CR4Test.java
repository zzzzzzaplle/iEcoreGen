import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1. Add Product "WidgetA" to W17.
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityI");
        warehouse.setAddress("W17");
        
        Supplier supplier = new Supplier();
        supplier.setName("Supplier1");
        supplier.setAddress("Address1");
        
        Product product = new Product();
        product.setName("WidgetA");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(10.0);
        
        warehouse.addOccupation(occupation);
        company.addWarehouse(warehouse);
        
        // Action: Verify "WidgetA" in W17
        boolean result = warehouse.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1. Add Product "GadgetB" to W18.
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityJ");
        warehouse.setAddress("W18");
        
        Supplier supplier = new Supplier();
        supplier.setName("Supplier2");
        supplier.setAddress("Address2");
        
        Product product = new Product();
        product.setName("GadgetB");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(15.0);
        
        warehouse.addOccupation(occupation);
        company.addWarehouse(warehouse);
        
        // Action: Verify "ToolC" in W18
        boolean result = warehouse.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1. Add Product "ItemX" to "W18" in company C2.
        Warehouse warehouseW19 = new Warehouse();
        warehouseW19.setCity("CityM");
        warehouseW19.setAddress("W19");
        company.addWarehouse(warehouseW19);
        
        // Create a different warehouse W18 (not added to company) with ItemX
        Warehouse warehouseW18 = new Warehouse();
        warehouseW18.setCity("CityN");
        warehouseW18.setAddress("W18");
        
        Supplier supplier = new Supplier();
        supplier.setName("Supplier3");
        supplier.setAddress("Address3");
        
        Product product = new Product();
        product.setName("ItemX");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(20.0);
        
        warehouseW18.addOccupation(occupation);
        // Note: warehouseW18 is NOT added to the company
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = warehouseW19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1.
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityK");
        warehouse.setAddress("W20");
        company.addWarehouse(warehouse);
        
        // Action: Verify "PartY" in W20
        boolean result = warehouse.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1. Add Products ["CompA", "CompB", "CompC"] to W21.
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityL");
        warehouse.setAddress("W21");
        
        Supplier supplier = new Supplier();
        supplier.setName("Supplier4");
        supplier.setAddress("Address4");
        
        // Add CompA
        Product productA = new Product();
        productA.setName("CompA");
        productA.setSupplier(supplier);
        ProductOccupation occupationA = new ProductOccupation();
        occupationA.setProduct(productA);
        occupationA.setVolume(5.0);
        warehouse.addOccupation(occupationA);
        
        // Add CompB
        Product productB = new Product();
        productB.setName("CompB");
        productB.setSupplier(supplier);
        ProductOccupation occupationB = new ProductOccupation();
        occupationB.setProduct(productB);
        occupationB.setVolume(8.0);
        warehouse.addOccupation(occupationB);
        
        // Add CompC
        Product productC = new Product();
        productC.setName("CompC");
        productC.setSupplier(supplier);
        ProductOccupation occupationC = new ProductOccupation();
        occupationC.setProduct(productC);
        occupationC.setVolume(12.0);
        warehouse.addOccupation(occupationC);
        
        company.addWarehouse(warehouse);
        
        // Action: Verify "CompB" in W21
        boolean result = warehouse.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21 with multiple products", result);
    }
}