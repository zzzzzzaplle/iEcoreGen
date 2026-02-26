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
        // Initialize all objects needed for tests
        northDistrict = new GeographicalArea();
        eastDistrict = new GeographicalArea();
        westDistrict = new GeographicalArea();
        centralDistrict = new GeographicalArea();
        southDistrict = new GeographicalArea();
        downtown = new GeographicalArea();
        
        john = new Mailman();
        mike = new Mailman();
        peter = new Mailman();
        sarah = new Mailman();
        tom = new Mailman();
        jerry = new Mailman();
        
        alice = new Inhabitant();
        bob = new Inhabitant();
        carol = new Inhabitant();
        dave = new Inhabitant();
        eve = new Inhabitant();
        
        letter1 = new Letter();
        parcel1 = new Parcel();
        letter2 = new Letter();
        parcel2 = new Parcel();
        letter3 = new Letter();
        letter4 = new Letter();
    }

    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict", add Mailman "John", add Inhabitant "Alice", create Registered Letter "Letter1" for Alice
        northDistrict.addMailman(john);
        northDistrict.addInhabitant(alice);
        letter1.setAddressee(alice);
        northDistrict.getAllMails().add(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue(result);
        assertEquals(john, letter1.getCarrier());
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict", add Mailman "Mike" to EastDistrict, add Inhabitant "Bob" to WestDistrict, create RegisteredParcel "Parcel1" for Bob
        eastDistrict.addMailman(mike);
        westDistrict.addInhabitant(bob);
        parcel1.setAddressee(bob);
        westDistrict.getAllMails().add(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse(result);
        assertNull(parcel1.getCarrier());
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter", add Inhabitant "Carol" to CentralDistrict, create RegisteredLetter "Letter2" for Carol
        centralDistrict.addInhabitant(carol);
        letter2.setAddressee(carol);
        centralDistrict.getAllMails().add(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2 (Peter not added to CentralDistrict)
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse(result);
        assertNull(letter2.getCarrier());
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave", add Mailman "Sarah" to SouthDistrict, create RegisteredParcel "Parcel2" for non-existent "Dave"
        southDistrict.addMailman(sarah);
        parcel2.setAddressee(dave); // Dave not added to SouthDistrict
        southDistrict.getAllMails().add(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse(result);
        assertNull(parcel2.getCarrier());
    }

    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown", add Mailman "Tom" and "Jerry" to Downtown, add Inhabitant "Eve" to Downtown, create RegisteredLetter "Letter3" for Eve, assigned to Tom
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        downtown.addInhabitant(eve);
        letter3.setAddressee(eve);
        downtown.getAllMails().add(letter3);
        
        // First assignment: Tom to Letter3
        boolean firstAssignment = downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        assertTrue(firstAssignment);
        assertEquals(tom, letter3.getCarrier());
        
        // Action 1: Reassign Letter3 to Jerry
        boolean reassignmentResult = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment) - Note: Specification says "false" but this seems contradictory
        // Based on the method logic, reassignment should fail because mail is already assigned
        assertFalse(reassignmentResult);
        assertEquals(tom, letter3.getCarrier()); // Carrier should remain Tom
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // This part seems incomplete in specification - creating a new area and testing assignment
        GeographicalArea midtown = new GeographicalArea();
        Inhabitant ieril = new Inhabitant();
        midtown.addInhabitant(ieril);
        letter4.setAddressee(ieril);
        midtown.getAllMails().add(letter4);
        
        // Try to assign a mailman not in midtown
        boolean assignmentResult = midtown.assignRegisteredMailDeliver(john, ieril, letter4);
        
        // Expected Output 2: false (mailman not in area)
        assertFalse(assignmentResult);
    }
}