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
    private Office hrMain;
    private Office officeX;
    
    @Before
    public void setUp() {
        company = new Company();
        engineeringDept = new Department();
        marketingDept = new Department();
        hrDept = new Department();
        officeA = new Office();
        officeB = new Office();
        hrMain = new Office();
        officeX = new Office();
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering" and add Office "OfficeA" to Engineering
        company.addDepartment(engineeringDept);
        engineeringDept.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid headquarter to department without existing headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("OfficeA should be set as headquarter", officeA, engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", add Office "OfficeA" to Marketing
        company.addDepartment(engineeringDept);
        company.addDepartment(marketingDept);
        marketingDept.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: false
        assertFalse("Should return false when assigning office that doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", add Offices "OfficeA" and "OfficeB" to Engineering, set "OfficeA" as headquarter
        company.addDepartment(engineeringDept);
        engineeringDept.addOffice(officeA);
        engineeringDept.addOffice(officeB);
        engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeB);
        
        // Expected Output: false
        assertFalse("Should return false when reassigning headquarter to department with existing headquarter", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Headquarter should remain OfficeA", officeA, engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", add only Office "HR-Main" to HR
        company.addDepartment(hrDept);
        hrDept.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hrDept.assignOfficeAsHeadquarter(hrMain);
        
        // Expected Output: true
        assertTrue("Should return true when assigning headquarter to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR-Main should be set as headquarter", hrMain, hrDept.getHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, create Department "ghostDept"
        // Note: ghostDept is not added to company, so it's essentially non-existent in company context
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        // Since ghostDept is not part of any company, we test directly on the department
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeX);
        
        // Expected Output: false (because the office doesn't exist in the department)
        assertFalse("Should return false when assigning office that doesn't exist in department", result);
        
        // Additional verification: engineeringDept should not have headquarter
        assertNull("Engineering department should not have headquarter", engineeringDept.getHeadquarter());
    }
}