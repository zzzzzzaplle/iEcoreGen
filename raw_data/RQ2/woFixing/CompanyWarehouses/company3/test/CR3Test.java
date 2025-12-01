package edu.company.company3.test;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Product;
import edu.company.ProductOccupation;
import edu.company.Supplier;
import edu.company.Warehouse;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR3Test {

    private CompanyFactory factory;

    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
    }

    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // Setup
        Company company = factory.createCompany();
        Supplier supplier = factory.createSupplier();
        supplier.setName("S10");
        
        Warehouse warehouse1 = factory.createWarehouse();
        Warehouse warehouse2 = factory.createWarehouse();
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        Product product1 = factory.createProduct();
        product1.setSupplier(supplier);
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setVolume(5);
        occupation1.setProduct(product1);
        warehouse1.getOccupations().add(occupation1);
        
        Product product2 = factory.createProduct();
        product2.setSupplier(supplier);
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setVolume(10);
        occupation2.setProduct(product2);
        warehouse2.getOccupations().add(occupation2);

        // Action
        double totalVolume = company.countTotalVolumeBySupplier("S10");

        // Assert
        assertEquals(15.0, totalVolume, 0.001);
    }

    @Test
    public void testCase2_SupplierWithNoProducts() {
        // Setup
        Company company = factory.createCompany();
        Supplier supplier = factory.createSupplier();
        supplier.setName("S11");
        // No products linked to supplier

        // Action
        double totalVolume = company.countTotalVolumeBySupplier("S11");

        // Assert
        assertEquals(0.0, totalVolume, 0.001);
    }

    @Test
    public void testCase3_NonexistentSupplier() {
        // Setup
        Company company = factory.createCompany();
        Supplier supplier = factory.createSupplier();
        supplier.setName("S10");
        
        Warehouse warehouse1 = factory.createWarehouse();
        Warehouse warehouse2 = factory.createWarehouse();
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        Product product1 = factory.createProduct();
        product1.setSupplier(supplier);
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setVolume(5);
        occupation1.setProduct(product1);
        warehouse1.getOccupations().add(occupation1);
        
        Product product2 = factory.createProduct();
        product2.setSupplier(supplier);
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setVolume(10);
        occupation2.setProduct(product2);
        warehouse2.getOccupations().add(occupation2);

        // Action - Query for non-existent supplier
        double totalVolume = company.countTotalVolumeBySupplier("S13");

        // Assert
        assertEquals(0.0, totalVolume, 0.001);
    }

    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // Setup
        Company company = factory.createCompany();
        Supplier supplier = factory.createSupplier();
        supplier.setName("S14");
        
        Warehouse warehouse = factory.createWarehouse();
        company.getWarehouses().add(warehouse);
        
        Product product = factory.createProduct();
        product.setSupplier(supplier);
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setVolume(8);
        occupation.setProduct(product);
        warehouse.getOccupations().add(occupation);

        // Action
        double totalVolume = company.countTotalVolumeBySupplier("S14");

        // Assert
        assertEquals(8.0, totalVolume, 0.001);
    }

    @Test
    public void testCase5_SupplierWithMultipleWarehouses() {
        // Setup
        Company company = factory.createCompany();
        Supplier supplier = factory.createSupplier();
        supplier.setName("S15");
        
        Warehouse warehouse1 = factory.createWarehouse();
        Warehouse warehouse2 = factory.createWarehouse();
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Product 1 in Warehouse1
        Product product1 = factory.createProduct();
        product1.setSupplier(supplier);
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setVolume(3);
        occupation1.setProduct(product1);
        warehouse1.getOccupations().add(occupation1);
        
        // Product 2 in Warehouse2
        Product product2 = factory.createProduct();
        product2.setSupplier(supplier);
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setVolume(7);
        occupation2.setProduct(product2);
        warehouse2.getOccupations().add(occupation2);
        
        // Product 3 in Warehouse1
        Product product3 = factory.createProduct();
        product3.setSupplier(supplier);
        ProductOccupation occupation3 = factory.createProductOccupation();
        occupation3.setVolume(2);
        occupation3.setProduct(product3);
        warehouse1.getOccupations().add(occupation3);

        // Action
        double totalVolume = company.countTotalVolumeBySupplier("S15");

        // Assert
        assertEquals(12.0, totalVolume, 0.001);
    }
}