import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Company company;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        company = new Company();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleManagerWithMultipleSubordinates() throws Exception {
        // Setup: Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        managerM1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        managerM1.setSocialInsuranceNumber("123-45-6789");
        managerM1.setDepartment("Management");
        
        // Create subordinates: Worker W1, SalesPerson S1, and Manager M2
        Worker workerW1 = new ShiftWorker();
        workerW1.setName("W1");
        workerW1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        workerW1.setSocialInsuranceNumber("987-65-4321");
        workerW1.setDepartment("Production");
        
        SalesPeople salesPersonS1 = new SalesPeople();
        salesPersonS1.setName("S1");
        salesPersonS1.setBirthDate(dateFormat.parse("1985-01-01 00:00:00"));
        salesPersonS1.setSocialInsuranceNumber("555-55-5555");
        salesPersonS1.setDepartment("Sales");
        
        Manager managerM2 = new Manager();
        managerM2.setName("M2");
        managerM2.setBirthDate(dateFormat.parse("1975-01-01 00:00:00"));
        managerM2.setSocialInsuranceNumber("111-11-1111");
        managerM2.setDepartment("Management");
        
        // Create department and add manager with subordinates
        Department department = new Department();
        department.setType(DepartmentType.CONTROL);
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        subordinates.add(salesPersonS1);
        subordinates.add(managerM2);
        department.setEmployees(subordinates);
        
        // Set department for manager M1 (simulating direct subordinates)
        managerM1.setDepartment("Management");
        
        // Test the getDirectSubordinateEmployeesCount method
        int actualCount = managerM1.getDirectSubordinateEmployeesCount();
        
        // Expected Output: The number of direct subordinates for Manager M1 is 3.
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 3, actualCount);
    }
    
    @Test
    public void testCase2_ManagerWithNoSubordinates() throws Exception {
        // Setup: Create Manager M1 with no subordinates
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        managerM1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        managerM1.setSocialInsuranceNumber("123-45-6789");
        managerM1.setDepartment("Management");
        
        // Create empty department (no subordinates)
        Department department = new Department();
        department.setType(DepartmentType.CONTROL);
        department.setEmployees(new ArrayList<>());
        
        // Test the getDirectSubordinateEmployeesCount method
        int actualCount = managerM1.getDirectSubordinateEmployeesCount();
        
        // Expected Output: The number of subordinates for the manager is 0.
        assertEquals("The number of subordinates for the manager is 0.", 0, actualCount);
    }
    
    @Test
    public void testCase3_MultipleManagersWithDifferentNumberOfSubordinates() throws Exception {
        // Setup: Create Manager A and assign 3 subordinates
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setBirthDate(dateFormat.parse("1970-01-01 00:00:00"));
        managerA.setSocialInsuranceNumber("111-11-1111");
        managerA.setDepartment("Management");
        
        // Create subordinates for Manager A: 1 ShiftWorker, 1 OffShiftWorker, 1 SalesPeople
        ShiftWorker shiftWorkerA1 = new ShiftWorker();
        shiftWorkerA1.setName("SW1");
        shiftWorkerA1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        shiftWorkerA1.setSocialInsuranceNumber("222-22-2222");
        shiftWorkerA1.setDepartment("Production");
        
        OffShiftWorker offShiftWorkerA1 = new OffShiftWorker();
        offShiftWorkerA1.setName("OSW1");
        offShiftWorkerA1.setBirthDate(dateFormat.parse("1991-01-01 00:00:00"));
        offShiftWorkerA1.setSocialInsuranceNumber("333-33-3333");
        offShiftWorkerA1.setDepartment("Production");
        
        SalesPeople salesPersonA1 = new SalesPeople();
        salesPersonA1.setName("SP1");
        salesPersonA1.setBirthDate(dateFormat.parse("1985-01-01 00:00:00"));
        salesPersonA1.setSocialInsuranceNumber("444-44-4444");
        salesPersonA1.setDepartment("Sales");
        
        Department departmentA = new Department();
        departmentA.setType(DepartmentType.CONTROL);
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(shiftWorkerA1);
        subordinatesA.add(offShiftWorkerA1);
        subordinatesA.add(salesPersonA1);
        departmentA.setEmployees(subordinatesA);
        
        // Setup: Create Manager B and assign 7 subordinates
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setBirthDate(dateFormat.parse("1975-01-01 00:00:00"));
        managerB.setSocialInsuranceNumber("555-55-5555");
        managerB.setDepartment("Management");
        
        // Create subordinates for Manager B: 2 ShiftWorker, 2 OffShiftWorker, 3 SalesPeople
        List<Employee> subordinatesB = new ArrayList<>();
        
        for (int i = 1; i <= 2; i++) {
            ShiftWorker sw = new ShiftWorker();
            sw.setName("SW" + (i + 1));
            sw.setBirthDate(dateFormat.parse("199" + i + "-01-01 00:00:00"));
            sw.setSocialInsuranceNumber("666-66-666" + i);
            sw.setDepartment("Production");
            subordinatesB.add(sw);
        }
        
        for (int i = 1; i <= 2; i++) {
            OffShiftWorker osw = new OffShiftWorker();
            osw.setName("OSW" + (i + 1));
            osw.setBirthDate(dateFormat.parse("199" + (i + 2) + "-01-01 00:00:00"));
            osw.setSocialInsuranceNumber("777-77-777" + i);
            osw.setDepartment("Production");
            subordinatesB.add(osw);
        }
        
        for (int i = 1; i <= 3; i++) {
            SalesPeople sp = new SalesPeople();
            sp.setName("SP" + (i + 1));
            sp.setBirthDate(dateFormat.parse("198" + i + "-01-01 00:00:00"));
            sp.setSocialInsuranceNumber("888-88-888" + i);
            sp.setDepartment("Sales");
            subordinatesB.add(sp);
        }
        
        Department departmentB = new Department();
        departmentB.setType(DepartmentType.CONTROL);
        departmentB.setEmployees(subordinatesB);
        
        // Test the getDirectSubordinateEmployeesCount method for both managers
        int countA = managerA.getDirectSubordinateEmployeesCount();
        int countB = managerB.getDirectSubordinateEmployeesCount();
        
        // Expected Output: The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7.
        assertEquals("The number of subordinates for Manager A is 3.", 3, countA);
        assertEquals("The number of subordinates for Manager B is 7.", 7, countB);
    }
    
    @Test
    public void testCase4_ManagerWithSingleSubordinate() throws Exception {
        // Setup: Create Manager M1
        Manager managerM1 = new Manager();
        managerM1.setName("M1");
        managerM1.setBirthDate(dateFormat.parse("1980-01-01 00:00:00"));
        managerM1.setSocialInsuranceNumber("123-45-6789");
        managerM1.setDepartment("Management");
        
        // Create subordinate: Worker W1
        Worker workerW1 = new ShiftWorker();
        workerW1.setName("W1");
        workerW1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        workerW1.setSocialInsuranceNumber("987-65-4321");
        workerW1.setDepartment("Production");
        
        // Create department with single subordinate
        Department department = new Department();
        department.setType(DepartmentType.CONTROL);
        List<Employee> subordinates = new ArrayList<>();
        subordinates.add(workerW1);
        department.setEmployees(subordinates);
        
        // Test the getDirectSubordinateEmployeesCount method
        int actualCount = managerM1.getDirectSubordinateEmployeesCount();
        
        // Expected Output: The number of subordinates for the manager is 1.
        assertEquals("The number of subordinates for the manager is 1.", 1, actualCount);
    }
    
    @Test
    public void testCase5_MultipleManagersWithNestedSubordinates() throws Exception {
        // Setup: Create Manager A and assign 5 subordinates
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        managerA.setBirthDate(dateFormat.parse("1970-01-01 00:00:00"));
        managerA.setSocialInsuranceNumber("111-11-1111");
        managerA.setDepartment("Management");
        
        // Create subordinates for Manager A: 1 Worker, 3 SalesPerson, 1 Manager B
        Worker workerW1 = new ShiftWorker();
        workerW1.setName("W1");
        workerW1.setBirthDate(dateFormat.parse("1990-01-01 00:00:00"));
        workerW1.setSocialInsuranceNumber("222-22-2222");
        workerW1.setDepartment("Production");
        
        SalesPeople salesPersonS1 = new SalesPeople();
        salesPersonS1.setName("S1");
        salesPersonS1.setBirthDate(dateFormat.parse("1985-01-01 00:00:00"));
        salesPersonS1.setSocialInsuranceNumber("333-33-3333");
        salesPersonS1.setDepartment("Sales");
        
        SalesPeople salesPersonS2 = new SalesPeople();
        salesPersonS2.setName("S2");
        salesPersonS2.setBirthDate(dateFormat.parse("1986-01-01 00:00:00"));
        salesPersonS2.setSocialInsuranceNumber("444-44-4444");
        salesPersonS2.setDepartment("Sales");
        
        SalesPeople salesPersonS3 = new SalesPeople();
        salesPersonS3.setName("S3");
        salesPersonS3.setBirthDate(dateFormat.parse("1987-01-01 00:00:00"));
        salesPersonS3.setSocialInsuranceNumber("555-55-5555");
        salesPersonS3.setDepartment("Sales");
        
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        managerB.setBirthDate(dateFormat.parse("1975-01-01 00:00:00"));
        managerB.setSocialInsuranceNumber("666-66-6666");
        managerB.setDepartment("Management");
        
        Department departmentA = new Department();
        departmentA.setType(DepartmentType.CONTROL);
        List<Employee> subordinatesA = new ArrayList<>();
        subordinatesA.add(workerW1);
        subordinatesA.add(salesPersonS1);
        subordinatesA.add(salesPersonS2);
        subordinatesA.add(salesPersonS3);
        subordinatesA.add(managerB);
        departmentA.setEmployees(subordinatesA);
        
        // Setup: Create Manager B and assign 1 subordinate
        Worker workerW2 = new ShiftWorker();
        workerW2.setName("W2");
        workerW2.setBirthDate(dateFormat.parse("1995-01-01 00:00:00"));
        workerW2.setSocialInsuranceNumber("777-77-7777");
        workerW2.setDepartment("Production");
        
        Department departmentB = new Department();
        departmentB.setType(DepartmentType.CONTROL);
        List<Employee> subordinatesB = new ArrayList<>();
        subordinatesB.add(workerW2);
        departmentB.setEmployees(subordinatesB);
        
        // Test the getDirectSubordinateEmployeesCount method for both managers
        int countA = managerA.getDirectSubordinateEmployeesCount();
        int countB = managerB.getDirectSubordinateEmployeesCount();
        
        // Expected Output: The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1.
        assertEquals("The number of subordinates for Manager A is 5.", 5, countA);
        assertEquals("The number of subordinates for Manager B is 1.", 1, countB);
    }
}