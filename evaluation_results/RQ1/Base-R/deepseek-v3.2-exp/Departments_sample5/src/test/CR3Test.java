import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    
    private Company company;
    private Department legalDept;
    private Department emptyDept;
    private Department soloDept;
    private Department remoteDept;
    private Department phantomDept;
    private Office l1;
    private Office l2;
    private Office o1;
    private Employee e101;
    private Employee e201;
    
    @Before
    public void setUp() {
        // Initialize company and common test objects
        company = new Company();
        
        // Create departments
        legalDept = new Department();
        legalDept.setOffices(new ArrayList<>());
        
        emptyDept = new Department();
        emptyDept.setOffices(new ArrayList<>());
        
        soloDept = new Department();
        
        remoteDept = new Department();
        remoteDept.setOffices(new ArrayList<>());
        
        phantomDept = new Department();
        
        // Create offices
        l1 = new Office();
        l1.setLocation("L1");
        
        l2 = new Office();
        l2.setLocation("L2");
        
        o1 = new Office();
        o1.setLocation("O1");
        
        // Create employees
        e101 = new Employee();
        e101.setName("E101");
        
        e201 = new Employee();
        e201.setName("E201");
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.getDepartments().add(legalDept);
        
        // Add Offices "L1", "L2" to Legal
        legalDept.getOffices().add(l1);
        legalDept.getOffices().add(l2);
        
        // Set "L1" as headquarter
        legalDept.setHeadquarters(l1);
        
        // Assign "E101" as manager to Legal
        legalDept.setManager(e101);
        company.getEmployees().add(e101);
        e101.setDepartment(legalDept);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true for existing department", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                   company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", 
                   company.getEmployees().contains(e101));
        
        // Note: Offices are not stored in company, so we verify through the department
        // Since department is removed, its offices are effectively removed from the company context
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setup
        
        // Create department "Phantom" but don't add to company
        // phantomDept is created in setup but not added to company
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Department deletion should return false for non-existent department", result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.getDepartments().add(emptyDept);
        
        // Add Office "O1" to EmptyDept
        emptyDept.getOffices().add(o1);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true for department with only offices", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept should be removed from company", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.getDepartments().add(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true for last remaining department", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                   company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.getDepartments().add(remoteDept);
        
        // Add Employee "E201" to Remote
        remoteDept.setManager(e201);
        company.getEmployees().add(e201);
        e201.setDepartment(remoteDept);
        
        // Assign "E201" as manager (already done above)
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true for department with manager but no offices", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                   company.getEmployees().contains(e201));
    }
}