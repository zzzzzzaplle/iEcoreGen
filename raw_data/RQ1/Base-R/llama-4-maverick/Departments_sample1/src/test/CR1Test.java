import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        // SetUp: Create Company with Department "Engineering", Add Office "OfficeA" to Engineering
        List<Office> engineeringOffices = new ArrayList<>();
        engineeringOffices.add(officeA);
        engineering.setOffices(engineeringOffices);
        
        List<Department> departments = new ArrayList<>();
        departments.add(engineering);
        company.setDepartments(departments);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignHeadquarters(officeA);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: OfficeA is the headquarter of Engineering
        assertEquals(officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase2_AssignOfficeNotBelongingToDepartment() {
        // SetUp: Create Company with Departments "Engineering" and "Marketing", Add Office "OfficeA" to Marketing
        List<Office> marketingOffices = new ArrayList<>();
        marketingOffices.add(officeA);
        marketing.setOffices(marketingOffices);
        
        List<Department> departments = new ArrayList<>();
        departments.add(engineering);
        departments.add(marketing);
        company.setDepartments(departments);
        
        // Action: Set "OfficeA" as headquarter for "Engineering"
        boolean result = engineering.assignHeadquarters(officeA);
        
        // Expected Output: false
        assertFalse(result);
        
        // Verification: Engineering do not have headquarter
        assertNull(engineering.getHeadquarters());
    }
    
    @Test
    public void testCase3_ReassignExistingHeadquarter() {
        // SetUp: Create Company with Department "Engineering", Add Offices "OfficeA" and "OfficeB" to Engineering, Set "OfficeA" as headquarter
        List<Office> engineeringOffices = new ArrayList<>();
        engineeringOffices.add(officeA);
        engineeringOffices.add(officeB);
        engineering.setOffices(engineeringOffices);
        
        List<Department> departments = new ArrayList<>();
        departments.add(engineering);
        company.setDepartments(departments);
        
        // First assignment should succeed
        boolean firstResult = engineering.assignHeadquarters(officeA);
        assertTrue(firstResult);
        
        // Action: Set "OfficeB" as new headquarter
        boolean secondResult = engineering.assignHeadquarters(officeB);
        
        // Expected Output: false
        assertFalse(secondResult);
        
        // Verification: Engineering's headquarter remains OfficeA
        assertEquals(officeA, engineering.getHeadquarters());
    }
    
    @Test
    public void testCase4_AssignToDepartmentWithSingleOffice() {
        // SetUp: Create Company with Department "HR", Add only Office "HR-Main" to HR
        List<Office> hrOffices = new ArrayList<>();
        hrOffices.add(hrMain);
        hr.setOffices(hrOffices);
        
        List<Department> departments = new ArrayList<>();
        departments.add(hr);
        company.setDepartments(departments);
        
        // Action: Set "HR-Main" as headquarter
        boolean result = hr.assignHeadquarters(hrMain);
        
        // Expected Output: true
        assertTrue(result);
        
        // Verification: HR's headquarter is HR-Main
        assertEquals(hrMain, hr.getHeadquarters());
    }
    
    @Test
    public void testCase5_AssignToNonExistentDepartment() {
        // SetUp: Create Company with no departments, Create Department "ghostDept"
        company.setDepartments(new ArrayList<>());
        
        // Action: Set "OfficeX" as headquarter for "ghostDept"
        boolean result = ghostDept.assignHeadquarters(officeX);
        
        // Expected Output: false (because the department "ghostDept" does not belong to the Company)
        assertFalse(result);
        
        // Additional verification: ghostDept should not have headquarters set
        assertNull(ghostDept.getHeadquarters());
    }
}