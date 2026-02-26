import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Manager managerM1;
    private Manager managerM2;
    private Manager managerA;
    private Manager managerB;
    private Worker workerW1;
    private SalesPeople salesPersonS1;
    private ShiftWorker shiftWorker1;
    private OffShiftWorker offShiftWorker1;
    private SalesPeople salesPerson1;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        managerM1 = new Manager();
        managerM2 = new Manager();
        managerA = new Manager();
        managerB = new Manager();
        workerW1 = new Worker() {}; // Anonymous subclass since Worker is abstract
        salesPersonS1 = new SalesPeople();
        shiftWorker1 = new ShiftWorker();
        offShiftWorker1 = new OffShiftWorker();
        salesPerson1 = new SalesPeople();
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
        assertEquals("The number of direct subordinates for Manager M1 is 3", 3, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates (default empty list)
        // No need to explicitly set subordinates as constructor initializes empty list
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0", 0, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople)
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(shiftWorker1);
        subordinatesA.add(offShiftWorker1);
        subordinatesA.add(salesPerson1);
        managerA.setSubordinates(subordinatesA);
        
        // Setup: Create Manager B with 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        List<Employee> subordinatesB = new ArrayList<>();
        // Create additional instances for Manager B's subordinates
        ShiftWorker shiftWorker2 = new ShiftWorker();
        OffShiftWorker offShiftWorker2 = new OffShiftWorker();
        SalesPeople salesPerson2 = new SalesPeople();
        SalesPeople salesPerson3 = new SalesPeople();
        
        subordinatesB.add(shiftWorker1);
        subordinatesB.add(shiftWorker2);
        subordinatesB.add(offShiftWorker1);
        subordinatesB.add(offShiftWorker2);
        subordinatesB.add(salesPerson1);
        subordinatesB.add(salesPerson2);
        subordinatesB.add(salesPerson3);
        managerB.setSubordinates(subordinatesB);
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7
        assertEquals("The number of subordinates for Manager A is 3", 3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 7", 7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 and assign Worker W1 as subordinate
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        managerM1.setSubordinates(subordinates);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1", 1, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Create Manager A with 5 subordinates (1 Worker, 3 SalesPerson, 1 Manager B)
        Worker workerForA = new Worker() {};
        SalesPeople salesPerson1 = new SalesPeople();
        SalesPeople salesPerson2 = new SalesPeople();
        SalesPeople salesPerson3 = new SalesPeople();
        
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(workerForA);
        subordinatesA.add(salesPerson1);
        subordinatesA.add(salesPerson2);
        subordinatesA.add(salesPerson3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Setup: Create Manager B with 1 subordinate (Worker W1)
        Worker workerForB = new Worker() {};
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(workerForB);
        managerB.setSubordinates(subordinatesB);
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1
        assertEquals("The number of subordinates for Manager A is 5", 5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 1", 1, managerB.getDirectSubordinateEmployeesCount());
    }
}