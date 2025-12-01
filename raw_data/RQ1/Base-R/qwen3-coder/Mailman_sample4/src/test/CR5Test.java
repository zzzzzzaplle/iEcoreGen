import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private Area area;
    private Inhabitant zoe, mia, ella, luke, noah;
    private Mailman aaron, ruby, peter;
    private Letter letter10, letter11, letter12, letter13;
    private Parcel parcel6, parcel7;
    
    @Before
    public void setUp() {
        // Create test objects that can be reused across tests
        area = new Area();
        zoe = new Inhabitant();
        mia = new Inhabitant();
        ella = new Inhabitant();
        luke = new Inhabitant();
        noah = new Inhabitant();
        
        aaron = new Mailman();
        ruby = new Mailman();
        peter = new Mailman();
        
        letter10 = new Letter();
        letter11 = new Letter();
        letter12 = new Letter();
        letter13 = new Letter();
        
        parcel6 = new Parcel();
        parcel7 = new Parcel();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        area.setName("MainStreet");
        
        // SetUp: Add Mailman "Aaron" to MainStreet
        aaron.setName("Aaron");
        area.addMailman(aaron);
        
        // SetUp: Add Inhabitant "Zoe" to MainStreet
        zoe.setName("Zoe");
        area.addInhabitant(zoe);
        
        // SetUp: Create and assign Letter10 for Zoe (Aaron)
        letter10.setAddressee(zoe);
        letter10.setMailman(aaron);
        area.getRegisteredMails().add(letter10);
        
        // SetUp: Create and assign Parcel6 for Zoe (Aaron)
        parcel6.setAddressee(zoe);
        parcel6.setMailman(aaron);
        area.getRegisteredMails().add(parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = area.getMailForInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        area.setName("MarketSquare");
        
        // SetUp: Add Inhabitant "Mia" to MarketSquare
        mia.setName("Mia");
        area.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.getMailForInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create an area but don't add Noah to it
        area.setName("SomeArea");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.getMailForInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        area.setName("OldTown");
        
        // SetUp: Add Inhabitant "Ella" to OldTown
        ella.setName("Ella");
        area.addInhabitant(ella);
        
        // SetUp: Create Letter11 assigned to mailman "Peter" in "OldTown"
        peter.setName("Peter");
        area.addMailman(peter);
        letter11.setAddressee(ella);
        letter11.setMailman(peter);
        area.getRegisteredMails().add(letter11);
        
        // SetUp: Create and assign Letter12 for Ella
        letter12.setAddressee(ella);
        letter12.setMailman(peter);
        area.getRegisteredMails().add(letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = area.getMailForInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter11", result.contains(letter11));
        assertTrue("Should contain letter12", result.contains(letter12));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        area.setName("NewDistrict");
        
        // SetUp: Add Mailman "Ruby" to NewDistrict
        ruby.setName("Ruby");
        area.addMailman(ruby);
        
        // SetUp: Add Inhabitant "Luke" to NewDistrict
        luke.setName("Luke");
        area.addInhabitant(luke);
        
        // SetUp: Create and assign Letter12 for Luke (Ruby)
        letter12.setAddressee(luke);
        letter12.setMailman(ruby);
        area.getRegisteredMails().add(letter12);
        
        // SetUp: Create and assign Parcel7 for Luke (Ruby)
        parcel7.setAddressee(luke);
        parcel7.setMailman(ruby);
        area.getRegisteredMails().add(parcel7);
        
        // SetUp: Create and assign Letter13 for Luke (Ruby)
        letter13.setAddressee(luke);
        letter13.setMailman(ruby);
        area.getRegisteredMails().add(letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = area.getMailForInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}