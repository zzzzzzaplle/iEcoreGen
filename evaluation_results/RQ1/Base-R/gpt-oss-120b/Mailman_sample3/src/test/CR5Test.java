import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR5Test {
    
    private GeographicalArea area;
    private Inhabitant inhabitant;
    private Mailman mailman;
    
    @Before
    public void setUp() {
        // Common setup that runs before each test
        area = new GeographicalArea();
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp: Create GeographicalArea "MainStreet"
        area.setName("MainStreet");
        
        // SetUp: Add Mailman "Aaron" to MainStreet
        mailman = new Mailman();
        mailman.setId("M001");
        mailman.setName("Aaron");
        area.addMailman(mailman);
        
        // SetUp: Add Inhabitant "Zoe" to MainStreet
        inhabitant = new Inhabitant();
        inhabitant.setId("I001");
        inhabitant.setName("Zoe");
        area.addInhabitant(inhabitant);
        
        // SetUp: Create and assign Letter10 for Zoe (Aaron)
        Letter letter10 = new Letter();
        letter10.setTrackingNumber("L10");
        letter10.setAddressee(inhabitant);
        letter10.setContent("Important document");
        area.addRegisteredMail(letter10);
        area.assignMail(letter10, mailman);
        
        // SetUp: Create and assign Parcel6 for Zoe (Aaron)
        Parcel parcel6 = new Parcel();
        parcel6.setTrackingNumber("P6");
        parcel6.setAddressee(inhabitant);
        parcel6.setWeight(2.5);
        parcel6.setDescription("Books");
        area.addRegisteredMail(parcel6);
        area.assignMail(parcel6, mailman);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = area.getMailForInhabitant(inhabitant);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain Letter10", result.contains(letter10));
        assertTrue("Should contain Parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp: Create GeographicalArea "MarketSquare"
        area.setName("MarketSquare");
        
        // SetUp: Add Inhabitant "Mia" to MarketSquare
        inhabitant = new Inhabitant();
        inhabitant.setId("I002");
        inhabitant.setName("Mia");
        area.addInhabitant(inhabitant);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = area.getMailForInhabitant(inhabitant);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea (no name specified in test case)
        area.setName("TestArea");
        
        // Create a non-existent inhabitant not added to the area
        Inhabitant nonExistentInhabitant = new Inhabitant();
        nonExistentInhabitant.setId("I999");
        nonExistentInhabitant.setName("Noah");
        
        // Action: List all mail for non-existent "Noah"
        List<RegisteredMail> result = area.getMailForInhabitant(nonExistentInhabitant);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp: Create GeographicalArea "OldTown"
        area.setName("OldTown");
        
        // SetUp: Add Inhabitant "Ella" to OldTown
        inhabitant = new Inhabitant();
        inhabitant.setId("I003");
        inhabitant.setName("Ella");
        area.addInhabitant(inhabitant);
        
        // SetUp: Add Mailman "Peter" to OldTown
        Mailman peter = new Mailman();
        peter.setId("M002");
        peter.setName("Peter");
        area.addMailman(peter);
        
        // SetUp: Create Letter11 assigned to mailman "Peter" in "OldTown"
        Letter letter11 = new Letter();
        letter11.setTrackingNumber("L11");
        letter11.setAddressee(inhabitant);
        letter11.setContent("Invoice");
        area.addRegisteredMail(letter11);
        area.assignMail(letter11, peter);
        
        // SetUp: Create and assign Letter12 for Ella
        Letter letter12 = new Letter();
        letter12.setTrackingNumber("L12");
        letter12.setAddressee(inhabitant);
        letter12.setContent("Newsletter");
        area.addRegisteredMail(letter12);
        area.assignMail(letter12, peter);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = area.getMailForInhabitant(inhabitant);
        
        // Expected Output: List containing Letter12 (and Letter11)
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain Letter11", result.contains(letter11));
        assertTrue("Should contain Letter12", result.contains(letter12));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp: Create GeographicalArea "NewDistrict"
        area.setName("NewDistrict");
        
        // SetUp: Add Mailman "Ruby" to NewDistrict
        Mailman ruby = new Mailman();
        ruby.setId("M003");
        ruby.setName("Ruby");
        area.addMailman(ruby);
        
        // SetUp: Add Inhabitant "Luke" to NewDistrict
        inhabitant = new Inhabitant();
        inhabitant.setId("I004");
        inhabitant.setName("Luke");
        area.addInhabitant(inhabitant);
        
        // SetUp: Create and assign Letter12 for Luke (Ruby)
        Letter letter12 = new Letter();
        letter12.setTrackingNumber("L12");
        letter12.setAddressee(inhabitant);
        letter12.setContent("Contract");
        area.addRegisteredMail(letter12);
        area.assignMail(letter12, ruby);
        
        // SetUp: Create and assign Parcel7 for Luke (Ruby)
        Parcel parcel7 = new Parcel();
        parcel7.setTrackingNumber("P7");
        parcel7.setAddressee(inhabitant);
        parcel7.setWeight(5.0);
        parcel7.setDescription("Electronics");
        area.addRegisteredMail(parcel7);
        area.assignMail(parcel7, ruby);
        
        // SetUp: Create and assign Letter13 for Luke (Ruby)
        Letter letter13 = new Letter();
        letter13.setTrackingNumber("L13");
        letter13.setAddressee(inhabitant);
        letter13.setContent("Greeting card");
        area.addRegisteredMail(letter13);
        area.assignMail(letter13, ruby);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = area.getMailForInhabitant(inhabitant);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain Letter12", result.contains(letter12));
        assertTrue("Should contain Parcel7", result.contains(parcel7));
        assertTrue("Should contain Letter13", result.contains(letter13));
    }
}