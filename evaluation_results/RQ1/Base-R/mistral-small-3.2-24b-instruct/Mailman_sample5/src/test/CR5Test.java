import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    private MailManagementSystem system;
    private GeographicalArea mainStreet;
    private GeographicalArea marketSquare;
    private GeographicalArea oldTown;
    private GeographicalArea newDistrict;
    
    @Before
    public void setUp() {
        system = new MailManagementSystem();
        
        // Create geographical areas
        mainStreet = new GeographicalArea();
        mainStreet.setAreaId("MainStreet");
        
        marketSquare = new GeographicalArea();
        marketSquare.setAreaId("MarketSquare");
        
        oldTown = new GeographicalArea();
        oldTown.setAreaId("OldTown");
        
        newDistrict = new GeographicalArea();
        newDistrict.setAreaId("NewDistrict");
        
        // Add areas to system
        system.addGeographicalArea(mainStreet);
        system.addGeographicalArea(marketSquare);
        system.addGeographicalArea(oldTown);
        system.addGeographicalArea(newDistrict);
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        // SetUp: Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        aaron.setMailmanId("A001");
        aaron.setName("Aaron");
        aaron.setGeographicalArea(mainStreet);
        mainStreet.addMailman(aaron);
        
        // SetUp: Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        zoe.setInhabitantId("Z001");
        zoe.setName("Zoe");
        zoe.setGeographicalArea(mainStreet);
        mainStreet.addInhabitant(zoe);
        
        // SetUp: Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        letter10.setMailId("Letter10");
        letter10.setAddressee(zoe);
        aaron.assignMail(letter10);
        
        // SetUp: Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        parcel6.setMailId("Parcel6");
        parcel6.setAddressee(zoe);
        aaron.assignMail(parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = system.getMailForInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        // SetUp: Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        mia.setInhabitantId("M001");
        mia.setName("Mia");
        mia.setGeographicalArea(marketSquare);
        marketSquare.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = system.getMailForInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Create a non-existent inhabitant not added to any area
        Inhabitant noah = new Inhabitant();
        noah.setInhabitantId("N001");
        noah.setName("Noah");
        
        // Action: List all mail for Noah
        List<RegisteredMail> result = system.getMailForInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        // SetUp: Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setInhabitantId("E001");
        ella.setName("Ella");
        ella.setGeographicalArea(oldTown);
        oldTown.addInhabitant(ella);
        
        // SetUp: Create Letter11 assigned to mailman "Peter" in "OldTown"
        Mailman peter = new Mailman();
        peter.setMailmanId("P001");
        peter.setName("Peter");
        peter.setGeographicalArea(oldTown);
        oldTown.addMailman(peter);
        
        // SetUp: Create and assign Letter12 for Ella
        Letter letter12 = new Letter();
        letter12.setMailId("Letter12");
        letter12.setAddressee(ella);
        peter.assignMail(letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = system.getMailForInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 mail item", 1, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        // SetUp: Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        ruby.setMailmanId("R001");
        ruby.setName("Ruby");
        ruby.setGeographicalArea(newDistrict);
        newDistrict.addMailman(ruby);
        
        // SetUp: Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        luke.setInhabitantId("L001");
        luke.setName("Luke");
        luke.setGeographicalArea(newDistrict);
        newDistrict.addInhabitant(luke);
        
        // SetUp: Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        letter12.setMailId("Letter12");
        letter12.setAddressee(luke);
        ruby.assignMail(letter12);
        
        // SetUp: Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        parcel7.setMailId("Parcel7");
        parcel7.setAddressee(luke);
        ruby.assignMail(parcel7);
        
        // SetUp: Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        letter13.setMailId("Letter13");
        letter13.setAddressee(luke);
        ruby.assignMail(letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = system.getMailForInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
        assertTrue("Should contain Letter13", result.contains(letter13));
    }
}