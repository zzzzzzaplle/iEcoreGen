import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager manager = new Manager("IT", "John Doe", LocalDate.of(1980, 1, 15), "123-45-6789", 50000);
        
        // Create subordinates: 1 Worker, 1 SalesPerson and 1 Manager
        Worker worker = new Worker("IT", "Alice Smith", LocalDate.of(1990, 5, 20), "987-65-4321", 40, 25.0, false);
        SalesPerson salesPerson = new SalesPerson("Sales", "Bob Johnson", LocalDate.of(1985, 8, 10), "456-78-9012", 30000, 50000, 0.1);
        Manager subordinateManager = new Manager("HR", "Carol Davis", LocalDate.of(1975, 3, 25), "789-01-2345", 45000);
        
        // Assign subordinates to manager
        manager.addSubordinate(worker);
        manager.addSubordinate(salesPerson);
        manager.addSubordinate(subordinateManager);
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Get number of direct subordinates for each manager
        List<Integer> subordinatesCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify the number of direct subordinates for Manager M1 is 3
        assertEquals(1, subordinatesCount.size());
        assertEquals(Integer.valueOf(3), subordinatesCount.get(0));
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager manager = new Manager("Finance", "Jane Wilson", LocalDate.of(1978, 12, 5), "321-54-9876", 55000);
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Get number of direct subordinates for each manager
        List<Integer> subordinatesCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify the number of subordinates for the manager is 0
        assertEquals(1, subordinatesCount.size());
        assertEquals(Integer.valueOf(0), subordinatesCount.get(0));
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A with 3 subordinates
        Manager managerA = new Manager("Operations", "Manager A", LocalDate.of(1970, 6, 15), "111-11-1111", 60000);
        
        // Create subordinates for Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPerson
        Worker shiftWorker = new Worker("Delivery", "Shift Worker", LocalDate.of(1992, 2, 28), "222-22-2222", 35, 20.0, true);
        Worker offShiftWorker = new Worker("Production", "OffShift Worker", LocalDate.of(1991, 7, 12), "333-33-3333", 40, 18.0, false);
        SalesPerson salesPerson = new SalesPerson("Sales", "Sales Person", LocalDate.of(1988, 9, 5), "444-44-4444", 35000, 60000, 0.08);
        
        managerA.addSubordinate(shiftWorker);
        managerA.addSubordinate(offShiftWorker);
        managerA.addSubordinate(salesPerson);
        
        // Create Manager B with 7 subordinates
        Manager managerB = new Manager("Marketing", "Manager B", LocalDate.of(1972, 11, 30), "555-55-5555", 58000);
        
        // Create subordinates for Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPerson
        for (int i = 1; i <= 2; i++) {
            Worker sw = new Worker("Delivery", "Shift Worker " + i, LocalDate.of(1993, 3, i), "666-0" + i + "-6666", 38, 22.0, true);
            Worker osw = new Worker("Production", "OffShift Worker " + i, LocalDate.of(1994, 4, i), "777-0" + i + "-7777", 42, 19.0, false);
            managerB.addSubordinate(sw);
            managerB.addSubordinate(osw);
        }
        
        for (int i = 1; i <= 3; i++) {
            SalesPerson sp = new SalesPerson("Sales", "Sales Person " + i, LocalDate.of(1989, 5, i), "888-0" + i + "-8888", 32000, 70000, 0.09);
            managerB.addSubordinate(sp);
        }
        
        // Add both managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get number of direct subordinates for each manager
        List<Integer> subordinatesCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify the number of subordinates for Manager A is 3 and Manager B is 7
        assertEquals(2, subordinatesCount.size());
        assertTrue(subordinatesCount.contains(3));
        assertTrue(subordinatesCount.contains(7));
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Create Manager M1
        Manager manager = new Manager("IT", "Mike Brown", LocalDate.of(1983, 4, 18), "999-88-7777", 52000);
        
        // Create subordinate: 1 Worker
        Worker worker = new Worker("IT", "Worker W1", LocalDate.of(1995, 10, 8), "111-99-8888", 40, 23.0, false);
        
        // Assign subordinate to manager
        manager.addSubordinate(worker);
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Get number of direct subordinates for each manager
        List<Integer> subordinatesCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify the number of subordinates for the manager is 1
        assertEquals(1, subordinatesCount.size());
        assertEquals(Integer.valueOf(1), subordinatesCount.get(0));
    }
    
    @Test
    public void testCase5_multipleManagersWithHierarchicalSubordinates() {
        // Create Manager A
        Manager managerA = new Manager("Executive", "Manager A", LocalDate.of(1965, 1, 1), "123-12-1234", 80000);
        
        // Create subordinates for Manager A: 1 Worker, 3 SalesPerson, and 1 Manager B
        Worker workerW1 = new Worker("Operations", "Worker W1", LocalDate.of(1990, 1, 1), "234-23-2345", 40, 25.0, false);
        SalesPerson salesPersonS1 = new SalesPerson("Sales", "SalesPerson S1", LocalDate.of(1985, 1, 1), "345-34-3456", 40000, 100000, 0.1);
        SalesPerson salesPersonS2 = new SalesPerson("Sales", "SalesPerson S2", LocalDate.of(1986, 1, 1), "456-45-4567", 40000, 120000, 0.1);
        SalesPerson salesPersonS3 = new SalesPerson("Sales", "SalesPerson S3", LocalDate.of(1987, 1, 1), "567-56-5678", 40000, 90000, 0.1);
        Manager managerB = new Manager("Middle Management", "Manager B", LocalDate.of(1975, 1, 1), "678-67-6789", 60000);
        
        managerA.addSubordinate(workerW1);
        managerA.addSubordinate(salesPersonS1);
        managerA.addSubordinate(salesPersonS2);
        managerA.addSubordinate(salesPersonS3);
        managerA.addSubordinate(managerB);
        
        // Create subordinate for Manager B: 1 Worker
        Worker workerW1ForB = new Worker("Operations", "Worker W1", LocalDate.of(1992, 1, 1), "789-78-7890", 35, 22.0, true);
        managerB.addSubordinate(workerW1ForB);
        
        // Add both managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get number of direct subordinates for each manager
        List<Integer> subordinatesCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify the number of subordinates for Manager A is 5 and Manager B is 1
        assertEquals(2, subordinatesCount.size());
        assertTrue(subordinatesCount.contains(5));
        assertTrue(subordinatesCount.contains(1));
    }
}