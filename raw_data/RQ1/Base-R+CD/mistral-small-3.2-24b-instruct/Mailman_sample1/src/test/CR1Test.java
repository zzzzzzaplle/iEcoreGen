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
        // Initialize all geographical areas
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
        // SetUp: Create GeographicalArea "NorthDistrict", add Mailman "John", add Inhabitant "Alice", create Registered Letter "Letter1"
        northDistrict.addMailman(john);
        northDistrict.addInhabitant(alice);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict", add Mailman "Mike" to EastDistrict, add Inhabitant "Bob" to WestDistrict
        eastDistrict.addMailman(mike);
        westDistrict.addInhabitant(bob);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = eastDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman should not be assigned to parcel in different area", result);
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", add Inhabitant "Carol"
        centralDistrict.addInhabitant(carol);
        
        // Action: Assign Mailman "Peter" (not added to area) to Letter2
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman should not be assigned", result);
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", add Mailman "Sarah"
        southDistrict.addMailman(sarah);
        
        // Action: Assign Sarah to deliver Parcel2 for non-existent "Dave"
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mail should not be assigned to non-existent inhabitant", result);
    }

    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown", add Mailman "Tom" and "Jerry", add Inhabitant "Eve"
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        downtown.addInhabitant(eve);
        
        // Action 1: Assign Letter3 to Tom first
        boolean firstAssignment = downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        assertTrue("First assignment should be successful", firstAssignment);
        
        // Action 2: Reassign Letter3 to Jerry
        boolean reassignment = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Should not be able to reassign already assigned mail", reassignment);
        
        // Additional test: Create new letter for non-existent inhabitant in different area
        Inhabitant ieril = new Inhabitant();
        ieril.setId("I006");
        ieril.setName("Ieril");
        
        GeographicalArea midtown = new GeographicalArea();
        boolean result = midtown.assignRegisteredMailDeliver(jerry, ieril, letter4);
        
        // Expected Output 2: false
        assertFalse("Should return false for non-existent inhabitant in different area", result);
    }
}