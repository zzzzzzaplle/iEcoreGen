import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private GeographicalArea area;
    
    @Before
    public void setUp() {
        // Reset area before each test
        area = null;
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        area = new GeographicalArea();
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant("1", "Linda");
        boolean result = area.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding new inhabitant should return true", result);
        assertTrue("Inhabitant should be in the area's inhabitant list", 
                  area.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        area = new GeographicalArea();
        
        // SetUp: Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman("M1", "Ken");
        area.addMailman(ken);
        
        // SetUp: Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant("I1", "Paul");
        area.addInhabitant(paul);
        
        // SetUp: Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter(ken, paul);
        area.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup: ensure mail was assigned
        assertTrue("Letter6 should be in allMails", area.getAllMails().contains(letter6));
        
        // Action: Remove Paul from Lakeside
        boolean result = area.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing inhabitant should return true", result);
        assertFalse("Paul should be removed from inhabitants", 
                   area.getInhabitants().contains(paul));
        assertFalse("Letter6 should be removed from allMails", 
                   area.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        area = new GeographicalArea();
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant("1", "Linda");
        boolean result1 = area.addInhabitant(linda);
        assertTrue("First addition should return true", result1);
        assertTrue("Linda should be in inhabitants", area.getInhabitants().contains(linda));
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant("2", "Becy");
        boolean result2 = area.addInhabitant(becy);
        assertTrue("Second addition should return true", result2);
        assertTrue("Becy should be in inhabitants", area.getInhabitants().contains(becy));
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = area.removeInhabitant(linda);
        assertTrue("Removal should return true", result3);
        assertFalse("Linda should be removed", area.getInhabitants().contains(linda));
        assertTrue("Becy should still be present", area.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        area = new GeographicalArea();
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant("99", "Victor");
        boolean result = area.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        area = new GeographicalArea();
        
        // SetUp: Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman("M1", "Amy");
        area.addMailman(amy);
        
        // SetUp: Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant("I1", "Rachel");
        area.addInhabitant(rachel);
        
        // SetUp: Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter(amy, rachel);
        area.assignRegisteredMailDeliver(amy, rachel, letter7);
        
        // SetUp: Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel(amy, rachel);
        area.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup: ensure both mail items were assigned
        assertTrue("Letter7 should be in allMails", area.getAllMails().contains(letter7));
        assertTrue("Parcel4 should be in allMails", area.getAllMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = area.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing inhabitant should return true", result);
        assertFalse("Rachel should be removed from inhabitants", 
                   area.getInhabitants().contains(rachel));
        assertFalse("Letter7 should be removed from allMails", 
                   area.getAllMails().contains(letter7));
        assertFalse("Parcel4 should be removed from allMails", 
                   area.getAllMails().contains(parcel4));
    }
}