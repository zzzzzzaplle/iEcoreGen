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

import org.eclipse.emf.common.util.EList;
import java.util.Map;

public class CR5Test {
    
    private Company company;
    private CompanyFactory factory;
    
    @Before
    public void setUp() {
        factory = CompanyFactory.eINSTANCE;
        company = factory.createCompany();
    }
    
    @Test
    public void testCase1_productInMultipleWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1.
        Warehouse w22 = factory.createWarehouse();
        w22.setCity("CityM");
        w22.setAddress("W22_addr");
        
        Warehouse w23 = factory.createWarehouse();
        w23.setCity("CityN");
        w23.setAddress("W23_addr");
        
        company.getWarehouses().add(w22);
        company.getWarehouses().add(w23);
        
        // 2. Add Product "MaterialX" to both W22 and W23.
        Product materialX = factory.createProduct();
        materialX.setName("MaterialX");
        Supplier supplier = factory.createSupplier();
        materialX.setSupplier(supplier);
        
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(materialX);
        w22.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(materialX);
        w23.getOccupations().add(occ2);
        
        // Action: Find locations of "MaterialX" in C1.
        EList<Map<String, String>> locations = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, locations.size());
        boolean foundW22 = false;
        boolean foundW23 = false;
        for (Map<String, String> location : locations) {
            if ("CityM".equals(location.get("city")) && "W22_addr".equals(location.get("address"))) {
                foundW22 = true;
            }
            if ("CityN".equals(location.get("city")) && "W23_addr".equals(location.get("address"))) {
                foundW23 = true;
            }
        }
        assertTrue(foundW22);
        assertTrue(foundW23);
    }
    
    @Test
    public void testCase2_productInSingleWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W24" in "CityO", in company C1.
        Warehouse w24 = factory.createWarehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        company.getWarehouses().add(w24);
        
        // 2. Add Product "DeviceY" to W24.
        Product deviceY = factory.createProduct();
        deviceY.setName("DeviceY");
        Supplier supplier = factory.createSupplier();
        deviceY.setSupplier(supplier);
        
        ProductOccupation occ = factory.createProductOccupation();
        occ.setProduct(deviceY);
        w24.getOccupations().add(occ);
        
        // Action: Find locations of "DeviceY" in C1.
        EList<Map<String, String>> locations = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        Map<String, String> location = locations.get(0);
        assertEquals("CityO", location.get("city"));
        assertEquals("W24_addr", location.get("address"));
    }
    
    @Test
    public void testCase3_nonExistentProduct() {
        // SetUp:
        // 1. Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
        Warehouse w25 = factory.createWarehouse();
        w25.setCity("CityP");
        w25.setAddress("W25_addr");
        company.getWarehouses().add(w25);
        
        Product partZ = factory.createProduct();
        partZ.setName("PartZ");
        Supplier supplier = factory.createSupplier();
        partZ.setSupplier(supplier);
        
        ProductOccupation occ = factory.createProductOccupation();
        occ.setProduct(partZ);
        w25.getOccupations().add(occ);
        
        // Action: Find locations of "ToolW" in C1.
        EList<Map<String, String>> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertEquals(0, locations.size());
    }
    
    @Test
    public void testCase4_productInNoWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W26", "W27"] with other products. 
        Warehouse w26 = factory.createWarehouse();
        w26.setCity("CityQ");
        w26.setAddress("W26_addr");
        company.getWarehouses().add(w26);
        
        Warehouse w27 = factory.createWarehouse();
        w27.setCity("CityR");
        w27.setAddress("W27_addr");
        company.getWarehouses().add(w27);
        
        // Add other products to warehouses
        Product otherProduct = factory.createProduct();
        otherProduct.setName("OtherProduct");
        Supplier supplier = factory.createSupplier();
        otherProduct.setSupplier(supplier);
        
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(otherProduct);
        w26.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(otherProduct);
        w27.getOccupations().add(occ2);
        
        // 2. Warehouses are both in company C1.
        // Already added above
        
        // Action: Find locations of "ItemV" in C1.
        EList<Map<String, String>> locations = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertEquals(0, locations.size());
    }
    
    @Test
    public void testCase5_productInAllWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1.
        Warehouse w28 = factory.createWarehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        company.getWarehouses().add(w28);
        
        Warehouse w29 = factory.createWarehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        company.getWarehouses().add(w29);
        
        // 2. Add Product "CommonPart" to both warehouses.
        Product commonPart = factory.createProduct();
        commonPart.setName("CommonPart");
        Supplier supplier = factory.createSupplier();
        commonPart.setSupplier(supplier);
        
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(commonPart);
        w28.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(commonPart);
        w29.getOccupations().add(occ2);
        
        // Action: Find locations of "CommonPart" in C1.
        EList<Map<String, String>> locations = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        boolean foundW28 = false;
        boolean foundW29 = false;
        for (Map<String, String> location : locations) {
            if ("CityP".equals(location.get("city")) && "W28_addr".equals(location.get("address"))) {
                foundW28 = true;
            }
            if ("CityQ".equals(location.get("city")) && "W29_addr".equals(location.get("address"))) {
                foundW29 = true;
            }
        }
        assertTrue(foundW28);
        assertTrue(foundW29);
    }
}