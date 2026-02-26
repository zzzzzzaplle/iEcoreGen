import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private MailManagementSystem system;
    
    @Before
    public void setUp() {
        system = new MailManagementSystem();
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        GeographicalArea uptown = new GeographicalArea("Uptown");
        system.addGeographicalArea(uptown);
        
        // Add Mailman "James" to Uptown
        Mailman james = new Mailman("James");
        system.addMailmanToArea("Uptown", james);
        
        // Add Inhabitants "Grace" and "Henry" to Uptown
        Inhabitant grace = new Inhabitant("Grace");
        Inhabitant henry = new Inhabitant("Henry");
        system.addInhabitantToArea("Uptown", grace);
        system.addInhabitantToArea("Uptown", henry);
        
        // Create and assign Letter1 for Grace (James)
        Letter letter1 = new Letter("Letter1", grace);
        grace.addRegisteredMail(letter1);
        system.assignMailToMailman("Uptown", letter1, james);
        
        // Create and assign Parcel1 for Henry (James)
        Parcel parcel1 = new Parcel("Parcel1", henry);
        henry.addRegisteredMail(parcel1);
        system.assignMailToMailman("Uptown", parcel1, james);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> deliveries = system.getDeliveriesForArea("Uptown");
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals(2, deliveries.size());
        assertTrue(deliveries.contains(letter1));
        assertTrue(deliveries.contains(parcel1));
        
        // Verify James is assigned to both mail items
        for (RegisteredMail mail : deliveries) {
            assertEquals(james, mail.getAssignedMailman());
        }
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        GeographicalArea suburb = new GeographicalArea("Suburb");
        system.addGeographicalArea(suburb);
        
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> deliveries = system.getDeliveriesForArea("Suburb");
        
        // Expected Output: Empty list
        assertNotNull(deliveries);
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        GeographicalArea midtown = new GeographicalArea("Midtown");
        system.addGeographicalArea(midtown);
        
        // Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant("Oscar");
        system.addInhabitantToArea("Midtown", oscar);
        
        // Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter("Letter4", oscar);
        oscar.addRegisteredMail(letter4);
        // Note: Not assigned to any mailman
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> deliveries = system.getDeliveriesForArea("Midtown");
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertNotNull(deliveries);
        assertTrue(deliveries.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        GeographicalArea harbor = new GeographicalArea("Harbor");
        system.addGeographicalArea(harbor);
        
        // Add Mailmen "Lucy" and "Rick" to Harbor
        Mailman lucy = new Mailman("Lucy");
        Mailman rick = new Mailman("Rick");
        system.addMailmanToArea("Harbor", lucy);
        system.addMailmanToArea("Harbor", rick);
        
        // Add Inhabitant "Tina" to Harbor
        Inhabitant tina = new Inhabitant("Tina");
        system.addInhabitantToArea("Harbor", tina);
        
        // Create and assign Letter5 for Tina (Lucy)
        Letter letter5 = new Letter("Letter5", tina);
        tina.addRegisteredMail(letter5);
        system.assignMailToMailman("Harbor", letter5, lucy);
        
        // Create and assign Parcel3 for Tina (Rick)
        Parcel parcel3 = new Parcel("Parcel3", tina);
        tina.addRegisteredMail(parcel3);
        system.assignMailToMailman("Harbor", parcel3, rick);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> deliveries = system.getDeliveriesForArea("Harbor");
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals(2, deliveries.size());
        assertTrue(deliveries.contains(letter5));
        assertTrue(deliveries.contains(parcel3));
        
        // Verify correct mailman assignments
        for (RegisteredMail mail : deliveries) {
            if (mail.equals(letter5)) {
                assertEquals(lucy, mail.getAssignedMailman());
            } else if (mail.equals(parcel3)) {
                assertEquals(rick, mail.getAssignedMailman());
            }
        }
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        List<RegisteredMail> deliveries = system.getDeliveriesForArea("MountainView");
        
        // Expected Output: Empty list
        assertNotNull(deliveries);
        assertTrue(deliveries.isEmpty());
    }
}