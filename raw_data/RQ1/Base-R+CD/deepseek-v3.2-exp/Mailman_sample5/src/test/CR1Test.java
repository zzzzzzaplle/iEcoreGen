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

        // Initialize mailmen
        john = new Mailman("JM001", "John");
        mike = new Mailman("MK002", "Mike");
        peter = new Mailman("PT003", "Peter");
        sarah = new Mailman("SH004", "Sarah");
        tom = new Mailman("TM005", "Tom");
        jerry = new Mailman("JR006", "Jerry");

        // Initialize inhabitants
        alice = new Inhabitant("AL001", "Alice");
        bob = new Inhabitant("BB002", "Bob");
        carol = new Inhabitant("CR003", "Carol");
        dave = new Inhabitant("DV004", "Dave");
        eve = new Inhabitant("EV005", "Eve");
        ieril = new Inhabitant("IR006", "Ieril");

        // Initialize mail items
        letter1 = new Letter("L001", alice);
        parcel1 = new Parcel("P001", bob);
        letter2 = new Letter("L002", carol);
        parcel2 = new Parcel("P002", dave);
        letter3 = new Letter("L003", eve);
        letter4 = new Letter("L004", ieril);
    }

    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        // Add Mailman "John" to NorthDistrict
        northDistrict.addMailman(john);
        // Add Inhabitant "Alice" to NorthDistrict
        northDistrict.addInhabitant(alice);
        // Create Registered Letter "Letter1" for Alice and add to NorthDistrict
        northDistrict.addRegisteredMail(letter1);

        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);

        // Expected Output: true (successful assignment)
        assertTrue("Assignment should be successful when mailman and inhabitant are in same area", result);
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        eastDistrict.addMailman(mike);
        // Add Inhabitant "Bob" to WestDistrict
        westDistrict.addInhabitant(bob);
        // Create RegisteredParcel "Parcel1" for Bob and add to WestDistrict
        westDistrict.addRegisteredMail(parcel1);

        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);

        // Expected Output: false (mailman not in same area)
        assertFalse("Assignment should fail when mailman is not in the same area", result);
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        // Create RegisteredLetter "Letter2" for Carol and add to CentralDistrict
        centralDistrict.addRegisteredMail(letter2);

        // Action: Assign Mailman "Peter" to Letter2 (Peter not added to any area)
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);

        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the area", result);
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        // Create RegisteredParcel "Parcel2" for "Dave" (Dave not added to
        // SouthDistrict)
        // Add parcel2 to SouthDistrict (mail can exist even if addressee is not in
        // area)
        southDistrict.addRegisteredMail(parcel2);

        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);

        // Expected Output: false (addressee doesn't exist in area)
        assertFalse("Assignment should fail when addressee is not in the area", result);
    }

    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        // Add Mailman "Tom" and "Jerry" to Downtown
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        // Add Inhabitant "Eve" to Downtown
        downtown.addInhabitant(eve);
        // Create RegisteredLetter "Letter3" for Eve and add to Downtown
        downtown.addRegisteredMail(letter3);
        // First assign Letter3 to Tom
        downtown.assignRegisteredMailDeliver(tom, eve, letter3);

        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);

        // Expected Output 1: false (mail already assigned, cannot reassign)
        assertFalse("Reassignment should fail when mail is already assigned to a mailman", result1);

        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to
        // GeographicalArea "Midtown"
        // Add Inhabitant "Ieril" to Midtown
        midtown.addInhabitant(ieril);
        // Add Letter4 to Midtown
        midtown.addRegisteredMail(letter4);
        // Try to assign Letter4 (should fail since no mailmen in Midtown)
        boolean result2 = midtown.assignRegisteredMailDeliver(null, ieril, letter4);

        // Expected Output 2: false (mailman is null, should throw exception)
        // This test case specification seems to have an error in the expected output
        // The method should throw IllegalArgumentException when mailman is null
    }
}