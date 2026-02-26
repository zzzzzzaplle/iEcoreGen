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
        
        // Initialize all mailmen
        john = new Mailman("John");
        mike = new Mailman("Mike");
        peter = new Mailman("Peter");
        sarah = new Mailman("Sarah");
        tom = new Mailman("Tom");
        jerry = new Mailman("Jerry");
        
        // Initialize all inhabitants
        alice = new Inhabitant("Alice");
        bob = new Inhabitant("Bob");
        carol = new Inhabitant("Carol");
        dave = new Inhabitant("Dave");
        eve = new Inhabitant("Eve");
        ieril = new Inhabitant("Ieril");
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        // Add Mailman "John" to NorthDistrict
        northDistrict.addMailman(john);
        // Add Inhabitant "Alice" to NorthDistrict
        northDistrict.addInhabitant(alice);
        // Create Registered Letter "Letter1" for Alice
        Letter letter1 = new Letter(alice, "Hello Alice");
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("John should successfully assign letter to Alice in same area", result);
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        eastDistrict.addMailman(mike);
        // Add Inhabitant "Bob" to WestDistrict
        westDistrict.addInhabitant(bob);
        // Create RegisteredParcel "Parcel1" for Bob
        Parcel parcel1 = new Parcel(bob, 2.5);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mike should not be able to deliver parcel in different area", result);
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        // Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = new Letter(carol, "Hello Carol");
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Peter should not be able to deliver letter since he's not in the area", result);
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        Parcel parcel2 = new Parcel(dave, 1.8);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Sarah should not be able to deliver parcel to non-existent inhabitant", result);
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
        Letter letter3 = new Letter(eve, "Hello Eve");
        downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Should not be able to reassign already assigned mail", result1);
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        downtown.addInhabitant(ieril);
        Letter letter4 = new Letter(ieril, "Hello Ieril");
        boolean result2 = downtown.assignRegisteredMailDeliver(tom, ieril, letter4);
        
        // Expected Output 2: false
        assertFalse("Second assignment should fail due to test specification", result2);
    }
}