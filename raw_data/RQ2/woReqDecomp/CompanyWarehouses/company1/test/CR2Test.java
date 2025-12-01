package edu.company.company1.test;

import static org.junit.Assert.*;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;
import edu.company.CompanyFactory;
import edu.company.CompanyPackage;
import edu.company.Company;
import edu.company.Warehouse;
import edu.company.Supplier;
import edu.company.Product;
import edu.company.ProductOccupation;

public class CR2Test {
    
    // Initialize the factory
    private final CompanyFactory factory = CompanyFactory.eINSTANCE;
    
    /**
     * Test Case 1: Warehouse with products from multiple suppliers
     */
    @Test
    public void testCase1_warehouseWithMultipleSuppliers() {
        // SetUp
        Company company = factory.createCompany();
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityE");
        warehouse.setSurface(350.0);
        company.getWarehouses().add(warehouse);
        
        Supplier supplierS6 = factory.createSupplier();
        supplierS6.setName("S6");
        Supplier supplierS7 = factory.createSupplier();
        supplierS7.setName("S7");
        
        Product product1 = factory.createProduct();
        product1.setName("Item1");
        product1.setSupplier(supplierS6);
        
        Product product2 = factory.createProduct();
        product2.setName("Item2");
        product2.setSupplier(supplierS7);
        
        Product product3 = factory.createProduct();
        product3.setName("Item3");
        product3.setSupplier(supplierS6);
        
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(product1);
        warehouse.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(product2);
        warehouse.getOccupations().add(occ2);
        
        ProductOccupation occ3 = factory.createProductOccupation();
        occ3.setProduct(product3);
        warehouse.getOccupations().add(occ3);
        
        // Action: Retrieve unique suppliers
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        assertTrue(containsSupplierNamed(result, "S6"));
        assertTrue(containsSupplierNamed(result, "S7"));
    }
    
    /**
     * Test Case 2: Warehouse with single product
     */
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // SetUp
        Company company = factory.createCompany();
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityF");
        warehouse.setSurface(250.0);
        company.getWarehouses().add(warehouse);
        
        Supplier supplierS8 = factory.createSupplier();
        supplierS8.setName("S8");
        
        Product product = factory.createProduct();
        product.setName("Item4");
        product.setSupplier(supplierS8);
        
        ProductOccupation occ = factory.createProductOccupation();
        occ.setProduct(product);
        warehouse.getOccupations().add(occ);
        
        // Action: Retrieve unique suppliers
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    /**
     * Test Case 3: Empty warehouse
     */
    @Test
    public void testCase3_emptyWarehouse() {
        // SetUp
        Company company = factory.createCompany();
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityG");
        warehouse.setSurface(150.0);
        company.getWarehouses().add(warehouse);
        
        // Action: Retrieve unique suppliers
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    /**
     * Test Case 4: Non-existent warehouse
     */
    @Test
    public void testCase4_nonExistentWarehouse() {
        // SetUp
        Company company = factory.createCompany(); // No warehouses
        
        // Create a warehouse that's not part of the company
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityX");
        warehouse.setSurface(100.0);
        
        // Action: Retrieve unique suppliers for non-existent warehouse
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    /**
     * Test Case 5: Warehouse with products from same supplier
     */
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // SetUp
        Company company = factory.createCompany();
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityH");
        warehouse.setSurface(300.0);
        company.getWarehouses().add(warehouse);
        
        Supplier supplierS9 = factory.createSupplier();
        supplierS9.setName("S9");
        
        Product product1 = factory.createProduct();
        product1.setName("A1");
        product1.setSupplier(supplierS9);
        
        Product product2 = factory.createProduct();
        product2.setName("A2");
        product2.setSupplier(supplierS9);
        
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(product1);
        warehouse.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(product2);
        warehouse.getOccupations().add(occ2);
        
        // Action: Retrieve unique suppliers
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
    
    // Helper method to check if a supplier list contains a supplier by name
    private boolean containsSupplierNamed(EList<Supplier> suppliers, String name) {
        for (Supplier s : suppliers) {
            if (name.equals(s.getName())) {
                return true;
            }
        }
        return false;
    }
}