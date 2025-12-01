import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private MailDeliveryManager manager;
    private GeographicalArea riverside;
    private GeographicalArea lakeside;
    private GeographicalArea downtown;
    private GeographicalArea hillside;
    private GeographicalArea beachfront;
    private Mailman ken;
    private Mailman amy;
    private Inhabitant paul;
    private Inhabitant linda;
    private Inhabitant becy;
    private Inhabitant victor;
    private Inhabitant rachel;
    private Letter letter6;
    private Letter letter7;
    private Parcel parcel4;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
        
        // Create geographical areas
        riverside = new GeographicalArea();
        riverside.setName("Riverside");
        
        lakeside = new GeographicalArea();
        lakeside.setName("Lakeside");
        
        downtown = new GeographicalArea();
        downtown.setName("Downtown");
        
        hillside = new GeographicalArea();
        hillside.setName("Hillside");
        
        beachfront = new GeographicalArea();
        beachfront.setName("Beachfront");
        
        // Create mailmen
        ken = new Mailman();
        ken.setName("Ken");
        
        amy = new Mailman();
        amy.setName("Amy");
        
        // Create inhabitants
        paul = new Inhabitant();
        paul.setName("Paul");
        
        linda = new Inhabitant();
        linda.setName("Linda");
        
        becy = new Inhabitant();
        becy.setName("Becy");
        
        victor = new Inhabitant();
        victor.setName("Victor");
        
        rachel = new Inhabitant();
        rachel.setName("Rachel");
        
        // Create mail items
        letter6 = new Letter();
        letter6.setContent("Letter 6 content");
        
        letter7 = new Letter();
        letter7.setContent("Letter 7 content");
        
        parcel4 = new Parcel();
        parcel4.setWeight(2.5);
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = manager.manageInhabitant(linda, riverside, true);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
        assertEquals("Linda's geographical area should be set to Riverside", riverside, linda.getGeographicalArea());
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        lakeside.addMailman(ken);
        ken.setGeographicalArea(lakeside);
        
        // 3. Add Inhabitant "Paul" to Lakeside
        lakeside.addInhabitant(paul);
        paul.setGeographicalArea(lakeside);
        
        // 4. Create and assign Letter6 for Paul (Ken)
        letter6.setAddressee(paul);
        letter6.setMailman(ken);
        lakeside.addRegisteredMail(letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = manager.manageInhabitant(paul, lakeside, false);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul", lakeside.getInhabitants().contains(paul));
        assertFalse("Letter6 should be removed from Lakeside", lakeside.getRegisteredMails().contains(letter6));
        assertNull("Paul's geographical area should be null", paul.getGeographicalArea());
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = manager.manageInhabitant(linda, downtown, true);
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // - Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = manager.manageInhabitant(becy, downtown, true);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = manager.manageInhabitant(linda, downtown, false);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertFalse("Downtown should not contain Linda", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = manager.manageInhabitant(victor, hillside, false);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor from Hillside should return false", result);
        assertFalse("Hillside should not contain Victor", hillside.getInhabitants().contains(victor));
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        beachfront.addMailman(amy);
        amy.setGeographicalArea(beachfront);
        
        // 3. Add Inhabitant "Rachel" to Beachfront
        beachfront.addInhabitant(rachel);
        rachel.setGeographicalArea(beachfront);
        
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        letter7.setAddressee(rachel);
        letter7.setMailman(amy);
        beachfront.addRegisteredMail(letter7);
        
        //    - Parcel4 for Rachel (Amy)
        parcel4.setAddressee(rachel);
        parcel4.setMailman(amy);
        beachfront.addRegisteredMail(parcel4);
        
        // Verify setup
        assertEquals("Beachfront should have 2 registered mails", 2, beachfront.getRegisteredMails().size());
        assertTrue("Beachfront should contain Letter7", beachfront.getRegisteredMails().contains(letter7));
        assertTrue("Beachfront should contain Parcel4", beachfront.getRegisteredMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = manager.manageInhabitant(rachel, beachfront, false);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertTrue("Both mail items should be removed from Beachfront", beachfront.getRegisteredMails().isEmpty());
        assertNull("Rachel's geographical area should be null", rachel.getGeographicalArea());
    }
}