import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Area northDistrict;
    private Area eastDistrict;
    private Area westDistrict;
    private Area centralDistrict;
    private Area southDistrict;
    private Area downtown;
    private Area midtown;
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
        // Initialize all areas
        northDistrict = new Area();
        northDistrict.setName("NorthDistrict");
        
        eastDistrict = new Area();
        eastDistrict.setName("EastDistrict");
        
        westDistrict = new Area();
        westDistrict.setName("WestDistrict");
        
        centralDistrict = new Area();
        centralDistrict.setName("CentralDistrict");
        
        southDistrict = new Area();
        southDistrict.setName("SouthDistrict");
        
        downtown = new Area();
        downtown.setName("Downtown");
        
        midtown = new Area();
        midtown.setName("Midtown");
        
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
        john.addAssignedArea(northDistrict);
        
        // Add Inhabitant "Alice" to NorthDistrict
        alice.setArea(northDistrict);
        northDistrict.addInhabitant(alice);
        
        // Create Registered Letter "Letter1" for Alice
        letter1.setAddressee(alice);
        letter1.setArea(northDistrict);
        
        // Action: Assign John to deliver Letter1
        boolean result = letter1.assignMailman(john);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("Letter should be assigned to John", john, letter1.getAssignedMailman());
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        eastDistrict.addMailman(mike);
        mike.addAssignedArea(eastDistrict);
        
        // Add Inhabitant "Bob" to WestDistrict
        bob.setArea(westDistrict);
        westDistrict.addInhabitant(bob);
        
        // Create RegisteredParcel "Parcel1" for Bob
        parcel1.setAddressee(bob);
        parcel1.setArea(westDistrict);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = parcel1.assignMailman(mike);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman should not be assigned to parcel in different area", result);
        assertNull("Parcel should not have any assigned mailman", parcel1.getAssignedMailman());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        carol.setArea(centralDistrict);
        centralDistrict.addInhabitant(carol);
        
        // Create RegisteredLetter "Letter2" for Carol
        letter2.setAddressee(carol);
        letter2.setArea(centralDistrict);
        
        // Action: Assign Mailman "Peter" to Letter2
        // Note: Peter is created but NOT added to CentralDistrict
        boolean result = letter2.assignMailman(peter);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman should not be assigned", result);
        assertNull("Letter should not have any assigned mailman", letter2.getAssignedMailman());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        sarah.addAssignedArea(southDistrict);
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        // Note: Dave is created but NOT added to SouthDistrict
        parcel2.setAddressee(dave);
        parcel2.setArea(southDistrict);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = parcel2.assignMailman(sarah);
        
        // Expected Output: false (addressee doesn't exist in area)
        assertFalse("Mailman should not be assigned to mail for non-existent inhabitant", result);
        assertNull("Parcel should not have any assigned mailman", parcel2.getAssignedMailman());
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        // Add Mailman "Tom" and "Jerry" to Downtown
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        tom.addAssignedArea(downtown);
        jerry.addAssignedArea(downtown);
        
        // Add Inhabitant "Eve" to Downtown
        eve.setArea(downtown);
        downtown.addInhabitant(eve);
        
        // Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        letter3.setAddressee(eve);
        letter3.setArea(downtown);
        letter3.assignMailman(tom); // First assignment
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = letter3.assignMailman(jerry);
        
        // Expected Output 1: false (successful reassignment)
        // Correction: The test specification says "false" but the description says "successful reassignment"
        // Based on the method logic, reassignment should return false when mail is already assigned
        assertFalse("Should not reassign mail that is already assigned", result1);
        assertEquals("Letter should remain assigned to Tom", tom, letter3.getAssignedMailman());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // Add Inhabitant "Ieril" to Midtown
        ieril.setArea(midtown);
        midtown.addInhabitant(ieril);
        
        // Create RegisteredLetter "Letter4" for Ieril
        letter4.setAddressee(ieril);
        letter4.setArea(midtown);
        
        // Expected Output 2: false
        // Note: This part is unclear in the specification - testing assignment without mailman
        boolean result2 = letter4.assignMailman(null);
        assertFalse("Assignment without mailman should fail", result2);
    }
}