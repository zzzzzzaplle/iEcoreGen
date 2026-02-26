import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    private GeographicalArea geographicalArea;
    private Mailman mailman;
    private Inhabitant inhabitant;
    
    @Before
    public void setUp() {
        geographicalArea = new GeographicalArea();
        mailman = new Mailman();
        inhabitant = new Inhabitant();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        GeographicalArea riverside = new GeographicalArea();
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding new inhabitant Linda should return true", result);
        assertTrue("Riverside should contain inhabitant Linda", 
                  riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        GeographicalArea lakeside = new GeographicalArea();
        
        // Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        lakeside.addMailman(ken);
        
        // Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        lakeside.addInhabitant(paul);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Verify setup is correct
        assertTrue("Lakeside should contain Paul", lakeside.getInhabitants().contains(paul));
        assertNotNull("Letter6 should be assigned to Paul", letter6.getAddressee());
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing Paul should return true", result);
        assertFalse("Lakeside should not contain Paul after removal", 
                   lakeside.getInhabitants().contains(paul));
        
        // Verify mail was deleted
        List<RegisteredMail> paulsMail = lakeside.listRegisteredMailWithInhabitant(paul);
        assertNull("Paul's mail should be deleted", paulsMail);
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = new GeographicalArea();
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Adding Linda should return true", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Adding Becy should return true", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Removing Linda should return true", result3);
        assertFalse("Downtown should not contain Linda after removal", 
                   downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        GeographicalArea hillside = new GeographicalArea();
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant(); // Victor is not added to Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        GeographicalArea beachfront = new GeographicalArea();
        
        // Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        beachfront.addMailman(amy);
        
        // Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        beachfront.addInhabitant(rachel);
        
        // Create and assign: Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        
        // Create and assign: Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify setup is correct
        assertTrue("Beachfront should contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertEquals("Letter7 should be assigned to Rachel", rachel, letter7.getAddressee());
        assertEquals("Parcel4 should be assigned to Rachel", rachel, parcel4.getAddressee());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel should return true", result);
        assertFalse("Beachfront should not contain Rachel after removal", 
                   beachfront.getInhabitants().contains(rachel));
        
        // Verify both mail items were deleted
        List<RegisteredMail> rachelsMail = beachfront.listRegisteredMailWithInhabitant(rachel);
        assertNull("Rachel's mail should be deleted", rachelsMail);
        
        // Verify mail items are no longer in allMails
        assertFalse("Letter7 should be removed from allMails", 
                   beachfront.getAllMails().contains(letter7));
        assertFalse("Parcel4 should be removed from allMails", 
                   beachfront.getAllMails().contains(parcel4));
    }
}