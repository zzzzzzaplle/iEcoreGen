import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

// Assuming these classes exist based on the test requirements
class Company {
    private List<Warehouse> warehouses;
    private String name;
    
    public Company(String name) {
        this.name = name;
        this.warehouses = new ArrayList<>();
    }
    
    public void addWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }
    
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }
    
    public List<Location> findProductLocations(String productName) {
        List<Location> locations = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.hasProduct(productName)) {
                locations.add(new Location(warehouse.getCity(), warehouse.getAddress()));
            }
        }
        return locations;
    }
}

class Warehouse {
    private String name;
    private String city;
    private String address;
    private List<String> products;
    
    public Warehouse(String name, String city, String address) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.products = new ArrayList<>();
    }
    
    public void addProduct(String product) {
        products.add(product);
    }
    
    public boolean hasProduct(String product) {
        return products.contains(product);
    }
    
    public String getCity() {
        return city;
    }
    
    public String getAddress() {
        return address;
    }
}

class Location {
    private String city;
    private String address;
    
    public Location(String city, String address) {
        this.city = city;
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getAddress() {
        return address;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Location location = (Location) obj;
        return city.equals(location.city) && address.equals(location.address);
    }
    
    @Override
    public String toString() {
        return "city:" + city + ",address:" + address;
    }
}

public class CR5Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company("C1");
    }
    
    @Test
    public void testCase1_productInMultipleWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W22" in "CityM", "W23" in "CityN"], in company C1.
        Warehouse w22 = new Warehouse("W22", "CityM", "W22_addr");
        Warehouse w23 = new Warehouse("W23", "CityN", "W23_addr");
        company.addWarehouse(w22);
        company.addWarehouse(w23);
        
        // 2. Add Product "MaterialX" to both W22 and W23.
        w22.addProduct("MaterialX");
        w23.addProduct("MaterialX");
        
        // Action: Find locations of "MaterialX" in C1.
        List<Location> locations = company.findProductLocations("MaterialX");
        
        // Expected Output: 
        // - "city":"CityM","address":"W22_addr"
        // - "city":"CityN","address":"W23_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains(new Location("CityM", "W22_addr")));
        assertTrue(locations.contains(new Location("CityN", "W23_addr")));
    }
    
    @Test
    public void testCase2_productInSingleWarehouse() {
        // SetUp:
        // 1. Create Warehouse "W24" in "CityO", in company C1.
        Warehouse w24 = new Warehouse("W24", "CityO", "W24_addr");
        company.addWarehouse(w24);
        
        // 2. Add Product "DeviceY" to W24.
        w24.addProduct("DeviceY");
        
        // Action: Find locations of "DeviceY" in C1.
        List<Location> locations = company.findProductLocations("DeviceY");
        
        // Expected Output: 
        // - "city":"CityO","address":"W24_addr"
        assertEquals(1, locations.size());
        assertEquals(new Location("CityO", "W24_addr"), locations.get(0));
    }
    
    @Test
    public void testCase3_nonExistentProduct() {
        // SetUp:
        // 1. Initialize system with Warehouse "W25" (in company C1) containing Product "PartZ".
        Warehouse w25 = new Warehouse("W25", "CityP", "W25_addr");
        company.addWarehouse(w25);
        w25.addProduct("PartZ");
        
        // Action: Find locations of "ToolW" in C1.
        List<Location> locations = company.findProductLocations("ToolW");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase4_productInNoWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W26", "W27"] with other products. 
        Warehouse w26 = new Warehouse("W26", "CityR", "W26_addr");
        Warehouse w27 = new Warehouse("W27", "CityS", "W27_addr");
        company.addWarehouse(w26);
        company.addWarehouse(w27);
        
        // 2. Add other products to warehouses
        w26.addProduct("OtherProduct1");
        w27.addProduct("OtherProduct2");
        
        // Action: Find locations of "ItemV" in C1.
        List<Location> locations = company.findProductLocations("ItemV");
        
        // Expected Output: []
        assertTrue(locations.isEmpty());
    }
    
    @Test
    public void testCase5_productInAllWarehouses() {
        // SetUp:
        // 1. Create Warehouses ["W28" in "CityP", "W29" in "CityQ"], in company C1.
        Warehouse w28 = new Warehouse("W28", "CityP", "W28_addr");
        Warehouse w29 = new Warehouse("W29", "CityQ", "W29_addr");
        company.addWarehouse(w28);
        company.addWarehouse(w29);
        
        // 2. Add Product "CommonPart" to both warehouses.
        w28.addProduct("CommonPart");
        w29.addProduct("CommonPart");
        
        // Action: Find locations of "CommonPart" in C1.
        List<Location> locations = company.findProductLocations("CommonPart");
        
        // Expected Output: 
        // - "city":"CityP","address":"W28_addr"
        // - "city":"CityQ","address":"W29_addr"
        assertEquals(2, locations.size());
        assertTrue(locations.contains(new Location("CityP", "W28_addr")));
        assertTrue(locations.contains(new Location("CityQ", "W29_addr")));
    }
}