package edu.company.company3.test;

import edu.company.CompanyFactory;
import edu.company.Company;
import edu.company.Warehouse;
import edu.company.Supplier;
import edu.company.Product;
import edu.company.ProductOccupation;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR2Test {

    private CompanyFactory factory;
    private Company company;

    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
        company = factory.createCompany();
    }

    @Test
    public void testCase1_WarehouseWithProductsFromMultipleSuppliers() {
        // Create warehouse W7
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityE");
        company.getWarehouses().add(warehouse);
        
        // Create suppliers and products
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
        
        // Create occupations and add to warehouse
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(product1);
        warehouse.getOccupations().add(po1);
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(product2);
        warehouse.getOccupations().add(po2);
        ProductOccupation po3 = factory.createProductOccupation();
        po3.setProduct(product3);
        warehouse.getOccupations().add(po3);
        
        // Execute operation
        BasicEList<Supplier> result = (BasicEList<Supplier>) warehouse.retrieveUniqueSuppliers();
        
        // Verify results
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> "S6".equals(s.getName())));
        assertTrue(result.stream().anyMatch(s -> "S7".equals(s.getName())));
    }

    @Test
    public void testCase2_WarehouseWithSingleProduct() {
        // Create warehouse W8
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityF");
        company.getWarehouses().add(warehouse);
        
        // Create supplier and product
        Supplier supplierS8 = factory.createSupplier();
        supplierS8.setName("S8");
        Product product = factory.createProduct();
        product.setName("Item4");
        product.setSupplier(supplierS8);
        
        // Create occupation and add to warehouse
        ProductOccupation po = factory.createProductOccupation();
        po.setProduct(product);
        warehouse.getOccupations().add(po);
        
        // Execute operation
        BasicEList<Supplier> result = (BasicEList<Supplier>) warehouse.retrieveUniqueSuppliers();
        
        // Verify results
        assertEquals(1, result.size());
        assertEquals("S8", result.get(0).getName());
    }

    @Test
    public void testCase3_EmptyWarehouse() {
        // Create warehouse W9 (no products)
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityG");
        company.getWarehouses().add(warehouse);
        
        // Execute operation
        BasicEList<Supplier> result = (BasicEList<Supplier>) warehouse.retrieveUniqueSuppliers();
        
        // Verify results
        assertTrue(result.isEmpty());
    }

    @Test
    public void testCase4_NonExistentWarehouse() {
        // Create warehouse not added to company
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityH");
        
        // Execute operation
        BasicEList<Supplier> result = (BasicEList<Supplier>) warehouse.retrieveUniqueSuppliers();
        
        // Verify results
        assertTrue(result.isEmpty());
    }

    @Test
    public void testCase5_WarehouseWithProductsFromSameSupplier() {
        // Create warehouse W11
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("CityH");
        company.getWarehouses().add(warehouse);
        
        // Create supplier and products
        Supplier supplierS9 = factory.createSupplier();
        supplierS9.setName("S9");
        Product product1 = factory.createProduct();
        product1.setName("A1");
        product1.setSupplier(supplierS9);
        Product product2 = factory.createProduct();
        product2.setName("A2");
        product2.setSupplier(supplierS9);
        
        // Create occupations and add to warehouse
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(product1);
        warehouse.getOccupations().add(po1);
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(product2);
        warehouse.getOccupations().add(po2);
        
        // Execute operation
        BasicEList<Supplier> result = (BasicEList<Supplier>) warehouse.retrieveUniqueSuppliers();
        
        // Verify results
        assertEquals(1, result.size());
        assertEquals("S9", result.get(0).getName());
    }
}