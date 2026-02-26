import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Manager managerA;
    private Manager managerB;
    private Manager managerM1;
    private Manager managerM2;
    private Worker workerW1;
    private SalesPeople salesPersonS1;
    private SalesPeople salesPersonS2;
    private SalesPeople salesPersonS3;
    private ShiftWorker shiftWorker;
    private OffShiftWorker offShiftWorker;
    
    @Before
    public void setUp() {
        // Initialize test objects
        managerA = new Manager();
        managerB = new Manager();
        managerM1 = new Manager();
        managerM2 = new Manager();
        workerW1 = new Worker() {}; // Anonymous subclass to instantiate abstract class
        salesPersonS1 = new SalesPeople();
        salesPersonS2 = new SalesPeople();
        salesPersonS3 = new SalesPeople();
        shiftWorker = new ShiftWorker();
        offShiftWorker = new OffShiftWorker();
    }
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 with 3 direct subordinate employees
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        subordinates.add(salesPersonS1);
        subordinates.add(managerM2);
        managerM1.setSubordinates(subordinates);
        
        // Verify the number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3", 
                     3, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates (empty list)
        managerM1.setSubordinates(new ArrayList<Employee>());
        
        // Verify the number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0", 
                     0, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentSubordinates() {
        // Setup: Manager A has 3 subordinates
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(shiftWorker);
        subordinatesA.add(offShiftWorker);
        subordinatesA.add(salesPersonS1);
        managerA.setSubordinates(subordinatesA);
        
        // Setup: Manager B has 7 subordinates
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        managerB.setSubordinates(subordinatesB);
        
        // Verify counts for both managers
        assertEquals("The number of subordinates for Manager A is 3", 
                     3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 7", 
                     7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 subordinate
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        managerM1.setSubordinates(subordinates);
        
        // Verify the number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1", 
                     1, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_nestedManagersWithDifferentSubordinates() {
        // Setup: Manager B has 1 subordinate
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(workerW1);
        managerB.setSubordinates(subordinatesB);
        
        // Setup: Manager A has 5 subordinates including Manager B
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(workerW1);
        subordinatesA.add(salesPersonS1);
        subordinatesA.add(salesPersonS2);
        subordinatesA.add(salesPersonS3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Verify counts for both managers
        assertEquals("The number of subordinates for Manager A is 5", 
                     5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 1", 
                     1, managerB.getDirectSubordinateEmployeesCount());
    }
}