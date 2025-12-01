import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        company.getDepartments().add(legalDept);
        
        // Add Offices "L1", "L2" to Legal
        Office officeL1 = new Office();
        Office officeL2 = new Office();
        legalDept.getOffices().add(officeL1);
        legalDept.getOffices().add(officeL2);
        
        // Set "L1" as headquarter
        legalDept.assignHeadquarter(officeL1);
        
        // Assign "E101" as manager to Legal
        Employee employeeE101 = new Employee();
        company.getEmployees().add(employeeE101);
        legalDept.setManager(employeeE101);
        legalDept.getEmployees().add(employeeE101);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse(company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse(company.getEmployees().contains(employeeE101));
        
        // Verification: Offices "L1", "L2" removed (implicit through department removal)
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        // Company is already empty from setUp()
        
        // Create department "Phantom"
        Department phantomDept = new Department();
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDept);
        
        // Expected Output: false
        assertFalse(result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        Department emptyDept = new Department();
        company.getDepartments().add(emptyDept);
        
        // Add Office "O1" to EmptyDept
        Office officeO1 = new Office();
        emptyDept.getOffices().add(officeO1);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse(company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        Department soloDept = new Department();
        company.getDepartments().add(soloDept);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's departments is empty
        assertTrue(company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        Department remoteDept = new Department();
        company.getDepartments().add(remoteDept);
        
        // Add Employee "E201" to Remote
        Employee employeeE201 = new Employee();
        company.getEmployees().add(employeeE201);
        remoteDept.getEmployees().add(employeeE201);
        
        // Assign "E201" as manager
        remoteDept.setManager(employeeE201);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse(company.getEmployees().contains(employeeE201));
    }
}