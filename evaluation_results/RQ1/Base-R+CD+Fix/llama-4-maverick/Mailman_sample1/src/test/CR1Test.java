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
        // Initialize all geographical areas
        northDistrict = new GeographicalArea();
        eastDistrict = new GeographicalArea();
        westDistrict = new GeographicalArea();
        centralDistrict = new GeographicalArea();
        southDistrict = new GeographicalArea();
        downtown = new GeographicalArea();
        midtown = new GeographicalArea();
        
        // Initialize all mailmen
        john = new Mailman();
        mike = new Mailman();
        peter = new Mailman();
        sarah = new Mailman();
        tom = new Mailman();
        jerry = new Mailman();
        
        // Initialize all inhabitants
        alice = new Inhabitant();
        bob = new Inhabitant();
        carol = new Inhabitant();
        dave = new Inhabitant();
        eve = new Inhabitant();
        ieril = new Inhabitant();
        
        // Initialize all mail items
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
        assertTrue("John should be assigned to deliver Letter1 to Alice in NorthDistrict", result);
        assertEquals("Letter1 carrier should be John", john, letter1.getCarrier());
        assertEquals("Letter1 addressee should be Alice", alice, letter1.getAddressee());
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
        assertFalse("Mike should not be assigned to deliver Parcel1 to Bob in different areas", result);
        assertNull("Parcel1 carrier should remain null", parcel1.getCarrier());
        assertEquals("Parcel1 addressee should remain Bob", bob, parcel1.getAddressee());
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
        assertFalse("Peter should not be assigned to deliver Letter2 as he is not in CentralDistrict", result);
        assertNull("Letter2 carrier should remain null", letter2.getCarrier());
        assertEquals("Letter2 addressee should remain Carol", carol, letter2.getAddressee());
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
        assertFalse("Sarah should not be assigned to deliver Parcel2 to Dave as Dave is not in SouthDistrict", result);
        assertNull("Parcel2 carrier should remain null", parcel2.getCarrier());
        assertEquals("Parcel2 addressee should remain Dave", dave, parcel2.getAddressee());
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
        letter3.setCarrier(tom);
        downtown.getAllMails().add(letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Jerry should not be reassigned to Letter3 as it is already assigned to Tom", result1);
        assertEquals("Letter3 carrier should remain Tom", tom, letter3.getCarrier());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        letter4.setAddressee(ieril);
        midtown.getAllMails().add(letter4);
        // Attempt to assign any mailman (using tom) to Letter4 in Midtown
        boolean result2 = midtown.assignRegisteredMailDeliver(tom, ieril, letter4);
        
        // Expected Output 2: false
        assertFalse("Tom should not be assigned to Letter4 as he is not in Midtown and Ieril is not in Midtown", result2);
        assertNull("Letter4 carrier should remain null", letter4.getCarrier());
    }
}