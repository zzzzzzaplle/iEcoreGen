import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private GeographicalArea geographicalArea;
    private Mailman mailmanAaron;
    private Mailman mailmanRuby;
    private Mailman mailmanPeter;
    private Inhabitant inhabitantZoe;
    private Inhabitant inhabitantMia;
    private Inhabitant inhabitantElla;
    private Inhabitant inhabitantLuke;
    private Inhabitant nonExistentNoah;
    private Letter letter10;
    private Parcel parcel6;
    private Letter letter11;
    private Letter letter12;
    private Parcel parcel7;
    private Letter letter13;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        geographicalArea = null; // Will be reset in each test
        mailmanAaron = new Mailman();
        mailmanRuby = new Mailman();
        mailmanPeter = new Mailman();
        inhabitantZoe = new Inhabitant();
        inhabitantMia = new Inhabitant();
        inhabitantElla = new Inhabitant();
        inhabitantLuke = new Inhabitant();
        nonExistentNoah = new Inhabitant();
        letter10 = new Letter();
        parcel6 = new Parcel();
        letter11 = new Letter();
        letter12 = new Letter();
        parcel7 = new Parcel();
        letter13 = new Letter();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        geographicalArea = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        geographicalArea.addMailman(mailmanAaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        geographicalArea.addInhabitant(inhabitantZoe);
        
        // Create and assign: Letter10 for Zoe (Aaron), Parcel6 for Zoe (Aaron)
        geographicalArea.assignRegisteredMailDeliver(mailmanAaron, inhabitantZoe, letter10);
        geographicalArea.assignRegisteredMailDeliver(mailmanAaron, inhabitantZoe, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = geographicalArea.listRegisteredMail(inhabitantZoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter10));
        assertTrue(result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        geographicalArea = new GeographicalArea();
        
        // Add Inhabitant "Mia" to MarketSquare
        geographicalArea.addInhabitant(inhabitantMia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = geographicalArea.listRegisteredMail(inhabitantMia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create empty GeographicalArea
        geographicalArea = new GeographicalArea();
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = geographicalArea.listRegisteredMail(nonExistentNoah);
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        geographicalArea = new GeographicalArea();
        
        // Add Inhabitant "Ella" to OldTown
        geographicalArea.addInhabitant(inhabitantElla);
        
        // Create Letter11 assigned to mailman "Peter" (but not assigned to Ella)
        // Note: This mail is not assigned to Ella, so it shouldn't appear in the result
        Letter otherLetter = new Letter();
        Inhabitant otherInhabitant = new Inhabitant();
        geographicalArea.addMailman(mailmanPeter);
        geographicalArea.addInhabitant(otherInhabitant);
        geographicalArea.assignRegisteredMailDeliver(mailmanPeter, otherInhabitant, otherLetter);
        
        // Create and assign: Letter12 for Ella
        geographicalArea.assignRegisteredMailDeliver(mailmanPeter, inhabitantElla, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = geographicalArea.listRegisteredMail(inhabitantElla);
        
        // Expected Output: Letter12 only (not letter11)
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(letter12, result.get(0));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        geographicalArea = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        geographicalArea.addMailman(mailmanRuby);
        
        // Add Inhabitant "Luke" to NewDistrict
        geographicalArea.addInhabitant(inhabitantLuke);
        
        // Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
        geographicalArea.assignRegisteredMailDeliver(mailmanRuby, inhabitantLuke, letter12);
        geographicalArea.assignRegisteredMailDeliver(mailmanRuby, inhabitantLuke, parcel7);
        geographicalArea.assignRegisteredMailDeliver(mailmanRuby, inhabitantLuke, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = geographicalArea.listRegisteredMail(inhabitantLuke);
        
        // Expected Output: List containing all three mail items
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel7));
        assertTrue(result.contains(letter13));
    }
}