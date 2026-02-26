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
        eastDistrict = new GeographicalArea();
        westDistrict = new GeographicalArea();
        centralDistrict = new GeographicalArea();
        southDistrict = new GeographicalArea();
        downtown = new GeographicalArea();
        midtown = new GeographicalArea();
        
        // Initialize mailmen
        john = new Mailman("J001", "John");
        mike = new Mailman("M001", "Mike");
        peter = new Mailman("P001", "Peter");
        sarah = new Mailman("S001", "Sarah");
        tom = new Mailman("T001", "Tom");
        jerry = new Mailman("J002", "Jerry");
        
        // Initialize inhabitants
        alice = new Inhabitant("A001", "Alice");
        bob = new Inhabitant("B001", "Bob");
        carol = new Inhabitant("C001", "Carol");
        dave = new Inhabitant("D001", "Dave");
        eve = new Inhabitant("E001", "Eve");
        ieril = new Inhabitant("I001", "Ieril");
        
        // Initialize mail items
        letter1 = new Letter(alice);
        parcel1 = new Parcel(bob);
        letter2 = new Letter(carol);
        parcel2 = new Parcel(dave);
        letter3 = new Letter(eve);
        letter4 = new Letter(ieril);
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
        assertTrue("Assignment should succeed when mailman, addressee and mail are in same area", result);
        assertEquals("Mail should be assigned to John", john, letter1.getCarrier());
        assertEquals("Addressee should be Alice", alice, letter1.getAddressee());
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
        assertNull("Mail should not be assigned to any carrier", parcel1.getCarrier());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict"
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        // Create RegisteredLetter "Letter2" for Carol and add to CentralDistrict
        centralDistrict.addRegisteredMail(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2 (Peter not added to CentralDistrict)
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the area", result);
        assertNull("Mail should not be assigned to any carrier", letter2.getCarrier());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        // Create RegisteredParcel "Parcel2" for non-existent "Dave" (Dave not added to SouthDistrict)
        southDistrict.addRegisteredMail(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Assignment should fail when addressee is not in the area", result);
        assertNull("Mail should not be assigned to any carrier", parcel2.getCarrier());
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
        downtown.addRegisteredMail(letter3);
        downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Reassignment should fail when mail is already assigned", result1);
        assertEquals("Mail should remain assigned to Tom", tom, letter3.getCarrier());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        midtown.addInhabitant(ieril);
        midtown.addRegisteredMail(letter4);
        
        // Expected Output 2: false (no mailman assigned yet)
        // This test case seems incomplete - we need to test assignment in Midtown
        // Since no mailman exists in Midtown, any assignment should fail
        Mailman nonExistentMailman = new Mailman("X001", "NonExistent");
        boolean result2 = midtown.assignRegisteredMailDeliver(nonExistentMailman, ieril, letter4);
        assertFalse("Assignment should fail when mailman doesn't exist in area", result2);
    }
}