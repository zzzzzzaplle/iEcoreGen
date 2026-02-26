import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private GeographicalArea riverside;
    private GeographicalArea lakeside;
    private GeographicalArea downtown;
    private GeographicalArea hillside;
    private GeographicalArea beachfront;
    
    @Before
    public void setUp() {
        // Initialize geographical areas for each test
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: "Add New Inhabitant to Area"
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant("Linda");
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: "Remove Inhabitant with Assigned Mail"
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman("Ken");
        lakeside.addMailman(ken);
        
        // 3. Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant("Paul");
        lakeside.addInhabitant(paul);
        
        // 4. Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup: Paul should have assigned mail
        assertNotNull("Paul should have assigned mail", lakeside.listRegisteredMailWithInhabitant(paul));
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", lakeside.getInhabitants().contains(paul));
        assertNull("Paul's mail should be removed", lakeside.listRegisteredMailWithInhabitant(paul));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        // SetUp: Create GeographicalArea "Downtown"
        
        // Action & Expected Output:
        // - Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant("Linda");
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // - Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant("Becy");
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertFalse("Downtown should not contain Linda after removal", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        // SetUp: Create GeographicalArea "Hillside"
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant("Victor");
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor from Hillside should return false", result);
        assertFalse("Hillside should not contain Victor", hillside.getInhabitants().contains(victor));
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: "Remove Inhabitant with Multiple Mail Items"
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman("Amy");
        beachfront.addMailman(amy);
        
        // 3. Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant("Rachel");
        beachfront.addInhabitant(rachel);
        
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        //    - Parcel4 for Rachel (Amy)
        Letter letter7 = new Letter();
        Parcel parcel4 = new Parcel();
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup: Rachel should have multiple assigned mails
        assertNotNull("Rachel should have assigned mails", beachfront.listRegisteredMailWithInhabitant(rachel));
        assertEquals("Rachel should have 2 mails", 2, beachfront.listRegisteredMailWithInhabitant(rachel).size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", beachfront.getInhabitants().contains(rachel));
        assertNull("Rachel's mails should be removed", beachfront.listRegisteredMailWithInhabitant(rachel));
    }
}