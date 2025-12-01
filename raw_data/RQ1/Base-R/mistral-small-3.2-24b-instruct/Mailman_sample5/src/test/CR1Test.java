import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    private MailManagementSystem system;
    
    @Before
    public void setUp() {
        system = new MailManagementSystem();
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // Setup: Create GeographicalArea "NorthDistrict"
        GeographicalArea northDistrict = new GeographicalArea();
        northDistrict.setAreaId("NorthDistrict");
        system.addGeographicalArea(northDistrict);
        
        // Setup: Add Mailman "John" to NorthDistrict
        Mailman john = new Mailman();
        john.setMailmanId("John");
        john.setGeographicalArea(northDistrict);
        northDistrict.addMailman(john);
        
        // Setup: Add Inhabitant "Alice" to NorthDistrict
        Inhabitant alice = new Inhabitant();
        alice.setInhabitantId("Alice");
        alice.setGeographicalArea(northDistrict);
        northDistrict.addInhabitant(alice);
        
        // Setup: Create Registered Letter "Letter1" for Alice
        Letter letter1 = new Letter();
        letter1.setMailId("Letter1");
        letter1.setAddressee(alice);
        
        // Action: Assign John to deliver Letter1
        boolean result = john.assignMail(letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be able to assign letter in same area", result);
        assertEquals("Letter should be assigned to John", john, letter1.getMailman());
        assertTrue("John's deliveries should contain the letter", john.getDeliveries().contains(letter1));
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // Setup: Create GeographicalArea "EastDistrict" and "WestDistrict"
        GeographicalArea eastDistrict = new GeographicalArea();
        eastDistrict.setAreaId("EastDistrict");
        system.addGeographicalArea(eastDistrict);
        
        GeographicalArea westDistrict = new GeographicalArea();
        westDistrict.setAreaId("WestDistrict");
        system.addGeographicalArea(westDistrict);
        
        // Setup: Add Mailman "Mike" to EastDistrict
        Mailman mike = new Mailman();
        mike.setMailmanId("Mike");
        mike.setGeographicalArea(eastDistrict);
        eastDistrict.addMailman(mike);
        
        // Setup: Add Inhabitant "Bob" to WestDistrict
        Inhabitant bob = new Inhabitant();
        bob.setInhabitantId("Bob");
        bob.setGeographicalArea(westDistrict);
        westDistrict.addInhabitant(bob);
        
        // Setup: Create RegisteredParcel "Parcel1" for Bob
        Parcel parcel1 = new Parcel();
        parcel1.setMailId("Parcel1");
        parcel1.setAddressee(bob);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = mike.assignMail(parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman should not be able to assign parcel in different area", result);
        assertNull("Parcel should not have mailman assigned", parcel1.getMailman());
        assertFalse("Mike's deliveries should not contain the parcel", mike.getDeliveries().contains(parcel1));
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // Setup: Create GeographicalArea "CentralDistrict"
        GeographicalArea centralDistrict = new GeographicalArea();
        centralDistrict.setAreaId("CentralDistrict");
        system.addGeographicalArea(centralDistrict);
        
        // Setup: Create Mailman "Peter" (but not added to any area)
        Mailman peter = new Mailman();
        peter.setMailmanId("Peter");
        // Note: Peter is not added to any geographical area
        
        // Setup: Add Inhabitant "Carol" to CentralDistrict
        Inhabitant carol = new Inhabitant();
        carol.setInhabitantId("Carol");
        carol.setGeographicalArea(centralDistrict);
        centralDistrict.addInhabitant(carol);
        
        // Setup: Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = new Letter();
        letter2.setMailId("Letter2");
        letter2.setAddressee(carol);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = peter.assignMail(letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Mailman not in geographical area should not be able to assign mail", result);
        assertNull("Letter should not have mailman assigned", letter2.getMailman());
        assertFalse("Peter's deliveries should not contain the letter", peter.getDeliveries().contains(letter2));
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // Setup: Create GeographicalArea "SouthDistrict"
        GeographicalArea southDistrict = new GeographicalArea();
        southDistrict.setAreaId("SouthDistrict");
        system.addGeographicalArea(southDistrict);
        
        // Setup: Create Inhabitant "Dave" but not added to any area
        Inhabitant dave = new Inhabitant();
        dave.setInhabitantId("Dave");
        // Note: Dave is not added to any geographical area
        
        // Setup: Add Mailman "Sarah" to SouthDistrict
        Mailman sarah = new Mailman();
        sarah.setMailmanId("Sarah");
        sarah.setGeographicalArea(southDistrict);
        southDistrict.addMailman(sarah);
        
        // Setup: Create RegisteredParcel "Parcel2" for non-existent "Dave"
        Parcel parcel2 = new Parcel();
        parcel2.setMailId("Parcel2");
        parcel2.setAddressee(dave);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = sarah.assignMail(parcel2);
        
        // Expected Output: false (addressee doesn't exist in area)
        assertFalse("Mailman should not be able to assign mail for inhabitant not in area", result);
        assertNull("Parcel should not have mailman assigned", parcel2.getMailman());
        assertFalse("Sarah's deliveries should not contain the parcel", sarah.getDeliveries().contains(parcel2));
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // Setup: Create GeographicalArea "Downtown"
        GeographicalArea downtown = new GeographicalArea();
        downtown.setAreaId("Downtown");
        system.addGeographicalArea(downtown);
        
        // Setup: Add Mailman "Tom" and "Jerry" to Downtown
        Mailman tom = new Mailman();
        tom.setMailmanId("Tom");
        tom.setGeographicalArea(downtown);
        downtown.addMailman(tom);
        
        Mailman jerry = new Mailman();
        jerry.setMailmanId("Jerry");
        jerry.setGeographicalArea(downtown);
        downtown.addMailman(jerry);
        
        // Setup: Add Inhabitant "Eve" to Downtown
        Inhabitant eve = new Inhabitant();
        eve.setInhabitantId("Eve");
        eve.setGeographicalArea(downtown);
        downtown.addInhabitant(eve);
        
        // Setup: Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        Letter letter3 = new Letter();
        letter3.setMailId("Letter3");
        letter3.setAddressee(eve);
        tom.assignMail(letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = jerry.assignMail(letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Should not be able to reassign already assigned mail", result1);
        assertEquals("Letter should remain assigned to Tom", tom, letter3.getMailman());
        assertTrue("Tom's deliveries should contain the letter", tom.getDeliveries().contains(letter3));
        assertFalse("Jerry's deliveries should not contain the letter", jerry.getDeliveries().contains(letter3));
        
        // Setup for Action 2: Create GeographicalArea "Midtown"
        GeographicalArea midtown = new GeographicalArea();
        midtown.setAreaId("Midtown");
        system.addGeographicalArea(midtown);
        
        // Setup for Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        Inhabitant ieril = new Inhabitant();
        ieril.setInhabitantId("Ieril");
        ieril.setGeographicalArea(midtown);
        midtown.addInhabitant(ieril);
        
        Letter letter4 = new Letter();
        letter4.setMailId("Letter4");
        letter4.setAddressee(ieril);
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // Note: This is just creation, not assignment, so we need to test assignment
        boolean result2 = tom.assignMail(letter4);
        
        // Expected Output 2: false (mailman not in same area as addressee)
        assertFalse("Mailman from different area should not be able to assign mail", result2);
        assertNull("Letter should not have mailman assigned", letter4.getMailman());
        assertFalse("Tom's deliveries should not contain the letter", tom.getDeliveries().contains(letter4));
    }
}