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
        mailman.setId("1");
        mailman.setName("Aaron");
        geographicalArea.addMailman(mailman);
        
        // Add Inhabitant "Zoe" to MainStreet
        inhabitant = new Inhabitant();
        inhabitant.setId("1");
        inhabitant.setName("Zoe");
        geographicalArea.addInhabitant(inhabitant);
        
        // Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, letter10);
        
        // Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        geographicalArea = new GeographicalArea();
        
        // Add Inhabitant "Mia" to MarketSquare
        inhabitant = new Inhabitant();
        inhabitant.setId("1");
        inhabitant.setName("Mia");
        geographicalArea.addInhabitant(inhabitant);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea (no setup needed beyond basic instance)
        geographicalArea = new GeographicalArea();
        
        // Create non-existent inhabitant "Noah"
        Inhabitant nonExistentInhabitant = new Inhabitant();
        nonExistentInhabitant.setId("999");
        nonExistentInhabitant.setName("Noah");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(nonExistentInhabitant);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        geographicalArea = new GeographicalArea();
        
        // Add Mailman "Peter" to OldTown
        Mailman peter = new Mailman();
        peter.setId("1");
        peter.setName("Peter");
        geographicalArea.addMailman(peter);
        
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setId("1");
        ella.setName("Ella");
        geographicalArea.addInhabitant(ella);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Inhabitant joseph = new Inhabitant();
        joseph.setId("2");
        joseph.setName("Joseph");
        geographicalArea.addInhabitant(joseph);
        
        Letter letter11 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertFalse("Should not contain letter11", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        geographicalArea = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        ruby.setId("1");
        ruby.setName("Ruby");
        geographicalArea.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        luke.setId("1");
        luke.setName("Luke");
        geographicalArea.addInhabitant(luke);
        
        // Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(ruby, luke, letter12);
        
        // Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        geographicalArea.assignRegisteredMailDeliver(ruby, luke, parcel7);
        
        // Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(ruby, luke, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}