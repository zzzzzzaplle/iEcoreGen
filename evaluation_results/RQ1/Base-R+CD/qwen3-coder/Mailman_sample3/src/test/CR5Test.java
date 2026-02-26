import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    private GeographicalArea area;
    private Mailman mailman;
    private Inhabitant inhabitant;
    
    @Before
    public void setUp() {
        // Common setup for tests that need a fresh area
        area = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        area = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        area.addMailman(aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        area.addInhabitant(zoe);
        
        // Create and assign: Letter10 for Zoe (Aaron), Parcel6 for Zoe (Aaron)
        Letter letter10 = new Letter();
        Parcel parcel6 = new Parcel();
        
        // Add mails to area's allMails list
        area.getAllMails().add(letter10);
        area.getAllMails().add(parcel6);
        
        // Assign mails to Zoe via Aaron
        area.assignRegisteredMailDeliver(aaron, zoe, letter10);
        area.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        area = new GeographicalArea();
        
        // Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        area.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Use default area created in setUp()
        
        // Create a non-existent inhabitant "Noah" (not added to area)
        Inhabitant noah = new Inhabitant();
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea();
        
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        area.addInhabitant(ella);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Inhabitant joseph = new Inhabitant();
        area.addInhabitant(joseph);
        Mailman peter = new Mailman();
        area.addMailman(peter);
        
        Letter letter11 = new Letter();
        area.getAllMails().add(letter11);
        area.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign: Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        area.getAllMails().add(letter12);
        area.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12 only (not Letter11 which is for Joseph)
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertFalse("Should not contain letter11", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        area = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        area.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        area.addInhabitant(luke);
        
        // Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
        Letter letter12 = new Letter();
        Parcel parcel7 = new Parcel();
        Letter letter13 = new Letter();
        
        // Add mails to area's allMails list
        area.getAllMails().add(letter12);
        area.getAllMails().add(parcel7);
        area.getAllMails().add(letter13);
        
        // Assign mails to Luke via Ruby
        area.assignRegisteredMailDeliver(ruby, luke, letter12);
        area.assignRegisteredMailDeliver(ruby, luke, parcel7);
        area.assignRegisteredMailDeliver(ruby, luke, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}