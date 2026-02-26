import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
    private Parcel parcel6;
    private Letter letter11;
    private Letter letter12;
    private Letter letter13;
    private Parcel parcel7;

    @Before
    public void setUp() {
        // Initialize all objects needed for tests
        mainStreet = new GeographicalArea();
        marketSquare = new GeographicalArea();
        oldTown = new GeographicalArea();
        newDistrict = new GeographicalArea();
        
        aaron = new Mailman();
        peter = new Mailman();
        ruby = new Mailman();
        
        zoe = new Inhabitant();
        mia = new Inhabitant();
        ella = new Inhabitant();
        luke = new Inhabitant();
        noah = new Inhabitant();
        
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
        
        // Create and assign mail items for Zoe
        letter10.setAddressee(zoe);
        parcel6.setAddressee(zoe);
        
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, letter10);
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = mainStreet.listRegisteredMailWithInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter10));
        assertTrue(result.contains(parcel6));
    }

    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        // Add Inhabitant "Mia" to MarketSquare
        marketSquare.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = marketSquare.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }

    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = mainStreet.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull(result);
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
        oldTown.addInhabitant(joseph);
        letter11.setAddressee(joseph);
        oldTown.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign Letter12 for Ella (Peter)
        letter12.setAddressee(ella);
        oldTown.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = oldTown.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(letter12, result.get(0));
    }

    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        // Add Mailman "Ruby" to NewDistrict
        newDistrict.addMailman(ruby);
        // Add Inhabitant "Luke" to NewDistrict
        newDistrict.addInhabitant(luke);
        
        // Create and assign mail items for Luke
        letter12.setAddressee(luke);
        parcel7.setAddressee(luke);
        letter13.setAddressee(luke);
        
        newDistrict.assignRegisteredMailDeliver(ruby, luke, letter12);
        newDistrict.assignRegisteredMailDeliver(ruby, luke, parcel7);
        newDistrict.assignRegisteredMailDeliver(ruby, luke, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = newDistrict.listRegisteredMailWithInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel7));
        assertTrue(result.contains(letter13));
    }
}