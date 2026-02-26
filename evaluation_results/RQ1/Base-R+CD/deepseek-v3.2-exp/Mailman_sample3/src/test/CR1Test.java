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
        // Initialize geographical areas
        northDistrict = new GeographicalArea();
        eastDistrict = new GeographicalArea();
        westDistrict = new GeographicalArea();
        centralDistrict = new GeographicalArea();
        southDistrict = new GeographicalArea();
        downtown = new GeographicalArea();
        midtown = new GeographicalArea();
        
        // Initialize mailmen
        john = new Mailman();
        john.setName("John");
        
        mike = new Mailman();
        mike.setName("Mike");
        
        peter = new Mailman();
        peter.setName("Peter");
        
        sarah = new Mailman();
        sarah.setName("Sarah");
        
        tom = new Mailman();
        tom.setName("Tom");
        
        jerry = new Mailman();
        jerry.setName("Jerry");
        
        // Initialize inhabitants
        alice = new Inhabitant();
        alice.setName("Alice");
        
        bob = new Inhabitant();
        bob.setName("Bob");
        
        carol = new Inhabitant();
        carol.setName("Carol");
        
        dave = new Inhabitant();
        dave.setName("Dave");
        
        eve = new Inhabitant();
        eve.setName("Eve");
        
        ieril = new Inhabitant();
        ieril.setName("Ieril");
        
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
        // SetUp: Create GeographicalArea "NorthDistrict"
        // Add Mailman "John" to NorthDistrict
        northDistrict.addMailman(john);
        
        // Add Inhabitant "Alice" to NorthDistrict
        northDistrict.addInhabitant(alice);
        
        // Create Registered Letter "Letter1" for Alice
        letter1.setAddressee(alice);
        northDistrict.getAllMails().add(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("John should be assigned as carrier", john, letter1.getCarrier());
        assertEquals("Alice should be the addressee", alice, letter1.getAddressee());
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
        westDistrict.getAllMails().add(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman from different area should not be assigned", result);
        assertNull("Carrier should remain null", parcel1.getCarrier());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        
        // Create RegisteredLetter "Letter2" for Carol
        letter2.setAddressee(carol);
        centralDistrict.getAllMails().add(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman should not be assigned", result);
        assertNull("Carrier should remain null", letter2.getCarrier());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        parcel2.setAddressee(dave);
        southDistrict.getAllMails().add(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mailman should not be assigned to non-existent inhabitant", result);
        assertNull("Carrier should remain null", parcel2.getCarrier());
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
        downtown.getAllMails().add(letter3);
        downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Should not be able to reassign already assigned mail", result1);
        assertEquals("Tom should remain as carrier", tom, letter3.getCarrier());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // Add Inhabitant "Ieril" to Midtown
        midtown.addInhabitant(ieril);
        
        // Create RegisteredLetter "Letter4" for Ieril
        letter4.setAddressee(ieril);
        midtown.getAllMails().add(letter4);
        
        // Try to assign without mailman in Midtown
        boolean result2 = midtown.assignRegisteredMailDeliver(null, ieril, letter4);
        
        // Expected Output 2: false (no mailman available)
        assertFalse("Should not be able to assign without mailman", result2);
        assertNull("Carrier should remain null", letter4.getCarrier());
    }
}