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
        // Initialize geographical areas
        northDistrict = new GeographicalArea();
        eastDistrict = new GeographicalArea();
        westDistrict = new GeographicalArea();
        centralDistrict = new GeographicalArea();
        southDistrict = new GeographicalArea();
        downtown = new GeographicalArea();
        midtown = new GeographicalArea();
        
        // Initialize mailmen
        john = new Mailman("John");
        mike = new Mailman("Mike");
        peter = new Mailman("Peter");
        sarah = new Mailman("Sarah");
        tom = new Mailman("Tom");
        jerry = new Mailman("Jerry");
        
        // Initialize inhabitants
        alice = new Inhabitant("Alice");
        bob = new Inhabitant("Bob");
        carol = new Inhabitant("Carol");
        dave = new Inhabitant("Dave");
        eve = new Inhabitant("Eve");
        ieril = new Inhabitant("Ieril");
        
        // Initialize mail items
        letter1 = new Letter(alice);
        parcel1 = new Parcel(bob);
        letter2 = new Letter(carol);
        parcel2 = new Parcel(dave);
        letter3 = new Letter(eve);
        letter4 = new Letter(ieril);
    }

    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        // Add Mailman "John" to NorthDistrict
        northDistrict.addMailman(john);
        // Add Inhabitant "Alice" to NorthDistrict
        northDistrict.addInhabitant(alice);
        // Create Registered Letter "Letter1" for Alice
        northDistrict.addRegisteredMail(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Assignment should succeed when mailman, addressee and mail are in same area", result);
        assertEquals("John should be assigned as carrier", john, letter1.getCarrier());
        assertEquals("Alice should be the addressee", alice, letter1.getAddressee());
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        eastDistrict.addMailman(mike);
        // Add Inhabitant "Bob" to WestDistrict
        westDistrict.addInhabitant(bob);
        // Create RegisteredParcel "Parcel1" for Bob
        westDistrict.addRegisteredMail(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = eastDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Assignment should fail when mailman is not in the same area as addressee", result);
        assertNull("Parcel should not have a carrier assigned", parcel1.getCarrier());
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        // Create RegisteredLetter "Letter2" for Carol
        centralDistrict.addRegisteredMail(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the geographical area", result);
        assertNull("Letter should not have a carrier assigned", letter2.getCarrier());
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        southDistrict.addRegisteredMail(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Assignment should fail when addressee is not in the geographical area", result);
        assertNull("Parcel should not have a carrier assigned", parcel2.getCarrier());
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
        downtown.addRegisteredMail(letter3);
        boolean firstAssignment = downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        assertTrue("First assignment should succeed", firstAssignment);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Reassignment should fail when mail already has a carrier", result1);
        assertEquals("Tom should remain as carrier", tom, letter3.getCarrier());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        midtown.addInhabitant(ieril);
        midtown.addRegisteredMail(letter4);
        boolean result2 = midtown.assignRegisteredMailDeliver(jerry, ieril, letter4);
        
        // Expected Output 2: false
        assertFalse("Assignment should fail when mailman is not in the geographical area", result2);
        assertNull("Letter4 should not have a carrier assigned", letter4.getCarrier());
    }
}