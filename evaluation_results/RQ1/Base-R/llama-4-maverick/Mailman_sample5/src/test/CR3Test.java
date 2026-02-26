import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR3Test {
    
    private MailDeliveryManager manager;
    private GeographicalArea riverside;
    private GeographicalArea lakeside;
    private GeographicalArea downtown;
    private GeographicalArea hillside;
    private GeographicalArea beachfront;
    
    @Before
    public void setUp() {
        manager = new MailDeliveryManager();
        riverside = new GeographicalArea();
        riverside.setName("Riverside");
        
        lakeside = new GeographicalArea();
        lakeside.setName("Lakeside");
        
        downtown = new GeographicalArea();
        downtown.setName("Downtown");
        
        hillside = new GeographicalArea();
        hillside.setName("Hillside");
        
        beachfront = new GeographicalArea();
        beachfront.setName("Beachfront");
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: "Add New Inhabitant to Area"
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        // Expected Output: true (successful addition)
        
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        
        boolean result = manager.manageInhabitant(linda, riverside, true);
        
        assertTrue("Adding Linda to Riverside should return true", result);
        assertTrue("Riverside should contain Linda", riverside.getInhabitants().contains(linda));
        assertEquals("Linda's geographical area should be Riverside", riverside, linda.getGeographicalArea());
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: "Remove Inhabitant with Assigned Mail"
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        // 3. Add Inhabitant "Paul" to Lakeside
        // 4. Create and assign Letter6 for Paul (Ken)
        // Action: Remove Paul from Lakeside
        // Expected Output: true (Paul removed, Letter6 deleted)
        
        Mailman ken = new Mailman();
        ken.setName("Ken");
        manager.manageMailman(ken, lakeside, null, true);
        
        Inhabitant paul = new Inhabitant();
        paul.setName("Paul");
        manager.manageInhabitant(paul, lakeside, true);
        
        Letter letter6 = new Letter();
        letter6.setAddressee(paul);
        letter6.assignMailman(ken);
        lakeside.addRegisteredMail(letter6);
        
        boolean result = manager.manageInhabitant(paul, lakeside, false);
        
        assertTrue("Removing Paul from Lakeside should return true", result);
        assertFalse("Lakeside should not contain Paul", lakeside.getInhabitants().contains(paul));
        assertFalse("Lakeside should not contain letter6", lakeside.getRegisteredMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        // SetUp: Create GeographicalArea "Downtown"
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        // - Add new Inhabitant "Becy" to Downtown, true
        // - Remove Inhabitant "Linda" to Downtown, true
        
        Inhabitant linda = new Inhabitant();
        linda.setName("Linda");
        
        Inhabitant becy = new Inhabitant();
        becy.setName("Becy");
        
        // Add Linda to Downtown
        boolean result1 = manager.manageInhabitant(linda, downtown, true);
        assertTrue("Adding Linda to Downtown should return true", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // Add Becy to Downtown
        boolean result2 = manager.manageInhabitant(becy, downtown, true);
        assertTrue("Adding Becy to Downtown should return true", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // Remove Linda from Downtown
        boolean result3 = manager.manageInhabitant(linda, downtown, false);
        assertTrue("Removing Linda from Downtown should return true", result3);
        assertFalse("Downtown should not contain Linda", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        // SetUp: Create GeographicalArea "Hillside"
        // Action: Remove non-existent "Victor" from Hillside
        // Expected Output: false (inhabitant doesn't exist)
        
        Inhabitant victor = new Inhabitant();
        victor.setName("Victor");
        
        boolean result = manager.manageInhabitant(victor, hillside, false);
        
        assertFalse("Removing non-existent Victor from Hillside should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: "Remove Inhabitant with Multiple Mail Items"
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        // 3. Add Inhabitant "Rachel" to Beachfront
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        //    - Parcel4 for Rachel (Amy)
        // Action: Remove Rachel from Beachfront
        // Expected Output: true (Rachel removed, both mail items deleted)
        
        Mailman amy = new Mailman();
        amy.setName("Amy");
        manager.manageMailman(amy, beachfront, null, true);
        
        Inhabitant rachel = new Inhabitant();
        rachel.setName("Rachel");
        manager.manageInhabitant(rachel, beachfront, true);
        
        Letter letter7 = new Letter();
        letter7.setAddressee(rachel);
        letter7.assignMailman(amy);
        beachfront.addRegisteredMail(letter7);
        
        Parcel parcel4 = new Parcel();
        parcel4.setAddressee(rachel);
        parcel4.assignMailman(amy);
        beachfront.addRegisteredMail(parcel4);
        
        boolean result = manager.manageInhabitant(rachel, beachfront, false);
        
        assertTrue("Removing Rachel from Beachfront should return true", result);
        assertFalse("Beachfront should not contain Rachel", beachfront.getInhabitants().contains(rachel));
        assertFalse("Beachfront should not contain letter7", beachfront.getRegisteredMails().contains(letter7));
        assertFalse("Beachfront should not contain parcel4", beachfront.getRegisteredMails().contains(parcel4));
    }
}