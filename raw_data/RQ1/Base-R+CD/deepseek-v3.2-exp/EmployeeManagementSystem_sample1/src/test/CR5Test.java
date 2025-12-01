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
        
        // Create subordinates: Worker W1, SalesPerson S1, and Manager M2
        Worker workerW1 = new OffShiftWorker();
        workerW1.setName("W1");
        
        SalesPeople salesPersonS1 = new SalesPeople();
        salesPersonS1.setName("S1");
        
        Manager managerM2 = new Manager();
        managerM2.setName("M2");
        
        // Assign subordinates to Manager M1
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        subordinates.add(salesPersonS1);
        subordinates.add(managerM2);
        managerM1.setSubordinates(subordinates);
        
        // Add manager to company
        company.getEmployees().add(managerM1);
        
        // Get subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify output
        assertEquals(1, results.size());
        assertEquals("M1: 3 subordinates", results.get(0));
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        managerM1.setSubordinates(new ArrayList<>());
        
        // Add manager to company
        company.getEmployees().add(managerM1);
        
        // Get subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify output
        assertEquals(1, results.size());
        assertEquals("M1: 0 subordinates", results.get(0));
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentSubordinateCounts() {
        // Create Manager A with 3 subordinates
        Manager managerA = new Manager();
        managerA.setName("A");
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("SW1");
        
        OffShiftWorker offShiftWorker1 = new OffShiftWorker();
        offShiftWorker1.setName("OSW1");
        
        SalesPeople salesPerson1 = new SalesPeople();
        salesPerson1.setName("SP1");
        
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(shiftWorker1);
        subordinatesA.add(offShiftWorker1);
        subordinatesA.add(salesPerson1);
        managerA.setSubordinates(subordinatesA);
        
        // Create Manager B with 7 subordinates
        Manager managerB = new Manager();
        managerB.setName("B");
        
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
        
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(shiftWorker2);
        subordinatesB.add(shiftWorker3);
        subordinatesB.add(offShiftWorker2);
        subordinatesB.add(offShiftWorker3);
        subordinatesB.add(salesPerson2);
        subordinatesB.add(salesPerson3);
        subordinatesB.add(salesPerson4);
        managerB.setSubordinates(subordinatesB);
        
        // Add managers to company
        company.getEmployees().add(managerA);
        company.getEmployees().add(managerB);
        
        // Get subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify output - order may vary, so check both managers
        assertEquals(2, results.size());
        assertTrue(results.contains("A: 3 subordinates"));
        assertTrue(results.contains("B: 7 subordinates"));
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        // Create Worker W1 as subordinate
        Worker workerW1 = new OffShiftWorker();
        workerW1.setName("W1");
        
        // Assign subordinate to Manager M1
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        managerM1.setSubordinates(subordinates);
        
        // Add manager to company
        company.getEmployees().add(managerM1);
        
        // Get subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify output
        assertEquals(1, results.size());
        assertEquals("M1: 1 subordinates", results.get(0));
    }
    
    @Test
    public void testCase5_nestedManagersWithSubordinates() {
        // Create Worker W1
        Worker workerW1 = new OffShiftWorker();
        workerW1.setName("W1");
        
        // Create Manager B with Worker W1 as subordinate
        Manager managerB = new Manager();
        managerB.setName("B");
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(workerW1);
        managerB.setSubordinates(subordinatesB);
        
        // Create SalesPerson S1, S2, S3
        SalesPeople salesPersonS1 = new SalesPeople();
        salesPersonS1.setName("S1");
        SalesPeople salesPersonS2 = new SalesPeople();
        salesPersonS2.setName("S2");
        SalesPeople salesPersonS3 = new SalesPeople();
        salesPersonS3.setName("S3");
        
        // Create Manager A with subordinates: Worker W1, SalesPerson S1, S2, S3, and Manager B
        Manager managerA = new Manager();
        managerA.setName("A");
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(workerW1);
        subordinatesA.add(salesPersonS1);
        subordinatesA.add(salesPersonS2);
        subordinatesA.add(salesPersonS3);
        subordinatesA.add(managerB);
        managerA.setSubordinates(subordinatesA);
        
        // Add managers to company
        company.getEmployees().add(managerA);
        company.getEmployees().add(managerB);
        
        // Get subordinate counts
        List<String> results = company.getManagerSubordinateCounts();
        
        // Verify output - order may vary, so check both managers
        assertEquals(2, results.size());
        assertTrue(results.contains("A: 5 subordinates"));
        assertTrue(results.contains("B: 1 subordinates"));
    }
}