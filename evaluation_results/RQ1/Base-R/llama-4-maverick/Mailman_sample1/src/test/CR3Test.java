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
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
        
        // Create geographical areas
        riverside = new GeographicalArea();
        riverside.setName("Riverside");
        manager.addGeographicalArea(riverside);
        
        lakeside = new GeographicalArea();
        lakeside.setName("Lakeside");
        manager.addGeographicalArea(lakeside);
        
        downtown = new GeographicalArea();
        downtown.setName("Downtown");
        manager.addGeographicalArea(downtown);
        
        hillside = new GeographicalArea();
        hillside.setName("Hillside");
        manager.addGeographicalArea(hillside);
        
        beachfront = new GeographicalArea();
        beachfront.setName("Beachfront");
        manager.addGeographicalArea(beachfront);
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: "Add New Inhabitant to Area"
        
        // Create new inhabitant Linda
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        
        // Add Linda to Riverside
        boolean result = manager.addInhabitantToGeographicalArea(linda, riverside);
        
        // Verify addition was successful
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
        assertEquals("Linda's geographical area should be Riverside", riverside, linda.getGeographicalArea());
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: "Remove Inhabitant with Assigned Mail"
        
        // Add Mailman Ken to Lakeside
        Mailman ken = new Mailman();
        ken.setName("Ken");
        manager.addMailmanToGeographicalArea(ken, lakeside);
        
        // Add Inhabitant Paul to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        manager.addInhabitantToGeographicalArea(paul, lakeside);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setContent("Letter6 content");
        letter6.setAddressee(paul);
        letter6.setMailman(ken);
        lakeside.addRegisteredMail(letter6);
        
        // Verify initial state
        assertTrue("Lakeside should contain Paul", lakeside.getInhabitants().contains(paul));
        assertTrue("Lakeside should contain letter6", lakeside.getRegisteredMails().contains(letter6));
        
        // Remove Paul from Lakeside
        boolean result = manager.removeInhabitantFromGeographicalArea(paul);
        
        // Verify removal was successful and letter6 was deleted
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", lakeside.getInhabitants().contains(paul));
        assertFalse("Lakeside should not contain letter6 after removal", lakeside.getRegisteredMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        
        // Add new Inhabitant Linda to Downtown
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        boolean result1 = manager.addInhabitantToGeographicalArea(linda, downtown);
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // Add new Inhabitant Becy to Downtown
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        boolean result2 = manager.addInhabitantToGeographicalArea(becy, downtown);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // Remove Inhabitant Linda from Downtown
        boolean result3 = manager.removeInhabitantFromGeographicalArea(linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertFalse("Downtown should not contain Linda after removal", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        
        // Create non-existent inhabitant Victor
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        
        // Try to remove Victor from Hillside (who was never added)
        boolean result = manager.removeInhabitantFromGeographicalArea(victor);
        
        // Verify removal failed
        assertFalse("Removing non-existent Victor from Hillside should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: "Remove Inhabitant with Multiple Mail Items"
        
        // Add Mailman Amy to Beachfront
        Mailman amy = new Mailman();
        amy.setName("Amy");
        manager.addMailmanToGeographicalArea(amy, beachfront);
        
        // Add Inhabitant Rachel to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        manager.addInhabitantToGeographicalArea(rachel, beachfront);
        
        // Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setContent("Letter7 content");
        letter7.setAddressee(rachel);
        letter7.setMailman(amy);
        beachfront.addRegisteredMail(letter7);
        
        // Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setWeight(5.5);
        parcel4.setAddressee(rachel);
        parcel4.setMailman(amy);
        beachfront.addRegisteredMail(parcel4);
        
        // Verify initial state
        assertTrue("Beachfront should contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertTrue("Beachfront should contain letter7", beachfront.getRegisteredMails().contains(letter7));
        assertTrue("Beachfront should contain parcel4", beachfront.getRegisteredMails().contains(parcel4));
        
        // Remove Rachel from Beachfront
        boolean result = manager.removeInhabitantFromGeographicalArea(rachel);
        
        // Verify removal was successful and both mail items were deleted
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", beachfront.getInhabitants().contains(rachel));
        assertFalse("Beachfront should not contain letter7 after removal", beachfront.getRegisteredMails().contains(letter7));
        assertFalse("Beachfront should not contain parcel4 after removal", beachfront.getRegisteredMails().contains(parcel4));
    }
}