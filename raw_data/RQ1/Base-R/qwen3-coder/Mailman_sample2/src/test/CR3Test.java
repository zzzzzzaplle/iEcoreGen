import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private Area area;
    private Mailman mailman;
    private Inhabitant inhabitant;
    private MailDeliverySystem system;
    
    @Before
    public void setUp() {
        area = new Area();
        mailman = new Mailman();
        inhabitant = new Inhabitant();
        system = new MailDeliverySystem();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        area.setName("Riverside");
        
        // Create and set up inhabitant Linda
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        linda.setArea(area);
        
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = area.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Linda should be successfully added to Riverside area", result);
        assertTrue("Riverside area should contain Linda", area.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        area.setName("Lakeside");
        
        // SetUp: Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        ken.setName("Ken");
        area.addMailman(ken);
        
        // SetUp: Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        paul.setArea(area);
        area.addInhabitant(paul);
        
        // SetUp: Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setAddressee(paul);
        letter6.setMailman(ken);
        area.getMails().add(letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = area.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Paul should be successfully removed from Lakeside", result);
        assertFalse("Lakeside area should not contain Paul anymore", area.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted as it was assigned to Paul", area.getMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        area.setName("Downtown");
        
        // Create inhabitants Linda and Becy
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        linda.setArea(area);
        
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        becy.setArea(area);
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = area.addInhabitant(linda);
        assertTrue("Linda should be successfully added to Downtown", result1);
        assertTrue("Downtown area should contain Linda", area.getInhabitants().contains(linda));
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = area.addInhabitant(becy);
        assertTrue("Becy should be successfully added to Downtown", result2);
        assertTrue("Downtown area should contain Becy", area.getInhabitants().contains(becy));
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = area.removeInhabitant(linda);
        assertTrue("Linda should be successfully removed from Downtown", result3);
        assertFalse("Downtown area should not contain Linda anymore", area.getInhabitants().contains(linda));
        assertTrue("Downtown area should still contain Becy", area.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        area.setName("Hillside");
        
        // Create non-existent inhabitant Victor (not added to area)
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        victor.setArea(area);
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = area.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor should return false", result);
        assertFalse("Hillside area should not contain Victor", area.getInhabitants().contains(victor));
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        area.setName("Beachfront");
        
        // SetUp: Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setName("Amy");
        area.addMailman(amy);
        
        // SetUp: Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        rachel.setArea(area);
        area.addInhabitant(rachel);
        
        // SetUp: Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setAddressee(rachel);
        letter7.setMailman(amy);
        area.getMails().add(letter7);
        
        // SetUp: Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setAddressee(rachel);
        parcel4.setMailman(amy);
        area.getMails().add(parcel4);
        
        // Verify initial setup
        assertEquals("Beachfront should have 2 mail items", 2, area.getMails().size());
        assertTrue("Beachfront should contain Letter7", area.getMails().contains(letter7));
        assertTrue("Beachfront should contain Parcel4", area.getMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = area.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Rachel should be successfully removed from Beachfront", result);
        assertFalse("Beachfront area should not contain Rachel anymore", area.getInhabitants().contains(rachel));
        assertTrue("Both assigned mail items should be deleted", area.getMails().isEmpty());
    }
}