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
        john = new Mailman("M001", "John");
        mike = new Mailman("M002", "Mike");
        peter = new Mailman("M003", "Peter");
        sarah = new Mailman("M004", "Sarah");
        tom = new Mailman("M005", "Tom");
        jerry = new Mailman("M006", "Jerry");

        // Initialize inhabitants
        alice = new Inhabitant("I001", "Alice");
        bob = new Inhabitant("I002", "Bob");
        carol = new Inhabitant("I003", "Carol");
        dave = new Inhabitant("I004", "Dave");
        eve = new Inhabitant("I005", "Eve");

        // Initialize mail items
        letter1 = new Letter("L001");
        parcel1 = new Parcel("P001");
        letter2 = new Letter("L002");
        parcel2 = new Parcel("P002");
        letter3 = new Letter("L003");
        letter4 = new Letter("L004");
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
        northDistrict.addRegisteredMail(letter1);

        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);

        // Expected Output: true (successful assignment)
        assertTrue(result);
        assertEquals(john, letter1.getCarrier());
        assertEquals(alice, letter1.getAddressee());
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
        westDistrict.addRegisteredMail(parcel1);

        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);

        // Expected Output: false (mailman not in same area)
        assertFalse(result);
        assertNull(parcel1.getCarrier());
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        // Create RegisteredLetter "Letter2" for Carol
        letter2.setAddressee(carol);
        centralDistrict.addRegisteredMail(letter2);

        // Action: Assign Mailman "Peter" to Letter2
        // Note: Peter exists but is not added to CentralDistrict
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);

        // Expected Output: false (mailman not in area)
        assertFalse(result);
        assertNull(letter2.getCarrier());
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        // Note: Dave exists but is not added to SouthDistrict
        parcel2.setAddressee(dave);
        southDistrict.addRegisteredMail(parcel2);

        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);

        // Expected Output: false (addressee doesn't exist in area)
        assertFalse(result);
        assertNull(parcel2.getCarrier());
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
        downtown.addRegisteredMail(letter3);
        downtown.assignRegisteredMailDeliver(tom, eve, letter3);

        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);

        // Expected Output 1: false (successful reassignment)
        assertFalse(result1);
        assertEquals(tom, letter3.getCarrier()); // Should remain assigned to Tom

        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // Note: This part seems incorrect in specification - creating new area "Midtown" but no inhabitant "Ieril" defined
        // Based on the expected output "false", we'll interpret this as testing assignment failure
        Inhabitant ieril = new Inhabitant("I006", "Ieril");
        GeographicalArea midtown = new GeographicalArea();
        letter4.setAddressee(ieril);
        midtown.addRegisteredMail(letter4);
        
        // Try to assign any mailman (none exist in midtown)
        boolean result2 = midtown.assignRegisteredMailDeliver(null, ieril, letter4);

        // Expected Output 2: false
        assertFalse(result2);
    }

}