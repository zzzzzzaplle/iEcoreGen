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
        
        // Create Supplier "S1"
        Supplier supplier1 = factory.createSupplier();
        supplier1.setName("S1");
        supplier1.setAddress("AddressS1");
        
        // Create Supplier "S2"
        Supplier supplier2 = factory.createSupplier();
        supplier2.setName("S2");
        supplier2.setAddress("AddressS2");
        
        // Create Supplier "S3"
        Supplier supplier3 = factory.createSupplier();
        supplier3.setName("S3");
        supplier3.setAddress("AddressS3");
        
        // Create Product "ChemX" (toxic=true)
        Product chemX = factory.createProduct();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        chemX.setSupplier(supplier1);
        
        // Create Product "PaintY" (toxic=true)
        Product paintY = factory.createProduct();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        paintY.setSupplier(supplier2);
        
        // Create Product "ToolZ" (toxic=false)
        Product toolZ = factory.createProduct();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        toolZ.setSupplier(supplier3);
        
        // Create ProductOccupation for ChemX
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(chemX);
        occupation1.setVolume(10.0);
        warehouse.getOccupations().add(occupation1);
        
        // Create ProductOccupation for PaintY
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(paintY);
        occupation2.setVolume(5.0);
        warehouse.getOccupations().add(occupation2);
        
        // Create ProductOccupation for ToolZ
        ProductOccupation occupation3 = factory.createProductOccupation();
        occupation3.setProduct(toolZ);
        occupation3.setVolume(15.0);
        warehouse.getOccupations().add(occupation3);
        
        // Action: Retrieve toxic products in W1
        EList<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["ChemX", "PaintY"]
        assertEquals(2, toxicProducts.size());
        assertTrue(toxicProducts.contains("ChemX"));
        assertTrue(toxicProducts.contains("PaintY"));
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
        
        // Create Supplier "S1"
        Supplier supplier1 = factory.createSupplier();
        supplier1.setName("S1");
        supplier1.setAddress("AddressS1");
        
        // Create Supplier "S4"
        Supplier supplier4 = factory.createSupplier();
        supplier4.setName("S4");
        supplier4.setAddress("AddressS4");
        
        // Create Product "BoxA" (toxic=false)
        Product boxA = factory.createProduct();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        boxA.setSupplier(supplier1);
        
        // Create Product "CableB" (toxic=false)
        Product cableB = factory.createProduct();
        cableB.setName("CableB");
        cableB.setToxic(false);
        cableB.setSupplier(supplier4);
        
        // Create ProductOccupation for BoxA
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(boxA);
        occupation1.setVolume(8.0);
        warehouse.getOccupations().add(occupation1);
        
        // Create ProductOccupation for CableB
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(cableB);
        occupation2.setVolume(3.0);
        warehouse.getOccupations().add(occupation2);
        
        // Action: Retrieve toxic products in W2
        EList<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, toxicProducts.size());
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
        EList<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, toxicProducts.size());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // Initialize company C3 with Warehouse "W4" only
        Company company = factory.createCompany();
        Warehouse warehouseW4 = factory.createWarehouse();
        warehouseW4.setCity("CityW4");
        warehouseW4.setAddress("AddressW4");
        warehouseW4.setSurface(100.0);
        company.getWarehouses().add(warehouseW4);
        
        // Create a new warehouse W5 that doesn't exist in the company
        Warehouse warehouseW5 = factory.createWarehouse();
        warehouseW5.setCity("CityW5");
        warehouseW5.setAddress("AddressW5");
        warehouseW5.setSurface(150.0);
        // Note: We don't add W5 to the company's warehouses list
        
        // Action: Retrieve toxic products in "W5" (doesn't exist in C3)
        EList<String> toxicProducts = warehouseW5.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, toxicProducts.size());
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
        
        // Create Supplier "S5"
        Supplier supplier5 = factory.createSupplier();
        supplier5.setName("S5");
        supplier5.setAddress("AddressS5");
        
        // Create Product "AcidK" (toxic=true)
        Product acidK = factory.createProduct();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        acidK.setSupplier(supplier5);
        
        // Create ProductOccupation for AcidK
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(acidK);
        occupation.setVolume(12.0);
        warehouse.getOccupations().add(occupation);
        
        // Action: Retrieve toxic products in W6
        EList<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, toxicProducts.size());
        assertTrue(toxicProducts.contains("AcidK"));
    }
}