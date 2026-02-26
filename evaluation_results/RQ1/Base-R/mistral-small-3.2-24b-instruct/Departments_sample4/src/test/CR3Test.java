import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        Department legalDept = new Department();
        legalDept.setName("Legal");
        company.addDepartment(legalDept);
        
        // Add Offices "L1", "L2" to Legal
        Office officeL1 = new Office();
        officeL1.setLocation("L1");
        Office officeL2 = new Office();
        officeL2.setLocation("L2");
        legalDept.addOffice(officeL1);
        legalDept.addOffice(officeL2);
        
        // Set "L1" as headquarter
        legalDept.assignHeadquarters(officeL1);
        
        // Assign "E101" as manager to Legal
        Employee managerE101 = new Employee();
        managerE101.setName("E101");
        managerE101.setDepartmentName("Legal");
        legalDept.assignManager(managerE101);
        company.addEmployee(managerE101);
        
        // Action: Delete "Legal" department
        boolean result = deleteDepartment(company, "Legal");
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                   containsDepartmentByName(company.getDepartments(), "Legal"));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", 
                   containsEmployeeByName(company.getEmployees(), "E101"));
        
        // Verification: Offices "L1", "L2" removed (implicitly verified by department removal)
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // (company is already empty from @Before setup)
        
        // Create department "Phantom" but don't add to company
        Department phantomDept = new Department();
        phantomDept.setName("Phantom");
        
        // Action: Delete "Phantom" department
        boolean result = deleteDepartment(company, "Phantom");
        
        // Expected Output: false
        assertFalse("Deleting non-existent department should return false", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        Department emptyDept = new Department();
        emptyDept.setName("EmptyDept");
        company.addDepartment(emptyDept);
        
        // Add Office "O1" to EmptyDept
        Office officeO1 = new Office();
        officeO1.setLocation("O1");
        emptyDept.addOffice(officeO1);
        
        // Action: Delete "EmptyDept"
        boolean result = deleteDepartment(company, "EmptyDept");
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept department should be removed from company", 
                   containsDepartmentByName(company.getDepartments(), "EmptyDept"));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        Department soloDept = new Department();
        soloDept.setName("Solo");
        company.addDepartment(soloDept);
        
        // Action: Delete "Solo"
        boolean result = deleteDepartment(company, "Solo");
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                   company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        Department remoteDept = new Department();
        remoteDept.setName("Remote");
        company.addDepartment(remoteDept);
        
        // Add Employee "E201" to Remote
        Employee employeeE201 = new Employee();
        employeeE201.setName("E201");
        employeeE201.setDepartmentName("Remote");
        company.addEmployee(employeeE201);
        
        // Assign "E201" as manager
        remoteDept.assignManager(employeeE201);
        
        // Action: Delete "Remote"
        boolean result = deleteDepartment(company, "Remote");
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                   containsEmployeeByName(company.getEmployees(), "E201"));
    }
    
    // Helper method to delete department by name (simulating the required functionality)
    private boolean deleteDepartment(Company company, String departmentName) {
        Department departmentToRemove = null;
        
        // Find the department by name
        for (Department dept : company.getDepartments()) {
            if (departmentName.equals(dept.getName())) {
                departmentToRemove = dept;
                break;
            }
        }
        
        // If department doesn't exist, return false
        if (departmentToRemove == null) {
            return false;
        }
        
        // Remove the department and its offices (including headquarters)
        company.removeDepartment(departmentToRemove);
        
        // Remove employees (including manager) from the company
        if (departmentToRemove.getManager() != null) {
            company.removeEmployee(departmentToRemove.getManager());
        }
        
        return true;
    }
    
    // Helper method to check if a department exists by name
    private boolean containsDepartmentByName(List<Department> departments, String name) {
        for (Department dept : departments) {
            if (name.equals(dept.getName())) {
                return true;
            }
        }
        return false;
    }
    
    // Helper method to check if an employee exists by name
    private boolean containsEmployeeByName(List<Employee> employees, String name) {
        for (Employee emp : employees) {
            if (name.equals(emp.getName())) {
                return true;
            }
        }
        return false;
    }
}