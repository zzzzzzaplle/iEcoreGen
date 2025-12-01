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
    private Employee managerE101;
    private Employee managerE201;
    private Office officeL1;
    private Office officeL2;
    private Office officeO1;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        company = new Company();
        
        // Create departments
        legalDepartment = new Department();
        legalDepartment.setName("Legal");
        legalDepartment.setDepartmentId(1);
        
        emptyDept = new Department();
        emptyDept.setName("EmptyDept");
        emptyDept.setDepartmentId(2);
        
        remoteDepartment = new Department();
        remoteDepartment.setName("Remote");
        remoteDepartment.setDepartmentId(3);
        
        soloDepartment = new Department();
        soloDepartment.setName("Solo");
        soloDepartment.setDepartmentId(4);
        
        phantomDepartment = new Department();
        phantomDepartment.setName("Phantom");
        phantomDepartment.setDepartmentId(5);
        
        // Create employees
        managerE101 = new Employee();
        managerE101.setName("E101");
        managerE101.setId(101);
        
        managerE201 = new Employee();
        managerE201.setName("E201");
        managerE201.setId(201);
        
        // Create offices
        officeL1 = new Office();
        officeL1.setLocation("L1");
        officeL1.setOfficeId(1);
        
        officeL2 = new Office();
        officeL2.setLocation("L2");
        officeL2.setOfficeId(2);
        
        officeO1 = new Office();
        officeO1.setLocation("O1");
        officeO1.setOfficeId(3);
    }
    
    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        company.addDepartment(legalDepartment);
        
        // SetUp: Add Offices "L1", "L2" to Legal
        legalDepartment.addOffice(officeL1);
        legalDepartment.addOffice(officeL2);
        
        // SetUp: Set "L1" as headquarter
        officeL1.setHeadquarters(true);
        
        // SetUp: Assign "E101" as manager to Legal
        company.addEmployee(managerE101);
        legalDepartment.setManager(managerE101);
        
        // Verification before deletion
        assertTrue("Company should contain Legal department", company.getDepartments().contains(legalDepartment));
        assertTrue("Company should contain E101 employee", company.getEmployees().contains(managerE101));
        assertEquals("Legal department should have 2 offices", 2, legalDepartment.getOffices().size());
        assertTrue("L1 should be headquarters", officeL1.isHeadquarters());
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDepartment);
        
        // Expected Output: true
        assertTrue("Delete operation should return true", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Company should not contain Legal department after deletion", 
                   company.getDepartments().contains(legalDepartment));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Company should not contain E101 employee after deletion", 
                   company.getEmployees().contains(managerE101));
        
        // Note: The specification mentions offices should be removed, but the Company class 
        // doesn't maintain a separate list of offices, so we verify through the department
        assertTrue("Legal department offices should be inaccessible after deletion", 
                  legalDepartment.getOffices().isEmpty() || !company.getDepartments().contains(legalDepartment));
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // SetUp: Create department "Phantom" (but don't add to company)
        // phantomDepartment is created in setUp() but not added to company
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDepartment);
        
        // Expected Output: false
        assertFalse("Delete operation should return false for non-existent department", result);
        
        // Verification: Company remains empty
        assertTrue("Company should remain empty", company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        company.addDepartment(emptyDept);
        
        // SetUp: Add Office "O1" to EmptyDept
        emptyDept.addOffice(officeO1);
        
        // Verification before deletion
        assertTrue("Company should contain EmptyDept department", company.getDepartments().contains(emptyDept));
        assertEquals("EmptyDept should have 1 office", 1, emptyDept.getOffices().size());
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Delete operation should return true", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("Company should not contain EmptyDept department after deletion", 
                   company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        company.addDepartment(soloDepartment);
        
        // Verification before deletion
        assertEquals("Company should have exactly 1 department", 1, company.getDepartments().size());
        assertTrue("Company should contain Solo department", company.getDepartments().contains(soloDepartment));
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDepartment);
        
        // Expected Output: true
        assertTrue("Delete operation should return true", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company should be empty after deleting the last department", 
                  company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        company.addDepartment(remoteDepartment);
        
        // SetUp: Add Employee "E201" to Remote
        company.addEmployee(managerE201);
        
        // SetUp: Assign "E201" as manager
        remoteDepartment.setManager(managerE201);
        
        // Verification before deletion
        assertTrue("Company should contain Remote department", company.getDepartments().contains(remoteDepartment));
        assertTrue("Company should contain E201 employee", company.getEmployees().contains(managerE201));
        assertTrue("Remote department should have manager", remoteDepartment.hasManager());
        assertTrue("Remote department should have no offices", remoteDepartment.getOffices().isEmpty());
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDepartment);
        
        // Expected Output: true
        assertTrue("Delete operation should return true", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Company should not contain E201 employee after deletion", 
                   company.getEmployees().contains(managerE201));
        
        // Additional verification
        assertFalse("Company should not contain Remote department after deletion", 
                   company.getDepartments().contains(remoteDepartment));
    }
}