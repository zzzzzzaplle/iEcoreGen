import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private MailDeliverySystem system;
    private Area uptown;
    private Area suburb;
    private Area midtown;
    private Area harbor;
    
    @Before
    public void setUp() {
        system = new MailDeliverySystem();
        
        // Create areas for tests
        uptown = new Area();
        uptown.setName("Uptown");
        
        suburb = new Area();
        suburb.setName("Suburb");
        
        midtown = new Area();
        midtown.setName("Midtown");
        
        harbor = new Area();
        harbor.setName("Harbor");
        
        // Add areas to system
        List<Area> areas = new ArrayList<>();
        areas.add(uptown);
        areas.add(suburb);
        areas.add(midtown);
        areas.add(harbor);
        system.setAreas(areas);
    }
    
    @Test
    public void testCase1_RetrieveDeliveriesForBusyArea() {
        // SetUp: Create GeographicalArea "Uptown"
        // Add Mailman "James" to Uptown
        Mailman james = new Mailman();
        james.setName("James");
        system.addMailman(james, uptown);
        
        // Add Inhabitants "Grace" and "Henry" to Uptown
        Inhabitant grace = new Inhabitant();
        grace.setName("Grace");
        system.addInhabitant(grace, uptown);
        
        Inhabitant henry = new Inhabitant();
        henry.setName("Henry");
        system.addInhabitant(henry, uptown);
        
        // Create and assign: Letter1 for Grace (James), Parcel1 for Henry (James)
        Letter letter1 = new Letter();
        Parcel parcel1 = new Parcel();
        
        system.assignMailmanToDeliverMail(letter1, james, grace);
        system.assignMailmanToDeliverMail(parcel1, james, henry);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> result = system.getDeliveriesForArea(uptown);
        
        // Expected Output: List containing both mail items with James as carrier
        assertEquals(2, result.size());
        assertTrue(result.contains(letter1));
        assertTrue(result.contains(parcel1));
        assertEquals(james, letter1.getDeliveringMailman());
        assertEquals(james, parcel1.getDeliveringMailman());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> result = system.getDeliveriesForArea(suburb);
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        // Add Inhabitant "Oscar" to Midtown
        Inhabitant oscar = new Inhabitant();
        oscar.setName("Oscar");
        system.addInhabitant(oscar, midtown);
        
        // Create RegisteredLetter "Letter4" for Oscar (unassigned)
        Letter letter4 = new Letter();
        letter4.setAddressee(oscar);
        // Note: Not assigned to any mailman
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> result = system.getDeliveriesForArea(midtown);
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        // Add Mailmen "Lucy" and "Rick" to Harbor
        Mailman lucy = new Mailman();
        lucy.setName("Lucy");
        system.addMailman(lucy, harbor);
        
        Mailman rick = new Mailman();
        rick.setName("Rick");
        system.addMailman(rick, harbor);
        
        // Add Inhabitant "Tina" to Harbor
        Inhabitant tina = new Inhabitant();
        tina.setName("Tina");
        system.addInhabitant(tina, harbor);
        
        // Create and assign: Letter5 for Tina (Lucy), Parcel3 for Tina (Rick)
        Letter letter5 = new Letter();
        Parcel parcel3 = new Parcel();
        
        system.assignMailmanToDeliverMail(letter5, lucy, tina);
        system.assignMailmanToDeliverMail(parcel3, rick, tina);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> result = system.getDeliveriesForArea(harbor);
        
        // Expected Output: List containing both mail items with respective carriers
        assertEquals(2, result.size());
        assertTrue(result.contains(letter5));
        assertTrue(result.contains(parcel3));
        assertEquals(lucy, letter5.getDeliveringMailman());
        assertEquals(rick, parcel3.getDeliveringMailman());
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        Area mountainView = new Area();
        mountainView.setName("MountainView");
        
        List<RegisteredMail> result = system.getDeliveriesForArea(mountainView);
        
        // Expected Output: Empty list
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}