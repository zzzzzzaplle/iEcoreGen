import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    private GeographicalArea area;
    private Mailman mailman;
    private Inhabitant inhabitant;
    
    @Before
    public void setUp() {
        area = new GeographicalArea();
        mailman = new Mailman();
        inhabitant = new Inhabitant();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        GeographicalArea mainStreet = new GeographicalArea();
        
        // SetUp: Add Mailman "Aaron" to MainStreet
        Mailman aaron = new Mailman();
        mainStreet.addMailman(aaron);
        
        // SetUp: Add Inhabitant "Zoe" to MainStreet
        Inhabitant zoe = new Inhabitant();
        mainStreet.addInhabitant(zoe);
        
        // SetUp: Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, letter10);
        
        // SetUp: Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        mainStreet.assignRegisteredMailDeliver(aaron, zoe, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : mainStreet.getAllDeliveries()) {
            if (mail.getAddressee() != null && mail.getAddressee().equals(zoe)) {
                result.add(mail);
            }
        }
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter10));
        assertTrue(result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        GeographicalArea marketSquare = new GeographicalArea();
        
        // SetUp: Add Inhabitant "Mia" to MarketSquare
        Inhabitant mia = new Inhabitant();
        marketSquare.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = null;
        for (RegisteredMail mail : marketSquare.getAllDeliveries()) {
            if (mail.getAddressee() != null && mail.getAddressee().equals(mia)) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(mail);
            }
        }
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea
        GeographicalArea area = new GeographicalArea();
        
        // Action: List all mail for non-existent "Noah"
        Inhabitant noah = new Inhabitant(); // Create but don't add to area
        List<RegisteredMail> result = null;
        for (RegisteredMail mail : area.getAllDeliveries()) {
            if (mail.getAddressee() != null && mail.getAddressee().equals(noah)) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(mail);
            }
        }
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        GeographicalArea oldTown = new GeographicalArea();
        
        // SetUp: Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant();
        oldTown.addInhabitant(ella);
        
        // SetUp: Create Letter11 assigned to mailman "Peter" in "OldTown"
        // Note: This mail is not assigned to Ella, so it shouldn't appear in results
        Mailman peter = new Mailman();
        oldTown.addMailman(peter);
        Letter letter11 = new Letter();
        oldTown.assignRegisteredMailDeliver(peter, new Inhabitant(), letter11); // Assign to different inhabitant
        
        // SetUp: Create and assign Letter12 for Ella
        Letter letter12 = new Letter();
        oldTown.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : oldTown.getAllDeliveries()) {
            if (mail.getAddressee() != null && mail.getAddressee().equals(ella)) {
                result.add(mail);
            }
        }
        
        // Expected Output: Letter12
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(letter12, result.get(0));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        GeographicalArea newDistrict = new GeographicalArea();
        
        // SetUp: Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        newDistrict.addMailman(ruby);
        
        // SetUp: Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant();
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
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : newDistrict.getAllDeliveries()) {
            if (mail.getAddressee() != null && mail.getAddressee().equals(luke)) {
                result.add(mail);
            }
        }
        
        // Expected Output: List containing all three mail items
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel7));
        assertTrue(result.contains(letter13));
    }
}