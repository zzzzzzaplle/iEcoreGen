import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private Manager managerA;
    private Manager managerB;
    private Manager managerM1;
    private Manager managerM2;
    private Worker workerW1;
    private ShiftWorker shiftWorker1;
    private ShiftWorker shiftWorker2;
    private OffShiftWorker offShiftWorker1;
    private OffShiftWorker offShiftWorker2;
    private SalesPeople salesPersonS1;
    private SalesPeople salesPersonS2;
    private SalesPeople salesPersonS3;
    
    @Before
    public void setUp() {
        // Initialize test objects that can be reused across tests
        managerA = new Manager();
        managerB = new Manager();
        managerM1 = new Manager();
        managerM2 = new Manager();
        workerW1 = new Worker() {}; // Anonymous subclass since Worker is abstract
        shiftWorker1 = new ShiftWorker();
        shiftWorker2 = new ShiftWorker();
        offShiftWorker1 = new OffShiftWorker();
        offShiftWorker2 = new OffShiftWorker();
        salesPersonS1 = new SalesPeople();
        salesPersonS2 = new SalesPeople();
        salesPersonS3 = new SalesPeople();
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        subordinates.add(salesPersonS1);
        subordinates.add(managerM2);
        managerM1.setSubordinates(subordinates);
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        int expectedCount = 3;
        int actualCount = managerM1.getDirectSubordinateEmployeesCount();
        
        assertEquals("Manager M1 should have 3 direct subordinates", expectedCount, actualCount);
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        managerM1.setSubordinates(new ArrayList<Employee>());
        
        // Expected Output: The number of subordinates for the manager is 0
        int expectedCount = 0;
        int actualCount = managerM1.getDirectSubordinateEmployeesCount();
        
        assertEquals("Manager M1 should have 0 subordinates", expectedCount, actualCount);
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Manager A with 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople)
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(shiftWorker1);
        subordinatesA.add(offShiftWorker1);
        subordinatesA.add(salesPersonS1);
        managerA.setSubordinates(subordinatesA);
        
        // Setup: Manager B with 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(shiftWorker1);
        subordinatesB.add(shiftWorker2);
        subordinatesB.add(offShiftWorker1);
        subordinatesB.add(offShiftWorker2);
        subordinatesB.add(salesPersonS1);
        subordinatesB.add(salesPersonS2);
        subordinatesB.add(salesPersonS3);
        managerB.setSubordinates(subordinatesB);
        
        // Expected Output: Manager A has 3 subordinates, Manager B has 7 subordinates
        int expectedCountA = 3;
        int expectedCountB = 7;
        int actualCountA = managerA.getDirectSubordinateEmployeesCount();
        int actualCountB = managerB.getDirectSubordinateEmployeesCount();
        
        assertEquals("Manager A should have 3 subordinates", expectedCountA, actualCountA);
        assertEquals("Manager B should have 7 subordinates", expectedCountB, actualCountB);
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 subordinate (Worker W1)
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        managerM1.setSubordinates(subordinates);
        
        // Expected Output: The number of subordinates for the manager is 1
        int expectedCount = 1;
        int actualCount = managerM1.getDirectSubordinateEmployeesCount();
        
        assertEquals("Manager M1 should have 1 subordinate", expectedCount, actualCount);
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Manager B with 1 subordinate (Worker W1)
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(workerW1);
        managerB.setSubordinates(subordinatesB);
        
        // Setup: Manager A with 5 subordinates (1 Worker, 3 SalesPerson, 1 Manager B)
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(workerW1);
        subordinatesA.add(salesPersonS1);
        subordinatesA.add(salesPersonS2);
        subordinatesA.add(salesPersonS3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Expected Output: Manager A has 5 subordinates, Manager B has 1 subordinate
        int expectedCountA = 5;
        int expectedCountB = 1;
        int actualCountA = managerA.getDirectSubordinateEmployeesCount();
        int actualCountB = managerB.getDirectSubordinateEmployeesCount();
        
        assertEquals("Manager A should have 5 subordinates", expectedCountA, actualCountA);
        assertEquals("Manager B should have 1 subordinate", expectedCountB, actualCountB);
    }
}