import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        // Initialize all objects needed for tests
        northDistrict = new GeographicalArea();
        eastDistrict = new GeographicalArea();
        westDistrict = new GeographicalArea();
        centralDistrict = new GeographicalArea();
        southDistrict = new GeographicalArea();
        downtown = new GeographicalArea();
        midtown = new GeographicalArea();
        
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
        assertTrue("Assignment should succeed when mailman and inhabitant are in same area", result);
        assertEquals("Mail carrier should be set to John", john, letter1.getCarrier());
        assertTrue("Letter should be added to deliveries", northDistrict.getDeliveries().contains(letter1));
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
        assertFalse("Assignment should fail when mailman and inhabitant are in different areas", result);
        assertNull("Mail carrier should not be set", parcel1.getCarrier());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp
        centralDistrict.addInhabitant(carol);
        letter2.setAddressee(carol);
        
        // Action: Assign Mailman "Peter" to Letter2 (but Peter is not added to any area)
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Assignment should fail when mailman is not in the area", result);
        assertNull("Mail carrier should not be set", letter2.getCarrier());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp
        southDistrict.addMailman(sarah);
        parcel2.setAddressee(dave); // Dave exists as object but is not added to SouthDistrict
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist in the area)
        assertFalse("Assignment should fail when inhabitant is not in the area", result);
        assertNull("Mail carrier should not be set", parcel2.getCarrier());
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        downtown.addInhabitant(eve);
        letter3.setAddressee(eve);
        
        // First assignment: Tom to Letter3
        boolean firstAssignment = downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        assertTrue("First assignment should succeed", firstAssignment);
        assertEquals("Letter3 should be assigned to Tom", tom, letter3.getCarrier());
        
        // Action 1: Reassign Letter3 to Jerry
        boolean reassignment = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Reassignment should fail when mail is already assigned", reassignment);
        assertEquals("Letter3 should still be assigned to Tom", tom, letter3.getCarrier());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        midtown.addInhabitant(ieril);
        letter4.setAddressee(ieril);
        
        // Expected Output 2: false (no mailman in Midtown area)
        boolean assignmentToMidtown = midtown.assignRegisteredMailDeliver(jerry, ieril, letter4);
        assertFalse("Assignment should fail when no mailman in the area", assignmentToMidtown);
    }
}