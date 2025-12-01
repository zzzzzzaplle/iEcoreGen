import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    private GeographicalArea geographicalArea;
    private Mailman mailman;
    private Inhabitant inhabitant;
    
    @Before
    public void setUp() {
        // Reset geographical area before each test
        geographicalArea = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        geographicalArea = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        mailman = new Mailman("M001", "Aaron");
        geographicalArea.addMailman(mailman);
        
        // Add Inhabitant "Zoe" to MainStreet
        inhabitant = new Inhabitant("I001", "Zoe");
        geographicalArea.addInhabitant(inhabitant);
        
        // Create and assign: Letter10 for Zoe (Aaron) and Parcel6 for Zoe (Aaron)
        Letter letter10 = new Letter(mailman, inhabitant);
        Parcel parcel6 = new Parcel(mailman, inhabitant);
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, letter10);
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        geographicalArea = new GeographicalArea();
        
        // Add Inhabitant "Mia" to MarketSquare
        inhabitant = new Inhabitant("I002", "Mia");
        geographicalArea.addInhabitant(inhabitant);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea (no setup needed beyond default)
        geographicalArea = new GeographicalArea();
        
        // Create non-existent inhabitant "Noah" (not added to geographical area)
        Inhabitant nonExistentInhabitant = new Inhabitant("I999", "Noah");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(nonExistentInhabitant);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        geographicalArea = new GeographicalArea();
        
        // Add Inhabitant "Ella" to OldTown
        inhabitant = new Inhabitant("I003", "Ella");
        geographicalArea.addInhabitant(inhabitant);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Mailman peter = new Mailman("M002", "Peter");
        geographicalArea.addMailman(peter);
        Inhabitant joseph = new Inhabitant("I004", "Joseph");
        geographicalArea.addInhabitant(joseph);
        Letter letter11 = new Letter(peter, joseph);
        geographicalArea.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign: Letter12 for Ella (Peter)
        Letter letter12 = new Letter(peter, inhabitant);
        geographicalArea.assignRegisteredMailDeliver(peter, inhabitant, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertFalse("Should not contain Letter11 (for Joseph)", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        geographicalArea = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        mailman = new Mailman("M003", "Ruby");
        geographicalArea.addMailman(mailman);
        
        // Add Inhabitant "Luke" to NewDistrict
        inhabitant = new Inhabitant("I005", "Luke");
        geographicalArea.addInhabitant(inhabitant);
        
        // Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
        Letter letter12 = new Letter(mailman, inhabitant);
        Parcel parcel7 = new Parcel(mailman, inhabitant);
        Letter letter13 = new Letter(mailman, inhabitant);
        
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, letter12);
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, parcel7);
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
        assertTrue("Should contain Letter13", result.contains(letter13));
    }
}