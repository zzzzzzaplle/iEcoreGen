import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR1Test {
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // SetUp: Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        Company company = new Company();
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityA");
        warehouse.setSurface(500.0);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(10.0);
        Product product1 = new Product();
        product1.setName("ChemX");
        product1.setToxic(true);
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        product1.setSupplier(supplier1);
        occupation1.setProduct(product1);
        warehouse.addOccupations(occupation1);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(5.0);
        Product product2 = new Product();
        product2.setName("PaintY");
        product2.setToxic(true);
        Supplier supplier2 = new Supplier();
        supplier2.setName("S2");
        product2.setSupplier(supplier2);
        occupation2.setProduct(product2);
        warehouse.addOccupations(occupation2);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        ProductOccupation occupation3 = new ProductOccupation();
        occupation3.setVolume(15.0);
        Product product3 = new Product();
        product3.setName("ToolZ");
        product3.setToxic(false);
        Supplier supplier3 = new Supplier();
        supplier3.setName("S3");
        product3.setSupplier(supplier3);
        occupation3.setProduct(product3);
        warehouse.addOccupations(occupation3);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve toxic products in W1
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        List<String> expected = Arrays.asList("ChemX", "PaintY");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Company company = new Company();
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityB");
        warehouse.setSurface(300.0);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        ProductOccupation occupation1 = new ProductOccupation();
        occupation1.setVolume(8.0);
        Product product1 = new Product();
        product1.setName("BoxA");
        product1.setToxic(false);
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        product1.setSupplier(supplier1);
        occupation1.setProduct(product1);
        warehouse.addOccupations(occupation1);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        ProductOccupation occupation2 = new ProductOccupation();
        occupation2.setVolume(3.0);
        Product product2 = new Product();
        product2.setName("CableB");
        product2.setToxic(false);
        Supplier supplier2 = new Supplier();
        supplier2.setName("S4");
        product2.setSupplier(supplier2);
        occupation2.setProduct(product2);
        warehouse.addOccupations(occupation2);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve toxic products in W2
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added), in company C3
        Company company = new Company();
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityC");
        warehouse.setSurface(200.0);
        company.addWarehouses(warehouse);
        
        // Action: Retrieve toxic products in W3
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Company company = new Company();
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityW4");
        warehouse.setSurface(250.0);
        company.addWarehouses(warehouse);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        // Since we're testing Warehouse.retrieveToxicProductNames(), we need to create a new warehouse W5
        Warehouse warehouseW5 = new Warehouse();
        warehouseW5.setCity("CityW5");
        warehouseW5.setSurface(300.0);
        
        List<String> result = warehouseW5.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Company company = new Company();
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityD");
        warehouse.setSurface(400.0);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        ProductOccupation occupation = new ProductOccupation();
        occupation.setVolume(12.0);
        Product product = new Product();
        product.setName("AcidK");
        product.setToxic(true);
        Supplier supplier = new Supplier();
        supplier.setName("S5");
        product.setSupplier(supplier);
        occupation.setProduct(product);
        warehouse.addOccupations(occupation);
        
        company.addWarehouses(warehouse);
        
        // Action: Retrieve toxic products in W6
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        List<String> expected = Arrays.asList("AcidK");
        assertEquals(expected, result);
    }
}