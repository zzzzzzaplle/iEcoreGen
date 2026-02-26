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
        area = new GeographicalArea();
        
        // Add Mailman "Aaron" to MainStreet
        mailman = new Mailman("M001", "Aaron");
        area.addMailman(mailman);
        
        // Add Inhabitant "Zoe" to MainStreet
        inhabitant = new Inhabitant("I001", "Zoe");
        area.addInhabitant(inhabitant);
        
        // Create and assign: Letter10 for Zoe (Aaron) and Parcel6 for Zoe (Aaron)
        Letter letter10 = new Letter(mailman, inhabitant);
        Parcel parcel6 = new Parcel(mailman, inhabitant);
        area.assignRegisteredMailDeliver(mailman, inhabitant, letter10);
        area.assignRegisteredMailDeliver(mailman, inhabitant, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Should return list of mail items", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        area = new GeographicalArea();
        
        // Add Inhabitant "Mia" to MarketSquare
        inhabitant = new Inhabitant("I002", "Mia");
        area.addInhabitant(inhabitant);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: null (no mail items)
        assertNull("Should return null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        inhabitant = new Inhabitant("I999", "Noah"); // Not added to area
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: null
        assertNull("Should return null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        area = new GeographicalArea();
        
        // Add Inhabitant "Ella" to OldTown
        inhabitant = new Inhabitant("I003", "Ella");
        area.addInhabitant(inhabitant);
        
        // Create Letter11 (Joseph) assigned to mailman "Peter" in "OldTown"
        Mailman peter = new Mailman("M002", "Peter");
        Inhabitant joseph = new Inhabitant("I004", "Joseph");
        area.addMailman(peter);
        area.addInhabitant(joseph);
        Letter letter11 = new Letter(peter, joseph);
        area.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create and assign: Letter12 for Ella (Peter)
        Letter letter12 = new Letter(peter, inhabitant);
        area.assignRegisteredMailDeliver(peter, inhabitant, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: Letter12
        assertNotNull("Should return list of mail items", result);
        assertEquals("Should contain exactly 1 mail item", 1, result.size());
        assertEquals("Should contain Letter12", letter12, result.get(0));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        area = new GeographicalArea();
        
        // Add Mailman "Ruby" to NewDistrict
        mailman = new Mailman("M003", "Ruby");
        area.addMailman(mailman);
        
        // Add Inhabitant "Luke" to NewDistrict
        inhabitant = new Inhabitant("I005", "Luke");
        area.addInhabitant(inhabitant);
        
        // Create and assign: Letter12 for Luke (Ruby), Parcel7 for Luke (Ruby), Letter13 for Luke (Ruby)
        Letter letter12 = new Letter(mailman, inhabitant);
        Parcel parcel7 = new Parcel(mailman, inhabitant);
        Letter letter13 = new Letter(mailman, inhabitant);
        area.assignRegisteredMailDeliver(mailman, inhabitant, letter12);
        area.assignRegisteredMailDeliver(mailman, inhabitant, parcel7);
        area.assignRegisteredMailDeliver(mailman, inhabitant, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = area.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Should return list of mail items", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
        assertTrue("Should contain Letter13", result.contains(letter13));
    }
}