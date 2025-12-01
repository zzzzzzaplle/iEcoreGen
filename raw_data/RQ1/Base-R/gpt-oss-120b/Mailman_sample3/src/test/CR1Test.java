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
        // Initialize all geographical areas
        northDistrict = new GeographicalArea();
        northDistrict.setName("NorthDistrict");
        
        eastDistrict = new GeographicalArea();
        eastDistrict.setName("EastDistrict");
        
        westDistrict = new GeographicalArea();
        westDistrict.setName("WestDistrict");
        
        centralDistrict = new GeographicalArea();
        centralDistrict.setName("CentralDistrict");
        
        southDistrict = new GeographicalArea();
        southDistrict.setName("SouthDistrict");
        
        downtown = new GeographicalArea();
        downtown.setName("Downtown");
        
        midtown = new GeographicalArea();
        midtown.setName("Midtown");
        
        // Initialize all mailmen
        john = new Mailman();
        john.setId("J001");
        john.setName("John");
        
        mike = new Mailman();
        mike.setId("M001");
        mike.setName("Mike");
        
        peter = new Mailman();
        peter.setId("P001");
        peter.setName("Peter");
        
        sarah = new Mailman();
        sarah.setId("S001");
        sarah.setName("Sarah");
        
        tom = new Mailman();
        tom.setId("T001");
        tom.setName("Tom");
        
        jerry = new Mailman();
        jerry.setId("J002");
        jerry.setName("Jerry");
        
        // Initialize all inhabitants
        alice = new Inhabitant();
        alice.setId("A001");
        alice.setName("Alice");
        
        bob = new Inhabitant();
        bob.setId("B001");
        bob.setName("Bob");
        
        carol = new Inhabitant();
        carol.setId("C001");
        carol.setName("Carol");
        
        dave = new Inhabitant();
        dave.setId("D001");
        dave.setName("Dave");
        
        eve = new Inhabitant();
        eve.setId("E001");
        eve.setName("Eve");
        
        ieril = new Inhabitant();
        ieril.setId("I001");
        ieril.setName("Ieril");
        
        // Initialize all mail items
        letter1 = new Letter();
        letter1.setTrackingNumber("L001");
        
        parcel1 = new Parcel();
        parcel1.setTrackingNumber("P001");
        
        letter2 = new Letter();
        letter2.setTrackingNumber("L002");
        
        parcel2 = new Parcel();
        parcel2.setTrackingNumber("P002");
        
        letter3 = new Letter();
        letter3.setTrackingNumber("L003");
        
        letter4 = new Letter();
        letter4.setTrackingNumber("L004");
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
        northDistrict.addRegisteredMail(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignMail(letter1, john);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("Letter should be assigned to John", john, letter1.getAssignedMailman());
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
        westDistrict.addRegisteredMail(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignMail(parcel1, mike);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman from different area should not be assigned", result);
        assertNull("Parcel should not be assigned to any mailman", parcel1.getAssignedMailman());
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Add Inhabitant "Carol" to CentralDistrict
        centralDistrict.addInhabitant(carol);
        
        // Create RegisteredLetter "Letter2" for Carol
        letter2.setAddressee(carol);
        centralDistrict.addRegisteredMail(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignMail(letter2, peter);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman in area should not be assigned", result);
        assertNull("Letter should not be assigned to any mailman", letter2.getAssignedMailman());
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Add Mailman "Sarah" to SouthDistrict
        southDistrict.addMailman(sarah);
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        parcel2.setAddressee(dave); // Dave is not added to SouthDistrict
        // Note: parcel2 is not added to SouthDistrict since addressee doesn't exist there
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignMail(parcel2, sarah);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mail for non-existent inhabitant should not be assignable", result);
        assertNull("Parcel should not be assigned to any mailman", parcel2.getAssignedMailman());
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
        downtown.addRegisteredMail(letter3);
        downtown.assignMail(letter3, tom);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignMail(letter3, jerry);
        
        // Expected Output 1: false (successful reassignment)
        // Correction: The specification says "false" but based on the method logic, 
        // it should return false because mail is already assigned
        assertFalse("Already assigned mail should not be reassigned", result1);
        assertEquals("Letter should remain assigned to Tom", tom, letter3.getAssignedMailman());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        midtown.addInhabitant(ieril);
        letter4.setAddressee(ieril);
        midtown.addRegisteredMail(letter4);
        
        // Expected Output 2: false
        // This action doesn't involve assignment, so we test that letter4 has no assignment
        assertNull("New letter should not have any mailman assigned", letter4.getAssignedMailman());
    }
}