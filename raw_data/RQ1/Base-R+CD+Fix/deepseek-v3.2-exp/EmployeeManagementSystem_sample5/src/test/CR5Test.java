import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        Worker workerW1 = new OffShiftWorker();
        workerW1.setName("W1");
        
        SalesPeople salesPersonS1 = new SalesPeople();
        salesPersonS1.setName("S1");
        
        Manager managerM2 = new Manager();
        managerM2.setName("M2");
        
        // Add subordinates to manager M1
        managerM1.addSubordinate(workerW1);
        managerM1.addSubordinate(salesPersonS1);
        managerM1.addSubordinate(managerM2);
        
        // Add manager to company
        company.addEmployee(managerM1);
        
        // Execute: Get direct subordinate counts for managers
        List<String> result = company.getDirectSubordinateCountsForManagers();
        
        // Verify: The number of direct subordinates for Manager M1 is 3
        assertEquals(1, result.size());
        assertEquals("M1: 3 subordinates", result.get(0));
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        // Add manager to company
        company.addEmployee(managerM1);
        
        // Execute: Get direct subordinate counts for managers
        List<String> result = company.getDirectSubordinateCountsForManagers();
        
        // Verify: The number of subordinates for the manager is 0
        assertEquals(1, result.size());
        assertEquals("M1: 0 subordinates", result.get(0));
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates and Manager B with 7 subordinates
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinates for Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        ShiftWorker shiftWorkerA1 = new ShiftWorker();
        shiftWorkerA1.setName("SW_A1");
        
        OffShiftWorker offShiftWorkerA1 = new OffShiftWorker();
        offShiftWorkerA1.setName("OSW_A1");
        
        SalesPeople salesPersonA1 = new SalesPeople();
        salesPersonA1.setName("SP_A1");
        
        // Add subordinates to Manager A
        managerA.addSubordinate(shiftWorkerA1);
        managerA.addSubordinate(offShiftWorkerA1);
        managerA.addSubordinate(salesPersonA1);
        
        // Create subordinates for Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        for (int i = 1; i <= 2; i++) {
            ShiftWorker sw = new ShiftWorker();
            sw.setName("SW_B" + i);
            managerB.addSubordinate(sw);
        }
        
        for (int i = 1; i <= 2; i++) {
            OffShiftWorker osw = new OffShiftWorker();
            osw.setName("OSW_B" + i);
            managerB.addSubordinate(osw);
        }
        
        for (int i = 1; i <= 3; i++) {
            SalesPeople sp = new SalesPeople();
            sp.setName("SP_B" + i);
            managerB.addSubordinate(sp);
        }
        
        // Add managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Execute: Get direct subordinate counts for managers
        List<String> result = company.getDirectSubordinateCountsForManagers();
        
        // Verify: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7.
        assertEquals(2, result.size());
        
        // Check both managers are present with correct counts
        boolean foundManagerA = false;
        boolean foundManagerB = false;
        
        for (String entry : result) {
            if (entry.equals("Manager A: 3 subordinates")) {
                foundManagerA = true;
            } else if (entry.equals("Manager B: 7 subordinates")) {
                foundManagerB = true;
            }
        }
        
        assertTrue("Manager A with 3 subordinates should be found", foundManagerA);
        assertTrue("Manager B with 7 subordinates should be found", foundManagerB);
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 subordinate: Worker W1
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        Worker workerW1 = new OffShiftWorker();
        workerW1.setName("W1");
        
        // Add subordinate to manager M1
        managerM1.addSubordinate(workerW1);
        
        // Add manager to company
        company.addEmployee(managerM1);
        
        // Execute: Get direct subordinate counts for managers
        List<String> result = company.getDirectSubordinateCountsForManagers();
        
        // Verify: The number of subordinates for the manager is 1
        assertEquals(1, result.size());
        assertEquals("M1: 1 subordinates", result.get(0));
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Manager A has 5 subordinates: 1 Worker, 3 SalesPerson and 1 Manager B
        // Manager B has 1 subordinate: Worker W1
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinates for Manager A
        Worker workerA1 = new OffShiftWorker();
        workerA1.setName("W1");
        
        SalesPeople salesPersonA1 = new SalesPeople();
        salesPersonA1.setName("S1");
        
        SalesPeople salesPersonA2 = new SalesPeople();
        salesPersonA2.setName("S2");
        
        SalesPeople salesPersonA3 = new SalesPeople();
        salesPersonA3.setName("S3");
        
        // Add subordinates to Manager A
        managerA.addSubordinate(workerA1);
        managerA.addSubordinate(salesPersonA1);
        managerA.addSubordinate(salesPersonA2);
        managerA.addSubordinate(salesPersonA3);
        managerA.addSubordinate(managerB);
        
        // Create subordinate for Manager B
        Worker workerB1 = new OffShiftWorker();
        workerB1.setName("W1");
        managerB.addSubordinate(workerB1);
        
        // Add managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Execute: Get direct subordinate counts for managers
        List<String> result = company.getDirectSubordinateCountsForManagers();
        
        // Verify: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1.
        assertEquals(2, result.size());
        
        // Check both managers are present with correct counts
        boolean foundManagerA = false;
        boolean foundManagerB = false;
        
        for (String entry : result) {
            if (entry.equals("Manager A: 5 subordinates")) {
                foundManagerA = true;
            } else if (entry.equals("Manager B: 1 subordinates")) {
                foundManagerB = true;
            }
        }
        
        assertTrue("Manager A with 5 subordinates should be found", foundManagerA);
        assertTrue("Manager B with 1 subordinate should be found", foundManagerB);
    }
}