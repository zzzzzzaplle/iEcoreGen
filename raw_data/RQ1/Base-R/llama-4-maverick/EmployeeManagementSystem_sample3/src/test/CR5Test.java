import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Manager managerA;
    private Manager managerB;
    private ShiftWorker shiftWorker;
    private NonShiftWorker nonShiftWorker;
    private SalesPerson salesPerson;
    private Manager subordinateManager;
    
    @Before
    public void setUp() {
        // Common setup for test objects
        managerA = new Manager();
        managerB = new Manager();
        shiftWorker = new ShiftWorker();
        nonShiftWorker = new NonShiftWorker();
        salesPerson = new SalesPerson();
        subordinateManager = new Manager();
    }
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        Manager m1 = new Manager();
        Worker w1 = new ShiftWorker();
        SalesPerson s1 = new SalesPerson();
        Manager m2 = new Manager();
        
        m1.addSubordinate(w1);
        m1.addSubordinate(s1);
        m1.addSubordinate(m2);
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3", 3, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0", 0, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates (1 ShiftWorker, 1 NonShiftWorker, 1 SalesPerson)
        Manager managerA = new Manager();
        ShiftWorker sw1 = new ShiftWorker();
        NonShiftWorker nsw1 = new NonShiftWorker();
        SalesPerson sp1 = new SalesPerson();
        
        managerA.addSubordinate(sw1);
        managerA.addSubordinate(nsw1);
        managerA.addSubordinate(sp1);
        
        // Setup: Create Manager B with 7 subordinates (2 ShiftWorker, 2 NonShiftWorker, 3 SalesPerson)
        Manager managerB = new Manager();
        ShiftWorker sw2 = new ShiftWorker();
        ShiftWorker sw3 = new ShiftWorker();
        NonShiftWorker nsw2 = new NonShiftWorker();
        NonShiftWorker nsw3 = new NonShiftWorker();
        SalesPerson sp2 = new SalesPerson();
        SalesPerson sp3 = new SalesPerson();
        SalesPerson sp4 = new SalesPerson();
        
        managerB.addSubordinate(sw2);
        managerB.addSubordinate(sw3);
        managerB.addSubordinate(nsw2);
        managerB.addSubordinate(nsw3);
        managerB.addSubordinate(sp2);
        managerB.addSubordinate(sp3);
        managerB.addSubordinate(sp4);
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7
        assertEquals("The number of subordinates for Manager A is 3", 3, managerA.getNumberOfSubordinates());
        assertEquals("The number of subordinates for Manager B is 7", 7, managerB.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 subordinate (Worker W1)
        Manager m1 = new Manager();
        Worker w1 = new ShiftWorker();
        
        m1.addSubordinate(w1);
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1", 1, m1.getNumberOfSubordinates());
    }
    
    @Test
    public void testCase5_multipleManagersWithNestedSubordinates() {
        // Setup: Create Manager A with 5 subordinates (1 Worker, 3 SalesPerson, 1 Manager B)
        Manager managerA = new Manager();
        Worker w1 = new ShiftWorker();
        SalesPerson s1 = new SalesPerson();
        SalesPerson s2 = new SalesPerson();
        SalesPerson s3 = new SalesPerson();
        Manager managerB = new Manager();
        
        managerA.addSubordinate(w1);
        managerA.addSubordinate(s1);
        managerA.addSubordinate(s2);
        managerA.addSubordinate(s3);
        managerA.addSubordinate(managerB);
        
        // Setup: Create Manager B with 1 subordinate (Worker W1)
        Worker w2 = new ShiftWorker();
        managerB.addSubordinate(w2);
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1
        assertEquals("The number of subordinates for Manager A is 5", 5, managerA.getNumberOfSubordinates());
        assertEquals("The number of subordinates for Manager B is 1", 1, managerB.getNumberOfSubordinates());
    }
}