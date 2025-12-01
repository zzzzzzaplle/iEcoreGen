import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR5Test {
    
    private Manager managerA;
    private Manager managerB;
    private Worker worker1;
    private Worker worker2;
    private ShiftWorker shiftWorker1;
    private OffShiftWorker offShiftWorker1;
    private SalesPeople salesPerson1;
    private SalesPeople salesPerson2;
    private SalesPeople salesPerson3;
    
    @Before
    public void setUp() throws Exception {
        // Initialize test objects
        managerA = new Manager();
        managerB = new Manager();
        worker1 = new ShiftWorker();
        worker2 = new OffShiftWorker();
        shiftWorker1 = new ShiftWorker();
        offShiftWorker1 = new OffShiftWorker();
        salesPerson1 = new SalesPeople();
        salesPerson2 = new SalesPeople();
        salesPerson3 = new SalesPeople();
        
        // Set basic employee information
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        managerA.setName("Manager A");
        managerA.setBirthDate(sdf.parse("1980-01-01 00:00:00"));
        managerA.setSocialInsuranceNumber("123-45-6789");
        
        managerB.setName("Manager B");
        managerB.setBirthDate(sdf.parse("1985-05-15 00:00:00"));
        managerB.setSocialInsuranceNumber("987-65-4321");
        
        worker1.setName("Worker W1");
        worker2.setName("Worker W2");
        shiftWorker1.setName("ShiftWorker SW1");
        offShiftWorker1.setName("OffShiftWorker OSW1");
        salesPerson1.setName("SalesPerson S1");
        salesPerson2.setName("SalesPerson S2");
        salesPerson3.setName("SalesPerson S3");
    }
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 with 3 direct subordinate employees: 1 Worker, 1 SalesPerson and 1 Manager
        Manager managerM1 = new Manager();
        Worker workerW1 = new ShiftWorker();
        SalesPeople salesPersonS1 = new SalesPeople();
        Manager managerM2 = new Manager();
        
        // Assign subordinates to Manager M1
        managerM1.addSubordinate(workerW1);
        managerM1.addSubordinate(salesPersonS1);
        managerM1.addSubordinate(managerM2);
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 should be 3", 
                     3, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Setup: Create Manager M1 with no direct subordinate employees
        Manager managerM1 = new Manager();
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager should be 0", 
                     0, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople)
        managerA.addSubordinate(shiftWorker1);
        managerA.addSubordinate(offShiftWorker1);
        managerA.addSubordinate(salesPerson1);
        
        // Setup: Create Manager B with 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        ShiftWorker shiftWorker2 = new ShiftWorker();
        OffShiftWorker offShiftWorker2 = new OffShiftWorker();
        SalesPeople salesPerson4 = new SalesPeople();
        SalesPeople salesPerson5 = new SalesPeople();
        
        managerB.addSubordinate(shiftWorker1); // Reusing shiftWorker1 for simplicity
        managerB.addSubordinate(shiftWorker2);
        managerB.addSubordinate(offShiftWorker1); // Reusing offShiftWorker1 for simplicity
        managerB.addSubordinate(offShiftWorker2);
        managerB.addSubordinate(salesPerson1); // Reusing salesPerson1 for simplicity
        managerB.addSubordinate(salesPerson4);
        managerB.addSubordinate(salesPerson5);
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7.
        assertEquals("The number of subordinates for Manager A should be 3", 
                     3, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 7", 
                     7, managerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 direct subordinate employee: 1 Worker
        Manager managerM1 = new Manager();
        Worker workerW1 = new ShiftWorker();
        
        managerM1.addSubordinate(workerW1);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager should be 1", 
                     1, managerM1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_multipleManagersWithNestedSubordinates() {
        // Setup: Create Manager A with 5 subordinate employees: 1 Worker, 3 SalesPerson and 1 Manager B
        managerA.addSubordinate(worker1);
        managerA.addSubordinate(salesPerson1);
        managerA.addSubordinate(salesPerson2);
        managerA.addSubordinate(salesPerson3);
        managerA.addSubordinate(managerB);
        
        // Setup: Create Manager B with 1 subordinate employee: Worker W1
        managerB.addSubordinate(worker1);
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1.
        assertEquals("The number of subordinates for Manager A should be 5", 
                     5, managerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B should be 1", 
                     1, managerB.getDirectSubordinateEmployeesCount());
    }
}