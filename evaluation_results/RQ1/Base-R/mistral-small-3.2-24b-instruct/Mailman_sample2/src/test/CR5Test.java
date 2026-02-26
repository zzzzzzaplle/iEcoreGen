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
    private Mailman aaron;
    private Mailman ruby;
    private Mailman peter;
    private Inhabitant zoe;
    private Inhabitant mia;
    private Inhabitant ella;
    private Inhabitant luke;
    private Inhabitant noah;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
        
        // Create geographical areas
        mainStreet = new GeographicalArea("MainStreet");
        marketSquare = new GeographicalArea("MarketSquare");
        oldTown = new GeographicalArea("OldTown");
        newDistrict = new GeographicalArea("NewDistrict");
        
        // Create mailmen
        aaron = new Mailman("M1", "Aaron");
        ruby = new Mailman("M2", "Ruby");
        peter = new Mailman("M3", "Peter");
        
        // Create inhabitants
        zoe = new Inhabitant("I1", "Zoe");
        mia = new Inhabitant("I2", "Mia");
        ella = new Inhabitant("I3", "Ella");
        luke = new Inhabitant("I4", "Luke");
        noah = new Inhabitant("I5", "Noah");
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        // Add Mailman "Aaron" to MainStreet
        manager.addMailman(aaron, mainStreet);
        
        // Add Inhabitant "Zoe" to MainStreet
        manager.addInhabitant(zoe, mainStreet);
        
        // Create and assign: Letter10 for Zoe (Aaron), Parcel6 for Zoe (Aaron)
        Letter letter10 = new Letter("Letter10", zoe);
        Parcel parcel6 = new Parcel("Parcel6", zoe);
        
        manager.assignMailman(letter10, aaron);
        manager.assignMailman(parcel6, aaron);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = manager.listMailItemsForInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter10));
        assertTrue(result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        // Add Inhabitant "Mia" to MarketSquare
        manager.addInhabitant(mia, marketSquare);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = manager.listMailItemsForInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = manager.listMailItemsForInhabitant(noah);
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        // Add Inhabitant "Ella" to OldTown
        manager.addInhabitant(ella, oldTown);
        
        // Add Mailman "Peter" to OldTown
        manager.addMailman(peter, oldTown);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown" (for some other inhabitant)
        Inhabitant otherInhabitant = new Inhabitant("I6", "Other");
        manager.addInhabitant(otherInhabitant, oldTown);
        Letter letter11 = new Letter("Letter11", otherInhabitant);
        manager.assignMailman(letter11, peter);
        
        // Create and assign: Letter12 for Ella
        Letter letter12 = new Letter("Letter12", ella);
        manager.assignMailman(letter12, peter);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = manager.listMailItemsForInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(letter12, result.get(0));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        // Add Mailman "Ruby" to NewDistrict
        manager.addMailman(ruby, newDistrict);
        
        // Add Inhabitant "Luke" to NewDistrict
        manager.addInhabitant(luke, newDistrict);
        
        // Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
        Letter letter12 = new Letter("Letter12", luke);
        Parcel parcel7 = new Parcel("Parcel7", luke);
        Letter letter13 = new Letter("Letter13", luke);
        
        manager.assignMailman(letter12, ruby);
        manager.assignMailman(parcel7, ruby);
        manager.assignMailman(letter13, ruby);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = manager.listMailItemsForInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel7));
        assertTrue(result.contains(letter13));
    }
}