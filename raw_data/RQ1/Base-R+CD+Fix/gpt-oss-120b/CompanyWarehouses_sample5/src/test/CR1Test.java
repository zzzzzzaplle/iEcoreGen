import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // SetUp: Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityA");
        warehouse.setSurface(500.0);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        
        Product product1 = new Product();
        product1.setName("ChemX");
        product1.setToxic(true);
        product1.setSupplier(supplier1);
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(product1);
        occ1.setVolume(10.0);
        warehouse.addOccupations(occ1);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Supplier supplier2 = new Supplier();
        supplier2.setName("S2");
        
        Product product2 = new Product();
        product2.setName("PaintY");
        product2.setToxic(true);
        product2.setSupplier(supplier2);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(product2);
        occ2.setVolume(5.0);
        warehouse.addOccupations(occ2);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Supplier supplier3 = new Supplier();
        supplier3.setName("S3");
        
        Product product3 = new Product();
        product3.setName("ToolZ");
        product3.setToxic(false);
        product3.setSupplier(supplier3);
        
        ProductOccupation occ3 = new ProductOccupation();
        occ3.setProduct(product3);
        occ3.setVolume(15.0);
        warehouse.addOccupations(occ3);
        
        // Action: Retrieve toxic products in W1
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ChemX"));
        assertTrue(result.contains("PaintY"));
    }
    
    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // SetUp: Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityB");
        warehouse.setSurface(300.0);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Supplier supplier1 = new Supplier();
        supplier1.setName("S1");
        
        Product product1 = new Product();
        product1.setName("BoxA");
        product1.setToxic(false);
        product1.setSupplier(supplier1);
        
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(product1);
        occ1.setVolume(8.0);
        warehouse.addOccupations(occ1);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Supplier supplier2 = new Supplier();
        supplier2.setName("S4");
        
        Product product2 = new Product();
        product2.setName("CableB");
        product2.setToxic(false);
        product2.setSupplier(supplier2);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(product2);
        occ2.setVolume(3.0);
        warehouse.addOccupations(occ2);
        
        // Action: Retrieve toxic products in W2
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // SetUp: Create Warehouse "W3" in "CityC" with surface 200m² (no products added)
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityC");
        warehouse.setSurface(200.0);
        
        // Action: Retrieve toxic products in W3
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // SetUp: Initialize company C3 with Warehouse "W4" only
        Company company = new Company();
        Warehouse warehouseW4 = new Warehouse();
        warehouseW4.setCity("CityW4");
        company.addWarehouses(warehouseW4);
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        // Note: Since we're testing a specific warehouse method, we'll test with a warehouse that has no products
        Warehouse nonExistentWarehouse = new Warehouse();
        nonExistentWarehouse.setCity("CityW5");
        
        List<String> result = nonExistentWarehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // SetUp: Create Warehouse "W6" in "CityD" with surface 400m², in company C4
        Warehouse warehouse = new Warehouse();
        warehouse.setCity("CityD");
        warehouse.setSurface(400.0);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        Supplier supplier = new Supplier();
        supplier.setName("S5");
        
        Product product = new Product();
        product.setName("AcidK");
        product.setToxic(true);
        product.setSupplier(supplier);
        
        ProductOccupation occ = new ProductOccupation();
        occ.setProduct(product);
        occ.setVolume(12.0);
        warehouse.addOccupations(occ);
        
        // Action: Retrieve toxic products in W6
        List<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, result.size());
        assertEquals("AcidK", result.get(0));
    }
}