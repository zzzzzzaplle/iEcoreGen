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
    private ShiftWorker shiftWorker1;
    private OffShiftWorker offShiftWorker1;
    private SalesPeople salesPerson1;
    private SalesPeople salesPerson2;
    private SalesPeople salesPerson3;
    private Worker worker1;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        managerA = new Manager();
        managerB = new Manager();
        managerM1 = new Manager();
        managerM2 = new Manager();
        shiftWorker1 = new ShiftWorker();
        offShiftWorker1 = new OffShiftWorker();
        salesPerson1 = new SalesPeople();
        salesPerson2 = new SalesPeople();
        salesPerson3 = new SalesPeople();
        worker1 = new OffShiftWorker();
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        managerM1.addSubordinate(worker1);
        managerM1.addSubordinate(salesPerson1);
        managerM1.addSubordinate(managerM2);
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        assertEquals("Manager M1 should have 3 direct subordinates", 3, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        // No subordinates added to managerM1
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("Manager M1 should have 0 subordinates", 0, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople)
        managerA.addSubordinate(shiftWorker1);
        managerA.addSubordinate(offShiftWorker1);
        managerA.addSubordinate(salesPerson1);
        
        // Setup: Create Manager B with 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        ShiftWorker shiftWorker2 = new ShiftWorker();
        OffShiftWorker offShiftWorker2 = new OffShiftWorker();
        SalesPeople salesPerson4 = new SalesPeople();
        SalesPeople salesPerson5 = new SalesPeople();
        
        managerB.addSubordinate(shiftWorker1);
        managerB.addSubordinate(shiftWorker2);
        managerB.addSubordinate(offShiftWorker1);
        managerB.addSubordinate(offShiftWorker2);
        managerB.addSubordinate(salesPerson1);
        managerB.addSubordinate(salesPerson4);
        managerB.addSubordinate(salesPerson5);
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7.
        assertEquals("Manager A should have 3 subordinates", 3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("Manager B should have 7 subordinates", 7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 and assign Worker W1 as subordinate
        managerM1.addSubordinate(worker1);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("Manager M1 should have 1 subordinate", 1, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A and assign worker W1, SalesPerson (S1, S2, S3), Manager B as direct subordinates
        managerA.addSubordinate(worker1);
        managerA.addSubordinate(salesPerson1);
        managerA.addSubordinate(salesPerson2);
        managerA.addSubordinate(salesPerson3);
        managerA.addSubordinate(managerB);
        
        // Setup: Create Manager B and assign 1 subordinate employee: Worker W1
        managerB.addSubordinate(worker1);
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1.
        assertEquals("Manager A should have 5 subordinates", 5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("Manager B should have 1 subordinate", 1, managerB.getDirectSubordinateEmployeesCount());
    }
}