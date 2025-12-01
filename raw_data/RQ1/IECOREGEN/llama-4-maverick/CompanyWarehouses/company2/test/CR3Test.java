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

public class CR3Test {
    
    private CompanyFactory factory;
    
    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // Create company
        Company company = factory.createCompany();
        
        // Create supplier S10
        Supplier supplier = factory.createSupplier();
        supplier.setName("S10");
        
        // Create warehouses
        Warehouse warehouse1 = factory.createWarehouse();
        warehouse1.setCity("W12");
        Warehouse warehouse2 = factory.createWarehouse();
        warehouse2.setCity("W13");
        
        // Add warehouses to company
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Create products
        Product product1 = factory.createProduct();
        product1.setName("P1");
        product1.setSupplier(supplier);
        
        Product product2 = factory.createProduct();
        product2.setName("P2");
        product2.setSupplier(supplier);
        
        // Create occupations
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(5.0);
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(10.0);
        
        // Add occupations to warehouses
        warehouse1.getOccupations().add(occupation1);
        warehouse2.getOccupations().add(occupation2);
        
        // Action: List volumes for supplier "S10" in company
        double result = company.countTotalVolumeBySupplier("S10");
        
        // Expected Output: 15
        assertEquals(15.0, result, 0.001);
    }
    
    @Test
    public void testCase2_supplierWithNoProducts() {
        // Create company C2 without warehouse
        Company company = factory.createCompany();
        
        // Create Supplier "S11" (no products linked)
        Supplier supplier = factory.createSupplier();
        supplier.setName("S11");
        
        // Action: List volumes for supplier "S11" in company C2
        double result = company.countTotalVolumeBySupplier("S11");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_nonExistentSupplier() {
        // Create company
        Company company = factory.createCompany();
        
        // Create supplier S10
        Supplier supplier = factory.createSupplier();
        supplier.setName("S10");
        
        // Create warehouses
        Warehouse warehouse1 = factory.createWarehouse();
        warehouse1.setCity("W12");
        Warehouse warehouse2 = factory.createWarehouse();
        warehouse2.setCity("W13");
        
        // Add warehouses to company
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Create products
        Product product1 = factory.createProduct();
        product1.setName("P1");
        product1.setSupplier(supplier);
        
        Product product2 = factory.createProduct();
        product2.setName("P2");
        product2.setSupplier(supplier);
        
        // Create occupations
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(5.0);
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(10.0);
        
        // Add occupations to warehouses
        warehouse1.getOccupations().add(occupation1);
        warehouse2.getOccupations().add(occupation2);
        
        // Create Supplier "S12" (no products linked)
        Supplier supplier2 = factory.createSupplier();
        supplier2.setName("S12");
        
        // Action: List volumes for supplier name "S13" in company
        double result = company.countTotalVolumeBySupplier("S13");
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_supplierWithSingleProduct() {
        // Create company
        Company company = factory.createCompany();
        
        // Add Supplier "S14" to system
        Supplier supplier = factory.createSupplier();
        supplier.setName("S14");
        
        // Create warehouse
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCity("W14");
        company.getWarehouses().add(warehouse);
        
        // Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company
        Product product = factory.createProduct();
        product.setName("P3");
        product.setSupplier(supplier);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(8.0);
        
        warehouse.getOccupations().add(occupation);
        
        // Action: List volumes for supplier "S14" in company
        double result = company.countTotalVolumeBySupplier("S14");
        
        // Expected Output: 8
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // Create company
        Company company = factory.createCompany();
        
        // Add Supplier "S15" to system
        Supplier supplier = factory.createSupplier();
        supplier.setName("S15");
        
        // Create warehouses
        Warehouse warehouse1 = factory.createWarehouse();
        warehouse1.setCity("W15");
        Warehouse warehouse2 = factory.createWarehouse();
        warehouse2.setCity("W16");
        
        // Add warehouses to company
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company
        Product product1 = factory.createProduct();
        product1.setName("X1");
        product1.setSupplier(supplier);
        
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(3.0);
        
        warehouse1.getOccupations().add(occupation1);
        
        // Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company
        Product product2 = factory.createProduct();
        product2.setName("X2");
        product2.setSupplier(supplier);
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(7.0);
        
        warehouse2.getOccupations().add(occupation2);
        
        // Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company
        Product product3 = factory.createProduct();
        product3.setName("X3");
        product3.setSupplier(supplier);
        
        ProductOccupation occupation3 = factory.createProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(2.0);
        
        warehouse1.getOccupations().add(occupation3);
        
        // Action: List volumes for supplier "S15" in company
        double result = company.countTotalVolumeBySupplier("S15");
        
        // Expected Output: 12
        assertEquals(12.0, result, 0.001);
    }
}