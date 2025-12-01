package edu.company.company4.test;

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
        
        // Create warehouse W1 in CityA with surface 500m²
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityA");
        warehouse.setSurface(500.0);
        company.getWarehouses().add(warehouse);
        
        // Create suppliers
        Supplier s1 = factory.createSupplier();
        s1.setName("S1");
        Supplier s2 = factory.createSupplier();
        s2.setName("S2");
        Supplier s3 = factory.createSupplier();
        s3.setName("S3");
        
        // Create products
        Product chemX = factory.createProduct();
        chemX.setName("ChemX");
        chemX.setToxic(true);
        chemX.setSupplier(s1);
        
        Product paintY = factory.createProduct();
        paintY.setName("PaintY");
        paintY.setToxic(true);
        paintY.setSupplier(s2);
        
        Product toolZ = factory.createProduct();
        toolZ.setName("ToolZ");
        toolZ.setToxic(false);
        toolZ.setSupplier(s3);
        
        // Create occupations
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(chemX);
        occupation1.setVolume(10.0);
        warehouse.getOccupations().add(occupation1);
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(paintY);
        occupation2.setVolume(5.0);
        warehouse.getOccupations().add(occupation2);
        
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
        
        // Create warehouse W2 in CityB with surface 300m²
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityB");
        warehouse.setSurface(300.0);
        company.getWarehouses().add(warehouse);
        
        // Create suppliers
        Supplier s1 = factory.createSupplier();
        s1.setName("S1");
        Supplier s4 = factory.createSupplier();
        s4.setName("S4");
        
        // Create products
        Product boxA = factory.createProduct();
        boxA.setName("BoxA");
        boxA.setToxic(false);
        boxA.setSupplier(s1);
        
        Product cableB = factory.createProduct();
        cableB.setName("CableB");
        cableB.setToxic(false);
        cableB.setSupplier(s4);
        
        // Create occupations
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(boxA);
        occupation1.setVolume(8.0);
        warehouse.getOccupations().add(occupation1);
        
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
        
        // Create warehouse W3 in CityC with surface 200m² (no products added)
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityC");
        warehouse.setSurface(200.0);
        company.getWarehouses().add(warehouse);
        
        // Action: Retrieve toxic products in W3
        EList<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, toxicProducts.size());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // Create company C3 with Warehouse W4 only
        Company company = factory.createCompany();
        Warehouse w4 = factory.createWarehouse();
        w4.setCity("CityE");
        company.getWarehouses().add(w4);
        
        // Since we're testing a method on a warehouse object, we need to create
        // a warehouse that is not part of the company to simulate "non-existent"
        // In this context, we'll create a warehouse that's not added to any company
        Warehouse w5 = factory.createWarehouse();
        // Note: w5 is not added to any company
        
        // Action: Retrieve toxic products in W5 (doesn't exist in C3)
        EList<String> toxicProducts = w5.retrieveToxicProductNames();
        
        // Expected Output: []
        assertEquals(0, toxicProducts.size());
    }
    
    @Test
    public void testCase5_warehouseWithSingleToxicProduct() {
        // Create company
        Company company = factory.createCompany();
        
        // Create warehouse W6 in CityD with surface 400m²
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityD");
        warehouse.setSurface(400.0);
        company.getWarehouses().add(warehouse);
        
        // Create supplier
        Supplier s5 = factory.createSupplier();
        s5.setName("S5");
        
        // Create product
        Product acidK = factory.createProduct();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        acidK.setSupplier(s5);
        
        // Create occupation
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(acidK);
        occupation.setVolume(12.0);
        warehouse.getOccupations().add(occupation);
        
        // Action: Retrieve toxic products in W6
        EList<String> toxicProducts = warehouse.retrieveToxicProductNames();
        
        // Expected Output: ["AcidK"]
        assertEquals(1, toxicProducts.size());
        assertEquals("AcidK", toxicProducts.get(0));
    }
}