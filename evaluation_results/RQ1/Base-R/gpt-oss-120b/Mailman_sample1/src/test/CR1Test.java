import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private MailService mailService;
    
    @Before
    public void setUp() {
        mailService = new MailService();
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        GeographicalArea northDistrict = new GeographicalArea("ND001", "NorthDistrict");
        mailService.addArea(northDistrict);
        
        // SetUp: Add Mailman "John" to NorthDistrict
        Mailman john = new Mailman("M001", "John", null);
        mailService.addMailman("ND001", john);
        
        // SetUp: Add Inhabitant "Alice" to NorthDistrict
        Inhabitant alice = new Inhabitant("I001", "Alice", null);
        mailService.addInhabitant("ND001", alice);
        
        // SetUp: Create Registered Letter "Letter1" for Alice
        Letter letter1 = new Letter("L001", alice, "Test Subject", "Test Content");
        mailService.registerMailItem(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = mailService.assignMailItemToMailman("L001", "M001");
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        GeographicalArea eastDistrict = new GeographicalArea("ED001", "EastDistrict");
        GeographicalArea westDistrict = new GeographicalArea("WD001", "WestDistrict");
        mailService.addArea(eastDistrict);
        mailService.addArea(westDistrict);
        
        // SetUp: Add Mailman "Mike" to EastDistrict
        Mailman mike = new Mailman("M002", "Mike", null);
        mailService.addMailman("ED001", mike);
        
        // SetUp: Add Inhabitant "Bob" to WestDistrict
        Inhabitant bob = new Inhabitant("I002", "Bob", null);
        mailService.addInhabitant("WD001", bob);
        
        // SetUp: Create RegisteredParcel "Parcel1" for Bob
        Parcel parcel1 = new Parcel("P001", bob, 2.5, "Test Package");
        mailService.registerMailItem(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = mailService.assignMailItemToMailman("P001", "M002");
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Assignment should fail when mailman is in different area", result);
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        GeographicalArea centralDistrict = new GeographicalArea("CD001", "CentralDistrict");
        mailService.addArea(centralDistrict);
        
        // SetUp: Create Mailman "Peter" (but don't add to any area)
        // Mailman peter = new Mailman("M003", "Peter", null);
        // Intentionally not added to any area
        
        // SetUp: Add Inhabitant "Carol" to CentralDistrict
        Inhabitant carol = new Inhabitant("I003", "Carol", null);
        mailService.addInhabitant("CD001", carol);
        
        // SetUp: Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = new Letter("L002", carol, "Test Subject", "Test Content");
        mailService.registerMailItem(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = mailService.assignMailItemToMailman("L002", "M003");
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman doesn't exist in area", result);
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict"
        GeographicalArea southDistrict = new GeographicalArea("SD001", "SouthDistrict");
        mailService.addArea(southDistrict);
        
        // SetUp: Create Inhabitant "Dave" (but don't add to area)
        Inhabitant dave = new Inhabitant("I004", "Dave", null);
        // Intentionally not added to area
        
        // SetUp: Add Mailman "Sarah" to SouthDistrict
        Mailman sarah = new Mailman("M004", "Sarah", null);
        mailService.addMailman("SD001", sarah);
        
        // SetUp: Create RegisteredParcel "Parcel2" for non-existent "Dave"
        Parcel parcel2 = new Parcel("P002", dave, 1.5, "Test Package");
        // The parcel is created with Dave who is not in any area
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = mailService.assignMailItemToMailman("P002", "M004");
        
        // Expected Output: false (addressee doesn't exist in service)
        assertFalse("Assignment should fail when addressee doesn't exist in service", result);
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = new GeographicalArea("DT001", "Downtown");
        mailService.addArea(downtown);
        
        // SetUp: Add Mailman "Tom" and "Jerry" to Downtown
        Mailman tom = new Mailman("M005", "Tom", null);
        Mailman jerry = new Mailman("M006", "Jerry", null);
        mailService.addMailman("DT001", tom);
        mailService.addMailman("DT001", jerry);
        
        // SetUp: Add Inhabitant "Eve" to Downtown
        Inhabitant eve = new Inhabitant("I005", "Eve", null);
        mailService.addInhabitant("DT001", eve);
        
        // SetUp: Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        Letter letter3 = new Letter("L003", eve, "Test Subject", "Test Content");
        mailService.registerMailItem(letter3);
        
        // First assignment (should succeed)
        boolean firstAssignment = mailService.assignMailItemToMailman("L003", "M005");
        assertTrue("First assignment should succeed", firstAssignment);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean reassignmentResult = mailService.assignMailItemToMailman("L003", "M006");
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Reassignment should fail when mail is already assigned", reassignmentResult);
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // SetUp: Create GeographicalArea "Midtown"
        GeographicalArea midtown = new GeographicalArea("MT001", "Midtown");
        mailService.addArea(midtown);
        
        // SetUp: Add Inhabitant "Ieril" to Midtown
        Inhabitant ieril = new Inhabitant("I006", "Ieril", null);
        mailService.addInhabitant("MT001", ieril);
        
        // SetUp: Create RegisteredLetter "Letter4" for Ieril
        Letter letter4 = new Letter("L004", ieril, "Test Subject", "Test Content");
        
        // Expected Output 2: false (mail item not registered yet)
        boolean registerResult = mailService.registerMailItem(letter4);
        assertTrue("Registration should succeed", registerResult);
        
        // Additional test: Try to assign non-existent mailman to Letter4
        boolean assignmentResult2 = mailService.assignMailItemToMailman("L004", "NON_EXISTENT");
        assertFalse("Assignment should fail with non-existent mailman", assignmentResult2);
    }
}