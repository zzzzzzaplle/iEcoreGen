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
        ken.setId("M001");
        ken.setName("Ken");
        
        amy = new Mailman();
        amy.setId("M002");
        amy.setName("Amy");
        
        // Initialize inhabitants
        linda = new Inhabitant();
        linda.setId("I001");
        linda.setName("Linda");
        
        paul = new Inhabitant();
        paul.setId("I002");
        paul.setName("Paul");
        
        becy = new Inhabitant();
        becy.setId("I003");
        becy.setName("Becy");
        
        victor = new Inhabitant();
        victor.setId("I004");
        victor.setName("Victor");
        
        rachel = new Inhabitant();
        rachel.setId("I005");
        rachel.setName("Rachel");
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda after addition", riverside.getInhabitants().contains(linda));
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
        
        // Verify setup is correct
        assertTrue("Lakeside should contain Paul", lakeside.getInhabitants().contains(paul));
        assertNotNull("Letter6 should be assigned to Paul", letter6.getAddressee());
        assertEquals("Letter6 should be assigned to Paul", paul, letter6.getAddressee());
        assertNotNull("Letter6 should have carrier Ken", letter6.getCarrier());
        assertEquals("Letter6 should have carrier Ken", ken, letter6.getCarrier());
        assertTrue("Lakeside should contain letter6", lakeside.getAllMails().contains(letter6));
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", lakeside.getInhabitants().contains(paul));
        assertFalse("Letter6 should be removed from Lakeside after Paul's removal", lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Downtown should contain Linda after addition", downtown.getInhabitants().contains(linda));
        
        // - Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Downtown should contain Becy after addition", downtown.getInhabitants().contains(becy));
        
        // Verify both inhabitants are present
        assertEquals("Downtown should have 2 inhabitants", 2, downtown.getInhabitants().size());
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertFalse("Downtown should not contain Linda after removal", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy after Linda's removal", downtown.getInhabitants().contains(becy));
        assertEquals("Downtown should have 1 inhabitant after removal", 1, downtown.getInhabitants().size());
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor from Hillside should return false", result);
        assertEquals("Hillside should have 0 inhabitants", 0, hillside.getInhabitants().size());
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
        
        // Verify setup is correct
        assertTrue("Beachfront should contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertNotNull("Letter7 should be assigned to Rachel", letter7.getAddressee());
        assertEquals("Letter7 should be assigned to Rachel", rachel, letter7.getAddressee());
        assertNotNull("Parcel4 should be assigned to Rachel", parcel4.getAddressee());
        assertEquals("Parcel4 should be assigned to Rachel", rachel, parcel4.getAddressee());
        assertTrue("Beachfront should contain letter7", beachfront.getAllMails().contains(letter7));
        assertTrue("Beachfront should contain parcel4", beachfront.getAllMails().contains(parcel4));
        assertEquals("Beachfront should have 2 mail items", 2, beachfront.getAllMails().size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", beachfront.getInhabitants().contains(rachel));
        assertFalse("Letter7 should be removed from Beachfront after Rachel's removal", beachfront.getAllMails().contains(letter7));
        assertFalse("Parcel4 should be removed from Beachfront after Rachel's removal", beachfront.getAllMails().contains(parcel4));
        assertEquals("Beachfront should have 0 mail items after Rachel's removal", 0, beachfront.getAllMails().size());
    }
}