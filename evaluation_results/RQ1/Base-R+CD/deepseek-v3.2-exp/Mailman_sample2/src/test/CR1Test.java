import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
    private Inhabitant dave;
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
        john.setName("John");
        
        mike = new Mailman();
        mike.setName("Mike");
        
        peter = new Mailman();
        peter.setName("Peter");
        
        sarah = new Mailman();
        sarah.setName("Sarah");
        
        tom = new Mailman();
        tom.setName("Tom");
        
        jerry = new Mailman();
        jerry.setName("Jerry");
        
        // Initialize all inhabitants
        alice = new Inhabitant();
        alice.setName("Alice");
        
        bob = new Inhabitant();
        bob.setName("Bob");
        
        carol = new Inhabitant();
        carol.setName("Carol");
        
        dave = new Inhabitant();
        dave.setName("Dave");
        
        eve = new Inhabitant();
        eve.setName("Eve");
        
        ieril = new Inhabitant();
        ieril.setName("Ieril");
        
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
        alice.setArea(northDistrict);
        northDistrict.addInhabitant(alice);
        
        // Create Registered Letter "Letter1" for Alice
        letter1.setAddressee(alice);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Assignment should be successful when mailman and inhabitant are in same area", result);
        assertEquals("Mail carrier should be set to John", john, letter1.getCarrier());
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        eastDistrict.addMailman(mike);
        
        // Add Inhabitant "Bob" to WestDistrict
        bob.setArea(westDistrict);
        westDistrict.addInhabitant(bob);
        
        // Create RegisteredParcel "Parcel1" for Bob
        parcel1.setAddressee(bob);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Assignment should fail when mailman is not in the same area as inhabitant", result);
        assertNull("Mail carrier should remain null", parcel1.getCarrier());
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        carol.setArea(centralDistrict);
        centralDistrict.addInhabitant(carol);
        
        // Create RegisteredLetter "Letter2" for Carol
        letter2.setAddressee(carol);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the geographical area", result);
        assertNull("Mail carrier should remain null", letter2.getCarrier());
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        // Note: We'll create Dave but not add him to SouthDistrict to simulate non-existent scenario
        dave.setArea(southDistrict); // Set area but don't add to inhabitants list
        parcel2.setAddressee(dave);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Assignment should fail when addressee is not in the geographical area", result);
        assertNull("Mail carrier should remain null", parcel2.getCarrier());
    }

    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        // Add Mailman "Tom" and "Jerry" to Downtown
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        
        // Add Inhabitant "Eve" to Downtown
        eve.setArea(downtown);
        downtown.addInhabitant(eve);
        
        // Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        letter3.setAddressee(eve);
        downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Reassignment should fail when mail is already assigned to a carrier", result1);
        assertEquals("Mail carrier should remain Tom", tom, letter3.getCarrier());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        ieril.setArea(midtown);
        midtown.addInhabitant(ieril);
        letter4.setAddressee(ieril);
        
        // This is just setup - no assignment action specified, so no assertion needed
        // The test case appears to have incomplete specification for Action 2
    }

}