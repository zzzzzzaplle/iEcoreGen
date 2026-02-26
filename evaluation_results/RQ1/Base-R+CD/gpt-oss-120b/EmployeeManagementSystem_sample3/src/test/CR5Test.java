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
    private ShiftWorker shiftWorker1;
    private ShiftWorker shiftWorker2;
    private OffShiftWorker offShiftWorker1;
    private OffShiftWorker offShiftWorker2;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        managerA = new Manager();
        managerB = new Manager();
        managerM1 = new Manager();
        managerM2 = new Manager();
        workerW1 = new OffShiftWorker();
        salesPersonS1 = new SalesPeople();
        salesPersonS2 = new SalesPeople();
        salesPersonS3 = new SalesPeople();
        shiftWorker1 = new ShiftWorker();
        shiftWorker2 = new ShiftWorker();
        offShiftWorker1 = new OffShiftWorker();
        offShiftWorker2 = new OffShiftWorker();
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        managerM1.addSubordinate(workerW1);
        managerM1.addSubordinate(salesPersonS1);
        managerM1.addSubordinate(managerM2);
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 should be 3", 
                     3, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 without assigning any subordinates
        // No subordinates added to managerM1
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager should be 0", 
                     0, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A and assign 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople)
        managerA.addSubordinate(shiftWorker1);
        managerA.addSubordinate(offShiftWorker1);
        managerA.addSubordinate(salesPersonS1);
        
        // Setup: Create Manager B and assign 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        managerB.addSubordinate(shiftWorker1);
        managerB.addSubordinate(shiftWorker2);
        managerB.addSubordinate(offShiftWorker1);
        managerB.addSubordinate(offShiftWorker2);
        managerB.addSubordinate(salesPersonS1);
        managerB.addSubordinate(salesPersonS2);
        managerB.addSubordinate(salesPersonS3);
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7
        assertEquals("The number of subordinates for Manager A should be 3", 
                     3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 7", 
                     7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 and assign Worker W1 as subordinate
        managerM1.addSubordinate(workerW1);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager should be 1", 
                     1, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithDifferentNumberOfSubordinatesComplex() {
        // Setup: Create Manager A and assign worker W1, SalesPerson (S1, S2, S3), Manager B as direct subordinates
        managerA.addSubordinate(workerW1);
        managerA.addSubordinate(salesPersonS1);
        managerA.addSubordinate(salesPersonS2);
        managerA.addSubordinate(salesPersonS3);
        managerA.addSubordinate(managerB);
        
        // Setup: Create Manager B and assign 1 subordinate employee: Worker W1
        managerB.addSubordinate(workerW1);
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1
        assertEquals("The number of subordinates for Manager A should be 5", 
                     5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 1", 
                     1, managerB.getDirectSubordinateEmployeesCount());
    }
}