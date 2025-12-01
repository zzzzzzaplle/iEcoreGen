import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private MailDeliverySystem mailDeliverySystem;
    private GeographicalArea uptown;
    private GeographicalArea suburb;
    private GeographicalArea midtown;
    private GeographicalArea harbor;
    
    @Before
    public void setUp() {
        mailDeliverySystem = new MailDeliverySystem();
        
        // Create geographical areas for use in tests
        uptown = new GeographicalArea("Uptown");
        suburb = new GeographicalArea("Suburb");
        midtown = new GeographicalArea("Midtown");
        harbor = new GeographicalArea("Harbor");
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        // SetUp: Add Mailman "James" to Uptown
        Mailman james = new Mailman("James", uptown);
        mailDeliverySystem.addMailman(james, uptown);
        
        // SetUp: Add Inhabitants "Grace" and "Henry" to Uptown
        Inhabitant grace = new Inhabitant("Grace", uptown);
        Inhabitant henry = new Inhabitant("Henry", uptown);
        mailDeliverySystem.addInhabitant(grace, uptown);
        mailDeliverySystem.addInhabitant(henry, uptown);
        
        // SetUp: Create and assign Letter1 for Grace (James) and Parcel1 for Henry (James)
        Letter letter1 = new Letter(grace, james, uptown);
        Parcel parcel1 = new Parcel(henry, james, uptown);
        mailDeliverySystem.assignMail(letter1, james);
        mailDeliverySystem.assignMail(parcel1, james);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> deliveries = mailDeliverySystem.getDeliveriesForArea(uptown);
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals(2, deliveries.size());
        assertTrue(deliveries.contains(letter1));
        assertTrue(deliveries.contains(parcel1));
        assertEquals(james, deliveries.get(0).getMailman());
        assertEquals(james, deliveries.get(1).getMailman());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> deliveries = mailDeliverySystem.getDeliveriesForArea(suburb);
        
        // Expected Output: Empty list
        assertNotNull(deliveries);
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        // SetUp: Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant("Oscar", midtown);
        mailDeliverySystem.addInhabitant(oscar, midtown);
        
        // SetUp: Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter(oscar, null, midtown);
        // Note: Since mail is unassigned, it won't be added to deliveries
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> deliveries = mailDeliverySystem.getDeliveriesForArea(midtown);
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertNotNull(deliveries);
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        // SetUp: Add Mailmen "Lucy" and "Rick" to Harbor
        Mailman lucy = new Mailman("Lucy", harbor);
        Mailman rick = new Mailman("Rick", harbor);
        mailDeliverySystem.addMailman(lucy, harbor);
        mailDeliverySystem.addMailman(rick, harbor);
        
        // SetUp: Add Inhabitant "Tina" to Harbor
        Inhabitant tina = new Inhabitant("Tina", harbor);
        mailDeliverySystem.addInhabitant(tina, harbor);
        
        // SetUp: Create and assign Letter5 for Tina (Lucy) and Parcel3 for Tina (Rick)
        Letter letter5 = new Letter(tina, lucy, harbor);
        Parcel parcel3 = new Parcel(tina, rick, harbor);
        mailDeliverySystem.assignMail(letter5, lucy);
        mailDeliverySystem.assignMail(parcel3, rick);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> deliveries = mailDeliverySystem.getDeliveriesForArea(harbor);
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals(2, deliveries.size());
        assertTrue(deliveries.contains(letter5));
        assertTrue(deliveries.contains(parcel3));
        
        // Verify correct mailman assignments
        RegisteredMail mail1 = deliveries.get(0);
        RegisteredMail mail2 = deliveries.get(1);
        
        // Check that each mail has the correct mailman assigned
        if (mail1 instanceof Letter) {
            assertEquals(lucy, mail1.getMailman());
            assertEquals(rick, mail2.getMailman());
        } else {
            assertEquals(rick, mail1.getMailman());
            assertEquals(lucy, mail2.getMailman());
        }
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        GeographicalArea mountainView = new GeographicalArea("MountainView");
        List<RegisteredMail> deliveries = mailDeliverySystem.getDeliveriesForArea(mountainView);
        
        // Expected Output: Empty list
        assertNotNull(deliveries);
        assertTrue(deliveries.isEmpty());
    }
}