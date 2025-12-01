import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private MailDeliveryManager manager;
    private GeographicalArea uptown;
    private GeographicalArea suburb;
    private GeographicalArea midtown;
    private GeographicalArea harbor;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
        
        // Create geographical areas
        uptown = new GeographicalArea();
        uptown.setName("Uptown");
        manager.addGeographicalArea(uptown);
        
        suburb = new GeographicalArea();
        suburb.setName("Suburb");
        manager.addGeographicalArea(suburb);
        
        midtown = new GeographicalArea();
        midtown.setName("Midtown");
        manager.addGeographicalArea(midtown);
        
        harbor = new GeographicalArea();
        harbor.setName("Harbor");
        manager.addGeographicalArea(harbor);
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        // SetUp: Add Mailman "James" to Uptown
        Mailman james = new Mailman();
        james.setName("James");
        manager.addMailmanToGeographicalArea(james, uptown);
        
        // SetUp: Add Inhabitants "Grace" and "Henry" to Uptown
        Inhabitant grace = new Inhabitant();
        grace.setName("Grace");
        manager.addInhabitantToGeographicalArea(grace, uptown);
        
        Inhabitant henry = new Inhabitant();
        henry.setName("Henry");
        manager.addInhabitantToGeographicalArea(henry, uptown);
        
        // SetUp: Create and assign Letter1 for Grace (James)
        Letter letter1 = new Letter();
        letter1.setAddressee(grace);
        letter1.setMailman(james);
        uptown.addRegisteredMail(letter1);
        
        // SetUp: Create and assign Parcel1 for Henry (James)
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(henry);
        parcel1.setMailman(james);
        uptown.addRegisteredMail(parcel1);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> result = manager.getRegisteredMailDeliveries(uptown);
        
        // Expected Output: List containing both mail items with James as carrier
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter1", result.contains(letter1));
        assertTrue("Should contain parcel1", result.contains(parcel1));
        assertEquals("Letter1 should have James as carrier", james, result.get(result.indexOf(letter1)).getMailman());
        assertEquals("Parcel1 should have James as carrier", james, result.get(result.indexOf(parcel1)).getMailman());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb" (already done in setUp)
        
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> result = manager.getRegisteredMailDeliveries(suburb);
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown" (already done in setUp)
        
        // SetUp: Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant();
        oscar.setName("Oscar");
        manager.addInhabitantToGeographicalArea(oscar, midtown);
        
        // SetUp: Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter();
        letter4.setAddressee(oscar);
        // Mailman not assigned (null)
        midtown.addRegisteredMail(letter4);
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> result = manager.getRegisteredMailDeliveries(midtown);
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty since mail is unassigned", result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor" (already done in setUp)
        
        // SetUp: Add Mailmen "Lucy" and "Rick" to Harbor
        Mailman lucy = new Mailman();
        lucy.setName("Lucy");
        manager.addMailmanToGeographicalArea(lucy, harbor);
        
        Mailman rick = new Mailman();
        rick.setName("Rick");
        manager.addMailmanToGeographicalArea(rick, harbor);
        
        // SetUp: Add Inhabitant "Tina" to Harbor
        Inhabitant tina = new Inhabitant();
        tina.setName("Tina");
        manager.addInhabitantToGeographicalArea(tina, harbor);
        
        // SetUp: Create and assign Letter5 for Tina (Lucy)
        Letter letter5 = new Letter();
        letter5.setAddressee(tina);
        letter5.setMailman(lucy);
        harbor.addRegisteredMail(letter5);
        
        // SetUp: Create and assign Parcel3 for Tina (Rick)
        Parcel parcel3 = new Parcel();
        parcel3.setAddressee(tina);
        parcel3.setMailman(rick);
        harbor.addRegisteredMail(parcel3);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> result = manager.getRegisteredMailDeliveries(harbor);
        
        // Expected Output: List containing both mail items with respective carriers
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter5", result.contains(letter5));
        assertTrue("Should contain parcel3", result.contains(parcel3));
        
        // Verify carriers
        RegisteredMail foundLetter5 = result.get(result.indexOf(letter5));
        RegisteredMail foundParcel3 = result.get(result.indexOf(parcel3));
        assertEquals("Letter5 should have Lucy as carrier", lucy, foundLetter5.getMailman());
        assertEquals("Parcel3 should have Rick as carrier", rick, foundParcel3.getMailman());
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Create a non-existent area that's not in the manager
        GeographicalArea mountainView = new GeographicalArea();
        mountainView.setName("MountainView");
        
        // Action: Retrieve deliveries for non-existent "MountainView"
        List<RegisteredMail> result = manager.getRegisteredMailDeliveries(mountainView);
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty for non-existent area", result.isEmpty());
    }
}