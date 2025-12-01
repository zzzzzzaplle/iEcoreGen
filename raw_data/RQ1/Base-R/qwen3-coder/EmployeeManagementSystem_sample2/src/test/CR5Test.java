import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR5Test {
    
    private Manager managerA;
    private Manager managerB;
    private Worker worker1;
    private Worker worker2;
    private Worker worker3;
    private Worker worker4;
    private Worker worker5;
    private Worker worker6;
    private SalesPerson salesPerson1;
    private SalesPerson salesPerson2;
    private SalesPerson salesPerson3;
    private SalesPerson salesPerson4;
    private SalesPerson salesPerson5;
    private ShiftWorker shiftWorker1;
    private ShiftWorker shiftWorker2;
    private OffShiftWorker offShiftWorker1;
    private OffShiftWorker offShiftWorker2;
    
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
        worker6 = new Worker();
        salesPerson1 = new SalesPerson();
        salesPerson2 = new SalesPerson();
        salesPerson3 = new SalesPerson();
        salesPerson4 = new SalesPerson();
        salesPerson5 = new SalesPerson();
        shiftWorker1 = new ShiftWorker();
        shiftWorker2 = new ShiftWorker();
        offShiftWorker1 = new OffShiftWorker();
        offShiftWorker2 = new OffShiftWorker();
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        Manager m1 = new Manager();
        Worker w1 = new Worker();
        SalesPerson s1 = new SalesPerson();
        Manager m2 = new Manager();
        
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3", 3, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0", 0, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Manager A has 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPerson)
        managerA.addSubordinate(shiftWorker1);
        managerA.addSubordinate(offShiftWorker1);
        managerA.addSubordinate(salesPerson1);
        
        // Setup: Manager B has 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPerson)
        managerB.addSubordinate(shiftWorker1);
        managerB.addSubordinate(shiftWorker2);
        managerB.addSubordinate(offShiftWorker1);
        managerB.addSubordinate(offShiftWorker2);
        managerB.addSubordinate(salesPerson1);
        managerB.addSubordinate(salesPerson2);
        managerB.addSubordinate(salesPerson3);
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7.
        assertEquals("The number of subordinates for Manager A is 3", 3, managerA.getNumberOfSubordinates());
        assertEquals("The number of subordinates for Manager B is 7", 7, managerB.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 and assign Worker W1 as subordinate
        Manager m1 = new Manager();
        Worker w1 = new Worker();
        
        m1.addSubordinate(w1);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1", 1, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Manager A has 5 subordinates: 1 Worker, 3 SalesPerson and Manager B
        managerA.addSubordinate(worker1);
        managerA.addSubordinate(salesPerson1);
        managerA.addSubordinate(salesPerson2);
        managerA.addSubordinate(salesPerson3);
        managerA.addSubordinate(managerB);
        
        // Setup: Manager B has 1 subordinate: Worker W1
        managerB.addSubordinate(worker1);
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1.
        assertEquals("The number of subordinates for Manager A is 5", 5, managerA.getNumberOfSubordinates());
        assertEquals("The number of subordinates for Manager B is 1", 1, managerB.getNumberOfSubordinates());
    }
}