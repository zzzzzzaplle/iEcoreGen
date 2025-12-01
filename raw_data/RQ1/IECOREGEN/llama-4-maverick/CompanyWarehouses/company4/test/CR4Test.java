package edu.company.company4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.ProductOccupation;
import edu.company.Supplier;

public class CR4Test {
    
    private CompanyFactory factory;
    private Company company1;
    private Company company2;
    
    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
        company1 = factory.createCompany();
        company2 = factory.createCompany();
    }
    
    @Test
    public void testCase1_productExistsInWarehouse() {
        // Create Warehouse "W17" in "CityI", in company C1
        Warehouse w17 = factory.createWarehouse();
        w17.setCity("CityI");
        w17.setAddress("Address17");
        company1.getWarehouses().add(w17);
        
        // Create supplier for the product
        Supplier supplier = factory.createSupplier();
        supplier.setName("SupplierA");
        supplier.setAddress("AddressSupplierA");
        
        // Add Product "WidgetA" to W17
        Product widgetA = factory.createProduct();
        widgetA.setName("WidgetA");
        widgetA.setSupplier(supplier);
        widgetA.setToxic(false);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(widgetA);
        occupation.setVolume(10.0);
        w17.getOccupations().add(occupation);
        
        // Action: Verify "WidgetA" in W17
        boolean result = w17.containsProduct("WidgetA");
        
        // Expected Output: true
        assertTrue(result);
    }
    
    @Test
    public void testCase2_productDoesntExistInWarehouse() {
        // Create Warehouse "W18" in "CityJ", in company C1
        Warehouse w18 = factory.createWarehouse();
        w18.setCity("CityJ");
        w18.setAddress("Address18");
        company1.getWarehouses().add(w18);
        
        // Create supplier for the product
        Supplier supplier = factory.createSupplier();
        supplier.setName("SupplierB");
        supplier.setAddress("AddressSupplierB");
        
        // Add Product "GadgetB" to W18
        Product gadgetB = factory.createProduct();
        gadgetB.setName("GadgetB");
        gadgetB.setSupplier(supplier);
        gadgetB.setToxic(false);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(gadgetB);
        occupation.setVolume(15.0);
        w18.getOccupations().add(occupation);
        
        // Action: Verify "ToolC" in W18
        boolean result = w18.containsProduct("ToolC");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_nonExistentWarehouse() {
        // Create Warehouse "W19" in company C1
        Warehouse w19 = factory.createWarehouse();
        w19.setCity("CityK");
        w19.setAddress("Address19");
        company1.getWarehouses().add(w19);
        
        // Create supplier for the product
        Supplier supplier = factory.createSupplier();
        supplier.setName("SupplierC");
        supplier.setAddress("AddressSupplierC");
        
        // Add Product "ItemX" to "W18" in company C2 (different warehouse)
        Warehouse w18 = factory.createWarehouse();
        w18.setCity("CityL");
        w18.setAddress("Address18");
        company2.getWarehouses().add(w18);
        
        Product itemX = factory.createProduct();
        itemX.setName("ItemX");
        itemX.setSupplier(supplier);
        itemX.setToxic(true);
        
        ProductOccupation occupation = factory.createProductOccupation();
        occupation.setProduct(itemX);
        occupation.setVolume(20.0);
        w18.getOccupations().add(occupation);
        
        // Action: Verify "ItemX" in Warehouse "W19"
        boolean result = w19.containsProduct("ItemX");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase4_emptyWarehouseCheck() {
        // Create Warehouse "W20" in "CityK" (no products), in company C1
        Warehouse w20 = factory.createWarehouse();
        w20.setCity("CityK");
        w20.setAddress("Address20");
        company1.getWarehouses().add(w20);
        // No products added to the warehouse
        
        // Action: Verify "PartY" in W20
        boolean result = w20.containsProduct("PartY");
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase5_multipleProductsInWarehouse() {
        // Create Warehouse "W21" in "CityL", in company C1
        Warehouse w21 = factory.createWarehouse();
        w21.setCity("CityL");
        w21.setAddress("Address21");
        company1.getWarehouses().add(w21);
        
        // Create supplier for the products
        Supplier supplier = factory.createSupplier();
        supplier.setName("SupplierD");
        supplier.setAddress("AddressSupplierD");
        
        // Add Products ["CompA", "CompB", "CompC"] to W21
        Product compA = factory.createProduct();
        compA.setName("CompA");
        compA.setSupplier(supplier);
        compA.setToxic(false);
        
        Product compB = factory.createProduct();
        compB.setName("CompB");
        compB.setSupplier(supplier);
        compB.setToxic(false);
        
        Product compC = factory.createProduct();
        compC.setName("CompC");
        compC.setSupplier(supplier);
        compC.setToxic(false);
        
        ProductOccupation occupationA = factory.createProductOccupation();
        occupationA.setProduct(compA);
        occupationA.setVolume(5.0);
        w21.getOccupations().add(occupationA);
        
        ProductOccupation occupationB = factory.createProductOccupation();
        occupationB.setProduct(compB);
        occupationB.setVolume(10.0);
        w21.getOccupations().add(occupationB);
        
        ProductOccupation occupationC = factory.createProductOccupation();
        occupationC.setProduct(compC);
        occupationC.setVolume(15.0);
        w21.getOccupations().add(occupationC);
        
        // Action: Verify "CompB" in W21
        boolean result = w21.containsProduct("CompB");
        
        // Expected Output: true
        assertTrue(result);
    }
}