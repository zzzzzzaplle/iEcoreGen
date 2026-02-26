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
    
    private Mailman john;
    private Mailman mike;
    private Mailman peter;
    private Mailman sarah;
    private Mailman tom;
    private Mailman jerry;
    
    private Inhabitant alice;
    private Inhabitant bob;
    private Inhabitant carol;
    private Inhabitant daveNonexistent;
    private Inhabitant eve;
    private Inhabitant ieril;
    
    private Letter letter1;
    private Parcel parcel1;
    private Letter letter2;
    private Parcel parcel2;
    private Letter letter3;
    private Letter letter4;
    
    @Before
    public void setUp() {
        // Initialize all geographical areas
        northDistrict = new GeographicalArea();
        eastDistrict = new GeographicalArea();
        westDistrict = new GeographicalArea();
        centralDistrict = new GeographicalArea();
        southDistrict = new GeographicalArea();
        downtown = new GeographicalArea();
        midtown = new GeographicalArea();
        
        // Initialize all mailmen
        john = new Mailman();
        mike = new Mailman();
        peter = new Mailman();
        sarah = new Mailman();
        tom = new Mailman();
        jerry = new Mailman();
        
        // Initialize all inhabitants
        alice = new Inhabitant();
        bob = new Inhabitant();
        carol = new Inhabitant();
        daveNonexistent = new Inhabitant();
        eve = new Inhabitant();
        ieril = new Inhabitant();
        
        // Initialize all mail items
        letter1 = new Letter();
        parcel1 = new Parcel();
        letter2 = new Letter();
        parcel2 = new Parcel();
        letter3 = new Letter();
        letter4 = new Letter();
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        // Add Mailman "John" to NorthDistrict
        northDistrict.addMailman(john);
        // Add Inhabitant "Alice" to NorthDistrict
        northDistrict.addInhabitant(alice);
        // Create Registered Letter "Letter1" for Alice
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Assignment should succeed when mailman and inhabitant are in same area", result);
        
        // Verify the assignment was actually made
        assertEquals("Carrier should be set to John", john, letter1.getCarrier());
        assertEquals("Addressee should be set to Alice", alice, letter1.getAddressee());
        assertTrue("Mail should be added to allMails list", northDistrict.getAllMails().contains(letter1));
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        eastDistrict.addMailman(mike);
        // Add Inhabitant "Bob" to WestDistrict
        westDistrict.addInhabitant(bob);
        // Create RegisteredParcel "Parcel1" for Bob
        // Action: Assign Mike to deliver Parcel1
        boolean result = eastDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Assignment should fail when inhabitant is not in mailman's area", result);
        
        // Verify no assignment was made
        assertNull("Carrier should not be set", parcel1.getCarrier());
        assertNull("Addressee should not be set", parcel1.getAddressee());
        assertFalse("Mail should not be added to allMails list", eastDistrict.getAllMails().contains(parcel1));
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        // Create RegisteredLetter "Letter2" for Carol
        // Action: Assign Mailman "Peter" to Letter2 (Peter not added to CentralDistrict)
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the area", result);
        
        // Verify no assignment was made
        assertNull("Carrier should not be set", letter2.getCarrier());
        assertNull("Addressee should not be set", letter2.getAddressee());
        assertFalse("Mail should not be added to allMails list", centralDistrict.getAllMails().contains(letter2));
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, daveNonexistent, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Assignment should fail when inhabitant is not in the area", result);
        
        // Verify no assignment was made
        assertNull("Carrier should not be set", parcel2.getCarrier());
        assertNull("Addressee should not be set", parcel2.getAddressee());
        assertFalse("Mail should not be added to allMails list", southDistrict.getAllMails().contains(parcel2));
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        // Add Mailman "Tom" and "Jerry" to Downtown
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        // Add Inhabitant "Eve" to Downtown
        downtown.addInhabitant(eve);
        // Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        boolean firstAssignment = downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        assertTrue("First assignment should succeed", firstAssignment);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean reassignmentResult = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Reassignment should fail when mail is already assigned", reassignmentResult);
        
        // Verify the original assignment remains
        assertEquals("Carrier should remain Tom", tom, letter3.getCarrier());
        assertEquals("Addressee should remain Eve", eve, letter3.getAddressee());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // Add Inhabitant "Ieril" to Midtown
        midtown.addInhabitant(ieril);
        // Create RegisteredLetter "Letter4" for Ieril
        boolean secondAssignment = midtown.assignRegisteredMailDeliver(null, ieril, letter4);
        
        // Expected Output 2: false
        assertFalse("Assignment should fail when mailman is null", secondAssignment);
        
        // Verify no assignment was made for letter4
        assertNull("Carrier should not be set for letter4", letter4.getCarrier());
        assertNull("Addressee should not be set for letter4", letter4.getAddressee());
        assertFalse("Mail should not be added to allMails list", midtown.getAllMails().contains(letter4));
    }
}