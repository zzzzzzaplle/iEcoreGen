import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private Manager manager;
    
    @Before
    public void setUp() {
        manager = new Manager();
    }
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        Manager m1 = new Manager();
        Worker w1 = new ShiftWorker(); // Using ShiftWorker as concrete Worker implementation
        SalesPeople s1 = new SalesPeople();
        Manager m2 = new Manager();
        
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        subordinates.add(s1);
        subordinates.add(m2);
        m1.setSubordinates(subordinates);
        
        // Verify the number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 3, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        
        // Verify the number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0.", 0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates and Manager B with 7 subordinates
        Manager managerA = new Manager();
        Manager managerB = new Manager();
        
        // Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(new ShiftWorker());
        subordinatesA.add(new OffShiftWorker());
        subordinatesA.add(new SalesPeople());
        managerA.setSubordinates(subordinatesA);
        
        // Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        managerB.setSubordinates(subordinatesB);
        
        // Verify the number of subordinates for Manager A is 3 and Manager B is 7
        assertEquals("The number of subordinates for Manager A is 3.", 3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 7.", 7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Setup: Create Manager M1 and assign Worker W1 as subordinate
        Manager m1 = new Manager();
        Worker w1 = new ShiftWorker(); // Using ShiftWorker as concrete Worker implementation
        
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        m1.setSubordinates(subordinates);
        
        // Verify the number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1.", 1, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_multipleManagersWithNestedSubordinates() {
        // Setup: Create Manager A with 5 subordinates and Manager B with 1 subordinate
        Manager managerA = new Manager();
        Manager managerB = new Manager();
        
        // Manager A: 1 Worker, 3 SalesPerson, 1 Manager B
        Worker w1 = new ShiftWorker();
        SalesPeople s1 = new SalesPeople();
        SalesPeople s2 = new SalesPeople();
        SalesPeople s3 = new SalesPeople();
        
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(w1);
        subordinatesA.add(s1);
        subordinatesA.add(s2);
        subordinatesA.add(s3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Manager B: 1 Worker W1
        Worker w2 = new ShiftWorker();
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(w2);
        managerB.setSubordinates(subordinatesB);
        
        // Verify the number of subordinates for Manager A is 5 and Manager B is 1
        assertEquals("The number of subordinates for Manager A is 5.", 5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 1.", 1, managerB.getDirectSubordinateEmployeesCount());
    }
}