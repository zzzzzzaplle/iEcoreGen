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
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        // Create Worker W1
        Worker workerW1 = new OffShiftWorker();
        workerW1.setName("W1");
        
        // Create SalesPerson S1
        SalesPeople salesPersonS1 = new SalesPeople();
        salesPersonS1.setName("S1");
        
        // Create Manager M2
        Manager managerM2 = new Manager();
        managerM2.setName("M2");
        
        // Assign subordinates to M1
        managerM1.addSubordinate(workerW1);
        managerM1.addSubordinate(salesPersonS1);
        managerM1.addSubordinate(managerM2);
        
        // Add manager to company
        company.addEmployee(managerM1);
        
        // Get manager subordinate counts
        List<String> managerCounts = company.getManagerSubordinateCounts();
        
        // Verify the result
        assertEquals(1, managerCounts.size());
        assertEquals("M1: 3 subordinates", managerCounts.get(0));
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        // Add manager to company
        company.addEmployee(managerM1);
        
        // Get manager subordinate counts
        List<String> managerCounts = company.getManagerSubordinateCounts();
        
        // Verify the result
        assertEquals(1, managerCounts.size());
        assertEquals("M1: 0 subordinates", managerCounts.get(0));
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create subordinates for Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("SW1");
        OffShiftWorker offShiftWorker1 = new OffShiftWorker();
        offShiftWorker1.setName("OSW1");
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setName("SP1");
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(shiftWorker1);
        managerA.addSubordinate(offShiftWorker1);
        managerA.addSubordinate(salesPerson1);
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinates for Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("SW2");
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("SW3");
        OffShiftWorker offShiftWorker2 = new OffShiftWorker();
        offShiftWorker2.setName("OSW2");
        OffShiftWorker offShiftWorker3 = new OffShiftWorker();
        offShiftWorker3.setName("OSW3");
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setName("SP2");
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setName("SP3");
        SalesPeople salesPerson4 = new SalesPeople();
        salesPerson4.setName("SP4");
        
        // Assign subordinates to Manager B
        managerB.addSubordinate(shiftWorker2);
        managerB.addSubordinate(shiftWorker3);
        managerB.addSubordinate(offShiftWorker2);
        managerB.addSubordinate(offShiftWorker3);
        managerB.addSubordinate(salesPerson2);
        managerB.addSubordinate(salesPerson3);
        managerB.addSubordinate(salesPerson4);
        
        // Add managers to company
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
    public void testCase4_managerWithSingleSubordinate() {
        // Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        // Create Worker W1
        Worker workerW1 = new OffShiftWorker();
        workerW1.setName("W1");
        
        // Assign subordinate to M1
        managerM1.addSubordinate(workerW1);
        
        // Add manager to company
        company.addEmployee(managerM1);
        
        // Get manager subordinate counts
        List<String> managerCounts = company.getManagerSubordinateCounts();
        
        // Verify the result
        assertEquals(1, managerCounts.size());
        assertEquals("M1: 1 subordinates", managerCounts.get(0));
    }
    
    @Test
    public void testCase5_multipleManagersWithNestedSubordinates() {
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create Worker W1 for Manager B
        Worker workerW1 = new OffShiftWorker();
        workerW1.setName("W1");
        
        // Assign subordinate to Manager B
        managerB.addSubordinate(workerW1);
        
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create subordinates for Manager A: 1 Worker, 3 SalesPerson, and Manager B
        Worker workerA1 = new OffShiftWorker();
        workerA1.setName("WorkerA1");
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setName("S1");
        SalesPeople salesPerson2 = new SalesPeople();
        salesPerson2.setName("S2");
        SalesPeople salesPerson3 = new SalesPeople();
        salesPerson3.setName("S3");
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(workerA1);
        managerA.addSubordinate(salesPerson1);
        managerA.addSubordinate(salesPerson2);
        managerA.addSubordinate(salesPerson3);
        managerA.addSubordinate(managerB);
        
        // Add managers to company
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