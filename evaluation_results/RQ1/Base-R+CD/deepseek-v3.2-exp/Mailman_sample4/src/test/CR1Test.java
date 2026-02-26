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
        john = new Mailman();
        john.setId("M001");
        john.setName("John");
        
        mike = new Mailman();
        mike.setId("M002");
        mike.setName("Mike");
        
        peter = new Mailman();
        peter.setId("M003");
        peter.setName("Peter");
        
        sarah = new Mailman();
        sarah.setId("M004");
        sarah.setName("Sarah");
        
        tom = new Mailman();
        tom.setId("M005");
        tom.setName("Tom");
        
        jerry = new Mailman();
        jerry.setId("M006");
        jerry.setName("Jerry");
        
        // Initialize inhabitants
        alice = new Inhabitant();
        alice.setId("I001");
        alice.setName("Alice");
        
        bob = new Inhabitant();
        bob.setId("I002");
        bob.setName("Bob");
        
        carol = new Inhabitant();
        carol.setId("I003");
        carol.setName("Carol");
        
        dave = new Inhabitant();
        dave.setId("I004");
        dave.setName("Dave");
        
        eve = new Inhabitant();
        eve.setId("I005");
        eve.setName("Eve");
        
        ieril = new Inhabitant();
        ieril.setId("I006");
        ieril.setName("Ieril");
        
        // Initialize mail items
        letter1 = new Letter();
        letter1.setId("L001");
        
        parcel1 = new Parcel();
        parcel1.setId("P001");
        
        letter2 = new Letter();
        letter2.setId("L002");
        
        parcel2 = new Parcel();
        parcel2.setId("P002");
        
        letter3 = new Letter();
        letter3.setId("L003");
        
        letter4 = new Letter();
        letter4.setId("L004");
    }

    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict", add Mailman "John", add Inhabitant "Alice", create Registered Letter "Letter1" for Alice
        northDistrict.addMailman(john);
        northDistrict.addInhabitant(alice);
        northDistrict.getAllMails().add(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Assignment should be successful when mailman and inhabitant are in same area", result);
        assertEquals("Letter1 should be assigned to John", john, letter1.getCarrier());
        assertEquals("Letter1 should be addressed to Alice", alice, letter1.getAddressee());
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict", add Mailman "Mike" to EastDistrict, add Inhabitant "Bob" to WestDistrict, create RegisteredParcel "Parcel1" for Bob
        eastDistrict.addMailman(mike);
        westDistrict.addInhabitant(bob);
        westDistrict.getAllMails().add(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Assignment should fail when mailman is not in the same area as the mail", result);
        assertNull("Parcel1 should not have a carrier assigned", parcel1.getCarrier());
        assertNull("Parcel1 should not have an addressee assigned", parcel1.getAddressee());
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter", add Inhabitant "Carol" to CentralDistrict, create RegisteredLetter "Letter2" for Carol
        centralDistrict.addInhabitant(carol);
        centralDistrict.getAllMails().add(letter2);
        // Note: Peter is not added to CentralDistrict
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the area", result);
        assertNull("Letter2 should not have a carrier assigned", letter2.getCarrier());
        assertNull("Letter2 should not have an addressee assigned", letter2.getAddressee());
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave", add Mailman "Sarah" to SouthDistrict, create RegisteredParcel "Parcel2" for non-existent "Dave"
        southDistrict.addMailman(sarah);
        southDistrict.getAllMails().add(parcel2);
        // Note: Dave is not added to SouthDistrict
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Assignment should fail when inhabitant is not in the area", result);
        assertNull("Parcel2 should not have a carrier assigned", parcel2.getCarrier());
        assertNull("Parcel2 should not have an addressee assigned", parcel2.getAddressee());
    }

    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown", add Mailman "Tom" and "Jerry" to Downtown, add Inhabitant "Eve" to Downtown, create RegisteredLetter "Letter3" for Eve, assigned to Tom
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        downtown.addInhabitant(eve);
        downtown.getAllMails().add(letter3);
        
        // First assignment: Assign Tom to Letter3
        boolean firstAssignment = downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        assertTrue("First assignment should be successful", firstAssignment);
        assertEquals("Letter3 should be assigned to Tom", tom, letter3.getCarrier());
        
        // Action 1: Reassign Letter3 to Jerry
        boolean reassignmentResult = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment) - Note: The specification says false for reassignment
        assertFalse("Reassignment should fail when mail is already assigned", reassignmentResult);
        assertEquals("Letter3 should remain assigned to Tom", tom, letter3.getCarrier());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        midtown.addInhabitant(ieril);
        midtown.getAllMails().add(letter4);
        // Note: No mailman is added to Midtown
        
        // Action 2: Try to assign Letter4 (no mailman available in Midtown)
        boolean secondAssignment = midtown.assignRegisteredMailDeliver(null, ieril, letter4);
        
        // Expected Output 2: false
        assertFalse("Assignment should fail when no mailman is specified", secondAssignment);
        assertNull("Letter4 should not have a carrier assigned", letter4.getCarrier());
    }

}