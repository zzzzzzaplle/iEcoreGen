import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private Company company;
    private Department legalDepartment;
    private Department emptyDept;
    private Department remoteDepartment;
    private Department soloDepartment;
    private Department phantomDepartment;
    private Office l1Office;
    private Office l2Office;
    private Office o1Office;
    private Employee e101Employee;
    private Employee e201Employee;

    @Before
    public void setUp() {
        // Initialize company and common test objects
        company = new Company();
        
        // Create departments
        legalDepartment = new Department();
        emptyDept = new Department();
        remoteDepartment = new Department();
        soloDepartment = new Department();
        phantomDepartment = new Department();
        
        // Create offices
        l1Office = new Office();
        l2Office = new Office();
        o1Office = new Office();
        
        // Create employees
        e101Employee = new Employee();
        e201Employee = new Employee();
    }

    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.getDepartments().add(legalDepartment);
        
        // SetUp: Add Offices "L1", "L2" to Legal
        legalDepartment.getOffices().add(l1Office);
        legalDepartment.getOffices().add(l2Office);
        
        // SetUp: Set "L1" as headquarter
        legalDepartment.setHeadquarters(l1Office);
        
        // SetUp: Assign "E101" as manager to Legal
        e101Employee.setDepartment(legalDepartment);
        company.getEmployees().add(e101Employee);
        legalDepartment.setManager(e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDepartment);
        
        // Expected Output: true
        assertTrue("Delete should return true for existing department", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                   company.getDepartments().contains(legalDepartment));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", 
                   company.getEmployees().contains(e101Employee));
        
        // Verification: Offices "L1", "L2" removed (implicitly through department removal)
        // Note: The current implementation doesn't maintain a separate office list at company level
    }

    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // SetUp: Create department "Phantom" (but don't add to company)
        // phantomDepartment is created in setUp but not added to company
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDepartment);
        
        // Expected Output: false
        assertFalse("Delete should return false for non-existent department", result);
    }

    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.getDepartments().add(emptyDept);
        
        // SetUp: Add Office "O1" to EmptyDept
        emptyDept.getOffices().add(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Delete should return true for department with only offices", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept should be removed from company", 
                   company.getDepartments().contains(emptyDept));
    }

    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.getDepartments().add(soloDepartment);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDepartment);
        
        // Expected Output: true
        assertTrue("Delete should return true for last remaining department", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                  company.getDepartments().isEmpty());
    }

    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.getDepartments().add(remoteDepartment);
        
        // SetUp: Add Employee "E201" to Remote
        e201Employee.setDepartment(remoteDepartment);
        company.getEmployees().add(e201Employee);
        
        // SetUp: Assign "E201" as manager
        remoteDepartment.setManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDepartment);
        
        // Expected Output: true
        assertTrue("Delete should return true for department with manager but no offices", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                   company.getEmployees().contains(e201Employee));
    }
}