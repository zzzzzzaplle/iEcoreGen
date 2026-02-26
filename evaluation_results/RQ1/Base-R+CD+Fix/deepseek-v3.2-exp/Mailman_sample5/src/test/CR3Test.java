import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private GeographicalArea geographicalArea;
    
    @Before
    public void setUp() {
        // Reset the geographical area before each test
        geographicalArea = new GeographicalArea();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        linda.setId("L001");
        linda.setAddress("123 Riverside Drive");
        
        boolean result = geographicalArea.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding new inhabitant Linda should return true", result);
        assertTrue("Linda should be in the inhabitants list", 
                   geographicalArea.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        // SetUp: Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        ken.setName("Ken");
        ken.setId("K001");
        geographicalArea.addMailman(ken);
        
        // SetUp: Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        paul.setId("P001");
        paul.setAddress("456 Lakeside Ave");
        geographicalArea.addInhabitant(paul);
        
        // SetUp: Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setTrackingNumber("LTR6");
        letter6.setWeight(0.5);
        geographicalArea.assignRegisteredMailDeliver(ken, paul, letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = geographicalArea.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing inhabitant Paul should return true", result);
        assertFalse("Paul should not be in the inhabitants list", 
                    geographicalArea.getInhabitants().contains(paul));
        
        // Verify that the mail assigned to Paul was deleted
        List<RegisteredMail> paulsMail = geographicalArea.listRegisteredMailWithInhabitant(paul);
        assertNull("Paul's mail should be deleted", paulsMail);
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        linda.setId("L002");
        linda.setAddress("789 Downtown St");
        boolean result1 = geographicalArea.addInhabitant(linda);
        assertTrue("Adding Linda should return true", result1);
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        becy.setId("B001");
        becy.setAddress("101 Downtown Ave");
        boolean result2 = geographicalArea.addInhabitant(becy);
        assertTrue("Adding Becy should return true", result2);
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = geographicalArea.removeInhabitant(linda);
        assertTrue("Removing Linda should return true", result3);
        
        // Verify final state
        assertFalse("Linda should not be in inhabitants list", 
                    geographicalArea.getInhabitants().contains(linda));
        assertTrue("Becy should still be in inhabitants list", 
                   geographicalArea.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        victor.setId("V001");
        victor.setAddress("222 Hillside Rd");
        
        boolean result = geographicalArea.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        // SetUp: Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setName("Amy");
        amy.setId("A001");
        geographicalArea.addMailman(amy);
        
        // SetUp: Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        rachel.setId("R001");
        rachel.setAddress("333 Beachfront Blvd");
        geographicalArea.addInhabitant(rachel);
        
        // SetUp: Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setTrackingNumber("LTR7");
        letter7.setWeight(0.3);
        geographicalArea.assignRegisteredMailDeliver(amy, rachel, letter7);
        
        // SetUp: Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setTrackingNumber("PCL4");
        parcel4.setWeight(5.0);
        parcel4.setDimensions(20.0);
        geographicalArea.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        // Verify mail was assigned before removal
        List<RegisteredMail> rachelsMail = geographicalArea.listRegisteredMailWithInhabitant(rachel);
        assertNotNull("Rachel should have mail before removal", rachelsMail);
        assertEquals("Rachel should have 2 mail items", 2, rachelsMail.size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = geographicalArea.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel should return true", result);
        assertFalse("Rachel should not be in inhabitants list", 
                    geographicalArea.getInhabitants().contains(rachel));
        
        // Verify that both mail items assigned to Rachel were deleted
        rachelsMail = geographicalArea.listRegisteredMailWithInhabitant(rachel);
        assertNull("Rachel's mail should be deleted after removal", rachelsMail);
    }
}