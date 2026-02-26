import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private Area area;
    private Inhabitant inhabitant;
    private Mailman mailman;
    private RegisteredMail mail;
    
    @Before
    public void setUp() {
        // Common setup that runs before each test
        area = new Area();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        area.setName("Riverside");
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        
        boolean result = area.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda", area.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        area.setName("Lakeside");
        
        // Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        ken.setName("Ken");
        area.addMailman(ken);
        
        // Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        area.addInhabitant(paul);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setAddressee(paul);
        letter6.setMailman(ken);
        area.getRegisteredMails().add(letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = area.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", area.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted", area.getRegisteredMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        area.setName("Downtown");
        
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        boolean result1 = area.addInhabitant(linda);
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Downtown should contain Linda", area.getInhabitants().contains(linda));
        
        // - Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        boolean result2 = area.addInhabitant(becy);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Downtown should contain Becy", area.getInhabitants().contains(becy));
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = area.removeInhabitant(linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertFalse("Downtown should not contain Linda after removal", area.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", area.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        area.setName("Hillside");
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        
        boolean result = area.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor from Hillside should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        area.setName("Beachfront");
        
        // Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setName("Amy");
        area.addMailman(amy);
        
        // Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        area.addInhabitant(rachel);
        
        // Create and assign:
        // - Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setAddressee(rachel);
        letter7.setMailman(amy);
        area.getRegisteredMails().add(letter7);
        
        // - Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setAddressee(rachel);
        parcel4.setMailman(amy);
        area.getRegisteredMails().add(parcel4);
        
        // Verify initial state
        assertEquals("Beachfront should have 2 registered mails", 2, area.getRegisteredMails().size());
        assertTrue("Beachfront should contain Rachel", area.getInhabitants().contains(rachel));
        
        // Action: Remove Rachel from Beachfront
        boolean result = area.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", area.getInhabitants().contains(rachel));
        assertTrue("Both mail items should be deleted", area.getRegisteredMails().isEmpty());
    }
}