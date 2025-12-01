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
        ieril = new Inhabitant("I006", "Ieril");
        
        // Initialize mail items
        letter1 = new Letter("L001", null, alice, 50.0);
        parcel1 = new Parcel("P001", null, bob, 1000.0);
        letter2 = new Letter("L002", null, carol, 75.0);
        parcel2 = new Parcel("P002", null, dave, 2000.0);
        letter3 = new Letter("L003", tom, eve, 60.0);
        letter4 = new Letter("L004", null, ieril, 80.0);
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict", add Mailman "John", add Inhabitant "Alice", create Registered Letter "Letter1"
        northDistrict.addMailman(john);
        northDistrict.addInhabitant(alice);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Assignment should be successful when mailman and inhabitant are in same area", result);
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict", add Mailman "Mike" to EastDistrict, add Inhabitant "Bob" to WestDistrict
        eastDistrict.addMailman(mike);
        westDistrict.addInhabitant(bob);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = eastDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Assignment should fail when mailman and inhabitant are in different areas", result);
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", add Inhabitant "Carol", create RegisteredLetter "Letter2"
        centralDistrict.addInhabitant(carol);
        
        // Action: Assign Mailman "Peter" to Letter2 (Peter not added to CentralDistrict)
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the area", result);
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", add Mailman "Sarah", create RegisteredParcel "Parcel2" for non-existent "Dave"
        southDistrict.addMailman(sarah);
        // Note: Dave is not added to SouthDistrict
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Assignment should fail when inhabitant is not in the area", result);
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown", add Mailman "Tom" and "Jerry", add Inhabitant "Eve", create RegisteredLetter "Letter3" for Eve assigned to Tom
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        downtown.addInhabitant(eve);
        downtown.getAllMails().add(letter3); // Add pre-assigned mail to area
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Reassignment should fail when mail is already assigned to a mailman", result1);
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        GeographicalArea midtown = new GeographicalArea();
        midtown.addInhabitant(ieril);
        
        // Try to assign without adding a mailman first
        Mailman nonExistentMailman = new Mailman("M999", "NonExistent");
        boolean result2 = midtown.assignRegisteredMailDeliver(nonExistentMailman, ieril, letter4);
        
        // Expected Output 2: false
        assertFalse("Assignment should fail when mailman is not in the area", result2);
    }
    

}