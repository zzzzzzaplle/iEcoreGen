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
    
    @Before
    public void setUp() {
        // Initialize geographical areas for each test
        riverside = new GeographicalArea("Riverside");
        lakeside = new GeographicalArea("Lakeside");
        downtown = new GeographicalArea("Downtown");
        hillside = new GeographicalArea("Hillside");
        beachfront = new GeographicalArea("Beachfront");
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside" (done in @Before)
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant("Linda");
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Linda should be successfully added to Riverside", result);
        assertEquals("Riverside should have 1 inhabitant", 1, riverside.getInhabitants().size());
        assertTrue("Linda should be in the inhabitants list", riverside.getInhabitants().contains(linda));
        assertEquals("Linda's area should be set to Riverside", riverside, linda.getArea());
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp:
        // 1. Create GeographicalArea "Lakeside" (done in @Before)
        // 2. Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman("Ken");
        lakeside.addMailman(ken);
        
        // 3. Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant("Paul");
        lakeside.addInhabitant(paul);
        
        // 4. Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter("Letter6", paul, "Hello Paul");
        lakeside.assignMail(letter6, ken);
        
        // Verify setup
        assertEquals("Lakeside should have 1 mailman", 1, lakeside.getMailmen().size());
        assertEquals("Lakeside should have 1 inhabitant", 1, lakeside.getInhabitants().size());
        assertEquals("Letter6 should be assigned to Ken", ken, letter6.getAssignedMailman());
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Paul should be successfully removed from Lakeside", result);
        assertEquals("Lakeside should have 0 inhabitants after removal", 0, lakeside.getInhabitants().size());
        assertFalse("Paul should not be in the inhabitants list", lakeside.getInhabitants().contains(paul));
        
        // Check that letter6 is no longer in registered mails
        List<RegisteredMail> mails = lakeside.getRegisteredMails();
        boolean letter6Found = false;
        for (RegisteredMail mail : mails) {
            if (mail.getId() != null && mail.getId().equals("Letter6")) {
                letter6Found = true;
                break;
            }
        }
        assertFalse("Letter6 should be deleted after Paul's removal", letter6Found);
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown" (done in @Before)
        
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true.
        Inhabitant linda = new Inhabitant("Linda");
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Linda should be successfully added to Downtown", result1);
        assertEquals("Downtown should have 1 inhabitant", 1, downtown.getInhabitants().size());
        
        // - Add new Inhabitant "Becy" to Downtown, true.
        Inhabitant becy = new Inhabitant("Becy");
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Becy should be successfully added to Downtown", result2);
        assertEquals("Downtown should have 2 inhabitants", 2, downtown.getInhabitants().size());
        
        // - Remove Inhabitant "Linda" to Downtown, true.
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Linda should be successfully removed from Downtown", result3);
        assertEquals("Downtown should have 1 inhabitant after removal", 1, downtown.getInhabitants().size());
        assertFalse("Linda should not be in the inhabitants list", downtown.getInhabitants().contains(linda));
        assertTrue("Becy should still be in the inhabitants list", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside" (done in @Before)
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant("Victor");
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent Victor should return false", result);
        assertEquals("Hillside should have 0 inhabitants", 0, hillside.getInhabitants().size());
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp:
        // 1. Create GeographicalArea "Beachfront" (done in @Before)
        // 2. Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman("Amy");
        beachfront.addMailman(amy);
        
        // 3. Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant("Rachel");
        beachfront.addInhabitant(rachel);
        
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        Letter letter7 = new Letter("Letter7", rachel, "Hello Rachel");
        beachfront.assignMail(letter7, amy);
        
        //    - Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel("Parcel4", rachel, 2.5);
        beachfront.assignMail(parcel4, amy);
        
        // Verify setup
        assertEquals("Beachfront should have 1 mailman", 1, beachfront.getMailmen().size());
        assertEquals("Beachfront should have 1 inhabitant", 1, beachfront.getInhabitants().size());
        assertEquals("Letter7 should be assigned to Amy", amy, letter7.getAssignedMailman());
        assertEquals("Parcel4 should be assigned to Amy", amy, parcel4.getAssignedMailman());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Rachel should be successfully removed from Beachfront", result);
        assertEquals("Beachfront should have 0 inhabitants after removal", 0, beachfront.getInhabitants().size());
        assertFalse("Rachel should not be in the inhabitants list", beachfront.getInhabitants().contains(rachel));
        
        // Check that both mail items are no longer in registered mails
        List<RegisteredMail> mails = beachfront.getRegisteredMails();
        boolean letter7Found = false;
        boolean parcel4Found = false;
        for (RegisteredMail mail : mails) {
            if (mail.getId() != null && mail.getId().equals("Letter7")) {
                letter7Found = true;
            }
            if (mail.getId() != null && mail.getId().equals("Parcel4")) {
                parcel4Found = true;
            }
        }
        assertFalse("Letter7 should be deleted after Rachel's removal", letter7Found);
        assertFalse("Parcel4 should be deleted after Rachel's removal", parcel4Found);
    }
}