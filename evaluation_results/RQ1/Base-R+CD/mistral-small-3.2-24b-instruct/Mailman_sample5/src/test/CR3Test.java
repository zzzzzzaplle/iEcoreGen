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
    
    @Before
    public void setUp() {
        // Initialize geographical areas
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
        
        // Initialize people
        ken = new Mailman("K001", "Ken");
        amy = new Mailman("A001", "Amy");
        linda = new Inhabitant("L001", "Linda");
        paul = new Inhabitant("P001", "Paul");
        becy = new Inhabitant("B001", "Becy");
        victor = new Inhabitant("V001", "Victor");
        rachel = new Inhabitant("R001", "Rachel");
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Verify successful addition
        assertTrue("Linda should be successfully added to Riverside", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Setup: Add Mailman "Ken" to Lakeside
        lakeside.addMailman(ken);
        
        // Setup: Add Inhabitant "Paul" to Lakeside
        lakeside.addInhabitant(paul);
        
        // Setup: Create and assign Letter for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setAddressee(paul);
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Verify successful removal and mail deletion
        assertTrue("Paul should be successfully removed from Lakeside", result);
        assertFalse("Lakeside should not contain Paul", lakeside.getInhabitants().contains(paul));
        assertNull("Letter6 should be deleted from mail list", lakeside.listRegisteredMailWithInhabitant(paul));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Add new Inhabitant "Linda" to Downtown
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Linda should be successfully added to Downtown", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // Add new Inhabitant "Becy" to Downtown
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Becy should be successfully added to Downtown", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // Remove Inhabitant "Linda" from Downtown
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Linda should be successfully removed from Downtown", result3);
        assertFalse("Downtown should not contain Linda", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Verify failure to remove non-existent inhabitant
        assertFalse("Should return false when trying to remove non-existent inhabitant", result);
        assertFalse("Hillside should not contain Victor", hillside.getInhabitants().contains(victor));
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Setup: Add Mailman "Amy" to Beachfront
        beachfront.addMailman(amy);
        
        // Setup: Add Inhabitant "Rachel" to Beachfront
        beachfront.addInhabitant(rachel);
        
        // Setup: Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setAddressee(rachel);
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        
        // Setup: Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setAddressee(rachel);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify mail items exist before removal
        List<RegisteredMail> rachelMail = beachfront.listRegisteredMailWithInhabitant(rachel);
        assertNotNull("Rachel should have mail items before removal", rachelMail);
        assertEquals("Rachel should have 2 mail items", 2, rachelMail.size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Verify successful removal and mail deletion
        assertTrue("Rachel should be successfully removed from Beachfront", result);
        assertFalse("Beachfront should not contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertNull("All mail items for Rachel should be deleted", beachfront.listRegisteredMailWithInhabitant(rachel));
    }
}