package edu.company.company2.test;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Product;
import edu.company.ProductOccupation;
import edu.company.Supplier;
import edu.company.Warehouse;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;
import static org.junit.Assert.*;

public class CR5Test {

    private CompanyFactory factory = CompanyFactory.eINSTANCE;

    @Test
    public void testCase1_ProductInMultipleWarehouses() {
        // Create company
        Company company = factory.createCompany();
        
        // Create warehouses
        Warehouse w22 = factory.createWarehouse();
        w22.setCity("CityM");
        w22.setAddress("W22_addr");
        company.getWarehouses().add(w22);
        
        Warehouse w23 = factory.createWarehouse();
        w23.setCity("CityN");
        w23.setAddress("W23_addr");
        company.getWarehouses().add(w23);
        
        // Create product and add to both warehouses
        Product productX = factory.createProduct();
        productX.setName("MaterialX");
        
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(productX);
        w22.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(productX);
        w23.getOccupations().add(occ2);
        
        // Verify locations
        EList<Map<String, String>> locations = company.findProductLocations("MaterialX");
        assertEquals(2, locations.size());
        
        // Verify expected warehouse details
        Set<String> actualLocations = new HashSet<>();
        for (Map<String, String> loc : locations) {
            actualLocations.add(loc.get("city") + ":" + loc.get("address"));
        }
        
        assertTrue(actualLocations.contains("CityM:W22_addr"));
        assertTrue(actualLocations.contains("CityN:W23_addr"));
    }

    @Test
    public void testCase2_ProductInSingleWarehouse() {
        // Create company
        Company company = factory.createCompany();
        
        // Create warehouse
        Warehouse w24 = factory.createWarehouse();
        w24.setCity("CityO");
        w24.setAddress("W24_addr");
        company.getWarehouses().add(w24);
        
        // Create product and add to warehouse
        Product productY = factory.createProduct();
        productY.setName("DeviceY");
        
        ProductOccupation occ = factory.createProductOccupation();
        occ.setProduct(productY);
        w24.getOccupations().add(occ);
        
        // Verify location
        EList<Map<String, String>> locations = company.findProductLocations("DeviceY");
        assertEquals(1, locations.size());
        assertEquals("CityO", locations.get(0).get("city"));
        assertEquals("W24_addr", locations.get(0).get("address"));
    }

    @Test
    public void testCase3_NonExistentProduct() {
        // Create company
        Company company = factory.createCompany();
        
        // Create warehouse with different product
        Warehouse w25 = factory.createWarehouse();
        w25.setCity("SomeCity");
        w25.setAddress("W25_addr");
        company.getWarehouses().add(w25);
        
        Product productZ = factory.createProduct();
        productZ.setName("PartZ");
        
        ProductOccupation occ = factory.createProductOccupation();
        occ.setProduct(productZ);
        w25.getOccupations().add(occ);
        
        // Verify non-existent product
        EList<Map<String, String>> locations = company.findProductLocations("ToolW");
        assertTrue(locations.isEmpty());
    }

    @Test
    public void testCase4_ProductInNoWarehouses() {
        // Create company
        Company company = factory.createCompany();
        
        // Create warehouses with different products
        Warehouse w26 = factory.createWarehouse();
        w26.setCity("CityR");
        w26.setAddress("W26_addr");
        company.getWarehouses().add(w26);
        
        Warehouse w27 = factory.createWarehouse();
        w27.setCity("CityS");
        w27.setAddress("W27_addr");
        company.getWarehouses().add(w27);
        
        // Add other products
        Product productA = factory.createProduct();
        productA.setName("ProductA");
        
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(productA);
        w26.getOccupations().add(occ1);
        
        Product productB = factory.createProduct();
        productB.setName("ProductB");
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(productB);
        w27.getOccupations().add(occ2);
        
        // Verify missing product
        EList<Map<String, String>> locations = company.findProductLocations("ItemV");
        assertTrue(locations.isEmpty());
    }

    @Test
    public void testCase5_ProductInAllWarehouses() {
        // Create company
        Company company = factory.createCompany();
        
        // Create warehouses
        Warehouse w28 = factory.createWarehouse();
        w28.setCity("CityP");
        w28.setAddress("W28_addr");
        company.getWarehouses().add(w28);
        
        Warehouse w29 = factory.createWarehouse();
        w29.setCity("CityQ");
        w29.setAddress("W29_addr");
        company.getWarehouses().add(w29);
        
        // Create product and add to both warehouses
        Product commonProduct = factory.createProduct();
        commonProduct.setName("CommonPart");
        
        ProductOccupation occ1 = factory.createProductOccupation();
        occ1.setProduct(commonProduct);
        w28.getOccupations().add(occ1);
        
        ProductOccupation occ2 = factory.createProductOccupation();
        occ2.setProduct(commonProduct);
        w29.getOccupations().add(occ2);
        
        // Verify locations
        EList<Map<String, String>> locations = company.findProductLocations("CommonPart");
        assertEquals(2, locations.size());
        
        // Verify expected warehouse details
        Set<String> actualLocations = new HashSet<>();
        for (Map<String, String> loc : locations) {
            actualLocations.add(loc.get("city") + ":" + loc.get("address"));
        }
        
        assertTrue(actualLocations.contains("CityP:W28_addr"));
        assertTrue(actualLocations.contains("CityQ:W29_addr"));
    }
}