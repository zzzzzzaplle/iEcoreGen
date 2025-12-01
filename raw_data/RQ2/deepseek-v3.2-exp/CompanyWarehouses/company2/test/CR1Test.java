package edu.company.company2.test;

import edu.company.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class CR1Test {
    private CompanyFactory factory;
    private Company company;

    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
        company = factory.createCompany();
    }

    @Test
    public void testCase1_WarehouseWithMultipleToxicProducts() {
        // Create Warehouse "W1" in "CityA" with surface 500m², in company C1
        Warehouse w1 = factory.createWarehouse();
        w1.setCity("CityA");
        w1.setSurface(500.0);
        company.getWarehouses().add(w1);

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

        // Create product occupations
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(chemX);
        po1.setVolume(10.0);

        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(paintY);
        po2.setVolume(5.0);

        ProductOccupation po3 = factory.createProductOccupation();
        po3.setProduct(toolZ);
        po3.setVolume(15.0);

        // Add occupations to warehouse
        w1.getOccupations().add(po1);
        w1.getOccupations().add(po2);
        w1.getOccupations().add(po3);

        // Retrieve toxic products
        List<String> toxicProducts = w1.retrieveToxicProductNames();

        // Verify expected toxic products (order preserved)
        assertEquals(2, toxicProducts.size());
        assertEquals("ChemX", toxicProducts.get(0));
        assertEquals("PaintY", toxicProducts.get(1));
    }

    @Test
    public void testCase2_WarehouseWithNoToxicProducts() {
        // Create Warehouse "W2" in "CityB" with surface 300m², in company C2
        Warehouse w2 = factory.createWarehouse();
        w2.setCity("CityB");
        w2.setSurface(300.0);
        company.getWarehouses().add(w2);

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

        // Create product occupations
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(boxA);
        po1.setVolume(8.0);

        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(cableB);
        po2.setVolume(3.0);

        // Add occupations to warehouse
        w2.getOccupations().add(po1);
        w2.getOccupations().add(po2);

        // Retrieve toxic products
        List<String> toxicProducts = w2.retrieveToxicProductNames();

        // Verify no toxic products found
        assertTrue(toxicProducts.isEmpty());
    }

    @Test
    public void testCase3_EmptyWarehouse() {
        // Create Warehouse "W3" in "CityC" with surface 200m² (no products)
        Warehouse w3 = factory.createWarehouse();
        w3.setCity("CityC");
        w3.setSurface(200.0);
        company.getWarehouses().add(w3);

        // Retrieve toxic products
        List<String> toxicProducts = w3.retrieveToxicProductNames();

        // Verify no toxic products found
        assertTrue(toxicProducts.isEmpty());
    }

    @Test
    public void testCase4_NonExistentWarehouse() {
        // Create Warehouse "W4" and add to company
        Warehouse w4 = factory.createWarehouse();
        company.getWarehouses().add(w4);

        // Create a warehouse that isn't added to any company ("W5")
        Warehouse w5 = factory.createWarehouse();
        
        // Retrieve toxic products for non-registered warehouse
        List<String> toxicProducts = w5.retrieveToxicProductNames();

        // Verify no toxic products found
        assertTrue(toxicProducts.isEmpty());
    }

    @Test
    public void testCase5_WarehouseWithSingleToxicProduct() {
        // Create Warehouse "W6" in "CityD" with surface 400m²
        Warehouse w6 = factory.createWarehouse();
        w6.setCity("CityD");
        w6.setSurface(400.0);
        company.getWarehouses().add(w6);

        // Create supplier
        Supplier s5 = factory.createSupplier();
        s5.setName("S5");

        // Create product
        Product acidK = factory.createProduct();
        acidK.setName("AcidK");
        acidK.setToxic(true);
        acidK.setSupplier(s5);

        // Create product occupation
        ProductOccupation po = factory.createProductOccupation();
        po.setProduct(acidK);
        po.setVolume(12.0);

        // Add occupation to warehouse
        w6.getOccupations().add(po);

        // Retrieve toxic products
        List<String> toxicProducts = w6.retrieveToxicProductNames();

        // Verify expected toxic product
        assertEquals(1, toxicProducts.size());
        assertEquals("AcidK", toxicProducts.get(0));
    }
}