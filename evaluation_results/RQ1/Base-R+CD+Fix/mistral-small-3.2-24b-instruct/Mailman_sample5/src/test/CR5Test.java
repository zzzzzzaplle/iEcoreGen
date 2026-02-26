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
        GeographicalArea mainStreet = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        aaron.setId("M001");
        aaron.setName("Aaron");
        mainStreet.addMailman(aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        zoe.setId("I001");
        zoe.setName("Zoe");
        mainStreet.addInhabitant(zoe);
        
        // Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, letter10);
        
        // Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = mainStreet.listRegisteredMailWithInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null when mail exists", result);
        assertEquals("Should contain exactly 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        GeographicalArea marketSquare = new GeographicalArea();
        
        // Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        mia.setId("I002");
        mia.setName("Mia");
        marketSquare.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Should return null when no mail exists for inhabitant", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea (no setup needed for non-existent inhabitant)
        GeographicalArea area = new GeographicalArea();
        
        // Create a non-existent inhabitant "Noah"
        Inhabitant noah = new Inhabitant();
        noah.setId("I999");
        noah.setName("Noah");
        // Note: Noah is NOT added to the area
        
        // Action: List all mail for Noah (non-existent in the area)
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull("Should return null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        ella.setId("I003");
        ella.setName("Ella");
        oldTown.addInhabitant(ella);
        
        // Add Mailman "Peter" to OldTown
        Mailman peter = new Mailman();
        peter.setId("M002");
        peter.setName("Peter");
        oldTown.addMailman(peter);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Inhabitant joseph = new Inhabitant();
        joseph.setId("I004");
        joseph.setName("Joseph");
        oldTown.addInhabitant(joseph);
        
        Letter letter11 = new Letter();
        oldTown.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        oldTown.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = oldTown.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null when mail exists", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertFalse("Should not contain letter11 (for Joseph)", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        GeographicalArea newDistrict = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        ruby.setId("M003");
        ruby.setName("Ruby");
        newDistrict.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        luke.setId("I005");
        luke.setName("Luke");
        newDistrict.addInhabitant(luke);
        
        // Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        newDistrict.assignRegisteredMailDeliver(ruby, luke, letter12);
        
        // Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        newDistrict.assignRegisteredMailDeliver(ruby, luke, parcel7);
        
        // Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        newDistrict.assignRegisteredMailDeliver(ruby, luke, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = newDistrict.listRegisteredMailWithInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null when mail exists", result);
        assertEquals("Should contain exactly 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}