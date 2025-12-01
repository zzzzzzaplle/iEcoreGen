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
        geographicalArea = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        geographicalArea = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        mailman = new Mailman();
        geographicalArea.addMailman(mailman);
        
        // Add Inhabitant "Zoe" to MainStreet
        inhabitant = new Inhabitant();
        geographicalArea.addInhabitant(inhabitant);
        
        // Create and assign: Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, letter10);
        
        // Create and assign: Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = geographicalArea.listRegisteredMailForInhabitant(inhabitant);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain exactly 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        geographicalArea = new GeographicalArea();
        
        // Add Inhabitant "Mia" to MarketSquare
        inhabitant = new Inhabitant();
        geographicalArea.addInhabitant(inhabitant);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = geographicalArea.listRegisteredMailForInhabitant(inhabitant);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail items exist", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create non-existent inhabitant "Noah"
        inhabitant = new Inhabitant();
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = geographicalArea.listRegisteredMailForInhabitant(inhabitant);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        geographicalArea = new GeographicalArea();
        
        // Add Inhabitant "Ella" to OldTown
        inhabitant = new Inhabitant();
        geographicalArea.addInhabitant(inhabitant);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        Mailman peter = new Mailman();
        geographicalArea.addMailman(peter);
        Letter letter11 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(peter, inhabitant, letter11);
        
        // Create and assign: Letter12 for Ella
        Letter letter12 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(peter, inhabitant, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = geographicalArea.listRegisteredMailForInhabitant(inhabitant);
        
        // Expected Output: Letter12 (and also letter11)
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain exactly 2 mail items", 2, result.size());
        assertTrue("Should contain letter11", result.contains(letter11));
        assertTrue("Should contain letter12", result.contains(letter12));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        geographicalArea = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        mailman = new Mailman();
        geographicalArea.addMailman(mailman);
        
        // Add Inhabitant "Luke" to NewDistrict
        inhabitant = new Inhabitant();
        geographicalArea.addInhabitant(inhabitant);
        
        // Create and assign: Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, letter12);
        
        // Create and assign: Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, parcel7);
        
        // Create and assign: Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = geographicalArea.listRegisteredMailForInhabitant(inhabitant);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain exactly 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}