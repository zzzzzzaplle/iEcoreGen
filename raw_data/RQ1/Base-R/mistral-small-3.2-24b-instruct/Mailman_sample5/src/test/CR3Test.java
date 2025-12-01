import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    private MailManagementSystem system;
    private GeographicalArea riverside;
    private GeographicalArea lakeside;
    private GeographicalArea downtown;
    private GeographicalArea hillside;
    private GeographicalArea beachfront;

    @Before
    public void setUp() {
        system = new MailManagementSystem();
        
        // Initialize geographical areas
        riverside = new GeographicalArea();
        riverside.setAreaId("Riverside");
        
        lakeside = new GeographicalArea();
        lakeside.setAreaId("Lakeside");
        
        downtown = new GeographicalArea();
        downtown.setAreaId("Downtown");
        
        hillside = new GeographicalArea();
        hillside.setAreaId("Hillside");
        
        beachfront = new GeographicalArea();
        beachfront.setAreaId("Beachfront");
        
        // Add areas to system
        system.addGeographicalArea(riverside);
        system.addGeographicalArea(lakeside);
        system.addGeographicalArea(downtown);
        system.addGeographicalArea(hillside);
        system.addGeographicalArea(beachfront);
    }

    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = new Inhabitant();
        linda.setInhabitantId("Linda001");
        linda.setName("Linda");
        
        boolean result = system.addInhabitantToArea("Riverside", linda);
        
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
    }

    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp: Create Lakeside area with Mailman Ken and Inhabitant Paul
        Mailman ken = new Mailman();
        ken.setMailmanId("Ken001");
        ken.setName("Ken");
        ken.setGeographicalArea(lakeside);
        lakeside.addMailman(ken);
        
        Inhabitant paul = new Inhabitant();
        paul.setInhabitantId("Paul001");
        paul.setName("Paul");
        paul.setGeographicalArea(lakeside);
        lakeside.addInhabitant(paul);
        
        // Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        letter6.setMailId("Letter6");
        letter6.setAddressee(paul);
        ken.assignMail(letter6);
        
        // Action: Remove Paul from Lakeside
        boolean result = system.removeInhabitantFromArea("Lakeside", paul);
        
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul", lakeside.getInhabitants().contains(paul));
        assertFalse("Ken's deliveries should not contain Letter6", ken.getDeliveries().contains(letter6));
    }

    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: Add multiple inhabitants to Downtown
        Inhabitant linda = new Inhabitant();
        linda.setInhabitantId("Linda002");
        linda.setName("Linda");
        
        Inhabitant becy = new Inhabitant();
        becy.setInhabitantId("Becy001");
        becy.setName("Becy");
        
        // Add Linda to Downtown - should return true
        boolean result1 = system.addInhabitantToArea("Downtown", linda);
        assertTrue("Adding Linda to Downtown should return true", result1);
        
        // Add Becy to Downtown - should return true
        boolean result2 = system.addInhabitantToArea("Downtown", becy);
        assertTrue("Adding Becy to Downtown should return true", result2);
        
        // Remove Linda from Downtown - should return true
        boolean result3 = system.removeInhabitantFromArea("Downtown", linda);
        assertTrue("Removing Linda from Downtown should return true", result3);
        
        // Verify final state
        assertFalse("Downtown should not contain Linda after removal", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }

    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: Remove non-existent "Victor" from Hillside
        Inhabitant victor = new Inhabitant();
        victor.setInhabitantId("Victor001");
        victor.setName("Victor");
        
        boolean result = system.removeInhabitantFromArea("Hillside", victor);
        
        assertFalse("Removing non-existent Victor should return false", result);
        assertFalse("Hillside should not contain Victor", hillside.getInhabitants().contains(victor));
    }

    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp: Create Beachfront area with Mailman Amy and Inhabitant Rachel
        Mailman amy = new Mailman();
        amy.setMailmanId("Amy001");
        amy.setName("Amy");
        amy.setGeographicalArea(beachfront);
        beachfront.addMailman(amy);
        
        Inhabitant rachel = new Inhabitant();
        rachel.setInhabitantId("Rachel001");
        rachel.setName("Rachel");
        rachel.setGeographicalArea(beachfront);
        beachfront.addInhabitant(rachel);
        
        // Create and assign Letter7 for Rachel (Amy)
        Letter letter7 = new Letter();
        letter7.setMailId("Letter7");
        letter7.setAddressee(rachel);
        amy.assignMail(letter7);
        
        // Create and assign Parcel4 for Rachel (Amy)
        Parcel parcel4 = new Parcel();
        parcel4.setMailId("Parcel4");
        parcel4.setAddressee(rachel);
        parcel4.setWeight(2.5);
        parcel4.setDescription("Books");
        amy.assignMail(parcel4);
        
        // Verify initial state
        assertEquals("Amy should have 2 deliveries initially", 2, amy.getDeliveries().size());
        assertTrue("Amy's deliveries should contain Letter7", amy.getDeliveries().contains(letter7));
        assertTrue("Amy's deliveries should contain Parcel4", amy.getDeliveries().contains(parcel4));
        
        // Action: Remove Rachel from Beachfront
        boolean result = system.removeInhabitantFromArea("Beachfront", rachel);
        
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertTrue("Amy's deliveries should be empty after Rachel removal", amy.getDeliveries().isEmpty());
    }
}