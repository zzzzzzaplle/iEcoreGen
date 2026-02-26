import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private GeographicalArea riverside;
    private GeographicalArea lakeside;
    private GeographicalArea downtown;
    private GeographicalArea hillside;
    private GeographicalArea beachfront;
    
    @Before
    public void setUp() {
        // Create geographical areas for each test
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        
        boolean result = riverside.addInhabitant(linda);
        assertTrue("Linda should be successfully added to Riverside", result);
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create Mailman "Ken" and add to Lakeside
        Mailman ken = new Mailman();
        ken.setName("Ken");
        lakeside.addMailman(ken);
        
        // SetUp: Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        lakeside.addInhabitant(paul);
        
        // SetUp: Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        lakeside.getAllMails().add(letter6);
        lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Paul should be successfully removed from Lakeside", result);
        assertFalse("Paul should no longer be in inhabitants list", lakeside.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted from allMails", lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Add new Inhabitant "Linda" to Downtown
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Linda should be successfully added to Downtown", result1);
        
        // Add new Inhabitant "Becy" to Downtown
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Becy should be successfully added to Downtown", result2);
        
        // Remove Inhabitant "Linda" from Downtown
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Linda should be successfully removed from Downtown", result3);
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Create non-existent inhabitant "Victor"
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setName("Amy");
        beachfront.addMailman(amy);
        
        // SetUp: Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        beachfront.addInhabitant(rachel);
        
        // SetUp: Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        beachfront.getAllMails().add(letter7);
        beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        
        // SetUp: Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        beachfront.getAllMails().add(parcel4);
        beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify mail items exist before removal
        assertTrue("Letter7 should be in allMails before removal", beachfront.getAllMails().contains(letter7));
        assertTrue("Parcel4 should be in allMails before removal", beachfront.getAllMails().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Rachel should be successfully removed from Beachfront", result);
        assertFalse("Rachel should no longer be in inhabitants list", beachfront.getInhabitants().contains(rachel));
        assertFalse("Letter7 should be deleted from allMails", beachfront.getAllMails().contains(letter7));
        assertFalse("Parcel4 should be deleted from allMails", beachfront.getAllMails().contains(parcel4));
    }
}