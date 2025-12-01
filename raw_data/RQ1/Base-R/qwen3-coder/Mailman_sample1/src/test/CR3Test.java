import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private Area area;
    private Inhabitant inhabitant;
    private Mailman mailman;
    private Letter letter;
    private Parcel parcel;
    private MailDeliverySystem system;
    
    @Before
    public void setUp() {
        area = new Area();
        inhabitant = new Inhabitant();
        mailman = new Mailman();
        letter = new Letter();
        parcel = new Parcel();
        system = new MailDeliverySystem();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        area.setName("Riverside");
        
        // Action: Add new Inhabitant "Linda" to Riverside
        inhabitant.setName("Linda");
        inhabitant.setArea(area);
        area.addInhabitant(inhabitant);
        
        // Expected Output: true (successful addition)
        assertTrue("Inhabitant Linda should be added to Riverside", 
                   area.getInhabitants().contains(inhabitant));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        area.setName("Lakeside");
        
        // SetUp: Add Mailman "Ken" to Lakeside
        mailman.setName("Ken");
        area.addMailman(mailman);
        
        // SetUp: Add Inhabitant "Paul" to Lakeside
        inhabitant.setName("Paul");
        inhabitant.setArea(area);
        area.addInhabitant(inhabitant);
        
        // SetUp: Create and assign Letter6 for Paul (Ken)
        letter.setAddressee(inhabitant);
        letter.setArea(area);
        letter.setAssignedMailman(mailman);
        area.getRegisteredMails().add(letter);
        
        // Action: Remove Paul from Lakeside
        boolean result = area.removeInhabitant(inhabitant);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Paul should be successfully removed", result);
        assertFalse("Paul should no longer be in the area", 
                    area.getInhabitants().contains(inhabitant));
        assertTrue("Assigned mail for Paul should be deleted", 
                   area.getRegisteredMails().isEmpty());
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        area.setName("Downtown");
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        linda.setArea(area);
        area.addInhabitant(linda);
        assertTrue("Linda should be added to Downtown", 
                   area.getInhabitants().contains(linda));
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        becy.setArea(area);
        area.addInhabitant(becy);
        assertTrue("Becy should be added to Downtown", 
                   area.getInhabitants().contains(becy));
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result = area.removeInhabitant(linda);
        assertTrue("Linda should be successfully removed", result);
        assertFalse("Linda should no longer be in the area", 
                    area.getInhabitants().contains(linda));
        assertTrue("Becy should still be in the area", 
                   area.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        area.setName("Hillside");
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        victor.setArea(area);
        
        boolean result = area.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        area.setName("Beachfront");
        
        // SetUp: Add Mailman "Amy" to Beachfront
        mailman.setName("Amy");
        area.addMailman(mailman);
        
        // SetUp: Add Inhabitant "Rachel" to Beachfront
        inhabitant.setName("Rachel");
        inhabitant.setArea(area);
        area.addInhabitant(inhabitant);
        
        // SetUp: Create and assign Letter7 for Rachel (Amy)
        letter.setAddressee(inhabitant);
        letter.setArea(area);
        letter.setAssignedMailman(mailman);
        area.getRegisteredMails().add(letter);
        
        // SetUp: Create and assign Parcel4 for Rachel (Amy)
        parcel.setAddressee(inhabitant);
        parcel.setArea(area);
        parcel.setAssignedMailman(mailman);
        area.getRegisteredMails().add(parcel);
        
        // Verify setup: both mail items should be present and assigned
        assertEquals("Should have 2 registered mails", 2, area.getRegisteredMails().size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = area.removeInhabitant(inhabitant);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Rachel should be successfully removed", result);
        assertFalse("Rachel should no longer be in the area", 
                    area.getInhabitants().contains(inhabitant));
        assertTrue("All assigned mail for Rachel should be deleted", 
                   area.getRegisteredMails().isEmpty());
    }
}