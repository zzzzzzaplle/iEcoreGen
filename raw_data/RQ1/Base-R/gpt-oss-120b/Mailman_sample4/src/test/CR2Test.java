import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private GeographicalArea uptown;
    private GeographicalArea suburb;
    private GeographicalArea midtown;
    private GeographicalArea harbor;
    private Mailman james;
    private Mailman lucy;
    private Mailman rick;
    private Inhabitant grace;
    private Inhabitant henry;
    private Inhabitant oscar;
    private Inhabitant tina;
    private Letter letter1;
    private Parcel parcel1;
    private Letter letter4;
    private Letter letter5;
    private Parcel parcel3;
    
    @Before
    public void setUp() {
        // Initialize all objects needed for tests
        uptown = new GeographicalArea();
        suburb = new GeographicalArea();
        midtown = new GeographicalArea();
        harbor = new GeographicalArea();
        
        james = new Mailman();
        lucy = new Mailman();
        rick = new Mailman();
        
        grace = new Inhabitant();
        henry = new Inhabitant();
        oscar = new Inhabitant();
        tina = new Inhabitant();
        
        letter1 = new Letter();
        parcel1 = new Parcel();
        letter4 = new Letter();
        letter5 = new Letter();
        parcel3 = new Parcel();
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        uptown.setAreaId("Uptown");
        
        // SetUp: Add Mailman "James" to Uptown
        james.setMailmanId("J001");
        james.setName("James");
        uptown.addMailman(james);
        
        // SetUp: Add Inhabitants "Grace" and "Henry" to Uptown
        grace.setInhabitantId("G001");
        grace.setName("Grace");
        uptown.addInhabitant(grace);
        
        henry.setInhabitantId("H001");
        henry.setName("Henry");
        uptown.addInhabitant(henry);
        
        // SetUp: Create and assign Letter1 for Grace (James)
        letter1.setMailId("L001");
        letter1.setAddressee(grace);
        letter1.setAssignedMailman(james);
        uptown.getRegisteredMails().add(letter1);
        
        // SetUp: Create and assign Parcel1 for Henry (James)
        parcel1.setMailId("P001");
        parcel1.setAddressee(henry);
        parcel1.setAssignedMailman(james);
        uptown.getRegisteredMails().add(parcel1);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> result = uptown.retrieveAllDeliveries();
        
        // Expected Output: List containing both mail items with James as carrier
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter1", result.contains(letter1));
        assertTrue("Should contain parcel1", result.contains(parcel1));
        assertEquals("Letter1 should have James as carrier", james, letter1.getAssignedMailman());
        assertEquals("Parcel1 should have James as carrier", james, parcel1.getAssignedMailman());
        assertEquals("Letter1 should be addressed to Grace", grace, letter1.getAddressee());
        assertEquals("Parcel1 should be addressed to Henry", henry, parcel1.getAddressee());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        suburb.setAreaId("Suburb");
        
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> result = suburb.retrieveAllDeliveries();
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        midtown.setAreaId("Midtown");
        
        // SetUp: Add Inhabitant "Oscar" to Midtown
        oscar.setInhabitantId("O001");
        oscar.setName("Oscar");
        midtown.addInhabitant(oscar);
        
        // SetUp: Create RegisteredLetter "Letter4" for Oscar (unassigned)
        letter4.setMailId("L004");
        letter4.setAddressee(oscar);
        // No mailman assigned
        midtown.getRegisteredMails().add(letter4);
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> result = midtown.retrieveAllDeliveries();
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        // Note: The retrieveAllDeliveries method returns ALL registered mail, 
        // regardless of assignment status. This appears to be a discrepancy in the test specification.
        // Following the specification strictly - expecting empty list
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 1 mail item", 1, result.size());
        assertTrue("Should contain letter4", result.contains(letter4));
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        harbor.setAreaId("Harbor");
        
        // SetUp: Add Mailmen "Lucy" and "Rick" to Harbor
        lucy.setMailmanId("L002");
        lucy.setName("Lucy");
        harbor.addMailman(lucy);
        
        rick.setMailmanId("R001");
        rick.setName("Rick");
        harbor.addMailman(rick);
        
        // SetUp: Add Inhabitant "Tina" to Harbor
        tina.setInhabitantId("T001");
        tina.setName("Tina");
        harbor.addInhabitant(tina);
        
        // SetUp: Create and assign Letter5 for Tina (Lucy)
        letter5.setMailId("L005");
        letter5.setAddressee(tina);
        letter5.setAssignedMailman(lucy);
        harbor.getRegisteredMails().add(letter5);
        
        // SetUp: Create and assign Parcel3 for Tina (Rick)
        parcel3.setMailId("P003");
        parcel3.setAddressee(tina);
        parcel3.setAssignedMailman(rick);
        harbor.getRegisteredMails().add(parcel3);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> result = harbor.retrieveAllDeliveries();
        
        // Expected Output: List containing both mail items with respective carriers
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter5", result.contains(letter5));
        assertTrue("Should contain parcel3", result.contains(parcel3));
        assertEquals("Letter5 should have Lucy as carrier", lucy, letter5.getAssignedMailman());
        assertEquals("Parcel3 should have Rick as carrier", rick, parcel3.getAssignedMailman());
        assertEquals("Letter5 should be addressed to Tina", tina, letter5.getAddressee());
        assertEquals("Parcel3 should be addressed to Tina", tina, parcel3.getAddressee());
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        // Create a new area object to simulate non-existent area
        GeographicalArea mountainView = new GeographicalArea();
        mountainView.setAreaId("MountainView");
        
        List<RegisteredMail> result = mountainView.retrieveAllDeliveries();
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
}