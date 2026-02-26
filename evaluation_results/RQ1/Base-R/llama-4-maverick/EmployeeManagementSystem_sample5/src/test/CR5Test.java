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
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 with 3 direct subordinate employees
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        Worker workerW1 = new ShiftWorker();
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
        
        // Verify the number of direct subordinates for Manager M1 is 3
        List<Integer> subordinateCounts = company.getManagerSubordinateCounts();
        assertEquals("Should have one manager in the list", 1, subordinateCounts.size());
        assertEquals("The number of direct subordinates for Manager M1 should be 3", 
                     Integer.valueOf(3), subordinateCounts.get(0));
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no direct subordinate employees
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        company.addEmployee(managerM1);
        
        // Verify the number of subordinates for the manager is 0
        List<Integer> subordinateCounts = company.getManagerSubordinateCounts();
        assertEquals("Should have one manager in the list", 1, subordinateCounts.size());
        assertEquals("The number of subordinates for the manager should be 0", 
                     Integer.valueOf(0), subordinateCounts.get(0));
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Manager A has 3 subordinates, Manager B has 7 subordinates
        Manager managerA = new Manager();
        managerA.setName("A");
        
        Manager managerB = new Manager();
        managerB.setName("B");
        
        // Create subordinates for Manager A (3 subordinates)
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
        
        // Create subordinates for Manager B (7 subordinates)
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("SW2");
        ShiftWorker shiftWorker3 = new ShiftWorker();
        shiftWorker3.setName("SW3");
        
        NonShiftWorker nonShiftWorker2 = new NonShiftWorker();
        nonShiftWorker2.setName("NSW2");
        NonShiftWorker nonShiftWorker3 = new NonShiftWorker();
        nonShiftWorker3.setName("NSW3");
        
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setName("SP2");
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setName("SP3");
        SalesPerson salesPerson4 = new SalesPerson();
        salesPerson4.setName("SP4");
        
        // Assign subordinates to Manager B
        managerB.addSubordinate(shiftWorker2);
        managerB.addSubordinate(shiftWorker3);
        managerB.addSubordinate(nonShiftWorker2);
        managerB.addSubordinate(nonShiftWorker3);
        managerB.addSubordinate(salesPerson2);
        managerB.addSubordinate(salesPerson3);
        managerB.addSubordinate(salesPerson4);
        
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Verify the number of subordinates for both managers
        List<Integer> subordinateCounts = company.getManagerSubordinateCounts();
        assertEquals("Should have two managers in the list", 2, subordinateCounts.size());
        
        // Find which count belongs to which manager (order may vary)
        boolean foundManagerA = false;
        boolean foundManagerB = false;
        
        for (Integer count : subordinateCounts) {
            if (count == 3) {
                foundManagerA = true;
            } else if (count == 7) {
                foundManagerB = true;
            }
        }
        
        assertTrue("Manager A should have 3 subordinates", foundManagerA);
        assertTrue("Manager B should have 7 subordinates", foundManagerB);
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 direct subordinate employee
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        
        Worker workerW1 = new ShiftWorker();
        workerW1.setName("W1");
        
        // Assign subordinate to M1
        managerM1.addSubordinate(workerW1);
        
        company.addEmployee(managerM1);
        
        // Verify the number of subordinates for the manager is 1
        List<Integer> subordinateCounts = company.getManagerSubordinateCounts();
        assertEquals("Should have one manager in the list", 1, subordinateCounts.size());
        assertEquals("The number of subordinates for the manager should be 1", 
                     Integer.valueOf(1), subordinateCounts.get(0));
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Manager A has 5 subordinates, Manager B has 1 subordinate
        Manager managerA = new Manager();
        managerA.setName("A");
        
        Manager managerB = new Manager();
        managerB.setName("B");
        
        Worker workerW1 = new ShiftWorker();
        workerW1.setName("W1");
        
        SalesPerson salesPersonS1 = new SalesPerson();
        salesPersonS1.setName("S1");
        SalesPerson salesPersonS2 = new SalesPerson();
        salesPersonS2.setName("S2");
        SalesPerson salesPersonS3 = new SalesPerson();
        salesPersonS3.setName("S3");
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(workerW1);
        managerA.addSubordinate(salesPersonS1);
        managerA.addSubordinate(salesPersonS2);
        managerA.addSubordinate(salesPersonS3);
        managerA.addSubordinate(managerB);
        
        // Assign subordinate to Manager B
        Worker workerW2 = new ShiftWorker();
        workerW2.setName("W2");
        managerB.addSubordinate(workerW2);
        
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Verify the number of subordinates for both managers
        List<Integer> subordinateCounts = company.getManagerSubordinateCounts();
        assertEquals("Should have two managers in the list", 2, subordinateCounts.size());
        
        // Find which count belongs to which manager (order may vary)
        boolean foundManagerA = false;
        boolean foundManagerB = false;
        
        for (Integer count : subordinateCounts) {
            if (count == 5) {
                foundManagerA = true;
            } else if (count == 1) {
                foundManagerB = true;
            }
        }
        
        assertTrue("Manager A should have 5 subordinates", foundManagerA);
        assertTrue("Manager B should have 1 subordinate", foundManagerB);
    }
}