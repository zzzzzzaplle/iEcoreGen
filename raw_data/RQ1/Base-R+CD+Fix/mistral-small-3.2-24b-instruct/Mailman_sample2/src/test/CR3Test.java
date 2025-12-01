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
        ken.setId("KEN001");
        ken.setName("Ken");
        
        amy = new Mailman();
        amy.setId("AMY001");
        amy.setName("Amy");
        
        // Initialize inhabitants
        linda = new Inhabitant();
        linda.setId("LIN001");
        linda.setName("Linda");
        
        paul = new Inhabitant();
        paul.setId("PAU001");
        paul.setName("Paul");
        
        becy = new Inhabitant();
        becy.setId("BEC001");
        becy.setName("Becy");
        
        victor = new Inhabitant();
        victor.setId("VIC001");
        victor.setName("Victor");
        
        rachel = new Inhabitant();
        rachel.setId("RAC001");
        rachel.setName("Rachel");
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding new inhabitant Linda to Riverside should return true", result);
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        lakeside.addMailman(ken);
        // 3. Add Inhabitant "Paul" to Lakeside
        lakeside.addInhabitant(paul);
        // 4. Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing inhabitant Paul with assigned mail should return true", result);
        
        // Verify mail was deleted
        assertNull("Mail for removed inhabitant should be deleted", 
                  lakeside.listRegisteredMailWithInhabitant(paul));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Adding first inhabitant Linda should return true", result1);
        
        // - Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Adding second inhabitant Becy should return true", result2);
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Removing inhabitant Linda should return true", result3);
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        beachfront.addMailman(amy);
        // 3. Add Inhabitant "Rachel" to Beachfront
        beachfront.addInhabitant(rachel);
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        //    - Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing inhabitant with multiple mail items should return true", result);
        
        // Verify all mail was deleted
        assertNull("All mail for removed inhabitant should be deleted", 
                  beachfront.listRegisteredMailWithInhabitant(rachel));
    }
}