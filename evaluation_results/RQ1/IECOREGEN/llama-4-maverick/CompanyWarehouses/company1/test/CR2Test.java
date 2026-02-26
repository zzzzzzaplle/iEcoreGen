package edu.company.company1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.company.Company;
import edu.company.CompanyFactory;
import edu.company.Warehouse;
import edu.company.Product;
import edu.company.Supplier;
import edu.company.ProductOccupation;
import org.eclipse.emf.common.util.EList;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Create a company for all test cases
        company = CompanyFactory.eINSTANCE.createCompany();
    }
    
    @Test
    public void testCase1_warehouseWithProductsFromMultipleSuppliers() {
        // Create Warehouse "W7" in "CityE" with surface 350m², in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityE");
        warehouse.setAddress("AddressW7");
        warehouse.setSurface(350.0);
        company.getWarehouses().add(warehouse);
        
        // Create Supplier "S6"
        Supplier supplierS6 = CompanyFactory.eINSTANCE.createSupplier();
        supplierS6.setName("S6");
        supplierS6.setAddress("AddressS6");
        
        // Create Supplier "S7"
        Supplier supplierS7 = CompanyFactory.eINSTANCE.createSupplier();
        supplierS7.setName("S7");
        supplierS7.setAddress("AddressS7");
        
        // Create Product "Item1" from Supplier "S6"
        Product item1 = CompanyFactory.eINSTANCE.createProduct();
        item1.setName("Item1");
        item1.setToxic(false);
        item1.setSupplier(supplierS6);
        
        // Create Product "Item2" from Supplier "S7"
        Product item2 = CompanyFactory.eINSTANCE.createProduct();
        item2.setName("Item2");
        item2.setToxic(false);
        item2.setSupplier(supplierS7);
        
        // Create Product "Item3" from Supplier "S6" (duplicate supplier)
        Product item3 = CompanyFactory.eINSTANCE.createProduct();
        item3.setName("Item3");
        item3.setToxic(false);
        item3.setSupplier(supplierS6);
        
        // Create ProductOccupations and add to warehouse
        ProductOccupation po1 = CompanyFactory.eINSTANCE.createProductOccupation();
        po1.setProduct(item1);
        po1.setVolume(10.0);
        warehouse.getOccupations().add(po1);
        
        ProductOccupation po2 = CompanyFactory.eINSTANCE.createProductOccupation();
        po2.setProduct(item2);
        po2.setVolume(15.0);
        warehouse.getOccupations().add(po2);
        
        ProductOccupation po3 = CompanyFactory.eINSTANCE.createProductOccupation();
        po3.setProduct(item3);
        po3.setVolume(20.0);
        warehouse.getOccupations().add(po3);
        
        // Action: Retrieve unique suppliers for W7
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S6", "S7"]
        assertEquals(2, result.size());
        assertTrue(result.contains(supplierS6));
        assertTrue(result.contains(supplierS7));
    }
    
    @Test
    public void testCase2_warehouseWithSingleProduct() {
        // Create Warehouse "W8" in "CityF" with surface 250m², in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityF");
        warehouse.setAddress("AddressW8");
        warehouse.setSurface(250.0);
        company.getWarehouses().add(warehouse);
        
        // Create Supplier "S8"
        Supplier supplierS8 = CompanyFactory.eINSTANCE.createSupplier();
        supplierS8.setName("S8");
        supplierS8.setAddress("AddressS8");
        
        // Create Product "Item4" from Supplier "S8"
        Product item4 = CompanyFactory.eINSTANCE.createProduct();
        item4.setName("Item4");
        item4.setToxic(false);
        item4.setSupplier(supplierS8);
        
        // Create ProductOccupation and add to warehouse
        ProductOccupation po = CompanyFactory.eINSTANCE.createProductOccupation();
        po.setProduct(item4);
        po.setVolume(25.0);
        warehouse.getOccupations().add(po);
        
        // Action: Retrieve unique suppliers for W8
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S8"]
        assertEquals(1, result.size());
        assertEquals(supplierS8, result.get(0));
    }
    
    @Test
    public void testCase3_emptyWarehouse() {
        // Create Warehouse "W9" in "CityG" with surface 150m² (no products), in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityG");
        warehouse.setAddress("AddressW9");
        warehouse.setSurface(150.0);
        company.getWarehouses().add(warehouse);
        
        // Action: Retrieve unique suppliers for W9
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_nonExistentWarehouse() {
        // Initialize company C1 with no warehouses
        // (Company is already initialized in setUp with no warehouses)
        
        // Since we cannot call retrieveUniqueSuppliers() on a non-existent warehouse,
        // we interpret this as trying to get suppliers from a warehouse that doesn't exist in the company
        // We'll create a warehouse but don't add it to the company
        
        Warehouse warehouseW10 = CompanyFactory.eINSTANCE.createWarehouse();
        warehouseW10.setCity("CityI");
        warehouseW10.setAddress("AddressW10");
        warehouseW10.setSurface(200.0);
        // Note: Not adding to company.getWarehouses()
        
        // Action: Retrieve unique suppliers for W10 (which is not in the company)
        EList<Supplier> result = warehouseW10.retrieveUniqueSuppliers();
        
        // Expected Output: [] (since there are no occupations)
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase5_warehouseWithProductsFromSameSupplier() {
        // Create Warehouse "W11" in "CityH" with surface 300m², in company C1
        Warehouse warehouse = CompanyFactory.eINSTANCE.createWarehouse();
        warehouse.setCity("CityH");
        warehouse.setAddress("AddressW11");
        warehouse.setSurface(300.0);
        company.getWarehouses().add(warehouse);
        
        // Create Supplier "S9"
        Supplier supplierS9 = CompanyFactory.eINSTANCE.createSupplier();
        supplierS9.setName("S9");
        supplierS9.setAddress("AddressS9");
        
        // Create Product "A1" from Supplier "S9"
        Product a1 = CompanyFactory.eINSTANCE.createProduct();
        a1.setName("A1");
        a1.setToxic(false);
        a1.setSupplier(supplierS9);
        
        // Create Product "A2" from Supplier "S9"
        Product a2 = CompanyFactory.eINSTANCE.createProduct();
        a2.setName("A2");
        a2.setToxic(false);
        a2.setSupplier(supplierS9);
        
        // Create ProductOccupations and add to warehouse
        ProductOccupation po1 = CompanyFactory.eINSTANCE.createProductOccupation();
        po1.setProduct(a1);
        po1.setVolume(30.0);
        warehouse.getOccupations().add(po1);
        
        ProductOccupation po2 = CompanyFactory.eINSTANCE.createProductOccupation();
        po2.setProduct(a2);
        po2.setVolume(35.0);
        warehouse.getOccupations().add(po2);
        
        // Action: Retrieve unique suppliers for W11
        EList<Supplier> result = warehouse.retrieveUniqueSuppliers();
        
        // Expected Output: ["S9"]
        assertEquals(1, result.size());
        assertEquals(supplierS9, result.get(0));
    }
}