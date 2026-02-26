import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private MailService mailService;
    
    @Before
    public void setUp() {
        mailService = new MailService();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        GeographicalArea riverside = new GeographicalArea("AR1", "Riverside");
        mailService.addArea(riverside);
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant("I1", "Linda", null);
        boolean result = mailService.addInhabitant("AR1", linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Inhabitant should be added successfully", result);
        
        // Verify inhabitant was actually added
        assertNotNull("Inhabitant should exist in the area", 
                     riverside.findInhabitantById("I1"));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        GeographicalArea lakeside = new GeographicalArea("AR2", "Lakeside");
        mailService.addArea(lakeside);
        
        // Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman("M1", "Ken", null);
        mailService.addMailman("AR2", ken);
        
        // Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant("I2", "Paul", null);
        mailService.addInhabitant("AR2", paul);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter("L6", paul, "Test Subject", "Test Content");
        mailService.registerMailItem(letter6);
        mailService.assignMailItemToMailman("L6", "M1");
        
        // Verify setup: letter exists and is assigned
        assertNotNull("Letter should exist", lakeside.findMailItemById("L6"));
        assertNotNull("Letter should be assigned", letter6.getAssignedMailman());
        
        // Action: Remove Paul from Lakeside
        boolean result = mailService.removeInhabitant("AR2", "I2");
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Inhabitant removal should succeed", result);
        
        // Verify inhabitant was removed
        assertNull("Inhabitant should be removed", lakeside.findInhabitantById("I2"));
        
        // Verify assigned mail was deleted
        assertNull("Assigned mail should be deleted", lakeside.findMailItemById("L6"));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = new GeographicalArea("AR3", "Downtown");
        mailService.addArea(downtown);
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant("I3", "Linda", null);
        boolean result1 = mailService.addInhabitant("AR3", linda);
        assertTrue("First inhabitant should be added successfully", result1);
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant("I4", "Becy", null);
        boolean result2 = mailService.addInhabitant("AR3", becy);
        assertTrue("Second inhabitant should be added successfully", result2);
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = mailService.removeInhabitant("AR3", "I3");
        assertTrue("Inhabitant removal should succeed", result3);
        
        // Verify Linda is removed but Becy remains
        assertNull("Linda should be removed", downtown.findInhabitantById("I3"));
        assertNotNull("Becy should still exist", downtown.findInhabitantById("I4"));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        GeographicalArea hillside = new GeographicalArea("AR4", "Hillside");
        mailService.addArea(hillside);
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = mailService.removeInhabitant("AR4", "Victor");
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        GeographicalArea beachfront = new GeographicalArea("AR5", "Beachfront");
        mailService.addArea(beachfront);
        
        // Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman("M2", "Amy", null);
        mailService.addMailman("AR5", amy);
        
        // Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant("I5", "Rachel", null);
        mailService.addInhabitant("AR5", rachel);
        
        // Create and assign: Letter7 for Rachel (Amy)
        Letter letter7 = new Letter("L7", rachel, "Important Letter", "Urgent content");
        mailService.registerMailItem(letter7);
        mailService.assignMailItemToMailman("L7", "M2");
        
        // Create and assign: Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel("P4", rachel, 2.5, "Fragile package");
        mailService.registerMailItem(parcel4);
        mailService.assignMailItemToMailman("P4", "M2");
        
        // Verify setup: both mail items exist and are assigned
        assertNotNull("Letter should exist", beachfront.findMailItemById("L7"));
        assertNotNull("Parcel should exist", beachfront.findMailItemById("P4"));
        assertNotNull("Letter should be assigned", letter7.getAssignedMailman());
        assertNotNull("Parcel should be assigned", parcel4.getAssignedMailman());
        
        // Action: Remove Rachel from Beachfront
        boolean result = mailService.removeInhabitant("AR5", "I5");
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Inhabitant removal should succeed", result);
        
        // Verify inhabitant was removed
        assertNull("Inhabitant should be removed", beachfront.findInhabitantById("I5"));
        
        // Verify both assigned mail items were deleted
        assertNull("Letter should be deleted", beachfront.findMailItemById("L7"));
        assertNull("Parcel should be deleted", beachfront.findMailItemById("P4"));
    }
}