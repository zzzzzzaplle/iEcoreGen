import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
    private Letter letter10;
    private Parcel parcel6;
    private Letter letter11;
    private Letter letter12;
    private Letter letter13;
    private Parcel parcel7;

    @Before
    public void setUp() {
        // Create geographical areas
        mainStreet = new GeographicalArea();
        marketSquare = new GeographicalArea();
        oldTown = new GeographicalArea();
        newDistrict = new GeographicalArea();
        
        // Create mailmen
        aaron = new Mailman();
        aaron.setName("Aaron");
        
        peter = new Mailman();
        peter.setName("Peter");
        
        ruby = new Mailman();
        ruby.setName("Ruby");
        
        // Create inhabitants
        zoe = new Inhabitant();
        zoe.setName("Zoe");
        
        mia = new Inhabitant();
        mia.setName("Mia");
        
        ella = new Inhabitant();
        ella.setName("Ella");
        
        luke = new Inhabitant();
        luke.setName("Luke");
        
        // Create mail items
        letter10 = new Letter();
        parcel6 = new Parcel();
        letter11 = new Letter();
        letter12 = new Letter();
        letter13 = new Letter();
        parcel7 = new Parcel();
    }

    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        // Add Mailman "Aaron" to MainStreet
        mainStreet.addMailman(aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        mainStreet.addInhabitant(zoe);
        
        // Create and assign: Letter10 for Zoe (Aaron), Parcel6 for Zoe (Aaron)
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
        // Add Inhabitant "Mia" to MarketSquare
        marketSquare.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }

    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        Inhabitant noah = new Inhabitant();
        noah.setName("Noah");
        
        List<RegisteredMail> result = marketSquare.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        // Add Inhabitant "Ella" to OldTown
        oldTown.addInhabitant(ella);
        
        // Add Mailman "Peter" to OldTown
        oldTown.addMailman(peter);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Inhabitant joseph = new Inhabitant();
        joseph.setName("Joseph");
        oldTown.addInhabitant(joseph);
        oldTown.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign: Letter12 for Ella (Peter)
        oldTown.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = oldTown.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 mail item", 1, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertFalse("Should not contain letter11", result.contains(letter11));
    }

    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        // Add Mailman "Ruby" to NewDistrict
        newDistrict.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        newDistrict.addInhabitant(luke);
        
        // Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
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