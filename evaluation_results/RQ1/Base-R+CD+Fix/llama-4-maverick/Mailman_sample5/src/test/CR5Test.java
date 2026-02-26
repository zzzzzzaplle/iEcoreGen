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
        mailman = new Mailman();
        inhabitant = new Inhabitant();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        mainStreet.addMailman(aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        mainStreet.addInhabitant(zoe);
        
        // Create and assign: Letter10 for Zoe (Aaron) and Parcel6 for Zoe (Aaron)
        Letter letter10 = new Letter();
        Parcel parcel6 = new Parcel();
        
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, letter10);
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
        
        // Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        marketSquare.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea (no setup needed for non-existent inhabitant)
        GeographicalArea area = new GeographicalArea();
        
        // Create a non-existent inhabitant "Noah" that is not added to the area
        Inhabitant noah = new Inhabitant();
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        oldTown.addInhabitant(ella);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        // Note: Since Joseph is not in the area, this assignment will fail
        Inhabitant joseph = new Inhabitant();
        Mailman peter = new Mailman();
        oldTown.addMailman(peter);
        
        Letter letter11 = new Letter();
        // This assignment should fail since Joseph is not in the geographical area
        oldTown.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign: Letter12 for Ella (Peter)
        Letter letter12 = new Letter();
        oldTown.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = oldTown.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertFalse("Should not contain letter11", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        GeographicalArea newDistrict = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        newDistrict.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
        newDistrict.addInhabitant(luke);
        
        // Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
        Letter letter12 = new Letter();
        Parcel parcel7 = new Parcel();
        Letter letter13 = new Letter();
        
        newDistrict.assignRegisteredMailDeliver(ruby, luke, letter12);
        newDistrict.assignRegisteredMailDeliver(ruby, luke, parcel7);
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