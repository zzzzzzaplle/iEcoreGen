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
        // Initialize all objects for test cases
        northDistrict = new GeographicalArea();
        eastDistrict = new GeographicalArea();
        westDistrict = new GeographicalArea();
        centralDistrict = new GeographicalArea();
        southDistrict = new GeographicalArea();
        downtown = new GeographicalArea();
        
        john = new Mailman();
        mike = new Mailman();
        peter = new Mailman();
        sarah = new Mailman();
        tom = new Mailman();
        jerry = new Mailman();
        
        alice = new Inhabitant();
        bob = new Inhabitant();
        carol = new Inhabitant();
        dave = new Inhabitant();
        eve = new Inhabitant();
        ieril = new Inhabitant();
        
        letter1 = new Letter();
        parcel1 = new Parcel();
        letter2 = new Letter();
        parcel2 = new Parcel();
        letter3 = new Letter();
        letter4 = new Letter();
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp
        northDistrict.addMailman(john);
        northDistrict.addInhabitant(alice);
        letter1.setAddressee(alice);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp
        eastDistrict.addMailman(mike);
        westDistrict.addInhabitant(bob);
        parcel1.setAddressee(bob);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = eastDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Should return false when mailman is not in same area as addressee", result);
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp
        centralDistrict.addInhabitant(carol);
        letter2.setAddressee(carol);
        
        // Action: Assign Mailman "Peter" to Letter2 (Peter not added to any area)
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Should return false when mailman is not in the geographical area", result);
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Note: Dave is not added to SouthDistrict
        southDistrict.addMailman(sarah);
        parcel2.setAddressee(dave);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Should return false when addressee is not in the geographical area", result);
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        downtown.addInhabitant(eve);
        letter3.setAddressee(eve);
        
        // First assign Tom to Letter3
        downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        // Note: The test specification says "Expected Output 1: false" but the description says "successful reassignment"
        // This seems contradictory. Based on the method implementation, it should return false
        // because the mail is already assigned to a mailman
        assertFalse("Should return false when trying to reassign already assigned mail", result1);
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // This part seems to be testing a different scenario but is included in the same test case
        // Since Midtown is not created, and Ieril is not added to any area, this should also return false
        letter4.setAddressee(ieril);
        boolean result2 = downtown.assignRegisteredMailDeliver(tom, ieril, letter4);
        
        // Expected Output 2: false
        assertFalse("Should return false when addressee is not in the area", result2);
    }
}