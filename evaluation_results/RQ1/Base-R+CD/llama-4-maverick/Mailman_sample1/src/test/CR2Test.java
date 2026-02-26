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
        // Initialize test objects
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
        // Add Mailman "James" to Uptown
        uptown.addMailman(james);
        
        // Add Inhabitants "Grace" and "Henry" to Uptown
        uptown.addInhabitant(grace);
        uptown.addInhabitant(henry);
        
        // Create and assign Letter1 for Grace (James)
        letter1.setAddressee(grace);
        uptown.assignRegisteredMailDeliver(james, grace, letter1);
        
        // Create and assign Parcel1 for Henry (James)
        parcel1.setAddressee(henry);
        uptown.assignRegisteredMailDeliver(james, henry, parcel1);
        
        // Action: Retrieve all deliveries for Uptown
        List<RegisteredMail> result = uptown.getAllDeliveries();
        
        // Expected Output: List containing both mail items with James as carrier
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter1", result.contains(letter1));
        assertTrue("Should contain parcel1", result.contains(parcel1));
        assertEquals("Letter1 carrier should be James", james, letter1.getCarrier());
        assertEquals("Parcel1 carrier should be James", james, parcel1.getCarrier());
    }
    
    @Test
    public void testCase2_RetrieveDeliveriesForEmptyArea() {
        // SetUp: Create empty GeographicalArea "Suburb"
        // Action: Retrieve deliveries for Suburb
        List<RegisteredMail> result = suburb.getAllDeliveries();
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_RetrieveDeliveriesForAreaWithUnassignedMail() {
        // SetUp: Create GeographicalArea "Midtown"
        // Add Inhabitant "Oscar" to Midtown
        midtown.addInhabitant(oscar);
        
        // Create RegisteredLetter "Letter4" for Oscar (unassigned)
        letter4.setAddressee(oscar);
        // Note: Not assigning to any mailman, so it shouldn't appear in deliveries
        
        // Action: Retrieve deliveries for Midtown
        List<RegisteredMail> result = midtown.getAllDeliveries();
        
        // Expected Output: Empty list (mail not assigned to any mailman)
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase4_RetrieveDeliveriesForAreaWithMultipleMailmen() {
        // SetUp: Create GeographicalArea "Harbor"
        // Add Mailmen "Lucy" and "Rick" to Harbor
        harbor.addMailman(lucy);
        harbor.addMailman(rick);
        
        // Add Inhabitant "Tina" to Harbor
        harbor.addInhabitant(tina);
        
        // Create and assign Letter5 for Tina (Lucy)
        letter5.setAddressee(tina);
        harbor.assignRegisteredMailDeliver(lucy, tina, letter5);
        
        // Create and assign Parcel3 for Tina (Rick)
        parcel3.setAddressee(tina);
        harbor.assignRegisteredMailDeliver(rick, tina, parcel3);
        
        // Action: Retrieve deliveries for Harbor
        List<RegisteredMail> result = harbor.getAllDeliveries();
        
        // Expected Output: List containing both mail items with respective carriers
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain 2 mail items", 2, result.size());
        assertTrue("Should contain letter5", result.contains(letter5));
        assertTrue("Should contain parcel3", result.contains(parcel3));
        assertEquals("Letter5 carrier should be Lucy", lucy, letter5.getCarrier());
        assertEquals("Parcel3 carrier should be Rick", rick, parcel3.getCarrier());
    }
    
    @Test
    public void testCase5_RetrieveDeliveriesForNonExistentArea() {
        // Action: Retrieve deliveries for non-existent "MountainView"
        // Create a new area that hasn't been set up (like MountainView)
        GeographicalArea mountainView = new GeographicalArea();
        List<RegisteredMail> result = mountainView.getAllDeliveries();
        
        // Expected Output: Empty list
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
}