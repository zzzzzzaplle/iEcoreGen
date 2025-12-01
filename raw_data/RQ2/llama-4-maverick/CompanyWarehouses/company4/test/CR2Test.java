package edu.company.company4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.ProductOccupation;
import edu.company.Supplier;
import org.eclipse.emf.common.util.EList;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = CompanyFactory.eINSTANCE.createCompany();
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // SetUp:
        // 1. Create Warehouse "W7" in "CityE" with surface 350m², in company C1.
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityE");
        warehouse.setAddress("W7 Address");
        warehouse.setSurface(350.0);
        company.getWarehouses().add(warehouse);
        
        // 2. Add Product "Item1" from Supplier "S6".
        Supplier supplier1 = CompanyFactory.eINSTANCE.createSupplier();
        supplier1.setName("S6");
        supplier1.setAddress("S6 Address");
        
        Product product1 = CompanyFactory.eINSTANCE.createProduct();
        product1.setName("Item1");
        product1.setToxic(false);
        product1.setSupplier(supplier1);
        
        ProductOccupation occupation1 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(10.0);
        warehouse.getOccupations().add(occupation1);
        
        // 3. Add Product "Item2" from Supplier "S7".
        Supplier supplier2 = CompanyFactory.eINSTANCE.createSupplier();
        supplier2.setName("S7");
        supplier2.setAddress("S7 Address");
        
        Product product2 = CompanyFactory.eINSTANCE.createProduct();
        product2.setName("Item2");
        product2.setToxic(false);
        product2.setSupplier(supplier2);
        
        ProductOccupation occupation2 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(15.0);
        warehouse.getOccupations().add(occupation2);
        
        // 4. Add Product "Item3" from Supplier "S6" (duplicate supplier).
        Product product3 = CompanyFactory.eINSTANCE.createProduct();
        product3.setName("Item3");
        product3.setToxic(false);
        product3.setSupplier(supplier1); // Same supplier as product1
        
        ProductOccupation occupation3 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(20.0);
        warehouse.getOccupations().add(occupation3);
        
        // Action: Retrieve unique suppliers for W7.
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        
        boolean foundS6 = false;
        boolean foundS7 = false;
        for (Supplier supplier : result) {
            if ("S6".equals(supplier.getName())) {
                foundS6 = true;
            } else if ("S7".equals(supplier.getName())) {
                foundS7 = true;
            }
        }
        assertTrue("Should contain S6", foundS6);
        assertTrue("Should contain S7", foundS7);
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp:
        // 1. Create Warehouse "W8" in "CityF" with surface 250m², in company C1.
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityF");
        warehouse.setAddress("W8 Address");
        warehouse.setSurface(250.0);
        company.getWarehouses().add(warehouse);
        
        // 2. Add Product "Item4" from Supplier "S8".
        Supplier supplier = CompanyFactory.eINSTANCE.createSupplier();
        supplier.setName("S8");
        supplier.setAddress("S8 Address");
        
        Product product = CompanyFactory.eINSTANCE.createProduct();
        product.setName("Item4");
        product.setToxic(false);
        product.setSupplier(supplier);
        
        ProductOccupation occupation = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(25.0);
        warehouse.getOccupations().add(occupation);
        
        // Action: Retrieve unique suppliers for W8.
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1.
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityG");
        warehouse.setAddress("W9 Address");
        warehouse.setSurface(150.0);
        company.getWarehouses().add(warehouse);
        
        // 2. Action: Retrieve unique suppliers for W9.
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp:
        // 1. Initialize company C1 with no warehouses.
        // (Already done in setUp - company has no warehouses)
        
        // Since we cannot directly retrieve a warehouse that doesn't exist,
        // we interpret this as trying to get suppliers from a warehouse not in the company
        // We'll create a warehouse but not add it to the company
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityJ");
        warehouse.setAddress("W10 Address");
        warehouse.setSurface(200.0);
        // Note: Not adding to company.getWarehouses()
        
        // Action: Retrieve unique suppliers for "W10" (the unattached warehouse).
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp:
        // 1. Create Warehouse "W11" in "CityH" with surface 300m², in company C1.
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityH");
        warehouse.setAddress("W11 Address");
        warehouse.setSurface(300.0);
        company.getWarehouses().add(warehouse);
        
        // 2. Add Product "A1" from Supplier "S9".
        Supplier supplier = CompanyFactory.eINSTANCE.createSupplier();
        supplier.setName("S9");
        supplier.setAddress("S9 Address");
        
        Product product1 = CompanyFactory.eINSTANCE.createProduct();
        product1.setName("A1");
        product1.setToxic(false);
        product1.setSupplier(supplier);
        
        ProductOccupation occupation1 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(30.0);
        warehouse.getOccupations().add(occupation1);
        
        // 3. Add Product "A2" from Supplier "S9".
        Product product2 = CompanyFactory.eINSTANCE.createProduct();
        product2.setName("A2");
        product2.setToxic(true);
        product2.setSupplier(supplier); // Same supplier
        
        ProductOccupation occupation2 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(40.0);
        warehouse.getOccupations().add(occupation2);
        
        // Action: Retrieve unique suppliers for W11.
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}