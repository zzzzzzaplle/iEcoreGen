import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private Department salesDept;
    private Department financeDept;
    private Department itDept;
    private Department rdDept;
    private Department supportDept;
    private Employee empE001;
    private Employee empE002;
    private Employee empE003;
    private Employee empE004;
    private Employee empE999;
    
    @Before
    public void setUp() {
        company = new Company();
        
        // Initialize departments
        salesDept = new Department();
        salesDept.setName("Sales");
        
        financeDept = new Department();
        financeDept.setName("Finance");
        
        itDept = new Department();
        itDept.setName("IT");
        
        rdDept = new Department();
        rdDept.setName("R&D");
        
        supportDept = new Department();
        supportDept.setName("Support");
        
        // Initialize employees
        empE001 = new Employee();
        empE001.setName("E001");
        
        empE002 = new Employee();
        empE002.setName("E002");
        
        empE003 = new Employee();
        empE003.setName("E003");
        
        empE004 = new Employee();
        empE004.setName("E004");
        
        empE999 = new Employee();
        empE999.setName("E999");
    }
    
    @Test
    public void testCase1_AssignValidEmployeeToManagerlessDepartment() {
        // SetUp: Create Company with Department "Sales" and add Employee "E001" to Company
        List<Department> deptList = new ArrayList<>();
        deptList.add(salesDept);
        company.setDepartments(deptList);
        
        List<Employee> empList = new ArrayList<>();
        empList.add(empE001);
        company.setEmployees(empList);
        
        // Action: Assign "E001" as manager to "Sales"
        boolean result = company.assignManager(salesDept, empE001);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid employee to managerless department", result);
        
        // Verification: Sales's manager == E001
        assertEquals("Sales department manager should be E001", empE001, salesDept.getManager());
    }
    
    @Test
    public void testCase2_AssignNonExistentEmployee() {
        // SetUp: Create Company with Department "Finance"
        List<Department> deptList = new ArrayList<>();
        deptList.add(financeDept);
        company.setDepartments(deptList);
        
        // Note: Employee E999 is created but NOT added to company
        
        // Action: Assign "E999" (non-existent) as manager
        boolean result = company.assignManager(financeDept, empE999);
        
        // Expected Output: false
        assertFalse("Should return false when assigning non-existent employee", result);
        
        // Verification: Finance do not have a manager
        assertNull("Finance department should not have a manager", financeDept.getManager());
    }
    
    @Test
    public void testCase3_ReassignToDepartmentWithExistingManager() {
        // SetUp: 
        // 1. Create Company with Department "IT"
        // 2. Add Employees "E002" and "E003" to Company
        // 3. Assign "E002" as IT manager
        List<Department> deptList = new ArrayList<>();
        deptList.add(itDept);
        company.setDepartments(deptList);
        
        List<Employee> empList = new ArrayList<>();
        empList.add(empE002);
        empList.add(empE003);
        company.setEmployees(empList);
        
        // First assignment
        company.assignManager(itDept, empE002);
        
        // Action: Assign "E003" as new manager
        boolean result = company.assignManager(itDept, empE003);
        
        // Expected Output: false
        assertFalse("Should return false when reassigning to department with existing manager", result);
        
        // Verification: IT's manager remains E002
        assertEquals("IT department manager should remain E002", empE002, itDept.getManager());
    }
    
    @Test
    public void testCase4_AssignEmployeeFromDifferentDepartment() {
        // SetUp:
        // 1. Create Company with Departments "R&D" and "Support"
        // 2. Add Employee "E004" to R&D
        List<Department> deptList = new ArrayList<>();
        deptList.add(rdDept);
        deptList.add(supportDept);
        company.setDepartments(deptList);
        
        List<Employee> empList = new ArrayList<>();
        empList.add(empE004);
        company.setEmployees(empList);
        
        // Assign employee to R&D department
        empE004.setDepartment(rdDept);
        
        // Action: Assign "E004" as Support manager
        boolean result = company.assignManager(supportDept, empE004);
        
        // Expected Output: true (since employee belongs to company)
        assertTrue("Should return true when assigning employee from different department", result);
        
        // Verification: Support's manager is E004
        assertEquals("Support department manager should be E004", empE004, supportDept.getManager());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp:
        // 1. Create empty Company (no departments)
        // 2. Create department "GhostDept" (but not added to company)
        Department ghostDept = new Department();
        ghostDept.setName("GhostDept");
        
        // Add employee to company
        List<Employee> empList = new ArrayList<>();
        empList.add(empE001);
        company.setEmployees(empList);
        
        // Action: Assign Employee "E001" to "GhostDept"
        boolean result = company.assignManager(ghostDept, empE001);
        
        // Expected Output: false
        assertFalse("Should return false when assigning to non-existent department", result);
    }
}