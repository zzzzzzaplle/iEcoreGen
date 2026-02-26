import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Manager managerA;
    private Manager managerB;
    private Worker worker1;
    private Worker worker2;
    private Worker worker3;
    private Worker worker4;
    private Worker worker5;
    private SalesPerson salesPerson1;
    private SalesPerson salesPerson2;
    private SalesPerson salesPerson3;
    private SalesPerson salesPerson4;
    private SalesPerson salesPerson5;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        managerA = new Manager();
        managerB = new Manager();
        
        worker1 = new Worker();
        worker2 = new Worker();
        worker3 = new Worker();
        worker4 = new Worker();
        worker5 = new Worker();
        
        salesPerson1 = new SalesPerson();
        salesPerson2 = new SalesPerson();
        salesPerson3 = new SalesPerson();
        salesPerson4 = new SalesPerson();
        salesPerson5 = new SalesPerson();
    }
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        Manager managerM1 = new Manager();
        Worker workerW1 = new Worker();
        SalesPerson salesPersonS1 = new SalesPerson();
        Manager managerM2 = new Manager();
        
        managerM1.addSubordinate(workerW1);
        managerM1.addSubordinate(salesPersonS1);
        managerM1.addSubordinate(managerM2);
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3", 3, managerM1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager managerM1 = new Manager();
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0", 0, managerM1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Manager A with 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople)
        Worker shiftWorker = new Worker();
        shiftWorker.setShiftWorker(true);
        
        Worker offShiftWorker = new Worker();
        offShiftWorker.setShiftWorker(false);
        
        SalesPerson salesPerson = new SalesPerson();
        
        managerA.addSubordinate(shiftWorker);
        managerA.addSubordinate(offShiftWorker);
        managerA.addSubordinate(salesPerson);
        
        // Setup: Manager B with 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople)
        Worker shiftWorker1 = new Worker();
        shiftWorker1.setShiftWorker(true);
        Worker shiftWorker2 = new Worker();
        shiftWorker2.setShiftWorker(true);
        
        Worker offShiftWorker1 = new Worker();
        offShiftWorker1.setShiftWorker(false);
        Worker offShiftWorker2 = new Worker();
        offShiftWorker2.setShiftWorker(false);
        
        SalesPerson salesPerson1 = new SalesPerson();
        SalesPerson salesPerson2 = new SalesPerson();
        SalesPerson salesPerson3 = new SalesPerson();
        
        managerB.addSubordinate(shiftWorker1);
        managerB.addSubordinate(shiftWorker2);
        managerB.addSubordinate(offShiftWorker1);
        managerB.addSubordinate(offShiftWorker2);
        managerB.addSubordinate(salesPerson1);
        managerB.addSubordinate(salesPerson2);
        managerB.addSubordinate(salesPerson3);
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7
        assertEquals("The number of subordinates for Manager A is 3", 3, managerA.getNumberOfSubordinates());
        assertEquals("The number of subordinates for Manager B is 7", 7, managerB.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Setup: Create Manager M1 and assign Worker W1 as subordinate
        Manager managerM1 = new Manager();
        Worker workerW1 = new Worker();
        
        managerM1.addSubordinate(workerW1);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1", 1, managerM1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase5_multipleManagersWithNestedSubordinates() {
        // Setup: Manager A with 5 subordinates (1 Worker, 3 SalesPerson, and Manager B)
        Worker workerW1 = new Worker();
        SalesPerson salesPersonS1 = new SalesPerson();
        SalesPerson salesPersonS2 = new SalesPerson();
        SalesPerson salesPersonS3 = new SalesPerson();
        Manager managerB = new Manager();
        
        managerA.addSubordinate(workerW1);
        managerA.addSubordinate(salesPersonS1);
        managerA.addSubordinate(salesPersonS2);
        managerA.addSubordinate(salesPersonS3);
        managerA.addSubordinate(managerB);
        
        // Setup: Manager B with 1 subordinate (Worker W1)
        Worker workerW2 = new Worker();
        managerB.addSubordinate(workerW2);
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1
        assertEquals("The number of subordinates for Manager A is 5", 5, managerA.getNumberOfSubordinates());
        assertEquals("The number of subordinates for Manager B is 1", 1, managerB.getNumberOfSubordinates());
    }
}