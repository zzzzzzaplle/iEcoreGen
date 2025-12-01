import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Manager managerA;
    private Manager managerB;
    private Worker worker1;
    private SalesPeople salesPerson1;
    private ShiftWorker shiftWorker1;
    private OffShiftWorker offShiftWorker1;
    private SalesPeople salesPerson2;
    private SalesPeople salesPerson3;
    private ShiftWorker shiftWorker2;
    private OffShiftWorker offShiftWorker2;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        managerA = new Manager();
        managerB = new Manager();
        worker1 = new OffShiftWorker();
        salesPerson1 = new SalesPeople();
        shiftWorker1 = new ShiftWorker();
        offShiftWorker1 = new OffShiftWorker();
        salesPerson2 = new SalesPeople();
        salesPerson3 = new SalesPeople();
        shiftWorker2 = new ShiftWorker();
        offShiftWorker2 = new OffShiftWorker();
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        Manager m1 = new Manager();
        Worker w1 = new OffShiftWorker();
        SalesPeople s1 = new SalesPeople();
        Manager m2 = new Manager();
        
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(w1);
        subordinates.add(s1);
        subordinates.add(m2);
        m1.setSubordinates(subordinates);
        
        // Execute: Get the number of direct subordinates for Manager M1
        int result = m1.getDirectSubordinateEmployeesCount();
        
        // Verify: The number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 should be 3", 3, result);
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setSubordinates(new ArrayList<Employee>());
        
        // Execute: Get the number of direct subordinates for Manager M1
        int result = m1.getDirectSubordinateEmployeesCount();
        
        // Verify: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager should be 0", 0, result);
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
        subordinatesB.add(shiftWorker1);
        subordinatesB.add(shiftWorker2);
        subordinatesB.add(offShiftWorker1);
        subordinatesB.add(offShiftWorker2);
        subordinatesB.add(salesPerson1);
        subordinatesB.add(salesPerson2);
        subordinatesB.add(salesPerson3);
        managerB.setSubordinates(subordinatesB);
        
        // Execute: Get the number of direct subordinates for both managers
        int resultA = managerA.getDirectSubordinateEmployeesCount();
        int resultB = managerB.getDirectSubordinateEmployeesCount();
        
        // Verify: The number of subordinates for Manager A is 3 and Manager B is 7
        assertEquals("The number of subordinates for Manager A should be 3", 3, resultA);
        assertEquals("The number of subordinates for Manager B should be 7", 7, resultB);
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 and assign Worker W1 as subordinate
        Manager m1 = new Manager();
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(worker1);
        m1.setSubordinates(subordinates);
        
        // Execute: Get the number of direct subordinates for Manager M1
        int result = m1.getDirectSubordinateEmployeesCount();
        
        // Verify: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Create Manager B and assign 1 subordinate employee: Worker W1
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(worker1);
        managerB.setSubordinates(subordinatesB);
        
        // Setup: Create Manager A and assign worker W1, SalesPerson (S1, S2, S3), Manager B as direct subordinates
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(worker1);
        subordinatesA.add(salesPerson1);
        subordinatesA.add(salesPerson2);
        subordinatesA.add(salesPerson3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Execute: Get the number of direct subordinates for both managers
        int resultA = managerA.getDirectSubordinateEmployeesCount();
        int resultB = managerB.getDirectSubordinateEmployeesCount();
        
        // Verify: The number of subordinates for Manager A is 5 and Manager B is 1
        assertEquals("The number of subordinates for Manager A should be 5", 5, resultA);
        assertEquals("The number of subordinates for Manager B should be 1", 1, resultB);
    }
}