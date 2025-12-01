import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.AbstractMap;

public class CR3Test {

    // Helper method to create a supplier with name
    private Supplier createSupplier(String name) {
        Supplier supplier = new Supplier();
        supplier.setName(name);
        return supplier;
    }

    // Helper method to create a product with name, toxicity, and supplier
    private Product createProduct(String name, boolean isToxic, Supplier supplier) {
        Product product = new Product();
        product.setName(name);
        product.setToxic(isToxic);
        product.setSupplier(supplier);
        return product;
    }

    // Helper method to create a product occupation with volume and product
    private ProductOccupation createProductOccupation(Double volume, Product product) {
        ProductOccupation occupation = new ProductOccupation();
        occupation.setVolume(volume);
        occupation.setProduct(product);
        return occupation;
    }

    // Helper method to create a warehouse with city, address, and surface
    private Warehouse createWarehouse(String city, String address, Double surface) {
        Warehouse warehouse = new Warehouse();
        warehouse.setCity(city);
        warehouse.setAddress(address);
        warehouse.setSurface(surface);
        return warehouse;
    }

    // Helper method to create a company
    private Company createCompany() {
        return new Company();
    }

    @Test
    public void testCase1_supplierWithMultipleProducts() {
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier s10 = createSupplier("S10");

        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Product p1 = createProduct("P1", false, s10);
        ProductOccupation po1 = createProductOccupation(5.0, p1);
        Warehouse w12 = createWarehouse("W12", "Address W12", 100.0);
        w12.addOccupations(po1);

        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Product p2 = createProduct("P2", false, s10);
        ProductOccupation po2 = createProductOccupation(10.0, p2);
        Warehouse w13 = createWarehouse("W13", "Address W13", 150.0);
        w13.addOccupations(po2);

        // Create company C1 and add warehouses
        Company c1 = createCompany();
        c1.addWarehouses(w12);
        c1.addWarehouses(w13);

        // Action: List volumes for supplier "S10" in company C1.
        Double result = c1.countTotalVolumeBySupplier("S10");

        // Expected Output: 15
        assertEquals(Double.valueOf(15.0), result);
    }

    @Test
    public void testCase2_supplierWithNoProducts() {
        // SetUp:
        // 1. Create company C2 without warehouse.
        Company c2 = createCompany();

        // 2. Create Supplier "S11" (no products linked).
        // Action: List volumes for supplier "S11" in company C2.
        Double result = c2.countTotalVolumeBySupplier("S11");

        // Expected Output: 0
        assertEquals(Double.valueOf(0.0), result);
    }

    @Test
    public void testCase3_nonExistentSupplier() {
        // SetUp:
        // 1. Create Supplier "S10"
        Supplier s10 = createSupplier("S10");

        // 2. Add Product "P1" (volume=5) from S10 to Warehouse "W12" in company C1.
        Product p1 = createProduct("P1", false, s10);
        ProductOccupation po1 = createProductOccupation(5.0, p1);
        Warehouse w12 = createWarehouse("W12", "Address W12", 100.0);
        w12.addOccupations(po1);

        // 3. Add Product "P2" (volume=10) from S10 to Warehouse "W13" in company C1.
        Product p2 = createProduct("P2", false, s10);
        ProductOccupation po2 = createProductOccupation(10.0, p2);
        Warehouse w13 = createWarehouse("W13", "Address W13", 150.0);
        w13.addOccupations(po2);

        // Create company C1 and add warehouses
        Company c1 = createCompany();
        c1.addWarehouses(w12);
        c1.addWarehouses(w13);

        // 4. Create Supplier "S12" (no products linked).
        // Action: List volumes for supplier name "S13" in company C1.
        Double result = c1.countTotalVolumeBySupplier("S13");

        // Expected Output: 0
        assertEquals(Double.valueOf(0.0), result);
    }

    @Test
    public void testCase4_supplierWithSingleProduct() {
        // SetUp:
        // 1. Add Supplier "S14" to system.
        Supplier s14 = createSupplier("S14");

        // 2. Add Product "P3" (volume=8) from S14 to Warehouse "W14" in company C3.
        Product p3 = createProduct("P3", false, s14);
        ProductOccupation po3 = createProductOccupation(8.0, p3);
        Warehouse w14 = createWarehouse("W14", "Address W14", 200.0);
        w14.addOccupations(po3);

        // Create company C3 and add warehouse
        Company c3 = createCompany();
        c3.addWarehouses(w14);

        // Action: List volumes for supplier "S14" in company C3.
        Double result = c3.countTotalVolumeBySupplier("S14");

        // Expected Output: 8
        assertEquals(Double.valueOf(8.0), result);
    }

    @Test
    public void testCase5_supplierWithProductsInMultipleWarehouses() {
        // SetUp:
        // 1. Add Supplier "S15" to system.
        Supplier s15 = createSupplier("S15");

        // 2. Add Product "X1" (volume=3) from S15 to Warehouse "W15", in company C4.
        Product x1 = createProduct("X1", false, s15);
        ProductOccupation pox1 = createProductOccupation(3.0, x1);
        Warehouse w15 = createWarehouse("W15", "Address W15", 120.0);
        w15.addOccupations(pox1);

        // 3. Add Product "X2" (volume=7) from S15 to Warehouse "W16", in company C4.
        Product x2 = createProduct("X2", false, s15);
        ProductOccupation pox2 = createProductOccupation(7.0, x2);
        Warehouse w16 = createWarehouse("W16", "Address W16", 180.0);
        w16.addOccupations(pox2);

        // 4. Add Product "X3" (volume=2) from S15 to Warehouse "W15", in company C4.
        Product x3 = createProduct("X3", false, s15);
        ProductOccupation pox3 = createProductOccupation(2.0, x3);
        w15.addOccupations(pox3);

        // Create company C4 and add warehouses
        Company c4 = createCompany();
        c4.addWarehouses(w15);
        c4.addWarehouses(w16);

        // Action: List volumes for supplier "S15" in company C4.
        Double result = c4.countTotalVolumeBySupplier("S15");

        // Expected Output: 12
        assertEquals(Double.valueOf(12.0), result);
    }
}