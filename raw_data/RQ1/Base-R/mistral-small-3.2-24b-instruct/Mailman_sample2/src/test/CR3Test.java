import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private MailDeliveryManager manager;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        GeographicalArea riverside = new GeographicalArea("Riverside");
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant("L001", "Linda");
        boolean result = manager.addInhabitant(linda, riverside);
        
        // Expected Output: true (successful addition)
        assertTrue("Inhabitant should be added successfully", result);
        assertEquals("Inhabitant should be assigned to Riverside area", riverside, linda.getArea());
        assertTrue("Riverside should contain the inhabitant", riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        GeographicalArea lakeside = new GeographicalArea("Lakeside");
        
        // Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman("M001", "Ken");
        manager.addMailman(ken, lakeside);
        
        // Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant("I001", "Paul");
        manager.addInhabitant(paul, lakeside);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter("Letter6", paul);
        manager.assignMailman(letter6, ken);
        
        // Action: Remove Paul from Lakeside
        boolean result = manager.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Inhabitant should be removed successfully", result);
        assertNull("Inhabitant should no longer have an area assigned", paul.getArea());
        assertFalse("Lakeside should not contain the removed inhabitant", lakeside.getInhabitants().contains(paul));
        assertNull("Assigned mail should be deleted (mailman set to null)", letter6.getAssignedMailman());
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = new GeographicalArea("Downtown");
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant("I001", "Linda");
        boolean result1 = manager.addInhabitant(linda, downtown);
        assertTrue("First inhabitant should be added successfully", result1);
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant("I002", "Becy");
        boolean result2 = manager.addInhabitant(becy, downtown);
        assertTrue("Second inhabitant should be added successfully", result2);
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = manager.removeInhabitant(linda);
        assertTrue("Inhabitant should be removed successfully", result3);
        
        // Verify final state
        assertFalse("Downtown should not contain removed inhabitant", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain remaining inhabitant", downtown.getInhabitants().contains(becy));
        assertEquals("Downtown should have exactly 1 inhabitant remaining", 1, downtown.getInhabitants().size());
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        GeographicalArea hillside = new GeographicalArea("Hillside");
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant("I999", "Victor"); // Not added to any area
        boolean result = manager.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Non-existent inhabitant removal should fail", result);
        assertNull("Inhabitant should not have an area assigned", victor.getArea());
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        GeographicalArea beachfront = new GeographicalArea("Beachfront");
        
        // Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman("M001", "Amy");
        manager.addMailman(amy, beachfront);
        
        // Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant("I001", "Rachel");
        manager.addInhabitant(rachel, beachfront);
        
        // Create and assign: Letter7 for Rachel (Amy)
        Letter letter7 = new Letter("Letter7", rachel);
        manager.assignMailman(letter7, amy);
        
        // Create and assign: Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel("Parcel4", rachel);
        manager.assignMailman(parcel4, amy);
        
        // Action: Remove Rachel from Beachfront
        boolean result = manager.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Inhabitant should be removed successfully", result);
        assertNull("Inhabitant should no longer have an area assigned", rachel.getArea());
        assertFalse("Beachfront should not contain the removed inhabitant", beachfront.getInhabitants().contains(rachel));
        assertNull("First mail item should be deleted (mailman set to null)", letter7.getAssignedMailman());
        assertNull("Second mail item should be deleted (mailman set to null)", parcel4.getAssignedMailman());
    }
}