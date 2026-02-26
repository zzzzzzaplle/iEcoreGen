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
        // Create geographical areas
        northDistrict = new GeographicalArea();
        eastDistrict = new GeographicalArea();
        westDistrict = new GeographicalArea();
        centralDistrict = new GeographicalArea();
        southDistrict = new GeographicalArea();
        downtown = new GeographicalArea();
        
        // Create mailmen
        john = new Mailman();
        mike = new Mailman();
        peter = new Mailman();
        sarah = new Mailman();
        tom = new Mailman();
        jerry = new Mailman();
        
        // Create inhabitants
        alice = new Inhabitant();
        bob = new Inhabitant();
        carol = new Inhabitant();
        dave = new Inhabitant();
        eve = new Inhabitant();
        ieril = new Inhabitant();
        
        // Create mail items
        letter1 = new Letter();
        parcel1 = new Parcel();
        letter2 = new Letter();
        parcel2 = new Parcel();
        letter3 = new Letter();
        letter4 = new Letter();
    }

    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // Set up Test Case 1: "Assign Mailman to Letter in Same Area"
        northDistrict.addMailman(john);
        northDistrict.addInhabitant(alice);
        letter1.setAddressee(alice);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to deliver letter", result);
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // Set up Test Case 2: "Assign Mailman to Parcel in Different Area"
        eastDistrict.addMailman(mike);
        westDistrict.addInhabitant(bob);
        parcel1.setAddressee(bob);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman from different area should not be assigned", result);
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // Set up Test Case 3: "Assign Non-existent Mailman"
        centralDistrict.addInhabitant(carol);
        letter2.setAddressee(carol);
        
        // Action: Assign Mailman "Peter" to Letter2 (Peter not added to centralDistrict)
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman should not be assigned", result);
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // Set up Test Case 4: "Assign Mailman to Mail for Non-existent Inhabitant"
        southDistrict.addMailman(sarah);
        parcel2.setAddressee(dave); // Dave not added to southDistrict
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mail for non-existent inhabitant should not be assigned", result);
    }

    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // Set up Test Case 5: "Reassign Mailman to Already Assigned Mail"
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        downtown.addInhabitant(eve);
        letter3.setAddressee(eve);
        
        // First assignment: Tom to Letter3
        downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Already assigned mail should not be reassigned", result1);
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // Note: This test case seems incomplete in specification. Assuming we need to test assignment to non-existent area
        boolean result2 = downtown.assignRegisteredMailDeliver(tom, ieril, letter4);
        
        // Expected Output 2: false (inhabitant not in area)
        assertFalse("Mail for non-existent inhabitant should not be assigned", result2);
    }
}