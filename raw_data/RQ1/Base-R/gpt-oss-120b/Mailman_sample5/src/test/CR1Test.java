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
        northDistrict = new GeographicalArea("NorthDistrict");
        eastDistrict = new GeographicalArea("EastDistrict");
        westDistrict = new GeographicalArea("WestDistrict");
        centralDistrict = new GeographicalArea("CentralDistrict");
        southDistrict = new GeographicalArea("SouthDistrict");
        downtown = new GeographicalArea("Downtown");
        midtown = new GeographicalArea("Midtown");
        
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
        
        // Initialize mail items
        letter1 = new Letter("Letter1", alice, "Hello Alice");
        parcel1 = new Parcel("Parcel1", bob, 2.5);
        letter2 = new Letter("Letter2", carol, "Hello Carol");
        parcel2 = new Parcel("Parcel2", dave, 1.8);
        letter3 = new Letter("Letter3", eve, "Hello Eve");
        letter4 = new Letter("Letter4", ieril, "Hello Ieril");
    }

    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // Setup: Create GeographicalArea "NorthDistrict"
        // Add Mailman "John" to NorthDistrict
        northDistrict.addMailman(john);
        // Add Inhabitant "Alice" to NorthDistrict
        northDistrict.addInhabitant(alice);
        // Create Registered Letter "Letter1" for Alice
        // (letter1 already created in setUp with Alice as addressee)
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignMail(letter1, john);
        
        // Expected Output: true (successful assignment)
        assertTrue("Assignment should succeed when mailman and mail are in same area", result);
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // Setup: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        eastDistrict.addMailman(mike);
        // Add Inhabitant "Bob" to WestDistrict
        westDistrict.addInhabitant(bob);
        // Create RegisteredParcel "Parcel1" for Bob
        // (parcel1 already created in setUp with Bob as addressee)
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = eastDistrict.assignMail(parcel1, mike);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Assignment should fail when mailman and mail are in different areas", result);
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // Setup: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // (Peter exists but not added to CentralDistrict)
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        // Create RegisteredLetter "Letter2" for Carol
        // (letter2 already created in setUp with Carol as addressee)
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignMail(letter2, peter);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the area", result);
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // Setup: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // (Dave exists but not added to SouthDistrict)
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        // (parcel2 already created in setUp with Dave as addressee)
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignMail(parcel2, sarah);
        
        // Expected Output: false (addressee doesn't exist in area)
        assertFalse("Assignment should fail when addressee is not in the area", result);
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
        downtown.assignMail(letter3, tom);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignMail(letter3, jerry);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Reassignment should fail when mail is already assigned", result1);
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // Add Inhabitant "Ieril" to Midtown
        midtown.addInhabitant(ieril);
        // Expected Output 2: false (mailman not assigned yet)
        boolean result2 = midtown.assignMail(letter4, null);
        assertFalse("Assignment should fail when mailman is null", result2);
    }
}