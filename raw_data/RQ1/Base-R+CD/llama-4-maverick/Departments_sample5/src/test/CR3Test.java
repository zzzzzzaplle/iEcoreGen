import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private Company company;
    private Department legalDept;
    private Department emptyDept;
    private Department soloDept;
    private Department remoteDept;
    private Department phantomDept;
    private Office l1Office;
    private Office l2Office;
    private Office o1Office;
    private Employee e101Employee;
    private Employee e201Employee;

    @Before
    public void setUp() {
        company = new Company();
        
        // Setup for test case 1
        legalDept = new Department();
        l1Office = new Office("L1");
        l2Office = new Office("L2");
        legalDept.getOffices().add(l1Office);
        legalDept.getOffices().add(l2Office);
        legalDept.assignOfficeAsHeadquarter(l1Office);
        e101Employee = new Employee("E101");
        company.addEmployee(e101Employee);
        legalDept.setManager(e101Employee);
        company.addDepartment(legalDept);
        
        // Setup for test case 3
        emptyDept = new Department();
        o1Office = new Office("O1");
        emptyDept.getOffices().add(o1Office);
        company.addDepartment(emptyDept);
        
        // Setup for test case 4
        soloDept = new Department();
        company.addDepartment(soloDept);
        
        // Setup for test case 5
        remoteDept = new Department();
        e201Employee = new Employee("E201");
        company.addEmployee(e201Employee);
        remoteDept.setManager(e201Employee);
        company.addDepartment(remoteDept);
        
        // Setup for test case 2
        phantomDept = new Department();
    }

    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue("Should return true when deleting existing department", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", company.getEmployees().contains(e101Employee));
        
        // Verification: Offices "L1", "L2" removed
        // Note: We can't directly verify office removal from company since offices are stored at department level
        // But we can verify that the department's offices are properly handled during deletion
        assertTrue("Legal department offices should be cleared", legalDept.getOffices().isEmpty());
        assertNull("Legal department headquarter should be null", legalDept.getHeadquarter());
        assertNull("Legal department manager should be null", legalDept.getManager());
    }

    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // Action: Delete "Phantom" department (not added to company)
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse("Should return false when deleting non-existent department", result);
    }

    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Should return true when deleting department with only offices", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept should be removed from company", company.getDepartments().contains(emptyDept));
    }

    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // Remove all departments except Solo
        company.deleteDepartment(legalDept);
        company.deleteDepartment(emptyDept);
        company.deleteDepartment(remoteDept);
        
        // Action: Delete "Solo" (last remaining department)
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue("Should return true when deleting last remaining department", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", company.getDepartments().isEmpty());
    }

    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue("Should return true when deleting department with manager but no offices", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", company.getEmployees().contains(e201Employee));
    }
}