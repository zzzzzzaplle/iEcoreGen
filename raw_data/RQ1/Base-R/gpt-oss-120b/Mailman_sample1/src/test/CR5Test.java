import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private MailService mailService;
    private GeographicalArea mainStreet;
    private GeographicalArea marketSquare;
    private GeographicalArea oldTown;
    private GeographicalArea newDistrict;
    
    @Before
    public void setUp() {
        mailService = new MailService();
        
        // Create geographical areas
        mainStreet = new GeographicalArea("MS1", "MainStreet");
        marketSquare = new GeographicalArea("MS2", "MarketSquare");
        oldTown = new GeographicalArea("OT1", "OldTown");
        newDistrict = new GeographicalArea("ND1", "NewDistrict");
        
        mailService.addArea(mainStreet);
        mailService.addArea(marketSquare);
        mailService.addArea(oldTown);
        mailService.addArea(newDistrict);
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        // Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman("M1", "Aaron", null);
        mailService.addMailman("MS1", aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant("I1", "Zoe", null);
        mailService.addInhabitant("MS1", zoe);
        
        // Create and assign: Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter("L10", zoe, "Subject10", "Content10");
        letter10.setAssignedMailman(aaron);
        mailService.registerMailItem(letter10);
        
        // Create and assign: Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel("P6", zoe, 2.5, "Small package");
        parcel6.setAssignedMailman(aaron);
        mailService.registerMailItem(parcel6);
        
        // Action: List all mail for Zoe
        List<MailItem> result = mailService.getMailItemsForInhabitant("I1");
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        // Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant("I2", "Mia", null);
        mailService.addInhabitant("MS2", mia);
        
        // Action: List all mail for Mia
        List<MailItem> result = mailService.getMailItemsForInhabitant("I2");
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail items exist", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        List<MailItem> result = mailService.getMailItemsForInhabitant("Noah");
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant("I3", "Ella", null);
        mailService.addInhabitant("OT1", ella);
        
        // Add Mailman "Peter" to OldTown
        Mailman peter = new Mailman("M2", "Peter", null);
        mailService.addMailman("OT1", peter);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        Letter letter11 = new Letter("L11", ella, "Subject11", "Content11");
        letter11.setAssignedMailman(peter);
        mailService.registerMailItem(letter11);
        
        // Create Letter12 for Ella (no assignment)
        Letter letter12 = new Letter("L12", ella, "Subject12", "Content12");
        mailService.registerMailItem(letter12);
        
        // Action: List all mail for Ella
        List<MailItem> result = mailService.getMailItemsForInhabitant("I3");
        
        // Expected Output: Letter12 (only assigned mail should be returned)
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain Letter11", result.contains(letter11));
        assertTrue("Should contain Letter12", result.contains(letter12));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman("M3", "Ruby", null);
        mailService.addMailman("ND1", ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant("I4", "Luke", null);
        mailService.addInhabitant("ND1", luke);
        
        // Create and assign: Letter12 for Luke (Ruby)
        Letter letter12 = new Letter("L12", luke, "Subject12", "Content12");
        letter12.setAssignedMailman(ruby);
        mailService.registerMailItem(letter12);
        
        // Create and assign: Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel("P7", luke, 5.0, "Medium package");
        parcel7.setAssignedMailman(ruby);
        mailService.registerMailItem(parcel7);
        
        // Create and assign: Letter13 for Luke (Ruby)
        Letter letter13 = new Letter("L13", luke, "Subject13", "Content13");
        letter13.setAssignedMailman(ruby);
        mailService.registerMailItem(letter13);
        
        // Action: List all mail for Luke
        List<MailItem> result = mailService.getMailItemsForInhabitant("I4");
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
        assertTrue("Should contain Letter13", result.contains(letter13));
    }
}