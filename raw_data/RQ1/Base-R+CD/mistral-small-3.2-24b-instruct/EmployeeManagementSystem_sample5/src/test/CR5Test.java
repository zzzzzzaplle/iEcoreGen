import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private List<Employee> testEmployees;
    
    @Before
    public void setUp() {
        testEmployees = new ArrayList<>();
    }
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setDepartment("TestDepartment");
        
        // Create subordinates: Worker W1, SalesPerson S1, and Manager M2
        Worker w1 = new ShiftWorker();
        w1.setDepartment("TestDepartment");
        
        SalesPeople s1 = new SalesPeople();
        s1.setDepartment("TestDepartment");
        
        Manager m2 = new Manager();
        m2.setDepartment("TestDepartment");
        
        // Add all employees to the test list
        testEmployees.add(m1);
        testEmployees.add(w1);
        testEmployees.add(s1);
        testEmployees.add(m2);
        
        // Verify the number of direct subordinates for Manager M1 is 3
        assertEquals("The number of direct subordinates for Manager M1 is 3", 
                     3, m1.getDirectSubordinateEmployeesCount(testEmployees));
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setDepartment("TestDepartment");
        
        // Add only the manager to the test list (no subordinates)
        testEmployees.add(m1);
        
        // Verify the number of subordinates for the manager is 0
        assertEquals("The number of subordinates for the manager is 0", 
                     0, m1.getDirectSubordinateEmployeesCount(testEmployees));
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentNumberOfSubordinates() {
        // Create Manager A
        Manager managerA = new Manager();
        managerA.setDepartment("DepartmentA");
        
        // Create Manager B
        Manager managerB = new Manager();
        managerB.setDepartment("DepartmentB");
        
        // Create subordinates for Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        ShiftWorker sw1 = new ShiftWorker();
        sw1.setDepartment("DepartmentA");
        
        OffShiftWorker osw1 = new OffShiftWorker();
        osw1.setDepartment("DepartmentA");
        
        SalesPeople sp1 = new SalesPeople();
        sp1.setDepartment("DepartmentA");
        
        // Create subordinates for Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        ShiftWorker sw2 = new ShiftWorker();
        sw2.setDepartment("DepartmentB");
        
        ShiftWorker sw3 = new ShiftWorker();
        sw3.setDepartment("DepartmentB");
        
        OffShiftWorker osw2 = new OffShiftWorker();
        osw2.setDepartment("DepartmentB");
        
        OffShiftWorker osw3 = new OffShiftWorker();
        osw3.setDepartment("DepartmentB");
        
        SalesPeople sp2 = new SalesPeople();
        sp2.setDepartment("DepartmentB");
        
        SalesPeople sp3 = new SalesPeople();
        sp3.setDepartment("DepartmentB");
        
        SalesPeople sp4 = new SalesPeople();
        sp4.setDepartment("DepartmentB");
        
        // Add all employees to the test list
        testEmployees.add(managerA);
        testEmployees.add(managerB);
        testEmployees.add(sw1);
        testEmployees.add(osw1);
        testEmployees.add(sp1);
        testEmployees.add(sw2);
        testEmployees.add(sw3);
        testEmployees.add(osw2);
        testEmployees.add(osw3);
        testEmployees.add(sp2);
        testEmployees.add(sp3);
        testEmployees.add(sp4);
        
        // Verify the number of subordinates for Manager A is 3
        assertEquals("The number of subordinates for Manager A is 3", 
                     3, managerA.getDirectSubordinateEmployeesCount(testEmployees));
        
        // Verify the number of subordinates for Manager B is 7
        assertEquals("The number of subordinates for Manager B is 7", 
                     7, managerB.getDirectSubordinateEmployeesCount(testEmployees));
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Create Manager M1
        Manager m1 = new Manager();
        m1.setDepartment("TestDepartment");
        
        // Create subordinate: Worker W1
        Worker w1 = new ShiftWorker();
        w1.setDepartment("TestDepartment");
        
        // Add both employees to the test list
        testEmployees.add(m1);
        testEmployees.add(w1);
        
        // Verify the number of subordinates for the manager is 1
        assertEquals("The number of subordinates for the manager is 1", 
                     1, m1.getDirectSubordinateEmployeesCount(testEmployees));
    }
    
    @Test
    public void testCase5_multipleManagersWithNestedSubordinates() {
        // Create Manager A and Manager B
        Manager managerA = new Manager();
        managerA.setDepartment("DepartmentA");
        
        Manager managerB = new Manager();
        managerB.setDepartment("DepartmentA"); // Same department as Manager A
        
        // Create subordinates for Manager A: 1 Worker, 3 SalesPerson, and Manager B
        Worker w1 = new ShiftWorker();
        w1.setDepartment("DepartmentA");
        
        SalesPeople s1 = new SalesPeople();
        s1.setDepartment("DepartmentA");
        
        SalesPeople s2 = new SalesPeople();
        s2.setDepartment("DepartmentA");
        
        SalesPeople s3 = new SalesPeople();
        s3.setDepartment("DepartmentA");
        
        // Create subordinate for Manager B: 1 Worker
        Worker w2 = new ShiftWorker();
        w2.setDepartment("DepartmentA");
        
        // Add all employees to the test list
        testEmployees.add(managerA);
        testEmployees.add(managerB);
        testEmployees.add(w1);
        testEmployees.add(s1);
        testEmployees.add(s2);
        testEmployees.add(s3);
        testEmployees.add(w2);
        
        // Verify the number of subordinates for Manager A is 5
        assertEquals("The number of subordinates for Manager A is 5", 
                     5, managerA.getDirectSubordinateEmployeesCount(testEmployees));
        
        // Verify the number of subordinates for Manager B is 1
        assertEquals("The number of subordinates for Manager B is 1", 
                     1, managerB.getDirectSubordinateEmployeesCount(testEmployees));
    }
}