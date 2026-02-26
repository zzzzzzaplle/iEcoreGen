import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        
        // Initialize all mailmen
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
        
        // Initialize all inhabitants
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
        alice.setArea(northDistrict);
        northDistrict.addInhabitant(alice);
        
        // Create Registered Letter "Letter1" for Alice
        letter1.setAddressee(alice);
        northDistrict.getRegisteredMails().add(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignMailToMailman(letter1, john, alice);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("Mailman should be assigned to the letter", john, letter1.getMailman());
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        eastDistrict.addMailman(mike);
        
        // Add Inhabitant "Bob" to WestDistrict
        bob.setArea(westDistrict);
        westDistrict.addInhabitant(bob);
        
        // Create RegisteredParcel "Parcel1" for Bob
        parcel1.setAddressee(bob);
        westDistrict.getRegisteredMails().add(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignMailToMailman(parcel1, mike, bob);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman from different area should not be assigned", result);
        assertNull("Mailman should not be assigned to the parcel", parcel1.getMailman());
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        carol.setArea(centralDistrict);
        centralDistrict.addInhabitant(carol);
        
        // Create RegisteredLetter "Letter2" for Carol
        letter2.setAddressee(carol);
        centralDistrict.getRegisteredMails().add(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        // Note: Peter is not added to centralDistrict's mailmen list
        boolean result = centralDistrict.assignMailToMailman(letter2, peter, carol);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman should not be assigned", result);
        assertNull("Mailman should not be assigned to the letter", letter2.getMailman());
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        // Note: Dave is not added to southDistrict's inhabitants list
        parcel2.setAddressee(dave);
        southDistrict.getRegisteredMails().add(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignMailToMailman(parcel2, sarah, dave);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mailman should not be assigned for non-existent inhabitant", result);
        assertNull("Mailman should not be assigned to the parcel", parcel2.getMailman());
    }

    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        // Add Mailman "Tom" and "Jerry" to Downtown
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        
        // Add Inhabitant "Eve" to Downtown
        eve.setArea(downtown);
        downtown.addInhabitant(eve);
        
        // Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        letter3.setAddressee(eve);
        downtown.getRegisteredMails().add(letter3);
        letter3.setMailman(tom); // Pre-assign to Tom
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignMailToMailman(letter3, jerry, eve);
        
        // Expected Output 1: false (successful reassignment - but specification says false)
        assertFalse("Should not be able to reassign already assigned mail", result1);
        assertEquals("Mail should remain assigned to original mailman", tom, letter3.getMailman());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        ieril.setArea(midtown);
        midtown.addInhabitant(ieril);
        letter4.setAddressee(ieril);
        midtown.getRegisteredMails().add(letter4);
        
        // Try to assign without adding mailman to midtown
        boolean result2 = midtown.assignMailToMailman(letter4, john, ieril);
        
        // Expected Output 2: false
        assertFalse("Should not be able to assign without mailman in area", result2);
        assertNull("Mailman should not be assigned without proper setup", letter4.getMailman());
    }
}