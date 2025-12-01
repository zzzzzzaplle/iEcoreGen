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
        // Create Department "Legal"
        Department legalDept = new Department();
        
        // Create Offices "L1", "L2"
        Office l1 = new Office();
        Office l2 = new Office();
        
        // Add offices to Legal department
        legalDept.getOffices().add(l1);
        legalDept.getOffices().add(l2);
        
        // Set "L1" as headquarter
        legalDept.setHeadquarter(l1);
        
        // Create Employee "E101"
        Employee e101 = new Employee();
        
        // Assign "E101" as manager to Legal
        legalDept.setManager(e101);
        
        // Add department and employee to company
        company.addDepartment(legalDept);
        company.addEmployee(e101);
        
        // Delete "Legal" department
        boolean result = company.deleteDepartment(legalDept);
        
        // Verify expected output
        assertTrue(result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse(company.getDepartments().contains(legalDept));
        
        // Verification: Company's employees excludes "E101"
        assertFalse(company.getEmployees().contains(e101));
        
        // Note: Offices are internal to department and not directly tracked by company,
        // so verification of office removal is handled by the department deletion
    }
    
    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // Create empty Company (handled by @Before)
        
        // Create department "Phantom"
        Department phantomDept = new Department();
        
        // Delete "Phantom" department (not added to company)
        boolean result = company.deleteDepartment(phantomDept);
        
        // Verify expected output
        assertFalse(result);
    }
    
    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // Create Department "EmptyDept"
        Department emptyDept = new Department();
        
        // Create Office "O1"
        Office o1 = new Office();
        
        // Add office to EmptyDept
        emptyDept.getOffices().add(o1);
        
        // Add department to company
        company.addDepartment(emptyDept);
        
        // Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Verify expected output
        assertTrue(result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse(company.getDepartments().contains(emptyDept));
    }
    
    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // Create Department "Solo"
        Department soloDept = new Department();
        
        // Add department to company
        company.addDepartment(soloDept);
        
        // Verify initial state
        assertEquals(1, company.getDepartments().size());
        assertTrue(company.getDepartments().contains(soloDept));
        
        // Delete "Solo"
        boolean result = company.deleteDepartment(soloDept);
        
        // Verify expected output
        assertTrue(result);
        
        // Verification: Company's departments is empty
        assertTrue(company.getDepartments().isEmpty());
    }
    
    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // Create Department "Remote"
        Department remoteDept = new Department();
        
        // Create Employee "E201"
        Employee e201 = new Employee();
        
        // Assign "E201" as manager
        remoteDept.setManager(e201);
        
        // Add department and employee to company
        company.addDepartment(remoteDept);
        company.addEmployee(e201);
        
        // Delete "Remote"
        boolean result = company.deleteDepartment(remoteDept);
        
        // Verify expected output
        assertTrue(result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse(company.getEmployees().contains(e201));
        
        // Verification: Department is removed
        assertFalse(company.getDepartments().contains(remoteDept));
    }
}