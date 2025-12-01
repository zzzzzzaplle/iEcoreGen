package edu.company.company2.test;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Product;
import edu.company.ProductOccupation;
import edu.company.Supplier;
import edu.company.Warehouse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CR2Test {

    private CompanyFactory factory;
    private Company company;

    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
        company = factory.createCompany();
    }

    @Test
    public void testCase1_MultipleSuppliers() {
        // Create warehouse W7 and add to company
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityE");
        warehouse.setSurface(350);
        company.getWarehouses().add(warehouse);

        // Create suppliers
        Supplier s6 = factory.createSupplier();
        s6.setName("S6");
        Supplier s7 = factory.createSupplier();
        s7.setName("S7");

        // Create products with occupations
        Product item1 = factory.createProduct();
        item1.setName("Item1");
        item1.setSupplier(s6);
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(item1);
        warehouse.getOccupations().add(po1);

        Product item2 = factory.createProduct();
        item2.setName("Item2");
        item2.setSupplier(s7);
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(item2);
        warehouse.getOccupations().add(po2);

        Product item3 = factory.createProduct();
        item3.setName("Item3");
        item3.setSupplier(s6); // Duplicate supplier
        ProductOccupation po3 = factory.createProductOccupation();
        po3.setProduct(item3);
        warehouse.getOccupations().add(po3);

        // Retrieve unique suppliers and verify
        Set<String> resultNames = new HashSet<>();
        for (Supplier s : warehouse.retrieveUniqueSuppliers()) {
            resultNames.add(s.getName());
        }
        
        assertEquals(new HashSet<>(Arrays.asList("S6", "S7")), resultNames);
    }

    @Test
    public void testCase2_SingleProduct() {
        // Create warehouse W8 and add to company
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityF");
        warehouse.setSurface(250);
        company.getWarehouses().add(warehouse);

        // Create supplier and product
        Supplier s8 = factory.createSupplier();
        s8.setName("S8");
        Product item4 = factory.createProduct();
        item4.setName("Item4");
        item4.setSupplier(s8);
        ProductOccupation po = factory.createProductOccupation();
        po.setProduct(item4);
        warehouse.getOccupations().add(po);

        // Retrieve unique suppliers and verify
        Set<String> resultNames = new HashSet<>();
        for (Supplier s : warehouse.retrieveUniqueSuppliers()) {
            resultNames.add(s.getName());
        }
        
        assertEquals(new HashSet<>(Arrays.asList("S8")), resultNames);
    }

    @Test
    public void testCase3_EmptyWarehouse() {
        // Create empty warehouse W9 and add to company
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityG");
        warehouse.setSurface(150);
        company.getWarehouses().add(warehouse);

        // Verify empty result
        assertTrue(warehouse.retrieveUniqueSuppliers().isEmpty());
    }

    @Test
    public void testCase4_NonExistentWarehouse() {
        // Create standalone warehouse not added to company
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityX");
        warehouse.setSurface(200);

        // Verify empty result
        assertTrue(warehouse.retrieveUniqueSuppliers().isEmpty());
    }

    @Test
    public void testCase5_SameSupplier() {
        // Create warehouse W11 and add to company
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityH");
        warehouse.setSurface(300);
        company.getWarehouses().add(warehouse);

        // Create single supplier
        Supplier s9 = factory.createSupplier();
        s9.setName("S9");

        // Create multiple products with same supplier
        Product a1 = factory.createProduct();
        a1.setName("A1");
        a1.setSupplier(s9);
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(a1);
        warehouse.getOccupations().add(po1);

        Product a2 = factory.createProduct();
        a2.setName("A2");
        a2.setSupplier(s9);
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(a2);
        warehouse.getOccupations().add(po2);

        // Retrieve unique suppliers and verify
        Set<String> resultNames = new HashSet<>();
        for (Supplier s : warehouse.retrieveUniqueSuppliers()) {
            resultNames.add(s.getName());
        }
        
        assertEquals(new HashSet<>(Arrays.asList("S9")), resultNames);
    }
}