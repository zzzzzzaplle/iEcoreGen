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
        northDistrict.setAreaCode("N001");
        northDistrict.setAreaName("NorthDistrict");
        
        eastDistrict = new GeographicalArea();
        eastDistrict.setAreaCode("E001");
        eastDistrict.setAreaName("EastDistrict");
        
        westDistrict = new GeographicalArea();
        westDistrict.setAreaCode("W001");
        westDistrict.setAreaName("WestDistrict");
        
        centralDistrict = new GeographicalArea();
        centralDistrict.setAreaCode("C001");
        centralDistrict.setAreaName("CentralDistrict");
        
        southDistrict = new GeographicalArea();
        southDistrict.setAreaCode("S001");
        southDistrict.setAreaName("SouthDistrict");
        
        downtown = new GeographicalArea();
        downtown.setAreaCode("D001");
        downtown.setAreaName("Downtown");
        
        midtown = new GeographicalArea();
        midtown.setAreaCode("M001");
        midtown.setAreaName("Midtown");
        
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
        alice.setAddress("123 North St");
        
        bob = new Inhabitant();
        bob.setId("I002");
        bob.setName("Bob");
        bob.setAddress("456 West St");
        
        carol = new Inhabitant();
        carol.setId("I003");
        carol.setName("Carol");
        carol.setAddress("789 Central St");
        
        dave = new Inhabitant();
        dave.setId("I004");
        dave.setName("Dave");
        dave.setAddress("321 South St");
        
        eve = new Inhabitant();
        eve.setId("I005");
        eve.setName("Eve");
        eve.setAddress("654 Downtown St");
        
        ieril = new Inhabitant();
        ieril.setId("I006");
        ieril.setName("Ieril");
        ieril.setAddress("987 Midtown St");
        
        // Initialize mail items
        letter1 = new Letter();
        letter1.setTrackingNumber("L001");
        letter1.setWeight(0.1);
        letter1.setUrgent(false);
        
        parcel1 = new Parcel();
        parcel1.setTrackingNumber("P001");
        parcel1.setWeight(2.5);
        parcel1.setDimensions(30.0);
        parcel1.setRequiresSignature(true);
        
        letter2 = new Letter();
        letter2.setTrackingNumber("L002");
        letter2.setWeight(0.2);
        letter2.setUrgent(true);
        
        parcel2 = new Parcel();
        parcel2.setTrackingNumber("P002");
        parcel2.setWeight(5.0);
        parcel2.setDimensions(50.0);
        parcel2.setRequiresSignature(false);
        
        letter3 = new Letter();
        letter3.setTrackingNumber("L003");
        letter3.setWeight(0.15);
        letter3.setUrgent(false);
        
        letter4 = new Letter();
        letter4.setTrackingNumber("L004");
        letter4.setWeight(0.25);
        letter4.setUrgent(true);
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
        assertTrue("Assignment should be successful when mailman and inhabitant are in same area", result);
        assertTrue("Mail should be marked as assigned", letter1.isAssigned());
        assertEquals("Mail carrier should be John", john, letter1.getCarrier());
        assertEquals("Mail addressee should be Alice", alice, letter1.getAddressee());
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
        assertFalse("Mail should not be assigned", parcel1.isAssigned());
        assertNull("Mail should not have a carrier", parcel1.getCarrier());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        
        // Create RegisteredLetter "Letter2" for Carol
        letter2.setAddressee(carol);
        
        // Action: Assign Mailman "Peter" to Letter2 (Peter not added to CentralDistrict)
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the area", result);
        assertFalse("Mail should not be assigned", letter2.isAssigned());
        assertNull("Mail should not have a carrier", letter2.getCarrier());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        // Note: Dave exists but is not added to SouthDistrict
        parcel2.setAddressee(dave);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist in area)
        assertFalse("Assignment should fail when inhabitant is not in the area", result);
        assertFalse("Mail should not be assigned", parcel2.isAssigned());
        assertNull("Mail should not have a carrier", parcel2.getCarrier());
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
        assertTrue("First assignment should be successful", firstAssignment);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Reassignment should fail when mail is already assigned", result1);
        assertEquals("Mail carrier should remain Tom", tom, letter3.getCarrier());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        midtown.addInhabitant(ieril);
        letter4.setAddressee(ieril);
        
        // Expected Output 2: false
        // This appears to be incomplete in the specification - testing assignment without mailman
        boolean result2 = midtown.assignRegisteredMailDeliver(null, ieril, letter4);
        assertFalse("Assignment should fail when mailman is null", result2);
    }
}