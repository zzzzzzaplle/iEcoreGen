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
    
    private Mailman john;
    private Mailman mike;
    private Mailman sarah;
    private Mailman tom;
    private Mailman jerry;
    private Mailman peter;
    
    private Inhabitant alice;
    private Inhabitant bob;
    private Inhabitant carol;
    private Inhabitant dave;
    private Inhabitant eve;
    private Inhabitant ieril;
    
    @Before
    public void setUp() {
        // Initialize all geographical areas
        northDistrict = new GeographicalArea();
        eastDistrict = new GeographicalArea();
        westDistrict = new GeographicalArea();
        centralDistrict = new GeographicalArea();
        southDistrict = new GeographicalArea();
        downtown = new GeographicalArea();
        
        // Initialize all mailmen
        john = new Mailman();
        mike = new Mailman();
        sarah = new Mailman();
        tom = new Mailman();
        jerry = new Mailman();
        peter = new Mailman();
        
        // Initialize all inhabitants
        alice = new Inhabitant();
        bob = new Inhabitant();
        carol = new Inhabitant();
        dave = new Inhabitant();
        eve = new Inhabitant();
        ieril = new Inhabitant();
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // Set up NorthDistrict with John and Alice
        northDistrict.addMailman(john);
        northDistrict.addInhabitant(alice);
        Letter letter1 = new Letter();
        
        // Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Verify successful assignment
        assertTrue("John should be able to deliver Letter1 in same area", result);
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // Set up EastDistrict with Mike and WestDistrict with Bob
        eastDistrict.addMailman(mike);
        westDistrict.addInhabitant(bob);
        Parcel parcel1 = new Parcel();
        
        // Assign Mike to deliver Parcel1 to Bob in different area
        boolean result = eastDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Verify assignment fails due to different areas
        assertFalse("Mike should not be able to deliver to Bob in different area", result);
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // Set up CentralDistrict with Carol but Peter is not added
        centralDistrict.addInhabitant(carol);
        Letter letter2 = new Letter();
        
        // Assign non-existent Peter to deliver Letter2
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Verify assignment fails due to non-existent mailman
        assertFalse("Peter should not be able to deliver as he's not in area", result);
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // Set up SouthDistrict with Sarah but Dave is not added
        southDistrict.addMailman(sarah);
        Parcel parcel2 = new Parcel();
        
        // Assign Sarah to deliver Parcel2 to non-existent Dave
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Verify assignment fails due to non-existent inhabitant
        assertFalse("Sarah should not be able to deliver to non-existent Dave", result);
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // Set up Downtown with Tom, Jerry and Eve
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        downtown.addInhabitant(eve);
        Letter letter3 = new Letter();
        
        // First assignment: Tom delivers Letter3
        boolean result1 = downtown.assignRegisteredMailDeliver(tom, eve, letter3);
        assertTrue("Tom should be able to deliver Letter3", result1);
        
        // Attempt reassignment: Jerry delivers already assigned Letter3
        boolean result2 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Verify reassignment fails
        assertFalse("Jerry should not be able to reassign already assigned mail", result2);
        
        // Additional test: Create new area and test assignment
        GeographicalArea midtown = new GeographicalArea();
        Inhabitant ieril = new Inhabitant();
        midtown.addInhabitant(ieril);
        Letter letter4 = new Letter();
        
        // Assign without mailman in area
        boolean result3 = midtown.assignRegisteredMailDeliver(jerry, ieril, letter4);
        assertFalse("Assignment should fail without mailman in area", result3);
    }
}