import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private Area area;
    private Inhabitant zoe;
    private Inhabitant mia;
    private Inhabitant ella;
    private Inhabitant luke;
    private Inhabitant noah;
    private Mailman aaron;
    private Mailman peter;
    private Mailman ruby;
    private Letter letter10;
    private Parcel parcel6;
    private Letter letter11;
    private Letter letter12;
    private Letter letter13;
    private Parcel parcel7;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        area = new Area();
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
        area.setName("MainStreet");
        
        // Add Mailman "Aaron" to MainStreet
        aaron.setName("Aaron");
        area.addMailman(aaron);
        
        // Add Inhabitant "Zoe" to MainStreet
        zoe.setName("Zoe");
        area.addInhabitant(zoe);
        
        // Create and assign Letter10 for Zoe (Aaron)
        letter10.setAddressee(zoe);
        letter10.setMailman(aaron);
        area.getMails().add(letter10);
        
        // Create and assign Parcel6 for Zoe (Aaron)
        parcel6.setAddressee(zoe);
        parcel6.setMailman(aaron);
        area.getMails().add(parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = area.getMailsForInhabitant(zoe);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter10));
        assertTrue(result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        area.setName("MarketSquare");
        
        // Add Inhabitant "Mia" to MarketSquare
        mia.setName("Mia");
        area.addInhabitant(mia);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.getMailsForInhabitant(mia);
        
        // Expected Output: null (no mail items)
        assertNull(result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: No setup needed as we're testing with non-existent inhabitant
        
        // Action: List all mail for non-existent "Noah"
        noah.setName("Noah");
        List<RegisteredMail> result = area.getMailsForInhabitant(noah);
        
        // Expected Output: null
        assertNull(result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        area.setName("OldTown");
        
        // Add Inhabitant "Ella" to OldTown
        ella.setName("Ella");
        area.addInhabitant(ella);
        
        // Create Letter11 assigned to mailman "Peter" in "OldTown"
        peter.setName("Peter");
        area.addMailman(peter);
        letter11.setAddressee(ella);
        letter11.setMailman(peter);
        area.getMails().add(letter11);
        
        // Create and assign Letter12 for Ella (no mailman specified)
        letter12.setAddressee(ella);
        // Note: letter12 is not assigned to any mailman, but should still be included in the result
        area.getMails().add(letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = area.getMailsForInhabitant(ella);
        
        // Expected Output: List containing both Letter11 and Letter12
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(letter11));
        assertTrue(result.contains(letter12));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        area.setName("NewDistrict");
        
        // Add Mailman "Ruby" to NewDistrict
        ruby.setName("Ruby");
        area.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        luke.setName("Luke");
        area.addInhabitant(luke);
        
        // Create and assign Letter12 for Luke (Ruby)
        letter12.setAddressee(luke);
        letter12.setMailman(ruby);
        area.getMails().add(letter12);
        
        // Create and assign Parcel7 for Luke (Ruby)
        parcel7.setAddressee(luke);
        parcel7.setMailman(ruby);
        area.getMails().add(parcel7);
        
        // Create and assign Letter13 for Luke (Ruby)
        letter13.setAddressee(luke);
        letter13.setMailman(ruby);
        area.getMails().add(letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = area.getMailsForInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(letter12));
        assertTrue(result.contains(parcel7));
        assertTrue(result.contains(letter13));
    }
}