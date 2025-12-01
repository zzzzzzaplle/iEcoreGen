import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private Department engineeringDept;
    private Department marketingDept;
    private Department hrDept;
    private Office officeA;
    private Office officeB;
    private Office officeX;
    private Office hrMain;
    
    @Before
    public void setUp() {
        company = new Company();
        engineeringDept = new Department();
        marketingDept = new Department();
        hrDept = new Department();
        officeA = new Office("OfficeA");
        officeB = new Office("OfficeB");
        officeX = new Office("OfficeX");
        hrMain = new Office("HR-Main");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineeringDept);
        
        // SetUp: Add Office "OfficeA" to Engineering
        engineeringDept.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("OfficeA should be set as headquarter", officeA, engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing"
        company.addDepartment(engineeringDept);
        company.addDepartment(marketingDept);
        
        // SetUp: Add Office "OfficeA" to Marketing
        marketingDept.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: false
        assertFalse("Should return false when office doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineeringDept);
        
        // SetUp: Add Offices "OfficeA" and "OfficeB" to Engineering
        engineeringDept.addOffice(officeA);
        engineeringDept.addOffice(officeB);
        
        // SetUp: Set "OfficeA" as headquarter
        engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeB);
        
        // Expected Output: false
        assertFalse("Should return false when reassigning existing headquarter", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Headquarter should remain OfficeA", officeA, engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR"
        company.addDepartment(hrDept);
        
        // SetUp: Add only Office "HR-Main" to HR
        hrDept.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hrDept.assignOfficeAsHeadquarter(hrMain);
        
        // Expected Output: true
        assertTrue("Should return true when assigning to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR-Main should be set as headquarter", hrMain, hrDept.getHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments
        // (company is created empty in setUp)
        
        // SetUp: Create Department "ghostDept"
        Department ghostDept = new Department();
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        // Note: ghostDept is not added to company, but the test is on the department itself
        boolean result = ghostDept.assignOfficeAsHeadquarter(officeX);
        
        // Expected Output: false (because the office doesn't exist in the department)
        assertFalse("Should return false when office doesn't exist in department", result);
        
        // Verification: ghostDept should not have headquarter
        assertNull("ghostDept should not have a headquarter", ghostDept.getHeadquarter());
    }
}