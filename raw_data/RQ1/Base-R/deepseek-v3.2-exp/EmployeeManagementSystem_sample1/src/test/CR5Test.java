import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        // Create subordinates: Worker W1, SalesPerson S1, and Manager M2
        Worker workerW1 = new Worker();
        workerW1.setName("W1");
        
        SalesPerson salesPersonS1 = new SalesPerson();
        salesPersonS1.setName("S1");
        
        Manager managerM2 = new Manager();
        managerM2.setName("M2");
        
        // Assign subordinates to M1
        managerM1.addSubordinate(workerW1);
        managerM1.addSubordinate(salesPersonS1);
        managerM1.addSubordinate(managerM2);
        
        company.addEmployee(managerM1);
        
        // Get manager subordinate counts
        List<String> managerCounts = company.getManagerSubordinateCounts();
        
        // Verify the result
        assertEquals(1, managerCounts.size());
        assertEquals("M1: 3 subordinates", managerCounts.get(0));
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        company.addEmployee(managerM1);
        
        // Get manager subordinate counts
        List<String> managerCounts = company.getManagerSubordinateCounts();
        
        // Verify the result
        assertEquals(1, managerCounts.size());
        assertEquals("M1: 0 subordinates", managerCounts.get(0));
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create subordinates for Manager A: 1 ShiftWorker, 1 NonShiftWorker, 1 SalesPerson
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("SW1");
        
        NonShiftWorker nonShiftWorker1 = new NonShiftWorker();
        nonShiftWorker1.setName("NSW1");
        
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setName("SP1");
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(shiftWorker1);
        managerA.addSubordinate(nonShiftWorker1);
        managerA.addSubordinate(salesPerson1);
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinates for Manager B: 2 ShiftWorker, 2 NonShiftWorker, 3 SalesPerson
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
        
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get manager subordinate counts
        List<String> managerCounts = company.getManagerSubordinateCounts();
        
        // Verify the results
        assertEquals(2, managerCounts.size());
        assertTrue(managerCounts.contains("Manager A: 3 subordinates"));
        assertTrue(managerCounts.contains("Manager B: 7 subordinates"));
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        // Create Worker W1
        Worker workerW1 = new Worker();
        workerW1.setName("W1");
        
        // Assign W1 as subordinate to M1
        managerM1.addSubordinate(workerW1);
        
        company.addEmployee(managerM1);
        
        // Get manager subordinate counts
        List<String> managerCounts = company.getManagerSubordinateCounts();
        
        // Verify the result
        assertEquals(1, managerCounts.size());
        assertEquals("M1: 1 subordinates", managerCounts.get(0));
    }
    
    @Test
    public void testCase5_MultipleManagersWithHierarchicalSubordinates() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create Worker W1
        Worker workerW1 = new Worker();
        workerW1.setName("W1");
        
        // Create SalesPerson S1, S2, S3
        SalesPerson salesPersonS1 = new SalesPerson();
        salesPersonS1.setName("S1");
        
        SalesPerson salesPersonS2 = new SalesPerson();
        salesPersonS2.setName("S2");
        
        SalesPerson salesPersonS3 = new SalesPerson();
        salesPersonS3.setName("S3");
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create Worker W1 for Manager B
        Worker workerW1ForB = new Worker();
        workerW1ForB.setName("W1");
        
        // Assign subordinate to Manager B
        managerB.addSubordinate(workerW1ForB);
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(workerW1);
        managerA.addSubordinate(salesPersonS1);
        managerA.addSubordinate(salesPersonS2);
        managerA.addSubordinate(salesPersonS3);
        managerA.addSubordinate(managerB);
        
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get manager subordinate counts
        List<String> managerCounts = company.getManagerSubordinateCounts();
        
        // Verify the results
        assertEquals(2, managerCounts.size());
        assertTrue(managerCounts.contains("Manager A: 5 subordinates"));
        assertTrue(managerCounts.contains("Manager B: 1 subordinates"));
    }
}