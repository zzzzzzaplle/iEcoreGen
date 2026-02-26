import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private Department engineeringDept;
    private Department marketingDept;
    private Department hrDept;
    private Office officeA;
    private Office officeB;
    private Office hrMain;
    
    @Before
    public void setUp() {
        company = new Company();
        engineeringDept = new Department();
        marketingDept = new Department();
        hrDept = new Department();
        officeA = new Office();
        officeB = new Office();
        hrMain = new Office();
        
        // Set names/locations for identification
        officeA.setLocation("OfficeA");
        officeB.setLocation("OfficeB");
        hrMain.setLocation("HR-Main");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering" and add Office "OfficeA" to Engineering
        company.getDepartments().add(engineeringDept);
        engineeringDept.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineeringDept, officeA);
        
        // Expected Output: true
        assertTrue("Should return true when assigning valid headquarter", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("Engineering's headquarter should be OfficeA", officeA, engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", add Office "OfficeA" to Marketing
        company.getDepartments().add(engineeringDept);
        company.getDepartments().add(marketingDept);
        marketingDept.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineeringDept, officeA);
        
        // Expected Output: false
        assertFalse("Should return false when office doesn't belong to department", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", add Offices "OfficeA" and "OfficeB" to Engineering
        company.getDepartments().add(engineeringDept);
        engineeringDept.getOffices().add(officeA);
        engineeringDept.getOffices().add(officeB);
        
        // Set "OfficeA" as headquarter
        company.assignHeadquarters(engineeringDept, officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = company.assignHeadquarters(engineeringDept, officeB);
        
        // Expected Output: false
        assertFalse("Should return false when reassigning existing headquarter", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Engineering's headquarter should remain OfficeA", officeA, engineeringDept.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", add only Office "HR-Main" to HR
        company.getDepartments().add(hrDept);
        hrDept.getOffices().add(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = company.assignHeadquarters(hrDept, hrMain);
        
        // Expected Output: true
        assertTrue("Should return true when assigning headquarter to department with single office", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR's headquarter should be HR-Main", hrMain, hrDept.getHeadquarters());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, create Department "ghostDept"
        Department ghostDept = new Department();
        Office officeX = new Office();
        officeX.setLocation("OfficeX");
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = company.assignHeadquarters(ghostDept, officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse("Should return false when assigning to non-existent department", result);
    }
}