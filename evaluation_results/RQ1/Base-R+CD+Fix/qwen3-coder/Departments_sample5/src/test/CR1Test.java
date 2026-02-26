import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private Department engineering;
    private Department marketing;
    private Department hr;
    private Department ghostDept;
    private Office officeA;
    private Office officeB;
    private Office hrMain;
    private Office officeX;
    
    @Before
    public void setUp() {
        company = new Company();
        engineering = new Department();
        marketing = new Department();
        hr = new Department();
        ghostDept = new Department();
        officeA = new Office();
        officeB = new Office();
        hrMain = new Office();
        officeX = new Office();
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering" and add Office "OfficeA" to Engineering
        company.addDepartment(engineering);
        engineering.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: true
        assertTrue("OfficeA should be successfully assigned as headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("Engineering's headquarter should be OfficeA", officeA, engineering.getHeadquarter());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", add Office "OfficeA" to Marketing
        company.addDepartment(engineering);
        company.addDepartment(marketing);
        marketing.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: false
        assertFalse("OfficeA should not be assigned as headquarter since it doesn't belong to Engineering", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineering.getHeadquarter());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", add Offices "OfficeA" and "OfficeB" to Engineering, set "OfficeA" as headquarter
        company.addDepartment(engineering);
        engineering.getOffices().add(officeA);
        engineering.getOffices().add(officeB);
        engineering.assignOfficeAsHeadquarter(officeA); // First assignment
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineering.assignOfficeAsHeadquarter(officeB);
        
        // Expected Output: false
        assertFalse("Should not be able to reassign headquarter when one already exists", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Engineering's headquarter should remain OfficeA", officeA, engineering.getHeadquarter());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", add only Office "HR-Main" to HR
        company.addDepartment(hr);
        hr.getOffices().add(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hr.assignOfficeAsHeadquarter(hrMain);
        
        // Expected Output: true
        assertTrue("HR-Main should be successfully assigned as headquarter", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR's headquarter should be HR-Main", hrMain, hr.getHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // Note: The test case description mentions setting headquarter for "ghostDept" which doesn't belong to the company,
        // but the assignOfficeAsHeadquarter method is called on the department object itself, not through company.
        // Since ghostDept is not added to company, but we're calling the method directly on the department,
        // we need to test the department's behavior when the office doesn't belong to it.
        
        // SetUp: Create Company with no departments, create Department "ghostDept"
        // ghostDept is not added to company and has no offices
        // officeX is not added to ghostDept
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = ghostDept.assignOfficeAsHeadquarter(officeX);
        
        // Expected Output: false (because the office doesn't exist in the department)
        assertFalse("Should not assign headquarter since officeX doesn't belong to ghostDept", result);
        
        // Verification: ghostDept should not have a headquarter
        assertNull("ghostDept should not have a headquarter", ghostDept.getHeadquarter());
    }
}