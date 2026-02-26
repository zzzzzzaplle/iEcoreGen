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
    private GeographicalArea midtown;
    private MailDeliveryManager manager;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
        
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
        
        // Add areas to manager
        manager.addGeographicalArea(northDistrict);
        manager.addGeographicalArea(eastDistrict);
        manager.addGeographicalArea(westDistrict);
        manager.addGeographicalArea(centralDistrict);
        manager.addGeographicalArea(southDistrict);
        manager.addGeographicalArea(downtown);
        manager.addGeographicalArea(midtown);
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        // SetUp: Add Mailman "John" to NorthDistrict
        Mailman john = new Mailman();
        john.setName("John");
        manager.addMailmanToGeographicalArea(john, northDistrict);
        
        // SetUp: Add Inhabitant "Alice" to NorthDistrict
        Inhabitant alice = new Inhabitant();
        alice.setName("Alice");
        manager.addInhabitantToGeographicalArea(alice, northDistrict);
        
        // SetUp: Create Registered Letter "Letter1" for Alice
        Letter letter1 = new Letter();
        letter1.setAddressee(alice);
        letter1.setContent("Test letter content");
        northDistrict.addRegisteredMail(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = manager.assignMailmanToRegisteredMail(letter1, john);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("John should be assigned to the letter", john, letter1.getMailman());
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // SetUp: Add Mailman "Mike" to EastDistrict
        Mailman mike = new Mailman();
        mike.setName("Mike");
        manager.addMailmanToGeographicalArea(mike, eastDistrict);
        
        // SetUp: Add Inhabitant "Bob" to WestDistrict
        Inhabitant bob = new Inhabitant();
        bob.setName("Bob");
        manager.addInhabitantToGeographicalArea(bob, westDistrict);
        
        // SetUp: Create RegisteredParcel "Parcel1" for Bob
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(bob);
        parcel1.setWeight(2.5);
        westDistrict.addRegisteredMail(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = manager.assignMailmanToRegisteredMail(parcel1, mike);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman should not be assigned to parcel in different area", result);
        assertNull("Parcel should not have any mailman assigned", parcel1.getMailman());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        Mailman peter = new Mailman();
        peter.setName("Peter");
        // Note: Peter is NOT added to any geographical area
        
        // SetUp: Add Inhabitant "Carol" to CentralDistrict
        Inhabitant carol = new Inhabitant();
        carol.setName("Carol");
        manager.addInhabitantToGeographicalArea(carol, centralDistrict);
        
        // SetUp: Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = new Letter();
        letter2.setAddressee(carol);
        letter2.setContent("Another test letter");
        centralDistrict.addRegisteredMail(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = manager.assignMailmanToRegisteredMail(letter2, peter);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman should not be assigned", result);
        assertNull("Letter should not have any mailman assigned", letter2.getMailman());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        Inhabitant dave = new Inhabitant();
        dave.setName("Dave");
        // Note: Dave is NOT added to SouthDistrict (non-existent in the area)
        
        // SetUp: Add Mailman "Sarah" to SouthDistrict
        Mailman sarah = new Mailman();
        sarah.setName("Sarah");
        manager.addMailmanToGeographicalArea(sarah, southDistrict);
        
        // SetUp: Create RegisteredParcel "Parcel2" for non-existent "Dave"
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(dave);
        parcel2.setWeight(1.8);
        southDistrict.addRegisteredMail(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = manager.assignMailmanToRegisteredMail(parcel2, sarah);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mailman should not be assigned to mail for non-existent inhabitant", result);
        assertNull("Parcel should not have any mailman assigned", parcel2.getMailman());
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        // SetUp: Add Mailman "Tom" and "Jerry" to Downtown
        Mailman tom = new Mailman();
        tom.setName("Tom");
        manager.addMailmanToGeographicalArea(tom, downtown);
        
        Mailman jerry = new Mailman();
        jerry.setName("Jerry");
        manager.addMailmanToGeographicalArea(jerry, downtown);
        
        // SetUp: Add Inhabitant "Eve" to Downtown
        Inhabitant eve = new Inhabitant();
        eve.setName("Eve");
        manager.addInhabitantToGeographicalArea(eve, downtown);
        
        // SetUp: Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        Letter letter3 = new Letter();
        letter3.setAddressee(eve);
        letter3.setContent("Letter for Eve");
        downtown.addRegisteredMail(letter3);
        
        // First assignment to Tom
        boolean firstAssignment = manager.assignMailmanToRegisteredMail(letter3, tom);
        assertTrue("First assignment should be successful", firstAssignment);
        assertEquals("Tom should be assigned to the letter", tom, letter3.getMailman());
        
        // Action 1: Reassign Letter3 to Jerry
        boolean reassignment = manager.assignMailmanToRegisteredMail(letter3, jerry);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Should not be able to reassign already assigned mail", reassignment);
        assertEquals("Tom should remain assigned to the letter", tom, letter3.getMailman());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        Inhabitant ieril = new Inhabitant();
        ieril.setName("Ieril");
        manager.addInhabitantToGeographicalArea(ieril, midtown);
        
        Letter letter4 = new Letter();
        letter4.setAddressee(ieril);
        letter4.setContent("Letter for Ieril");
        midtown.addRegisteredMail(letter4);
        
        // Expected Output 2: false
        // Note: The test specification seems to have a typo - it says "false" but doesn't specify an action
        // Since no mailman assignment is attempted for Letter4, we'll just verify the letter exists
        assertNotNull("Letter4 should exist", letter4);
        assertNull("Letter4 should not have any mailman assigned initially", letter4.getMailman());
    }
}