import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private MailDeliveryManager manager;
    private GeographicalArea northDistrict;
    private GeographicalArea eastDistrict;
    private GeographicalArea westDistrict;
    private GeographicalArea centralDistrict;
    private GeographicalArea southDistrict;
    private GeographicalArea downtown;
    private GeographicalArea midtown;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
        
        // Initialize geographical areas
        northDistrict = new GeographicalArea("NorthDistrict");
        eastDistrict = new GeographicalArea("EastDistrict");
        westDistrict = new GeographicalArea("WestDistrict");
        centralDistrict = new GeographicalArea("CentralDistrict");
        southDistrict = new GeographicalArea("SouthDistrict");
        downtown = new GeographicalArea("Downtown");
        midtown = new GeographicalArea("Midtown");
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp:
        // 1. Create GeographicalArea "NorthDistrict".
        // 2. Add Mailman "John" to NorthDistrict.
        Mailman john = new Mailman("M001", "John");
        manager.addMailman(john, northDistrict);
        
        // 3. Add Inhabitant "Alice" to NorthDistrict.
        Inhabitant alice = new Inhabitant("I001", "Alice");
        manager.addInhabitant(alice, northDistrict);
        
        // 4. Create Registered Letter "Letter1" for Alice.
        Letter letter1 = new Letter("L001", alice);
        
        // Action: Assign John to deliver Letter1.
        boolean result = manager.assignMailman(letter1, john);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("Letter should be assigned to John", john, letter1.getAssignedMailman());
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp:
        // 1. Create GeographicalArea "EastDistrict" and "WestDistrict".
        // 2. Add Mailman "Mike" to EastDistrict.
        Mailman mike = new Mailman("M002", "Mike");
        manager.addMailman(mike, eastDistrict);
        
        // 3. Add Inhabitant "Bob" to WestDistrict.
        Inhabitant bob = new Inhabitant("I002", "Bob");
        manager.addInhabitant(bob, westDistrict);
        
        // 4. Create RegisteredParcel "Parcel1" for Bob.
        Parcel parcel1 = new Parcel("P001", bob);
        
        // Action: Assign Mike to deliver Parcel1.
        try {
            boolean result = manager.assignMailman(parcel1, mike);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected Output: false (mailman not in same area)
            assertEquals("Mailman and inhabitant do not belong to the same geographical area.", e.getMessage());
        }
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp:
        // 1. Create GeographicalArea "CentralDistrict", create Mailman "Peter".
        Mailman peter = new Mailman("M003", "Peter");
        // Note: Peter is NOT added to any area
        
        // 2. Add Inhabitant "Carol" to CentralDistrict.
        Inhabitant carol = new Inhabitant("I003", "Carol");
        manager.addInhabitant(carol, centralDistrict);
        
        // 3. Create RegisteredLetter "Letter2" for Carol.
        Letter letter2 = new Letter("L002", carol);
        
        // Action: Assign Mailman "Peter" to Letter2.
        try {
            boolean result = manager.assignMailman(letter2, peter);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected Output: false (mailman not in area)
            assertEquals("Mailman and inhabitant do not belong to the same geographical area.", e.getMessage());
        }
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp:
        // 1. Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        Inhabitant dave = new Inhabitant("I004", "Dave");
        // Note: Dave is NOT added to any area
        
        // 2. Add Mailman "Sarah" to SouthDistrict.
        Mailman sarah = new Mailman("M004", "Sarah");
        manager.addMailman(sarah, southDistrict);
        
        // 3. Create RegisteredParcel "Parcel2" for non-existent "Dave".
        Parcel parcel2 = new Parcel("P002", dave);
        
        // Action: Assign Sarah to deliver Parcel2.
        try {
            boolean result = manager.assignMailman(parcel2, sarah);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected Output: false (addressee doesn't exist)
            assertEquals("Mailman and inhabitant do not belong to the same geographical area.", e.getMessage());
        }
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp:
        // 1. Create GeographicalArea "Downtown".
        // 2. Add Mailman "Tom" and "Jerry" to Downtown.
        Mailman tom = new Mailman("M005", "Tom");
        Mailman jerry = new Mailman("M006", "Jerry");
        manager.addMailman(tom, downtown);
        manager.addMailman(jerry, downtown);
        
        // 3. Add Inhabitant "Eve" to Downtown.
        Inhabitant eve = new Inhabitant("I005", "Eve");
        manager.addInhabitant(eve, downtown);
        
        // 4. Create RegisteredLetter "Letter3" for Eve, assigned to Tom.
        Letter letter3 = new Letter("L003", eve);
        manager.assignMailman(letter3, tom);
        
        // Action 1: Reassign Letter3 to Jerry.
        try {
            boolean result = manager.assignMailman(letter3, jerry);
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // Expected Output 1: false (successful reassignment)
            assertEquals("Mail item is already assigned to a mailman.", e.getMessage());
        }
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown".
        Inhabitant ieril = new Inhabitant("I006", "Ieril");
        manager.addInhabitant(ieril, midtown);
        Letter letter4 = new Letter("L004", ieril);
        
        // Expected Output 2: false
        // Since there's no mailman in Midtown area, assignment should fail
        Mailman nonExistentMailman = new Mailman("M007", "NonExistent");
        try {
            boolean result = manager.assignMailman(letter4, nonExistentMailman);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Mailman and inhabitant do not belong to the same geographical area.", e.getMessage());
        }
    }
}