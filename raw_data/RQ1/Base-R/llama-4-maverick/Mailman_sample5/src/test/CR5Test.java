import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private MailDeliveryManager manager;
    private GeographicalArea mainStreet;
    private GeographicalArea marketSquare;
    private GeographicalArea oldTown;
    private GeographicalArea newDistrict;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        mainStreet = new GeographicalArea();
        mainStreet.setName("MainStreet");
        
        // Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        aaron.setName("Aaron");
        aaron.setGeographicalArea(mainStreet);
        mainStreet.addMailman(aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        zoe.setName("Zoe");
        zoe.setGeographicalArea(mainStreet);
        mainStreet.addInhabitant(zoe);
        
        // Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        letter10.setAddressee(zoe);
        letter10.assignMailman(aaron);
        mainStreet.addRegisteredMail(letter10);
        
        // Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        parcel6.setAddressee(zoe);
        parcel6.assignMailman(aaron);
        mainStreet.addRegisteredMail(parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = manager.listRegisteredMailItems(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter10));
        assertTrue(result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        marketSquare = new GeographicalArea();
        marketSquare.setName("MarketSquare");
        
        // Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        mia.setName("Mia");
        mia.setGeographicalArea(marketSquare);
        marketSquare.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = manager.listRegisteredMailItems(mia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Create a non-existent inhabitant "Noah" not associated with any area
        Inhabitant noah = new Inhabitant();
        noah.setName("Noah");
        // Note: Noah has no geographical area set
        
        // Action: List all mail for Noah
        List<RegisteredMail> result = manager.listRegisteredMailItems(noah);
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        oldTown = new GeographicalArea();
        oldTown.setName("OldTown");
        
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setName("Ella");
        ella.setGeographicalArea(oldTown);
        oldTown.addInhabitant(ella);
        
        // Add Mailman "Peter" to OldTown
        Mailman peter = new Mailman();
        peter.setName("Peter");
        peter.setGeographicalArea(oldTown);
        oldTown.addMailman(peter);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown" (but not for Ella)
        Letter letter11 = new Letter();
        Inhabitant otherInhabitant = new Inhabitant();
        otherInhabitant.setName("Other");
        otherInhabitant.setGeographicalArea(oldTown);
        oldTown.addInhabitant(otherInhabitant);
        letter11.setAddressee(otherInhabitant);
        letter11.assignMailman(peter);
        oldTown.addRegisteredMail(letter11);
        
        // Create and assign Letter12 for Ella
        Letter letter12 = new Letter();
        letter12.setAddressee(ella);
        letter12.assignMailman(peter);
        oldTown.addRegisteredMail(letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = manager.listRegisteredMailItems(ella);
        
        // Expected Output: Letter12 only
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(letter12, result.get(0));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        newDistrict = new GeographicalArea();
        newDistrict.setName("NewDistrict");
        
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        ruby.setName("Ruby");
        ruby.setGeographicalArea(newDistrict);
        newDistrict.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        luke.setName("Luke");
        luke.setGeographicalArea(newDistrict);
        newDistrict.addInhabitant(luke);
        
        // Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        letter12.setAddressee(luke);
        letter12.assignMailman(ruby);
        newDistrict.addRegisteredMail(letter12);
        
        // Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        parcel7.setAddressee(luke);
        parcel7.assignMailman(ruby);
        newDistrict.addRegisteredMail(parcel7);
        
        // Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        letter13.setAddressee(luke);
        letter13.assignMailman(ruby);
        newDistrict.addRegisteredMail(letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = manager.listRegisteredMailItems(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel7));
        assertTrue(result.contains(letter13));
    }
}