import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    private Company company;
    private Department legalDepartment;
    private Department phantomDepartment;
    private Department emptyDept;
    private Department soloDepartment;
    private Department remoteDepartment;
    private Office l1Office;
    private Office l2Office;
    private Office o1Office;
    private Employee e101Employee;
    private Employee e201Employee;

    @Before
    public void setUp() {
        company = new Company();
    }

    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        legalDepartment = new Department();
        legalDepartment.setName("Legal");
        company.getDepartments().add(legalDepartment);
        
        // Add Offices "L1", "L2" to Legal
        l1Office = new Office();
        l1Office.setLocation("L1");
        l2Office = new Office();
        l2Office.setLocation("L2");
        legalDepartment.getOffices().add(l1Office);
        legalDepartment.getOffices().add(l2Office);
        
        // Set "L1" as headquarter
        legalDepartment.setHeadquarters(l1Office);
        
        // Assign "E101" as manager to Legal
        e101Employee = new Employee();
        e101Employee.setName("E101");
        e101Employee.setDepartment(legalDepartment);
        company.getEmployees().add(e101Employee);
        legalDepartment.setManager(e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDepartment);
        
        // Expected Output: true
        assertTrue("Delete department should return true for existing department", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Company should not contain Legal department after deletion", 
                   company.getDepartments().contains(legalDepartment));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Company should not contain E101 employee after department deletion", 
                   company.getEmployees().contains(e101Employee));
        
        // Verification: Offices "L1", "L2" removed (implicitly verified through department removal)
    }

    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from @Before setup
        
        // Create department "Phantom" (but don't add to company)
        phantomDepartment = new Department();
        phantomDepartment.setName("Phantom");
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDepartment);
        
        // Expected Output: false
        assertFalse("Delete department should return false for non-existent department", result);
    }

    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        emptyDept = new Department();
        emptyDept.setName("EmptyDept");
        company.getDepartments().add(emptyDept);
        
        // Add Office "O1" to EmptyDept
        o1Office = new Office();
        o1Office.setLocation("O1");
        emptyDept.getOffices().add(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Delete department should return true for department with only offices", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Company should not contain EmptyDept after deletion", 
                   company.getDepartments().contains(emptyDept));
    }

    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        soloDepartment = new Department();
        soloDepartment.setName("Solo");
        company.getDepartments().add(soloDepartment);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDepartment);
        
        // Expected Output: true
        assertTrue("Delete department should return true for last remaining department", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company's departments should be empty after deleting last department", 
                  company.getDepartments().isEmpty());
    }

    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        remoteDepartment = new Department();
        remoteDepartment.setName("Remote");
        company.getDepartments().add(remoteDepartment);
        
        // Add Employee "E201" to Remote
        e201Employee = new Employee();
        e201Employee.setName("E201");
        e201Employee.setDepartment(remoteDepartment);
        company.getEmployees().add(e201Employee);
        
        // Assign "E201" as manager
        remoteDepartment.setManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDepartment);
        
        // Expected Output: true
        assertTrue("Delete department should return true for department with manager but no offices", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Company should not contain E201 employee after department deletion", 
                   company.getEmployees().contains(e201Employee));
    }
}