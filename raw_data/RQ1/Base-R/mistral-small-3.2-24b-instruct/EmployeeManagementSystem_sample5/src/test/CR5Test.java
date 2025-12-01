import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

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
        managerM1.setName("Manager M1");
        
        // Create subordinates: Worker W1, SalesPerson S1, and Manager M2
        Worker workerW1 = new Worker();
        workerW1.setName("Worker W1");
        
        SalesPerson salesPersonS1 = new SalesPerson();
        salesPersonS1.setName("SalesPerson S1");
        
        Manager managerM2 = new Manager();
        managerM2.setName("Manager M2");
        
        // Assign subordinates to Manager M1
        managerM1.addSubordinate(workerW1);
        managerM1.addSubordinate(salesPersonS1);
        managerM1.addSubordinate(managerM2);
        
        // Add Manager M1 to company
        company.addEmployee(managerM1);
        
        // Get the number of direct subordinates for each manager
        Map<Manager, Integer> subordinateCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify Manager M1 has 3 direct subordinates
        assertEquals(3, subordinateCount.get(managerM1).intValue());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Create Manager M1 with no subordinates
        Manager managerM1 = new Manager();
        managerM1.setName("Manager M1");
        
        // Add Manager M1 to company
        company.addEmployee(managerM1);
        
        // Get the number of direct subordinates for each manager
        Map<Manager, Integer> subordinateCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify Manager M1 has 0 direct subordinates
        assertEquals(0, subordinateCount.get(managerM1).intValue());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Assign 3 subordinates to Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPerson
        Worker shiftWorker1 = new Worker();
        shiftWorker1.setName("ShiftWorker1");
        shiftWorker1.setShiftWorker(true);
        
        Worker offShiftWorker1 = new Worker();
        offShiftWorker1.setName("OffShiftWorker1");
        offShiftWorker1.setShiftWorker(false);
        
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setName("SalesPerson1");
        
        managerA.addSubordinate(shiftWorker1);
        managerA.addSubordinate(offShiftWorker1);
        managerA.addSubordinate(salesPerson1);
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Assign 7 subordinates to Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPerson
        Worker shiftWorker2 = new Worker();
        shiftWorker2.setName("ShiftWorker2");
        shiftWorker2.setShiftWorker(true);
        
        Worker shiftWorker3 = new Worker();
        shiftWorker3.setName("ShiftWorker3");
        shiftWorker3.setShiftWorker(true);
        
        Worker offShiftWorker2 = new Worker();
        offShiftWorker2.setName("OffShiftWorker2");
        offShiftWorker2.setShiftWorker(false);
        
        Worker offShiftWorker3 = new Worker();
        offShiftWorker3.setName("OffShiftWorker3");
        offShiftWorker3.setShiftWorker(false);
        
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setName("SalesPerson2");
        
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setName("SalesPerson3");
        
        SalesPerson salesPerson4 = new SalesPerson();
        salesPerson4.setName("SalesPerson4");
        
        managerB.addSubordinate(shiftWorker2);
        managerB.addSubordinate(shiftWorker3);
        managerB.addSubordinate(offShiftWorker2);
        managerB.addSubordinate(offShiftWorker3);
        managerB.addSubordinate(salesPerson2);
        managerB.addSubordinate(salesPerson3);
        managerB.addSubordinate(salesPerson4);
        
        // Add both managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get the number of direct subordinates for each manager
        Map<Manager, Integer> subordinateCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify Manager A has 3 direct subordinates and Manager B has 7 direct subordinates
        assertEquals(3, subordinateCount.get(managerA).intValue());
        assertEquals(7, subordinateCount.get(managerB).intValue());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("Manager M1");
        
        // Create Worker W1
        Worker workerW1 = new Worker();
        workerW1.setName("Worker W1");
        
        // Assign Worker W1 as subordinate to Manager M1
        managerM1.addSubordinate(workerW1);
        
        // Add Manager M1 to company
        company.addEmployee(managerM1);
        
        // Get the number of direct subordinates for each manager
        Map<Manager, Integer> subordinateCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify Manager M1 has 1 direct subordinate
        assertEquals(1, subordinateCount.get(managerM1).intValue());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        // Create subordinates for Manager A: Worker W1, SalesPerson S1, S2, S3, and Manager B
        Worker workerW1 = new Worker();
        workerW1.setName("Worker W1");
        
        SalesPerson salesPersonS1 = new SalesPerson();
        salesPersonS1.setName("SalesPerson S1");
        
        SalesPerson salesPersonS2 = new SalesPerson();
        salesPersonS2.setName("SalesPerson S2");
        
        SalesPerson salesPersonS3 = new SalesPerson();
        salesPersonS3.setName("SalesPerson S3");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Assign subordinates to Manager A
        managerA.addSubordinate(workerW1);
        managerA.addSubordinate(salesPersonS1);
        managerA.addSubordinate(salesPersonS2);
        managerA.addSubordinate(salesPersonS3);
        managerA.addSubordinate(managerB);
        
        // Create Worker W2 for Manager B
        Worker workerW2 = new Worker();
        workerW2.setName("Worker W2");
        
        // Assign Worker W2 as subordinate to Manager B
        managerB.addSubordinate(workerW2);
        
        // Add both managers to company
        company.addEmployee(managerA);
        company.addEmployee(managerB);
        
        // Get the number of direct subordinates for each manager
        Map<Manager, Integer> subordinateCount = company.getNumberOfDirectSubordinatesForEachManager();
        
        // Verify Manager A has 5 direct subordinates and Manager B has 1 direct subordinate
        assertEquals(5, subordinateCount.get(managerA).intValue());
        assertEquals(1, subordinateCount.get(managerB).intValue());
    }
}