import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private GeographicalArea riverside;
    private GeographicalArea lakeside;
    private GeographicalArea downtown;
    private GeographicalArea hillside;
    private GeographicalArea beachfront;
    
    private Mailman ken;
    private Mailman amy;
    
    private Inhabitant linda;
    private Inhabitant paul;
    private Inhabitant becy;
    private Inhabitant victor;
    private Inhabitant rachel;
    
    private RegisteredMail letter6;
    private RegisteredMail letter7;
    private RegisteredMail parcel4;
    
    @Before
    public void setUp() {
        // Initialize geographical areas
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
        
        // Initialize mailmen
        ken = new Mailman();
        amy = new Mailman();
        
        // Initialize inhabitants
        linda = new Inhabitant();
        paul = new Inhabitant();
        becy = new Inhabitant();
        victor = new Inhabitant();
        rachel = new Inhabitant();
        
        // Initialize mail items
        letter6 = new Letter();
        letter7 = new Letter();
        parcel4 = new Parcel();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: "Add New Inhabitant to Area"
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding new inhabitant should return true", result);
        assertTrue("Riverside should contain Linda after addition", 
                   riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: "Remove Inhabitant with Assigned Mail"
        // SetUp:
        // 1. Add Mailman "Ken" to Lakeside
        lakeside.addMailman(ken);
        // 2. Add Inhabitant "Paul" to Lakeside
        lakeside.addInhabitant(paul);
        // 3. Create and assign Letter6 for Paul (Ken)
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup: Letter6 should be in allMails
        assertTrue("Letter6 should be assigned to Paul", 
                   lakeside.getAllMails().contains(letter6));
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing inhabitant with mail should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", 
                    lakeside.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted after Paul's removal", 
                    lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("First inhabitant addition should return true", result1);
        assertEquals("Downtown should have 1 inhabitant", 1, downtown.getInhabitants().size());
        
        // - Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Second inhabitant addition should return true", result2);
        assertEquals("Downtown should have 2 inhabitants", 2, downtown.getInhabitants().size());
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Inhabitant removal should return true", result3);
        assertEquals("Downtown should have 1 inhabitant after removal", 1, downtown.getInhabitants().size());
        assertFalse("Linda should be removed", downtown.getInhabitants().contains(linda));
        assertTrue("Becy should still be present", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
        assertTrue("Hillside inhabitants should remain empty", 
                   hillside.getInhabitants().isEmpty());
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: "Remove Inhabitant with Multiple Mail Items"
        // SetUp:
        // 1. Add Mailman "Amy" to Beachfront
        beachfront.addMailman(amy);
        // 2. Add Inhabitant "Rachel" to Beachfront
        beachfront.addInhabitant(rachel);
        // 3. Create and assign:
        //    - Letter7 for Rachel (Amy)
        //    - Parcel4 for Rachel (Amy)
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup: Both mail items should be in allMails
        assertEquals("Beachfront should have 2 mail items", 2, beachfront.getAllMails().size());
        assertTrue("Letter7 should be assigned", beachfront.getAllMails().contains(letter7));
        assertTrue("Parcel4 should be assigned", beachfront.getAllMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing inhabitant with multiple mail items should return true", result);
        assertFalse("Rachel should be removed from inhabitants", 
                    beachfront.getInhabitants().contains(rachel));
        assertTrue("All mail items for Rachel should be deleted", 
                   beachfront.getAllMails().isEmpty());
    }
}