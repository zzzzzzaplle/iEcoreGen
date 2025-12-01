import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private MailDeliverySystem mailDeliverySystem;
    
    @Before
    public void setUp() {
        mailDeliverySystem = new MailDeliverySystem();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = new GeographicalArea();
        mainStreet.setName("MainStreet");
        
        // Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        aaron.setName("Aaron");
        mailDeliverySystem.addMailman(mainStreet, aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        zoe.setName("Zoe");
        mailDeliverySystem.addInhabitant(mainStreet, zoe);
        
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
        List<RegisteredMail> result = mailDeliverySystem.getRegisteredMailItems(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter10));
        assertTrue(result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        GeographicalArea marketSquare = new GeographicalArea();
        marketSquare.setName("MarketSquare");
        
        // Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        mia.setName("Mia");
        mailDeliverySystem.addInhabitant(marketSquare, mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = mailDeliverySystem.getRegisteredMailItems(mia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Create a non-existent inhabitant
        Inhabitant noah = new Inhabitant();
        noah.setName("Noah");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = mailDeliverySystem.getRegisteredMailItems(noah);
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        oldTown.setName("OldTown");
        
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setName("Ella");
        mailDeliverySystem.addInhabitant(oldTown, ella);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        Mailman peter = new Mailman();
        peter.setName("Peter");
        mailDeliverySystem.addMailman(oldTown, peter);
        
        Letter letter11 = new Letter();
        letter11.setAddressee(ella);
        letter11.assignMailman(peter);
        oldTown.addRegisteredMail(letter11);
        
        // Create and assign Letter12 for Ella
        Letter letter12 = new Letter();
        letter12.setAddressee(ella);
        oldTown.addRegisteredMail(letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = mailDeliverySystem.getRegisteredMailItems(ella);
        
        // Expected Output: Letter12 (only assigned mail)
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(letter12));
        assertFalse(result.contains(letter11)); // letter11 is not assigned to a mailman
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        GeographicalArea newDistrict = new GeographicalArea();
        newDistrict.setName("NewDistrict");
        
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        ruby.setName("Ruby");
        mailDeliverySystem.addMailman(newDistrict, ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        luke.setName("Luke");
        mailDeliverySystem.addInhabitant(newDistrict, luke);
        
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
        List<RegisteredMail> result = mailDeliverySystem.getRegisteredMailItems(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel7));
        assertTrue(result.contains(letter13));
    }
}