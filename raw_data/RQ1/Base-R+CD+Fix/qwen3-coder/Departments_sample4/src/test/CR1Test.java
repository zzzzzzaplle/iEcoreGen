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
        engineeringDept.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: true
        assertTrue("Valid headquarter assignment should return true", result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals("OfficeA should be set as headquarter", officeA, engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", add Office "OfficeA" to Marketing
        company.addDepartment(engineeringDept);
        company.addDepartment(marketingDept);
        marketingDept.getOffices().add(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Expected Output: false
        assertFalse("Assigning office not in department should return false", result);
        
        // Verification: Engineering do not have headquarter
        assertNull("Engineering should not have a headquarter", engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", add Offices "OfficeA" and "OfficeB" to Engineering, set "OfficeA" as headquarter
        company.addDepartment(engineeringDept);
        engineeringDept.getOffices().add(officeA);
        engineeringDept.getOffices().add(officeB);
        engineeringDept.assignOfficeAsHeadquarter(officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = engineeringDept.assignOfficeAsHeadquarter(officeB);
        
        // Expected Output: false
        assertFalse("Reassigning headquarter should return false", result);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals("Headquarter should remain OfficeA", officeA, engineeringDept.getHeadquarter());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", add only Office "HR-Main" to HR
        company.addDepartment(hrDept);
        hrDept.getOffices().add(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hrDept.assignOfficeAsHeadquarter(hrMain);
        
        // Expected Output: true
        assertTrue("Assigning headquarter to department with single office should return true", result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals("HR-Main should be set as headquarter", hrMain, hrDept.getHeadquarter());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, create Department "ghostDept"
        Department ghostDept = new Department();
        // Note: ghostDept is not added to company
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = ghostDept.assignOfficeAsHeadquarter(officeX);
        
        // Expected Output: false (because the office doesn't exist in the department)
        assertFalse("Assigning headquarter with office not in department should return false", result);
        
        // Verification: ghostDept should not have headquarter
        assertNull("ghostDept should not have a headquarter", ghostDept.getHeadquarter());
    }
}