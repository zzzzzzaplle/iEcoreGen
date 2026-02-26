import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private GeographicalArea area;
    private Inhabitant inhabitant;
    private Mailman mailman;
    private Letter letter;
    private Parcel parcel;
    
    @Before
    public void setUp() {
        // Common setup that can be reused across tests
        area = new GeographicalArea();
        area.setAreaId("TestArea");
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = new GeographicalArea();
        mainStreet.setAreaId("MainStreet");
        
        // SetUp: Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        aaron.setMailmanId("A001");
        aaron.setName("Aaron");
        mainStreet.addMailman(aaron);
        
        // SetUp: Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        zoe.setInhabitantId("Z001");
        zoe.setName("Zoe");
        mainStreet.addInhabitant(zoe);
        
        // SetUp: Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        letter10.setMailId("L10");
        letter10.setAddressee(zoe);
        letter10.setContent("Letter content for Zoe");
        mainStreet.getRegisteredMails().add(letter10);
        mainStreet.assignMailToMailman(letter10, aaron);
        
        // SetUp: Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        parcel6.setMailId("P6");
        parcel6.setAddressee(zoe);
        parcel6.setWeightKg(2.5);
        parcel6.setDescription("Package for Zoe");
        mainStreet.getRegisteredMails().add(parcel6);
        mainStreet.assignMailToMailman(parcel6, aaron);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = mainStreet.listMailForInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        GeographicalArea marketSquare = new GeographicalArea();
        marketSquare.setAreaId("MarketSquare");
        
        // SetUp: Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        mia.setInhabitantId("M001");
        mia.setName("Mia");
        marketSquare.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listMailForInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea (no specific setup needed beyond area creation)
        GeographicalArea area = new GeographicalArea();
        area.setAreaId("AnyArea");
        
        // Create a non-existent inhabitant (not added to any area)
        Inhabitant noah = new Inhabitant();
        noah.setInhabitantId("N001");
        noah.setName("Noah");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listMailForInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        oldTown.setAreaId("OldTown");
        
        // SetUp: Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setInhabitantId("E001");
        ella.setName("Ella");
        oldTown.addInhabitant(ella);
        
        // SetUp: Add another inhabitant for Letter11
        Inhabitant otherInhabitant = new Inhabitant();
        otherInhabitant.setInhabitantId("O001");
        otherInhabitant.setName("Other");
        oldTown.addInhabitant(otherInhabitant);
        
        // SetUp: Add Mailman "Peter" to OldTown
        Mailman peter = new Mailman();
        peter.setMailmanId("P001");
        peter.setName("Peter");
        oldTown.addMailman(peter);
        
        // SetUp: Create Letter11 assigned to mailman "Peter" for other inhabitant
        Letter letter11 = new Letter();
        letter11.setMailId("L11");
        letter11.setAddressee(otherInhabitant);
        letter11.setContent("Letter for other inhabitant");
        oldTown.getRegisteredMails().add(letter11);
        oldTown.assignMailToMailman(letter11, peter);
        
        // SetUp: Create and assign Letter12 for Ella
        Letter letter12 = new Letter();
        letter12.setMailId("L12");
        letter12.setAddressee(ella);
        letter12.setContent("Letter for Ella");
        oldTown.getRegisteredMails().add(letter12);
        oldTown.assignMailToMailman(letter12, peter);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = oldTown.listMailForInhabitant(ella);
        
        // Expected Output: Letter12 only
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertFalse("Should not contain Letter11", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        GeographicalArea newDistrict = new GeographicalArea();
        newDistrict.setAreaId("NewDistrict");
        
        // SetUp: Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        ruby.setMailmanId("R001");
        ruby.setName("Ruby");
        newDistrict.addMailman(ruby);
        
        // SetUp: Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        luke.setInhabitantId("L001");
        luke.setName("Luke");
        newDistrict.addInhabitant(luke);
        
        // SetUp: Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        letter12.setMailId("L12");
        letter12.setAddressee(luke);
        letter12.setContent("First letter for Luke");
        newDistrict.getRegisteredMails().add(letter12);
        newDistrict.assignMailToMailman(letter12, ruby);
        
        // SetUp: Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        parcel7.setMailId("P7");
        parcel7.setAddressee(luke);
        parcel7.setWeightKg(1.8);
        parcel7.setDescription("First parcel for Luke");
        newDistrict.getRegisteredMails().add(parcel7);
        newDistrict.assignMailToMailman(parcel7, ruby);
        
        // SetUp: Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        letter13.setMailId("L13");
        letter13.setAddressee(luke);
        letter13.setContent("Second letter for Luke");
        newDistrict.getRegisteredMails().add(letter13);
        newDistrict.assignMailToMailman(letter13, ruby);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = newDistrict.listMailForInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
        assertTrue("Should contain Letter13", result.contains(letter13));
    }
}