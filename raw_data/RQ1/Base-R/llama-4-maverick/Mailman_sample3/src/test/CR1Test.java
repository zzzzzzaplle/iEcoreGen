import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private MailDeliveryManager mailDeliveryManager;
    private GeographicalArea northDistrict;
    private GeographicalArea eastDistrict;
    private GeographicalArea westDistrict;
    private GeographicalArea centralDistrict;
    private GeographicalArea southDistrict;
    private GeographicalArea downtown;
    
    @Before
    public void setUp() {
        mailDeliveryManager = new MailDeliveryManager();
        
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
        
        // Add areas to manager
        mailDeliveryManager.addGeographicalArea(northDistrict);
        mailDeliveryManager.addGeographicalArea(eastDistrict);
        mailDeliveryManager.addGeographicalArea(westDistrict);
        mailDeliveryManager.addGeographicalArea(centralDistrict);
        mailDeliveryManager.addGeographicalArea(southDistrict);
        mailDeliveryManager.addGeographicalArea(downtown);
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        // Add Mailman "John" to NorthDistrict
        Mailman john = new Mailman();
        john.setName("John");
        mailDeliveryManager.manageMailman(john, northDistrict, null, true);
        
        // Add Inhabitant "Alice" to NorthDistrict
        Inhabitant alice = new Inhabitant();
        alice.setName("Alice");
        mailDeliveryManager.manageInhabitant(alice, northDistrict, true);
        
        // Create Registered Letter "Letter1" for Alice
        Letter letter1 = new Letter();
        letter1.setAddressee(alice);
        northDistrict.addRegisteredMail(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = mailDeliveryManager.assignMailman(letter1, john);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("Mailman should be assigned to the letter", john, letter1.getMailman());
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        Mailman mike = new Mailman();
        mike.setName("Mike");
        mailDeliveryManager.manageMailman(mike, eastDistrict, null, true);
        
        // Add Inhabitant "Bob" to WestDistrict
        Inhabitant bob = new Inhabitant();
        bob.setName("Bob");
        mailDeliveryManager.manageInhabitant(bob, westDistrict, true);
        
        // Create RegisteredParcel "Parcel1" for Bob
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(bob);
        westDistrict.addRegisteredMail(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = mailDeliveryManager.assignMailman(parcel1, mike);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman should not be assigned to parcel in different area", result);
        assertNull("Mailman should not be assigned to the parcel", parcel1.getMailman());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        Inhabitant carol = new Inhabitant();
        carol.setName("Carol");
        mailDeliveryManager.manageInhabitant(carol, centralDistrict, true);
        
        // Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = new Letter();
        letter2.setAddressee(carol);
        centralDistrict.addRegisteredMail(letter2);
        
        // Create Mailman "Peter" but don't add to any area
        Mailman peter = new Mailman();
        peter.setName("Peter");
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = mailDeliveryManager.assignMailman(letter2, peter);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman should not be assigned", result);
        assertNull("Mailman should not be assigned to the letter", letter2.getMailman());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        Mailman sarah = new Mailman();
        sarah.setName("Sarah");
        mailDeliveryManager.manageMailman(sarah, southDistrict, null, true);
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        // Create a new inhabitant but don't add to any area
        Inhabitant dave = new Inhabitant();
        dave.setName("Dave");
        
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(dave);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = mailDeliveryManager.assignMailman(parcel2, sarah);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mailman should not be assigned to mail for non-existent inhabitant", result);
        assertNull("Mailman should not be assigned to the parcel", parcel2.getMailman());
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        // Add Mailman "Tom" and "Jerry" to Downtown
        Mailman tom = new Mailman();
        tom.setName("Tom");
        mailDeliveryManager.manageMailman(tom, downtown, null, true);
        
        Mailman jerry = new Mailman();
        jerry.setName("Jerry");
        mailDeliveryManager.manageMailman(jerry, downtown, null, true);
        
        // Add Inhabitant "Eve" to Downtown
        Inhabitant eve = new Inhabitant();
        eve.setName("Eve");
        mailDeliveryManager.manageInhabitant(eve, downtown, true);
        
        // Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        Letter letter3 = new Letter();
        letter3.setAddressee(eve);
        downtown.addRegisteredMail(letter3);
        mailDeliveryManager.assignMailman(letter3, tom);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = mailDeliveryManager.assignMailman(letter3, jerry);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Should not be able to reassign already assigned mail", result1);
        assertEquals("Original mailman should remain assigned", tom, letter3.getMailman());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // Note: Midtown doesn't exist in our setup, so creating new area
        GeographicalArea midtown = new GeographicalArea();
        midtown.setName("Midtown");
        mailDeliveryManager.addGeographicalArea(midtown);
        
        Inhabitant ieril = new Inhabitant();
        ieril.setName("Ieril");
        mailDeliveryManager.manageInhabitant(ieril, midtown, true);
        
        Letter letter4 = new Letter();
        letter4.setAddressee(ieril);
        midtown.addRegisteredMail(letter4);
        
        // Try to assign Jerry (from Downtown) to Letter4 (in Midtown)
        boolean result2 = mailDeliveryManager.assignMailman(letter4, jerry);
        
        // Expected Output 2: false
        assertFalse("Mailman from different area should not be assigned", result2);
        assertNull("No mailman should be assigned to the letter", letter4.getMailman());
    }
}