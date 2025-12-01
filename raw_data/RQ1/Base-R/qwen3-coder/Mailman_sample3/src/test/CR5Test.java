import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private MailDeliverySystem system;
    private Area mainStreet;
    private Area marketSquare;
    private Area oldTown;
    private Area newDistrict;
    private Mailman aaron;
    private Mailman ruby;
    private Mailman peter;
    private Inhabitant zoe;
    private Inhabitant mia;
    private Inhabitant ella;
    private Inhabitant luke;
    
    @Before
    public void setUp() {
        system = new MailDeliverySystem();
        
        // Create areas
        mainStreet = new Area();
        mainStreet.setName("MainStreet");
        
        marketSquare = new Area();
        marketSquare.setName("MarketSquare");
        
        oldTown = new Area();
        oldTown.setName("OldTown");
        
        newDistrict = new Area();
        newDistrict.setName("NewDistrict");
        
        // Add areas to system
        system.getAreas().add(mainStreet);
        system.getAreas().add(marketSquare);
        system.getAreas().add(oldTown);
        system.getAreas().add(newDistrict);
        
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
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        // Add Mailman "Aaron" to MainStreet
        system.addMailman(aaron, mainStreet);
        
        // Add Inhabitant "Zoe" to MainStreet
        system.addInhabitant(zoe, mainStreet);
        
        // Create and assign: Letter10 for Zoe (Aaron), Parcel6 for Zoe (Aaron)
        Letter letter10 = new Letter();
        Parcel parcel6 = new Parcel();
        
        system.assignMailmanToDeliverMail(letter10, aaron, zoe);
        system.assignMailmanToDeliverMail(parcel6, aaron, zoe);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = system.getMailForInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain exactly 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        // Add Inhabitant "Mia" to MarketSquare
        system.addInhabitant(mia, marketSquare);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = system.getMailForInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail items exist", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Create a non-existent inhabitant
        Inhabitant noah = new Inhabitant();
        noah.setName("Noah");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = system.getMailForInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        // Add Mailman "Peter" to OldTown
        system.addMailman(peter, oldTown);
        
        // Add Inhabitant "Ella" to OldTown
        system.addInhabitant(ella, oldTown);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown" without specifying addressee
        Letter letter11 = new Letter();
        letter11.setDeliveringMailman(peter);
        oldTown.getDeliveries().add(letter11);
        
        // Create and assign: Letter12 for Ella
        Letter letter12 = new Letter();
        system.assignMailmanToDeliverMail(letter12, peter, ella);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = system.getMailForInhabitant(ella);
        
        // Expected Output: Letter12 (only mail specifically addressed to Ella)
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertFalse("Should not contain letter11 (not addressed to Ella)", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        // Add Mailman "Ruby" to NewDistrict
        system.addMailman(ruby, newDistrict);
        
        // Add Inhabitant "Luke" to NewDistrict
        system.addInhabitant(luke, newDistrict);
        
        // Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
        Letter letter12 = new Letter();
        Parcel parcel7 = new Parcel();
        Letter letter13 = new Letter();
        
        system.assignMailmanToDeliverMail(letter12, ruby, luke);
        system.assignMailmanToDeliverMail(parcel7, ruby, luke);
        system.assignMailmanToDeliverMail(letter13, ruby, luke);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = system.getMailForInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain exactly 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}