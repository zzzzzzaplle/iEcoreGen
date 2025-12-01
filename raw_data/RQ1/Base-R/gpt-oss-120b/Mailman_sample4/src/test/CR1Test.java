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
        northDistrict.setAreaId("NorthDistrict");
        
        eastDistrict = new GeographicalArea();
        eastDistrict.setAreaId("EastDistrict");
        
        westDistrict = new GeographicalArea();
        westDistrict.setAreaId("WestDistrict");
        
        centralDistrict = new GeographicalArea();
        centralDistrict.setAreaId("CentralDistrict");
        
        southDistrict = new GeographicalArea();
        southDistrict.setAreaId("SouthDistrict");
        
        downtown = new GeographicalArea();
        downtown.setAreaId("Downtown");
        
        midtown = new GeographicalArea();
        midtown.setAreaId("Midtown");
        
        // Initialize all mailmen
        john = new Mailman();
        john.setMailmanId("J001");
        john.setName("John");
        
        mike = new Mailman();
        mike.setMailmanId("M001");
        mike.setName("Mike");
        
        peter = new Mailman();
        peter.setMailmanId("P001");
        peter.setName("Peter");
        
        sarah = new Mailman();
        sarah.setMailmanId("S001");
        sarah.setName("Sarah");
        
        tom = new Mailman();
        tom.setMailmanId("T001");
        tom.setName("Tom");
        
        jerry = new Mailman();
        jerry.setMailmanId("J002");
        jerry.setName("Jerry");
        
        // Initialize all inhabitants
        alice = new Inhabitant();
        alice.setInhabitantId("A001");
        alice.setName("Alice");
        
        bob = new Inhabitant();
        bob.setInhabitantId("B001");
        bob.setName("Bob");
        
        carol = new Inhabitant();
        carol.setInhabitantId("C001");
        carol.setName("Carol");
        
        dave = new Inhabitant();
        dave.setInhabitantId("D001");
        dave.setName("Dave");
        
        eve = new Inhabitant();
        eve.setInhabitantId("E001");
        eve.setName("Eve");
        
        ieril = new Inhabitant();
        ieril.setInhabitantId("I001");
        ieril.setName("Ieril");
        
        // Initialize all mail items
        letter1 = new Letter();
        letter1.setMailId("L001");
        letter1.setContent("Test letter content 1");
        
        parcel1 = new Parcel();
        parcel1.setMailId("P001");
        parcel1.setWeightKg(2.5);
        parcel1.setDescription("Books");
        
        letter2 = new Letter();
        letter2.setMailId("L002");
        letter2.setContent("Test letter content 2");
        
        parcel2 = new Parcel();
        parcel2.setMailId("P002");
        parcel2.setWeightKg(1.8);
        parcel2.setDescription("Clothes");
        
        letter3 = new Letter();
        letter3.setMailId("L003");
        letter3.setContent("Test letter content 3");
        
        letter4 = new Letter();
        letter4.setMailId("L004");
        letter4.setContent("Test letter content 4");
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
        northDistrict.getRegisteredMails().add(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignMailToMailman(letter1, john);
        
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
        westDistrict.getRegisteredMails().add(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignMailToMailman(parcel1, mike);
        
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
        centralDistrict.getRegisteredMails().add(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignMailToMailman(letter2, peter);
        
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
        southDistrict.getRegisteredMails().add(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignMailToMailman(parcel2, sarah);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mail for non-existent inhabitant should not be assigned", result);
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
        downtown.getRegisteredMails().add(letter3);
        
        // First assignment should succeed
        boolean firstAssignment = downtown.assignMailToMailman(letter3, tom);
        assertTrue("First assignment should succeed", firstAssignment);
        assertEquals("Letter should be assigned to Tom", tom, letter3.getAssignedMailman());
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignMailToMailman(letter3, jerry);
        
        // Expected Output 1: false (successful reassignment) - Note: This appears to be a typo in the spec, should be false
        assertFalse("Reassignment of already assigned mail should fail", result1);
        assertEquals("Letter should remain assigned to Tom", tom, letter3.getAssignedMailman());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        midtown.addInhabitant(ieril);
        letter4.setAddressee(ieril);
        midtown.getRegisteredMails().add(letter4);
        
        // Try to assign without adding mailman to Midtown
        boolean result2 = midtown.assignMailToMailman(letter4, tom);
        
        // Expected Output 2: false
        assertFalse("Assignment without mailman in area should fail", result2);
        assertNull("Letter should not be assigned to any mailman", letter4.getAssignedMailman());
    }
}