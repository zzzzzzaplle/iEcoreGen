import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 with 3 direct subordinates (1 Worker, 1 SalesPerson and 1 Manager)
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        Worker workerW1 = new Worker();
        workerW1.setName("W1");
        
        SalesPerson salesPersonS1 = new SalesPerson();
        salesPersonS1.setName("S1");
        
        Manager managerM2 = new Manager();
        managerM2.setName("M2");
        
        // Add subordinates to M1
        managerM1.addSubordinate(workerW1);
        managerM1.addSubordinate(salesPersonS1);
        managerM1.addSubordinate(managerM2);
        
        company.addEmployee(managerM1);
        
        // Execute: Get manager subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify: The number of direct subordinates for Manager M1 is 3
        assertEquals(1, results.size());
        assertEquals("M1: 3 subordinates", results.get(0));
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        company.addEmployee(managerM1);
        
        // Execute: Get manager subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify: The number of subordinates for the manager is 0
        assertEquals(1, results.size());
        assertEquals("M1: 0 subordinates", results.get(0));
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Manager A has 3 subordinates, Manager B has 7 subordinates
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Add 3 subordinates to Manager A (1 ShiftWorker, 1 NonShiftWorker, 1 SalesPerson)
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("SW1");
        managerA.addSubordinate(shiftWorker1);
        
        NonShiftWorker nonShiftWorker1 = new NonShiftWorker();
        nonShiftWorker1.setName("NSW1");
        managerA.addSubordinate(nonShiftWorker1);
        
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setName("SP1");
        managerA.addSubordinate(salesPerson1);
        
        // Add 7 subordinates to Manager B (2 ShiftWorker, 2 NonShiftWorker, 3 SalesPerson)
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("SW2");
        managerB.addSubordinate(shiftWorker2);
        
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("SW3");
        managerB.addSubordinate(shiftWorker3);
        
        NonShiftWorker nonShiftWorker2 = new NonShiftWorker();
        nonShiftWorker2.setName("NSW2");
        managerB.addSubordinate(nonShiftWorker2);
        
        NonShiftWorker nonShiftWorker3 = new NonShiftWorker();
        nonShiftWorker3.setName("NSW3");
        managerB.addSubordinate(nonShiftWorker3);
        
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setName("SP2");
        managerB.addSubordinate(salesPerson2);
        
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setName("SP3");
        managerB.addSubordinate(salesPerson3);
        
        SalesPerson salesPerson4 = new SalesPerson();
        salesPerson4.setName("SP4");
        managerB.addSubordinate(salesPerson4);
        
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Execute: Get manager subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify: The number of subordinates for Manager A is 3, Manager B is 7
        assertEquals(2, results.size());
        assertTrue(results.contains("Manager A: 3 subordinates"));
        assertTrue(results.contains("Manager B: 7 subordinates"));
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 subordinate (1 Worker)
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        Worker workerW1 = new Worker();
        workerW1.setName("W1");
        
        managerM1.addSubordinate(workerW1);
        
        company.addEmployee(managerM1);
        
        // Execute: Get manager subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify: The number of subordinates for the manager is 1
        assertEquals(1, results.size());
        assertEquals("M1: 1 subordinates", results.get(0));
    }
    
    @Test
    public void testCase5_multipleManagersWithNestedSubordinates() {
        // Setup: Manager A has 5 subordinates (1 Worker, 3 SalesPerson, 1 Manager B), Manager B has 1 subordinate (Worker W1)
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        Worker workerW1 = new Worker();
        workerW1.setName("W1");
        
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setName("S1");
        
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setName("S2");
        
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setName("S3");
        
        // Add subordinates to Manager A
        managerA.addSubordinate(workerW1);
        managerA.addSubordinate(salesPerson1);
        managerA.addSubordinate(salesPerson2);
        managerA.addSubordinate(salesPerson3);
        managerA.addSubordinate(managerB);
        
        // Add subordinate to Manager B
        managerB.addSubordinate(workerW1);
        
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Execute: Get manager subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify: The number of subordinates for Manager A is 5, Manager B is 1
        assertEquals(2, results.size());
        assertTrue(results.contains("Manager A: 5 subordinates"));
        assertTrue(results.contains("Manager B: 1 subordinates"));
    }
}