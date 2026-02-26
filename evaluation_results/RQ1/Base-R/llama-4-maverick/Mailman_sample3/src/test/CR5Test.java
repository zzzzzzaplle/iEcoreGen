import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private MailDeliveryManager mailDeliveryManager;
    private GeographicalArea mainStreet;
    private GeographicalArea marketSquare;
    private GeographicalArea oldTown;
    private GeographicalArea newDistrict;
    private Mailman aaron;
    private Mailman ruby;
    private Mailman peter;
    private Inhabitant zoe;
    private Inhabitant mia;
    private Inhabitant ella;
    private Inhabitant luke;
    private Inhabitant noah;
    private Letter letter10;
    private Parcel parcel6;
    private Letter letter11;
    private Letter letter12;
    private Letter letter13;
    private Parcel parcel7;

    @Before
    public void setUp() {
        mailDeliveryManager = new MailDeliveryManager();
        
        // Create geographical areas
        mainStreet = new GeographicalArea();
        mainStreet.setName("MainStreet");
        marketSquare = new GeographicalArea();
        marketSquare.setName("MarketSquare");
        oldTown = new GeographicalArea();
        oldTown.setName("OldTown");
        newDistrict = new GeographicalArea();
        newDistrict.setName("NewDistrict");
        
        // Add geographical areas to manager
        mailDeliveryManager.addGeographicalArea(mainStreet);
        mailDeliveryManager.addGeographicalArea(marketSquare);
        mailDeliveryManager.addGeographicalArea(oldTown);
        mailDeliveryManager.addGeographicalArea(newDistrict);
        
        // Create mailmen
        aaron = new Mailman();
        aaron.setName("Aaron");
        ruby = new Mailman();
        ruby.setName("Ruby");
        peter = new Mailman();
        peter.setName("Peter");
        
        // Create inhabitants
        zoe = new Inhabitant();
        zoe.setName("Zoe");
        mia = new Inhabitant();
        mia.setName("Mia");
        ella = new Inhabitant();
        ella.setName("Ella");
        luke = new Inhabitant();
        luke.setName("Luke");
        noah = new Inhabitant();
        noah.setName("Noah");
        
        // Create mail items
        letter10 = new Letter();
        letter10.setContent("Letter10 content");
        parcel6 = new Parcel();
        parcel6.setWeight(6.0);
        letter11 = new Letter();
        letter11.setContent("Letter11 content");
        letter12 = new Letter();
        letter12.setContent("Letter12 content");
        letter13 = new Letter();
        letter13.setContent("Letter13 content");
        parcel7 = new Parcel();
        parcel7.setWeight(7.0);
    }

    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet", add Mailman "Aaron" and Inhabitant "Zoe"
        mailDeliveryManager.manageMailman(aaron, mainStreet, null, true);
        mailDeliveryManager.manageInhabitant(zoe, mainStreet, true);
        
        // Create and assign Letter10 and Parcel6 for Zoe (Aaron)
        letter10.setAddressee(zoe);
        letter10.assignMailman(aaron);
        mainStreet.addRegisteredMail(letter10);
        
        parcel6.setAddressee(zoe);
        parcel6.assignMailman(aaron);
        mainStreet.addRegisteredMail(parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = mailDeliveryManager.listRegisteredMailItems(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Should not return null when mail items exist", result);
        assertEquals("Should contain exactly 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }

    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare" and add Inhabitant "Mia"
        mailDeliveryManager.manageInhabitant(mia, marketSquare, true);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = mailDeliveryManager.listRegisteredMailItems(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Should return null when no mail items exist", result);
    }

    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = mailDeliveryManager.listRegisteredMailItems(noah);
        
        // Expected Output: null
        assertNull("Should return null for non-existent inhabitant", result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown" and add Inhabitant "Ella"
        mailDeliveryManager.manageInhabitant(ella, oldTown, true);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        mailDeliveryManager.manageMailman(peter, oldTown, null, true);
        letter11.setAddressee(ella);
        letter11.assignMailman(peter);
        oldTown.addRegisteredMail(letter11);
        
        // Create and assign Letter12 for Ella
        letter12.setAddressee(ella);
        oldTown.addRegisteredMail(letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = mailDeliveryManager.listRegisteredMailItems(ella);
        
        // Expected Output: List containing Letter12 (but not Letter11 since it's not assigned to Ella)
        assertNotNull("Should not return null when mail items exist", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertFalse("Should not contain letter11", result.contains(letter11));
    }

    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict", add Mailman "Ruby" and Inhabitant "Luke"
        mailDeliveryManager.manageMailman(ruby, newDistrict, null, true);
        mailDeliveryManager.manageInhabitant(luke, newDistrict, true);
        
        // Create and assign Letter12, Parcel7, and Letter13 for Luke (Ruby)
        letter12.setAddressee(luke);
        letter12.assignMailman(ruby);
        newDistrict.addRegisteredMail(letter12);
        
        parcel7.setAddressee(luke);
        parcel7.assignMailman(ruby);
        newDistrict.addRegisteredMail(parcel7);
        
        letter13.setAddressee(luke);
        letter13.assignMailman(ruby);
        newDistrict.addRegisteredMail(letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = mailDeliveryManager.listRegisteredMailItems(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Should not return null when mail items exist", result);
        assertEquals("Should contain exactly 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}