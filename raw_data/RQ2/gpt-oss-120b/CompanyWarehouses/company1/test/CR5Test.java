package edu.company.company1.test;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.ProductOccupation;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

public class CR5Test {
    
    private final CompanyFactory factory = CompanyFactory.eINSTANCE;
    
    @Test
    public void testProductInMultipleWarehouses() {
        // Create company C1
        Company c1 = factory.createCompany();
        
        // Create warehouses
        Warehouse w22 = factory.createWarehouse();
        w22.setCity("CityM");
        w22.setAddress("W22_addr");
        
        Warehouse w23 = factory.createWarehouse();
        w23.setCity("CityN");
        w23.setAddress("W23_addr");
        
        // Create product MaterialX
        Product materialX = factory.createProduct();
        materialX.setName("MaterialX");
        
        // Create occupations
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(materialX);
        w22.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(materialX);
        w23.getOccupations().add(occ2);
        
        // Add warehouses to company
        c1.getWarehouses().add(w22);
        c1.getWarehouses().add(w23);
        
        // Find locations
        BasicEList<Map<String, String>> locations = 
            (BasicEList<Map<String, String>>) c1.findProductLocations("MaterialX");
        
        // Verify results
        assertEquals(2, locations.size());
        
        Set<String> locationSet = new HashSet<>();
        for (Map<String, String> map : locations) {
            locationSet.add(map.get("city") + "," + map.get("address"));
        }
        
        assertTrue(locationSet.contains("CityM,W22_addr"));
        assertTrue(locationSet.contains("CityN,W23_addr"));
    }
    
    @Test
    public void testProductInSingleWarehouse() {
        // Create company C1
        Company c1 = factory.createCompany();
        
        // Create warehouse
        Warehouse w24 = factory.createWarehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        
        // Create product DeviceY
        Product deviceY = factory.createProduct();
        deviceY.setName("DeviceY");
        
        // Create occupation
        ProductOccupation occ = factory.createProductOccupation();
        occ.setProduct(deviceY);
        w24.getOccupations().add(occ);
        
        // Add warehouse to company
        c1.getWarehouses().add(w24);
        
        // Find locations
        BasicEList<Map<String, String>> locations = 
            (BasicEList<Map<String, String>>) c1.findProductLocations("DeviceY");
        
        // Verify results
        assertEquals(1, locations.size());
        Map<String, String> loc = locations.get(0);
        assertEquals("CityO", loc.get("city"));
        assertEquals("W24_addr", loc.get("address"));
    }
    
    @Test
    public void testNonExistentProduct() {
        // Create company C1
        Company c1 = factory.createCompany();
        
        // Create warehouse with product
        Warehouse w25 = factory.createWarehouse();
        w25.setCity("SomeCity");
        w25.setAddress("W25_addr");
        
        Product partZ = factory.createProduct();
        partZ.setName("PartZ");
        
        ProductOccupation occ = factory.createProductOccupation();
        occ.setProduct(partZ);
        w25.getOccupations().add(occ);
        
        // Add warehouse to company
        c1.getWarehouses().add(w25);
        
        // Find non-existent product
        BasicEList<Map<String, String>> locations = 
            (BasicEList<Map<String, String>>) c1.findProductLocations("ToolW");
        
        // Verify empty result
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testProductInNoWarehouses() {
        // Create company C1
        Company c1 = factory.createCompany();
        
        // Create warehouses with other products
        Warehouse w26 = factory.createWarehouse();
        w26.setCity("CityR");
        w26.setAddress("W26_addr");
        
        Product other1 = factory.createProduct();
        other1.setName("OtherProduct1");
        
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(other1);
        w26.getOccupations().add(occ1);
        
        Warehouse w27 = factory.createWarehouse();
        w27.setCity("CityS");
        w27.setAddress("W27_addr");
        
        Product other2 = factory.createProduct();
        other2.setName("OtherProduct2");
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(other2);
        w27.getOccupations().add(occ2);
        
        // Add warehouses to company
        c1.getWarehouses().add(w26);
        c1.getWarehouses().add(w27);
        
        // Find missing product
        BasicEList<Map<String, String>> locations = 
            (BasicEList<Map<String, String>>) c1.findProductLocations("ItemV");
        
        // Verify empty result
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testProductInAllWarehouses() {
        // Create company C1
        Company c1 = factory.createCompany();
        
        // Create warehouses
        Warehouse w28 = factory.createWarehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        
        Warehouse w29 = factory.createWarehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        
        // Create common product
        Product commonPart = factory.createProduct();
        commonPart.setName("CommonPart");
        
        // Create occupations
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(commonPart);
        w28.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(commonPart);
        w29.getOccupations().add(occ2);
        
        // Add warehouses to company
        c1.getWarehouses().add(w28);
        c1.getWarehouses().add(w29);
        
        // Find locations
        BasicEList<Map<String, String>> locations = 
            (BasicEList<Map<String, String>>) c1.findProductLocations("CommonPart");
        
        // Verify results
        assertEquals(2, locations.size());
        
        Set<String> locationSet = new HashSet<>();
        for (Map<String, String> map : locations) {
            locationSet.add(map.get("city") + "," + map.get("address"));
        }
        
        assertTrue(locationSet.contains("CityP,W28_addr"));
        assertTrue(locationSet.contains("CityQ,W29_addr"));
    }
}