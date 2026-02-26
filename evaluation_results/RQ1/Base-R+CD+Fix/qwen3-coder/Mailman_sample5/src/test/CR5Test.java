import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private GeographicalArea area;
    private Mailman mailman;
    private Inhabitant inhabitant;
    
    @Before
    public void setUp() {
        area = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        area = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        area.addMailman(aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        area.addInhabitant(zoe);
        
        // Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        letter10.setAddressee(zoe);
        letter10.setCarrier(aaron);
        area.getAllMails().add(letter10);
        
        // Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        parcel6.setAddressee(zoe);
        parcel6.setCarrier(aaron);
        area.getAllMails().add(parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        area = new GeographicalArea();
        
        // Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        area.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea (implied by @Before)
        
        // Create a non-existent inhabitant "Noah" (not added to area)
        Inhabitant noah = new Inhabitant();
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea();
        
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        area.addInhabitant(ella);
        
        // Add Mailman "Peter" to OldTown
        Mailman peter = new Mailman();
        area.addMailman(peter);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Inhabitant joseph = new Inhabitant();
        area.addInhabitant(joseph);
        Letter letter11 = new Letter();
        letter11.setAddressee(joseph);
        letter11.setCarrier(peter);
        area.getAllMails().add(letter11);
        
        // Create and assign Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        letter12.setAddressee(ella);
        letter12.setCarrier(peter);
        area.getAllMails().add(letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertEquals("Should contain letter12", letter12, result.get(0));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        area = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        area.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        area.addInhabitant(luke);
        
        // Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        letter12.setAddressee(luke);
        letter12.setCarrier(ruby);
        area.getAllMails().add(letter12);
        
        // Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        parcel7.setAddressee(luke);
        parcel7.setCarrier(ruby);
        area.getAllMails().add(parcel7);
        
        // Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        letter13.setAddressee(luke);
        letter13.setCarrier(ruby);
        area.getAllMails().add(letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}