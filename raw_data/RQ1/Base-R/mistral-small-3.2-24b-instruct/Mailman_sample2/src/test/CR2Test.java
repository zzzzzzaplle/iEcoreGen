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
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        uptown = new GeographicalArea("Uptown");
        
        // SetUp: Add Mailman "James" to Uptown
        Mailman james = new Mailman("M001", "James");
        manager.addMailman(james, uptown);
        
        // SetUp: Add Inhabitants "Grace" and "Henry" to Uptown
        Inhabitant grace = new Inhabitant("I001", "Grace");
        Inhabitant henry = new Inhabitant("I002", "Henry");
        manager.addInhabitant(grace, uptown);
        manager.addInhabitant(henry, uptown);
        
        // SetUp: Create and assign Letter1 for Grace (James) and Parcel1 for Henry (James)
        Letter letter1 = new Letter("L001", grace);
        Parcel parcel1 = new Parcel("P001", henry);
        manager.assignMailman(letter1, james);
        manager.assignMailman(parcel1, james);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> result = manager.getDeliveriesForArea(uptown);
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals(2, result.size());
        assertTrue(result.contains(letter1));
        assertTrue(result.contains(parcel1));
        assertEquals(james, result.get(0).getAssignedMailman());
        assertEquals(james, result.get(1).getAssignedMailman());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        suburb = new GeographicalArea("Suburb");
        
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> result = manager.getDeliveriesForArea(suburb);
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        midtown = new GeographicalArea("Midtown");
        
        // SetUp: Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant("I003", "Oscar");
        manager.addInhabitant(oscar, midtown);
        
        // SetUp: Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter("L004", oscar);
        // Note: Not assigned to any mailman
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> result = manager.getDeliveriesForArea(midtown);
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        harbor = new GeographicalArea("Harbor");
        
        // SetUp: Add Mailmen "Lucy" and "Rick" to Harbor
        Mailman lucy = new Mailman("M002", "Lucy");
        Mailman rick = new Mailman("M003", "Rick");
        manager.addMailman(lucy, harbor);
        manager.addMailman(rick, harbor);
        
        // SetUp: Add Inhabitant "Tina" to Harbor
        Inhabitant tina = new Inhabitant("I004", "Tina");
        manager.addInhabitant(tina, harbor);
        
        // SetUp: Create and assign Letter5 for Tina (Lucy) and Parcel3 for Tina (Rick)
        Letter letter5 = new Letter("L005", tina);
        Parcel parcel3 = new Parcel("P003", tina);
        manager.assignMailman(letter5, lucy);
        manager.assignMailman(parcel3, rick);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> result = manager.getDeliveriesForArea(harbor);
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals(2, result.size());
        assertTrue(result.contains(letter5));
        assertTrue(result.contains(parcel3));
        
        // Verify correct mailman assignments
        RegisteredMail letterResult = result.stream()
            .filter(mail -> mail.getMailId().equals("L005"))
            .findFirst()
            .orElse(null);
        assertNotNull(letterResult);
        assertEquals(lucy, letterResult.getAssignedMailman());
        
        RegisteredMail parcelResult = result.stream()
            .filter(mail -> mail.getMailId().equals("P003"))
            .findFirst()
            .orElse(null);
        assertNotNull(parcelResult);
        assertEquals(rick, parcelResult.getAssignedMailman());
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        GeographicalArea mountainView = new GeographicalArea("MountainView");
        List<RegisteredMail> result = manager.getDeliveriesForArea(mountainView);
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}