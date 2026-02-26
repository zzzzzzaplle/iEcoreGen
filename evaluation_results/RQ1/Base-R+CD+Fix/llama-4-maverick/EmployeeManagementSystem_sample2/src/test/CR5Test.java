import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private Manager managerM1;
    private Manager managerM2;
    private Manager managerA;
    private Manager managerB;
    private Worker workerW1;
    private SalesPeople salesPersonS1;
    private SalesPeople salesPersonS2;
    private SalesPeople salesPersonS3;
    private ShiftWorker shiftWorker1;
    private ShiftWorker shiftWorker2;
    private OffShiftWorker offShiftWorker1;
    private OffShiftWorker offShiftWorker2;
    
    @Before
    public void setUp() throws Exception {
        // Initialize test objects
        managerM1 = new Manager();
        managerM2 = new Manager();
        managerA = new Manager();
        managerB = new Manager();
        
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
        // Setup: Create Manager M1 with 3 direct subordinates
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        subordinates.add(salesPersonS1);
        subordinates.add(managerM2);
        managerM1.setSubordinates(subordinates);
        
        // Verify expected output
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 
                     3, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        managerM1.setSubordinates(new ArrayList<Employee>());
        
        // Verify expected output
        assertEquals("The number of subordinates for the manager is 0.", 
                     0, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(shiftWorker1);
        subordinatesA.add(offShiftWorker1);
        subordinatesA.add(salesPersonS1);
        managerA.setSubordinates(subordinatesA);
        
        // Setup: Create Manager B with 7 subordinates
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(shiftWorker1);
        subordinatesB.add(shiftWorker2);
        subordinatesB.add(offShiftWorker1);
        subordinatesB.add(offShiftWorker2);
        subordinatesB.add(salesPersonS1);
        subordinatesB.add(salesPersonS2);
        subordinatesB.add(salesPersonS3);
        managerB.setSubordinates(subordinatesB);
        
        // Verify expected output for both managers
        assertEquals("The number of subordinates for Manager A is 3.", 
                     3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 7.", 
                     7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 subordinate
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        managerM1.setSubordinates(subordinates);
        
        // Verify expected output
        assertEquals("The number of subordinates for the manager is 1.", 
                     1, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Create Manager B with 1 subordinate
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(workerW1);
        managerB.setSubordinates(subordinatesB);
        
        // Setup: Create Manager A with 5 subordinates including Manager B
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(workerW1);
        subordinatesA.add(salesPersonS1);
        subordinatesA.add(salesPersonS2);
        subordinatesA.add(salesPersonS3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Verify expected output for both managers
        assertEquals("The number of subordinates for Manager A is 5.", 
                     5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 1.", 
                     1, managerB.getDirectSubordinateEmployeesCount());
    }
}