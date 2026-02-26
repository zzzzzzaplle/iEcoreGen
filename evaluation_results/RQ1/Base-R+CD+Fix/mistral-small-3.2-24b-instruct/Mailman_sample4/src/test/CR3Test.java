import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private GeographicalArea geographicalArea;
    
    @Before
    public void setUp() {
        geographicalArea = new GeographicalArea();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        // This is handled by @Before method
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setId("1");
        linda.setName("Linda");
        boolean result = geographicalArea.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding new inhabitant should return true", result);
        assertTrue("Inhabitant should be in the geographical area", 
                   geographicalArea.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create GeographicalArea "Lakeside"
        // This is handled by @Before method
        
        // Add Mailman "Ken" to Lakeside
        Mailman ken = new Mailman();
        ken.setId("M1");
        ken.setName("Ken");
        geographicalArea.addMailman(ken);
        
        // Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = new Inhabitant();
        paul.setId("I1");
        paul.setName("Paul");
        geographicalArea.addInhabitant(paul);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setAddressee(paul);
        letter6.setCarrier(ken);
        geographicalArea.getAllMails().add(letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = geographicalArea.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing inhabitant with assigned mail should return true", result);
        assertFalse("Paul should be removed from inhabitants", 
                    geographicalArea.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted from all mails", 
                    geographicalArea.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        // This is handled by @Before method
        
        // Action & Expected Output: Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = new Inhabitant();
        linda.setId("1");
        linda.setName("Linda");
        boolean result1 = geographicalArea.addInhabitant(linda);
        assertTrue("Adding Linda should return true", result1);
        
        // Action & Expected Output: Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = new Inhabitant();
        becy.setId("2");
        becy.setName("Becy");
        boolean result2 = geographicalArea.addInhabitant(becy);
        assertTrue("Adding Becy should return true", result2);
        
        // Action & Expected Output: Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = geographicalArea.removeInhabitant(linda);
        assertTrue("Removing Linda should return true", result3);
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        // This is handled by @Before method
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setId("999");
        victor.setName("Victor");
        boolean result = geographicalArea.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create GeographicalArea "Beachfront"
        // This is handled by @Before method
        
        // Add Mailman "Amy" to Beachfront
        Mailman amy = new Mailman();
        amy.setId("M2");
        amy.setName("Amy");
        geographicalArea.addMailman(amy);
        
        // Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = new Inhabitant();
        rachel.setId("I2");
        rachel.setName("Rachel");
        geographicalArea.addInhabitant(rachel);
        
        // Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setAddressee(rachel);
        letter7.setCarrier(amy);
        geographicalArea.getAllMails().add(letter7);
        
        // Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setAddressee(rachel);
        parcel4.setCarrier(amy);
        geographicalArea.getAllMails().add(parcel4);
        
        // Verify initial state
        assertEquals("Should have 2 mail items initially", 2, geographicalArea.getAllMails().size());
        
        // Action: Remove Rachel from Beachfront
        boolean result = geographicalArea.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing inhabitant with multiple mail items should return true", result);
        assertFalse("Rachel should be removed from inhabitants", 
                    geographicalArea.getInhabitants().contains(rachel));
        assertTrue("All mail items should be deleted", geographicalArea.getAllMails().isEmpty());
    }
}