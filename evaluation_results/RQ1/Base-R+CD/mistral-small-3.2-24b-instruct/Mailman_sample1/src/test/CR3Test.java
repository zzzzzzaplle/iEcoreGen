import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private GeographicalArea geographicalArea;
    private Inhabitant inhabitant;
    private Mailman mailman;
    
    @Before
    public void setUp() {
        geographicalArea = new GeographicalArea();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: "Add New Inhabitant to Area"
        // Setup: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        inhabitant = new Inhabitant();
        inhabitant.setName("Linda");
        
        boolean result = geographicalArea.addInhabitant(inhabitant);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding new inhabitant should return true", result);
        assertTrue("Inhabitant should be in geographical area", geographicalArea.getInhabitants().contains(inhabitant));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: "Remove Inhabitant with Assigned Mail"
        // Setup:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        // 3. Add Inhabitant "Paul" to Lakeside
        // 4. Create and assign Letter6 for Paul (Ken)
        mailman = new Mailman();
        mailman.setName("Ken");
        geographicalArea.addMailman(mailman);
        
        inhabitant = new Inhabitant();
        inhabitant.setName("Paul");
        geographicalArea.addInhabitant(inhabitant);
        
        Letter letter6 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = geographicalArea.removeInhabitant(inhabitant);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing inhabitant with assigned mail should return true", result);
        assertFalse("Inhabitant should be removed from geographical area", geographicalArea.getInhabitants().contains(inhabitant));
        assertNull("Mail should be deleted - should not be found in list for mailman", geographicalArea.listRegisteredMail(mailman));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        // Setup: Create GeographicalArea "Downtown"
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        // - Add new Inhabitant "Becy" to Downtown, true
        // - Remove Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        
        boolean addLindaResult = geographicalArea.addInhabitant(linda);
        boolean addBecyResult = geographicalArea.addInhabitant(becy);
        boolean removeLindaResult = geographicalArea.removeInhabitant(linda);
        
        assertTrue("Adding Linda should return true", addLindaResult);
        assertTrue("Adding Becy should return true", addBecyResult);
        assertTrue("Removing Linda should return true", removeLindaResult);
        assertFalse("Linda should be removed from geographical area", geographicalArea.getInhabitants().contains(linda));
        assertTrue("Becy should remain in geographical area", geographicalArea.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        // Setup: Create GeographicalArea "Hillside"
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        
        boolean result = geographicalArea.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: "Remove Inhabitant with Multiple Mail Items"
        // Setup:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        // 3. Add Inhabitant "Rachel" to Beachfront
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        //    - Parcel4 for Rachel (Amy)
        mailman = new Mailman();
        mailman.setName("Amy");
        geographicalArea.addMailman(mailman);
        
        inhabitant = new Inhabitant();
        inhabitant.setName("Rachel");
        geographicalArea.addInhabitant(inhabitant);
        
        Letter letter7 = new Letter();
        Parcel parcel4 = new Parcel();
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, letter7);
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, parcel4);
        
        // Action: Remove Rachel from Beachfront
        boolean result = geographicalArea.removeInhabitant(inhabitant);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing inhabitant with multiple mail items should return true", result);
        assertFalse("Inhabitant should be removed from geographical area", geographicalArea.getInhabitants().contains(inhabitant));
        assertNull("All mail items should be deleted - should not be found in list for mailman", geographicalArea.listRegisteredMail(mailman));
    }
}