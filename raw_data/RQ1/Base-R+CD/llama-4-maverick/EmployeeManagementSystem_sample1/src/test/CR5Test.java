import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private Manager managerA;
    private Manager managerB;
    private ShiftWorker shiftWorker;
    private OffShiftWorker offShiftWorker;
    private SalesPeople salesPerson;
    private Worker worker;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        managerA = new Manager();
        managerB = new Manager();
        shiftWorker = new ShiftWorker();
        offShiftWorker = new OffShiftWorker();
        salesPerson = new SalesPeople();
        worker = new OffShiftWorker(); // Using OffShiftWorker as concrete Worker implementation
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign 3 direct subordinates
        Manager m1 = new Manager();
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(worker);
        subordinates.add(salesPerson);
        subordinates.add(managerB); // Manager can be subordinate to another manager
        m1.setSubordinates(subordinates);
        
        // Verify the number of direct subordinates is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 
                     3, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setSubordinates(new ArrayList<Employee>()); // Empty list
        
        // Verify the number of subordinates is 0
        assertEquals("The number of subordinates for the manager is 0.", 
                     0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates
        Manager managerA = new Manager();
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(shiftWorker);
        subordinatesA.add(offShiftWorker);
        subordinatesA.add(salesPerson);
        managerA.setSubordinates(subordinatesA);
        
        // Setup: Create Manager B with 7 subordinates
        Manager managerB = new Manager();
        List<Employee> subordinatesB = new ArrayList<>();
        // Add 2 ShiftWorkers
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new ShiftWorker());
        // Add 2 OffShiftWorkers
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        // Add 3 SalesPeople
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        managerB.setSubordinates(subordinatesB);
        
        // Verify both managers have correct subordinate counts
        assertEquals("The number of subordinates for Manager A is 3.", 
                     3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 7.", 
                     7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 subordinate
        Manager m1 = new Manager();
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(worker);
        m1.setSubordinates(subordinates);
        
        // Verify the number of subordinates is 1
        assertEquals("The number of subordinates for the manager is 1.", 
                     1, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Create Manager B with 1 subordinate
        Manager managerB = new Manager();
        List<Employee> subordinatesB = new ArrayList<>();
        Worker w1 = new OffShiftWorker();
        subordinatesB.add(w1);
        managerB.setSubordinates(subordinatesB);
        
        // Setup: Create Manager A with 5 subordinates including Manager B
        Manager managerA = new Manager();
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(w1); // Worker W1
        // Add 3 SalesPeople
        subordinatesA.add(new SalesPeople());
        subordinatesA.add(new SalesPeople());
        subordinatesA.add(new SalesPeople());
        subordinatesA.add(managerB); // Manager B as subordinate
        managerA.setSubordinates(subordinatesA);
        
        // Verify both managers have correct subordinate counts
        assertEquals("The number of subordinates for Manager A is 5.", 
                     5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 1.", 
                     1, managerB.getDirectSubordinateEmployeesCount());
    }
}