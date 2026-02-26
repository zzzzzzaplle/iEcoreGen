import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 with 3 direct subordinates (1 Worker, 1 SalesPerson, 1 Manager)
        Manager m1 = new Manager();
        m1.setName("M1");
        
        Worker w1 = new Worker();
        SalesPerson s1 = new SalesPerson();
        Manager m2 = new Manager();
        
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        company.addEmployee(m1);
        
        // Execute: Get number of direct subordinates for each manager
        List<String> result = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify: Manager M1 should have 3 direct subordinates
        assertEquals(1, result.size());
        assertEquals("M1: 3", result.get(0));
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setName("M1");
        
        company.addEmployee(m1);
        
        // Execute: Get number of direct subordinates for each manager
        List<String> result = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify: Manager M1 should have 0 direct subordinates
        assertEquals(1, result.size());
        assertEquals("M1: 0", result.get(0));
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates and Manager B with 7 subordinates
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinates for Manager A (3 subordinates)
        Worker shiftWorkerA = new Worker();
        shiftWorkerA.setShiftWorker(true);
        
        Worker offShiftWorkerA = new Worker();
        offShiftWorkerA.setShiftWorker(false);
        
        SalesPerson salesPersonA = new SalesPerson();
        
        managerA.addSubordinate(shiftWorkerA);
        managerA.addSubordinate(offShiftWorkerA);
        managerA.addSubordinate(salesPersonA);
        
        // Create subordinates for Manager B (7 subordinates)
        for (int i = 0; i < 2; i++) {
            Worker shiftWorker = new Worker();
            shiftWorker.setShiftWorker(true);
            managerB.addSubordinate(shiftWorker);
        }
        
        for (int i = 0; i < 2; i++) {
            Worker offShiftWorker = new Worker();
            offShiftWorker.setShiftWorker(false);
            managerB.addSubordinate(offShiftWorker);
        }
        
        for (int i = 0; i < 3; i++) {
            SalesPerson salesPerson = new SalesPerson();
            managerB.addSubordinate(salesPerson);
        }
        
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Execute: Get number of direct subordinates for each manager
        List<String> result = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify: Both managers should have correct number of subordinates
        assertEquals(2, result.size());
        assertTrue(result.contains("Manager A: 3"));
        assertTrue(result.contains("Manager B: 7"));
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 direct subordinate (1 Worker)
        Manager m1 = new Manager();
        m1.setName("M1");
        
        Worker w1 = new Worker();
        m1.addSubordinate(w1);
        
        company.addEmployee(m1);
        
        // Execute: Get number of direct subordinates for each manager
        List<String> result = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify: Manager M1 should have 1 direct subordinate
        assertEquals(1, result.size());
        assertEquals("M1: 1", result.get(0));
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Manager A has 5 subordinates, Manager B has 1 subordinate
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinates for Manager A
        Worker w1 = new Worker();
        SalesPerson s1 = new SalesPerson();
        SalesPerson s2 = new SalesPerson();
        SalesPerson s3 = new SalesPerson();
        
        managerA.addSubordinate(w1);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Create subordinate for Manager B
        Worker w2 = new Worker();
        managerB.addSubordinate(w2);
        
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Execute: Get number of direct subordinates for each manager
        List<String> result = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify: Both managers should have correct number of subordinates
        assertEquals(2, result.size());
        assertTrue(result.contains("Manager A: 5"));
        assertTrue(result.contains("Manager B: 1"));
    }
}