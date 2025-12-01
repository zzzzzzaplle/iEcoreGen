import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    private MailDeliverySystem mailDeliverySystem;
    private GeographicalArea northDistrict;
    private GeographicalArea eastDistrict;
    private GeographicalArea westDistrict;
    private GeographicalArea centralDistrict;
    private GeographicalArea southDistrict;
    private GeographicalArea downtown;
    private GeographicalArea midtown;
    
    @Before
    public void setUp() {
        mailDeliverySystem = new MailDeliverySystem();
        
        // Create geographical areas
        northDistrict = new GeographicalArea();
        northDistrict.setName("NorthDistrict");
        
        eastDistrict = new GeographicalArea();
        eastDistrict.setName("EastDistrict");
        
        westDistrict = new GeographicalArea();
        westDistrict.setName("WestDistrict");
        
        centralDistrict = new GeographicalArea();
        centralDistrict.setName("CentralDistrict");
        
        southDistrict = new GeographicalArea();
        southDistrict.setName("SouthDistrict");
        
        downtown = new GeographicalArea();
        downtown.setName("Downtown");
        
        midtown = new GeographicalArea();
        midtown.setName("Midtown");
        
        // Add areas to mail delivery system
        mailDeliverySystem.addGeographicalArea(northDistrict);
        mailDeliverySystem.addGeographicalArea(eastDistrict);
        mailDeliverySystem.addGeographicalArea(westDistrict);
        mailDeliverySystem.addGeographicalArea(centralDistrict);
        mailDeliverySystem.addGeographicalArea(southDistrict);
        mailDeliverySystem.addGeographicalArea(downtown);
        mailDeliverySystem.addGeographicalArea(midtown);
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        // SetUp: Add Mailman "John" to NorthDistrict
        Mailman john = new Mailman();
        john.setName("John");
        mailDeliverySystem.addMailman(northDistrict, john);
        
        // SetUp: Add Inhabitant "Alice" to NorthDistrict
        Inhabitant alice = new Inhabitant();
        alice.setName("Alice");
        mailDeliverySystem.addInhabitant(northDistrict, alice);
        
        // SetUp: Create Registered Letter "Letter1" for Alice
        Letter letter1 = new Letter();
        letter1.setAddressee(alice);
        letter1.setContent("Test letter content");
        northDistrict.addRegisteredMail(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = mailDeliverySystem.assignMailman(letter1, john);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("John should be assigned as mailman", john, letter1.getMailman());
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // SetUp: Add Mailman "Mike" to EastDistrict
        Mailman mike = new Mailman();
        mike.setName("Mike");
        mailDeliverySystem.addMailman(eastDistrict, mike);
        
        // SetUp: Add Inhabitant "Bob" to WestDistrict
        Inhabitant bob = new Inhabitant();
        bob.setName("Bob");
        mailDeliverySystem.addInhabitant(westDistrict, bob);
        
        // SetUp: Create RegisteredParcel "Parcel1" for Bob
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(bob);
        parcel1.setWeight(2.5);
        westDistrict.addRegisteredMail(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = mailDeliverySystem.assignMailman(parcel1, mike);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman should not be assigned to parcel in different area", result);
        assertNull("Parcel should not have any mailman assigned", parcel1.getMailman());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        Mailman peter = new Mailman();
        peter.setName("Peter");
        // Note: Peter is created but NOT added to any geographical area
        
        // SetUp: Add Inhabitant "Carol" to CentralDistrict
        Inhabitant carol = new Inhabitant();
        carol.setName("Carol");
        mailDeliverySystem.addInhabitant(centralDistrict, carol);
        
        // SetUp: Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = new Letter();
        letter2.setAddressee(carol);
        letter2.setContent("Another test letter");
        centralDistrict.addRegisteredMail(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = mailDeliverySystem.assignMailman(letter2, peter);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman should not be assignable", result);
        assertNull("Letter should not have any mailman assigned", letter2.getMailman());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        Inhabitant dave = new Inhabitant();
        dave.setName("Dave");
        // Note: Dave is created but NOT added to any geographical area
        
        // SetUp: Add Mailman "Sarah" to SouthDistrict
        Mailman sarah = new Mailman();
        sarah.setName("Sarah");
        mailDeliverySystem.addMailman(southDistrict, sarah);
        
        // SetUp: Create RegisteredParcel "Parcel2" for non-existent "Dave"
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(dave); // Dave exists as object but not added to area
        parcel2.setWeight(1.8);
        southDistrict.addRegisteredMail(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = mailDeliverySystem.assignMailman(parcel2, sarah);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mailman should not be assignable to mail for non-existent inhabitant", result);
        assertNull("Parcel should not have any mailman assigned", parcel2.getMailman());
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        // SetUp: Add Mailman "Tom" and "Jerry" to Downtown
        Mailman tom = new Mailman();
        tom.setName("Tom");
        mailDeliverySystem.addMailman(downtown, tom);
        
        Mailman jerry = new Mailman();
        jerry.setName("Jerry");
        mailDeliverySystem.addMailman(downtown, jerry);
        
        // SetUp: Add Inhabitant "Eve" to Downtown
        Inhabitant eve = new Inhabitant();
        eve.setName("Eve");
        mailDeliverySystem.addInhabitant(downtown, eve);
        
        // SetUp: Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        Letter letter3 = new Letter();
        letter3.setAddressee(eve);
        letter3.setContent("Letter for Eve");
        downtown.addRegisteredMail(letter3);
        
        // First assignment to Tom
        boolean firstAssignment = mailDeliverySystem.assignMailman(letter3, tom);
        assertTrue("First assignment should be successful", firstAssignment);
        assertEquals("Tom should be assigned as mailman", tom, letter3.getMailman());
        
        // Action 1: Reassign Letter3 to Jerry
        boolean reassignmentResult = mailDeliverySystem.assignMailman(letter3, jerry);
        
        // Expected Output 1: false (successful reassignment) - Note: The specification says "false" but this seems contradictory
        // Based on the code logic, reassignment should return false because mail is already assigned
        assertFalse("Reassignment to different mailman should fail", reassignmentResult);
        assertEquals("Original mailman should remain assigned", tom, letter3.getMailman());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        Inhabitant ieril = new Inhabitant();
        ieril.setName("Ieril");
        mailDeliverySystem.addInhabitant(midtown, ieril);
        
        Letter letter4 = new Letter();
        letter4.setAddressee(ieril);
        letter4.setContent("Letter for Ieril");
        midtown.addRegisteredMail(letter4);
        
        // Expected Output 2: false - This seems to be testing assignment without a mailman
        // Since no mailman is assigned, the test verifies no assignment was made
        assertNull("Letter4 should not have any mailman assigned initially", letter4.getMailman());
    }
}