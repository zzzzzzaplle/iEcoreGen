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
    private Department ghostDept;

    @Before
    public void setUp() {
        company = new Company();
        
        // Initialize departments
        sales = new Department();
        finance = new Department();
        it = new Department();
        rd = new Department();
        support = new Department();
        ghostDept = new Department();
        
        // Initialize employees
        e001 = new Employee();
        e002 = new Employee();
        e003 = new Employee();
        e004 = new Employee();
        e999 = new Employee();
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
        boolean result = company.assignManager(sales, e001);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Sales's manager == E001
        assertEquals(e001, sales.getManager());
    }

    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        List<Department> departments = new ArrayList<>();
        departments.add(finance);
        company.setDepartments(departments);
        
        // SetUp: Create Employee "E999" (but not added to company)
        // Note: e999 exists but is not added to company employees list
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(finance, e999);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: Finance do not have a manager
        assertNull(finance.getManager());
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
        company.assignManager(it, e002);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(it, e003);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: IT's manager remains E002
        assertEquals(e002, it.getManager());
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
        
        // Set employee department to R&D
        e004.setDepartment(rd);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(support, e004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue(result);
        
        // Verification: Support's manager is E004
        assertEquals(e004, support.getManager());
    }

    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company already created in setUp with no departments
        
        // SetUp: Create department "GhostDept" (but not added to company)
        // ghostDept exists but is not added to company departments list
        
        // SetUp: Add Employee "E001" to Company
        List<Employee> employees = new ArrayList<>();
        employees.add(e001);
        company.setEmployees(employees);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(ghostDept, e001);
        
        // Expected Output: false
        assertFalse(result);
    }
}