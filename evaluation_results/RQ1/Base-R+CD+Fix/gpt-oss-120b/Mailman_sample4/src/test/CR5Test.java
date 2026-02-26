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
        // Common setup that runs before each test
        area = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        area = new GeographicalArea(); // Explicitly create MainStreet area
        
        // SetUp: Add Mailman "Aaron" to MainStreet
        mailman = new Mailman();
        mailman.setName("Aaron");
        mailman.setId("M001");
        area.addMailman(mailman);
        
        // SetUp: Add Inhabitant "Zoe" to MainStreet
        inhabitant = new Inhabitant();
        inhabitant.setName("Zoe");
        inhabitant.setId("I001");
        area.addInhabitant(inhabitant);
        
        // SetUp: Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        letter10.setAddressee(inhabitant);
        letter10.setCarrier(mailman);
        area.addMail(letter10);
        
        // SetUp: Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        parcel6.setAddressee(inhabitant);
        parcel6.setCarrier(mailman);
        area.addMail(parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain exactly 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        area = new GeographicalArea(); // Explicitly create MarketSquare area
        
        // SetUp: Add Inhabitant "Mia" to MarketSquare
        inhabitant = new Inhabitant();
        inhabitant.setName("Mia");
        inhabitant.setId("I002");
        area.addInhabitant(inhabitant);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail items exist", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea (implicitly done in @Before)
        
        // Create a non-existent inhabitant
        Inhabitant nonExistentInhabitant = new Inhabitant();
        nonExistentInhabitant.setName("Noah");
        nonExistentInhabitant.setId("I999"); // Not added to area
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(nonExistentInhabitant);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea(); // Explicitly create OldTown area
        
        // SetUp: Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setName("Ella");
        ella.setId("I003");
        area.addInhabitant(ella);
        
        // SetUp: Add Inhabitant "Joseph" to OldTown
        Inhabitant joseph = new Inhabitant();
        joseph.setName("Joseph");
        joseph.setId("I004");
        area.addInhabitant(joseph);
        
        // SetUp: Add Mailman "Peter" to OldTown
        Mailman peter = new Mailman();
        peter.setName("Peter");
        peter.setId("M002");
        area.addMailman(peter);
        
        // SetUp: Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Letter letter11 = new Letter();
        letter11.setAddressee(joseph);
        letter11.setCarrier(peter);
        area.addMail(letter11);
        
        // SetUp: Create and assign Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        letter12.setAddressee(ella);
        letter12.setCarrier(peter);
        area.addMail(letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertFalse("Should not contain Letter11 (addressed to Joseph)", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        area = new GeographicalArea(); // Explicitly create NewDistrict area
        
        // SetUp: Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        ruby.setName("Ruby");
        ruby.setId("M003");
        area.addMailman(ruby);
        
        // SetUp: Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        luke.setName("Luke");
        luke.setId("I005");
        area.addInhabitant(luke);
        
        // SetUp: Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        letter12.setAddressee(luke);
        letter12.setCarrier(ruby);
        area.addMail(letter12);
        
        // SetUp: Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        parcel7.setAddressee(luke);
        parcel7.setCarrier(ruby);
        area.addMail(parcel7);
        
        // SetUp: Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        letter13.setAddressee(luke);
        letter13.setCarrier(ruby);
        area.addMail(letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain exactly 3 mail items", 3, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
        assertTrue("Should contain Letter13", result.contains(letter13));
    }
}