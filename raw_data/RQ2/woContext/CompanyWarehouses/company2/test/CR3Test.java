package edu.company.company2.test;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.company.CompanyFactory;
import edu.company.Company;
import edu.company.Supplier;
import edu.company.Product;
import edu.company.Warehouse;
import edu.company.ProductOccupation;

public class CR3Test {

    private CompanyFactory factory = CompanyFactory.eINSTANCE;
    
    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // Create company C1
        Company company = factory.createCompany();
        
        // Create Supplier "S10"
        Supplier supplier = factory.createSupplier();
        supplier.setName("S10");
        
        // Create Product "P1" from S10
        Product product1 = factory.createProduct();
        product1.setSupplier(supplier);
        
        // Create Product "P2" from S10
        Product product2 = factory.createProduct();
        product2.setSupplier(supplier);
        
        // Create Warehouse "W12" and add occupation for P1 (volume=5)
        Warehouse warehouse1 = factory.createWarehouse();
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(5.0);
        warehouse1.getOccupations().add(occupation1);
        
        // Create Warehouse "W13" and add occupation for P2 (volume=10)
        Warehouse warehouse2 = factory.createWarehouse();
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(10.0);
        warehouse2.getOccupations().add(occupation2);
        
        // Add warehouses to company
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Verify total volume for supplier "S10" is 15
        assertEquals(15.0, company.countTotalVolumeBySupplier("S10"), 0.001);
    }
    
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // Create company C2 without warehouses
        Company company = factory.createCompany();
        
        // Create Supplier "S11" with no products linked
        Supplier supplier = factory.createSupplier();
        supplier.setName("S11");
        
        // Verify total volume for supplier "S11" is 0
        assertEquals(0.0, company.countTotalVolumeBySupplier("S11"), 0.001);
    }
    
    @Test
    public void testCase3_NonExistentSupplier() {
        // Create company C1
        Company company = factory.createCompany();
        
        // Create Supplier "S10"
        Supplier supplier = factory.createSupplier();
        supplier.setName("S10");
        
        // Create Product "P1" from S10
        Product product1 = factory.createProduct();
        product1.setSupplier(supplier);
        
        // Create Warehouse "W12" with P1 (volume=5)
        Warehouse warehouse1 = factory.createWarehouse();
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(5.0);
        warehouse1.getOccupations().add(occupation1);
        
        // Create Warehouse "W13" with P2 (volume=10) from S10
        Product product2 = factory.createProduct();
        product2.setSupplier(supplier);
        Warehouse warehouse2 = factory.createWarehouse();
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(10.0);
        warehouse2.getOccupations().add(occupation2);
        
        // Add warehouses to company
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Create Supplier "S12" with no products
        Supplier unusedSupplier = factory.createSupplier();
        unusedSupplier.setName("S12");
        
        // Verify non-existent supplier "S13" returns 0
        assertEquals(0.0, company.countTotalVolumeBySupplier("S13"), 0.001);
    }
    
    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // Create company C3
        Company company = factory.createCompany();
        
        // Create Supplier "S14"
        Supplier supplier = factory.createSupplier();
        supplier.setName("S14");
        
        // Create Product "P3" from S14
        Product product = factory.createProduct();
        product.setSupplier(supplier);
        
        // Create Warehouse "W14" with occupation for P3 (volume=8)
        Warehouse warehouse = factory.createWarehouse();
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(8.0);
        warehouse.getOccupations().add(occupation);
        
        // Add warehouse to company
        company.getWarehouses().add(warehouse);
        
        // Verify total volume for supplier "S14" is 8
        assertEquals(8.0, company.countTotalVolumeBySupplier("S14"), 0.001);
    }
    
    @Test
    public void testCase5_SupplierWithProductsInMultipleWarehouses() {
        // Create company C4
        Company company = factory.createCompany();
        
        // Create Supplier "S15"
        Supplier supplier = factory.createSupplier();
        supplier.setName("S15");
        
        // Create products for S15
        Product product1 = factory.createProduct();
        product1.setSupplier(supplier);
        Product product2 = factory.createProduct();
        product2.setSupplier(supplier);
        Product product3 = factory.createProduct();
        product3.setSupplier(supplier);
        
        // Create Warehouse "W15" with occupations:
        Warehouse warehouse1 = factory.createWarehouse();
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(3.0);
        ProductOccupation occupation3 = factory.createProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(2.0);
        warehouse1.getOccupations().add(occupation1);
        warehouse1.getOccupations().add(occupation3);
        
        // Create Warehouse "W16" with occupation for product2 (volume=7)
        Warehouse warehouse2 = factory.createWarehouse();
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(7.0);
        warehouse2.getOccupations().add(occupation2);
        
        // Add warehouses to company
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Verify total volume for supplier "S15" is 12
        assertEquals(12.0, company.countTotalVolumeBySupplier("S15"), 0.001);
    }
}