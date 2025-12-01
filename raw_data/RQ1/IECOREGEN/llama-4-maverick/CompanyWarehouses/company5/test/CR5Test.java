package edu.company.company5.test;

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
    private Warehouse w22, w23, w24, w25, w26, w27, w28, w29;
    private Product materialX, deviceY, partZ, toolW, itemV, commonPart;
    private Supplier supplier;
    
    @Before
    public void setUp() {
        // Initialize factory and company
        company = CompanyFactory.eINSTANCE.createCompany();
        
        // Create a supplier for products
        supplier = CompanyFactory.eINSTANCE.createSupplier();
        supplier.setName("Supplier1");
        supplier.setAddress("Supplier Address");
        
        // Create products
        materialX = CompanyFactory.eINSTANCE.createProduct();
        materialX.setName("MaterialX");
        materialX.setToxic(false);
        materialX.setSupplier(supplier);
        
        deviceY = CompanyFactory.eINSTANCE.createProduct();
        deviceY.setName("DeviceY");
        deviceY.setToxic(false);
        deviceY.setSupplier(supplier);
        
        partZ = CompanyFactory.eINSTANCE.createProduct();
        partZ.setName("PartZ");
        partZ.setToxic(false);
        partZ.setSupplier(supplier);
        
        toolW = CompanyFactory.eINSTANCE.createProduct();
        toolW.setName("ToolW");
        toolW.setToxic(false);
        toolW.setSupplier(supplier);
        
        itemV = CompanyFactory.eINSTANCE.createProduct();
        itemV.setName("ItemV");
        itemV.setToxic(false);
        itemV.setSupplier(supplier);
        
        commonPart = CompanyFactory.eINSTANCE.createProduct();
        commonPart.setName("CommonPart");
        commonPart.setToxic(false);
        commonPart.setSupplier(supplier);
    }
    
    @Test
    public void testCase1_productInMultipleWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1.
        w22 = CompanyFactory.eINSTANCE.createWarehouse();
        w22.setCity("CityM");
        w22.setAddress("W22_addr");
        w22.setSurface(100.0);
        company.getWarehouses().add(w22);
        
        w23 = CompanyFactory.eINSTANCE.createWarehouse();
        w23.setCity("CityN");
        w23.setAddress("W23_addr");
        w23.setSurface(150.0);
        company.getWarehouses().add(w23);
        
        // 2. Add Product "MaterialX" to both W22 and W23.
        ProductOccupation po1 = CompanyFactory.eINSTANCE.createProductOccupation();
        po1.setProduct(materialX);
        po1.setVolume(10.0);
        w22.getOccupations().add(po1);
        
        ProductOccupation po2 = CompanyFactory.eINSTANCE.createProductOccupation();
        po2.setProduct(materialX);
        po2.setVolume(20.0);
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
        w24 = CompanyFactory.eINSTANCE.createWarehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        w24.setSurface(200.0);
        company.getWarehouses().add(w24);
        
        // 2. Add Product "DeviceY" to W24.
        ProductOccupation po = CompanyFactory.eINSTANCE.createProductOccupation();
        po.setProduct(deviceY);
        po.setVolume(15.0);
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
        w25 = CompanyFactory.eINSTANCE.createWarehouse();
        w25.setCity("CityP");
        w25.setAddress("W25_addr");
        w25.setSurface(120.0);
        company.getWarehouses().add(w25);
        
        ProductOccupation po = CompanyFactory.eINSTANCE.createProductOccupation();
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
        w26 = CompanyFactory.eINSTANCE.createWarehouse();
        w26.setCity("CityQ");
        w26.setAddress("W26_addr");
        w26.setSurface(80.0);
        company.getWarehouses().add(w26);
        
        w27 = CompanyFactory.eINSTANCE.createWarehouse();
        w27.setCity("CityR");
        w27.setAddress("W27_addr");
        w27.setSurface(90.0);
        company.getWarehouses().add(w27);
        
        // Add other products to these warehouses
        ProductOccupation po1 = CompanyFactory.eINSTANCE.createProductOccupation();
        po1.setProduct(partZ);
        po1.setVolume(10.0);
        w26.getOccupations().add(po1);
        
        ProductOccupation po2 = CompanyFactory.eINSTANCE.createProductOccupation();
        po2.setProduct(deviceY);
        po2.setVolume(15.0);
        w27.getOccupations().add(po2);
        
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
        w28 = CompanyFactory.eINSTANCE.createWarehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        w28.setSurface(110.0);
        company.getWarehouses().add(w28);
        
        w29 = CompanyFactory.eINSTANCE.createWarehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        w29.setSurface(130.0);
        company.getWarehouses().add(w29);
        
        // 2. Add Product "CommonPart" to both warehouses.
        ProductOccupation po1 = CompanyFactory.eINSTANCE.createProductOccupation();
        po1.setProduct(commonPart);
        po1.setVolume(25.0);
        w28.getOccupations().add(po1);
        
        ProductOccupation po2 = CompanyFactory.eINSTANCE.createProductOccupation();
        po2.setProduct(commonPart);
        po2.setVolume(30.0);
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