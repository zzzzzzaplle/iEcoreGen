import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private MailDeliverySystem system;
    private GeographicalArea northDistrict;
    private GeographicalArea eastDistrict;
    private GeographicalArea westDistrict;
    private GeographicalArea centralDistrict;
    private GeographicalArea southDistrict;
    private GeographicalArea downtown;
    private GeographicalArea midtown;
    
    private Mailman john;
    private Mailman mike;
    private Mailman peter;
    private Mailman sarah;
    private Mailman tom;
    private Mailman jerry;
    
    private Inhabitant alice;
    private Inhabitant bob;
    private Inhabitant carol;
    private Inhabitant dave;
    private Inhabitant eve;
    private Inhabitant ieril;
    
    @Before
    public void setUp() {
        system = new MailDeliverySystem();
        
        // Create geographical areas
        northDistrict = new GeographicalArea("ND001", "NorthDistrict");
        eastDistrict = new GeographicalArea("ED001", "EastDistrict");
        westDistrict = new GeographicalArea("WD001", "WestDistrict");
        centralDistrict = new GeographicalArea("CD001", "CentralDistrict");
        southDistrict = new GeographicalArea("SD001", "SouthDistrict");
        downtown = new GeographicalArea("DT001", "Downtown");
        midtown = new GeographicalArea("MT001", "Midtown");
        
        // Add areas to system
        system.addArea(northDistrict);
        system.addArea(eastDistrict);
        system.addArea(westDistrict);
        system.addArea(centralDistrict);
        system.addArea(southDistrict);
        system.addArea(downtown);
        system.addArea(midtown);
        
        // Create mailmen
        john = new Mailman("JM001", "John");
        mike = new Mailman("MK001", "Mike");
        peter = new Mailman("PT001", "Peter");
        sarah = new Mailman("SH001", "Sarah");
        tom = new Mailman("TM001", "Tom");
        jerry = new Mailman("JR001", "Jerry");
        
        // Create inhabitants
        alice = new Inhabitant("AL001", "Alice");
        bob = new Inhabitant("BB001", "Bob");
        carol = new Inhabitant("CR001", "Carol");
        dave = new Inhabitant("DV001", "Dave");
        eve = new Inhabitant("EV001", "Eve");
        ieril = new Inhabitant("IR001", "Ieril");
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        // Add Mailman "John" to NorthDistrict
        system.addMailman(northDistrict, john);
        // Add Inhabitant "Alice" to NorthDistrict
        system.addInhabitant(northDistrict, alice);
        // Create Registered Letter "Letter1" for Alice
        Letter letter1 = new Letter("LTR001", alice, "Test Subject", "Test Body");
        northDistrict.internalAddRegisteredMail(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = system.assignMailToMailman(letter1, john);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("Letter should be assigned to John", john, letter1.getAssignedMailman());
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        system.addMailman(eastDistrict, mike);
        // Add Inhabitant "Bob" to WestDistrict
        system.addInhabitant(westDistrict, bob);
        // Create RegisteredParcel "Parcel1" for Bob
        Parcel parcel1 = new Parcel("PRC001", bob, 2.5, "Test Package");
        westDistrict.internalAddRegisteredMail(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = system.assignMailToMailman(parcel1, mike);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman from different area should not be assigned", result);
        assertNull("Parcel should not have any assigned mailman", parcel1.getAssignedMailman());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Note: Peter is created but NOT added to CentralDistrict
        // Add Inhabitant "Carol" to CentralDistrict
        system.addInhabitant(centralDistrict, carol);
        // Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = new Letter("LTR002", carol, "Another Subject", "Another Body");
        centralDistrict.internalAddRegisteredMail(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = system.assignMailToMailman(letter2, peter);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman in area should not be assigned", result);
        assertNull("Letter should not have any assigned mailman", letter2.getAssignedMailman());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Note: Dave is created but NOT added to SouthDistrict
        // Add Mailman "Sarah" to SouthDistrict
        system.addMailman(southDistrict, sarah);
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        // Since Dave is not added to SouthDistrict, he doesn't exist in the area
        Parcel parcel2 = new Parcel("PRC002", dave, 1.8, "Small Package");
        southDistrict.internalAddRegisteredMail(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = system.assignMailToMailman(parcel2, sarah);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mail for non-existent inhabitant should not be assignable", result);
        assertNull("Parcel should not have any assigned mailman", parcel2.getAssignedMailman());
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        // Add Mailman "Tom" and "Jerry" to Downtown
        system.addMailman(downtown, tom);
        system.addMailman(downtown, jerry);
        // Add Inhabitant "Eve" to Downtown
        system.addInhabitant(downtown, eve);
        // Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        Letter letter3 = new Letter("LTR003", eve, "Important", "Urgent Message");
        downtown.internalAddRegisteredMail(letter3);
        system.assignMailToMailman(letter3, tom);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = system.assignMailToMailman(letter3, jerry);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Already assigned mail should not be reassigned", result1);
        assertEquals("Letter should remain assigned to Tom", tom, letter3.getAssignedMailman());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        system.addInhabitant(midtown, ieril);
        Letter letter4 = new Letter("LTR004", ieril, "Test", "Test Message");
        midtown.internalAddRegisteredMail(letter4);
        
        // Expected Output 2: false
        // Note: The test specification seems incomplete here. Since no mailman is assigned yet,
        // we'll test that assignment to a non-existent mailman fails
        boolean result2 = system.assignMailToMailman(letter4, new Mailman("NM001", "NewMailman"));
        assertFalse("Assignment to non-existent mailman should fail", result2);
    }
}