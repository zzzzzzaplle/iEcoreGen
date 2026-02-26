import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    
    private Area area;
    
    @Before
    public void setUp() {
        area = new Area();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        area.setName("Riverside");
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        area.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Linda should be added to Riverside", area.getInhabitants().contains(linda));
        assertEquals("Area should have 1 inhabitant", 1, area.getInhabitants().size());
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
        area.getMails().add(letter6);
        area.assignMailmanToMail(letter6, ken, paul);
        
        // Verify setup: Letter6 should be assigned to Ken
        assertNotNull("Letter6 should have a mailman assigned", letter6.getMailman());
        
        // Action: Remove Paul from Lakeside
        boolean result = area.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removal should be successful", result);
        assertFalse("Paul should be removed from area", area.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted from mails", area.getMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        area.setName("Downtown");
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        area.addInhabitant(linda);
        assertTrue("Linda should be added successfully", area.getInhabitants().contains(linda));
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        area.addInhabitant(becy);
        assertTrue("Becy should be added successfully", area.getInhabitants().contains(becy));
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean removalResult = area.removeInhabitant(linda);
        assertTrue("Linda should be removed successfully", removalResult);
        assertFalse("Linda should no longer be in area", area.getInhabitants().contains(linda));
        assertTrue("Becy should still be in area", area.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        area.setName("Hillside");
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        
        // Expected Output: false (inhabitant doesn't exist)
        boolean result = area.removeInhabitant(victor);
        assertFalse("Removal should fail for non-existent inhabitant", result);
        assertEquals("Area should have 0 inhabitants", 0, area.getInhabitants().size());
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
        
        // Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setAddressee(rachel);
        area.getMails().add(letter7);
        area.assignMailmanToMail(letter7, amy, rachel);
        
        // Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setAddressee(rachel);
        area.getMails().add(parcel4);
        area.assignMailmanToMail(parcel4, amy, rachel);
        
        // Verify setup: Both mail items should be assigned to Amy
        assertNotNull("Letter7 should have a mailman assigned", letter7.getMailman());
        assertNotNull("Parcel4 should have a mailman assigned", parcel4.getMailman());
        assertEquals("Area should have 2 mails", 2, area.getMails().size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = area.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removal should be successful", result);
        assertFalse("Rachel should be removed from area", area.getInhabitants().contains(rachel));
        assertFalse("Letter7 should be deleted from mails", area.getMails().contains(letter7));
        assertFalse("Parcel4 should be deleted from mails", area.getMails().contains(parcel4));
        assertEquals("Area should have 0 mails after removal", 0, area.getMails().size());
    }
}