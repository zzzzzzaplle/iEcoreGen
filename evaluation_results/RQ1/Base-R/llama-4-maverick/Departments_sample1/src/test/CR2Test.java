import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private Company company;
    private Department sales;
    private Department finance;
    private Department it;
    private Department rd;
    private Department support;
    private Employee e001;
    private Employee e002;
    private Employee e003;
    private Employee e004;
    private Employee e999;

    @Before
    public void setUp() {
        // Initialize common test objects
        company = new Company();
        
        sales = new Department();
        sales.setName("Sales");
        
        finance = new Department();
        finance.setName("Finance");
        
        it = new Department();
        it.setName("IT");
        
        rd = new Department();
        rd.setName("R&D");
        
        support = new Department();
        support.setName("Support");
        
        e001 = new Employee();
        e001.setName("E001");
        
        e002 = new Employee();
        e002.setName("E002");
        
        e003 = new Employee();
        e003.setName("E003");
        
        e004 = new Employee();
        e004.setName("E004");
        
        e999 = new Employee();
        e999.setName("E999");
    }

    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales"
        List<Department> departments = new ArrayList<>();
        departments.add(sales);
        company.setDepartments(departments);
        
        // SetUp: Add Employee "E001" to Company
        List<Employee> employees = new ArrayList<>();
        employees.add(e001);
        company.setEmployees(employees);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = sales.assignManager(e001);
        
        // Expected Output: true
        assertTrue("Manager assignment should succeed", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be E001", e001, sales.getManager());
    }

    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        List<Department> departments = new ArrayList<>();
        departments.add(finance);
        company.setDepartments(departments);
        
        // SetUp: Create Employee "E999" (but not added to company)
        // Note: e999 is created in setUp but not added to company employees
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = finance.assignManager(e999);
        
        // Expected Output: false
        assertFalse("Manager assignment should fail for non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", finance.getManager());
    }

    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: Create Company with Department "IT"
        List<Department> departments = new ArrayList<>();
        departments.add(it);
        company.setDepartments(departments);
        
        // SetUp: Add Employees "E002" and "E003" to Company
        List<Employee> employees = new ArrayList<>();
        employees.add(e002);
        employees.add(e003);
        company.setEmployees(employees);
        
        // SetUp: Assign "E002" as IT manager
        it.assignManager(e002);
        
        // Action: Assign "E003" as new manager
        boolean result = it.assignManager(e003);
        
        // Expected Output: false
        assertFalse("Manager reassignment should fail when department already has manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", e002, it.getManager());
    }

    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp: Create Company with Departments "R&D" and "Support"
        List<Department> departments = new ArrayList<>();
        departments.add(rd);
        departments.add(support);
        company.setDepartments(departments);
        
        // SetUp: Add Employee "E004" to R&D
        List<Employee> employees = new ArrayList<>();
        employees.add(e004);
        company.setEmployees(employees);
        e004.setDepartment(rd);
        
        // Action: Assign "E004" as Support manager
        boolean result = support.assignManager(e004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Manager assignment should succeed even if employee is from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be E004", e004, support.getManager());
    }

    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // company is already initialized as empty in setUp
        
        // SetUp: Create department "GhostDept" (but not added to company)
        Department ghostDept = new Department();
        ghostDept.setName("GhostDept");
        
        // SetUp: Add Employee "E001" to Company
        List<Employee> employees = new ArrayList<>();
        employees.add(e001);
        company.setEmployees(employees);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = ghostDept.assignManager(e001);
        
        // Expected Output: false
        assertFalse("Manager assignment should fail for non-existent department", result);
        
        // Verification: GhostDept should not have a manager (method returns false before setting manager)
        assertNull("GhostDept should not have a manager", ghostDept.getManager());
    }
}