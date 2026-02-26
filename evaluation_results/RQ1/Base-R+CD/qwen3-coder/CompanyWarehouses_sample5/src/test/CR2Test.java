import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_WarehouseWithProductsFromMultipleSuppliers() {
        // Create suppliers
        Supplier s6 = new Supplier();
        s6.setName("S6");
        Supplier s7 = new Supplier();
        s7.setName("S7");
        
        // Create products
        Product item1 = new Product();
        item1.setName("Item1");
        item1.setSupplier(s6);
        
        Product item2 = new Product();
        item2.setName("Item2");
        item2.setSupplier(s7);
        
        Product item3 = new Product();
        item3.setName("Item3");
        item3.setSupplier(s6); // duplicate supplier
        
        // Create warehouse W7
        Warehouse w7 = new Warehouse();
        w7.setCity("CityE");
        w7.setSurface(350.0);
        
        // Add product occupations to warehouse
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(item1);
        po1.setVolume(10.0);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(item2);
        po2.setVolume(20.0);
        
        ProductOccupation po3 = new ProductOccupation();
        po3.setProduct(item3);
        po3.setVolume(15.0);
        
        w7.addOccupation(po1);
        w7.addOccupation(po2);
        w7.addOccupation(po3);
        
        // Add warehouse to company
        company.addWarehouse(w7);
        
        // Action: Retrieve unique suppliers for W7
        List<Supplier> result = w7.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        
        List<String> supplierNames = new ArrayList<>();
        for (Supplier supplier : result) {
            supplierNames.add(supplier.getName());
        }
        Collections.sort(supplierNames);
        assertEquals("S6", supplierNames.get(0));
        assertEquals("S7", supplierNames.get(1));
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // Create supplier
        Supplier s8 = new Supplier();
        s8.setName("S8");
        
        // Create product
        Product item4 = new Product();
        item4.setName("Item4");
        item4.setSupplier(s8);
        
        // Create warehouse W8
        Warehouse w8 = new Warehouse();
        w8.setCity("CityF");
        w8.setSurface(250.0);
        
        // Add product occupation to warehouse
        ProductOccupation po4 = new ProductOccupation();
        po4.setProduct(item4);
        po4.setVolume(25.0);
        
        w8.addOccupation(po4);
        
        // Add warehouse to company
        company.addWarehouse(w8);
        
        // Action: Retrieve unique suppliers for W8
        List<Supplier> result = w8.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // Create warehouse W9 with no products
        Warehouse w9 = new Warehouse();
        w9.setCity("CityG");
        w9.setSurface(150.0);
        
        // Add warehouse to company
        company.addWarehouse(w9);
        
        // Action: Retrieve unique suppliers for W9
        List<Supplier> result = w9.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // Initialize company C1 with no warehouses
        company = new Company();
        
        // Since we're testing a method on Warehouse, we need to create a warehouse object
        // but don't add it to the company (simulating non-existent in company context)
        Warehouse w10 = new Warehouse();
        
        // Action: Retrieve unique suppliers for W10
        List<Supplier> result = w10.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // Create supplier
        Supplier s9 = new Supplier();
        s9.setName("S9");
        
        // Create products
        Product a1 = new Product();
        a1.setName("A1");
        a1.setSupplier(s9);
        
        Product a2 = new Product();
        a2.setName("A2");
        a2.setSupplier(s9);
        
        // Create warehouse W11
        Warehouse w11 = new Warehouse();
        w11.setCity("CityH");
        w11.setSurface(300.0);
        
        // Add product occupations to warehouse
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(a1);
        po1.setVolume(30.0);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(a2);
        po2.setVolume(40.0);
        
        w11.addOccupation(po1);
        w11.addOccupation(po2);
        
        // Add warehouse to company
        company.addWarehouse(w11);
        
        // Action: Retrieve unique suppliers for W11
        List<Supplier> result = w11.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}