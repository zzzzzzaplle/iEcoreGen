package edu.company.company3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.company.Company;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.Supplier;
import edu.company.ProductOccupation;
import edu.company.CompanyFactory;

import org.eclipse.emf.common.util.EList;
import java.util.HashSet;
import java.util.Set;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize company for all tests
        company = CompanyFactory.eINSTANCE.createCompany();
    }
    
    @Test
    public void testCase1_WarehouseWithProductsFromMultipleSuppliers() {
        // Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityE");
        warehouse.setAddress("Address for W7");
        warehouse.setSurface(350.0);
        company.getWarehouses().add(warehouse);
        
        // Create Supplier "S6"
        Supplier supplierS6 = CompanyFactory.eINSTANCE.createSupplier();
        supplierS6.setName("S6");
        supplierS6.setAddress("Address S6");
        
        // Create Supplier "S7"
        Supplier supplierS7 = CompanyFactory.eINSTANCE.createSupplier();
        supplierS7.setName("S7");
        supplierS7.setAddress("Address S7");
        
        // Add Product "Item1" from Supplier "S6"
        Product item1 = CompanyFactory.eINSTANCE.createProduct();
        item1.setName("Item1");
        item1.setSupplier(supplierS6);
        ProductOccupation occupation1 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation1.setProduct(item1);
        occupation1.setVolume(10.0);
        warehouse.getOccupations().add(occupation1);
        
        // Add Product "Item2" from Supplier "S7"
        Product item2 = CompanyFactory.eINSTANCE.createProduct();
        item2.setName("Item2");
        item2.setSupplier(supplierS7);
        ProductOccupation occupation2 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation2.setProduct(item2);
        occupation2.setVolume(15.0);
        warehouse.getOccupations().add(occupation2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product item3 = CompanyFactory.eINSTANCE.createProduct();
        item3.setName("Item3");
        item3.setSupplier(supplierS6);
        ProductOccupation occupation3 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation3.setProduct(item3);
        occupation3.setVolume(20.0);
        warehouse.getOccupations().add(occupation3);
        
        // Action: Retrieve unique suppliers for W7
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        
        Set<String> supplierNames = new HashSet<>();
        for (Supplier supplier : result) {
            supplierNames.add(supplier.getName());
        }
        
        assertTrue(supplierNames.contains("S6"));
        assertTrue(supplierNames.contains("S7"));
    }
    
    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityF");
        warehouse.setAddress("Address for W8");
        warehouse.setSurface(250.0);
        company.getWarehouses().add(warehouse);
        
        // Create Supplier "S8"
        Supplier supplierS8 = CompanyFactory.eINSTANCE.createSupplier();
        supplierS8.setName("S8");
        supplierS8.setAddress("Address S8");
        
        // Add Product "Item4" from Supplier "S8"
        Product item4 = CompanyFactory.eINSTANCE.createProduct();
        item4.setName("Item4");
        item4.setSupplier(supplierS8);
        ProductOccupation occupation = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation.setProduct(item4);
        occupation.setVolume(25.0);
        warehouse.getOccupations().add(occupation);
        
        // Action: Retrieve unique suppliers for W8
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_EmptyWarehouse() {
        // Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityG");
        warehouse.setAddress("Address for W9");
        warehouse.setSurface(150.0);
        company.getWarehouses().add(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_NonExistentWarehouse() {
        // Initialize company C1 with no warehouses
        // (Already done in setUp)
        
        // Since we're testing a method on a warehouse object, and there are no warehouses,
        // we need to create a warehouse that's not part of the company to simulate "non-existent"
        // However, looking at the requirement more carefully, it says "Return an empty list if the warehouse contains no products or does not exist"
        // In the context of EMF, a warehouse that doesn't exist would be null or not accessible
        // But since we're calling a method on the warehouse object, we'll test with an empty warehouse not added to company
        
        // Create warehouse but don't add it to company (simulating non-existent in company context)
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityJ");
        warehouse.setAddress("Address for W10");
        warehouse.setSurface(200.0);
        // Note: Not adding to company.getWarehouses()
        
        // Action: Retrieve unique suppliers for "W10" (represented by our warehouse object)
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityH");
        warehouse.setAddress("Address for W11");
        warehouse.setSurface(300.0);
        company.getWarehouses().add(warehouse);
        
        // Create Supplier "S9"
        Supplier supplierS9 = CompanyFactory.eINSTANCE.createSupplier();
        supplierS9.setName("S9");
        supplierS9.setAddress("Address S9");
        
        // Add Product "A1" from Supplier "S9"
        Product productA1 = CompanyFactory.eINSTANCE.createProduct();
        productA1.setName("A1");
        productA1.setSupplier(supplierS9);
        ProductOccupation occupation1 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation1.setProduct(productA1);
        occupation1.setVolume(30.0);
        warehouse.getOccupations().add(occupation1);
        
        // Add Product "A2" from Supplier "S9"
        Product productA2 = CompanyFactory.eINSTANCE.createProduct();
        productA2.setName("A2");
        productA2.setSupplier(supplierS9);
        ProductOccupation occupation2 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation2.setProduct(productA2);
        occupation2.setVolume(35.0);
        warehouse.getOccupations().add(occupation2);
        
        // Action: Retrieve unique suppliers for W11
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}