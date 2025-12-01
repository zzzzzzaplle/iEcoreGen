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
    }

    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict", add Mailman "John", add Inhabitant "Alice"
        northDistrict.addMailman(john);
        northDistrict.addInhabitant(alice);
        
        // Create Registered Letter "Letter1" for Alice
        Letter letter1 = new Letter(null, alice);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Assignment should succeed when mailman and inhabitant are in same area", result);
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalAreas "EastDistrict" and "WestDistrict"
        eastDistrict.addMailman(mike);
        westDistrict.addInhabitant(bob);
        
        // Create RegisteredParcel "Parcel1" for Bob
        Parcel parcel1 = new Parcel(null, bob);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = eastDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Assignment should fail when mailman and inhabitant are in different areas", result);
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        centralDistrict.addInhabitant(carol);
        // Note: Peter is created but NOT added to CentralDistrict
        
        // Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = new Letter(null, carol);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the area", result);
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        southDistrict.addMailman(sarah);
        // Note: Dave is created but NOT added to SouthDistrict
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        Parcel parcel2 = new Parcel(null, dave);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Assignment should fail when inhabitant is not in the area", result);
    }

    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        downtown.addInhabitant(eve);
        
        // Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        Letter letter3 = new Letter(null, eve);
        downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment should be false since mail is already assigned)
        assertFalse("Reassignment should fail when mail is already assigned to a mailman", result1);
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // Note: The test specification seems to have a typo - creating Midtown area and Ieril inhabitant
        GeographicalArea midtown = new GeographicalArea();
        Inhabitant ieril = new Inhabitant("I006", "Ieril");
        midtown.addInhabitant(ieril);
        
        Letter letter4 = new Letter(null, ieril);
        
        // Since no mailman is added to Midtown, assignment should fail
        boolean result2 = midtown.assignRegisteredMailDeliver(jerry, ieril, letter4);
        
        // Expected Output 2: false
        assertFalse("Assignment should fail when mailman is not in the area", result2);
    }
}