package edu.company.company1.test;

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

public class CR1Test {
    
    private CompanyFactory factory;
    
    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_warehouseWithMultipleToxicProducts() {
        // Create Company C1
        Company company = factory.createCompany();
        
        // Create Warehouse "W1" in "CityA" with surface 500m²
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityA");
        warehouse.setAddress("AddressA");
        warehouse.setSurface(500.0);
        company.getWarehouses().add(warehouse);
        
        // Create Supplier S1 and Product "ChemX" (toxic=true)
        Supplier supplier1 = factory.createSupplier();
        supplier1.setName("S1");
        supplier1.setAddress("AddressS1");
        
        Product product1 = factory.createProduct();
        product1.setName("ChemX");
        product1.setToxic(true);
        product1.setSupplier(supplier1);
        
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(10.0);
        warehouse.getOccupations().add(occupation1);
        
        // Create Supplier S2 and Product "PaintY" (toxic=true)
        Supplier supplier2 = factory.createSupplier();
        supplier2.setName("S2");
        supplier2.setAddress("AddressS2");
        
        Product product2 = factory.createProduct();
        product2.setName("PaintY");
        product2.setToxic(true);
        product2.setSupplier(supplier2);
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(5.0);
        warehouse.getOccupations().add(occupation2);
        
        // Create Supplier S3 and Product "ToolZ" (toxic=false)
        Supplier supplier3 = factory.createSupplier();
        supplier3.setName("S3");
        supplier3.setAddress("AddressS3");
        
        Product product3 = factory.createProduct();
        product3.setName("ToolZ");
        product3.setToxic(false);
        product3.setSupplier(supplier3);
        
        ProductOccupation occupation3 = factory.createProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(15.0);
        warehouse.getOccupations().add(occupation3);
        
        // Action: Retrieve toxic products in W1
        EList<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ChemX"));
        assertTrue(result.contains("PaintY"));
    }
    
    @Test
    public void testCase2_warehouseWithNoToxicProducts() {
        // Create Company C2
        Company company = factory.createCompany();
        
        // Create Warehouse "W2" in "CityB" with surface 300m²
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityB");
        warehouse.setAddress("AddressB");
        warehouse.setSurface(300.0);
        company.getWarehouses().add(warehouse);
        
        // Create Supplier S1 and Product "BoxA" (toxic=false)
        Supplier supplier1 = factory.createSupplier();
        supplier1.setName("S1");
        supplier1.setAddress("AddressS1");
        
        Product product1 = factory.createProduct();
        product1.setName("BoxA");
        product1.setToxic(false);
        product1.setSupplier(supplier1);
        
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(8.0);
        warehouse.getOccupations().add(occupation1);
        
        // Create Supplier S4 and Product "CableB" (toxic=false)
        Supplier supplier4 = factory.createSupplier();
        supplier4.setName("S4");
        supplier4.setAddress("AddressS4");
        
        Product product2 = factory.createProduct();
        product2.setName("CableB");
        product2.setToxic(false);
        product2.setSupplier(supplier4);
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(3.0);
        warehouse.getOccupations().add(occupation2);
        
        // Action: Retrieve toxic products in W2
        EList<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // Create Company C3
        Company company = factory.createCompany();
        
        // Create Warehouse "W3" in "CityC" with surface 200m² (no products added)
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityC");
        warehouse.setAddress("AddressC");
        warehouse.setSurface(200.0);
        company.getWarehouses().add(warehouse);
        
        // Action: Retrieve toxic products in W3
        EList<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // Initialize company C3 with Warehouse "W4" only
        Company company = factory.createCompany();
        
        Warehouse warehouseW4 = factory.createWarehouse();
        warehouseW4.setCity("CityW4");
        warehouseW4.setAddress("AddressW4");
        warehouseW4.setSurface(250.0);
        company.getWarehouses().add(warehouseW4);
        
        // Since we're testing a method on a warehouse object, and the test case
        // implies calling the method on a non-existent warehouse,
        // we interpret this as testing the method on an empty warehouse
        Warehouse warehouseW5 = factory.createWarehouse(); // Not added to company
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        EList<String> result = warehouseW5.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_warehouseWithSingleToxicProduct() {
        // Create Company C4
        Company company = factory.createCompany();
        
        // Create Warehouse "W6" in "CityD" with surface 400m²
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityD");
        warehouse.setAddress("AddressD");
        warehouse.setSurface(400.0);
        company.getWarehouses().add(warehouse);
        
        // Create Supplier S5 and Product "AcidK" (toxic=true)
        Supplier supplier5 = factory.createSupplier();
        supplier5.setName("S5");
        supplier5.setAddress("AddressS5");
        
        Product product = factory.createProduct();
        product.setName("AcidK");
        product.setToxic(true);
        product.setSupplier(supplier5);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(12.0);
        warehouse.getOccupations().add(occupation);
        
        // Action: Retrieve toxic products in W6
        EList<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, result.size());
        assertEquals("AcidK", result.get(0));
    }
}