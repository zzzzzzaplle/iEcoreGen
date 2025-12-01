import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        item3.setSupplier(s6); // Same supplier as item1
        
        // Create product occupations
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(item1);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(item2);
        
        ProductOccupation po3 = new ProductOccupation();
        po3.setProduct(item3);
        
        // Add occupations to warehouse
        w7.addOccupations(po1);
        w7.addOccupations(po2);
        w7.addOccupations(po3);
        
        // Add warehouse to company
        company.addWarehouses(w7);
        
        // Retrieve unique suppliers for W7
        List<Supplier> uniqueSuppliers = w7.retrieveUniqueSuppliers();
        
        // Verify we have 2 unique suppliers
        assertEquals(2, uniqueSuppliers.size());
        
        // Extract supplier names for easier comparison
        List<String> supplierNames = new ArrayList<>();
        for (Supplier supplier : uniqueSuppliers) {
            supplierNames.add(supplier.getName());
        }
        
        // Check that both S6 and S7 are present
        assertTrue(supplierNames.contains("S6"));
        assertTrue(supplierNames.contains("S7"));
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
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
        
        // Create product occupation
        ProductOccupation po4 = new ProductOccupation();
        po4.setProduct(item4);
        
        // Add occupation to warehouse
        w8.addOccupations(po4);
        
        // Add warehouse to company
        company.addWarehouses(w8);
        
        // Retrieve unique suppliers for W8
        List<Supplier> uniqueSuppliers = w8.retrieveUniqueSuppliers();
        
        // Verify we have 1 unique supplier
        assertEquals(1, uniqueSuppliers.size());
        
        // Check that S8 is present
        assertEquals("S8", uniqueSuppliers.get(0).getName());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // Create Warehouse "W9" in "CityG" with surface 150m² (no products)
        Warehouse w9 = new Warehouse();
        w9.setCity("CityG");
        w9.setSurface(150.0);
        
        // Add warehouse to company
        company.addWarehouses(w9);
        
        // Retrieve unique suppliers for W9
        List<Supplier> uniqueSuppliers = w9.retrieveUniqueSuppliers();
        
        // Verify we have an empty list
        assertEquals(0, uniqueSuppliers.size());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // Initialize company C1 with no warehouses
        // This is already done in setUp()
        
        // Since we're testing the method on a warehouse that doesn't exist in the company,
        // but the method is called on a warehouse object, we need to create a warehouse
        // but not add it to the company
        Warehouse w10 = new Warehouse();
        
        // Retrieve unique suppliers for W10
        List<Supplier> uniqueSuppliers = w10.retrieveUniqueSuppliers();
        
        // Verify we have an empty list
        assertEquals(0, uniqueSuppliers.size());
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
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
        
        // Create product occupations
        ProductOccupation po1 = new ProductOccupation();
        po1.setProduct(a1);
        
        ProductOccupation po2 = new ProductOccupation();
        po2.setProduct(a2);
        
        // Add occupations to warehouse
        w11.addOccupations(po1);
        w11.addOccupations(po2);
        
        // Add warehouse to company
        company.addWarehouses(w11);
        
        // Retrieve unique suppliers for W11
        List<Supplier> uniqueSuppliers = w11.retrieveUniqueSuppliers();
        
        // Verify we have 1 unique supplier (even though there are 2 products)
        assertEquals(1, uniqueSuppliers.size());
        
        // Check that S9 is present
        assertEquals("S9", uniqueSuppliers.get(0).getName());
    }
}