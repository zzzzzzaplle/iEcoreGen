import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        
        // Initialize departments
        legalDepartment = new Department();
        legalDepartment.setName("Legal");
        
        phantomDepartment = new Department();
        phantomDepartment.setName("Phantom");
        
        emptyDept = new Department();
        emptyDept.setName("EmptyDept");
        
        soloDepartment = new Department();
        soloDepartment.setName("Solo");
        
        remoteDepartment = new Department();
        remoteDepartment.setName("Remote");
        
        // Initialize offices
        l1Office = new Office();
        l1Office.setLocation("L1");
        l1Office.setAddress("123 Legal St");
        
        l2Office = new Office();
        l2Office.setLocation("L2");
        l2Office.setAddress("456 Legal Ave");
        
        o1Office = new Office();
        o1Office.setLocation("O1");
        o1Office.setAddress("789 Empty St");
        
        // Initialize employees
        e101Employee = new Employee();
        e101Employee.setName("John Doe");
        e101Employee.setEmployeeId("E101");
        e101Employee.setPosition("Legal Manager");
        
        e201Employee = new Employee();
        e201Employee.setName("Jane Smith");
        e201Employee.setEmployeeId("E201");
        e201Employee.setPosition("Remote Manager");
    }

    @Test
    public void testCase1_DeleteExistingDepartmentWithOfficesAndEmployees() {
        // SetUp: Create Company with Department "Legal"
        List<Department> departments = new ArrayList<>();
        departments.add(legalDepartment);
        company.setDepartments(departments);
        
        // Add Offices "L1", "L2" to Legal
        legalDepartment.addOffice(l1Office);
        legalDepartment.addOffice(l2Office);
        
        // Set "L1" as headquarter
        legalDepartment.setHeadquarters(l1Office);
        
        // Assign "E101" as manager to Legal
        List<Employee> employees = new ArrayList<>();
        employees.add(e101Employee);
        company.setEmployees(employees);
        legalDepartment.setManager(e101Employee);
        
        // Action: Delete "Legal" department
        boolean result = company.deleteDepartment(legalDepartment);
        
        // Expected Output: true
        assertTrue("Delete should be successful", result);
        
        // Verification: Company's departments excludes "Legal"
        assertFalse("Legal department should be removed from company", 
                   company.getDepartments().contains(legalDepartment));
        
        // Verification: Company's employees excludes "E101"
        assertFalse("Employee E101 should be removed from company", 
                   company.getEmployees().contains(e101Employee));
        
        // Verification: Offices "L1", "L2" removed
        assertTrue("Legal department offices should be cleared", 
                  legalDepartment.getOffices().isEmpty());
        assertNull("Headquarters should be cleared", legalDepartment.getHeadquarters());
        assertNull("Manager should be cleared", legalDepartment.getManager());
    }

    @Test
    public void testCase2_DeleteNonExistentDepartment() {
        // SetUp: Create empty Company
        company.setDepartments(new ArrayList<>());
        company.setEmployees(new ArrayList<>());
        
        // Create department "Phantom" (but not added to company)
        
        // Action: Delete "Phantom" department
        boolean result = company.deleteDepartment(phantomDepartment);
        
        // Expected Output: false
        assertFalse("Delete should fail for non-existent department", result);
    }

    @Test
    public void testCase3_DeleteDepartmentWithOnlyOffices() {
        // SetUp: Create Company with Department "EmptyDept"
        List<Department> departments = new ArrayList<>();
        departments.add(emptyDept);
        company.setDepartments(departments);
        
        // Add Office "O1" to EmptyDept
        emptyDept.addOffice(o1Office);
        
        // Action: Delete "EmptyDept"
        boolean result = company.deleteDepartment(emptyDept);
        
        // Expected Output: true
        assertTrue("Delete should be successful", result);
        
        // Verification: Company's departments excludes "EmptyDept"
        assertFalse("EmptyDept should be removed from company", 
                   company.getDepartments().contains(emptyDept));
        
        // Additional verification: Offices should be cleared
        assertTrue("EmptyDept offices should be cleared", 
                  emptyDept.getOffices().isEmpty());
    }

    @Test
    public void testCase4_DeleteLastRemainingDepartment() {
        // SetUp: Create Company with single Department "Solo"
        List<Department> departments = new ArrayList<>();
        departments.add(soloDepartment);
        company.setDepartments(departments);
        
        // Action: Delete "Solo"
        boolean result = company.deleteDepartment(soloDepartment);
        
        // Expected Output: true
        assertTrue("Delete should be successful", result);
        
        // Verification: Company's departments is empty
        assertTrue("Company departments should be empty after deleting last department", 
                  company.getDepartments().isEmpty());
    }

    @Test
    public void testCase5_DeleteDepartmentWithManagerButNoOffices() {
        // SetUp: Create Company with Department "Remote"
        List<Department> departments = new ArrayList<>();
        departments.add(remoteDepartment);
        company.setDepartments(departments);
        
        // Add Employee "E201" to Remote
        List<Employee> employees = new ArrayList<>();
        employees.add(e201Employee);
        company.setEmployees(employees);
        
        // Assign "E201" as manager
        remoteDepartment.setManager(e201Employee);
        
        // Action: Delete "Remote"
        boolean result = company.deleteDepartment(remoteDepartment);
        
        // Expected Output: true
        assertTrue("Delete should be successful", result);
        
        // Verification: Company's employees excludes "E201"
        assertFalse("Employee E201 should be removed from company", 
                   company.getEmployees().contains(e201Employee));
        
        // Additional verification: Department should be removed and manager cleared
        assertFalse("Remote department should be removed from company", 
                   company.getDepartments().contains(remoteDepartment));
        assertNull("Manager should be cleared", remoteDepartment.getManager());
    }
}