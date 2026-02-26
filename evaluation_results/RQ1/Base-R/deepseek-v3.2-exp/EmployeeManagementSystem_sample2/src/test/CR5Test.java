import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager manager = new Manager();
        manager.setName("M1");
        
        // Create Worker W1
        Worker worker = new Worker();
        worker.setName("W1");
        
        // Create SalesPerson S1
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setName("S1");
        
        // Create Manager M2
        Manager manager2 = new Manager();
        manager2.setName("M2");
        
        // Assign subordinates to M1
        manager.addSubordinate(worker);
        manager.addSubordinate(salesPerson);
        manager.addSubordinate(manager2);
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Get manager subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify the result
        assertEquals(1, results.size());
        assertEquals("M1: 3 subordinates", results.get(0));
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager manager = new Manager();
        manager.setName("M1");
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Get manager subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify the result
        assertEquals(1, results.size());
        assertEquals("M1: 0 subordinates", results.get(0));
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentSubordinateCounts() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinates for Manager A: 1 ShiftWorker, 1 NonShiftWorker, 1 SalesPerson
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("SW1");
        
        NonShiftWorker nonShiftWorker1 = new NonShiftWorker();
        nonShiftWorker1.setName("NSW1");
        
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setName("SP1");
        
        // Add subordinates to Manager A
        managerA.addSubordinate(shiftWorker1);
        managerA.addSubordinate(nonShiftWorker1);
        managerA.addSubordinate(salesPerson1);
        
        // Create subordinates for Manager B: 2 ShiftWorkers, 2 NonShiftWorkers, 3 SalesPersons
        for (int i = 1; i <= 2; i++) {
            ShiftWorker sw = new ShiftWorker();
            sw.setName("SW_B" + i);
            managerB.addSubordinate(sw);
        }
        
        for (int i = 1; i <= 2; i++) {
            NonShiftWorker nsw = new NonShiftWorker();
            nsw.setName("NSW_B" + i);
            managerB.addSubordinate(nsw);
        }
        
        for (int i = 1; i <= 3; i++) {
            SalesPerson sp = new SalesPerson();
            sp.setName("SP_B" + i);
            managerB.addSubordinate(sp);
        }
        
        // Add managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get manager subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify the results
        assertEquals(2, results.size());
        assertTrue(results.contains("Manager A: 3 subordinates"));
        assertTrue(results.contains("Manager B: 7 subordinates"));
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Create Manager M1
        Manager manager = new Manager();
        manager.setName("M1");
        
        // Create Worker W1
        Worker worker = new Worker();
        worker.setName("W1");
        
        // Assign subordinate to M1
        manager.addSubordinate(worker);
        
        // Add manager to company
        company.addEmployee(manager);
        
        // Get manager subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify the result
        assertEquals(1, results.size());
        assertEquals("M1: 1 subordinates", results.get(0));
    }
    
    @Test
    public void testCase5_nestedManagersWithSubordinates() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create Worker W1
        Worker workerW1 = new Worker();
        workerW1.setName("W1");
        
        // Create SalesPersons S1, S2, S3
        SalesPerson salesPersonS1 = new SalesPerson();
        salesPersonS1.setName("S1");
        
        SalesPerson salesPersonS2 = new SalesPerson();
        salesPersonS2.setName("S2");
        
        SalesPerson salesPersonS3 = new SalesPerson();
        salesPersonS3.setName("S3");
        
        // Assign subordinates to Manager A: 1 Worker, 3 SalesPersons, 1 Manager B
        managerA.addSubordinate(workerW1);
        managerA.addSubordinate(salesPersonS1);
        managerA.addSubordinate(salesPersonS2);
        managerA.addSubordinate(salesPersonS3);
        managerA.addSubordinate(managerB);
        
        // Create Worker W2 for Manager B
        Worker workerW2 = new Worker();
        workerW2.setName("W2");
        
        // Assign subordinate to Manager B
        managerB.addSubordinate(workerW2);
        
        // Add managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get manager subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify the results
        assertEquals(2, results.size());
        assertTrue(results.contains("Manager A: 5 subordinates"));
        assertTrue(results.contains("Manager B: 1 subordinates"));
    }
}