import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // Create Warehouse "W7" in "CityE" with surface 350m²
        Warehouse w7 = new Warehouse();
        w7.setCity("CityE");
        w7.setSurface(350.0);
        
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
        List<Supplier> uniqueSuppliers = w7.retrieveUniqueSuppliers();
        
        // Verify we have 2 unique suppliers
        assertEquals(2, uniqueSuppliers.size());
        
        // Extract supplier names for easier verification
        boolean hasS6 = false;
        boolean hasS7 = false;
        for (Supplier supplier : uniqueSuppliers) {
            if ("S6".equals(supplier.getName())) hasS6 = true;
            if ("S7".equals(supplier.getName())) hasS7 = true;
        }
        
        assertTrue(hasS6);
        assertTrue(hasS7);
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // Create Warehouse "W8" in "CityF" with surface 250m²
        Warehouse w8 = new Warehouse();
        w8.setCity("CityF");
        w8.setSurface(250.0);
        
        // Create supplier
        Supplier s8 = new Supplier();
        s8.setName("S8");
        
        // Create product
        Product item4 = new Product();
        item4.setName("Item4");
        item4.setSupplier(s8);
        
        // Create occupation
        ProductOccupation occ = new ProductOccupation();
        occ.setProduct(item4);
        occ.setVolume(25.0);
        
        // Add occupation to warehouse
        w8.addOccupations(occ);
        
        // Add warehouse to company
        company.addWarehouses(w8);
        
        // Retrieve unique suppliers for W8
        List<Supplier> uniqueSuppliers = w8.retrieveUniqueSuppliers();
        
        // Verify we have 1 unique supplier
        assertEquals(1, uniqueSuppliers.size());
        assertEquals("S8", uniqueSuppliers.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // Create Warehouse "W9" in "CityG" with surface 150m² (no products)
        Warehouse w9 = new Warehouse();
        w9.setCity("CityG");
        w9.setSurface(150.0);
        
        // Add warehouse to company
        company.addWarehouses(w9);
        
        // Retrieve unique suppliers for W9
        List<Supplier> uniqueSuppliers = w9.retrieveUniqueSuppliers();
        
        // Verify we have empty list
        assertEquals(0, uniqueSuppliers.size());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // Initialize company C1 with no warehouses
        // Already done in setUp()
        
        // Since we can't access a non-existent warehouse directly,
        // we create a new warehouse instance but don't add it to the company
        Warehouse w10 = new Warehouse();
        w10.setCity("CityJ");
        w10.setSurface(200.0);
        
        // Retrieve unique suppliers for W10 (which has no products)
        List<Supplier> uniqueSuppliers = w10.retrieveUniqueSuppliers();
        
        // Verify we have empty list
        assertEquals(0, uniqueSuppliers.size());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // Create Warehouse "W11" in "CityH" with surface 300m²
        Warehouse w11 = new Warehouse();
        w11.setCity("CityH");
        w11.setSurface(300.0);
        
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
        
        // Create occupations
        ProductOccupation occ1 = new ProductOccupation();
        occ1.setProduct(a1);
        occ1.setVolume(30.0);
        
        ProductOccupation occ2 = new ProductOccupation();
        occ2.setProduct(a2);
        occ2.setVolume(40.0);
        
        // Add occupations to warehouse
        w11.addOccupations(occ1);
        w11.addOccupations(occ2);
        
        // Add warehouse to company
        company.addWarehouses(w11);
        
        // Retrieve unique suppliers for W11
        List<Supplier> uniqueSuppliers = w11.retrieveUniqueSuppliers();
        
        // Verify we have 1 unique supplier
        assertEquals(1, uniqueSuppliers.size());
        assertEquals("S9", uniqueSuppliers.get(0).getName());
    }
}