package edu.company.company3.test;

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
        // Create company
        Company company = factory.createCompany();
        
        // Create Warehouse "W1" in "CityA" with surface 500m²
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityA");
        warehouse.setAddress("AddressA");
        warehouse.setSurface(500.0);
        company.getWarehouses().add(warehouse);
        
        // Add Product "ChemX" (toxic=true, volume=10) from Supplier "S1"
        Supplier supplier1 = factory.createSupplier();
        supplier1.setName("S1");
        supplier1.setAddress("AddressS1");
        
        Product chemX = factory.createProduct();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        chemX.setSupplier(supplier1);
        
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(chemX);
        occupation1.setVolume(10.0);
        warehouse.getOccupations().add(occupation1);
        
        // Add Product "PaintY" (toxic=true, volume=5) from Supplier "S2"
        Supplier supplier2 = factory.createSupplier();
        supplier2.setName("S2");
        supplier2.setAddress("AddressS2");
        
        Product paintY = factory.createProduct();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        paintY.setSupplier(supplier2);
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(paintY);
        occupation2.setVolume(5.0);
        warehouse.getOccupations().add(occupation2);
        
        // Add Product "ToolZ" (toxic=false, volume=15) from Supplier "S3"
        Supplier supplier3 = factory.createSupplier();
        supplier3.setName("S3");
        supplier3.setAddress("AddressS3");
        
        Product toolZ = factory.createProduct();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        toolZ.setSupplier(supplier3);
        
        ProductOccupation occupation3 = factory.createProductOccupation();
        occupation3.setProduct(toolZ);
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
        // Create company
        Company company = factory.createCompany();
        
        // Create Warehouse "W2" in "CityB" with surface 300m²
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityB");
        warehouse.setAddress("AddressB");
        warehouse.setSurface(300.0);
        company.getWarehouses().add(warehouse);
        
        // Add Product "BoxA" (toxic=false, volume=8) from Supplier "S1"
        Supplier supplier1 = factory.createSupplier();
        supplier1.setName("S1");
        supplier1.setAddress("AddressS1");
        
        Product boxA = factory.createProduct();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        boxA.setSupplier(supplier1);
        
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(boxA);
        occupation1.setVolume(8.0);
        warehouse.getOccupations().add(occupation1);
        
        // Add Product "CableB" (toxic=false, volume=3) from Supplier "S4"
        Supplier supplier4 = factory.createSupplier();
        supplier4.setName("S4");
        supplier4.setAddress("AddressS4");
        
        Product cableB = factory.createProduct();
        cableB.setName("CableB");
        cableB.setToxic(false);
        cableB.setSupplier(supplier4);
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(cableB);
        occupation2.setVolume(3.0);
        warehouse.getOccupations().add(occupation2);
        
        // Action: Retrieve toxic products in W2
        EList<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // Create company
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
        
        // Since we're testing a method on a warehouse object, we need to create
        // a warehouse that is not part of the company to simulate "non-existent"
        // In the context of this method, a warehouse not in the company would
        // behave the same as an empty warehouse since the method operates on
        // the warehouse's own occupations list
        
        // Create a warehouse that is not added to any company
        Warehouse warehouseW5 = factory.createWarehouse();
        warehouseW5.setCity("CityW5");
        warehouseW5.setAddress("AddressW5");
        warehouseW5.setSurface(300.0);
        // Note: Not adding to company.getWarehouses()
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        EList<String> result = warehouseW5.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_warehouseWithSingleToxicProduct() {
        // Create company
        Company company = factory.createCompany();
        
        // Create Warehouse "W6" in "CityD" with surface 400m²
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityD");
        warehouse.setAddress("AddressD");
        warehouse.setSurface(400.0);
        company.getWarehouses().add(warehouse);
        
        // Add Product "AcidK" (toxic=true, volume=12) from Supplier "S5"
        Supplier supplier5 = factory.createSupplier();
        supplier5.setName("S5");
        supplier5.setAddress("AddressS5");
        
        Product acidK = factory.createProduct();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        acidK.setSupplier(supplier5);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(acidK);
        occupation.setVolume(12.0);
        warehouse.getOccupations().add(occupation);
        
        // Action: Retrieve toxic products in W6
        EList<String> result = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, result.size());
        assertEquals("AcidK", result.get(0));
    }
}