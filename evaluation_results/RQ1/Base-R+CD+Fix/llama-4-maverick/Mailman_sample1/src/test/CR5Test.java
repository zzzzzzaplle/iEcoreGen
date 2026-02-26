import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
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
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        mainStreet.addMailman(aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        mainStreet.addInhabitant(zoe);
        
        // Create and assign: Letter10 for Zoe (Aaron) and Parcel6 for Zoe (Aaron)
        Letter letter10 = new Letter();
        Parcel parcel6 = new Parcel();
        
        // Add mails to geographical area
        mainStreet.getAllMails().add(letter10);
        mainStreet.getAllMails().add(parcel6);
        
        // Assign mail to carrier and addressee
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, letter10);
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = mainStreet.listRegisteredMailWithInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("List should contain 2 items", 2, result.size());
        assertTrue("List should contain letter10", result.contains(letter10));
        assertTrue("List should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        GeographicalArea marketSquare = new GeographicalArea();
        
        // Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        marketSquare.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea
        GeographicalArea area = new GeographicalArea();
        
        // Create non-existent inhabitant "Noah"
        Inhabitant noah = new Inhabitant();
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        oldTown.addInhabitant(ella);
        
        // Create mailman "Peter" and add to OldTown
        Mailman peter = new Mailman();
        oldTown.addMailman(peter);
        
        // Create Letter11 (Joseph) - not assigned to Ella
        Inhabitant joseph = new Inhabitant();
        oldTown.addInhabitant(joseph);
        Letter letter11 = new Letter();
        oldTown.getAllMails().add(letter11);
        oldTown.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign: Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        oldTown.getAllMails().add(letter12);
        oldTown.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = oldTown.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12 only
        assertNotNull("Result should not be null", result);
        assertEquals("List should contain exactly 1 item", 1, result.size());
        assertEquals("List should contain letter12", letter12, result.get(0));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        GeographicalArea newDistrict = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        newDistrict.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        newDistrict.addInhabitant(luke);
        
        // Create and assign: Letter12, Parcel7, and Letter13 for Luke (Ruby)
        Letter letter12 = new Letter();
        Parcel parcel7 = new Parcel();
        Letter letter13 = new Letter();
        
        // Add mails to geographical area
        newDistrict.getAllMails().add(letter12);
        newDistrict.getAllMails().add(parcel7);
        newDistrict.getAllMails().add(letter13);
        
        // Assign all mail to Luke with Ruby as carrier
        newDistrict.assignRegisteredMailDeliver(ruby, luke, letter12);
        newDistrict.assignRegisteredMailDeliver(ruby, luke, parcel7);
        newDistrict.assignRegisteredMailDeliver(ruby, luke, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = newDistrict.listRegisteredMailWithInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("List should contain 3 items", 3, result.size());
        assertTrue("List should contain letter12", result.contains(letter12));
        assertTrue("List should contain parcel7", result.contains(parcel7));
        assertTrue("List should contain letter13", result.contains(letter13));
    }
}