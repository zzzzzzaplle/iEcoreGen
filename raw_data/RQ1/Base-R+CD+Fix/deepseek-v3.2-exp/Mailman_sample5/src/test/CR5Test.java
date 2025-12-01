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
        // Common setup that runs before each test
        geographicalArea = new GeographicalArea();
        mailman = new Mailman();
        inhabitant = new Inhabitant();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        geographicalArea = new GeographicalArea();
        
        // SetUp: Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        aaron.setName("Aaron");
        aaron.setId("M001");
        geographicalArea.addMailman(aaron);
        
        // SetUp: Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        zoe.setName("Zoe");
        zoe.setId("I001");
        zoe.setAddress("123 Main Street");
        geographicalArea.addInhabitant(zoe);
        
        // SetUp: Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        letter10.setTrackingNumber("LETTER10");
        letter10.setWeight(0.5);
        geographicalArea.assignRegisteredMailDeliver(aaron, zoe, letter10);
        
        // SetUp: Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        parcel6.setTrackingNumber("PARCEL6");
        parcel6.setWeight(2.5);
        parcel6.setDimensions(30.0);
        geographicalArea.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(zoe);
        
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
        
        // SetUp: Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        mia.setName("Mia");
        mia.setId("I002");
        mia.setAddress("456 Market Square");
        geographicalArea.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea (empty)
        geographicalArea = new GeographicalArea();
        
        // Action: List all mail for non-existent "Noah"
        Inhabitant noah = new Inhabitant();
        noah.setName("Noah");
        noah.setId("I999");
        noah.setAddress("999 Non-existent Street");
        
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        geographicalArea = new GeographicalArea();
        
        // SetUp: Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setName("Ella");
        ella.setId("I003");
        ella.setAddress("789 Old Town Road");
        geographicalArea.addInhabitant(ella);
        
        // SetUp: Add Mailman "Peter" to OldTown
        Mailman peter = new Mailman();
        peter.setName("Peter");
        peter.setId("M002");
        geographicalArea.addMailman(peter);
        
        // SetUp: Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Letter letter11 = new Letter();
        letter11.setTrackingNumber("LETTER11");
        letter11.setWeight(0.3);
        
        Inhabitant joseph = new Inhabitant();
        joseph.setName("Joseph");
        joseph.setId("I004");
        joseph.setAddress("101 Old Town Road");
        geographicalArea.addInhabitant(joseph);
        
        geographicalArea.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // SetUp: Create and assign Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        letter12.setTrackingNumber("LETTER12");
        letter12.setWeight(0.4);
        geographicalArea.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 mail item", 1, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertFalse("Should not contain letter11", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        geographicalArea = new GeographicalArea();
        
        // SetUp: Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        ruby.setName("Ruby");
        ruby.setId("M003");
        geographicalArea.addMailman(ruby);
        
        // SetUp: Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        luke.setName("Luke");
        luke.setId("I005");
        luke.setAddress("222 New District Ave");
        geographicalArea.addInhabitant(luke);
        
        // SetUp: Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        letter12.setTrackingNumber("LETTER12");
        letter12.setWeight(0.6);
        geographicalArea.assignRegisteredMailDeliver(ruby, luke, letter12);
        
        // SetUp: Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        parcel7.setTrackingNumber("PARCEL7");
        parcel7.setWeight(3.0);
        parcel7.setDimensions(25.0);
        geographicalArea.assignRegisteredMailDeliver(ruby, luke, parcel7);
        
        // SetUp: Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        letter13.setTrackingNumber("LETTER13");
        letter13.setWeight(0.7);
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