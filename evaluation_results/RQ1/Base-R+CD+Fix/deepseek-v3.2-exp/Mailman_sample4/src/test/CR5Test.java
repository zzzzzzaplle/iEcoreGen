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
    }
    
    @Test
    public void testCase1_ListMultipleMailItemsForInhabitant() {
        // SetUp
        geographicalArea.setAreaName("MainStreet");
        mailman = new Mailman();
        mailman.setName("Aaron");
        mailman.setId("M001");
        geographicalArea.addMailman(mailman);
        
        inhabitant = new Inhabitant();
        inhabitant.setName("Zoe");
        inhabitant.setId("I001");
        inhabitant.setAddress("123 Main St");
        geographicalArea.addInhabitant(inhabitant);
        
        Letter letter10 = new Letter();
        letter10.setTrackingNumber("L10");
        letter10.setWeight(0.5);
        letter10.setUrgent(false);
        
        Parcel parcel6 = new Parcel();
        parcel6.setTrackingNumber("P6");
        parcel6.setWeight(2.5);
        parcel6.setDimensions(10.0);
        parcel6.setRequiresSignature(true);
        
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, letter10);
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, parcel6);
        
        // Action: List all mail for Zoe
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing both Letter10 and Parcel6
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter10", result.contains(letter10));
        assertTrue("Should contain parcel6", result.contains(parcel6));
    }
    
    @Test
    public void testCase2_ListMailForInhabitantWithNoMail() {
        // SetUp
        geographicalArea.setAreaName("MarketSquare");
        inhabitant = new Inhabitant();
        inhabitant.setName("Mia");
        inhabitant.setId("I002");
        inhabitant.setAddress("456 Market St");
        geographicalArea.addInhabitant(inhabitant);
        
        // Action: List all mail for Mia
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: null (no mail items)
        assertNull("Result should be null when no mail exists", result);
    }
    
    @Test
    public void testCase3_ListMailForNonExistentInhabitant() {
        // Action: List all mail for non-existent "Noah"
        Inhabitant nonExistentInhabitant = new Inhabitant();
        nonExistentInhabitant.setName("Noah");
        nonExistentInhabitant.setId("I999");
        nonExistentInhabitant.setAddress("999 Unknown St");
        
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(nonExistentInhabitant);
        
        // Expected Output: null
        assertNull("Result should be null for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase4_ListOnlyAssignedMailForInhabitant() {
        // SetUp
        geographicalArea.setAreaName("OldTown");
        
        // Add mailman Peter
        Mailman peter = new Mailman();
        peter.setName("Peter");
        peter.setId("M003");
        geographicalArea.addMailman(peter);
        
        // Add inhabitant Joseph
        Inhabitant joseph = new Inhabitant();
        joseph.setName("Joseph");
        joseph.setId("I003");
        joseph.setAddress("789 Old Rd");
        geographicalArea.addInhabitant(joseph);
        
        // Add inhabitant Ella
        inhabitant = new Inhabitant();
        inhabitant.setName("Ella");
        inhabitant.setId("I004");
        inhabitant.setAddress("101 Old Rd");
        geographicalArea.addInhabitant(inhabitant);
        
        // Create Letter11 for Joseph (assigned to Peter)
        Letter letter11 = new Letter();
        letter11.setTrackingNumber("L11");
        letter11.setWeight(0.3);
        letter11.setUrgent(true);
        geographicalArea.assignRegisteredMailDeliver(peter, joseph, letter11);
        
        // Create Letter12 for Ella (assigned to Peter)
        Letter letter12 = new Letter();
        letter12.setTrackingNumber("L12");
        letter12.setWeight(0.4);
        letter12.setUrgent(false);
        geographicalArea.assignRegisteredMailDeliver(peter, inhabitant, letter12);
        
        // Action: List all mail for Ella
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: Letter12
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 mail item", 1, result.size());
        assertEquals("Should be letter12", letter12, result.get(0));
    }
    
    @Test
    public void testCase5_ListMailForInhabitantWithMixedMailTypes() {
        // SetUp
        geographicalArea.setAreaName("NewDistrict");
        
        // Add mailman Ruby
        mailman = new Mailman();
        mailman.setName("Ruby");
        mailman.setId("M004");
        geographicalArea.addMailman(mailman);
        
        // Add inhabitant Luke
        inhabitant = new Inhabitant();
        inhabitant.setName("Luke");
        inhabitant.setId("I005");
        inhabitant.setAddress("222 New Ave");
        geographicalArea.addInhabitant(inhabitant);
        
        // Create and assign mail items for Luke
        Letter letter12 = new Letter();
        letter12.setTrackingNumber("L12");
        letter12.setWeight(0.6);
        letter12.setUrgent(true);
        
        Parcel parcel7 = new Parcel();
        parcel7.setTrackingNumber("P7");
        parcel7.setWeight(5.0);
        parcel7.setDimensions(15.0);
        parcel7.setRequiresSignature(false);
        
        Letter letter13 = new Letter();
        letter13.setTrackingNumber("L13");
        letter13.setWeight(0.7);
        letter13.setUrgent(false);
        
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, letter12);
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, parcel7);
        geographicalArea.assignRegisteredMailDeliver(mailman, inhabitant, letter13);
        
        // Action: List all mail for Luke
        List<RegisteredMail> result = geographicalArea.listRegisteredMailWithInhabitant(inhabitant);
        
        // Expected Output: List containing all three mail items
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 3 mail items", 3, result.size());
        assertTrue("Should contain letter12", result.contains(letter12));
        assertTrue("Should contain parcel7", result.contains(parcel7));
        assertTrue("Should contain letter13", result.contains(letter13));
    }
}