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
        Mailman aaron = new Mailman("1", "Aaron");
        geographicalArea.addMailman(aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant("1", "Zoe");
        geographicalArea.addInhabitant(zoe);
        
        // Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(aaron, zoe, letter10);
        
        // Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        geographicalArea.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(zoe);
        
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
        Inhabitant mia = new Inhabitant("1", "Mia");
        geographicalArea.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }

    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        Inhabitant noah = new Inhabitant("99", "Noah");
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull(result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        geographicalArea = new GeographicalArea();
        
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant("1", "Ella");
        geographicalArea.addInhabitant(ella);
        
        // Add Mailman "Peter" to OldTown
        Mailman peter = new Mailman("1", "Peter");
        geographicalArea.addMailman(peter);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Inhabitant joseph = new Inhabitant("2", "Joseph");
        geographicalArea.addInhabitant(joseph);
        Letter letter11 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        geographicalArea.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(letter12, result.get(0));
    }

    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        geographicalArea = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman("1", "Ruby");
        geographicalArea.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant("1", "Luke");
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
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel7));
        assertTrue(result.contains(letter13));
    }
}