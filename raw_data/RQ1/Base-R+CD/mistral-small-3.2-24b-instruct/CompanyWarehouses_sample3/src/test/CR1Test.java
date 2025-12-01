import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CR1Test {
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // Setup
        Company company = new Company();
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityA");
        warehouse.setSurface(500.0);
        
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        Product product1 = new Product();
        product1.setName("ChemX");
        product1.setToxic(true);
        product1.setSupplier(supplier1);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(10.0);
        occupation1.setProduct(product1);
        warehouse.addOccupation(occupation1);
        
        Supplier supplier2 = new Supplier();
        supplier2.setName("S2");
        Product product2 = new Product();
        product2.setName("PaintY");
        product2.setToxic(true);
        product2.setSupplier(supplier2);
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(5.0);
        occupation2.setProduct(product2);
        warehouse.addOccupation(occupation2);
        
        Supplier supplier3 = new Supplier();
        supplier3.setName("S3");
        Product product3 = new Product();
        product3.setName("ToolZ");
        product3.setToxic(false);
        product3.setSupplier(supplier3);
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setVolume(15.0);
        occupation3.setProduct(product3);
        warehouse.addOccupation(occupation3);
        
        company.addWarehouse(warehouse);
        
        // Action
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output
        List<String> expected = new ArrayList<>();
        expected.add("ChemX");
        expected.add("PaintY");
        
        // Assertion
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // Setup
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityB");
        warehouse.setSurface(300.0);
        
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        Product product1 = new Product();
        product1.setName("BoxA");
        product1.setToxic(false);
        product1.setSupplier(supplier1);
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(8.0);
        occupation1.setProduct(product1);
        warehouse.addOccupation(occupation1);
        
        Supplier supplier4 = new Supplier();
        supplier4.setName("S4");
        Product product2 = new Product();
        product2.setName("CableB");
        product2.setToxic(false);
        product2.setSupplier(supplier4);
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(3.0);
        occupation2.setProduct(product2);
        warehouse.addOccupation(occupation2);
        
        // Action
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output
        List<String> expected = new ArrayList<>();
        
        // Assertion
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // Setup
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityC");
        warehouse.setSurface(200.0);
        // No products added - empty warehouse
        
        // Action
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output
        List<String> expected = new ArrayList<>();
        
        // Assertion
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // Setup - Create company with warehouse W4 only
        Company company = new Company();
        Warehouse warehouseW4 = new Warehouse();
        warehouseW4.setCity("SomeCity");
        company.addWarehouse(warehouseW4);
        
        // Action - Try to retrieve toxic products from warehouse W5 (which doesn't exist in company)
        // Since the method is called on a specific warehouse instance, we need to handle this differently
        // We'll create a separate warehouse W5 that's not added to any company
        Warehouse warehouseW5 = new Warehouse();
        warehouseW5.setCity("AnotherCity");
        
        // Action
        List<String> result = warehouseW5.retrieveToxicProductNames();
        
        // Expected Output
        List<String> expected = new ArrayList<>();
        
        // Assertion
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // Setup
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityD");
        warehouse.setSurface(400.0);
        
        Supplier supplier5 = new Supplier();
        supplier5.setName("S5");
        Product product = new Product();
        product.setName("AcidK");
        product.setToxic(true);
        product.setSupplier(supplier5);
        ProductOccupation occupation = new ProductOccupation();
        occupation.setVolume(12.0);
        occupation.setProduct(product);
        warehouse.addOccupation(occupation);
        
        // Action
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output
        List<String> expected = new ArrayList<>();
        expected.add("AcidK");
        
        // Assertion
        assertEquals(expected, result);
    }
}