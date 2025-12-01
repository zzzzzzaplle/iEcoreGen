import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    private Company company;
    private Department engineering;
    private Department marketing;
    private Department hr;
    private Office officeA;
    private Office officeB;
    private Office hrMain;
    private Office officeX;
    private Department ghostDept;
    
    @Before
    public void setUp() {
        // Common setup that can be reused across test cases
        company = new Company();
        engineering = new Department("Engineering");
        marketing = new Department("Marketing");
        hr = new Department("HR");
        officeA = new Office("OfficeA");
        officeB = new Office("OfficeB");
        hrMain = new Office("HR-Main");
        officeX = new Office("OfficeX");
        ghostDept = new Department("ghostDept");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.getDepartments().add(engineering);
        
        // SetUp: Add Office "OfficeA" to Engineering
        engineering.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineering, officeA);
        
        // Expected Output: true
        assertTrue("Valid headquarter assignment should return true", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("Engineering's headquarter should be OfficeA", officeA, engineering.getHeadquarter());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing"
        company.getDepartments().add(engineering);
        company.getDepartments().add(marketing);
        
        // SetUp: Add Office "OfficeA" to Marketing
        marketing.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineering, officeA);
        
        // Expected Output: false
        assertFalse("Assigning office not in department should return false", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineering.getHeadquarter());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.getDepartments().add(engineering);
        
        // SetUp: Add Offices "OfficeA" and "OfficeB" to Engineering
        engineering.getOffices().add(officeA);
        engineering.getOffices().add(officeB);
        
        // SetUp: Set "OfficeA" as headquarter
        company.assignHeadquarters(engineering, officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = company.assignHeadquarters(engineering, officeB);
        
        // Expected Output: false
        assertFalse("Reassigning existing headquarter should return false", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Engineering's headquarter should remain OfficeA", officeA, engineering.getHeadquarter());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR"
        company.getDepartments().add(hr);
        
        // SetUp: Add only Office "HR-Main" to HR
        hr.getOffices().add(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = company.assignHeadquarters(hr, hrMain);
        
        // Expected Output: true
        assertTrue("Assigning headquarter to department with single office should return true", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR's headquarter should be HR-Main", hrMain, hr.getHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments
        // (company starts with empty departments list)
        
        // SetUp: Create Department "ghostDept"
        // (ghostDept is created but not added to company)
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = company.assignHeadquarters(ghostDept, officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse("Assigning headquarter to non-existent department should return false", result);
    }
}