import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private GeographicalArea riverside;
    private GeographicalArea lakeside;
    private GeographicalArea downtown;
    private GeographicalArea hillside;
    private GeographicalArea beachfront;
    
    @Before
    public void setUp() {
        // Initialize geographical areas before each test
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: Add New Inhabitant to Area
        // SetUp: Create GeographicalArea "Riverside"
        Inhabitant linda = new Inhabitant("I001", "Linda");
        
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Should successfully add Linda to Riverside", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: Remove Inhabitant with Assigned Mail
        // SetUp: Create GeographicalArea "Lakeside"
        Mailman ken = new Mailman("M001", "Ken");
        Inhabitant paul = new Inhabitant("I002", "Paul");
        Letter letter6 = new Letter("L006", ken, paul, 25.5);
        
        // Add Mailman "Ken" to Lakeside
        lakeside.addMailman(ken);
        // Add Inhabitant "Paul" to Lakeside
        lakeside.addInhabitant(paul);
        // Create and assign Letter6 for Paul (Ken)
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup
        assertTrue("Lakeside should contain Ken", lakeside.getMailmen().contains(ken));
        assertTrue("Lakeside should contain Paul", lakeside.getInhabitants().contains(paul));
        assertNotNull("Letter6 should be assigned to Ken", lakeside.listRegisteredMail(ken));
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Should successfully remove Paul from Lakeside", result);
        assertFalse("Lakeside should not contain Paul anymore", lakeside.getInhabitants().contains(paul));
        assertNull("Letter6 should be deleted", lakeside.listRegisteredMail(ken));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: Add Multiple Inhabitants to Area
        // SetUp: Create GeographicalArea "Downtown"
        Inhabitant linda = new Inhabitant("I003", "Linda");
        Inhabitant becy = new Inhabitant("I004", "Becy");
        
        // Action & Expected Output: 
        // Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Should successfully add Linda to Downtown", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Should successfully add Becy to Downtown", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Should successfully remove Linda from Downtown", result3);
        assertFalse("Downtown should not contain Linda anymore", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: Remove Non-existent Inhabitant
        // SetUp: Create GeographicalArea "Hillside"
        Inhabitant victor = new Inhabitant("I005", "Victor");
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Should return false when removing non-existent inhabitant", result);
        assertTrue("Hillside inhabitants should remain empty", hillside.getInhabitants().isEmpty());
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: Remove Inhabitant with Multiple Mail Items
        // SetUp: Create GeographicalArea "Beachfront"
        Mailman amy = new Mailman("M002", "Amy");
        Inhabitant rachel = new Inhabitant("I006", "Rachel");
        Letter letter7 = new Letter("L007", amy, rachel, 30.0);
        Parcel parcel4 = new Parcel("P004", amy, rachel, 5000.0);
        
        // Add Mailman "Amy" to Beachfront
        beachfront.addMailman(amy);
        // Add Inhabitant "Rachel" to Beachfront
        beachfront.addInhabitant(rachel);
        // Create and assign Letter7 for Rachel (Amy)
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        // Create and assign Parcel4 for Rachel (Amy)
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup
        assertTrue("Beachfront should contain Amy", beachfront.getMailmen().contains(amy));
        assertTrue("Beachfront should contain Rachel", beachfront.getInhabitants().contains(rachel));
        List<RegisteredMail> amyMail = beachfront.listRegisteredMail(amy);
        assertNotNull("Amy should have assigned mail", amyMail);
        assertEquals("Amy should have 2 mail items", 2, amyMail.size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Should successfully remove Rachel from Beachfront", result);
        assertFalse("Beachfront should not contain Rachel anymore", beachfront.getInhabitants().contains(rachel));
        assertNull("Both mail items should be deleted", beachfront.listRegisteredMail(amy));
    }
    

}