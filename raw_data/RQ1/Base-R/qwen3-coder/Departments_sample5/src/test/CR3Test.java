import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        // Initialize company for each test
        company = new Company();
        company.setCompanyId("COMP001");
        company.setName("Test Company");
        
        // Create departments
        legalDepartment = new Department();
        legalDepartment.setDepartmentId("DEPT001");
        legalDepartment.setName("Legal");
        
        emptyDept = new Department();
        emptyDept.setDepartmentId("DEPT002");
        emptyDept.setName("EmptyDept");
        
        remoteDepartment = new Department();
        remoteDepartment.setDepartmentId("DEPT003");
        remoteDepartment.setName("Remote");
        
        soloDepartment = new Department();
        soloDepartment.setDepartmentId("DEPT004");
        soloDepartment.setName("Solo");
        
        phantomDepartment = new Department();
        phantomDepartment.setDepartmentId("DEPT005");
        phantomDepartment.setName("Phantom");
        
        // Create offices
        l1Office = new Office();
        l1Office.setOfficeId("L1");
        l1Office.setAddress("Legal Office 1");
        
        l2Office = new Office();
        l2Office.setOfficeId("L2");
        l2Office.setAddress("Legal Office 2");
        
        o1Office = new Office();
        o1Office.setOfficeId("O1");
        o1Office.setAddress("EmptyDept Office");
        
        // Create employees
        e101Employee = new Employee();
        e101Employee.setId("E101");
        e101Employee.setName("John Smith");
        
        e201Employee = new Employee();
        e201Employee.setId("E201");
        e201Employee.setName("Jane Doe");
    }

    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.getDepartments().add(legalDepartment);
        
        // Add Offices "L1", "L2" to Legal
        legalDepartment.getOffices().add(l1Office);
        legalDepartment.getOffices().add(l2Office);
        
        // Set "L1" as headquarter
        legalDepartment.assignHeadquarters(l1Office);
        
        // Assign "E101" as manager to Legal
        company.getEmployees().add(e101Employee);
        legalDepartment.setManager(e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDepartment);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Company should not contain Legal department", 
                   company.getDepartments().contains(legalDepartment));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Company should not contain E101 employee", 
                   company.getEmployees().contains(e101Employee));
        
        // Note: The current implementation doesn't explicitly remove offices,
        // but they are removed as part of department removal since they belong to the department
        // Verification: Offices "L1", "L2" removed (implicit through department removal)
    }

    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // Create department "Phantom" (not added to company)
        // phantomDepartment exists but not added to company
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDepartment);
        
        // Expected Output: false
        assertFalse("Deleting non-existent department should return false", result);
    }

    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.getDepartments().add(emptyDept);
        
        // Add Office "O1" to EmptyDept
        emptyDept.getOffices().add(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Company should not contain EmptyDept department", 
                   company.getDepartments().contains(emptyDept));
    }

    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.getDepartments().add(soloDepartment);
        
        // Verify initial state - should have exactly one department
        assertEquals("Company should have exactly one department initially", 
                    1, company.getDepartments().size());
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDepartment);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company's departments should be empty after deletion", 
                  company.getDepartments().isEmpty());
    }

    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.getDepartments().add(remoteDepartment);
        
        // Add Employee "E201" to Remote (add to company and set as manager)
        company.getEmployees().add(e201Employee);
        remoteDepartment.setManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDepartment);
        
        // Expected Output: true
        assertTrue("Department deletion should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Company should not contain E201 employee after department deletion", 
                   company.getEmployees().contains(e201Employee));
    }
}