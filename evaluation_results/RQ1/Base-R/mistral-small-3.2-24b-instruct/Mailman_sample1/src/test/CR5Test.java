import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private MailDeliverySystem mailSystem;
    private GeographicalArea mainStreet;
    private GeographicalArea marketSquare;
    private GeographicalArea oldTown;
    private GeographicalArea newDistrict;
    private Mailman aaron;
    private Mailman peter;
    private Mailman ruby;
    private Inhabitant zoe;
    private Inhabitant mia;
    private Inhabitant ella;
    private Inhabitant luke;
    private Inhabitant noah;
    
    @Before
    public void setUp() {
        mailSystem = new MailDeliverySystem();
        
        // Create geographical areas
        mainStreet = new GeographicalArea("MainStreet");
        marketSquare = new GeographicalArea("MarketSquare");
        oldTown = new GeographicalArea("OldTown");
        newDistrict = new GeographicalArea("NewDistrict");
        
        // Create mailmen
        aaron = new Mailman("Aaron", null);
        peter = new Mailman("Peter", null);
        ruby = new Mailman("Ruby", null);
        
        // Create inhabitants
        zoe = new Inhabitant("Zoe", null);
        mia = new Inhabitant("Mia", null);
        ella = new Inhabitant("Ella", null);
        luke = new Inhabitant("Luke", null);
        noah = new Inhabitant("Noah", null);
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        // Add Mailman "Aaron" to MainStreet
        mailSystem.addMailman(aaron, mainStreet);
        
        // Add Inhabitant "Zoe" to MainStreet
        mailSystem.addInhabitant(zoe, mainStreet);
        
        // Create and assign: Letter10 for Zoe (Aaron) and Parcel6 for Zoe (Aaron)
        Letter letter10 = new Letter(zoe, null, mainStreet);
        Parcel parcel6 = new Parcel(zoe, null, mainStreet);
        
        mailSystem.assignMail(letter10, aaron);
        mailSystem.assignMail(parcel6, aaron);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = mailSystem.listMailItemsForInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        // Add Inhabitant "Mia" to MarketSquare
        mailSystem.addInhabitant(mia, marketSquare);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = mailSystem.listMailItemsForInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = mailSystem.listMailItemsForInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        // Add Inhabitant "Ella" to OldTown
        mailSystem.addInhabitant(ella, oldTown);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        mailSystem.addMailman(peter, oldTown);
        Letter letter11 = new Letter(ella, null, oldTown);
        mailSystem.assignMail(letter11, peter);
        
        // Create and assign: Letter12 for Ella
        Letter letter12 = new Letter(ella, null, oldTown);
        mailSystem.assignMail(letter12, peter);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = mailSystem.listMailItemsForInhabitant(ella);
        
        // Expected Output: Should contain both assigned letters
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter11", result.contains(letter11));
        assertTrue("Should contain letter12", result.contains(letter12));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        // Add Mailman "Ruby" to NewDistrict
        mailSystem.addMailman(ruby, newDistrict);
        
        // Add Inhabitant "Luke" to NewDistrict
        mailSystem.addInhabitant(luke, newDistrict);
        
        // Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
        Letter letter12 = new Letter(luke, null, newDistrict);
        Parcel parcel7 = new Parcel(luke, null, newDistrict);
        Letter letter13 = new Letter(luke, null, newDistrict);
        
        mailSystem.assignMail(letter12, ruby);
        mailSystem.assignMail(parcel7, ruby);
        mailSystem.assignMail(letter13, ruby);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = mailSystem.listMailItemsForInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}