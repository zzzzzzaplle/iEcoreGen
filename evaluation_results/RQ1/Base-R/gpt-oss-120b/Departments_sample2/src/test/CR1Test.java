import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private Company company;
    private Department engineering;
    private Department marketing;
    private Department hr;
    private Office officeA;
    private Office officeB;
    private Office hrMain;
    private Office officeX;
    
    @Before
    public void setUp() {
        company = new Company();
        engineering = new Department();
        engineering.setId("1");
        engineering.setName("Engineering");
        
        marketing = new Department();
        marketing.setId("2");
        marketing.setName("Marketing");
        
        hr = new Department();
        hr.setId("3");
        hr.setName("HR");
        
        officeA = new Office();
        officeA.setId("A");
        officeA.setName("OfficeA");
        
        officeB = new Office();
        officeB.setId("B");
        officeB.setName("OfficeB");
        
        hrMain = new Office();
        hrMain.setId("HR1");
        hrMain.setName("HR-Main");
        
        officeX = new Office();
        officeX.setId("X");
        officeX.setName("OfficeX");
    }
    
    @Test
    public void testCase1_AssignValidHeadquarterToDepartmentWithoutExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineering);
        // SetUp: Add Office "OfficeA" to Engineering
        engineering.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineering, officeA);
        
        // Expected Output: true
        assertTrue(result);
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals(officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing"
        company.addDepartment(engineering);
        company.addDepartment(marketing);
        // SetUp: Add Office "OfficeA" to Marketing
        marketing.addOffice(officeA);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = company.assignHeadquarters(engineering, officeA);
        
        // Expected Output: false
        assertFalse(result);
        // Verification: Engineering do not have headquarter
        assertNull(engineering.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering"
        company.addDepartment(engineering);
        // SetUp: Add Offices "OfficeA" and "OfficeB" to Engineering
        engineering.addOffice(officeA);
        engineering.addOffice(officeB);
        // SetUp: Set "OfficeA" as headquarter
        company.assignHeadquarters(engineering, officeA);
        
        // Action: Set "OfficeB" as new headquarter
        boolean result = company.assignHeadquarters(engineering, officeB);
        
        // Expected Output: false
        assertFalse(result);
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals(officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR"
        company.addDepartment(hr);
        // SetUp: Add only Office "HR-Main" to HR
        hr.addOffice(hrMain);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = company.assignHeadquarters(hr, hrMain);
        
        // Expected Output: true
        assertTrue(result);
        // Verification: HR's headquarter is HR-Main
        assertEquals(hrMain, hr.getHeadquarters());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments
        // (No departments added to company)
        // SetUp: Create Department "ghostDept" (engineering object represents ghostDept)
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = company.assignHeadquarters(engineering, officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse(result);
    }
}