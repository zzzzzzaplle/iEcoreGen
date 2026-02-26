package edu.company.company3.test;

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
    
    private CompanyFactory factory;
    private Company company;
    
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
        Supplier supplier = factory.createSupplier();
        supplier.setName("Supplier1");
        
        Product materialX = factory.createProduct();
        materialX.setName("MaterialX");
        materialX.setSupplier(supplier);
        
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(materialX);
        po1.setVolume(10.0);
        
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(materialX);
        po2.setVolume(15.0);
        
        w22.getOccupations().add(po1);
        w23.getOccupations().add(po2);
        
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
        Supplier supplier = factory.createSupplier();
        supplier.setName("Supplier1");
        
        Product deviceY = factory.createProduct();
        deviceY.setName("DeviceY");
        deviceY.setSupplier(supplier);
        
        ProductOccupation po = factory.createProductOccupation();
        po.setProduct(deviceY);
        po.setVolume(20.0);
        
        w24.getOccupations().add(po);
        
        // Action: Find locations of "DeviceY" in C1.
        EList<Map<String, String>> locations = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        assertEquals("CityO", locations.get(0).get("city"));
        assertEquals("W24_addr", locations.get(0).get("address"));
    }
    
    @Test
    public void testCase3_nonExistentProduct() {
        // SetUp:
        // 1. Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
        Warehouse w25 = factory.createWarehouse();
        w25.setCity("CityP");
        w25.setAddress("W25_addr");
        
        company.getWarehouses().add(w25);
        
        Supplier supplier = factory.createSupplier();
        supplier.setName("Supplier1");
        
        Product partZ = factory.createProduct();
        partZ.setName("PartZ");
        partZ.setSupplier(supplier);
        
        ProductOccupation po = factory.createProductOccupation();
        po.setProduct(partZ);
        po.setVolume(5.0);
        
        w25.getOccupations().add(po);
        
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
        
        Warehouse w27 = factory.createWarehouse();
        w27.setCity("CityR");
        w27.setAddress("W27_addr");
        
        company.getWarehouses().add(w26);
        company.getWarehouses().add(w27);
        
        // 2. Warehouses are both in company C1.
        Supplier supplier = factory.createSupplier();
        supplier.setName("Supplier1");
        
        Product otherProduct = factory.createProduct();
        otherProduct.setName("OtherProduct");
        otherProduct.setSupplier(supplier);
        
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(otherProduct);
        po1.setVolume(8.0);
        
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(otherProduct);
        po2.setVolume(12.0);
        
        w26.getOccupations().add(po1);
        w27.getOccupations().add(po2);
        
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
        
        Warehouse w29 = factory.createWarehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        
        company.getWarehouses().add(w28);
        company.getWarehouses().add(w29);
        
        // 2. Add Product "CommonPart" to both warehouses.
        Supplier supplier = factory.createSupplier();
        supplier.setName("Supplier1");
        
        Product commonPart = factory.createProduct();
        commonPart.setName("CommonPart");
        commonPart.setSupplier(supplier);
        
        ProductOccupation po1 = factory.createProductOccupation();
        po1.setProduct(commonPart);
        po1.setVolume(25.0);
        
        ProductOccupation po2 = factory.createProductOccupation();
        po2.setProduct(commonPart);
        po2.setVolume(30.0);
        
        w28.getOccupations().add(po1);
        w29.getOccupations().add(po2);
        
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