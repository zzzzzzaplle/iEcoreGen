import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private MailDeliveryManager manager;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        GeographicalArea riverside = new GeographicalArea();
        riverside.setName("Riverside");
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        boolean result = manager.manageInhabitant(linda, riverside, true);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding new inhabitant should return true", result);
        assertTrue("Inhabitant should be added to geographical area", 
                   riverside.getInhabitants().contains(linda));
        assertEquals("Inhabitant's geographical area should be set", 
                     riverside, linda.getGeographicalArea());
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        GeographicalArea lakeside = new GeographicalArea();
        lakeside.setName("Lakeside");
        
        // Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        ken.setName("Ken");
        ken.setGeographicalArea(lakeside);
        lakeside.addMailman(ken);
        
        // Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        paul.setGeographicalArea(lakeside);
        lakeside.addInhabitant(paul);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setAddressee(paul);
        letter6.assignMailman(ken);
        lakeside.addRegisteredMail(letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = manager.manageInhabitant(paul, lakeside, false);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing inhabitant with assigned mail should return true", result);
        assertFalse("Inhabitant should be removed from geographical area", 
                    lakeside.getInhabitants().contains(paul));
        assertFalse("Assigned mail should be deleted", 
                    lakeside.getRegisteredMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = new GeographicalArea();
        downtown.setName("Downtown");
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        boolean result1 = manager.manageInhabitant(linda, downtown, true);
        assertTrue("First addition should return true", result1);
        assertTrue("Linda should be in downtown", downtown.getInhabitants().contains(linda));
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        boolean result2 = manager.manageInhabitant(becy, downtown, true);
        assertTrue("Second addition should return true", result2);
        assertTrue("Becy should be in downtown", downtown.getInhabitants().contains(becy));
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = manager.manageInhabitant(linda, downtown, false);
        assertTrue("Removal should return true", result3);
        assertFalse("Linda should be removed from downtown", downtown.getInhabitants().contains(linda));
        assertTrue("Becy should still be in downtown", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        GeographicalArea hillside = new GeographicalArea();
        hillside.setName("Hillside");
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        boolean result = manager.manageInhabitant(victor, hillside, false);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        GeographicalArea beachfront = new GeographicalArea();
        beachfront.setName("Beachfront");
        
        // Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setName("Amy");
        amy.setGeographicalArea(beachfront);
        beachfront.addMailman(amy);
        
        // Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        rachel.setGeographicalArea(beachfront);
        beachfront.addInhabitant(rachel);
        
        // Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setAddressee(rachel);
        letter7.assignMailman(amy);
        beachfront.addRegisteredMail(letter7);
        
        // Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setAddressee(rachel);
        parcel4.assignMailman(amy);
        beachfront.addRegisteredMail(parcel4);
        
        // Verify initial state
        assertEquals("Should have 2 registered mails initially", 2, beachfront.getRegisteredMails().size());
        assertTrue("Letter7 should be in registered mails", beachfront.getRegisteredMails().contains(letter7));
        assertTrue("Parcel4 should be in registered mails", beachfront.getRegisteredMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = manager.manageInhabitant(rachel, beachfront, false);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing inhabitant with multiple mail items should return true", result);
        assertFalse("Rachel should be removed from geographical area", 
                    beachfront.getInhabitants().contains(rachel));
        assertTrue("Both assigned mail items should be deleted", 
                   beachfront.getRegisteredMails().isEmpty());
    }
}