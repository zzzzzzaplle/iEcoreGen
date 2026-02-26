import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private GeographicalArea geographicalArea;
    
    @Before
    public void setUp() {
        // Reset geographical area before each test
        geographicalArea = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        geographicalArea = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        aaron.setName("Aaron");
        geographicalArea.addMailman(aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        zoe.setName("Zoe");
        geographicalArea.addInhabitant(zoe);
        
        // Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        letter10.setAddressee(zoe);
        geographicalArea.getAllMails().add(letter10);
        geographicalArea.assignRegisteredMailDeliver(aaron, zoe, letter10);
        
        // Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        parcel6.setAddressee(zoe);
        geographicalArea.getAllMails().add(parcel6);
        geographicalArea.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        geographicalArea = new GeographicalArea();
        
        // Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        mia.setName("Mia");
        geographicalArea.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Create a non-existent inhabitant "Noah"
        Inhabitant noah = new Inhabitant();
        noah.setName("Noah");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        geographicalArea = new GeographicalArea();
        
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setName("Ella");
        geographicalArea.addInhabitant(ella);
        
        // Add Mailman "Peter" to OldTown
        Mailman peter = new Mailman();
        peter.setName("Peter");
        geographicalArea.addMailman(peter);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Inhabitant joseph = new Inhabitant();
        joseph.setName("Joseph");
        geographicalArea.addInhabitant(joseph);
        
        Letter letter11 = new Letter();
        letter11.setAddressee(joseph);
        geographicalArea.getAllMails().add(letter11);
        geographicalArea.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        letter12.setAddressee(ella);
        geographicalArea.getAllMails().add(letter12);
        geographicalArea.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 mail item", 1, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        geographicalArea = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        ruby.setName("Ruby");
        geographicalArea.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        luke.setName("Luke");
        geographicalArea.addInhabitant(luke);
        
        // Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        letter12.setAddressee(luke);
        geographicalArea.getAllMails().add(letter12);
        geographicalArea.assignRegisteredMailDeliver(ruby, luke, letter12);
        
        // Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        parcel7.setAddressee(luke);
        geographicalArea.getAllMails().add(parcel7);
        geographicalArea.assignRegisteredMailDeliver(ruby, luke, parcel7);
        
        // Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        letter13.setAddressee(luke);
        geographicalArea.getAllMails().add(letter13);
        geographicalArea.assignRegisteredMailDeliver(ruby, luke, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}