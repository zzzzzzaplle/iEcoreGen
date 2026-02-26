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
        // Add Mailman "Aaron" to MainStreet
        mailman = new Mailman("Aaron");
        area.addMailman(mailman);
        
        // Add Inhabitant "Zoe" to MainStreet
        inhabitant = new Inhabitant("Zoe");
        area.addInhabitant(inhabitant);
        
        // Create and assign: Letter10 for Zoe (Aaron) and Parcel6 for Zoe (Aaron)
        Letter letter10 = new Letter();
        Parcel parcel6 = new Parcel();
        area.assignRegisteredMailDeliver(mailman, inhabitant, letter10);
        area.assignRegisteredMailDeliver(mailman, inhabitant, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        // Add Inhabitant "Mia" to MarketSquare
        inhabitant = new Inhabitant("Mia");
        area.addInhabitant(inhabitant);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail items exist", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        Inhabitant nonExistentInhabitant = new Inhabitant("Noah");
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(nonExistentInhabitant);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        // Add Inhabitant "Ella" to OldTown
        inhabitant = new Inhabitant("Ella");
        area.addInhabitant(inhabitant);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Mailman peter = new Mailman("Peter");
        area.addMailman(peter);
        Inhabitant joseph = new Inhabitant("Joseph");
        area.addInhabitant(joseph);
        Letter letter11 = new Letter();
        area.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign: Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        area.assignRegisteredMailDeliver(peter, inhabitant, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain only 1 mail item", 1, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertFalse("Should not contain Letter11", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        // Add Mailman "Ruby" to NewDistrict
        mailman = new Mailman("Ruby");
        area.addMailman(mailman);
        
        // Add Inhabitant "Luke" to NewDistrict
        inhabitant = new Inhabitant("Luke");
        area.addInhabitant(inhabitant);
        
        // Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
        Letter letter12 = new Letter();
        Parcel parcel7 = new Parcel();
        Letter letter13 = new Letter();
        area.assignRegisteredMailDeliver(mailman, inhabitant, letter12);
        area.assignRegisteredMailDeliver(mailman, inhabitant, parcel7);
        area.assignRegisteredMailDeliver(mailman, inhabitant, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
        assertTrue("Should contain Letter13", result.contains(letter13));
    }
}