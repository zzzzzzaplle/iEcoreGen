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
        john = new Mailman();
        john.setName("John");
        john.setId("M001");
        
        mike = new Mailman();
        mike.setName("Mike");
        mike.setId("M002");
        
        peter = new Mailman();
        peter.setName("Peter");
        peter.setId("M003");
        
        sarah = new Mailman();
        sarah.setName("Sarah");
        sarah.setId("M004");
        
        tom = new Mailman();
        tom.setName("Tom");
        tom.setId("M005");
        
        jerry = new Mailman();
        jerry.setName("Jerry");
        jerry.setId("M006");
        
        // Initialize inhabitants
        alice = new Inhabitant();
        alice.setName("Alice");
        alice.setId("I001");
        
        bob = new Inhabitant();
        bob.setName("Bob");
        bob.setId("I002");
        
        carol = new Inhabitant();
        carol.setName("Carol");
        carol.setId("I003");
        
        dave = new Inhabitant();
        dave.setName("Dave");
        dave.setId("I004");
        
        eve = new Inhabitant();
        eve.setName("Eve");
        eve.setId("I005");
        
        ieril = new Inhabitant();
        ieril.setName("Ieril");
        ieril.setId("I006");
        
        // Initialize mail items
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
        letter1.setAddressee(alice);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Assignment should succeed when mailman and inhabitant are in same area", result);
        assertEquals("Carrier should be John", john, letter1.getCarrier());
        assertEquals("Addressee should be Alice", alice, letter1.getAddressee());
        assertTrue("Letter1 should be in allMails list", northDistrict.getAllMails().contains(letter1));
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        eastDistrict.addMailman(mike);
        // Add Inhabitant "Bob" to WestDistrict
        westDistrict.addInhabitant(bob);
        // Create RegisteredParcel "Parcel1" for Bob
        parcel1.setAddressee(bob);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = eastDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Assignment should fail when mailman and inhabitant are in different areas", result);
        assertNull("Parcel1 should not have a carrier assigned", parcel1.getCarrier());
        assertFalse("Parcel1 should not be in EastDistrict's allMails", eastDistrict.getAllMails().contains(parcel1));
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        // Create RegisteredLetter "Letter2" for Carol
        letter2.setAddressee(carol);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the area", result);
        assertNull("Letter2 should not have a carrier assigned", letter2.getCarrier());
        assertFalse("Letter2 should not be in CentralDistrict's allMails", centralDistrict.getAllMails().contains(letter2));
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        parcel2.setAddressee(dave);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Assignment should fail when inhabitant is not in the area", result);
        assertNull("Parcel2 should not have a carrier assigned", parcel2.getCarrier());
        assertFalse("Parcel2 should not be in SouthDistrict's allMails", southDistrict.getAllMails().contains(parcel2));
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
        letter3.setAddressee(eve);
        boolean initialAssignment = downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        assertTrue("Initial assignment should succeed", initialAssignment);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean reassignmentResult = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Reassignment should fail when mail is already assigned to a carrier", reassignmentResult);
        assertEquals("Carrier should remain Tom after reassignment attempt", tom, letter3.getCarrier());
        
        // SetUp for Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        midtown.addInhabitant(ieril);
        letter4.setAddressee(ieril);
        
        // Action 2: Assign letter4 (no mailman assignment attempted in specification)
        // Expected Output 2: false - This part seems incomplete in the specification
        // Since the specification doesn't specify what action to test for Expected Output 2,
        // and it says "false", I'll interpret this as testing assignment without mailman
        boolean assignmentResult = midtown.assignRegisteredMailDeliver(null, ieril, letter4);
        assertFalse("Assignment should fail without a mailman", assignmentResult);
    }
}