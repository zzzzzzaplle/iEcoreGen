import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    private Company company;
    private Department department1;
    private Department department2;
    private Manager manager1;
    private Manager manager2;
    private Worker worker1;
    private SalesPeople salesPerson1;
    private ShiftWorker shiftWorker1;
    private OffShiftWorker offShiftWorker1;
    
    @Before
    public void setUp() {
        company = new Company();
        department1 = new Department();
        department2 = new Department();
        
        // Initialize employees
        manager1 = new Manager();
        manager2 = new Manager();
        worker1 = new Worker() {}; // Anonymous subclass since Worker is abstract
        salesPerson1 = new SalesPeople();
        shiftWorker1 = new ShiftWorker();
        offShiftWorker1 = new OffShiftWorker();
        
        // Set up company departments and employees lists
        company.setDepartments(new ArrayList<>());
        company.setEmployees(new ArrayList<>());
        department1.setEmployees(new ArrayList<>());
        department2.setEmployees(new ArrayList<>());
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1 and assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates
        Manager M1 = new Manager();
        Manager M2 = new Manager();
        Worker W1 = new Worker() {};
        SalesPeople S1 = new SalesPeople();
        
        Department dept = new Department();
        dept.setManager(M1);
        
        // Add subordinates to department
        List<Employee> employees = new ArrayList<>();
        employees.add(W1);
        employees.add(S1);
        employees.add(M2);
        dept.setEmployees(employees);
        
        // Set department for M1
        M1.setDepartment(dept);
        
        // Test: Get direct subordinate count for M1
        int result = M1.getDirectSubordinateEmployeesCount();
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 should be 3", 3, result);
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() {
        // Setup: Create Manager M1 with no direct subordinate employees
        Manager M1 = new Manager();
        Department dept = new Department();
        dept.setManager(M1);
        dept.setEmployees(new ArrayList<>()); // Empty subordinates list
        M1.setDepartment(dept);
        
        // Test: Get direct subordinate count for M1
        int result = M1.getDirectSubordinateEmployeesCount();
        
        // Expected Output: The number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager should be 0", 0, result);
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() {
        // Setup: Create Manager A with 3 subordinates and Manager B with 7 subordinates
        Manager managerA = new Manager();
        Manager managerB = new Manager();
        
        Department deptA = new Department();
        Department deptB = new Department();
        
        deptA.setManager(managerA);
        deptB.setManager(managerB);
        
        // Manager A subordinates: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        List<Employee> subordinatesA = new ArrayList<>();
        ShiftWorker sw1 = new ShiftWorker();
        OffShiftWorker osw1 = new OffShiftWorker();
        SalesPeople sp1 = new SalesPeople();
        subordinatesA.add(sw1);
        subordinatesA.add(osw1);
        subordinatesA.add(sp1);
        deptA.setEmployees(subordinatesA);
        managerA.setDepartment(deptA);
        
        // Manager B subordinates: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new ShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new OffShiftWorker());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        subordinatesB.add(new SalesPeople());
        deptB.setEmployees(subordinatesB);
        managerB.setDepartment(deptB);
        
        // Test: Get direct subordinate counts
        int resultA = managerA.getDirectSubordinateEmployeesCount();
        int resultB = managerB.getDirectSubordinateEmployeesCount();
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7.
        assertEquals("The number of subordinates for Manager A should be 3", 3, resultA);
        assertEquals("The number of subordinates for Manager B should be 7", 7, resultB);
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() {
        // Setup: Create Manager M1 and assign Worker W1 as subordinate
        Manager M1 = new Manager();
        Worker W1 = new Worker() {};
        
        Department dept = new Department();
        dept.setManager(M1);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(W1);
        dept.setEmployees(employees);
        
        M1.setDepartment(dept);
        
        // Test: Get direct subordinate count for M1
        int result = M1.getDirectSubordinateEmployeesCount();
        
        // Expected Output: The number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() {
        // Setup: Manager A has 5 subordinates, Manager B has 1 subordinate
        Manager managerA = new Manager();
        Manager managerB = new Manager();
        Worker W1 = new Worker() {};
        SalesPeople S1 = new SalesPeople();
        SalesPeople S2 = new SalesPeople();
        SalesPeople S3 = new SalesPeople();
        
        Department deptA = new Department();
        Department deptB = new Department();
        
        deptA.setManager(managerA);
        deptB.setManager(managerB);
        
        // Manager A subordinates: W1, S1, S2, S3, managerB
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(W1);
        subordinatesA.add(S1);
        subordinatesA.add(S2);
        subordinatesA.add(S3);
        subordinatesA.add(managerB);
        deptA.setEmployees(subordinatesA);
        managerA.setDepartment(deptA);
        
        // Manager B subordinates: W1
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(W1);
        deptB.setEmployees(subordinatesB);
        managerB.setDepartment(deptB);
        
        // Test: Get direct subordinate counts
        int resultA = managerA.getDirectSubordinateEmployeesCount();
        int resultB = managerB.getDirectSubordinateEmployeesCount();
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1.
        assertEquals("The number of subordinates for Manager A should be 5", 5, resultA);
        assertEquals("The number of subordinates for Manager B should be 1", 1, resultB);
    }
}