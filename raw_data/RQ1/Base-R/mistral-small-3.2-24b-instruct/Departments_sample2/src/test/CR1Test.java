import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        engineering.setName("Engineering");
        
        marketing = new Department();
        marketing.setName("Marketing");
        
        hr = new Department();
        hr.setName("HR");
        
        ghostDept = new Department();
        ghostDept.setName("ghostDept");
        
        officeA = new Office();
        officeA.setLocation("OfficeA");
        
        officeB = new Office();
        officeB.setLocation("OfficeB");
        
        hrMain = new Office();
        hrMain.setLocation("HR-Main");
        
        officeX = new Office();
        officeX.setLocation("OfficeX");
    }

    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering" and add Office "OfficeA" to Engineering
        company.addDepartment(engineering);
        engineering.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignHeadquarters(officeA);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("OfficeA should be the headquarter", officeA, engineering.getHeadquarters());
    }

    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", add Office "OfficeA" to Marketing
        company.addDepartment(engineering);
        company.addDepartment(marketing);
        marketing.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignHeadquarters(officeA);
        
        // Expected Output: false
        assertFalse("Should return false when office doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineering.getHeadquarters());
    }

    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", add Offices "OfficeA" and "OfficeB" to Engineering, set "OfficeA" as headquarter
        company.addDepartment(engineering);
        engineering.getOffices().add(officeA);
        engineering.getOffices().add(officeB);
        engineering.assignHeadquarters(officeA); // First assignment
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineering.assignHeadquarters(officeB);
        
        // Expected Output: false
        assertFalse("Should return false when department already has headquarter", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Headquarter should remain OfficeA", officeA, engineering.getHeadquarters());
    }

    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", add only Office "HR-Main" to HR
        company.addDepartment(hr);
        hr.getOffices().add(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hr.assignHeadquarters(hrMain);
        
        // Expected Output: true
        assertTrue("Should return true when assigning headquarter to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR-Main should be the headquarter", hrMain, hr.getHeadquarters());
    }

    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, create Department "ghostDept" (but not added to company)
        // OfficeX is not added to any department
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = ghostDept.assignHeadquarters(officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse("Should return false when department doesn't exist in company", result);
        
        // Verification: ghostDept should not have a headquarter
        assertNull("ghostDept should not have a headquarter", ghostDept.getHeadquarters());
    }
}