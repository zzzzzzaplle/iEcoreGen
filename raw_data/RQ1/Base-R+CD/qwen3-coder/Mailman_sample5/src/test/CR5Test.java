import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    private GeographicalArea geographicalArea;
    private Inhabitant zoe;
    private Inhabitant mia;
    private Inhabitant ella;
    private Inhabitant luke;
    private Inhabitant noah;
    private Mailman aaron;
    private Mailman peter;
    private Mailman ruby;
    private RegisteredMail letter10;
    private RegisteredMail parcel6;
    private RegisteredMail letter11;
    private RegisteredMail letter12;
    private RegisteredMail letter13;
    private RegisteredMail parcel7;

    @Before
    public void setUp() {
        geographicalArea = new GeographicalArea();
        zoe = new Inhabitant();
        mia = new Inhabitant();
        ella = new Inhabitant();
        luke = new Inhabitant();
        noah = new Inhabitant();
        aaron = new Mailman();
        peter = new Mailman();
        ruby = new Mailman();
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
        // 1. Create GeographicalArea "MainStreet"
        // 2. Add Mailman "Aaron" to MainStreet
        geographicalArea.addMailman(aaron);
        // 3. Add Inhabitant "Zoe" to MainStreet
        geographicalArea.addInhabitant(zoe);
        // 4. Create and assign: Letter10 for Zoe (Aaron), Parcel6 for Zoe (Aaron)
        letter10.setAddressee(zoe);
        parcel6.setAddressee(zoe);
        geographicalArea.getAllMails().add(letter10);
        geographicalArea.getAllMails().add(parcel6);
        geographicalArea.assignRegisteredMailDeliver(aaron, zoe, letter10);
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
        // 1. Create GeographicalArea "MarketSquare"
        // 2. Add Inhabitant "Mia" to MarketSquare
        geographicalArea.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }

    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(noah);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }

    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        // 1. Create GeographicalArea "OldTown"
        // 2. Add Inhabitant "Ella" to OldTown
        geographicalArea.addInhabitant(ella);
        // 3. Add Mailman "Peter" to OldTown
        geographicalArea.addMailman(peter);
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Inhabitant joseph = new Inhabitant();
        geographicalArea.addInhabitant(joseph);
        letter11.setAddressee(joseph);
        geographicalArea.getAllMails().add(letter11);
        geographicalArea.assignRegisteredMailDeliver(peter, joseph, letter11);
        // 4. Create and assign: Letter12 for Ella (Peter)
        letter12.setAddressee(ella);
        geographicalArea.getAllMails().add(letter12);
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
        // 1. Create GeographicalArea "NewDistrict"
        // 2. Add Mailman "Ruby" to NewDistrict
        geographicalArea.addMailman(ruby);
        // 3. Add Inhabitant "Luke" to NewDistrict
        geographicalArea.addInhabitant(luke);
        // 4. Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
        letter12.setAddressee(luke);
        parcel7.setAddressee(luke);
        letter13.setAddressee(luke);
        geographicalArea.getAllMails().add(letter12);
        geographicalArea.getAllMails().add(parcel7);
        geographicalArea.getAllMails().add(letter13);
        geographicalArea.assignRegisteredMailDeliver(ruby, luke, letter12);
        geographicalArea.assignRegisteredMailDeliver(ruby, luke, parcel7);
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