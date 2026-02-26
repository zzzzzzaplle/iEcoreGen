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
    
    private Mailman ken;
    private Mailman amy;
    
    private Inhabitant linda;
    private Inhabitant paul;
    private Inhabitant becy;
    private Inhabitant victor;
    private Inhabitant rachel;
    
    private Letter letter6;
    private Letter letter7;
    private Parcel parcel4;
    
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
        // Setup: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Linda should be successfully added to Riverside", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Setup:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        lakeside.addMailman(ken);
        
        // 3. Add Inhabitant "Paul" to Lakeside
        lakeside.addInhabitant(paul);
        
        // 4. Create and assign Letter6 for Paul (Ken)
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup: Paul should have assigned mail
        List<RegisteredMail> paulsMail = lakeside.listRegisteredMailForInhabitant(paul);
        assertNotNull("Paul should have mail assigned", paulsMail);
        assertEquals("Paul should have exactly 1 mail item", 1, paulsMail.size());
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Paul should be successfully removed from Lakeside", result);
        assertFalse("Lakeside should not contain Paul", lakeside.getInhabitants().contains(paul));
        
        // Verify that Paul's mail was deleted
        List<RegisteredMail> remainingMail = lakeside.listRegisteredMailForInhabitant(paul);
        assertNull("No mail should remain for Paul after removal", remainingMail);
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Setup: Create GeographicalArea "Downtown"
        
        // Action & Expected Output:
        // - Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Linda should be successfully added to Downtown", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitant().contains(linda));
        
        // - Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Becy should be successfully added to Downtown", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitant().contains(becy));
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Linda should be successfully removed from Downtown", result3);
        assertFalse("Downtown should not contain Linda after removal", downtown.getInhabitant().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitant().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Setup: Create GeographicalArea "Hillside"
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor should return false", result);
        assertFalse("Hillside should not contain Victor", hillside.getInhabitants().contains(victor));
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Setup:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        beachfront.addMailman(amy);
        
        // 3. Add Inhabitant "Rachel" to Beachfront
        beachfront.addInhabitant(rachel);
        
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        //    - Parcel4 for Rachel (Amy)
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup: Rachel should have two mail items assigned
        List<RegisteredMail> rachelsMail = beachfront.listRegisteredMailForInhabitant(rachel);
        assertNotNull("Rachel should have mail assigned", rachelsMail);
        assertEquals("Rachel should have exactly 2 mail items", 2, rachelsMail.size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Rachel should be successfully removed from Beachfront", result);
        assertFalse("Beachfront should not contain Rachel", beachfront.getInhabitants().contains(rachel));
        
        // Verify that both of Rachel's mail items were deleted
        List<RegisteredMail> remainingMail = beachfront.listRegisteredMailForInhabitant(rachel);
        assertNull("No mail should remain for Rachel after removal", remainingMail);
    }
}