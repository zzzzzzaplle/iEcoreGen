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
        // Create geographical areas
        northDistrict = new GeographicalArea();
        eastDistrict = new GeographicalArea();
        westDistrict = new GeographicalArea();
        centralDistrict = new GeographicalArea();
        southDistrict = new GeographicalArea();
        downtown = new GeographicalArea();
        midtown = new GeographicalArea();
        
        // Create mailmen
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
        
        // Create inhabitants
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
        
        // Create registered mail items
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
        // Create Registered Letter "Letter1" for Alice and add to NorthDistrict
        letter1.setAddressee(alice);
        northDistrict.getAllMails().add(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to deliver letter in same area", result);
        assertEquals("Letter1 should be assigned to John", john, letter1.getCarrier());
        assertEquals("Letter1 should be addressed to Alice", alice, letter1.getAddressee());
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        eastDistrict.addMailman(mike);
        // Add Inhabitant "Bob" to WestDistrict
        westDistrict.addInhabitant(bob);
        // Create RegisteredParcel "Parcel1" for Bob and add to WestDistrict
        parcel1.setAddressee(bob);
        westDistrict.getAllMails().add(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman from different area should not be assigned", result);
        assertNull("Parcel1 should not have any carrier assigned", parcel1.getCarrier());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        // Create RegisteredLetter "Letter2" for Carol and add to CentralDistrict
        letter2.setAddressee(carol);
        centralDistrict.getAllMails().add(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman should not be assigned", result);
        assertNull("Letter2 should not have any carrier assigned", letter2.getCarrier());
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
        assertFalse("Mail for non-existent inhabitant should not be assigned", result);
        assertNull("Parcel2 should not have any carrier assigned", parcel2.getCarrier());
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        // Add Mailman "Tom" and "Jerry" to Downtown
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        // Add Inhabitant "Eve" to Downtown
        downtown.addInhabitant(eve);
        // Create RegisteredLetter "Letter3" for Eve, assigned to Tom and add to Downtown
        letter3.setAddressee(eve);
        letter3.setCarrier(tom);
        downtown.getAllMails().add(letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Already assigned mail should not be reassigned", result1);
        assertEquals("Letter3 should still be assigned to Tom", tom, letter3.getCarrier());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        midtown.addInhabitant(ieril);
        letter4.setAddressee(ieril);
        midtown.getAllMails().add(letter4);
        
        // Test assignment for Letter4
        boolean result2 = midtown.assignRegisteredMailDeliver(tom, ieril, letter4);
        
        // Expected Output 2: false (mailman not in Midtown area)
        assertFalse("Mailman from different area should not be assigned", result2);
        assertNull("Letter4 should not have any carrier assigned", letter4.getCarrier());
    }
}