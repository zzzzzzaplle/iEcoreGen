import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    private MailDeliveryManager manager;
    private GeographicalArea mainStreet;
    private GeographicalArea marketSquare;
    private GeographicalArea oldTown;
    private GeographicalArea newDistrict;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
        
        // Initialize geographical areas
        mainStreet = new GeographicalArea();
        mainStreet.setName("MainStreet");
        
        marketSquare = new GeographicalArea();
        marketSquare.setName("MarketSquare");
        
        oldTown = new GeographicalArea();
        oldTown.setName("OldTown");
        
        newDistrict = new GeographicalArea();
        newDistrict.setName("NewDistrict");
        
        // Add geographical areas to manager
        manager.addGeographicalArea(mainStreet);
        manager.addGeographicalArea(marketSquare);
        manager.addGeographicalArea(oldTown);
        manager.addGeographicalArea(newDistrict);
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        // SetUp: Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        aaron.setName("Aaron");
        manager.addMailmanToGeographicalArea(aaron, mainStreet);
        
        // SetUp: Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        zoe.setName("Zoe");
        manager.addInhabitantToGeographicalArea(zoe, mainStreet);
        
        // SetUp: Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        letter10.setContent("Letter10 content");
        letter10.setAddressee(zoe);
        letter10.assignMailman(aaron);
        mainStreet.addRegisteredMail(letter10);
        
        // SetUp: Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        parcel6.setWeight(2.5);
        parcel6.setAddressee(zoe);
        parcel6.assignMailman(aaron);
        mainStreet.addRegisteredMail(parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = manager.listRegisteredMailForInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        // SetUp: Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        mia.setName("Mia");
        manager.addInhabitantToGeographicalArea(mia, marketSquare);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = manager.listRegisteredMailForInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Create a non-existent inhabitant not added to any geographical area
        Inhabitant noah = new Inhabitant();
        noah.setName("Noah");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = manager.listRegisteredMailForInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        // SetUp: Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setName("Ella");
        manager.addInhabitantToGeographicalArea(ella, oldTown);
        
        // SetUp: Create Letter11 assigned to mailman "Peter" in "OldTown"
        Mailman peter = new Mailman();
        peter.setName("Peter");
        manager.addMailmanToGeographicalArea(peter, oldTown);
        
        Letter letter11 = new Letter();
        letter11.setContent("Letter11 content");
        letter11.setAddressee(ella);
        letter11.assignMailman(peter);
        oldTown.addRegisteredMail(letter11);
        
        // SetUp: Create and assign Letter12 for Ella
        Letter letter12 = new Letter();
        letter12.setContent("Letter12 content");
        letter12.setAddressee(ella);
        letter12.assignMailman(peter);
        oldTown.addRegisteredMail(letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = manager.listRegisteredMailForInhabitant(ella);
        
        // Expected Output: Should contain both letters (Letter11 and Letter12)
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter11", result.contains(letter11));
        assertTrue("Should contain letter12", result.contains(letter12));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        // SetUp: Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        ruby.setName("Ruby");
        manager.addMailmanToGeographicalArea(ruby, newDistrict);
        
        // SetUp: Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        luke.setName("Luke");
        manager.addInhabitantToGeographicalArea(luke, newDistrict);
        
        // SetUp: Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        letter12.setContent("Letter12 content");
        letter12.setAddressee(luke);
        letter12.assignMailman(ruby);
        newDistrict.addRegisteredMail(letter12);
        
        // SetUp: Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        parcel7.setWeight(3.7);
        parcel7.setAddressee(luke);
        parcel7.assignMailman(ruby);
        newDistrict.addRegisteredMail(parcel7);
        
        // SetUp: Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        letter13.setContent("Letter13 content");
        letter13.setAddressee(luke);
        letter13.assignMailman(ruby);
        newDistrict.addRegisteredMail(letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = manager.listRegisteredMailForInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}