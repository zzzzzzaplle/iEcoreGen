import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private GeographicalArea mainStreet;
    private GeographicalArea marketSquare;
    private GeographicalArea oldTown;
    private GeographicalArea newDistrict;
    private Mailman aaron;
    private Mailman peter;
    private Mailman ruby;
    private Inhabitant zoe;
    private Inhabitant mia;
    private Inhabitant ella;
    private Inhabitant luke;
    private Inhabitant noah;
    private Letter letter10;
    private Letter letter11;
    private Letter letter12;
    private Letter letter13;
    private Parcel parcel6;
    private Parcel parcel7;

    @Before
    public void setUp() {
        // Set up common test objects
        mainStreet = new GeographicalArea("MainStreet");
        marketSquare = new GeographicalArea("MarketSquare");
        oldTown = new GeographicalArea("OldTown");
        newDistrict = new GeographicalArea("NewDistrict");
        
        aaron = new Mailman("Aaron");
        peter = new Mailman("Peter");
        ruby = new Mailman("Ruby");
        
        zoe = new Inhabitant("Zoe");
        mia = new Inhabitant("Mia");
        ella = new Inhabitant("Ella");
        luke = new Inhabitant("Luke");
        noah = new Inhabitant("Noah");
    }

    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        // Add Mailman "Aaron" to MainStreet
        mainStreet.addMailman(aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        mainStreet.addInhabitant(zoe);
        
        // Create and assign: Letter10 for Zoe (Aaron), Parcel6 for Zoe (Aaron)
        letter10 = new Letter("L10", zoe, "Hello Zoe");
        parcel6 = new Parcel("P6", zoe, 2.5);
        
        mainStreet.assignMail(letter10, aaron);
        mainStreet.assignMail(parcel6, aaron);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = mainStreet.listMailForInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }

    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        // Add Inhabitant "Mia" to MarketSquare
        marketSquare.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listMailForInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }

    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        // Using mainStreet area which doesn't have Noah
        List<RegisteredMail> result = mainStreet.listMailForInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        // Add Inhabitant "Ella" to OldTown
        oldTown.addInhabitant(ella);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        oldTown.addMailman(peter);
        letter11 = new Letter("L11", ella, "Important document");
        letter12 = new Letter("L12", ella, "Another letter");
        
        // Assign only letter12
        oldTown.assignMail(letter12, peter);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = oldTown.listMailForInhabitant(ella);
        
        // Expected Output: Letter12 only (letter11 is not assigned so shouldn't appear)
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 mail item", 1, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertFalse("Should not contain unassigned letter11", result.contains(letter11));
    }

    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        // Add Mailman "Ruby" to NewDistrict
        newDistrict.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        newDistrict.addInhabitant(luke);
        
        // Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
        letter12 = new Letter("L12", luke, "Letter 1");
        parcel7 = new Parcel("P7", luke, 3.2);
        letter13 = new Letter("L13", luke, "Letter 2");
        
        newDistrict.assignMail(letter12, ruby);
        newDistrict.assignMail(parcel7, ruby);
        newDistrict.assignMail(letter13, ruby);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = newDistrict.listMailForInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}