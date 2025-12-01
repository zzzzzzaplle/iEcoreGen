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
        // Create Warehouse "W7" in "CityE" with surface 350m²
        Warehouse w7 = new Warehouse();
        w7.setCity("CityE");
        w7.setAddress("AddressW7");
        w7.setSurface(350.0);
        
        // Create suppliers
        Supplier s6 = new Supplier();
        s6.setName("S6");
        s6.setAddress("AddressS6");
        
        Supplier s7 = new Supplier();
        s7.setName("S7");
        s7.setAddress("AddressS7");
        
        // Create products
        Product item1 = new Product();
        item1.setName("Item1");
        item1.setSupplier(s6);
        
        Product item2 = new Product();
        item2.setName("Item2");
        item2.setSupplier(s7);
        
        Product item3 = new Product();
        item3.setName("Item3");
        item3.setSupplier(s6); // Same supplier as item1
        
        // Create occupations
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(item1);
        occ1.setVolume(10.0);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(item2);
        occ2.setVolume(20.0);
        
        ProductOccupation occ3 = new ProductOccupation();
        occ3.setProduct(item3);
        occ3.setVolume(15.0);
        
        // Add occupations to warehouse
        w7.addOccupations(occ1);
        w7.addOccupations(occ2);
        w7.addOccupations(occ3);
        
        // Add warehouse to company
        company.addWarehouses(w7);
        
        // Retrieve unique suppliers for W7
        List<Supplier> result = w7.retrieveUniqueSuppliers();
        
        // Extract supplier names for comparison
        List<String> supplierNames = new ArrayList<>();
        for (Supplier supplier : result) {
            supplierNames.add(supplier.getName());
        }
        
        // Sort for consistent comparison
        Collections.sort(supplierNames);
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        assertTrue(supplierNames.contains("S6"));
        assertTrue(supplierNames.contains("S7"));
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // Create Warehouse "W8" in "CityF" with surface 250m²
        Warehouse w8 = new Warehouse();
        w8.setCity("CityF");
        w8.setAddress("AddressW8");
        w8.setSurface(250.0);
        
        // Create supplier
        Supplier s8 = new Supplier();
        s8.setName("S8");
        s8.setAddress("AddressS8");
        
        // Create product
        Product item4 = new Product();
        item4.setName("Item4");
        item4.setSupplier(s8);
        
        // Create occupation
        ProductOccupation occ4 = new ProductOccupation();
        occ4.setProduct(item4);
        occ4.setVolume(25.0);
        
        // Add occupation to warehouse
        w8.addOccupations(occ4);
        
        // Add warehouse to company
        company.addWarehouses(w8);
        
        // Retrieve unique suppliers for W8
        List<Supplier> result = w8.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // Create Warehouse "W9" in "CityG" with surface 150m² (no products)
        Warehouse w9 = new Warehouse();
        w9.setCity("CityG");
        w9.setAddress("AddressW9");
        w9.setSurface(150.0);
        // No products added
        
        // Add warehouse to company
        company.addWarehouses(w9);
        
        // Retrieve unique suppliers for W9
        List<Supplier> result = w9.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // Initialize company C1 with no warehouses
        // Already done in setUp()
        
        // Since we're testing a method on Warehouse, we need to create a warehouse
        // but don't add it to the company (simulate non-existent in company context)
        Warehouse w10 = new Warehouse();
        w10.setCity("CityI");
        w10.setAddress("AddressW10");
        w10.setSurface(200.0);
        // Don't add to company and don't add any products
        
        // Retrieve unique suppliers for W10
        List<Supplier> result = w10.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // Create Warehouse "W11" in "CityH" with surface 300m²
        Warehouse w11 = new Warehouse();
        w11.setCity("CityH");
        w11.setAddress("AddressW11");
        w11.setSurface(300.0);
        
        // Create supplier
        Supplier s9 = new Supplier();
        s9.setName("S9");
        s9.setAddress("AddressS9");
        
        // Create products
        Product a1 = new Product();
        a1.setName("A1");
        a1.setSupplier(s9);
        
        Product a2 = new Product();
        a2.setName("A2");
        a2.setSupplier(s9);
        
        // Create occupations
        ProductOccupation occA1 = new ProductOccupation();
        occA1.setProduct(a1);
        occA1.setVolume(30.0);
        
        ProductOccupation occA2 = new ProductOccupation();
        occA2.setProduct(a2);
        occA2.setVolume(40.0);
        
        // Add occupations to warehouse
        w11.addOccupations(occA1);
        w11.addOccupations(occA2);
        
        // Add warehouse to company
        company.addWarehouses(w11);
        
        // Retrieve unique suppliers for W11
        List<Supplier> result = w11.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}