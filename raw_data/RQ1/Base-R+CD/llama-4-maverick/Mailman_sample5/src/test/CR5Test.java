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
        // SetUp
        GeographicalArea mainStreet = new GeographicalArea();
        Mailman aaron = new Mailman();
        Inhabitant zoe = new Inhabitant();
        mainStreet.addMailman(aaron);
        mainStreet.addInhabitant(zoe);
        
        Letter letter10 = new Letter();
        Parcel parcel6 = new Parcel();
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, letter10);
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        
        // Action
        List<RegisteredMail> result = mainStreet.listRegisteredMailForInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter10));
        assertTrue(result.contains(parcel6));
    }

    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp
        GeographicalArea marketSquare = new GeographicalArea();
        Inhabitant mia = new Inhabitant();
        marketSquare.addInhabitant(mia);
        
        // Action
        List<RegisteredMail> result = marketSquare.listRegisteredMailForInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }

    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp
        GeographicalArea area = new GeographicalArea();
        Inhabitant noah = new Inhabitant(); // Not added to area
        
        // Action
        List<RegisteredMail> result = area.listRegisteredMailForInhabitant(noah);
        
        // Expected Output: null
        assertNull(result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp
        GeographicalArea oldTown = new GeographicalArea();
        Inhabitant ella = new Inhabitant();
        oldTown.addInhabitant(ella);
        
        Letter letter12 = new Letter();
        Mailman peter = new Mailman();
        oldTown.addMailman(peter);
        oldTown.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action
        List<RegisteredMail> result = oldTown.listRegisteredMailForInhabitant(ella);
        
        // Expected Output: Letter12
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(letter12, result.get(0));
    }

    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp
        GeographicalArea newDistrict = new GeographicalArea();
        Mailman ruby = new Mailman();
        Inhabitant luke = new Inhabitant();
        newDistrict.addMailman(ruby);
        newDistrict.addInhabitant(luke);
        
        Letter letter12 = new Letter();
        Parcel parcel7 = new Parcel();
        Letter letter13 = new Letter();
        newDistrict.assignRegisteredMailDeliver(ruby, luke, letter12);
        newDistrict.assignRegisteredMailDeliver(ruby, luke, parcel7);
        newDistrict.assignRegisteredMailDeliver(ruby, luke, letter13);
        
        // Action
        List<RegisteredMail> result = newDistrict.listRegisteredMailForInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel7));
        assertTrue(result.contains(letter13));
    }
}