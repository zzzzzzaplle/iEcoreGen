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
    
    private Letter letter6;
    private Letter letter7;
    private Parcel parcel4;
    
    @Before
    public void setUp() {
        // Initialize geographical areas
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
        
        // Initialize mailmen
        ken = new Mailman();
        amy = new Mailman();
        
        // Initialize inhabitants
        linda = new Inhabitant();
        paul = new Inhabitant();
        becy = new Inhabitant();
        victor = new Inhabitant();
        rachel = new Inhabitant();
        
        // Initialize mail items
        letter6 = new Letter();
        letter7 = new Letter();
        parcel4 = new Parcel();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: "Add New Inhabitant to Area"
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: "Remove Inhabitant with Assigned Mail"
        // SetUp steps:
        // 1. Create GeographicalArea "Lakeside" (already done in @Before)
        // 2. Add Mailman "Ken" to Lakeside
        lakeside.addMailman(ken);
        // 3. Add Inhabitant "Paul" to Lakeside
        lakeside.addInhabitant(paul);
        // 4. Create and assign Letter6 for Paul (Ken)
        letter6.setAddressee(paul);
        lakeside.getAllMails().add(letter6);
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", lakeside.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted from allMails", lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        // SetUp: Create GeographicalArea "Downtown" (already done in @Before)
        
        // Action & Expected Output:
        // Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitant().contains(linda));
        
        // Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitant().contains(becy));
        
        // Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertFalse("Downtown should not contain Linda after removal", downtown.getInhabitant().contains(linda));
        assertTrue("Downtown should still contain Becy after Linda removal", downtown.getInhabitant().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        // SetUp: Create GeographicalArea "Hillside" (already done in @Before)
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: "Remove Inhabitant with Multiple Mail Items"
        // SetUp steps:
        // 1. Create GeographicalArea "Beachfront" (already done in @Before)
        // 2. Add Mailman "Amy" to Beachfront
        beachfront.addMailman(amy);
        // 3. Add Inhabitant "Rachel" to Beachfront
        beachfront.addInhabitant(rachel);
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        //    - Parcel4 for Rachel (Amy)
        letter7.setAddressee(rachel);
        parcel4.setAddressee(rachel);
        beachfront.getAllMails().add(letter7);
        beachfront.getAllMails().add(parcel4);
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup: both mails should be in the list and assigned to Amy
        List<RegisteredMail> amyMails = beachfront.listRegisteredMail(amy);
        assertNotNull("Amy should have mail items", amyMails);
        assertEquals("Amy should have 2 mail items", 2, amyMails.size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", beachfront.getInhabitant().contains(rachel));
        
        // Check that both mail items were deleted
        assertFalse("Letter7 should be deleted from allMails", beachfront.getAllMails().contains(letter7));
        assertFalse("Parcel4 should be deleted from allMails", beachfront.getAllMails().contains(parcel4));
        
        // Check that Amy has no mail items assigned after removal
        List<RegisteredMail> amyMailsAfterRemoval = beachfront.listRegisteredMail(amy);
        assertNull("Amy should have no mail items after Rachel removal", amyMailsAfterRemoval);
    }
}