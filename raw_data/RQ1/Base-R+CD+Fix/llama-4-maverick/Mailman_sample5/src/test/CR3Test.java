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
        // Test Case 1: "Add New Inhabitant to Area"
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        // Expected Output: true (successful addition)
        
        boolean result = riverside.addInhabitant(linda);
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda after addition", riverside.getInhabitants().contains(linda));
    }

    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: "Remove Inhabitant with Assigned Mail"
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        // 3. Add Inhabitant "Paul" to Lakeside
        // 4. Create and assign Letter6 for Paul (Ken)
        // Action: Remove Paul from Lakeside
        // Expected Output: true (Paul removed, Letter6 deleted)
        
        lakeside.addMailman(ken);
        lakeside.addInhabitant(paul);
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup is correct
        assertTrue("Lakeside should contain Paul before removal", lakeside.getInhabitants().contains(paul));
        assertTrue("Lakeside should contain Letter6 before removal", lakeside.getAllMails().contains(letter6));
        
        boolean result = lakeside.removeInhabitant(paul);
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", lakeside.getInhabitants().contains(paul));
        assertFalse("Lakeside should not contain Letter6 after Paul's removal", lakeside.getAllMails().contains(letter6));
    }

    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        // SetUp: Create GeographicalArea "Downtown"
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        // - Add new Inhabitant "Becy" to Downtown, true
        // - Remove Inhabitant "Linda" to Downtown, true
        
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Downtown should contain Linda after addition", downtown.getInhabitants().contains(linda));
        
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Downtown should contain Becy after addition", downtown.getInhabitants().contains(becy));
        
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertFalse("Downtown should not contain Linda after removal", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy after Linda's removal", downtown.getInhabitants().contains(becy));
    }

    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        // SetUp: Create GeographicalArea "Hillside"
        // Action: Remove non-existent "Victor" from Hillside
        // Expected Output: false (inhabitant doesn't exist)
        
        boolean result = hillside.removeInhabitant(victor);
        assertFalse("Removing non-existent Victor from Hillside should return false", result);
        assertFalse("Hillside should not contain Victor", hillside.getInhabitants().contains(victor));
    }

    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: "Remove Inhabitant with Multiple Mail Items"
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        // 3. Add Inhabitant "Rachel" to Beachfront
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        //    - Parcel4 for Rachel (Amy)
        // Action: Remove Rachel from Beachfront
        // Expected Output: true (Rachel removed, both mail items deleted)
        
        beachfront.addMailman(amy);
        beachfront.addInhabitant(rachel);
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup is correct
        assertTrue("Beachfront should contain Rachel before removal", beachfront.getInhabitants().contains(rachel));
        assertTrue("Beachfront should contain Letter7 before removal", beachfront.getAllMails().contains(letter7));
        assertTrue("Beachfront should contain Parcel4 before removal", beachfront.getAllMails().contains(parcel4));
        
        boolean result = beachfront.removeInhabitant(rachel);
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", beachfront.getInhabitants().contains(rachel));
        assertFalse("Beachfront should not contain Letter7 after Rachel's removal", beachfront.getAllMails().contains(letter7));
        assertFalse("Beachfront should not contain Parcel4 after Rachel's removal", beachfront.getAllMails().contains(parcel4));
    }
}