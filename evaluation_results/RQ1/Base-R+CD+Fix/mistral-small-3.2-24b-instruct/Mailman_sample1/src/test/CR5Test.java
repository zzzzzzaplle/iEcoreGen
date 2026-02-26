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
        // Initialize common test objects before each test
        geographicalArea = new GeographicalArea();
        mailman = new Mailman();
        inhabitant = new Inhabitant();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = new GeographicalArea();
        
        // SetUp: Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        aaron.setName("Aaron");
        aaron.setId("M001");
        mainStreet.addMailman(aaron);
        
        // SetUp: Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        zoe.setName("Zoe");
        zoe.setId("I001");
        mainStreet.addInhabitant(zoe);
        
        // SetUp: Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, letter10);
        
        // SetUp: Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = mainStreet.listRegisteredMailWithInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        GeographicalArea marketSquare = new GeographicalArea();
        
        // SetUp: Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        mia.setName("Mia");
        mia.setId("I002");
        marketSquare.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea (no setup needed for inhabitants)
        GeographicalArea area = new GeographicalArea();
        
        // Create a non-existent inhabitant that's not added to the area
        Inhabitant noah = new Inhabitant();
        noah.setName("Noah");
        noah.setId("I999");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // SetUp: Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setName("Ella");
        ella.setId("I003");
        oldTown.addInhabitant(ella);
        
        // SetUp: Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Mailman peter = new Mailman();
        peter.setName("Peter");
        peter.setId("M002");
        oldTown.addMailman(peter);
        
        Inhabitant joseph = new Inhabitant();
        joseph.setName("Joseph");
        joseph.setId("I004");
        oldTown.addInhabitant(joseph);
        
        Letter letter11 = new Letter();
        oldTown.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // SetUp: Create and assign Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        oldTown.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = oldTown.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 mail item", 1, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertFalse("Should not contain letter11 (assigned to Joseph)", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        GeographicalArea newDistrict = new GeographicalArea();
        
        // SetUp: Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        ruby.setName("Ruby");
        ruby.setId("M003");
        newDistrict.addMailman(ruby);
        
        // SetUp: Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        luke.setName("Luke");
        luke.setId("I005");
        newDistrict.addInhabitant(luke);
        
        // SetUp: Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        newDistrict.assignRegisteredMailDeliver(ruby, luke, letter12);
        
        // SetUp: Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        newDistrict.assignRegisteredMailDeliver(ruby, luke, parcel7);
        
        // SetUp: Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        newDistrict.assignRegisteredMailDeliver(ruby, luke, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = newDistrict.listRegisteredMailWithInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}