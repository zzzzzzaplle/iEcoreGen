import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

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
        
        // SetUp: Add Mailman "Aaron" to MainStreet
        mailman = new Mailman();
        mailman.setId("1");
        mailman.setName("Aaron");
        area.addMailman(mailman);
        
        // SetUp: Add Inhabitant "Zoe" to MainStreet
        inhabitant = new Inhabitant();
        inhabitant.setId("101");
        inhabitant.setName("Zoe");
        area.addInhabitant(inhabitant);
        
        // SetUp: Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        letter10.setId("Letter10");
        letter10.setCarrier(mailman);
        letter10.setAddressee(inhabitant);
        area.getAllMails().add(letter10);
        
        // SetUp: Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        parcel6.setId("Parcel6");
        parcel6.setCarrier(mailman);
        parcel6.setAddressee(inhabitant);
        area.getAllMails().add(parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        area = new GeographicalArea();
        
        // SetUp: Add Inhabitant "Mia" to MarketSquare
        inhabitant = new Inhabitant();
        inhabitant.setId("102");
        inhabitant.setName("Mia");
        area.addInhabitant(inhabitant);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea (no inhabitants added)
        area = new GeographicalArea();
        
        // Create a non-existent inhabitant
        Inhabitant nonExistentInhabitant = new Inhabitant();
        nonExistentInhabitant.setId("999");
        nonExistentInhabitant.setName("Noah");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(nonExistentInhabitant);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea();
        
        // SetUp: Add Inhabitant "Ella" to OldTown
        inhabitant = new Inhabitant();
        inhabitant.setId("103");
        inhabitant.setName("Ella");
        area.addInhabitant(inhabitant);
        
        // SetUp: Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Inhabitant joseph = new Inhabitant();
        joseph.setId("104");
        joseph.setName("Joseph");
        area.addInhabitant(joseph);
        
        Mailman peter = new Mailman();
        peter.setId("2");
        peter.setName("Peter");
        area.addMailman(peter);
        
        Letter letter11 = new Letter();
        letter11.setId("Letter11");
        letter11.setCarrier(peter);
        letter11.setAddressee(joseph);
        area.getAllMails().add(letter11);
        
        // SetUp: Create and assign Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        letter12.setId("Letter12");
        letter12.setCarrier(peter);
        letter12.setAddressee(inhabitant);
        area.getAllMails().add(letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain only 1 mail item", 1, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertFalse("Should not contain Letter11", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        area = new GeographicalArea();
        
        // SetUp: Add Mailman "Ruby" to NewDistrict
        mailman = new Mailman();
        mailman.setId("3");
        mailman.setName("Ruby");
        area.addMailman(mailman);
        
        // SetUp: Add Inhabitant "Luke" to NewDistrict
        inhabitant = new Inhabitant();
        inhabitant.setId("105");
        inhabitant.setName("Luke");
        area.addInhabitant(inhabitant);
        
        // SetUp: Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        letter12.setId("Letter12");
        letter12.setCarrier(mailman);
        letter12.setAddressee(inhabitant);
        area.getAllMails().add(letter12);
        
        // SetUp: Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        parcel7.setId("Parcel7");
        parcel7.setCarrier(mailman);
        parcel7.setAddressee(inhabitant);
        area.getAllMails().add(parcel7);
        
        // SetUp: Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        letter13.setId("Letter13");
        letter13.setCarrier(mailman);
        letter13.setAddressee(inhabitant);
        area.getAllMails().add(letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
        assertTrue("Should contain Letter13", result.contains(letter13));
    }
}