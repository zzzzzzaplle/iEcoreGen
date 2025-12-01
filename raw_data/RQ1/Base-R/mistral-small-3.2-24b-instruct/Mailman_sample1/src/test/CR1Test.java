import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private GeographicalArea northDistrict;
    private GeographicalArea eastDistrict;
    private GeographicalArea westDistrict;
    private GeographicalArea centralDistrict;
    private GeographicalArea southDistrict;
    private GeographicalArea downtown;
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
    private Letter letter1;
    private Parcel parcel1;
    private Letter letter2;
    private Parcel parcel2;
    private Letter letter3;
    private MailDeliverySystem mailSystem;

    @Before
    public void setUp() {
        mailSystem = new MailDeliverySystem();
        
        // Create geographical areas
        northDistrict = new GeographicalArea("NorthDistrict");
        eastDistrict = new GeographicalArea("EastDistrict");
        westDistrict = new GeographicalArea("WestDistrict");
        centralDistrict = new GeographicalArea("CentralDistrict");
        southDistrict = new GeographicalArea("SouthDistrict");
        downtown = new GeographicalArea("Downtown");
        
        // Create mailmen
        john = new Mailman("John", northDistrict);
        mike = new Mailman("Mike", eastDistrict);
        peter = new Mailman("Peter", null); // Not assigned to any area initially
        sarah = new Mailman("Sarah", southDistrict);
        tom = new Mailman("Tom", downtown);
        jerry = new Mailman("Jerry", downtown);
        
        // Create inhabitants
        alice = new Inhabitant("Alice", northDistrict);
        bob = new Inhabitant("Bob", westDistrict);
        carol = new Inhabitant("Carol", centralDistrict);
        dave = new Inhabitant("Dave", null); // Not assigned to any area initially
        eve = new Inhabitant("Eve", downtown);
        
        // Create registered mails
        letter1 = new Letter(alice, null, northDistrict);
        parcel1 = new Parcel(bob, null, westDistrict);
        letter2 = new Letter(carol, null, centralDistrict);
        parcel2 = new Parcel(dave, null, southDistrict);
        letter3 = new Letter(eve, tom, downtown);
        
        // Add mailmen to their areas
        northDistrict.getMailmen().add(john);
        eastDistrict.getMailmen().add(mike);
        southDistrict.getMailmen().add(sarah);
        downtown.getMailmen().add(tom);
        downtown.getMailmen().add(jerry);
        
        // Add inhabitants to their areas
        northDistrict.getInhabitants().add(alice);
        westDistrict.getInhabitants().add(bob);
        centralDistrict.getInhabitants().add(carol);
        southDistrict.getInhabitants().add(dave);
        downtown.getInhabitants().add(eve);
        
        // Add registered mails to their areas
        northDistrict.getRegisteredMails().add(letter1);
        westDistrict.getRegisteredMails().add(parcel1);
        centralDistrict.getRegisteredMails().add(letter2);
        southDistrict.getRegisteredMails().add(parcel2);
        downtown.getRegisteredMails().add(letter3);
    }

    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // Test Case 1: "Assign Mailman to Letter in Same Area"
        // Setup already done in @Before method
        boolean result = mailSystem.assignMail(letter1, john);
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("Mailman should be assigned to the letter", john, letter1.getMailman());
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // Test Case 2: "Assign Mailman to Parcel in Different Area"
        // Setup already done in @Before method
        boolean result = mailSystem.assignMail(parcel1, mike);
        assertFalse("Mailman should not be assigned to parcel in different area", result);
        assertNull("Mailman should not be assigned to the parcel", parcel1.getMailman());
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // Test Case 3: "Assign Non-existent Mailman"
        // Setup already done in @Before method
        boolean result = mailSystem.assignMail(letter2, peter);
        assertFalse("Mailman not assigned to any area should not be able to deliver mail", result);
        assertNull("Mailman should not be assigned to the letter", letter2.getMailman());
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // Test Case 4: "Assign Mailman to Mail for Non-existent Inhabitant"
        // Setup already done in @Before method
        // Note: Dave exists but is not properly added to the system via addInhabitant method
        // The test specification indicates this should fail
        boolean result = mailSystem.assignMail(parcel2, sarah);
        assertFalse("Mail for non-existent inhabitant should not be assignable", result);
        assertNull("Mailman should not be assigned to the parcel", parcel2.getMailman());
    }

    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // Test Case 5: "Reassign Mailman to Already Assigned Mail"
        // Setup already done in @Before method
        boolean result1 = mailSystem.assignMail(letter3, jerry);
        assertFalse("Already assigned mail should not be reassignable", result1);
        assertEquals("Original mailman should remain assigned", tom, letter3.getMailman());
        
        // Additional test as specified in the test case
        GeographicalArea midtown = new GeographicalArea("Midtown");
        Inhabitant ieril = new Inhabitant("Ieril", midtown);
        midtown.getInhabitants().add(ieril);
        Letter letter4 = new Letter(ieril, null, midtown);
        midtown.getRegisteredMails().add(letter4);
        
        boolean result2 = mailSystem.assignMail(letter4, jerry);
        assertFalse("Mailman from different area should not be assignable", result2);
    }
}