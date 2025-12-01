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
        alice.setAddress("123 North St");
        
        bob = new Inhabitant();
        bob.setName("Bob");
        bob.setId("I002");
        bob.setAddress("456 West St");
        
        carol = new Inhabitant();
        carol.setName("Carol");
        carol.setId("I003");
        carol.setAddress("789 Central St");
        
        dave = new Inhabitant();
        dave.setName("Dave");
        dave.setId("I004");
        dave.setAddress("321 South St");
        
        eve = new Inhabitant();
        eve.setName("Eve");
        eve.setId("I005");
        eve.setAddress("654 Downtown St");
        
        ieril = new Inhabitant();
        ieril.setName("Ieril");
        ieril.setId("I006");
        ieril.setAddress("987 Midtown St");
        
        // Initialize mail items
        letter1 = new Letter();
        letter1.setTrackingNumber("L001");
        letter1.setWeight(0.5);
        
        parcel1 = new Parcel();
        parcel1.setTrackingNumber("P001");
        parcel1.setWeight(2.5);
        parcel1.setDimensions(30.0);
        
        letter2 = new Letter();
        letter2.setTrackingNumber("L002");
        letter2.setWeight(0.3);
        
        parcel2 = new Parcel();
        parcel2.setTrackingNumber("P002");
        parcel2.setWeight(5.0);
        parcel2.setDimensions(50.0);
        
        letter3 = new Letter();
        letter3.setTrackingNumber("L003");
        letter3.setWeight(0.4);
        
        letter4 = new Letter();
        letter4.setTrackingNumber("L004");
        letter4.setWeight(0.6);
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // Setup: Create GeographicalArea "NorthDistrict"
        // Add Mailman "John" to NorthDistrict
        northDistrict.addMailman(john);
        
        // Add Inhabitant "Alice" to NorthDistrict
        northDistrict.addInhabitant(alice);
        
        // Create Registered Letter "Letter1" for Alice
        letter1.setAddressee(alice);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Assignment should be successful when mailman and inhabitant are in same area", result);
        assertEquals("Mail should be assigned to John", john, letter1.getCarrier());
        assertEquals("Mail should be addressed to Alice", alice, letter1.getAddressee());
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // Setup: Create GeographicalArea "EastDistrict" and "WestDistrict"
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
        assertNull("Mail should not be assigned to any carrier", parcel1.getCarrier());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // Setup: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        
        // Create RegisteredLetter "Letter2" for Carol
        letter2.setAddressee(carol);
        
        // Action: Assign Mailman "Peter" to Letter2 (Peter not added to CentralDistrict)
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the geographical area", result);
        assertNull("Mail should not be assigned to any carrier", letter2.getCarrier());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // Setup: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        // Note: Dave exists as an object but is not added to SouthDistrict
        parcel2.setAddressee(dave);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist in the area)
        assertFalse("Assignment should fail when inhabitant is not in the geographical area", result);
        assertNull("Mail should not be assigned to any carrier", parcel2.getCarrier());
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // Setup: Create GeographicalArea "Downtown"
        // Add Mailman "Tom" and "Jerry" to Downtown
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        
        // Add Inhabitant "Eve" to Downtown
        downtown.addInhabitant(eve);
        
        // Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        letter3.setAddressee(eve);
        boolean firstAssignment = downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        assertTrue("First assignment should be successful", firstAssignment);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment should fail)
        assertFalse("Reassignment should fail when mail is already assigned", result1);
        assertEquals("Mail should remain assigned to original carrier", tom, letter3.getCarrier());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // Note: "Midtown" geographical area is not created in setup, so we'll use a separate area
        GeographicalArea midtown = new GeographicalArea();
        midtown.addInhabitant(ieril);
        letter4.setAddressee(ieril);
        
        // Since no mailman is added to midtown, assignment should fail
        boolean result2 = midtown.assignRegisteredMailDeliver(jerry, ieril, letter4);
        
        // Expected Output 2: false
        assertFalse("Assignment should fail when mailman is not in the area", result2);
        assertNull("Mail should not be assigned to any carrier", letter4.getCarrier());
    }
}