package edu.company.company2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.Supplier;
import edu.company.ProductOccupation;
import org.eclipse.emf.common.util.EList;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = CompanyFactory.eINSTANCE.createCompany();
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityE");
        warehouse.setAddress("AddressW7");
        warehouse.setSurface(350.0);
        company.getWarehouses().add(warehouse);
        
        // Add Product "Item1" from Supplier "S6"
        Supplier supplier1 = CompanyFactory.eINSTANCE.createSupplier();
        supplier1.setName("S6");
        supplier1.setAddress("AddressS6");
        
        Product product1 = CompanyFactory.eINSTANCE.createProduct();
        product1.setName("Item1");
        product1.setToxic(false);
        product1.setSupplier(supplier1);
        
        ProductOccupation occupation1 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(10.0);
        warehouse.getOccupations().add(occupation1);
        
        // Add Product "Item2" from Supplier "S7"
        Supplier supplier2 = CompanyFactory.eINSTANCE.createSupplier();
        supplier2.setName("S7");
        supplier2.setAddress("AddressS7");
        
        Product product2 = CompanyFactory.eINSTANCE.createProduct();
        product2.setName("Item2");
        product2.setToxic(false);
        product2.setSupplier(supplier2);
        
        ProductOccupation occupation2 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(15.0);
        warehouse.getOccupations().add(occupation2);
        
        // Add Product "Item3" from Supplier "S6" (duplicate supplier)
        Product product3 = CompanyFactory.eINSTANCE.createProduct();
        product3.setName("Item3");
        product3.setToxic(false);
        product3.setSupplier(supplier1); // Same supplier as product1
        
        ProductOccupation occupation3 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(20.0);
        warehouse.getOccupations().add(occupation3);
        
        // Action: Retrieve unique suppliers for W7
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        boolean foundS6 = false;
        boolean foundS7 = false;
        for (Supplier s : result) {
            if ("S6".equals(s.getName())) foundS6 = true;
            if ("S7".equals(s.getName())) foundS7 = true;
        }
        assertTrue(foundS6);
        assertTrue(foundS7);
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityF");
        warehouse.setAddress("AddressW8");
        warehouse.setSurface(250.0);
        company.getWarehouses().add(warehouse);
        
        // Add Product "Item4" from Supplier "S8"
        Supplier supplier = CompanyFactory.eINSTANCE.createSupplier();
        supplier.setName("S8");
        supplier.setAddress("AddressS8");
        
        Product product = CompanyFactory.eINSTANCE.createProduct();
        product.setName("Item4");
        product.setToxic(false);
        product.setSupplier(supplier);
        
        ProductOccupation occupation = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(25.0);
        warehouse.getOccupations().add(occupation);
        
        // Action: Retrieve unique suppliers for W8
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityG");
        warehouse.setAddress("AddressW9");
        warehouse.setSurface(150.0);
        company.getWarehouses().add(warehouse);
        // No products added
        
        // Action: Retrieve unique suppliers for W9
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // Initialize company C1 with no warehouses
        // Action: Retrieve unique suppliers for "W10" (warehouse that doesn't exist)
        // Since we can't get a reference to a non-existent warehouse, we test with a warehouse that has no products
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityW10");
        warehouse.setAddress("AddressW10");
        warehouse.setSurface(200.0);
        // Not added to company, so it's effectively non-existent in the context
        // Or we can just test a warehouse with no occupations
        
        // Action: Retrieve unique suppliers for warehouse with no products
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityH");
        warehouse.setAddress("AddressW11");
        warehouse.setSurface(300.0);
        company.getWarehouses().add(warehouse);
        
        // Add Product "A1" from Supplier "S9"
        Supplier supplier = CompanyFactory.eINSTANCE.createSupplier();
        supplier.setName("S9");
        supplier.setAddress("AddressS9");
        
        Product product1 = CompanyFactory.eINSTANCE.createProduct();
        product1.setName("A1");
        product1.setToxic(false);
        product1.setSupplier(supplier);
        
        ProductOccupation occupation1 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(12.0);
        warehouse.getOccupations().add(occupation1);
        
        // Add Product "A2" from Supplier "S9"
        Product product2 = CompanyFactory.eINSTANCE.createProduct();
        product2.setName("A2");
        product2.setToxic(true);
        product2.setSupplier(supplier); // Same supplier
        
        ProductOccupation occupation2 = CompanyFactory.eINSTANCE.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(18.0);
        warehouse.getOccupations().add(occupation2);
        
        // Action: Retrieve unique suppliers for W11
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}