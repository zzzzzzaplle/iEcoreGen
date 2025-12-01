package edu.company.company1.test;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.company.CompanyFactory;
import edu.company.Company;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.Supplier;
import edu.company.ProductOccupation;

public class CR3Test {

    private CompanyFactory factory = CompanyFactory.eINSTANCE;

    // Test Case 1: "Supplier with multiple products"
    @Test
    public void testCase1_SupplierWithMultipleProducts() {
        // Create Company
        Company company = factory.createCompany();
        
        // Create Supplier
        Supplier supplier = factory.createSupplier();
        supplier.setName("S10");
        
        // Create Products
        Product product1 = factory.createProduct();
        product1.setSupplier(supplier);
        
        Product product2 = factory.createProduct();
        product2.setSupplier(supplier);
        
        // Create Warehouses
        Warehouse warehouse1 = factory.createWarehouse();
        warehouse1.setCompany(company);
        
        Warehouse warehouse2 = factory.createWarehouse();
        warehouse2.setCompany(company);
        
        // Create ProductOccupations
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(5.0);
        warehouse1.getOccupations().add(occupation1);
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(10.0);
        warehouse2.getOccupations().add(occupation2);
        
        // Add warehouses to company
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Call method and verify
        double result = company.countTotalVolumeBySupplier("S10");
        assertEquals(15.0, result, 0.001);
    }

    // Test Case 2: "Supplier with no products"
    @Test
    public void testCase2_SupplierWithNoProducts() {
        // Create Company
        Company company = factory.createCompany();
        
        // Create Supplier (not linked to any products)
        Supplier supplier = factory.createSupplier();
        supplier.setName("S11");
        
        // Call method and verify
        double result = company.countTotalVolumeBySupplier("S11");
        assertEquals(0.0, result, 0.001);
    }

    // Test Case 3: "Non-existent supplier"
    @Test
    public void testCase3_NonExistentSupplier() {
        // Create Company
        Company company = factory.createCompany();
        
        // Create Supplier and products
        Supplier supplier = factory.createSupplier();
        supplier.setName("S10");
        
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCompany(company);
        
        Product product = factory.createProduct();
        product.setSupplier(supplier);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(5.0);
        warehouse.getOccupations().add(occupation);
        
        // Add warehouse to company
        company.getWarehouses().add(warehouse);
        
        // Call method for non-existent supplier
        double result = company.countTotalVolumeBySupplier("S13");
        assertEquals(0.0, result, 0.001);
    }

    // Test Case 4: "Supplier with single product"
    @Test
    public void testCase4_SupplierWithSingleProduct() {
        // Create Company
        Company company = factory.createCompany();
        
        // Create Supplier and product
        Supplier supplier = factory.createSupplier();
        supplier.setName("S14");
        
        Warehouse warehouse = factory.createWarehouse();
        warehouse.setCompany(company);
        
        Product product = factory.createProduct();
        product.setSupplier(supplier);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(product);
        occupation.setVolume(8.0);
        warehouse.getOccupations().add(occupation);
        
        // Add warehouse to company
        company.getWarehouses().add(warehouse);
        
        // Call method and verify
        double result = company.countTotalVolumeBySupplier("S14");
        assertEquals(8.0, result, 0.001);
    }

    // Test Case 5: "Supplier with products in multiple warehouses"
    @Test
    public void testCase5_SupplierWithMultipleWarehouses() {
        // Create Company
        Company company = factory.createCompany();
        
        // Create Supplier
        Supplier supplier = factory.createSupplier();
        supplier.setName("S15");
        
        // Create Warehouses
        Warehouse warehouse1 = factory.createWarehouse();
        warehouse1.setCompany(company);
        
        Warehouse warehouse2 = factory.createWarehouse();
        warehouse2.setCompany(company);
        
        // Create Products
        Product product1 = factory.createProduct();
        product1.setSupplier(supplier);
        
        Product product2 = factory.createProduct();
        product2.setSupplier(supplier);
        
        Product product3 = factory.createProduct();
        product3.setSupplier(supplier);
        
        // Create ProductOccupations
        ProductOccupation occupation1 = factory.createProductOccupation();
        occupation1.setProduct(product1);
        occupation1.setVolume(3.0);
        warehouse1.getOccupations().add(occupation1);
        
        ProductOccupation occupation2 = factory.createProductOccupation();
        occupation2.setProduct(product2);
        occupation2.setVolume(7.0);
        warehouse2.getOccupations().add(occupation2);
        
        ProductOccupation occupation3 = factory.createProductOccupation();
        occupation3.setProduct(product3);
        occupation3.setVolume(2.0);
        warehouse1.getOccupations().add(occupation3);
        
        // Add warehouses to company
        company.getWarehouses().add(warehouse1);
        company.getWarehouses().add(warehouse2);
        
        // Call method and verify
        double result = company.countTotalVolumeBySupplier("S15");
        assertEquals(12.0, result, 0.001);
    }
}