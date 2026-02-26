import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private MailDeliverySystem mailSystem;
    private GeographicalArea riverside;
    private GeographicalArea lakeside;
    private GeographicalArea downtown;
    private GeographicalArea hillside;
    private GeographicalArea beachfront;
    
    @Before
    public void setUp() {
        mailSystem = new MailDeliverySystem();
        riverside = new GeographicalArea("Riverside");
        lakeside = new GeographicalArea("Lakeside");
        downtown = new GeographicalArea("Downtown");
        hillside = new GeographicalArea("Hillside");
        beachfront = new GeographicalArea("Beachfront");
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: "Add New Inhabitant to Area"
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        
        boolean result = mailSystem.addInhabitant(linda, riverside);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
        assertEquals("Linda's geographical area should be Riverside", riverside, linda.getGeographicalArea());
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: "Remove Inhabitant with Assigned Mail"
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        // 3. Add Inhabitant "Paul" to Lakeside
        // 4. Create and assign Letter6 for Paul (Ken)
        
        Mailman ken = new Mailman();
        ken.setName("Ken");
        mailSystem.addMailman(ken, lakeside);
        
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        mailSystem.addInhabitant(paul, lakeside);
        
        Letter letter6 = new Letter();
        letter6.setAddressee(paul);
        letter6.setGeographicalArea(lakeside);
        mailSystem.assignMail(letter6, ken);
        
        // Verify setup is correct
        assertTrue("Lakeside should contain assigned letter", lakeside.getRegisteredMails().contains(letter6));
        
        // Action: Remove Paul from Lakeside
        boolean result = mailSystem.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", lakeside.getInhabitants().contains(paul));
        assertNull("Paul's geographical area should be null after removal", paul.getGeographicalArea());
        assertFalse("Letter6 should be deleted from Lakeside", lakeside.getRegisteredMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        // SetUp: Create GeographicalArea "Downtown"
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        // - Add new Inhabitant "Becy" to Downtown, true
        // - Remove Inhabitant "Linda" to Downtown, true
        
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        
        // Add Linda to Downtown
        boolean result1 = mailSystem.addInhabitant(linda, downtown);
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // Add Becy to Downtown
        boolean result2 = mailSystem.addInhabitant(becy, downtown);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // Remove Linda from Downtown
        boolean result3 = mailSystem.removeInhabitant(linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertFalse("Downtown should not contain Linda after removal", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        // SetUp: Create GeographicalArea "Hillside"
        // Action: Remove non-existent "Victor" from Hillside
        
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        // Note: Victor is not added to Hillside
        
        boolean result = mailSystem.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor should return false", result);
        assertNull("Victor's geographical area should remain null", victor.getGeographicalArea());
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: "Remove Inhabitant with Multiple Mail Items"
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        // 3. Add Inhabitant "Rachel" to Beachfront
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        //    - Parcel4 for Rachel (Amy)
        
        Mailman amy = new Mailman();
        amy.setName("Amy");
        mailSystem.addMailman(amy, beachfront);
        
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        mailSystem.addInhabitant(rachel, beachfront);
        
        Letter letter7 = new Letter();
        letter7.setAddressee(rachel);
        letter7.setGeographicalArea(beachfront);
        mailSystem.assignMail(letter7, amy);
        
        Parcel parcel4 = new Parcel();
        parcel4.setAddressee(rachel);
        parcel4.setGeographicalArea(beachfront);
        mailSystem.assignMail(parcel4, amy);
        
        // Verify setup is correct
        assertTrue("Beachfront should contain letter7", beachfront.getRegisteredMails().contains(letter7));
        assertTrue("Beachfront should contain parcel4", beachfront.getRegisteredMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = mailSystem.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", beachfront.getInhabitants().contains(rachel));
        assertNull("Rachel's geographical area should be null after removal", rachel.getGeographicalArea());
        assertFalse("Letter7 should be deleted from Beachfront", beachfront.getRegisteredMails().contains(letter7));
        assertFalse("Parcel4 should be deleted from Beachfront", beachfront.getRegisteredMails().contains(parcel4));
    }
}