import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private MailManagementSystem mailSystem;
    
    @Before
    public void setUp() {
        mailSystem = new MailManagementSystem();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        GeographicalArea riverside = new GeographicalArea("Riverside");
        mailSystem.addGeographicalArea(riverside);
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant("Linda");
        boolean result = mailSystem.addInhabitantToArea("Riverside", linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        GeographicalArea lakeside = new GeographicalArea("Lakeside");
        mailSystem.addGeographicalArea(lakeside);
        
        // Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman("Ken");
        lakeside.addMailman(ken);
        
        // Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant("Paul");
        lakeside.addInhabitant(paul);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter("Letter6", paul);
        paul.addRegisteredMail(letter6);
        lakeside.assignMailToMailman(letter6, ken);
        
        // Action: Remove Paul from Lakeside
        boolean result = mailSystem.removeInhabitantFromArea("Lakeside", paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", lakeside.getInhabitants().contains(paul));
        assertNull("Letter6 should have assigned mailman set to null", letter6.getAssignedMailman());
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = new GeographicalArea("Downtown");
        mailSystem.addGeographicalArea(downtown);
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant("Linda");
        boolean result1 = mailSystem.addInhabitantToArea("Downtown", linda);
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant("Becy");
        boolean result2 = mailSystem.addInhabitantToArea("Downtown", becy);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = mailSystem.removeInhabitantFromArea("Downtown", linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertFalse("Downtown should not contain Linda after removal", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        GeographicalArea hillside = new GeographicalArea("Hillside");
        mailSystem.addGeographicalArea(hillside);
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant("Victor");
        boolean result = mailSystem.removeInhabitantFromArea("Hillside", victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor should return false", result);
        assertEquals("Hillside should have 0 inhabitants", 0, hillside.getInhabitants().size());
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        GeographicalArea beachfront = new GeographicalArea("Beachfront");
        mailSystem.addGeographicalArea(beachfront);
        
        // Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman("Amy");
        beachfront.addMailman(amy);
        
        // Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant("Rachel");
        beachfront.addInhabitant(rachel);
        
        // Create and assign: Letter7 for Rachel (Amy)
        Letter letter7 = new Letter("Letter7", rachel);
        rachel.addRegisteredMail(letter7);
        beachfront.assignMailToMailman(letter7, amy);
        
        // Create and assign: Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel("Parcel4", rachel);
        rachel.addRegisteredMail(parcel4);
        beachfront.assignMailToMailman(parcel4, amy);
        
        // Verify setup: both mail items should have Amy assigned
        assertEquals("Letter7 should be assigned to Amy", amy, letter7.getAssignedMailman());
        assertEquals("Parcel4 should be assigned to Amy", amy, parcel4.getAssignedMailman());
        
        // Action: Remove Rachel from Beachfront
        boolean result = mailSystem.removeInhabitantFromArea("Beachfront", rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", beachfront.getInhabitants().contains(rachel));
        assertNull("Letter7 should have assigned mailman set to null", letter7.getAssignedMailman());
        assertNull("Parcel4 should have assigned mailman set to null", parcel4.getAssignedMailman());
    }
}