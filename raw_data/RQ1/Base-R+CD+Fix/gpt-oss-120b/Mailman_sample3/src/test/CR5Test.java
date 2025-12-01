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
        // Common setup that runs before each test
        area = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        area = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        mailman = new Mailman("Aaron");
        area.addMailman(mailman);
        
        // Add Inhabitant "Zoe" to MainStreet
        inhabitant = new Inhabitant("Zoe");
        area.addInhabitant(inhabitant);
        
        // Create and assign: Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter(inhabitant, "Letter10 content");
        area.assignRegisteredMailDeliver(mailman, inhabitant, letter10);
        
        // Create and assign: Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel(inhabitant, 6.0);
        area.assignRegisteredMailDeliver(mailman, inhabitant, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain exactly 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        area = new GeographicalArea();
        
        // Add Inhabitant "Mia" to MarketSquare
        inhabitant = new Inhabitant("Mia");
        area.addInhabitant(inhabitant);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail items exist", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea (no setup needed beyond default)
        area = new GeographicalArea();
        
        // Create non-existent inhabitant "Noah" (not added to area)
        Inhabitant nonExistentInhabitant = new Inhabitant("Noah");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(nonExistentInhabitant);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea();
        
        // Add Mailman "Peter" to OldTown
        Mailman peter = new Mailman("Peter");
        area.addMailman(peter);
        
        // Add Inhabitant "Ella" to OldTown
        Inhabitant ella = new Inhabitant("Ella");
        area.addInhabitant(ella);
        
        // Add Inhabitant "Joseph" to OldTown
        Inhabitant joseph = new Inhabitant("Joseph");
        area.addInhabitant(joseph);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Letter letter11 = new Letter(joseph, "Letter11 content");
        area.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign: Letter12 for Ella (Peter)
        Letter letter12 = new Letter(ella, "Letter12 content");
        area.assignRegisteredMailDeliver(peter, ella, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(ella);
        
        // Expected Output: Letter12 only
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertFalse("Should not contain Letter11 (addressed to Joseph)", result.contains(letter11));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        area = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman("Ruby");
        area.addMailman(ruby);
        
        // Add Inhabitant "Luke" to NewDistrict
        Inhabitant luke = new Inhabitant("Luke");
        area.addInhabitant(luke);
        
        // Create and assign: Letter12 for Luke (Ruby)
        Letter letter12 = new Letter(luke, "Letter12 content");
        area.assignRegisteredMailDeliver(ruby, luke, letter12);
        
        // Create and assign: Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel(luke, 7.0);
        area.assignRegisteredMailDeliver(ruby, luke, parcel7);
        
        // Create and assign: Letter13 for Luke (Ruby)
        Letter letter13 = new Letter(luke, "Letter13 content");
        area.assignRegisteredMailDeliver(ruby, luke, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(luke);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null when mail items exist", result);
        assertEquals("Should contain exactly 3 mail items", 3, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
        assertTrue("Should contain Letter13", result.contains(letter13));
    }
}