import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // SetUp: Create Warehouse "W17" in "CityI", in company C1. Add Product "WidgetA" to W17.
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityI");
        warehouse.setAddress("W17");
        
        Supplier supplier = new Supplier();
        supplier.setName("SupplierA");
        
        Product product = new Product();
        product.setName("WidgetA");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(100.0);
        
        warehouse.addOccupations(occupation);
        company.addWarehouses(warehouse);
        
        // Action: Verify "WidgetA" in W17
        boolean result = warehouse.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue("Product WidgetA should exist in warehouse W17", result);
    }
    
    @Test
    public void testCase2_productDoesNotExistInWarehouse() {
        // SetUp: Create Warehouse "W18" in "CityJ", in company C1. Add Product "GadgetB" to W18.
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityJ");
        warehouse.setAddress("W18");
        
        Supplier supplier = new Supplier();
        supplier.setName("SupplierB");
        
        Product product = new Product();
        product.setName("GadgetB");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(150.0);
        
        warehouse.addOccupations(occupation);
        company.addWarehouses(warehouse);
        
        // Action: Verify "ToolC" in W18
        boolean result = warehouse.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse("Product ToolC should not exist in warehouse W18", result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // SetUp: Initialize Warehouse "W19" in company C1. 
        // Add Product "ItemX" to "W18" in company C2.
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setAddress("W19");
        company.addWarehouses(warehouse1);
        
        // Create a different warehouse (W18) that's not added to the company
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setAddress("W18");
        
        Supplier supplier = new Supplier();
        supplier.setName("SupplierC");
        
        Product product = new Product();
        product.setName("ItemX");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = new ProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(200.0);
        
        warehouse2.addOccupations(occupation);
        // Note: warehouse2 is NOT added to company
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = warehouse1.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse("Product ItemX should not exist in warehouse W19", result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // SetUp: Create Warehouse "W20" in "CityK" (no products), in company C1.
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityK");
        warehouse.setAddress("W20");
        company.addWarehouses(warehouse);
        
        // Action: Verify "PartY" in W20
        boolean result = warehouse.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse("Product PartY should not exist in empty warehouse W20", result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // SetUp: Create Warehouse "W21" in "CityL", in company C1. 
        // Add Products ["CompA", "CompB", "CompC"] to W21.
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityL");
        warehouse.setAddress("W21");
        
        Supplier supplier = new Supplier();
        supplier.setName("SupplierD");
        
        // Add Product CompA
        Product product1 = new Product();
        product1.setName("CompA");
        product1.setSupplier(supplier);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(50.0);
        warehouse.addOccupations(occupation1);
        
        // Add Product CompB
        Product product2 = new Product();
        product2.setName("CompB");
        product2.setSupplier(supplier);
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(75.0);
        warehouse.addOccupations(occupation2);
        
        // Add Product CompC
        Product product3 = new Product();
        product3.setName("CompC");
        product3.setSupplier(supplier);
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(100.0);
        warehouse.addOccupations(occupation3);
        
        company.addWarehouses(warehouse);
        
        // Action: Verify "CompB" in W21
        boolean result = warehouse.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue("Product CompB should exist in warehouse W21", result);
    }
}