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
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        subordinates.add(salesPersonS1);
        subordinates.add(managerM2);
        managerM1.setSubordinates(subordinates);
        
        // Execute: Get the number of direct subordinates for Manager M1
        int actualCount = managerM1.getDirectSubordinateEmployeesCount();
        
        // Verify: The number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 3, actualCount);
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates (default empty list)
        // No need to set subordinates as the list is initialized empty by default
        
        // Execute: Get the number of direct subordinates for Manager M1
        int actualCount = managerM1.getDirectSubordinateEmployeesCount();
        
        // Verify: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0.", 0, actualCount);
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup Manager A: Assign 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople)
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(shiftWorker1);
        subordinatesA.add(offShiftWorker1);
        subordinatesA.add(salesPersonS1);
        managerA.setSubordinates(subordinatesA);
        
        // Setup Manager B: Assign 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(shiftWorker1);
        subordinatesB.add(shiftWorker2);
        subordinatesB.add(offShiftWorker1);
        subordinatesB.add(offShiftWorker2);
        subordinatesB.add(salesPersonS1);
        subordinatesB.add(salesPersonS2);
        subordinatesB.add(salesPersonS3);
        managerB.setSubordinates(subordinatesB);
        
        // Execute: Get the number of direct subordinates for both managers
        int actualCountA = managerA.getDirectSubordinateEmployeesCount();
        int actualCountB = managerB.getDirectSubordinateEmployeesCount();
        
        // Verify: The number of subordinates for Manager A is 3 and Manager B is 7
        assertEquals("The number of subordinates for Manager A is 3.", 3, actualCountA);
        assertEquals("The number of subordinates for Manager B is 7.", 7, actualCountB);
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 and assign Worker W1 as subordinate
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        managerM1.setSubordinates(subordinates);
        
        // Execute: Get the number of direct subordinates for Manager M1
        int actualCount = managerM1.getDirectSubordinateEmployeesCount();
        
        // Verify: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1.", 1, actualCount);
    }
    
    @Test
    public void testCase5_MultipleManagersWithDifferentNumberOfSubordinatesComplex() {
        // Setup Manager A: Assign worker W1, SalesPerson (S1, S2, S3), Manager B as direct subordinates
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(workerW1);
        subordinatesA.add(salesPersonS1);
        subordinatesA.add(salesPersonS2);
        subordinatesA.add(salesPersonS3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Setup Manager B: Assign 1 subordinate employee: Worker W1
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(workerW1);
        managerB.setSubordinates(subordinatesB);
        
        // Execute: Get the number of direct subordinates for both managers
        int actualCountA = managerA.getDirectSubordinateEmployeesCount();
        int actualCountB = managerB.getDirectSubordinateEmployeesCount();
        
        // Verify: The number of subordinates for Manager A is 5 and Manager B is 1
        assertEquals("The number of subordinates for Manager A is 5.", 5, actualCountA);
        assertEquals("The number of subordinates for Manager B is 1.", 1, actualCountB);
    }
}