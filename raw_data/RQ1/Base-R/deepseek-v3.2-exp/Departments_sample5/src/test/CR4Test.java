import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Company company;
    private Department department;
    private Employee employee;
    private Office office;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CheckDepartmentWithAssignedManager() {
        // SetUp: Create Company with Department "Verified"
        department = new Department();
        department.setOffices(new ArrayList<>());
        company.setDepartments(new ArrayList<>());
        company.getDepartments().add(department);
        
        // SetUp: Add Employee "E301" and assign as manager
        employee = new Employee();
        employee.setName("E301");
        employee.setDepartment(department);
        company.setEmployees(new ArrayList<>());
        company.getEmployees().add(employee);
        
        // Assign manager to department
        company.assignManager(department, employee);
        
        // Action: Verify manager assignment for "Verified"
        boolean result = company.hasManager(department);
        
        // Expected Output: true
        assertTrue("Department with assigned manager should return true", result);
    }
    
    @Test
    public void testCase2_CheckManagerlessDepartment() {
        // SetUp: Create Company with Department "Unmanaged"
        department = new Department();
        department.setOffices(new ArrayList<>());
        company.setDepartments(new ArrayList<>());
        company.getDepartments().add(department);
        
        // Action: Verify manager assignment for "Unmanaged"
        boolean result = company.hasManager(department);
        
        // Expected Output: false
        assertFalse("Department without manager should return false", result);
    }
    
    @Test
    public void testCase3_CheckAfterManagerRemoval() {
        // SetUp: Create Company with Department "Temp"
        department = new Department();
        department.setOffices(new ArrayList<>());
        company.setDepartments(new ArrayList<>());
        company.getDepartments().add(department);
        
        // SetUp: Add and assign manager "E302"
        employee = new Employee();
        employee.setName("E302");
        employee.setDepartment(department);
        company.setEmployees(new ArrayList<>());
        company.getEmployees().add(employee);
        company.assignManager(department, employee);
        
        // SetUp: Delete "E302" from company
        company.getEmployees().remove(employee);
        // Note: The manager assignment remains in department object but employee is removed from company
        
        // Action: Verify manager assignment
        boolean result = company.hasManager(department);
        
        // Expected Output: false
        // Since the employee was removed from company, hasManager should return false
        assertFalse("Department should not have manager after employee removal", result);
    }
    
    @Test
    public void testCase4_CheckNonExistentDepartment() {
        // SetUp: Create empty Company
        company.setDepartments(new ArrayList<>());
        
        // Create a department that is not in the company
        Department missingDepartment = new Department();
        missingDepartment.setOffices(new ArrayList<>());
        
        // Action: Verify manager for "Missing"
        boolean result = company.hasManager(missingDepartment);
        
        // Expected Output: false
        assertFalse("Non-existent department should return false", result);
    }
    
    @Test
    public void testCase5_CheckRecentlyAssignedManager() {
        // SetUp: Create Company with Department "NewDept"
        department = new Department();
        department.setOffices(new ArrayList<>());
        company.setDepartments(new ArrayList<>());
        company.getDepartments().add(department);
        
        // SetUp: Add Employee "E303" and immediately assign as manager
        employee = new Employee();
        employee.setName("E303");
        employee.setDepartment(department);
        company.setEmployees(new ArrayList<>());
        company.getEmployees().add(employee);
        company.assignManager(department, employee);
        
        // Action: Verify assignment
        boolean result = company.hasManager(department);
        
        // Expected Output: true
        assertTrue("Recently assigned manager should return true", result);
    }
}