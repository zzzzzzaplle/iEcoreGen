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
        john.setId("J001");
        
        mike = new Mailman();
        mike.setName("Mike");
        mike.setId("M001");
        
        peter = new Mailman();
        peter.setName("Peter");
        peter.setId("P001");
        
        sarah = new Mailman();
        sarah.setName("Sarah");
        sarah.setId("S001");
        
        tom = new Mailman();
        tom.setName("Tom");
        tom.setId("T001");
        
        jerry = new Mailman();
        jerry.setName("Jerry");
        jerry.setId("J002");
        
        // Initialize inhabitants
        alice = new Inhabitant();
        alice.setName("Alice");
        alice.setId("A001");
        
        bob = new Inhabitant();
        bob.setName("Bob");
        bob.setId("B001");
        
        carol = new Inhabitant();
        carol.setName("Carol");
        carol.setId("C001");
        
        dave = new Inhabitant();
        dave.setName("Dave");
        dave.setId("D001");
        
        eve = new Inhabitant();
        eve.setName("Eve");
        eve.setId("E001");
        
        ieril = new Inhabitant();
        ieril.setName("Ieril");
        ieril.setId("I001");
        
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
        // SetUp: Create GeographicalArea "NorthDistrict", add Mailman "John" and Inhabitant "Alice"
        northDistrict.addMailman(john);
        northDistrict.addInhabitant(alice);
        
        // SetUp: Create Registered Letter "Letter1" for Alice
        letter1.setAddressee(alice);
        northDistrict.addMail(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Assignment should succeed when mailman and inhabitant are in same area", result);
        assertEquals("Mail should be assigned to John", john, letter1.getCarrier());
        assertEquals("Mail should be addressed to Alice", alice, letter1.getAddressee());
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        eastDistrict.addMailman(mike);
        westDistrict.addInhabitant(bob);
        
        // SetUp: Create RegisteredParcel "Parcel1" for Bob
        parcel1.setAddressee(bob);
        westDistrict.addMail(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Assignment should fail when mailman is not in the same area", result);
        assertNull("Parcel should not have a carrier assigned", parcel1.getCarrier());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", add Inhabitant "Carol"
        centralDistrict.addInhabitant(carol);
        
        // SetUp: Create RegisteredLetter "Letter2" for Carol
        letter2.setAddressee(carol);
        centralDistrict.addMail(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2 (Peter not added to area)
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the area", result);
        assertNull("Letter should not have a carrier assigned", letter2.getCarrier());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", add Mailman "Sarah"
        southDistrict.addMailman(sarah);
        
        // SetUp: Create RegisteredParcel "Parcel2" for non-existent "Dave"
        parcel2.setAddressee(dave);
        southDistrict.addMail(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist in area)
        assertFalse("Assignment should fail when addressee is not in the area", result);
        assertNull("Parcel should not have a carrier assigned", parcel2.getCarrier());
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown", add Mailmen "Tom" and "Jerry", add Inhabitant "Eve"
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        downtown.addInhabitant(eve);
        
        // SetUp: Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        letter3.setAddressee(eve);
        letter3.setCarrier(tom);
        downtown.addMail(letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Reassignment should fail when mail already has a carrier", result1);
        assertEquals("Letter should still be assigned to Tom", tom, letter3.getCarrier());
        
        // SetUp: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        midtown.addInhabitant(ieril);
        letter4.setAddressee(ieril);
        midtown.addMail(letter4);
        
        // Action 2: Try to assign null mailman (not explicitly stated but implied by context)
        boolean result2 = midtown.assignRegisteredMailDeliver(null, ieril, letter4);
        
        // Expected Output 2: false (null mailman)
        assertFalse("Assignment should fail with null mailman", result2);
        assertNull("Letter should not have a carrier assigned", letter4.getCarrier());
    }
}