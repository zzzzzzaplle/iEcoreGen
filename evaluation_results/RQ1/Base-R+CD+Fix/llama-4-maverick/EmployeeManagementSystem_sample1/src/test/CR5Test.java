import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private Manager manager;
    private List<Employee> subordinates;
    
    @Before
    public void setUp() {
        manager = new Manager();
        subordinates = new ArrayList<>();
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        Manager m1 = new Manager();
        Worker w1 = new ShiftWorker(); // Using ShiftWorker as concrete implementation
        SalesPeople s1 = new SalesPeople();
        Manager m2 = new Manager();
        
        List<Employee> m1Subordinates = new ArrayList<>();
        m1Subordinates.add(w1);
        m1Subordinates.add(s1);
        m1Subordinates.add(m2);
        m1.setSubordinates(m1Subordinates);
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        assertEquals("Manager M1 should have 3 direct subordinates", 3, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setSubordinates(new ArrayList<Employee>()); // Empty list
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("Manager with no subordinates should return 0", 0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates and Manager B with 7 subordinates
        Manager managerA = new Manager();
        Manager managerB = new Manager();
        
        // Manager A subordinates: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(new ShiftWorker());
        subordinatesA.add(new OffShiftWorker());
        subordinatesA.add(new SalesPeople());
        managerA.setSubordinates(subordinatesA);
        
        // Manager B subordinates: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        managerB.setSubordinates(subordinatesB);
        
        // Expected Output: Manager A has 3 subordinates, Manager B has 7 subordinates
        assertEquals("Manager A should have 3 subordinates", 3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("Manager B should have 7 subordinates", 7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 Worker subordinate
        Manager m1 = new Manager();
        Worker w1 = new ShiftWorker(); // Using ShiftWorker as concrete implementation
        
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        m1.setSubordinates(subordinates);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("Manager with single subordinate should return 1", 1, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Create Manager A with 5 subordinates and Manager B with 1 subordinate
        Manager managerA = new Manager();
        Manager managerB = new Manager();
        
        // Create subordinates
        Worker w1 = new ShiftWorker();
        SalesPeople s1 = new SalesPeople();
        SalesPeople s2 = new SalesPeople();
        SalesPeople s3 = new SalesPeople();
        
        // Manager B subordinates: 1 Worker
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(w1);
        managerB.setSubordinates(subordinatesB);
        
        // Manager A subordinates: 1 Worker, 3 SalesPerson, 1 Manager B
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(w1);
        subordinatesA.add(s1);
        subordinatesA.add(s2);
        subordinatesA.add(s3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Expected Output: Manager A has 5 subordinates, Manager B has 1 subordinate
        assertEquals("Manager A should have 5 subordinates", 5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("Manager B should have 1 subordinate", 1, managerB.getDirectSubordinateEmployeesCount());
    }
}