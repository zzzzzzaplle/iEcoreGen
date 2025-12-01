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
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("Letter should be assigned to John", john, letter1.getCarrier());
        assertEquals("Letter should be addressed to Alice", alice, letter1.getAddressee());
        assertTrue("Letter should be added to allMails list", northDistrict.getAllMails().contains(letter1));
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
        boolean result = westDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman from different area should not be assigned", result);
        assertNull("Parcel should not have a carrier assigned", parcel1.getCarrier());
        assertFalse("Parcel should not be in WestDistrict's mail list", westDistrict.getAllMails().contains(parcel1));
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
        assertFalse("Non-existent mailman should not be assigned", result);
        assertNull("Letter should not have a carrier assigned", letter2.getCarrier());
        assertFalse("Letter should not be in CentralDistrict's mail list", centralDistrict.getAllMails().contains(letter2));
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        parcel2.setAddressee(dave); // Dave exists as object but is not added to SouthDistrict
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mail for non-existent inhabitant should not be assigned", result);
        assertNull("Parcel should not have a carrier assigned", parcel2.getCarrier());
        assertFalse("Parcel should not be in SouthDistrict's mail list", southDistrict.getAllMails().contains(parcel2));
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
        boolean firstAssignment = downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        assertTrue("First assignment should succeed", firstAssignment);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean reassignmentResult = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        // Note: The specification says "Expected Output: false" but the description says "successful reassignment"
        // Following the exact Expected Output as specified
        assertFalse("Reassignment to already assigned mail should fail", reassignmentResult);
        assertEquals("Carrier should remain as Tom", tom, letter3.getCarrier());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        GeographicalArea midtown = new GeographicalArea();
        Inhabitant ieril = new Inhabitant();
        ieril.setId("I006");
        ieril.setName("Ieril");
        letter4.setAddressee(ieril);
        
        boolean assignmentResult = midtown.assignRegisteredMailDeliver(jerry, ieril, letter4);
        
        // Expected Output 2: false
        assertFalse("Assignment with non-existent mailman and inhabitant should fail", assignmentResult);
    }
}