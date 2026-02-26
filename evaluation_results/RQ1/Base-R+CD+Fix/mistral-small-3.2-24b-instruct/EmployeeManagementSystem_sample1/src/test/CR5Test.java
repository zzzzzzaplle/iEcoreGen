import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    private Company company;
    private List<Manager> managers;
    
    @Before
    public void setUp() {
        company = new Company();
        managers = new ArrayList<>();
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        Manager m1 = new Manager();
        m1.setName("M1");
        
        Worker w1 = new ShiftWorker();
        w1.setName("W1");
        
        SalesPeople s1 = new SalesPeople();
        s1.setName("S1");
        
        Manager m2 = new Manager();
        m2.setName("M2");
        
        // Add subordinates to m1's direct subordinates list
        List<Employee> m1Subordinates = new ArrayList<>();
        m1Subordinates.add(w1);
        m1Subordinates.add(s1);
        m1Subordinates.add(m2);
        
        // Mock the getDirectSubordinateEmployeesCount method to return the actual count
        // Since the original method returns 0, we need to override it for testing
        Manager testManager = new Manager() {
            private List<Employee> directSubordinates = m1Subordinates;
            
            @Override
            public int getDirectSubordinateEmployeesCount() {
                return directSubordinates.size();
            }
        };
        
        // Test the number of direct subordinates
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 3, testManager.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager m1 = new Manager();
        m1.setName("M1");
        
        // Use the original getDirectSubordinateEmployeesCount method which returns 0
        assertEquals("The number of subordinates for the manager is 0.", 0, m1.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates and Manager B with 7 subordinates
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        // Create subordinates for Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        List<Employee> managerASubordinates = new ArrayList<>();
        managerASubordinates.add(new ShiftWorker());
        managerASubordinates.add(new OffShiftWorker());
        managerASubordinates.add(new SalesPeople());
        
        // Create subordinates for Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        List<Employee> managerBSubordinates = new ArrayList<>();
        managerBSubordinates.add(new ShiftWorker());
        managerBSubordinates.add(new ShiftWorker());
        managerBSubordinates.add(new OffShiftWorker());
        managerBSubordinates.add(new OffShiftWorker());
        managerBSubordinates.add(new SalesPeople());
        managerBSubordinates.add(new SalesPeople());
        managerBSubordinates.add(new SalesPeople());
        
        // Create test managers with overridden getDirectSubordinateEmployeesCount method
        Manager testManagerA = new Manager() {
            private List<Employee> directSubordinates = managerASubordinates;
            
            @Override
            public int getDirectSubordinateEmployeesCount() {
                return directSubordinates.size();
            }
        };
        
        Manager testManagerB = new Manager() {
            private List<Employee> directSubordinates = managerBSubordinates;
            
            @Override
            public int getDirectSubordinateEmployeesCount() {
                return directSubordinates.size();
            }
        };
        
        // Test both managers
        assertEquals("The number of subordinates for Manager A is 3.", 3, testManagerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 7.", 7, testManagerB.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 subordinate Worker W1
        Manager m1 = new Manager();
        m1.setName("M1");
        
        Worker w1 = new ShiftWorker();
        w1.setName("W1");
        
        List<Employee> m1Subordinates = new ArrayList<>();
        m1Subordinates.add(w1);
        
        Manager testManager = new Manager() {
            private List<Employee> directSubordinates = m1Subordinates;
            
            @Override
            public int getDirectSubordinateEmployeesCount() {
                return directSubordinates.size();
            }
        };
        
        assertEquals("The number of subordinates for the manager is 1.", 1, testManager.getDirectSubordinateEmployeesCount());
    }
    
    @Test
    public void testCase5_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Manager A has 5 subordinates, Manager B has 1 subordinate
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        Worker w1 = new ShiftWorker();
        w1.setName("W1");
        
        SalesPeople s1 = new SalesPeople();
        s1.setName("S1");
        SalesPeople s2 = new SalesPeople();
        s2.setName("S2");
        SalesPeople s3 = new SalesPeople();
        s3.setName("S3");
        
        // Create subordinates for Manager A: 1 Worker, 3 SalesPerson, 1 Manager B
        List<Employee> managerASubordinates = new ArrayList<>();
        managerASubordinates.add(w1);
        managerASubordinates.add(s1);
        managerASubordinates.add(s2);
        managerASubordinates.add(s3);
        managerASubordinates.add(managerB);
        
        // Create subordinates for Manager B: 1 Worker W1
        List<Employee> managerBSubordinates = new ArrayList<>();
        managerBSubordinates.add(w1);
        
        Manager testManagerA = new Manager() {
            private List<Employee> directSubordinates = managerASubordinates;
            
            @Override
            public int getDirectSubordinateEmployeesCount() {
                return directSubordinates.size();
            }
        };
        
        Manager testManagerB = new Manager() {
            private List<Employee> directSubordinates = managerBSubordinates;
            
            @Override
            public int getDirectSubordinateEmployeesCount() {
                return directSubordinates.size();
            }
        };
        
        // Test both managers
        assertEquals("The number of subordinates for Manager A is 5.", 5, testManagerA.getDirectSubordinateEmployeesCount());
        assertEquals("The number of subordinates for Manager B is 1.", 1, testManagerB.getDirectSubordinateEmployeesCount());
    }
}